package com.tindung.jhip.repository;

import com.tindung.jhip.domain.NhanVien;
import com.tindung.jhip.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the NhanVien entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Long> {

    @Query("select n from NhanVien n inner join n.cuaHang c where c.id =:id")
    List<NhanVien> findAllByCuaHang(@Param("id") long cuaHangid);

    @Query("select n from NhanVien n inner join n.user u where u.id =:id")
    Optional<NhanVien> findOneByUser(@Param("id") long userid
    );

    @Query("select n from NhanVien n inner join n.user u where u.login =:id")
    Optional<NhanVien> findOneByUser(@Param("id") String login);

    Optional<NhanVien> findOneByUser(User user);
//    NhanVien findOneByUser(User user);
}
