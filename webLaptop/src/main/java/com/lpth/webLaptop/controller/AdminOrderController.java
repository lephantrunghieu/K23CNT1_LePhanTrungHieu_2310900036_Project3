package com.lpth.webLaptop.controller;

import com.lpth.webLaptop.model.Cthd;
import com.lpth.webLaptop.model.Hoadon;
import com.lpth.webLaptop.model.Khachhang;
import com.lpth.webLaptop.repository.CthdRepository;
import com.lpth.webLaptop.repository.HoadonRepository;
import com.lpth.webLaptop.repository.KhachhangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    @Autowired
    HoadonRepository hoadonRepo;

    @Autowired
    CthdRepository cthdRepo;

    @Autowired
    KhachhangRepository khachhangRepo;

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", hoadonRepo.findAll());
        return "admin/order-list";
    }

    @GetMapping("/{id}")
    public String orderDetail(@PathVariable Integer id, Model model) {

        Hoadon hd = hoadonRepo.findById(id).orElse(null);
        if (hd == null) return "redirect:/admin/orders";

        List<Cthd> items = cthdRepo.findBySohd(id);
        Khachhang kh = khachhangRepo.findById(hd.getKhachhang()).orElse(null);

        model.addAttribute("order", hd);
        model.addAttribute("items", items);
        model.addAttribute("kh", kh);

        return "admin/order-detail";
    }

    @PostMapping("/update")
    public String updateStatus(
            @RequestParam Integer sohd,
            @RequestParam Integer trangthai) {

        Hoadon hd = hoadonRepo.findById(sohd).orElse(null);
        if (hd != null) {
            hd.setTrangthai(trangthai);
            hoadonRepo.save(hd);
        }

        return "redirect:/admin/orders/" + sohd;
    }
}
