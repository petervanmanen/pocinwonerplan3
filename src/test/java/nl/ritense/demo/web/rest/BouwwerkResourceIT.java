package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BouwwerkAsserts.*;
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
import nl.ritense.demo.domain.Bouwwerk;
import nl.ritense.demo.repository.BouwwerkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BouwwerkResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BouwwerkResourceIT {

    private static final String DEFAULT_AANLEGHOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_AANLEGHOOGTE = "BBBBBBBBBB";

    private static final String DEFAULT_BOUWWERKMATERIAAL = "AAAAAAAAAA";
    private static final String UPDATED_BOUWWERKMATERIAAL = "BBBBBBBBBB";

    private static final String DEFAULT_BREEDTE = "AAAAAAAAAA";
    private static final String UPDATED_BREEDTE = "BBBBBBBBBB";

    private static final String DEFAULT_FABRIKANT = "AAAAAAAAAA";
    private static final String UPDATED_FABRIKANT = "BBBBBBBBBB";

    private static final String DEFAULT_HOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_HOOGTE = "BBBBBBBBBB";

    private static final String DEFAULT_JAARONDERHOUDUITGEVOERD = "AAAAAAAAAA";
    private static final String UPDATED_JAARONDERHOUDUITGEVOERD = "BBBBBBBBBB";

    private static final String DEFAULT_LENGTE = "AAAAAAAAAA";
    private static final String UPDATED_LENGTE = "BBBBBBBBBB";

    private static final String DEFAULT_LEVERANCIER = "AAAAAAAAAA";
    private static final String UPDATED_LEVERANCIER = "BBBBBBBBBB";

    private static final String DEFAULT_OPPERVLAKTE = "AAAAAAAAAA";
    private static final String UPDATED_OPPERVLAKTE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEFUNDERING = "AAAAAAAAAA";
    private static final String UPDATED_TYPEFUNDERING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bouwwerks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BouwwerkRepository bouwwerkRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBouwwerkMockMvc;

    private Bouwwerk bouwwerk;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bouwwerk createEntity(EntityManager em) {
        Bouwwerk bouwwerk = new Bouwwerk()
            .aanleghoogte(DEFAULT_AANLEGHOOGTE)
            .bouwwerkmateriaal(DEFAULT_BOUWWERKMATERIAAL)
            .breedte(DEFAULT_BREEDTE)
            .fabrikant(DEFAULT_FABRIKANT)
            .hoogte(DEFAULT_HOOGTE)
            .jaaronderhouduitgevoerd(DEFAULT_JAARONDERHOUDUITGEVOERD)
            .lengte(DEFAULT_LENGTE)
            .leverancier(DEFAULT_LEVERANCIER)
            .oppervlakte(DEFAULT_OPPERVLAKTE)
            .typefundering(DEFAULT_TYPEFUNDERING);
        return bouwwerk;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bouwwerk createUpdatedEntity(EntityManager em) {
        Bouwwerk bouwwerk = new Bouwwerk()
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .bouwwerkmateriaal(UPDATED_BOUWWERKMATERIAAL)
            .breedte(UPDATED_BREEDTE)
            .fabrikant(UPDATED_FABRIKANT)
            .hoogte(UPDATED_HOOGTE)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .typefundering(UPDATED_TYPEFUNDERING);
        return bouwwerk;
    }

    @BeforeEach
    public void initTest() {
        bouwwerk = createEntity(em);
    }

    @Test
    @Transactional
    void createBouwwerk() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Bouwwerk
        var returnedBouwwerk = om.readValue(
            restBouwwerkMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bouwwerk)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Bouwwerk.class
        );

        // Validate the Bouwwerk in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBouwwerkUpdatableFieldsEquals(returnedBouwwerk, getPersistedBouwwerk(returnedBouwwerk));
    }

    @Test
    @Transactional
    void createBouwwerkWithExistingId() throws Exception {
        // Create the Bouwwerk with an existing ID
        bouwwerk.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBouwwerkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bouwwerk)))
            .andExpect(status().isBadRequest());

        // Validate the Bouwwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBouwwerks() throws Exception {
        // Initialize the database
        bouwwerkRepository.saveAndFlush(bouwwerk);

        // Get all the bouwwerkList
        restBouwwerkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bouwwerk.getId().intValue())))
            .andExpect(jsonPath("$.[*].aanleghoogte").value(hasItem(DEFAULT_AANLEGHOOGTE)))
            .andExpect(jsonPath("$.[*].bouwwerkmateriaal").value(hasItem(DEFAULT_BOUWWERKMATERIAAL)))
            .andExpect(jsonPath("$.[*].breedte").value(hasItem(DEFAULT_BREEDTE)))
            .andExpect(jsonPath("$.[*].fabrikant").value(hasItem(DEFAULT_FABRIKANT)))
            .andExpect(jsonPath("$.[*].hoogte").value(hasItem(DEFAULT_HOOGTE)))
            .andExpect(jsonPath("$.[*].jaaronderhouduitgevoerd").value(hasItem(DEFAULT_JAARONDERHOUDUITGEVOERD)))
            .andExpect(jsonPath("$.[*].lengte").value(hasItem(DEFAULT_LENGTE)))
            .andExpect(jsonPath("$.[*].leverancier").value(hasItem(DEFAULT_LEVERANCIER)))
            .andExpect(jsonPath("$.[*].oppervlakte").value(hasItem(DEFAULT_OPPERVLAKTE)))
            .andExpect(jsonPath("$.[*].typefundering").value(hasItem(DEFAULT_TYPEFUNDERING)));
    }

    @Test
    @Transactional
    void getBouwwerk() throws Exception {
        // Initialize the database
        bouwwerkRepository.saveAndFlush(bouwwerk);

        // Get the bouwwerk
        restBouwwerkMockMvc
            .perform(get(ENTITY_API_URL_ID, bouwwerk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bouwwerk.getId().intValue()))
            .andExpect(jsonPath("$.aanleghoogte").value(DEFAULT_AANLEGHOOGTE))
            .andExpect(jsonPath("$.bouwwerkmateriaal").value(DEFAULT_BOUWWERKMATERIAAL))
            .andExpect(jsonPath("$.breedte").value(DEFAULT_BREEDTE))
            .andExpect(jsonPath("$.fabrikant").value(DEFAULT_FABRIKANT))
            .andExpect(jsonPath("$.hoogte").value(DEFAULT_HOOGTE))
            .andExpect(jsonPath("$.jaaronderhouduitgevoerd").value(DEFAULT_JAARONDERHOUDUITGEVOERD))
            .andExpect(jsonPath("$.lengte").value(DEFAULT_LENGTE))
            .andExpect(jsonPath("$.leverancier").value(DEFAULT_LEVERANCIER))
            .andExpect(jsonPath("$.oppervlakte").value(DEFAULT_OPPERVLAKTE))
            .andExpect(jsonPath("$.typefundering").value(DEFAULT_TYPEFUNDERING));
    }

    @Test
    @Transactional
    void getNonExistingBouwwerk() throws Exception {
        // Get the bouwwerk
        restBouwwerkMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBouwwerk() throws Exception {
        // Initialize the database
        bouwwerkRepository.saveAndFlush(bouwwerk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bouwwerk
        Bouwwerk updatedBouwwerk = bouwwerkRepository.findById(bouwwerk.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBouwwerk are not directly saved in db
        em.detach(updatedBouwwerk);
        updatedBouwwerk
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .bouwwerkmateriaal(UPDATED_BOUWWERKMATERIAAL)
            .breedte(UPDATED_BREEDTE)
            .fabrikant(UPDATED_FABRIKANT)
            .hoogte(UPDATED_HOOGTE)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .typefundering(UPDATED_TYPEFUNDERING);

        restBouwwerkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBouwwerk.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBouwwerk))
            )
            .andExpect(status().isOk());

        // Validate the Bouwwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBouwwerkToMatchAllProperties(updatedBouwwerk);
    }

    @Test
    @Transactional
    void putNonExistingBouwwerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwwerk.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBouwwerkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bouwwerk.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bouwwerk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bouwwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBouwwerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwwerk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBouwwerkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bouwwerk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bouwwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBouwwerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwwerk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBouwwerkMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bouwwerk)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bouwwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBouwwerkWithPatch() throws Exception {
        // Initialize the database
        bouwwerkRepository.saveAndFlush(bouwwerk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bouwwerk using partial update
        Bouwwerk partialUpdatedBouwwerk = new Bouwwerk();
        partialUpdatedBouwwerk.setId(bouwwerk.getId());

        partialUpdatedBouwwerk
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .breedte(UPDATED_BREEDTE)
            .hoogte(UPDATED_HOOGTE)
            .oppervlakte(UPDATED_OPPERVLAKTE);

        restBouwwerkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBouwwerk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBouwwerk))
            )
            .andExpect(status().isOk());

        // Validate the Bouwwerk in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBouwwerkUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBouwwerk, bouwwerk), getPersistedBouwwerk(bouwwerk));
    }

    @Test
    @Transactional
    void fullUpdateBouwwerkWithPatch() throws Exception {
        // Initialize the database
        bouwwerkRepository.saveAndFlush(bouwwerk);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bouwwerk using partial update
        Bouwwerk partialUpdatedBouwwerk = new Bouwwerk();
        partialUpdatedBouwwerk.setId(bouwwerk.getId());

        partialUpdatedBouwwerk
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .bouwwerkmateriaal(UPDATED_BOUWWERKMATERIAAL)
            .breedte(UPDATED_BREEDTE)
            .fabrikant(UPDATED_FABRIKANT)
            .hoogte(UPDATED_HOOGTE)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .typefundering(UPDATED_TYPEFUNDERING);

        restBouwwerkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBouwwerk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBouwwerk))
            )
            .andExpect(status().isOk());

        // Validate the Bouwwerk in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBouwwerkUpdatableFieldsEquals(partialUpdatedBouwwerk, getPersistedBouwwerk(partialUpdatedBouwwerk));
    }

    @Test
    @Transactional
    void patchNonExistingBouwwerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwwerk.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBouwwerkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bouwwerk.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bouwwerk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bouwwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBouwwerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwwerk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBouwwerkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bouwwerk))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bouwwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBouwwerk() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bouwwerk.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBouwwerkMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bouwwerk)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bouwwerk in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBouwwerk() throws Exception {
        // Initialize the database
        bouwwerkRepository.saveAndFlush(bouwwerk);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bouwwerk
        restBouwwerkMockMvc
            .perform(delete(ENTITY_API_URL_ID, bouwwerk.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bouwwerkRepository.count();
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

    protected Bouwwerk getPersistedBouwwerk(Bouwwerk bouwwerk) {
        return bouwwerkRepository.findById(bouwwerk.getId()).orElseThrow();
    }

    protected void assertPersistedBouwwerkToMatchAllProperties(Bouwwerk expectedBouwwerk) {
        assertBouwwerkAllPropertiesEquals(expectedBouwwerk, getPersistedBouwwerk(expectedBouwwerk));
    }

    protected void assertPersistedBouwwerkToMatchUpdatableProperties(Bouwwerk expectedBouwwerk) {
        assertBouwwerkAllUpdatablePropertiesEquals(expectedBouwwerk, getPersistedBouwwerk(expectedBouwwerk));
    }
}
