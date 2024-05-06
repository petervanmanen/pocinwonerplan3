package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.WozwaardeAsserts.*;
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
import nl.ritense.demo.domain.Wozwaarde;
import nl.ritense.demo.repository.WozwaardeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WozwaardeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WozwaardeResourceIT {

    private static final LocalDate DEFAULT_DATUMPEILINGTOESTAND = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMPEILINGTOESTAND = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMWAARDEPEILING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMWAARDEPEILING = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_STATUSBESCHIKKING = "AAAAAAAAAA";
    private static final String UPDATED_STATUSBESCHIKKING = "BBBBBBBBBB";

    private static final String DEFAULT_VASTGESTELDEWAARDE = "AAAAAAAAAA";
    private static final String UPDATED_VASTGESTELDEWAARDE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/wozwaardes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WozwaardeRepository wozwaardeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWozwaardeMockMvc;

    private Wozwaarde wozwaarde;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wozwaarde createEntity(EntityManager em) {
        Wozwaarde wozwaarde = new Wozwaarde()
            .datumpeilingtoestand(DEFAULT_DATUMPEILINGTOESTAND)
            .datumwaardepeiling(DEFAULT_DATUMWAARDEPEILING)
            .statusbeschikking(DEFAULT_STATUSBESCHIKKING)
            .vastgesteldewaarde(DEFAULT_VASTGESTELDEWAARDE);
        return wozwaarde;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wozwaarde createUpdatedEntity(EntityManager em) {
        Wozwaarde wozwaarde = new Wozwaarde()
            .datumpeilingtoestand(UPDATED_DATUMPEILINGTOESTAND)
            .datumwaardepeiling(UPDATED_DATUMWAARDEPEILING)
            .statusbeschikking(UPDATED_STATUSBESCHIKKING)
            .vastgesteldewaarde(UPDATED_VASTGESTELDEWAARDE);
        return wozwaarde;
    }

    @BeforeEach
    public void initTest() {
        wozwaarde = createEntity(em);
    }

    @Test
    @Transactional
    void createWozwaarde() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Wozwaarde
        var returnedWozwaarde = om.readValue(
            restWozwaardeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wozwaarde)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Wozwaarde.class
        );

        // Validate the Wozwaarde in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertWozwaardeUpdatableFieldsEquals(returnedWozwaarde, getPersistedWozwaarde(returnedWozwaarde));
    }

    @Test
    @Transactional
    void createWozwaardeWithExistingId() throws Exception {
        // Create the Wozwaarde with an existing ID
        wozwaarde.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWozwaardeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wozwaarde)))
            .andExpect(status().isBadRequest());

        // Validate the Wozwaarde in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWozwaardes() throws Exception {
        // Initialize the database
        wozwaardeRepository.saveAndFlush(wozwaarde);

        // Get all the wozwaardeList
        restWozwaardeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wozwaarde.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumpeilingtoestand").value(hasItem(DEFAULT_DATUMPEILINGTOESTAND.toString())))
            .andExpect(jsonPath("$.[*].datumwaardepeiling").value(hasItem(DEFAULT_DATUMWAARDEPEILING.toString())))
            .andExpect(jsonPath("$.[*].statusbeschikking").value(hasItem(DEFAULT_STATUSBESCHIKKING)))
            .andExpect(jsonPath("$.[*].vastgesteldewaarde").value(hasItem(DEFAULT_VASTGESTELDEWAARDE)));
    }

    @Test
    @Transactional
    void getWozwaarde() throws Exception {
        // Initialize the database
        wozwaardeRepository.saveAndFlush(wozwaarde);

        // Get the wozwaarde
        restWozwaardeMockMvc
            .perform(get(ENTITY_API_URL_ID, wozwaarde.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wozwaarde.getId().intValue()))
            .andExpect(jsonPath("$.datumpeilingtoestand").value(DEFAULT_DATUMPEILINGTOESTAND.toString()))
            .andExpect(jsonPath("$.datumwaardepeiling").value(DEFAULT_DATUMWAARDEPEILING.toString()))
            .andExpect(jsonPath("$.statusbeschikking").value(DEFAULT_STATUSBESCHIKKING))
            .andExpect(jsonPath("$.vastgesteldewaarde").value(DEFAULT_VASTGESTELDEWAARDE));
    }

    @Test
    @Transactional
    void getNonExistingWozwaarde() throws Exception {
        // Get the wozwaarde
        restWozwaardeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWozwaarde() throws Exception {
        // Initialize the database
        wozwaardeRepository.saveAndFlush(wozwaarde);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the wozwaarde
        Wozwaarde updatedWozwaarde = wozwaardeRepository.findById(wozwaarde.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWozwaarde are not directly saved in db
        em.detach(updatedWozwaarde);
        updatedWozwaarde
            .datumpeilingtoestand(UPDATED_DATUMPEILINGTOESTAND)
            .datumwaardepeiling(UPDATED_DATUMWAARDEPEILING)
            .statusbeschikking(UPDATED_STATUSBESCHIKKING)
            .vastgesteldewaarde(UPDATED_VASTGESTELDEWAARDE);

        restWozwaardeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWozwaarde.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedWozwaarde))
            )
            .andExpect(status().isOk());

        // Validate the Wozwaarde in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWozwaardeToMatchAllProperties(updatedWozwaarde);
    }

    @Test
    @Transactional
    void putNonExistingWozwaarde() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wozwaarde.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWozwaardeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, wozwaarde.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wozwaarde))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wozwaarde in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWozwaarde() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wozwaarde.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWozwaardeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(wozwaarde))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wozwaarde in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWozwaarde() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wozwaarde.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWozwaardeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wozwaarde)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Wozwaarde in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWozwaardeWithPatch() throws Exception {
        // Initialize the database
        wozwaardeRepository.saveAndFlush(wozwaarde);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the wozwaarde using partial update
        Wozwaarde partialUpdatedWozwaarde = new Wozwaarde();
        partialUpdatedWozwaarde.setId(wozwaarde.getId());

        partialUpdatedWozwaarde.datumpeilingtoestand(UPDATED_DATUMPEILINGTOESTAND);

        restWozwaardeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWozwaarde.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWozwaarde))
            )
            .andExpect(status().isOk());

        // Validate the Wozwaarde in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWozwaardeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedWozwaarde, wozwaarde),
            getPersistedWozwaarde(wozwaarde)
        );
    }

    @Test
    @Transactional
    void fullUpdateWozwaardeWithPatch() throws Exception {
        // Initialize the database
        wozwaardeRepository.saveAndFlush(wozwaarde);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the wozwaarde using partial update
        Wozwaarde partialUpdatedWozwaarde = new Wozwaarde();
        partialUpdatedWozwaarde.setId(wozwaarde.getId());

        partialUpdatedWozwaarde
            .datumpeilingtoestand(UPDATED_DATUMPEILINGTOESTAND)
            .datumwaardepeiling(UPDATED_DATUMWAARDEPEILING)
            .statusbeschikking(UPDATED_STATUSBESCHIKKING)
            .vastgesteldewaarde(UPDATED_VASTGESTELDEWAARDE);

        restWozwaardeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWozwaarde.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWozwaarde))
            )
            .andExpect(status().isOk());

        // Validate the Wozwaarde in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWozwaardeUpdatableFieldsEquals(partialUpdatedWozwaarde, getPersistedWozwaarde(partialUpdatedWozwaarde));
    }

    @Test
    @Transactional
    void patchNonExistingWozwaarde() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wozwaarde.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWozwaardeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, wozwaarde.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(wozwaarde))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wozwaarde in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWozwaarde() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wozwaarde.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWozwaardeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(wozwaarde))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wozwaarde in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWozwaarde() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wozwaarde.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWozwaardeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(wozwaarde)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Wozwaarde in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWozwaarde() throws Exception {
        // Initialize the database
        wozwaardeRepository.saveAndFlush(wozwaarde);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the wozwaarde
        restWozwaardeMockMvc
            .perform(delete(ENTITY_API_URL_ID, wozwaarde.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return wozwaardeRepository.count();
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

    protected Wozwaarde getPersistedWozwaarde(Wozwaarde wozwaarde) {
        return wozwaardeRepository.findById(wozwaarde.getId()).orElseThrow();
    }

    protected void assertPersistedWozwaardeToMatchAllProperties(Wozwaarde expectedWozwaarde) {
        assertWozwaardeAllPropertiesEquals(expectedWozwaarde, getPersistedWozwaarde(expectedWozwaarde));
    }

    protected void assertPersistedWozwaardeToMatchUpdatableProperties(Wozwaarde expectedWozwaarde) {
        assertWozwaardeAllUpdatablePropertiesEquals(expectedWozwaarde, getPersistedWozwaarde(expectedWozwaarde));
    }
}
