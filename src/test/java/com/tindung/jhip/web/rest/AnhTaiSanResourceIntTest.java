package com.tindung.jhip.web.rest;

import com.tindung.jhip.ServertdjhipApp;

import com.tindung.jhip.domain.AnhTaiSan;
import com.tindung.jhip.repository.AnhTaiSanRepository;
import com.tindung.jhip.service.AnhTaiSanService;
import com.tindung.jhip.service.dto.AnhTaiSanDTO;
import com.tindung.jhip.service.mapper.AnhTaiSanMapper;
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
 * Test class for the AnhTaiSanResource REST controller.
 *
 * @see AnhTaiSanResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServertdjhipApp.class)
public class AnhTaiSanResourceIntTest {

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private AnhTaiSanRepository anhTaiSanRepository;

    @Autowired
    private AnhTaiSanMapper anhTaiSanMapper;

    @Autowired
    private AnhTaiSanService anhTaiSanService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAnhTaiSanMockMvc;

    private AnhTaiSan anhTaiSan;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnhTaiSanResource anhTaiSanResource = new AnhTaiSanResource(anhTaiSanService);
        this.restAnhTaiSanMockMvc = MockMvcBuilders.standaloneSetup(anhTaiSanResource)
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
    public static AnhTaiSan createEntity(EntityManager em) {
        AnhTaiSan anhTaiSan = new AnhTaiSan()
            .url(DEFAULT_URL);
        return anhTaiSan;
    }

