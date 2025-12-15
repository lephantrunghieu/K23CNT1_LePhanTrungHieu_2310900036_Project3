package com.lpth.webLaptop.controller;

import com.lpth.webLaptop.model.Hang;
import com.lpth.webLaptop.model.Maytinh;
import com.lpth.webLaptop.repository.HangRepository;
import com.lpth.webLaptop.repository.MaytinhRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin/maytinh")
@PreAuthorize("hasRole('ADMIN')")
public class MaytinhController {

    @Autowired
    private MaytinhRepository maytinhRepository;

    @Autowired
    private HangRepository hangRepository;

    private static final String UPLOAD_DIR = "src/main/resources/static/images/maytinh/";

    private String saveImageFile(MultipartFile imageFile) throws IOException {
        if (imageFile.isEmpty()) {
            return null;
        }

        String originalFilename = imageFile.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String fileName = UUID.randomUUID().toString() + extension;

        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(imageFile.getInputStream(), filePath);

        return "/images/maytinh/" + fileName;
    }

    @GetMapping("")
    public String listMaytinh(Model model) {
        List<Maytinh> listMaytinh = maytinhRepository.findAll();
        model.addAttribute("listMaytinh", listMaytinh);
        return "admin/maytinh/list";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        Maytinh maytinh = new Maytinh();
        List<Hang> listHang = hangRepository.findAll();

        model.addAttribute("maytinh", maytinh);
        model.addAttribute("listHang", listHang);
        model.addAttribute("pageTitle", "Thêm Sản phẩm mới");
        return "admin/maytinh/form";
    }

    @PostMapping("/save")
    public String saveMaytinh(
            @ModelAttribute("maytinh") Maytinh maytinh,
            @RequestParam("imageUpload") MultipartFile imageUpload,
            RedirectAttributes ra) {

        String oldImagePath = maytinh.getHinhanh();

        try {
            if (!imageUpload.isEmpty()) {
                String imagePath = saveImageFile(imageUpload);
                maytinh.setHinhanh(imagePath);
            } else if (maytinh.getMamt() != null) {
                maytinh.setHinhanh(oldImagePath);
            }

            maytinhRepository.save(maytinh);

            String message = (maytinh.getMamt() == null)
                    ? "Thêm sản phẩm thành công!"
                    : "Cập nhật sản phẩm thành công!";

            ra.addFlashAttribute("message", message);
        } catch (IOException e) {
            ra.addFlashAttribute("message", "Lỗi Upload File: " + e.getMessage());
            return "redirect:/admin/maytinh/new";
        } catch (Exception e) {
            ra.addFlashAttribute("message", "Lỗi: Không thể lưu sản phẩm.");
        }

        return "redirect:/admin/maytinh";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id,
                                    Model model,
                                    RedirectAttributes ra) {
        try {
            Maytinh maytinh = maytinhRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại: " + id));

            List<Hang> listHang = hangRepository.findAll();

            model.addAttribute("maytinh", maytinh);
            model.addAttribute("listHang", listHang);
            model.addAttribute("pageTitle", "Chỉnh sửa Sản phẩm (ID: " + id + ")");

            return "admin/maytinh/form";

        } catch (Exception e) {
            ra.addFlashAttribute("message", "Lỗi: " + e.getMessage());
            return "redirect:/admin/maytinh";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteMaytinh(@PathVariable("id") Integer id,
                                     RedirectAttributes ra) {
        try {
            maytinhRepository.deleteById(id);
            ra.addFlashAttribute("message",
                    "Xóa sản phẩm ID " + id + " thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("message",
                    "Lỗi: Không thể xóa sản phẩm ID " + id + ". Vui lòng kiểm tra ràng buộc.");
        }
        return "redirect:/admin/maytinh";
    }
}
