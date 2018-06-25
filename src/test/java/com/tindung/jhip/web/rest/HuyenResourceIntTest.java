package com.tindung.jhip.web.rest;

import com.tindung.jhip.ServertdjhipApp;

import com.tindung.jhip.domain.Huyen;
import com.tindung.jhip.repository.HuyenRepository;
import com.tindung.jhip.service.HuyenService;
import com.tindung.jhip.service.dto.HuyenDTO;
import com.tindung.jhip.service.mapper.HuyenMapper;
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
 * Test class for the HuyenResource REST controller.
 *
 * @see HuyenResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServertdjhipApp.class)
public class HuyenResourceIntTest {

    private static final String DEFAULT_TEN = "AAAAAAAAAA";
    private static final String UPDATED_TEN = "BBBBBBBBBB";

    @Autowired
    private HuyenRepository huyenRepository;

    @Autowired
    private HuyenMapper huyenMapper;

    @Autowired
    private HuyenService huyenService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHuyenMockMvc;

    private Huyen huyen;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HuyenResource huyenResource = new HuyenResource(huyenService);
        this.restHuyenMockMvc = MockMvcBuilders.standaloneSetup(huyenResource)
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
    public static Huyen createEntity(EntityManager em) {
        Huyen huyen = new Huyen()
            .ten(DEFAULT_TEN);
        return huyen;
    }

    @Before
    public void initTest() {
        huyen = createEntity(em);
    }

    @Test
    @Transactional
    public void createHuyen() throws Exception {
        int databaseSizeBeforeCreate = huyenRepository.findAll().size();

        // Create the Huyen
        HuyenDTO huyenDTO = huyenMapper.toDto(huyen);
        restHuyenMockMvc.perform(post("/api/huyens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(huyenDTO)))
            .andExpect(status().isCreated());

        // Validate the Huyen in the database
        List<Huyen> huyenList = huyenRepository.findAll();
        assertThat(huyenList).hasSize(databaseSizeBeforeCreate + 1);
        Huyen testHuyen = huyenList.get(huyenList.size() - 1);
        assertThat(testHuyen.getTen()).isEqualTo(DEFAULT_TEN);
    }

    @Test
    @Transactional
    public void createHuyenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = huyenRepository.findAll().size();

        // Create the Huyen with an existing ID
        huyen.setId(1L);
        HuyenDTO huyenDTO = huyenMapper.toDto(huyen);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHuyenMockMvc.perform(post("/api/huyens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(huyenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Huyen in the database
        List<Huyen> huyenList = huyenRepository.findAll();
        assertThat(huyenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTenIsRequired() throws Exception {
        int databaseSizeBeforeTest = huyenRepository.findAll().size();
        // set the field null
        huyen.setTen(null);

        // Create the Huyen, which fails.
        HuyenDTO huyenDTO = huyenMapper.toDto(huyen);

        restHuyenMockMvc.perform(post("/api/huyens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(huyenDTO)))
            .andExpect(status().isBadRequest());

        List<Huyen> huyenList = huyenRepository.findAll();
        assertThat(huyenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHuyens() throws Exception {
        // Initialize the database
        huyenRepository.saveAndFlush(huyen);

        // Get all the huyenList
        restHuyenMockMvc.perform(get("/api/huyens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(huyen.getId().intValue())))
            .andExpect(jsonPath("$.[*].ten").value(hasItem(DEFAULT_TEN.toString())));
    }

    @Test
    @Transactional
    public void getHuyen() throws Exception {
        // Initialize the database
        huyenRepository.saveAndFlush(huyen);

        // Get the huyen
        restHuyenMockMvc.perform(get("/api/huyens/{id}", huyen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(huyen.getId().intValue()))
            .andExpect(jsonPath("$.ten").value(DEFAULT_TEN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHuyen() throws Exception {
        // Get the huyen
        restHuyenMockMvc.perform(get("/api/huyens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHuyen() throws Exception {
        // Initialize the database
        huyenRepository.saveAndFlush(huyen);
        int databaseSizeBeforeUpdate = huyenRepository.findAll().size();

        // Update the huyen
        Huyen updatedHuyen = huyenRepository.findOne(huyen.getId());
        // Disconnect from session so that the updates on updatedHuyen are not directly saved in db
        em.detach(updatedHuyen);
        updatedHuyen
            .ten(UPDATED_TEN);
        HuyenDTO huyenDTO = huyenMapper.toDto(updatedHuyen);

        restHuyenMockMvc.perform(put("/api/huyens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(huyenDTO)))
            .andExpect(status().isOk());

        // Validate the Huyen in the database
        List<Huyen> huyenList = huyenRepository.findAll();
        assertThat(huyenList).hasSize(databaseSizeBeforeUpdate);
        Huyen testHuyen = huyenList.get(huyenList.size() - 1);
        assertThat(testHuyen.getTen()).isEqualTo(UPDATED_TEN);
    }

    @Test
    @Transactional
    public void updateNonExistingHuyen() throws Exception {
        int databaseSizeBeforeUpdate = huyenRepository.findAll().size();

        // Create the Huyen
        HuyenDTO huyenDTO = huyenMapper.toDto(huyen);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHuyenMockMvc.perform(put("/api/huyens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(huyenDTO)))
            .andExpect(status().isCreated());

        // Validate the Huyen in the database
        List<Huyen> huyenList = huyenRepository.findAll();
        assertThat(huyenList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHuyen() throws Exception {
        // Initialize the database
        huyenRepository.saveAndFlush(huyen);
        int databaseSizeBeforeDelete = huyenRepository.findAll().size();

        // Get the huyen
        restHuyenMockMvc.perform(delete("/api/huyens/{id}", huyen.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Huyen> huyenList = huyenRepository.findAll();
        assertThat(huyenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Huyen.class);
        Huyen huyen1 = new Huyen();
        huyen1.setId(1L);
        Huyen huyen2 = new Huyen();
        huyen2.setId(huyen1.getId());
        assertThat(huyen1).isEqualTo(huyen2);
        huyen2.setId(2L);
        assertThat(huyen1).isNotEqualTo(huyen2);
        huyen1.setId(null);
        assertThat(huyen1).isNotEqualTo(huyen2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HuyenDTO.class);
        HuyenDTO huyenDTO1 = new HuyenDTO();
        huyenDTO1.setId(1L);
        HuyenDTO huyenDTO2 = new HuyenDTO();
        assertThat(huyenDTO1).isNotEqualTo(huyenDTO2);
        huyenDTO2.setId(huyenDTO1.getId());
        assertThat(huyenDTO1).isEqualTo(huyenDTO2);
        huyenDTO2.setId(2L);
        assertThat(huyenDTO1).isNotEqualTo(huyenDTO2);
        huyenDTO1.setId(null);
        assertThat(huyenDTO1).isNotEqualTo(huyenDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(huyenMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(huyenMapper.fromId(null)).isNull();
    }
}
