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

@Controller // Controller quản lý slide (banner)
@RequestMapping("/admin/slide") // Tất cả URL bắt đầu bằng /admin/slide
public class SlideController {

    @Autowired
    private SlideRepository slideRepository;
    // Repository thao tác với bảng Slide

    // Thư mục lưu ảnh upload (trong static để truy cập trực tiếp)
    private final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    /**
     * Trang danh sách slide
     */
    @GetMapping
    public String index(Model model) {

        // Lấy toàn bộ slide trong DB
        List<Slide> list = slideRepository.findAll();

        // Đưa danh sách slide sang view
        model.addAttribute("listSlide", list);

        // Trả về view admin/slide/index.html
        return "admin/slide/index";
    }

    /**
     * Hiển thị form thêm slide mới
     */
    @GetMapping("/new")
    public String create(Model model) {

        // Tạo đối tượng Slide rỗng để bind form
        model.addAttribute("slide", new Slide());

        // Tiêu đề trang
        model.addAttribute("pageTitle", "Thêm Slide");

        return "admin/slide/form";
    }

    /**
     * Hiển thị form chỉnh sửa slide
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {

        // Lấy slide theo ID (không có thì throw exception)
        Slide slide = slideRepository.findById(id).orElseThrow();

        model.addAttribute("slide", slide);
        model.addAttribute("pageTitle", "Sửa Slide");

        return "admin/slide/form";
    }

    /**
     * Lưu slide (thêm mới hoặc cập nhật)
     */
    @PostMapping("/save")
    public String save(
            @ModelAttribute Slide slide,
            @RequestParam("imageUpload") MultipartFile file
    ) throws IOException {

        // Nếu người dùng có upload ảnh mới
        if (!file.isEmpty()) {

            // Thư mục upload ảnh
            String uploadDir = "src/main/resources/static/uploads";
            Path uploadPath = Paths.get(uploadDir);

            // Nếu thư mục chưa tồn tại thì tạo mới
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Tên file gốc
            String originalFileName = file.getOriginalFilename();

            // Đổi tên file để tránh trùng
            String fileName = System.currentTimeMillis() + "-" + originalFileName;

            // Đường dẫn file lưu
            Path filePath = uploadPath.resolve(fileName);

            // Ghi file xuống ổ đĩa
            Files.write(filePath, file.getBytes());

            // Lưu đường dẫn ảnh vào DB (dùng cho hiển thị)
            slide.setHinhanh("/uploads/" + fileName);
        }

        // Lưu slide vào DB (insert hoặc update)
        slideRepository.save(slide);

        // Quay về danh sách slide
        return "redirect:/admin/slide";
    }

    /**
     * Xóa slide theo ID
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {

        // Xóa slide trong DB
        slideRepository.deleteById(id);

        return "redirect:/admin/slide";
    }
}
