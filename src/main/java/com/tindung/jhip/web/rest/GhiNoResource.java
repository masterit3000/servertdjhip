package com.tindung.jhip.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tindung.jhip.domain.enumeration.LOAIHOPDONG;
import com.tindung.jhip.service.GhiNoService;
import com.tindung.jhip.web.rest.errors.BadRequestAlertException;
import com.tindung.jhip.web.rest.util.HeaderUtil;
import com.tindung.jhip.service.dto.GhiNoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing GhiNo.
 */
@RestController
@RequestMapping("/api")
public class GhiNoResource {

    private final Logger log = LoggerFactory.getLogger(GhiNoResource.class);

    private static final String ENTITY_NAME = "ghiNo";

    private final GhiNoService ghiNoService;

    public GhiNoResource(GhiNoService ghiNoService) {
        this.ghiNoService = ghiNoService;
    }

    /**
     * POST /ghi-nos : Create a new ghiNo.
     *
     * @param ghiNoDTO the ghiNoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new ghiNoDTO, or with status 400 (Bad Request) if the ghiNo has already
     * an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ghi-nos")
    @Timed
    public ResponseEntity<GhiNoDTO> createGhiNo(@Valid @RequestBody GhiNoDTO ghiNoDTO) throws URISyntaxException {
        log.debug("REST request to save GhiNo : {}", ghiNoDTO);
        if (ghiNoDTO.getId() != null) {
            throw new BadRequestAlertException("A new ghiNo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GhiNoDTO result = ghiNoService.save(ghiNoDTO);
        return ResponseEntity.created(new URI("/api/ghi-nos/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /ghi-nos : Updates an existing ghiNo.
     *
     * @param ghiNoDTO the ghiNoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * ghiNoDTO, or with status 400 (Bad Request) if the ghiNoDTO is not valid,
     * or with status 500 (Internal Server Error) if the ghiNoDTO couldn't be
     * updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ghi-nos")
    @Timed
    public ResponseEntity<GhiNoDTO> updateGhiNo(@Valid @RequestBody GhiNoDTO ghiNoDTO) throws URISyntaxException {
        log.debug("REST request to update GhiNo : {}", ghiNoDTO);
        if (ghiNoDTO.getId() == null) {
            return createGhiNo(ghiNoDTO);
        }
        GhiNoDTO result = ghiNoService.save(ghiNoDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ghiNoDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET /ghi-nos : get all the ghiNos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ghiNos in
     * body
     */
    @GetMapping("/ghi-nos")
    @Timed
    public List<GhiNoDTO> getAllGhiNos() {
        log.debug("REST request to get all GhiNos");
        return ghiNoService.findAll();
    }

    @GetMapping("/ghi-nos-by-hopdong/{id}")
    @Timed
    public List<GhiNoDTO> getGhiNoByHopDong(@PathVariable Long id) {
        log.debug("REST request to get all GhiNos");
        return ghiNoService.findByHopDong(id);
    }

    /**
     * GET /ghi-nos/:id : get the "id" ghiNo.
     *
     * @param id the id of the ghiNoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * ghiNoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ghi-nos/{id}")
    @Timed
    public ResponseEntity<GhiNoDTO> getGhiNo(@PathVariable Long id) {
        log.debug("REST request to get GhiNo : {}", id);
        GhiNoDTO ghiNoDTO = ghiNoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ghiNoDTO));
    }

    /**
     * DELETE /ghi-nos/:id : delete the "id" ghiNo.
     *
     * @param id the id of the ghiNoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ghi-nos/{id}")
    @Timed
    public ResponseEntity<Void> deleteGhiNo(@PathVariable Long id) {
        log.debug("REST request to delete GhiNo : {}", id);
        ghiNoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/bao-cao-ghi-nos/{loaihopdong}/{start}/{end}")
    @Timed
    public List<GhiNoDTO> baoCao(@PathVariable(name = "loaihopdong") String loaihopdong, @PathVariable(name = "start") String start, @PathVariable(name = "end") String end) {
        log.debug("REST request to get all GhiNos");
        LOAIHOPDONG loai = LOAIHOPDONG.VAYLAI;
        ZonedDateTime timeStart = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy MM dd")).atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime timeEnd = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy MM dd")).atStartOfDay(ZoneId.systemDefault()).plusSeconds(86399);
        //doạn này convert loai thu chi dạng text sang dạng enum THUCHI
        switch (loaihopdong) {
            case "0":
                loai = LOAIHOPDONG.VAYLAI;
                break;
            case "1":
                loai = LOAIHOPDONG.BATHO;
                break;
        }
        return ghiNoService.baoCao(loai, timeStart, timeEnd);
    }

}
