package com.lpth.webLaptop.model;

public class CartItem {
    private Integer id;
    private String ten;
    private String hinhanh;
    private Integer gia;
    private int soluong;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public Integer getGia() {
        return gia;
    }

    public void setGia(Integer gia) {
        this.gia = gia;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public CartItem(Integer id, String ten, String hinhanh, Integer gia, int soluong) {
        this.id = id;
        this.ten = ten;
        this.hinhanh = hinhanh;
        this.gia = gia;
        this.soluong = soluong;
    }

    public int getThanhTien() {
        return gia * soluong;
    }
}
