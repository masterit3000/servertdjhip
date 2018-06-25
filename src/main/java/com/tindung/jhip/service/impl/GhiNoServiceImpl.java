package com.tindung.jhip.service.impl;

import com.tindung.jhip.service.GhiNoService;
import com.tindung.jhip.domain.GhiNo;
import com.tindung.jhip.repository.GhiNoRepository;
import com.tindung.jhip.service.dto.GhiNoDTO;
import com.tindung.jhip.service.mapper.GhiNoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing GhiNo.
 */
@Service
@Transactional
public class GhiNoServiceImpl implements GhiNoService {

    private final Logger log = LoggerFactory.getLogger(GhiNoServiceImpl.class);

    private final GhiNoRepository ghiNoRepository;

    private final GhiNoMapper ghiNoMapper;

    public GhiNoServiceImpl(GhiNoRepository ghiNoRepository, GhiNoMapper ghiNoMapper) {
        this.ghiNoRepository = ghiNoRepository;
        this.ghiNoMapper = ghiNoMapper;
    }

    /**
     * Save a ghiNo.
     *
     * @param ghiNoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GhiNoDTO save(GhiNoDTO ghiNoDTO) {
        log.debug("Request to save GhiNo : {}", ghiNoDTO);
        GhiNo ghiNo = ghiNoMapper.toEntity(ghiNoDTO);
        ghiNo = ghiNoRepository.save(ghiNo);
        return ghiNoMapper.toDto(ghiNo);
    }

    /**
     * Get all the ghiNos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<GhiNoDTO> findAll() {
        log.debug("Request to get all GhiNos");
        return ghiNoRepository.findAll().stream()
            .map(ghiNoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one ghiNo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public GhiNoDTO findOne(Long id) {
        log.debug("Request to get GhiNo : {}", id);
        GhiNo ghiNo = ghiNoRepository.findOne(id);
        return ghiNoMapper.toDto(ghiNo);
    }

    /**
     * Delete the ghiNo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GhiNo : {}", id);
        ghiNoRepository.delete(id);
    }
}
