package com.tindung.jhip.repository;

import com.tindung.jhip.domain.BatHo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BatHo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BatHoRepository extends JpaRepository<BatHo, Long> {

}
