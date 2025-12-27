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
    // Repository thao tác bảng HÓA ĐƠN

    @Autowired
    CthdRepository cthdRepo;
    // Repository thao tác bảng CHI TIẾT HÓA ĐƠN

    @Autowired
    KhachhangRepository khRepo;
    // Repository thao tác bảng KHÁCH HÀNG

    @Autowired
    TaikhoanRepository tkRepo;
    // Repository thao tác bảng TÀI KHOẢN (để lấy user đang đăng nhập)

    /**
     * Lấy mã khách hàng (makh) của user đang đăng nhập
     * @param authentication thông tin đăng nhập từ Spring Security
     * @return makh hoặc null nếu chưa đăng nhập / không tìm thấy
     */
    private Integer getLoggedCustomerId(Authentication authentication) {

        // Chưa đăng nhập hoặc chưa xác thực
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        // Lấy user đang login (username)
        User userDetails = (User) authentication.getPrincipal();

        // Tìm tài khoản theo username
        var tk = tkRepo.findByTaikhoan(userDetails.getUsername());
        if (tk == null) return null;

        // Tìm khách hàng gắn với tài khoản đó
        var kh = khRepo.findByTaikhoan(tk);
        if (kh == null) return null;

        // Trả về mã khách hàng
        return kh.getMakh();
    }

    /**
     * Xem danh sách hóa đơn của khách hàng đang đăng nhập
     */
    @GetMapping("/hoadon")
    public String listHoadon(Authentication authentication, Model model) {

        // Lấy mã khách hàng hiện tại
        Integer makh = getLoggedCustomerId(authentication);

        // Nếu chưa đăng nhập → quay về trang login
        if (makh == null) {
            return "redirect:/login";   // GIỮ NGUYÊN
        }

        // Lấy toàn bộ hóa đơn của khách hàng
        model.addAttribute(
                "orders",
                hoadonRepo.findAllByKhachhang(makh)
        );

        return "hoadon-list";
    }

    /**
     * Xem chi tiết 1 hóa đơn theo ID
     */
    @GetMapping("/order/{id}")
    public String viewInvoice(@PathVariable Integer id, Model model) {

        // Lấy hóa đơn theo ID
        Hoadon hd = hoadonRepo.findById(id).orElse(null);
        if (hd == null) return "redirect:/";

        // Lấy danh sách chi tiết hóa đơn
        List<Cthd> items = cthdRepo.findBySohd(id);

        // Lấy thông tin khách hàng của hóa đơn
        Khachhang kh = khRepo.findById(hd.getKhachhang()).orElse(null);

        // Tính tổng tiền = số lượng * giá từng sản phẩm
        Integer total = items.stream()
                .mapToInt(i ->
                        i.getSoluong().intValue()
                                * i.getMaytinhEntity().getGia().intValue()
                )
                .sum();

        // Đưa dữ liệu sang view
        model.addAttribute("order", hd);
        model.addAttribute("items", items);
        model.addAttribute("total", total);
        model.addAttribute("kh", kh);

        return "invoice";
    }
}
