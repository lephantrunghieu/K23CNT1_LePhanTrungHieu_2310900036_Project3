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

@Controller
public class SanphamController {

    @Autowired
    private MaytinhRepository maytinhRepository;

    @Autowired
    private HangRepository hangRepository;

    @Autowired
    private SlideRepository slideRepository;
    @Autowired
    private MaytinhRepository mayTinhRepository;

    @GetMapping("/category/{duongDan}")
    public String danhMuc(
            @PathVariable String duongDan,
            Model model
    ) {
        Hang hang = hangRepository.findByDuongdan(duongDan).orElse(null);
        if(hang == null) return "redirect:/";

        List<Maytinh> listSP = maytinhRepository.findAllByHang_Duongdan(duongDan);

        model.addAttribute("brands", hangRepository.findAll());
        model.addAttribute("listSlide", slideRepository.findByTrangthaiTrue());
        model.addAttribute("listMaytinh", mayTinhRepository.findTop8ByOrderByMamtDesc());

        model.addAttribute("hang", hang);
        model.addAttribute("listSP", listSP);
        return "danhmuc";
    }

    @GetMapping("/product/{mamt}")
    public String chiTietSP(
            @PathVariable Integer mamt,
            Model model
    ) {
        Maytinh sp = maytinhRepository.findById(mamt).orElse(null);
        if(sp == null) return "redirect:/";

        model.addAttribute("brands", hangRepository.findAll());
        model.addAttribute("listSlide", slideRepository.findByTrangthaiTrue());
        model.addAttribute("listMaytinh", mayTinhRepository.findTop8ByOrderByMamtDesc());
        model.addAttribute("sp", sp);
        return "chitiet";
    }
}
