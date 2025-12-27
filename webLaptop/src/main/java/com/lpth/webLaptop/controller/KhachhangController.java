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

@Controller // Controller MVC, trả về view HTML
@RequestMapping("/admin/khachhang") // URL quản lý khách hàng phía admin
@PreAuthorize("hasRole('ADMIN')") // Chỉ ADMIN mới được truy cập
public class KhachhangController {

    @Autowired
    private KhachhangRepository khachhangRepository;
    // Repository thao tác bảng KHACHHANG

    @Autowired
    private KhachhangService khachhangService;
    // Service xử lý logic tạo khách hàng + tài khoản (password, encode…)

    @Autowired
    private TaikhoanRepository taikhoanRepository;
    // Repository thao tác bảng TAIKHOAN

    // ===================== DANH SÁCH KHÁCH HÀNG =====================
    @GetMapping("")
    public String listKhachhang(Model model) {

        // Lấy toàn bộ khách hàng trong DB
        List<Khachhang> listKhachhang = khachhangRepository.findAll();

        // Gửi danh sách sang view
        model.addAttribute("listKhachhang", listKhachhang);

        // Trả về trang list.html
        return "admin/khachhang/list";
    }

    // ===================== FORM THÊM MỚI =====================
    @GetMapping("/new")
    public String showNewForm(Model model) {

        // Tạo đối tượng khách hàng rỗng
        Khachhang khachhang = new Khachhang();

        // GẮN sẵn một tài khoản rỗng để tránh NullPointer trong form
        khachhang.setTaikhoan(new Taikhoan());

        model.addAttribute("khachhang", khachhang);
        model.addAttribute("pageTitle", "Thêm Khách hàng mới");

        return "admin/khachhang/form";
    }

    // ===================== LƯU (THÊM / SỬA) =====================
    @PostMapping("/save")
    public String saveKhachhang(
            @ModelAttribute("khachhang") Khachhang khachhang,
            @RequestParam(value = "plainPassword", required = false) String plainPassword,
            RedirectAttributes ra) {

        // ====== TRƯỜNG HỢP THÊM MỚI ======
        if (khachhang.getMakh() == null && plainPassword != null && !plainPassword.isEmpty()) {

            // Gọi service để:
            // - encode password
            // - lưu tài khoản
            // - gán tài khoản cho khách hàng
            khachhangService.saveNewCustomer(khachhang, plainPassword);

            ra.addFlashAttribute("message", "Thêm khách hàng và tài khoản thành công!");

        }
        // ====== TRƯỜNG HỢP CẬP NHẬT ======
        else if (khachhang.getMakh() != null) {

            // Lấy khách hàng cũ trong DB
            Khachhang existingKh = khachhangRepository.findById(khachhang.getMakh())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Khách hàng không tồn tại: " + khachhang.getMakh()));

            // Chỉ cập nhật thông tin cá nhân (KHÔNG động vào tài khoản)
            existingKh.setTenkh(khachhang.getTenkh());
            existingKh.setDiachi(khachhang.getDiachi());
            existingKh.setSdt(khachhang.getSdt());

            khachhangRepository.save(existingKh);

            ra.addFlashAttribute("message", "Cập nhật thông tin khách hàng thành công!");
        }
        // ====== LỖI ======
        else {
            ra.addFlashAttribute("message", "Lỗi: Không thể lưu khách hàng.");
            return "redirect:/admin/khachhang/new";
        }

        return "redirect:/admin/khachhang";
    }

    // ===================== FORM SỬA =====================
    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable("id") Integer id,
            Model model,
            RedirectAttributes ra) {

        try {
            // Lấy khách hàng theo ID
            Khachhang khachhang = khachhangRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Khách hàng không tồn tại: " + id));

            model.addAttribute("khachhang", khachhang);
            model.addAttribute("pageTitle", "Chỉnh sửa Khách hàng (ID: " + id + ")");

            return "admin/khachhang/form";

        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/admin/khachhang";
        }
    }

    // ===================== XÓA KHÁCH HÀNG =====================
    @GetMapping("/delete/{id}")
    public String deleteKhachhang(
            @PathVariable("id") Integer id,
            RedirectAttributes ra) {

        try {
            // Lấy username của tài khoản gắn với khách hàng
            String username = khachhangRepository.findById(id)
                    .get()
                    .getTaikhoan()
                    .getTaikhoan();

            // Xóa khách hàng
            khachhangRepository.deleteById(id);

            // Xóa luôn tài khoản đăng nhập
            taikhoanRepository.deleteById(username);

            ra.addFlashAttribute("message", "Xóa khách hàng ID " + id + " thành công!");

        } catch (Exception e) {
            ra.addFlashAttribute("message", "Lỗi: Không thể xóa khách hàng ID " + id + ".");
        }

        return "redirect:/admin/khachhang";
    }
}
