package com.tindung.jhip.service.impl;

import com.tindung.jhip.service.LichSuDongTienService;
import com.tindung.jhip.domain.LichSuDongTien;
import com.tindung.jhip.domain.enumeration.DONGTIEN;
import com.tindung.jhip.repository.BatHoRepository;
import com.tindung.jhip.repository.HopDongRepository;
import com.tindung.jhip.repository.LichSuDongTienRepository;
import com.tindung.jhip.security.SecurityUtils;
import com.tindung.jhip.service.dto.LichSuDongTienDTO;
import com.tindung.jhip.service.mapper.LichSuDongTienMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tindung.jhip.service.CuaHangService;
import com.tindung.jhip.service.NhanVienService;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Service Implementation for managing LichSuDongTien.
 */
@Service
@Transactional
public class LichSuDongTienServiceImpl implements LichSuDongTienService {

    private final Logger log = LoggerFactory.getLogger(LichSuDongTienServiceImpl.class);

    private final LichSuDongTienRepository lichSuDongTienRepository;

    private final LichSuDongTienMapper lichSuDongTienMapper;

    private final HopDongRepository hopDongRepository;

//    @Autowired
    private final CuaHangService cuaHangService;

    public LichSuDongTienServiceImpl(LichSuDongTienRepository lichSuDongTienRepository, LichSuDongTienMapper lichSuDongTienMapper, HopDongRepository hopDongRepository, CuaHangService cuaHangService) {
        this.lichSuDongTienRepository = lichSuDongTienRepository;
        this.lichSuDongTienMapper = lichSuDongTienMapper;
        this.hopDongRepository = hopDongRepository;
        this.cuaHangService = cuaHangService;
    }





    /**
     * Save a lichSuDongTien.
     *
     * @param lichSuDongTienDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LichSuDongTienDTO save(LichSuDongTienDTO lichSuDongTienDTO) {
        log.debug("Request to save LichSuDongTien : {}", lichSuDongTienDTO);
        LichSuDongTien lichSuDongTien = lichSuDongTienMapper.toEntity(lichSuDongTienDTO);
        lichSuDongTien = lichSuDongTienRepository.save(lichSuDongTien);
        return lichSuDongTienMapper.toDto(lichSuDongTien);
    }

    /**
     * Get all the lichSuDongTiens.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LichSuDongTienDTO> findAll() {
        log.debug("Request to get all LichSuDongTiens");
        return lichSuDongTienRepository.findAll().stream()
                .map(lichSuDongTienMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one lichSuDongTien by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LichSuDongTienDTO findOne(Long id) {
        log.debug("Request to get LichSuDongTien : {}", id);
        LichSuDongTien lichSuDongTien = lichSuDongTienRepository.findOne(id);
        return lichSuDongTienMapper.toDto(lichSuDongTien);
    }

    /**
     * Delete the lichSuDongTien by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LichSuDongTien : {}", id);
        lichSuDongTienRepository.delete(id);
    }

    public LichSuDongTienDTO setDongTien(Long id) {
        LichSuDongTien lichSuDongTien = lichSuDongTienRepository.findOne(id);
        lichSuDongTien.setTrangthai(DONGTIEN.DADONG);
        return lichSuDongTienMapper.toDto(lichSuDongTien);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LichSuDongTienDTO> findByHopDong(Long id) {

            List<LichSuDongTien> findByHopDong = lichSuDongTienRepository.findByHopDong(id);
            List<LichSuDongTienDTO> collect = findByHopDong.stream()
                    .map(lichSuDongTienMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            return collect;

    }

}
