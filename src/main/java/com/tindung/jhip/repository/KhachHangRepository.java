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

    @Query("select k from KhachHang k inner join k.cuaHang c where c.id =:idcuahang ")
    public List<KhachHang> findAllByCuaHAng(@Param("idcuahang") Long id);

    @Query("select k from KhachHang k where k.ten like :key or k.cmnd like :key ")
    public List<KhachHang> findByNameOrCMND(@Param("key") String key);

    @Query("select k from KhachHang k where k.cmnd like :key ")
    public List<KhachHang> findInSystem(@Param("key") String key);

    @Query("select k from KhachHang k inner join k.cuaHang c where c.id =:id and (k.ten like :key or k.cmnd like :key) ")
    public List<KhachHang> findByNameOrCMNDAdmin(@Param("key") String key, @Param("id") Long id);

}
