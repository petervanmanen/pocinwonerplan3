package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VerblijfsrechtingeschrevennatuurlijkpersoonAsserts.*;
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
import nl.ritense.demo.domain.Verblijfsrechtingeschrevennatuurlijkpersoon;
import nl.ritense.demo.repository.VerblijfsrechtingeschrevennatuurlijkpersoonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VerblijfsrechtingeschrevennatuurlijkpersoonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VerblijfsrechtingeschrevennatuurlijkpersoonResourceIT {

    private static final String DEFAULT_AANDUIDINGVERBLIJFSRECHT = "AAAAAAAAAA";
    private static final String UPDATED_AANDUIDINGVERBLIJFSRECHT = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMAANVANGVERBLIJFSRECHT = "AAAAAAAAAA";
    private static final String UPDATED_DATUMAANVANGVERBLIJFSRECHT = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMMEDEDELINGVERBLIJFSRECHT = "AAAAAAAAAA";
    private static final String UPDATED_DATUMMEDEDELINGVERBLIJFSRECHT = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMVOORZIENEINDEVERBLIJFSRECHT = "AAAAAAAAAA";
    private static final String UPDATED_DATUMVOORZIENEINDEVERBLIJFSRECHT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/verblijfsrechtingeschrevennatuurlijkpersoons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VerblijfsrechtingeschrevennatuurlijkpersoonRepository verblijfsrechtingeschrevennatuurlijkpersoonRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVerblijfsrechtingeschrevennatuurlijkpersoonMockMvc;

    private Verblijfsrechtingeschrevennatuurlijkpersoon verblijfsrechtingeschrevennatuurlijkpersoon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verblijfsrechtingeschrevennatuurlijkpersoon createEntity(EntityManager em) {
        Verblijfsrechtingeschrevennatuurlijkpersoon verblijfsrechtingeschrevennatuurlijkpersoon =
            new Verblijfsrechtingeschrevennatuurlijkpersoon()
                .aanduidingverblijfsrecht(DEFAULT_AANDUIDINGVERBLIJFSRECHT)
                .datumaanvangverblijfsrecht(DEFAULT_DATUMAANVANGVERBLIJFSRECHT)
                .datummededelingverblijfsrecht(DEFAULT_DATUMMEDEDELINGVERBLIJFSRECHT)
                .datumvoorzieneindeverblijfsrecht(DEFAULT_DATUMVOORZIENEINDEVERBLIJFSRECHT);
        return verblijfsrechtingeschrevennatuurlijkpersoon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verblijfsrechtingeschrevennatuurlijkpersoon createUpdatedEntity(EntityManager em) {
        Verblijfsrechtingeschrevennatuurlijkpersoon verblijfsrechtingeschrevennatuurlijkpersoon =
            new Verblijfsrechtingeschrevennatuurlijkpersoon()
                .aanduidingverblijfsrecht(UPDATED_AANDUIDINGVERBLIJFSRECHT)
                .datumaanvangverblijfsrecht(UPDATED_DATUMAANVANGVERBLIJFSRECHT)
                .datummededelingverblijfsrecht(UPDATED_DATUMMEDEDELINGVERBLIJFSRECHT)
                .datumvoorzieneindeverblijfsrecht(UPDATED_DATUMVOORZIENEINDEVERBLIJFSRECHT);
        return verblijfsrechtingeschrevennatuurlijkpersoon;
    }

    @BeforeEach
    public void initTest() {
        verblijfsrechtingeschrevennatuurlijkpersoon = createEntity(em);
    }

    @Test
    @Transactional
    void createVerblijfsrechtingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Verblijfsrechtingeschrevennatuurlijkpersoon
        var returnedVerblijfsrechtingeschrevennatuurlijkpersoon = om.readValue(
            restVerblijfsrechtingeschrevennatuurlijkpersoonMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(verblijfsrechtingeschrevennatuurlijkpersoon))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Verblijfsrechtingeschrevennatuurlijkpersoon.class
        );

        // Validate the Verblijfsrechtingeschrevennatuurlijkpersoon in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVerblijfsrechtingeschrevennatuurlijkpersoonUpdatableFieldsEquals(
            returnedVerblijfsrechtingeschrevennatuurlijkpersoon,
            getPersistedVerblijfsrechtingeschrevennatuurlijkpersoon(returnedVerblijfsrechtingeschrevennatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void createVerblijfsrechtingeschrevennatuurlijkpersoonWithExistingId() throws Exception {
        // Create the Verblijfsrechtingeschrevennatuurlijkpersoon with an existing ID
        verblijfsrechtingeschrevennatuurlijkpersoon.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerblijfsrechtingeschrevennatuurlijkpersoonMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verblijfsrechtingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfsrechtingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVerblijfsrechtingeschrevennatuurlijkpersoons() throws Exception {
        // Initialize the database
        verblijfsrechtingeschrevennatuurlijkpersoonRepository.saveAndFlush(verblijfsrechtingeschrevennatuurlijkpersoon);

        // Get all the verblijfsrechtingeschrevennatuurlijkpersoonList
        restVerblijfsrechtingeschrevennatuurlijkpersoonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verblijfsrechtingeschrevennatuurlijkpersoon.getId().intValue())))
            .andExpect(jsonPath("$.[*].aanduidingverblijfsrecht").value(hasItem(DEFAULT_AANDUIDINGVERBLIJFSRECHT)))
            .andExpect(jsonPath("$.[*].datumaanvangverblijfsrecht").value(hasItem(DEFAULT_DATUMAANVANGVERBLIJFSRECHT)))
            .andExpect(jsonPath("$.[*].datummededelingverblijfsrecht").value(hasItem(DEFAULT_DATUMMEDEDELINGVERBLIJFSRECHT)))
            .andExpect(jsonPath("$.[*].datumvoorzieneindeverblijfsrecht").value(hasItem(DEFAULT_DATUMVOORZIENEINDEVERBLIJFSRECHT)));
    }

    @Test
    @Transactional
    void getVerblijfsrechtingeschrevennatuurlijkpersoon() throws Exception {
        // Initialize the database
        verblijfsrechtingeschrevennatuurlijkpersoonRepository.saveAndFlush(verblijfsrechtingeschrevennatuurlijkpersoon);

        // Get the verblijfsrechtingeschrevennatuurlijkpersoon
        restVerblijfsrechtingeschrevennatuurlijkpersoonMockMvc
            .perform(get(ENTITY_API_URL_ID, verblijfsrechtingeschrevennatuurlijkpersoon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(verblijfsrechtingeschrevennatuurlijkpersoon.getId().intValue()))
            .andExpect(jsonPath("$.aanduidingverblijfsrecht").value(DEFAULT_AANDUIDINGVERBLIJFSRECHT))
            .andExpect(jsonPath("$.datumaanvangverblijfsrecht").value(DEFAULT_DATUMAANVANGVERBLIJFSRECHT))
            .andExpect(jsonPath("$.datummededelingverblijfsrecht").value(DEFAULT_DATUMMEDEDELINGVERBLIJFSRECHT))
            .andExpect(jsonPath("$.datumvoorzieneindeverblijfsrecht").value(DEFAULT_DATUMVOORZIENEINDEVERBLIJFSRECHT));
    }

    @Test
    @Transactional
    void getNonExistingVerblijfsrechtingeschrevennatuurlijkpersoon() throws Exception {
        // Get the verblijfsrechtingeschrevennatuurlijkpersoon
        restVerblijfsrechtingeschrevennatuurlijkpersoonMockMvc
            .perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVerblijfsrechtingeschrevennatuurlijkpersoon() throws Exception {
        // Initialize the database
        verblijfsrechtingeschrevennatuurlijkpersoonRepository.saveAndFlush(verblijfsrechtingeschrevennatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verblijfsrechtingeschrevennatuurlijkpersoon
        Verblijfsrechtingeschrevennatuurlijkpersoon updatedVerblijfsrechtingeschrevennatuurlijkpersoon =
            verblijfsrechtingeschrevennatuurlijkpersoonRepository
                .findById(verblijfsrechtingeschrevennatuurlijkpersoon.getId())
                .orElseThrow();
        // Disconnect from session so that the updates on updatedVerblijfsrechtingeschrevennatuurlijkpersoon are not directly saved in db
        em.detach(updatedVerblijfsrechtingeschrevennatuurlijkpersoon);
        updatedVerblijfsrechtingeschrevennatuurlijkpersoon
            .aanduidingverblijfsrecht(UPDATED_AANDUIDINGVERBLIJFSRECHT)
            .datumaanvangverblijfsrecht(UPDATED_DATUMAANVANGVERBLIJFSRECHT)
            .datummededelingverblijfsrecht(UPDATED_DATUMMEDEDELINGVERBLIJFSRECHT)
            .datumvoorzieneindeverblijfsrecht(UPDATED_DATUMVOORZIENEINDEVERBLIJFSRECHT);

        restVerblijfsrechtingeschrevennatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVerblijfsrechtingeschrevennatuurlijkpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVerblijfsrechtingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Verblijfsrechtingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVerblijfsrechtingeschrevennatuurlijkpersoonToMatchAllProperties(updatedVerblijfsrechtingeschrevennatuurlijkpersoon);
    }

    @Test
    @Transactional
    void putNonExistingVerblijfsrechtingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfsrechtingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerblijfsrechtingeschrevennatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, verblijfsrechtingeschrevennatuurlijkpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verblijfsrechtingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfsrechtingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVerblijfsrechtingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfsrechtingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerblijfsrechtingeschrevennatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verblijfsrechtingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfsrechtingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVerblijfsrechtingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfsrechtingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerblijfsrechtingeschrevennatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verblijfsrechtingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verblijfsrechtingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVerblijfsrechtingeschrevennatuurlijkpersoonWithPatch() throws Exception {
        // Initialize the database
        verblijfsrechtingeschrevennatuurlijkpersoonRepository.saveAndFlush(verblijfsrechtingeschrevennatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verblijfsrechtingeschrevennatuurlijkpersoon using partial update
        Verblijfsrechtingeschrevennatuurlijkpersoon partialUpdatedVerblijfsrechtingeschrevennatuurlijkpersoon =
            new Verblijfsrechtingeschrevennatuurlijkpersoon();
        partialUpdatedVerblijfsrechtingeschrevennatuurlijkpersoon.setId(verblijfsrechtingeschrevennatuurlijkpersoon.getId());

        partialUpdatedVerblijfsrechtingeschrevennatuurlijkpersoon
            .aanduidingverblijfsrecht(UPDATED_AANDUIDINGVERBLIJFSRECHT)
            .datumaanvangverblijfsrecht(UPDATED_DATUMAANVANGVERBLIJFSRECHT)
            .datummededelingverblijfsrecht(UPDATED_DATUMMEDEDELINGVERBLIJFSRECHT);

        restVerblijfsrechtingeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerblijfsrechtingeschrevennatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerblijfsrechtingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Verblijfsrechtingeschrevennatuurlijkpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerblijfsrechtingeschrevennatuurlijkpersoonUpdatableFieldsEquals(
            createUpdateProxyForBean(
                partialUpdatedVerblijfsrechtingeschrevennatuurlijkpersoon,
                verblijfsrechtingeschrevennatuurlijkpersoon
            ),
            getPersistedVerblijfsrechtingeschrevennatuurlijkpersoon(verblijfsrechtingeschrevennatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void fullUpdateVerblijfsrechtingeschrevennatuurlijkpersoonWithPatch() throws Exception {
        // Initialize the database
        verblijfsrechtingeschrevennatuurlijkpersoonRepository.saveAndFlush(verblijfsrechtingeschrevennatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verblijfsrechtingeschrevennatuurlijkpersoon using partial update
        Verblijfsrechtingeschrevennatuurlijkpersoon partialUpdatedVerblijfsrechtingeschrevennatuurlijkpersoon =
            new Verblijfsrechtingeschrevennatuurlijkpersoon();
        partialUpdatedVerblijfsrechtingeschrevennatuurlijkpersoon.setId(verblijfsrechtingeschrevennatuurlijkpersoon.getId());

        partialUpdatedVerblijfsrechtingeschrevennatuurlijkpersoon
            .aanduidingverblijfsrecht(UPDATED_AANDUIDINGVERBLIJFSRECHT)
            .datumaanvangverblijfsrecht(UPDATED_DATUMAANVANGVERBLIJFSRECHT)
            .datummededelingverblijfsrecht(UPDATED_DATUMMEDEDELINGVERBLIJFSRECHT)
            .datumvoorzieneindeverblijfsrecht(UPDATED_DATUMVOORZIENEINDEVERBLIJFSRECHT);

        restVerblijfsrechtingeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerblijfsrechtingeschrevennatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerblijfsrechtingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Verblijfsrechtingeschrevennatuurlijkpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerblijfsrechtingeschrevennatuurlijkpersoonUpdatableFieldsEquals(
            partialUpdatedVerblijfsrechtingeschrevennatuurlijkpersoon,
            getPersistedVerblijfsrechtingeschrevennatuurlijkpersoon(partialUpdatedVerblijfsrechtingeschrevennatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void patchNonExistingVerblijfsrechtingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfsrechtingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerblijfsrechtingeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, verblijfsrechtingeschrevennatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verblijfsrechtingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfsrechtingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVerblijfsrechtingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfsrechtingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerblijfsrechtingeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verblijfsrechtingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfsrechtingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVerblijfsrechtingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfsrechtingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerblijfsrechtingeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verblijfsrechtingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verblijfsrechtingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVerblijfsrechtingeschrevennatuurlijkpersoon() throws Exception {
        // Initialize the database
        verblijfsrechtingeschrevennatuurlijkpersoonRepository.saveAndFlush(verblijfsrechtingeschrevennatuurlijkpersoon);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the verblijfsrechtingeschrevennatuurlijkpersoon
        restVerblijfsrechtingeschrevennatuurlijkpersoonMockMvc
            .perform(delete(ENTITY_API_URL_ID, verblijfsrechtingeschrevennatuurlijkpersoon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return verblijfsrechtingeschrevennatuurlijkpersoonRepository.count();
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

    protected Verblijfsrechtingeschrevennatuurlijkpersoon getPersistedVerblijfsrechtingeschrevennatuurlijkpersoon(
        Verblijfsrechtingeschrevennatuurlijkpersoon verblijfsrechtingeschrevennatuurlijkpersoon
    ) {
        return verblijfsrechtingeschrevennatuurlijkpersoonRepository
            .findById(verblijfsrechtingeschrevennatuurlijkpersoon.getId())
            .orElseThrow();
    }

    protected void assertPersistedVerblijfsrechtingeschrevennatuurlijkpersoonToMatchAllProperties(
        Verblijfsrechtingeschrevennatuurlijkpersoon expectedVerblijfsrechtingeschrevennatuurlijkpersoon
    ) {
        assertVerblijfsrechtingeschrevennatuurlijkpersoonAllPropertiesEquals(
            expectedVerblijfsrechtingeschrevennatuurlijkpersoon,
            getPersistedVerblijfsrechtingeschrevennatuurlijkpersoon(expectedVerblijfsrechtingeschrevennatuurlijkpersoon)
        );
    }

    protected void assertPersistedVerblijfsrechtingeschrevennatuurlijkpersoonToMatchUpdatableProperties(
        Verblijfsrechtingeschrevennatuurlijkpersoon expectedVerblijfsrechtingeschrevennatuurlijkpersoon
    ) {
        assertVerblijfsrechtingeschrevennatuurlijkpersoonAllUpdatablePropertiesEquals(
            expectedVerblijfsrechtingeschrevennatuurlijkpersoon,
            getPersistedVerblijfsrechtingeschrevennatuurlijkpersoon(expectedVerblijfsrechtingeschrevennatuurlijkpersoon)
        );
    }
}
