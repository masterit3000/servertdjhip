package com.tindung.jhip.service.impl;

import com.tindung.jhip.service.ThuChiService;
import com.tindung.jhip.domain.ThuChi;
import com.tindung.jhip.domain.enumeration.THUCHI;
import com.tindung.jhip.repository.ThuChiRepository;
import com.tindung.jhip.security.AuthoritiesConstants;
import com.tindung.jhip.security.SecurityUtils;
import com.tindung.jhip.service.BatHoService;
import com.tindung.jhip.service.CuaHangService;
import com.tindung.jhip.service.NhatKyService;
import com.tindung.jhip.service.dto.NhatKyDTO;
import com.tindung.jhip.service.dto.ThuChiDTO;
import com.tindung.jhip.service.mapper.ThuChiMapper;
import com.tindung.jhip.web.rest.errors.BadRequestAlertException;
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
 * Service Implementation for managing ThuChi.
 */
@Service
@Transactional
public class ThuChiServiceImpl implements ThuChiService {

    private final Logger log = LoggerFactory.getLogger(ThuChiServiceImpl.class);

    private final ThuChiRepository thuChiRepository;

    private final ThuChiMapper thuChiMapper;
    private final CuaHangService cuaHangService;
    private final BatHoService batHoService;
    private final NhatKyService nhatKyService;

    public ThuChiServiceImpl(ThuChiRepository thuChiRepository, ThuChiMapper thuChiMapper,
            CuaHangService cuaHangService, BatHoService batHoService, NhatKyService nhatKyService) {
        this.thuChiRepository = thuChiRepository;
        this.thuChiMapper = thuChiMapper;
        this.cuaHangService = cuaHangService;
        this.batHoService = batHoService;
        this.nhatKyService = nhatKyService;
    }

