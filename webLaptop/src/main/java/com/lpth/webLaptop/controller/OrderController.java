package com.lpth.webLaptop.controller;

import com.lpth.webLaptop.model.Cthd;
import com.lpth.webLaptop.model.Hoadon;
import com.lpth.webLaptop.model.Khachhang;
import com.lpth.webLaptop.repository.CthdRepository;
import com.lpth.webLaptop.repository.HoadonRepository;
import com.lpth.webLaptop.repository.KhachhangRepository;
import com.lpth.webLaptop.repository.TaikhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    HoadonRepository hoadonRepo;

    @Autowired
    CthdRepository cthdRepo;

    @Autowired
    KhachhangRepository khRepo;

    @Autowired
    TaikhoanRepository tkRepo;

    private Integer getLoggedCustomerId(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        User userDetails = (User) authentication.getPrincipal();

        var tk = tkRepo.findByTaikhoan(userDetails.getUsername());
        if (tk == null) return null;

        var kh = khRepo.findByTaikhoan(tk);
        if (kh == null) return null;

        return kh.getMakh();
    }


    @GetMapping("/hoadon")
    public String listHoadon(Authentication authentication, Model model) {

        Integer makh = getLoggedCustomerId(authentication);
        if (makh == null) {
            return "redirect:/login";   // GIỮ NGUYÊN
        }

        model.addAttribute("orders", hoadonRepo.findAllByKhachhang(makh));
        return "hoadon-list";
    }


    @GetMapping("/order/{id}")
    public String viewInvoice(@PathVariable Integer id, Model model) {

        Hoadon hd = hoadonRepo.findById(id).orElse(null);
        if (hd == null) return "redirect:/";

        List<Cthd> items = cthdRepo.findBySohd(id);
        Khachhang kh = khRepo.findById(hd.getKhachhang()).orElse(null);

        Integer total = items.stream()
                .mapToInt(i -> i.getSoluong().intValue() * i.getMaytinhEntity().getGia().intValue())
                .sum();

        model.addAttribute("order", hd);
        model.addAttribute("items", items);
        model.addAttribute("total", total);
        model.addAttribute("kh", kh);

        return "invoice";
    }
}
