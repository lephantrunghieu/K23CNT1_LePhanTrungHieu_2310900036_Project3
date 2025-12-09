package com.lpth.webLaptop.controller;

import com.lpth.webLaptop.model.Slide;
import com.lpth.webLaptop.repository.SlideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/admin/slide")
public class SlideController {

    @Autowired
    private SlideRepository slideRepository;

    private final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @GetMapping
    public String index(Model model) {
        List<Slide> list = slideRepository.findAll();
        model.addAttribute("listSlide", list);
        return "admin/slide/index";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("slide", new Slide());
        model.addAttribute("pageTitle", "Thêm Slide");
        return "admin/slide/form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Slide slide = slideRepository.findById(id).orElseThrow();
        model.addAttribute("slide", slide);
        model.addAttribute("pageTitle", "Sửa Slide");
        return "admin/slide/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Slide slide,
                       @RequestParam("imageUpload") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String uploadDir = "src/main/resources/static/uploads";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFileName = file.getOriginalFilename();
            String fileName = System.currentTimeMillis() + "-" + originalFileName;

            Path filePath = uploadPath.resolve(fileName);
            Files.write(filePath, file.getBytes());

            slide.setHinhanh("/uploads/" + fileName);
        }

        slideRepository.save(slide);
        return "redirect:/admin/slide";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        slideRepository.deleteById(id);
        return "redirect:/admin/slide";
    }
}
