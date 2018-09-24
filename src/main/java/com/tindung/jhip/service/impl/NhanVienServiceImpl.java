package com.tindung.jhip.service.impl;

import com.tindung.jhip.service.NhanVienService;
import com.tindung.jhip.domain.NhanVien;
import com.tindung.jhip.domain.User;
import com.tindung.jhip.repository.NhanVienRepository;
import com.tindung.jhip.repository.UserRepository;
import com.tindung.jhip.security.AuthoritiesConstants;
import com.tindung.jhip.security.SecurityUtils;
import com.tindung.jhip.service.NhatKyService;
import com.tindung.jhip.service.UserService;
import com.tindung.jhip.service.dto.NhanVienDTO;
import com.tindung.jhip.service.dto.NhatKyDTO;
import com.tindung.jhip.service.mapper.NhanVienMapper;
import com.tindung.jhip.service.mapper.UserMapper;
import com.tindung.jhip.web.rest.errors.InternalServerErrorException;
import java.time.ZonedDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing NhanVien.
 */
@Service
@Transactional
public class NhanVienServiceImpl implements NhanVienService {

    private final Logger log = LoggerFactory.getLogger(NhanVienServiceImpl.class);

    private final NhanVienRepository nhanVienRepository;
    private final UserRepository userRepository;
    private final UserService service;

    private final NhanVienMapper nhanVienMapper;
    private final NhatKyService nhatKyService;

    public NhanVienServiceImpl(NhanVienRepository nhanVienRepository, UserRepository userRepository, UserService service, NhanVienMapper nhanVienMapper, NhatKyService nhatKyService) {
        this.nhanVienRepository = nhanVienRepository;
        this.userRepository = userRepository;
        this.service = service;
        this.nhanVienMapper = nhanVienMapper;
        this.nhatKyService = nhatKyService;
    }

    /**
     * Save a nhanVien.
     *
     * @param nhanVienDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public NhanVienDTO save(NhanVienDTO nhanVienDTO) {
        log.debug("Request to save NhanVien : {}", nhanVienDTO);
        NhanVien nhanVien = nhanVienMapper.toEntity(nhanVienDTO);
        nhanVien = nhanVienRepository.save(nhanVien);
        User user = nhanVien.getUser();
//        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + user.getId());
        if (user != null) {
//            System.out.println(findOne);
            User findOne = userRepository.findOne(user.getId());
            findOne.setActivated(true);
            userRepository.save(findOne);

        }

        NhatKyDTO nhatKy = new NhatKyDTO();
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)) {
            String login = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
            NhanVien nv = nhanVienRepository.findOneByUser(login).get();
            Long cuaHangid = nv.getCuaHang().getId();
            nhatKy.setCuaHangId(cuaHangid);
            nhatKy.setNhanVienId(nv.getId());
        }
        String login = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
        nhatKy.setThoiGian(ZonedDateTime.now());
        nhatKy.setNoiDung("Thêm mới nhân viên");
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            nhatKy.setNoiDung("Thêm mới nhân viên- " + login);
        }
        nhatKyService.save(nhatKy);

        return nhanVienMapper.toDto(nhanVien);
    }

    /**
     * Get all the nhanViens.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<NhanVienDTO> findAll() {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            log.debug("Request to get all NhanViens");
            return nhanVienRepository.findAll().stream()
                    .map(nhanVienMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            log.debug("Request to get all NhanViens");
            String login = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
            NhanVien nv = nhanVienRepository.findOneByUser(login).get();
            Long cuaHangid = nv.getCuaHang().getId();
            return nhanVienRepository.findAllByCuaHang(cuaHangid).stream()
                    .map(nhanVienMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
        }
        throw new InternalServerErrorException("Khong co quyen");
    }

    @Override
    @Transactional(readOnly = true)
    public List<NhanVienDTO> findAllByCuaHang(Long id) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)||SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.KETOAN)) {
            log.debug("Request to get all NhanViens");
            return nhanVienRepository.findAllByCuaHang(id).stream()
                    .map(nhanVienMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
        }
        throw new InternalServerErrorException("Khong co quyen");
    }

    /**
     * Get one nhanVien by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public NhanVienDTO findOne(Long id
    ) {
        log.debug("Request to get NhanVien : {}", id);
        NhanVien nhanVien = nhanVienRepository.findOne(id);
        return nhanVienMapper.toDto(nhanVien);
    }

    /**
     * Delete the nhanVien by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id
    ) {
        log.debug("Request to delete NhanVien : {}", id);
        nhanVienRepository.delete(id);
    }

    @Override
    public NhanVienDTO findByUserLogin() {
        String user = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
//        User get = userRepository.findOneByLogin(user).get();
        log.debug("Request tim  NhanVien theo ussername : {}", user);
        return nhanVienMapper.toDto(nhanVienRepository.findOneByUser(user).orElseThrow(() -> new InternalServerErrorException("Current user login not found")));
    }

    @Override
    public List<NhanVienDTO> findByNameOrCMND(String key
    ) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            log.debug("Request to get all KhachHangs");
            key = new StringBuffer("%").append(key).append("%").toString();
            return nhanVienRepository.findByNameOrCMND(key).stream()
                    .map(nhanVienMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STOREADMIN)
                || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STAFFADMIN)) {
            log.debug("Request to get all KhachHangs");
            key = new StringBuffer("%").append(key).append("%").toString();
            String login = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
            NhanVien nv = nhanVienRepository.findOneByUser(login).get();
            Long cuaHangid = nv.getCuaHang().getId();
            return nhanVienRepository.findByNameOrCMND(key, cuaHangid).stream()
                    .map(nhanVienMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
        }
        throw new InternalServerErrorException("Khong co quyen");
    }

}
