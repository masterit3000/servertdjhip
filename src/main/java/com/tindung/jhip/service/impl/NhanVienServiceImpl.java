package com.tindung.jhip.service.impl;

import com.tindung.jhip.service.NhanVienService;
import com.tindung.jhip.domain.NhanVien;
import com.tindung.jhip.domain.User;
import com.tindung.jhip.repository.NhanVienRepository;
import com.tindung.jhip.repository.UserRepository;
import com.tindung.jhip.security.SecurityUtils;
import com.tindung.jhip.service.dto.NhanVienDTO;
import com.tindung.jhip.service.mapper.NhanVienMapper;
import com.tindung.jhip.web.rest.errors.InternalServerErrorException;
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

    private final NhanVienMapper nhanVienMapper;

    public NhanVienServiceImpl(NhanVienRepository nhanVienRepository, NhanVienMapper nhanVienMapper, UserRepository userRepository) {
        this.nhanVienRepository = nhanVienRepository;
        this.nhanVienMapper = nhanVienMapper;
        this.userRepository = userRepository;
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
        log.debug("Request to get all NhanViens");
        return nhanVienRepository.findAll().stream()
                .map(nhanVienMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one nhanVien by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public NhanVienDTO findOne(Long id) {
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
    public void delete(Long id) {
        log.debug("Request to delete NhanVien : {}", id);
        nhanVienRepository.delete(id);
    }

    @Override
    public NhanVienDTO findByUserLogin() {
        String user = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
        User get = userRepository.findOneByLogin(user).get();
        log.debug("Request tim  NhanVien theo ussername : {}", user);
        return nhanVienMapper.toDto(nhanVienRepository.findOneByUser(get).get());
    }

}
