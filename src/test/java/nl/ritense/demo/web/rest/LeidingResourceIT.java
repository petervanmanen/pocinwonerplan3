package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.LeidingAsserts.*;
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
import nl.ritense.demo.domain.Leiding;
import nl.ritense.demo.repository.LeidingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LeidingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeidingResourceIT {

    private static final String DEFAULT_AFWIJKENDEDIEPTELEGGING = "AAAAAAAAAA";
    private static final String UPDATED_AFWIJKENDEDIEPTELEGGING = "BBBBBBBBBB";

    private static final String DEFAULT_BREEDTE = "AAAAAAAAAA";
    private static final String UPDATED_BREEDTE = "BBBBBBBBBB";

    private static final String DEFAULT_DIAMETER = "AAAAAAAAAA";
    private static final String UPDATED_DIAMETER = "BBBBBBBBBB";

    private static final String DEFAULT_DIEPTE = "AAAAAAAAAA";
    private static final String UPDATED_DIEPTE = "BBBBBBBBBB";

    private static final String DEFAULT_EISVOORZORGSMAATREGEL = "AAAAAAAAAA";
    private static final String UPDATED_EISVOORZORGSMAATREGEL = "BBBBBBBBBB";

    private static final String DEFAULT_GEONAUWKEURIGHEIDXY = "AAAAAAAAAA";
    private static final String UPDATED_GEONAUWKEURIGHEIDXY = "BBBBBBBBBB";

    private static final String DEFAULT_HOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_HOOGTE = "BBBBBBBBBB";

    private static final String DEFAULT_JAARONDERHOUDUITGEVOERD = "AAAAAAAAAA";
    private static final String UPDATED_JAARONDERHOUDUITGEVOERD = "BBBBBBBBBB";

    private static final String DEFAULT_LENGTE = "AAAAAAAAAA";
    private static final String UPDATED_LENGTE = "BBBBBBBBBB";

    private static final String DEFAULT_LEVERANCIER = "AAAAAAAAAA";
    private static final String UPDATED_LEVERANCIER = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIAAL = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAAL = "BBBBBBBBBB";

    private static final String DEFAULT_THEMAIMKL = "AAAAAAAAAA";
    private static final String UPDATED_THEMAIMKL = "BBBBBBBBBB";

    private static final String DEFAULT_VERHOOGDRISICO = "AAAAAAAAAA";
    private static final String UPDATED_VERHOOGDRISICO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/leidings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LeidingRepository leidingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeidingMockMvc;

    private Leiding leiding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Leiding createEntity(EntityManager em) {
        Leiding leiding = new Leiding()
            .afwijkendedieptelegging(DEFAULT_AFWIJKENDEDIEPTELEGGING)
            .breedte(DEFAULT_BREEDTE)
            .diameter(DEFAULT_DIAMETER)
            .diepte(DEFAULT_DIEPTE)
            .eisvoorzorgsmaatregel(DEFAULT_EISVOORZORGSMAATREGEL)
            .geonauwkeurigheidxy(DEFAULT_GEONAUWKEURIGHEIDXY)
            .hoogte(DEFAULT_HOOGTE)
            .jaaronderhouduitgevoerd(DEFAULT_JAARONDERHOUDUITGEVOERD)
            .lengte(DEFAULT_LENGTE)
            .leverancier(DEFAULT_LEVERANCIER)
            .materiaal(DEFAULT_MATERIAAL)
            .themaimkl(DEFAULT_THEMAIMKL)
            .verhoogdrisico(DEFAULT_VERHOOGDRISICO);
        return leiding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Leiding createUpdatedEntity(EntityManager em) {
        Leiding leiding = new Leiding()
            .afwijkendedieptelegging(UPDATED_AFWIJKENDEDIEPTELEGGING)
            .breedte(UPDATED_BREEDTE)
            .diameter(UPDATED_DIAMETER)
            .diepte(UPDATED_DIEPTE)
            .eisvoorzorgsmaatregel(UPDATED_EISVOORZORGSMAATREGEL)
            .geonauwkeurigheidxy(UPDATED_GEONAUWKEURIGHEIDXY)
            .hoogte(UPDATED_HOOGTE)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .materiaal(UPDATED_MATERIAAL)
            .themaimkl(UPDATED_THEMAIMKL)
            .verhoogdrisico(UPDATED_VERHOOGDRISICO);
        return leiding;
    }

    @BeforeEach
    public void initTest() {
        leiding = createEntity(em);
    }

    @Test
    @Transactional
    void createLeiding() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Leiding
        var returnedLeiding = om.readValue(
            restLeidingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(leiding)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Leiding.class
        );

        // Validate the Leiding in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLeidingUpdatableFieldsEquals(returnedLeiding, getPersistedLeiding(returnedLeiding));
    }

    @Test
    @Transactional
    void createLeidingWithExistingId() throws Exception {
        // Create the Leiding with an existing ID
        leiding.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeidingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(leiding)))
            .andExpect(status().isBadRequest());

        // Validate the Leiding in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLeidings() throws Exception {
        // Initialize the database
        leidingRepository.saveAndFlush(leiding);

        // Get all the leidingList
        restLeidingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leiding.getId().intValue())))
            .andExpect(jsonPath("$.[*].afwijkendedieptelegging").value(hasItem(DEFAULT_AFWIJKENDEDIEPTELEGGING)))
            .andExpect(jsonPath("$.[*].breedte").value(hasItem(DEFAULT_BREEDTE)))
            .andExpect(jsonPath("$.[*].diameter").value(hasItem(DEFAULT_DIAMETER)))
            .andExpect(jsonPath("$.[*].diepte").value(hasItem(DEFAULT_DIEPTE)))
            .andExpect(jsonPath("$.[*].eisvoorzorgsmaatregel").value(hasItem(DEFAULT_EISVOORZORGSMAATREGEL)))
            .andExpect(jsonPath("$.[*].geonauwkeurigheidxy").value(hasItem(DEFAULT_GEONAUWKEURIGHEIDXY)))
            .andExpect(jsonPath("$.[*].hoogte").value(hasItem(DEFAULT_HOOGTE)))
            .andExpect(jsonPath("$.[*].jaaronderhouduitgevoerd").value(hasItem(DEFAULT_JAARONDERHOUDUITGEVOERD)))
            .andExpect(jsonPath("$.[*].lengte").value(hasItem(DEFAULT_LENGTE)))
            .andExpect(jsonPath("$.[*].leverancier").value(hasItem(DEFAULT_LEVERANCIER)))
            .andExpect(jsonPath("$.[*].materiaal").value(hasItem(DEFAULT_MATERIAAL)))
            .andExpect(jsonPath("$.[*].themaimkl").value(hasItem(DEFAULT_THEMAIMKL)))
            .andExpect(jsonPath("$.[*].verhoogdrisico").value(hasItem(DEFAULT_VERHOOGDRISICO)));
    }

    @Test
    @Transactional
    void getLeiding() throws Exception {
        // Initialize the database
        leidingRepository.saveAndFlush(leiding);

        // Get the leiding
        restLeidingMockMvc
            .perform(get(ENTITY_API_URL_ID, leiding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leiding.getId().intValue()))
            .andExpect(jsonPath("$.afwijkendedieptelegging").value(DEFAULT_AFWIJKENDEDIEPTELEGGING))
            .andExpect(jsonPath("$.breedte").value(DEFAULT_BREEDTE))
            .andExpect(jsonPath("$.diameter").value(DEFAULT_DIAMETER))
            .andExpect(jsonPath("$.diepte").value(DEFAULT_DIEPTE))
            .andExpect(jsonPath("$.eisvoorzorgsmaatregel").value(DEFAULT_EISVOORZORGSMAATREGEL))
            .andExpect(jsonPath("$.geonauwkeurigheidxy").value(DEFAULT_GEONAUWKEURIGHEIDXY))
            .andExpect(jsonPath("$.hoogte").value(DEFAULT_HOOGTE))
            .andExpect(jsonPath("$.jaaronderhouduitgevoerd").value(DEFAULT_JAARONDERHOUDUITGEVOERD))
            .andExpect(jsonPath("$.lengte").value(DEFAULT_LENGTE))
            .andExpect(jsonPath("$.leverancier").value(DEFAULT_LEVERANCIER))
            .andExpect(jsonPath("$.materiaal").value(DEFAULT_MATERIAAL))
            .andExpect(jsonPath("$.themaimkl").value(DEFAULT_THEMAIMKL))
            .andExpect(jsonPath("$.verhoogdrisico").value(DEFAULT_VERHOOGDRISICO));
    }

    @Test
    @Transactional
    void getNonExistingLeiding() throws Exception {
        // Get the leiding
        restLeidingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeiding() throws Exception {
        // Initialize the database
        leidingRepository.saveAndFlush(leiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the leiding
        Leiding updatedLeiding = leidingRepository.findById(leiding.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLeiding are not directly saved in db
        em.detach(updatedLeiding);
        updatedLeiding
            .afwijkendedieptelegging(UPDATED_AFWIJKENDEDIEPTELEGGING)
            .breedte(UPDATED_BREEDTE)
            .diameter(UPDATED_DIAMETER)
            .diepte(UPDATED_DIEPTE)
            .eisvoorzorgsmaatregel(UPDATED_EISVOORZORGSMAATREGEL)
            .geonauwkeurigheidxy(UPDATED_GEONAUWKEURIGHEIDXY)
            .hoogte(UPDATED_HOOGTE)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .materiaal(UPDATED_MATERIAAL)
            .themaimkl(UPDATED_THEMAIMKL)
            .verhoogdrisico(UPDATED_VERHOOGDRISICO);

        restLeidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeiding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLeiding))
            )
            .andExpect(status().isOk());

        // Validate the Leiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLeidingToMatchAllProperties(updatedLeiding);
    }

    @Test
    @Transactional
    void putNonExistingLeiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leiding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeidingMockMvc
            .perform(put(ENTITY_API_URL_ID, leiding.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(leiding)))
            .andExpect(status().isBadRequest());

        // Validate the Leiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(leiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeidingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(leiding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Leiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeidingWithPatch() throws Exception {
        // Initialize the database
        leidingRepository.saveAndFlush(leiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the leiding using partial update
        Leiding partialUpdatedLeiding = new Leiding();
        partialUpdatedLeiding.setId(leiding.getId());

        partialUpdatedLeiding
            .breedte(UPDATED_BREEDTE)
            .eisvoorzorgsmaatregel(UPDATED_EISVOORZORGSMAATREGEL)
            .geonauwkeurigheidxy(UPDATED_GEONAUWKEURIGHEIDXY)
            .lengte(UPDATED_LENGTE)
            .materiaal(UPDATED_MATERIAAL)
            .verhoogdrisico(UPDATED_VERHOOGDRISICO);

        restLeidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLeiding))
            )
            .andExpect(status().isOk());

        // Validate the Leiding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLeidingUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedLeiding, leiding), getPersistedLeiding(leiding));
    }

    @Test
    @Transactional
    void fullUpdateLeidingWithPatch() throws Exception {
        // Initialize the database
        leidingRepository.saveAndFlush(leiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the leiding using partial update
        Leiding partialUpdatedLeiding = new Leiding();
        partialUpdatedLeiding.setId(leiding.getId());

        partialUpdatedLeiding
            .afwijkendedieptelegging(UPDATED_AFWIJKENDEDIEPTELEGGING)
            .breedte(UPDATED_BREEDTE)
            .diameter(UPDATED_DIAMETER)
            .diepte(UPDATED_DIEPTE)
            .eisvoorzorgsmaatregel(UPDATED_EISVOORZORGSMAATREGEL)
            .geonauwkeurigheidxy(UPDATED_GEONAUWKEURIGHEIDXY)
            .hoogte(UPDATED_HOOGTE)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .materiaal(UPDATED_MATERIAAL)
            .themaimkl(UPDATED_THEMAIMKL)
            .verhoogdrisico(UPDATED_VERHOOGDRISICO);

        restLeidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLeiding))
            )
            .andExpect(status().isOk());

        // Validate the Leiding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLeidingUpdatableFieldsEquals(partialUpdatedLeiding, getPersistedLeiding(partialUpdatedLeiding));
    }

    @Test
    @Transactional
    void patchNonExistingLeiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leiding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leiding.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(leiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(leiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeidingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(leiding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Leiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeiding() throws Exception {
        // Initialize the database
        leidingRepository.saveAndFlush(leiding);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the leiding
        restLeidingMockMvc
            .perform(delete(ENTITY_API_URL_ID, leiding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return leidingRepository.count();
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

    protected Leiding getPersistedLeiding(Leiding leiding) {
        return leidingRepository.findById(leiding.getId()).orElseThrow();
    }

    protected void assertPersistedLeidingToMatchAllProperties(Leiding expectedLeiding) {
        assertLeidingAllPropertiesEquals(expectedLeiding, getPersistedLeiding(expectedLeiding));
    }

    protected void assertPersistedLeidingToMatchUpdatableProperties(Leiding expectedLeiding) {
        assertLeidingAllUpdatablePropertiesEquals(expectedLeiding, getPersistedLeiding(expectedLeiding));
    }
}
