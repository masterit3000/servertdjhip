package com.tindung.jhip.repository;

import com.tindung.jhip.domain.GhiNo;
import com.tindung.jhip.domain.enumeration.LOAIHOPDONG;
import com.tindung.jhip.domain.enumeration.NOTRA;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the GhiNo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GhiNoRepository extends JpaRepository<GhiNo, Long> {

    @Query(value = "select l from GhiNo l inner join l.hopDong h where h.id =:idhopdong")
    List<GhiNo> findByHopDong(@Param(value = "idhopdong") long idhopdong);

    @Query(value = "select l from GhiNo l inner join l.hopDong h inner join h.cuaHang c where l.ngayghino between  ?2 and ?3 and  h.loaihopdong =?1 and c.id= ?3 ")
    List<GhiNo> baocao(LOAIHOPDONG loaihopdong, ZonedDateTime start, ZonedDateTime end, Long idCuaHang);

    @Query(value = "select l from GhiNo l inner join l.hopDong h inner join h.nhanVien n inner join h.cuaHang c where l.ngayghino between  ?2 and ?3 and  h.loaihopdong =?1 and n.id = ?4 and c.id=?5")
    List<GhiNo> baocaoNV(LOAIHOPDONG loaihopdong, ZonedDateTime start, ZonedDateTime end, Long nhanVienid, Long idCuaHang);

    @Query(value = "select sum(l.sotien) from GhiNo l inner join l.hopDong h inner join h.cuaHang c where c.id =?2 and l.trangthai =?1")
    Optional<Double> tienNo(NOTRA trangthai, Long idcuaHang);

    @Query(value = "select l from GhiNo l inner join l.hopDong h inner join h.cuaHang c where c.id =?2 and l.trangthai =?1")
    Optional<Double> thongke(NOTRA trangthai, Long idcuaHang);
}
