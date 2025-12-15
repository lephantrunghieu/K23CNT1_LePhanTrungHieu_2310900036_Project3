package com.lpth.webLaptop.repository;

import com.lpth.webLaptop.model.Hoadon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HoadonRepository extends JpaRepository<Hoadon, Integer> {
    long countByTrangthai(Integer trangThai);
    List<Hoadon> findAllByKhachhang(Integer khachhang);
}
