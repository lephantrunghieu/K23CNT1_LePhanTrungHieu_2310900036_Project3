package com.lpth.webLaptop.security;

import com.lpth.webLaptop.model.Taikhoan;
import com.lpth.webLaptop.repository.TaikhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.Set;

@Service // Đánh dấu đây là Service để Spring Security sử dụng
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private TaikhoanRepository TaikhoanRepository;
    // Repository thao tác với bảng TAIKHOAN trong database

    /**
     * Phương thức bắt buộc của UserDetailsService
     * Spring Security sẽ tự gọi hàm này khi user đăng nhập
     */
    @Override
    public UserDetails loadUserByUsername(String taikhoan)
            throws UsernameNotFoundException {

        // Tìm tài khoản trong DB theo username
        Taikhoan account = TaikhoanRepository.findByTaikhoan(taikhoan);

        // Nếu không tìm thấy tài khoản → báo lỗi
        if (account == null) {
            throw new UsernameNotFoundException(
                    "Không tìm thấy tài khoản: " + taikhoan
            );
        }

        // Xác định role dựa vào cột "quyen"
        // true  → ADMIN
        // false → USER
        String role =
                (account.getQuyen() != null && account.getQuyen())
                        ? "ADMIN"
                        : "USER";

        // Tạo danh sách quyền cho Spring Security
        // BẮT BUỘC phải có tiền tố "ROLE_"
        Set<GrantedAuthority> authorities =
                Collections.singleton(
                        new SimpleGrantedAuthority("ROLE_" + role)
                );

        // Trả về đối tượng User (của Spring Security)
        // Spring dùng object này để:
        // - So sánh mật khẩu
        // - Kiểm tra quyền truy cập
        return new User(
                account.getTaikhoan(), // username
                account.getMatkhau(),  // mật khẩu (đã mã hóa hoặc plain)
                authorities            // quyền
        );
    }
}
//CustomUserDetailsService chịu trách nhiệm truy vấn tài khoản từ database khi đăng nhập,
// còn CustomUserDetails là đối tượng mô tả thông tin người dùng mà Spring Security sử dụng để xác thực và phân quyền.

