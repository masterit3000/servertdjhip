package com.tindung.jhip.repository;

import com.tindung.jhip.domain.NhatKy;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the NhatKy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhatKyRepository extends JpaRepository<NhatKy, Long> {

}
