package com.tindung.jhip.repository;

import com.tindung.jhip.domain.NhanVien;
import com.tindung.jhip.domain.User;
import java.util.Optional;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the NhanVien entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Long> {

    Optional<NhanVien> findOneByUser(User user);
}
