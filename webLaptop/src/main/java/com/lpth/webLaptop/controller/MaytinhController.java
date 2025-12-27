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
@RequestMapping("/admin/maytinh")          // URL quản lý sản phẩm máy tính (ADMIN)
@PreAuthorize("hasRole('ADMIN')")          // Chỉ ADMIN mới được truy cập
public class MaytinhController {

    @Autowired
    private MaytinhRepository maytinhRepository;
    // Repository thao tác bảng MAYTINH (sản phẩm)

    @Autowired
    private HangRepository hangRepository;
    // Repository thao tác bảng HANG (hãng sản phẩm)

    // Thư mục vật lý lưu ảnh sản phẩm
    private static final String UPLOAD_DIR =
            "src/main/resources/static/images/maytinh/";

    /**
     * Lưu file ảnh upload vào thư mục static
     * @return đường dẫn ảnh dùng để hiển thị trên web
     */
    private String saveImageFile(MultipartFile imageFile) throws IOException {

        // Nếu không có file → không xử lý
        if (imageFile.isEmpty()) {
            return null;
        }

        // Lấy tên file gốc
        String originalFilename = imageFile.getOriginalFilename();

        // Lấy phần đuôi file (.jpg, .png, ...)
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(
                    originalFilename.lastIndexOf("."));
        }

        // Tạo tên file mới ngẫu nhiên để tránh trùng
        String fileName = UUID.randomUUID().toString() + extension;

        // Tạo thư mục nếu chưa tồn tại
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Lưu file vào thư mục
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(imageFile.getInputStream(), filePath);

        // Trả về đường dẫn dùng trong HTML
        return "/images/maytinh/" + fileName;
    }

    // ===================== DANH SÁCH SẢN PHẨM =====================
    @GetMapping("")
    public String listMaytinh(Model model) {

        // Lấy toàn bộ sản phẩm
        List<Maytinh> listMaytinh = maytinhRepository.findAll();

        // Gửi sang view
        model.addAttribute("listMaytinh", listMaytinh);

        return "admin/maytinh/list";
    }

    // ===================== FORM THÊM MỚI =====================
    @GetMapping("/new")
    public String showNewForm(Model model) {

        // Tạo sản phẩm rỗng để binding form
        Maytinh maytinh = new Maytinh();

        // Lấy danh sách hãng để chọn
        List<Hang> listHang = hangRepository.findAll();

        model.addAttribute("maytinh", maytinh);
        model.addAttribute("listHang", listHang);
        model.addAttribute("pageTitle", "Thêm Sản phẩm mới");

        return "admin/maytinh/form";
    }

    // ===================== LƯU (THÊM / SỬA) =====================
    @PostMapping("/save")
    public String saveMaytinh(
            @ModelAttribute("maytinh") Maytinh maytinh,
            @RequestParam("imageUpload") MultipartFile imageUpload,
            RedirectAttributes ra) {

        // Lưu lại ảnh cũ (trường hợp update)
        String oldImagePath = maytinh.getHinhanh();

        try {
            // Nếu có upload ảnh mới → lưu ảnh mới
            if (!imageUpload.isEmpty()) {
                String imagePath = saveImageFile(imageUpload);
                maytinh.setHinhanh(imagePath);
            }
            // Nếu không upload ảnh và đang sửa → giữ ảnh cũ
            else if (maytinh.getMamt() != null) {
                maytinh.setHinhanh(oldImagePath);
            }

            // Lưu sản phẩm vào DB
            maytinhRepository.save(maytinh);

            // Thông báo theo hành động
            String message = (maytinh.getMamt() == null)
                    ? "Thêm sản phẩm thành công!"
                    : "Cập nhật sản phẩm thành công!";

            ra.addFlashAttribute("message", message);

        } catch (IOException e) {

            // Lỗi khi upload file
            ra.addFlashAttribute(
                    "message",
                    "Lỗi Upload File: " + e.getMessage());
            return "redirect:/admin/maytinh/new";

        } catch (Exception e) {

            // Lỗi chung (DB, ràng buộc, ...)
            ra.addFlashAttribute(
                    "message",
                    "Lỗi: Không thể lưu sản phẩm.");
        }

        return "redirect:/admin/maytinh";
    }

    // ===================== FORM CHỈNH SỬA =====================
    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable("id") Integer id,
            Model model,
            RedirectAttributes ra) {

        try {
            // Lấy sản phẩm theo ID
            Maytinh maytinh = maytinhRepository.findById(id)
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Sản phẩm không tồn tại: " + id));

            // Lấy danh sách hãng
            List<Hang> listHang = hangRepository.findAll();

            model.addAttribute("maytinh", maytinh);
            model.addAttribute("listHang", listHang);
            model.addAttribute(
                    "pageTitle",
                    "Chỉnh sửa Sản phẩm (ID: " + id + ")");

            return "admin/maytinh/form";

        } catch (Exception e) {
            ra.addFlashAttribute("message", "Lỗi: " + e.getMessage());
            return "redirect:/admin/maytinh";
        }
    }

    // ===================== XÓA SẢN PHẨM =====================
    @GetMapping("/delete/{id}")
    public String deleteMaytinh(
            @PathVariable("id") Integer id,
            RedirectAttributes ra) {

        try {
            // Xóa sản phẩm theo ID
            maytinhRepository.deleteById(id);

            ra.addFlashAttribute(
                    "message",
                    "Xóa sản phẩm ID " + id + " thành công!");

        } catch (Exception e) {

            // Thường lỗi do ràng buộc khóa ngoại (đơn hàng đã dùng sản phẩm)
            ra.addFlashAttribute(
                    "message",
                    "Lỗi: Không thể xóa sản phẩm ID " + id +
                            ". Vui lòng kiểm tra ràng buộc.");
        }

        return "redirect:/admin/maytinh";
    }
}
