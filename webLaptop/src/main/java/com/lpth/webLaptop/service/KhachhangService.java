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
    private KhachhangRepository KhachhangRepository;

    @Autowired
    private TaikhoanRepository TaikhoanRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void dangKyKhachHang(
            String taikhoan,
            String matkhau,
            String tenkh,
            String diachi,
            String sdt
    ) {
        if (TaikhoanRepository.findByTaikhoan(taikhoan) != null) {
            throw new RuntimeException("Tài khoản đã tồn tại!");
        }

        Taikhoan tk = new Taikhoan();
        tk.setTaikhoan(taikhoan);
        tk.setMatkhau(passwordEncoder.encode(matkhau));
        tk.setQuyen(false);

        TaikhoanRepository.save(tk);

        Khachhang kh = new Khachhang();
        kh.setTenkh(tenkh);
        kh.setDiachi(diachi);
        kh.setSdt(sdt);
        kh.setTaikhoan(tk);

        KhachhangRepository.save(kh);
    }

    @Transactional
    public Khachhang saveNewCustomer(Khachhang Khachhang, String plainPassword) {

        Taikhoan newAccount = new Taikhoan();
        String username = Khachhang.getTaikhoan().getTaikhoan();

        newAccount.setTaikhoan(username);
        newAccount.setMatkhau(passwordEncoder.encode(plainPassword));
        newAccount.setQuyen(false);

        TaikhoanRepository.save(newAccount);

        Khachhang.setTaikhoan(newAccount);
        return KhachhangRepository.save(Khachhang);
    }
}
