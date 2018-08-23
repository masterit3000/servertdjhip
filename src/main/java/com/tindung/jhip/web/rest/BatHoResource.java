package com.tindung.jhip.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tindung.jhip.domain.enumeration.TRANGTHAIHOPDONG;
import com.tindung.jhip.service.BatHoService;
import com.tindung.jhip.service.LichSuDongTienService;
import com.tindung.jhip.service.LichSuThaoTacHopDongService;
import com.tindung.jhip.service.NhanVienService;
import com.tindung.jhip.service.UserService;
import com.tindung.jhip.web.rest.errors.BadRequestAlertException;
import com.tindung.jhip.web.rest.util.HeaderUtil;
import com.tindung.jhip.service.dto.BatHoDTO;
import com.tindung.jhip.service.dto.LichSuDongTienDTO;
import com.tindung.jhip.service.dto.LichSuThaoTacHopDongDTO;
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
 * REST controller for managing BatHo.
 */
@RestController
@RequestMapping("/api")
public class BatHoResource {

    private final Logger log = LoggerFactory.getLogger(BatHoResource.class);

    private static final String ENTITY_NAME = "batHo";

    private final BatHoService batHoService;
    private final UserService userService;
    private final NhanVienService nhanVienService;
    private final LichSuDongTienService lichSuDongTienService;
    private final LichSuThaoTacHopDongService lichSuThaoTacHopDongService;

    public BatHoResource(LichSuThaoTacHopDongService lichSuThaoTacHopDongService, LichSuDongTienService lichSuDongTienService, BatHoService batHoService, UserService userService, NhanVienService nhanVienService) {
        this.batHoService = batHoService;
        this.userService = userService;
        this.nhanVienService = nhanVienService;
        this.lichSuDongTienService = lichSuDongTienService;
        this.lichSuThaoTacHopDongService = lichSuThaoTacHopDongService;

    }

    /**
     * POST /bat-hos : Create a new batHo.
     *
     * @param batHoDTO the batHoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new batHoDTO, or with status 400 (Bad Request) if the batHo has already
     * an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bat-hos")
    @Timed
    public ResponseEntity<BatHoDTO> createBatHo(@Valid @RequestBody BatHoDTO batHoDTO) throws URISyntaxException {
        log.debug("REST request to save BatHo : {}", batHoDTO);
        if (batHoDTO.getId() != null) {
            throw new BadRequestAlertException("A new batHo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BatHoDTO result = batHoService.save(batHoDTO);

        //
        return ResponseEntity.created(new URI("/api/bat-hos/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PostMapping("/dao-bat-hos/{id}/{mahopdong}")
    @Timed
    public ResponseEntity<BatHoDTO> vay(@RequestBody BatHoDTO batHoDTO, @PathVariable(name = "id") Long id, @PathVariable(name = "mahopdong") String mahopdong) throws URISyntaxException {
        log.debug("REST request to save BatHo : {}", batHoDTO);
        if (batHoDTO.getId() != null) {
            throw new BadRequestAlertException("A new batHo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BatHoDTO result = batHoService.daoHo(batHoDTO, id, mahopdong);

        //
        //save lich su thao tac bat ho
        //
        return ResponseEntity.created(new URI("/api/dao-bat-hos/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /bat-hos : Updates an existing batHo.
     *
     * @param batHoDTO the batHoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * batHoDTO, or with status 400 (Bad Request) if the batHoDTO is not valid,
     * or with status 500 (Internal Server Error) if the batHoDTO couldn't be
     * updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bat-hos")
    @Timed
    public ResponseEntity<BatHoDTO> updateBatHo(@Valid @RequestBody BatHoDTO batHoDTO) throws URISyntaxException {
        log.debug("REST request to update BatHo : {}", batHoDTO);
        if (batHoDTO.getId() == null) {
            return createBatHo(batHoDTO);
        }
        BatHoDTO result = batHoService.save(batHoDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, batHoDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET /bat-hos : get all the batHos.
     *
     * @param trangthai
     * @return the ResponseEntity with status 200 (OK) and the list of batHos in
     * body
     */
    @GetMapping("/bat-hos/{trangthai}")
    @Timed
    public List<BatHoDTO> getAllBatHos(@PathVariable(name = "trangthai") String trangthai) {
        log.debug("REST request to get all BatHos");
        TRANGTHAIHOPDONG trangthaihopdong = TRANGTHAIHOPDONG.DANGVAY;
        switch (trangthai) {
            case "0":
                trangthaihopdong = TRANGTHAIHOPDONG.QUAHAN;
                break;
            case "1":
                trangthaihopdong = TRANGTHAIHOPDONG.DANGVAY;
                break;
            case "2":
                trangthaihopdong = TRANGTHAIHOPDONG.DADONG;
                break;
        }
        return batHoService.findAll(trangthaihopdong);
    }

