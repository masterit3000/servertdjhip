package com.tindung.jhip.service.impl;

import com.tindung.jhip.service.KhachHangService;
import com.tindung.jhip.domain.KhachHang;
import com.tindung.jhip.repository.KhachHangRepository;
import com.tindung.jhip.service.dto.KhachHangDTO;
import com.tindung.jhip.service.mapper.KhachHangMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing KhachHang.
 */
@Service
@Transactional
public class KhachHangServiceImpl implements KhachHangService {

    private final Logger log = LoggerFactory.getLogger(KhachHangServiceImpl.class);

    private final KhachHangRepository khachHangRepository;

    private final KhachHangMapper khachHangMapper;

    public KhachHangServiceImpl(KhachHangRepository khachHangRepository, KhachHangMapper khachHangMapper) {
        this.khachHangRepository = khachHangRepository;
        this.khachHangMapper = khachHangMapper;
    }

    /**
     * Save a khachHang.
     *
     * @param khachHangDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public KhachHangDTO save(KhachHangDTO khachHangDTO) {
        log.debug("Request to save KhachHang : {}", khachHangDTO);
        KhachHang khachHang = khachHangMapper.toEntity(khachHangDTO);
        khachHang = khachHangRepository.save(khachHang);
        return khachHangMapper.toDto(khachHang);
    }

    /**
     * Get all the khachHangs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<KhachHangDTO> findAll() {
        log.debug("Request to get all KhachHangs");
        return khachHangRepository.findAll().stream()
            .map(khachHangMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one khachHang by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public KhachHangDTO findOne(Long id) {
        log.debug("Request to get KhachHang : {}", id);
        KhachHang khachHang = khachHangRepository.findOne(id);
        return khachHangMapper.toDto(khachHang);
    }

    /**
     * Delete the khachHang by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete KhachHang : {}", id);
        khachHangRepository.delete(id);
    }
}
