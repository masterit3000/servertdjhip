package com.tindung.jhip.web.rest;

import com.tindung.jhip.ServertdjhipApp;

import com.tindung.jhip.domain.BatHo;
import com.tindung.jhip.repository.BatHoRepository;
import com.tindung.jhip.service.BatHoService;
import com.tindung.jhip.service.LichSuDongTienService;
import com.tindung.jhip.service.LichSuThaoTacHopDongService;
import com.tindung.jhip.service.NhanVienService;
import com.tindung.jhip.service.UserService;
import com.tindung.jhip.service.dto.BatHoDTO;
import com.tindung.jhip.service.mapper.BatHoMapper;
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
 * Test class for the BatHoResource REST controller.
 *
 * @see BatHoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServertdjhipApp.class)
public class BatHoResourceIntTest {

    private static final Double DEFAULT_TIENDUAKHACH = 1D;
    private static final Double UPDATED_TIENDUAKHACH = 2D;

    private static final Double DEFAULT_TONGTIEN = 1D;
    private static final Double UPDATED_TONGTIEN = 2D;

    private static final Integer DEFAULT_TONGSONGAY = 1;
    private static final Integer UPDATED_TONGSONGAY = 2;

    private static final Integer DEFAULT_CHUKY = 1;
    private static final Integer UPDATED_CHUKY = 2;
    @Autowired
    private LichSuDongTienService lichSuDongTienService;
    @Autowired
    private LichSuThaoTacHopDongService lichSuThaoTacHopDongService;
    @Autowired
    private BatHoRepository batHoRepository;

    @Autowired
    private BatHoMapper batHoMapper;

    @Autowired
    private BatHoService batHoService;
    @Autowired
    private UserService userService;
    @Autowired
    private NhanVienService nhanVienService;
    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBatHoMockMvc;

    private BatHo batHo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BatHoResource batHoResource = new BatHoResource(lichSuThaoTacHopDongService, lichSuDongTienService, batHoService, userService, nhanVienService);
        this.restBatHoMockMvc = MockMvcBuilders.standaloneSetup(batHoResource)
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
    public static BatHo createEntity(EntityManager em) {
        BatHo batHo = new BatHo()
                .tienduakhach(DEFAULT_TIENDUAKHACH)
                .tongtien(DEFAULT_TONGTIEN)
                .tongsongay(DEFAULT_TONGSONGAY)
                .chuky(DEFAULT_CHUKY);
        return batHo;
    }

    @Before
    public void initTest() {
        batHo = createEntity(em);
    }

