package com.tindung.jhip.service.impl;

import com.tindung.jhip.service.AnhKhachHangService;
import com.tindung.jhip.domain.AnhKhachHang;
import com.tindung.jhip.repository.AnhKhachHangRepository;
import com.tindung.jhip.service.dto.AnhKhachHangDTO;
import com.tindung.jhip.service.mapper.AnhKhachHangMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing AnhKhachHang.
 */
@Service
@Transactional
public class AnhKhachHangServiceImpl implements AnhKhachHangService {

    private final Logger log = LoggerFactory.getLogger(AnhKhachHangServiceImpl.class);

    private final AnhKhachHangRepository anhKhachHangRepository;

    private final AnhKhachHangMapper anhKhachHangMapper;

    public AnhKhachHangServiceImpl(AnhKhachHangRepository anhKhachHangRepository, AnhKhachHangMapper anhKhachHangMapper) {
        this.anhKhachHangRepository = anhKhachHangRepository;
        this.anhKhachHangMapper = anhKhachHangMapper;
    }

    /**
     * Save a anhKhachHang.
     *
     * @param anhKhachHangDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AnhKhachHangDTO save(AnhKhachHangDTO anhKhachHangDTO) {
        log.debug("Request to save AnhKhachHang : {}", anhKhachHangDTO);
        AnhKhachHang anhKhachHang = anhKhachHangMapper.toEntity(anhKhachHangDTO);
        anhKhachHang.setFile(anhKhachHangDTO.getFile());
        anhKhachHang = anhKhachHangRepository.save(anhKhachHang);
        return anhKhachHangMapper.toDto(anhKhachHang);
    }

    /**
     * Get all the anhKhachHangs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AnhKhachHangDTO> findAll() {
        log.debug("Request to get all AnhKhachHangs");
        return anhKhachHangRepository.findAll().stream()
                .map(anhKhachHangMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one anhKhachHang by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AnhKhachHangDTO findOne(Long id) {
        log.debug("Request to get AnhKhachHang : {}", id);
        AnhKhachHang anhKhachHang = anhKhachHangRepository.findOne(id);
        AnhKhachHangDTO toDto = anhKhachHangMapper.toDto(anhKhachHang);
        toDto.setFile(anhKhachHang.getFile());
        return toDto;
    }

    /**
     * Delete the anhKhachHang by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnhKhachHang : {}", id);
        anhKhachHangRepository.delete(id);
    }
}
