package com.tindung.jhip.service;

import com.tindung.jhip.domain.enumeration.TRANGTHAIHOPDONG;
import com.tindung.jhip.service.dto.BatHoDTO;
import com.tindung.jhip.service.dto.LichSuDongTienDTO;
import com.tindung.jhip.service.dto.LichSuThaoTacHopDongDTO;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Service Interface for managing BatHo.
 */
public interface BatHoService {

    /**
     * Save a batHo.
     *
     * @param batHoDTO the entity to save
     * @return the persisted entity
     */
    BatHoDTO save(BatHoDTO batHoDTO);

    /**
     * Get all the batHos.
     *
     * @return the list of entities
     */
    List<BatHoDTO> findAll();

    /**
     * Get the "id" batHo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BatHoDTO findOne(Long id);

    /**
     * Delete the "id" batHo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    LichSuDongTienDTO setDongTien(Long id);

    // List<BatHoDTO> findByNameOrCMND(String key);

    BatHoDTO daoHo(BatHoDTO batHoDTO, Long id);
    List<BatHoDTO> findByNameOrCMND(String key);
    List<BatHoDTO> findByHopDong(Long id);
    List<BatHoDTO> findByCuaHangId(Long id); //Tùng add
    List<BatHoDTO> baoCao(ZonedDateTime start, ZonedDateTime end, Long idNhanVien);
    List<BatHoDTO> findByTrangThai(ZonedDateTime start, ZonedDateTime end,TRANGTHAIHOPDONG trangthai);
    List<BatHoDTO> baoCao(ZonedDateTime start, ZonedDateTime end);
}
