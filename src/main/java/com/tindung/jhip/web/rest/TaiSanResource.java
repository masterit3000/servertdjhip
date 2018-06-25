package com.tindung.jhip.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tindung.jhip.service.TaiSanService;
import com.tindung.jhip.web.rest.errors.BadRequestAlertException;
import com.tindung.jhip.web.rest.util.HeaderUtil;
import com.tindung.jhip.service.dto.TaiSanDTO;
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
 * REST controller for managing TaiSan.
 */
@RestController
@RequestMapping("/api")
public class TaiSanResource {

    private final Logger log = LoggerFactory.getLogger(TaiSanResource.class);

    private static final String ENTITY_NAME = "taiSan";

    private final TaiSanService taiSanService;

    public TaiSanResource(TaiSanService taiSanService) {
        this.taiSanService = taiSanService;
    }

    /**
     * POST  /tai-sans : Create a new taiSan.
     *
     * @param taiSanDTO the taiSanDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taiSanDTO, or with status 400 (Bad Request) if the taiSan has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tai-sans")
    @Timed
    public ResponseEntity<TaiSanDTO> createTaiSan(@Valid @RequestBody TaiSanDTO taiSanDTO) throws URISyntaxException {
        log.debug("REST request to save TaiSan : {}", taiSanDTO);
        if (taiSanDTO.getId() != null) {
            throw new BadRequestAlertException("A new taiSan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaiSanDTO result = taiSanService.save(taiSanDTO);
        return ResponseEntity.created(new URI("/api/tai-sans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tai-sans : Updates an existing taiSan.
     *
     * @param taiSanDTO the taiSanDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taiSanDTO,
     * or with status 400 (Bad Request) if the taiSanDTO is not valid,
     * or with status 500 (Internal Server Error) if the taiSanDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tai-sans")
    @Timed
    public ResponseEntity<TaiSanDTO> updateTaiSan(@Valid @RequestBody TaiSanDTO taiSanDTO) throws URISyntaxException {
        log.debug("REST request to update TaiSan : {}", taiSanDTO);
        if (taiSanDTO.getId() == null) {
            return createTaiSan(taiSanDTO);
        }
        TaiSanDTO result = taiSanService.save(taiSanDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, taiSanDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tai-sans : get all the taiSans.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of taiSans in body
     */
    @GetMapping("/tai-sans")
    @Timed
    public List<TaiSanDTO> getAllTaiSans() {
        log.debug("REST request to get all TaiSans");
        return taiSanService.findAll();
        }

    /**
     * GET  /tai-sans/:id : get the "id" taiSan.
     *
     * @param id the id of the taiSanDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taiSanDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tai-sans/{id}")
    @Timed
    public ResponseEntity<TaiSanDTO> getTaiSan(@PathVariable Long id) {
        log.debug("REST request to get TaiSan : {}", id);
        TaiSanDTO taiSanDTO = taiSanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(taiSanDTO));
    }

    /**
     * DELETE  /tai-sans/:id : delete the "id" taiSan.
     *
     * @param id the id of the taiSanDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tai-sans/{id}")
    @Timed
    public ResponseEntity<Void> deleteTaiSan(@PathVariable Long id) {
        log.debug("REST request to delete TaiSan : {}", id);
        taiSanService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
