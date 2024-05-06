package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BeperkingAsserts.*;
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
import nl.ritense.demo.domain.Beperking;
import nl.ritense.demo.repository.BeperkingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BeperkingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BeperkingResourceIT {

    private static final String DEFAULT_CATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTAAR = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAAR = "BBBBBBBBBB";

    private static final String DEFAULT_DUUR = "AAAAAAAAAA";
    private static final String UPDATED_DUUR = "BBBBBBBBBB";

    private static final String DEFAULT_WET = "AAAAAAAAAA";
    private static final String UPDATED_WET = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/beperkings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BeperkingRepository beperkingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBeperkingMockMvc;

    private Beperking beperking;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beperking createEntity(EntityManager em) {
        Beperking beperking = new Beperking()
            .categorie(DEFAULT_CATEGORIE)
            .commentaar(DEFAULT_COMMENTAAR)
            .duur(DEFAULT_DUUR)
            .wet(DEFAULT_WET);
        return beperking;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beperking createUpdatedEntity(EntityManager em) {
        Beperking beperking = new Beperking()
            .categorie(UPDATED_CATEGORIE)
            .commentaar(UPDATED_COMMENTAAR)
            .duur(UPDATED_DUUR)
            .wet(UPDATED_WET);
        return beperking;
    }

    @BeforeEach
    public void initTest() {
        beperking = createEntity(em);
    }

    @Test
    @Transactional
    void createBeperking() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Beperking
        var returnedBeperking = om.readValue(
            restBeperkingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beperking)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Beperking.class
        );

        // Validate the Beperking in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBeperkingUpdatableFieldsEquals(returnedBeperking, getPersistedBeperking(returnedBeperking));
    }

    @Test
    @Transactional
    void createBeperkingWithExistingId() throws Exception {
        // Create the Beperking with an existing ID
        beperking.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeperkingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beperking)))
            .andExpect(status().isBadRequest());

        // Validate the Beperking in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBeperkings() throws Exception {
        // Initialize the database
        beperkingRepository.saveAndFlush(beperking);

        // Get all the beperkingList
        restBeperkingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beperking.getId().intValue())))
            .andExpect(jsonPath("$.[*].categorie").value(hasItem(DEFAULT_CATEGORIE)))
            .andExpect(jsonPath("$.[*].commentaar").value(hasItem(DEFAULT_COMMENTAAR)))
            .andExpect(jsonPath("$.[*].duur").value(hasItem(DEFAULT_DUUR)))
            .andExpect(jsonPath("$.[*].wet").value(hasItem(DEFAULT_WET)));
    }

    @Test
    @Transactional
    void getBeperking() throws Exception {
        // Initialize the database
        beperkingRepository.saveAndFlush(beperking);

        // Get the beperking
        restBeperkingMockMvc
            .perform(get(ENTITY_API_URL_ID, beperking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(beperking.getId().intValue()))
            .andExpect(jsonPath("$.categorie").value(DEFAULT_CATEGORIE))
            .andExpect(jsonPath("$.commentaar").value(DEFAULT_COMMENTAAR))
            .andExpect(jsonPath("$.duur").value(DEFAULT_DUUR))
            .andExpect(jsonPath("$.wet").value(DEFAULT_WET));
    }

    @Test
    @Transactional
    void getNonExistingBeperking() throws Exception {
        // Get the beperking
        restBeperkingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBeperking() throws Exception {
        // Initialize the database
        beperkingRepository.saveAndFlush(beperking);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beperking
        Beperking updatedBeperking = beperkingRepository.findById(beperking.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBeperking are not directly saved in db
        em.detach(updatedBeperking);
        updatedBeperking.categorie(UPDATED_CATEGORIE).commentaar(UPDATED_COMMENTAAR).duur(UPDATED_DUUR).wet(UPDATED_WET);

        restBeperkingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBeperking.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBeperking))
            )
            .andExpect(status().isOk());

        // Validate the Beperking in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBeperkingToMatchAllProperties(updatedBeperking);
    }

    @Test
    @Transactional
    void putNonExistingBeperking() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beperking.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeperkingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, beperking.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beperking))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beperking in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBeperking() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beperking.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeperkingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(beperking))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beperking in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBeperking() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beperking.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeperkingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(beperking)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Beperking in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBeperkingWithPatch() throws Exception {
        // Initialize the database
        beperkingRepository.saveAndFlush(beperking);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beperking using partial update
        Beperking partialUpdatedBeperking = new Beperking();
        partialUpdatedBeperking.setId(beperking.getId());

        partialUpdatedBeperking.commentaar(UPDATED_COMMENTAAR).duur(UPDATED_DUUR).wet(UPDATED_WET);

        restBeperkingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBeperking.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBeperking))
            )
            .andExpect(status().isOk());

        // Validate the Beperking in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBeperkingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBeperking, beperking),
            getPersistedBeperking(beperking)
        );
    }

    @Test
    @Transactional
    void fullUpdateBeperkingWithPatch() throws Exception {
        // Initialize the database
        beperkingRepository.saveAndFlush(beperking);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the beperking using partial update
        Beperking partialUpdatedBeperking = new Beperking();
        partialUpdatedBeperking.setId(beperking.getId());

        partialUpdatedBeperking.categorie(UPDATED_CATEGORIE).commentaar(UPDATED_COMMENTAAR).duur(UPDATED_DUUR).wet(UPDATED_WET);

        restBeperkingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBeperking.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBeperking))
            )
            .andExpect(status().isOk());

        // Validate the Beperking in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBeperkingUpdatableFieldsEquals(partialUpdatedBeperking, getPersistedBeperking(partialUpdatedBeperking));
    }

    @Test
    @Transactional
    void patchNonExistingBeperking() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beperking.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeperkingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, beperking.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(beperking))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beperking in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBeperking() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beperking.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeperkingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(beperking))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beperking in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBeperking() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        beperking.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeperkingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(beperking)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Beperking in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBeperking() throws Exception {
        // Initialize the database
        beperkingRepository.saveAndFlush(beperking);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the beperking
        restBeperkingMockMvc
            .perform(delete(ENTITY_API_URL_ID, beperking.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return beperkingRepository.count();
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

    protected Beperking getPersistedBeperking(Beperking beperking) {
        return beperkingRepository.findById(beperking.getId()).orElseThrow();
    }

    protected void assertPersistedBeperkingToMatchAllProperties(Beperking expectedBeperking) {
        assertBeperkingAllPropertiesEquals(expectedBeperking, getPersistedBeperking(expectedBeperking));
    }

    protected void assertPersistedBeperkingToMatchUpdatableProperties(Beperking expectedBeperking) {
        assertBeperkingAllUpdatablePropertiesEquals(expectedBeperking, getPersistedBeperking(expectedBeperking));
    }
}
