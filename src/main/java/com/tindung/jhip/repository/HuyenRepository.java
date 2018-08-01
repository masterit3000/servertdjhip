package com.tindung.jhip.repository;

import com.tindung.jhip.domain.Huyen;
import java.util.List;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Huyen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HuyenRepository extends JpaRepository<Huyen, Long> {

    @Query("select h from Huyen h inner join h.tinh t where t.id=:id")
    public List<Huyen> findAllByTinh(@Param(value = "id") Long idtinh);

}
