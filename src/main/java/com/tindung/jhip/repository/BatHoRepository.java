package com.tindung.jhip.repository;

import com.tindung.jhip.domain.BatHo;
import com.tindung.jhip.domain.enumeration.TRANGTHAIHOPDONG;
import java.time.ZonedDateTime;
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
    //Tùng viết

    @Query(value = "select b from BatHo b inner join b.hopdongbh h inner join h.cuaHang c where c.id =:idcuahang")
    public List<BatHo> findByCuaHangId(@Param(value = "idcuahang") Long cuaHangId);
    //Tùng end

    @Query(value = "select b from BatHo b inner join b.hopdongbh h  where h.id =:idhopdong")
    public List<BatHo> findByHopDong(@Param(value = "idhopdong") Long hopdongId);

    @Query(value = "select b from BatHo b  inner join b.hopdongbh h where h.ngaytao between  ?1 and ?2 ")
    public List<BatHo> baocao(ZonedDateTime start, ZonedDateTime end);
    @Query(value = "select b from BatHo b  inner join b.hopdongbh h where h.ngaytao between  ?1 and ?2 and h.trangthaihopdong = ?3 ")
    public List<BatHo> findByTrangThai(ZonedDateTime start, ZonedDateTime end,TRANGTHAIHOPDONG trangthai);

    @Query(value = "select b from BatHo b inner join b.hopdongbh h inner join h.khachHang c where c.id =:idkhachhang")
    public List<BatHo> findAllByKhachHang(@Param(value = "idkhachhang") Long khachHangId);

}