    @Before
    public void initTest() {
        anhTaiSan = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnhTaiSan() throws Exception {
        int databaseSizeBeforeCreate = anhTaiSanRepository.findAll().size();

        // Create the AnhTaiSan
        AnhTaiSanDTO anhTaiSanDTO = anhTaiSanMapper.toDto(anhTaiSan);
        restAnhTaiSanMockMvc.perform(post("/api/anh-tai-sans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anhTaiSanDTO)))
            .andExpect(status().isCreated());

        // Validate the AnhTaiSan in the database
        List<AnhTaiSan> anhTaiSanList = anhTaiSanRepository.findAll();
        assertThat(anhTaiSanList).hasSize(databaseSizeBeforeCreate + 1);
        AnhTaiSan testAnhTaiSan = anhTaiSanList.get(anhTaiSanList.size() - 1);
        assertThat(testAnhTaiSan.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    public void createAnhTaiSanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = anhTaiSanRepository.findAll().size();

        // Create the AnhTaiSan with an existing ID
        anhTaiSan.setId(1L);
        AnhTaiSanDTO anhTaiSanDTO = anhTaiSanMapper.toDto(anhTaiSan);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnhTaiSanMockMvc.perform(post("/api/anh-tai-sans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anhTaiSanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnhTaiSan in the database
        List<AnhTaiSan> anhTaiSanList = anhTaiSanRepository.findAll();
        assertThat(anhTaiSanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = anhTaiSanRepository.findAll().size();
        // set the field null
        anhTaiSan.setUrl(null);

        // Create the AnhTaiSan, which fails.
        AnhTaiSanDTO anhTaiSanDTO = anhTaiSanMapper.toDto(anhTaiSan);

        restAnhTaiSanMockMvc.perform(post("/api/anh-tai-sans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anhTaiSanDTO)))
            .andExpect(status().isBadRequest());

        List<AnhTaiSan> anhTaiSanList = anhTaiSanRepository.findAll();
        assertThat(anhTaiSanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAnhTaiSans() throws Exception {
        // Initialize the database
        anhTaiSanRepository.saveAndFlush(anhTaiSan);

        // Get all the anhTaiSanList
        restAnhTaiSanMockMvc.perform(get("/api/anh-tai-sans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anhTaiSan.getId().intValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())));
    }

    @Test
    @Transactional
    public void getAnhTaiSan() throws Exception {
        // Initialize the database
        anhTaiSanRepository.saveAndFlush(anhTaiSan);

        // Get the anhTaiSan
        restAnhTaiSanMockMvc.perform(get("/api/anh-tai-sans/{id}", anhTaiSan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(anhTaiSan.getId().intValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAnhTaiSan() throws Exception {
        // Get the anhTaiSan
        restAnhTaiSanMockMvc.perform(get("/api/anh-tai-sans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnhTaiSan() throws Exception {
        // Initialize the database
        anhTaiSanRepository.saveAndFlush(anhTaiSan);
        int databaseSizeBeforeUpdate = anhTaiSanRepository.findAll().size();

        // Update the anhTaiSan
        AnhTaiSan updatedAnhTaiSan = anhTaiSanRepository.findOne(anhTaiSan.getId());
        // Disconnect from session so that the updates on updatedAnhTaiSan are not directly saved in db
        em.detach(updatedAnhTaiSan);
        updatedAnhTaiSan
            .url(UPDATED_URL);
        AnhTaiSanDTO anhTaiSanDTO = anhTaiSanMapper.toDto(updatedAnhTaiSan);

        restAnhTaiSanMockMvc.perform(put("/api/anh-tai-sans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anhTaiSanDTO)))
            .andExpect(status().isOk());

        // Validate the AnhTaiSan in the database
        List<AnhTaiSan> anhTaiSanList = anhTaiSanRepository.findAll();
        assertThat(anhTaiSanList).hasSize(databaseSizeBeforeUpdate);
        AnhTaiSan testAnhTaiSan = anhTaiSanList.get(anhTaiSanList.size() - 1);
        assertThat(testAnhTaiSan.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingAnhTaiSan() throws Exception {
        int databaseSizeBeforeUpdate = anhTaiSanRepository.findAll().size();

        // Create the AnhTaiSan
        AnhTaiSanDTO anhTaiSanDTO = anhTaiSanMapper.toDto(anhTaiSan);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAnhTaiSanMockMvc.perform(put("/api/anh-tai-sans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anhTaiSanDTO)))
            .andExpect(status().isCreated());

        // Validate the AnhTaiSan in the database
        List<AnhTaiSan> anhTaiSanList = anhTaiSanRepository.findAll();
        assertThat(anhTaiSanList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAnhTaiSan() throws Exception {
        // Initialize the database
        anhTaiSanRepository.saveAndFlush(anhTaiSan);
        int databaseSizeBeforeDelete = anhTaiSanRepository.findAll().size();

        // Get the anhTaiSan
        restAnhTaiSanMockMvc.perform(delete("/api/anh-tai-sans/{id}", anhTaiSan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AnhTaiSan> anhTaiSanList = anhTaiSanRepository.findAll();
        assertThat(anhTaiSanList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnhTaiSan.class);
        AnhTaiSan anhTaiSan1 = new AnhTaiSan();
        anhTaiSan1.setId(1L);
        AnhTaiSan anhTaiSan2 = new AnhTaiSan();
        anhTaiSan2.setId(anhTaiSan1.getId());
        assertThat(anhTaiSan1).isEqualTo(anhTaiSan2);
        anhTaiSan2.setId(2L);
        assertThat(anhTaiSan1).isNotEqualTo(anhTaiSan2);
        anhTaiSan1.setId(null);
        assertThat(anhTaiSan1).isNotEqualTo(anhTaiSan2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnhTaiSanDTO.class);
        AnhTaiSanDTO anhTaiSanDTO1 = new AnhTaiSanDTO();
        anhTaiSanDTO1.setId(1L);
        AnhTaiSanDTO anhTaiSanDTO2 = new AnhTaiSanDTO();
        assertThat(anhTaiSanDTO1).isNotEqualTo(anhTaiSanDTO2);
        anhTaiSanDTO2.setId(anhTaiSanDTO1.getId());
        assertThat(anhTaiSanDTO1).isEqualTo(anhTaiSanDTO2);
        anhTaiSanDTO2.setId(2L);
        assertThat(anhTaiSanDTO1).isNotEqualTo(anhTaiSanDTO2);
        anhTaiSanDTO1.setId(null);
        assertThat(anhTaiSanDTO1).isNotEqualTo(anhTaiSanDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(anhTaiSanMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(anhTaiSanMapper.fromId(null)).isNull();
    }
}
