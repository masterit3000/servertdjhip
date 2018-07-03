package com.tindung.jhip.service.impl;

import com.tindung.jhip.service.BatHoService;
import com.tindung.jhip.domain.BatHo;
import com.tindung.jhip.repository.BatHoRepository;
import com.tindung.jhip.security.AuthoritiesConstants;
import com.tindung.jhip.security.SecurityUtils;
import com.tindung.jhip.service.CuaHangService;
import com.tindung.jhip.service.HopDongService;
import com.tindung.jhip.service.NhanVienService;
import com.tindung.jhip.service.dto.BatHoDTO;
import com.tindung.jhip.service.dto.HopDongDTO;
import com.tindung.jhip.service.dto.NhanVienDTO;
import com.tindung.jhip.service.mapper.BatHoMapper;
import com.tindung.jhip.service.util.UserCuaHangUtil;
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

    private final BatHoMapper batHoMapper;
    private final BatHoRepository batHoRepository;
    private final HopDongService hopDongService;

    private final NhanVienService nhanVienService;
    private final CuaHangService cuaHangService;

    public BatHoServiceImpl(BatHoMapper batHoMapper, BatHoRepository batHoRepository, HopDongService hopDongService, NhanVienService nhanVienService, CuaHangService cuaHangService) {
        this.batHoMapper = batHoMapper;
        this.batHoRepository = batHoRepository;
        this.hopDongService = hopDongService;
        this.nhanVienService = nhanVienService;
        this.cuaHangService = cuaHangService;
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
        Long idCuaHang = cuaHangService.findIDByUserLogin();
        batHoDTO.getHopdong().setCuaHangId(idCuaHang);
        HopDongDTO save = hopDongService.save(batHoDTO.getHopdong());
        batHoDTO.setHopdong(save);
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
            Long idCuaHang = cuaHangService.findIDByUserLogin();
            LinkedList<BatHoDTO> collect = batHoRepository.findAllByCuaHang(idCuaHang).stream()
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
            Long idCuaHang = cuaHangService.findIDByUserLogin();
            if (batHo.getHopdongbh().getCuaHang().getId() == idCuaHang) {
                return batHoMapper.toDto(batHo);
            }
            return null;

        }
    }

    /**
     * Delete the batHo by id.
     *F
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BatHo : {}", id);
        if ((SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN))) {
            Long idCuaHang = cuaHangService.findIDByUserLogin();
            BatHoDTO findOne = findOne(id);
            if (findOne.getHopdong().getCuaHangId() == idCuaHang) {
                batHoRepository.delete(id);
                hopDongService.delete(findOne.getHopdong().getId());
                return;
            }

        }
        throw new InternalServerErrorException("Khong co quyen");
    }
}
