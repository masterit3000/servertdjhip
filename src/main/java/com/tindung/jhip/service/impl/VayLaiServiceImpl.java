package com.tindung.jhip.service.impl;

import com.tindung.jhip.domain.LichSuDongTien;
import com.tindung.jhip.service.VayLaiService;
import com.tindung.jhip.domain.VayLai;
import com.tindung.jhip.domain.enumeration.DONGTIEN;
import com.tindung.jhip.domain.enumeration.HINHTHUCLAI;
import com.tindung.jhip.domain.enumeration.LOAIHOPDONG;
import com.tindung.jhip.domain.enumeration.TINHLAI;
import com.tindung.jhip.repository.LichSuDongTienRepository;
import com.tindung.jhip.repository.VayLaiRepository;
import com.tindung.jhip.security.AuthoritiesConstants;
import com.tindung.jhip.security.SecurityUtils;
import com.tindung.jhip.service.CuaHangService;
import com.tindung.jhip.service.HopDongService;
import com.tindung.jhip.service.LichSuDongTienService;
import com.tindung.jhip.service.LichSuThaoTacHopDongService;
import com.tindung.jhip.service.NhanVienService;
import com.tindung.jhip.service.dto.HopDongDTO;
import com.tindung.jhip.service.dto.LichSuDongTienDTO;
import com.tindung.jhip.service.dto.LichSuThaoTacHopDongDTO;
import com.tindung.jhip.service.dto.NhanVienDTO;
import com.tindung.jhip.service.dto.VayLaiDTO;
import com.tindung.jhip.service.mapper.LichSuDongTienMapper;
import com.tindung.jhip.service.mapper.VayLaiMapper;
import com.tindung.jhip.web.rest.errors.InternalServerErrorException;
import java.time.ZonedDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
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
    private final LichSuDongTienService lichSuDongTienService;
    private final LichSuDongTienRepository lichSuDongTienRepository;
    private final LichSuDongTienMapper lichSuDongTienMapper;
    private final LichSuThaoTacHopDongService lichSuThaoTacHopDongService;

    public VayLaiServiceImpl(LichSuThaoTacHopDongService lichSuThaoTacHopDongService, VayLaiRepository vayLaiRepository, VayLaiMapper vayLaiMapper, HopDongService hopDongService, NhanVienService nhanVienService, CuaHangService cuaHangService, LichSuDongTienService lichSuDongTienService, LichSuDongTienRepository lichSuDongTienRepository, LichSuDongTienMapper lichSuDongTienMapper) {
        this.vayLaiRepository = vayLaiRepository;
        this.vayLaiMapper = vayLaiMapper;
        this.hopDongService = hopDongService;
        this.nhanVienService = nhanVienService;
        this.cuaHangService = cuaHangService;
        this.lichSuDongTienService = lichSuDongTienService;
        this.lichSuDongTienRepository = lichSuDongTienRepository;
        this.lichSuDongTienMapper = lichSuDongTienMapper;
        this.lichSuThaoTacHopDongService = lichSuThaoTacHopDongService;
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
            if (vayLaiDTO.getId() == null) {// add new vay lai
                HopDongDTO hopdong = vayLaiDTO.getHopdongvl();
                hopdong.setLoaihopdong(LOAIHOPDONG.VAYLAI);
                NhanVienDTO nhanVien = nhanVienService.findByUserLogin();

                hopdong.setNhanVienId(nhanVien.getId());
                if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
                    Long idCuaHang = cuaHangService.findIDByUserLogin();
                    hopdong.setCuaHangId(idCuaHang);
                }
                hopdong.setNgaytao(ZonedDateTime.now());
                hopdong = hopDongService.save(hopdong);
                vayLaiDTO.setHopdongvl(hopdong);
                VayLai vayLai = vayLaiMapper.toEntity(vayLaiDTO);
                vayLai = vayLaiRepository.save(vayLai);
                // lay du lieu nhan ve
                Double tongTienVay = vayLai.getTienvay();
                HINHTHUCLAI hinhThucLai = vayLai.getHinhthuclai();
                Boolean thuLaiTruoc = vayLai.isThulaitruoc();
                Float lai = vayLai.getLai();
                TINHLAI cachTinhLai = vayLai.getCachtinhlai();
                Integer soNgayVay = vayLai.getThoigianvay();
                Integer kyLai = vayLai.getChukylai();
                ZonedDateTime ngayVay = hopdong.getNgaytao();
                double tienTrongChuKi;
                if (cachTinhLai.equals(TINHLAI.MOTTRIEU)) {
                    tienTrongChuKi = lai * (tongTienVay / 1000000) * kyLai;
                } else {
                    tienTrongChuKi = lai * kyLai;
                }
                // xu li du lieu nhan ve
                int day = 0;
                ZonedDateTime batdau = ngayVay;
                int soChuKy = soNgayVay / kyLai;
                if (soNgayVay % kyLai != 0) {
                    soChuKy++;
                }

                long soTienTrongChuKy = Math.round(tienTrongChuKi * 1000) / 1000;//lam tron den 1000d
                for (int i = 0; i < soChuKy - 1; i++) {
                    LichSuDongTienDTO lichSuDongTienDTO = new LichSuDongTienDTO();
                    lichSuDongTienDTO.setHopDongId(hopdong.getId());
                    lichSuDongTienDTO.setNhanVienId(nhanVienService.findByUserLogin().getId());
                    lichSuDongTienDTO.setNgaybatdau(batdau);
                    batdau = batdau.plusDays(kyLai);
                    lichSuDongTienDTO.setNgayketthuc(batdau);
                    lichSuDongTienDTO.setSotien(soTienTrongChuKy * 1d);
                    day += kyLai;
                    lichSuDongTienDTO.setTrangthai(DONGTIEN.CHUADONG);
                    lichSuDongTienService.save(lichSuDongTienDTO);
                }
                //phat cuoi
                LichSuDongTienDTO lichSuDongTienDTO = new LichSuDongTienDTO();
                lichSuDongTienDTO.setHopDongId(hopdong.getId());
                lichSuDongTienDTO.setNhanVienId(nhanVienService.findByUserLogin().getId());
                lichSuDongTienDTO.setNgaybatdau(batdau);
                batdau = ngayVay.plusDays(soNgayVay);
                lichSuDongTienDTO.setNgayketthuc(batdau);
                lichSuDongTienDTO.setSotien(soTienTrongChuKy * 1d);
                lichSuDongTienDTO.setTrangthai(DONGTIEN.CHUADONG);
                lichSuDongTienService.save(lichSuDongTienDTO);

                return vayLaiMapper.toDto(vayLai);

            } else {

                HopDongDTO hopdong = vayLaiDTO.getHopdongvl();
                hopdong.setLoaihopdong(LOAIHOPDONG.VAYLAI);
                NhanVienDTO nhanVien = nhanVienService.findByUserLogin();

                hopdong.setNhanVienId(nhanVien.getId());
                if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
                    Long idCuaHang = cuaHangService.findIDByUserLogin();
                    hopdong.setCuaHangId(idCuaHang);
                }
                hopdong.setNgaytao(ZonedDateTime.now());
                hopdong = hopDongService.save(hopdong);
                vayLaiDTO.setHopdongvl(hopdong);
                VayLai vayLai = vayLaiMapper.toEntity(vayLaiDTO);
                vayLai = vayLaiRepository.save(vayLai);
                // lay du lieu nhan ve
                Double tongTienVay = vayLai.getTienvay();
                HINHTHUCLAI hinhThucLai = vayLai.getHinhthuclai();
                Boolean thuLaiTruoc = vayLai.isThulaitruoc();
                Float lai = vayLai.getLai();
                TINHLAI cachTinhLai = vayLai.getCachtinhlai();
                Integer soNgayVay = vayLai.getThoigianvay();
                Integer kyLai = vayLai.getChukylai();
                ZonedDateTime ngayVay = hopdong.getNgaytao();
                int chuKyDaDong = 0;
                int soNgayDaDong =0;
                List<LichSuDongTienDTO> LSDT = lichSuDongTienService.findByHopDong(hopdong.getId());
                for (LichSuDongTienDTO lichSuDongTienDTO : LSDT) {
                    if (lichSuDongTienDTO.getTrangthai().equals(DONGTIEN.DADONG)) {
                        chuKyDaDong++;
                        soNgayDaDong++;
                    }else if (lichSuDongTienDTO.getTrangthai().equals(DONGTIEN.CHUADONG)) {
                        lichSuDongTienService.delete(lichSuDongTienDTO.getId());
                    }
                }
                vayLai.setThoigianvay(chuKyDaDong*kyLai);
                double tienTrongChuKi;
                if (cachTinhLai.equals(TINHLAI.MOTTRIEU)) {
                    tienTrongChuKi = lai * (tongTienVay / 1000000) * kyLai;
                } else {
                    tienTrongChuKi = lai * kyLai - chuKyDaDong;
                }
                // xu li du lieu nhan ve
                int day = 0;
                ZonedDateTime batdau = ngayVay;
                int soChuKy = (soNgayVay-chuKyDaDong*kyLai) / kyLai;
                if ((soNgayVay-chuKyDaDong*kyLai) % kyLai != 0) {
                    soChuKy++;
                }

                long soTienTrongChuKy = Math.round(tienTrongChuKi * 1000) / 1000;//lam tron den 1000d
                for (int i = 0; i < soChuKy - 1; i++) {
                    LichSuDongTienDTO lichSuDongTienDTO = new LichSuDongTienDTO();
                    lichSuDongTienDTO.setHopDongId(hopdong.getId());
                    lichSuDongTienDTO.setNhanVienId(nhanVienService.findByUserLogin().getId());
                    lichSuDongTienDTO.setNgaybatdau(batdau);
                    batdau = batdau.plusDays(kyLai);
                    lichSuDongTienDTO.setNgayketthuc(batdau);
                    lichSuDongTienDTO.setSotien(soTienTrongChuKy * 1d);
                    day += kyLai;
                    lichSuDongTienDTO.setTrangthai(DONGTIEN.CHUADONG);
                    lichSuDongTienService.save(lichSuDongTienDTO);
                }
                //phat cuoi
                LichSuDongTienDTO lichSuDongTienDTO = new LichSuDongTienDTO();
                lichSuDongTienDTO.setHopDongId(hopdong.getId());
                lichSuDongTienDTO.setNhanVienId(nhanVienService.findByUserLogin().getId());
                lichSuDongTienDTO.setNgaybatdau(batdau);
                batdau = ngayVay.plusDays(soNgayVay);
                lichSuDongTienDTO.setNgayketthuc(batdau);
                lichSuDongTienDTO.setSotien(soTienTrongChuKy * 1d);
                lichSuDongTienDTO.setTrangthai(DONGTIEN.CHUADONG);
                lichSuDongTienService.save(lichSuDongTienDTO);

                return vayLaiMapper.toDto(vayLai);
            }

        }
        throw new InternalServerErrorException("Khong co quyen");

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
        String login = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
        VayLai vayLai = null;
        vayLai = vayLaiRepository.findOne(id);
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            return vayLaiMapper.toDto(vayLai);

        } else {
            Long idCuaHang = cuaHangService.findIDByUserLogin();
            if (vayLai.getHopdongvl().getCuaHang().getId() == idCuaHang) {
                return vayLaiMapper.toDto(vayLai);
            }
            return null;

        }
    }

    @Override
    public List<VayLaiDTO> findByNameOrCMND(String key) {
        log.debug("Request to get all KhachHangs");
        key = new StringBuffer("%").append(key).append("%").toString();

        return vayLaiRepository.findByNameOrCMND(key).stream()
                .map(vayLaiMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
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
