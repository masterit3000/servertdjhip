package com.tindung.jhip.repository;

import com.tindung.jhip.domain.LichSuThaoTacHopDong;
import java.util.List;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the LichSuThaoTacHopDong entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LichSuThaoTacHopDongRepository extends JpaRepository<LichSuThaoTacHopDong, Long> {

    @Query(value = "select l from  LichSuThaoTacHopDong l inner join l.hopDong h where h.id =:idhopdong")
    List< LichSuThaoTacHopDong> findByHopDong(@Param(value = "idhopdong")long idhopdong);
}
