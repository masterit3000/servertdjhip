package com.tindung.jhip.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tindung.jhip.security.AuthoritiesConstants;
import com.tindung.jhip.service.TinhService;
import com.tindung.jhip.web.rest.errors.BadRequestAlertException;
import com.tindung.jhip.web.rest.util.HeaderUtil;
import com.tindung.jhip.service.dto.TinhDTO;
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
import org.springframework.security.access.annotation.Secured;

/**
 * REST controller for managing Tinh.
 */
@RestController
@RequestMapping("/api")
@Secured(AuthoritiesConstants.ADMIN)
public class TinhResource {

    private final Logger log = LoggerFactory.getLogger(TinhResource.class);

    private static final String ENTITY_NAME = "tinh";

    private final TinhService tinhService;

    public TinhResource(TinhService tinhService) {
        this.tinhService = tinhService;
    }

    /**
     * POST  /tinhs : Create a new tinh.
     *
     * @param tinhDTO the tinhDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tinhDTO, or with status 400 (Bad Request) if the tinh has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tinhs")
    @Timed
    public ResponseEntity<TinhDTO> createTinh(@Valid @RequestBody TinhDTO tinhDTO) throws URISyntaxException {
        log.debug("REST request to save Tinh : {}", tinhDTO);
        if (tinhDTO.getId() != null) {
            throw new BadRequestAlertException("A new tinh cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TinhDTO result = tinhService.save(tinhDTO);
        return ResponseEntity.created(new URI("/api/tinhs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tinhs : Updates an existing tinh.
     *
     * @param tinhDTO the tinhDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tinhDTO,
     * or with status 400 (Bad Request) if the tinhDTO is not valid,
     * or with status 500 (Internal Server Error) if the tinhDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tinhs")
    @Timed
    public ResponseEntity<TinhDTO> updateTinh(@Valid @RequestBody TinhDTO tinhDTO) throws URISyntaxException {
        log.debug("REST request to update Tinh : {}", tinhDTO);
        if (tinhDTO.getId() == null) {
            return createTinh(tinhDTO);
        }
        TinhDTO result = tinhService.save(tinhDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tinhDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tinhs : get all the tinhs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tinhs in body
     */
    @GetMapping("/tinhs")
    @Timed
    public List<TinhDTO> getAllTinhs() {
        log.debug("REST request to get all Tinhs");
        return tinhService.findAll();
        }

    /**
     * GET  /tinhs/:id : get the "id" tinh.
     *
     * @param id the id of the tinhDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tinhDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tinhs/{id}")
    @Timed
    public ResponseEntity<TinhDTO> getTinh(@PathVariable Long id) {
        log.debug("REST request to get Tinh : {}", id);
        TinhDTO tinhDTO = tinhService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tinhDTO));
    }

    /**
     * DELETE  /tinhs/:id : delete the "id" tinh.
     *
     * @param id the id of the tinhDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tinhs/{id}")
    @Timed
    public ResponseEntity<Void> deleteTinh(@PathVariable Long id) {
        log.debug("REST request to delete Tinh : {}", id);
        tinhService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
