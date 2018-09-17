package com.tindung.jhip.service;

import com.tindung.jhip.domain.enumeration.LOAIHOPDONG;
import com.tindung.jhip.domain.enumeration.TRANGTHAIHOPDONG;
import com.tindung.jhip.service.dto.HopDongDTO;
import java.util.List;

/**
 * Service Interface for managing HopDong.
 */
public interface HopDongService {

    /**
     * Save a hopDong.
     *
     * @param hopDongDTO the entity to save
     * @return the persisted entity
     */
    HopDongDTO save(HopDongDTO hopDongDTO);

    /**
     * Get all the hopDongs.
     *
     * @return the list of entities
     */
    List<HopDongDTO> findAll();

    /**
     * Get the "id" hopDong.
     *
     * @param id the id of the entity
     * @return the entity
     */
    HopDongDTO findOne(Long id);

    /**
     * Delete the "id" hopDong.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    
    List<HopDongDTO> thongKe(TRANGTHAIHOPDONG trangthai,LOAIHOPDONG loai);
    
    List<HopDongDTO> findHopDongTraCham(LOAIHOPDONG loaihopdong);
}
