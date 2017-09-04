package mono.jhip.demomysql.web.rest;

import mono.jhip.demomysql.DemomysqlApp;

import mono.jhip.demomysql.domain.Dmsinhvien;
import mono.jhip.demomysql.repository.DmsinhvienRepository;
import mono.jhip.demomysql.web.rest.errors.ExceptionTranslator;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DmsinhvienResource REST controller.
 *
 * @see DmsinhvienResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemomysqlApp.class)
public class DmsinhvienResourceIntTest {

    private static final String DEFAULT_MA = "AAAAAAAAAA";
    private static final String UPDATED_MA = "BBBBBBBBBB";

    private static final String DEFAULT_TEN = "AAAAAAAAAA";
    private static final String UPDATED_TEN = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_NGAYSINH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NGAYSINH = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DmsinhvienRepository dmsinhvienRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDmsinhvienMockMvc;

    private Dmsinhvien dmsinhvien;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DmsinhvienResource dmsinhvienResource = new DmsinhvienResource(dmsinhvienRepository);
        this.restDmsinhvienMockMvc = MockMvcBuilders.standaloneSetup(dmsinhvienResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dmsinhvien createEntity(EntityManager em) {
        Dmsinhvien dmsinhvien = new Dmsinhvien()
            .ma(DEFAULT_MA)
            .ten(DEFAULT_TEN)
            .ngaysinh(DEFAULT_NGAYSINH);
        return dmsinhvien;
    }

    @Before
    public void initTest() {
        dmsinhvien = createEntity(em);
    }

    @Test
    @Transactional
    public void createDmsinhvien() throws Exception {
        int databaseSizeBeforeCreate = dmsinhvienRepository.findAll().size();

        // Create the Dmsinhvien
        restDmsinhvienMockMvc.perform(post("/api/dmsinhviens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dmsinhvien)))
            .andExpect(status().isCreated());

        // Validate the Dmsinhvien in the database
        List<Dmsinhvien> dmsinhvienList = dmsinhvienRepository.findAll();
        assertThat(dmsinhvienList).hasSize(databaseSizeBeforeCreate + 1);
        Dmsinhvien testDmsinhvien = dmsinhvienList.get(dmsinhvienList.size() - 1);
        assertThat(testDmsinhvien.getMa()).isEqualTo(DEFAULT_MA);
        assertThat(testDmsinhvien.getTen()).isEqualTo(DEFAULT_TEN);
        assertThat(testDmsinhvien.getNgaysinh()).isEqualTo(DEFAULT_NGAYSINH);
    }

    @Test
    @Transactional
    public void createDmsinhvienWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dmsinhvienRepository.findAll().size();

        // Create the Dmsinhvien with an existing ID
        dmsinhvien.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDmsinhvienMockMvc.perform(post("/api/dmsinhviens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dmsinhvien)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Dmsinhvien> dmsinhvienList = dmsinhvienRepository.findAll();
        assertThat(dmsinhvienList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDmsinhviens() throws Exception {
        // Initialize the database
        dmsinhvienRepository.saveAndFlush(dmsinhvien);

        // Get all the dmsinhvienList
        restDmsinhvienMockMvc.perform(get("/api/dmsinhviens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dmsinhvien.getId().intValue())))
            .andExpect(jsonPath("$.[*].ma").value(hasItem(DEFAULT_MA.toString())))
            .andExpect(jsonPath("$.[*].ten").value(hasItem(DEFAULT_TEN.toString())))
            .andExpect(jsonPath("$.[*].ngaysinh").value(hasItem(DEFAULT_NGAYSINH.toString())));
    }

    @Test
    @Transactional
    public void getDmsinhvien() throws Exception {
        // Initialize the database
        dmsinhvienRepository.saveAndFlush(dmsinhvien);

        // Get the dmsinhvien
        restDmsinhvienMockMvc.perform(get("/api/dmsinhviens/{id}", dmsinhvien.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dmsinhvien.getId().intValue()))
            .andExpect(jsonPath("$.ma").value(DEFAULT_MA.toString()))
            .andExpect(jsonPath("$.ten").value(DEFAULT_TEN.toString()))
            .andExpect(jsonPath("$.ngaysinh").value(DEFAULT_NGAYSINH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDmsinhvien() throws Exception {
        // Get the dmsinhvien
        restDmsinhvienMockMvc.perform(get("/api/dmsinhviens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDmsinhvien() throws Exception {
        // Initialize the database
        dmsinhvienRepository.saveAndFlush(dmsinhvien);
        int databaseSizeBeforeUpdate = dmsinhvienRepository.findAll().size();

        // Update the dmsinhvien
        Dmsinhvien updatedDmsinhvien = dmsinhvienRepository.findOne(dmsinhvien.getId());
        updatedDmsinhvien
            .ma(UPDATED_MA)
            .ten(UPDATED_TEN)
            .ngaysinh(UPDATED_NGAYSINH);

        restDmsinhvienMockMvc.perform(put("/api/dmsinhviens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDmsinhvien)))
            .andExpect(status().isOk());

        // Validate the Dmsinhvien in the database
        List<Dmsinhvien> dmsinhvienList = dmsinhvienRepository.findAll();
        assertThat(dmsinhvienList).hasSize(databaseSizeBeforeUpdate);
        Dmsinhvien testDmsinhvien = dmsinhvienList.get(dmsinhvienList.size() - 1);
        assertThat(testDmsinhvien.getMa()).isEqualTo(UPDATED_MA);
        assertThat(testDmsinhvien.getTen()).isEqualTo(UPDATED_TEN);
        assertThat(testDmsinhvien.getNgaysinh()).isEqualTo(UPDATED_NGAYSINH);
    }

    @Test
    @Transactional
    public void updateNonExistingDmsinhvien() throws Exception {
        int databaseSizeBeforeUpdate = dmsinhvienRepository.findAll().size();

        // Create the Dmsinhvien

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDmsinhvienMockMvc.perform(put("/api/dmsinhviens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dmsinhvien)))
            .andExpect(status().isCreated());

        // Validate the Dmsinhvien in the database
        List<Dmsinhvien> dmsinhvienList = dmsinhvienRepository.findAll();
        assertThat(dmsinhvienList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDmsinhvien() throws Exception {
        // Initialize the database
        dmsinhvienRepository.saveAndFlush(dmsinhvien);
        int databaseSizeBeforeDelete = dmsinhvienRepository.findAll().size();

        // Get the dmsinhvien
        restDmsinhvienMockMvc.perform(delete("/api/dmsinhviens/{id}", dmsinhvien.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dmsinhvien> dmsinhvienList = dmsinhvienRepository.findAll();
        assertThat(dmsinhvienList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dmsinhvien.class);
        Dmsinhvien dmsinhvien1 = new Dmsinhvien();
        dmsinhvien1.setId(1L);
        Dmsinhvien dmsinhvien2 = new Dmsinhvien();
        dmsinhvien2.setId(dmsinhvien1.getId());
        assertThat(dmsinhvien1).isEqualTo(dmsinhvien2);
        dmsinhvien2.setId(2L);
        assertThat(dmsinhvien1).isNotEqualTo(dmsinhvien2);
        dmsinhvien1.setId(null);
        assertThat(dmsinhvien1).isNotEqualTo(dmsinhvien2);
    }
}
