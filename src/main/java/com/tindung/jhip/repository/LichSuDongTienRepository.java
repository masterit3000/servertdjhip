package com.tindung.jhip.repository;

import com.tindung.jhip.domain.LichSuDongTien;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LichSuDongTien entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LichSuDongTienRepository extends JpaRepository<LichSuDongTien, Long> {

}
