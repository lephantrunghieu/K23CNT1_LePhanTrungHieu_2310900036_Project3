package com.lpth.webLaptop.controller;

import com.lpth.webLaptop.model.Hang;
import com.lpth.webLaptop.model.Maytinh;
import com.lpth.webLaptop.repository.HangRepository;
import com.lpth.webLaptop.repository.MaytinhRepository;
import com.lpth.webLaptop.repository.SlideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller // Controller xử lý các trang sản phẩm (danh mục, chi tiết)
public class SanphamController {

    @Autowired
    private MaytinhRepository maytinhRepository;
    // Repository thao tác với bảng Máy tính (sản phẩm)

    @Autowired
    private HangRepository hangRepository;
    // Repository thao tác với bảng Hãng (brand)

    @Autowired
    private SlideRepository slideRepository;
    // Repository thao tác với bảng Slide (banner)

    @Autowired
    private MaytinhRepository mayTinhRepository;
    // Repository lấy danh sách sản phẩm mới (trùng type nhưng dùng mục đích khác)

    /**
     * Trang danh mục sản phẩm theo hãng
     * Ví dụ: /category/asus
     */
    @GetMapping("/category/{duongDan}")
    public String danhMuc(
            @PathVariable String duongDan, // slug của hãng
            Model model
    ) {

        // Tìm hãng theo đường dẫn (slug)
        Hang hang = hangRepository.findByDuongdan(duongDan).orElse(null);

        // Nếu hãng không tồn tại → quay về trang chủ
        if (hang == null) return "redirect:/";

        // Lấy danh sách sản phẩm thuộc hãng đó
        List<Maytinh> listSP =
                maytinhRepository.findAllByHang_Duongdan(duongDan);

        // Dữ liệu dùng chung cho layout (menu, slide, sản phẩm gợi ý)
        model.addAttribute("brands", hangRepository.findAll());
        model.addAttribute("listSlide", slideRepository.findByTrangthaiIsTrue());
        model.addAttribute("listMaytinh",
                mayTinhRepository.findTop8ByOrderByMamtDesc());

        // Dữ liệu riêng cho trang danh mục
        model.addAttribute("hang", hang);
        model.addAttribute("listSP", listSP);

        // Trả về view danhmuc.html
        return "danhmuc";
    }

    /**
     * Trang chi tiết sản phẩm
     * Ví dụ: /product/15
     */
    @GetMapping("/product/{mamt}")
    public String chiTietSP(
            @PathVariable Integer mamt, // mã sản phẩm
            Model model
    ) {

        // Lấy sản phẩm theo mã
        Maytinh sp = maytinhRepository.findById(mamt).orElse(null);

        // Nếu không tồn tại → quay về trang chủ
        if (sp == null) return "redirect:/";

        // Dữ liệu dùng chung cho layout
        model.addAttribute("brands", hangRepository.findAll());
        model.addAttribute("listSlide", slideRepository.findByTrangthaiIsTrue());
        model.addAttribute("listMaytinh",
                mayTinhRepository.findTop8ByOrderByMamtDesc());

        // Dữ liệu sản phẩm chi tiết
        model.addAttribute("sp", sp);

        // Trả về view chitiet.html
        return "chitiet";
    }
}
