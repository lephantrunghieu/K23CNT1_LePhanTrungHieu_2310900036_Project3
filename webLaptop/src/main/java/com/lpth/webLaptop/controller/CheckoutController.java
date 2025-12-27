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

@Controller                        // Controller xử lý chức năng thanh toán (checkout)
public class CheckoutController {

    @Autowired
    CartService cartService;        // Service quản lý giỏ hàng

    @Autowired
    HoadonRepository hoadonRepo;    // Repository thao tác dữ liệu hóa đơn

    @Autowired
    KhachhangRepository khachhangRepository;   // Repository thao tác dữ liệu khách hàng

    @Autowired
    TaikhoanRepository taikhoanRepository;     // Repository thao tác dữ liệu tài khoản

    @Autowired
    CthdRepository cthdRepo;        // Repository thao tác chi tiết hóa đơn

    @Autowired
    HttpSession session;            // Session (hiện tại chưa sử dụng trực tiếp)

    @GetMapping("/checkout")
    public String checkout(Model model) {

        // Lấy danh sách sản phẩm trong giỏ hàng
        model.addAttribute("cart", cartService.getItems());

        // Lấy tổng tiền giỏ hàng
        model.addAttribute("total", cartService.getTotal());

        // Trả về trang checkout
        return "checkout";
    }

    @PostMapping("/checkout")
    public String processCheckout(Authentication authentication, Model model) {

        // Nếu chưa đăng nhập → chuyển về trang login
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        // Lấy thông tin user đang đăng nhập
        User userDetails = (User) authentication.getPrincipal();

        // Lấy mã khách hàng (makh) từ username đang đăng nhập
        Integer makh = khachhangRepository
                .findByTaikhoan(
                        taikhoanRepository.findByTaikhoan(userDetails.getUsername())
                )
                .getMakh();

        // Nếu không tìm thấy khách hàng → yêu cầu đăng nhập lại
        if (makh == null) {
            return "redirect:/login";
        }

        // Tạo hóa đơn mới
        Hoadon hd = new Hoadon();
        hd.setKhachhang(makh);      // Gán khách hàng
        hd.setNgaydat(new Date());  // Ngày đặt hàng
        hd.setTrangthai(1);         // Trạng thái ban đầu (đơn mới)

        // Lưu hóa đơn vào DB
        Hoadon saved = hoadonRepo.save(hd);

        // Lưu từng sản phẩm trong giỏ vào bảng chi tiết hóa đơn
        cartService.getItems().forEach(item -> {
            Cthd ct = new Cthd();
            ct.setSohd(saved.getSohd());      // Mã hóa đơn
            ct.setMaytinh(item.getId());      // Mã sản phẩm
            ct.setSoluong(item.getSoluong()); // Số lượng
            cthdRepo.save(ct);
        });

        // Xóa giỏ hàng sau khi đặt hàng thành công
        cartService.clear();

        // Điều hướng sang trang xem đơn hàng
        return "redirect:/order/" + saved.getSohd();
    }
}
