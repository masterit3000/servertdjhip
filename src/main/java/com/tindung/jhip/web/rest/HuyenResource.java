package com.tindung.jhip.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tindung.jhip.security.AuthoritiesConstants;
import com.tindung.jhip.service.HuyenService;
import com.tindung.jhip.web.rest.errors.BadRequestAlertException;
import com.tindung.jhip.web.rest.util.HeaderUtil;
import com.tindung.jhip.service.dto.HuyenDTO;
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
 * REST controller for managing Huyen.
 */
@RestController
@RequestMapping("/api")
public class HuyenResource {

    private final Logger log = LoggerFactory.getLogger(HuyenResource.class);

    private static final String ENTITY_NAME = "huyen";

    private final HuyenService huyenService;

    public HuyenResource(HuyenService huyenService) {
        this.huyenService = huyenService;
    }

    /**
     * POST /huyens : Create a new huyen.
     *
     * @param huyenDTO the huyenDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new huyenDTO, or with status 400 (Bad Request) if the huyen has already
     * an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/huyens")
    @Timed
    public ResponseEntity<HuyenDTO> createHuyen(@Valid @RequestBody HuyenDTO huyenDTO) throws URISyntaxException {
        log.debug("REST request to save Huyen : {}", huyenDTO);
        if (huyenDTO.getId() != null) {
            throw new BadRequestAlertException("A new huyen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HuyenDTO result = huyenService.save(huyenDTO);
        return ResponseEntity.created(new URI("/api/huyens/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /huyens : Updates an existing huyen.
     *
     * @param huyenDTO the huyenDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * huyenDTO, or with status 400 (Bad Request) if the huyenDTO is not valid,
     * or with status 500 (Internal Server Error) if the huyenDTO couldn't be
     * updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/huyens")
    @Timed
    public ResponseEntity<HuyenDTO> updateHuyen(@Valid @RequestBody HuyenDTO huyenDTO) throws URISyntaxException {
        log.debug("REST request to update Huyen : {}", huyenDTO);
        if (huyenDTO.getId() == null) {
            return createHuyen(huyenDTO);
        }
        HuyenDTO result = huyenService.save(huyenDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, huyenDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET /huyens : get all the huyens.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of huyens in
     * body
     */
    @GetMapping("/huyens")
    @Timed
    public List<HuyenDTO> getAllHuyens() {
        log.debug("REST request to get all Huyens");
        return huyenService.findAll();
    }

    @GetMapping("/huyens/tinh/{idtinh}")
    @Timed
    public List<HuyenDTO> getAllHuyensbyTinh(@PathVariable(name = "idtinh") Long idtinh) {
        log.debug("REST request to get all Huyens");
        return huyenService.findAllByTinh(idtinh);
    }

    /**
     * GET /huyens/:id : get the "id" huyen.
     *
     * @param id the id of the huyenDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * huyenDTO, or with status 404 (Not Found)
     */
    @GetMapping("/huyens/{id}")
    @Timed
    public ResponseEntity<HuyenDTO> getHuyen(@PathVariable Long id) {
        log.debug("REST request to get Huyen : {}", id);
        HuyenDTO huyenDTO = huyenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(huyenDTO));
    }

    /**
     * DELETE /huyens/:id : delete the "id" huyen.
     *
     * @param id the id of the huyenDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/huyens/{id}")
    @Timed
    public ResponseEntity<Void> deleteHuyen(@PathVariable Long id) {
        log.debug("REST request to delete Huyen : {}", id);
        huyenService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
