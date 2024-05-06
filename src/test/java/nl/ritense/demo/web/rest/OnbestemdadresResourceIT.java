package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OnbestemdadresAsserts.*;
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
import nl.ritense.demo.domain.Onbestemdadres;
import nl.ritense.demo.repository.OnbestemdadresRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OnbestemdadresResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OnbestemdadresResourceIT {

    private static final String DEFAULT_HUISLETTER = "AAAAAAAAAA";
    private static final String UPDATED_HUISLETTER = "BBBBBBBBBB";

    private static final String DEFAULT_HUISNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_HUISNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_HUISNUMMERTOEVOEGING = "AAAAAAAAAA";
    private static final String UPDATED_HUISNUMMERTOEVOEGING = "BBBBBBBBBB";

    private static final String DEFAULT_POSTCODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_STRAATNAAM = "AAAAAAAAAA";
    private static final String UPDATED_STRAATNAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/onbestemdadres";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OnbestemdadresRepository onbestemdadresRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOnbestemdadresMockMvc;

    private Onbestemdadres onbestemdadres;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Onbestemdadres createEntity(EntityManager em) {
        Onbestemdadres onbestemdadres = new Onbestemdadres()
            .huisletter(DEFAULT_HUISLETTER)
            .huisnummer(DEFAULT_HUISNUMMER)
            .huisnummertoevoeging(DEFAULT_HUISNUMMERTOEVOEGING)
            .postcode(DEFAULT_POSTCODE)
            .straatnaam(DEFAULT_STRAATNAAM);
        return onbestemdadres;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Onbestemdadres createUpdatedEntity(EntityManager em) {
        Onbestemdadres onbestemdadres = new Onbestemdadres()
            .huisletter(UPDATED_HUISLETTER)
            .huisnummer(UPDATED_HUISNUMMER)
            .huisnummertoevoeging(UPDATED_HUISNUMMERTOEVOEGING)
            .postcode(UPDATED_POSTCODE)
            .straatnaam(UPDATED_STRAATNAAM);
        return onbestemdadres;
    }

    @BeforeEach
    public void initTest() {
        onbestemdadres = createEntity(em);
    }

    @Test
    @Transactional
    void createOnbestemdadres() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Onbestemdadres
        var returnedOnbestemdadres = om.readValue(
            restOnbestemdadresMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(onbestemdadres)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Onbestemdadres.class
        );

        // Validate the Onbestemdadres in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOnbestemdadresUpdatableFieldsEquals(returnedOnbestemdadres, getPersistedOnbestemdadres(returnedOnbestemdadres));
    }

    @Test
    @Transactional
    void createOnbestemdadresWithExistingId() throws Exception {
        // Create the Onbestemdadres with an existing ID
        onbestemdadres.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOnbestemdadresMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(onbestemdadres)))
            .andExpect(status().isBadRequest());

        // Validate the Onbestemdadres in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOnbestemdadres() throws Exception {
        // Initialize the database
        onbestemdadresRepository.saveAndFlush(onbestemdadres);

        // Get all the onbestemdadresList
        restOnbestemdadresMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(onbestemdadres.getId().intValue())))
            .andExpect(jsonPath("$.[*].huisletter").value(hasItem(DEFAULT_HUISLETTER)))
            .andExpect(jsonPath("$.[*].huisnummer").value(hasItem(DEFAULT_HUISNUMMER)))
            .andExpect(jsonPath("$.[*].huisnummertoevoeging").value(hasItem(DEFAULT_HUISNUMMERTOEVOEGING)))
            .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE)))
            .andExpect(jsonPath("$.[*].straatnaam").value(hasItem(DEFAULT_STRAATNAAM)));
    }

    @Test
    @Transactional
    void getOnbestemdadres() throws Exception {
        // Initialize the database
        onbestemdadresRepository.saveAndFlush(onbestemdadres);

        // Get the onbestemdadres
        restOnbestemdadresMockMvc
            .perform(get(ENTITY_API_URL_ID, onbestemdadres.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(onbestemdadres.getId().intValue()))
            .andExpect(jsonPath("$.huisletter").value(DEFAULT_HUISLETTER))
            .andExpect(jsonPath("$.huisnummer").value(DEFAULT_HUISNUMMER))
            .andExpect(jsonPath("$.huisnummertoevoeging").value(DEFAULT_HUISNUMMERTOEVOEGING))
            .andExpect(jsonPath("$.postcode").value(DEFAULT_POSTCODE))
            .andExpect(jsonPath("$.straatnaam").value(DEFAULT_STRAATNAAM));
    }

    @Test
    @Transactional
    void getNonExistingOnbestemdadres() throws Exception {
        // Get the onbestemdadres
        restOnbestemdadresMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOnbestemdadres() throws Exception {
        // Initialize the database
        onbestemdadresRepository.saveAndFlush(onbestemdadres);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the onbestemdadres
        Onbestemdadres updatedOnbestemdadres = onbestemdadresRepository.findById(onbestemdadres.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOnbestemdadres are not directly saved in db
        em.detach(updatedOnbestemdadres);
        updatedOnbestemdadres
            .huisletter(UPDATED_HUISLETTER)
            .huisnummer(UPDATED_HUISNUMMER)
            .huisnummertoevoeging(UPDATED_HUISNUMMERTOEVOEGING)
            .postcode(UPDATED_POSTCODE)
            .straatnaam(UPDATED_STRAATNAAM);

        restOnbestemdadresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOnbestemdadres.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOnbestemdadres))
            )
            .andExpect(status().isOk());

        // Validate the Onbestemdadres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOnbestemdadresToMatchAllProperties(updatedOnbestemdadres);
    }

    @Test
    @Transactional
    void putNonExistingOnbestemdadres() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onbestemdadres.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOnbestemdadresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, onbestemdadres.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(onbestemdadres))
            )
            .andExpect(status().isBadRequest());

        // Validate the Onbestemdadres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOnbestemdadres() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onbestemdadres.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOnbestemdadresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(onbestemdadres))
            )
            .andExpect(status().isBadRequest());

        // Validate the Onbestemdadres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOnbestemdadres() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onbestemdadres.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOnbestemdadresMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(onbestemdadres)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Onbestemdadres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOnbestemdadresWithPatch() throws Exception {
        // Initialize the database
        onbestemdadresRepository.saveAndFlush(onbestemdadres);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the onbestemdadres using partial update
        Onbestemdadres partialUpdatedOnbestemdadres = new Onbestemdadres();
        partialUpdatedOnbestemdadres.setId(onbestemdadres.getId());

        partialUpdatedOnbestemdadres.huisletter(UPDATED_HUISLETTER).postcode(UPDATED_POSTCODE);

        restOnbestemdadresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOnbestemdadres.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOnbestemdadres))
            )
            .andExpect(status().isOk());

        // Validate the Onbestemdadres in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOnbestemdadresUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOnbestemdadres, onbestemdadres),
            getPersistedOnbestemdadres(onbestemdadres)
        );
    }

    @Test
    @Transactional
    void fullUpdateOnbestemdadresWithPatch() throws Exception {
        // Initialize the database
        onbestemdadresRepository.saveAndFlush(onbestemdadres);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the onbestemdadres using partial update
        Onbestemdadres partialUpdatedOnbestemdadres = new Onbestemdadres();
        partialUpdatedOnbestemdadres.setId(onbestemdadres.getId());

        partialUpdatedOnbestemdadres
            .huisletter(UPDATED_HUISLETTER)
            .huisnummer(UPDATED_HUISNUMMER)
            .huisnummertoevoeging(UPDATED_HUISNUMMERTOEVOEGING)
            .postcode(UPDATED_POSTCODE)
            .straatnaam(UPDATED_STRAATNAAM);

        restOnbestemdadresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOnbestemdadres.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOnbestemdadres))
            )
            .andExpect(status().isOk());

        // Validate the Onbestemdadres in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOnbestemdadresUpdatableFieldsEquals(partialUpdatedOnbestemdadres, getPersistedOnbestemdadres(partialUpdatedOnbestemdadres));
    }

    @Test
    @Transactional
    void patchNonExistingOnbestemdadres() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onbestemdadres.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOnbestemdadresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, onbestemdadres.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(onbestemdadres))
            )
            .andExpect(status().isBadRequest());

        // Validate the Onbestemdadres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOnbestemdadres() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onbestemdadres.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOnbestemdadresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(onbestemdadres))
            )
            .andExpect(status().isBadRequest());

        // Validate the Onbestemdadres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOnbestemdadres() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        onbestemdadres.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOnbestemdadresMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(onbestemdadres)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Onbestemdadres in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOnbestemdadres() throws Exception {
        // Initialize the database
        onbestemdadresRepository.saveAndFlush(onbestemdadres);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the onbestemdadres
        restOnbestemdadresMockMvc
            .perform(delete(ENTITY_API_URL_ID, onbestemdadres.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return onbestemdadresRepository.count();
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

    protected Onbestemdadres getPersistedOnbestemdadres(Onbestemdadres onbestemdadres) {
        return onbestemdadresRepository.findById(onbestemdadres.getId()).orElseThrow();
    }

    protected void assertPersistedOnbestemdadresToMatchAllProperties(Onbestemdadres expectedOnbestemdadres) {
        assertOnbestemdadresAllPropertiesEquals(expectedOnbestemdadres, getPersistedOnbestemdadres(expectedOnbestemdadres));
    }

    protected void assertPersistedOnbestemdadresToMatchUpdatableProperties(Onbestemdadres expectedOnbestemdadres) {
        assertOnbestemdadresAllUpdatablePropertiesEquals(expectedOnbestemdadres, getPersistedOnbestemdadres(expectedOnbestemdadres));
    }
}
