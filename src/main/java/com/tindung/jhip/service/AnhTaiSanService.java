package com.tindung.jhip.service;

import com.tindung.jhip.service.dto.AnhTaiSanDTO;
import java.util.List;

/**
 * Service Interface for managing AnhTaiSan.
 */
public interface AnhTaiSanService {

    /**
     * Save a anhTaiSan.
     *
     * @param anhTaiSanDTO the entity to save
     * @return the persisted entity
     */
    AnhTaiSanDTO save(AnhTaiSanDTO anhTaiSanDTO);

    /**
     * Get all the anhTaiSans.
     *
     * @return the list of entities
     */
    List<AnhTaiSanDTO> findAll();

    /**
     * Get the "id" anhTaiSan.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AnhTaiSanDTO findOne(Long id);

    /**
     * Delete the "id" anhTaiSan.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
