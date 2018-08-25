package com.tindung.jhip.repository;

import com.tindung.jhip.domain.NhatKy;
import java.util.List;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the NhatKy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhatKyRepository extends JpaRepository<NhatKy, Long> {

    @Query("select u from NhatKy u inner join u.cuaHang h where h.id = ?1 order by u.thoiGian desc")
    List<NhatKy> findAllByCuaHang(Long id);

    @Query("select u from NhatKy u inner join u.cuaHang h inner join u.nhanVien n where (u.noiDung like :key or n.ten like :key) and h.id =:id order by u.thoiGian desc")
    List<NhatKy> findAllByNoiDungorNhanVien(@Param("key")String key,@Param("id") Long id);

    @Query("select u from NhatKy u inner join u.nhanVien n where (u.noiDung like :key or n.ten like :key) order by u.thoiGian desc")
    List<NhatKy> findAllByNoiDungorNhanVienAdmin(@Param("key")String key);
}
