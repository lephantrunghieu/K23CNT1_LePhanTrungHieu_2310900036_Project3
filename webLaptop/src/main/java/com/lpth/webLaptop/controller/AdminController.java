package com.lpth.webLaptop.controller;

import com.lpth.webLaptop.repository.HoadonRepository;
import com.lpth.webLaptop.repository.KhachhangRepository;
import com.lpth.webLaptop.repository.MaytinhRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")                 // Tất cả URL trong controller đều bắt đầu bằng /admin
@PreAuthorize("hasRole('ADMIN')")         // Chỉ người dùng có ROLE_ADMIN mới được truy cập controller này
public class AdminController {

    @Autowired
    MaytinhRepository maytinhRepo;         // Repository thao tác dữ liệu sản phẩm

    @Autowired
    HoadonRepository hoadonRepo;           // Repository thao tác dữ liệu hóa đơn

    @Autowired
    KhachhangRepository khachhangRepo;     // Repository thao tác dữ liệu khách hàng

    @GetMapping({"/dashboard", "", "/"})   // Mapping cho các URL: /admin, /admin/, /admin/dashboard
    public String adminDashboard(Model model) {

        long totalProducts = maytinhRepo.count();          // Đếm tổng số sản phẩm
        long totalCustomers = khachhangRepo.count();       // Đếm tổng số khách hàng
        long newOrders = hoadonRepo.countByTrangthai(1);   // Đếm số đơn hàng mới (trạng thái = 1)

        model.addAttribute("totalProducts", totalProducts);    // Gửi tổng sản phẩm sang view
        model.addAttribute("totalCustomers", totalCustomers);  // Gửi tổng khách hàng sang view
        model.addAttribute("newOrders", newOrders);            // Gửi số đơn hàng mới sang view

        return "admin/dashboard";   // Trả về view dashboard cho admin
    }
}
