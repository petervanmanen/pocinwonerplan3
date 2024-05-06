package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.LeidingelementAsserts.*;
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
import nl.ritense.demo.domain.Leidingelement;
import nl.ritense.demo.repository.LeidingelementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LeidingelementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeidingelementResourceIT {

    private static final String DEFAULT_AFWIJKENDEDIEPTELEGGING = "AAAAAAAAAA";
    private static final String UPDATED_AFWIJKENDEDIEPTELEGGING = "BBBBBBBBBB";

    private static final String DEFAULT_DIEPTE = "AAAAAAAAAA";
    private static final String UPDATED_DIEPTE = "BBBBBBBBBB";

    private static final String DEFAULT_GEONAUWKEURIGHEIDXY = "AAAAAAAAAA";
    private static final String UPDATED_GEONAUWKEURIGHEIDXY = "BBBBBBBBBB";

    private static final String DEFAULT_JAARONDERHOUDUITGEVOERD = "AAAAAAAAAA";
    private static final String UPDATED_JAARONDERHOUDUITGEVOERD = "BBBBBBBBBB";

    private static final String DEFAULT_LEVERANCIER = "AAAAAAAAAA";
    private static final String UPDATED_LEVERANCIER = "BBBBBBBBBB";

    private static final String DEFAULT_THEMAIMKL = "AAAAAAAAAA";
    private static final String UPDATED_THEMAIMKL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/leidingelements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LeidingelementRepository leidingelementRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeidingelementMockMvc;

    private Leidingelement leidingelement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Leidingelement createEntity(EntityManager em) {
        Leidingelement leidingelement = new Leidingelement()
            .afwijkendedieptelegging(DEFAULT_AFWIJKENDEDIEPTELEGGING)
            .diepte(DEFAULT_DIEPTE)
            .geonauwkeurigheidxy(DEFAULT_GEONAUWKEURIGHEIDXY)
            .jaaronderhouduitgevoerd(DEFAULT_JAARONDERHOUDUITGEVOERD)
            .leverancier(DEFAULT_LEVERANCIER)
            .themaimkl(DEFAULT_THEMAIMKL);
        return leidingelement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Leidingelement createUpdatedEntity(EntityManager em) {
        Leidingelement leidingelement = new Leidingelement()
            .afwijkendedieptelegging(UPDATED_AFWIJKENDEDIEPTELEGGING)
            .diepte(UPDATED_DIEPTE)
            .geonauwkeurigheidxy(UPDATED_GEONAUWKEURIGHEIDXY)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .leverancier(UPDATED_LEVERANCIER)
            .themaimkl(UPDATED_THEMAIMKL);
        return leidingelement;
    }

    @BeforeEach
    public void initTest() {
        leidingelement = createEntity(em);
    }

    @Test
    @Transactional
    void createLeidingelement() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Leidingelement
        var returnedLeidingelement = om.readValue(
            restLeidingelementMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(leidingelement)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Leidingelement.class
        );

        // Validate the Leidingelement in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLeidingelementUpdatableFieldsEquals(returnedLeidingelement, getPersistedLeidingelement(returnedLeidingelement));
    }

    @Test
    @Transactional
    void createLeidingelementWithExistingId() throws Exception {
        // Create the Leidingelement with an existing ID
        leidingelement.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeidingelementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(leidingelement)))
            .andExpect(status().isBadRequest());

        // Validate the Leidingelement in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLeidingelements() throws Exception {
        // Initialize the database
        leidingelementRepository.saveAndFlush(leidingelement);

        // Get all the leidingelementList
        restLeidingelementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leidingelement.getId().intValue())))
            .andExpect(jsonPath("$.[*].afwijkendedieptelegging").value(hasItem(DEFAULT_AFWIJKENDEDIEPTELEGGING)))
            .andExpect(jsonPath("$.[*].diepte").value(hasItem(DEFAULT_DIEPTE)))
            .andExpect(jsonPath("$.[*].geonauwkeurigheidxy").value(hasItem(DEFAULT_GEONAUWKEURIGHEIDXY)))
            .andExpect(jsonPath("$.[*].jaaronderhouduitgevoerd").value(hasItem(DEFAULT_JAARONDERHOUDUITGEVOERD)))
            .andExpect(jsonPath("$.[*].leverancier").value(hasItem(DEFAULT_LEVERANCIER)))
            .andExpect(jsonPath("$.[*].themaimkl").value(hasItem(DEFAULT_THEMAIMKL)));
    }

    @Test
    @Transactional
    void getLeidingelement() throws Exception {
        // Initialize the database
        leidingelementRepository.saveAndFlush(leidingelement);

        // Get the leidingelement
        restLeidingelementMockMvc
            .perform(get(ENTITY_API_URL_ID, leidingelement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leidingelement.getId().intValue()))
            .andExpect(jsonPath("$.afwijkendedieptelegging").value(DEFAULT_AFWIJKENDEDIEPTELEGGING))
            .andExpect(jsonPath("$.diepte").value(DEFAULT_DIEPTE))
            .andExpect(jsonPath("$.geonauwkeurigheidxy").value(DEFAULT_GEONAUWKEURIGHEIDXY))
            .andExpect(jsonPath("$.jaaronderhouduitgevoerd").value(DEFAULT_JAARONDERHOUDUITGEVOERD))
            .andExpect(jsonPath("$.leverancier").value(DEFAULT_LEVERANCIER))
            .andExpect(jsonPath("$.themaimkl").value(DEFAULT_THEMAIMKL));
    }

    @Test
    @Transactional
    void getNonExistingLeidingelement() throws Exception {
        // Get the leidingelement
        restLeidingelementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeidingelement() throws Exception {
        // Initialize the database
        leidingelementRepository.saveAndFlush(leidingelement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the leidingelement
        Leidingelement updatedLeidingelement = leidingelementRepository.findById(leidingelement.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLeidingelement are not directly saved in db
        em.detach(updatedLeidingelement);
        updatedLeidingelement
            .afwijkendedieptelegging(UPDATED_AFWIJKENDEDIEPTELEGGING)
            .diepte(UPDATED_DIEPTE)
            .geonauwkeurigheidxy(UPDATED_GEONAUWKEURIGHEIDXY)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .leverancier(UPDATED_LEVERANCIER)
            .themaimkl(UPDATED_THEMAIMKL);

        restLeidingelementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeidingelement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLeidingelement))
            )
            .andExpect(status().isOk());

        // Validate the Leidingelement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLeidingelementToMatchAllProperties(updatedLeidingelement);
    }

    @Test
    @Transactional
    void putNonExistingLeidingelement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leidingelement.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeidingelementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leidingelement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(leidingelement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leidingelement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeidingelement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leidingelement.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeidingelementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(leidingelement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leidingelement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeidingelement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leidingelement.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeidingelementMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(leidingelement)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Leidingelement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeidingelementWithPatch() throws Exception {
        // Initialize the database
        leidingelementRepository.saveAndFlush(leidingelement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the leidingelement using partial update
        Leidingelement partialUpdatedLeidingelement = new Leidingelement();
        partialUpdatedLeidingelement.setId(leidingelement.getId());

        partialUpdatedLeidingelement
            .diepte(UPDATED_DIEPTE)
            .geonauwkeurigheidxy(UPDATED_GEONAUWKEURIGHEIDXY)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .themaimkl(UPDATED_THEMAIMKL);

        restLeidingelementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeidingelement.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLeidingelement))
            )
            .andExpect(status().isOk());

        // Validate the Leidingelement in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLeidingelementUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedLeidingelement, leidingelement),
            getPersistedLeidingelement(leidingelement)
        );
    }

    @Test
    @Transactional
    void fullUpdateLeidingelementWithPatch() throws Exception {
        // Initialize the database
        leidingelementRepository.saveAndFlush(leidingelement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the leidingelement using partial update
        Leidingelement partialUpdatedLeidingelement = new Leidingelement();
        partialUpdatedLeidingelement.setId(leidingelement.getId());

        partialUpdatedLeidingelement
            .afwijkendedieptelegging(UPDATED_AFWIJKENDEDIEPTELEGGING)
            .diepte(UPDATED_DIEPTE)
            .geonauwkeurigheidxy(UPDATED_GEONAUWKEURIGHEIDXY)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .leverancier(UPDATED_LEVERANCIER)
            .themaimkl(UPDATED_THEMAIMKL);

        restLeidingelementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeidingelement.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLeidingelement))
            )
            .andExpect(status().isOk());

        // Validate the Leidingelement in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLeidingelementUpdatableFieldsEquals(partialUpdatedLeidingelement, getPersistedLeidingelement(partialUpdatedLeidingelement));
    }

    @Test
    @Transactional
    void patchNonExistingLeidingelement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leidingelement.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeidingelementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leidingelement.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(leidingelement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leidingelement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeidingelement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leidingelement.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeidingelementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(leidingelement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Leidingelement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeidingelement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        leidingelement.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeidingelementMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(leidingelement)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Leidingelement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeidingelement() throws Exception {
        // Initialize the database
        leidingelementRepository.saveAndFlush(leidingelement);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the leidingelement
        restLeidingelementMockMvc
            .perform(delete(ENTITY_API_URL_ID, leidingelement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return leidingelementRepository.count();
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

    protected Leidingelement getPersistedLeidingelement(Leidingelement leidingelement) {
        return leidingelementRepository.findById(leidingelement.getId()).orElseThrow();
    }

    protected void assertPersistedLeidingelementToMatchAllProperties(Leidingelement expectedLeidingelement) {
        assertLeidingelementAllPropertiesEquals(expectedLeidingelement, getPersistedLeidingelement(expectedLeidingelement));
    }

    protected void assertPersistedLeidingelementToMatchUpdatableProperties(Leidingelement expectedLeidingelement) {
        assertLeidingelementAllUpdatablePropertiesEquals(expectedLeidingelement, getPersistedLeidingelement(expectedLeidingelement));
    }
}
