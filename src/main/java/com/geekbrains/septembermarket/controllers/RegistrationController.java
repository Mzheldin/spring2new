package com.geekbrains.septembermarket.controllers;

import com.geekbrains.septembermarket.entities.User;
import com.geekbrains.septembermarket.services.MailService;
import com.geekbrains.septembermarket.services.OrderService;
import com.geekbrains.septembermarket.services.UserService;
import com.geekbrains.septembermarket.utils.SystemUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private UserService userService;
    private OrderService orderService;
    private MailService mailService;
    private Logger log = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/")
    public String showMyLoginPage(Model model) {
        model.addAttribute("systemUser", new SystemUser());
        return "registration-form";
    }

    @PostMapping("/process")
    public String processRegistrationForm(@Valid @ModelAttribute("systemUser") SystemUser systemUser, BindingResult bindingResult, Model model) {
        String username = systemUser.getUsername();
        if (bindingResult.hasErrors()) {
            return "registration-form";
        }
        User existing = userService.findByUsername(username);
        if (existing != null) {
            model.addAttribute("systemUser", systemUser);
            model.addAttribute("registrationError", "User with current username is already exist");
            return "registration-form";
        }
        userService.save(systemUser);
        return "registration-confirmation";
    }


    @PostMapping("/shortFormProcess")
    public String shortProcessRegistrationForm(@ModelAttribute("systemUser") SystemUser systemUser, Model model) {
        systemUser.setUsername(systemUser.getEmail());
        systemUser.setFirstName(systemUser.getEmail());
        systemUser.setLastName(systemUser.getEmail());
        systemUser.setPassword(systemUser.getPhone());
        systemUser.setMatchingPassword(systemUser.getPhone());
        String username = systemUser.getUsername();
        User existing = userService.findByUsername(username);
        if (existing != null) {
            model.addAttribute("systemUser", systemUser);
            model.addAttribute("registrationError", "User with current username is already exist");
            log.warn("warrrnn");
            return "short-registration-form";
        }
        mailService.sendOrderMail(orderService.createOrder(userService.save(systemUser)));
        return "redirect:/shop";
    }
}
