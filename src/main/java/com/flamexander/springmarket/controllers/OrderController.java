package com.flamexander.springmarket.controllers;

import com.flamexander.springmarket.entities.Order;
import com.flamexander.springmarket.entities.TempUser;
import com.flamexander.springmarket.entities.User;
import com.flamexander.springmarket.services.DeliveryAddressService;
import com.flamexander.springmarket.services.OrderService;
import com.flamexander.springmarket.services.TempUserService;
import com.flamexander.springmarket.services.UserService;
import com.flamexander.springmarket.utils.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class OrderController {
    private UserService userService;
    private OrderService orderService;
    private DeliveryAddressService deliverAddressService;
    private ShoppingCart shoppingCart;
    private TempUserService tempUserService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setDeliverAddressService(DeliveryAddressService deliverAddressService) {
        this.deliverAddressService = deliverAddressService;
    }

    @Autowired
    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @GetMapping("/order/fill")
    public String orderFill(Model model, Principal principal) {
        model.addAttribute("cart", shoppingCart);
        if (principal == null) {
            model.addAttribute("deliveryAddresses", null);
            //return "redirect:/login";
        } else {
            User user = userService.findByUserName(principal.getName());
            model.addAttribute("deliveryAddresses", deliverAddressService.getUserAddresses(user.getId()));
        }
        return "order-filler";
    }

    @PostMapping("/order/confirm")
    public String orderConfirm(Model model, Principal principal,
                               @RequestParam("phoneNumber") String phoneNumber,
                               @RequestParam("deliveryAddressId") Long deliveryAddressId,
                               @RequestParam("deliveryAddress") String deliveryAddress,
                               @RequestParam("userName") String userName,
                               @RequestParam("email") String email) {
        User user = null;
        String address;
        if (principal == null) {
            if (deliveryAddress != null && userName != null && email != null){
                TempUser tempUser = tempUserService.create(userName, phoneNumber, email, deliveryAddress);
                tempUserService.save(tempUser);
                address = deliveryAddress;
            } else
                return "redirect:/login";
        } else{
            user = userService.findByUserName(principal.getName());
            address = deliverAddressService.getUserAddressById(deliveryAddressId).getAddress();
        }
        Order order = orderService.makeOrder(shoppingCart, user);
        order.setAddress(address);
        order.setPhoneNumber(phoneNumber);
        order.setDeliveryDate(LocalDateTime.now().plusDays(7));
        order.setDeliveryPrice(BigDecimal.valueOf(0.0));
        order = orderService.saveOrder(order);
        model.addAttribute("order", order);
        return "order-before-purchase";
    }

    @GetMapping("/order/result/{id}")
    public String orderConfirm(Model model, @PathVariable(name = "id") Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        // todo ждем до оплаты, проверка безопасности и проблема с повторной отправкой письма сделать одноразовый вход
        User user = userService.findByUserName(principal.getName());
        Order confirmedOrder = orderService.findById(id);
        if (!user.getId().equals(confirmedOrder.getUser().getId())) {
            return "redirect:/";
        }
        model.addAttribute("order", confirmedOrder);
        return "order-result";
    }
}
