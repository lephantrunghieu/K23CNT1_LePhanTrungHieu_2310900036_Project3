package com.lpth.webLaptop.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "hoadon")
public class Hoadon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sohd;

    private Integer khachhang;

    private Date ngaydat;

    private Integer trangthai;

    @ManyToOne
    @JoinColumn(name = "khachhang", referencedColumnName = "makh", insertable = false, updatable = false)
    private Khachhang KhachhangEntity;

    @OneToMany(mappedBy = "sohd", cascade = CascadeType.ALL)
    private List<Cthd> chitiet;

    public Integer getSohd() {
        return sohd;
    }

    public void setSohd(Integer sohd) {
        this.sohd = sohd;
    }

    public Integer getKhachhang() {
        return khachhang;
    }

    public void setKhachhang(Integer khachhang) {
        this.khachhang = khachhang;
    }

    public Date getNgaydat() {
        return ngaydat;
    }

    public void setNgaydat(Date ngaydat) {
        this.ngaydat = ngaydat;
    }

    public Integer getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(Integer trangthai) {
        this.trangthai = trangthai;
    }

    public Khachhang getKhachhangEntity() {
        return KhachhangEntity;
    }

    public void setKhachhangEntity(Khachhang khachhangEntity) {
        KhachhangEntity = khachhangEntity;
    }

    public List<Cthd> getChitiet() {
        return chitiet;
    }

    public void setChitiet(List<Cthd> chitiet) {
        this.chitiet = chitiet;
    }
}

