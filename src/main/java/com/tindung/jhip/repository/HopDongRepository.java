package com.tindung.jhip.repository;

import com.tindung.jhip.domain.HopDong;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the HopDong entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HopDongRepository extends JpaRepository<HopDong, Long> {

}
