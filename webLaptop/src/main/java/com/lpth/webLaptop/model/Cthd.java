package com.lpth.webLaptop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cthd")
public class Cthd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer sohd;
    private Integer maytinh;
    private Integer soluong;

    @ManyToOne
    @JoinColumn(name = "maytinh", referencedColumnName = "mamt", insertable = false, updatable = false)
    private Maytinh maytinhEntity;

    @ManyToOne
    @JoinColumn(name = "sohd", insertable = false, updatable = false)
    private Hoadon Hoadon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSohd() {
        return sohd;
    }

    public void setSohd(Integer sohd) {
        this.sohd = sohd;
    }

    public Integer getMaytinh() {
        return maytinh;
    }

    public void setMaytinh(Integer maytinh) {
        this.maytinh = maytinh;
    }

    public Integer getSoluong() {
        return soluong;
    }

    public void setSoluong(Integer soluong) {
        this.soluong = soluong;
    }

    public Maytinh getMaytinhEntity() {
        return maytinhEntity;
    }

    public void setMaytinhEntity(Maytinh maytinhEntity) {
        this.maytinhEntity = maytinhEntity;
    }

    public com.lpth.webLaptop.model.Hoadon getHoadon() {
        return Hoadon;
    }

    public void setHoadon(com.lpth.webLaptop.model.Hoadon hoadon) {
        Hoadon = hoadon;
    }
}

