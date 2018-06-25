package com.tindung.jhip.service.impl;

import com.tindung.jhip.service.VayLaiService;
import com.tindung.jhip.domain.VayLai;
import com.tindung.jhip.repository.VayLaiRepository;
import com.tindung.jhip.service.dto.VayLaiDTO;
import com.tindung.jhip.service.mapper.VayLaiMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing VayLai.
 */
@Service
@Transactional
public class VayLaiServiceImpl implements VayLaiService {

    private final Logger log = LoggerFactory.getLogger(VayLaiServiceImpl.class);

    private final VayLaiRepository vayLaiRepository;

    private final VayLaiMapper vayLaiMapper;

    public VayLaiServiceImpl(VayLaiRepository vayLaiRepository, VayLaiMapper vayLaiMapper) {
        this.vayLaiRepository = vayLaiRepository;
        this.vayLaiMapper = vayLaiMapper;
    }

    /**
     * Save a vayLai.
     *
     * @param vayLaiDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VayLaiDTO save(VayLaiDTO vayLaiDTO) {
        log.debug("Request to save VayLai : {}", vayLaiDTO);
        VayLai vayLai = vayLaiMapper.toEntity(vayLaiDTO);
        vayLai = vayLaiRepository.save(vayLai);
        return vayLaiMapper.toDto(vayLai);
    }

    /**
     * Get all the vayLais.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<VayLaiDTO> findAll() {
        log.debug("Request to get all VayLais");
        return vayLaiRepository.findAll().stream()
            .map(vayLaiMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one vayLai by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public VayLaiDTO findOne(Long id) {
        log.debug("Request to get VayLai : {}", id);
        VayLai vayLai = vayLaiRepository.findOne(id);
        return vayLaiMapper.toDto(vayLai);
    }

    /**
     * Delete the vayLai by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VayLai : {}", id);
        vayLaiRepository.delete(id);
    }
}
