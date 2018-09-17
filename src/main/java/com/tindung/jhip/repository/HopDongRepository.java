package com.tindung.jhip.repository;

import com.tindung.jhip.domain.HopDong;
import com.tindung.jhip.domain.enumeration.DONGTIEN;
import com.tindung.jhip.domain.enumeration.LOAIHOPDONG;
import com.tindung.jhip.domain.enumeration.TRANGTHAIHOPDONG;
import java.time.ZonedDateTime;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the HopDong entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HopDongRepository extends JpaRepository<HopDong, Long> {

    @Query(value = "select h from HopDong h  inner join h.cuaHang c where c.id =?2 and h.trangthaihopdong =?1 and h.loaihopdong = ?3")
    List<HopDong> thongke(TRANGTHAIHOPDONG trangthai, Long idcuaHang, LOAIHOPDONG loai);

    @Query(value = "select h from HopDong h  where h.trangthaihopdong =?1 and h.loaihopdong = ?2")
    List<HopDong> thongkeAdmin(TRANGTHAIHOPDONG trangthai, LOAIHOPDONG loai);

    @Query(value = "SELECT * from hop_dong WHERE tinhNo(hop_dong.id) > 0 AND hop_dong.trangthai=?1 and hop_dong.loaihopdong =?2", nativeQuery = true)
    List<HopDong> thongkeHopDongNo(TRANGTHAIHOPDONG trangthai, LOAIHOPDONG loai);

    @Query(value = "SELECT * from hop_dong WHERE tinhNo(hop_dong.id) > 0 AND hop_dong.loaihopdong='BATHO'", nativeQuery = true)
    List<HopDong> thongkeHopDongNoBatHo();

    @Query(value = "SELECT * from hop_dong WHERE tinhNo(hop_dong.id) > 0 AND hop_dong.loaihopdong='VAYLAI'", nativeQuery = true)
    List<HopDong> thongkeHopDongNoVayLai();

    @Query(value = "select m from HopDong m  where exists(select l from LichSuDongTien l inner join l.hopDong h inner join h.cuaHang c where m.id = h.id and l.trangthai =?1 and h.loaihopdong=?2 and (l.ngayketthuc < ?3) and c.id =?4 )")
    List<HopDong> hopDongTraCham(DONGTIEN dongtien, LOAIHOPDONG loaihopdong, ZonedDateTime ngayhientai, Long cuaHangid);
}
