package com.tindung.jhip.service;

import com.tindung.jhip.service.dto.KhachHangDTO;
import java.util.List;

/**
 * Service Interface for managing KhachHang.
 */
public interface KhachHangService {

    /**
     * Save a khachHang.
     *
     * @param khachHangDTO the entity to save
     * @return the persisted entity
     */
    KhachHangDTO save(KhachHangDTO khachHangDTO);
    KhachHangDTO saveforAdmin(KhachHangDTO khachHangDTO);

    
    /**
     * Get all the khachHangs.
     *
     * @return the list of entities
     */
    List<KhachHangDTO> findAll();

    /**
     * Get the "id" khachHang.
     *
     * @param id the id of the entity
     * @return the entity
     */
    KhachHangDTO findOne(Long id);

    /**
     * Delete the "id" khachHang.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    List<KhachHangDTO> findByNameOrCMND(String key);
    List<KhachHangDTO> findAllByCuaHang(Long id);
}
