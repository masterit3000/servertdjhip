package com.tindung.jhip.service.impl;

import com.tindung.jhip.service.VayLaiService;
import com.tindung.jhip.domain.VayLai;
import com.tindung.jhip.domain.enumeration.DONGTIEN;
import com.tindung.jhip.domain.enumeration.HINHTHUCLAI;
import com.tindung.jhip.domain.enumeration.LOAIHOPDONG;
import com.tindung.jhip.domain.enumeration.TINHLAI;
import com.tindung.jhip.repository.BatHoRepository;
import com.tindung.jhip.repository.VayLaiRepository;
import com.tindung.jhip.security.AuthoritiesConstants;
import com.tindung.jhip.security.SecurityUtils;
import com.tindung.jhip.service.CuaHangService;
import com.tindung.jhip.service.HopDongService;
import com.tindung.jhip.service.NhanVienService;
import com.tindung.jhip.service.dto.HopDongDTO;
import com.tindung.jhip.service.dto.LichSuDongTienDTO;
import com.tindung.jhip.service.dto.NhanVienDTO;
import com.tindung.jhip.service.dto.VayLaiDTO;
import com.tindung.jhip.service.mapper.VayLaiMapper;
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
 * Service Implementation for managing VayLai.
 */
@Service
@Transactional
public class VayLaiServiceImpl implements VayLaiService {

    private final Logger log = LoggerFactory.getLogger(VayLaiServiceImpl.class);

    private final VayLaiRepository vayLaiRepository;

    private final VayLaiMapper vayLaiMapper;

    private final HopDongService hopDongService;

    private final NhanVienService nhanVienService;
    private final CuaHangService cuaHangService;

    public VayLaiServiceImpl() {
        this.vayLaiRepository = null;
        this.vayLaiMapper = null;
        this.hopDongService = null;
        this.nhanVienService = null;
        this.cuaHangService = null;
    }

    /**
     * Save a vayLai.
     *
     * @param vayLaiDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VayLaiDTO save(VayLaiDTO vayLaiDTO) {
        log.debug("Request to save VayLai : {}", vayLaiDTO);
//        String login = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            if (vayLaiDTO.getId() == null) {
                HopDongDTO hopdong = vayLaiDTO.getHopdongvl();
                hopdong.setLoaihopdong(LOAIHOPDONG.VAYLAI);
                NhanVienDTO findByUserLogin = nhanVienService.findByUserLogin();
                hopdong.setNhanVienId(findByUserLogin.getId());
                hopdong.setNgaytao(ZonedDateTime.now());
                if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
                    Long idCuaHang = cuaHangService.findIDByUserLogin();
                    hopdong.setCuaHangId(idCuaHang);
                }
                hopdong = hopDongService.save(hopdong);
                vayLaiDTO.setHopdongvl(hopdong);
                Integer chukylai = vayLaiDTO.getChukylai();
                TINHLAI cachtinhlai = vayLaiDTO.getCachtinhlai();
                HINHTHUCLAI hinhthuclai = vayLaiDTO.getHinhthuclai();
                Integer thoigianvay = vayLaiDTO.getThoigianvay();
                Boolean thulaitruoc = vayLaiDTO.getThulaitruoc();
                Double tienvay = vayLaiDTO.getTienvay();
                LichSuDongTienDTO lichSuDongTienDTO = new LichSuDongTienDTO();
                lichSuDongTienDTO.setHopDongId(hopdong.getId());
                lichSuDongTienDTO.setNhanVienId(nhanVienService.findByUserLogin().getId());
                lichSuDongTienDTO.setTrangthai(DONGTIEN.CHUADONG);

                ZonedDateTime ngaybatdau = null;
                ZonedDateTime ngayketthuc = null;
                double soTien = 0f;
                switch (hinhthuclai) {
                    case NAM:
                        switch (cachtinhlai) {
                            case CHUKY:
                                break;
                            case MOTTRIEU:
                                break;
                            case PHANTRAM:
                                break;
                        }
                        break;
                    case THANG:
                        switch (cachtinhlai) {
                            case CHUKY:
                                break;
                            case MOTTRIEU:
                                break;
                            case PHANTRAM:
                                break;
                        }
                        break;
                    case THANGCODINH:
                        switch (cachtinhlai) {
                            case CHUKY:
                                break;
                            case MOTTRIEU:
                                break;
                            case PHANTRAM:
                                break;
                        }
                        break;
                    case TUAN:
                        switch (cachtinhlai) {
                            case CHUKY:
                                break;
                            case MOTTRIEU:
                                break;
                            case PHANTRAM:
                                break;
                        }
                        break;
                    case NGAY:
                        switch (cachtinhlai) {
                            case CHUKY:
                                break;
                            case MOTTRIEU:
                                double trieu = tienvay / 1000000D;
                                break;
                            case PHANTRAM:
                                break;
                        }
                        break;
                }

                VayLai vayLai = vayLaiMapper.toEntity(vayLaiDTO);
                vayLai = vayLaiRepository.save(vayLai);
                return vayLaiMapper.toDto(vayLai);
            }
        }
        throw new InternalError("Khong co quyen");
    }

    /**
     * Get all the vayLais.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<VayLaiDTO> findAll() {
        log.debug("Request to get all VayLais");
//        String login = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            LinkedList<VayLaiDTO> collect = vayLaiRepository.findAll().stream()
                    .map(vayLaiMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            return collect;
        } else {
            NhanVienDTO nhanVien = nhanVienService.findByUserLogin();
            Long cuaHangId = nhanVien.getCuaHangId();
            LinkedList<VayLaiDTO> collect = vayLaiRepository.findAllByCuaHang(cuaHangId).stream()
                    .map(vayLaiMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            return collect;

        }
//        return vayLaiRepository.findAll().stream()
//                .map(vayLaiMapper::toDto)
//                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one vayLai by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public VayLaiDTO findOne(Long id
    ) {
        log.debug("Request to get VayLai : {}", id);
        VayLai vayLai = vayLaiRepository.findOne(id);
        return vayLaiMapper.toDto(vayLai);
    }

    /**
     * Delete the vayLai by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id
    ) {
        log.debug("Request to delete VayLai : {}", id);
        if ((SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN))) {
//            String login = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
            NhanVienDTO nhanVien = nhanVienService.findByUserLogin();
            Long cuaHangId = nhanVien.getCuaHangId();
            VayLaiDTO findOne = findOne(id);
            if (findOne.getHopdongvl().getCuaHangId() == cuaHangId) {
                vayLaiRepository.delete(id);
                hopDongService.delete(findOne.getHopdongvl().getId());
                return;
            }
        }
        throw new InternalServerErrorException("Khong co quyen");

    }
}
