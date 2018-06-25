package com.tindung.jhip.service.impl;

import com.tindung.jhip.service.LichSuThaoTacHopDongService;
import com.tindung.jhip.domain.LichSuThaoTacHopDong;
import com.tindung.jhip.repository.LichSuThaoTacHopDongRepository;
import com.tindung.jhip.service.dto.LichSuThaoTacHopDongDTO;
import com.tindung.jhip.service.mapper.LichSuThaoTacHopDongMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing LichSuThaoTacHopDong.
 */
@Service
@Transactional
public class LichSuThaoTacHopDongServiceImpl implements LichSuThaoTacHopDongService {

    private final Logger log = LoggerFactory.getLogger(LichSuThaoTacHopDongServiceImpl.class);

    private final LichSuThaoTacHopDongRepository lichSuThaoTacHopDongRepository;

    private final LichSuThaoTacHopDongMapper lichSuThaoTacHopDongMapper;

    public LichSuThaoTacHopDongServiceImpl(LichSuThaoTacHopDongRepository lichSuThaoTacHopDongRepository, LichSuThaoTacHopDongMapper lichSuThaoTacHopDongMapper) {
        this.lichSuThaoTacHopDongRepository = lichSuThaoTacHopDongRepository;
        this.lichSuThaoTacHopDongMapper = lichSuThaoTacHopDongMapper;
    }

    /**
     * Save a lichSuThaoTacHopDong.
     *
     * @param lichSuThaoTacHopDongDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LichSuThaoTacHopDongDTO save(LichSuThaoTacHopDongDTO lichSuThaoTacHopDongDTO) {
        log.debug("Request to save LichSuThaoTacHopDong : {}", lichSuThaoTacHopDongDTO);
        LichSuThaoTacHopDong lichSuThaoTacHopDong = lichSuThaoTacHopDongMapper.toEntity(lichSuThaoTacHopDongDTO);
        lichSuThaoTacHopDong = lichSuThaoTacHopDongRepository.save(lichSuThaoTacHopDong);
        return lichSuThaoTacHopDongMapper.toDto(lichSuThaoTacHopDong);
    }

    /**
     * Get all the lichSuThaoTacHopDongs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LichSuThaoTacHopDongDTO> findAll() {
        log.debug("Request to get all LichSuThaoTacHopDongs");
        return lichSuThaoTacHopDongRepository.findAll().stream()
            .map(lichSuThaoTacHopDongMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one lichSuThaoTacHopDong by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LichSuThaoTacHopDongDTO findOne(Long id) {
        log.debug("Request to get LichSuThaoTacHopDong : {}", id);
        LichSuThaoTacHopDong lichSuThaoTacHopDong = lichSuThaoTacHopDongRepository.findOne(id);
        return lichSuThaoTacHopDongMapper.toDto(lichSuThaoTacHopDong);
    }

    /**
     * Delete the lichSuThaoTacHopDong by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LichSuThaoTacHopDong : {}", id);
        lichSuThaoTacHopDongRepository.delete(id);
    }
}
