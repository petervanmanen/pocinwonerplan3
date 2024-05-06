package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.TenaamstellingAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Tenaamstelling;
import nl.ritense.demo.repository.TenaamstellingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TenaamstellingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TenaamstellingResourceIT {

    private static final String DEFAULT_AANDEELINRECHT = "AAAAAAAAAA";
    private static final String UPDATED_AANDEELINRECHT = "BBBBBBBBBB";

    private static final String DEFAULT_BURGERLIJKESTAATTENTIJDEVANVERKRIJGING = "AAAAAAAAAA";
    private static final String UPDATED_BURGERLIJKESTAATTENTIJDEVANVERKRIJGING = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EXPLOITANTCODE = "AAAAAAAAAA";
    private static final String UPDATED_EXPLOITANTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIETENAAMSTELLING = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIETENAAMSTELLING = "BBBBBBBBBB";

    private static final String DEFAULT_VERKLARINGINZAKEDERDENBESCHERMING = "AAAAAAAAAA";
    private static final String UPDATED_VERKLARINGINZAKEDERDENBESCHERMING = "BBBBBBBBBB";

    private static final String DEFAULT_VERKREGENNAMENSSAMENWERKINGSVERBAND = "AAAAAAAAAA";
    private static final String UPDATED_VERKREGENNAMENSSAMENWERKINGSVERBAND = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tenaamstellings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TenaamstellingRepository tenaamstellingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTenaamstellingMockMvc;

    private Tenaamstelling tenaamstelling;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tenaamstelling createEntity(EntityManager em) {
        Tenaamstelling tenaamstelling = new Tenaamstelling()
            .aandeelinrecht(DEFAULT_AANDEELINRECHT)
            .burgerlijkestaattentijdevanverkrijging(DEFAULT_BURGERLIJKESTAATTENTIJDEVANVERKRIJGING)
            .datumbegingeldigheid(DEFAULT_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(DEFAULT_DATUMEINDEGELDIGHEID)
            .exploitantcode(DEFAULT_EXPLOITANTCODE)
            .identificatietenaamstelling(DEFAULT_IDENTIFICATIETENAAMSTELLING)
            .verklaringinzakederdenbescherming(DEFAULT_VERKLARINGINZAKEDERDENBESCHERMING)
            .verkregennamenssamenwerkingsverband(DEFAULT_VERKREGENNAMENSSAMENWERKINGSVERBAND);
        return tenaamstelling;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tenaamstelling createUpdatedEntity(EntityManager em) {
        Tenaamstelling tenaamstelling = new Tenaamstelling()
            .aandeelinrecht(UPDATED_AANDEELINRECHT)
            .burgerlijkestaattentijdevanverkrijging(UPDATED_BURGERLIJKESTAATTENTIJDEVANVERKRIJGING)
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .exploitantcode(UPDATED_EXPLOITANTCODE)
            .identificatietenaamstelling(UPDATED_IDENTIFICATIETENAAMSTELLING)
            .verklaringinzakederdenbescherming(UPDATED_VERKLARINGINZAKEDERDENBESCHERMING)
            .verkregennamenssamenwerkingsverband(UPDATED_VERKREGENNAMENSSAMENWERKINGSVERBAND);
        return tenaamstelling;
    }

    @BeforeEach
    public void initTest() {
        tenaamstelling = createEntity(em);
    }

    @Test
    @Transactional
    void createTenaamstelling() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Tenaamstelling
        var returnedTenaamstelling = om.readValue(
            restTenaamstellingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tenaamstelling)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Tenaamstelling.class
        );

        // Validate the Tenaamstelling in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTenaamstellingUpdatableFieldsEquals(returnedTenaamstelling, getPersistedTenaamstelling(returnedTenaamstelling));
    }

    @Test
    @Transactional
    void createTenaamstellingWithExistingId() throws Exception {
        // Create the Tenaamstelling with an existing ID
        tenaamstelling.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTenaamstellingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tenaamstelling)))
            .andExpect(status().isBadRequest());

        // Validate the Tenaamstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTenaamstellings() throws Exception {
        // Initialize the database
        tenaamstellingRepository.saveAndFlush(tenaamstelling);

        // Get all the tenaamstellingList
        restTenaamstellingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tenaamstelling.getId().intValue())))
            .andExpect(jsonPath("$.[*].aandeelinrecht").value(hasItem(DEFAULT_AANDEELINRECHT)))
            .andExpect(
                jsonPath("$.[*].burgerlijkestaattentijdevanverkrijging").value(hasItem(DEFAULT_BURGERLIJKESTAATTENTIJDEVANVERKRIJGING))
            )
            .andExpect(jsonPath("$.[*].datumbegingeldigheid").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheid").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].exploitantcode").value(hasItem(DEFAULT_EXPLOITANTCODE)))
            .andExpect(jsonPath("$.[*].identificatietenaamstelling").value(hasItem(DEFAULT_IDENTIFICATIETENAAMSTELLING)))
            .andExpect(jsonPath("$.[*].verklaringinzakederdenbescherming").value(hasItem(DEFAULT_VERKLARINGINZAKEDERDENBESCHERMING)))
            .andExpect(jsonPath("$.[*].verkregennamenssamenwerkingsverband").value(hasItem(DEFAULT_VERKREGENNAMENSSAMENWERKINGSVERBAND)));
    }

    @Test
    @Transactional
    void getTenaamstelling() throws Exception {
        // Initialize the database
        tenaamstellingRepository.saveAndFlush(tenaamstelling);

        // Get the tenaamstelling
        restTenaamstellingMockMvc
            .perform(get(ENTITY_API_URL_ID, tenaamstelling.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tenaamstelling.getId().intValue()))
            .andExpect(jsonPath("$.aandeelinrecht").value(DEFAULT_AANDEELINRECHT))
            .andExpect(jsonPath("$.burgerlijkestaattentijdevanverkrijging").value(DEFAULT_BURGERLIJKESTAATTENTIJDEVANVERKRIJGING))
            .andExpect(jsonPath("$.datumbegingeldigheid").value(DEFAULT_DATUMBEGINGELDIGHEID.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheid").value(DEFAULT_DATUMEINDEGELDIGHEID.toString()))
            .andExpect(jsonPath("$.exploitantcode").value(DEFAULT_EXPLOITANTCODE))
            .andExpect(jsonPath("$.identificatietenaamstelling").value(DEFAULT_IDENTIFICATIETENAAMSTELLING))
            .andExpect(jsonPath("$.verklaringinzakederdenbescherming").value(DEFAULT_VERKLARINGINZAKEDERDENBESCHERMING))
            .andExpect(jsonPath("$.verkregennamenssamenwerkingsverband").value(DEFAULT_VERKREGENNAMENSSAMENWERKINGSVERBAND));
    }

    @Test
    @Transactional
    void getNonExistingTenaamstelling() throws Exception {
        // Get the tenaamstelling
        restTenaamstellingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTenaamstelling() throws Exception {
        // Initialize the database
        tenaamstellingRepository.saveAndFlush(tenaamstelling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tenaamstelling
        Tenaamstelling updatedTenaamstelling = tenaamstellingRepository.findById(tenaamstelling.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTenaamstelling are not directly saved in db
        em.detach(updatedTenaamstelling);
        updatedTenaamstelling
            .aandeelinrecht(UPDATED_AANDEELINRECHT)
            .burgerlijkestaattentijdevanverkrijging(UPDATED_BURGERLIJKESTAATTENTIJDEVANVERKRIJGING)
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .exploitantcode(UPDATED_EXPLOITANTCODE)
            .identificatietenaamstelling(UPDATED_IDENTIFICATIETENAAMSTELLING)
            .verklaringinzakederdenbescherming(UPDATED_VERKLARINGINZAKEDERDENBESCHERMING)
            .verkregennamenssamenwerkingsverband(UPDATED_VERKREGENNAMENSSAMENWERKINGSVERBAND);

        restTenaamstellingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTenaamstelling.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTenaamstelling))
            )
            .andExpect(status().isOk());

        // Validate the Tenaamstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTenaamstellingToMatchAllProperties(updatedTenaamstelling);
    }

    @Test
    @Transactional
    void putNonExistingTenaamstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tenaamstelling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTenaamstellingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tenaamstelling.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tenaamstelling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tenaamstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTenaamstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tenaamstelling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTenaamstellingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tenaamstelling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tenaamstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTenaamstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tenaamstelling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTenaamstellingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tenaamstelling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tenaamstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTenaamstellingWithPatch() throws Exception {
        // Initialize the database
        tenaamstellingRepository.saveAndFlush(tenaamstelling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tenaamstelling using partial update
        Tenaamstelling partialUpdatedTenaamstelling = new Tenaamstelling();
        partialUpdatedTenaamstelling.setId(tenaamstelling.getId());

        partialUpdatedTenaamstelling
            .aandeelinrecht(UPDATED_AANDEELINRECHT)
            .burgerlijkestaattentijdevanverkrijging(UPDATED_BURGERLIJKESTAATTENTIJDEVANVERKRIJGING)
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .exploitantcode(UPDATED_EXPLOITANTCODE)
            .verklaringinzakederdenbescherming(UPDATED_VERKLARINGINZAKEDERDENBESCHERMING)
            .verkregennamenssamenwerkingsverband(UPDATED_VERKREGENNAMENSSAMENWERKINGSVERBAND);

        restTenaamstellingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTenaamstelling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTenaamstelling))
            )
            .andExpect(status().isOk());

        // Validate the Tenaamstelling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTenaamstellingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTenaamstelling, tenaamstelling),
            getPersistedTenaamstelling(tenaamstelling)
        );
    }

    @Test
    @Transactional
    void fullUpdateTenaamstellingWithPatch() throws Exception {
        // Initialize the database
        tenaamstellingRepository.saveAndFlush(tenaamstelling);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tenaamstelling using partial update
        Tenaamstelling partialUpdatedTenaamstelling = new Tenaamstelling();
        partialUpdatedTenaamstelling.setId(tenaamstelling.getId());

        partialUpdatedTenaamstelling
            .aandeelinrecht(UPDATED_AANDEELINRECHT)
            .burgerlijkestaattentijdevanverkrijging(UPDATED_BURGERLIJKESTAATTENTIJDEVANVERKRIJGING)
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .exploitantcode(UPDATED_EXPLOITANTCODE)
            .identificatietenaamstelling(UPDATED_IDENTIFICATIETENAAMSTELLING)
            .verklaringinzakederdenbescherming(UPDATED_VERKLARINGINZAKEDERDENBESCHERMING)
            .verkregennamenssamenwerkingsverband(UPDATED_VERKREGENNAMENSSAMENWERKINGSVERBAND);

        restTenaamstellingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTenaamstelling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTenaamstelling))
            )
            .andExpect(status().isOk());

        // Validate the Tenaamstelling in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTenaamstellingUpdatableFieldsEquals(partialUpdatedTenaamstelling, getPersistedTenaamstelling(partialUpdatedTenaamstelling));
    }

    @Test
    @Transactional
    void patchNonExistingTenaamstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tenaamstelling.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTenaamstellingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tenaamstelling.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tenaamstelling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tenaamstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTenaamstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tenaamstelling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTenaamstellingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tenaamstelling))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tenaamstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTenaamstelling() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tenaamstelling.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTenaamstellingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tenaamstelling)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tenaamstelling in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTenaamstelling() throws Exception {
        // Initialize the database
        tenaamstellingRepository.saveAndFlush(tenaamstelling);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tenaamstelling
        restTenaamstellingMockMvc
            .perform(delete(ENTITY_API_URL_ID, tenaamstelling.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tenaamstellingRepository.count();
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

    protected Tenaamstelling getPersistedTenaamstelling(Tenaamstelling tenaamstelling) {
        return tenaamstellingRepository.findById(tenaamstelling.getId()).orElseThrow();
    }

    protected void assertPersistedTenaamstellingToMatchAllProperties(Tenaamstelling expectedTenaamstelling) {
        assertTenaamstellingAllPropertiesEquals(expectedTenaamstelling, getPersistedTenaamstelling(expectedTenaamstelling));
    }

    protected void assertPersistedTenaamstellingToMatchUpdatableProperties(Tenaamstelling expectedTenaamstelling) {
        assertTenaamstellingAllUpdatablePropertiesEquals(expectedTenaamstelling, getPersistedTenaamstelling(expectedTenaamstelling));
    }
}
