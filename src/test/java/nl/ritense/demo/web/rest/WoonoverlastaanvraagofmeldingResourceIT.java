package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.WoonoverlastaanvraagofmeldingAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Woonoverlastaanvraagofmelding;
import nl.ritense.demo.repository.WoonoverlastaanvraagofmeldingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WoonoverlastaanvraagofmeldingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WoonoverlastaanvraagofmeldingResourceIT {

    private static final String DEFAULT_LOCATIE = "AAAAAAAAAA";
    private static final String UPDATED_LOCATIE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATIEOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_LOCATIEOMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_MELDINGOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_MELDINGOMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_MELDINGTEKST = "AAAAAAAAAA";
    private static final String UPDATED_MELDINGTEKST = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/woonoverlastaanvraagofmeldings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WoonoverlastaanvraagofmeldingRepository woonoverlastaanvraagofmeldingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWoonoverlastaanvraagofmeldingMockMvc;

    private Woonoverlastaanvraagofmelding woonoverlastaanvraagofmelding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Woonoverlastaanvraagofmelding createEntity(EntityManager em) {
        Woonoverlastaanvraagofmelding woonoverlastaanvraagofmelding = new Woonoverlastaanvraagofmelding()
            .locatie(DEFAULT_LOCATIE)
            .locatieomschrijving(DEFAULT_LOCATIEOMSCHRIJVING)
            .meldingomschrijving(DEFAULT_MELDINGOMSCHRIJVING)
            .meldingtekst(DEFAULT_MELDINGTEKST);
        return woonoverlastaanvraagofmelding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Woonoverlastaanvraagofmelding createUpdatedEntity(EntityManager em) {
        Woonoverlastaanvraagofmelding woonoverlastaanvraagofmelding = new Woonoverlastaanvraagofmelding()
            .locatie(UPDATED_LOCATIE)
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING)
            .meldingomschrijving(UPDATED_MELDINGOMSCHRIJVING)
            .meldingtekst(UPDATED_MELDINGTEKST);
        return woonoverlastaanvraagofmelding;
    }

    @BeforeEach
    public void initTest() {
        woonoverlastaanvraagofmelding = createEntity(em);
    }

    @Test
    @Transactional
    void createWoonoverlastaanvraagofmelding() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Woonoverlastaanvraagofmelding
        var returnedWoonoverlastaanvraagofmelding = om.readValue(
            restWoonoverlastaanvraagofmeldingMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(woonoverlastaanvraagofmelding))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Woonoverlastaanvraagofmelding.class
        );

        // Validate the Woonoverlastaanvraagofmelding in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertWoonoverlastaanvraagofmeldingUpdatableFieldsEquals(
            returnedWoonoverlastaanvraagofmelding,
            getPersistedWoonoverlastaanvraagofmelding(returnedWoonoverlastaanvraagofmelding)
        );
    }

    @Test
    @Transactional
    void createWoonoverlastaanvraagofmeldingWithExistingId() throws Exception {
        // Create the Woonoverlastaanvraagofmelding with an existing ID
        woonoverlastaanvraagofmelding.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWoonoverlastaanvraagofmeldingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(woonoverlastaanvraagofmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Woonoverlastaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWoonoverlastaanvraagofmeldings() throws Exception {
        // Initialize the database
        woonoverlastaanvraagofmeldingRepository.saveAndFlush(woonoverlastaanvraagofmelding);

        // Get all the woonoverlastaanvraagofmeldingList
        restWoonoverlastaanvraagofmeldingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(woonoverlastaanvraagofmelding.getId().intValue())))
            .andExpect(jsonPath("$.[*].locatie").value(hasItem(DEFAULT_LOCATIE)))
            .andExpect(jsonPath("$.[*].locatieomschrijving").value(hasItem(DEFAULT_LOCATIEOMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].meldingomschrijving").value(hasItem(DEFAULT_MELDINGOMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].meldingtekst").value(hasItem(DEFAULT_MELDINGTEKST)));
    }

    @Test
    @Transactional
    void getWoonoverlastaanvraagofmelding() throws Exception {
        // Initialize the database
        woonoverlastaanvraagofmeldingRepository.saveAndFlush(woonoverlastaanvraagofmelding);

        // Get the woonoverlastaanvraagofmelding
        restWoonoverlastaanvraagofmeldingMockMvc
            .perform(get(ENTITY_API_URL_ID, woonoverlastaanvraagofmelding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(woonoverlastaanvraagofmelding.getId().intValue()))
            .andExpect(jsonPath("$.locatie").value(DEFAULT_LOCATIE))
            .andExpect(jsonPath("$.locatieomschrijving").value(DEFAULT_LOCATIEOMSCHRIJVING))
            .andExpect(jsonPath("$.meldingomschrijving").value(DEFAULT_MELDINGOMSCHRIJVING))
            .andExpect(jsonPath("$.meldingtekst").value(DEFAULT_MELDINGTEKST));
    }

    @Test
    @Transactional
    void getNonExistingWoonoverlastaanvraagofmelding() throws Exception {
        // Get the woonoverlastaanvraagofmelding
        restWoonoverlastaanvraagofmeldingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWoonoverlastaanvraagofmelding() throws Exception {
        // Initialize the database
        woonoverlastaanvraagofmeldingRepository.saveAndFlush(woonoverlastaanvraagofmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the woonoverlastaanvraagofmelding
        Woonoverlastaanvraagofmelding updatedWoonoverlastaanvraagofmelding = woonoverlastaanvraagofmeldingRepository
            .findById(woonoverlastaanvraagofmelding.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedWoonoverlastaanvraagofmelding are not directly saved in db
        em.detach(updatedWoonoverlastaanvraagofmelding);
        updatedWoonoverlastaanvraagofmelding
            .locatie(UPDATED_LOCATIE)
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING)
            .meldingomschrijving(UPDATED_MELDINGOMSCHRIJVING)
            .meldingtekst(UPDATED_MELDINGTEKST);

        restWoonoverlastaanvraagofmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWoonoverlastaanvraagofmelding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedWoonoverlastaanvraagofmelding))
            )
            .andExpect(status().isOk());

        // Validate the Woonoverlastaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWoonoverlastaanvraagofmeldingToMatchAllProperties(updatedWoonoverlastaanvraagofmelding);
    }

    @Test
    @Transactional
    void putNonExistingWoonoverlastaanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        woonoverlastaanvraagofmelding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWoonoverlastaanvraagofmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, woonoverlastaanvraagofmelding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(woonoverlastaanvraagofmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Woonoverlastaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWoonoverlastaanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        woonoverlastaanvraagofmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWoonoverlastaanvraagofmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(woonoverlastaanvraagofmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Woonoverlastaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWoonoverlastaanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        woonoverlastaanvraagofmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWoonoverlastaanvraagofmeldingMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(woonoverlastaanvraagofmelding))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Woonoverlastaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWoonoverlastaanvraagofmeldingWithPatch() throws Exception {
        // Initialize the database
        woonoverlastaanvraagofmeldingRepository.saveAndFlush(woonoverlastaanvraagofmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the woonoverlastaanvraagofmelding using partial update
        Woonoverlastaanvraagofmelding partialUpdatedWoonoverlastaanvraagofmelding = new Woonoverlastaanvraagofmelding();
        partialUpdatedWoonoverlastaanvraagofmelding.setId(woonoverlastaanvraagofmelding.getId());

        partialUpdatedWoonoverlastaanvraagofmelding.meldingomschrijving(UPDATED_MELDINGOMSCHRIJVING).meldingtekst(UPDATED_MELDINGTEKST);

        restWoonoverlastaanvraagofmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWoonoverlastaanvraagofmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWoonoverlastaanvraagofmelding))
            )
            .andExpect(status().isOk());

        // Validate the Woonoverlastaanvraagofmelding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWoonoverlastaanvraagofmeldingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedWoonoverlastaanvraagofmelding, woonoverlastaanvraagofmelding),
            getPersistedWoonoverlastaanvraagofmelding(woonoverlastaanvraagofmelding)
        );
    }

    @Test
    @Transactional
    void fullUpdateWoonoverlastaanvraagofmeldingWithPatch() throws Exception {
        // Initialize the database
        woonoverlastaanvraagofmeldingRepository.saveAndFlush(woonoverlastaanvraagofmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the woonoverlastaanvraagofmelding using partial update
        Woonoverlastaanvraagofmelding partialUpdatedWoonoverlastaanvraagofmelding = new Woonoverlastaanvraagofmelding();
        partialUpdatedWoonoverlastaanvraagofmelding.setId(woonoverlastaanvraagofmelding.getId());

        partialUpdatedWoonoverlastaanvraagofmelding
            .locatie(UPDATED_LOCATIE)
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING)
            .meldingomschrijving(UPDATED_MELDINGOMSCHRIJVING)
            .meldingtekst(UPDATED_MELDINGTEKST);

        restWoonoverlastaanvraagofmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWoonoverlastaanvraagofmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWoonoverlastaanvraagofmelding))
            )
            .andExpect(status().isOk());

        // Validate the Woonoverlastaanvraagofmelding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWoonoverlastaanvraagofmeldingUpdatableFieldsEquals(
            partialUpdatedWoonoverlastaanvraagofmelding,
            getPersistedWoonoverlastaanvraagofmelding(partialUpdatedWoonoverlastaanvraagofmelding)
        );
    }

    @Test
    @Transactional
    void patchNonExistingWoonoverlastaanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        woonoverlastaanvraagofmelding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWoonoverlastaanvraagofmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, woonoverlastaanvraagofmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(woonoverlastaanvraagofmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Woonoverlastaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWoonoverlastaanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        woonoverlastaanvraagofmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWoonoverlastaanvraagofmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(woonoverlastaanvraagofmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Woonoverlastaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWoonoverlastaanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        woonoverlastaanvraagofmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWoonoverlastaanvraagofmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(woonoverlastaanvraagofmelding))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Woonoverlastaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWoonoverlastaanvraagofmelding() throws Exception {
        // Initialize the database
        woonoverlastaanvraagofmeldingRepository.saveAndFlush(woonoverlastaanvraagofmelding);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the woonoverlastaanvraagofmelding
        restWoonoverlastaanvraagofmeldingMockMvc
            .perform(delete(ENTITY_API_URL_ID, woonoverlastaanvraagofmelding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return woonoverlastaanvraagofmeldingRepository.count();
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

    protected Woonoverlastaanvraagofmelding getPersistedWoonoverlastaanvraagofmelding(
        Woonoverlastaanvraagofmelding woonoverlastaanvraagofmelding
    ) {
        return woonoverlastaanvraagofmeldingRepository.findById(woonoverlastaanvraagofmelding.getId()).orElseThrow();
    }

    protected void assertPersistedWoonoverlastaanvraagofmeldingToMatchAllProperties(
        Woonoverlastaanvraagofmelding expectedWoonoverlastaanvraagofmelding
    ) {
        assertWoonoverlastaanvraagofmeldingAllPropertiesEquals(
            expectedWoonoverlastaanvraagofmelding,
            getPersistedWoonoverlastaanvraagofmelding(expectedWoonoverlastaanvraagofmelding)
        );
    }

    protected void assertPersistedWoonoverlastaanvraagofmeldingToMatchUpdatableProperties(
        Woonoverlastaanvraagofmelding expectedWoonoverlastaanvraagofmelding
    ) {
        assertWoonoverlastaanvraagofmeldingAllUpdatablePropertiesEquals(
            expectedWoonoverlastaanvraagofmelding,
            getPersistedWoonoverlastaanvraagofmelding(expectedWoonoverlastaanvraagofmelding)
        );
    }
}
