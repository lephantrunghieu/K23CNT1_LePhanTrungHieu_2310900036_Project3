package com.lpth.webLaptop.controller;

import com.lpth.webLaptop.repository.HoadonRepository;
import com.lpth.webLaptop.repository.KhachhangRepository;
import com.lpth.webLaptop.repository.MaytinhRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    MaytinhRepository maytinhRepo;

    @Autowired
    HoadonRepository hoadonRepo;

    @Autowired
    KhachhangRepository khachhangRepo;

    @GetMapping({"/dashboard","","/"})
    public String adminDashboard(Model model) {
        long totalProducts = maytinhRepo.count();
        long totalCustomers = khachhangRepo.count();
        long newOrders = hoadonRepo.countByTrangthai(1);

        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("totalCustomers", totalCustomers);
        model.addAttribute("newOrders", newOrders);
        return "admin/dashboard";
    }
}
