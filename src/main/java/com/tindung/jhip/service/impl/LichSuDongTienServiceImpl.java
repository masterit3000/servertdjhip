package com.tindung.jhip.service.impl;

import com.tindung.jhip.domain.GhiNo;
import com.tindung.jhip.domain.HopDong;
import com.tindung.jhip.service.LichSuDongTienService;
import com.tindung.jhip.domain.LichSuDongTien;
import com.tindung.jhip.domain.enumeration.DONGTIEN;
import com.tindung.jhip.domain.enumeration.LOAIHOPDONG;
import com.tindung.jhip.domain.enumeration.NOTRA;
import com.tindung.jhip.domain.enumeration.StatusKhachHang;
import com.tindung.jhip.domain.enumeration.TRANGTHAIHOPDONG;
import com.tindung.jhip.repository.BatHoRepository;
import com.tindung.jhip.repository.HopDongRepository;
import com.tindung.jhip.repository.LichSuDongTienRepository;
import com.tindung.jhip.repository.LichSuThaoTacHopDongRepository;
import com.tindung.jhip.repository.VayLaiRepository;
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
import com.tindung.jhip.service.HopDongService;
import com.tindung.jhip.service.KhachHangService;
import com.tindung.jhip.service.LichSuThaoTacHopDongService;
import com.tindung.jhip.service.dto.GhiNoDTO;
import com.tindung.jhip.service.dto.HopDongDTO;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import com.tindung.jhip.service.dto.LichSuThaoTacHopDongDTO;
import java.util.Set;

/**
 * Service Implementation for managing LichSuDongTien.
 */
@Service
@Transactional
public class LichSuDongTienServiceImpl implements LichSuDongTienService {

    private final Logger log = LoggerFactory.getLogger(LichSuDongTienServiceImpl.class);

    private final LichSuDongTienRepository lichSuDongTienRepository;

    private final LichSuDongTienMapper lichSuDongTienMapper;
    private final HopDongRepository hopDongRepository;
    private final NhanVienService nhanVienService;
    private final HopDongService hopDongService;
    private final KhachHangService khachHangService;

//    @Autowired
    private final CuaHangService cuaHangService;
    private final LichSuThaoTacHopDongRepository lichSuThaoTacHopDongRepository;
    private final LichSuThaoTacHopDongService lichSuThaoTacHopDongService;
    private final VayLaiRepository vayLaiRepository;

