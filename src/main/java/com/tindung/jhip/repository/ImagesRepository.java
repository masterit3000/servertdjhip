package com.tindung.jhip.repository;

import com.tindung.jhip.domain.Images;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Images entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImagesRepository extends JpaRepository<Images, Long> {

}
