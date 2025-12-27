package com.lpth.webLaptop.controller.viewmodel;

import com.lpth.webLaptop.model.Khachhang;

/**
 * ViewModel dùng để gom dữ liệu từ form khách hàng
 * Bao gồm:
 *  - Thông tin khách hàng
 *  - Username đăng nhập
 *  - Password (mật khẩu thuần từ form)
 *
 * Class này KHÔNG phải entity
 * → chỉ dùng để nhận / truyền dữ liệu giữa View ↔ Controller
 */
public class KhachhangForm {

    // Đối tượng khách hàng (tên, địa chỉ, SĐT, ...)
    private Khachhang khachhang;

    // Mật khẩu người dùng nhập từ form (chưa mã hóa)
    private String password;

    // Username đăng nhập
    private String username;

    /**
     * Lấy thông tin khách hàng
     */
    public Khachhang getkhachhang() {
        return khachhang;
    }

    /**
     * Gán thông tin khách hàng
     */
    public void setkhachhang(Khachhang khachhang) {
        this.khachhang = khachhang;
    }

    /**
     * Lấy mật khẩu từ form
     */
    public String getpassword() {
        return password;
    }

    /**
     * Gán mật khẩu từ form
     */
    public void setpassword(String password) {
        this.password = password;
    }

    /**
     * Lấy username từ form
     */
    public String getusername() {
        return username;
    }

    /**
     * Gán username từ form
     */
    public void setusername(String username) {
        this.username = username;
    }
}
