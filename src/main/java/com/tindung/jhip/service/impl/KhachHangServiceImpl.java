package com.tindung.jhip.service.impl;

import com.tindung.jhip.service.KhachHangService;
import com.tindung.jhip.domain.KhachHang;
import com.tindung.jhip.domain.enumeration.StatusKhachHang;
import com.tindung.jhip.domain.enumeration.TrangThaiKhachHang;
import com.tindung.jhip.repository.KhachHangRepository;
import com.tindung.jhip.security.AuthoritiesConstants;
import com.tindung.jhip.security.SecurityUtils;
import com.tindung.jhip.service.CuaHangService;
import com.tindung.jhip.service.NhanVienService;
import com.tindung.jhip.service.NhatKyService;
import com.tindung.jhip.service.dto.KhachHangDTO;
import com.tindung.jhip.service.dto.NhanVienDTO;
import com.tindung.jhip.service.dto.NhatKyDTO;
import com.tindung.jhip.service.mapper.KhachHangMapper;
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
 * Service Implementation for managing KhachHang.
 */
@Service
@Transactional
public class KhachHangServiceImpl implements KhachHangService {

    private final Logger log = LoggerFactory.getLogger(KhachHangServiceImpl.class);

    private final KhachHangRepository khachHangRepository;

    private final KhachHangMapper khachHangMapper;
    private final CuaHangService cuaHangService;
    private final NhanVienService nhanVienService;
    private final NhatKyService nhatKyService;

    public KhachHangServiceImpl(KhachHangRepository khachHangRepository, KhachHangMapper khachHangMapper, CuaHangService cuaHangService, NhanVienService nhanVienService, NhatKyService nhatKyService) {
        this.khachHangRepository = khachHangRepository;
        this.khachHangMapper = khachHangMapper;
        this.cuaHangService = cuaHangService;
        this.nhanVienService = nhanVienService;
        this.nhatKyService = nhatKyService;
    }

