package com.tindung.jhip.web.rest;

import com.tindung.jhip.ServertdjhipApp;

import com.tindung.jhip.domain.TaiSan;
import com.tindung.jhip.repository.TaiSanRepository;
import com.tindung.jhip.service.TaiSanService;
import com.tindung.jhip.service.dto.TaiSanDTO;
import com.tindung.jhip.service.mapper.TaiSanMapper;
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
import java.util.List;

import static com.tindung.jhip.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TaiSanResource REST controller.
 *
 * @see TaiSanResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServertdjhipApp.class)
public class TaiSanResourceIntTest {

    private static final String DEFAULT_MOTA = "AAAAAAAAAA";
    private static final String UPDATED_MOTA = "BBBBBBBBBB";

    private static final String DEFAULT_SOHIEU = "AAAAAAAAAA";
    private static final String UPDATED_SOHIEU = "BBBBBBBBBB";

    private static final String DEFAULT_NOICAT = "AAAAAAAAAA";
    private static final String UPDATED_NOICAT = "BBBBBBBBBB";

    @Autowired
    private TaiSanRepository taiSanRepository;

    @Autowired
    private TaiSanMapper taiSanMapper;

    @Autowired
    private TaiSanService taiSanService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTaiSanMockMvc;

    private TaiSan taiSan;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TaiSanResource taiSanResource = new TaiSanResource(taiSanService);
        this.restTaiSanMockMvc = MockMvcBuilders.standaloneSetup(taiSanResource)
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
    public static TaiSan createEntity(EntityManager em) {
        TaiSan taiSan = new TaiSan()
            .mota(DEFAULT_MOTA)
            .sohieu(DEFAULT_SOHIEU)
            .noicat(DEFAULT_NOICAT);
        return taiSan;
    }

