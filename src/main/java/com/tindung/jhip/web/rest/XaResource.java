package com.tindung.jhip.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tindung.jhip.service.XaService;
import com.tindung.jhip.web.rest.errors.BadRequestAlertException;
import com.tindung.jhip.web.rest.util.HeaderUtil;
import com.tindung.jhip.service.dto.XaDTO;
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
 * REST controller for managing Xa.
 */
@RestController
@RequestMapping("/api")
public class XaResource {

    private final Logger log = LoggerFactory.getLogger(XaResource.class);

    private static final String ENTITY_NAME = "xa";

    private final XaService xaService;

    public XaResource(XaService xaService) {
        this.xaService = xaService;
    }

    /**
     * POST  /xas : Create a new xa.
     *
     * @param xaDTO the xaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new xaDTO, or with status 400 (Bad Request) if the xa has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/xas")
    @Timed
    public ResponseEntity<XaDTO> createXa(@Valid @RequestBody XaDTO xaDTO) throws URISyntaxException {
        log.debug("REST request to save Xa : {}", xaDTO);
        if (xaDTO.getId() != null) {
            throw new BadRequestAlertException("A new xa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        XaDTO result = xaService.save(xaDTO);
        return ResponseEntity.created(new URI("/api/xas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /xas : Updates an existing xa.
     *
     * @param xaDTO the xaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated xaDTO,
     * or with status 400 (Bad Request) if the xaDTO is not valid,
     * or with status 500 (Internal Server Error) if the xaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/xas")
    @Timed
    public ResponseEntity<XaDTO> updateXa(@Valid @RequestBody XaDTO xaDTO) throws URISyntaxException {
        log.debug("REST request to update Xa : {}", xaDTO);
        if (xaDTO.getId() == null) {
            return createXa(xaDTO);
        }
        XaDTO result = xaService.save(xaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, xaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /xas : get all the xas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of xas in body
     */
    @GetMapping("/xas")
    @Timed
    public List<XaDTO> getAllXas() {
        log.debug("REST request to get all Xas");
        return xaService.findAll();
        }

    /**
     * GET  /xas/:id : get the "id" xa.
     *
     * @param id the id of the xaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the xaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/xas/{id}")
    @Timed
    public ResponseEntity<XaDTO> getXa(@PathVariable Long id) {
        log.debug("REST request to get Xa : {}", id);
        XaDTO xaDTO = xaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(xaDTO));
    }

    /**
     * DELETE  /xas/:id : delete the "id" xa.
     *
     * @param id the id of the xaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/xas/{id}")
    @Timed
    public ResponseEntity<Void> deleteXa(@PathVariable Long id) {
        log.debug("REST request to delete Xa : {}", id);
        xaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
