package com.lpth.webLaptop.repository;

import com.lpth.webLaptop.model.Hang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HangRepository extends JpaRepository<Hang, Integer> {

}
