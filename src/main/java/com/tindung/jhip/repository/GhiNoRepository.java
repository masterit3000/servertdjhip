package com.tindung.jhip.repository;

import com.tindung.jhip.domain.GhiNo;
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
}
