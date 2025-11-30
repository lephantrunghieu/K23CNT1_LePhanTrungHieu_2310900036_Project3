package com.lpth.webLaptop.controller;

import com.lpth.webLaptop.repository.HangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private HangRepository hangRepository;

    @GetMapping("/")
    public String listBrands(Model model) {
        model.addAttribute("brands", hangRepository.findAll());
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
