package com.tindung.jhip.service.impl;

import com.tindung.jhip.service.LichSuThaoTacHopDongService;
import com.tindung.jhip.domain.LichSuThaoTacHopDong;
import com.tindung.jhip.repository.LichSuThaoTacHopDongRepository;
import com.tindung.jhip.security.AuthoritiesConstants;
import com.tindung.jhip.security.SecurityUtils;
import com.tindung.jhip.service.CuaHangService;
import com.tindung.jhip.service.HopDongService;
import com.tindung.jhip.service.NhanVienService;
import com.tindung.jhip.service.dto.HopDongDTO;
import com.tindung.jhip.service.dto.LichSuThaoTacHopDongDTO;
import com.tindung.jhip.service.mapper.LichSuThaoTacHopDongMapper;
import com.tindung.jhip.web.rest.errors.InternalServerErrorException;
import java.time.ZonedDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
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
    private final HopDongService hopDongService;
    private final NhanVienService nhanvienService;

    public LichSuThaoTacHopDongServiceImpl(LichSuThaoTacHopDongRepository lichSuThaoTacHopDongRepository, LichSuThaoTacHopDongMapper lichSuThaoTacHopDongMapper, HopDongService hopDongService, NhanVienService cuaHangService) {
        this.lichSuThaoTacHopDongRepository = lichSuThaoTacHopDongRepository;
        this.lichSuThaoTacHopDongMapper = lichSuThaoTacHopDongMapper;
        this.hopDongService = hopDongService;
        this.nhanvienService = cuaHangService;
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
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN) || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {

            lichSuThaoTacHopDongDTO.setNhanVienId(nhanvienService.findByUserLogin().getId());
            lichSuThaoTacHopDongDTO.setThoigian(ZonedDateTime.now());
            LichSuThaoTacHopDong lichSuThaoTacHopDong = lichSuThaoTacHopDongMapper.toEntity(lichSuThaoTacHopDongDTO);
            lichSuThaoTacHopDong = lichSuThaoTacHopDongRepository.save(lichSuThaoTacHopDong);
            return lichSuThaoTacHopDongMapper.toDto(lichSuThaoTacHopDong);
        }
        throw new InternalError("Khong cos quyen");
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

    @Override
    public List<LichSuThaoTacHopDongDTO> findByHopDong(long idhopdong) {
        List<LichSuThaoTacHopDong> findByHopDong = lichSuThaoTacHopDongRepository.findByHopDong(idhopdong);
        List<LichSuThaoTacHopDongDTO> collect = findByHopDong.stream().map(lichSuThaoTacHopDongMapper::toDto).collect(Collectors.toList());
        return collect;
    }
}
