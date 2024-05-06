package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.NationaliteitingeschrevennatuurlijkpersoonAsserts.*;
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
import nl.ritense.demo.domain.Nationaliteitingeschrevennatuurlijkpersoon;
import nl.ritense.demo.repository.NationaliteitingeschrevennatuurlijkpersoonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NationaliteitingeschrevennatuurlijkpersoonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NationaliteitingeschrevennatuurlijkpersoonResourceIT {

    private static final String DEFAULT_BUITENLANDSPERSOONSNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_BUITENLANDSPERSOONSNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITEIT = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITEIT = "BBBBBBBBBB";

    private static final String DEFAULT_REDENVERKRIJGING = "AAAAAAAAAA";
    private static final String UPDATED_REDENVERKRIJGING = "BBBBBBBBBB";

    private static final String DEFAULT_REDENVERLIES = "AAAAAAAAAA";
    private static final String UPDATED_REDENVERLIES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nationaliteitingeschrevennatuurlijkpersoons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NationaliteitingeschrevennatuurlijkpersoonRepository nationaliteitingeschrevennatuurlijkpersoonRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNationaliteitingeschrevennatuurlijkpersoonMockMvc;

    private Nationaliteitingeschrevennatuurlijkpersoon nationaliteitingeschrevennatuurlijkpersoon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nationaliteitingeschrevennatuurlijkpersoon createEntity(EntityManager em) {
        Nationaliteitingeschrevennatuurlijkpersoon nationaliteitingeschrevennatuurlijkpersoon =
            new Nationaliteitingeschrevennatuurlijkpersoon()
                .buitenlandspersoonsnummer(DEFAULT_BUITENLANDSPERSOONSNUMMER)
                .nationaliteit(DEFAULT_NATIONALITEIT)
                .redenverkrijging(DEFAULT_REDENVERKRIJGING)
                .redenverlies(DEFAULT_REDENVERLIES);
        return nationaliteitingeschrevennatuurlijkpersoon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nationaliteitingeschrevennatuurlijkpersoon createUpdatedEntity(EntityManager em) {
        Nationaliteitingeschrevennatuurlijkpersoon nationaliteitingeschrevennatuurlijkpersoon =
            new Nationaliteitingeschrevennatuurlijkpersoon()
                .buitenlandspersoonsnummer(UPDATED_BUITENLANDSPERSOONSNUMMER)
                .nationaliteit(UPDATED_NATIONALITEIT)
                .redenverkrijging(UPDATED_REDENVERKRIJGING)
                .redenverlies(UPDATED_REDENVERLIES);
        return nationaliteitingeschrevennatuurlijkpersoon;
    }

    @BeforeEach
    public void initTest() {
        nationaliteitingeschrevennatuurlijkpersoon = createEntity(em);
    }

    @Test
    @Transactional
    void createNationaliteitingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Nationaliteitingeschrevennatuurlijkpersoon
        var returnedNationaliteitingeschrevennatuurlijkpersoon = om.readValue(
            restNationaliteitingeschrevennatuurlijkpersoonMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(nationaliteitingeschrevennatuurlijkpersoon))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Nationaliteitingeschrevennatuurlijkpersoon.class
        );

        // Validate the Nationaliteitingeschrevennatuurlijkpersoon in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertNationaliteitingeschrevennatuurlijkpersoonUpdatableFieldsEquals(
            returnedNationaliteitingeschrevennatuurlijkpersoon,
            getPersistedNationaliteitingeschrevennatuurlijkpersoon(returnedNationaliteitingeschrevennatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void createNationaliteitingeschrevennatuurlijkpersoonWithExistingId() throws Exception {
        // Create the Nationaliteitingeschrevennatuurlijkpersoon with an existing ID
        nationaliteitingeschrevennatuurlijkpersoon.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNationaliteitingeschrevennatuurlijkpersoonMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(nationaliteitingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nationaliteitingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNationaliteitingeschrevennatuurlijkpersoons() throws Exception {
        // Initialize the database
        nationaliteitingeschrevennatuurlijkpersoonRepository.saveAndFlush(nationaliteitingeschrevennatuurlijkpersoon);

        // Get all the nationaliteitingeschrevennatuurlijkpersoonList
        restNationaliteitingeschrevennatuurlijkpersoonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nationaliteitingeschrevennatuurlijkpersoon.getId().intValue())))
            .andExpect(jsonPath("$.[*].buitenlandspersoonsnummer").value(hasItem(DEFAULT_BUITENLANDSPERSOONSNUMMER)))
            .andExpect(jsonPath("$.[*].nationaliteit").value(hasItem(DEFAULT_NATIONALITEIT)))
            .andExpect(jsonPath("$.[*].redenverkrijging").value(hasItem(DEFAULT_REDENVERKRIJGING)))
            .andExpect(jsonPath("$.[*].redenverlies").value(hasItem(DEFAULT_REDENVERLIES)));
    }

    @Test
    @Transactional
    void getNationaliteitingeschrevennatuurlijkpersoon() throws Exception {
        // Initialize the database
        nationaliteitingeschrevennatuurlijkpersoonRepository.saveAndFlush(nationaliteitingeschrevennatuurlijkpersoon);

        // Get the nationaliteitingeschrevennatuurlijkpersoon
        restNationaliteitingeschrevennatuurlijkpersoonMockMvc
            .perform(get(ENTITY_API_URL_ID, nationaliteitingeschrevennatuurlijkpersoon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nationaliteitingeschrevennatuurlijkpersoon.getId().intValue()))
            .andExpect(jsonPath("$.buitenlandspersoonsnummer").value(DEFAULT_BUITENLANDSPERSOONSNUMMER))
            .andExpect(jsonPath("$.nationaliteit").value(DEFAULT_NATIONALITEIT))
            .andExpect(jsonPath("$.redenverkrijging").value(DEFAULT_REDENVERKRIJGING))
            .andExpect(jsonPath("$.redenverlies").value(DEFAULT_REDENVERLIES));
    }

    @Test
    @Transactional
    void getNonExistingNationaliteitingeschrevennatuurlijkpersoon() throws Exception {
        // Get the nationaliteitingeschrevennatuurlijkpersoon
        restNationaliteitingeschrevennatuurlijkpersoonMockMvc
            .perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNationaliteitingeschrevennatuurlijkpersoon() throws Exception {
        // Initialize the database
        nationaliteitingeschrevennatuurlijkpersoonRepository.saveAndFlush(nationaliteitingeschrevennatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the nationaliteitingeschrevennatuurlijkpersoon
        Nationaliteitingeschrevennatuurlijkpersoon updatedNationaliteitingeschrevennatuurlijkpersoon =
            nationaliteitingeschrevennatuurlijkpersoonRepository.findById(nationaliteitingeschrevennatuurlijkpersoon.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNationaliteitingeschrevennatuurlijkpersoon are not directly saved in db
        em.detach(updatedNationaliteitingeschrevennatuurlijkpersoon);
        updatedNationaliteitingeschrevennatuurlijkpersoon
            .buitenlandspersoonsnummer(UPDATED_BUITENLANDSPERSOONSNUMMER)
            .nationaliteit(UPDATED_NATIONALITEIT)
            .redenverkrijging(UPDATED_REDENVERKRIJGING)
            .redenverlies(UPDATED_REDENVERLIES);

        restNationaliteitingeschrevennatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNationaliteitingeschrevennatuurlijkpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedNationaliteitingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Nationaliteitingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNationaliteitingeschrevennatuurlijkpersoonToMatchAllProperties(updatedNationaliteitingeschrevennatuurlijkpersoon);
    }

    @Test
    @Transactional
    void putNonExistingNationaliteitingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nationaliteitingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNationaliteitingeschrevennatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nationaliteitingeschrevennatuurlijkpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(nationaliteitingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nationaliteitingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNationaliteitingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nationaliteitingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNationaliteitingeschrevennatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(nationaliteitingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nationaliteitingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNationaliteitingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nationaliteitingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNationaliteitingeschrevennatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(nationaliteitingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nationaliteitingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNationaliteitingeschrevennatuurlijkpersoonWithPatch() throws Exception {
        // Initialize the database
        nationaliteitingeschrevennatuurlijkpersoonRepository.saveAndFlush(nationaliteitingeschrevennatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the nationaliteitingeschrevennatuurlijkpersoon using partial update
        Nationaliteitingeschrevennatuurlijkpersoon partialUpdatedNationaliteitingeschrevennatuurlijkpersoon =
            new Nationaliteitingeschrevennatuurlijkpersoon();
        partialUpdatedNationaliteitingeschrevennatuurlijkpersoon.setId(nationaliteitingeschrevennatuurlijkpersoon.getId());

        partialUpdatedNationaliteitingeschrevennatuurlijkpersoon
            .buitenlandspersoonsnummer(UPDATED_BUITENLANDSPERSOONSNUMMER)
            .nationaliteit(UPDATED_NATIONALITEIT)
            .redenverkrijging(UPDATED_REDENVERKRIJGING);

        restNationaliteitingeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNationaliteitingeschrevennatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNationaliteitingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Nationaliteitingeschrevennatuurlijkpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNationaliteitingeschrevennatuurlijkpersoonUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNationaliteitingeschrevennatuurlijkpersoon, nationaliteitingeschrevennatuurlijkpersoon),
            getPersistedNationaliteitingeschrevennatuurlijkpersoon(nationaliteitingeschrevennatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void fullUpdateNationaliteitingeschrevennatuurlijkpersoonWithPatch() throws Exception {
        // Initialize the database
        nationaliteitingeschrevennatuurlijkpersoonRepository.saveAndFlush(nationaliteitingeschrevennatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the nationaliteitingeschrevennatuurlijkpersoon using partial update
        Nationaliteitingeschrevennatuurlijkpersoon partialUpdatedNationaliteitingeschrevennatuurlijkpersoon =
            new Nationaliteitingeschrevennatuurlijkpersoon();
        partialUpdatedNationaliteitingeschrevennatuurlijkpersoon.setId(nationaliteitingeschrevennatuurlijkpersoon.getId());

        partialUpdatedNationaliteitingeschrevennatuurlijkpersoon
            .buitenlandspersoonsnummer(UPDATED_BUITENLANDSPERSOONSNUMMER)
            .nationaliteit(UPDATED_NATIONALITEIT)
            .redenverkrijging(UPDATED_REDENVERKRIJGING)
            .redenverlies(UPDATED_REDENVERLIES);

        restNationaliteitingeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNationaliteitingeschrevennatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNationaliteitingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Nationaliteitingeschrevennatuurlijkpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNationaliteitingeschrevennatuurlijkpersoonUpdatableFieldsEquals(
            partialUpdatedNationaliteitingeschrevennatuurlijkpersoon,
            getPersistedNationaliteitingeschrevennatuurlijkpersoon(partialUpdatedNationaliteitingeschrevennatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void patchNonExistingNationaliteitingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nationaliteitingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNationaliteitingeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nationaliteitingeschrevennatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(nationaliteitingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nationaliteitingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNationaliteitingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nationaliteitingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNationaliteitingeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(nationaliteitingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nationaliteitingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNationaliteitingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nationaliteitingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNationaliteitingeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(nationaliteitingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nationaliteitingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNationaliteitingeschrevennatuurlijkpersoon() throws Exception {
        // Initialize the database
        nationaliteitingeschrevennatuurlijkpersoonRepository.saveAndFlush(nationaliteitingeschrevennatuurlijkpersoon);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the nationaliteitingeschrevennatuurlijkpersoon
        restNationaliteitingeschrevennatuurlijkpersoonMockMvc
            .perform(delete(ENTITY_API_URL_ID, nationaliteitingeschrevennatuurlijkpersoon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return nationaliteitingeschrevennatuurlijkpersoonRepository.count();
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

    protected Nationaliteitingeschrevennatuurlijkpersoon getPersistedNationaliteitingeschrevennatuurlijkpersoon(
        Nationaliteitingeschrevennatuurlijkpersoon nationaliteitingeschrevennatuurlijkpersoon
    ) {
        return nationaliteitingeschrevennatuurlijkpersoonRepository
            .findById(nationaliteitingeschrevennatuurlijkpersoon.getId())
            .orElseThrow();
    }

    protected void assertPersistedNationaliteitingeschrevennatuurlijkpersoonToMatchAllProperties(
        Nationaliteitingeschrevennatuurlijkpersoon expectedNationaliteitingeschrevennatuurlijkpersoon
    ) {
        assertNationaliteitingeschrevennatuurlijkpersoonAllPropertiesEquals(
            expectedNationaliteitingeschrevennatuurlijkpersoon,
            getPersistedNationaliteitingeschrevennatuurlijkpersoon(expectedNationaliteitingeschrevennatuurlijkpersoon)
        );
    }

    protected void assertPersistedNationaliteitingeschrevennatuurlijkpersoonToMatchUpdatableProperties(
        Nationaliteitingeschrevennatuurlijkpersoon expectedNationaliteitingeschrevennatuurlijkpersoon
    ) {
        assertNationaliteitingeschrevennatuurlijkpersoonAllUpdatablePropertiesEquals(
            expectedNationaliteitingeschrevennatuurlijkpersoon,
            getPersistedNationaliteitingeschrevennatuurlijkpersoon(expectedNationaliteitingeschrevennatuurlijkpersoon)
        );
    }
}
