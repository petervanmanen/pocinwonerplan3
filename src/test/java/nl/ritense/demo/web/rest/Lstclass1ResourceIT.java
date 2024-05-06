package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.Lstclass1Asserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Lstclass1;
import nl.ritense.demo.repository.Lstclass1Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link Lstclass1Resource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class Lstclass1ResourceIT {

    private static final Integer DEFAULT_WAARDE = 1;
    private static final Integer UPDATED_WAARDE = 2;

    private static final Integer DEFAULT_DWHRECORDID = 1;
    private static final Integer UPDATED_DWHRECORDID = 2;

    private static final Integer DEFAULT_DWHODSRECORDID = 1;
    private static final Integer UPDATED_DWHODSRECORDID = 2;

    private static final LocalDate DEFAULT_DWHSTARTDT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DWHSTARTDT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DWHEINDDT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DWHEINDDT = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_DWHRUNID = 1;
    private static final Integer UPDATED_DWHRUNID = 2;

    private static final String DEFAULT_DWHBRON = "AAAAAAAAAA";
    private static final String UPDATED_DWHBRON = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DWHLAADDT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DWHLAADDT = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_DWHACTUEEL = 1;
    private static final Integer UPDATED_DWHACTUEEL = 2;

    private static final Integer DEFAULT_LSTCLASS_1_ID = 1;
    private static final Integer UPDATED_LSTCLASS_1_ID = 2;

    private static final String ENTITY_API_URL = "/api/lstclass-1-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private Lstclass1Repository lstclass1Repository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLstclass1MockMvc;

    private Lstclass1 lstclass1;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lstclass1 createEntity(EntityManager em) {
        Lstclass1 lstclass1 = new Lstclass1()
            .waarde(DEFAULT_WAARDE)
            .dwhrecordid(DEFAULT_DWHRECORDID)
            .dwhodsrecordid(DEFAULT_DWHODSRECORDID)
            .dwhstartdt(DEFAULT_DWHSTARTDT)
            .dwheinddt(DEFAULT_DWHEINDDT)
            .dwhrunid(DEFAULT_DWHRUNID)
            .dwhbron(DEFAULT_DWHBRON)
            .dwhlaaddt(DEFAULT_DWHLAADDT)
            .dwhactueel(DEFAULT_DWHACTUEEL)
            .lstclass1id(DEFAULT_LSTCLASS_1_ID);
        return lstclass1;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lstclass1 createUpdatedEntity(EntityManager em) {
        Lstclass1 lstclass1 = new Lstclass1()
            .waarde(UPDATED_WAARDE)
            .dwhrecordid(UPDATED_DWHRECORDID)
            .dwhodsrecordid(UPDATED_DWHODSRECORDID)
            .dwhstartdt(UPDATED_DWHSTARTDT)
            .dwheinddt(UPDATED_DWHEINDDT)
            .dwhrunid(UPDATED_DWHRUNID)
            .dwhbron(UPDATED_DWHBRON)
            .dwhlaaddt(UPDATED_DWHLAADDT)
            .dwhactueel(UPDATED_DWHACTUEEL)
            .lstclass1id(UPDATED_LSTCLASS_1_ID);
        return lstclass1;
    }

    @BeforeEach
    public void initTest() {
        lstclass1 = createEntity(em);
    }

    @Test
    @Transactional
    void createLstclass1() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Lstclass1
        var returnedLstclass1 = om.readValue(
            restLstclass1MockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lstclass1)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Lstclass1.class
        );

        // Validate the Lstclass1 in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLstclass1UpdatableFieldsEquals(returnedLstclass1, getPersistedLstclass1(returnedLstclass1));
    }

    @Test
    @Transactional
    void createLstclass1WithExistingId() throws Exception {
        // Create the Lstclass1 with an existing ID
        lstclass1.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLstclass1MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lstclass1)))
            .andExpect(status().isBadRequest());

        // Validate the Lstclass1 in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLstclass1s() throws Exception {
        // Initialize the database
        lstclass1Repository.saveAndFlush(lstclass1);

        // Get all the lstclass1List
        restLstclass1MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lstclass1.getId().intValue())))
            .andExpect(jsonPath("$.[*].waarde").value(hasItem(DEFAULT_WAARDE)))
            .andExpect(jsonPath("$.[*].dwhrecordid").value(hasItem(DEFAULT_DWHRECORDID)))
            .andExpect(jsonPath("$.[*].dwhodsrecordid").value(hasItem(DEFAULT_DWHODSRECORDID)))
            .andExpect(jsonPath("$.[*].dwhstartdt").value(hasItem(DEFAULT_DWHSTARTDT.toString())))
            .andExpect(jsonPath("$.[*].dwheinddt").value(hasItem(DEFAULT_DWHEINDDT.toString())))
            .andExpect(jsonPath("$.[*].dwhrunid").value(hasItem(DEFAULT_DWHRUNID)))
            .andExpect(jsonPath("$.[*].dwhbron").value(hasItem(DEFAULT_DWHBRON)))
            .andExpect(jsonPath("$.[*].dwhlaaddt").value(hasItem(DEFAULT_DWHLAADDT.toString())))
            .andExpect(jsonPath("$.[*].dwhactueel").value(hasItem(DEFAULT_DWHACTUEEL)))
            .andExpect(jsonPath("$.[*].lstclass1id").value(hasItem(DEFAULT_LSTCLASS_1_ID)));
    }

    @Test
    @Transactional
    void getLstclass1() throws Exception {
        // Initialize the database
        lstclass1Repository.saveAndFlush(lstclass1);

        // Get the lstclass1
        restLstclass1MockMvc
            .perform(get(ENTITY_API_URL_ID, lstclass1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lstclass1.getId().intValue()))
            .andExpect(jsonPath("$.waarde").value(DEFAULT_WAARDE))
            .andExpect(jsonPath("$.dwhrecordid").value(DEFAULT_DWHRECORDID))
            .andExpect(jsonPath("$.dwhodsrecordid").value(DEFAULT_DWHODSRECORDID))
            .andExpect(jsonPath("$.dwhstartdt").value(DEFAULT_DWHSTARTDT.toString()))
            .andExpect(jsonPath("$.dwheinddt").value(DEFAULT_DWHEINDDT.toString()))
            .andExpect(jsonPath("$.dwhrunid").value(DEFAULT_DWHRUNID))
            .andExpect(jsonPath("$.dwhbron").value(DEFAULT_DWHBRON))
            .andExpect(jsonPath("$.dwhlaaddt").value(DEFAULT_DWHLAADDT.toString()))
            .andExpect(jsonPath("$.dwhactueel").value(DEFAULT_DWHACTUEEL))
            .andExpect(jsonPath("$.lstclass1id").value(DEFAULT_LSTCLASS_1_ID));
    }

    @Test
    @Transactional
    void getNonExistingLstclass1() throws Exception {
        // Get the lstclass1
        restLstclass1MockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLstclass1() throws Exception {
        // Initialize the database
        lstclass1Repository.saveAndFlush(lstclass1);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lstclass1
        Lstclass1 updatedLstclass1 = lstclass1Repository.findById(lstclass1.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLstclass1 are not directly saved in db
        em.detach(updatedLstclass1);
        updatedLstclass1
            .waarde(UPDATED_WAARDE)
            .dwhrecordid(UPDATED_DWHRECORDID)
            .dwhodsrecordid(UPDATED_DWHODSRECORDID)
            .dwhstartdt(UPDATED_DWHSTARTDT)
            .dwheinddt(UPDATED_DWHEINDDT)
            .dwhrunid(UPDATED_DWHRUNID)
            .dwhbron(UPDATED_DWHBRON)
            .dwhlaaddt(UPDATED_DWHLAADDT)
            .dwhactueel(UPDATED_DWHACTUEEL)
            .lstclass1id(UPDATED_LSTCLASS_1_ID);

        restLstclass1MockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLstclass1.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLstclass1))
            )
            .andExpect(status().isOk());

        // Validate the Lstclass1 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLstclass1ToMatchAllProperties(updatedLstclass1);
    }

    @Test
    @Transactional
    void putNonExistingLstclass1() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lstclass1.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLstclass1MockMvc
            .perform(
                put(ENTITY_API_URL_ID, lstclass1.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lstclass1))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lstclass1 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLstclass1() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lstclass1.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLstclass1MockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(lstclass1))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lstclass1 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLstclass1() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lstclass1.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLstclass1MockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lstclass1)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Lstclass1 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLstclass1WithPatch() throws Exception {
        // Initialize the database
        lstclass1Repository.saveAndFlush(lstclass1);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lstclass1 using partial update
        Lstclass1 partialUpdatedLstclass1 = new Lstclass1();
        partialUpdatedLstclass1.setId(lstclass1.getId());

        partialUpdatedLstclass1.dwhstartdt(UPDATED_DWHSTARTDT).dwhlaaddt(UPDATED_DWHLAADDT).dwhactueel(UPDATED_DWHACTUEEL);

        restLstclass1MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLstclass1.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLstclass1))
            )
            .andExpect(status().isOk());

        // Validate the Lstclass1 in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLstclass1UpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedLstclass1, lstclass1),
            getPersistedLstclass1(lstclass1)
        );
    }

    @Test
    @Transactional
    void fullUpdateLstclass1WithPatch() throws Exception {
        // Initialize the database
        lstclass1Repository.saveAndFlush(lstclass1);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lstclass1 using partial update
        Lstclass1 partialUpdatedLstclass1 = new Lstclass1();
        partialUpdatedLstclass1.setId(lstclass1.getId());

        partialUpdatedLstclass1
            .waarde(UPDATED_WAARDE)
            .dwhrecordid(UPDATED_DWHRECORDID)
            .dwhodsrecordid(UPDATED_DWHODSRECORDID)
            .dwhstartdt(UPDATED_DWHSTARTDT)
            .dwheinddt(UPDATED_DWHEINDDT)
            .dwhrunid(UPDATED_DWHRUNID)
            .dwhbron(UPDATED_DWHBRON)
            .dwhlaaddt(UPDATED_DWHLAADDT)
            .dwhactueel(UPDATED_DWHACTUEEL)
            .lstclass1id(UPDATED_LSTCLASS_1_ID);

        restLstclass1MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLstclass1.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLstclass1))
            )
            .andExpect(status().isOk());

        // Validate the Lstclass1 in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLstclass1UpdatableFieldsEquals(partialUpdatedLstclass1, getPersistedLstclass1(partialUpdatedLstclass1));
    }

    @Test
    @Transactional
    void patchNonExistingLstclass1() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lstclass1.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLstclass1MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lstclass1.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(lstclass1))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lstclass1 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLstclass1() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lstclass1.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLstclass1MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(lstclass1))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lstclass1 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLstclass1() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lstclass1.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLstclass1MockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(lstclass1)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Lstclass1 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLstclass1() throws Exception {
        // Initialize the database
        lstclass1Repository.saveAndFlush(lstclass1);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the lstclass1
        restLstclass1MockMvc
            .perform(delete(ENTITY_API_URL_ID, lstclass1.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return lstclass1Repository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Lstclass1 getPersistedLstclass1(Lstclass1 lstclass1) {
        return lstclass1Repository.findById(lstclass1.getId()).orElseThrow();
    }

    protected void assertPersistedLstclass1ToMatchAllProperties(Lstclass1 expectedLstclass1) {
        assertLstclass1AllPropertiesEquals(expectedLstclass1, getPersistedLstclass1(expectedLstclass1));
    }

    protected void assertPersistedLstclass1ToMatchUpdatableProperties(Lstclass1 expectedLstclass1) {
        assertLstclass1AllUpdatablePropertiesEquals(expectedLstclass1, getPersistedLstclass1(expectedLstclass1));
    }
}
