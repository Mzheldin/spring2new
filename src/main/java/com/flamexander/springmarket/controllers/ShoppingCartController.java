package com.flamexander.springmarket.controllers;

import com.flamexander.springmarket.utils.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/shop/cart")
public class ShoppingCartController {
    private ShoppingCart shoppingCart;

    @Autowired
    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @GetMapping
    public String cartPage(Model model) {
        model.addAttribute("cart", shoppingCart);
        return "cart-page";
    }

    @GetMapping("/add/{id}")
    public String addProductToCart(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        shoppingCart.add(id);
        return "redirect:" + httpServletRequest.getHeader("referer");
    }
}
