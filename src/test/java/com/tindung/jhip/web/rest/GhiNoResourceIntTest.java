package com.tindung.jhip.web.rest;

import com.tindung.jhip.ServertdjhipApp;

import com.tindung.jhip.domain.GhiNo;
import com.tindung.jhip.repository.GhiNoRepository;
import com.tindung.jhip.service.GhiNoService;
import com.tindung.jhip.service.dto.GhiNoDTO;
import com.tindung.jhip.service.mapper.GhiNoMapper;
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

import com.tindung.jhip.domain.enumeration.NOTRA;
/**
 * Test class for the GhiNoResource REST controller.
 *
 * @see GhiNoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServertdjhipApp.class)
public class GhiNoResourceIntTest {

    private static final ZonedDateTime DEFAULT_NGAYGHINO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_NGAYGHINO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_SOTIEN = 1D;
    private static final Double UPDATED_SOTIEN = 2D;

    private static final NOTRA DEFAULT_TRANGTHAI = NOTRA.NO;
    private static final NOTRA UPDATED_TRANGTHAI = NOTRA.TRA;

    private static final String DEFAULT_GHI_CHU = "AAAAAAAAAA";
    private static final String UPDATED_GHI_CHU = "BBBBBBBBBB";

    @Autowired
    private GhiNoRepository ghiNoRepository;

    @Autowired
    private GhiNoMapper ghiNoMapper;

    @Autowired
    private GhiNoService ghiNoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGhiNoMockMvc;

    private GhiNo ghiNo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GhiNoResource ghiNoResource = new GhiNoResource(ghiNoService);
        this.restGhiNoMockMvc = MockMvcBuilders.standaloneSetup(ghiNoResource)
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
    public static GhiNo createEntity(EntityManager em) {
        GhiNo ghiNo = new GhiNo()
            .ngayghino(DEFAULT_NGAYGHINO)
            .sotien(DEFAULT_SOTIEN)
            .trangthai(DEFAULT_TRANGTHAI)
            .ghiChu(DEFAULT_GHI_CHU);
        return ghiNo;
    }

    @Before
    public void initTest() {
        ghiNo = createEntity(em);
    }

    @Test
    @Transactional
    public void createGhiNo() throws Exception {
        int databaseSizeBeforeCreate = ghiNoRepository.findAll().size();

        // Create the GhiNo
        GhiNoDTO ghiNoDTO = ghiNoMapper.toDto(ghiNo);
        restGhiNoMockMvc.perform(post("/api/ghi-nos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ghiNoDTO)))
            .andExpect(status().isCreated());

        // Validate the GhiNo in the database
        List<GhiNo> ghiNoList = ghiNoRepository.findAll();
        assertThat(ghiNoList).hasSize(databaseSizeBeforeCreate + 1);
        GhiNo testGhiNo = ghiNoList.get(ghiNoList.size() - 1);
        assertThat(testGhiNo.getNgayghino()).isEqualTo(DEFAULT_NGAYGHINO);
        assertThat(testGhiNo.getSotien()).isEqualTo(DEFAULT_SOTIEN);
        assertThat(testGhiNo.getTrangthai()).isEqualTo(DEFAULT_TRANGTHAI);
        assertThat(testGhiNo.getGhiChu()).isEqualTo(DEFAULT_GHI_CHU);
    }

    @Test
    @Transactional
    public void createGhiNoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ghiNoRepository.findAll().size();

        // Create the GhiNo with an existing ID
        ghiNo.setId(1L);
        GhiNoDTO ghiNoDTO = ghiNoMapper.toDto(ghiNo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGhiNoMockMvc.perform(post("/api/ghi-nos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ghiNoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GhiNo in the database
        List<GhiNo> ghiNoList = ghiNoRepository.findAll();
        assertThat(ghiNoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNgayghinoIsRequired() throws Exception {
        int databaseSizeBeforeTest = ghiNoRepository.findAll().size();
        // set the field null
        ghiNo.setNgayghino(null);

        // Create the GhiNo, which fails.
        GhiNoDTO ghiNoDTO = ghiNoMapper.toDto(ghiNo);

        restGhiNoMockMvc.perform(post("/api/ghi-nos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ghiNoDTO)))
            .andExpect(status().isBadRequest());

        List<GhiNo> ghiNoList = ghiNoRepository.findAll();
        assertThat(ghiNoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSotienIsRequired() throws Exception {
        int databaseSizeBeforeTest = ghiNoRepository.findAll().size();
        // set the field null
        ghiNo.setSotien(null);

        // Create the GhiNo, which fails.
        GhiNoDTO ghiNoDTO = ghiNoMapper.toDto(ghiNo);

        restGhiNoMockMvc.perform(post("/api/ghi-nos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ghiNoDTO)))
            .andExpect(status().isBadRequest());

        List<GhiNo> ghiNoList = ghiNoRepository.findAll();
        assertThat(ghiNoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTrangthaiIsRequired() throws Exception {
        int databaseSizeBeforeTest = ghiNoRepository.findAll().size();
        // set the field null
        ghiNo.setTrangthai(null);

        // Create the GhiNo, which fails.
        GhiNoDTO ghiNoDTO = ghiNoMapper.toDto(ghiNo);

        restGhiNoMockMvc.perform(post("/api/ghi-nos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ghiNoDTO)))
            .andExpect(status().isBadRequest());

        List<GhiNo> ghiNoList = ghiNoRepository.findAll();
        assertThat(ghiNoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGhiNos() throws Exception {
        // Initialize the database
        ghiNoRepository.saveAndFlush(ghiNo);

        // Get all the ghiNoList
        restGhiNoMockMvc.perform(get("/api/ghi-nos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ghiNo.getId().intValue())))
            .andExpect(jsonPath("$.[*].ngayghino").value(hasItem(sameInstant(DEFAULT_NGAYGHINO))))
            .andExpect(jsonPath("$.[*].sotien").value(hasItem(DEFAULT_SOTIEN.doubleValue())))
            .andExpect(jsonPath("$.[*].trangthai").value(hasItem(DEFAULT_TRANGTHAI.toString())))
            .andExpect(jsonPath("$.[*].ghiChu").value(hasItem(DEFAULT_GHI_CHU.toString())));
    }

    @Test
    @Transactional
    public void getGhiNo() throws Exception {
        // Initialize the database
        ghiNoRepository.saveAndFlush(ghiNo);

        // Get the ghiNo
        restGhiNoMockMvc.perform(get("/api/ghi-nos/{id}", ghiNo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ghiNo.getId().intValue()))
            .andExpect(jsonPath("$.ngayghino").value(sameInstant(DEFAULT_NGAYGHINO)))
            .andExpect(jsonPath("$.sotien").value(DEFAULT_SOTIEN.doubleValue()))
            .andExpect(jsonPath("$.trangthai").value(DEFAULT_TRANGTHAI.toString()))
            .andExpect(jsonPath("$.ghiChu").value(DEFAULT_GHI_CHU.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGhiNo() throws Exception {
        // Get the ghiNo
        restGhiNoMockMvc.perform(get("/api/ghi-nos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGhiNo() throws Exception {
        // Initialize the database
        ghiNoRepository.saveAndFlush(ghiNo);
        int databaseSizeBeforeUpdate = ghiNoRepository.findAll().size();

        // Update the ghiNo
        GhiNo updatedGhiNo = ghiNoRepository.findOne(ghiNo.getId());
        // Disconnect from session so that the updates on updatedGhiNo are not directly saved in db
        em.detach(updatedGhiNo);
        updatedGhiNo
            .ngayghino(UPDATED_NGAYGHINO)
            .sotien(UPDATED_SOTIEN)
            .trangthai(UPDATED_TRANGTHAI)
            .ghiChu(UPDATED_GHI_CHU);
        GhiNoDTO ghiNoDTO = ghiNoMapper.toDto(updatedGhiNo);

        restGhiNoMockMvc.perform(put("/api/ghi-nos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ghiNoDTO)))
            .andExpect(status().isOk());

        // Validate the GhiNo in the database
        List<GhiNo> ghiNoList = ghiNoRepository.findAll();
        assertThat(ghiNoList).hasSize(databaseSizeBeforeUpdate);
        GhiNo testGhiNo = ghiNoList.get(ghiNoList.size() - 1);
        assertThat(testGhiNo.getNgayghino()).isEqualTo(UPDATED_NGAYGHINO);
        assertThat(testGhiNo.getSotien()).isEqualTo(UPDATED_SOTIEN);
        assertThat(testGhiNo.getTrangthai()).isEqualTo(UPDATED_TRANGTHAI);
        assertThat(testGhiNo.getGhiChu()).isEqualTo(UPDATED_GHI_CHU);
    }

    @Test
    @Transactional
    public void updateNonExistingGhiNo() throws Exception {
        int databaseSizeBeforeUpdate = ghiNoRepository.findAll().size();

        // Create the GhiNo
        GhiNoDTO ghiNoDTO = ghiNoMapper.toDto(ghiNo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGhiNoMockMvc.perform(put("/api/ghi-nos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ghiNoDTO)))
            .andExpect(status().isCreated());

        // Validate the GhiNo in the database
        List<GhiNo> ghiNoList = ghiNoRepository.findAll();
        assertThat(ghiNoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGhiNo() throws Exception {
        // Initialize the database
        ghiNoRepository.saveAndFlush(ghiNo);
        int databaseSizeBeforeDelete = ghiNoRepository.findAll().size();

        // Get the ghiNo
        restGhiNoMockMvc.perform(delete("/api/ghi-nos/{id}", ghiNo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GhiNo> ghiNoList = ghiNoRepository.findAll();
        assertThat(ghiNoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GhiNo.class);
        GhiNo ghiNo1 = new GhiNo();
        ghiNo1.setId(1L);
        GhiNo ghiNo2 = new GhiNo();
        ghiNo2.setId(ghiNo1.getId());
        assertThat(ghiNo1).isEqualTo(ghiNo2);
        ghiNo2.setId(2L);
        assertThat(ghiNo1).isNotEqualTo(ghiNo2);
        ghiNo1.setId(null);
        assertThat(ghiNo1).isNotEqualTo(ghiNo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GhiNoDTO.class);
        GhiNoDTO ghiNoDTO1 = new GhiNoDTO();
        ghiNoDTO1.setId(1L);
        GhiNoDTO ghiNoDTO2 = new GhiNoDTO();
        assertThat(ghiNoDTO1).isNotEqualTo(ghiNoDTO2);
        ghiNoDTO2.setId(ghiNoDTO1.getId());
        assertThat(ghiNoDTO1).isEqualTo(ghiNoDTO2);
        ghiNoDTO2.setId(2L);
        assertThat(ghiNoDTO1).isNotEqualTo(ghiNoDTO2);
        ghiNoDTO1.setId(null);
        assertThat(ghiNoDTO1).isNotEqualTo(ghiNoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ghiNoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ghiNoMapper.fromId(null)).isNull();
    }
}
