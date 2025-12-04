package com.lpth.webLaptop.controller;

import com.lpth.webLaptop.model.Hang;
import com.lpth.webLaptop.repository.HangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/hang")
@PreAuthorize("hasRole('ADMIN')")
public class HangController {

    @Autowired
    private HangRepository hangRepository;

    @GetMapping("")
    public String listHang(Model model) {
        List<Hang> listHang = hangRepository.findAll();
        model.addAttribute("listHang", listHang);
        return "admin/hang/list";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        Hang hang = new Hang();
        model.addAttribute("hang", hang);
        model.addAttribute("pageTitle", "Thêm Hãng mới");
        return "admin/hang/form";
    }

    @PostMapping("/save")
    public String saveHang(@ModelAttribute("hang") Hang hang,
                           RedirectAttributes ra,
                           Model model) {
        String duongDanMoi = hang.getDuongdan().toLowerCase();
        Optional<Hang> existingHang;
        if (hang.getMahang() == null) {
            existingHang = hangRepository.findByDuongdan(duongDanMoi);
        } else {
            existingHang = hangRepository.findByDuongdanAndExcludeId(duongDanMoi, hang.getMahang());
        }
        if (existingHang.isPresent()) {
            String pageTitle = (hang.getMahang() == null) ? "Thêm Hãng mới" : "Chỉnh sửa Hãng (ID: " + hang.getMahang() + ")";
            model.addAttribute("pageTitle", pageTitle);
            model.addAttribute("hang", hang);
            model.addAttribute("error", "Lỗi: Đường dẫn (Slug) '" + duongDanMoi + "' đã bị trùng lặp. Vui lòng chọn đường dẫn khác.");
            return "admin/hang/form";
        }

        try {
            String message = (hang.getMahang() == null) ? "Thêm hãng thành công!" : "Cập nhật hãng thành công!";
            hangRepository.save(hang);
            ra.addFlashAttribute("message", message);
        } catch (Exception e) {
            ra.addFlashAttribute("message", "Lỗi: Không thể lưu hãng.");
        }
        return "redirect:/admin/hang";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Hang hang = hangRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Hãng không tồn tại: " + id));

            model.addAttribute("hang", hang);
            model.addAttribute("pageTitle", "Chỉnh sửa Hãng (ID: " + id + ")");
            return "admin/hang/form";

        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/admin/hang";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteHang(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            hangRepository.deleteById(id);
            ra.addFlashAttribute("message", "Xóa hãng ID " + id + " thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("message", "Lỗi: Không thể xóa hãng ID " + id + ". Vui lòng kiểm tra ràng buộc.");
        }
        return "redirect:/admin/hang";
    }
}