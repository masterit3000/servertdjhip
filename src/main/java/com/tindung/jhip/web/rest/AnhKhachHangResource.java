package com.tindung.jhip.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tindung.jhip.service.AnhKhachHangService;
import com.tindung.jhip.web.rest.errors.BadRequestAlertException;
import com.tindung.jhip.web.rest.util.HeaderUtil;
import com.tindung.jhip.service.dto.AnhKhachHangDTO;
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

/**
 * REST controller for managing AnhKhachHang.
 */
@RestController
@RequestMapping("/api")
public class AnhKhachHangResource {

    private final Logger log = LoggerFactory.getLogger(AnhKhachHangResource.class);

    private static final String ENTITY_NAME = "anhKhachHang";

    private final AnhKhachHangService anhKhachHangService;

    public AnhKhachHangResource(AnhKhachHangService anhKhachHangService) {
        this.anhKhachHangService = anhKhachHangService;
    }

    /**
     * POST  /anh-khach-hangs : Create a new anhKhachHang.
     *
     * @param anhKhachHangDTO the anhKhachHangDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new anhKhachHangDTO, or with status 400 (Bad Request) if the anhKhachHang has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/anh-khach-hangs")
    @Timed
    public ResponseEntity<AnhKhachHangDTO> createAnhKhachHang(@Valid @RequestBody AnhKhachHangDTO anhKhachHangDTO) throws URISyntaxException {
        log.debug("REST request to save AnhKhachHang : {}", anhKhachHangDTO);
        if (anhKhachHangDTO.getId() != null) {
            throw new BadRequestAlertException("A new anhKhachHang cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnhKhachHangDTO result = anhKhachHangService.save(anhKhachHangDTO);
        return ResponseEntity.created(new URI("/api/anh-khach-hangs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /anh-khach-hangs : Updates an existing anhKhachHang.
     *
     * @param anhKhachHangDTO the anhKhachHangDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated anhKhachHangDTO,
     * or with status 400 (Bad Request) if the anhKhachHangDTO is not valid,
     * or with status 500 (Internal Server Error) if the anhKhachHangDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/anh-khach-hangs")
    @Timed
    public ResponseEntity<AnhKhachHangDTO> updateAnhKhachHang(@Valid @RequestBody AnhKhachHangDTO anhKhachHangDTO) throws URISyntaxException {
        log.debug("REST request to update AnhKhachHang : {}", anhKhachHangDTO);
        if (anhKhachHangDTO.getId() == null) {
            return createAnhKhachHang(anhKhachHangDTO);
        }
        AnhKhachHangDTO result = anhKhachHangService.save(anhKhachHangDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, anhKhachHangDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /anh-khach-hangs : get all the anhKhachHangs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of anhKhachHangs in body
     */
    @GetMapping("/anh-khach-hangs")
    @Timed
    public List<AnhKhachHangDTO> getAllAnhKhachHangs() {
        log.debug("REST request to get all AnhKhachHangs");
        return anhKhachHangService.findAll();
        }

    /**
     * GET  /anh-khach-hangs/:id : get the "id" anhKhachHang.
     *
     * @param id the id of the anhKhachHangDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the anhKhachHangDTO, or with status 404 (Not Found)
     */
    @GetMapping("/anh-khach-hangs/{id}")
    @Timed
    public ResponseEntity<AnhKhachHangDTO> getAnhKhachHang(@PathVariable Long id) {
        log.debug("REST request to get AnhKhachHang : {}", id);
        AnhKhachHangDTO anhKhachHangDTO = anhKhachHangService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(anhKhachHangDTO));
    }

    /**
     * DELETE  /anh-khach-hangs/:id : delete the "id" anhKhachHang.
     *
     * @param id the id of the anhKhachHangDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/anh-khach-hangs/{id}")
    @Timed
    public ResponseEntity<Void> deleteAnhKhachHang(@PathVariable Long id) {
        log.debug("REST request to delete AnhKhachHang : {}", id);
        anhKhachHangService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
