package com.tindung.jhip.repository;

import com.tindung.jhip.domain.VayLai;
import com.tindung.jhip.domain.enumeration.LOAIHOPDONG;
import com.tindung.jhip.domain.enumeration.TRANGTHAIHOPDONG;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the VayLai entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VayLaiRepository extends JpaRepository<VayLai, Long> {

    @Query(value = "select b from VayLai b inner join b.hopdongvl h inner join h.cuaHang c where c.id =:idcuahang order by h.ngaytao desc")
    public List<VayLai> findAllByCuaHang(@Param(value = "idcuahang") Long cuaHangId);

    @Query(value = "select b from VayLai b inner join b.hopdongvl h inner join h.cuaHang c where h.trangthaihopdong =?1 and c.id =?2 order by h.ngaytao desc")
    public List<VayLai> findAllByTrangThaiHopDong(TRANGTHAIHOPDONG trangthai, Long cuaHangId);

    @Query(value = "select b from VayLai b inner join b.hopdongvl h where h.trangthaihopdong =?1 order by h.ngaytao desc")
    public List<VayLai> findAllByTrangThaiHopDongAdmin(TRANGTHAIHOPDONG trangthai);

    @Query(value = "select b from VayLai b inner join b.hopdongvl h inner join h.nhanVien c where c.id =:idnhanvien order by h.ngaytao desc")
    public List<VayLai> findByNhanVien(@Param(value = "idnhanvien") Long id);

    @Query(value = "select b from VayLai b inner join b.hopdongvl h  where h.id =:idhopdong order by h.ngaytao desc")
    public VayLai findByHopDong(@Param(value = "idhopdong") Long hopdongId);

//    @Query(value = "select b from VayLai b inner join b.hopdongvl h inner join h.khachHang c where c.id =:idkhachhang")
//    public List<VayLai> findAllByKhachHang(@Param(value = "idkhachhang") Long khachHangId);
    @Query("select k from VayLai k inner join k.hopdongvl h inner join h.khachHang j inner join h.cuaHang c where c.id =:id and (h.mahopdong like :key or  j.ten like :key or j.cmnd like :key ) order by h.ngaytao desc")
    public List<VayLai> findByNameOrCMND(@Param("key") String key, @Param("id") Long cuaHangid);

    @Query("select k from VayLai k inner join k.hopdongvl h inner join h.khachHang j  where  h.mahopdong like :key or  j.ten like :key or j.cmnd like :key order by h.ngaytao desc")
    public List<VayLai> findByNameOrCMNDAdmin(@Param("key") String key);

    @Query(value = "select b from VayLai b  inner join b.hopdongvl h inner join h.cuaHang c  where h.ngaytao between  ?1 and ?2 and c.id =?3 and b.hopdongvl.hopdonggoc is null order by h.ngaytao desc")
    List<VayLai> baocao(ZonedDateTime start, ZonedDateTime end, Long cuahangid);

    @Query(value = "select b from VayLai b  inner join b.hopdongvl h inner join h.cuaHang c  where h.ngaytao between  ?1 and ?2 and c.id =?3 and b.hopdongvl.hopdonggoc is not null order by h.ngaytao desc")
    List<VayLai> vayThemTraGoc(ZonedDateTime start, ZonedDateTime end, Long cuahangid);

    @Query(value = "select b from VayLai b  inner join b.hopdongvl h inner join h.cuaHang c inner join h.nhanVien n where h.ngaytao between  ?1 and ?2 and c.id =?3 and n.id=?4 and b.hopdongvl.hopdonggoc is null order by h.ngaytao desc")
    List<VayLai> baocaoNV(ZonedDateTime start, ZonedDateTime end, Long cuahangid, Long nhanVienid);

    @Query(value = "select b from VayLai b  inner join b.hopdongvl h inner join h.cuaHang c inner join h.nhanVien n where h.ngaytao between  ?1 and ?2 and c.id =?3 and n.id=?4 and b.hopdongvl.hopdonggoc is not null order by h.ngaytao desc")
    List<VayLai> vayThemTraGocNV(ZonedDateTime start, ZonedDateTime end, Long cuahangid, Long nhanVienid);

    @Query(value = "select sum(b.tienvay) from VayLai b inner join b.hopdongvl h inner join h.cuaHang c where c.id =:idcuahang order by h.ngaytao desc")
    Optional<Double> tienVayDuaKhach(@Param(value = "idcuahang") Long cuaHangId);

    @Query(value = "select b from VayLai b inner join b.hopdongvl h inner join h.cuaHang c where c.id =?1 and h.hopdonggoc.id =?2 order by h.ngaytao desc")
    public VayLai findHopDongGoc(Long cuahangid, Long id);
    //    @Query(value = "select b from VayLai b inner join b.hopdongvl h inner join h.cuaHang c where c.id =:idcuahang")
    //    public List<VayLai> findTaoMoiByCuaHang(@Param(value = "idcuahang") Long cuaHangId);
}
