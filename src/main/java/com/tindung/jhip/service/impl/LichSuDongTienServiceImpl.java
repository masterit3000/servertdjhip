package com.tindung.jhip.service.impl;

import com.tindung.jhip.domain.GhiNo;
import com.tindung.jhip.service.LichSuDongTienService;
import com.tindung.jhip.domain.LichSuDongTien;
import com.tindung.jhip.domain.enumeration.DONGTIEN;
import com.tindung.jhip.domain.enumeration.NOTRA;
import com.tindung.jhip.repository.BatHoRepository;
import com.tindung.jhip.repository.HopDongRepository;
import com.tindung.jhip.repository.LichSuDongTienRepository;
import com.tindung.jhip.security.AuthoritiesConstants;
import com.tindung.jhip.security.SecurityUtils;
import com.tindung.jhip.service.dto.LichSuDongTienDTO;
import com.tindung.jhip.service.mapper.LichSuDongTienMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tindung.jhip.service.CuaHangService;
import com.tindung.jhip.service.NhanVienService;
import com.tindung.jhip.web.rest.errors.InternalServerErrorException;
import java.time.ZonedDateTime;
import com.tindung.jhip.service.GhiNoService;
import com.tindung.jhip.service.dto.GhiNoDTO;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Service Implementation for managing LichSuDongTien.
 */
@Service
@Transactional
public class LichSuDongTienServiceImpl implements LichSuDongTienService {

    private final Logger log = LoggerFactory.getLogger(LichSuDongTienServiceImpl.class);

    private final LichSuDongTienRepository lichSuDongTienRepository;

    private final LichSuDongTienMapper lichSuDongTienMapper;
    private final GhiNoService ghiNoService;
    private final HopDongRepository hopDongRepository;
    private final NhanVienService nhanVienService;
//    @Autowired
    private final CuaHangService cuaHangService;

    public LichSuDongTienServiceImpl(LichSuDongTienRepository lichSuDongTienRepository, LichSuDongTienMapper lichSuDongTienMapper, GhiNoService ghiNoService, HopDongRepository hopDongRepository, NhanVienService nhanVienService, CuaHangService cuaHangService) {
        this.lichSuDongTienRepository = lichSuDongTienRepository;
        this.lichSuDongTienMapper = lichSuDongTienMapper;
        this.ghiNoService = ghiNoService;
        this.hopDongRepository = hopDongRepository;
        this.nhanVienService = nhanVienService;
        this.cuaHangService = cuaHangService;
    }

    /**
     * Save a lichSuDongTien.
     *
     * @param lichSuDongTienDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LichSuDongTienDTO save(LichSuDongTienDTO lichSuDongTienDTO) {
        log.debug("Request to save LichSuDongTien : {}", lichSuDongTienDTO);
        LichSuDongTien lichSuDongTien = lichSuDongTienMapper.toEntity(lichSuDongTienDTO);
        lichSuDongTien = lichSuDongTienRepository.save(lichSuDongTien);
        return lichSuDongTienMapper.toDto(lichSuDongTien);
    }

    /**
     * Get all the lichSuDongTiens.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LichSuDongTienDTO> findAll() {
        log.debug("Request to get all LichSuDongTiens");
        return lichSuDongTienRepository.findAll().stream()
                .map(lichSuDongTienMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one lichSuDongTien by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LichSuDongTienDTO findOne(Long id) {
        log.debug("Request to get LichSuDongTien : {}", id);
        LichSuDongTien lichSuDongTien = lichSuDongTienRepository.findOne(id);
        return lichSuDongTienMapper.toDto(lichSuDongTien);
    }

    /**
     * Delete the lichSuDongTien by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LichSuDongTien : {}", id);
        lichSuDongTienRepository.delete(id);
    }

    @Override
    public LichSuDongTienDTO setDongTien(Long id) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            LichSuDongTien lichSuDongTien = lichSuDongTienRepository.findOne(id);
            lichSuDongTien.setTrangthai(DONGTIEN.DADONG);
            return lichSuDongTienMapper.toDto(lichSuDongTien);
        }
        throw new InternalServerErrorException("Khong co quyen");
    }

    @Override
    @Transactional(readOnly = true)
    public List<LichSuDongTienDTO> findByHopDong(Long id) {

        List<LichSuDongTien> findByHopDong = lichSuDongTienRepository.findByHopDong(id);
        List<LichSuDongTienDTO> collect = findByHopDong.stream()
                .map(lichSuDongTienMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
        return collect;

    }

    @Override
    public void dongHopDong(Long id) {
        List<LichSuDongTien> findByHopDong = lichSuDongTienRepository.findByHopDong(id);
        double tienConPhaiDong=0;
        for (LichSuDongTien lichSuDongTien : findByHopDong) {
            if(lichSuDongTien.getTrangthai().equals(DONGTIEN.CHUADONG)){
                tienConPhaiDong += lichSuDongTien.getSotien();
                lichSuDongTienRepository.delete(lichSuDongTien);
            }
        }
        List<GhiNoDTO> ghiNos = ghiNoService.findByHopDong(id);
        for (GhiNoDTO ghiNo : ghiNos) {
            if(ghiNo.getTrangthai().equals(NOTRA.NO)){
                tienConPhaiDong = tienConPhaiDong + ghiNo.getSotien();
            }else if(ghiNo.getTrangthai().equals(NOTRA.TRA)){
                tienConPhaiDong =  tienConPhaiDong  -ghiNo.getSotien();
            }
        }
        LichSuDongTienDTO lichSuDongTienDTO = new LichSuDongTienDTO();
        lichSuDongTienDTO.setHopDongId(id);
        lichSuDongTienDTO.setNgaybatdau(ZonedDateTime.now());
        lichSuDongTienDTO.setNgayketthuc(ZonedDateTime.now());
        lichSuDongTienDTO.setSotien(tienConPhaiDong);
        lichSuDongTienDTO.setNhanVienId(nhanVienService.findByUserLogin().getId());
        lichSuDongTienDTO.setTrangthai(DONGTIEN.DADONG);
        LichSuDongTien lichSuDongTien = lichSuDongTienMapper.toEntity(lichSuDongTienDTO);
        lichSuDongTienRepository.save(lichSuDongTien);
    }

}
