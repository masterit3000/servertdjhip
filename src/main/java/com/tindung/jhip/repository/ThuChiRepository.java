package com.tindung.jhip.repository;

import com.tindung.jhip.domain.ThuChi;
import com.tindung.jhip.domain.enumeration.THUCHI;
import java.time.ZonedDateTime;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the ThuChi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThuChiRepository extends JpaRepository<ThuChi, Long> {

    @Query("select t from ThuChi t where t.thoigian between  ?1 and ?2")
    public void findbyTime(ZonedDateTime start, ZonedDateTime end);

    @Query("select t from ThuChi t where t.thoigian between  ?1 and ?2 and t.thuchi=?3")
    public void findbyTime(ZonedDateTime start, ZonedDateTime end, THUCHI thuchi);

    @Query("select t from ThuChi t inner join t.cuaHang c where t.thoigian between  ?1 and ?2 and c.id =?3")
    public void findbyTime(ZonedDateTime start, ZonedDateTime end, Long cuahangID);

    @Query("select t from ThuChi t  inner join t.cuaHang c  where t.thoigian between  ?1 and ?2 and t.thuchi=?3 and c.id=?4")
    public void findbyTime(ZonedDateTime start, ZonedDateTime end, THUCHI thuchi, Long cuahangID);

}
