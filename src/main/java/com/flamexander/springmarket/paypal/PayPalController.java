package com.flamexander.springmarket.paypal;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/paypal")
public class PayPalController {
    private String clientId = "Ac6mqPzlr247b0QMzUzKBLOZ9dQz3ChHuKY_2LEw6zXgbIaMnQvonkW8SuBz_GCYhBsUjEN_ZO5U_4OT";
    private String clientSecret = "EL1h1RGHIpG5C38mt0WpsNFnWbilSATbTad7Sz2PbHkiiT_fststoPlrI7A75SQsHPJ44mafrl6f_W1z";
    private String mode = "sandbox";

    private APIContext apiContext = new APIContext(clientId, clientSecret, mode);

    @RequestMapping("/buy")
    public String buy(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            Payer payer = new Payer();
            payer.setPaymentMethod("paypal");
            RedirectUrls redirectUrls = new RedirectUrls();
            redirectUrls.setCancelUrl("http://localhost:8189/springmarket/paypal/cancel");
            redirectUrls.setReturnUrl("http://localhost:8189/springmarket/paypal/success");

            Amount amount = new Amount();
            amount.setCurrency("RUB");
            amount.setTotal("1.0");

            Transaction transaction = new Transaction();
            transaction.setAmount(amount);
            transaction.setDescription("Покупка в SpringMarket");

            List<Transaction> transactions = new ArrayList<>();
            transactions.add(transaction);

            Payment payment = new Payment();
            payment.setPayer(payer);
            payment.setRedirectUrls(redirectUrls);
            payment.setTransactions(transactions);
            payment.setIntent("sale");

            Payment doPayment = payment.create(apiContext);

            Iterator<Links> links = doPayment.getLinks().iterator();

            while (links.hasNext()) {
                Links link = links.next();
                if (link.getRel().equalsIgnoreCase("approval_url")) {
                    return "redirect:" + link.getHref();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "result";
    }

    @GetMapping("/success")
    public String success(HttpServletRequest request, HttpServletResponse response, Model model) {
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
                model.addAttribute("message", "Ваш заказ сформирован");
            } else {
                model.addAttribute("message", "Что-то пошло не так при формировании заказа, попробуйте повторить операцию");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "page-success";
    }
}
