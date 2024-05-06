package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ZaalAsserts.*;
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
import nl.ritense.demo.domain.Zaal;
import nl.ritense.demo.repository.ZaalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ZaalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ZaalResourceIT {

    private static final String DEFAULT_CAPACITEIT = "AAAAAAAAAA";
    private static final String UPDATED_CAPACITEIT = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_NUMMER = "AAAAAAAAAA";
    private static final String UPDATED_NUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/zaals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ZaalRepository zaalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restZaalMockMvc;

    private Zaal zaal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Zaal createEntity(EntityManager em) {
        Zaal zaal = new Zaal().capaciteit(DEFAULT_CAPACITEIT).naam(DEFAULT_NAAM).nummer(DEFAULT_NUMMER).omschrijving(DEFAULT_OMSCHRIJVING);
        return zaal;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Zaal createUpdatedEntity(EntityManager em) {
        Zaal zaal = new Zaal().capaciteit(UPDATED_CAPACITEIT).naam(UPDATED_NAAM).nummer(UPDATED_NUMMER).omschrijving(UPDATED_OMSCHRIJVING);
        return zaal;
    }

    @BeforeEach
    public void initTest() {
        zaal = createEntity(em);
    }

    @Test
    @Transactional
    void createZaal() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Zaal
        var returnedZaal = om.readValue(
            restZaalMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zaal)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Zaal.class
        );

        // Validate the Zaal in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertZaalUpdatableFieldsEquals(returnedZaal, getPersistedZaal(returnedZaal));
    }

    @Test
    @Transactional
    void createZaalWithExistingId() throws Exception {
        // Create the Zaal with an existing ID
        zaal.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restZaalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zaal)))
            .andExpect(status().isBadRequest());

        // Validate the Zaal in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllZaals() throws Exception {
        // Initialize the database
        zaalRepository.saveAndFlush(zaal);

        // Get all the zaalList
        restZaalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zaal.getId().intValue())))
            .andExpect(jsonPath("$.[*].capaciteit").value(hasItem(DEFAULT_CAPACITEIT)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].nummer").value(hasItem(DEFAULT_NUMMER)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getZaal() throws Exception {
        // Initialize the database
        zaalRepository.saveAndFlush(zaal);

        // Get the zaal
        restZaalMockMvc
            .perform(get(ENTITY_API_URL_ID, zaal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(zaal.getId().intValue()))
            .andExpect(jsonPath("$.capaciteit").value(DEFAULT_CAPACITEIT))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.nummer").value(DEFAULT_NUMMER))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingZaal() throws Exception {
        // Get the zaal
        restZaalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingZaal() throws Exception {
        // Initialize the database
        zaalRepository.saveAndFlush(zaal);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the zaal
        Zaal updatedZaal = zaalRepository.findById(zaal.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedZaal are not directly saved in db
        em.detach(updatedZaal);
        updatedZaal.capaciteit(UPDATED_CAPACITEIT).naam(UPDATED_NAAM).nummer(UPDATED_NUMMER).omschrijving(UPDATED_OMSCHRIJVING);

        restZaalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedZaal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedZaal))
            )
            .andExpect(status().isOk());

        // Validate the Zaal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedZaalToMatchAllProperties(updatedZaal);
    }

    @Test
    @Transactional
    void putNonExistingZaal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zaal.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZaalMockMvc
            .perform(put(ENTITY_API_URL_ID, zaal.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zaal)))
            .andExpect(status().isBadRequest());

        // Validate the Zaal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchZaal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zaal.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZaalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(zaal))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zaal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamZaal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zaal.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZaalMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zaal)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Zaal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateZaalWithPatch() throws Exception {
        // Initialize the database
        zaalRepository.saveAndFlush(zaal);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the zaal using partial update
        Zaal partialUpdatedZaal = new Zaal();
        partialUpdatedZaal.setId(zaal.getId());

        partialUpdatedZaal.nummer(UPDATED_NUMMER);

        restZaalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedZaal.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedZaal))
            )
            .andExpect(status().isOk());

        // Validate the Zaal in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertZaalUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedZaal, zaal), getPersistedZaal(zaal));
    }

    @Test
    @Transactional
    void fullUpdateZaalWithPatch() throws Exception {
        // Initialize the database
        zaalRepository.saveAndFlush(zaal);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the zaal using partial update
        Zaal partialUpdatedZaal = new Zaal();
        partialUpdatedZaal.setId(zaal.getId());

        partialUpdatedZaal.capaciteit(UPDATED_CAPACITEIT).naam(UPDATED_NAAM).nummer(UPDATED_NUMMER).omschrijving(UPDATED_OMSCHRIJVING);

        restZaalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedZaal.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedZaal))
            )
            .andExpect(status().isOk());

        // Validate the Zaal in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertZaalUpdatableFieldsEquals(partialUpdatedZaal, getPersistedZaal(partialUpdatedZaal));
    }

    @Test
    @Transactional
    void patchNonExistingZaal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zaal.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZaalMockMvc
            .perform(patch(ENTITY_API_URL_ID, zaal.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(zaal)))
            .andExpect(status().isBadRequest());

        // Validate the Zaal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchZaal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zaal.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZaalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(zaal))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zaal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamZaal() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zaal.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZaalMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(zaal)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Zaal in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteZaal() throws Exception {
        // Initialize the database
        zaalRepository.saveAndFlush(zaal);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the zaal
        restZaalMockMvc
            .perform(delete(ENTITY_API_URL_ID, zaal.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return zaalRepository.count();
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

    protected Zaal getPersistedZaal(Zaal zaal) {
        return zaalRepository.findById(zaal.getId()).orElseThrow();
    }

    protected void assertPersistedZaalToMatchAllProperties(Zaal expectedZaal) {
        assertZaalAllPropertiesEquals(expectedZaal, getPersistedZaal(expectedZaal));
    }

    protected void assertPersistedZaalToMatchUpdatableProperties(Zaal expectedZaal) {
        assertZaalAllUpdatablePropertiesEquals(expectedZaal, getPersistedZaal(expectedZaal));
    }
}
