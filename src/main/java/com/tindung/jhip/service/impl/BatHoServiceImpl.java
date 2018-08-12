package com.tindung.jhip.service.impl;

import com.tindung.jhip.service.BatHoService;
import com.tindung.jhip.domain.BatHo;
import com.tindung.jhip.domain.LichSuDongTien;
import com.tindung.jhip.domain.enumeration.DONGTIEN;
import com.tindung.jhip.domain.enumeration.LOAIHOPDONG;
import com.tindung.jhip.repository.BatHoRepository;
import com.tindung.jhip.repository.LichSuDongTienRepository;
import com.tindung.jhip.security.AuthoritiesConstants;
import com.tindung.jhip.security.SecurityUtils;
import com.tindung.jhip.service.CuaHangService;
import com.tindung.jhip.service.HopDongService;
import com.tindung.jhip.service.LichSuDongTienService;
import com.tindung.jhip.service.LichSuThaoTacHopDongService;
import com.tindung.jhip.service.NhanVienService;
import com.tindung.jhip.service.dto.BatHoDTO;
import com.tindung.jhip.service.dto.GhiNoDTO;
import com.tindung.jhip.service.dto.HopDongDTO;
import com.tindung.jhip.service.dto.KhachHangDTO;
import com.tindung.jhip.service.dto.LichSuDongTienDTO;
import com.tindung.jhip.service.dto.LichSuThaoTacHopDongDTO;
import com.tindung.jhip.service.dto.NhanVienDTO;
import com.tindung.jhip.service.mapper.BatHoMapper;
import com.tindung.jhip.service.mapper.LichSuDongTienMapper;
import com.tindung.jhip.service.GhiNoService;
import com.tindung.jhip.domain.enumeration.NOTRA;
import com.tindung.jhip.domain.enumeration.THUCHI;
import com.tindung.jhip.domain.enumeration.TRANGTHAIHOPDONG;
import com.tindung.jhip.repository.GhiNoRepository;
import com.tindung.jhip.repository.ThuChiRepository;
import com.tindung.jhip.repository.VayLaiRepository;
import com.tindung.jhip.web.rest.errors.InternalServerErrorException;
import java.sql.Date;
import java.time.ZonedDateTime;
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
    private final VayLaiRepository vayLaiRepository;
    private final NhanVienService nhanVienService;
    private final CuaHangService cuaHangService;
    private final LichSuDongTienService lichSuDongTienService;
    private final LichSuDongTienRepository lichSuDongTienRepository;
    private final LichSuDongTienMapper lichSuDongTienMapper;
    private final LichSuThaoTacHopDongService lichSuThaoTacHopDongService;
    private final GhiNoRepository ghiNoRepository;
    private final ThuChiRepository thuChiRepository;

    public BatHoServiceImpl(BatHoMapper batHoMapper, BatHoRepository batHoRepository, HopDongService hopDongService, VayLaiRepository vayLaiRepository, NhanVienService nhanVienService, CuaHangService cuaHangService, LichSuDongTienService lichSuDongTienService, LichSuDongTienRepository lichSuDongTienRepository, LichSuDongTienMapper lichSuDongTienMapper, LichSuThaoTacHopDongService lichSuThaoTacHopDongService, GhiNoRepository ghiNoRepository, ThuChiRepository thuChiRepository) {
        this.batHoMapper = batHoMapper;
        this.batHoRepository = batHoRepository;
        this.hopDongService = hopDongService;
        this.vayLaiRepository = vayLaiRepository;
        this.nhanVienService = nhanVienService;
        this.cuaHangService = cuaHangService;
        this.lichSuDongTienService = lichSuDongTienService;
        this.lichSuDongTienRepository = lichSuDongTienRepository;
        this.lichSuDongTienMapper = lichSuDongTienMapper;
        this.lichSuThaoTacHopDongService = lichSuThaoTacHopDongService;
        this.ghiNoRepository = ghiNoRepository;
        this.thuChiRepository = thuChiRepository;
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
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {

            validate(batHoDTO);
            if (batHoDTO.getId() == null) { //add new bat ho
                if (batHoDTO.getTienduakhach() <= quanLyVon()) {
                    HopDongDTO hopdong = batHoDTO.getHopdong();
                    hopdong.setLoaihopdong(LOAIHOPDONG.BATHO);
                    hopdong.setCuaHangId(cuaHangService.findIDByUserLogin());
                    NhanVienDTO findByUserLogin = nhanVienService.findByUserLogin();
                    hopdong.setNhanVienId(findByUserLogin.getId());
                    if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
                        Long idCuaHang = cuaHangService.findIDByUserLogin();
                        hopdong.setCuaHangId(idCuaHang);
                    }

                    hopdong.setNgaytao(ZonedDateTime.now());
                    hopdong.setTrangthaihopdong(TRANGTHAIHOPDONG.DANGVAY);
                    hopdong = hopDongService.save(hopdong);
                    batHoDTO.setHopdong(hopdong);
                    BatHo batHo = batHoMapper.toEntity(batHoDTO);
                    batHo = batHoRepository.save(batHo);
                    Integer chuky = batHo.getChuky();
                    Double tienduakhach = batHo.getTienduakhach();
                    Integer tongsongay = batHo.getTongsongay();
                    Double tongtien = batHo.getTongtien();
                    ZonedDateTime ngaytao = hopdong.getNgaytao();

                    ZonedDateTime batdau = ngaytao;
//            Date date = new Date(batdau.)
//batdau.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))
                    int soChuKy = tongsongay / chuky;
                    if (tongsongay % chuky != 0) {
                        soChuKy++;
                    }

                    long soTienTrongChuKy = Math.round((tongtien / soChuKy) * 1000) / 1000;//lam tron den 1000d
                    for (int i = 0; i < soChuKy - 1; i++) {
                        LichSuDongTienDTO lichSuDongTienDTO = new LichSuDongTienDTO();
                        lichSuDongTienDTO.setHopDongId(hopdong.getId());
                        lichSuDongTienDTO.setNhanVienId(nhanVienService.findByUserLogin().getId());
                        lichSuDongTienDTO.setNgaybatdau(batdau);
                        batdau = batdau.plusDays(chuky);
                        lichSuDongTienDTO.setNgayketthuc(batdau);
                        lichSuDongTienDTO.setSotien(soTienTrongChuKy * 1d);
                        lichSuDongTienDTO.setTrangthai(DONGTIEN.CHUADONG);
                        lichSuDongTienService.save(lichSuDongTienDTO);
                    }
                    //phat cuoi
                    LichSuDongTienDTO lichSuDongTienDTO = new LichSuDongTienDTO();
                    lichSuDongTienDTO.setHopDongId(hopdong.getId());
                    lichSuDongTienDTO.setNhanVienId(nhanVienService.findByUserLogin().getId());
                    lichSuDongTienDTO.setNgaybatdau(batdau);
                    batdau = ngaytao.plusDays(tongsongay);
                    lichSuDongTienDTO.setNgayketthuc(batdau);
                    lichSuDongTienDTO.setSotien(soTienTrongChuKy * 1d);
                    lichSuDongTienDTO.setTrangthai(DONGTIEN.CHUADONG);
                    lichSuDongTienService.save(lichSuDongTienDTO);

                    LichSuThaoTacHopDongDTO lichSuThaoTacHopDongDTO = new LichSuThaoTacHopDongDTO();
                    lichSuThaoTacHopDongDTO.setHopDongId(hopdong.getId());
                    lichSuThaoTacHopDongDTO.setNhanVienId(nhanVienService.findByUserLogin().getId());
                    lichSuThaoTacHopDongDTO.setNoidung("Tạo mới bát họ");
                    lichSuThaoTacHopDongDTO.setThoigian(ZonedDateTime.now());
                    lichSuThaoTacHopDongDTO.setSoTienGhiCo(0d);
                    lichSuThaoTacHopDongDTO.setSoTienGhiNo(batHo.getTienduakhach());
                    lichSuThaoTacHopDongService.save(lichSuThaoTacHopDongDTO);

                    return batHoMapper.toDto(batHo);
                } else {
                    throw new InternalServerErrorException("Không đủ tiền");
                }
            } else {
                throw new InternalServerErrorException("Không được sửa bat họ");
//                Long idCuaHang = cuaHangService.findIDByUserLogin();
//                BatHo findOne = batHoRepository.findOne(batHoDTO.getId());
//
//                batHoDTO.getHopdong().setCuaHangId(idCuaHang);//de phong user thay doi idcuahang
//                HopDongDTO hopdong = hopDongService.save(batHoDTO.getHopdong());
//                batHoDTO.setHopdong(hopdong);
//                double tienPhaiDong = 0;
//                List<LichSuDongTienDTO> LSDT = lichSuDongTienService.findByHopDong(hopdong.getId());
//                for (LichSuDongTienDTO lichSuDongTienDTO : LSDT) {
//                    if (lichSuDongTienDTO.getTrangthai().equals(DONGTIEN.CHUADONG)) {
//                        tienPhaiDong += lichSuDongTienDTO.getSotien();
//                        lichSuDongTienService.delete(lichSuDongTienDTO.getId());
//                    }
//                }
//                List<GhiNoDTO> GN = ghiNoService.findByHopDong(hopdong.getId());
//                for (GhiNoDTO ghiNo : GN) {
//                    if (ghiNo.getTrangthai().equals(NOTRA.NO)) {
//                        tienPhaiDong += ghiNo.getSotien();
//                    } else if (ghiNo.getTrangthai().equals(NOTRA.TRA)) {
//                        tienPhaiDong = tienPhaiDong - ghiNo.getSotien();
//                    }
//                    ghiNoService.delete(ghiNo.getId());
//                }
//
//                BatHo batHo = batHoMapper.toEntity(batHoDTO);
//                batHo = batHoRepository.save(batHo);
//                LichSuDongTienDTO lichSuDongTienDaoHo = new LichSuDongTienDTO();
//                lichSuDongTienDaoHo.setHopDongId(hopdong.getId());
//                lichSuDongTienDaoHo.setNhanVienId(nhanVienService.findByUserLogin().getId());
//                lichSuDongTienDaoHo.setNgaybatdau(ZonedDateTime.now());
//                lichSuDongTienDaoHo.setNgayketthuc(ZonedDateTime.now());
//                lichSuDongTienDaoHo.setSotien(tienPhaiDong * 1d);
//                lichSuDongTienDaoHo.setTrangthai(DONGTIEN.DADONG);
//                lichSuDongTienService.save(lichSuDongTienDaoHo);
//                Integer chuky = batHo.getChuky();
//                Double tienduakhach = batHo.getTienduakhach();
//                Integer tongsongay = batHo.getTongsongay();
//                Double tongtien = batHo.getTongtien();
//                ZonedDateTime ngaytao = hopdong.getNgaytao();
//
//                ZonedDateTime batdau = ngaytao;
////            Date date = new Date(batdau.)
////batdau.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))
//
//                int soChuKy = tongsongay / chuky;
//                if (tongsongay % chuky != 0) {
//                    soChuKy++;
//                }
//
//                long soTienTrongChuKy = Math.round((tongtien / soChuKy)*1000)/1000;//lam tron den 1000d
//                for (int i = 0; i < soChuKy - 1; i++) {
//                    LichSuDongTienDTO lichSuDongTienDTO = new LichSuDongTienDTO();
//                    lichSuDongTienDTO.setHopDongId(hopdong.getId());
//                    lichSuDongTienDTO.setNhanVienId(nhanVienService.findByUserLogin().getId());
//                    lichSuDongTienDTO.setNgaybatdau(batdau);
//                    batdau = batdau.plusDays(chuky);
//                    lichSuDongTienDTO.setNgayketthuc(batdau);
//                    lichSuDongTienDTO.setSotien(soTienTrongChuKy * 1d);
//                    lichSuDongTienDTO.setTrangthai(DONGTIEN.CHUADONG);
//                    lichSuDongTienService.save(lichSuDongTienDTO);
//                }
//                //phat cuoi
//                LichSuDongTienDTO lichSuDongTienDTO = new LichSuDongTienDTO();
//                lichSuDongTienDTO.setHopDongId(hopdong.getId());
//                lichSuDongTienDTO.setNhanVienId(nhanVienService.findByUserLogin().getId());
//                lichSuDongTienDTO.setNgaybatdau(batdau);
//                batdau = ngaytao.plusDays(tongsongay);
//                lichSuDongTienDTO.setNgayketthuc(batdau);
//                lichSuDongTienDTO.setSotien(soTienTrongChuKy * 1d);
//                lichSuDongTienDTO.setTrangthai(DONGTIEN.CHUADONG);
//                lichSuDongTienService.save(lichSuDongTienDTO);
//                return batHoMapper.toDto(batHo);

            }
        }
        throw new InternalServerErrorException("Khong co quyen");

    }

    @Override
    public BatHoDTO daoHo(BatHoDTO batHoDTO, Long id
    ) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            if (batHoDTO.getTienduakhach() < quanLyVon()) {
                HopDongDTO hopdong = new HopDongDTO();
                hopdong.setLoaihopdong(LOAIHOPDONG.BATHO);
                hopdong.setCuaHangId(cuaHangService.findIDByUserLogin());
                NhanVienDTO findByUserLogin = nhanVienService.findByUserLogin();
                hopdong.setNhanVienId(findByUserLogin.getId());
                if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
                    Long idCuaHang = cuaHangService.findIDByUserLogin();
                    hopdong.setCuaHangId(idCuaHang);
                }
                hopdong.setMahopdong("DH");
                hopdong.setHopdonggocId(id);
                hopdong.setNgaytao(ZonedDateTime.now());
                hopdong.setTrangthaihopdong(TRANGTHAIHOPDONG.DANGVAY);
                hopdong.setKhachHangId(hopDongService.findOne(id).getKhachHangId());
                hopdong = hopDongService.save(hopdong);
                batHoDTO.setHopdong(hopdong);
                BatHo batHo = batHoMapper.toEntity(batHoDTO);
                batHo = batHoRepository.save(batHo);
                Integer chuky = batHo.getChuky();
                Double tienduakhach = batHo.getTienduakhach();
                Integer tongsongay = batHo.getTongsongay();
                Double tongtien = batHo.getTongtien();
                ZonedDateTime ngaytao = hopdong.getNgaytao();

                ZonedDateTime batdau = ngaytao;
