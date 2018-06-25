package com.tindung.jhip.service.impl;

import com.tindung.jhip.service.TaiSanService;
import com.tindung.jhip.domain.TaiSan;
import com.tindung.jhip.repository.TaiSanRepository;
import com.tindung.jhip.service.dto.TaiSanDTO;
import com.tindung.jhip.service.mapper.TaiSanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing TaiSan.
 */
@Service
@Transactional
public class TaiSanServiceImpl implements TaiSanService {

    private final Logger log = LoggerFactory.getLogger(TaiSanServiceImpl.class);

    private final TaiSanRepository taiSanRepository;

    private final TaiSanMapper taiSanMapper;

    public TaiSanServiceImpl(TaiSanRepository taiSanRepository, TaiSanMapper taiSanMapper) {
        this.taiSanRepository = taiSanRepository;
        this.taiSanMapper = taiSanMapper;
    }

    /**
     * Save a taiSan.
     *
     * @param taiSanDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TaiSanDTO save(TaiSanDTO taiSanDTO) {
        log.debug("Request to save TaiSan : {}", taiSanDTO);
        TaiSan taiSan = taiSanMapper.toEntity(taiSanDTO);
        taiSan = taiSanRepository.save(taiSan);
        return taiSanMapper.toDto(taiSan);
    }

    /**
     * Get all the taiSans.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TaiSanDTO> findAll() {
        log.debug("Request to get all TaiSans");
        return taiSanRepository.findAll().stream()
            .map(taiSanMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one taiSan by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TaiSanDTO findOne(Long id) {
        log.debug("Request to get TaiSan : {}", id);
        TaiSan taiSan = taiSanRepository.findOne(id);
        return taiSanMapper.toDto(taiSan);
    }

    /**
     * Delete the taiSan by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaiSan : {}", id);
        taiSanRepository.delete(id);
    }
}
