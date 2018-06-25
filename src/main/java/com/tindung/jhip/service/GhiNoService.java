package com.tindung.jhip.service;

import com.tindung.jhip.service.dto.GhiNoDTO;
import java.util.List;

/**
 * Service Interface for managing GhiNo.
 */
public interface GhiNoService {

    /**
     * Save a ghiNo.
     *
     * @param ghiNoDTO the entity to save
     * @return the persisted entity
     */
    GhiNoDTO save(GhiNoDTO ghiNoDTO);

    /**
     * Get all the ghiNos.
     *
     * @return the list of entities
     */
    List<GhiNoDTO> findAll();

    /**
     * Get the "id" ghiNo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    GhiNoDTO findOne(Long id);

    /**
     * Delete the "id" ghiNo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
