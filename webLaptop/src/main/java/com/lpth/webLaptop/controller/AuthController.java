package com.lpth.webLaptop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller                     // Controller xử lý các chức năng xác thực (login)
public class AuthController {

    @GetMapping("/login")        // Mapping URL /login (GET)
    public String loginUser() {

        // Trả về trang đăng nhập
        // Tương ứng file: src/main/resources/templates/login.html
        return "login";
    }
}
