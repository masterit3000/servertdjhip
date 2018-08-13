package com.tindung.jhip.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tindung.jhip.service.CuaHangService;
import com.tindung.jhip.web.rest.errors.BadRequestAlertException;
import com.tindung.jhip.web.rest.util.HeaderUtil;
import com.tindung.jhip.service.dto.CuaHangDTO;
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
 * REST controller for managing CuaHang.
 */
@RestController
@RequestMapping("/api")
public class CuaHangResource {

    private final Logger log = LoggerFactory.getLogger(CuaHangResource.class);

    private static final String ENTITY_NAME = "cuaHang";

    private final CuaHangService cuaHangService;

    public CuaHangResource(CuaHangService cuaHangService) {
        this.cuaHangService = cuaHangService;
    }

    /**
     * POST  /cua-hangs : Create a new cuaHang.
     *
     * @param cuaHangDTO the cuaHangDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cuaHangDTO, or with status 400 (Bad Request) if the cuaHang has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cua-hangs")
    @Timed
    public ResponseEntity<CuaHangDTO> createCuaHang(@Valid @RequestBody CuaHangDTO cuaHangDTO) throws URISyntaxException {
        log.debug("REST request to save CuaHang : {}", cuaHangDTO);
        if (cuaHangDTO.getId() != null) {
            throw new BadRequestAlertException("A new cuaHang cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CuaHangDTO result = cuaHangService.save(cuaHangDTO);
        return ResponseEntity.created(new URI("/api/cua-hangs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cua-hangs : Updates an existing cuaHang.
     *
     * @param cuaHangDTO the cuaHangDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cuaHangDTO,
     * or with status 400 (Bad Request) if the cuaHangDTO is not valid,
     * or with status 500 (Internal Server Error) if the cuaHangDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cua-hangs")
    @Timed
    public ResponseEntity<CuaHangDTO> updateCuaHang(@Valid @RequestBody CuaHangDTO cuaHangDTO) throws URISyntaxException {
        log.debug("REST request to update CuaHang : {}", cuaHangDTO);
        if (cuaHangDTO.getId() == null) {
            return createCuaHang(cuaHangDTO);
        }
        CuaHangDTO result = cuaHangService.save(cuaHangDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cuaHangDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cua-hangs : get all the cuaHangs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cuaHangs in body
     */
    @GetMapping("/cua-hangs")
    @Timed
    public List<CuaHangDTO> getAllCuaHangs() {
        log.debug("REST request to get all CuaHangs");
        return cuaHangService.findAll();
        }
    @GetMapping("/tim-cua-hangs-by-name/{key}")
    @Timed
    public List<CuaHangDTO> findCuaHang(@PathVariable(name = "key") String key) {
        log.debug("REST request to get all CuaHangs");
        return cuaHangService.findByName(key);
        }

    /**
     * GET  /cua-hangs/:id : get the "id" cuaHang.
     *
     * @param id the id of the cuaHangDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cuaHangDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cua-hangs/{id}")
    @Timed
    public ResponseEntity<CuaHangDTO> getCuaHang(@PathVariable Long id) {
        log.debug("REST request to get CuaHang : {}", id);
        CuaHangDTO cuaHangDTO = cuaHangService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cuaHangDTO));
    }

    /**
     * DELETE  /cua-hangs/:id : delete the "id" cuaHang.
     *
     * @param id the id of the cuaHangDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cua-hangs/{id}")
    @Timed
    public ResponseEntity<Void> deleteCuaHang(@PathVariable Long id) {
        log.debug("REST request to delete CuaHang : {}", id);
        cuaHangService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
