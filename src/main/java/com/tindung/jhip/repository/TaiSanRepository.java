package com.tindung.jhip.repository;

import com.tindung.jhip.domain.TaiSan;
import java.util.List;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the TaiSan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaiSanRepository extends JpaRepository<TaiSan, Long> {

    /**
     *
     * @param idhopdong
     * @return
     */
    @Query(value = "select l from TaiSan l inner join l.hopDong h where h.id =:idhopdong")
    List<TaiSan> findByHopDong(@Param(value = "idhopdong") long idhopdong);;

}
