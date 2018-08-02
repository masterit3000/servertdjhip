package com.tindung.jhip.service;

import com.tindung.jhip.service.dto.TinhDTO;
import java.util.List;

/**
 * Service Interface for managing Tinh.
 */
public interface TinhService {

    /**
     * Save a tinh.
     *
     * @param tinhDTO the entity to save
     * @return the persisted entity
     */
    TinhDTO save(TinhDTO tinhDTO);

    /**
     * Get all the tinhs.
     *
     * @return the list of entities
     */
    List<TinhDTO> findAll();

    /**
     * Get the "id" tinh.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TinhDTO findOne(Long id);

    /**
     * Delete the "id" tinh.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

     List<TinhDTO> findByName(String query);
}