//            Date date = new Date(batdau.)
//batdau.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))
                int soChuKy = tongsongay / chuky;
                if (tongsongay % chuky != 0) {
                    soChuKy++;
                }

                long soTienTrongChuKy = Math.round((tongtien / soChuKy) * 1000) / 1000;//lam tron den 1000d
                for (int i = 0; i < soChuKy - 1; i++) {
                    LichSuDongTienDTO lichSuDongTienDTO = new LichSuDongTienDTO();
                    lichSuDongTienDTO.setHopDongId(hopdong.getId());
                    lichSuDongTienDTO.setNhanVienId(nhanVienService.findByUserLogin().getId());
                    lichSuDongTienDTO.setNgaybatdau(batdau);
                    batdau = batdau.plusDays(chuky);
                    lichSuDongTienDTO.setNgayketthuc(batdau);
                    lichSuDongTienDTO.setSotien(soTienTrongChuKy * 1d);
                    lichSuDongTienDTO.setTrangthai(DONGTIEN.CHUADONG);
                    lichSuDongTienService.save(lichSuDongTienDTO);
                }
                //phat cuoi
                LichSuDongTienDTO lichSuDongTienDTO = new LichSuDongTienDTO();
                lichSuDongTienDTO.setHopDongId(hopdong.getId());
                lichSuDongTienDTO.setNhanVienId(nhanVienService.findByUserLogin().getId());
                lichSuDongTienDTO.setNgaybatdau(batdau);
                batdau = ngaytao.plusDays(tongsongay);
                lichSuDongTienDTO.setNgayketthuc(batdau);
                lichSuDongTienDTO.setSotien(soTienTrongChuKy * 1d);
                lichSuDongTienDTO.setTrangthai(DONGTIEN.CHUADONG);
                lichSuDongTienService.save(lichSuDongTienDTO);

                return batHoMapper.toDto(batHo);
            } else {
                throw new InternalServerErrorException("Không đủ tiền");
            }

        } else {
            throw new InternalServerErrorException("không có quyền");
        }
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
    public BatHoDTO findOne(Long id
    ) {
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
     * Delete the batHo by id. F
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id
    ) {
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

    @Override
    public LichSuDongTienDTO setDongTien(Long id
    ) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            LichSuDongTien lichSuDongTien = null;
            lichSuDongTien = lichSuDongTienRepository.findOne(id);
            lichSuDongTien.setTrangthai(DONGTIEN.DADONG);
            return lichSuDongTienMapper.toDto(lichSuDongTien);
        }
        throw new InternalServerErrorException("Khong co quyen");
    }

    @Override
    public List<BatHoDTO> findByNameOrCMND(String key
    ) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            log.debug("Request to get all KhachHangs");
            key = new StringBuffer("%").append(key).append("%").toString();
            Long idcuaHang = cuaHangService.findIDByUserLogin();
            return batHoRepository.findByNameOrCMND(key, idcuaHang).stream()
                    .map(batHoMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
        }
        throw new InternalServerErrorException("Khong co quyen");
    }

    private void validate(BatHoDTO bh) {

        if (bh.getTongsongay() < 0) {
            throw new InternalServerErrorException("thong tin bat ho - tong so ngay khong dung");
        }

        if (bh.getChuky() < 0 || bh.getChuky() > bh.getTongsongay()) {
            throw new InternalServerErrorException("thong tin bat ho - chu ky khong dung");
        }
        if (bh.getTongtien() < 0) {
            throw new InternalServerErrorException("thong tin bat ho - tong tien khong dung");

        }
        if (bh.getTienduakhach() < 0) {
            throw new InternalServerErrorException("thong tin bat ho - tien dua khach khong dung");

        }
    }

    //Tùng viết
    @Override
    @Transactional(readOnly = true)
    public List<BatHoDTO> findByCuaHangId(Long id) {

        List<BatHo> findByCuaHangId = batHoRepository.findByCuaHangId(id);
        List<BatHoDTO> collect = findByCuaHangId.stream()
                .map(batHoMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
        return collect;
    }

    //Tùng end
    @Override
    public List<BatHoDTO> findByHopDong(Long id) {
        List<BatHo> findByCuaHangId = batHoRepository.findByHopDong(id);
        List<BatHoDTO> collect = findByCuaHangId.stream()
                .map(batHoMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
        return collect;

    }

    @Override
    public List<BatHoDTO> baoCao(ZonedDateTime start, ZonedDateTime end, Long idNhanVien) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            Long idCuaHang = cuaHangService.findIDByUserLogin();
            List<BatHo> baoCao = batHoRepository.baocaoNV(start, end, idCuaHang, idNhanVien);
            List<BatHoDTO> collect = baoCao.stream()
                    .map(batHoMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            return collect;

        }
        throw new InternalServerErrorException("Khong co quyen");
    }

    @Override
    public List<BatHoDTO> baoCao(ZonedDateTime start, ZonedDateTime end) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            Long idCuaHang = cuaHangService.findIDByUserLogin();
            List<BatHo> baoCao = batHoRepository.baocao(start, end, idCuaHang);
            List<BatHoDTO> collect = baoCao.stream()
                    .map(batHoMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            return collect;

        }
        throw new InternalServerErrorException("Khong co quyen");
    }

    @Override
    public List<BatHoDTO> findByTrangThai(ZonedDateTime start, ZonedDateTime end, TRANGTHAIHOPDONG trangthai) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            Long idCuaHang = cuaHangService.findIDByUserLogin();
            List<BatHo> baoCao = batHoRepository.findByTrangThai(start, end, trangthai, idCuaHang);
            List<BatHoDTO> collect = baoCao.stream()
                    .map(batHoMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            return collect;

        }
        throw new InternalServerErrorException("Khong co quyen");
    }

    @Override
    public List<BatHoDTO> findByTrangThaiHopDong(TRANGTHAIHOPDONG trangthai) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            Long idCuaHang = cuaHangService.findIDByUserLogin();
            List<BatHo> baoCao = batHoRepository.findByTrangThaiHopDong(trangthai, idCuaHang);
            List<BatHoDTO> collect = baoCao.stream()
                    .map(batHoMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            return collect;

        }
        throw new InternalServerErrorException("Khong co quyen");
    }

    @Override
    public List<BatHoDTO> findByTrangThaiNV(ZonedDateTime start, ZonedDateTime end, TRANGTHAIHOPDONG trangthai, Long id) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            Long idCuaHang = cuaHangService.findIDByUserLogin();
            List<BatHo> baoCao = batHoRepository.findByTrangThaiNV(start, end, trangthai, idCuaHang, id);
            List<BatHoDTO> collect = baoCao.stream()
                    .map(batHoMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            return collect;

        }
        throw new InternalServerErrorException("Khong co quyen");
    }

    @Override
    public Double quanLyVon() {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            Long idCuaHang = cuaHangService.findIDByUserLogin();

            Double tienDuaKhachBatHo = batHoRepository.tienDuaKhach(idCuaHang).orElse(0d);
            Double tienNo = ghiNoRepository.tienNo(NOTRA.NO, idCuaHang).orElse(0d);;
            Double tienTraNo = ghiNoRepository.tienNo(NOTRA.TRA, idCuaHang).orElse(0d);;
            Double tienVayDuaKhach = vayLaiRepository.tienVayDuaKhach(idCuaHang).orElse(0d);;
            Double lichSuThuTienVayLaiBatHo = lichSuDongTienRepository.lichSuDongTien(DONGTIEN.DADONG, idCuaHang).orElse(0d);;
            Double tienTraGocVayLai = lichSuDongTienRepository.lichSuDongTien(DONGTIEN.TRAGOC, idCuaHang).orElse(0d);;
            Double thu = thuChiRepository.thuchi(THUCHI.THU, idCuaHang).orElse(0d);;
            Double chi = thuChiRepository.thuchi(THUCHI.CHI, idCuaHang).orElse(0d);;
            Double gopVon = thuChiRepository.thuchi(THUCHI.GOPVON, idCuaHang).orElse(0d);;
            Double rutVon = thuChiRepository.thuchi(THUCHI.RUTVON, idCuaHang).orElse(0d);;

            Double nguonvon = 0d;
            nguonvon = (gopVon + thu + lichSuThuTienVayLaiBatHo + tienTraNo + tienTraGocVayLai) - (tienDuaKhachBatHo + tienVayDuaKhach + tienNo + chi + rutVon);
            return nguonvon;
        }
        throw new InternalServerErrorException("Khong co quyen");
    }

}
