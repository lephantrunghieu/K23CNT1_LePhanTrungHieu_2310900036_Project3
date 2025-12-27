package com.lpth.webLaptop.config;

// Service tự custom để Spring Security lấy thông tin user từ DB
import com.lpth.webLaptop.security.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Class cấu hình bảo mật Spring Security
 * - Phân quyền
 * - Cấu hình đăng nhập / đăng xuất
 * - Kiểm soát URL truy cập
 */
@Configuration                  // Đánh dấu đây là class cấu hình Spring
@EnableWebSecurity               // Bật Spring Security cho ứng dụng
public class SecurityConfig {

    /**
     * CustomUserDetailsService:
     * - Chịu trách nhiệm lấy thông tin user từ DB
     * - Load username, password, role cho Spring Security
     */
    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * PasswordEncoder:
     * - Dùng để mã hóa và so sánh mật khẩu khi đăng nhập
     * - Bean này được cấu hình ở PasswordConfig
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * SecurityFilterChain:
     * - Cấu hình toàn bộ luồng bảo mật cho HTTP request
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // ❌ Tắt CSRF (thường dùng cho project học tập / test)
                .csrf(csrf -> csrf.disable())

                // 🔐 Cấu hình phân quyền truy cập URL
                .authorizeHttpRequests(authorize -> authorize

                        // ✅ Các URL được phép truy cập KHÔNG cần đăng nhập
                        .requestMatchers(
                                "/", "/category/**", "/product/**", "/maytinh/**",
                                "/css/**", "/js/**", "/images/**", "/uploads/**",
                                "/cart/**", "/checkout/**", "/hoadon/**", "/order/**",
                                "/login", "/login-admin", "/register"
                        ).permitAll()

                        // 🔒 Chỉ ADMIN mới được truy cập /admin/**
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // 🔑 Các request còn lại phải đăng nhập
                        .anyRequest().authenticated()
                )

                // 🧑‍💻 Cấu hình đăng nhập bằng form
                .formLogin(form -> form

                        // Trang đăng nhập custom
                        .loginPage("/login")

                        // URL xử lý submit form login
                        // Spring Security tự bắt request này
                        .loginProcessingUrl("/login")

                        // 🎯 Xử lý sau khi đăng nhập thành công
                        .successHandler((request, response, authentication) -> {

                            // Kiểm tra user có ROLE_ADMIN hay không
                            boolean isAdmin = authentication.getAuthorities().stream()
                                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

                            // Nếu là ADMIN → chuyển vào trang quản trị
                            if (isAdmin) {
                                response.sendRedirect("/admin/dashboard");
                            }
                            // Nếu là USER → chuyển về trang chủ
                            else {
                                response.sendRedirect("/");
                            }
                        })

                        // ❌ Đăng nhập thất bại → quay lại login + error
                        .failureUrl("/login?error")

                        // Cho phép tất cả truy cập trang login
                        .permitAll()
                )

                // 🚪 Cấu hình đăng xuất
                .logout(logout -> logout

                        // URL xử lý logout
                        .logoutUrl("/logout")

                        // Sau khi logout → về trang chủ
                        .logoutSuccessUrl("/")

                        // Cho phép tất cả logout
                        .permitAll()
                );

        // Trả về SecurityFilterChain cho Spring Security sử dụng
        return http.build();
    }
}
