package com.tindung.jhip.service.impl;

import com.tindung.jhip.domain.GhiNo;
import com.tindung.jhip.service.HopDongService;
import com.tindung.jhip.domain.HopDong;
import com.tindung.jhip.domain.enumeration.DONGTIEN;
import com.tindung.jhip.domain.enumeration.LOAIHOPDONG;
import com.tindung.jhip.domain.enumeration.NOTRA;
import com.tindung.jhip.domain.enumeration.TRANGTHAIHOPDONG;
import com.tindung.jhip.repository.GhiNoRepository;
import com.tindung.jhip.repository.HopDongRepository;
import com.tindung.jhip.security.AuthoritiesConstants;
import com.tindung.jhip.security.SecurityUtils;
import com.tindung.jhip.service.CuaHangService;
import com.tindung.jhip.service.dto.HopDongDTO;
import com.tindung.jhip.service.mapper.HopDongMapper;
import com.tindung.jhip.web.rest.errors.InternalServerErrorException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing HopDong.
 */
@Service
@Transactional
public class HopDongServiceImpl implements HopDongService {

    private final Logger log = LoggerFactory.getLogger(HopDongServiceImpl.class);

    private final HopDongRepository hopDongRepository;
    private final GhiNoRepository ghiNoRepository;
    private final HopDongMapper hopDongMapper;
    private final CuaHangService cuaHangService;

    public HopDongServiceImpl(HopDongRepository hopDongRepository, GhiNoRepository ghiNoRepository, HopDongMapper hopDongMapper, CuaHangService cuaHangService) {
        this.hopDongRepository = hopDongRepository;
        this.ghiNoRepository = ghiNoRepository;
        this.hopDongMapper = hopDongMapper;
        this.cuaHangService = cuaHangService;
    }

    /**
     * Save a hopDong.
     *
     * @param hopDongDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public HopDongDTO save(HopDongDTO hopDongDTO) {
        log.debug("Request to save HopDong : {}", hopDongDTO);
        HopDong hopDong = hopDongMapper.toEntity(hopDongDTO);
        hopDong = hopDongRepository.saveAndFlush(hopDong);
        return hopDongMapper.toDto(hopDong);
    }

    /**
     * Get all the hopDongs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HopDongDTO> findAll() {
        log.debug("Request to get all HopDongs");
        return hopDongRepository.findAll().stream()
                .map(hopDongMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<HopDongDTO> thongKe(TRANGTHAIHOPDONG trangthai, LOAIHOPDONG loai) {
        log.debug("Request to get all HopDongs");
        List<HopDong> hopDongs = null;
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
//            List<HopDong> hopDongs = hopDongRepository.thongkeHopDongNo(TRANGTHAIHOPDONG.DADONG, LOAIHOPDONG.BATHO);
//            List<HopDong> hopDongs = hopDongRepository.thongkeHopDongNo(TRANGTHAIHOPDONG.DADONG, LOAIHOPDONG.BATHO);
            if (loai.equals(LOAIHOPDONG.BATHO)) {
                hopDongs = hopDongRepository.thongkeHopDongNoBatHo();
            } else if (loai.equals(LOAIHOPDONG.VAYLAI)) {
                hopDongs = hopDongRepository.thongkeHopDongNoVayLai();
            }

            return hopDongs.stream()
                    .map(hopDongMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            Long idCuaHang = cuaHangService.findIDByUserLogin();
//            List<HopDong> hopDongs = hopDongRepository.thongkeHopDongNo(TRANGTHAIHOPDONG.DADONG, LOAIHOPDONG.BATHO);
            if (loai.equals(LOAIHOPDONG.BATHO)) {
                hopDongs = hopDongRepository.thongkeHopDongNoBatHo();
            } else if (loai.equals(LOAIHOPDONG.VAYLAI)) {
                hopDongs = hopDongRepository.thongkeHopDongNoVayLai();
            }

            return hopDongs.stream()
                    .map(hopDongMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
        }
        throw new InternalServerErrorException("Khong co quyen");

    }

    /**
     * Get one hopDong by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public HopDongDTO findOne(Long id
    ) {
        log.debug("Request to get HopDong : {}", id);
        HopDong hopDong = hopDongRepository.findOne(id);
        return hopDongMapper.toDto(hopDong);
    }

    /**
     * Delete the hopDong by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id
    ) {
        log.debug("Request to delete HopDong : {}", id);
        hopDongRepository.delete(id);
    }

    public List<HopDongDTO> findHopDongTraCham(LOAIHOPDONG loaihopdong) {
        log.debug("Request to get all HopDongs");
        ZonedDateTime ngayhientai = ZonedDateTime.now();
        Long idcuahang = cuaHangService.findIDByUserLogin();
        List<HopDong> dsHopDongTraCham = hopDongRepository.hopDongTraCham(DONGTIEN.CHUADONG, loaihopdong, ngayhientai, idcuahang);
         return dsHopDongTraCham.stream()
                .map(hopDongMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

}
