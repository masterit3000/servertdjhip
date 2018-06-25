package com.tindung.jhip.repository;

import com.tindung.jhip.domain.ThuChi;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ThuChi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThuChiRepository extends JpaRepository<ThuChi, Long> {

}
