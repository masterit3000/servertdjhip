package com.tindung.jhip.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tindung.jhip.service.AnhTaiSanService;
import com.tindung.jhip.web.rest.errors.BadRequestAlertException;
import com.tindung.jhip.web.rest.util.HeaderUtil;
import com.tindung.jhip.service.dto.AnhTaiSanDTO;
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
 * REST controller for managing AnhTaiSan.
 */
@RestController
@RequestMapping("/api")
public class AnhTaiSanResource {

    private final Logger log = LoggerFactory.getLogger(AnhTaiSanResource.class);

    private static final String ENTITY_NAME = "anhTaiSan";

    private final AnhTaiSanService anhTaiSanService;

    public AnhTaiSanResource(AnhTaiSanService anhTaiSanService) {
        this.anhTaiSanService = anhTaiSanService;
    }

    /**
     * POST  /anh-tai-sans : Create a new anhTaiSan.
     *
     * @param anhTaiSanDTO the anhTaiSanDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new anhTaiSanDTO, or with status 400 (Bad Request) if the anhTaiSan has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/anh-tai-sans")
    @Timed
    public ResponseEntity<AnhTaiSanDTO> createAnhTaiSan(@Valid @RequestBody AnhTaiSanDTO anhTaiSanDTO) throws URISyntaxException {
        log.debug("REST request to save AnhTaiSan : {}", anhTaiSanDTO);
        if (anhTaiSanDTO.getId() != null) {
            throw new BadRequestAlertException("A new anhTaiSan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnhTaiSanDTO result = anhTaiSanService.save(anhTaiSanDTO);
        return ResponseEntity.created(new URI("/api/anh-tai-sans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /anh-tai-sans : Updates an existing anhTaiSan.
     *
     * @param anhTaiSanDTO the anhTaiSanDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated anhTaiSanDTO,
     * or with status 400 (Bad Request) if the anhTaiSanDTO is not valid,
     * or with status 500 (Internal Server Error) if the anhTaiSanDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/anh-tai-sans")
    @Timed
    public ResponseEntity<AnhTaiSanDTO> updateAnhTaiSan(@Valid @RequestBody AnhTaiSanDTO anhTaiSanDTO) throws URISyntaxException {
        log.debug("REST request to update AnhTaiSan : {}", anhTaiSanDTO);
        if (anhTaiSanDTO.getId() == null) {
            return createAnhTaiSan(anhTaiSanDTO);
        }
        AnhTaiSanDTO result = anhTaiSanService.save(anhTaiSanDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, anhTaiSanDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /anh-tai-sans : get all the anhTaiSans.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of anhTaiSans in body
     */
    @GetMapping("/anh-tai-sans")
    @Timed
    public List<AnhTaiSanDTO> getAllAnhTaiSans() {
        log.debug("REST request to get all AnhTaiSans");
        return anhTaiSanService.findAll();
        }

    /**
     * GET  /anh-tai-sans/:id : get the "id" anhTaiSan.
     *
     * @param id the id of the anhTaiSanDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the anhTaiSanDTO, or with status 404 (Not Found)
     */
    @GetMapping("/anh-tai-sans/{id}")
    @Timed
    public ResponseEntity<AnhTaiSanDTO> getAnhTaiSan(@PathVariable Long id) {
        log.debug("REST request to get AnhTaiSan : {}", id);
        AnhTaiSanDTO anhTaiSanDTO = anhTaiSanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(anhTaiSanDTO));
    }

    /**
     * DELETE  /anh-tai-sans/:id : delete the "id" anhTaiSan.
     *
     * @param id the id of the anhTaiSanDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/anh-tai-sans/{id}")
    @Timed
    public ResponseEntity<Void> deleteAnhTaiSan(@PathVariable Long id) {
        log.debug("REST request to delete AnhTaiSan : {}", id);
        anhTaiSanService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
