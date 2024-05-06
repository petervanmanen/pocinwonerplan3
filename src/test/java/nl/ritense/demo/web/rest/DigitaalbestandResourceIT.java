package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.DigitaalbestandAsserts.*;
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
import nl.ritense.demo.domain.Digitaalbestand;
import nl.ritense.demo.repository.DigitaalbestandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DigitaalbestandResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DigitaalbestandResourceIT {

    private static final String DEFAULT_BLOB = "AAAAAAAAAA";
    private static final String UPDATED_BLOB = "BBBBBBBBBB";

    private static final String DEFAULT_MIMETYPE = "AAAAAAAAAA";
    private static final String UPDATED_MIMETYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/digitaalbestands";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DigitaalbestandRepository digitaalbestandRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDigitaalbestandMockMvc;

    private Digitaalbestand digitaalbestand;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Digitaalbestand createEntity(EntityManager em) {
        Digitaalbestand digitaalbestand = new Digitaalbestand()
            .blob(DEFAULT_BLOB)
            .mimetype(DEFAULT_MIMETYPE)
            .naam(DEFAULT_NAAM)
            .omschrijving(DEFAULT_OMSCHRIJVING);
        return digitaalbestand;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Digitaalbestand createUpdatedEntity(EntityManager em) {
        Digitaalbestand digitaalbestand = new Digitaalbestand()
            .blob(UPDATED_BLOB)
            .mimetype(UPDATED_MIMETYPE)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING);
        return digitaalbestand;
    }

    @BeforeEach
    public void initTest() {
        digitaalbestand = createEntity(em);
    }

    @Test
    @Transactional
    void createDigitaalbestand() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Digitaalbestand
        var returnedDigitaalbestand = om.readValue(
            restDigitaalbestandMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(digitaalbestand)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Digitaalbestand.class
        );

        // Validate the Digitaalbestand in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDigitaalbestandUpdatableFieldsEquals(returnedDigitaalbestand, getPersistedDigitaalbestand(returnedDigitaalbestand));
    }

    @Test
    @Transactional
    void createDigitaalbestandWithExistingId() throws Exception {
        // Create the Digitaalbestand with an existing ID
        digitaalbestand.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDigitaalbestandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(digitaalbestand)))
            .andExpect(status().isBadRequest());

        // Validate the Digitaalbestand in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDigitaalbestands() throws Exception {
        // Initialize the database
        digitaalbestandRepository.saveAndFlush(digitaalbestand);

        // Get all the digitaalbestandList
        restDigitaalbestandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(digitaalbestand.getId().intValue())))
            .andExpect(jsonPath("$.[*].blob").value(hasItem(DEFAULT_BLOB)))
            .andExpect(jsonPath("$.[*].mimetype").value(hasItem(DEFAULT_MIMETYPE)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getDigitaalbestand() throws Exception {
        // Initialize the database
        digitaalbestandRepository.saveAndFlush(digitaalbestand);

        // Get the digitaalbestand
        restDigitaalbestandMockMvc
            .perform(get(ENTITY_API_URL_ID, digitaalbestand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(digitaalbestand.getId().intValue()))
            .andExpect(jsonPath("$.blob").value(DEFAULT_BLOB))
            .andExpect(jsonPath("$.mimetype").value(DEFAULT_MIMETYPE))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingDigitaalbestand() throws Exception {
        // Get the digitaalbestand
        restDigitaalbestandMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDigitaalbestand() throws Exception {
        // Initialize the database
        digitaalbestandRepository.saveAndFlush(digitaalbestand);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the digitaalbestand
        Digitaalbestand updatedDigitaalbestand = digitaalbestandRepository.findById(digitaalbestand.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDigitaalbestand are not directly saved in db
        em.detach(updatedDigitaalbestand);
        updatedDigitaalbestand.blob(UPDATED_BLOB).mimetype(UPDATED_MIMETYPE).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restDigitaalbestandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDigitaalbestand.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDigitaalbestand))
            )
            .andExpect(status().isOk());

        // Validate the Digitaalbestand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDigitaalbestandToMatchAllProperties(updatedDigitaalbestand);
    }

    @Test
    @Transactional
    void putNonExistingDigitaalbestand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        digitaalbestand.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDigitaalbestandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, digitaalbestand.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(digitaalbestand))
            )
            .andExpect(status().isBadRequest());

        // Validate the Digitaalbestand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDigitaalbestand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        digitaalbestand.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDigitaalbestandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(digitaalbestand))
            )
            .andExpect(status().isBadRequest());

        // Validate the Digitaalbestand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDigitaalbestand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        digitaalbestand.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDigitaalbestandMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(digitaalbestand)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Digitaalbestand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDigitaalbestandWithPatch() throws Exception {
        // Initialize the database
        digitaalbestandRepository.saveAndFlush(digitaalbestand);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the digitaalbestand using partial update
        Digitaalbestand partialUpdatedDigitaalbestand = new Digitaalbestand();
        partialUpdatedDigitaalbestand.setId(digitaalbestand.getId());

        partialUpdatedDigitaalbestand.blob(UPDATED_BLOB).mimetype(UPDATED_MIMETYPE);

        restDigitaalbestandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDigitaalbestand.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDigitaalbestand))
            )
            .andExpect(status().isOk());

        // Validate the Digitaalbestand in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDigitaalbestandUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDigitaalbestand, digitaalbestand),
            getPersistedDigitaalbestand(digitaalbestand)
        );
    }

    @Test
    @Transactional
    void fullUpdateDigitaalbestandWithPatch() throws Exception {
        // Initialize the database
        digitaalbestandRepository.saveAndFlush(digitaalbestand);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the digitaalbestand using partial update
        Digitaalbestand partialUpdatedDigitaalbestand = new Digitaalbestand();
        partialUpdatedDigitaalbestand.setId(digitaalbestand.getId());

        partialUpdatedDigitaalbestand.blob(UPDATED_BLOB).mimetype(UPDATED_MIMETYPE).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restDigitaalbestandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDigitaalbestand.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDigitaalbestand))
            )
            .andExpect(status().isOk());

        // Validate the Digitaalbestand in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDigitaalbestandUpdatableFieldsEquals(
            partialUpdatedDigitaalbestand,
            getPersistedDigitaalbestand(partialUpdatedDigitaalbestand)
        );
    }

    @Test
    @Transactional
    void patchNonExistingDigitaalbestand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        digitaalbestand.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDigitaalbestandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, digitaalbestand.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(digitaalbestand))
            )
            .andExpect(status().isBadRequest());

        // Validate the Digitaalbestand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDigitaalbestand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        digitaalbestand.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDigitaalbestandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(digitaalbestand))
            )
            .andExpect(status().isBadRequest());

        // Validate the Digitaalbestand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDigitaalbestand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        digitaalbestand.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDigitaalbestandMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(digitaalbestand)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Digitaalbestand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDigitaalbestand() throws Exception {
        // Initialize the database
        digitaalbestandRepository.saveAndFlush(digitaalbestand);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the digitaalbestand
        restDigitaalbestandMockMvc
            .perform(delete(ENTITY_API_URL_ID, digitaalbestand.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return digitaalbestandRepository.count();
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

    protected Digitaalbestand getPersistedDigitaalbestand(Digitaalbestand digitaalbestand) {
        return digitaalbestandRepository.findById(digitaalbestand.getId()).orElseThrow();
    }

    protected void assertPersistedDigitaalbestandToMatchAllProperties(Digitaalbestand expectedDigitaalbestand) {
        assertDigitaalbestandAllPropertiesEquals(expectedDigitaalbestand, getPersistedDigitaalbestand(expectedDigitaalbestand));
    }

    protected void assertPersistedDigitaalbestandToMatchUpdatableProperties(Digitaalbestand expectedDigitaalbestand) {
        assertDigitaalbestandAllUpdatablePropertiesEquals(expectedDigitaalbestand, getPersistedDigitaalbestand(expectedDigitaalbestand));
    }
}
