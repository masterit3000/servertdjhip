package com.tindung.jhip.repository;

import com.tindung.jhip.domain.KhachHang;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the KhachHang entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Long> {

}
