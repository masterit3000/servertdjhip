package com.tindung.jhip.service.impl;

import com.tindung.jhip.service.NhatKyService;
import com.tindung.jhip.domain.NhatKy;
import com.tindung.jhip.repository.NhatKyRepository;
import com.tindung.jhip.service.dto.NhatKyDTO;
import com.tindung.jhip.service.mapper.NhatKyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing NhatKy.
 */
@Service
@Transactional
public class NhatKyServiceImpl implements NhatKyService {

    private final Logger log = LoggerFactory.getLogger(NhatKyServiceImpl.class);

    private final NhatKyRepository nhatKyRepository;

    private final NhatKyMapper nhatKyMapper;

    public NhatKyServiceImpl(NhatKyRepository nhatKyRepository, NhatKyMapper nhatKyMapper) {
        this.nhatKyRepository = nhatKyRepository;
        this.nhatKyMapper = nhatKyMapper;
    }

    /**
     * Save a nhatKy.
     *
     * @param nhatKyDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public NhatKyDTO save(NhatKyDTO nhatKyDTO) {
        log.debug("Request to save NhatKy : {}", nhatKyDTO);
        NhatKy nhatKy = nhatKyMapper.toEntity(nhatKyDTO);
        nhatKy = nhatKyRepository.save(nhatKy);
        return nhatKyMapper.toDto(nhatKy);
    }

    /**
     * Get all the nhatKies.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<NhatKyDTO> findAll() {
        log.debug("Request to get all NhatKies");
        return nhatKyRepository.findAll().stream()
            .map(nhatKyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one nhatKy by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public NhatKyDTO findOne(Long id) {
        log.debug("Request to get NhatKy : {}", id);
        NhatKy nhatKy = nhatKyRepository.findOne(id);
        return nhatKyMapper.toDto(nhatKy);
    }

    /**
     * Delete the nhatKy by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete NhatKy : {}", id);
        nhatKyRepository.delete(id);
    }
}
