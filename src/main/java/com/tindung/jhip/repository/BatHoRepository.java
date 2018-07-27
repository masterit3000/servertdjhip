package com.tindung.jhip.repository;

import com.tindung.jhip.domain.BatHo;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the BatHo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BatHoRepository extends JpaRepository<BatHo, Long> {

    @Query(value = "select b from BatHo b inner join b.hopdongbh h inner join h.cuaHang c where c.id =:idcuahang")
    public List<BatHo> findAllByCuaHang(@Param(value = "idcuahang") Long cuaHangId);

    @Query("select k from BatHo k inner join k.hopdongbh h inner join h.khachHang j where j.ten like :key or j.cmnd like :key ")
    public List<BatHo> findByNameOrCMND(@Param("key") String key);
//    public Optional<BatHo> findOneByCuaHang(Long id, Long cuaHangId);
}
