package com.tindung.jhip.web.rest;

import com.tindung.jhip.ServertdjhipApp;

import com.tindung.jhip.domain.NhatKy;
import com.tindung.jhip.repository.NhatKyRepository;
import com.tindung.jhip.service.NhatKyService;
import com.tindung.jhip.service.dto.NhatKyDTO;
import com.tindung.jhip.service.mapper.NhatKyMapper;
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

/**
 * Test class for the NhatKyResource REST controller.
 *
 * @see NhatKyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServertdjhipApp.class)
public class NhatKyResourceIntTest {

    private static final ZonedDateTime DEFAULT_THOI_GIAN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_THOI_GIAN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_NOI_DUNG = "AAAAAAAAAA";
    private static final String UPDATED_NOI_DUNG = "BBBBBBBBBB";

    @Autowired
    private NhatKyRepository nhatKyRepository;

    @Autowired
    private NhatKyMapper nhatKyMapper;

    @Autowired
    private NhatKyService nhatKyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNhatKyMockMvc;

    private NhatKy nhatKy;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NhatKyResource nhatKyResource = new NhatKyResource(nhatKyService);
        this.restNhatKyMockMvc = MockMvcBuilders.standaloneSetup(nhatKyResource)
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
    public static NhatKy createEntity(EntityManager em) {
        NhatKy nhatKy = new NhatKy()
            .thoiGian(DEFAULT_THOI_GIAN)
            .noiDung(DEFAULT_NOI_DUNG);
        return nhatKy;
    }

    @Before
    public void initTest() {
        nhatKy = createEntity(em);
    }

    @Test
    @Transactional
    public void createNhatKy() throws Exception {
        int databaseSizeBeforeCreate = nhatKyRepository.findAll().size();

        // Create the NhatKy
        NhatKyDTO nhatKyDTO = nhatKyMapper.toDto(nhatKy);
        restNhatKyMockMvc.perform(post("/api/nhat-kies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhatKyDTO)))
            .andExpect(status().isCreated());

        // Validate the NhatKy in the database
        List<NhatKy> nhatKyList = nhatKyRepository.findAll();
        assertThat(nhatKyList).hasSize(databaseSizeBeforeCreate + 1);
        NhatKy testNhatKy = nhatKyList.get(nhatKyList.size() - 1);
        assertThat(testNhatKy.getThoiGian()).isEqualTo(DEFAULT_THOI_GIAN);
        assertThat(testNhatKy.getNoiDung()).isEqualTo(DEFAULT_NOI_DUNG);
    }

    @Test
    @Transactional
    public void createNhatKyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nhatKyRepository.findAll().size();

        // Create the NhatKy with an existing ID
        nhatKy.setId(1L);
        NhatKyDTO nhatKyDTO = nhatKyMapper.toDto(nhatKy);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNhatKyMockMvc.perform(post("/api/nhat-kies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhatKyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NhatKy in the database
        List<NhatKy> nhatKyList = nhatKyRepository.findAll();
        assertThat(nhatKyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNhatKies() throws Exception {
        // Initialize the database
        nhatKyRepository.saveAndFlush(nhatKy);

        // Get all the nhatKyList
        restNhatKyMockMvc.perform(get("/api/nhat-kies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhatKy.getId().intValue())))
            .andExpect(jsonPath("$.[*].thoiGian").value(hasItem(sameInstant(DEFAULT_THOI_GIAN))))
            .andExpect(jsonPath("$.[*].noiDung").value(hasItem(DEFAULT_NOI_DUNG.toString())));
    }

    @Test
    @Transactional
    public void getNhatKy() throws Exception {
        // Initialize the database
        nhatKyRepository.saveAndFlush(nhatKy);

        // Get the nhatKy
        restNhatKyMockMvc.perform(get("/api/nhat-kies/{id}", nhatKy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nhatKy.getId().intValue()))
            .andExpect(jsonPath("$.thoiGian").value(sameInstant(DEFAULT_THOI_GIAN)))
            .andExpect(jsonPath("$.noiDung").value(DEFAULT_NOI_DUNG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNhatKy() throws Exception {
        // Get the nhatKy
        restNhatKyMockMvc.perform(get("/api/nhat-kies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNhatKy() throws Exception {
        // Initialize the database
        nhatKyRepository.saveAndFlush(nhatKy);
        int databaseSizeBeforeUpdate = nhatKyRepository.findAll().size();

        // Update the nhatKy
        NhatKy updatedNhatKy = nhatKyRepository.findOne(nhatKy.getId());
        // Disconnect from session so that the updates on updatedNhatKy are not directly saved in db
        em.detach(updatedNhatKy);
        updatedNhatKy
            .thoiGian(UPDATED_THOI_GIAN)
            .noiDung(UPDATED_NOI_DUNG);
        NhatKyDTO nhatKyDTO = nhatKyMapper.toDto(updatedNhatKy);

        restNhatKyMockMvc.perform(put("/api/nhat-kies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhatKyDTO)))
            .andExpect(status().isOk());

        // Validate the NhatKy in the database
        List<NhatKy> nhatKyList = nhatKyRepository.findAll();
        assertThat(nhatKyList).hasSize(databaseSizeBeforeUpdate);
        NhatKy testNhatKy = nhatKyList.get(nhatKyList.size() - 1);
        assertThat(testNhatKy.getThoiGian()).isEqualTo(UPDATED_THOI_GIAN);
        assertThat(testNhatKy.getNoiDung()).isEqualTo(UPDATED_NOI_DUNG);
    }

    @Test
    @Transactional
    public void updateNonExistingNhatKy() throws Exception {
        int databaseSizeBeforeUpdate = nhatKyRepository.findAll().size();

        // Create the NhatKy
        NhatKyDTO nhatKyDTO = nhatKyMapper.toDto(nhatKy);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNhatKyMockMvc.perform(put("/api/nhat-kies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhatKyDTO)))
            .andExpect(status().isCreated());

        // Validate the NhatKy in the database
        List<NhatKy> nhatKyList = nhatKyRepository.findAll();
        assertThat(nhatKyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNhatKy() throws Exception {
        // Initialize the database
        nhatKyRepository.saveAndFlush(nhatKy);
        int databaseSizeBeforeDelete = nhatKyRepository.findAll().size();

        // Get the nhatKy
        restNhatKyMockMvc.perform(delete("/api/nhat-kies/{id}", nhatKy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NhatKy> nhatKyList = nhatKyRepository.findAll();
        assertThat(nhatKyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NhatKy.class);
        NhatKy nhatKy1 = new NhatKy();
        nhatKy1.setId(1L);
        NhatKy nhatKy2 = new NhatKy();
        nhatKy2.setId(nhatKy1.getId());
        assertThat(nhatKy1).isEqualTo(nhatKy2);
        nhatKy2.setId(2L);
        assertThat(nhatKy1).isNotEqualTo(nhatKy2);
        nhatKy1.setId(null);
        assertThat(nhatKy1).isNotEqualTo(nhatKy2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NhatKyDTO.class);
        NhatKyDTO nhatKyDTO1 = new NhatKyDTO();
        nhatKyDTO1.setId(1L);
        NhatKyDTO nhatKyDTO2 = new NhatKyDTO();
        assertThat(nhatKyDTO1).isNotEqualTo(nhatKyDTO2);
        nhatKyDTO2.setId(nhatKyDTO1.getId());
        assertThat(nhatKyDTO1).isEqualTo(nhatKyDTO2);
        nhatKyDTO2.setId(2L);
        assertThat(nhatKyDTO1).isNotEqualTo(nhatKyDTO2);
        nhatKyDTO1.setId(null);
        assertThat(nhatKyDTO1).isNotEqualTo(nhatKyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(nhatKyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(nhatKyMapper.fromId(null)).isNull();
    }
}
