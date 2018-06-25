package com.tindung.jhip.web.rest;

import com.tindung.jhip.ServertdjhipApp;

import com.tindung.jhip.domain.AnhKhachHang;
import com.tindung.jhip.repository.AnhKhachHangRepository;
import com.tindung.jhip.service.AnhKhachHangService;
import com.tindung.jhip.service.dto.AnhKhachHangDTO;
import com.tindung.jhip.service.mapper.AnhKhachHangMapper;
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
 * Test class for the AnhKhachHangResource REST controller.
 *
 * @see AnhKhachHangResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServertdjhipApp.class)
public class AnhKhachHangResourceIntTest {

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private AnhKhachHangRepository anhKhachHangRepository;

    @Autowired
    private AnhKhachHangMapper anhKhachHangMapper;

    @Autowired
    private AnhKhachHangService anhKhachHangService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAnhKhachHangMockMvc;

    private AnhKhachHang anhKhachHang;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnhKhachHangResource anhKhachHangResource = new AnhKhachHangResource(anhKhachHangService);
        this.restAnhKhachHangMockMvc = MockMvcBuilders.standaloneSetup(anhKhachHangResource)
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
    public static AnhKhachHang createEntity(EntityManager em) {
        AnhKhachHang anhKhachHang = new AnhKhachHang()
            .url(DEFAULT_URL);
        return anhKhachHang;
    }

