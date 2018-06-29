package com.tindung.jhip.service.impl;

import com.tindung.jhip.service.BatHoService;
import com.tindung.jhip.domain.BatHo;
import com.tindung.jhip.repository.BatHoRepository;
import com.tindung.jhip.security.AuthoritiesConstants;
import com.tindung.jhip.security.SecurityUtils;
import com.tindung.jhip.service.NhanVienService;
import com.tindung.jhip.service.dto.BatHoDTO;
import com.tindung.jhip.service.dto.NhanVienDTO;
import com.tindung.jhip.service.mapper.BatHoMapper;
import com.tindung.jhip.web.rest.errors.InternalServerErrorException;
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
    private final NhanVienService nhanVienService;

    public BatHoServiceImpl(BatHoRepository batHoRepository, BatHoMapper batHoMapper, NhanVienService nhanVienService) {
        this.batHoRepository = batHoRepository;
        this.batHoMapper = batHoMapper;
        this.nhanVienService = nhanVienService;
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

        String login = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            LinkedList<BatHoDTO> collect = batHoRepository.findAll().stream()
                    .map(batHoMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            return collect;
        } else {
            NhanVienDTO nhanVien = nhanVienService.findByUserLogin(login);
            Long cuaHangId = nhanVien.getCuaHangId();
            LinkedList<BatHoDTO> collect = batHoRepository.findAllByCuaHang(cuaHangId).stream()
                    .map(batHoMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            return collect;

        }
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
        String login = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
        BatHo batHo = null;
        batHo = batHoRepository.findOne(id);
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            return batHoMapper.toDto(batHo);

        } else {
            NhanVienDTO nhanVien = nhanVienService.findByUserLogin(login);
            Long cuaHangId = nhanVien.getCuaHangId();
            if (batHo.getHopdongbh().getCuaHang().getId() == cuaHangId) {
                return batHoMapper.toDto(batHo);
            }
            return null;

        }
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
