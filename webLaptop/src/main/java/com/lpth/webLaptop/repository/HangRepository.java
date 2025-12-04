package com.lpth.webLaptop.repository;

import com.lpth.webLaptop.model.Hang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HangRepository extends JpaRepository<Hang, Integer> {
    Optional<Hang> findByDuongdan(String duongdan);
    @Query("SELECT h FROM Hang h WHERE h.duongdan = ?1 AND h.mahang <> ?2")
    Optional<Hang> findByDuongdanAndExcludeId(String duongdan, Integer id);
}