    public LichSuDongTienServiceImpl(LichSuDongTienRepository lichSuDongTienRepository, LichSuDongTienMapper lichSuDongTienMapper, HopDongRepository hopDongRepository, NhanVienService nhanVienService, HopDongService hopDongService, KhachHangService khachHangService, CuaHangService cuaHangService, LichSuThaoTacHopDongRepository lichSuThaoTacHopDongRepository, LichSuThaoTacHopDongService lichSuThaoTacHopDongService, VayLaiRepository vayLaiRepository) {
        this.lichSuDongTienRepository = lichSuDongTienRepository;
        this.lichSuDongTienMapper = lichSuDongTienMapper;
        this.hopDongRepository = hopDongRepository;
        this.nhanVienService = nhanVienService;
        this.hopDongService = hopDongService;
        this.khachHangService = khachHangService;
        this.cuaHangService = cuaHangService;
        this.lichSuThaoTacHopDongRepository = lichSuThaoTacHopDongRepository;
        this.lichSuThaoTacHopDongService = lichSuThaoTacHopDongService;
        this.vayLaiRepository = vayLaiRepository;
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
    public void setDongTien(Long id, DONGTIEN dongtien) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)) {
            LichSuDongTien lichSuDongTien = lichSuDongTienRepository.findOne(id);
            lichSuDongTien.setTrangthai(DONGTIEN.DADONG);
            lichSuDongTien.setNgaygiaodich(ZonedDateTime.now());
            lichSuDongTienRepository.save(lichSuDongTien);
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.KETOAN)) {
            LichSuDongTien lichSuDongTien = lichSuDongTienRepository.findOne(id);
            lichSuDongTien.setTrangthai(DONGTIEN.CHUADONG);
            lichSuDongTien.setNgaygiaodich(ZonedDateTime.now());
            lichSuDongTienRepository.save(lichSuDongTien);
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<LichSuDongTienDTO> findByHopDong(Long id
    ) {

        List<LichSuDongTien> findByHopDong = lichSuDongTienRepository.findByHopDong(id);
        List<LichSuDongTienDTO> collect = findByHopDong.stream()
                .map(lichSuDongTienMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
        return collect;

    }

    @Override
    public void dongHopDong(Long id
    ) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            hopDongRepository.findOne(id).setTrangthaihopdong(TRANGTHAIHOPDONG.DADONG);
            List<LichSuDongTien> findByHopDong = lichSuDongTienRepository.findByHopDong(id);
            double tienConPhaiDong = 0;
            for (LichSuDongTien lichSuDongTien : findByHopDong) {
                if (lichSuDongTien.getTrangthai().equals(DONGTIEN.CHUADONG)) {
                    tienConPhaiDong += lichSuDongTien.getSotien();
                    lichSuDongTienRepository.delete(lichSuDongTien);
                }
            }

            khachHangService.setStatus(hopDongRepository.findOne(id).getKhachHang().getId(), StatusKhachHang.DUNGHOATDONG);

            LichSuThaoTacHopDongDTO lichSuThaoTacHopDong = new LichSuThaoTacHopDongDTO();
            lichSuThaoTacHopDong.setHopDongId(id);
            lichSuThaoTacHopDong.setNhanVienId(cuaHangService.findIDByUserLogin());
            lichSuThaoTacHopDong.setSoTienGhiCo(tienConPhaiDong);
            lichSuThaoTacHopDong.setSoTienGhiNo(0 * 1d);
            lichSuThaoTacHopDong.setThoigian(ZonedDateTime.now());
            lichSuThaoTacHopDong.setNoidung("đóng hợp đồng");
            lichSuThaoTacHopDongService.save(lichSuThaoTacHopDong);

//        List<GhiNoDTO> ghiNos = ghiNoService.findByHopDong(id);
//        for (GhiNoDTO ghiNo : ghiNos) {
//            if(ghiNo.getTrangthai().equals(NOTRA.NO)){
//                tienConPhaiDong = tienConPhaiDong + ghiNo.getSotien();
//            }else if(ghiNo.getTrangthai().equals(NOTRA.TRA)){
//                tienConPhaiDong =  tienConPhaiDong  -ghiNo.getSotien();
//            }
//        }
            if (tienConPhaiDong > 0) {
                LichSuDongTienDTO lichSuDongTienDTO = new LichSuDongTienDTO();
                lichSuDongTienDTO.setHopDongId(id);
                lichSuDongTienDTO.setNgaybatdau(ZonedDateTime.now());
                lichSuDongTienDTO.setNgayketthuc(ZonedDateTime.now());
                lichSuDongTienDTO.setSotien(tienConPhaiDong);
                lichSuDongTienDTO.setNhanVienId(nhanVienService.findByUserLogin().getId());
                lichSuDongTienDTO.setTrangthai(DONGTIEN.DADONG);
                lichSuDongTienDTO.setNgaygiaodich(ZonedDateTime.now());
                LichSuDongTien lichSuDongTien = lichSuDongTienMapper.toEntity(lichSuDongTienDTO);
                lichSuDongTienRepository.save(lichSuDongTien);
            }
            if (hopDongRepository.findOne(id).getLoaihopdong().equals(LOAIHOPDONG.VAYLAI)) {
                double tienvay = vayLaiRepository.findByHopDong(id).getTienvay();
                LichSuDongTienDTO lichSuDongTienDTO = new LichSuDongTienDTO();
                lichSuDongTienDTO.setHopDongId(id);
                lichSuDongTienDTO.setNgaybatdau(ZonedDateTime.now());
                lichSuDongTienDTO.setNgayketthuc(ZonedDateTime.now());
                lichSuDongTienDTO.setSotien(tienvay);
                lichSuDongTienDTO.setNhanVienId(nhanVienService.findByUserLogin().getId());
                lichSuDongTienDTO.setTrangthai(DONGTIEN.TRAGOC);
                lichSuDongTienDTO.setNgaygiaodich(ZonedDateTime.now());
                LichSuDongTien lichSuDongTien = lichSuDongTienMapper.toEntity(lichSuDongTienDTO);
                lichSuDongTienRepository.save(lichSuDongTien);
            }
        }

    }

    @Override
    public List<LichSuDongTienDTO> baoCao(DONGTIEN dongtien, LOAIHOPDONG loaihopdong,
            ZonedDateTime start, ZonedDateTime end
    ) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            Long cuaHangid = cuaHangService.findIDByUserLogin();
            List<LichSuDongTien> lichSuDongTiens = lichSuDongTienRepository.baocao(dongtien, loaihopdong, start, end, cuaHangid);
            List<LichSuDongTienDTO> collect = lichSuDongTiens.stream()
                    .map(lichSuDongTienMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            return collect;
        }
        throw new InternalServerErrorException("Khong co quyen");

    }

    @Override
    public List<LichSuDongTienDTO> baoCaoKeToan(DONGTIEN dongtien, LOAIHOPDONG loaihopdong,
            ZonedDateTime start, ZonedDateTime end, Long cuaHangid) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.KETOAN)) {
            List<LichSuDongTien> lichSuDongTiens = lichSuDongTienRepository.baocao(dongtien, loaihopdong, start, end, cuaHangid);
            List<LichSuDongTienDTO> collect = lichSuDongTiens.stream()
                    .map(lichSuDongTienMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            return collect;
        }
        throw new InternalServerErrorException("Khong co quyen");

    }

    @Override
    public List<LichSuDongTienDTO> baoCaoNV(DONGTIEN dongtien, LOAIHOPDONG loaihopdong,
            ZonedDateTime start, ZonedDateTime end,
            Long nhanVienid
    ) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            Long cuaHangid = cuaHangService.findIDByUserLogin();
            List<LichSuDongTien> lichSuDongTiens = lichSuDongTienRepository.baocaoNV(dongtien, loaihopdong, start, end, cuaHangid, nhanVienid);
            List<LichSuDongTienDTO> collect = lichSuDongTiens.stream()
                    .map(lichSuDongTienMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            return collect;
        }
        throw new InternalServerErrorException("Khong co quyen");

    }

    @Override
    public List<LichSuDongTienDTO> lichSuTraCham(DONGTIEN dongtien, LOAIHOPDONG loaihopdong
    ) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            ZonedDateTime ngayhientai = ZonedDateTime.now();
            List<LichSuDongTien> lichSuDongTiens = lichSuDongTienRepository.lichSuTraChamAdmin(dongtien, loaihopdong, ngayhientai);
