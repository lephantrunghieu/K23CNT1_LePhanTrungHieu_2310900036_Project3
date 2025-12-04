package com.lpth.webLaptop.repository;

import com.lpth.webLaptop.model.Maytinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaytinhRepository extends JpaRepository<Maytinh, Integer> {

}