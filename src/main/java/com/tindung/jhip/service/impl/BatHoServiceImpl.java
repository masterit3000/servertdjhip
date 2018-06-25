package com.tindung.jhip.service.impl;

import com.tindung.jhip.service.BatHoService;
import com.tindung.jhip.domain.BatHo;
import com.tindung.jhip.repository.BatHoRepository;
import com.tindung.jhip.service.dto.BatHoDTO;
import com.tindung.jhip.service.mapper.BatHoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing BatHo.
 */
@Service
@Transactional
public class BatHoServiceImpl implements BatHoService {

    private final Logger log = LoggerFactory.getLogger(BatHoServiceImpl.class);

    private final BatHoRepository batHoRepository;

    private final BatHoMapper batHoMapper;

    public BatHoServiceImpl(BatHoRepository batHoRepository, BatHoMapper batHoMapper) {
        this.batHoRepository = batHoRepository;
        this.batHoMapper = batHoMapper;
    }

    /**
     * Save a batHo.
     *
     * @param batHoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BatHoDTO save(BatHoDTO batHoDTO) {
        log.debug("Request to save BatHo : {}", batHoDTO);
        BatHo batHo = batHoMapper.toEntity(batHoDTO);
        batHo = batHoRepository.save(batHo);
        return batHoMapper.toDto(batHo);
    }

    /**
     * Get all the batHos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BatHoDTO> findAll() {
        log.debug("Request to get all BatHos");
        return batHoRepository.findAll().stream()
            .map(batHoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one batHo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BatHoDTO findOne(Long id) {
        log.debug("Request to get BatHo : {}", id);
        BatHo batHo = batHoRepository.findOne(id);
        return batHoMapper.toDto(batHo);
    }

    /**
     * Delete the batHo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BatHo : {}", id);
        batHoRepository.delete(id);
    }
}
