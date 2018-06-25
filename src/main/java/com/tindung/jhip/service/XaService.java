package com.tindung.jhip.service;

import com.tindung.jhip.service.dto.XaDTO;
import java.util.List;

/**
 * Service Interface for managing Xa.
 */
public interface XaService {

    /**
     * Save a xa.
     *
     * @param xaDTO the entity to save
     * @return the persisted entity
     */
    XaDTO save(XaDTO xaDTO);

    /**
     * Get all the xas.
     *
     * @return the list of entities
     */
    List<XaDTO> findAll();

    /**
     * Get the "id" xa.
     *
     * @param id the id of the entity
     * @return the entity
     */
    XaDTO findOne(Long id);

    /**
     * Delete the "id" xa.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