    @Before
    public void initTest() {
        taiSan = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaiSan() throws Exception {
        int databaseSizeBeforeCreate = taiSanRepository.findAll().size();

        // Create the TaiSan
        TaiSanDTO taiSanDTO = taiSanMapper.toDto(taiSan);
        restTaiSanMockMvc.perform(post("/api/tai-sans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taiSanDTO)))
            .andExpect(status().isCreated());

        // Validate the TaiSan in the database
        List<TaiSan> taiSanList = taiSanRepository.findAll();
        assertThat(taiSanList).hasSize(databaseSizeBeforeCreate + 1);
        TaiSan testTaiSan = taiSanList.get(taiSanList.size() - 1);
        assertThat(testTaiSan.getMota()).isEqualTo(DEFAULT_MOTA);
        assertThat(testTaiSan.getSohieu()).isEqualTo(DEFAULT_SOHIEU);
        assertThat(testTaiSan.getNoicat()).isEqualTo(DEFAULT_NOICAT);
    }

    @Test
    @Transactional
    public void createTaiSanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taiSanRepository.findAll().size();

        // Create the TaiSan with an existing ID
        taiSan.setId(1L);
        TaiSanDTO taiSanDTO = taiSanMapper.toDto(taiSan);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaiSanMockMvc.perform(post("/api/tai-sans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taiSanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaiSan in the database
        List<TaiSan> taiSanList = taiSanRepository.findAll();
        assertThat(taiSanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMotaIsRequired() throws Exception {
        int databaseSizeBeforeTest = taiSanRepository.findAll().size();
        // set the field null
        taiSan.setMota(null);

        // Create the TaiSan, which fails.
        TaiSanDTO taiSanDTO = taiSanMapper.toDto(taiSan);

        restTaiSanMockMvc.perform(post("/api/tai-sans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taiSanDTO)))
            .andExpect(status().isBadRequest());

        List<TaiSan> taiSanList = taiSanRepository.findAll();
        assertThat(taiSanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSohieuIsRequired() throws Exception {
        int databaseSizeBeforeTest = taiSanRepository.findAll().size();
        // set the field null
        taiSan.setSohieu(null);

        // Create the TaiSan, which fails.
        TaiSanDTO taiSanDTO = taiSanMapper.toDto(taiSan);

        restTaiSanMockMvc.perform(post("/api/tai-sans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taiSanDTO)))
            .andExpect(status().isBadRequest());

        List<TaiSan> taiSanList = taiSanRepository.findAll();
        assertThat(taiSanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNoicatIsRequired() throws Exception {
        int databaseSizeBeforeTest = taiSanRepository.findAll().size();
        // set the field null
        taiSan.setNoicat(null);

        // Create the TaiSan, which fails.
        TaiSanDTO taiSanDTO = taiSanMapper.toDto(taiSan);

        restTaiSanMockMvc.perform(post("/api/tai-sans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taiSanDTO)))
            .andExpect(status().isBadRequest());

        List<TaiSan> taiSanList = taiSanRepository.findAll();
        assertThat(taiSanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTaiSans() throws Exception {
        // Initialize the database
        taiSanRepository.saveAndFlush(taiSan);

        // Get all the taiSanList
        restTaiSanMockMvc.perform(get("/api/tai-sans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taiSan.getId().intValue())))
            .andExpect(jsonPath("$.[*].mota").value(hasItem(DEFAULT_MOTA.toString())))
            .andExpect(jsonPath("$.[*].sohieu").value(hasItem(DEFAULT_SOHIEU.toString())))
            .andExpect(jsonPath("$.[*].noicat").value(hasItem(DEFAULT_NOICAT.toString())));
    }

    @Test
    @Transactional
    public void getTaiSan() throws Exception {
        // Initialize the database
        taiSanRepository.saveAndFlush(taiSan);

        // Get the taiSan
        restTaiSanMockMvc.perform(get("/api/tai-sans/{id}", taiSan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(taiSan.getId().intValue()))
            .andExpect(jsonPath("$.mota").value(DEFAULT_MOTA.toString()))
            .andExpect(jsonPath("$.sohieu").value(DEFAULT_SOHIEU.toString()))
            .andExpect(jsonPath("$.noicat").value(DEFAULT_NOICAT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTaiSan() throws Exception {
        // Get the taiSan
        restTaiSanMockMvc.perform(get("/api/tai-sans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaiSan() throws Exception {
        // Initialize the database
        taiSanRepository.saveAndFlush(taiSan);
        int databaseSizeBeforeUpdate = taiSanRepository.findAll().size();

        // Update the taiSan
        TaiSan updatedTaiSan = taiSanRepository.findOne(taiSan.getId());
        // Disconnect from session so that the updates on updatedTaiSan are not directly saved in db
        em.detach(updatedTaiSan);
        updatedTaiSan
            .mota(UPDATED_MOTA)
            .sohieu(UPDATED_SOHIEU)
            .noicat(UPDATED_NOICAT);
        TaiSanDTO taiSanDTO = taiSanMapper.toDto(updatedTaiSan);

        restTaiSanMockMvc.perform(put("/api/tai-sans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taiSanDTO)))
            .andExpect(status().isOk());

        // Validate the TaiSan in the database
        List<TaiSan> taiSanList = taiSanRepository.findAll();
        assertThat(taiSanList).hasSize(databaseSizeBeforeUpdate);
        TaiSan testTaiSan = taiSanList.get(taiSanList.size() - 1);
        assertThat(testTaiSan.getMota()).isEqualTo(UPDATED_MOTA);
        assertThat(testTaiSan.getSohieu()).isEqualTo(UPDATED_SOHIEU);
        assertThat(testTaiSan.getNoicat()).isEqualTo(UPDATED_NOICAT);
    }

    @Test
    @Transactional
    public void updateNonExistingTaiSan() throws Exception {
        int databaseSizeBeforeUpdate = taiSanRepository.findAll().size();

        // Create the TaiSan
        TaiSanDTO taiSanDTO = taiSanMapper.toDto(taiSan);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTaiSanMockMvc.perform(put("/api/tai-sans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taiSanDTO)))
            .andExpect(status().isCreated());

        // Validate the TaiSan in the database
        List<TaiSan> taiSanList = taiSanRepository.findAll();
        assertThat(taiSanList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTaiSan() throws Exception {
        // Initialize the database
        taiSanRepository.saveAndFlush(taiSan);
        int databaseSizeBeforeDelete = taiSanRepository.findAll().size();

        // Get the taiSan
        restTaiSanMockMvc.perform(delete("/api/tai-sans/{id}", taiSan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TaiSan> taiSanList = taiSanRepository.findAll();
        assertThat(taiSanList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaiSan.class);
        TaiSan taiSan1 = new TaiSan();
        taiSan1.setId(1L);
        TaiSan taiSan2 = new TaiSan();
        taiSan2.setId(taiSan1.getId());
        assertThat(taiSan1).isEqualTo(taiSan2);
        taiSan2.setId(2L);
        assertThat(taiSan1).isNotEqualTo(taiSan2);
        taiSan1.setId(null);
        assertThat(taiSan1).isNotEqualTo(taiSan2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaiSanDTO.class);
        TaiSanDTO taiSanDTO1 = new TaiSanDTO();
        taiSanDTO1.setId(1L);
        TaiSanDTO taiSanDTO2 = new TaiSanDTO();
        assertThat(taiSanDTO1).isNotEqualTo(taiSanDTO2);
        taiSanDTO2.setId(taiSanDTO1.getId());
        assertThat(taiSanDTO1).isEqualTo(taiSanDTO2);
        taiSanDTO2.setId(2L);
        assertThat(taiSanDTO1).isNotEqualTo(taiSanDTO2);
        taiSanDTO1.setId(null);
        assertThat(taiSanDTO1).isNotEqualTo(taiSanDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(taiSanMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(taiSanMapper.fromId(null)).isNull();
    }
}
