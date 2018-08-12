package com.tindung.jhip.service;

import com.tindung.jhip.domain.enumeration.THUCHI;
import com.tindung.jhip.service.dto.ThuChiDTO;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Service Interface for managing ThuChi.
 */
public interface ThuChiService {

    /**
     * Save a thuChi.
     *
     * @param thuChiDTO the entity to save
     * @return the persisted entity
     */
    ThuChiDTO save(ThuChiDTO thuChiDTO);

    /**
     * Get all the thuChis.
     *
     * @return the list of entities
     */
    List<ThuChiDTO> findAll();

    List<ThuChiDTO> findAllThuTheoLoai(THUCHI thuchi);

    /**
     * Get the "id" thuChi.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ThuChiDTO findOne(Long id);

    /**
     * Delete the "id" thuChi.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    public List<ThuChiDTO> findByTime(ZonedDateTime start, ZonedDateTime end);

    public List<ThuChiDTO> findByTime(ZonedDateTime start, ZonedDateTime end, THUCHI thuchi);

    List<ThuChiDTO> baoCao(ZonedDateTime start, ZonedDateTime end,Long id);
}
