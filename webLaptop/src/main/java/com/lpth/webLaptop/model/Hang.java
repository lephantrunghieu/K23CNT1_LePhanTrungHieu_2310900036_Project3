package com.lpth.webLaptop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "hang")
public class Hang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mahang")
    private Integer mahang;

    @Column(name = "tenhang", length = 100)
    private String tenhang;

    @Column(name = "duongdan", columnDefinition = "TEXT")
    private String duongdan;

    public Integer getMahang() {
        return mahang;
    }

    public void setMahang(Integer mahang) {
        this.mahang = mahang;
    }

    public String getTenhang() {
        return tenhang;
    }

    public void setTenhang(String tenhang) {
        this.tenhang = tenhang;
    }

    public String getDuongdan() {
        return duongdan;
    }

    public void setDuongdan(String duongdan) {
        this.duongdan = duongdan;
    }
}
