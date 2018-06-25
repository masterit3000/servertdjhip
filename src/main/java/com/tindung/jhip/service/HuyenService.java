package com.tindung.jhip.service;

import com.tindung.jhip.service.dto.HuyenDTO;
import java.util.List;

/**
 * Service Interface for managing Huyen.
 */
public interface HuyenService {

    /**
     * Save a huyen.
     *
     * @param huyenDTO the entity to save
     * @return the persisted entity
     */
    HuyenDTO save(HuyenDTO huyenDTO);

    /**
     * Get all the huyens.
     *
     * @return the list of entities
     */
    List<HuyenDTO> findAll();

    /**
     * Get the "id" huyen.
     *
     * @param id the id of the entity
     * @return the entity
     */
    HuyenDTO findOne(Long id);

    /**
     * Delete the "id" huyen.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
