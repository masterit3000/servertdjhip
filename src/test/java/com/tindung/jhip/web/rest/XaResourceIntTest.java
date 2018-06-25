package com.tindung.jhip.web.rest;

import com.tindung.jhip.ServertdjhipApp;

import com.tindung.jhip.domain.Xa;
import com.tindung.jhip.repository.XaRepository;
import com.tindung.jhip.service.XaService;
import com.tindung.jhip.service.dto.XaDTO;
import com.tindung.jhip.service.mapper.XaMapper;
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
 * Test class for the XaResource REST controller.
 *
 * @see XaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServertdjhipApp.class)
public class XaResourceIntTest {

    private static final String DEFAULT_TEN = "AAAAAAAAAA";
    private static final String UPDATED_TEN = "BBBBBBBBBB";

    @Autowired
    private XaRepository xaRepository;

    @Autowired
    private XaMapper xaMapper;

    @Autowired
    private XaService xaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restXaMockMvc;

    private Xa xa;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final XaResource xaResource = new XaResource(xaService);
        this.restXaMockMvc = MockMvcBuilders.standaloneSetup(xaResource)
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
    public static Xa createEntity(EntityManager em) {
        Xa xa = new Xa()
            .ten(DEFAULT_TEN);
        return xa;
    }

    @Before
    public void initTest() {
        xa = createEntity(em);
    }

    @Test
    @Transactional
    public void createXa() throws Exception {
        int databaseSizeBeforeCreate = xaRepository.findAll().size();

        // Create the Xa
        XaDTO xaDTO = xaMapper.toDto(xa);
        restXaMockMvc.perform(post("/api/xas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(xaDTO)))
            .andExpect(status().isCreated());

        // Validate the Xa in the database
        List<Xa> xaList = xaRepository.findAll();
        assertThat(xaList).hasSize(databaseSizeBeforeCreate + 1);
        Xa testXa = xaList.get(xaList.size() - 1);
        assertThat(testXa.getTen()).isEqualTo(DEFAULT_TEN);
    }

    @Test
    @Transactional
    public void createXaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = xaRepository.findAll().size();

        // Create the Xa with an existing ID
        xa.setId(1L);
        XaDTO xaDTO = xaMapper.toDto(xa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restXaMockMvc.perform(post("/api/xas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(xaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Xa in the database
        List<Xa> xaList = xaRepository.findAll();
        assertThat(xaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTenIsRequired() throws Exception {
        int databaseSizeBeforeTest = xaRepository.findAll().size();
        // set the field null
        xa.setTen(null);

        // Create the Xa, which fails.
        XaDTO xaDTO = xaMapper.toDto(xa);

        restXaMockMvc.perform(post("/api/xas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(xaDTO)))
            .andExpect(status().isBadRequest());

        List<Xa> xaList = xaRepository.findAll();
        assertThat(xaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllXas() throws Exception {
        // Initialize the database
        xaRepository.saveAndFlush(xa);

        // Get all the xaList
        restXaMockMvc.perform(get("/api/xas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(xa.getId().intValue())))
            .andExpect(jsonPath("$.[*].ten").value(hasItem(DEFAULT_TEN.toString())));
    }

    @Test
    @Transactional
    public void getXa() throws Exception {
        // Initialize the database
        xaRepository.saveAndFlush(xa);

        // Get the xa
        restXaMockMvc.perform(get("/api/xas/{id}", xa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(xa.getId().intValue()))
            .andExpect(jsonPath("$.ten").value(DEFAULT_TEN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingXa() throws Exception {
        // Get the xa
        restXaMockMvc.perform(get("/api/xas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateXa() throws Exception {
        // Initialize the database
        xaRepository.saveAndFlush(xa);
        int databaseSizeBeforeUpdate = xaRepository.findAll().size();

        // Update the xa
        Xa updatedXa = xaRepository.findOne(xa.getId());
        // Disconnect from session so that the updates on updatedXa are not directly saved in db
        em.detach(updatedXa);
        updatedXa
            .ten(UPDATED_TEN);
        XaDTO xaDTO = xaMapper.toDto(updatedXa);

        restXaMockMvc.perform(put("/api/xas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(xaDTO)))
            .andExpect(status().isOk());

        // Validate the Xa in the database
        List<Xa> xaList = xaRepository.findAll();
        assertThat(xaList).hasSize(databaseSizeBeforeUpdate);
        Xa testXa = xaList.get(xaList.size() - 1);
        assertThat(testXa.getTen()).isEqualTo(UPDATED_TEN);
    }

    @Test
    @Transactional
    public void updateNonExistingXa() throws Exception {
        int databaseSizeBeforeUpdate = xaRepository.findAll().size();

        // Create the Xa
        XaDTO xaDTO = xaMapper.toDto(xa);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restXaMockMvc.perform(put("/api/xas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(xaDTO)))
            .andExpect(status().isCreated());

        // Validate the Xa in the database
        List<Xa> xaList = xaRepository.findAll();
        assertThat(xaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteXa() throws Exception {
        // Initialize the database
        xaRepository.saveAndFlush(xa);
        int databaseSizeBeforeDelete = xaRepository.findAll().size();

        // Get the xa
        restXaMockMvc.perform(delete("/api/xas/{id}", xa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Xa> xaList = xaRepository.findAll();
        assertThat(xaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Xa.class);
        Xa xa1 = new Xa();
        xa1.setId(1L);
        Xa xa2 = new Xa();
        xa2.setId(xa1.getId());
        assertThat(xa1).isEqualTo(xa2);
        xa2.setId(2L);
        assertThat(xa1).isNotEqualTo(xa2);
        xa1.setId(null);
        assertThat(xa1).isNotEqualTo(xa2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(XaDTO.class);
        XaDTO xaDTO1 = new XaDTO();
        xaDTO1.setId(1L);
        XaDTO xaDTO2 = new XaDTO();
        assertThat(xaDTO1).isNotEqualTo(xaDTO2);
        xaDTO2.setId(xaDTO1.getId());
        assertThat(xaDTO1).isEqualTo(xaDTO2);
        xaDTO2.setId(2L);
        assertThat(xaDTO1).isNotEqualTo(xaDTO2);
        xaDTO1.setId(null);
        assertThat(xaDTO1).isNotEqualTo(xaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(xaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(xaMapper.fromId(null)).isNull();
    }
}
