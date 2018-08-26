package com.tindung.jhip.service.impl;

import com.tindung.jhip.domain.NhanVien;
import com.tindung.jhip.service.NhatKyService;
import com.tindung.jhip.domain.NhatKy;
import com.tindung.jhip.repository.CuaHangRepository;
import com.tindung.jhip.repository.NhanVienRepository;
import com.tindung.jhip.repository.NhatKyRepository;
import com.tindung.jhip.security.AuthoritiesConstants;
import com.tindung.jhip.security.SecurityUtils;
import com.tindung.jhip.service.dto.NhatKyDTO;
import com.tindung.jhip.service.mapper.NhatKyMapper;
import com.tindung.jhip.web.rest.errors.InternalServerErrorException;
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

    private final NhanVienRepository nhanVienRepository;

    public NhatKyServiceImpl(NhatKyRepository nhatKyRepository, NhatKyMapper nhatKyMapper, NhanVienRepository nhanVienRepository) {
        this.nhatKyRepository = nhatKyRepository;
        this.nhatKyMapper = nhatKyMapper;
        this.nhanVienRepository = nhanVienRepository;
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

        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN) || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            String login = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
            NhanVien nv = nhanVienRepository.findOneByUser(login).get();
            return nhatKyRepository.findAllByCuaHang(nv.getCuaHang().getId()).stream()
                    .map(nhatKyMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            return nhatKyRepository.findAll().stream()
                    .map(nhatKyMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
        }
        throw new InternalServerErrorException("Khong co quyen");

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

    @Override
    public List<NhatKyDTO> findAllByNoiDungorNhanVien(String key) {
        key = new StringBuffer("%").append(key).append("%").toString();
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN) || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            String login = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
            NhanVien nv = nhanVienRepository.findOneByUser(login).get();
            return nhatKyRepository.findAllByNoiDungorNhanVien(key,nv.getCuaHang().getId()).stream()
                    .map(nhatKyMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            return nhatKyRepository.findAllByNoiDungorNhanVienAdmin(key).stream()
                    .map(nhatKyMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
        }
        throw new InternalServerErrorException("Khong co quyen");

    }
}
