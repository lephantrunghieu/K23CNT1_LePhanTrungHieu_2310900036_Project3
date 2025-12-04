package com.lpth.webLaptop.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "maytinh")
public class Maytinh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mamt")
    private Integer mamt;

    @Column(name = "tenmt", length = 100)
    private String tenmt;

    @Column(name = "mota", columnDefinition = "TEXT")
    private String mota;

    @Column(name = "gia", precision = 18, scale = 0)
    private BigDecimal gia;

    @Column(name = "hinhanh", columnDefinition = "TEXT")
    private String hinhanh;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hang")
    private Hang hang;

    public Maytinh() {}

    public Integer getMamt() {
        return mamt;
    }

    public void setMamt(Integer mamt) {
        this.mamt = mamt;
    }

    public String getTenmt() {
        return tenmt;
    }

    public void setTenmt(String tenmt) {
        this.tenmt = tenmt;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public BigDecimal getGia() {
        return gia;
    }

    public void setGia(BigDecimal gia) {
        this.gia = gia;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public Hang getHang() {
        return hang;
    }

    public void setHang(Hang hang) {
        this.hang = hang;
    }
}