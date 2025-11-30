package com.lpth.webLaptop.repository;

import com.lpth.webLaptop.model.Khachhang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhachhangRepository extends JpaRepository<Khachhang, Integer> {

}
