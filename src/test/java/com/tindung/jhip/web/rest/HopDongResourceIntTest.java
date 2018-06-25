package com.tindung.jhip.web.rest;

import com.tindung.jhip.ServertdjhipApp;

import com.tindung.jhip.domain.HopDong;
import com.tindung.jhip.repository.HopDongRepository;
import com.tindung.jhip.service.HopDongService;
import com.tindung.jhip.service.dto.HopDongDTO;
import com.tindung.jhip.service.mapper.HopDongMapper;
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

import com.tindung.jhip.domain.enumeration.LOAIHOPDONG;
/**
 * Test class for the HopDongResource REST controller.
 *
 * @see HopDongResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServertdjhipApp.class)
public class HopDongResourceIntTest {

    private static final String DEFAULT_GHICHU = "AAAAAAAAAA";
    private static final String UPDATED_GHICHU = "BBBBBBBBBB";

    private static final LOAIHOPDONG DEFAULT_LOAIHOPDONG = LOAIHOPDONG.VAYLAI;
    private static final LOAIHOPDONG UPDATED_LOAIHOPDONG = LOAIHOPDONG.BATHO;

    private static final ZonedDateTime DEFAULT_NGAYTAO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_NGAYTAO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private HopDongRepository hopDongRepository;

    @Autowired
    private HopDongMapper hopDongMapper;

    @Autowired
    private HopDongService hopDongService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHopDongMockMvc;

    private HopDong hopDong;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HopDongResource hopDongResource = new HopDongResource(hopDongService);
        this.restHopDongMockMvc = MockMvcBuilders.standaloneSetup(hopDongResource)
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
    public static HopDong createEntity(EntityManager em) {
        HopDong hopDong = new HopDong()
            .ghichu(DEFAULT_GHICHU)
            .loaihopdong(DEFAULT_LOAIHOPDONG)
            .ngaytao(DEFAULT_NGAYTAO);
        return hopDong;
    }

    @Before
    public void initTest() {
        hopDong = createEntity(em);
    }

    @Test
    @Transactional
    public void createHopDong() throws Exception {
        int databaseSizeBeforeCreate = hopDongRepository.findAll().size();

        // Create the HopDong
        HopDongDTO hopDongDTO = hopDongMapper.toDto(hopDong);
        restHopDongMockMvc.perform(post("/api/hop-dongs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hopDongDTO)))
            .andExpect(status().isCreated());

        // Validate the HopDong in the database
        List<HopDong> hopDongList = hopDongRepository.findAll();
        assertThat(hopDongList).hasSize(databaseSizeBeforeCreate + 1);
        HopDong testHopDong = hopDongList.get(hopDongList.size() - 1);
        assertThat(testHopDong.getGhichu()).isEqualTo(DEFAULT_GHICHU);
        assertThat(testHopDong.getLoaihopdong()).isEqualTo(DEFAULT_LOAIHOPDONG);
        assertThat(testHopDong.getNgaytao()).isEqualTo(DEFAULT_NGAYTAO);
    }

    @Test
    @Transactional
    public void createHopDongWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hopDongRepository.findAll().size();

        // Create the HopDong with an existing ID
        hopDong.setId(1L);
        HopDongDTO hopDongDTO = hopDongMapper.toDto(hopDong);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHopDongMockMvc.perform(post("/api/hop-dongs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hopDongDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HopDong in the database
        List<HopDong> hopDongList = hopDongRepository.findAll();
        assertThat(hopDongList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLoaihopdongIsRequired() throws Exception {
        int databaseSizeBeforeTest = hopDongRepository.findAll().size();
        // set the field null
        hopDong.setLoaihopdong(null);

        // Create the HopDong, which fails.
        HopDongDTO hopDongDTO = hopDongMapper.toDto(hopDong);

        restHopDongMockMvc.perform(post("/api/hop-dongs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hopDongDTO)))
            .andExpect(status().isBadRequest());

        List<HopDong> hopDongList = hopDongRepository.findAll();
        assertThat(hopDongList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNgaytaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = hopDongRepository.findAll().size();
        // set the field null
        hopDong.setNgaytao(null);

        // Create the HopDong, which fails.
        HopDongDTO hopDongDTO = hopDongMapper.toDto(hopDong);

        restHopDongMockMvc.perform(post("/api/hop-dongs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hopDongDTO)))
            .andExpect(status().isBadRequest());

        List<HopDong> hopDongList = hopDongRepository.findAll();
        assertThat(hopDongList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHopDongs() throws Exception {
        // Initialize the database
        hopDongRepository.saveAndFlush(hopDong);

        // Get all the hopDongList
        restHopDongMockMvc.perform(get("/api/hop-dongs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hopDong.getId().intValue())))
            .andExpect(jsonPath("$.[*].ghichu").value(hasItem(DEFAULT_GHICHU.toString())))
            .andExpect(jsonPath("$.[*].loaihopdong").value(hasItem(DEFAULT_LOAIHOPDONG.toString())))
            .andExpect(jsonPath("$.[*].ngaytao").value(hasItem(sameInstant(DEFAULT_NGAYTAO))));
    }

    @Test
    @Transactional
    public void getHopDong() throws Exception {
        // Initialize the database
        hopDongRepository.saveAndFlush(hopDong);

        // Get the hopDong
        restHopDongMockMvc.perform(get("/api/hop-dongs/{id}", hopDong.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hopDong.getId().intValue()))
            .andExpect(jsonPath("$.ghichu").value(DEFAULT_GHICHU.toString()))
            .andExpect(jsonPath("$.loaihopdong").value(DEFAULT_LOAIHOPDONG.toString()))
            .andExpect(jsonPath("$.ngaytao").value(sameInstant(DEFAULT_NGAYTAO)));
    }

    @Test
    @Transactional
    public void getNonExistingHopDong() throws Exception {
        // Get the hopDong
        restHopDongMockMvc.perform(get("/api/hop-dongs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHopDong() throws Exception {
        // Initialize the database
        hopDongRepository.saveAndFlush(hopDong);
        int databaseSizeBeforeUpdate = hopDongRepository.findAll().size();

        // Update the hopDong
        HopDong updatedHopDong = hopDongRepository.findOne(hopDong.getId());
        // Disconnect from session so that the updates on updatedHopDong are not directly saved in db
        em.detach(updatedHopDong);
        updatedHopDong
            .ghichu(UPDATED_GHICHU)
            .loaihopdong(UPDATED_LOAIHOPDONG)
            .ngaytao(UPDATED_NGAYTAO);
        HopDongDTO hopDongDTO = hopDongMapper.toDto(updatedHopDong);

        restHopDongMockMvc.perform(put("/api/hop-dongs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hopDongDTO)))
            .andExpect(status().isOk());

        // Validate the HopDong in the database
        List<HopDong> hopDongList = hopDongRepository.findAll();
        assertThat(hopDongList).hasSize(databaseSizeBeforeUpdate);
        HopDong testHopDong = hopDongList.get(hopDongList.size() - 1);
        assertThat(testHopDong.getGhichu()).isEqualTo(UPDATED_GHICHU);
        assertThat(testHopDong.getLoaihopdong()).isEqualTo(UPDATED_LOAIHOPDONG);
        assertThat(testHopDong.getNgaytao()).isEqualTo(UPDATED_NGAYTAO);
    }

    @Test
    @Transactional
    public void updateNonExistingHopDong() throws Exception {
        int databaseSizeBeforeUpdate = hopDongRepository.findAll().size();

        // Create the HopDong
        HopDongDTO hopDongDTO = hopDongMapper.toDto(hopDong);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHopDongMockMvc.perform(put("/api/hop-dongs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hopDongDTO)))
            .andExpect(status().isCreated());

        // Validate the HopDong in the database
        List<HopDong> hopDongList = hopDongRepository.findAll();
        assertThat(hopDongList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHopDong() throws Exception {
        // Initialize the database
        hopDongRepository.saveAndFlush(hopDong);
        int databaseSizeBeforeDelete = hopDongRepository.findAll().size();

        // Get the hopDong
        restHopDongMockMvc.perform(delete("/api/hop-dongs/{id}", hopDong.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HopDong> hopDongList = hopDongRepository.findAll();
        assertThat(hopDongList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HopDong.class);
        HopDong hopDong1 = new HopDong();
        hopDong1.setId(1L);
        HopDong hopDong2 = new HopDong();
        hopDong2.setId(hopDong1.getId());
        assertThat(hopDong1).isEqualTo(hopDong2);
        hopDong2.setId(2L);
        assertThat(hopDong1).isNotEqualTo(hopDong2);
        hopDong1.setId(null);
        assertThat(hopDong1).isNotEqualTo(hopDong2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HopDongDTO.class);
        HopDongDTO hopDongDTO1 = new HopDongDTO();
        hopDongDTO1.setId(1L);
        HopDongDTO hopDongDTO2 = new HopDongDTO();
        assertThat(hopDongDTO1).isNotEqualTo(hopDongDTO2);
        hopDongDTO2.setId(hopDongDTO1.getId());
        assertThat(hopDongDTO1).isEqualTo(hopDongDTO2);
        hopDongDTO2.setId(2L);
        assertThat(hopDongDTO1).isNotEqualTo(hopDongDTO2);
        hopDongDTO1.setId(null);
        assertThat(hopDongDTO1).isNotEqualTo(hopDongDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(hopDongMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(hopDongMapper.fromId(null)).isNull();
    }
}
