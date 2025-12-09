package com.lpth.webLaptop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "slide")
public class Slide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maslide;

    private String tieude;

    @Column(columnDefinition = "TEXT")
    private String hinhanh;

    private String lienket;

    private Boolean trangthai;

    public Integer getMaslide() {
        return maslide;
    }

    public void setMaslide(Integer maslide) {
        this.maslide = maslide;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getLienket() {
        return lienket;
    }

    public void setLienket(String lienket) {
        this.lienket = lienket;
    }

    public Boolean getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(Boolean trangthai) {
        this.trangthai = trangthai;
    }
}
