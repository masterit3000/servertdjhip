package com.tindung.jhip.repository;

import com.tindung.jhip.domain.VayLai;
import java.util.List;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the VayLai entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VayLaiRepository extends JpaRepository<VayLai, Long> {

    @Query(value = "select b from VayLai b inner join b.hopdongvl h inner join h.cuaHang c where c.id =:idcuahang")
    public List<VayLai> findAllByCuaHang(@Param(value = "idcuahang") Long cuaHangId);

}
