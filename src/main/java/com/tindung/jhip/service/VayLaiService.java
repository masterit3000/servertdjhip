package com.tindung.jhip.service;

import com.tindung.jhip.domain.enumeration.TRANGTHAIHOPDONG;
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
    List<VayLaiDTO> findAll(TRANGTHAIHOPDONG trangthai);

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

    List<VayLaiDTO> findByNameOrCMND(String key,TRANGTHAIHOPDONG trangthai);

    VayLaiDTO vay(VayLaiDTO vayLaiDTO, Long id, String mahopdong);

    List<VayLaiDTO> baoCao(ZonedDateTime start, ZonedDateTime end, Integer vayThemTraGoc);

    List<VayLaiDTO> baoCao(ZonedDateTime start, ZonedDateTime end, Long id, Integer vayThemTraGoc);

    List<VayLaiDTO> findAllByCuaHang(Long id);

    List<VayLaiDTO> findByNhanVien(Long id);

    VayLaiDTO findByHopDong(Long id);

    Double tienVayThemTraBot(Long id);

    List<VayLaiDTO> lichSuTraCham();

    List<VayLaiDTO> baoCaoKeToan(ZonedDateTime start, ZonedDateTime end, Integer vayThemTraGoc, Long idCuaHang);

//    List<VayLaiDTO> findByKhachHang(List<VayLaiDTO> list, Long id);
}
