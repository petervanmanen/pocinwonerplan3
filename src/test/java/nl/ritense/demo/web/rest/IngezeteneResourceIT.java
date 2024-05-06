package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.IngezeteneAsserts.*;
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
import nl.ritense.demo.domain.Ingezetene;
import nl.ritense.demo.repository.IngezeteneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link IngezeteneResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IngezeteneResourceIT {

    private static final Boolean DEFAULT_AANDUIDINGEUROPEESKIESRECHT = false;
    private static final Boolean UPDATED_AANDUIDINGEUROPEESKIESRECHT = true;

    private static final Boolean DEFAULT_AANDUIDINGUITGESLOTENKIESRECHT = false;
    private static final Boolean UPDATED_AANDUIDINGUITGESLOTENKIESRECHT = true;

    private static final String DEFAULT_DATUMVERKRIJGINGVERBLIJFSTITEL = "AAAAAAAAAA";
    private static final String UPDATED_DATUMVERKRIJGINGVERBLIJFSTITEL = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMVERLIESVERBLIJFSTITEL = "AAAAAAAAAA";
    private static final String UPDATED_DATUMVERLIESVERBLIJFSTITEL = "BBBBBBBBBB";

    private static final String DEFAULT_INDICATIEBLOKKERING = "AAAAAAAAAA";
    private static final String UPDATED_INDICATIEBLOKKERING = "BBBBBBBBBB";

    private static final String DEFAULT_INDICATIECURATELEREGISTER = "AAAAAAAAAA";
    private static final String UPDATED_INDICATIECURATELEREGISTER = "BBBBBBBBBB";

    private static final String DEFAULT_INDICATIEGEZAGMINDERJARIGE = "AAAAAAAAAA";
    private static final String UPDATED_INDICATIEGEZAGMINDERJARIGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ingezetenes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private IngezeteneRepository ingezeteneRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIngezeteneMockMvc;

    private Ingezetene ingezetene;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ingezetene createEntity(EntityManager em) {
        Ingezetene ingezetene = new Ingezetene()
            .aanduidingeuropeeskiesrecht(DEFAULT_AANDUIDINGEUROPEESKIESRECHT)
            .aanduidinguitgeslotenkiesrecht(DEFAULT_AANDUIDINGUITGESLOTENKIESRECHT)
            .datumverkrijgingverblijfstitel(DEFAULT_DATUMVERKRIJGINGVERBLIJFSTITEL)
            .datumverliesverblijfstitel(DEFAULT_DATUMVERLIESVERBLIJFSTITEL)
            .indicatieblokkering(DEFAULT_INDICATIEBLOKKERING)
            .indicatiecurateleregister(DEFAULT_INDICATIECURATELEREGISTER)
            .indicatiegezagminderjarige(DEFAULT_INDICATIEGEZAGMINDERJARIGE);
        return ingezetene;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ingezetene createUpdatedEntity(EntityManager em) {
        Ingezetene ingezetene = new Ingezetene()
            .aanduidingeuropeeskiesrecht(UPDATED_AANDUIDINGEUROPEESKIESRECHT)
            .aanduidinguitgeslotenkiesrecht(UPDATED_AANDUIDINGUITGESLOTENKIESRECHT)
            .datumverkrijgingverblijfstitel(UPDATED_DATUMVERKRIJGINGVERBLIJFSTITEL)
            .datumverliesverblijfstitel(UPDATED_DATUMVERLIESVERBLIJFSTITEL)
            .indicatieblokkering(UPDATED_INDICATIEBLOKKERING)
            .indicatiecurateleregister(UPDATED_INDICATIECURATELEREGISTER)
            .indicatiegezagminderjarige(UPDATED_INDICATIEGEZAGMINDERJARIGE);
        return ingezetene;
    }

    @BeforeEach
    public void initTest() {
        ingezetene = createEntity(em);
    }

    @Test
    @Transactional
    void createIngezetene() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Ingezetene
        var returnedIngezetene = om.readValue(
            restIngezeteneMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ingezetene)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Ingezetene.class
        );

        // Validate the Ingezetene in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertIngezeteneUpdatableFieldsEquals(returnedIngezetene, getPersistedIngezetene(returnedIngezetene));
    }

    @Test
    @Transactional
    void createIngezeteneWithExistingId() throws Exception {
        // Create the Ingezetene with an existing ID
        ingezetene.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIngezeteneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ingezetene)))
            .andExpect(status().isBadRequest());

        // Validate the Ingezetene in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIngezetenes() throws Exception {
        // Initialize the database
        ingezeteneRepository.saveAndFlush(ingezetene);

        // Get all the ingezeteneList
        restIngezeteneMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingezetene.getId().intValue())))
            .andExpect(jsonPath("$.[*].aanduidingeuropeeskiesrecht").value(hasItem(DEFAULT_AANDUIDINGEUROPEESKIESRECHT.booleanValue())))
            .andExpect(
                jsonPath("$.[*].aanduidinguitgeslotenkiesrecht").value(hasItem(DEFAULT_AANDUIDINGUITGESLOTENKIESRECHT.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].datumverkrijgingverblijfstitel").value(hasItem(DEFAULT_DATUMVERKRIJGINGVERBLIJFSTITEL)))
            .andExpect(jsonPath("$.[*].datumverliesverblijfstitel").value(hasItem(DEFAULT_DATUMVERLIESVERBLIJFSTITEL)))
            .andExpect(jsonPath("$.[*].indicatieblokkering").value(hasItem(DEFAULT_INDICATIEBLOKKERING)))
            .andExpect(jsonPath("$.[*].indicatiecurateleregister").value(hasItem(DEFAULT_INDICATIECURATELEREGISTER)))
            .andExpect(jsonPath("$.[*].indicatiegezagminderjarige").value(hasItem(DEFAULT_INDICATIEGEZAGMINDERJARIGE)));
    }

    @Test
    @Transactional
    void getIngezetene() throws Exception {
        // Initialize the database
        ingezeteneRepository.saveAndFlush(ingezetene);

        // Get the ingezetene
        restIngezeteneMockMvc
            .perform(get(ENTITY_API_URL_ID, ingezetene.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ingezetene.getId().intValue()))
            .andExpect(jsonPath("$.aanduidingeuropeeskiesrecht").value(DEFAULT_AANDUIDINGEUROPEESKIESRECHT.booleanValue()))
            .andExpect(jsonPath("$.aanduidinguitgeslotenkiesrecht").value(DEFAULT_AANDUIDINGUITGESLOTENKIESRECHT.booleanValue()))
            .andExpect(jsonPath("$.datumverkrijgingverblijfstitel").value(DEFAULT_DATUMVERKRIJGINGVERBLIJFSTITEL))
            .andExpect(jsonPath("$.datumverliesverblijfstitel").value(DEFAULT_DATUMVERLIESVERBLIJFSTITEL))
            .andExpect(jsonPath("$.indicatieblokkering").value(DEFAULT_INDICATIEBLOKKERING))
            .andExpect(jsonPath("$.indicatiecurateleregister").value(DEFAULT_INDICATIECURATELEREGISTER))
            .andExpect(jsonPath("$.indicatiegezagminderjarige").value(DEFAULT_INDICATIEGEZAGMINDERJARIGE));
    }

    @Test
    @Transactional
    void getNonExistingIngezetene() throws Exception {
        // Get the ingezetene
        restIngezeteneMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIngezetene() throws Exception {
        // Initialize the database
        ingezeteneRepository.saveAndFlush(ingezetene);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ingezetene
        Ingezetene updatedIngezetene = ingezeteneRepository.findById(ingezetene.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedIngezetene are not directly saved in db
        em.detach(updatedIngezetene);
        updatedIngezetene
            .aanduidingeuropeeskiesrecht(UPDATED_AANDUIDINGEUROPEESKIESRECHT)
            .aanduidinguitgeslotenkiesrecht(UPDATED_AANDUIDINGUITGESLOTENKIESRECHT)
            .datumverkrijgingverblijfstitel(UPDATED_DATUMVERKRIJGINGVERBLIJFSTITEL)
            .datumverliesverblijfstitel(UPDATED_DATUMVERLIESVERBLIJFSTITEL)
            .indicatieblokkering(UPDATED_INDICATIEBLOKKERING)
            .indicatiecurateleregister(UPDATED_INDICATIECURATELEREGISTER)
            .indicatiegezagminderjarige(UPDATED_INDICATIEGEZAGMINDERJARIGE);

        restIngezeteneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIngezetene.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedIngezetene))
            )
            .andExpect(status().isOk());

        // Validate the Ingezetene in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedIngezeteneToMatchAllProperties(updatedIngezetene);
    }

    @Test
    @Transactional
    void putNonExistingIngezetene() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ingezetene.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIngezeteneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ingezetene.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ingezetene))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ingezetene in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIngezetene() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ingezetene.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngezeteneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ingezetene))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ingezetene in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIngezetene() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ingezetene.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngezeteneMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ingezetene)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ingezetene in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIngezeteneWithPatch() throws Exception {
        // Initialize the database
        ingezeteneRepository.saveAndFlush(ingezetene);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ingezetene using partial update
        Ingezetene partialUpdatedIngezetene = new Ingezetene();
        partialUpdatedIngezetene.setId(ingezetene.getId());

        partialUpdatedIngezetene
            .aanduidingeuropeeskiesrecht(UPDATED_AANDUIDINGEUROPEESKIESRECHT)
            .aanduidinguitgeslotenkiesrecht(UPDATED_AANDUIDINGUITGESLOTENKIESRECHT)
            .datumverkrijgingverblijfstitel(UPDATED_DATUMVERKRIJGINGVERBLIJFSTITEL)
            .datumverliesverblijfstitel(UPDATED_DATUMVERLIESVERBLIJFSTITEL)
            .indicatieblokkering(UPDATED_INDICATIEBLOKKERING)
            .indicatiecurateleregister(UPDATED_INDICATIECURATELEREGISTER);

        restIngezeteneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIngezetene.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIngezetene))
            )
            .andExpect(status().isOk());

        // Validate the Ingezetene in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIngezeteneUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedIngezetene, ingezetene),
            getPersistedIngezetene(ingezetene)
        );
    }

    @Test
    @Transactional
    void fullUpdateIngezeteneWithPatch() throws Exception {
        // Initialize the database
        ingezeteneRepository.saveAndFlush(ingezetene);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ingezetene using partial update
        Ingezetene partialUpdatedIngezetene = new Ingezetene();
        partialUpdatedIngezetene.setId(ingezetene.getId());

        partialUpdatedIngezetene
            .aanduidingeuropeeskiesrecht(UPDATED_AANDUIDINGEUROPEESKIESRECHT)
            .aanduidinguitgeslotenkiesrecht(UPDATED_AANDUIDINGUITGESLOTENKIESRECHT)
            .datumverkrijgingverblijfstitel(UPDATED_DATUMVERKRIJGINGVERBLIJFSTITEL)
            .datumverliesverblijfstitel(UPDATED_DATUMVERLIESVERBLIJFSTITEL)
            .indicatieblokkering(UPDATED_INDICATIEBLOKKERING)
            .indicatiecurateleregister(UPDATED_INDICATIECURATELEREGISTER)
            .indicatiegezagminderjarige(UPDATED_INDICATIEGEZAGMINDERJARIGE);

        restIngezeteneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIngezetene.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIngezetene))
            )
            .andExpect(status().isOk());

        // Validate the Ingezetene in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIngezeteneUpdatableFieldsEquals(partialUpdatedIngezetene, getPersistedIngezetene(partialUpdatedIngezetene));
    }

    @Test
    @Transactional
    void patchNonExistingIngezetene() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ingezetene.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIngezeteneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ingezetene.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ingezetene))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ingezetene in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIngezetene() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ingezetene.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngezeteneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ingezetene))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ingezetene in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIngezetene() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ingezetene.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngezeteneMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ingezetene)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ingezetene in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIngezetene() throws Exception {
        // Initialize the database
        ingezeteneRepository.saveAndFlush(ingezetene);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ingezetene
        restIngezeteneMockMvc
            .perform(delete(ENTITY_API_URL_ID, ingezetene.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ingezeteneRepository.count();
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

    protected Ingezetene getPersistedIngezetene(Ingezetene ingezetene) {
        return ingezeteneRepository.findById(ingezetene.getId()).orElseThrow();
    }

    protected void assertPersistedIngezeteneToMatchAllProperties(Ingezetene expectedIngezetene) {
        assertIngezeteneAllPropertiesEquals(expectedIngezetene, getPersistedIngezetene(expectedIngezetene));
    }

    protected void assertPersistedIngezeteneToMatchUpdatableProperties(Ingezetene expectedIngezetene) {
        assertIngezeteneAllUpdatablePropertiesEquals(expectedIngezetene, getPersistedIngezetene(expectedIngezetene));
    }
}
