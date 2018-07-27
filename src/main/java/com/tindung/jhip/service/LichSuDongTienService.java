package com.tindung.jhip.service;

import com.tindung.jhip.domain.enumeration.DONGTIEN;
import com.tindung.jhip.service.dto.LichSuDongTienDTO;
import java.util.List;

/**
 * Service Interface for managing LichSuDongTien.
 */
public interface LichSuDongTienService {

    /**
     * Save a lichSuDongTien.
     *
     * @param lichSuDongTienDTO the entity to save
     * @return the persisted entity
     */
    LichSuDongTienDTO save(LichSuDongTienDTO lichSuDongTienDTO);

    /**
     * Get all the lichSuDongTiens.
     *
     * @return the list of entities
     */
    List<LichSuDongTienDTO> findAll();

    /**
     * Get the "id" lichSuDongTien.
     *
     * @param id the id of the entity
     * @return the entity
     */
    LichSuDongTienDTO findOne(Long id);

    /**
     * Delete the "id" lichSuDongTien.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    LichSuDongTienDTO setDongTien(Long id);

    List<LichSuDongTienDTO> findByHopDong(Long id);
}
