package com.lpth.webLaptop.controller;

import com.lpth.webLaptop.repository.TaikhoanRepository;
import com.lpth.webLaptop.service.KhachhangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    @Autowired
    private TaikhoanRepository taikhoanRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private KhachhangService khachhangService;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String taikhoan,
            @RequestParam String matkhau,
            @RequestParam String tenkh,
            @RequestParam String diachi,
            @RequestParam String sdt,
            Model model
    ) {

        try {
            khachhangService.dangKyKhachHang(
                    taikhoan, matkhau, tenkh, diachi, sdt
            );

            return "redirect:/login?success";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
