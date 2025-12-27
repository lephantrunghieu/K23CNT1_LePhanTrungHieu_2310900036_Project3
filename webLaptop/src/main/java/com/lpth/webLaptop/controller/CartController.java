package com.lpth.webLaptop.controller;

import com.lpth.webLaptop.model.CartItem;
import com.lpth.webLaptop.model.Maytinh;
import com.lpth.webLaptop.repository.HangRepository;
import com.lpth.webLaptop.repository.MaytinhRepository;
import com.lpth.webLaptop.repository.SlideRepository;
import com.lpth.webLaptop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")                 // Controller xử lý các chức năng giỏ hàng
public class CartController {

    @Autowired
    private CartService cartService;     // Service quản lý giỏ hàng (add, update, remove, clear)

    @Autowired
    private MaytinhRepository maytinhRepository;   // Repository lấy thông tin sản phẩm

    @Autowired
    private HangRepository hangRepository;         // Repository lấy danh sách hãng

    @Autowired
    private SlideRepository slideRepository;       // Repository lấy danh sách slide

    @Autowired
    private MaytinhRepository mayTinhRepository;   // Repository lấy danh sách máy tính (hiển thị gợi ý)

    @GetMapping
    public String viewCart(Model model) {

        // Danh sách sản phẩm trong giỏ hàng
        model.addAttribute("cartItems", cartService.getItems());

        // Tổng tiền của giỏ hàng
        model.addAttribute("total", cartService.getTotal());

        // Danh sách hãng (hiển thị menu / sidebar)
        model.addAttribute("brands", hangRepository.findAll());

        // Danh sách slide đang hoạt động
        model.addAttribute("listSlide", slideRepository.findByTrangthaiIsTrue());

        // Danh sách 8 sản phẩm mới nhất (gợi ý thêm)
        model.addAttribute("listMaytinh", mayTinhRepository.findTop8ByOrderByMamtDesc());

        // Trả về trang giỏ hàng
        return "cart";
    }

    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable Integer id) {

        // Lấy thông tin sản phẩm theo id
        Maytinh mt = maytinhRepository.findById(id).orElse(null);

        // Nếu sản phẩm tồn tại → thêm vào giỏ
        if (mt != null) {
            cartService.add(new CartItem(
                    mt.getMamt(),           // Mã sản phẩm
                    mt.getTenmt(),          // Tên sản phẩm
                    mt.getHinhanh(),        // Hình ảnh
                    mt.getGia().intValue(), // Giá
                    1                       // Số lượng mặc định = 1
            ));
        }

        // Sau khi thêm → quay lại trang giỏ hàng
        return "redirect:/cart";
    }

    @GetMapping("/update/{id}")
    public String updateQty(@PathVariable Integer id,
                            @RequestParam int qty) {

        // Cập nhật số lượng sản phẩm trong giỏ
        cartService.update(id, qty);

        // Quay lại trang giỏ hàng
        return "redirect:/cart";
    }

    @GetMapping("/remove/{id}")
    public String removeItem(@PathVariable Integer id) {

        // Xóa 1 sản phẩm khỏi giỏ hàng
        cartService.remove(id);

        // Quay lại trang giỏ hàng
        return "redirect:/cart";
    }

    @GetMapping("/clear")
    public String clearCart() {

        // Xóa toàn bộ sản phẩm trong giỏ hàng
        cartService.clear();

        // Quay lại trang giỏ hàng
        return "redirect:/cart";
    }
}