    @Test
    @Transactional
    public void createBatHo() throws Exception {
        int databaseSizeBeforeCreate = batHoRepository.findAll().size();

        // Create the BatHo
        BatHoDTO batHoDTO = batHoMapper.toDto(batHo);
        restBatHoMockMvc.perform(post("/api/bat-hos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(batHoDTO)))
                .andExpect(status().isCreated());

        // Validate the BatHo in the database
        List<BatHo> batHoList = batHoRepository.findAll();
        assertThat(batHoList).hasSize(databaseSizeBeforeCreate + 1);
        BatHo testBatHo = batHoList.get(batHoList.size() - 1);
        assertThat(testBatHo.getTienduakhach()).isEqualTo(DEFAULT_TIENDUAKHACH);
        assertThat(testBatHo.getTongtien()).isEqualTo(DEFAULT_TONGTIEN);
        assertThat(testBatHo.getTongsongay()).isEqualTo(DEFAULT_TONGSONGAY);
        assertThat(testBatHo.getChuky()).isEqualTo(DEFAULT_CHUKY);
    }

    @Test
    @Transactional
    public void createBatHoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = batHoRepository.findAll().size();

        // Create the BatHo with an existing ID
        batHo.setId(1L);
        BatHoDTO batHoDTO = batHoMapper.toDto(batHo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBatHoMockMvc.perform(post("/api/bat-hos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(batHoDTO)))
                .andExpect(status().isBadRequest());

        // Validate the BatHo in the database
        List<BatHo> batHoList = batHoRepository.findAll();
        assertThat(batHoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTienduakhachIsRequired() throws Exception {
        int databaseSizeBeforeTest = batHoRepository.findAll().size();
        // set the field null
        batHo.setTienduakhach(null);

        // Create the BatHo, which fails.
        BatHoDTO batHoDTO = batHoMapper.toDto(batHo);

        restBatHoMockMvc.perform(post("/api/bat-hos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(batHoDTO)))
                .andExpect(status().isBadRequest());

        List<BatHo> batHoList = batHoRepository.findAll();
        assertThat(batHoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTongtienIsRequired() throws Exception {
        int databaseSizeBeforeTest = batHoRepository.findAll().size();
        // set the field null
        batHo.setTongtien(null);

        // Create the BatHo, which fails.
        BatHoDTO batHoDTO = batHoMapper.toDto(batHo);

        restBatHoMockMvc.perform(post("/api/bat-hos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(batHoDTO)))
                .andExpect(status().isBadRequest());

        List<BatHo> batHoList = batHoRepository.findAll();
        assertThat(batHoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTongsongayIsRequired() throws Exception {
        int databaseSizeBeforeTest = batHoRepository.findAll().size();
        // set the field null
        batHo.setTongsongay(null);

        // Create the BatHo, which fails.
        BatHoDTO batHoDTO = batHoMapper.toDto(batHo);

        restBatHoMockMvc.perform(post("/api/bat-hos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(batHoDTO)))
                .andExpect(status().isBadRequest());

        List<BatHo> batHoList = batHoRepository.findAll();
        assertThat(batHoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkChukyIsRequired() throws Exception {
        int databaseSizeBeforeTest = batHoRepository.findAll().size();
        // set the field null
        batHo.setChuky(null);

        // Create the BatHo, which fails.
        BatHoDTO batHoDTO = batHoMapper.toDto(batHo);

        restBatHoMockMvc.perform(post("/api/bat-hos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(batHoDTO)))
                .andExpect(status().isBadRequest());

        List<BatHo> batHoList = batHoRepository.findAll();
        assertThat(batHoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBatHos() throws Exception {
        // Initialize the database
        batHoRepository.saveAndFlush(batHo);

        // Get all the batHoList
        restBatHoMockMvc.perform(get("/api/bat-hos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(batHo.getId().intValue())))
                .andExpect(jsonPath("$.[*].tienduakhach").value(hasItem(DEFAULT_TIENDUAKHACH.doubleValue())))
                .andExpect(jsonPath("$.[*].tongtien").value(hasItem(DEFAULT_TONGTIEN.doubleValue())))
                .andExpect(jsonPath("$.[*].tongsongay").value(hasItem(DEFAULT_TONGSONGAY)))
                .andExpect(jsonPath("$.[*].chuky").value(hasItem(DEFAULT_CHUKY)));
    }

    @Test
    @Transactional
    public void getBatHo() throws Exception {
        // Initialize the database
        batHoRepository.saveAndFlush(batHo);

        // Get the batHo
        restBatHoMockMvc.perform(get("/api/bat-hos/{id}", batHo.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(batHo.getId().intValue()))
                .andExpect(jsonPath("$.tienduakhach").value(DEFAULT_TIENDUAKHACH.doubleValue()))
                .andExpect(jsonPath("$.tongtien").value(DEFAULT_TONGTIEN.doubleValue()))
                .andExpect(jsonPath("$.tongsongay").value(DEFAULT_TONGSONGAY))
                .andExpect(jsonPath("$.chuky").value(DEFAULT_CHUKY));
    }

    @Test
    @Transactional
    public void getNonExistingBatHo() throws Exception {
        // Get the batHo
        restBatHoMockMvc.perform(get("/api/bat-hos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBatHo() throws Exception {
        // Initialize the database
        batHoRepository.saveAndFlush(batHo);
        int databaseSizeBeforeUpdate = batHoRepository.findAll().size();

        // Update the batHo
        BatHo updatedBatHo = batHoRepository.findOne(batHo.getId());
        // Disconnect from session so that the updates on updatedBatHo are not directly saved in db
        em.detach(updatedBatHo);
        updatedBatHo
                .tienduakhach(UPDATED_TIENDUAKHACH)
                .tongtien(UPDATED_TONGTIEN)
                .tongsongay(UPDATED_TONGSONGAY)
                .chuky(UPDATED_CHUKY);
        BatHoDTO batHoDTO = batHoMapper.toDto(updatedBatHo);

        restBatHoMockMvc.perform(put("/api/bat-hos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(batHoDTO)))
                .andExpect(status().isOk());

        // Validate the BatHo in the database
        List<BatHo> batHoList = batHoRepository.findAll();
        assertThat(batHoList).hasSize(databaseSizeBeforeUpdate);
        BatHo testBatHo = batHoList.get(batHoList.size() - 1);
        assertThat(testBatHo.getTienduakhach()).isEqualTo(UPDATED_TIENDUAKHACH);
        assertThat(testBatHo.getTongtien()).isEqualTo(UPDATED_TONGTIEN);
        assertThat(testBatHo.getTongsongay()).isEqualTo(UPDATED_TONGSONGAY);
        assertThat(testBatHo.getChuky()).isEqualTo(UPDATED_CHUKY);
    }

    @Test
    @Transactional
    public void updateNonExistingBatHo() throws Exception {
        int databaseSizeBeforeUpdate = batHoRepository.findAll().size();

        // Create the BatHo
        BatHoDTO batHoDTO = batHoMapper.toDto(batHo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBatHoMockMvc.perform(put("/api/bat-hos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(batHoDTO)))
                .andExpect(status().isCreated());

        // Validate the BatHo in the database
        List<BatHo> batHoList = batHoRepository.findAll();
        assertThat(batHoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBatHo() throws Exception {
        // Initialize the database
        batHoRepository.saveAndFlush(batHo);
        int databaseSizeBeforeDelete = batHoRepository.findAll().size();

        // Get the batHo
        restBatHoMockMvc.perform(delete("/api/bat-hos/{id}", batHo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<BatHo> batHoList = batHoRepository.findAll();
        assertThat(batHoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BatHo.class);
        BatHo batHo1 = new BatHo();
        batHo1.setId(1L);
        BatHo batHo2 = new BatHo();
        batHo2.setId(batHo1.getId());
        assertThat(batHo1).isEqualTo(batHo2);
        batHo2.setId(2L);
        assertThat(batHo1).isNotEqualTo(batHo2);
        batHo1.setId(null);
        assertThat(batHo1).isNotEqualTo(batHo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BatHoDTO.class);
        BatHoDTO batHoDTO1 = new BatHoDTO();
        batHoDTO1.setId(1L);
        BatHoDTO batHoDTO2 = new BatHoDTO();
        assertThat(batHoDTO1).isNotEqualTo(batHoDTO2);
        batHoDTO2.setId(batHoDTO1.getId());
        assertThat(batHoDTO1).isEqualTo(batHoDTO2);
        batHoDTO2.setId(2L);
        assertThat(batHoDTO1).isNotEqualTo(batHoDTO2);
        batHoDTO1.setId(null);
        assertThat(batHoDTO1).isNotEqualTo(batHoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(batHoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(batHoMapper.fromId(null)).isNull();
    }
}
