package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OpschortingzaakAsserts.*;
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
import nl.ritense.demo.domain.Opschortingzaak;
import nl.ritense.demo.repository.OpschortingzaakRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OpschortingzaakResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OpschortingzaakResourceIT {

    private static final String DEFAULT_INDICATIEOPSCHORTING = "AAAAAAAAAA";
    private static final String UPDATED_INDICATIEOPSCHORTING = "BBBBBBBBBB";

    private static final String DEFAULT_REDENOPSCHORTING = "AAAAAAAAAA";
    private static final String UPDATED_REDENOPSCHORTING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/opschortingzaaks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OpschortingzaakRepository opschortingzaakRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOpschortingzaakMockMvc;

    private Opschortingzaak opschortingzaak;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Opschortingzaak createEntity(EntityManager em) {
        Opschortingzaak opschortingzaak = new Opschortingzaak()
            .indicatieopschorting(DEFAULT_INDICATIEOPSCHORTING)
            .redenopschorting(DEFAULT_REDENOPSCHORTING);
        return opschortingzaak;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Opschortingzaak createUpdatedEntity(EntityManager em) {
        Opschortingzaak opschortingzaak = new Opschortingzaak()
            .indicatieopschorting(UPDATED_INDICATIEOPSCHORTING)
            .redenopschorting(UPDATED_REDENOPSCHORTING);
        return opschortingzaak;
    }

    @BeforeEach
    public void initTest() {
        opschortingzaak = createEntity(em);
    }

    @Test
    @Transactional
    void createOpschortingzaak() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Opschortingzaak
        var returnedOpschortingzaak = om.readValue(
            restOpschortingzaakMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(opschortingzaak)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Opschortingzaak.class
        );

        // Validate the Opschortingzaak in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOpschortingzaakUpdatableFieldsEquals(returnedOpschortingzaak, getPersistedOpschortingzaak(returnedOpschortingzaak));
    }

    @Test
    @Transactional
    void createOpschortingzaakWithExistingId() throws Exception {
        // Create the Opschortingzaak with an existing ID
        opschortingzaak.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOpschortingzaakMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(opschortingzaak)))
            .andExpect(status().isBadRequest());

        // Validate the Opschortingzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOpschortingzaaks() throws Exception {
        // Initialize the database
        opschortingzaakRepository.saveAndFlush(opschortingzaak);

        // Get all the opschortingzaakList
        restOpschortingzaakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(opschortingzaak.getId().intValue())))
            .andExpect(jsonPath("$.[*].indicatieopschorting").value(hasItem(DEFAULT_INDICATIEOPSCHORTING)))
            .andExpect(jsonPath("$.[*].redenopschorting").value(hasItem(DEFAULT_REDENOPSCHORTING)));
    }

    @Test
    @Transactional
    void getOpschortingzaak() throws Exception {
        // Initialize the database
        opschortingzaakRepository.saveAndFlush(opschortingzaak);

        // Get the opschortingzaak
        restOpschortingzaakMockMvc
            .perform(get(ENTITY_API_URL_ID, opschortingzaak.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(opschortingzaak.getId().intValue()))
            .andExpect(jsonPath("$.indicatieopschorting").value(DEFAULT_INDICATIEOPSCHORTING))
            .andExpect(jsonPath("$.redenopschorting").value(DEFAULT_REDENOPSCHORTING));
    }

    @Test
    @Transactional
    void getNonExistingOpschortingzaak() throws Exception {
        // Get the opschortingzaak
        restOpschortingzaakMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOpschortingzaak() throws Exception {
        // Initialize the database
        opschortingzaakRepository.saveAndFlush(opschortingzaak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the opschortingzaak
        Opschortingzaak updatedOpschortingzaak = opschortingzaakRepository.findById(opschortingzaak.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOpschortingzaak are not directly saved in db
        em.detach(updatedOpschortingzaak);
        updatedOpschortingzaak.indicatieopschorting(UPDATED_INDICATIEOPSCHORTING).redenopschorting(UPDATED_REDENOPSCHORTING);

        restOpschortingzaakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOpschortingzaak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOpschortingzaak))
            )
            .andExpect(status().isOk());

        // Validate the Opschortingzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOpschortingzaakToMatchAllProperties(updatedOpschortingzaak);
    }

    @Test
    @Transactional
    void putNonExistingOpschortingzaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opschortingzaak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpschortingzaakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, opschortingzaak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(opschortingzaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opschortingzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOpschortingzaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opschortingzaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpschortingzaakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(opschortingzaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opschortingzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOpschortingzaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opschortingzaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpschortingzaakMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(opschortingzaak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Opschortingzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOpschortingzaakWithPatch() throws Exception {
        // Initialize the database
        opschortingzaakRepository.saveAndFlush(opschortingzaak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the opschortingzaak using partial update
        Opschortingzaak partialUpdatedOpschortingzaak = new Opschortingzaak();
        partialUpdatedOpschortingzaak.setId(opschortingzaak.getId());

        partialUpdatedOpschortingzaak.indicatieopschorting(UPDATED_INDICATIEOPSCHORTING).redenopschorting(UPDATED_REDENOPSCHORTING);

        restOpschortingzaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpschortingzaak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOpschortingzaak))
            )
            .andExpect(status().isOk());

        // Validate the Opschortingzaak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOpschortingzaakUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOpschortingzaak, opschortingzaak),
            getPersistedOpschortingzaak(opschortingzaak)
        );
    }

    @Test
    @Transactional
    void fullUpdateOpschortingzaakWithPatch() throws Exception {
        // Initialize the database
        opschortingzaakRepository.saveAndFlush(opschortingzaak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the opschortingzaak using partial update
        Opschortingzaak partialUpdatedOpschortingzaak = new Opschortingzaak();
        partialUpdatedOpschortingzaak.setId(opschortingzaak.getId());

        partialUpdatedOpschortingzaak.indicatieopschorting(UPDATED_INDICATIEOPSCHORTING).redenopschorting(UPDATED_REDENOPSCHORTING);

        restOpschortingzaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpschortingzaak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOpschortingzaak))
            )
            .andExpect(status().isOk());

        // Validate the Opschortingzaak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOpschortingzaakUpdatableFieldsEquals(
            partialUpdatedOpschortingzaak,
            getPersistedOpschortingzaak(partialUpdatedOpschortingzaak)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOpschortingzaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opschortingzaak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpschortingzaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, opschortingzaak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(opschortingzaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opschortingzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOpschortingzaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opschortingzaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpschortingzaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(opschortingzaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opschortingzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOpschortingzaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opschortingzaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpschortingzaakMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(opschortingzaak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Opschortingzaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOpschortingzaak() throws Exception {
        // Initialize the database
        opschortingzaakRepository.saveAndFlush(opschortingzaak);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the opschortingzaak
        restOpschortingzaakMockMvc
            .perform(delete(ENTITY_API_URL_ID, opschortingzaak.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return opschortingzaakRepository.count();
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

    protected Opschortingzaak getPersistedOpschortingzaak(Opschortingzaak opschortingzaak) {
        return opschortingzaakRepository.findById(opschortingzaak.getId()).orElseThrow();
    }

    protected void assertPersistedOpschortingzaakToMatchAllProperties(Opschortingzaak expectedOpschortingzaak) {
        assertOpschortingzaakAllPropertiesEquals(expectedOpschortingzaak, getPersistedOpschortingzaak(expectedOpschortingzaak));
    }

    protected void assertPersistedOpschortingzaakToMatchUpdatableProperties(Opschortingzaak expectedOpschortingzaak) {
        assertOpschortingzaakAllUpdatablePropertiesEquals(expectedOpschortingzaak, getPersistedOpschortingzaak(expectedOpschortingzaak));
    }
}