    /**
     * Get lịch sử đóng tiền theo id của hợp đồng
     *
     * @param id: id của hợp đồng tương ứng
     * @return list lịch sử đóng tiền theo hợp đồng
     */
    /**
     * GET /bat-hos/:id : get the "id" batHo.
     *
     * @param id the id of the batHoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * batHoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/find-one-bat-hos/{id}")
    @Timed
    public ResponseEntity<BatHoDTO> getBatHo(@PathVariable Long id) {
        log.debug("REST request to get BatHo : {}", id);

        BatHoDTO batHoDTO = batHoService.findOne(id);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(batHoDTO));
    }

    @GetMapping("/tim-bat-hos/{id}")
    @Timed
    public ResponseEntity<BatHoDTO> getBatHoByHopDong(@PathVariable Long id) {
        log.debug("REST request to get BatHo : {}", id);

        BatHoDTO batHoDTO = batHoService.findByHopDong(id);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(batHoDTO));
    }

    @GetMapping("/bat-hos/dongtien/{id}")
    @Timed
    public ResponseEntity<LichSuDongTienDTO> setDongTien(@PathVariable Long id) {
        log.debug("REST request to get BatHo : {}", id);

        LichSuDongTienDTO lichSuDongTienDTO = batHoService.setDongTien(id);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lichSuDongTienDTO));
    }

    @GetMapping("/tim-bat-hos-by-ten-cmnd/{key}")
    @Timed
    public List<BatHoDTO> timBatHosByTenCMND(@PathVariable(name = "key") String key) {
        log.debug("REST request to get all KhachHangs");
        return batHoService.findByNameOrCMND(key);
    }

    /**
     * DELETE /bat-hos/:id : delete the "id" batHo.
     *
     * @param id the id of the batHoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bat-hos/{id}")
    @Timed
    public ResponseEntity<Void> deleteBatHo(@PathVariable Long id) {
        log.debug("REST request to delete BatHo : {}", id);
        batHoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    //Tùng viết
    @GetMapping("/bat-hos-by-cua-hang/{id}")
    @Timed
    public List<BatHoDTO> getBatHoByCuaHangId(@PathVariable(name = "id") Long cuaHangId) {
        log.debug("REST request to get BatHo by CuaHangId: {}", cuaHangId);
        return batHoService.findByCuaHangId(cuaHangId);
    }
    //Tùng end

    @GetMapping("/bao-cao-bat-hos-nhanvien/{start}/{end}/{id}")
    @Timed
    public List<BatHoDTO> baoCao(@PathVariable(name = "start") String start, @PathVariable(name = "end") String end, @PathVariable(name = "id") Long id) {
        log.debug("REST request to get all BatHos");
        ZonedDateTime timeStart = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy MM dd")).atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime timeEnd = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy MM dd")).atStartOfDay(ZoneId.systemDefault()).plusSeconds(86399);
        return batHoService.baoCao(timeStart, timeEnd, id);
    }

    @GetMapping("/bao-cao-bat-hos/{start}/{end}")
    @Timed
    public List<BatHoDTO> baoCao(@PathVariable(name = "start") String start, @PathVariable(name = "end") String end) {
        log.debug("REST request to get all BatHos");
        ZonedDateTime timeStart = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy MM dd")).atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime timeEnd = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy MM dd")).atStartOfDay(ZoneId.systemDefault()).plusSeconds(86399);
        return batHoService.baoCao(timeStart, timeEnd);
    }

    @GetMapping("/find-by-trangthai-bat-hos/{start}/{end}/{trangthai}")
    @Timed
    public List<BatHoDTO> findByTrangThai(@PathVariable(name = "start") String start, @PathVariable(name = "end") String end, @PathVariable(name = "trangthai") String trangthai) {
        log.debug("REST request to get all BatHos");
        ZonedDateTime timeStart = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy MM dd")).atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime timeEnd = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy MM dd")).atStartOfDay(ZoneId.systemDefault()).plusSeconds(86399);
        TRANGTHAIHOPDONG trangthaihopdong = TRANGTHAIHOPDONG.DADONG;
        switch (trangthai) {
            case "0":
                trangthaihopdong = TRANGTHAIHOPDONG.QUAHAN;
                break;
            case "1":
                trangthaihopdong = TRANGTHAIHOPDONG.DANGVAY;
                break;
            case "2":
                trangthaihopdong = TRANGTHAIHOPDONG.DADONG;
                break;
        }
        return batHoService.findByTrangThai(timeStart, timeEnd, trangthaihopdong);
    }

    @GetMapping("/find-by-trangthai-hopdong-bat-hos/{trangthai}")
    @Timed
    public List<BatHoDTO> findByTrangThai(@PathVariable(name = "trangthai") String trangthai) {
        log.debug("REST request to get all BatHos");

        TRANGTHAIHOPDONG trangthaihopdong = TRANGTHAIHOPDONG.DADONG;
        switch (trangthai) {
            case "0":
                trangthaihopdong = TRANGTHAIHOPDONG.QUAHAN;
                break;
            case "1":
                trangthaihopdong = TRANGTHAIHOPDONG.DANGVAY;
                break;
            case "2":
                trangthaihopdong = TRANGTHAIHOPDONG.DADONG;
                break;
        }
        return batHoService.findByTrangThaiHopDong(trangthaihopdong);
    }

    @GetMapping("/find-by-nhanvien/{id}")
    @Timed
    public List<BatHoDTO> findByTrangThai(@PathVariable Long id) {
        log.debug("REST request to get all BatHos");

        return batHoService.findByNhanVien(id);
    }

    @GetMapping("/find-by-trangthai-bat-hos/{start}/{end}/{trangthai}/{id}")
    @Timed
    public List<BatHoDTO> findByTrangThai(@PathVariable(name = "start") String start, @PathVariable(name = "end") String end, @PathVariable(name = "trangthai") String trangthai, @PathVariable(name = "id") Long id) {
        log.debug("REST request to get all BatHos");
        ZonedDateTime timeStart = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy MM dd")).atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime timeEnd = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy MM dd")).atStartOfDay(ZoneId.systemDefault()).plusSeconds(86399);
        TRANGTHAIHOPDONG trangthaihopdong = TRANGTHAIHOPDONG.DADONG;
        switch (trangthai) {
            case "0":
                trangthaihopdong = TRANGTHAIHOPDONG.QUAHAN;
                break;
            case "1":
                trangthaihopdong = TRANGTHAIHOPDONG.DANGVAY;
                break;
            case "2":
                trangthaihopdong = TRANGTHAIHOPDONG.DADONG;
                break;
        }
        return batHoService.findByTrangThaiNV(timeStart, timeEnd, trangthaihopdong, id);
    }

    @GetMapping("/quan-ly-von")
    @Timed
    public ResponseEntity<String> quanLyVon() {
        log.debug("REST request to get all BatHos");
        String result = batHoService.quanLyVon().toString();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }
}
