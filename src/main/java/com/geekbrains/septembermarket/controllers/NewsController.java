package com.geekbrains.septembermarket.controllers;

import com.geekbrains.septembermarket.entities.News;
import com.geekbrains.septembermarket.entities.Role;
import com.geekbrains.septembermarket.services.MailService;
import com.geekbrains.septembermarket.services.NewsService;
import com.geekbrains.septembermarket.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/news")
public class NewsController {

    private NewsService newsService;
    private UserService userService;
    private MailService mailService;

    @Autowired
    public void setNewsService(NewsService newsService) {
        this.newsService = newsService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("")
    public String show(Model model){
        model.addAttribute("news", newsService.findAll());
        return "news-panel";
    }

    @GetMapping("/news/send")
    public void sendToRole(@RequestParam("role") Role role, @RequestParam("role") Long newsId){
        userService.findAllByRole(role).forEach(user -> {
            if (newsService.findOneById(newsId).isPresent())
                mailService.sendNewsMail(newsService.findOneById(newsId).get(), user);
        });
    }
}
