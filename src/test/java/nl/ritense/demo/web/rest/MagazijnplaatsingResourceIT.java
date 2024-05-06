package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.MagazijnplaatsingAsserts.*;
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
import nl.ritense.demo.domain.Magazijnplaatsing;
import nl.ritense.demo.repository.MagazijnplaatsingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MagazijnplaatsingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MagazijnplaatsingResourceIT {

    private static final String DEFAULT_BESCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_BESCHRIJVING = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMGEPLAATST = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMGEPLAATST = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_HERKOMST = "AAAAAAAAAA";
    private static final String UPDATED_HERKOMST = "BBBBBBBBBB";

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_KEYDOOS = "AAAAAAAAAA";
    private static final String UPDATED_KEYDOOS = "BBBBBBBBBB";

    private static final String DEFAULT_KEYMAGAZIJNLOCATIE = "AAAAAAAAAA";
    private static final String UPDATED_KEYMAGAZIJNLOCATIE = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECTCD = "AAAAAAAAAA";
    private static final String UPDATED_PROJECTCD = "BBBBBBBBBB";

    private static final Boolean DEFAULT_UITGELEEND = false;
    private static final Boolean UPDATED_UITGELEEND = true;

    private static final String ENTITY_API_URL = "/api/magazijnplaatsings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MagazijnplaatsingRepository magazijnplaatsingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMagazijnplaatsingMockMvc;

    private Magazijnplaatsing magazijnplaatsing;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Magazijnplaatsing createEntity(EntityManager em) {
        Magazijnplaatsing magazijnplaatsing = new Magazijnplaatsing()
            .beschrijving(DEFAULT_BESCHRIJVING)
            .datumgeplaatst(DEFAULT_DATUMGEPLAATST)
            .herkomst(DEFAULT_HERKOMST)
            .key(DEFAULT_KEY)
            .keydoos(DEFAULT_KEYDOOS)
            .keymagazijnlocatie(DEFAULT_KEYMAGAZIJNLOCATIE)
            .projectcd(DEFAULT_PROJECTCD)
            .uitgeleend(DEFAULT_UITGELEEND);
        return magazijnplaatsing;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Magazijnplaatsing createUpdatedEntity(EntityManager em) {
        Magazijnplaatsing magazijnplaatsing = new Magazijnplaatsing()
            .beschrijving(UPDATED_BESCHRIJVING)
            .datumgeplaatst(UPDATED_DATUMGEPLAATST)
            .herkomst(UPDATED_HERKOMST)
            .key(UPDATED_KEY)
            .keydoos(UPDATED_KEYDOOS)
            .keymagazijnlocatie(UPDATED_KEYMAGAZIJNLOCATIE)
            .projectcd(UPDATED_PROJECTCD)
            .uitgeleend(UPDATED_UITGELEEND);
        return magazijnplaatsing;
    }

    @BeforeEach
    public void initTest() {
        magazijnplaatsing = createEntity(em);
    }

    @Test
    @Transactional
    void createMagazijnplaatsing() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Magazijnplaatsing
        var returnedMagazijnplaatsing = om.readValue(
            restMagazijnplaatsingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(magazijnplaatsing)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Magazijnplaatsing.class
        );

        // Validate the Magazijnplaatsing in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMagazijnplaatsingUpdatableFieldsEquals(returnedMagazijnplaatsing, getPersistedMagazijnplaatsing(returnedMagazijnplaatsing));
    }

    @Test
    @Transactional
    void createMagazijnplaatsingWithExistingId() throws Exception {
        // Create the Magazijnplaatsing with an existing ID
        magazijnplaatsing.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMagazijnplaatsingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(magazijnplaatsing)))
            .andExpect(status().isBadRequest());

        // Validate the Magazijnplaatsing in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMagazijnplaatsings() throws Exception {
        // Initialize the database
        magazijnplaatsingRepository.saveAndFlush(magazijnplaatsing);

        // Get all the magazijnplaatsingList
        restMagazijnplaatsingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(magazijnplaatsing.getId().intValue())))
            .andExpect(jsonPath("$.[*].beschrijving").value(hasItem(DEFAULT_BESCHRIJVING)))
            .andExpect(jsonPath("$.[*].datumgeplaatst").value(hasItem(DEFAULT_DATUMGEPLAATST.toString())))
            .andExpect(jsonPath("$.[*].herkomst").value(hasItem(DEFAULT_HERKOMST)))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].keydoos").value(hasItem(DEFAULT_KEYDOOS)))
            .andExpect(jsonPath("$.[*].keymagazijnlocatie").value(hasItem(DEFAULT_KEYMAGAZIJNLOCATIE)))
            .andExpect(jsonPath("$.[*].projectcd").value(hasItem(DEFAULT_PROJECTCD)))
            .andExpect(jsonPath("$.[*].uitgeleend").value(hasItem(DEFAULT_UITGELEEND.booleanValue())));
    }

    @Test
    @Transactional
    void getMagazijnplaatsing() throws Exception {
        // Initialize the database
        magazijnplaatsingRepository.saveAndFlush(magazijnplaatsing);

        // Get the magazijnplaatsing
        restMagazijnplaatsingMockMvc
            .perform(get(ENTITY_API_URL_ID, magazijnplaatsing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(magazijnplaatsing.getId().intValue()))
            .andExpect(jsonPath("$.beschrijving").value(DEFAULT_BESCHRIJVING))
            .andExpect(jsonPath("$.datumgeplaatst").value(DEFAULT_DATUMGEPLAATST.toString()))
            .andExpect(jsonPath("$.herkomst").value(DEFAULT_HERKOMST))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.keydoos").value(DEFAULT_KEYDOOS))
            .andExpect(jsonPath("$.keymagazijnlocatie").value(DEFAULT_KEYMAGAZIJNLOCATIE))
            .andExpect(jsonPath("$.projectcd").value(DEFAULT_PROJECTCD))
            .andExpect(jsonPath("$.uitgeleend").value(DEFAULT_UITGELEEND.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingMagazijnplaatsing() throws Exception {
        // Get the magazijnplaatsing
        restMagazijnplaatsingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMagazijnplaatsing() throws Exception {
        // Initialize the database
        magazijnplaatsingRepository.saveAndFlush(magazijnplaatsing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the magazijnplaatsing
        Magazijnplaatsing updatedMagazijnplaatsing = magazijnplaatsingRepository.findById(magazijnplaatsing.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMagazijnplaatsing are not directly saved in db
        em.detach(updatedMagazijnplaatsing);
        updatedMagazijnplaatsing
            .beschrijving(UPDATED_BESCHRIJVING)
            .datumgeplaatst(UPDATED_DATUMGEPLAATST)
            .herkomst(UPDATED_HERKOMST)
            .key(UPDATED_KEY)
            .keydoos(UPDATED_KEYDOOS)
            .keymagazijnlocatie(UPDATED_KEYMAGAZIJNLOCATIE)
            .projectcd(UPDATED_PROJECTCD)
            .uitgeleend(UPDATED_UITGELEEND);

        restMagazijnplaatsingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMagazijnplaatsing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMagazijnplaatsing))
            )
            .andExpect(status().isOk());

        // Validate the Magazijnplaatsing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMagazijnplaatsingToMatchAllProperties(updatedMagazijnplaatsing);
    }

    @Test
    @Transactional
    void putNonExistingMagazijnplaatsing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        magazijnplaatsing.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMagazijnplaatsingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, magazijnplaatsing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(magazijnplaatsing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Magazijnplaatsing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMagazijnplaatsing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        magazijnplaatsing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMagazijnplaatsingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(magazijnplaatsing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Magazijnplaatsing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMagazijnplaatsing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        magazijnplaatsing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMagazijnplaatsingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(magazijnplaatsing)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Magazijnplaatsing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMagazijnplaatsingWithPatch() throws Exception {
        // Initialize the database
        magazijnplaatsingRepository.saveAndFlush(magazijnplaatsing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the magazijnplaatsing using partial update
        Magazijnplaatsing partialUpdatedMagazijnplaatsing = new Magazijnplaatsing();
        partialUpdatedMagazijnplaatsing.setId(magazijnplaatsing.getId());

        partialUpdatedMagazijnplaatsing
            .beschrijving(UPDATED_BESCHRIJVING)
            .herkomst(UPDATED_HERKOMST)
            .keydoos(UPDATED_KEYDOOS)
            .projectcd(UPDATED_PROJECTCD)
            .uitgeleend(UPDATED_UITGELEEND);

        restMagazijnplaatsingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMagazijnplaatsing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMagazijnplaatsing))
            )
            .andExpect(status().isOk());

        // Validate the Magazijnplaatsing in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMagazijnplaatsingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMagazijnplaatsing, magazijnplaatsing),
            getPersistedMagazijnplaatsing(magazijnplaatsing)
        );
    }

    @Test
    @Transactional
    void fullUpdateMagazijnplaatsingWithPatch() throws Exception {
        // Initialize the database
        magazijnplaatsingRepository.saveAndFlush(magazijnplaatsing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the magazijnplaatsing using partial update
        Magazijnplaatsing partialUpdatedMagazijnplaatsing = new Magazijnplaatsing();
        partialUpdatedMagazijnplaatsing.setId(magazijnplaatsing.getId());

        partialUpdatedMagazijnplaatsing
            .beschrijving(UPDATED_BESCHRIJVING)
            .datumgeplaatst(UPDATED_DATUMGEPLAATST)
            .herkomst(UPDATED_HERKOMST)
            .key(UPDATED_KEY)
            .keydoos(UPDATED_KEYDOOS)
            .keymagazijnlocatie(UPDATED_KEYMAGAZIJNLOCATIE)
            .projectcd(UPDATED_PROJECTCD)
            .uitgeleend(UPDATED_UITGELEEND);

        restMagazijnplaatsingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMagazijnplaatsing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMagazijnplaatsing))
            )
            .andExpect(status().isOk());

        // Validate the Magazijnplaatsing in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMagazijnplaatsingUpdatableFieldsEquals(
            partialUpdatedMagazijnplaatsing,
            getPersistedMagazijnplaatsing(partialUpdatedMagazijnplaatsing)
        );
    }

    @Test
    @Transactional
    void patchNonExistingMagazijnplaatsing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        magazijnplaatsing.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMagazijnplaatsingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, magazijnplaatsing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(magazijnplaatsing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Magazijnplaatsing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMagazijnplaatsing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        magazijnplaatsing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMagazijnplaatsingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(magazijnplaatsing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Magazijnplaatsing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMagazijnplaatsing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        magazijnplaatsing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMagazijnplaatsingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(magazijnplaatsing)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Magazijnplaatsing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMagazijnplaatsing() throws Exception {
        // Initialize the database
        magazijnplaatsingRepository.saveAndFlush(magazijnplaatsing);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the magazijnplaatsing
        restMagazijnplaatsingMockMvc
            .perform(delete(ENTITY_API_URL_ID, magazijnplaatsing.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return magazijnplaatsingRepository.count();
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

    protected Magazijnplaatsing getPersistedMagazijnplaatsing(Magazijnplaatsing magazijnplaatsing) {
        return magazijnplaatsingRepository.findById(magazijnplaatsing.getId()).orElseThrow();
    }

    protected void assertPersistedMagazijnplaatsingToMatchAllProperties(Magazijnplaatsing expectedMagazijnplaatsing) {
        assertMagazijnplaatsingAllPropertiesEquals(expectedMagazijnplaatsing, getPersistedMagazijnplaatsing(expectedMagazijnplaatsing));
    }

    protected void assertPersistedMagazijnplaatsingToMatchUpdatableProperties(Magazijnplaatsing expectedMagazijnplaatsing) {
        assertMagazijnplaatsingAllUpdatablePropertiesEquals(
            expectedMagazijnplaatsing,
            getPersistedMagazijnplaatsing(expectedMagazijnplaatsing)
        );
    }
}
