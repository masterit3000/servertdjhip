package com.tindung.jhip.web.rest;

import com.tindung.jhip.ServertdjhipApp;

import com.tindung.jhip.domain.VayLai;
import com.tindung.jhip.repository.VayLaiRepository;
import com.tindung.jhip.service.VayLaiService;
import com.tindung.jhip.service.dto.VayLaiDTO;
import com.tindung.jhip.service.mapper.VayLaiMapper;
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

import com.tindung.jhip.domain.enumeration.HINHTHUCLAI;
import com.tindung.jhip.domain.enumeration.TINHLAI;
/**
 * Test class for the VayLaiResource REST controller.
 *
 * @see VayLaiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServertdjhipApp.class)
public class VayLaiResourceIntTest {

    private static final Double DEFAULT_TIENVAY = 1D;
    private static final Double UPDATED_TIENVAY = 2D;

    private static final HINHTHUCLAI DEFAULT_HINHTHUCLAI = HINHTHUCLAI.NGAY;
    private static final HINHTHUCLAI UPDATED_HINHTHUCLAI = HINHTHUCLAI.THANG;

    private static final Integer DEFAULT_THOIGIANVAY = 1;
    private static final Integer UPDATED_THOIGIANVAY = 2;

    private static final Integer DEFAULT_CHUKYLAI = 1;
    private static final Integer UPDATED_CHUKYLAI = 2;

    private static final Float DEFAULT_LAI = 1F;
    private static final Float UPDATED_LAI = 2F;

    private static final TINHLAI DEFAULT_CACHTINHLAI = TINHLAI.MOTTRIEU;
    private static final TINHLAI UPDATED_CACHTINHLAI = TINHLAI.CHUKY;

    private static final Boolean DEFAULT_THULAITRUOC = false;
    private static final Boolean UPDATED_THULAITRUOC = true;

    @Autowired
    private VayLaiRepository vayLaiRepository;

    @Autowired
    private VayLaiMapper vayLaiMapper;

    @Autowired
    private VayLaiService vayLaiService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVayLaiMockMvc;

    private VayLai vayLai;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VayLaiResource vayLaiResource = new VayLaiResource(vayLaiService);
        this.restVayLaiMockMvc = MockMvcBuilders.standaloneSetup(vayLaiResource)
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
    public static VayLai createEntity(EntityManager em) {
        VayLai vayLai = new VayLai()
            .tienvay(DEFAULT_TIENVAY)
            .hinhthuclai(DEFAULT_HINHTHUCLAI)
            .thoigianvay(DEFAULT_THOIGIANVAY)
            .chukylai(DEFAULT_CHUKYLAI)
            .lai(DEFAULT_LAI)
            .cachtinhlai(DEFAULT_CACHTINHLAI)
            .thulaitruoc(DEFAULT_THULAITRUOC);
        return vayLai;
    }

    @Before
    public void initTest() {
        vayLai = createEntity(em);
    }

    @Test
    @Transactional
    public void createVayLai() throws Exception {
        int databaseSizeBeforeCreate = vayLaiRepository.findAll().size();

        // Create the VayLai
        VayLaiDTO vayLaiDTO = vayLaiMapper.toDto(vayLai);
        restVayLaiMockMvc.perform(post("/api/vay-lais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vayLaiDTO)))
            .andExpect(status().isCreated());

        // Validate the VayLai in the database
        List<VayLai> vayLaiList = vayLaiRepository.findAll();
        assertThat(vayLaiList).hasSize(databaseSizeBeforeCreate + 1);
        VayLai testVayLai = vayLaiList.get(vayLaiList.size() - 1);
        assertThat(testVayLai.getTienvay()).isEqualTo(DEFAULT_TIENVAY);
        assertThat(testVayLai.getHinhthuclai()).isEqualTo(DEFAULT_HINHTHUCLAI);
        assertThat(testVayLai.getThoigianvay()).isEqualTo(DEFAULT_THOIGIANVAY);
        assertThat(testVayLai.getChukylai()).isEqualTo(DEFAULT_CHUKYLAI);
        assertThat(testVayLai.getLai()).isEqualTo(DEFAULT_LAI);
        assertThat(testVayLai.getCachtinhlai()).isEqualTo(DEFAULT_CACHTINHLAI);
        assertThat(testVayLai.isThulaitruoc()).isEqualTo(DEFAULT_THULAITRUOC);
    }

    @Test
    @Transactional
    public void createVayLaiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vayLaiRepository.findAll().size();

        // Create the VayLai with an existing ID
        vayLai.setId(1L);
        VayLaiDTO vayLaiDTO = vayLaiMapper.toDto(vayLai);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVayLaiMockMvc.perform(post("/api/vay-lais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vayLaiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VayLai in the database
        List<VayLai> vayLaiList = vayLaiRepository.findAll();
        assertThat(vayLaiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTienvayIsRequired() throws Exception {
        int databaseSizeBeforeTest = vayLaiRepository.findAll().size();
        // set the field null
        vayLai.setTienvay(null);

        // Create the VayLai, which fails.
        VayLaiDTO vayLaiDTO = vayLaiMapper.toDto(vayLai);

        restVayLaiMockMvc.perform(post("/api/vay-lais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vayLaiDTO)))
            .andExpect(status().isBadRequest());

        List<VayLai> vayLaiList = vayLaiRepository.findAll();
        assertThat(vayLaiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHinhthuclaiIsRequired() throws Exception {
        int databaseSizeBeforeTest = vayLaiRepository.findAll().size();
        // set the field null
        vayLai.setHinhthuclai(null);

        // Create the VayLai, which fails.
        VayLaiDTO vayLaiDTO = vayLaiMapper.toDto(vayLai);

        restVayLaiMockMvc.perform(post("/api/vay-lais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vayLaiDTO)))
            .andExpect(status().isBadRequest());

        List<VayLai> vayLaiList = vayLaiRepository.findAll();
        assertThat(vayLaiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkThoigianvayIsRequired() throws Exception {
        int databaseSizeBeforeTest = vayLaiRepository.findAll().size();
        // set the field null
        vayLai.setThoigianvay(null);

        // Create the VayLai, which fails.
        VayLaiDTO vayLaiDTO = vayLaiMapper.toDto(vayLai);

        restVayLaiMockMvc.perform(post("/api/vay-lais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vayLaiDTO)))
            .andExpect(status().isBadRequest());

        List<VayLai> vayLaiList = vayLaiRepository.findAll();
        assertThat(vayLaiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkChukylaiIsRequired() throws Exception {
        int databaseSizeBeforeTest = vayLaiRepository.findAll().size();
        // set the field null
        vayLai.setChukylai(null);

        // Create the VayLai, which fails.
        VayLaiDTO vayLaiDTO = vayLaiMapper.toDto(vayLai);

        restVayLaiMockMvc.perform(post("/api/vay-lais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vayLaiDTO)))
            .andExpect(status().isBadRequest());

        List<VayLai> vayLaiList = vayLaiRepository.findAll();
        assertThat(vayLaiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLaiIsRequired() throws Exception {
        int databaseSizeBeforeTest = vayLaiRepository.findAll().size();
        // set the field null
        vayLai.setLai(null);

        // Create the VayLai, which fails.
        VayLaiDTO vayLaiDTO = vayLaiMapper.toDto(vayLai);

        restVayLaiMockMvc.perform(post("/api/vay-lais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vayLaiDTO)))
            .andExpect(status().isBadRequest());

        List<VayLai> vayLaiList = vayLaiRepository.findAll();
        assertThat(vayLaiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCachtinhlaiIsRequired() throws Exception {
        int databaseSizeBeforeTest = vayLaiRepository.findAll().size();
        // set the field null
        vayLai.setCachtinhlai(null);

        // Create the VayLai, which fails.
        VayLaiDTO vayLaiDTO = vayLaiMapper.toDto(vayLai);

        restVayLaiMockMvc.perform(post("/api/vay-lais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vayLaiDTO)))
            .andExpect(status().isBadRequest());

        List<VayLai> vayLaiList = vayLaiRepository.findAll();
        assertThat(vayLaiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVayLais() throws Exception {
        // Initialize the database
        vayLaiRepository.saveAndFlush(vayLai);

        // Get all the vayLaiList
        restVayLaiMockMvc.perform(get("/api/vay-lais?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vayLai.getId().intValue())))
            .andExpect(jsonPath("$.[*].tienvay").value(hasItem(DEFAULT_TIENVAY.doubleValue())))
            .andExpect(jsonPath("$.[*].hinhthuclai").value(hasItem(DEFAULT_HINHTHUCLAI.toString())))
            .andExpect(jsonPath("$.[*].thoigianvay").value(hasItem(DEFAULT_THOIGIANVAY)))
            .andExpect(jsonPath("$.[*].chukylai").value(hasItem(DEFAULT_CHUKYLAI)))
            .andExpect(jsonPath("$.[*].lai").value(hasItem(DEFAULT_LAI.doubleValue())))
            .andExpect(jsonPath("$.[*].cachtinhlai").value(hasItem(DEFAULT_CACHTINHLAI.toString())))
            .andExpect(jsonPath("$.[*].thulaitruoc").value(hasItem(DEFAULT_THULAITRUOC.booleanValue())));
    }

    @Test
    @Transactional
    public void getVayLai() throws Exception {
        // Initialize the database
        vayLaiRepository.saveAndFlush(vayLai);

        // Get the vayLai
        restVayLaiMockMvc.perform(get("/api/vay-lais/{id}", vayLai.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vayLai.getId().intValue()))
            .andExpect(jsonPath("$.tienvay").value(DEFAULT_TIENVAY.doubleValue()))
            .andExpect(jsonPath("$.hinhthuclai").value(DEFAULT_HINHTHUCLAI.toString()))
            .andExpect(jsonPath("$.thoigianvay").value(DEFAULT_THOIGIANVAY))
            .andExpect(jsonPath("$.chukylai").value(DEFAULT_CHUKYLAI))
            .andExpect(jsonPath("$.lai").value(DEFAULT_LAI.doubleValue()))
            .andExpect(jsonPath("$.cachtinhlai").value(DEFAULT_CACHTINHLAI.toString()))
            .andExpect(jsonPath("$.thulaitruoc").value(DEFAULT_THULAITRUOC.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVayLai() throws Exception {
        // Get the vayLai
        restVayLaiMockMvc.perform(get("/api/vay-lais/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVayLai() throws Exception {
        // Initialize the database
        vayLaiRepository.saveAndFlush(vayLai);
        int databaseSizeBeforeUpdate = vayLaiRepository.findAll().size();

        // Update the vayLai
        VayLai updatedVayLai = vayLaiRepository.findOne(vayLai.getId());
        // Disconnect from session so that the updates on updatedVayLai are not directly saved in db
        em.detach(updatedVayLai);
        updatedVayLai
            .tienvay(UPDATED_TIENVAY)
            .hinhthuclai(UPDATED_HINHTHUCLAI)
            .thoigianvay(UPDATED_THOIGIANVAY)
            .chukylai(UPDATED_CHUKYLAI)
            .lai(UPDATED_LAI)
            .cachtinhlai(UPDATED_CACHTINHLAI)
            .thulaitruoc(UPDATED_THULAITRUOC);
        VayLaiDTO vayLaiDTO = vayLaiMapper.toDto(updatedVayLai);

        restVayLaiMockMvc.perform(put("/api/vay-lais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vayLaiDTO)))
            .andExpect(status().isOk());

        // Validate the VayLai in the database
        List<VayLai> vayLaiList = vayLaiRepository.findAll();
        assertThat(vayLaiList).hasSize(databaseSizeBeforeUpdate);
        VayLai testVayLai = vayLaiList.get(vayLaiList.size() - 1);
        assertThat(testVayLai.getTienvay()).isEqualTo(UPDATED_TIENVAY);
        assertThat(testVayLai.getHinhthuclai()).isEqualTo(UPDATED_HINHTHUCLAI);
        assertThat(testVayLai.getThoigianvay()).isEqualTo(UPDATED_THOIGIANVAY);
        assertThat(testVayLai.getChukylai()).isEqualTo(UPDATED_CHUKYLAI);
        assertThat(testVayLai.getLai()).isEqualTo(UPDATED_LAI);
        assertThat(testVayLai.getCachtinhlai()).isEqualTo(UPDATED_CACHTINHLAI);
        assertThat(testVayLai.isThulaitruoc()).isEqualTo(UPDATED_THULAITRUOC);
    }

    @Test
    @Transactional
    public void updateNonExistingVayLai() throws Exception {
        int databaseSizeBeforeUpdate = vayLaiRepository.findAll().size();

        // Create the VayLai
        VayLaiDTO vayLaiDTO = vayLaiMapper.toDto(vayLai);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVayLaiMockMvc.perform(put("/api/vay-lais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vayLaiDTO)))
            .andExpect(status().isCreated());

        // Validate the VayLai in the database
        List<VayLai> vayLaiList = vayLaiRepository.findAll();
        assertThat(vayLaiList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVayLai() throws Exception {
        // Initialize the database
        vayLaiRepository.saveAndFlush(vayLai);
        int databaseSizeBeforeDelete = vayLaiRepository.findAll().size();

        // Get the vayLai
        restVayLaiMockMvc.perform(delete("/api/vay-lais/{id}", vayLai.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VayLai> vayLaiList = vayLaiRepository.findAll();
        assertThat(vayLaiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VayLai.class);
        VayLai vayLai1 = new VayLai();
        vayLai1.setId(1L);
        VayLai vayLai2 = new VayLai();
        vayLai2.setId(vayLai1.getId());
        assertThat(vayLai1).isEqualTo(vayLai2);
        vayLai2.setId(2L);
        assertThat(vayLai1).isNotEqualTo(vayLai2);
        vayLai1.setId(null);
        assertThat(vayLai1).isNotEqualTo(vayLai2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VayLaiDTO.class);
        VayLaiDTO vayLaiDTO1 = new VayLaiDTO();
        vayLaiDTO1.setId(1L);
        VayLaiDTO vayLaiDTO2 = new VayLaiDTO();
        assertThat(vayLaiDTO1).isNotEqualTo(vayLaiDTO2);
        vayLaiDTO2.setId(vayLaiDTO1.getId());
        assertThat(vayLaiDTO1).isEqualTo(vayLaiDTO2);
        vayLaiDTO2.setId(2L);
        assertThat(vayLaiDTO1).isNotEqualTo(vayLaiDTO2);
        vayLaiDTO1.setId(null);
        assertThat(vayLaiDTO1).isNotEqualTo(vayLaiDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(vayLaiMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(vayLaiMapper.fromId(null)).isNull();
    }
}