    @Before
    public void initTest() {
        anhKhachHang = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnhKhachHang() throws Exception {
        int databaseSizeBeforeCreate = anhKhachHangRepository.findAll().size();

        // Create the AnhKhachHang
        AnhKhachHangDTO anhKhachHangDTO = anhKhachHangMapper.toDto(anhKhachHang);
        restAnhKhachHangMockMvc.perform(post("/api/anh-khach-hangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anhKhachHangDTO)))
            .andExpect(status().isCreated());

        // Validate the AnhKhachHang in the database
        List<AnhKhachHang> anhKhachHangList = anhKhachHangRepository.findAll();
        assertThat(anhKhachHangList).hasSize(databaseSizeBeforeCreate + 1);
        AnhKhachHang testAnhKhachHang = anhKhachHangList.get(anhKhachHangList.size() - 1);
        assertThat(testAnhKhachHang.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    public void createAnhKhachHangWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = anhKhachHangRepository.findAll().size();

        // Create the AnhKhachHang with an existing ID
        anhKhachHang.setId(1L);
        AnhKhachHangDTO anhKhachHangDTO = anhKhachHangMapper.toDto(anhKhachHang);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnhKhachHangMockMvc.perform(post("/api/anh-khach-hangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anhKhachHangDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnhKhachHang in the database
        List<AnhKhachHang> anhKhachHangList = anhKhachHangRepository.findAll();
        assertThat(anhKhachHangList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = anhKhachHangRepository.findAll().size();
        // set the field null
        anhKhachHang.setUrl(null);

        // Create the AnhKhachHang, which fails.
        AnhKhachHangDTO anhKhachHangDTO = anhKhachHangMapper.toDto(anhKhachHang);

        restAnhKhachHangMockMvc.perform(post("/api/anh-khach-hangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anhKhachHangDTO)))
            .andExpect(status().isBadRequest());

        List<AnhKhachHang> anhKhachHangList = anhKhachHangRepository.findAll();
        assertThat(anhKhachHangList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAnhKhachHangs() throws Exception {
        // Initialize the database
        anhKhachHangRepository.saveAndFlush(anhKhachHang);

        // Get all the anhKhachHangList
        restAnhKhachHangMockMvc.perform(get("/api/anh-khach-hangs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anhKhachHang.getId().intValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())));
    }

    @Test
    @Transactional
    public void getAnhKhachHang() throws Exception {
        // Initialize the database
        anhKhachHangRepository.saveAndFlush(anhKhachHang);

        // Get the anhKhachHang
        restAnhKhachHangMockMvc.perform(get("/api/anh-khach-hangs/{id}", anhKhachHang.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(anhKhachHang.getId().intValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAnhKhachHang() throws Exception {
        // Get the anhKhachHang
        restAnhKhachHangMockMvc.perform(get("/api/anh-khach-hangs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnhKhachHang() throws Exception {
        // Initialize the database
        anhKhachHangRepository.saveAndFlush(anhKhachHang);
        int databaseSizeBeforeUpdate = anhKhachHangRepository.findAll().size();

        // Update the anhKhachHang
        AnhKhachHang updatedAnhKhachHang = anhKhachHangRepository.findOne(anhKhachHang.getId());
        // Disconnect from session so that the updates on updatedAnhKhachHang are not directly saved in db
        em.detach(updatedAnhKhachHang);
        updatedAnhKhachHang
            .url(UPDATED_URL);
        AnhKhachHangDTO anhKhachHangDTO = anhKhachHangMapper.toDto(updatedAnhKhachHang);

        restAnhKhachHangMockMvc.perform(put("/api/anh-khach-hangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anhKhachHangDTO)))
            .andExpect(status().isOk());

        // Validate the AnhKhachHang in the database
        List<AnhKhachHang> anhKhachHangList = anhKhachHangRepository.findAll();
        assertThat(anhKhachHangList).hasSize(databaseSizeBeforeUpdate);
        AnhKhachHang testAnhKhachHang = anhKhachHangList.get(anhKhachHangList.size() - 1);
        assertThat(testAnhKhachHang.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingAnhKhachHang() throws Exception {
        int databaseSizeBeforeUpdate = anhKhachHangRepository.findAll().size();

        // Create the AnhKhachHang
        AnhKhachHangDTO anhKhachHangDTO = anhKhachHangMapper.toDto(anhKhachHang);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAnhKhachHangMockMvc.perform(put("/api/anh-khach-hangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anhKhachHangDTO)))
            .andExpect(status().isCreated());

        // Validate the AnhKhachHang in the database
        List<AnhKhachHang> anhKhachHangList = anhKhachHangRepository.findAll();
        assertThat(anhKhachHangList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAnhKhachHang() throws Exception {
        // Initialize the database
        anhKhachHangRepository.saveAndFlush(anhKhachHang);
        int databaseSizeBeforeDelete = anhKhachHangRepository.findAll().size();

        // Get the anhKhachHang
        restAnhKhachHangMockMvc.perform(delete("/api/anh-khach-hangs/{id}", anhKhachHang.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AnhKhachHang> anhKhachHangList = anhKhachHangRepository.findAll();
        assertThat(anhKhachHangList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnhKhachHang.class);
        AnhKhachHang anhKhachHang1 = new AnhKhachHang();
        anhKhachHang1.setId(1L);
        AnhKhachHang anhKhachHang2 = new AnhKhachHang();
        anhKhachHang2.setId(anhKhachHang1.getId());
        assertThat(anhKhachHang1).isEqualTo(anhKhachHang2);
        anhKhachHang2.setId(2L);
        assertThat(anhKhachHang1).isNotEqualTo(anhKhachHang2);
        anhKhachHang1.setId(null);
        assertThat(anhKhachHang1).isNotEqualTo(anhKhachHang2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnhKhachHangDTO.class);
        AnhKhachHangDTO anhKhachHangDTO1 = new AnhKhachHangDTO();
        anhKhachHangDTO1.setId(1L);
        AnhKhachHangDTO anhKhachHangDTO2 = new AnhKhachHangDTO();
        assertThat(anhKhachHangDTO1).isNotEqualTo(anhKhachHangDTO2);
        anhKhachHangDTO2.setId(anhKhachHangDTO1.getId());
        assertThat(anhKhachHangDTO1).isEqualTo(anhKhachHangDTO2);
        anhKhachHangDTO2.setId(2L);
        assertThat(anhKhachHangDTO1).isNotEqualTo(anhKhachHangDTO2);
        anhKhachHangDTO1.setId(null);
        assertThat(anhKhachHangDTO1).isNotEqualTo(anhKhachHangDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(anhKhachHangMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(anhKhachHangMapper.fromId(null)).isNull();
    }
}
