package com.tindung.jhip.repository;

import com.tindung.jhip.domain.CuaHang;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CuaHang entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CuaHangRepository extends JpaRepository<CuaHang, Long> {

}
