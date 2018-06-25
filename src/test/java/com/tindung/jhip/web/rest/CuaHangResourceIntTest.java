package com.tindung.jhip.web.rest;

import com.tindung.jhip.ServertdjhipApp;

import com.tindung.jhip.domain.CuaHang;
import com.tindung.jhip.repository.CuaHangRepository;
import com.tindung.jhip.service.CuaHangService;
import com.tindung.jhip.service.dto.CuaHangDTO;
import com.tindung.jhip.service.mapper.CuaHangMapper;
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

import com.tindung.jhip.domain.enumeration.TrangThaiCuaHang;
/**
 * Test class for the CuaHangResource REST controller.
 *
 * @see CuaHangResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServertdjhipApp.class)
public class CuaHangResourceIntTest {

    private static final String DEFAULT_TEN = "AAAAAAAAAA";
    private static final String UPDATED_TEN = "BBBBBBBBBB";

    private static final String DEFAULT_DIACHI = "AAAAAAAAAA";
    private static final String UPDATED_DIACHI = "BBBBBBBBBB";

    private static final String DEFAULT_DIENTHOAI = "AAAAAAAAAA";
    private static final String UPDATED_DIENTHOAI = "BBBBBBBBBB";

    private static final TrangThaiCuaHang DEFAULT_TRANGTHAI = TrangThaiCuaHang.DUNGHOATDONG;
    private static final TrangThaiCuaHang UPDATED_TRANGTHAI = TrangThaiCuaHang.HOATDONG;

    private static final ZonedDateTime DEFAULT_NGAY_TAO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_NGAY_TAO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_GHI_CHU = "AAAAAAAAAA";
    private static final String UPDATED_GHI_CHU = "BBBBBBBBBB";

    @Autowired
    private CuaHangRepository cuaHangRepository;

    @Autowired
    private CuaHangMapper cuaHangMapper;

    @Autowired
    private CuaHangService cuaHangService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCuaHangMockMvc;

    private CuaHang cuaHang;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CuaHangResource cuaHangResource = new CuaHangResource(cuaHangService);
        this.restCuaHangMockMvc = MockMvcBuilders.standaloneSetup(cuaHangResource)
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
    public static CuaHang createEntity(EntityManager em) {
        CuaHang cuaHang = new CuaHang()
            .ten(DEFAULT_TEN)
            .diachi(DEFAULT_DIACHI)
            .dienthoai(DEFAULT_DIENTHOAI)
            .trangthai(DEFAULT_TRANGTHAI)
            .ngayTao(DEFAULT_NGAY_TAO)
            .ghiChu(DEFAULT_GHI_CHU);
        return cuaHang;
    }

    @Before
    public void initTest() {
        cuaHang = createEntity(em);
    }

    @Test
    @Transactional
    public void createCuaHang() throws Exception {
        int databaseSizeBeforeCreate = cuaHangRepository.findAll().size();

        // Create the CuaHang
        CuaHangDTO cuaHangDTO = cuaHangMapper.toDto(cuaHang);
        restCuaHangMockMvc.perform(post("/api/cua-hangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuaHangDTO)))
            .andExpect(status().isCreated());

        // Validate the CuaHang in the database
        List<CuaHang> cuaHangList = cuaHangRepository.findAll();
        assertThat(cuaHangList).hasSize(databaseSizeBeforeCreate + 1);
        CuaHang testCuaHang = cuaHangList.get(cuaHangList.size() - 1);
        assertThat(testCuaHang.getTen()).isEqualTo(DEFAULT_TEN);
        assertThat(testCuaHang.getDiachi()).isEqualTo(DEFAULT_DIACHI);
        assertThat(testCuaHang.getDienthoai()).isEqualTo(DEFAULT_DIENTHOAI);
        assertThat(testCuaHang.getTrangthai()).isEqualTo(DEFAULT_TRANGTHAI);
        assertThat(testCuaHang.getNgayTao()).isEqualTo(DEFAULT_NGAY_TAO);
        assertThat(testCuaHang.getGhiChu()).isEqualTo(DEFAULT_GHI_CHU);
    }

    @Test
    @Transactional
    public void createCuaHangWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cuaHangRepository.findAll().size();

        // Create the CuaHang with an existing ID
        cuaHang.setId(1L);
        CuaHangDTO cuaHangDTO = cuaHangMapper.toDto(cuaHang);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCuaHangMockMvc.perform(post("/api/cua-hangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuaHangDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CuaHang in the database
        List<CuaHang> cuaHangList = cuaHangRepository.findAll();
        assertThat(cuaHangList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTenIsRequired() throws Exception {
        int databaseSizeBeforeTest = cuaHangRepository.findAll().size();
        // set the field null
        cuaHang.setTen(null);

        // Create the CuaHang, which fails.
        CuaHangDTO cuaHangDTO = cuaHangMapper.toDto(cuaHang);

        restCuaHangMockMvc.perform(post("/api/cua-hangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuaHangDTO)))
            .andExpect(status().isBadRequest());

        List<CuaHang> cuaHangList = cuaHangRepository.findAll();
        assertThat(cuaHangList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCuaHangs() throws Exception {
        // Initialize the database
        cuaHangRepository.saveAndFlush(cuaHang);

        // Get all the cuaHangList
        restCuaHangMockMvc.perform(get("/api/cua-hangs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cuaHang.getId().intValue())))
            .andExpect(jsonPath("$.[*].ten").value(hasItem(DEFAULT_TEN.toString())))
            .andExpect(jsonPath("$.[*].diachi").value(hasItem(DEFAULT_DIACHI.toString())))
            .andExpect(jsonPath("$.[*].dienthoai").value(hasItem(DEFAULT_DIENTHOAI.toString())))
            .andExpect(jsonPath("$.[*].trangthai").value(hasItem(DEFAULT_TRANGTHAI.toString())))
            .andExpect(jsonPath("$.[*].ngayTao").value(hasItem(sameInstant(DEFAULT_NGAY_TAO))))
            .andExpect(jsonPath("$.[*].ghiChu").value(hasItem(DEFAULT_GHI_CHU.toString())));
    }

    @Test
    @Transactional
    public void getCuaHang() throws Exception {
        // Initialize the database
        cuaHangRepository.saveAndFlush(cuaHang);

        // Get the cuaHang
        restCuaHangMockMvc.perform(get("/api/cua-hangs/{id}", cuaHang.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cuaHang.getId().intValue()))
            .andExpect(jsonPath("$.ten").value(DEFAULT_TEN.toString()))
            .andExpect(jsonPath("$.diachi").value(DEFAULT_DIACHI.toString()))
            .andExpect(jsonPath("$.dienthoai").value(DEFAULT_DIENTHOAI.toString()))
            .andExpect(jsonPath("$.trangthai").value(DEFAULT_TRANGTHAI.toString()))
            .andExpect(jsonPath("$.ngayTao").value(sameInstant(DEFAULT_NGAY_TAO)))
            .andExpect(jsonPath("$.ghiChu").value(DEFAULT_GHI_CHU.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCuaHang() throws Exception {
        // Get the cuaHang
        restCuaHangMockMvc.perform(get("/api/cua-hangs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCuaHang() throws Exception {
        // Initialize the database
        cuaHangRepository.saveAndFlush(cuaHang);
        int databaseSizeBeforeUpdate = cuaHangRepository.findAll().size();

        // Update the cuaHang
        CuaHang updatedCuaHang = cuaHangRepository.findOne(cuaHang.getId());
        // Disconnect from session so that the updates on updatedCuaHang are not directly saved in db
        em.detach(updatedCuaHang);
        updatedCuaHang
            .ten(UPDATED_TEN)
            .diachi(UPDATED_DIACHI)
            .dienthoai(UPDATED_DIENTHOAI)
            .trangthai(UPDATED_TRANGTHAI)
            .ngayTao(UPDATED_NGAY_TAO)
            .ghiChu(UPDATED_GHI_CHU);
        CuaHangDTO cuaHangDTO = cuaHangMapper.toDto(updatedCuaHang);

        restCuaHangMockMvc.perform(put("/api/cua-hangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuaHangDTO)))
            .andExpect(status().isOk());

        // Validate the CuaHang in the database
        List<CuaHang> cuaHangList = cuaHangRepository.findAll();
        assertThat(cuaHangList).hasSize(databaseSizeBeforeUpdate);
        CuaHang testCuaHang = cuaHangList.get(cuaHangList.size() - 1);
        assertThat(testCuaHang.getTen()).isEqualTo(UPDATED_TEN);
        assertThat(testCuaHang.getDiachi()).isEqualTo(UPDATED_DIACHI);
        assertThat(testCuaHang.getDienthoai()).isEqualTo(UPDATED_DIENTHOAI);
        assertThat(testCuaHang.getTrangthai()).isEqualTo(UPDATED_TRANGTHAI);
        assertThat(testCuaHang.getNgayTao()).isEqualTo(UPDATED_NGAY_TAO);
        assertThat(testCuaHang.getGhiChu()).isEqualTo(UPDATED_GHI_CHU);
    }

    @Test
    @Transactional
    public void updateNonExistingCuaHang() throws Exception {
        int databaseSizeBeforeUpdate = cuaHangRepository.findAll().size();

        // Create the CuaHang
        CuaHangDTO cuaHangDTO = cuaHangMapper.toDto(cuaHang);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCuaHangMockMvc.perform(put("/api/cua-hangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuaHangDTO)))
            .andExpect(status().isCreated());

        // Validate the CuaHang in the database
        List<CuaHang> cuaHangList = cuaHangRepository.findAll();
        assertThat(cuaHangList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCuaHang() throws Exception {
        // Initialize the database
        cuaHangRepository.saveAndFlush(cuaHang);
        int databaseSizeBeforeDelete = cuaHangRepository.findAll().size();

        // Get the cuaHang
        restCuaHangMockMvc.perform(delete("/api/cua-hangs/{id}", cuaHang.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CuaHang> cuaHangList = cuaHangRepository.findAll();
        assertThat(cuaHangList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CuaHang.class);
        CuaHang cuaHang1 = new CuaHang();
        cuaHang1.setId(1L);
        CuaHang cuaHang2 = new CuaHang();
        cuaHang2.setId(cuaHang1.getId());
        assertThat(cuaHang1).isEqualTo(cuaHang2);
        cuaHang2.setId(2L);
        assertThat(cuaHang1).isNotEqualTo(cuaHang2);
        cuaHang1.setId(null);
        assertThat(cuaHang1).isNotEqualTo(cuaHang2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CuaHangDTO.class);
        CuaHangDTO cuaHangDTO1 = new CuaHangDTO();
        cuaHangDTO1.setId(1L);
        CuaHangDTO cuaHangDTO2 = new CuaHangDTO();
        assertThat(cuaHangDTO1).isNotEqualTo(cuaHangDTO2);
        cuaHangDTO2.setId(cuaHangDTO1.getId());
        assertThat(cuaHangDTO1).isEqualTo(cuaHangDTO2);
        cuaHangDTO2.setId(2L);
        assertThat(cuaHangDTO1).isNotEqualTo(cuaHangDTO2);
        cuaHangDTO1.setId(null);
        assertThat(cuaHangDTO1).isNotEqualTo(cuaHangDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cuaHangMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cuaHangMapper.fromId(null)).isNull();
    }
}
