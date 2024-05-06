package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.NotitieAsserts.*;
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
import nl.ritense.demo.domain.Notitie;
import nl.ritense.demo.repository.NotitieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NotitieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NotitieResourceIT {

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_INHOUD = "AAAAAAAAAA";
    private static final String UPDATED_INHOUD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/notities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NotitieRepository notitieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotitieMockMvc;

    private Notitie notitie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notitie createEntity(EntityManager em) {
        Notitie notitie = new Notitie().datum(DEFAULT_DATUM).inhoud(DEFAULT_INHOUD);
        return notitie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notitie createUpdatedEntity(EntityManager em) {
        Notitie notitie = new Notitie().datum(UPDATED_DATUM).inhoud(UPDATED_INHOUD);
        return notitie;
    }

    @BeforeEach
    public void initTest() {
        notitie = createEntity(em);
    }

    @Test
    @Transactional
    void createNotitie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Notitie
        var returnedNotitie = om.readValue(
            restNotitieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(notitie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Notitie.class
        );

        // Validate the Notitie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertNotitieUpdatableFieldsEquals(returnedNotitie, getPersistedNotitie(returnedNotitie));
    }

    @Test
    @Transactional
    void createNotitieWithExistingId() throws Exception {
        // Create the Notitie with an existing ID
        notitie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotitieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(notitie)))
            .andExpect(status().isBadRequest());

        // Validate the Notitie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNotities() throws Exception {
        // Initialize the database
        notitieRepository.saveAndFlush(notitie);

        // Get all the notitieList
        restNotitieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notitie.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].inhoud").value(hasItem(DEFAULT_INHOUD)));
    }

    @Test
    @Transactional
    void getNotitie() throws Exception {
        // Initialize the database
        notitieRepository.saveAndFlush(notitie);

        // Get the notitie
        restNotitieMockMvc
            .perform(get(ENTITY_API_URL_ID, notitie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notitie.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.inhoud").value(DEFAULT_INHOUD));
    }

    @Test
    @Transactional
    void getNonExistingNotitie() throws Exception {
        // Get the notitie
        restNotitieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNotitie() throws Exception {
        // Initialize the database
        notitieRepository.saveAndFlush(notitie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the notitie
        Notitie updatedNotitie = notitieRepository.findById(notitie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNotitie are not directly saved in db
        em.detach(updatedNotitie);
        updatedNotitie.datum(UPDATED_DATUM).inhoud(UPDATED_INHOUD);

        restNotitieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNotitie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedNotitie))
            )
            .andExpect(status().isOk());

        // Validate the Notitie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNotitieToMatchAllProperties(updatedNotitie);
    }

    @Test
    @Transactional
    void putNonExistingNotitie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        notitie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotitieMockMvc
            .perform(put(ENTITY_API_URL_ID, notitie.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(notitie)))
            .andExpect(status().isBadRequest());

        // Validate the Notitie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNotitie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        notitie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotitieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(notitie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notitie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNotitie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        notitie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotitieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(notitie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Notitie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNotitieWithPatch() throws Exception {
        // Initialize the database
        notitieRepository.saveAndFlush(notitie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the notitie using partial update
        Notitie partialUpdatedNotitie = new Notitie();
        partialUpdatedNotitie.setId(notitie.getId());

        partialUpdatedNotitie.inhoud(UPDATED_INHOUD);

        restNotitieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotitie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNotitie))
            )
            .andExpect(status().isOk());

        // Validate the Notitie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNotitieUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedNotitie, notitie), getPersistedNotitie(notitie));
    }

    @Test
    @Transactional
    void fullUpdateNotitieWithPatch() throws Exception {
        // Initialize the database
        notitieRepository.saveAndFlush(notitie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the notitie using partial update
        Notitie partialUpdatedNotitie = new Notitie();
        partialUpdatedNotitie.setId(notitie.getId());

        partialUpdatedNotitie.datum(UPDATED_DATUM).inhoud(UPDATED_INHOUD);

        restNotitieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotitie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNotitie))
            )
            .andExpect(status().isOk());

        // Validate the Notitie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNotitieUpdatableFieldsEquals(partialUpdatedNotitie, getPersistedNotitie(partialUpdatedNotitie));
    }

    @Test
    @Transactional
    void patchNonExistingNotitie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        notitie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotitieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, notitie.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(notitie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notitie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNotitie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        notitie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotitieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(notitie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notitie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNotitie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        notitie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotitieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(notitie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Notitie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNotitie() throws Exception {
        // Initialize the database
        notitieRepository.saveAndFlush(notitie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the notitie
        restNotitieMockMvc
            .perform(delete(ENTITY_API_URL_ID, notitie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return notitieRepository.count();
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

    protected Notitie getPersistedNotitie(Notitie notitie) {
        return notitieRepository.findById(notitie.getId()).orElseThrow();
    }

    protected void assertPersistedNotitieToMatchAllProperties(Notitie expectedNotitie) {
        assertNotitieAllPropertiesEquals(expectedNotitie, getPersistedNotitie(expectedNotitie));
    }

    protected void assertPersistedNotitieToMatchUpdatableProperties(Notitie expectedNotitie) {
        assertNotitieAllUpdatablePropertiesEquals(expectedNotitie, getPersistedNotitie(expectedNotitie));
    }
}
