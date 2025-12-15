package com.lpth.webLaptop.controller;

import com.lpth.webLaptop.model.Cthd;
import com.lpth.webLaptop.model.Hoadon;
import com.lpth.webLaptop.repository.CthdRepository;
import com.lpth.webLaptop.repository.HoadonRepository;
import com.lpth.webLaptop.repository.KhachhangRepository;
import com.lpth.webLaptop.repository.TaikhoanRepository;
import com.lpth.webLaptop.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class CheckoutController {

    @Autowired
    CartService cartService;

    @Autowired
    HoadonRepository hoadonRepo;

    @Autowired
    KhachhangRepository khachhangRepository;
    @Autowired
    TaikhoanRepository taikhoanRepository;

    @Autowired
    CthdRepository cthdRepo;

    @Autowired
    HttpSession session;

    @GetMapping("/checkout")
    public String checkout(Model model) {
        model.addAttribute("cart", cartService.getItems());
        model.addAttribute("total", cartService.getTotal());
        return "checkout";
    }

    @PostMapping("/checkout")
    public String processCheckout(Authentication authentication, Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
        User userDetails = (User) authentication.getPrincipal();

        Integer makh = khachhangRepository.findByTaikhoan(taikhoanRepository.findByTaikhoan(userDetails.getUsername())).getMakh();

        if (makh == null) {
            return "redirect:/login";
        }

        Hoadon hd = new Hoadon();
        hd.setKhachhang(makh);
        hd.setNgaydat(new Date());
        hd.setTrangthai(1);

        Hoadon saved = hoadonRepo.save(hd);

        cartService.getItems().forEach(item -> {
            Cthd ct = new Cthd();
            ct.setSohd(saved.getSohd());
            ct.setMaytinh(item.getId());
            ct.setSoluong(item.getSoluong());
            cthdRepo.save(ct);
        });

        cartService.clear();

        model.addAttribute("sohd", saved.getSohd());
        return "redirect:/order/" + saved.getSohd();
    }
}
