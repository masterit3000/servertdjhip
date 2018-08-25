package com.tindung.jhip.repository;

import com.tindung.jhip.domain.LichSuDongTien;
import com.tindung.jhip.domain.enumeration.DONGTIEN;
import com.tindung.jhip.domain.enumeration.LOAIHOPDONG;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the LichSuDongTien entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LichSuDongTienRepository extends JpaRepository<LichSuDongTien, Long> {

    @Query(value = "select l from LichSuDongTien l inner join l.hopDong h where h.id =:idhopdong")
    List<LichSuDongTien> findByHopDong(@Param(value = "idhopdong") long idhopdong);

    @Query(value = "select l from LichSuDongTien l inner join l.hopDong h inner join h.cuaHang c where l.ngaygiaodich between  ?3 and ?4 and l.trangthai=?1 and  h.loaihopdong =?2 and c.id =?5 order by l.ngaygiaodich desc")
    List<LichSuDongTien> baocao(DONGTIEN dongtien, LOAIHOPDONG loaihopdong, ZonedDateTime start, ZonedDateTime end, Long cuaHangid);

    @Query(value = "select l from LichSuDongTien l inner join l.hopDong h inner join h.cuaHang c inner join h.nhanVien n where l.ngaygiaodich between  ?3 and ?4 and l.trangthai=?1 and  h.loaihopdong =?2 and c.id =?5 and n.id =?6 order by l.ngaygiaodich desc ")
    List<LichSuDongTien> baocaoNV(DONGTIEN dongtien, LOAIHOPDONG loaihopdong, ZonedDateTime start, ZonedDateTime end, Long cuaHangid, Long nhanVienid);

    @Query(value = "select l from LichSuDongTien l inner join l.hopDong h inner join h.cuaHang c where c.id =?2 and l.trangthai= ?1 and h.id =?3")
    List<LichSuDongTien> findByTrangThai(DONGTIEN trangthai, Long idcuahang, Long idhopdong);

    @Query(value = "select l from LichSuDongTien l inner join l.hopDong h where l.trangthai= ?1 and h.id =?2")
    List<LichSuDongTien> findByTrangThaiAdmin(DONGTIEN trangthai, Long idhopdong);

    @Query(value = "select case when count(l) >0 then true else false end from LichSuDongTien l inner join l.hopDong h where h.id =:idhopdong and l.ngayketthuc < now() ")
    boolean kiemtra(@Param(value = "idhopdong") long idhopdong);

    @Query(value = "select sum(l.sotien) from LichSuDongTien l inner join l.hopDong h inner join h.cuaHang c where c.id =?2 and l.trangthai= ?1")
    Optional<Double> lichSuDongTien(DONGTIEN trangthai, Long idcuahang);

    @Query(value = "select l from LichSuDongTien l inner join l.hopDong h inner join h.cuaHang c where l.trangthai =?1 and h.loaihopdong=?2 and (l.ngayketthuc < ?3) and c.id =?4 order by l.ngayketthuc ")
    List<LichSuDongTien> lichSuTraCham(DONGTIEN dongtien, LOAIHOPDONG loaihopdong, ZonedDateTime ngayhientai, Long cuaHangid);

    @Query(value = "select l from LichSuDongTien l inner join l.hopDong h  where l.trangthai =?1 and h.loaihopdong=?2 and (l.ngayketthuc < ?3) order by l.ngaygiaodich ")
    List<LichSuDongTien> lichSuTraChamAdmin(DONGTIEN dongtien, LOAIHOPDONG loaihopdong, ZonedDateTime ngayhientai);

    @Query(value = "select l from LichSuDongTien l inner join l.hopDong h inner join h.cuaHang c where l.trangthai =?1 and h.loaihopdong=?2 and (?3 between l.ngaybatdau and l.ngayketthuc ) and c.id =?4 order by l.ngaybatdau ")
    List<LichSuDongTien> lichSuTraHomNay(DONGTIEN dongtien, LOAIHOPDONG loaihopdong, ZonedDateTime ngayhientai, Long cuaHangid);

    @Query(value = "select l from LichSuDongTien l inner join l.hopDong h  where l.trangthai =?1 and h.loaihopdong=?2 and (?3 between l.ngaybatdau and l.ngayketthuc )  order by l.ngaygiaodich ")
    List<LichSuDongTien> lichSuTraHomNayAdmin(DONGTIEN dongtien, LOAIHOPDONG loaihopdong, ZonedDateTime ngayhientai);

}
