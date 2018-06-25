package com.tindung.jhip.repository;

import com.tindung.jhip.domain.AnhKhachHang;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AnhKhachHang entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnhKhachHangRepository extends JpaRepository<AnhKhachHang, Long> {

}
