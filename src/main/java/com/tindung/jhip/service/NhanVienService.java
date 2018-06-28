package com.tindung.jhip.service;

import com.tindung.jhip.domain.User;
import com.tindung.jhip.service.dto.NhanVienDTO;
import java.util.List;

/**
 * Service Interface for managing NhanVien.
 */
public interface NhanVienService {

    /**
     * Save a nhanVien.
     *
     * @param nhanVienDTO the entity to save
     * @return the persisted entity
     */
    NhanVienDTO save(NhanVienDTO nhanVienDTO);
    
    /**
     * Get all the nhanViens.
     *
     * @return the list of entities
     */
    List<NhanVienDTO> findAll();

    /**
     * Get the "id" nhanVien.
     *
     * @param id the id of the entity
     * @return the entity
     */
    NhanVienDTO findOne(Long id);

    /**
     * Delete the "id" nhanVien.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    NhanVienDTO findByUserLogin(User user);
}
