package com.tindung.jhip.repository;

import com.tindung.jhip.domain.KhachHang;
import java.util.List;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the KhachHang entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Long> {

    @Query("select k from KhachHang k where k.ten like :key or k.cmnd like :key ")
    public List<KhachHang> findByNameOrCMND(@Param("key")String key);

}
