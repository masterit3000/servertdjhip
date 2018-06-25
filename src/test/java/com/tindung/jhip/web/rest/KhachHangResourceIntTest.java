package com.tindung.jhip.web.rest;

import com.tindung.jhip.ServertdjhipApp;

import com.tindung.jhip.domain.KhachHang;
import com.tindung.jhip.repository.KhachHangRepository;
import com.tindung.jhip.service.KhachHangService;
import com.tindung.jhip.service.dto.KhachHangDTO;
import com.tindung.jhip.service.mapper.KhachHangMapper;
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

import com.tindung.jhip.domain.enumeration.TrangThaiKhachHang;
/**
 * Test class for the KhachHangResource REST controller.
 *
 * @see KhachHangResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServertdjhipApp.class)
public class KhachHangResourceIntTest {

    private static final String DEFAULT_TEN = "AAAAAAAAAA";
    private static final String UPDATED_TEN = "BBBBBBBBBB";

    private static final String DEFAULT_DIACHI = "AAAAAAAAAA";
    private static final String UPDATED_DIACHI = "BBBBBBBBBB";

    private static final String DEFAULT_DIENTHOAI = "AAAAAAAAAA";
    private static final String UPDATED_DIENTHOAI = "BBBBBBBBBB";

    private static final String DEFAULT_CMND = "AAAAAAAAAA";
    private static final String UPDATED_CMND = "BBBBBBBBBB";

    private static final TrangThaiKhachHang DEFAULT_TRANGTHAI = TrangThaiKhachHang.HOATDONG;
    private static final TrangThaiKhachHang UPDATED_TRANGTHAI = TrangThaiKhachHang.DUNGHOATDONG;

    private static final ZonedDateTime DEFAULT_NGAY_TAO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_NGAY_TAO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_FACEBOOK = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK = "BBBBBBBBBB";

    private static final String DEFAULT_GHI_CHU = "AAAAAAAAAA";
    private static final String UPDATED_GHI_CHU = "BBBBBBBBBB";

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private KhachHangMapper khachHangMapper;

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKhachHangMockMvc;

    private KhachHang khachHang;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KhachHangResource khachHangResource = new KhachHangResource(khachHangService);
        this.restKhachHangMockMvc = MockMvcBuilders.standaloneSetup(khachHangResource)
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
    public static KhachHang createEntity(EntityManager em) {
        KhachHang khachHang = new KhachHang()
            .ten(DEFAULT_TEN)
            .diachi(DEFAULT_DIACHI)
            .dienthoai(DEFAULT_DIENTHOAI)
            .cmnd(DEFAULT_CMND)
            .trangthai(DEFAULT_TRANGTHAI)
            .ngayTao(DEFAULT_NGAY_TAO)
            .email(DEFAULT_EMAIL)
            .facebook(DEFAULT_FACEBOOK)
            .ghiChu(DEFAULT_GHI_CHU);
        return khachHang;
    }

    @Before
    public void initTest() {
        khachHang = createEntity(em);
    }

    @Test
    @Transactional
    public void createKhachHang() throws Exception {
        int databaseSizeBeforeCreate = khachHangRepository.findAll().size();

        // Create the KhachHang
        KhachHangDTO khachHangDTO = khachHangMapper.toDto(khachHang);
        restKhachHangMockMvc.perform(post("/api/khach-hangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(khachHangDTO)))
            .andExpect(status().isCreated());

        // Validate the KhachHang in the database
        List<KhachHang> khachHangList = khachHangRepository.findAll();
        assertThat(khachHangList).hasSize(databaseSizeBeforeCreate + 1);
        KhachHang testKhachHang = khachHangList.get(khachHangList.size() - 1);
        assertThat(testKhachHang.getTen()).isEqualTo(DEFAULT_TEN);
        assertThat(testKhachHang.getDiachi()).isEqualTo(DEFAULT_DIACHI);
        assertThat(testKhachHang.getDienthoai()).isEqualTo(DEFAULT_DIENTHOAI);
        assertThat(testKhachHang.getCmnd()).isEqualTo(DEFAULT_CMND);
        assertThat(testKhachHang.getTrangthai()).isEqualTo(DEFAULT_TRANGTHAI);
        assertThat(testKhachHang.getNgayTao()).isEqualTo(DEFAULT_NGAY_TAO);
        assertThat(testKhachHang.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testKhachHang.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testKhachHang.getGhiChu()).isEqualTo(DEFAULT_GHI_CHU);
    }

    @Test
    @Transactional
    public void createKhachHangWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = khachHangRepository.findAll().size();

        // Create the KhachHang with an existing ID
        khachHang.setId(1L);
        KhachHangDTO khachHangDTO = khachHangMapper.toDto(khachHang);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKhachHangMockMvc.perform(post("/api/khach-hangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(khachHangDTO)))
            .andExpect(status().isBadRequest());

        // Validate the KhachHang in the database
        List<KhachHang> khachHangList = khachHangRepository.findAll();
        assertThat(khachHangList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTenIsRequired() throws Exception {
        int databaseSizeBeforeTest = khachHangRepository.findAll().size();
        // set the field null
        khachHang.setTen(null);

        // Create the KhachHang, which fails.
        KhachHangDTO khachHangDTO = khachHangMapper.toDto(khachHang);

        restKhachHangMockMvc.perform(post("/api/khach-hangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(khachHangDTO)))
            .andExpect(status().isBadRequest());

        List<KhachHang> khachHangList = khachHangRepository.findAll();
        assertThat(khachHangList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKhachHangs() throws Exception {
        // Initialize the database
        khachHangRepository.saveAndFlush(khachHang);

        // Get all the khachHangList
        restKhachHangMockMvc.perform(get("/api/khach-hangs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(khachHang.getId().intValue())))
            .andExpect(jsonPath("$.[*].ten").value(hasItem(DEFAULT_TEN.toString())))
            .andExpect(jsonPath("$.[*].diachi").value(hasItem(DEFAULT_DIACHI.toString())))
            .andExpect(jsonPath("$.[*].dienthoai").value(hasItem(DEFAULT_DIENTHOAI.toString())))
            .andExpect(jsonPath("$.[*].cmnd").value(hasItem(DEFAULT_CMND.toString())))
            .andExpect(jsonPath("$.[*].trangthai").value(hasItem(DEFAULT_TRANGTHAI.toString())))
            .andExpect(jsonPath("$.[*].ngayTao").value(hasItem(sameInstant(DEFAULT_NGAY_TAO))))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK.toString())))
            .andExpect(jsonPath("$.[*].ghiChu").value(hasItem(DEFAULT_GHI_CHU.toString())));
    }

    @Test
    @Transactional
    public void getKhachHang() throws Exception {
        // Initialize the database
        khachHangRepository.saveAndFlush(khachHang);

        // Get the khachHang
        restKhachHangMockMvc.perform(get("/api/khach-hangs/{id}", khachHang.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(khachHang.getId().intValue()))
            .andExpect(jsonPath("$.ten").value(DEFAULT_TEN.toString()))
            .andExpect(jsonPath("$.diachi").value(DEFAULT_DIACHI.toString()))
            .andExpect(jsonPath("$.dienthoai").value(DEFAULT_DIENTHOAI.toString()))
            .andExpect(jsonPath("$.cmnd").value(DEFAULT_CMND.toString()))
            .andExpect(jsonPath("$.trangthai").value(DEFAULT_TRANGTHAI.toString()))
            .andExpect(jsonPath("$.ngayTao").value(sameInstant(DEFAULT_NGAY_TAO)))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.facebook").value(DEFAULT_FACEBOOK.toString()))
            .andExpect(jsonPath("$.ghiChu").value(DEFAULT_GHI_CHU.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKhachHang() throws Exception {
        // Get the khachHang
        restKhachHangMockMvc.perform(get("/api/khach-hangs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKhachHang() throws Exception {
        // Initialize the database
        khachHangRepository.saveAndFlush(khachHang);
        int databaseSizeBeforeUpdate = khachHangRepository.findAll().size();

        // Update the khachHang
        KhachHang updatedKhachHang = khachHangRepository.findOne(khachHang.getId());
        // Disconnect from session so that the updates on updatedKhachHang are not directly saved in db
        em.detach(updatedKhachHang);
        updatedKhachHang
            .ten(UPDATED_TEN)
            .diachi(UPDATED_DIACHI)
            .dienthoai(UPDATED_DIENTHOAI)
            .cmnd(UPDATED_CMND)
            .trangthai(UPDATED_TRANGTHAI)
            .ngayTao(UPDATED_NGAY_TAO)
            .email(UPDATED_EMAIL)
            .facebook(UPDATED_FACEBOOK)
            .ghiChu(UPDATED_GHI_CHU);
        KhachHangDTO khachHangDTO = khachHangMapper.toDto(updatedKhachHang);

        restKhachHangMockMvc.perform(put("/api/khach-hangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(khachHangDTO)))
            .andExpect(status().isOk());

        // Validate the KhachHang in the database
        List<KhachHang> khachHangList = khachHangRepository.findAll();
        assertThat(khachHangList).hasSize(databaseSizeBeforeUpdate);
        KhachHang testKhachHang = khachHangList.get(khachHangList.size() - 1);
        assertThat(testKhachHang.getTen()).isEqualTo(UPDATED_TEN);
        assertThat(testKhachHang.getDiachi()).isEqualTo(UPDATED_DIACHI);
        assertThat(testKhachHang.getDienthoai()).isEqualTo(UPDATED_DIENTHOAI);
        assertThat(testKhachHang.getCmnd()).isEqualTo(UPDATED_CMND);
        assertThat(testKhachHang.getTrangthai()).isEqualTo(UPDATED_TRANGTHAI);
        assertThat(testKhachHang.getNgayTao()).isEqualTo(UPDATED_NGAY_TAO);
        assertThat(testKhachHang.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testKhachHang.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testKhachHang.getGhiChu()).isEqualTo(UPDATED_GHI_CHU);
    }

    @Test
    @Transactional
    public void updateNonExistingKhachHang() throws Exception {
        int databaseSizeBeforeUpdate = khachHangRepository.findAll().size();

        // Create the KhachHang
        KhachHangDTO khachHangDTO = khachHangMapper.toDto(khachHang);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restKhachHangMockMvc.perform(put("/api/khach-hangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(khachHangDTO)))
            .andExpect(status().isCreated());

        // Validate the KhachHang in the database
        List<KhachHang> khachHangList = khachHangRepository.findAll();
        assertThat(khachHangList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteKhachHang() throws Exception {
        // Initialize the database
        khachHangRepository.saveAndFlush(khachHang);
        int databaseSizeBeforeDelete = khachHangRepository.findAll().size();

        // Get the khachHang
        restKhachHangMockMvc.perform(delete("/api/khach-hangs/{id}", khachHang.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<KhachHang> khachHangList = khachHangRepository.findAll();
        assertThat(khachHangList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KhachHang.class);
        KhachHang khachHang1 = new KhachHang();
        khachHang1.setId(1L);
        KhachHang khachHang2 = new KhachHang();
        khachHang2.setId(khachHang1.getId());
        assertThat(khachHang1).isEqualTo(khachHang2);
        khachHang2.setId(2L);
        assertThat(khachHang1).isNotEqualTo(khachHang2);
        khachHang1.setId(null);
        assertThat(khachHang1).isNotEqualTo(khachHang2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(KhachHangDTO.class);
        KhachHangDTO khachHangDTO1 = new KhachHangDTO();
        khachHangDTO1.setId(1L);
        KhachHangDTO khachHangDTO2 = new KhachHangDTO();
        assertThat(khachHangDTO1).isNotEqualTo(khachHangDTO2);
        khachHangDTO2.setId(khachHangDTO1.getId());
        assertThat(khachHangDTO1).isEqualTo(khachHangDTO2);
        khachHangDTO2.setId(2L);
        assertThat(khachHangDTO1).isNotEqualTo(khachHangDTO2);
        khachHangDTO1.setId(null);
        assertThat(khachHangDTO1).isNotEqualTo(khachHangDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(khachHangMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(khachHangMapper.fromId(null)).isNull();
    }
}
