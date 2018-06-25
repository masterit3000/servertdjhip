package com.tindung.jhip.web.rest;

import com.tindung.jhip.ServertdjhipApp;

import com.tindung.jhip.domain.NhanVien;
import com.tindung.jhip.repository.NhanVienRepository;
import com.tindung.jhip.service.NhanVienService;
import com.tindung.jhip.service.dto.NhanVienDTO;
import com.tindung.jhip.service.mapper.NhanVienMapper;
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

import com.tindung.jhip.domain.enumeration.TrangThaiNhanVien;
/**
 * Test class for the NhanVienResource REST controller.
 *
 * @see NhanVienResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServertdjhipApp.class)
public class NhanVienResourceIntTest {

    private static final String DEFAULT_TEN = "AAAAAAAAAA";
    private static final String UPDATED_TEN = "BBBBBBBBBB";

    private static final String DEFAULT_DIACHI = "AAAAAAAAAA";
    private static final String UPDATED_DIACHI = "BBBBBBBBBB";

    private static final String DEFAULT_DIENTHOAI = "AAAAAAAAAA";
    private static final String UPDATED_DIENTHOAI = "BBBBBBBBBB";

    private static final String DEFAULT_CMND = "AAAAAAAAAA";
    private static final String UPDATED_CMND = "BBBBBBBBBB";

    private static final TrangThaiNhanVien DEFAULT_TRANGTHAI = TrangThaiNhanVien.DUNGHOATDONG;
    private static final TrangThaiNhanVien UPDATED_TRANGTHAI = TrangThaiNhanVien.NHANVIEN;

    private static final ZonedDateTime DEFAULT_NGAY_TAO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_NGAY_TAO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_GHI_CHU = "AAAAAAAAAA";
    private static final String UPDATED_GHI_CHU = "BBBBBBBBBB";

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private NhanVienMapper nhanVienMapper;

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

    private MockMvc restNhanVienMockMvc;

    private NhanVien nhanVien;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NhanVienResource nhanVienResource = new NhanVienResource(nhanVienService);
        this.restNhanVienMockMvc = MockMvcBuilders.standaloneSetup(nhanVienResource)
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
    public static NhanVien createEntity(EntityManager em) {
        NhanVien nhanVien = new NhanVien()
            .ten(DEFAULT_TEN)
            .diachi(DEFAULT_DIACHI)
            .dienthoai(DEFAULT_DIENTHOAI)
            .cmnd(DEFAULT_CMND)
            .trangthai(DEFAULT_TRANGTHAI)
            .ngayTao(DEFAULT_NGAY_TAO)
            .ghiChu(DEFAULT_GHI_CHU);
        return nhanVien;
    }

    @Before
    public void initTest() {
        nhanVien = createEntity(em);
    }

    @Test
    @Transactional
    public void createNhanVien() throws Exception {
        int databaseSizeBeforeCreate = nhanVienRepository.findAll().size();

        // Create the NhanVien
        NhanVienDTO nhanVienDTO = nhanVienMapper.toDto(nhanVien);
        restNhanVienMockMvc.perform(post("/api/nhan-viens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhanVienDTO)))
            .andExpect(status().isCreated());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeCreate + 1);
        NhanVien testNhanVien = nhanVienList.get(nhanVienList.size() - 1);
        assertThat(testNhanVien.getTen()).isEqualTo(DEFAULT_TEN);
        assertThat(testNhanVien.getDiachi()).isEqualTo(DEFAULT_DIACHI);
        assertThat(testNhanVien.getDienthoai()).isEqualTo(DEFAULT_DIENTHOAI);
        assertThat(testNhanVien.getCmnd()).isEqualTo(DEFAULT_CMND);
        assertThat(testNhanVien.getTrangthai()).isEqualTo(DEFAULT_TRANGTHAI);
        assertThat(testNhanVien.getNgayTao()).isEqualTo(DEFAULT_NGAY_TAO);
        assertThat(testNhanVien.getGhiChu()).isEqualTo(DEFAULT_GHI_CHU);
    }

    @Test
    @Transactional
    public void createNhanVienWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nhanVienRepository.findAll().size();

        // Create the NhanVien with an existing ID
        nhanVien.setId(1L);
        NhanVienDTO nhanVienDTO = nhanVienMapper.toDto(nhanVien);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNhanVienMockMvc.perform(post("/api/nhan-viens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhanVienDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTenIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhanVienRepository.findAll().size();
        // set the field null
        nhanVien.setTen(null);

        // Create the NhanVien, which fails.
        NhanVienDTO nhanVienDTO = nhanVienMapper.toDto(nhanVien);

        restNhanVienMockMvc.perform(post("/api/nhan-viens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhanVienDTO)))
            .andExpect(status().isBadRequest());

        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNhanViens() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList
        restNhanVienMockMvc.perform(get("/api/nhan-viens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhanVien.getId().intValue())))
            .andExpect(jsonPath("$.[*].ten").value(hasItem(DEFAULT_TEN.toString())))
            .andExpect(jsonPath("$.[*].diachi").value(hasItem(DEFAULT_DIACHI.toString())))
            .andExpect(jsonPath("$.[*].dienthoai").value(hasItem(DEFAULT_DIENTHOAI.toString())))
            .andExpect(jsonPath("$.[*].cmnd").value(hasItem(DEFAULT_CMND.toString())))
            .andExpect(jsonPath("$.[*].trangthai").value(hasItem(DEFAULT_TRANGTHAI.toString())))
            .andExpect(jsonPath("$.[*].ngayTao").value(hasItem(sameInstant(DEFAULT_NGAY_TAO))))
            .andExpect(jsonPath("$.[*].ghiChu").value(hasItem(DEFAULT_GHI_CHU.toString())));
    }

    @Test
    @Transactional
    public void getNhanVien() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get the nhanVien
        restNhanVienMockMvc.perform(get("/api/nhan-viens/{id}", nhanVien.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nhanVien.getId().intValue()))
            .andExpect(jsonPath("$.ten").value(DEFAULT_TEN.toString()))
            .andExpect(jsonPath("$.diachi").value(DEFAULT_DIACHI.toString()))
            .andExpect(jsonPath("$.dienthoai").value(DEFAULT_DIENTHOAI.toString()))
            .andExpect(jsonPath("$.cmnd").value(DEFAULT_CMND.toString()))
            .andExpect(jsonPath("$.trangthai").value(DEFAULT_TRANGTHAI.toString()))
            .andExpect(jsonPath("$.ngayTao").value(sameInstant(DEFAULT_NGAY_TAO)))
            .andExpect(jsonPath("$.ghiChu").value(DEFAULT_GHI_CHU.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNhanVien() throws Exception {
        // Get the nhanVien
        restNhanVienMockMvc.perform(get("/api/nhan-viens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNhanVien() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);
        int databaseSizeBeforeUpdate = nhanVienRepository.findAll().size();

        // Update the nhanVien
        NhanVien updatedNhanVien = nhanVienRepository.findOne(nhanVien.getId());
        // Disconnect from session so that the updates on updatedNhanVien are not directly saved in db
        em.detach(updatedNhanVien);
        updatedNhanVien
            .ten(UPDATED_TEN)
            .diachi(UPDATED_DIACHI)
            .dienthoai(UPDATED_DIENTHOAI)
            .cmnd(UPDATED_CMND)
            .trangthai(UPDATED_TRANGTHAI)
            .ngayTao(UPDATED_NGAY_TAO)
            .ghiChu(UPDATED_GHI_CHU);
        NhanVienDTO nhanVienDTO = nhanVienMapper.toDto(updatedNhanVien);

        restNhanVienMockMvc.perform(put("/api/nhan-viens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhanVienDTO)))
            .andExpect(status().isOk());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeUpdate);
        NhanVien testNhanVien = nhanVienList.get(nhanVienList.size() - 1);
        assertThat(testNhanVien.getTen()).isEqualTo(UPDATED_TEN);
        assertThat(testNhanVien.getDiachi()).isEqualTo(UPDATED_DIACHI);
        assertThat(testNhanVien.getDienthoai()).isEqualTo(UPDATED_DIENTHOAI);
        assertThat(testNhanVien.getCmnd()).isEqualTo(UPDATED_CMND);
        assertThat(testNhanVien.getTrangthai()).isEqualTo(UPDATED_TRANGTHAI);
        assertThat(testNhanVien.getNgayTao()).isEqualTo(UPDATED_NGAY_TAO);
        assertThat(testNhanVien.getGhiChu()).isEqualTo(UPDATED_GHI_CHU);
    }

    @Test
    @Transactional
    public void updateNonExistingNhanVien() throws Exception {
        int databaseSizeBeforeUpdate = nhanVienRepository.findAll().size();

        // Create the NhanVien
        NhanVienDTO nhanVienDTO = nhanVienMapper.toDto(nhanVien);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNhanVienMockMvc.perform(put("/api/nhan-viens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhanVienDTO)))
            .andExpect(status().isCreated());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNhanVien() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);
        int databaseSizeBeforeDelete = nhanVienRepository.findAll().size();

        // Get the nhanVien
        restNhanVienMockMvc.perform(delete("/api/nhan-viens/{id}", nhanVien.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NhanVien.class);
        NhanVien nhanVien1 = new NhanVien();
        nhanVien1.setId(1L);
        NhanVien nhanVien2 = new NhanVien();
        nhanVien2.setId(nhanVien1.getId());
        assertThat(nhanVien1).isEqualTo(nhanVien2);
        nhanVien2.setId(2L);
        assertThat(nhanVien1).isNotEqualTo(nhanVien2);
        nhanVien1.setId(null);
        assertThat(nhanVien1).isNotEqualTo(nhanVien2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NhanVienDTO.class);
        NhanVienDTO nhanVienDTO1 = new NhanVienDTO();
        nhanVienDTO1.setId(1L);
        NhanVienDTO nhanVienDTO2 = new NhanVienDTO();
        assertThat(nhanVienDTO1).isNotEqualTo(nhanVienDTO2);
        nhanVienDTO2.setId(nhanVienDTO1.getId());
        assertThat(nhanVienDTO1).isEqualTo(nhanVienDTO2);
        nhanVienDTO2.setId(2L);
        assertThat(nhanVienDTO1).isNotEqualTo(nhanVienDTO2);
        nhanVienDTO1.setId(null);
        assertThat(nhanVienDTO1).isNotEqualTo(nhanVienDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(nhanVienMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(nhanVienMapper.fromId(null)).isNull();
    }
}
