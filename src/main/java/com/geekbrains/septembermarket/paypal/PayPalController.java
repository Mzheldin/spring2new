package com.geekbrains.septembermarket.paypal;

import com.geekbrains.septembermarket.entities.Order;
import com.geekbrains.septembermarket.services.OrderService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/paypal")
public class PayPalController {
    private String clientId = "Af7vXvQm8q1xSzqPQaCEK05gzb7UlVmXHMcZ7KBbTYBGv0LYP3SIS_sUifeWH7s3j9qUGIGO-LqwyyMs";
    private String clientSecret = "EJssbTNb4fYccomCJfRGu3OcWTziQWj57gnbwfZ0mKNbW4W-X03mQM5TzRc3yJ5W2cKQO-7H0eYyzIc4";
    private String mode = "sandbox";

    private APIContext apiContext = new APIContext(clientId, clientSecret, mode);

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping("/buy")
    public String buy(@ModelAttribute(name = "order") Order order, HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            Payer payer = new Payer();
            payer.setPaymentMethod("paypal");
            RedirectUrls redirectUrls = new RedirectUrls();
            redirectUrls.setCancelUrl("http://localhost:8189/market/paypal/cancel");
            redirectUrls.setReturnUrl("http://localhost:8189/market/paypal/success");

            Amount amount = new Amount();
            amount.setCurrency("RUB");
            amount.setTotal(order.getPrice().toString());

            Transaction transaction = new Transaction();
            transaction.setAmount(amount);
            transaction.setDescription("Покупка в September Market");

            List<Transaction> transactions = new ArrayList<>();
            transactions.add(transaction);

            Payment payment = new Payment();
            payment.setPayer(payer);
            payment.setRedirectUrls(redirectUrls);
            payment.setTransactions(transactions);
            payment.setIntent("sale");

            Payment doPayment = payment.create(apiContext);

            for (Links link : doPayment.getLinks()) {
                if (link.getRel().equalsIgnoreCase("approval_url")) {
                    return link.getHref().equals("/success") ? "redirect:" + link.getHref() + "/" + order.getId() : "redirect:" + link.getHref();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("message", "Вы сюда не должны были попасть...");
        return "paypal-result";
    }

    @GetMapping("/success/{orderId}")
    public String success(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Long orderId) {
        try {
            String paymentId = request.getParameter("paymentId");
            String payerId = request.getParameter("PayerID");

            if (paymentId == null || paymentId.isEmpty() || payerId == null || payerId.isEmpty()) {
                return "redirect:/";
            }

            Payment payment = new Payment();
            payment.setId(paymentId);

            PaymentExecution paymentExecution = new PaymentExecution();
            paymentExecution.setPayerId(payerId);

            Payment executedPayment = payment.execute(apiContext, paymentExecution);

            if (executedPayment.getState().equals("approved")) {
                Order order = orderService.findOrderById(orderId);
                if (order != null){
                    order.setStatus(Order.Status.PAID);
                    orderService.saveOrder(order);
                }
                model.addAttribute("message", "Ваш заказ сформирован");
            } else {
                model.addAttribute("message", "Что-то пошло не так при формировании заказа, попробуйте повторить операцию");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "paypal-result";
    }

    @GetMapping("/cancel")
    public String cancel(Model model) {
        model.addAttribute("message", "Оплата заказа не была проведена. Возможно Вы отменили ее...");
        return "paypal-result";
    }
}