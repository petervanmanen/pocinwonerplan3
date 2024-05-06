package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BordAsserts.*;
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
import nl.ritense.demo.domain.Bord;
import nl.ritense.demo.repository.BordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BordResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BordResourceIT {

    private static final String DEFAULT_BREEDTE = "AAAAAAAAAA";
    private static final String UPDATED_BREEDTE = "BBBBBBBBBB";

    private static final String DEFAULT_DIAMETER = "AAAAAAAAAA";
    private static final String UPDATED_DIAMETER = "BBBBBBBBBB";

    private static final String DEFAULT_DRAGER = "AAAAAAAAAA";
    private static final String UPDATED_DRAGER = "BBBBBBBBBB";

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

    private static final String DEFAULT_VORM = "AAAAAAAAAA";
    private static final String UPDATED_VORM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bords";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BordRepository bordRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBordMockMvc;

    private Bord bord;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bord createEntity(EntityManager em) {
        Bord bord = new Bord()
            .breedte(DEFAULT_BREEDTE)
            .diameter(DEFAULT_DIAMETER)
            .drager(DEFAULT_DRAGER)
            .hoogte(DEFAULT_HOOGTE)
            .jaaronderhouduitgevoerd(DEFAULT_JAARONDERHOUDUITGEVOERD)
            .lengte(DEFAULT_LENGTE)
            .leverancier(DEFAULT_LEVERANCIER)
            .materiaal(DEFAULT_MATERIAAL)
            .vorm(DEFAULT_VORM);
        return bord;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bord createUpdatedEntity(EntityManager em) {
        Bord bord = new Bord()
            .breedte(UPDATED_BREEDTE)
            .diameter(UPDATED_DIAMETER)
            .drager(UPDATED_DRAGER)
            .hoogte(UPDATED_HOOGTE)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .materiaal(UPDATED_MATERIAAL)
            .vorm(UPDATED_VORM);
        return bord;
    }

    @BeforeEach
    public void initTest() {
        bord = createEntity(em);
    }

    @Test
    @Transactional
    void createBord() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Bord
        var returnedBord = om.readValue(
            restBordMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bord)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Bord.class
        );

        // Validate the Bord in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBordUpdatableFieldsEquals(returnedBord, getPersistedBord(returnedBord));
    }

    @Test
    @Transactional
    void createBordWithExistingId() throws Exception {
        // Create the Bord with an existing ID
        bord.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBordMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bord)))
            .andExpect(status().isBadRequest());

        // Validate the Bord in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBords() throws Exception {
        // Initialize the database
        bordRepository.saveAndFlush(bord);

        // Get all the bordList
        restBordMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bord.getId().intValue())))
            .andExpect(jsonPath("$.[*].breedte").value(hasItem(DEFAULT_BREEDTE)))
            .andExpect(jsonPath("$.[*].diameter").value(hasItem(DEFAULT_DIAMETER)))
            .andExpect(jsonPath("$.[*].drager").value(hasItem(DEFAULT_DRAGER)))
            .andExpect(jsonPath("$.[*].hoogte").value(hasItem(DEFAULT_HOOGTE)))
            .andExpect(jsonPath("$.[*].jaaronderhouduitgevoerd").value(hasItem(DEFAULT_JAARONDERHOUDUITGEVOERD)))
            .andExpect(jsonPath("$.[*].lengte").value(hasItem(DEFAULT_LENGTE)))
            .andExpect(jsonPath("$.[*].leverancier").value(hasItem(DEFAULT_LEVERANCIER)))
            .andExpect(jsonPath("$.[*].materiaal").value(hasItem(DEFAULT_MATERIAAL)))
            .andExpect(jsonPath("$.[*].vorm").value(hasItem(DEFAULT_VORM)));
    }

    @Test
    @Transactional
    void getBord() throws Exception {
        // Initialize the database
        bordRepository.saveAndFlush(bord);

        // Get the bord
        restBordMockMvc
            .perform(get(ENTITY_API_URL_ID, bord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bord.getId().intValue()))
            .andExpect(jsonPath("$.breedte").value(DEFAULT_BREEDTE))
            .andExpect(jsonPath("$.diameter").value(DEFAULT_DIAMETER))
            .andExpect(jsonPath("$.drager").value(DEFAULT_DRAGER))
            .andExpect(jsonPath("$.hoogte").value(DEFAULT_HOOGTE))
            .andExpect(jsonPath("$.jaaronderhouduitgevoerd").value(DEFAULT_JAARONDERHOUDUITGEVOERD))
            .andExpect(jsonPath("$.lengte").value(DEFAULT_LENGTE))
            .andExpect(jsonPath("$.leverancier").value(DEFAULT_LEVERANCIER))
            .andExpect(jsonPath("$.materiaal").value(DEFAULT_MATERIAAL))
            .andExpect(jsonPath("$.vorm").value(DEFAULT_VORM));
    }

    @Test
    @Transactional
    void getNonExistingBord() throws Exception {
        // Get the bord
        restBordMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBord() throws Exception {
        // Initialize the database
        bordRepository.saveAndFlush(bord);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bord
        Bord updatedBord = bordRepository.findById(bord.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBord are not directly saved in db
        em.detach(updatedBord);
        updatedBord
            .breedte(UPDATED_BREEDTE)
            .diameter(UPDATED_DIAMETER)
            .drager(UPDATED_DRAGER)
            .hoogte(UPDATED_HOOGTE)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .materiaal(UPDATED_MATERIAAL)
            .vorm(UPDATED_VORM);

        restBordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBord.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBord))
            )
            .andExpect(status().isOk());

        // Validate the Bord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBordToMatchAllProperties(updatedBord);
    }

    @Test
    @Transactional
    void putNonExistingBord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bord.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBordMockMvc
            .perform(put(ENTITY_API_URL_ID, bord.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bord)))
            .andExpect(status().isBadRequest());

        // Validate the Bord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bord.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bord))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bord.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBordMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bord)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBordWithPatch() throws Exception {
        // Initialize the database
        bordRepository.saveAndFlush(bord);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bord using partial update
        Bord partialUpdatedBord = new Bord();
        partialUpdatedBord.setId(bord.getId());

        partialUpdatedBord.breedte(UPDATED_BREEDTE).hoogte(UPDATED_HOOGTE).materiaal(UPDATED_MATERIAAL);

        restBordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBord.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBord))
            )
            .andExpect(status().isOk());

        // Validate the Bord in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBordUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBord, bord), getPersistedBord(bord));
    }

    @Test
    @Transactional
    void fullUpdateBordWithPatch() throws Exception {
        // Initialize the database
        bordRepository.saveAndFlush(bord);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bord using partial update
        Bord partialUpdatedBord = new Bord();
        partialUpdatedBord.setId(bord.getId());

        partialUpdatedBord
            .breedte(UPDATED_BREEDTE)
            .diameter(UPDATED_DIAMETER)
            .drager(UPDATED_DRAGER)
            .hoogte(UPDATED_HOOGTE)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .materiaal(UPDATED_MATERIAAL)
            .vorm(UPDATED_VORM);

        restBordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBord.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBord))
            )
            .andExpect(status().isOk());

        // Validate the Bord in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBordUpdatableFieldsEquals(partialUpdatedBord, getPersistedBord(partialUpdatedBord));
    }

    @Test
    @Transactional
    void patchNonExistingBord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bord.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBordMockMvc
            .perform(patch(ENTITY_API_URL_ID, bord.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bord)))
            .andExpect(status().isBadRequest());

        // Validate the Bord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bord.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bord))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bord.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBordMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bord)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBord() throws Exception {
        // Initialize the database
        bordRepository.saveAndFlush(bord);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bord
        restBordMockMvc
            .perform(delete(ENTITY_API_URL_ID, bord.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bordRepository.count();
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

    protected Bord getPersistedBord(Bord bord) {
        return bordRepository.findById(bord.getId()).orElseThrow();
    }

    protected void assertPersistedBordToMatchAllProperties(Bord expectedBord) {
        assertBordAllPropertiesEquals(expectedBord, getPersistedBord(expectedBord));
    }

    protected void assertPersistedBordToMatchUpdatableProperties(Bord expectedBord) {
        assertBordAllUpdatablePropertiesEquals(expectedBord, getPersistedBord(expectedBord));
    }
}
