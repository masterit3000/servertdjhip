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

    @Query(value = "select b from BatHo b inner join b.hopdongbh h inner join h.cuaHang c where c.id =:idcuahang order by h.ngaytao desc")
    public List<BatHo> findAllByCuaHang(@Param(value = "idcuahang") Long cuaHangId);

    @Query(value = "select b from BatHo b inner join b.hopdongbh h inner join h.nhanVien n where n.id =:idnhanvien order by h.ngaytao desc")
    public List<BatHo> findAllByNhanVien(@Param(value = "idnhanvien") Long id);

    @Query("select k from BatHo k inner join k.hopdongbh h inner join h.khachHang j inner join h.cuaHang c where c.id =:id and h.mahopdong like :key or  j.ten like :key or j.cmnd like :key order by h.ngaytao desc")
    public List<BatHo> findByNameOrCMND(@Param("key") String key, @Param("id") Long cuaHangid);

    @Query("select k from BatHo k inner join k.hopdongbh h inner join h.khachHang j  where  h.mahopdong like :key or  j.ten like :key or j.cmnd like :key order by h.ngaytao desc")
    public List<BatHo> findByNameOrCMNDAdmin(@Param("key") String key);
//    public Optional<BatHo> findOneByCuaHang(Long id, Long cuaHangId);
    //Tùng viết

    @Query(value = "select b from BatHo b inner join b.hopdongbh h inner join h.cuaHang c where c.id =:idcuahang order by h.ngaytao desc")
    public List<BatHo> findByCuaHangId(@Param(value = "idcuahang") Long cuaHangId);
    //Tùng end

    @Query(value = "select b from BatHo b inner join b.hopdongbh h  where h.id =:idhopdong order by h.ngaytao desc")
    public BatHo findByHopDong(@Param(value = "idhopdong") Long hopdongId);

    @Query(value = "select b from BatHo b  inner join b.hopdongbh h inner join h.cuaHang c inner join h.nhanVien n where h.ngaytao between  ?1 and ?2 and c.id=?3 and n.id = ?4 order by h.ngaytao desc")
    public List<BatHo> baocaoNV(ZonedDateTime start, ZonedDateTime end, Long cuaHangid, Long idNhanVien);

    @Query(value = "select b from BatHo b  inner join b.hopdongbh h inner join h.cuaHang c where h.ngaytao between  ?1 and ?2 and c.id=?3 order by h.ngaytao desc")
    public List<BatHo> baocao(ZonedDateTime start, ZonedDateTime end, Long cuaHangid);

    @Query(value = "select b from BatHo b  inner join b.hopdongbh h inner join h.cuaHang c  where h.ngaytao between  ?1 and ?2 and h.trangthaihopdong = ?3 and c.id=?4 order by h.ngaytao desc")
    public List<BatHo> findByTrangThai(ZonedDateTime start, ZonedDateTime end, TRANGTHAIHOPDONG trangthai, Long cuaHangid);

    @Query(value = "select b from BatHo b  inner join b.hopdongbh h inner join h.cuaHang c  where  h.trangthaihopdong = ?1 and c.id=?2 order by h.ngaytao desc")
    public List<BatHo> findByTrangThaiHopDong(TRANGTHAIHOPDONG trangthai, Long cuaHangid);

    @Query(value = "select b from BatHo b  inner join b.hopdongbh h  where  h.trangthaihopdong = ?1 order by h.ngaytao desc")
    public List<BatHo> findByTrangThaiHopDongAdmin(TRANGTHAIHOPDONG trangthai);

    @Query(value = "select b from BatHo b  inner join b.hopdongbh h inner join h.cuaHang c inner join h.nhanVien n  where h.ngaytao between  ?1 and ?2 and h.trangthaihopdong = ?3 and c.id=?4 and n.id = ?5 order by h.ngaytao desc")
    public List<BatHo> findByTrangThaiNV(ZonedDateTime start, ZonedDateTime end, TRANGTHAIHOPDONG trangthai, Long cuaHangid, Long nhanVienId);

    @Query(value = "select sum(b.tienduakhach) from BatHo b inner join b.hopdongbh h inner join h.cuaHang c where c.id =:idcuahang order by h.ngaytao desc")
    Optional<Double> tienDuaKhach(@Param(value = "idcuahang") Long cuaHangId);
}
