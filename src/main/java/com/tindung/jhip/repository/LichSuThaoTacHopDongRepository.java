package com.tindung.jhip.repository;

import com.tindung.jhip.domain.LichSuThaoTacHopDong;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LichSuThaoTacHopDong entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LichSuThaoTacHopDongRepository extends JpaRepository<LichSuThaoTacHopDong, Long> {

}
