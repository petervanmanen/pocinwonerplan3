package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.WoonfraudeaanvraagofmeldingAsserts.*;
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
import nl.ritense.demo.domain.Woonfraudeaanvraagofmelding;
import nl.ritense.demo.repository.WoonfraudeaanvraagofmeldingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WoonfraudeaanvraagofmeldingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WoonfraudeaanvraagofmeldingResourceIT {

    private static final String DEFAULT_ADRES = "AAAAAAAAAA";
    private static final String UPDATED_ADRES = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATIEOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_LOCATIEOMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_MELDINGOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_MELDINGOMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_MELDINGTEKST = "AAAAAAAAAA";
    private static final String UPDATED_MELDINGTEKST = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/woonfraudeaanvraagofmeldings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WoonfraudeaanvraagofmeldingRepository woonfraudeaanvraagofmeldingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWoonfraudeaanvraagofmeldingMockMvc;

    private Woonfraudeaanvraagofmelding woonfraudeaanvraagofmelding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Woonfraudeaanvraagofmelding createEntity(EntityManager em) {
        Woonfraudeaanvraagofmelding woonfraudeaanvraagofmelding = new Woonfraudeaanvraagofmelding()
            .adres(DEFAULT_ADRES)
            .categorie(DEFAULT_CATEGORIE)
            .locatieomschrijving(DEFAULT_LOCATIEOMSCHRIJVING)
            .meldingomschrijving(DEFAULT_MELDINGOMSCHRIJVING)
            .meldingtekst(DEFAULT_MELDINGTEKST);
        return woonfraudeaanvraagofmelding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Woonfraudeaanvraagofmelding createUpdatedEntity(EntityManager em) {
        Woonfraudeaanvraagofmelding woonfraudeaanvraagofmelding = new Woonfraudeaanvraagofmelding()
            .adres(UPDATED_ADRES)
            .categorie(UPDATED_CATEGORIE)
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING)
            .meldingomschrijving(UPDATED_MELDINGOMSCHRIJVING)
            .meldingtekst(UPDATED_MELDINGTEKST);
        return woonfraudeaanvraagofmelding;
    }

    @BeforeEach
    public void initTest() {
        woonfraudeaanvraagofmelding = createEntity(em);
    }

    @Test
    @Transactional
    void createWoonfraudeaanvraagofmelding() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Woonfraudeaanvraagofmelding
        var returnedWoonfraudeaanvraagofmelding = om.readValue(
            restWoonfraudeaanvraagofmeldingMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(woonfraudeaanvraagofmelding))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Woonfraudeaanvraagofmelding.class
        );

        // Validate the Woonfraudeaanvraagofmelding in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertWoonfraudeaanvraagofmeldingUpdatableFieldsEquals(
            returnedWoonfraudeaanvraagofmelding,
            getPersistedWoonfraudeaanvraagofmelding(returnedWoonfraudeaanvraagofmelding)
        );
    }

    @Test
    @Transactional
    void createWoonfraudeaanvraagofmeldingWithExistingId() throws Exception {
        // Create the Woonfraudeaanvraagofmelding with an existing ID
        woonfraudeaanvraagofmelding.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWoonfraudeaanvraagofmeldingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(woonfraudeaanvraagofmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Woonfraudeaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWoonfraudeaanvraagofmeldings() throws Exception {
        // Initialize the database
        woonfraudeaanvraagofmeldingRepository.saveAndFlush(woonfraudeaanvraagofmelding);

        // Get all the woonfraudeaanvraagofmeldingList
        restWoonfraudeaanvraagofmeldingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(woonfraudeaanvraagofmelding.getId().intValue())))
            .andExpect(jsonPath("$.[*].adres").value(hasItem(DEFAULT_ADRES)))
            .andExpect(jsonPath("$.[*].categorie").value(hasItem(DEFAULT_CATEGORIE)))
            .andExpect(jsonPath("$.[*].locatieomschrijving").value(hasItem(DEFAULT_LOCATIEOMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].meldingomschrijving").value(hasItem(DEFAULT_MELDINGOMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].meldingtekst").value(hasItem(DEFAULT_MELDINGTEKST)));
    }

    @Test
    @Transactional
    void getWoonfraudeaanvraagofmelding() throws Exception {
        // Initialize the database
        woonfraudeaanvraagofmeldingRepository.saveAndFlush(woonfraudeaanvraagofmelding);

        // Get the woonfraudeaanvraagofmelding
        restWoonfraudeaanvraagofmeldingMockMvc
            .perform(get(ENTITY_API_URL_ID, woonfraudeaanvraagofmelding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(woonfraudeaanvraagofmelding.getId().intValue()))
            .andExpect(jsonPath("$.adres").value(DEFAULT_ADRES))
            .andExpect(jsonPath("$.categorie").value(DEFAULT_CATEGORIE))
            .andExpect(jsonPath("$.locatieomschrijving").value(DEFAULT_LOCATIEOMSCHRIJVING))
            .andExpect(jsonPath("$.meldingomschrijving").value(DEFAULT_MELDINGOMSCHRIJVING))
            .andExpect(jsonPath("$.meldingtekst").value(DEFAULT_MELDINGTEKST));
    }

    @Test
    @Transactional
    void getNonExistingWoonfraudeaanvraagofmelding() throws Exception {
        // Get the woonfraudeaanvraagofmelding
        restWoonfraudeaanvraagofmeldingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWoonfraudeaanvraagofmelding() throws Exception {
        // Initialize the database
        woonfraudeaanvraagofmeldingRepository.saveAndFlush(woonfraudeaanvraagofmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the woonfraudeaanvraagofmelding
        Woonfraudeaanvraagofmelding updatedWoonfraudeaanvraagofmelding = woonfraudeaanvraagofmeldingRepository
            .findById(woonfraudeaanvraagofmelding.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedWoonfraudeaanvraagofmelding are not directly saved in db
        em.detach(updatedWoonfraudeaanvraagofmelding);
        updatedWoonfraudeaanvraagofmelding
            .adres(UPDATED_ADRES)
            .categorie(UPDATED_CATEGORIE)
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING)
            .meldingomschrijving(UPDATED_MELDINGOMSCHRIJVING)
            .meldingtekst(UPDATED_MELDINGTEKST);

        restWoonfraudeaanvraagofmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWoonfraudeaanvraagofmelding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedWoonfraudeaanvraagofmelding))
            )
            .andExpect(status().isOk());

        // Validate the Woonfraudeaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWoonfraudeaanvraagofmeldingToMatchAllProperties(updatedWoonfraudeaanvraagofmelding);
    }

    @Test
    @Transactional
    void putNonExistingWoonfraudeaanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        woonfraudeaanvraagofmelding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWoonfraudeaanvraagofmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, woonfraudeaanvraagofmelding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(woonfraudeaanvraagofmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Woonfraudeaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWoonfraudeaanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        woonfraudeaanvraagofmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWoonfraudeaanvraagofmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(woonfraudeaanvraagofmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Woonfraudeaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWoonfraudeaanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        woonfraudeaanvraagofmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWoonfraudeaanvraagofmeldingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(woonfraudeaanvraagofmelding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Woonfraudeaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWoonfraudeaanvraagofmeldingWithPatch() throws Exception {
        // Initialize the database
        woonfraudeaanvraagofmeldingRepository.saveAndFlush(woonfraudeaanvraagofmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the woonfraudeaanvraagofmelding using partial update
        Woonfraudeaanvraagofmelding partialUpdatedWoonfraudeaanvraagofmelding = new Woonfraudeaanvraagofmelding();
        partialUpdatedWoonfraudeaanvraagofmelding.setId(woonfraudeaanvraagofmelding.getId());

        partialUpdatedWoonfraudeaanvraagofmelding
            .categorie(UPDATED_CATEGORIE)
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING)
            .meldingomschrijving(UPDATED_MELDINGOMSCHRIJVING)
            .meldingtekst(UPDATED_MELDINGTEKST);

        restWoonfraudeaanvraagofmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWoonfraudeaanvraagofmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWoonfraudeaanvraagofmelding))
            )
            .andExpect(status().isOk());

        // Validate the Woonfraudeaanvraagofmelding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWoonfraudeaanvraagofmeldingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedWoonfraudeaanvraagofmelding, woonfraudeaanvraagofmelding),
            getPersistedWoonfraudeaanvraagofmelding(woonfraudeaanvraagofmelding)
        );
    }

    @Test
    @Transactional
    void fullUpdateWoonfraudeaanvraagofmeldingWithPatch() throws Exception {
        // Initialize the database
        woonfraudeaanvraagofmeldingRepository.saveAndFlush(woonfraudeaanvraagofmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the woonfraudeaanvraagofmelding using partial update
        Woonfraudeaanvraagofmelding partialUpdatedWoonfraudeaanvraagofmelding = new Woonfraudeaanvraagofmelding();
        partialUpdatedWoonfraudeaanvraagofmelding.setId(woonfraudeaanvraagofmelding.getId());

        partialUpdatedWoonfraudeaanvraagofmelding
            .adres(UPDATED_ADRES)
            .categorie(UPDATED_CATEGORIE)
            .locatieomschrijving(UPDATED_LOCATIEOMSCHRIJVING)
            .meldingomschrijving(UPDATED_MELDINGOMSCHRIJVING)
            .meldingtekst(UPDATED_MELDINGTEKST);

        restWoonfraudeaanvraagofmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWoonfraudeaanvraagofmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWoonfraudeaanvraagofmelding))
            )
            .andExpect(status().isOk());

        // Validate the Woonfraudeaanvraagofmelding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWoonfraudeaanvraagofmeldingUpdatableFieldsEquals(
            partialUpdatedWoonfraudeaanvraagofmelding,
            getPersistedWoonfraudeaanvraagofmelding(partialUpdatedWoonfraudeaanvraagofmelding)
        );
    }

    @Test
    @Transactional
    void patchNonExistingWoonfraudeaanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        woonfraudeaanvraagofmelding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWoonfraudeaanvraagofmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, woonfraudeaanvraagofmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(woonfraudeaanvraagofmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Woonfraudeaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWoonfraudeaanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        woonfraudeaanvraagofmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWoonfraudeaanvraagofmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(woonfraudeaanvraagofmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Woonfraudeaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWoonfraudeaanvraagofmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        woonfraudeaanvraagofmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWoonfraudeaanvraagofmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(woonfraudeaanvraagofmelding))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Woonfraudeaanvraagofmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWoonfraudeaanvraagofmelding() throws Exception {
        // Initialize the database
        woonfraudeaanvraagofmeldingRepository.saveAndFlush(woonfraudeaanvraagofmelding);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the woonfraudeaanvraagofmelding
        restWoonfraudeaanvraagofmeldingMockMvc
            .perform(delete(ENTITY_API_URL_ID, woonfraudeaanvraagofmelding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return woonfraudeaanvraagofmeldingRepository.count();
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

    protected Woonfraudeaanvraagofmelding getPersistedWoonfraudeaanvraagofmelding(Woonfraudeaanvraagofmelding woonfraudeaanvraagofmelding) {
        return woonfraudeaanvraagofmeldingRepository.findById(woonfraudeaanvraagofmelding.getId()).orElseThrow();
    }

    protected void assertPersistedWoonfraudeaanvraagofmeldingToMatchAllProperties(
        Woonfraudeaanvraagofmelding expectedWoonfraudeaanvraagofmelding
    ) {
        assertWoonfraudeaanvraagofmeldingAllPropertiesEquals(
            expectedWoonfraudeaanvraagofmelding,
            getPersistedWoonfraudeaanvraagofmelding(expectedWoonfraudeaanvraagofmelding)
        );
    }

    protected void assertPersistedWoonfraudeaanvraagofmeldingToMatchUpdatableProperties(
        Woonfraudeaanvraagofmelding expectedWoonfraudeaanvraagofmelding
    ) {
        assertWoonfraudeaanvraagofmeldingAllUpdatablePropertiesEquals(
            expectedWoonfraudeaanvraagofmelding,
            getPersistedWoonfraudeaanvraagofmelding(expectedWoonfraudeaanvraagofmelding)
        );
    }
}
