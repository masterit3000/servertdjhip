package com.tindung.jhip.repository;

import com.tindung.jhip.domain.CuaHang;
import java.util.List;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the CuaHang entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CuaHangRepository extends JpaRepository<CuaHang, Long> {

    @Query("select k from CuaHang k  where k.ten like :key or k.dienthoai like :key")
    public List<CuaHang> findByName(@Param("key") String key);
}
