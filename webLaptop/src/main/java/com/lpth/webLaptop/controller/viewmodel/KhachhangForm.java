package com.lpth.webLaptop.controller.viewmodel;

import com.lpth.webLaptop.model.Khachhang;

public class KhachhangForm {
    private Khachhang khachhang;
    private String password;
    private String username;

    public Khachhang getKhachhang() {
        return khachhang;
    }

    public void setKhachhang(Khachhang khachhang) {
        this.khachhang = khachhang;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
