package com.tindung.jhip.service;

import com.tindung.jhip.service.dto.AnhKhachHangDTO;
import java.util.List;

/**
 * Service Interface for managing AnhKhachHang.
 */
public interface AnhKhachHangService {

    /**
     * Save a anhKhachHang.
     *
     * @param anhKhachHangDTO the entity to save
     * @return the persisted entity
     */
    AnhKhachHangDTO save(AnhKhachHangDTO anhKhachHangDTO);

    /**
     * Get all the anhKhachHangs.
     *
     * @return the list of entities
     */
    List<AnhKhachHangDTO> findAll();

    /**
     * Get the "id" anhKhachHang.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AnhKhachHangDTO findOne(Long id);

    /**
     * Delete the "id" anhKhachHang.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
