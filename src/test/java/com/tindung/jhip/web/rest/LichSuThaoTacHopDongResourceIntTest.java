package com.tindung.jhip.web.rest;

import com.tindung.jhip.ServertdjhipApp;

import com.tindung.jhip.domain.LichSuThaoTacHopDong;
import com.tindung.jhip.repository.LichSuThaoTacHopDongRepository;
import com.tindung.jhip.service.LichSuThaoTacHopDongService;
import com.tindung.jhip.service.dto.LichSuThaoTacHopDongDTO;
import com.tindung.jhip.service.mapper.LichSuThaoTacHopDongMapper;
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
 * Test class for the LichSuThaoTacHopDongResource REST controller.
 *
 * @see LichSuThaoTacHopDongResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServertdjhipApp.class)
public class LichSuThaoTacHopDongResourceIntTest {

    private static final ZonedDateTime DEFAULT_THOIGIAN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_THOIGIAN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_NOIDUNG = "AAAAAAAAAA";
    private static final String UPDATED_NOIDUNG = "BBBBBBBBBB";

    @Autowired
    private LichSuThaoTacHopDongRepository lichSuThaoTacHopDongRepository;

    @Autowired
    private LichSuThaoTacHopDongMapper lichSuThaoTacHopDongMapper;

    @Autowired
    private LichSuThaoTacHopDongService lichSuThaoTacHopDongService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLichSuThaoTacHopDongMockMvc;

    private LichSuThaoTacHopDong lichSuThaoTacHopDong;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LichSuThaoTacHopDongResource lichSuThaoTacHopDongResource = new LichSuThaoTacHopDongResource(lichSuThaoTacHopDongService);
        this.restLichSuThaoTacHopDongMockMvc = MockMvcBuilders.standaloneSetup(lichSuThaoTacHopDongResource)
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
    public static LichSuThaoTacHopDong createEntity(EntityManager em) {
        LichSuThaoTacHopDong lichSuThaoTacHopDong = new LichSuThaoTacHopDong()
            .thoigian(DEFAULT_THOIGIAN)
            .noidung(DEFAULT_NOIDUNG);
        return lichSuThaoTacHopDong;
    }

    @Before
    public void initTest() {
        lichSuThaoTacHopDong = createEntity(em);
    }

