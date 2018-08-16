package com.tindung.jhip.service.impl;

import com.tindung.jhip.service.GhiNoService;
import com.tindung.jhip.domain.GhiNo;
import com.tindung.jhip.domain.enumeration.LOAIHOPDONG;
import com.tindung.jhip.repository.GhiNoRepository;
import com.tindung.jhip.security.AuthoritiesConstants;
import com.tindung.jhip.security.SecurityUtils;
import com.tindung.jhip.service.CuaHangService;
import com.tindung.jhip.service.NhanVienService;
import com.tindung.jhip.service.dto.GhiNoDTO;
import com.tindung.jhip.service.mapper.GhiNoMapper;
import com.tindung.jhip.web.rest.errors.InternalServerErrorException;
import java.time.ZonedDateTime;
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
    private final NhanVienService nhanVienService;
    private final CuaHangService cuaHangService;

    public GhiNoServiceImpl(GhiNoRepository ghiNoRepository, GhiNoMapper ghiNoMapper, NhanVienService nhanVienService, CuaHangService cuaHangService) {
        this.ghiNoRepository = ghiNoRepository;
        this.ghiNoMapper = ghiNoMapper;
        this.nhanVienService = nhanVienService;
        this.cuaHangService = cuaHangService;
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
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN) || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)) {
            if (ghiNoDTO.getId() == null) {//them moi
                ghiNoDTO.setNgayghino(ZonedDateTime.now());
                ghiNoDTO.setNhanVienId(nhanVienService.findByUserLogin().getId());
                GhiNo ghiNo = ghiNoMapper.toEntity(ghiNoDTO);
                ghiNo = ghiNoRepository.save(ghiNo);
                return ghiNoMapper.toDto(ghiNo);
            }
        }
        throw new InternalError("Khong cos quyen");
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

    @Override
    @Transactional(readOnly = true)
    public List<GhiNoDTO> findByHopDong(Long id) {
        log.debug("Request to get all GhiNos by hopdong");

        return ghiNoRepository.findByHopDong(id).stream()
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
    public GhiNoDTO findOne(Long id
    ) {
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
    public void delete(Long id
    ) {
        log.debug("Request to delete GhiNo : {}", id);
        ghiNoRepository.delete(id);
    }

    @Override
    public List<GhiNoDTO> baoCao(LOAIHOPDONG loaihopdong, ZonedDateTime start, ZonedDateTime end) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            Long cuaHangId = cuaHangService.findIDByUserLogin();
            List< GhiNo> ghiNos = ghiNoRepository.baocao(loaihopdong, start, end, cuaHangId);
            List<GhiNoDTO> collect = ghiNos.stream()
                    .map(ghiNoMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            return collect;
        }
        throw new InternalServerErrorException("Khong co quyen");

    }

    @Override
    public List<GhiNoDTO> baoCaoNV(LOAIHOPDONG loaihopdong, ZonedDateTime start, ZonedDateTime end, Long nhanVienid) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            Long cuaHangId = cuaHangService.findIDByUserLogin();
            List<GhiNo> ghiNos = ghiNoRepository.baocaoNV(loaihopdong, start, end, nhanVienid, cuaHangId);
            List<GhiNoDTO> collect = ghiNos.stream()
                    .map(ghiNoMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            return collect;
        }
        throw new InternalServerErrorException("Khong co quyen");

    }
}
