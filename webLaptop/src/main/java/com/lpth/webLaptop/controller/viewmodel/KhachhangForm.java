package com.lpth.webLaptop.controller.viewmodel;

import com.lpth.webLaptop.model.Khachhang;

public class KhachhangForm {
    private Khachhang khachhang;
    private String password;
    private String username;

    public Khachhang getkhachhang() {
        return khachhang;
    }

    public void setkhachhang(Khachhang khachhang) {
        this.khachhang = khachhang;
    }

    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }
}
