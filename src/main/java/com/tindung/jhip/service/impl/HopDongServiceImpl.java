package com.tindung.jhip.service.impl;

import com.tindung.jhip.service.HopDongService;
import com.tindung.jhip.domain.HopDong;
import com.tindung.jhip.repository.HopDongRepository;
import com.tindung.jhip.service.dto.HopDongDTO;
import com.tindung.jhip.service.mapper.HopDongMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing HopDong.
 */
@Service
@Transactional
public class HopDongServiceImpl implements HopDongService {

    private final Logger log = LoggerFactory.getLogger(HopDongServiceImpl.class);

    private final HopDongRepository hopDongRepository;

    private final HopDongMapper hopDongMapper;

    public HopDongServiceImpl(HopDongRepository hopDongRepository, HopDongMapper hopDongMapper) {
        this.hopDongRepository = hopDongRepository;
        this.hopDongMapper = hopDongMapper;
    }

    /**
     * Save a hopDong.
     *
     * @param hopDongDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public HopDongDTO save(HopDongDTO hopDongDTO) {
        log.debug("Request to save HopDong : {}", hopDongDTO);
        HopDong hopDong = hopDongMapper.toEntity(hopDongDTO);
        hopDong = hopDongRepository.save(hopDong);
        return hopDongMapper.toDto(hopDong);
    }

    /**
     * Get all the hopDongs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HopDongDTO> findAll() {
        log.debug("Request to get all HopDongs");
        return hopDongRepository.findAll().stream()
            .map(hopDongMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one hopDong by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public HopDongDTO findOne(Long id) {
        log.debug("Request to get HopDong : {}", id);
        HopDong hopDong = hopDongRepository.findOne(id);
        return hopDongMapper.toDto(hopDong);
    }

    /**
     * Delete the hopDong by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HopDong : {}", id);
        hopDongRepository.delete(id);
    }
}
