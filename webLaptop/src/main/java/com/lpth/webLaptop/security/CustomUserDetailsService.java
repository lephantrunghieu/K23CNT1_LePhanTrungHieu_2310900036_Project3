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

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private TaikhoanRepository taikhoanRepository;

    @Override
    public UserDetails loadUserByUsername(String taikhoan) throws UsernameNotFoundException {
        // 1. Tìm tài khoản trong database
        Taikhoan account = taikhoanRepository.findByTaikhoan(taikhoan);

        if (account == null) {
            throw new UsernameNotFoundException("Không tìm thấy tài khoản: " + taikhoan);
        }

        // 2. Xác định quyền (Role)
        String role = (account.getQuyen() != null && account.getQuyen()) ? "ADMIN" : "USER";

        Set<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role));

        // 3. Trả về đối tượng UserDetails của Spring Security
        return new User(
                account.getTaikhoan(),
                account.getMatkhau(),
                authorities
        );
    }
}
