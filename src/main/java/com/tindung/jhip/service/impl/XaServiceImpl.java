package com.tindung.jhip.service.impl;

import com.tindung.jhip.service.XaService;
import com.tindung.jhip.domain.Xa;
import com.tindung.jhip.repository.XaRepository;
import com.tindung.jhip.service.dto.XaDTO;
import com.tindung.jhip.service.mapper.XaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Xa.
 */
@Service
@Transactional
public class XaServiceImpl implements XaService {

    private final Logger log = LoggerFactory.getLogger(XaServiceImpl.class);

    private final XaRepository xaRepository;

    private final XaMapper xaMapper;

    public XaServiceImpl(XaRepository xaRepository, XaMapper xaMapper) {
        this.xaRepository = xaRepository;
        this.xaMapper = xaMapper;
    }

    /**
     * Save a xa.
     *
     * @param xaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public XaDTO save(XaDTO xaDTO) {
        log.debug("Request to save Xa : {}", xaDTO);
        Xa xa = xaMapper.toEntity(xaDTO);
        xa = xaRepository.save(xa);
        return xaMapper.toDto(xa);
    }

    /**
     * Get all the xas.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<XaDTO> findAll() {
        log.debug("Request to get all Xas");
        return xaRepository.findAll().stream()
            .map(xaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one xa by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public XaDTO findOne(Long id) {
        log.debug("Request to get Xa : {}", id);
        Xa xa = xaRepository.findOne(id);
        return xaMapper.toDto(xa);
    }

    /**
     * Delete the xa by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Xa : {}", id);
        xaRepository.delete(id);
    }
}