    @Test
    @Transactional
    public void createLichSuThaoTacHopDong() throws Exception {
        int databaseSizeBeforeCreate = lichSuThaoTacHopDongRepository.findAll().size();

        // Create the LichSuThaoTacHopDong
        LichSuThaoTacHopDongDTO lichSuThaoTacHopDongDTO = lichSuThaoTacHopDongMapper.toDto(lichSuThaoTacHopDong);
        restLichSuThaoTacHopDongMockMvc.perform(post("/api/lich-su-thao-tac-hop-dongs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lichSuThaoTacHopDongDTO)))
            .andExpect(status().isCreated());

        // Validate the LichSuThaoTacHopDong in the database
        List<LichSuThaoTacHopDong> lichSuThaoTacHopDongList = lichSuThaoTacHopDongRepository.findAll();
        assertThat(lichSuThaoTacHopDongList).hasSize(databaseSizeBeforeCreate + 1);
        LichSuThaoTacHopDong testLichSuThaoTacHopDong = lichSuThaoTacHopDongList.get(lichSuThaoTacHopDongList.size() - 1);
        assertThat(testLichSuThaoTacHopDong.getThoigian()).isEqualTo(DEFAULT_THOIGIAN);
        assertThat(testLichSuThaoTacHopDong.getNoidung()).isEqualTo(DEFAULT_NOIDUNG);
    }

    @Test
    @Transactional
    public void createLichSuThaoTacHopDongWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lichSuThaoTacHopDongRepository.findAll().size();

        // Create the LichSuThaoTacHopDong with an existing ID
        lichSuThaoTacHopDong.setId(1L);
        LichSuThaoTacHopDongDTO lichSuThaoTacHopDongDTO = lichSuThaoTacHopDongMapper.toDto(lichSuThaoTacHopDong);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLichSuThaoTacHopDongMockMvc.perform(post("/api/lich-su-thao-tac-hop-dongs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lichSuThaoTacHopDongDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LichSuThaoTacHopDong in the database
        List<LichSuThaoTacHopDong> lichSuThaoTacHopDongList = lichSuThaoTacHopDongRepository.findAll();
        assertThat(lichSuThaoTacHopDongList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkThoigianIsRequired() throws Exception {
        int databaseSizeBeforeTest = lichSuThaoTacHopDongRepository.findAll().size();
        // set the field null
        lichSuThaoTacHopDong.setThoigian(null);

        // Create the LichSuThaoTacHopDong, which fails.
        LichSuThaoTacHopDongDTO lichSuThaoTacHopDongDTO = lichSuThaoTacHopDongMapper.toDto(lichSuThaoTacHopDong);

        restLichSuThaoTacHopDongMockMvc.perform(post("/api/lich-su-thao-tac-hop-dongs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lichSuThaoTacHopDongDTO)))
            .andExpect(status().isBadRequest());

        List<LichSuThaoTacHopDong> lichSuThaoTacHopDongList = lichSuThaoTacHopDongRepository.findAll();
        assertThat(lichSuThaoTacHopDongList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNoidungIsRequired() throws Exception {
        int databaseSizeBeforeTest = lichSuThaoTacHopDongRepository.findAll().size();
        // set the field null
        lichSuThaoTacHopDong.setNoidung(null);

        // Create the LichSuThaoTacHopDong, which fails.
        LichSuThaoTacHopDongDTO lichSuThaoTacHopDongDTO = lichSuThaoTacHopDongMapper.toDto(lichSuThaoTacHopDong);

        restLichSuThaoTacHopDongMockMvc.perform(post("/api/lich-su-thao-tac-hop-dongs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lichSuThaoTacHopDongDTO)))
            .andExpect(status().isBadRequest());

        List<LichSuThaoTacHopDong> lichSuThaoTacHopDongList = lichSuThaoTacHopDongRepository.findAll();
        assertThat(lichSuThaoTacHopDongList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLichSuThaoTacHopDongs() throws Exception {
        // Initialize the database
        lichSuThaoTacHopDongRepository.saveAndFlush(lichSuThaoTacHopDong);

        // Get all the lichSuThaoTacHopDongList
        restLichSuThaoTacHopDongMockMvc.perform(get("/api/lich-su-thao-tac-hop-dongs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lichSuThaoTacHopDong.getId().intValue())))
            .andExpect(jsonPath("$.[*].thoigian").value(hasItem(sameInstant(DEFAULT_THOIGIAN))))
            .andExpect(jsonPath("$.[*].noidung").value(hasItem(DEFAULT_NOIDUNG.toString())));
    }

    @Test
    @Transactional
    public void getLichSuThaoTacHopDong() throws Exception {
        // Initialize the database
        lichSuThaoTacHopDongRepository.saveAndFlush(lichSuThaoTacHopDong);

        // Get the lichSuThaoTacHopDong
        restLichSuThaoTacHopDongMockMvc.perform(get("/api/lich-su-thao-tac-hop-dongs/{id}", lichSuThaoTacHopDong.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lichSuThaoTacHopDong.getId().intValue()))
            .andExpect(jsonPath("$.thoigian").value(sameInstant(DEFAULT_THOIGIAN)))
            .andExpect(jsonPath("$.noidung").value(DEFAULT_NOIDUNG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLichSuThaoTacHopDong() throws Exception {
        // Get the lichSuThaoTacHopDong
        restLichSuThaoTacHopDongMockMvc.perform(get("/api/lich-su-thao-tac-hop-dongs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLichSuThaoTacHopDong() throws Exception {
        // Initialize the database
        lichSuThaoTacHopDongRepository.saveAndFlush(lichSuThaoTacHopDong);
        int databaseSizeBeforeUpdate = lichSuThaoTacHopDongRepository.findAll().size();

        // Update the lichSuThaoTacHopDong
        LichSuThaoTacHopDong updatedLichSuThaoTacHopDong = lichSuThaoTacHopDongRepository.findOne(lichSuThaoTacHopDong.getId());
        // Disconnect from session so that the updates on updatedLichSuThaoTacHopDong are not directly saved in db
        em.detach(updatedLichSuThaoTacHopDong);
        updatedLichSuThaoTacHopDong
            .thoigian(UPDATED_THOIGIAN)
            .noidung(UPDATED_NOIDUNG);
        LichSuThaoTacHopDongDTO lichSuThaoTacHopDongDTO = lichSuThaoTacHopDongMapper.toDto(updatedLichSuThaoTacHopDong);

        restLichSuThaoTacHopDongMockMvc.perform(put("/api/lich-su-thao-tac-hop-dongs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lichSuThaoTacHopDongDTO)))
            .andExpect(status().isOk());

        // Validate the LichSuThaoTacHopDong in the database
        List<LichSuThaoTacHopDong> lichSuThaoTacHopDongList = lichSuThaoTacHopDongRepository.findAll();
        assertThat(lichSuThaoTacHopDongList).hasSize(databaseSizeBeforeUpdate);
        LichSuThaoTacHopDong testLichSuThaoTacHopDong = lichSuThaoTacHopDongList.get(lichSuThaoTacHopDongList.size() - 1);
        assertThat(testLichSuThaoTacHopDong.getThoigian()).isEqualTo(UPDATED_THOIGIAN);
        assertThat(testLichSuThaoTacHopDong.getNoidung()).isEqualTo(UPDATED_NOIDUNG);
    }

    @Test
    @Transactional
    public void updateNonExistingLichSuThaoTacHopDong() throws Exception {
        int databaseSizeBeforeUpdate = lichSuThaoTacHopDongRepository.findAll().size();

        // Create the LichSuThaoTacHopDong
        LichSuThaoTacHopDongDTO lichSuThaoTacHopDongDTO = lichSuThaoTacHopDongMapper.toDto(lichSuThaoTacHopDong);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLichSuThaoTacHopDongMockMvc.perform(put("/api/lich-su-thao-tac-hop-dongs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lichSuThaoTacHopDongDTO)))
            .andExpect(status().isCreated());

        // Validate the LichSuThaoTacHopDong in the database
        List<LichSuThaoTacHopDong> lichSuThaoTacHopDongList = lichSuThaoTacHopDongRepository.findAll();
        assertThat(lichSuThaoTacHopDongList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLichSuThaoTacHopDong() throws Exception {
        // Initialize the database
        lichSuThaoTacHopDongRepository.saveAndFlush(lichSuThaoTacHopDong);
        int databaseSizeBeforeDelete = lichSuThaoTacHopDongRepository.findAll().size();

        // Get the lichSuThaoTacHopDong
        restLichSuThaoTacHopDongMockMvc.perform(delete("/api/lich-su-thao-tac-hop-dongs/{id}", lichSuThaoTacHopDong.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LichSuThaoTacHopDong> lichSuThaoTacHopDongList = lichSuThaoTacHopDongRepository.findAll();
        assertThat(lichSuThaoTacHopDongList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LichSuThaoTacHopDong.class);
        LichSuThaoTacHopDong lichSuThaoTacHopDong1 = new LichSuThaoTacHopDong();
        lichSuThaoTacHopDong1.setId(1L);
        LichSuThaoTacHopDong lichSuThaoTacHopDong2 = new LichSuThaoTacHopDong();
        lichSuThaoTacHopDong2.setId(lichSuThaoTacHopDong1.getId());
        assertThat(lichSuThaoTacHopDong1).isEqualTo(lichSuThaoTacHopDong2);
        lichSuThaoTacHopDong2.setId(2L);
        assertThat(lichSuThaoTacHopDong1).isNotEqualTo(lichSuThaoTacHopDong2);
        lichSuThaoTacHopDong1.setId(null);
        assertThat(lichSuThaoTacHopDong1).isNotEqualTo(lichSuThaoTacHopDong2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LichSuThaoTacHopDongDTO.class);
        LichSuThaoTacHopDongDTO lichSuThaoTacHopDongDTO1 = new LichSuThaoTacHopDongDTO();
        lichSuThaoTacHopDongDTO1.setId(1L);
        LichSuThaoTacHopDongDTO lichSuThaoTacHopDongDTO2 = new LichSuThaoTacHopDongDTO();
        assertThat(lichSuThaoTacHopDongDTO1).isNotEqualTo(lichSuThaoTacHopDongDTO2);
        lichSuThaoTacHopDongDTO2.setId(lichSuThaoTacHopDongDTO1.getId());
        assertThat(lichSuThaoTacHopDongDTO1).isEqualTo(lichSuThaoTacHopDongDTO2);
        lichSuThaoTacHopDongDTO2.setId(2L);
        assertThat(lichSuThaoTacHopDongDTO1).isNotEqualTo(lichSuThaoTacHopDongDTO2);
        lichSuThaoTacHopDongDTO1.setId(null);
        assertThat(lichSuThaoTacHopDongDTO1).isNotEqualTo(lichSuThaoTacHopDongDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(lichSuThaoTacHopDongMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(lichSuThaoTacHopDongMapper.fromId(null)).isNull();
    }
}
