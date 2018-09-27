package com.tindung.jhip.service.impl;

import com.tindung.jhip.domain.KhachHang;
import com.tindung.jhip.domain.LichSuDongTien;
import com.tindung.jhip.service.VayLaiService;
import com.tindung.jhip.domain.VayLai;
import com.tindung.jhip.domain.enumeration.DONGTIEN;
import com.tindung.jhip.domain.enumeration.HINHTHUCLAI;
import com.tindung.jhip.domain.enumeration.LOAIHOPDONG;
import com.tindung.jhip.domain.enumeration.StatusKhachHang;
import com.tindung.jhip.domain.enumeration.TINHLAI;
import com.tindung.jhip.domain.enumeration.TRANGTHAIHOPDONG;
import com.tindung.jhip.domain.enumeration.TrangThaiKhachHang;
import com.tindung.jhip.repository.HopDongRepository;
import com.tindung.jhip.repository.KhachHangRepository;
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
import com.tindung.jhip.service.BatHoService;
import com.tindung.jhip.service.KhachHangService;
import com.tindung.jhip.service.NhatKyService;
import com.tindung.jhip.service.dto.NhatKyDTO;
import com.tindung.jhip.service.mapper.KhachHangMapper;
import com.tindung.jhip.web.rest.errors.BadRequestAlertException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
    private final KhachHangRepository khachHangRepository;

    private final KhachHangService khachHangService;
    private final BatHoService batHoService;
    private final CuaHangService cuaHangService;
    private final LichSuDongTienService lichSuDongTienService;
    private final LichSuThaoTacHopDongService lichSuThaoTacHopDongService;
    private final NhatKyService nhatKyService;
    private final HopDongRepository hopDongRepository;

    public VayLaiServiceImpl(VayLaiRepository vayLaiRepository, VayLaiMapper vayLaiMapper, HopDongService hopDongService, NhanVienService nhanVienService, KhachHangRepository khachHangRepository, KhachHangService khachHangService, BatHoService batHoService, CuaHangService cuaHangService, LichSuDongTienService lichSuDongTienService, LichSuThaoTacHopDongService lichSuThaoTacHopDongService, NhatKyService nhatKyService, HopDongRepository hopDongRepository) {
        this.vayLaiRepository = vayLaiRepository;
        this.vayLaiMapper = vayLaiMapper;
        this.hopDongService = hopDongService;
        this.nhanVienService = nhanVienService;
        this.khachHangRepository = khachHangRepository;
        this.khachHangService = khachHangService;
        this.batHoService = batHoService;
        this.cuaHangService = cuaHangService;
        this.lichSuDongTienService = lichSuDongTienService;
        this.lichSuThaoTacHopDongService = lichSuThaoTacHopDongService;
        this.nhatKyService = nhatKyService;
        this.hopDongRepository = hopDongRepository;
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
            if (batHoService.quanLyVon() >= vayLaiDTO.getTienvay()) {
                if (vayLaiDTO.getId() == null
                        && khachHangRepository.findOne(vayLaiDTO.getHopdongvl().getKhachHangId()).getStatus().equals(StatusKhachHang.DUNGHOATDONG)) {// add new vay lai
                    HopDongDTO hopdong = vayLaiDTO.getHopdongvl();
                    hopdong.setLoaihopdong(LOAIHOPDONG.VAYLAI);
                    NhanVienDTO nhanVien = nhanVienService.findByUserLogin();

                    hopdong.setNhanVienId(nhanVien.getId());
                    hopdong.setCuaHangId(cuaHangService.findIDByUserLogin());
                    if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
                        Long idCuaHang = cuaHangService.findIDByUserLogin();
                        hopdong.setCuaHangId(idCuaHang);
                    }
                    hopdong.setNgaytao(ZonedDateTime.now());
                    hopdong.setTrangthaihopdong(TRANGTHAIHOPDONG.DANGVAY);
                    hopdong = hopDongService.save(hopdong);
                    vayLaiDTO.setHopdongvl(hopdong);

                    khachHangService.setStatus(hopdong.getKhachHangId(), StatusKhachHang.HOATDONG);// set trang thai khach hang

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

                    long soTienTrongChuKy = Math.round(tienTrongChuKi / 1000) * 1000;//lam tron den 1000d
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

                    LichSuThaoTacHopDongDTO lichSuThaoTacHopDongDTO = new LichSuThaoTacHopDongDTO();
                    lichSuThaoTacHopDongDTO.setHopDongId(hopdong.getId());
                    lichSuThaoTacHopDongDTO.setNhanVienId(nhanVienService.findByUserLogin().getId());
                    lichSuThaoTacHopDongDTO.setNoidung("Tạo mới vay lãi");
                    lichSuThaoTacHopDongDTO.setThoigian(ZonedDateTime.now());
                    lichSuThaoTacHopDongDTO.setSoTienGhiCo(0d);
                    lichSuThaoTacHopDongDTO.setSoTienGhiNo(vayLai.getTienvay());
                    lichSuThaoTacHopDongService.save(lichSuThaoTacHopDongDTO);

                    NhatKyDTO nhatKy = new NhatKyDTO();
                    if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
                        nhatKy.setCuaHangId(cuaHangService.findIDByUserLogin());
                    }
                    nhatKy.setNhanVienId(nhanVien.getId());
                    nhatKy.setThoiGian(ZonedDateTime.now());
                    nhatKy.setNoiDung("Thêm mới vay lãi");
                    nhatKyService.save(nhatKy);

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
                    hopdong.setCuaHangId(cuaHangService.findIDByUserLogin());
//                    hopdong.setNgaytao();
                    hopdong.setTrangthaihopdong(TRANGTHAIHOPDONG.DANGVAY);
                    hopdong = hopDongService.save(hopdong);
                    vayLaiDTO.setHopdongvl(hopdong);

                    khachHangService.setStatus(hopdong.getKhachHangId(), StatusKhachHang.HOATDONG);// set trang thai khach hang
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
                    int soNgayDaDong = 0;
                    List<LichSuDongTienDTO> LSDT = lichSuDongTienService.findByHopDong(hopdong.getId());
                    for (LichSuDongTienDTO lichSuDongTienDTO : LSDT) {
                        if (lichSuDongTienDTO.getTrangthai().equals(DONGTIEN.DADONG)) {
                            chuKyDaDong++;
                            soNgayDaDong++;
                        } else if (lichSuDongTienDTO.getTrangthai().equals(DONGTIEN.CHUADONG)) {
                            lichSuDongTienService.delete(lichSuDongTienDTO.getId());
                        }
                    }
                    double tienTrongChuKi;
                    if (cachTinhLai.equals(TINHLAI.MOTTRIEU)) {
                        tienTrongChuKi = lai * (tongTienVay / 1000000) * kyLai;
                    } else {
                        tienTrongChuKi = lai * kyLai - chuKyDaDong;
                    }
                    // xu li du lieu nhan ve
                    int day = 0;
                    ZonedDateTime batdau = ngayVay;
                    int soChuKy = (soNgayVay - chuKyDaDong * kyLai) / kyLai;
                    if ((soNgayVay - chuKyDaDong * kyLai) % kyLai != 0) {
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

                    NhatKyDTO nhatKy = new NhatKyDTO();
                    if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
                        nhatKy.setCuaHangId(cuaHangService.findIDByUserLogin());
                    }
                    nhatKy.setNhanVienId(nhanVien.getId());
                    nhatKy.setThoiGian(ZonedDateTime.now());
                    nhatKy.setNoiDung("Gia hạn vay lãi");
                    nhatKyService.save(nhatKy);

                    return vayLaiMapper.toDto(vayLai);
                }
            } else {
                throw new InternalServerErrorException("Khong du tien");
            }

        }
        throw new InternalServerErrorException("Khong co quyen");

    }

    @Override
    public VayLaiDTO vay(VayLaiDTO vayLaiDTO, Long id, String mahopdong) {

        log.debug("Request to save VayLai : {}", vayLaiDTO);
//        String login = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            if (batHoService.quanLyVon() >= vayLaiDTO.getTienvay()
                    && khachHangRepository.findOne(hopDongRepository.findOne(id).getKhachHang().getId()).getStatus().equals(StatusKhachHang.DUNGHOATDONG)) {
                if (vayLaiDTO.getId() == null) {// add new vay lai
                    HopDongDTO hopdongcu = hopDongService.findOne(id);
                    hopdongcu.setTrangthaihopdong(TRANGTHAIHOPDONG.DADONG);
                    hopDongService.save(hopdongcu);
                    HopDongDTO hopdong = new HopDongDTO();
                    vayLaiDTO.setHopdongvl(hopdong);
                    hopdong.setLoaihopdong(LOAIHOPDONG.VAYLAI);
                    NhanVienDTO nhanVien = nhanVienService.findByUserLogin();
                    hopdong.setHopdonggocId(id);
                    hopdong.setKhachHangId(hopDongService.findOne(id).getKhachHangId());
                    hopdong.setNhanVienId(nhanVien.getId());
                    hopdong.setCuaHangId(cuaHangService.findIDByUserLogin());
                    if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
                        Long idCuaHang = cuaHangService.findIDByUserLogin();
                        hopdong.setCuaHangId(idCuaHang);
                    }
                    hopdong.setNgaytao(ZonedDateTime.now());
                    hopdong.setTrangthaihopdong(TRANGTHAIHOPDONG.DANGVAY);
                    hopdong.setMahopdong(mahopdong);
                    hopdong = hopDongService.save(hopdong);
                    vayLaiDTO.setHopdongvl(hopdong);

                    khachHangService.setStatus(hopdong.getKhachHangId(), StatusKhachHang.HOATDONG);// set trang thai khach hang
                    VayLai vayLai = vayLaiMapper.toEntity(vayLaiDTO);
                    vayLai = vayLaiRepository.save(vayLai);
                    // lay du lieu nhan ve
                    int chuKyDaDong = 0;
                    List<LichSuDongTienDTO> LSDT = lichSuDongTienService.findByHopDong(id);
                    for (LichSuDongTienDTO lichSuDongTienDTO : LSDT) {
                        if (lichSuDongTienDTO.getTrangthai().equals(DONGTIEN.DADONG)) {
                            chuKyDaDong++;
                        } else if (lichSuDongTienDTO.getTrangthai().equals(DONGTIEN.CHUADONG)) {
                            lichSuDongTienService.delete(lichSuDongTienDTO.getId());
                        }
                    }
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
                        tienTrongChuKi = lai * kyLai - chuKyDaDong;
                    }
                    // xu li du lieu nhan ve
                    int day = 0;
                    vayLai.setThoigianvay(soNgayVay - chuKyDaDong * kyLai);
                    ZonedDateTime batdau = ngayVay;
                    int soChuKy = (soNgayVay - chuKyDaDong * kyLai) / kyLai;
                    if ((soNgayVay - chuKyDaDong * kyLai) % kyLai != 0) {
                        soChuKy++;
                    }

                    long soTienTrongChuKy = Math.round(tienTrongChuKi / 1000) * 1000;//lam tron den 1000d
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

                    LichSuDongTienDTO lichSuDongTienTraGoc = new LichSuDongTienDTO();

                    lichSuDongTienTraGoc.setHopDongId(id);
                    lichSuDongTienTraGoc.setNhanVienId(nhanVienService.findByUserLogin().getId());
                    lichSuDongTienTraGoc.setNgaybatdau(ZonedDateTime.now());
                    lichSuDongTienTraGoc.setNgayketthuc(ZonedDateTime.now());
                    lichSuDongTienTraGoc.setSotien(vayLaiRepository.findByHopDong(id).getTienvay());
                    lichSuDongTienTraGoc.setTrangthai(DONGTIEN.TRAGOC);
                    lichSuDongTienTraGoc.setNgaygiaodich(ZonedDateTime.now());
                    lichSuDongTienService.save(lichSuDongTienTraGoc);

                    NhatKyDTO nhatKy = new NhatKyDTO();
                    if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
                        nhatKy.setCuaHangId(cuaHangService.findIDByUserLogin());
                    }
                    nhatKy.setNhanVienId(nhanVien.getId());
                    nhatKy.setThoiGian(ZonedDateTime.now());
                    nhatKy.setNoiDung("Vay thêm/Trả bớt vay lãi");
                    nhatKyService.save(nhatKy);

                    return vayLaiMapper.toDto(vayLai);
                }
            } else {
                throw new InternalServerErrorException("Khong du tien");
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
    public List<VayLaiDTO> findAll(TRANGTHAIHOPDONG trangthai) {
        log.debug("Request to get all VayLais");
//        String login = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.KETOAN)) {
            LinkedList<VayLaiDTO> collect = vayLaiRepository.findAllByTrangThaiHopDongAdmin(trangthai).stream()
                    .map(vayLaiMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            return collect;
        } else {
            NhanVienDTO nhanVien = nhanVienService.findByUserLogin();
            Long cuaHangId = nhanVien.getCuaHangId();
            LinkedList<VayLaiDTO> collect = vayLaiRepository.findAllByTrangThaiHopDong(trangthai, cuaHangId).stream()
                    .map(vayLaiMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            return collect;

        }
//        return vayLaiRepository.findAll().stream()
//                .map(vayLaiMapper::toDto)
//                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VayLaiDTO> findAllByCuaHang(Long id) {
        log.debug("Request to get all VayLais");
//        String login = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)||SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.KETOAN)) {
            LinkedList<VayLaiDTO> collect = vayLaiRepository.findAllByCuaHang(id).stream()
                    .map(vayLaiMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            return collect;
        } else {
            throw new InternalServerErrorException("Khong co quyen");
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
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.KETOAN)) {
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
    @Transactional(readOnly = true)
    public VayLaiDTO findByHopDong(Long id
    ) {
        log.debug("Request to get VayLai : {}", id);
        String login = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
        VayLai vayLai = null;
        vayLai = vayLaiRepository.findByHopDong(id);
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
    public List<VayLaiDTO> findByNameOrCMND(String key
    ) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            log.debug("Request to get all KhachHangs");
            key = new StringBuffer("%").append(key).append("%").toString();
            Long cuaHangid = cuaHangService.findIDByUserLogin();

            return vayLaiRepository.findByNameOrCMNDAdmin(key).stream()
                    .map(vayLaiMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            log.debug("Request to get all KhachHangs");
            key = new StringBuffer("%").append(key).append("%").toString();
            Long cuaHangid = cuaHangService.findIDByUserLogin();

            return vayLaiRepository.findByNameOrCMND(key, cuaHangid).stream()
                    .map(vayLaiMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
        }
        throw new InternalServerErrorException("Khong co quyen");
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

    @Override
    public List<VayLaiDTO> baoCao(ZonedDateTime start, ZonedDateTime end, Integer vayThemTraGoc
    ) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            Long idCuaHang = cuaHangService.findIDByUserLogin();
            List<VayLai> baoCao = null;
            if (vayThemTraGoc == 0) {
                baoCao = vayLaiRepository.baocao(start, end, idCuaHang);
            } else {
                baoCao = vayLaiRepository.vayThemTraGoc(start, end, idCuaHang);
            }

            List<VayLaiDTO> collect = baoCao.stream()
                    .map(vayLaiMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            return collect;

        }
        throw new InternalServerErrorException("Khong co quyen");
    }
    @Override
    public List<VayLaiDTO> baoCaoKeToan(ZonedDateTime start, ZonedDateTime end, Integer vayThemTraGoc,Long idCuaHang) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.KETOAN)) {
            List<VayLai> baoCao = null;
            if (vayThemTraGoc == 0) {
                baoCao = vayLaiRepository.baocao(start, end, idCuaHang);
            } else {
                baoCao = vayLaiRepository.vayThemTraGoc(start, end, idCuaHang);
            }

            List<VayLaiDTO> collect = baoCao.stream()
                    .map(vayLaiMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            return collect;

        }
        throw new InternalServerErrorException("Khong co quyen");
    }

    @Override
    public List<VayLaiDTO> baoCao(ZonedDateTime start, ZonedDateTime end,
            Long id, Integer vayThemTraGoc
    ) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            Long idCuaHang = cuaHangService.findIDByUserLogin();
            List<VayLai> baoCao = null;
            if (vayThemTraGoc == 0) {
                baoCao = vayLaiRepository.baocaoNV(start, end, idCuaHang, id);
            } else {
                baoCao = vayLaiRepository.vayThemTraGocNV(start, end, idCuaHang, id);
            }
            List<VayLaiDTO> collect = baoCao.stream()
                    .map(vayLaiMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            return collect;

        }
        throw new InternalServerErrorException("Khong co quyen");
    }

    @Override
    public List<VayLaiDTO> findByNhanVien(Long id
    ) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)||SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.KETOAN)) {
            List<VayLai> listVayLai = vayLaiRepository.findByNhanVien(id);
            return listVayLai.stream()
                    .map(vayLaiMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));

        }
        throw new InternalServerErrorException("Khong co quyen");
    }

    public Double tienVayThemTraBot(Long id) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            Long cuaHangId = cuaHangService.findIDByUserLogin();
            VayLai vayLai = vayLaiRepository.findHopDongGoc(cuaHangId, id);
            Double sotien = 0d;
            sotien = vayLaiRepository.findOne(id).getTienvay() - vayLai.getTienvay();
            return sotien;
        }

        throw new InternalServerErrorException("Khong co quyen");
    }

    @Override
    public List<VayLaiDTO> lichSuTraCham() {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            List<LichSuDongTienDTO> listLichSuDongTien = lichSuDongTienService.lichSuTraCham(DONGTIEN.CHUADONG, LOAIHOPDONG.VAYLAI);
            Set<VayLai> listVayLaiTraCham = new HashSet<VayLai>();
            for (LichSuDongTienDTO lichSuDongTien : listLichSuDongTien) {
                listVayLaiTraCham.add(vayLaiRepository.findByHopDong(lichSuDongTien.getHopDongId()));
            }
            List<VayLaiDTO> collect = listVayLaiTraCham.stream()
                    .map(vayLaiMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            return collect;

        }
        throw new BadRequestAlertException("không có quyền", null, null);
    }

}
