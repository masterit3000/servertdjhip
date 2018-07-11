package com.tindung.jhip.web.rest;

import com.tindung.jhip.ServertdjhipApp;

import com.tindung.jhip.domain.ThuChi;
import com.tindung.jhip.repository.ThuChiRepository;
import com.tindung.jhip.service.ThuChiService;
import com.tindung.jhip.service.dto.ThuChiDTO;
import com.tindung.jhip.service.mapper.ThuChiMapper;
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

import com.tindung.jhip.domain.enumeration.THUCHI;
/**
 * Test class for the ThuChiResource REST controller.
 *
 * @see ThuChiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServertdjhipApp.class)
public class ThuChiResourceIntTest {

    private static final String DEFAULT_NOIDUNG = "AAAAAAAAAA";
    private static final String UPDATED_NOIDUNG = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_THOIGIAN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_THOIGIAN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final THUCHI DEFAULT_THUCHI = THUCHI.THU;
    private static final THUCHI UPDATED_THUCHI = THUCHI.CHI;

    private static final Double DEFAULT_SOTIEN = 1D;
    private static final Double UPDATED_SOTIEN = 2D;

    @Autowired
    private ThuChiRepository thuChiRepository;

    @Autowired
    private ThuChiMapper thuChiMapper;

    @Autowired
    private ThuChiService thuChiService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restThuChiMockMvc;

    private ThuChi thuChi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ThuChiResource thuChiResource = new ThuChiResource(thuChiService);
        this.restThuChiMockMvc = MockMvcBuilders.standaloneSetup(thuChiResource)
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
    public static ThuChi createEntity(EntityManager em) {
        ThuChi thuChi = new ThuChi()
            .noidung(DEFAULT_NOIDUNG)
            .thoigian(DEFAULT_THOIGIAN)
            .thuchi(DEFAULT_THUCHI)
            .sotien(DEFAULT_SOTIEN);
        return thuChi;
    }

    @Before
    public void initTest() {
        thuChi = createEntity(em);
    }

    @Test
    @Transactional
    public void createThuChi() throws Exception {
        int databaseSizeBeforeCreate = thuChiRepository.findAll().size();

        // Create the ThuChi
        ThuChiDTO thuChiDTO = thuChiMapper.toDto(thuChi);
        restThuChiMockMvc.perform(post("/api/thu-chis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thuChiDTO)))
            .andExpect(status().isCreated());

        // Validate the ThuChi in the database
        List<ThuChi> thuChiList = thuChiRepository.findAll();
        assertThat(thuChiList).hasSize(databaseSizeBeforeCreate + 1);
        ThuChi testThuChi = thuChiList.get(thuChiList.size() - 1);
        assertThat(testThuChi.getNoidung()).isEqualTo(DEFAULT_NOIDUNG);
        assertThat(testThuChi.getThoigian()).isEqualTo(DEFAULT_THOIGIAN);
        assertThat(testThuChi.getThuchi()).isEqualTo(DEFAULT_THUCHI);
        assertThat(testThuChi.getSotien()).isEqualTo(DEFAULT_SOTIEN);
    }

    @Test
    @Transactional
    public void createThuChiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = thuChiRepository.findAll().size();

        // Create the ThuChi with an existing ID
        thuChi.setId(1L);
        ThuChiDTO thuChiDTO = thuChiMapper.toDto(thuChi);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThuChiMockMvc.perform(post("/api/thu-chis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thuChiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ThuChi in the database
        List<ThuChi> thuChiList = thuChiRepository.findAll();
        assertThat(thuChiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNoidungIsRequired() throws Exception {
        int databaseSizeBeforeTest = thuChiRepository.findAll().size();
        // set the field null
        thuChi.setNoidung(null);

        // Create the ThuChi, which fails.
        ThuChiDTO thuChiDTO = thuChiMapper.toDto(thuChi);

        restThuChiMockMvc.perform(post("/api/thu-chis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thuChiDTO)))
            .andExpect(status().isBadRequest());

        List<ThuChi> thuChiList = thuChiRepository.findAll();
        assertThat(thuChiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkThoigianIsRequired() throws Exception {
        int databaseSizeBeforeTest = thuChiRepository.findAll().size();
        // set the field null
        thuChi.setThoigian(null);

        // Create the ThuChi, which fails.
        ThuChiDTO thuChiDTO = thuChiMapper.toDto(thuChi);

        restThuChiMockMvc.perform(post("/api/thu-chis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thuChiDTO)))
            .andExpect(status().isBadRequest());

        List<ThuChi> thuChiList = thuChiRepository.findAll();
        assertThat(thuChiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSotienIsRequired() throws Exception {
        int databaseSizeBeforeTest = thuChiRepository.findAll().size();
        // set the field null
        thuChi.setSotien(null);

        // Create the ThuChi, which fails.
        ThuChiDTO thuChiDTO = thuChiMapper.toDto(thuChi);

        restThuChiMockMvc.perform(post("/api/thu-chis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thuChiDTO)))
            .andExpect(status().isBadRequest());

        List<ThuChi> thuChiList = thuChiRepository.findAll();
        assertThat(thuChiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllThuChis() throws Exception {
        // Initialize the database
        thuChiRepository.saveAndFlush(thuChi);

        // Get all the thuChiList
        restThuChiMockMvc.perform(get("/api/thu-chis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thuChi.getId().intValue())))
            .andExpect(jsonPath("$.[*].noidung").value(hasItem(DEFAULT_NOIDUNG.toString())))
            .andExpect(jsonPath("$.[*].thoigian").value(hasItem(sameInstant(DEFAULT_THOIGIAN))))
            .andExpect(jsonPath("$.[*].thuchi").value(hasItem(DEFAULT_THUCHI.toString())))
            .andExpect(jsonPath("$.[*].sotien").value(hasItem(DEFAULT_SOTIEN.doubleValue())));
    }

    @Test
    @Transactional
    public void getThuChi() throws Exception {
        // Initialize the database
        thuChiRepository.saveAndFlush(thuChi);

        // Get the thuChi
        restThuChiMockMvc.perform(get("/api/thu-chis/{id}", thuChi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(thuChi.getId().intValue()))
            .andExpect(jsonPath("$.noidung").value(DEFAULT_NOIDUNG.toString()))
            .andExpect(jsonPath("$.thoigian").value(sameInstant(DEFAULT_THOIGIAN)))
            .andExpect(jsonPath("$.thuchi").value(DEFAULT_THUCHI.toString()))
            .andExpect(jsonPath("$.sotien").value(DEFAULT_SOTIEN.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingThuChi() throws Exception {
        // Get the thuChi
        restThuChiMockMvc.perform(get("/api/thu-chis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThuChi() throws Exception {
        // Initialize the database
        thuChiRepository.saveAndFlush(thuChi);
        int databaseSizeBeforeUpdate = thuChiRepository.findAll().size();

        // Update the thuChi
        ThuChi updatedThuChi = thuChiRepository.findOne(thuChi.getId());
        // Disconnect from session so that the updates on updatedThuChi are not directly saved in db
        em.detach(updatedThuChi);
        updatedThuChi
            .noidung(UPDATED_NOIDUNG)
            .thoigian(UPDATED_THOIGIAN)
            .thuchi(UPDATED_THUCHI)
            .sotien(UPDATED_SOTIEN);
        ThuChiDTO thuChiDTO = thuChiMapper.toDto(updatedThuChi);

        restThuChiMockMvc.perform(put("/api/thu-chis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thuChiDTO)))
            .andExpect(status().isOk());

        // Validate the ThuChi in the database
        List<ThuChi> thuChiList = thuChiRepository.findAll();
        assertThat(thuChiList).hasSize(databaseSizeBeforeUpdate);
        ThuChi testThuChi = thuChiList.get(thuChiList.size() - 1);
        assertThat(testThuChi.getNoidung()).isEqualTo(UPDATED_NOIDUNG);
        assertThat(testThuChi.getThoigian()).isEqualTo(UPDATED_THOIGIAN);
        assertThat(testThuChi.getThuchi()).isEqualTo(UPDATED_THUCHI);
        assertThat(testThuChi.getSotien()).isEqualTo(UPDATED_SOTIEN);
    }

    @Test
    @Transactional
    public void updateNonExistingThuChi() throws Exception {
        int databaseSizeBeforeUpdate = thuChiRepository.findAll().size();

        // Create the ThuChi
        ThuChiDTO thuChiDTO = thuChiMapper.toDto(thuChi);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restThuChiMockMvc.perform(put("/api/thu-chis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thuChiDTO)))
            .andExpect(status().isCreated());

        // Validate the ThuChi in the database
        List<ThuChi> thuChiList = thuChiRepository.findAll();
        assertThat(thuChiList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteThuChi() throws Exception {
        // Initialize the database
        thuChiRepository.saveAndFlush(thuChi);
        int databaseSizeBeforeDelete = thuChiRepository.findAll().size();

        // Get the thuChi
        restThuChiMockMvc.perform(delete("/api/thu-chis/{id}", thuChi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ThuChi> thuChiList = thuChiRepository.findAll();
        assertThat(thuChiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThuChi.class);
        ThuChi thuChi1 = new ThuChi();
        thuChi1.setId(1L);
        ThuChi thuChi2 = new ThuChi();
        thuChi2.setId(thuChi1.getId());
        assertThat(thuChi1).isEqualTo(thuChi2);
        thuChi2.setId(2L);
        assertThat(thuChi1).isNotEqualTo(thuChi2);
        thuChi1.setId(null);
        assertThat(thuChi1).isNotEqualTo(thuChi2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThuChiDTO.class);
        ThuChiDTO thuChiDTO1 = new ThuChiDTO();
        thuChiDTO1.setId(1L);
        ThuChiDTO thuChiDTO2 = new ThuChiDTO();
        assertThat(thuChiDTO1).isNotEqualTo(thuChiDTO2);
        thuChiDTO2.setId(thuChiDTO1.getId());
        assertThat(thuChiDTO1).isEqualTo(thuChiDTO2);
        thuChiDTO2.setId(2L);
        assertThat(thuChiDTO1).isNotEqualTo(thuChiDTO2);
        thuChiDTO1.setId(null);
        assertThat(thuChiDTO1).isNotEqualTo(thuChiDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(thuChiMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(thuChiMapper.fromId(null)).isNull();
    }
}
