package com.tindung.jhip.repository;

import com.tindung.jhip.domain.LichSuDongTien;
import java.util.List;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the LichSuDongTien entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LichSuDongTienRepository extends JpaRepository<LichSuDongTien, Long> {

    
    @Query(value = "select l from LichSuDongTien l inner join l.hopDong h where h.id =:idhopdong")
    List<LichSuDongTien> findByHopDong(@Param(value = "idhopdong")long idhopdong);
}
