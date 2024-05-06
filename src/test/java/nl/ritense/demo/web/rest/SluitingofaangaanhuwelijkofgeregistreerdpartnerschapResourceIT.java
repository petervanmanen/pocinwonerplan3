package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SluitingofaangaanhuwelijkofgeregistreerdpartnerschapAsserts.*;
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
import nl.ritense.demo.domain.Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap;
import nl.ritense.demo.repository.SluitingofaangaanhuwelijkofgeregistreerdpartnerschapRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SluitingofaangaanhuwelijkofgeregistreerdpartnerschapResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SluitingofaangaanhuwelijkofgeregistreerdpartnerschapResourceIT {

    private static final String DEFAULT_BUITENLANDSEPLAATSAANVANG = "AAAAAAAAAA";
    private static final String UPDATED_BUITENLANDSEPLAATSAANVANG = "BBBBBBBBBB";

    private static final String DEFAULT_BUITENLANDSEREGIOAANVANG = "AAAAAAAAAA";
    private static final String UPDATED_BUITENLANDSEREGIOAANVANG = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMAANVANG = "AAAAAAAAAA";
    private static final String UPDATED_DATUMAANVANG = "BBBBBBBBBB";

    private static final String DEFAULT_GEMEENTEAANVANG = "AAAAAAAAAA";
    private static final String UPDATED_GEMEENTEAANVANG = "BBBBBBBBBB";

    private static final String DEFAULT_LANDOFGEBIEDAANVANG = "AAAAAAAAAA";
    private static final String UPDATED_LANDOFGEBIEDAANVANG = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVINGLOCATIEAANVANG = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVINGLOCATIEAANVANG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sluitingofaangaanhuwelijkofgeregistreerdpartnerschaps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SluitingofaangaanhuwelijkofgeregistreerdpartnerschapRepository sluitingofaangaanhuwelijkofgeregistreerdpartnerschapRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSluitingofaangaanhuwelijkofgeregistreerdpartnerschapMockMvc;

    private Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap sluitingofaangaanhuwelijkofgeregistreerdpartnerschap;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap createEntity(EntityManager em) {
        Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap sluitingofaangaanhuwelijkofgeregistreerdpartnerschap =
            new Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap()
                .buitenlandseplaatsaanvang(DEFAULT_BUITENLANDSEPLAATSAANVANG)
                .buitenlandseregioaanvang(DEFAULT_BUITENLANDSEREGIOAANVANG)
                .datumaanvang(DEFAULT_DATUMAANVANG)
                .gemeenteaanvang(DEFAULT_GEMEENTEAANVANG)
                .landofgebiedaanvang(DEFAULT_LANDOFGEBIEDAANVANG)
                .omschrijvinglocatieaanvang(DEFAULT_OMSCHRIJVINGLOCATIEAANVANG);
        return sluitingofaangaanhuwelijkofgeregistreerdpartnerschap;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap createUpdatedEntity(EntityManager em) {
        Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap sluitingofaangaanhuwelijkofgeregistreerdpartnerschap =
            new Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap()
                .buitenlandseplaatsaanvang(UPDATED_BUITENLANDSEPLAATSAANVANG)
                .buitenlandseregioaanvang(UPDATED_BUITENLANDSEREGIOAANVANG)
                .datumaanvang(UPDATED_DATUMAANVANG)
                .gemeenteaanvang(UPDATED_GEMEENTEAANVANG)
                .landofgebiedaanvang(UPDATED_LANDOFGEBIEDAANVANG)
                .omschrijvinglocatieaanvang(UPDATED_OMSCHRIJVINGLOCATIEAANVANG);
        return sluitingofaangaanhuwelijkofgeregistreerdpartnerschap;
    }

    @BeforeEach
    public void initTest() {
        sluitingofaangaanhuwelijkofgeregistreerdpartnerschap = createEntity(em);
    }

    @Test
    @Transactional
    void createSluitingofaangaanhuwelijkofgeregistreerdpartnerschap() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap
        var returnedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap = om.readValue(
            restSluitingofaangaanhuwelijkofgeregistreerdpartnerschapMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(sluitingofaangaanhuwelijkofgeregistreerdpartnerschap))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.class
        );

        // Validate the Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSluitingofaangaanhuwelijkofgeregistreerdpartnerschapUpdatableFieldsEquals(
            returnedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap,
            getPersistedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap(returnedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap)
        );
    }

    @Test
    @Transactional
    void createSluitingofaangaanhuwelijkofgeregistreerdpartnerschapWithExistingId() throws Exception {
        // Create the Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap with an existing ID
        sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSluitingofaangaanhuwelijkofgeregistreerdpartnerschapMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sluitingofaangaanhuwelijkofgeregistreerdpartnerschap))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSluitingofaangaanhuwelijkofgeregistreerdpartnerschaps() throws Exception {
        // Initialize the database
        sluitingofaangaanhuwelijkofgeregistreerdpartnerschapRepository.saveAndFlush(sluitingofaangaanhuwelijkofgeregistreerdpartnerschap);

        // Get all the sluitingofaangaanhuwelijkofgeregistreerdpartnerschapList
        restSluitingofaangaanhuwelijkofgeregistreerdpartnerschapMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getId().intValue())))
            .andExpect(jsonPath("$.[*].buitenlandseplaatsaanvang").value(hasItem(DEFAULT_BUITENLANDSEPLAATSAANVANG)))
            .andExpect(jsonPath("$.[*].buitenlandseregioaanvang").value(hasItem(DEFAULT_BUITENLANDSEREGIOAANVANG)))
            .andExpect(jsonPath("$.[*].datumaanvang").value(hasItem(DEFAULT_DATUMAANVANG)))
            .andExpect(jsonPath("$.[*].gemeenteaanvang").value(hasItem(DEFAULT_GEMEENTEAANVANG)))
            .andExpect(jsonPath("$.[*].landofgebiedaanvang").value(hasItem(DEFAULT_LANDOFGEBIEDAANVANG)))
            .andExpect(jsonPath("$.[*].omschrijvinglocatieaanvang").value(hasItem(DEFAULT_OMSCHRIJVINGLOCATIEAANVANG)));
    }

    @Test
    @Transactional
    void getSluitingofaangaanhuwelijkofgeregistreerdpartnerschap() throws Exception {
        // Initialize the database
        sluitingofaangaanhuwelijkofgeregistreerdpartnerschapRepository.saveAndFlush(sluitingofaangaanhuwelijkofgeregistreerdpartnerschap);

        // Get the sluitingofaangaanhuwelijkofgeregistreerdpartnerschap
        restSluitingofaangaanhuwelijkofgeregistreerdpartnerschapMockMvc
            .perform(get(ENTITY_API_URL_ID, sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getId().intValue()))
            .andExpect(jsonPath("$.buitenlandseplaatsaanvang").value(DEFAULT_BUITENLANDSEPLAATSAANVANG))
            .andExpect(jsonPath("$.buitenlandseregioaanvang").value(DEFAULT_BUITENLANDSEREGIOAANVANG))
            .andExpect(jsonPath("$.datumaanvang").value(DEFAULT_DATUMAANVANG))
            .andExpect(jsonPath("$.gemeenteaanvang").value(DEFAULT_GEMEENTEAANVANG))
            .andExpect(jsonPath("$.landofgebiedaanvang").value(DEFAULT_LANDOFGEBIEDAANVANG))
            .andExpect(jsonPath("$.omschrijvinglocatieaanvang").value(DEFAULT_OMSCHRIJVINGLOCATIEAANVANG));
    }

    @Test
    @Transactional
    void getNonExistingSluitingofaangaanhuwelijkofgeregistreerdpartnerschap() throws Exception {
        // Get the sluitingofaangaanhuwelijkofgeregistreerdpartnerschap
        restSluitingofaangaanhuwelijkofgeregistreerdpartnerschapMockMvc
            .perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSluitingofaangaanhuwelijkofgeregistreerdpartnerschap() throws Exception {
        // Initialize the database
        sluitingofaangaanhuwelijkofgeregistreerdpartnerschapRepository.saveAndFlush(sluitingofaangaanhuwelijkofgeregistreerdpartnerschap);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sluitingofaangaanhuwelijkofgeregistreerdpartnerschap
        Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap updatedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap =
            sluitingofaangaanhuwelijkofgeregistreerdpartnerschapRepository
                .findById(sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getId())
                .orElseThrow();
        // Disconnect from session so that the updates on updatedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap are not directly saved in db
        em.detach(updatedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap);
        updatedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap
            .buitenlandseplaatsaanvang(UPDATED_BUITENLANDSEPLAATSAANVANG)
            .buitenlandseregioaanvang(UPDATED_BUITENLANDSEREGIOAANVANG)
            .datumaanvang(UPDATED_DATUMAANVANG)
            .gemeenteaanvang(UPDATED_GEMEENTEAANVANG)
            .landofgebiedaanvang(UPDATED_LANDOFGEBIEDAANVANG)
            .omschrijvinglocatieaanvang(UPDATED_OMSCHRIJVINGLOCATIEAANVANG);

        restSluitingofaangaanhuwelijkofgeregistreerdpartnerschapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap))
            )
            .andExpect(status().isOk());

        // Validate the Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSluitingofaangaanhuwelijkofgeregistreerdpartnerschapToMatchAllProperties(
            updatedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap
        );
    }

    @Test
    @Transactional
    void putNonExistingSluitingofaangaanhuwelijkofgeregistreerdpartnerschap() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSluitingofaangaanhuwelijkofgeregistreerdpartnerschapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sluitingofaangaanhuwelijkofgeregistreerdpartnerschap))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSluitingofaangaanhuwelijkofgeregistreerdpartnerschap() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSluitingofaangaanhuwelijkofgeregistreerdpartnerschapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sluitingofaangaanhuwelijkofgeregistreerdpartnerschap))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSluitingofaangaanhuwelijkofgeregistreerdpartnerschap() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSluitingofaangaanhuwelijkofgeregistreerdpartnerschapMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sluitingofaangaanhuwelijkofgeregistreerdpartnerschap))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSluitingofaangaanhuwelijkofgeregistreerdpartnerschapWithPatch() throws Exception {
        // Initialize the database
        sluitingofaangaanhuwelijkofgeregistreerdpartnerschapRepository.saveAndFlush(sluitingofaangaanhuwelijkofgeregistreerdpartnerschap);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sluitingofaangaanhuwelijkofgeregistreerdpartnerschap using partial update
        Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap partialUpdatedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap =
            new Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap();
        partialUpdatedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap.setId(
            sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getId()
        );

        partialUpdatedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap
            .landofgebiedaanvang(UPDATED_LANDOFGEBIEDAANVANG)
            .omschrijvinglocatieaanvang(UPDATED_OMSCHRIJVINGLOCATIEAANVANG);

        restSluitingofaangaanhuwelijkofgeregistreerdpartnerschapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap))
            )
            .andExpect(status().isOk());

        // Validate the Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSluitingofaangaanhuwelijkofgeregistreerdpartnerschapUpdatableFieldsEquals(
            createUpdateProxyForBean(
                partialUpdatedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap,
                sluitingofaangaanhuwelijkofgeregistreerdpartnerschap
            ),
            getPersistedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap(sluitingofaangaanhuwelijkofgeregistreerdpartnerschap)
        );
    }

    @Test
    @Transactional
    void fullUpdateSluitingofaangaanhuwelijkofgeregistreerdpartnerschapWithPatch() throws Exception {
        // Initialize the database
        sluitingofaangaanhuwelijkofgeregistreerdpartnerschapRepository.saveAndFlush(sluitingofaangaanhuwelijkofgeregistreerdpartnerschap);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sluitingofaangaanhuwelijkofgeregistreerdpartnerschap using partial update
        Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap partialUpdatedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap =
            new Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap();
        partialUpdatedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap.setId(
            sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getId()
        );

        partialUpdatedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap
            .buitenlandseplaatsaanvang(UPDATED_BUITENLANDSEPLAATSAANVANG)
            .buitenlandseregioaanvang(UPDATED_BUITENLANDSEREGIOAANVANG)
            .datumaanvang(UPDATED_DATUMAANVANG)
            .gemeenteaanvang(UPDATED_GEMEENTEAANVANG)
            .landofgebiedaanvang(UPDATED_LANDOFGEBIEDAANVANG)
            .omschrijvinglocatieaanvang(UPDATED_OMSCHRIJVINGLOCATIEAANVANG);

        restSluitingofaangaanhuwelijkofgeregistreerdpartnerschapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap))
            )
            .andExpect(status().isOk());

        // Validate the Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSluitingofaangaanhuwelijkofgeregistreerdpartnerschapUpdatableFieldsEquals(
            partialUpdatedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap,
            getPersistedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap(
                partialUpdatedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap
            )
        );
    }

    @Test
    @Transactional
    void patchNonExistingSluitingofaangaanhuwelijkofgeregistreerdpartnerschap() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSluitingofaangaanhuwelijkofgeregistreerdpartnerschapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sluitingofaangaanhuwelijkofgeregistreerdpartnerschap))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSluitingofaangaanhuwelijkofgeregistreerdpartnerschap() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSluitingofaangaanhuwelijkofgeregistreerdpartnerschapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sluitingofaangaanhuwelijkofgeregistreerdpartnerschap))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSluitingofaangaanhuwelijkofgeregistreerdpartnerschap() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSluitingofaangaanhuwelijkofgeregistreerdpartnerschapMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sluitingofaangaanhuwelijkofgeregistreerdpartnerschap))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSluitingofaangaanhuwelijkofgeregistreerdpartnerschap() throws Exception {
        // Initialize the database
        sluitingofaangaanhuwelijkofgeregistreerdpartnerschapRepository.saveAndFlush(sluitingofaangaanhuwelijkofgeregistreerdpartnerschap);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sluitingofaangaanhuwelijkofgeregistreerdpartnerschap
        restSluitingofaangaanhuwelijkofgeregistreerdpartnerschapMockMvc
            .perform(
                delete(ENTITY_API_URL_ID, sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getId()).accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sluitingofaangaanhuwelijkofgeregistreerdpartnerschapRepository.count();
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

    protected Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap getPersistedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap(
        Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap sluitingofaangaanhuwelijkofgeregistreerdpartnerschap
    ) {
        return sluitingofaangaanhuwelijkofgeregistreerdpartnerschapRepository
            .findById(sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.getId())
            .orElseThrow();
    }

    protected void assertPersistedSluitingofaangaanhuwelijkofgeregistreerdpartnerschapToMatchAllProperties(
        Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap expectedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap
    ) {
        assertSluitingofaangaanhuwelijkofgeregistreerdpartnerschapAllPropertiesEquals(
            expectedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap,
            getPersistedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap(expectedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap)
        );
    }

    protected void assertPersistedSluitingofaangaanhuwelijkofgeregistreerdpartnerschapToMatchUpdatableProperties(
        Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap expectedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap
    ) {
        assertSluitingofaangaanhuwelijkofgeregistreerdpartnerschapAllUpdatablePropertiesEquals(
            expectedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap,
            getPersistedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap(expectedSluitingofaangaanhuwelijkofgeregistreerdpartnerschap)
        );
    }
}
