package com.lpth.webLaptop.repository;

import com.lpth.webLaptop.model.Cthd;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CthdRepository extends JpaRepository<Cthd, Integer> {
    List<Cthd> findBySohd(Integer sohd);
}
