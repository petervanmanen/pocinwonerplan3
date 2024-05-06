package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ScheidingAsserts.*;
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
import nl.ritense.demo.domain.Scheiding;
import nl.ritense.demo.repository.ScheidingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ScheidingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ScheidingResourceIT {

    private static final String DEFAULT_AANLEGHOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_AANLEGHOOGTE = "BBBBBBBBBB";

    private static final String DEFAULT_BREEDTE = "AAAAAAAAAA";
    private static final String UPDATED_BREEDTE = "BBBBBBBBBB";

    private static final String DEFAULT_HOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_HOOGTE = "BBBBBBBBBB";

    private static final String DEFAULT_JAARONDERHOUDUITGEVOERD = "AAAAAAAAAA";
    private static final String UPDATED_JAARONDERHOUDUITGEVOERD = "BBBBBBBBBB";

    private static final String DEFAULT_LENGTE = "AAAAAAAAAA";
    private static final String UPDATED_LENGTE = "BBBBBBBBBB";

    private static final String DEFAULT_LEVERANCIER = "AAAAAAAAAA";
    private static final String UPDATED_LEVERANCIER = "BBBBBBBBBB";

    private static final String DEFAULT_EOBJECTNAAM = "AAAAAAAAAA";
    private static final String UPDATED_EOBJECTNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_EOBJECTNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_EOBJECTNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_OPPERVLAKTE = "AAAAAAAAAA";
    private static final String UPDATED_OPPERVLAKTE = "BBBBBBBBBB";

    private static final String DEFAULT_SCHEIDINGMATERIAAL = "AAAAAAAAAA";
    private static final String UPDATED_SCHEIDINGMATERIAAL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VERPLAATSBAAR = false;
    private static final Boolean UPDATED_VERPLAATSBAAR = true;

    private static final String ENTITY_API_URL = "/api/scheidings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ScheidingRepository scheidingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScheidingMockMvc;

    private Scheiding scheiding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scheiding createEntity(EntityManager em) {
        Scheiding scheiding = new Scheiding()
            .aanleghoogte(DEFAULT_AANLEGHOOGTE)
            .breedte(DEFAULT_BREEDTE)
            .hoogte(DEFAULT_HOOGTE)
            .jaaronderhouduitgevoerd(DEFAULT_JAARONDERHOUDUITGEVOERD)
            .lengte(DEFAULT_LENGTE)
            .leverancier(DEFAULT_LEVERANCIER)
            .eobjectnaam(DEFAULT_EOBJECTNAAM)
            .eobjectnummer(DEFAULT_EOBJECTNUMMER)
            .oppervlakte(DEFAULT_OPPERVLAKTE)
            .scheidingmateriaal(DEFAULT_SCHEIDINGMATERIAAL)
            .verplaatsbaar(DEFAULT_VERPLAATSBAAR);
        return scheiding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scheiding createUpdatedEntity(EntityManager em) {
        Scheiding scheiding = new Scheiding()
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .breedte(UPDATED_BREEDTE)
            .hoogte(UPDATED_HOOGTE)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .eobjectnaam(UPDATED_EOBJECTNAAM)
            .eobjectnummer(UPDATED_EOBJECTNUMMER)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .scheidingmateriaal(UPDATED_SCHEIDINGMATERIAAL)
            .verplaatsbaar(UPDATED_VERPLAATSBAAR);
        return scheiding;
    }

    @BeforeEach
    public void initTest() {
        scheiding = createEntity(em);
    }

    @Test
    @Transactional
    void createScheiding() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Scheiding
        var returnedScheiding = om.readValue(
            restScheidingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(scheiding)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Scheiding.class
        );

        // Validate the Scheiding in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertScheidingUpdatableFieldsEquals(returnedScheiding, getPersistedScheiding(returnedScheiding));
    }

    @Test
    @Transactional
    void createScheidingWithExistingId() throws Exception {
        // Create the Scheiding with an existing ID
        scheiding.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restScheidingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(scheiding)))
            .andExpect(status().isBadRequest());

        // Validate the Scheiding in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllScheidings() throws Exception {
        // Initialize the database
        scheidingRepository.saveAndFlush(scheiding);

        // Get all the scheidingList
        restScheidingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scheiding.getId().intValue())))
            .andExpect(jsonPath("$.[*].aanleghoogte").value(hasItem(DEFAULT_AANLEGHOOGTE)))
            .andExpect(jsonPath("$.[*].breedte").value(hasItem(DEFAULT_BREEDTE)))
            .andExpect(jsonPath("$.[*].hoogte").value(hasItem(DEFAULT_HOOGTE)))
            .andExpect(jsonPath("$.[*].jaaronderhouduitgevoerd").value(hasItem(DEFAULT_JAARONDERHOUDUITGEVOERD)))
            .andExpect(jsonPath("$.[*].lengte").value(hasItem(DEFAULT_LENGTE)))
            .andExpect(jsonPath("$.[*].leverancier").value(hasItem(DEFAULT_LEVERANCIER)))
            .andExpect(jsonPath("$.[*].eobjectnaam").value(hasItem(DEFAULT_EOBJECTNAAM)))
            .andExpect(jsonPath("$.[*].eobjectnummer").value(hasItem(DEFAULT_EOBJECTNUMMER)))
            .andExpect(jsonPath("$.[*].oppervlakte").value(hasItem(DEFAULT_OPPERVLAKTE)))
            .andExpect(jsonPath("$.[*].scheidingmateriaal").value(hasItem(DEFAULT_SCHEIDINGMATERIAAL)))
            .andExpect(jsonPath("$.[*].verplaatsbaar").value(hasItem(DEFAULT_VERPLAATSBAAR.booleanValue())));
    }

    @Test
    @Transactional
    void getScheiding() throws Exception {
        // Initialize the database
        scheidingRepository.saveAndFlush(scheiding);

        // Get the scheiding
        restScheidingMockMvc
            .perform(get(ENTITY_API_URL_ID, scheiding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(scheiding.getId().intValue()))
            .andExpect(jsonPath("$.aanleghoogte").value(DEFAULT_AANLEGHOOGTE))
            .andExpect(jsonPath("$.breedte").value(DEFAULT_BREEDTE))
            .andExpect(jsonPath("$.hoogte").value(DEFAULT_HOOGTE))
            .andExpect(jsonPath("$.jaaronderhouduitgevoerd").value(DEFAULT_JAARONDERHOUDUITGEVOERD))
            .andExpect(jsonPath("$.lengte").value(DEFAULT_LENGTE))
            .andExpect(jsonPath("$.leverancier").value(DEFAULT_LEVERANCIER))
            .andExpect(jsonPath("$.eobjectnaam").value(DEFAULT_EOBJECTNAAM))
            .andExpect(jsonPath("$.eobjectnummer").value(DEFAULT_EOBJECTNUMMER))
            .andExpect(jsonPath("$.oppervlakte").value(DEFAULT_OPPERVLAKTE))
            .andExpect(jsonPath("$.scheidingmateriaal").value(DEFAULT_SCHEIDINGMATERIAAL))
            .andExpect(jsonPath("$.verplaatsbaar").value(DEFAULT_VERPLAATSBAAR.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingScheiding() throws Exception {
        // Get the scheiding
        restScheidingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingScheiding() throws Exception {
        // Initialize the database
        scheidingRepository.saveAndFlush(scheiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the scheiding
        Scheiding updatedScheiding = scheidingRepository.findById(scheiding.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedScheiding are not directly saved in db
        em.detach(updatedScheiding);
        updatedScheiding
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .breedte(UPDATED_BREEDTE)
            .hoogte(UPDATED_HOOGTE)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .eobjectnaam(UPDATED_EOBJECTNAAM)
            .eobjectnummer(UPDATED_EOBJECTNUMMER)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .scheidingmateriaal(UPDATED_SCHEIDINGMATERIAAL)
            .verplaatsbaar(UPDATED_VERPLAATSBAAR);

        restScheidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedScheiding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedScheiding))
            )
            .andExpect(status().isOk());

        // Validate the Scheiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedScheidingToMatchAllProperties(updatedScheiding);
    }

    @Test
    @Transactional
    void putNonExistingScheiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        scheiding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScheidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, scheiding.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(scheiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Scheiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchScheiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        scheiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScheidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(scheiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Scheiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamScheiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        scheiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScheidingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(scheiding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Scheiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateScheidingWithPatch() throws Exception {
        // Initialize the database
        scheidingRepository.saveAndFlush(scheiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the scheiding using partial update
        Scheiding partialUpdatedScheiding = new Scheiding();
        partialUpdatedScheiding.setId(scheiding.getId());

        partialUpdatedScheiding
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .breedte(UPDATED_BREEDTE)
            .leverancier(UPDATED_LEVERANCIER)
            .eobjectnaam(UPDATED_EOBJECTNAAM)
            .eobjectnummer(UPDATED_EOBJECTNUMMER)
            .oppervlakte(UPDATED_OPPERVLAKTE);

        restScheidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScheiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedScheiding))
            )
            .andExpect(status().isOk());

        // Validate the Scheiding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertScheidingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedScheiding, scheiding),
            getPersistedScheiding(scheiding)
        );
    }

    @Test
    @Transactional
    void fullUpdateScheidingWithPatch() throws Exception {
        // Initialize the database
        scheidingRepository.saveAndFlush(scheiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the scheiding using partial update
        Scheiding partialUpdatedScheiding = new Scheiding();
        partialUpdatedScheiding.setId(scheiding.getId());

        partialUpdatedScheiding
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .breedte(UPDATED_BREEDTE)
            .hoogte(UPDATED_HOOGTE)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .eobjectnaam(UPDATED_EOBJECTNAAM)
            .eobjectnummer(UPDATED_EOBJECTNUMMER)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .scheidingmateriaal(UPDATED_SCHEIDINGMATERIAAL)
            .verplaatsbaar(UPDATED_VERPLAATSBAAR);

        restScheidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScheiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedScheiding))
            )
            .andExpect(status().isOk());

        // Validate the Scheiding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertScheidingUpdatableFieldsEquals(partialUpdatedScheiding, getPersistedScheiding(partialUpdatedScheiding));
    }

    @Test
    @Transactional
    void patchNonExistingScheiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        scheiding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScheidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, scheiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(scheiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Scheiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchScheiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        scheiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScheidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(scheiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Scheiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamScheiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        scheiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScheidingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(scheiding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Scheiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteScheiding() throws Exception {
        // Initialize the database
        scheidingRepository.saveAndFlush(scheiding);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the scheiding
        restScheidingMockMvc
            .perform(delete(ENTITY_API_URL_ID, scheiding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return scheidingRepository.count();
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

    protected Scheiding getPersistedScheiding(Scheiding scheiding) {
        return scheidingRepository.findById(scheiding.getId()).orElseThrow();
    }

    protected void assertPersistedScheidingToMatchAllProperties(Scheiding expectedScheiding) {
        assertScheidingAllPropertiesEquals(expectedScheiding, getPersistedScheiding(expectedScheiding));
    }

    protected void assertPersistedScheidingToMatchUpdatableProperties(Scheiding expectedScheiding) {
        assertScheidingAllUpdatablePropertiesEquals(expectedScheiding, getPersistedScheiding(expectedScheiding));
    }
}
