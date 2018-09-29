package com.tindung.jhip.service;

import com.tindung.jhip.service.dto.NhatKyDTO;
import java.util.List;

/**
 * Service Interface for managing NhatKy.
 */
public interface NhatKyService {

    /**
     * Save a nhatKy.
     *
     * @param nhatKyDTO the entity to save
     * @return the persisted entity
     */
    NhatKyDTO save(NhatKyDTO nhatKyDTO);

    /**
     * Get all the nhatKies.
     *
     * @return the list of entities
     */
    List<NhatKyDTO> findAll();

    /**
     * Get the "id" nhatKy.
     *
     * @param id the id of the entity
     * @return the entity
     */
    NhatKyDTO findOne(Long id);

    /**
     * Delete the "id" nhatKy.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    List<NhatKyDTO> findAllByNoiDungorNhanVien(String key);
    
    List<NhatKyDTO> findAllByCuaHangId(Long cuaHangId);
    
    List<NhatKyDTO> findNhatKyTheoCuaHang(String key,Long cuaHangId);
}
