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
    private TaikhoanRepository TaikhoanRepository;

    @Override
    public UserDetails loadUserByUsername(String taikhoan) throws UsernameNotFoundException {
        Taikhoan account = TaikhoanRepository.findByTaikhoan(taikhoan);

        if (account == null) {
            throw new UsernameNotFoundException("Không tìm thấy tài khoản: " + taikhoan);
        }

        String role = (account.getQuyen() != null && account.getQuyen()) ? "ADMIN" : "USER";

        Set<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role));

        return new User(
                account.getTaikhoan(),
                account.getMatkhau(),
                authorities
        );
    }
}
