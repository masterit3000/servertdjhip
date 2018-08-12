package com.tindung.jhip.repository;

import com.tindung.jhip.domain.ThuChi;
import com.tindung.jhip.domain.enumeration.THUCHI;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the ThuChi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThuChiRepository extends JpaRepository<ThuChi, Long> {

    @Query("select t from ThuChi t where t.thoigian between  ?1 and ?2")
    public List<ThuChi> findbyTime(ZonedDateTime start, ZonedDateTime end);

    @Query("select t from ThuChi t where t.thoigian between  ?1 and ?2 and t.thuchi=?3")
    public List<ThuChi> findbyTime(ZonedDateTime start, ZonedDateTime end, THUCHI thuchi);

    @Query("select t from ThuChi t inner join t.cuaHang c where t.thoigian between  ?1 and ?2 and c.id =?3")
    public List<ThuChi> findbyTime(ZonedDateTime start, ZonedDateTime end, Long cuahangID);

    @Query("select t from ThuChi t  inner join t.cuaHang c  where t.thoigian between  ?1 and ?2 and t.thuchi=?3 and c.id=?4")
    public List<ThuChi> findbyTime(ZonedDateTime start, ZonedDateTime end, THUCHI thuchi, Long cuahangID);

    @Query("select t from ThuChi t  inner join t.cuaHang c inner join t.nhanVien n where t.thoigian between  ?1 and ?2  and c.id=?3 and n.id=?4")
    public List<ThuChi> baoCao(ZonedDateTime start, ZonedDateTime end,  Long cuahangID,Long nhanVienid);

    @Query("select t from ThuChi t where t.thuchi =?1")
    public List<ThuChi> findAllThuChiTheoLoai(@Param(value = "thuchi") THUCHI thuchi);

    @Query("select sum(t.sotien) from ThuChi t  inner join t.cuaHang c  where   t.thuchi=?1 and c.id=?2")
    Optional<Double> thuchi(THUCHI thuchi, Long cuahangID);
}
