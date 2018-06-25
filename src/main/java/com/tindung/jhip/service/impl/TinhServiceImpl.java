package com.tindung.jhip.service.impl;

import com.tindung.jhip.service.TinhService;
import com.tindung.jhip.domain.Tinh;
import com.tindung.jhip.repository.TinhRepository;
import com.tindung.jhip.service.dto.TinhDTO;
import com.tindung.jhip.service.mapper.TinhMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Tinh.
 */
@Service
@Transactional
public class TinhServiceImpl implements TinhService {

    private final Logger log = LoggerFactory.getLogger(TinhServiceImpl.class);

    private final TinhRepository tinhRepository;

    private final TinhMapper tinhMapper;

    public TinhServiceImpl(TinhRepository tinhRepository, TinhMapper tinhMapper) {
        this.tinhRepository = tinhRepository;
        this.tinhMapper = tinhMapper;
    }

    /**
     * Save a tinh.
     *
     * @param tinhDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TinhDTO save(TinhDTO tinhDTO) {
        log.debug("Request to save Tinh : {}", tinhDTO);
        Tinh tinh = tinhMapper.toEntity(tinhDTO);
        tinh = tinhRepository.save(tinh);
        return tinhMapper.toDto(tinh);
    }

    /**
     * Get all the tinhs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TinhDTO> findAll() {
        log.debug("Request to get all Tinhs");
        return tinhRepository.findAll().stream()
            .map(tinhMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one tinh by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TinhDTO findOne(Long id) {
        log.debug("Request to get Tinh : {}", id);
        Tinh tinh = tinhRepository.findOne(id);
        return tinhMapper.toDto(tinh);
    }

    /**
     * Delete the tinh by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tinh : {}", id);
        tinhRepository.delete(id);
    }
}
