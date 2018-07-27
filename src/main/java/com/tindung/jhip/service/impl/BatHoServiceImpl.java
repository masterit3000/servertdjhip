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
import com.tindung.jhip.service.dto.HopDongDTO;
import com.tindung.jhip.service.dto.KhachHangDTO;
import com.tindung.jhip.service.dto.LichSuDongTienDTO;
import com.tindung.jhip.service.dto.LichSuThaoTacHopDongDTO;
import com.tindung.jhip.service.dto.NhanVienDTO;
import com.tindung.jhip.service.mapper.BatHoMapper;
import com.tindung.jhip.service.mapper.LichSuDongTienMapper;
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

    private final NhanVienService nhanVienService;
    private final CuaHangService cuaHangService;
    private final LichSuDongTienService lichSuDongTienService;
    private final LichSuDongTienRepository lichSuDongTienRepository;
    private final LichSuDongTienMapper lichSuDongTienMapper;
    private final LichSuThaoTacHopDongService lichSuThaoTacHopDongService;

    public BatHoServiceImpl(BatHoMapper batHoMapper, BatHoRepository batHoRepository, HopDongService hopDongService, NhanVienService nhanVienService, CuaHangService cuaHangService, LichSuDongTienService lichSuDongTienService, LichSuDongTienRepository lichSuDongTienRepository, LichSuDongTienMapper lichSuDongTienMapper, LichSuThaoTacHopDongService lichSuThaoTacHopDongService) {
        this.batHoMapper = batHoMapper;
        this.batHoRepository = batHoRepository;
        this.hopDongService = hopDongService;
        this.nhanVienService = nhanVienService;
        this.cuaHangService = cuaHangService;
        this.lichSuDongTienService = lichSuDongTienService;
        this.lichSuDongTienRepository = lichSuDongTienRepository;
        this.lichSuThaoTacHopDongService = lichSuThaoTacHopDongService;
        this.lichSuDongTienMapper = lichSuDongTienMapper;
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

                HopDongDTO hopdong = batHoDTO.getHopdong();
                hopdong.setLoaihopdong(LOAIHOPDONG.BATHO);
                NhanVienDTO findByUserLogin = nhanVienService.findByUserLogin();
                hopdong.setNhanVienId(findByUserLogin.getId());
                if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
                    Long idCuaHang = cuaHangService.findIDByUserLogin();
                    hopdong.setCuaHangId(idCuaHang);
                }

                hopdong.setNgaytao(ZonedDateTime.now());
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

                long soTienTrongChuKy = Math.round((tongtien / soChuKy));//lam tron den 1000d
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

                throw new InternalServerErrorException("Không được sửa bat họ");

            }
        }
        throw new InternalServerErrorException("Khong co quyen");

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
    public LichSuDongTienDTO setDongTien(Long id) {
        LichSuDongTien lichSuDongTien = null;
        lichSuDongTien = lichSuDongTienRepository.findOne(id);
        lichSuDongTien.setTrangthai(DONGTIEN.DADONG);
        return lichSuDongTienMapper.toDto(lichSuDongTien);
    }

    @Override
    public List<BatHoDTO> findByNameOrCMND(String key) {
        log.debug("Request to get all KhachHangs");
        key = new StringBuffer("%").append(key).append("%").toString();

        return batHoRepository.findByNameOrCMND(key).stream()
                .map(batHoMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
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
}
