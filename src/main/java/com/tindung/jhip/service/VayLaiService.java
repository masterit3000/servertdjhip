package com.tindung.jhip.service;

import com.tindung.jhip.service.dto.LichSuDongTienDTO;
import com.tindung.jhip.service.dto.LichSuThaoTacHopDongDTO;
import com.tindung.jhip.service.dto.VayLaiDTO;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Service Interface for managing VayLai.
 */
public interface VayLaiService {

    /**
     * Save a vayLai.
     *
     * @param vayLaiDTO the entity to save
     * @return the persisted entity
     */
    VayLaiDTO save(VayLaiDTO vayLaiDTO);

    /**
     * Get all the vayLais.
     *
     * @return the list of entities
     */
    List<VayLaiDTO> findAll();

    /**
     * Get the "id" vayLai.
     *
     * @param id the id of the entity
     * @return the entity
     */
    VayLaiDTO findOne(Long id);

    /**
     * Delete the "id" vayLai.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    List<VayLaiDTO> findByNameOrCMND(String key);

    VayLaiDTO vay(VayLaiDTO vayLaiDTO, Long id);

    List<VayLaiDTO> baoCao(ZonedDateTime start, ZonedDateTime end);

    List<VayLaiDTO> baoCao(ZonedDateTime start, ZonedDateTime end,Long id);

//    List<VayLaiDTO> findByKhachHang(List<VayLaiDTO> list, Long id);
}
