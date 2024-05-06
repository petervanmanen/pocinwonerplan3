package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OorspronkelijkefunctieAsserts.*;
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
import nl.ritense.demo.domain.Oorspronkelijkefunctie;
import nl.ritense.demo.repository.OorspronkelijkefunctieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OorspronkelijkefunctieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OorspronkelijkefunctieResourceIT {

    private static final String DEFAULT_FUNCTIE = "AAAAAAAAAA";
    private static final String UPDATED_FUNCTIE = "BBBBBBBBBB";

    private static final String DEFAULT_FUNCTIESOORT = "AAAAAAAAAA";
    private static final String UPDATED_FUNCTIESOORT = "BBBBBBBBBB";

    private static final String DEFAULT_HOOFDCATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_HOOFDCATEGORIE = "BBBBBBBBBB";

    private static final String DEFAULT_HOOFDFUNCTIE = "AAAAAAAAAA";
    private static final String UPDATED_HOOFDFUNCTIE = "BBBBBBBBBB";

    private static final String DEFAULT_SUBCATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_SUBCATEGORIE = "BBBBBBBBBB";

    private static final String DEFAULT_TOELICHTING = "AAAAAAAAAA";
    private static final String UPDATED_TOELICHTING = "BBBBBBBBBB";

    private static final String DEFAULT_VERBIJZONDERING = "AAAAAAAAAA";
    private static final String UPDATED_VERBIJZONDERING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/oorspronkelijkefuncties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OorspronkelijkefunctieRepository oorspronkelijkefunctieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOorspronkelijkefunctieMockMvc;

    private Oorspronkelijkefunctie oorspronkelijkefunctie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Oorspronkelijkefunctie createEntity(EntityManager em) {
        Oorspronkelijkefunctie oorspronkelijkefunctie = new Oorspronkelijkefunctie()
            .functie(DEFAULT_FUNCTIE)
            .functiesoort(DEFAULT_FUNCTIESOORT)
            .hoofdcategorie(DEFAULT_HOOFDCATEGORIE)
            .hoofdfunctie(DEFAULT_HOOFDFUNCTIE)
            .subcategorie(DEFAULT_SUBCATEGORIE)
            .toelichting(DEFAULT_TOELICHTING)
            .verbijzondering(DEFAULT_VERBIJZONDERING);
        return oorspronkelijkefunctie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Oorspronkelijkefunctie createUpdatedEntity(EntityManager em) {
        Oorspronkelijkefunctie oorspronkelijkefunctie = new Oorspronkelijkefunctie()
            .functie(UPDATED_FUNCTIE)
            .functiesoort(UPDATED_FUNCTIESOORT)
            .hoofdcategorie(UPDATED_HOOFDCATEGORIE)
            .hoofdfunctie(UPDATED_HOOFDFUNCTIE)
            .subcategorie(UPDATED_SUBCATEGORIE)
            .toelichting(UPDATED_TOELICHTING)
            .verbijzondering(UPDATED_VERBIJZONDERING);
        return oorspronkelijkefunctie;
    }

    @BeforeEach
    public void initTest() {
        oorspronkelijkefunctie = createEntity(em);
    }

    @Test
    @Transactional
    void createOorspronkelijkefunctie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Oorspronkelijkefunctie
        var returnedOorspronkelijkefunctie = om.readValue(
            restOorspronkelijkefunctieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(oorspronkelijkefunctie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Oorspronkelijkefunctie.class
        );

        // Validate the Oorspronkelijkefunctie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOorspronkelijkefunctieUpdatableFieldsEquals(
            returnedOorspronkelijkefunctie,
            getPersistedOorspronkelijkefunctie(returnedOorspronkelijkefunctie)
        );
    }

    @Test
    @Transactional
    void createOorspronkelijkefunctieWithExistingId() throws Exception {
        // Create the Oorspronkelijkefunctie with an existing ID
        oorspronkelijkefunctie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOorspronkelijkefunctieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(oorspronkelijkefunctie)))
            .andExpect(status().isBadRequest());

        // Validate the Oorspronkelijkefunctie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOorspronkelijkefuncties() throws Exception {
        // Initialize the database
        oorspronkelijkefunctieRepository.saveAndFlush(oorspronkelijkefunctie);

        // Get all the oorspronkelijkefunctieList
        restOorspronkelijkefunctieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(oorspronkelijkefunctie.getId().intValue())))
            .andExpect(jsonPath("$.[*].functie").value(hasItem(DEFAULT_FUNCTIE)))
            .andExpect(jsonPath("$.[*].functiesoort").value(hasItem(DEFAULT_FUNCTIESOORT)))
            .andExpect(jsonPath("$.[*].hoofdcategorie").value(hasItem(DEFAULT_HOOFDCATEGORIE)))
            .andExpect(jsonPath("$.[*].hoofdfunctie").value(hasItem(DEFAULT_HOOFDFUNCTIE)))
            .andExpect(jsonPath("$.[*].subcategorie").value(hasItem(DEFAULT_SUBCATEGORIE)))
            .andExpect(jsonPath("$.[*].toelichting").value(hasItem(DEFAULT_TOELICHTING)))
            .andExpect(jsonPath("$.[*].verbijzondering").value(hasItem(DEFAULT_VERBIJZONDERING)));
    }

    @Test
    @Transactional
    void getOorspronkelijkefunctie() throws Exception {
        // Initialize the database
        oorspronkelijkefunctieRepository.saveAndFlush(oorspronkelijkefunctie);

        // Get the oorspronkelijkefunctie
        restOorspronkelijkefunctieMockMvc
            .perform(get(ENTITY_API_URL_ID, oorspronkelijkefunctie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(oorspronkelijkefunctie.getId().intValue()))
            .andExpect(jsonPath("$.functie").value(DEFAULT_FUNCTIE))
            .andExpect(jsonPath("$.functiesoort").value(DEFAULT_FUNCTIESOORT))
            .andExpect(jsonPath("$.hoofdcategorie").value(DEFAULT_HOOFDCATEGORIE))
            .andExpect(jsonPath("$.hoofdfunctie").value(DEFAULT_HOOFDFUNCTIE))
            .andExpect(jsonPath("$.subcategorie").value(DEFAULT_SUBCATEGORIE))
            .andExpect(jsonPath("$.toelichting").value(DEFAULT_TOELICHTING))
            .andExpect(jsonPath("$.verbijzondering").value(DEFAULT_VERBIJZONDERING));
    }

    @Test
    @Transactional
    void getNonExistingOorspronkelijkefunctie() throws Exception {
        // Get the oorspronkelijkefunctie
        restOorspronkelijkefunctieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOorspronkelijkefunctie() throws Exception {
        // Initialize the database
        oorspronkelijkefunctieRepository.saveAndFlush(oorspronkelijkefunctie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the oorspronkelijkefunctie
        Oorspronkelijkefunctie updatedOorspronkelijkefunctie = oorspronkelijkefunctieRepository
            .findById(oorspronkelijkefunctie.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedOorspronkelijkefunctie are not directly saved in db
        em.detach(updatedOorspronkelijkefunctie);
        updatedOorspronkelijkefunctie
            .functie(UPDATED_FUNCTIE)
            .functiesoort(UPDATED_FUNCTIESOORT)
            .hoofdcategorie(UPDATED_HOOFDCATEGORIE)
            .hoofdfunctie(UPDATED_HOOFDFUNCTIE)
            .subcategorie(UPDATED_SUBCATEGORIE)
            .toelichting(UPDATED_TOELICHTING)
            .verbijzondering(UPDATED_VERBIJZONDERING);

        restOorspronkelijkefunctieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOorspronkelijkefunctie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOorspronkelijkefunctie))
            )
            .andExpect(status().isOk());

        // Validate the Oorspronkelijkefunctie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOorspronkelijkefunctieToMatchAllProperties(updatedOorspronkelijkefunctie);
    }

    @Test
    @Transactional
    void putNonExistingOorspronkelijkefunctie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        oorspronkelijkefunctie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOorspronkelijkefunctieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, oorspronkelijkefunctie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(oorspronkelijkefunctie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Oorspronkelijkefunctie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOorspronkelijkefunctie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        oorspronkelijkefunctie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOorspronkelijkefunctieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(oorspronkelijkefunctie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Oorspronkelijkefunctie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOorspronkelijkefunctie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        oorspronkelijkefunctie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOorspronkelijkefunctieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(oorspronkelijkefunctie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Oorspronkelijkefunctie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOorspronkelijkefunctieWithPatch() throws Exception {
        // Initialize the database
        oorspronkelijkefunctieRepository.saveAndFlush(oorspronkelijkefunctie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the oorspronkelijkefunctie using partial update
        Oorspronkelijkefunctie partialUpdatedOorspronkelijkefunctie = new Oorspronkelijkefunctie();
        partialUpdatedOorspronkelijkefunctie.setId(oorspronkelijkefunctie.getId());

        partialUpdatedOorspronkelijkefunctie
            .functie(UPDATED_FUNCTIE)
            .hoofdcategorie(UPDATED_HOOFDCATEGORIE)
            .toelichting(UPDATED_TOELICHTING);

        restOorspronkelijkefunctieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOorspronkelijkefunctie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOorspronkelijkefunctie))
            )
            .andExpect(status().isOk());

        // Validate the Oorspronkelijkefunctie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOorspronkelijkefunctieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOorspronkelijkefunctie, oorspronkelijkefunctie),
            getPersistedOorspronkelijkefunctie(oorspronkelijkefunctie)
        );
    }

    @Test
    @Transactional
    void fullUpdateOorspronkelijkefunctieWithPatch() throws Exception {
        // Initialize the database
        oorspronkelijkefunctieRepository.saveAndFlush(oorspronkelijkefunctie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the oorspronkelijkefunctie using partial update
        Oorspronkelijkefunctie partialUpdatedOorspronkelijkefunctie = new Oorspronkelijkefunctie();
        partialUpdatedOorspronkelijkefunctie.setId(oorspronkelijkefunctie.getId());

        partialUpdatedOorspronkelijkefunctie
            .functie(UPDATED_FUNCTIE)
            .functiesoort(UPDATED_FUNCTIESOORT)
            .hoofdcategorie(UPDATED_HOOFDCATEGORIE)
            .hoofdfunctie(UPDATED_HOOFDFUNCTIE)
            .subcategorie(UPDATED_SUBCATEGORIE)
            .toelichting(UPDATED_TOELICHTING)
            .verbijzondering(UPDATED_VERBIJZONDERING);

        restOorspronkelijkefunctieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOorspronkelijkefunctie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOorspronkelijkefunctie))
            )
            .andExpect(status().isOk());

        // Validate the Oorspronkelijkefunctie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOorspronkelijkefunctieUpdatableFieldsEquals(
            partialUpdatedOorspronkelijkefunctie,
            getPersistedOorspronkelijkefunctie(partialUpdatedOorspronkelijkefunctie)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOorspronkelijkefunctie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        oorspronkelijkefunctie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOorspronkelijkefunctieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, oorspronkelijkefunctie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(oorspronkelijkefunctie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Oorspronkelijkefunctie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOorspronkelijkefunctie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        oorspronkelijkefunctie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOorspronkelijkefunctieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(oorspronkelijkefunctie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Oorspronkelijkefunctie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOorspronkelijkefunctie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        oorspronkelijkefunctie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOorspronkelijkefunctieMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(oorspronkelijkefunctie))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Oorspronkelijkefunctie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOorspronkelijkefunctie() throws Exception {
        // Initialize the database
        oorspronkelijkefunctieRepository.saveAndFlush(oorspronkelijkefunctie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the oorspronkelijkefunctie
        restOorspronkelijkefunctieMockMvc
            .perform(delete(ENTITY_API_URL_ID, oorspronkelijkefunctie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return oorspronkelijkefunctieRepository.count();
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

    protected Oorspronkelijkefunctie getPersistedOorspronkelijkefunctie(Oorspronkelijkefunctie oorspronkelijkefunctie) {
        return oorspronkelijkefunctieRepository.findById(oorspronkelijkefunctie.getId()).orElseThrow();
    }

    protected void assertPersistedOorspronkelijkefunctieToMatchAllProperties(Oorspronkelijkefunctie expectedOorspronkelijkefunctie) {
        assertOorspronkelijkefunctieAllPropertiesEquals(
            expectedOorspronkelijkefunctie,
            getPersistedOorspronkelijkefunctie(expectedOorspronkelijkefunctie)
        );
    }

    protected void assertPersistedOorspronkelijkefunctieToMatchUpdatableProperties(Oorspronkelijkefunctie expectedOorspronkelijkefunctie) {
        assertOorspronkelijkefunctieAllUpdatablePropertiesEquals(
            expectedOorspronkelijkefunctie,
            getPersistedOorspronkelijkefunctie(expectedOorspronkelijkefunctie)
        );
    }
}
