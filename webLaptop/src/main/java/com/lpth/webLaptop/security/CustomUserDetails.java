package com.lpth.webLaptop.security;

import com.lpth.webLaptop.model.Taikhoan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final Taikhoan tk;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Taikhoan tk, Collection<? extends GrantedAuthority> authorities) {
        this.tk = tk;
        this.authorities = authorities;
    }

    public Taikhoan getTaikhoanEntity() {
        return tk;
    }

    public String getRole() {
        return tk.getQuyen() ? "ADMIN" : "USER";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return tk.getMatkhau();
    }

    @Override
    public String getUsername() {
        return tk.getTaikhoan();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
