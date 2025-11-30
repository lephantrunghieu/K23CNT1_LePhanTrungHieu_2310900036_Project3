package com.lpth.webLaptop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "khachhang")
public class Khachhang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "makh")
    private Integer makh;

    @Column(name = "tenkh", length = 100)
    private String tenkh;

    @Column(name = "diachi", columnDefinition = "TEXT")
    private String diachi;

    @Column(name = "sdt", length = 12)
    private String sdt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taikhoan", referencedColumnName = "taikhoan")
    private Taikhoan taikhoan;

    public Khachhang() {}

    public Integer getMakh() { return makh; }
    public void setMakh(Integer makh) { this.makh = makh; }
    public String getTenkh() { return tenkh; }
    public void setTenkh(String tenkh) { this.tenkh = tenkh; }
    public String getDiachi() { return diachi; }
    public void setDiachi(String diachi) { this.diachi = diachi; }
    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }

    public Taikhoan getTaikhoan() { return taikhoan; }
    public void setTaikhoan(Taikhoan taikhoan) { this.taikhoan = taikhoan; }
}
