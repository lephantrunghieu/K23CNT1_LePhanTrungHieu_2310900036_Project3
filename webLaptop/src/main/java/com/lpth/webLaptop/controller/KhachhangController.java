package com.lpth.webLaptop.controller;

import com.lpth.webLaptop.model.Khachhang;
import com.lpth.webLaptop.model.Taikhoan;
import com.lpth.webLaptop.repository.KhachhangRepository;
import com.lpth.webLaptop.repository.TaikhoanRepository;
import com.lpth.webLaptop.service.KhachhangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/khachhang") // Đường dẫn chung
@PreAuthorize("hasRole('ADMIN')") // Chỉ Admin được truy cập
public class KhachhangController {

    @Autowired
    private KhachhangRepository khachhangRepository;
    @Autowired
    private KhachhangService khachhangService;
    @Autowired
    private TaikhoanRepository taikhoanRepository;

    @GetMapping("")
    public String listKhachhang(Model model) {
        List<Khachhang> listKhachhang = khachhangRepository.findAll();
        model.addAttribute("listKhachhang", listKhachhang);
        return "admin/khachhang/list";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        Khachhang khachhang = new Khachhang();
        khachhang.setTaikhoan(new Taikhoan());
        model.addAttribute("khachhang", khachhang);
        model.addAttribute("pageTitle", "Thêm Khách hàng mới");
        return "admin/khachhang/form";
    }

    @PostMapping("/save")
    public String saveKhachhang(@ModelAttribute("khachhang") Khachhang khachhang,
                                @RequestParam(value = "plainPassword", required = false) String plainPassword,
                                RedirectAttributes ra) {
        if (khachhang.getMakh() == null && plainPassword != null && !plainPassword.isEmpty()) {

            khachhangService.saveNewCustomer(khachhang, plainPassword);
            ra.addFlashAttribute("message", "Thêm khách hàng và tài khoản thành công!");

        } else if (khachhang.getMakh() != null) {
            Khachhang existingKhachhang = khachhangRepository.findById(khachhang.getMakh())
                    .orElseThrow(() -> new IllegalArgumentException("Khách hàng không tồn tại: " + khachhang.getMakh()));

            existingKhachhang.setTenkh(khachhang.getTenkh());
            existingKhachhang.setDiachi(khachhang.getDiachi());
            existingKhachhang.setSdt(khachhang.getSdt());
            khachhangRepository.save(existingKhachhang);
            ra.addFlashAttribute("message", "Cập nhật thông tin khách hàng thành công!");
        } else {
            ra.addFlashAttribute("message", "Lỗi: Không thể lưu khách hàng.");
            return "redirect:/admin/khachhang/new";
        }

        return "redirect:/admin/khachhang";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Khachhang khachhang = khachhangRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Khách hàng không tồn tại: " + id));

            model.addAttribute("khachhang", khachhang);
            model.addAttribute("pageTitle", "Chỉnh sửa Khách hàng (ID: " + id + ")");
            return "admin/khachhang/form";

        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/admin/khachhang";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteKhachhang(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            khachhangRepository.deleteById(id);
            ra.addFlashAttribute("message", "Xóa khách hàng ID " + id + " thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("message", "Lỗi: Không thể xóa khách hàng ID " + id + ".");
        }
        return "redirect:/admin/khachhang";
    }
}
