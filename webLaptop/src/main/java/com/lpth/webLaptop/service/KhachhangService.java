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

    public void dangKyKhachHang(
            String taikhoan,
            String matkhau,
            String tenkh,
            String diachi,
            String sdt
    ) {
        if (taikhoanRepository.findByTaikhoan(taikhoan) != null) {
            throw new RuntimeException("Tài khoản đã tồn tại!");
        }

        Taikhoan tk = new Taikhoan();
        tk.setTaikhoan(taikhoan);
        tk.setMatkhau(passwordEncoder.encode(matkhau));
        tk.setQuyen(false);

        taikhoanRepository.save(tk);

        Khachhang kh = new Khachhang();
        kh.setTenkh(tenkh);
        kh.setDiachi(diachi);
        kh.setSdt(sdt);
        kh.setTaikhoan(tk);

        khachhangRepository.save(kh);
    }

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
