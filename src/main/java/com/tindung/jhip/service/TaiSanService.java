package com.tindung.jhip.service;

import com.tindung.jhip.service.dto.TaiSanDTO;
import java.util.List;

/**
 * Service Interface for managing TaiSan.
 */
public interface TaiSanService {

    /**
     * Save a taiSan.
     *
     * @param taiSanDTO the entity to save
     * @return the persisted entity
     */
    TaiSanDTO save(TaiSanDTO taiSanDTO);

    /**
     * Get all the taiSans.
     *
     * @return the list of entities
     */
    List<TaiSanDTO> findAll();

    /**
     * Get the "id" taiSan.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TaiSanDTO findOne(Long id);

    /**
     * Delete the "id" taiSan.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    List<TaiSanDTO> findByHopDong(Long id);
}
