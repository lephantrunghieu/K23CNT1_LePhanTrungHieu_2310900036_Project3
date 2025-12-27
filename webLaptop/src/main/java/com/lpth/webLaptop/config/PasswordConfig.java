package com.lpth.webLaptop.config;

// Import annotation @Bean để khai báo Bean cho Spring Container
import org.springframework.context.annotation.Bean;

// Import annotation @Configuration để đánh dấu class cấu hình
import org.springframework.context.annotation.Configuration;

// NoOpPasswordEncoder: encoder KHÔNG mã hóa mật khẩu (plain text)
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

// Interface chung cho các loại mã hóa mật khẩu
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Class cấu hình PasswordEncoder cho Spring Security
 * Mục đích: cung cấp cách mã hóa (hoặc không mã hóa) mật khẩu
 */
@Configuration // Đánh dấu đây là class cấu hình của Spring
public class PasswordConfig {

    /**
     * Khai báo Bean PasswordEncoder
     * Spring sẽ quản lý Bean này và tự động inject khi cần
     *
     * @return PasswordEncoder (không mã hóa mật khẩu)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {

        // Trả về NoOpPasswordEncoder:
        // - Không mã hóa mật khẩu
        // - Mật khẩu lưu DB giống hệt mật khẩu nhập
        // - Chỉ dùng cho học tập / test
        // - KHÔNG dùng cho môi trường production
        return NoOpPasswordEncoder.getInstance();
    }
}