    /**
     * Save a thuChi.
     *
     * @param thuChiDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ThuChiDTO save(ThuChiDTO thuChiDTO) {
        log.debug("Request to save ThuChi : {}", thuChiDTO);
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            if (((thuChiDTO.getThuchi().equals(THUCHI.CHI) || thuChiDTO.getThuchi().equals(THUCHI.RUTVON))
                    && thuChiDTO.getSotien() <= batHoService.quanLyVon())
                    || (thuChiDTO.getThuchi().equals(THUCHI.THU) || thuChiDTO.getThuchi().equals(THUCHI.GOPVON))) {
                if (thuChiDTO.getId() == null) {// them mo
                    thuChiDTO.setThoigian(ZonedDateTime.now());
                    thuChiDTO.setCuaHangId(cuaHangService.findIDByUserLogin());
                    ThuChi thuChi = thuChiMapper.toEntity(thuChiDTO);
                    // thuChi.setThoigian(ZonedDateTime.now());

                    NhatKyDTO nhatKy = new NhatKyDTO();
                    if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
                        nhatKy.setCuaHangId(cuaHangService.findIDByUserLogin());
                    }
                    String login = SecurityUtils.getCurrentUserLogin()
                            .orElseThrow(() -> new InternalServerErrorException("Current user login not found"));

                    nhatKy.setNhanVienId(thuChiDTO.getNhanVienId());
                    nhatKy.setThoiGian(ZonedDateTime.now());
                    nhatKy.setNoiDung("Thêm mới thu chi");
                    nhatKyService.save(nhatKy);

                    thuChi = thuChiRepository.save(thuChi);
                    return thuChiMapper.toDto(thuChi);

                }
                throw new InternalError("Khong sua duoc thu chi");

            } else {
                throw new BadRequestAlertException("Không đủ tiền", null, null);
            }
        }
        throw new InternalError("Khong cos quyen");

    }

    @Override
    public ThuChiDTO saveKeToan(ThuChiDTO thuChiDTO, Long idcuahang) {
        log.debug("Request to save ThuChi : {}", thuChiDTO);
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.KETOAN)) {
            if (((thuChiDTO.getThuchi().equals(THUCHI.CHI) || thuChiDTO.getThuchi().equals(THUCHI.RUTVON))
                    && thuChiDTO.getSotien() <= batHoService.quanLyVonKeToan(idcuahang))
                    || (thuChiDTO.getThuchi().equals(THUCHI.THU) || thuChiDTO.getThuchi().equals(THUCHI.GOPVON))) {
                if (thuChiDTO.getId() == null) {// them mo
                    thuChiDTO.setThoigian(ZonedDateTime.now());
                    thuChiDTO.setCuaHangId(idcuahang);
                    ThuChi thuChi = thuChiMapper.toEntity(thuChiDTO);
                    // thuChi.setThoigian(ZonedDateTime.now());

                    NhatKyDTO nhatKy = new NhatKyDTO();
                    if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
                        nhatKy.setCuaHangId(idcuahang);
                    }
                    String login = SecurityUtils.getCurrentUserLogin()
                            .orElseThrow(() -> new InternalServerErrorException("Current user login not found"));

                    nhatKy.setThoiGian(ZonedDateTime.now());
                    nhatKy.setNoiDung("Thêm mới thu chi bởi kế toán " + login);
                    nhatKyService.save(nhatKy);

                    thuChi = thuChiRepository.save(thuChi);
                    return thuChiMapper.toDto(thuChi);

                }
                throw new InternalError("Khong sua duoc thu chi");

            } else {
                throw new BadRequestAlertException("Không đủ tiền", null, null);
            }
        }
        throw new InternalError("Khong cos quyen");

    }

    /**
     * Get all the thuChis.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ThuChiDTO> findAll() {
        log.debug("Request to get all ThuChis");
        return thuChiRepository.findAll().stream().map(thuChiMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one thuChi by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ThuChiDTO findOne(Long id) {
        log.debug("Request to get ThuChi : {}", id);
        ThuChi thuChi = thuChiRepository.findOne(id);
        return thuChiMapper.toDto(thuChi);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ThuChiDTO> findAllThuTheoLoai(THUCHI thuchi) {
        log.debug("Request to get all ThuChi");
        return thuChiRepository.findAllThuChiTheoLoai(thuchi).stream().map(thuChiMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Delete the thuChi by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ThuChi : {}", id);
        thuChiRepository.delete(id);
    }

    @Override
    public List<ThuChiDTO> findByTime(ZonedDateTime start, ZonedDateTime end) {

        log.debug("Request to find ThuChi : {}", start, end);
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.USER)) {
            Long cuahangID = cuaHangService.findIDByUserLogin();
            List<ThuChi> findbyTime = thuChiRepository.findbyTime(start, end, cuahangID);
            return findbyTime.stream().map(thuChiMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
        }
        throw new InternalError("Khong cos quyen");
    }

    @Override
    public List<ThuChiDTO> findByTime(ZonedDateTime start, ZonedDateTime end, THUCHI thuchi) {

        log.debug("Request to find ThuChi : {}", start, end);
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.USER)) {
            // kiem tra quyền
            Long cuahangID = cuaHangService.findIDByUserLogin();// lây về cửa hang của user hiên tại
            List<ThuChi> findbyTime = thuChiRepository.findbyTime(start, end, thuchi, cuahangID);// goi hàm để lây vê
                                                                                                 // thu chi của cửa hang
                                                                                                 // fhienej tauij
            return findbyTime.stream().map(thuChiMapper::toDto).collect(Collectors.toCollection(LinkedList::new));// convert
                                                                                                                  // sang
                                                                                                                  // DTO
                                                                                                                  // và
                                                                                                                  // trả
                                                                                                                  // vè
        }
        throw new InternalError("Khong cos quyen");
    }

    @Override
    public List<ThuChiDTO> findByTimeKeToan(ZonedDateTime start, ZonedDateTime end, THUCHI thuchi, Long cuahangID) {

        log.debug("Request to find ThuChi : {}", start, end);
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.KETOAN)) {
            // kiem tra quyền
            List<ThuChi> findbyTime = thuChiRepository.findbyTime(start, end, thuchi, cuahangID);// goi hàm để lây vê
                                                                                                 // thu chi của cửa hang
                                                                                                 // fhienej tauij
            return findbyTime.stream().map(thuChiMapper::toDto).collect(Collectors.toCollection(LinkedList::new));// convert
                                                                                                                  // sang
                                                                                                                  // DTO
                                                                                                                  // và
                                                                                                                  // trả
                                                                                                                  // vè
        }
        throw new InternalError("Khong cos quyen");
    }

    @Override
    public List<ThuChiDTO> baoCao(ZonedDateTime start, ZonedDateTime end, Long id) {

        log.debug("Request to find ThuChi : {}", start, end);
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            // kiem tra quyền
            Long cuahangID = cuaHangService.findIDByUserLogin();// lây về cửa hang của user hiên tại
            List<ThuChi> findbyTime = thuChiRepository.baoCao(start, end, cuahangID, id);// goi hàm để lây vê thu chi
                                                                                         // của cửa hang fhienej tauij
            return findbyTime.stream().map(thuChiMapper::toDto).collect(Collectors.toCollection(LinkedList::new));// convert
                                                                                                                  // sang
                                                                                                                  // DTO
                                                                                                                  // và
                                                                                                                  // trả
                                                                                                                  // vè
        }
        throw new InternalError("Khong cos quyen");
    }

    @Override
    public List<ThuChiDTO> baoCaoKeToan(ZonedDateTime start, ZonedDateTime end, Long cuahangID) {

        log.debug("Request to find ThuChi : {}", start, end);
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.KETOAN)) {
            List<ThuChi> findbyTime = thuChiRepository.findbyTime(start, end, cuahangID);
            return findbyTime.stream().map(thuChiMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
        }
        throw new InternalError("Khong cos quyen");
    }

}
