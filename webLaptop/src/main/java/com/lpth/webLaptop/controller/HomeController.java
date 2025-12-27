package com.lpth.webLaptop.controller;

import com.lpth.webLaptop.repository.HangRepository;
import com.lpth.webLaptop.repository.MaytinhRepository;
import com.lpth.webLaptop.repository.SlideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Đánh dấu đây là Controller xử lý request web (MVC)
public class HomeController {

    @Autowired
    private HangRepository hangRepository;
    // Repository thao tác với bảng Hãng (brand)

    @Autowired
    private SlideRepository slideRepository;
    // Repository thao tác với bảng Slide (banner trang chủ)

    @Autowired
    private MaytinhRepository mayTinhRepository;
    // Repository thao tác với bảng Máy tính (sản phẩm)

    @GetMapping("/") // Mapping URL trang chủ
    public String listBrands(Model model) {

        // Lấy toàn bộ hãng để hiển thị menu / danh mục
        model.addAttribute("brands", hangRepository.findAll());

        // Lấy danh sách slide đang được bật (trangthai = true)
        model.addAttribute("listSlide", slideRepository.findByTrangthaiIsTrue());

        // Lấy 8 sản phẩm mới nhất (mã máy tính giảm dần)
        model.addAttribute("listMaytinh", mayTinhRepository.findTop8ByOrderByMamtDesc());

        // Trả về view index.html (trang chủ)
        return "index";
    }

    /*
     * Mapping /login đã được tách sang AuthController
     * để tránh trùng mapping và để Spring Security xử lý đăng nhập
     */
}
