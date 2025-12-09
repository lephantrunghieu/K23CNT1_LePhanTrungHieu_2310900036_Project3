package com.lpth.webLaptop.controller;

import com.lpth.webLaptop.repository.HangRepository;
import com.lpth.webLaptop.repository.MaytinhRepository;
import com.lpth.webLaptop.repository.SlideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private HangRepository hangRepository;
    @Autowired
    private SlideRepository slideRepository;
    @Autowired
    private MaytinhRepository mayTinhRepository;

    @GetMapping("/")
    public String listBrands(Model model) {
        model.addAttribute("brands", hangRepository.findAll());
        model.addAttribute("listSlide", slideRepository.findByTrangthaiTrue());
        model.addAttribute("listMaytinh", mayTinhRepository.findTop8ByOrderByMamtDesc());
        return "index";
    }

//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }
}
