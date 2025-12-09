package com.lpth.webLaptop.repository;

import com.lpth.webLaptop.model.Maytinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaytinhRepository extends JpaRepository<Maytinh, Integer> {
    List<Maytinh> findTop8ByOrderByMamtDesc();
    List<Maytinh> findAllByHang_Duongdan(String duongDan);
}