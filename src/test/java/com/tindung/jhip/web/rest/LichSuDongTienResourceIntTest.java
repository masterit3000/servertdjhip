package com.tindung.jhip.web.rest;

import com.tindung.jhip.ServertdjhipApp;

import com.tindung.jhip.domain.LichSuDongTien;
import com.tindung.jhip.repository.LichSuDongTienRepository;
import com.tindung.jhip.service.LichSuDongTienService;
import com.tindung.jhip.service.dto.LichSuDongTienDTO;
import com.tindung.jhip.service.mapper.LichSuDongTienMapper;
import com.tindung.jhip.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.tindung.jhip.web.rest.TestUtil.sameInstant;
import static com.tindung.jhip.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tindung.jhip.domain.enumeration.DONGTIEN;
/**
 * Test class for the LichSuDongTienResource REST controller.
 *
 * @see LichSuDongTienResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServertdjhipApp.class)
public class LichSuDongTienResourceIntTest {

    private static final ZonedDateTime DEFAULT_NGAYBATDAU = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_NGAYBATDAU = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_NGAYKETTHUC = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_NGAYKETTHUC = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_SOTIEN = 1D;
    private static final Double UPDATED_SOTIEN = 2D;

    private static final DONGTIEN DEFAULT_TRANGTHAI = DONGTIEN.CHUADONG;
    private static final DONGTIEN UPDATED_TRANGTHAI = DONGTIEN.DADONG;

    private static final String DEFAULT_GHI_CHU = "AAAAAAAAAA";
    private static final String UPDATED_GHI_CHU = "BBBBBBBBBB";

    @Autowired
    private LichSuDongTienRepository lichSuDongTienRepository;

    @Autowired
    private LichSuDongTienMapper lichSuDongTienMapper;

    @Autowired
    private LichSuDongTienService lichSuDongTienService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLichSuDongTienMockMvc;

    private LichSuDongTien lichSuDongTien;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LichSuDongTienResource lichSuDongTienResource = new LichSuDongTienResource(lichSuDongTienService);
        this.restLichSuDongTienMockMvc = MockMvcBuilders.standaloneSetup(lichSuDongTienResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LichSuDongTien createEntity(EntityManager em) {
        LichSuDongTien lichSuDongTien = new LichSuDongTien()
            .ngaybatdau(DEFAULT_NGAYBATDAU)
            .ngayketthuc(DEFAULT_NGAYKETTHUC)
            .sotien(DEFAULT_SOTIEN)
            .trangthai(DEFAULT_TRANGTHAI)
            .ghiChu(DEFAULT_GHI_CHU);
        return lichSuDongTien;
    }

    @Before
    public void initTest() {
        lichSuDongTien = createEntity(em);
    }

    @Test
    @Transactional
    public void createLichSuDongTien() throws Exception {
        int databaseSizeBeforeCreate = lichSuDongTienRepository.findAll().size();

        // Create the LichSuDongTien
        LichSuDongTienDTO lichSuDongTienDTO = lichSuDongTienMapper.toDto(lichSuDongTien);
        restLichSuDongTienMockMvc.perform(post("/api/lich-su-dong-tiens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lichSuDongTienDTO)))
            .andExpect(status().isCreated());

        // Validate the LichSuDongTien in the database
        List<LichSuDongTien> lichSuDongTienList = lichSuDongTienRepository.findAll();
        assertThat(lichSuDongTienList).hasSize(databaseSizeBeforeCreate + 1);
        LichSuDongTien testLichSuDongTien = lichSuDongTienList.get(lichSuDongTienList.size() - 1);
        assertThat(testLichSuDongTien.getNgaybatdau()).isEqualTo(DEFAULT_NGAYBATDAU);
        assertThat(testLichSuDongTien.getNgayketthuc()).isEqualTo(DEFAULT_NGAYKETTHUC);
        assertThat(testLichSuDongTien.getSotien()).isEqualTo(DEFAULT_SOTIEN);
        assertThat(testLichSuDongTien.getTrangthai()).isEqualTo(DEFAULT_TRANGTHAI);
        assertThat(testLichSuDongTien.getGhiChu()).isEqualTo(DEFAULT_GHI_CHU);
    }

    @Test
    @Transactional
    public void createLichSuDongTienWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lichSuDongTienRepository.findAll().size();

        // Create the LichSuDongTien with an existing ID
        lichSuDongTien.setId(1L);
        LichSuDongTienDTO lichSuDongTienDTO = lichSuDongTienMapper.toDto(lichSuDongTien);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLichSuDongTienMockMvc.perform(post("/api/lich-su-dong-tiens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lichSuDongTienDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LichSuDongTien in the database
        List<LichSuDongTien> lichSuDongTienList = lichSuDongTienRepository.findAll();
        assertThat(lichSuDongTienList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNgaybatdauIsRequired() throws Exception {
        int databaseSizeBeforeTest = lichSuDongTienRepository.findAll().size();
        // set the field null
        lichSuDongTien.setNgaybatdau(null);

        // Create the LichSuDongTien, which fails.
        LichSuDongTienDTO lichSuDongTienDTO = lichSuDongTienMapper.toDto(lichSuDongTien);

        restLichSuDongTienMockMvc.perform(post("/api/lich-su-dong-tiens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lichSuDongTienDTO)))
            .andExpect(status().isBadRequest());

        List<LichSuDongTien> lichSuDongTienList = lichSuDongTienRepository.findAll();
        assertThat(lichSuDongTienList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNgayketthucIsRequired() throws Exception {
        int databaseSizeBeforeTest = lichSuDongTienRepository.findAll().size();
        // set the field null
        lichSuDongTien.setNgayketthuc(null);

        // Create the LichSuDongTien, which fails.
        LichSuDongTienDTO lichSuDongTienDTO = lichSuDongTienMapper.toDto(lichSuDongTien);

        restLichSuDongTienMockMvc.perform(post("/api/lich-su-dong-tiens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lichSuDongTienDTO)))
            .andExpect(status().isBadRequest());

        List<LichSuDongTien> lichSuDongTienList = lichSuDongTienRepository.findAll();
        assertThat(lichSuDongTienList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSotienIsRequired() throws Exception {
        int databaseSizeBeforeTest = lichSuDongTienRepository.findAll().size();
        // set the field null
        lichSuDongTien.setSotien(null);

        // Create the LichSuDongTien, which fails.
        LichSuDongTienDTO lichSuDongTienDTO = lichSuDongTienMapper.toDto(lichSuDongTien);

        restLichSuDongTienMockMvc.perform(post("/api/lich-su-dong-tiens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lichSuDongTienDTO)))
            .andExpect(status().isBadRequest());

        List<LichSuDongTien> lichSuDongTienList = lichSuDongTienRepository.findAll();
        assertThat(lichSuDongTienList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTrangthaiIsRequired() throws Exception {
        int databaseSizeBeforeTest = lichSuDongTienRepository.findAll().size();
        // set the field null
        lichSuDongTien.setTrangthai(null);

        // Create the LichSuDongTien, which fails.
        LichSuDongTienDTO lichSuDongTienDTO = lichSuDongTienMapper.toDto(lichSuDongTien);

        restLichSuDongTienMockMvc.perform(post("/api/lich-su-dong-tiens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lichSuDongTienDTO)))
            .andExpect(status().isBadRequest());

        List<LichSuDongTien> lichSuDongTienList = lichSuDongTienRepository.findAll();
        assertThat(lichSuDongTienList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLichSuDongTiens() throws Exception {
        // Initialize the database
        lichSuDongTienRepository.saveAndFlush(lichSuDongTien);

        // Get all the lichSuDongTienList
        restLichSuDongTienMockMvc.perform(get("/api/lich-su-dong-tiens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lichSuDongTien.getId().intValue())))
            .andExpect(jsonPath("$.[*].ngaybatdau").value(hasItem(sameInstant(DEFAULT_NGAYBATDAU))))
            .andExpect(jsonPath("$.[*].ngayketthuc").value(hasItem(sameInstant(DEFAULT_NGAYKETTHUC))))
            .andExpect(jsonPath("$.[*].sotien").value(hasItem(DEFAULT_SOTIEN.doubleValue())))
            .andExpect(jsonPath("$.[*].trangthai").value(hasItem(DEFAULT_TRANGTHAI.toString())))
            .andExpect(jsonPath("$.[*].ghiChu").value(hasItem(DEFAULT_GHI_CHU.toString())));
    }

    @Test
    @Transactional
    public void getLichSuDongTien() throws Exception {
        // Initialize the database
        lichSuDongTienRepository.saveAndFlush(lichSuDongTien);

        // Get the lichSuDongTien
        restLichSuDongTienMockMvc.perform(get("/api/lich-su-dong-tiens/{id}", lichSuDongTien.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lichSuDongTien.getId().intValue()))
            .andExpect(jsonPath("$.ngaybatdau").value(sameInstant(DEFAULT_NGAYBATDAU)))
            .andExpect(jsonPath("$.ngayketthuc").value(sameInstant(DEFAULT_NGAYKETTHUC)))
            .andExpect(jsonPath("$.sotien").value(DEFAULT_SOTIEN.doubleValue()))
            .andExpect(jsonPath("$.trangthai").value(DEFAULT_TRANGTHAI.toString()))
            .andExpect(jsonPath("$.ghiChu").value(DEFAULT_GHI_CHU.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLichSuDongTien() throws Exception {
        // Get the lichSuDongTien
        restLichSuDongTienMockMvc.perform(get("/api/lich-su-dong-tiens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLichSuDongTien() throws Exception {
        // Initialize the database
        lichSuDongTienRepository.saveAndFlush(lichSuDongTien);
        int databaseSizeBeforeUpdate = lichSuDongTienRepository.findAll().size();

        // Update the lichSuDongTien
        LichSuDongTien updatedLichSuDongTien = lichSuDongTienRepository.findOne(lichSuDongTien.getId());
        // Disconnect from session so that the updates on updatedLichSuDongTien are not directly saved in db
        em.detach(updatedLichSuDongTien);
        updatedLichSuDongTien
            .ngaybatdau(UPDATED_NGAYBATDAU)
            .ngayketthuc(UPDATED_NGAYKETTHUC)
            .sotien(UPDATED_SOTIEN)
            .trangthai(UPDATED_TRANGTHAI)
            .ghiChu(UPDATED_GHI_CHU);
        LichSuDongTienDTO lichSuDongTienDTO = lichSuDongTienMapper.toDto(updatedLichSuDongTien);

        restLichSuDongTienMockMvc.perform(put("/api/lich-su-dong-tiens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lichSuDongTienDTO)))
            .andExpect(status().isOk());

        // Validate the LichSuDongTien in the database
        List<LichSuDongTien> lichSuDongTienList = lichSuDongTienRepository.findAll();
        assertThat(lichSuDongTienList).hasSize(databaseSizeBeforeUpdate);
        LichSuDongTien testLichSuDongTien = lichSuDongTienList.get(lichSuDongTienList.size() - 1);
        assertThat(testLichSuDongTien.getNgaybatdau()).isEqualTo(UPDATED_NGAYBATDAU);
        assertThat(testLichSuDongTien.getNgayketthuc()).isEqualTo(UPDATED_NGAYKETTHUC);
        assertThat(testLichSuDongTien.getSotien()).isEqualTo(UPDATED_SOTIEN);
        assertThat(testLichSuDongTien.getTrangthai()).isEqualTo(UPDATED_TRANGTHAI);
        assertThat(testLichSuDongTien.getGhiChu()).isEqualTo(UPDATED_GHI_CHU);
    }

    @Test
    @Transactional
    public void updateNonExistingLichSuDongTien() throws Exception {
        int databaseSizeBeforeUpdate = lichSuDongTienRepository.findAll().size();

        // Create the LichSuDongTien
        LichSuDongTienDTO lichSuDongTienDTO = lichSuDongTienMapper.toDto(lichSuDongTien);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLichSuDongTienMockMvc.perform(put("/api/lich-su-dong-tiens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lichSuDongTienDTO)))
            .andExpect(status().isCreated());

        // Validate the LichSuDongTien in the database
        List<LichSuDongTien> lichSuDongTienList = lichSuDongTienRepository.findAll();
        assertThat(lichSuDongTienList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLichSuDongTien() throws Exception {
        // Initialize the database
        lichSuDongTienRepository.saveAndFlush(lichSuDongTien);
        int databaseSizeBeforeDelete = lichSuDongTienRepository.findAll().size();

        // Get the lichSuDongTien
        restLichSuDongTienMockMvc.perform(delete("/api/lich-su-dong-tiens/{id}", lichSuDongTien.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LichSuDongTien> lichSuDongTienList = lichSuDongTienRepository.findAll();
        assertThat(lichSuDongTienList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LichSuDongTien.class);
        LichSuDongTien lichSuDongTien1 = new LichSuDongTien();
        lichSuDongTien1.setId(1L);
        LichSuDongTien lichSuDongTien2 = new LichSuDongTien();
        lichSuDongTien2.setId(lichSuDongTien1.getId());
        assertThat(lichSuDongTien1).isEqualTo(lichSuDongTien2);
        lichSuDongTien2.setId(2L);
        assertThat(lichSuDongTien1).isNotEqualTo(lichSuDongTien2);
        lichSuDongTien1.setId(null);
        assertThat(lichSuDongTien1).isNotEqualTo(lichSuDongTien2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LichSuDongTienDTO.class);
        LichSuDongTienDTO lichSuDongTienDTO1 = new LichSuDongTienDTO();
        lichSuDongTienDTO1.setId(1L);
        LichSuDongTienDTO lichSuDongTienDTO2 = new LichSuDongTienDTO();
        assertThat(lichSuDongTienDTO1).isNotEqualTo(lichSuDongTienDTO2);
        lichSuDongTienDTO2.setId(lichSuDongTienDTO1.getId());
        assertThat(lichSuDongTienDTO1).isEqualTo(lichSuDongTienDTO2);
        lichSuDongTienDTO2.setId(2L);
        assertThat(lichSuDongTienDTO1).isNotEqualTo(lichSuDongTienDTO2);
        lichSuDongTienDTO1.setId(null);
        assertThat(lichSuDongTienDTO1).isNotEqualTo(lichSuDongTienDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(lichSuDongTienMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(lichSuDongTienMapper.fromId(null)).isNull();
    }
}