    /**
     * Save a khachHang.
     *
     * @param khachHangDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public KhachHangDTO save(KhachHangDTO khachHangDTO) {
        log.debug("Request to save KhachHang : {}", khachHangDTO);
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {

            Long idCuaHang = cuaHangService.findIDByUserLogin();
            khachHangDTO.setCuaHangId(idCuaHang);

            KhachHang khachHang = khachHangMapper.toEntity(khachHangDTO);
            khachHang = khachHangRepository.saveAndFlush(khachHang);

            NhatKyDTO nhatKy = new NhatKyDTO();
            if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
                nhatKy.setCuaHangId(cuaHangService.findIDByUserLogin());
            }
            nhatKy.setNhanVienId(nhanVienService.findByUserLogin().getId());
            nhatKy.setThoiGian(ZonedDateTime.now());
            nhatKy.setNoiDung("Thêm mới khách hàng");
            nhatKyService.save(nhatKy);
            setStatus(khachHang.getId(), StatusKhachHang.DUNGHOATDONG);
            return khachHangMapper.toDto(khachHang);
        }

        throw new InternalError("Khong co quyen them khach hang");
    }

    @Override
    public KhachHangDTO saveforAdmin(KhachHangDTO khachHangDTO) {
        log.debug("Request to save KhachHang : {}", khachHangDTO);
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
                Long idCuaHang = cuaHangService.findIDByUserLogin();
                khachHangDTO.setCuaHangId(idCuaHang);
            }

            NhatKyDTO nhatKy = new NhatKyDTO();
            if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
                nhatKy.setCuaHangId(cuaHangService.findIDByUserLogin());
            }
            nhatKy.setNhanVienId(nhanVienService.findByUserLogin().getId());
            nhatKy.setThoiGian(ZonedDateTime.now());
            nhatKy.setNoiDung("Thêm mới khách hàng");
            nhatKyService.save(nhatKy);
            KhachHang khachHang = khachHangMapper.toEntity(khachHangDTO);
            khachHang = khachHangRepository.saveAndFlush(khachHang);
            setStatus(khachHang.getId(), StatusKhachHang.DUNGHOATDONG);
            return khachHangMapper.toDto(khachHang);
        }

        throw new InternalError("Khong co quyen them khach hang");
    }

    /**
     * Get all the khachHangs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<KhachHangDTO> findAll() {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            log.debug("Request to get all KhachHangs");
            return khachHangRepository.findAll().stream()
                    .map(khachHangMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
        } else {
            Long cuaHangid = cuaHangService.findIDByUserLogin();
            return khachHangRepository.findAllByCuaHAng(cuaHangid).stream()
                    .map(khachHangMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<KhachHangDTO> findAllByCuaHang(Long id) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)||SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.KETOAN)) {
            log.debug("Request to get all KhachHangs");
            return khachHangRepository.findAllByCuaHAng(id).stream()
                    .map(khachHangMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
        }
        throw new InternalServerErrorException("Khong co quyen");
    }

    /**
     * Get one khachHang by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public KhachHangDTO findOne(Long id
    ) {
        log.debug("Request to get KhachHang : {}", id);
        KhachHang khachHang = khachHangRepository.findOne(id);
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {

            return khachHangMapper.toDto(khachHang);
        } else {
            Long idCuaHang = cuaHangService.findIDByUserLogin();
            if (khachHang.getCuaHang().getId() == idCuaHang) {
                return khachHangMapper.toDto(khachHang);
            } else {
                return null;
            }
        }
    }

    /**
     * Delete the khachHang by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id
    ) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            log.debug("Request to delete KhachHang : {}", id);
            NhanVienDTO nhanVien = nhanVienService.findByUserLogin();
            Long cuaHangId = nhanVien.getCuaHangId();
            if (findOne(id).getCuaHangId() == cuaHangId) {
                khachHangRepository.delete(id);
            }
        }
        throw new InternalServerErrorException("Khong co quyen");
    }

    @Override
    public List<KhachHangDTO> findByNameOrCMND(String key
    ) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            log.debug("Request to get all KhachHangs");
            key = new StringBuffer("%").append(key).append("%").toString();

            return khachHangRepository.findByNameOrCMND(key).stream()
                    .map(khachHangMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            log.debug("Request to get all KhachHangs");
            key = new StringBuffer("%").append(key).append("%").toString();
            NhanVienDTO nhanVien = nhanVienService.findByUserLogin();
            Long cuaHangId = nhanVien.getCuaHangId();
            return khachHangRepository.findByNameOrCMNDAdmin(key, cuaHangId).stream()
                    .map(khachHangMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
        }
        throw new InternalServerErrorException("Khong co quyen");
    }

    public List<KhachHangDTO> findInSystem(String key) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            log.debug("Request to get all KhachHangs");
            key = new StringBuffer("%").append(key).append("%").toString();

            return khachHangRepository.findInSystem(key).stream()
                    .map(khachHangMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            log.debug("Request to get all KhachHangs");
            key = new StringBuffer("%").append(key).append("%").toString();
            return khachHangRepository.findInSystem(key).stream()
                    .map(khachHangMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
        }
        throw new InternalServerErrorException("Khong co quyen");
    }

    public void setStatus(Long id, StatusKhachHang status) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            KhachHang khachHang = khachHangRepository.findOne(id);
            khachHang.setStatus(status);
            khachHangRepository.saveAndFlush(khachHang);
        }

    }

    public void setTrangThai(Long id, TrangThaiKhachHang trangthai) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            KhachHang khachHang = khachHangRepository.findOne(id);
            khachHang.setTrangthai(trangthai);

            NhatKyDTO nhatKy = new NhatKyDTO();
            if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
                nhatKy.setCuaHangId(cuaHangService.findIDByUserLogin());
            }
            nhatKy.setNhanVienId(nhanVienService.findByUserLogin().getId());
            nhatKy.setThoiGian(ZonedDateTime.now());
            nhatKy.setNoiDung("Thay đổi trạng thái khách hàng ");
            nhatKyService.save(nhatKy);
            khachHangRepository.saveAndFlush(khachHang);
        }

    }
}
