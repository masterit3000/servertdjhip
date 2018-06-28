package com.tindung.jhip.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tindung.jhip.service.KhachHangService;
import com.tindung.jhip.web.rest.errors.BadRequestAlertException;
import com.tindung.jhip.web.rest.util.HeaderUtil;
import com.tindung.jhip.service.dto.KhachHangDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * REST controller for managing KhachHang.
 */
@RestController
@RequestMapping("/api")
public class KhachHangResource {

    private final Logger log = LoggerFactory.getLogger(KhachHangResource.class);

    private static final String ENTITY_NAME = "khachHang";

    private final KhachHangService khachHangService;

    public KhachHangResource(KhachHangService khachHangService) {
        this.khachHangService = khachHangService;
    }

    /**
     * POST  /khach-hangs : Create a new khachHang.
     *
     * @param khachHangDTO the khachHangDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new khachHangDTO, or with status 400 (Bad Request) if the khachHang has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/khach-hangs")
    @Timed
    public ResponseEntity<KhachHangDTO> createKhachHang(@Valid @RequestBody KhachHangDTO khachHangDTO) throws URISyntaxException {
        log.debug("REST request to save KhachHang : {}", khachHangDTO);
        if (khachHangDTO.getId() != null) {
            throw new BadRequestAlertException("A new khachHang cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        KhachHangDTO result = khachHangService.save(khachHangDTO);
        return ResponseEntity.created(new URI("/api/khach-hangs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /khach-hangs : Updates an existing khachHang.
     *
     * @param khachHangDTO the khachHangDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated khachHangDTO,
     * or with status 400 (Bad Request) if the khachHangDTO is not valid,
     * or with status 500 (Internal Server Error) if the khachHangDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/khach-hangs")
    @Timed
    public ResponseEntity<KhachHangDTO> updateKhachHang(@Valid @RequestBody KhachHangDTO khachHangDTO) throws URISyntaxException {
        log.debug("REST request to update KhachHang : {}", khachHangDTO);
        if (khachHangDTO.getId() == null) {
            return createKhachHang(khachHangDTO);
        }
        KhachHangDTO result = khachHangService.save(khachHangDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, khachHangDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /khach-hangs : get all the khachHangs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of khachHangs in body
     */
    @GetMapping("/khach-hangs")
    @Timed
    public List<KhachHangDTO> getAllKhachHangs() {
        log.debug("REST request to get all KhachHangs");
        return khachHangService.findAll();
        }

    /**
     * GET  /khach-hangs/:id : get the "id" khachHang.
     *
     * @param id the id of the khachHangDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the khachHangDTO, or with status 404 (Not Found)
     */
    @GetMapping("/khach-hangs/{id}")
    @Timed
    public ResponseEntity<KhachHangDTO> getKhachHang(@PathVariable Long id) {
        log.debug("REST request to get KhachHang : {}", id);
        KhachHangDTO khachHangDTO = khachHangService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(khachHangDTO));
    }

    /**
     * DELETE  /khach-hangs/:id : delete the "id" khachHang.
     *
     * @param id the id of the khachHangDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/khach-hangs/{id}")
    @Timed
    public ResponseEntity<Void> deleteKhachHang(@PathVariable Long id) {
        log.debug("REST request to delete KhachHang : {}", id);
        khachHangService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
