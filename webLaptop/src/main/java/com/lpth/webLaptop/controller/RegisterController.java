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

@Controller // Controller xử lý chức năng đăng ký tài khoản
public class RegisterController {

    @Autowired
    private TaikhoanRepository taikhoanRepository;
    // Repository thao tác bảng tài khoản (kiểm tra trùng username, lưu tài khoản)

    @Autowired
    private PasswordEncoder passwordEncoder;
    // Encoder mã hóa mật khẩu (BCrypt / NoOp tùy cấu hình Security)

    @Autowired
    private KhachhangService khachhangService;
    // Service xử lý logic đăng ký khách hàng + tài khoản

    /**
     * Hiển thị form đăng ký
     */
    @GetMapping("/register")
    public String showRegisterForm() {

        // Trả về trang register.html
        return "register";
    }

    /**
     * Xử lý đăng ký tài khoản + khách hàng
     */
    @PostMapping("/register")
    public String register(
            @RequestParam String taikhoan, // username
            @RequestParam String matkhau,  // mật khẩu (chưa mã hóa)
            @RequestParam String tenkh,    // tên khách hàng
            @RequestParam String diachi,   // địa chỉ
            @RequestParam String sdt,      // số điện thoại
            Model model
    ) {

        try {
            // Gọi service để:
            // 1. Kiểm tra tài khoản có tồn tại không
            // 2. Mã hóa mật khẩu
            // 3. Lưu tài khoản
            // 4. Lưu thông tin khách hàng
            khachhangService.dangKyKhachHang(
                    taikhoan, matkhau, tenkh, diachi, sdt
            );

            // Đăng ký thành công → chuyển về trang login
            return "redirect:/login?success";

        } catch (Exception e) {

            // Nếu có lỗi (trùng tài khoản, lỗi DB, ...)
            // → trả lại trang register kèm thông báo lỗi
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
