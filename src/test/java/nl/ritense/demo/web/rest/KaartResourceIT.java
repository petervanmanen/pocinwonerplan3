package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KaartAsserts.*;
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
import nl.ritense.demo.domain.Kaart;
import nl.ritense.demo.repository.KaartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KaartResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KaartResourceIT {

    private static final String DEFAULT_KAART = "AAAAAAAAAA";
    private static final String UPDATED_KAART = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/kaarts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KaartRepository kaartRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKaartMockMvc;

    private Kaart kaart;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kaart createEntity(EntityManager em) {
        Kaart kaart = new Kaart().kaart(DEFAULT_KAART).naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return kaart;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kaart createUpdatedEntity(EntityManager em) {
        Kaart kaart = new Kaart().kaart(UPDATED_KAART).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return kaart;
    }

    @BeforeEach
    public void initTest() {
        kaart = createEntity(em);
    }

    @Test
    @Transactional
    void createKaart() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Kaart
        var returnedKaart = om.readValue(
            restKaartMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kaart)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Kaart.class
        );

        // Validate the Kaart in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKaartUpdatableFieldsEquals(returnedKaart, getPersistedKaart(returnedKaart));
    }

    @Test
    @Transactional
    void createKaartWithExistingId() throws Exception {
        // Create the Kaart with an existing ID
        kaart.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKaartMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kaart)))
            .andExpect(status().isBadRequest());

        // Validate the Kaart in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKaarts() throws Exception {
        // Initialize the database
        kaartRepository.saveAndFlush(kaart);

        // Get all the kaartList
        restKaartMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kaart.getId().intValue())))
            .andExpect(jsonPath("$.[*].kaart").value(hasItem(DEFAULT_KAART)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getKaart() throws Exception {
        // Initialize the database
        kaartRepository.saveAndFlush(kaart);

        // Get the kaart
        restKaartMockMvc
            .perform(get(ENTITY_API_URL_ID, kaart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kaart.getId().intValue()))
            .andExpect(jsonPath("$.kaart").value(DEFAULT_KAART))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingKaart() throws Exception {
        // Get the kaart
        restKaartMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKaart() throws Exception {
        // Initialize the database
        kaartRepository.saveAndFlush(kaart);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kaart
        Kaart updatedKaart = kaartRepository.findById(kaart.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedKaart are not directly saved in db
        em.detach(updatedKaart);
        updatedKaart.kaart(UPDATED_KAART).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restKaartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKaart.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedKaart))
            )
            .andExpect(status().isOk());

        // Validate the Kaart in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedKaartToMatchAllProperties(updatedKaart);
    }

    @Test
    @Transactional
    void putNonExistingKaart() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kaart.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKaartMockMvc
            .perform(put(ENTITY_API_URL_ID, kaart.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kaart)))
            .andExpect(status().isBadRequest());

        // Validate the Kaart in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKaart() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kaart.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKaartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kaart))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kaart in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKaart() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kaart.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKaartMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kaart)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kaart in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKaartWithPatch() throws Exception {
        // Initialize the database
        kaartRepository.saveAndFlush(kaart);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kaart using partial update
        Kaart partialUpdatedKaart = new Kaart();
        partialUpdatedKaart.setId(kaart.getId());

        partialUpdatedKaart.kaart(UPDATED_KAART).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restKaartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKaart.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKaart))
            )
            .andExpect(status().isOk());

        // Validate the Kaart in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKaartUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedKaart, kaart), getPersistedKaart(kaart));
    }

    @Test
    @Transactional
    void fullUpdateKaartWithPatch() throws Exception {
        // Initialize the database
        kaartRepository.saveAndFlush(kaart);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kaart using partial update
        Kaart partialUpdatedKaart = new Kaart();
        partialUpdatedKaart.setId(kaart.getId());

        partialUpdatedKaart.kaart(UPDATED_KAART).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restKaartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKaart.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKaart))
            )
            .andExpect(status().isOk());

        // Validate the Kaart in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKaartUpdatableFieldsEquals(partialUpdatedKaart, getPersistedKaart(partialUpdatedKaart));
    }

    @Test
    @Transactional
    void patchNonExistingKaart() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kaart.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKaartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kaart.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(kaart))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kaart in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKaart() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kaart.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKaartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kaart))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kaart in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKaart() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kaart.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKaartMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(kaart)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kaart in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKaart() throws Exception {
        // Initialize the database
        kaartRepository.saveAndFlush(kaart);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the kaart
        restKaartMockMvc
            .perform(delete(ENTITY_API_URL_ID, kaart.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return kaartRepository.count();
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

    protected Kaart getPersistedKaart(Kaart kaart) {
        return kaartRepository.findById(kaart.getId()).orElseThrow();
    }

    protected void assertPersistedKaartToMatchAllProperties(Kaart expectedKaart) {
        assertKaartAllPropertiesEquals(expectedKaart, getPersistedKaart(expectedKaart));
    }

    protected void assertPersistedKaartToMatchUpdatableProperties(Kaart expectedKaart) {
        assertKaartAllUpdatablePropertiesEquals(expectedKaart, getPersistedKaart(expectedKaart));
    }
}
