package com.lpth.webLaptop.security;

import com.lpth.webLaptop.model.Taikhoan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * CustomUserDetails
 * -----------------
 * Class này dùng để "bọc" entity Taikhoan
 * cho Spring Security hiểu và sử dụng khi đăng nhập
 */
public class CustomUserDetails implements UserDetails {

    // Entity tài khoản trong database
    private final Taikhoan tk;

    // Danh sách quyền (ROLE_USER, ROLE_ADMIN)
    private final Collection<? extends GrantedAuthority> authorities;

    /**
     * Constructor
     * @param tk entity Taikhoan
     * @param authorities danh sách quyền của user
     */
    public CustomUserDetails(Taikhoan tk,
                             Collection<? extends GrantedAuthority> authorities) {
        this.tk = tk;
        this.authorities = authorities;
    }

    /**
     * Trả về entity Taikhoan gốc
     * → dùng khi cần truy cập thông tin DB của user đang login
     */
    public Taikhoan getTaikhoanEntity() {
        return tk;
    }

    /**
     * Trả về role dạng text (ADMIN hoặc USER)
     * Dựa vào cột quyen trong bảng taikhoan
     */
    public String getRole() {
        return tk.getQuyen() ? "ADMIN" : "USER";
    }

    /**
     * Danh sách quyền Spring Security dùng để phân quyền URL
     * Ví dụ: ROLE_ADMIN, ROLE_USER
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Trả về mật khẩu (đã mã hóa) của user
     * Spring Security dùng để so sánh khi login
     */
    @Override
    public String getPassword() {
        return tk.getMatkhau();
    }

    /**
     * Trả về username đăng nhập
     */
    @Override
    public String getUsername() {
        return tk.getTaikhoan();
    }

    /**
     * Tài khoản còn hạn hay không
     * true = không bị hết hạn
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Tài khoản có bị khóa không
     * true = không bị khóa
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Mật khẩu còn hiệu lực hay không
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Tài khoản có được kích hoạt không
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
