package com.tindung.jhip.repository;

import com.tindung.jhip.domain.HopDong;
import com.tindung.jhip.domain.enumeration.TRANGTHAIHOPDONG;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the HopDong entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HopDongRepository extends JpaRepository<HopDong, Long> {

    @Query(value = "select h from HopDong h  inner join h.cuaHang c where c.id =?2 and h.trangthaihopdong =?1")
    List<HopDong> thongke(TRANGTHAIHOPDONG trangthai, Long idcuaHang);
}
