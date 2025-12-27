package com.lpth.webLaptop.controller;

import com.lpth.webLaptop.model.Hang;
import com.lpth.webLaptop.repository.HangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/hang")                 // Controller quản lý Hãng sản phẩm (ADMIN)
@PreAuthorize("hasRole('ADMIN')")              // Chỉ ADMIN mới được truy cập
public class HangController {

    @Autowired
    private HangRepository hangRepository;      // Repository thao tác dữ liệu Hãng

    @GetMapping("")
    public String listHang(Model model) {

        // Lấy danh sách tất cả các hãng
        List<Hang> listHang = hangRepository.findAll();

        // Gửi danh sách hãng sang view
        model.addAttribute("listHang", listHang);

        // Trả về trang danh sách hãng
        return "admin/hang/list";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {

        // Tạo đối tượng hãng rỗng để binding với form
        Hang hang = new Hang();

        model.addAttribute("hang", hang);
        model.addAttribute("pageTitle", "Thêm Hãng mới");

        // Trả về form thêm hãng
        return "admin/hang/form";
    }

    @PostMapping("/save")
    public String saveHang(@ModelAttribute("hang") Hang hang,
                           RedirectAttributes ra,
                           Model model) {

        // Chuẩn hóa đường dẫn (slug) về chữ thường
        String duongDanMoi = hang.getDuongdan().toLowerCase();

        Optional<Hang> existingHang;

        // Nếu là thêm mới → kiểm tra trùng slug
        if (hang.getMahang() == null) {
            existingHang = hangRepository.findByDuongdan(duongDanMoi);
        }
        // Nếu là cập nhật → kiểm tra trùng slug (trừ chính nó)
        else {
            existingHang = hangRepository
                    .findByDuongdanAndExcludeId(duongDanMoi, hang.getMahang());
        }

        // Nếu slug đã tồn tại → trả lại form và báo lỗi
        if (existingHang.isPresent()) {

            String pageTitle = (hang.getMahang() == null)
                    ? "Thêm Hãng mới"
                    : "Chỉnh sửa Hãng (ID: " + hang.getMahang() + ")";

            model.addAttribute("pageTitle", pageTitle);
            model.addAttribute("hang", hang);
            model.addAttribute(
                    "error",
                    "Lỗi: Đường dẫn (Slug) '" + duongDanMoi +
                            "' đã bị trùng lặp. Vui lòng chọn đường dẫn khác."
            );

            return "admin/hang/form";
        }

        try {
            // Phân biệt thông báo thêm mới / cập nhật
            String message = (hang.getMahang() == null)
                    ? "Thêm hãng thành công!"
                    : "Cập nhật hãng thành công!";

            // Lưu hãng vào DB
            hangRepository.save(hang);

            // Thông báo thành công
            ra.addFlashAttribute("message", message);

        } catch (Exception e) {

            // Thông báo lỗi nếu lưu thất bại
            ra.addFlashAttribute("message", "Lỗi: Không thể lưu hãng.");
        }

        // Sau khi lưu → quay về danh sách hãng
        return "redirect:/admin/hang";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id,
                               Model model,
                               RedirectAttributes ra) {

        try {
            // Tìm hãng theo ID, nếu không có → ném lỗi
            Hang hang = hangRepository.findById(id)
                    .orElseThrow(() ->
                            new IllegalArgumentException("Hãng không tồn tại: " + id));

            model.addAttribute("hang", hang);
            model.addAttribute("pageTitle", "Chỉnh sửa Hãng (ID: " + id + ")");

            // Trả về form chỉnh sửa
            return "admin/hang/form";

        } catch (IllegalArgumentException e) {

            // Nếu không tìm thấy hãng → thông báo lỗi
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/admin/hang";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteHang(@PathVariable("id") Integer id,
                             RedirectAttributes ra) {

        try {
            // Xóa hãng theo ID
            hangRepository.deleteById(id);

            ra.addFlashAttribute(
                    "message",
                    "Xóa hãng ID " + id + " thành công!"
            );

        } catch (Exception e) {

            // Thường lỗi do ràng buộc khóa ngoại (sản phẩm đang dùng hãng)
            ra.addFlashAttribute(
                    "message",
                    "Lỗi: Không thể xóa hãng ID " + id +
                            ". Vui lòng kiểm tra ràng buộc."
            );
        }

        // Quay về danh sách hãng
        return "redirect:/admin/hang";
    }
}
