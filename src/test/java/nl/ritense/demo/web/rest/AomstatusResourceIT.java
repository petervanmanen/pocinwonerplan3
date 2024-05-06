package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AomstatusAsserts.*;
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
import nl.ritense.demo.domain.Aomstatus;
import nl.ritense.demo.repository.AomstatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AomstatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AomstatusResourceIT {

    private static final LocalDate DEFAULT_DATUMBEGINSTATUS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINSTATUS = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDESTATUS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDESTATUS = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_STATUSCODE = "AAAAAAAAAA";
    private static final String UPDATED_STATUSCODE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUSVOLGORDE = "AAAAAAAAAA";
    private static final String UPDATED_STATUSVOLGORDE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/aomstatuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AomstatusRepository aomstatusRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAomstatusMockMvc;

    private Aomstatus aomstatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aomstatus createEntity(EntityManager em) {
        Aomstatus aomstatus = new Aomstatus()
            .datumbeginstatus(DEFAULT_DATUMBEGINSTATUS)
            .datumeindestatus(DEFAULT_DATUMEINDESTATUS)
            .status(DEFAULT_STATUS)
            .statuscode(DEFAULT_STATUSCODE)
            .statusvolgorde(DEFAULT_STATUSVOLGORDE);
        return aomstatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aomstatus createUpdatedEntity(EntityManager em) {
        Aomstatus aomstatus = new Aomstatus()
            .datumbeginstatus(UPDATED_DATUMBEGINSTATUS)
            .datumeindestatus(UPDATED_DATUMEINDESTATUS)
            .status(UPDATED_STATUS)
            .statuscode(UPDATED_STATUSCODE)
            .statusvolgorde(UPDATED_STATUSVOLGORDE);
        return aomstatus;
    }

    @BeforeEach
    public void initTest() {
        aomstatus = createEntity(em);
    }

    @Test
    @Transactional
    void createAomstatus() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Aomstatus
        var returnedAomstatus = om.readValue(
            restAomstatusMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aomstatus)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Aomstatus.class
        );

        // Validate the Aomstatus in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAomstatusUpdatableFieldsEquals(returnedAomstatus, getPersistedAomstatus(returnedAomstatus));
    }

    @Test
    @Transactional
    void createAomstatusWithExistingId() throws Exception {
        // Create the Aomstatus with an existing ID
        aomstatus.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAomstatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aomstatus)))
            .andExpect(status().isBadRequest());

        // Validate the Aomstatus in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAomstatuses() throws Exception {
        // Initialize the database
        aomstatusRepository.saveAndFlush(aomstatus);

        // Get all the aomstatusList
        restAomstatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aomstatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumbeginstatus").value(hasItem(DEFAULT_DATUMBEGINSTATUS.toString())))
            .andExpect(jsonPath("$.[*].datumeindestatus").value(hasItem(DEFAULT_DATUMEINDESTATUS.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].statuscode").value(hasItem(DEFAULT_STATUSCODE)))
            .andExpect(jsonPath("$.[*].statusvolgorde").value(hasItem(DEFAULT_STATUSVOLGORDE)));
    }

    @Test
    @Transactional
    void getAomstatus() throws Exception {
        // Initialize the database
        aomstatusRepository.saveAndFlush(aomstatus);

        // Get the aomstatus
        restAomstatusMockMvc
            .perform(get(ENTITY_API_URL_ID, aomstatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aomstatus.getId().intValue()))
            .andExpect(jsonPath("$.datumbeginstatus").value(DEFAULT_DATUMBEGINSTATUS.toString()))
            .andExpect(jsonPath("$.datumeindestatus").value(DEFAULT_DATUMEINDESTATUS.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.statuscode").value(DEFAULT_STATUSCODE))
            .andExpect(jsonPath("$.statusvolgorde").value(DEFAULT_STATUSVOLGORDE));
    }

    @Test
    @Transactional
    void getNonExistingAomstatus() throws Exception {
        // Get the aomstatus
        restAomstatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAomstatus() throws Exception {
        // Initialize the database
        aomstatusRepository.saveAndFlush(aomstatus);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aomstatus
        Aomstatus updatedAomstatus = aomstatusRepository.findById(aomstatus.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAomstatus are not directly saved in db
        em.detach(updatedAomstatus);
        updatedAomstatus
            .datumbeginstatus(UPDATED_DATUMBEGINSTATUS)
            .datumeindestatus(UPDATED_DATUMEINDESTATUS)
            .status(UPDATED_STATUS)
            .statuscode(UPDATED_STATUSCODE)
            .statusvolgorde(UPDATED_STATUSVOLGORDE);

        restAomstatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAomstatus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAomstatus))
            )
            .andExpect(status().isOk());

        // Validate the Aomstatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAomstatusToMatchAllProperties(updatedAomstatus);
    }

    @Test
    @Transactional
    void putNonExistingAomstatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aomstatus.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAomstatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aomstatus.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aomstatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aomstatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAomstatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aomstatus.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAomstatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aomstatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aomstatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAomstatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aomstatus.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAomstatusMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aomstatus)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aomstatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAomstatusWithPatch() throws Exception {
        // Initialize the database
        aomstatusRepository.saveAndFlush(aomstatus);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aomstatus using partial update
        Aomstatus partialUpdatedAomstatus = new Aomstatus();
        partialUpdatedAomstatus.setId(aomstatus.getId());

        partialUpdatedAomstatus
            .datumbeginstatus(UPDATED_DATUMBEGINSTATUS)
            .datumeindestatus(UPDATED_DATUMEINDESTATUS)
            .statuscode(UPDATED_STATUSCODE);

        restAomstatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAomstatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAomstatus))
            )
            .andExpect(status().isOk());

        // Validate the Aomstatus in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAomstatusUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAomstatus, aomstatus),
            getPersistedAomstatus(aomstatus)
        );
    }

    @Test
    @Transactional
    void fullUpdateAomstatusWithPatch() throws Exception {
        // Initialize the database
        aomstatusRepository.saveAndFlush(aomstatus);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aomstatus using partial update
        Aomstatus partialUpdatedAomstatus = new Aomstatus();
        partialUpdatedAomstatus.setId(aomstatus.getId());

        partialUpdatedAomstatus
            .datumbeginstatus(UPDATED_DATUMBEGINSTATUS)
            .datumeindestatus(UPDATED_DATUMEINDESTATUS)
            .status(UPDATED_STATUS)
            .statuscode(UPDATED_STATUSCODE)
            .statusvolgorde(UPDATED_STATUSVOLGORDE);

        restAomstatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAomstatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAomstatus))
            )
            .andExpect(status().isOk());

        // Validate the Aomstatus in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAomstatusUpdatableFieldsEquals(partialUpdatedAomstatus, getPersistedAomstatus(partialUpdatedAomstatus));
    }

    @Test
    @Transactional
    void patchNonExistingAomstatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aomstatus.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAomstatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aomstatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aomstatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aomstatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAomstatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aomstatus.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAomstatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aomstatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aomstatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAomstatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aomstatus.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAomstatusMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(aomstatus)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aomstatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAomstatus() throws Exception {
        // Initialize the database
        aomstatusRepository.saveAndFlush(aomstatus);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the aomstatus
        restAomstatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, aomstatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return aomstatusRepository.count();
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

    protected Aomstatus getPersistedAomstatus(Aomstatus aomstatus) {
        return aomstatusRepository.findById(aomstatus.getId()).orElseThrow();
    }

    protected void assertPersistedAomstatusToMatchAllProperties(Aomstatus expectedAomstatus) {
        assertAomstatusAllPropertiesEquals(expectedAomstatus, getPersistedAomstatus(expectedAomstatus));
    }

    protected void assertPersistedAomstatusToMatchUpdatableProperties(Aomstatus expectedAomstatus) {
        assertAomstatusAllUpdatablePropertiesEquals(expectedAomstatus, getPersistedAomstatus(expectedAomstatus));
    }
}
