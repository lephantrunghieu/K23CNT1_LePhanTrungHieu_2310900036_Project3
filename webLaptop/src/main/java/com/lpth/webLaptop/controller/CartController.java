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
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private MaytinhRepository maytinhRepository;

    @Autowired
    private HangRepository hangRepository;
    @Autowired
    private SlideRepository slideRepository;
    @Autowired
    private MaytinhRepository mayTinhRepository;

    @GetMapping
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cartService.getItems());
        model.addAttribute("total", cartService.getTotal());
        model.addAttribute("brands", hangRepository.findAll());
        model.addAttribute("listSlide", slideRepository.findByTrangthaiIsTrue());
        model.addAttribute("listMaytinh", mayTinhRepository.findTop8ByOrderByMamtDesc());
        return "cart";
    }

    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable Integer id) {
        Maytinh mt = maytinhRepository.findById(id).orElse(null);
        if (mt != null) {
            cartService.add(new CartItem(
                    mt.getMamt(),
                    mt.getTenmt(),
                    mt.getHinhanh(),
                    mt.getGia().intValue(),
                    1
            ));
        }
        return "redirect:/cart";
    }

    @GetMapping("/update/{id}")
    public String updateQty(@PathVariable Integer id, @RequestParam int qty) {
        cartService.update(id, qty);
        return "redirect:/cart";
    }

    @GetMapping("/remove/{id}")
    public String removeItem(@PathVariable Integer id) {
        cartService.remove(id);
        return "redirect:/cart";
    }

    @GetMapping("/clear")
    public String clearCart() {
        cartService.clear();
        return "redirect:/cart";
    }
}