//            for (LichSuDongTien lichSuDongTien : lichSuDongTiens) {
//                HopDong hopdong = hopDongRepository.findOne(lichSuDongTien.getHopDong().getId());
//                hopdong.setTrangthaihopdong(TRANGTHAIHOPDONG.QUAHAN);
//                hopDongRepository.save(hopdong);
//                
//
//            }
            List<LichSuDongTienDTO> collect = lichSuDongTiens.stream()
                    .map(lichSuDongTienMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            return collect;
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            Long cuaHangid = cuaHangService.findIDByUserLogin();
            ZonedDateTime ngayhientai = ZonedDateTime.now();
            List<LichSuDongTien> lichSuDongTiens = lichSuDongTienRepository.lichSuTraCham(dongtien, loaihopdong, ngayhientai, cuaHangid);

            List<LichSuDongTienDTO> collect = lichSuDongTiens.stream()
                    .map(lichSuDongTienMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            return collect;
        }
        throw new InternalServerErrorException("Khong co quyen");

    }

    @Override
    public List<LichSuDongTienDTO> lichSuTraHomNay(DONGTIEN dongtien, LOAIHOPDONG loaihopdong
    ) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            ZonedDateTime ngayhientai = ZonedDateTime.now();
            List<LichSuDongTien> lichSuDongTiens = lichSuDongTienRepository.lichSuTraHomNayAdmin(dongtien, loaihopdong, ngayhientai);
            List<LichSuDongTienDTO> collect = lichSuDongTiens.stream()
                    .map(lichSuDongTienMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            return collect;
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            Long cuaHangid = cuaHangService.findIDByUserLogin();
            ZonedDateTime ngayhientai = ZonedDateTime.now();
            List<LichSuDongTien> lichSuDongTiens = lichSuDongTienRepository.lichSuTraHomNay(dongtien, loaihopdong, ngayhientai, cuaHangid);
            List<LichSuDongTienDTO> collect = lichSuDongTiens.stream()
                    .map(lichSuDongTienMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            return collect;
        }
        throw new InternalServerErrorException("Khong co quyen");

    }

    @Override
    public List<LichSuDongTienDTO> findByTrangThai(DONGTIEN dongtien, Long id) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.KETOAN)) {
            ZonedDateTime ngayhientai = ZonedDateTime.now();
            List<LichSuDongTien> lichSuDongTiens = lichSuDongTienRepository.findByTrangThaiAdmin(dongtien, id);
            List<LichSuDongTienDTO> collect = lichSuDongTiens.stream()
                    .map(lichSuDongTienMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            return collect;
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            Long cuaHangid = cuaHangService.findIDByUserLogin();
            ZonedDateTime ngayhientai = ZonedDateTime.now();
            List<LichSuDongTien> lichSuDongTiens = lichSuDongTienRepository.findByTrangThai(dongtien, cuaHangid, id);
            List<LichSuDongTienDTO> collect = lichSuDongTiens.stream()
                    .map(lichSuDongTienMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            return collect;
        }
        throw new InternalServerErrorException("Khong co quyen");

    }

    @Override
    public List<LichSuDongTienDTO> findLichSuTraChamByHopDong(DONGTIEN dongtien, LOAIHOPDONG loaihopdong, Long hopdongid) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.KETOAN)) {
            ZonedDateTime ngayhientai = ZonedDateTime.now();
            List<LichSuDongTien> lichSuDongTiens = lichSuDongTienRepository.findLichSuTraChamByHopDongAdmin(dongtien, loaihopdong, ngayhientai, hopdongid);
            List<LichSuDongTienDTO> collect = lichSuDongTiens.stream()
                    .map(lichSuDongTienMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            return collect;
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            Long cuaHangid = cuaHangService.findIDByUserLogin();
            ZonedDateTime ngayhientai = ZonedDateTime.now();

            List<LichSuDongTien> lichSuDongTiens = lichSuDongTienRepository.findLichSuTraChamByHopDong(dongtien, loaihopdong, ngayhientai, cuaHangid, hopdongid);
            List<LichSuDongTienDTO> collect = lichSuDongTiens.stream()
                    .map(lichSuDongTienMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            return collect;
        }
        throw new InternalServerErrorException("Khong co quyen");

    }

//    public void kiemTra(id){
//        if(lichSuDongTienRepository.kiemtra(id)){
//            
//        }
//    }
}
