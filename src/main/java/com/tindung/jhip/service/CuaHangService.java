package com.tindung.jhip.service;

import com.tindung.jhip.service.dto.CuaHangDTO;
import java.util.List;

/**
 * Service Interface for managing CuaHang.
 */
public interface CuaHangService {

    /**
     * Save a cuaHang.
     *
     * @param cuaHangDTO the entity to save
     * @return the persisted entity
     */
    CuaHangDTO save(CuaHangDTO cuaHangDTO);

    /**
     * Get all the cuaHangs.
     *
     * @return the list of entities
     */
    List<CuaHangDTO> findAll();

    /**
     * Get the "id" cuaHang.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CuaHangDTO findOne(Long id);

    /**
     * Delete the "id" cuaHang.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
