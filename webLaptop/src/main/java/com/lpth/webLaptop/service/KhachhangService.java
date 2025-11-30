package com.lpth.webLaptop.service;

import com.lpth.webLaptop.model.Khachhang;
import com.lpth.webLaptop.model.Taikhoan;
import com.lpth.webLaptop.repository.KhachhangRepository;
import com.lpth.webLaptop.repository.TaikhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KhachhangService {

    @Autowired
    private KhachhangRepository khachhangRepository;

    @Autowired
    private TaikhoanRepository taikhoanRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Khachhang saveNewCustomer(Khachhang khachhang, String plainPassword) {

        Taikhoan newAccount = new Taikhoan();
        String username = khachhang.getTaikhoan().getTaikhoan();

        newAccount.setTaikhoan(username);
        newAccount.setMatkhau(passwordEncoder.encode(plainPassword)); // Mã hóa mật khẩu
        newAccount.setQuyen(false);

        taikhoanRepository.save(newAccount);

        khachhang.setTaikhoan(newAccount);
        return khachhangRepository.save(khachhang);
    }
}
