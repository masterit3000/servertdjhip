package com.tindung.jhip.repository;

import com.tindung.jhip.domain.GhiNo;
import com.tindung.jhip.domain.enumeration.LOAIHOPDONG;
import com.tindung.jhip.domain.enumeration.NOTRA;
import java.time.ZonedDateTime;
import java.util.List;
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

    @Query(value = "select l from GhiNo l inner join l.hopDong h where l.ngayghino between  ?2 and ?3 and  h.loaihopdong =?1 ")
    List<GhiNo> baocao(LOAIHOPDONG loaihopdong, ZonedDateTime start, ZonedDateTime end);
}
