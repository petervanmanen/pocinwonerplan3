package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.PaalAsserts.*;
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
import nl.ritense.demo.domain.Paal;
import nl.ritense.demo.repository.PaalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PaalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaalResourceIT {

    private static final String DEFAULT_BREEDTE = "AAAAAAAAAA";
    private static final String UPDATED_BREEDTE = "BBBBBBBBBB";

    private static final String DEFAULT_DIAMETER = "AAAAAAAAAA";
    private static final String UPDATED_DIAMETER = "BBBBBBBBBB";

    private static final String DEFAULT_HOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_HOOGTE = "BBBBBBBBBB";

    private static final String DEFAULT_JAARONDERHOUDUITGEVOERD = "AAAAAAAAAA";
    private static final String UPDATED_JAARONDERHOUDUITGEVOERD = "BBBBBBBBBB";

    private static final String DEFAULT_KWALITEITSNIVEAUACTUEEL = "AAAAAAAAAA";
    private static final String UPDATED_KWALITEITSNIVEAUACTUEEL = "BBBBBBBBBB";

    private static final String DEFAULT_KWALITEITSNIVEAUGEWENST = "AAAAAAAAAA";
    private static final String UPDATED_KWALITEITSNIVEAUGEWENST = "BBBBBBBBBB";

    private static final String DEFAULT_LENGTE = "AAAAAAAAAA";
    private static final String UPDATED_LENGTE = "BBBBBBBBBB";

    private static final String DEFAULT_LEVERANCIER = "AAAAAAAAAA";
    private static final String UPDATED_LEVERANCIER = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIAAL = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAAL = "BBBBBBBBBB";

    private static final String DEFAULT_VORM = "AAAAAAAAAA";
    private static final String UPDATED_VORM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/paals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PaalRepository paalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaalMockMvc;

    private Paal paal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paal createEntity(EntityManager em) {
        Paal paal = new Paal()
            .breedte(DEFAULT_BREEDTE)
            .diameter(DEFAULT_DIAMETER)
            .hoogte(DEFAULT_HOOGTE)
            .jaaronderhouduitgevoerd(DEFAULT_JAARONDERHOUDUITGEVOERD)
            .kwaliteitsniveauactueel(DEFAULT_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(DEFAULT_KWALITEITSNIVEAUGEWENST)
            .lengte(DEFAULT_LENGTE)
            .leverancier(DEFAULT_LEVERANCIER)
            .materiaal(DEFAULT_MATERIAAL)
            .vorm(DEFAULT_VORM);
        return paal;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paal createUpdatedEntity(EntityManager em) {
        Paal paal = new Paal()
            .breedte(UPDATED_BREEDTE)
            .diameter(UPDATED_DIAMETER)
            .hoogte(UPDATED_HOOGTE)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .materiaal(UPDATED_MATERIAAL)
            .vorm(UPDATED_VORM);
        return paal;
    }

    @BeforeEach
    public void initTest() {
        paal = createEntity(em);
    }

    @Test
    @Transactional
    void createPaal() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Paal
        var returnedPaal = om.readValue(
            restPaalMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paal)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Paal.class
        );

        // Validate the Paal in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPaalUpdatableFieldsEquals(returnedPaal, getPersistedPaal(returnedPaal));
    }

    @Test
    @Transactional
    void createPaalWithExistingId() throws Exception {
        // Create the Paal with an existing ID
        paal.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paal)))
            .andExpect(status().isBadRequest());

        // Validate the Paal in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPaals() throws Exception {
        // Initialize the database
        paalRepository.saveAndFlush(paal);

        // Get all the paalList
        restPaalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paal.getId().intValue())))
            .andExpect(jsonPath("$.[*].breedte").value(hasItem(DEFAULT_BREEDTE)))
            .andExpect(jsonPath("$.[*].diameter").value(hasItem(DEFAULT_DIAMETER)))
            .andExpect(jsonPath("$.[*].hoogte").value(hasItem(DEFAULT_HOOGTE)))
            .andExpect(jsonPath("$.[*].jaaronderhouduitgevoerd").value(hasItem(DEFAULT_JAARONDERHOUDUITGEVOERD)))
            .andExpect(jsonPath("$.[*].kwaliteitsniveauactueel").value(hasItem(DEFAULT_KWALITEITSNIVEAUACTUEEL)))
            .andExpect(jsonPath("$.[*].kwaliteitsniveaugewenst").value(hasItem(DEFAULT_KWALITEITSNIVEAUGEWENST)))
            .andExpect(jsonPath("$.[*].lengte").value(hasItem(DEFAULT_LENGTE)))
            .andExpect(jsonPath("$.[*].leverancier").value(hasItem(DEFAULT_LEVERANCIER)))
            .andExpect(jsonPath("$.[*].materiaal").value(hasItem(DEFAULT_MATERIAAL)))
            .andExpect(jsonPath("$.[*].vorm").value(hasItem(DEFAULT_VORM)));
    }

    @Test
    @Transactional
    void getPaal() throws Exception {
        // Initialize the database
        paalRepository.saveAndFlush(paal);

        // Get the paal
        restPaalMockMvc
            .perform(get(ENTITY_API_URL_ID, paal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paal.getId().intValue()))
            .andExpect(jsonPath("$.breedte").value(DEFAULT_BREEDTE))
            .andExpect(jsonPath("$.diameter").value(DEFAULT_DIAMETER))
            .andExpect(jsonPath("$.hoogte").value(DEFAULT_HOOGTE))
            .andExpect(jsonPath("$.jaaronderhouduitgevoerd").value(DEFAULT_JAARONDERHOUDUITGEVOERD))
            .andExpect(jsonPath("$.kwaliteitsniveauactueel").value(DEFAULT_KWALITEITSNIVEAUACTUEEL))
            .andExpect(jsonPath("$.kwaliteitsniveaugewenst").value(DEFAULT_KWALITEITSNIVEAUGEWENST))
            .andExpect(jsonPath("$.lengte").value(DEFAULT_LENGTE))
            .andExpect(jsonPath("$.leverancier").value(DEFAULT_LEVERANCIER))
            .andExpect(jsonPath("$.materiaal").value(DEFAULT_MATERIAAL))
            .andExpect(jsonPath("$.vorm").value(DEFAULT_VORM));
    }

    @Test
    @Transactional
    void getNonExistingPaal() throws Exception {
        // Get the paal
        restPaalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPaal() throws Exception {
        // Initialize the database
        paalRepository.saveAndFlush(paal);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the paal
        Paal updatedPaal = paalRepository.findById(paal.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPaal are not directly saved in db
        em.detach(updatedPaal);
        updatedPaal
            .breedte(UPDATED_BREEDTE)
            .diameter(UPDATED_DIAMETER)
            .hoogte(UPDATED_HOOGTE)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .materiaal(UPDATED_MATERIAAL)
            .vorm(UPDATED_VORM);

        restPaalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPaal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPaal))
            )
            .andExpect(status().isOk());

        // Validate the Paal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPaalToMatchAllProperties(updatedPaal);
    }

    @Test
    @Transactional
    void putNonExistingPaal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paal.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaalMockMvc
            .perform(put(ENTITY_API_URL_ID, paal.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paal)))
            .andExpect(status().isBadRequest());

        // Validate the Paal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paal.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(paal))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paal.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaalMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paal)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Paal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaalWithPatch() throws Exception {
        // Initialize the database
        paalRepository.saveAndFlush(paal);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the paal using partial update
        Paal partialUpdatedPaal = new Paal();
        partialUpdatedPaal.setId(paal.getId());

        partialUpdatedPaal
            .breedte(UPDATED_BREEDTE)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .vorm(UPDATED_VORM);

        restPaalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaal.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPaal))
            )
            .andExpect(status().isOk());

        // Validate the Paal in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPaalUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPaal, paal), getPersistedPaal(paal));
    }

    @Test
    @Transactional
    void fullUpdatePaalWithPatch() throws Exception {
        // Initialize the database
        paalRepository.saveAndFlush(paal);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the paal using partial update
        Paal partialUpdatedPaal = new Paal();
        partialUpdatedPaal.setId(paal.getId());

        partialUpdatedPaal
            .breedte(UPDATED_BREEDTE)
            .diameter(UPDATED_DIAMETER)
            .hoogte(UPDATED_HOOGTE)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .materiaal(UPDATED_MATERIAAL)
            .vorm(UPDATED_VORM);

        restPaalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaal.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPaal))
            )
            .andExpect(status().isOk());

        // Validate the Paal in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPaalUpdatableFieldsEquals(partialUpdatedPaal, getPersistedPaal(partialUpdatedPaal));
    }

    @Test
    @Transactional
    void patchNonExistingPaal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paal.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaalMockMvc
            .perform(patch(ENTITY_API_URL_ID, paal.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(paal)))
            .andExpect(status().isBadRequest());

        // Validate the Paal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paal.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(paal))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paal.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaalMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(paal)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Paal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaal() throws Exception {
        // Initialize the database
        paalRepository.saveAndFlush(paal);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the paal
        restPaalMockMvc
            .perform(delete(ENTITY_API_URL_ID, paal.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return paalRepository.count();
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

    protected Paal getPersistedPaal(Paal paal) {
        return paalRepository.findById(paal.getId()).orElseThrow();
    }

    protected void assertPersistedPaalToMatchAllProperties(Paal expectedPaal) {
        assertPaalAllPropertiesEquals(expectedPaal, getPersistedPaal(expectedPaal));
    }

    protected void assertPersistedPaalToMatchUpdatableProperties(Paal expectedPaal) {
        assertPaalAllUpdatablePropertiesEquals(expectedPaal, getPersistedPaal(expectedPaal));
    }
}
