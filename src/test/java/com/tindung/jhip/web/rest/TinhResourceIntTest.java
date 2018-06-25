package com.tindung.jhip.web.rest;

import com.tindung.jhip.ServertdjhipApp;

import com.tindung.jhip.domain.Tinh;
import com.tindung.jhip.repository.TinhRepository;
import com.tindung.jhip.service.TinhService;
import com.tindung.jhip.service.dto.TinhDTO;
import com.tindung.jhip.service.mapper.TinhMapper;
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
 * Test class for the TinhResource REST controller.
 *
 * @see TinhResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServertdjhipApp.class)
public class TinhResourceIntTest {

    private static final String DEFAULT_TEN = "AAAAAAAAAA";
    private static final String UPDATED_TEN = "BBBBBBBBBB";

    @Autowired
    private TinhRepository tinhRepository;

    @Autowired
    private TinhMapper tinhMapper;

    @Autowired
    private TinhService tinhService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTinhMockMvc;

    private Tinh tinh;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TinhResource tinhResource = new TinhResource(tinhService);
        this.restTinhMockMvc = MockMvcBuilders.standaloneSetup(tinhResource)
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
    public static Tinh createEntity(EntityManager em) {
        Tinh tinh = new Tinh()
            .ten(DEFAULT_TEN);
        return tinh;
    }

    @Before
    public void initTest() {
        tinh = createEntity(em);
    }

    @Test
    @Transactional
    public void createTinh() throws Exception {
        int databaseSizeBeforeCreate = tinhRepository.findAll().size();

        // Create the Tinh
        TinhDTO tinhDTO = tinhMapper.toDto(tinh);
        restTinhMockMvc.perform(post("/api/tinhs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tinhDTO)))
            .andExpect(status().isCreated());

        // Validate the Tinh in the database
        List<Tinh> tinhList = tinhRepository.findAll();
        assertThat(tinhList).hasSize(databaseSizeBeforeCreate + 1);
        Tinh testTinh = tinhList.get(tinhList.size() - 1);
        assertThat(testTinh.getTen()).isEqualTo(DEFAULT_TEN);
    }

    @Test
    @Transactional
    public void createTinhWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tinhRepository.findAll().size();

        // Create the Tinh with an existing ID
        tinh.setId(1L);
        TinhDTO tinhDTO = tinhMapper.toDto(tinh);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTinhMockMvc.perform(post("/api/tinhs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tinhDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tinh in the database
        List<Tinh> tinhList = tinhRepository.findAll();
        assertThat(tinhList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTenIsRequired() throws Exception {
        int databaseSizeBeforeTest = tinhRepository.findAll().size();
        // set the field null
        tinh.setTen(null);

        // Create the Tinh, which fails.
        TinhDTO tinhDTO = tinhMapper.toDto(tinh);

        restTinhMockMvc.perform(post("/api/tinhs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tinhDTO)))
            .andExpect(status().isBadRequest());

        List<Tinh> tinhList = tinhRepository.findAll();
        assertThat(tinhList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTinhs() throws Exception {
        // Initialize the database
        tinhRepository.saveAndFlush(tinh);

        // Get all the tinhList
        restTinhMockMvc.perform(get("/api/tinhs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tinh.getId().intValue())))
            .andExpect(jsonPath("$.[*].ten").value(hasItem(DEFAULT_TEN.toString())));
    }

    @Test
    @Transactional
    public void getTinh() throws Exception {
        // Initialize the database
        tinhRepository.saveAndFlush(tinh);

        // Get the tinh
        restTinhMockMvc.perform(get("/api/tinhs/{id}", tinh.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tinh.getId().intValue()))
            .andExpect(jsonPath("$.ten").value(DEFAULT_TEN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTinh() throws Exception {
        // Get the tinh
        restTinhMockMvc.perform(get("/api/tinhs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTinh() throws Exception {
        // Initialize the database
        tinhRepository.saveAndFlush(tinh);
        int databaseSizeBeforeUpdate = tinhRepository.findAll().size();

        // Update the tinh
        Tinh updatedTinh = tinhRepository.findOne(tinh.getId());
        // Disconnect from session so that the updates on updatedTinh are not directly saved in db
        em.detach(updatedTinh);
        updatedTinh
            .ten(UPDATED_TEN);
        TinhDTO tinhDTO = tinhMapper.toDto(updatedTinh);

        restTinhMockMvc.perform(put("/api/tinhs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tinhDTO)))
            .andExpect(status().isOk());

        // Validate the Tinh in the database
        List<Tinh> tinhList = tinhRepository.findAll();
        assertThat(tinhList).hasSize(databaseSizeBeforeUpdate);
        Tinh testTinh = tinhList.get(tinhList.size() - 1);
        assertThat(testTinh.getTen()).isEqualTo(UPDATED_TEN);
    }

    @Test
    @Transactional
    public void updateNonExistingTinh() throws Exception {
        int databaseSizeBeforeUpdate = tinhRepository.findAll().size();

        // Create the Tinh
        TinhDTO tinhDTO = tinhMapper.toDto(tinh);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTinhMockMvc.perform(put("/api/tinhs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tinhDTO)))
            .andExpect(status().isCreated());

        // Validate the Tinh in the database
        List<Tinh> tinhList = tinhRepository.findAll();
        assertThat(tinhList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTinh() throws Exception {
        // Initialize the database
        tinhRepository.saveAndFlush(tinh);
        int databaseSizeBeforeDelete = tinhRepository.findAll().size();

        // Get the tinh
        restTinhMockMvc.perform(delete("/api/tinhs/{id}", tinh.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tinh> tinhList = tinhRepository.findAll();
        assertThat(tinhList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tinh.class);
        Tinh tinh1 = new Tinh();
        tinh1.setId(1L);
        Tinh tinh2 = new Tinh();
        tinh2.setId(tinh1.getId());
        assertThat(tinh1).isEqualTo(tinh2);
        tinh2.setId(2L);
        assertThat(tinh1).isNotEqualTo(tinh2);
        tinh1.setId(null);
        assertThat(tinh1).isNotEqualTo(tinh2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TinhDTO.class);
        TinhDTO tinhDTO1 = new TinhDTO();
        tinhDTO1.setId(1L);
        TinhDTO tinhDTO2 = new TinhDTO();
        assertThat(tinhDTO1).isNotEqualTo(tinhDTO2);
        tinhDTO2.setId(tinhDTO1.getId());
        assertThat(tinhDTO1).isEqualTo(tinhDTO2);
        tinhDTO2.setId(2L);
        assertThat(tinhDTO1).isNotEqualTo(tinhDTO2);
        tinhDTO1.setId(null);
        assertThat(tinhDTO1).isNotEqualTo(tinhDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tinhMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tinhMapper.fromId(null)).isNull();
    }
}
