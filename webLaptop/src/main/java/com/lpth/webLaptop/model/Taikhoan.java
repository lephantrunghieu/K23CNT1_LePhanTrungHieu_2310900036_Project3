package com.lpth.webLaptop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "taikhoan")
public class Taikhoan {
    @Id
    @Column(name = "taikhoan", length = 100)
    private String taikhoan;

    @Column(name = "matkhau", length = 100)
    private String matkhau;

    @Column(name = "quyen", columnDefinition = "BIT(1)")
    private Boolean quyen;

    public String getTaikhoan() { return taikhoan; }
    public void setTaikhoan(String taikhoan) { this.taikhoan = taikhoan; }
    public String getMatkhau() { return matkhau; }
    public void setMatkhau(String matkhau) { this.matkhau = matkhau; }
    public Boolean getQuyen() { return quyen; }
    public void setQuyen(Boolean quyen) { this.quyen = quyen; }
}