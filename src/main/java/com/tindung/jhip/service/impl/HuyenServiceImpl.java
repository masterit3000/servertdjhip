package com.tindung.jhip.service.impl;

import com.tindung.jhip.service.HuyenService;
import com.tindung.jhip.domain.Huyen;
import com.tindung.jhip.repository.HuyenRepository;
import com.tindung.jhip.service.dto.HuyenDTO;
import com.tindung.jhip.service.mapper.HuyenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Huyen.
 */
@Service
@Transactional
public class HuyenServiceImpl implements HuyenService {

    private final Logger log = LoggerFactory.getLogger(HuyenServiceImpl.class);

    private final HuyenRepository huyenRepository;

    private final HuyenMapper huyenMapper;

    public HuyenServiceImpl(HuyenRepository huyenRepository, HuyenMapper huyenMapper) {
        this.huyenRepository = huyenRepository;
        this.huyenMapper = huyenMapper;
    }

    /**
     * Save a huyen.
     *
     * @param huyenDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public HuyenDTO save(HuyenDTO huyenDTO) {
        log.debug("Request to save Huyen : {}", huyenDTO);
        Huyen huyen = huyenMapper.toEntity(huyenDTO);
        huyen = huyenRepository.save(huyen);
        return huyenMapper.toDto(huyen);
    }

    /**
     * Get all the huyens.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HuyenDTO> findAll() {
        log.debug("Request to get all Huyens");
        return huyenRepository.findAll().stream()
            .map(huyenMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one huyen by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public HuyenDTO findOne(Long id) {
        log.debug("Request to get Huyen : {}", id);
        Huyen huyen = huyenRepository.findOne(id);
        return huyenMapper.toDto(huyen);
    }

    /**
     * Delete the huyen by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Huyen : {}", id);
        huyenRepository.delete(id);
    }

    @Override
    public List<HuyenDTO> findAllByTinh(Long idtinh) {
         log.debug("Request to get all Huyens by tinh:"+idtinh);
        return huyenRepository.findAllByTinh(idtinh).stream()
            .map(huyenMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
