package com.tindung.jhip.repository;

import com.tindung.jhip.domain.Tinh;
import java.util.List;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Tinh entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TinhRepository extends JpaRepository<Tinh, Long> {

    @Query("select t from Tinh t where t.ten like :query")
    public List<Tinh> findByName(@Param(value = "query") String query);

}
