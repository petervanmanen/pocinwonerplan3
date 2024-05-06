package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonAsserts.*;
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
import nl.ritense.demo.domain.Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon;
import nl.ritense.demo.repository.VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonResourceIT {

    private static final String DEFAULT_GEMEENTEVERORDENING = "AAAAAAAAAA";
    private static final String UPDATED_GEMEENTEVERORDENING = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVINGDERDE = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVINGDERDE = "BBBBBBBBBB";

    private static final String DEFAULT_PARTIJ = "AAAAAAAAAA";
    private static final String UPDATED_PARTIJ = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRepository verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonMockMvc;

    private Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon createEntity(EntityManager em) {
        Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon =
            new Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon()
                .gemeenteverordening(DEFAULT_GEMEENTEVERORDENING)
                .omschrijvingderde(DEFAULT_OMSCHRIJVINGDERDE)
                .partij(DEFAULT_PARTIJ);
        return verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon createUpdatedEntity(EntityManager em) {
        Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon =
            new Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon()
                .gemeenteverordening(UPDATED_GEMEENTEVERORDENING)
                .omschrijvingderde(UPDATED_OMSCHRIJVINGDERDE)
                .partij(UPDATED_PARTIJ);
        return verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon;
    }

    @BeforeEach
    public void initTest() {
        verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon = createEntity(em);
    }

    @Test
    @Transactional
    void createVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
        var returnedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon = om.readValue(
            restVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.class
        );

        // Validate the Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonUpdatableFieldsEquals(
            returnedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon,
            getPersistedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon(
                returnedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
            )
        );
    }

    @Test
    @Transactional
    void createVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonWithExistingId() throws Exception {
        // Create the Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon with an existing ID
        verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoons() throws Exception {
        // Initialize the database
        verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRepository.saveAndFlush(
            verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
        );

        // Get all the verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonList
        restVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.getId().intValue())))
            .andExpect(jsonPath("$.[*].gemeenteverordening").value(hasItem(DEFAULT_GEMEENTEVERORDENING)))
            .andExpect(jsonPath("$.[*].omschrijvingderde").value(hasItem(DEFAULT_OMSCHRIJVINGDERDE)))
            .andExpect(jsonPath("$.[*].partij").value(hasItem(DEFAULT_PARTIJ)));
    }

    @Test
    @Transactional
    void getVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon() throws Exception {
        // Initialize the database
        verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRepository.saveAndFlush(
            verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
        );

        // Get the verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
        restVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonMockMvc
            .perform(get(ENTITY_API_URL_ID, verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.getId().intValue()))
            .andExpect(jsonPath("$.gemeenteverordening").value(DEFAULT_GEMEENTEVERORDENING))
            .andExpect(jsonPath("$.omschrijvingderde").value(DEFAULT_OMSCHRIJVINGDERDE))
            .andExpect(jsonPath("$.partij").value(DEFAULT_PARTIJ));
    }

    @Test
    @Transactional
    void getNonExistingVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon() throws Exception {
        // Get the verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
        restVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonMockMvc
            .perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon() throws Exception {
        // Initialize the database
        verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRepository.saveAndFlush(
            verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
        Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon updatedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon =
            verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRepository
                .findById(verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.getId())
                .orElseThrow();
        // Disconnect from session so that the updates on updatedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon are not directly saved in db
        em.detach(updatedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon);
        updatedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
            .gemeenteverordening(UPDATED_GEMEENTEVERORDENING)
            .omschrijvingderde(UPDATED_OMSCHRIJVINGDERDE)
            .partij(UPDATED_PARTIJ);

        restVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonToMatchAllProperties(
            updatedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
        );
    }

    @Test
    @Transactional
    void putNonExistingVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonWithPatch() throws Exception {
        // Initialize the database
        verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRepository.saveAndFlush(
            verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon using partial update
        Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon partialUpdatedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon =
            new Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon();
        partialUpdatedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.setId(
            verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.getId()
        );

        partialUpdatedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
            .gemeenteverordening(UPDATED_GEMEENTEVERORDENING)
            .omschrijvingderde(UPDATED_OMSCHRIJVINGDERDE)
            .partij(UPDATED_PARTIJ);

        restVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonUpdatableFieldsEquals(
            createUpdateProxyForBean(
                partialUpdatedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon,
                verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
            ),
            getPersistedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon(
                verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
            )
        );
    }

    @Test
    @Transactional
    void fullUpdateVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonWithPatch() throws Exception {
        // Initialize the database
        verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRepository.saveAndFlush(
            verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon using partial update
        Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon partialUpdatedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon =
            new Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon();
        partialUpdatedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.setId(
            verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.getId()
        );

        partialUpdatedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
            .gemeenteverordening(UPDATED_GEMEENTEVERORDENING)
            .omschrijvingderde(UPDATED_OMSCHRIJVINGDERDE)
            .partij(UPDATED_PARTIJ);

        restVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonUpdatableFieldsEquals(
            partialUpdatedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon,
            getPersistedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon(
                partialUpdatedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
            )
        );
    }

    @Test
    @Transactional
    void patchNonExistingVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon() throws Exception {
        // Initialize the database
        verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRepository.saveAndFlush(
            verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
        );

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
        restVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonMockMvc
            .perform(
                delete(ENTITY_API_URL_ID, verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.getId()).accept(
                    MediaType.APPLICATION_JSON
                )
            )
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRepository.count();
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

    protected Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon getPersistedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon(
        Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
    ) {
        return verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRepository
            .findById(verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.getId())
            .orElseThrow();
    }

    protected void assertPersistedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonToMatchAllProperties(
        Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon expectedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
    ) {
        assertVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonAllPropertiesEquals(
            expectedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon,
            getPersistedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon(
                expectedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
            )
        );
    }

    protected void assertPersistedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonToMatchUpdatableProperties(
        Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon expectedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
    ) {
        assertVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonAllUpdatablePropertiesEquals(
            expectedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon,
            getPersistedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon(
                expectedVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
            )
        );
    }
}
