package com.lpth.webLaptop.repository;

import com.lpth.webLaptop.model.Slide;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SlideRepository extends JpaRepository<Slide, Integer> {
    List<Slide> findByTrangthaiTrue();
}
