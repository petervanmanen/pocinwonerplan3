package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.StartkwalificatieAsserts.*;
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
import nl.ritense.demo.domain.Startkwalificatie;
import nl.ritense.demo.repository.StartkwalificatieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StartkwalificatieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StartkwalificatieResourceIT {

    private static final LocalDate DEFAULT_DATUMBEHAALD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEHAALD = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/startkwalificaties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StartkwalificatieRepository startkwalificatieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStartkwalificatieMockMvc;

    private Startkwalificatie startkwalificatie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Startkwalificatie createEntity(EntityManager em) {
        Startkwalificatie startkwalificatie = new Startkwalificatie().datumbehaald(DEFAULT_DATUMBEHAALD);
        return startkwalificatie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Startkwalificatie createUpdatedEntity(EntityManager em) {
        Startkwalificatie startkwalificatie = new Startkwalificatie().datumbehaald(UPDATED_DATUMBEHAALD);
        return startkwalificatie;
    }

    @BeforeEach
    public void initTest() {
        startkwalificatie = createEntity(em);
    }

    @Test
    @Transactional
    void createStartkwalificatie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Startkwalificatie
        var returnedStartkwalificatie = om.readValue(
            restStartkwalificatieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(startkwalificatie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Startkwalificatie.class
        );

        // Validate the Startkwalificatie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertStartkwalificatieUpdatableFieldsEquals(returnedStartkwalificatie, getPersistedStartkwalificatie(returnedStartkwalificatie));
    }

    @Test
    @Transactional
    void createStartkwalificatieWithExistingId() throws Exception {
        // Create the Startkwalificatie with an existing ID
        startkwalificatie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStartkwalificatieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(startkwalificatie)))
            .andExpect(status().isBadRequest());

        // Validate the Startkwalificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStartkwalificaties() throws Exception {
        // Initialize the database
        startkwalificatieRepository.saveAndFlush(startkwalificatie);

        // Get all the startkwalificatieList
        restStartkwalificatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(startkwalificatie.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumbehaald").value(hasItem(DEFAULT_DATUMBEHAALD.toString())));
    }

    @Test
    @Transactional
    void getStartkwalificatie() throws Exception {
        // Initialize the database
        startkwalificatieRepository.saveAndFlush(startkwalificatie);

        // Get the startkwalificatie
        restStartkwalificatieMockMvc
            .perform(get(ENTITY_API_URL_ID, startkwalificatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(startkwalificatie.getId().intValue()))
            .andExpect(jsonPath("$.datumbehaald").value(DEFAULT_DATUMBEHAALD.toString()));
    }

    @Test
    @Transactional
    void getNonExistingStartkwalificatie() throws Exception {
        // Get the startkwalificatie
        restStartkwalificatieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStartkwalificatie() throws Exception {
        // Initialize the database
        startkwalificatieRepository.saveAndFlush(startkwalificatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the startkwalificatie
        Startkwalificatie updatedStartkwalificatie = startkwalificatieRepository.findById(startkwalificatie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStartkwalificatie are not directly saved in db
        em.detach(updatedStartkwalificatie);
        updatedStartkwalificatie.datumbehaald(UPDATED_DATUMBEHAALD);

        restStartkwalificatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStartkwalificatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedStartkwalificatie))
            )
            .andExpect(status().isOk());

        // Validate the Startkwalificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStartkwalificatieToMatchAllProperties(updatedStartkwalificatie);
    }

    @Test
    @Transactional
    void putNonExistingStartkwalificatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        startkwalificatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStartkwalificatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, startkwalificatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(startkwalificatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Startkwalificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStartkwalificatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        startkwalificatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStartkwalificatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(startkwalificatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Startkwalificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStartkwalificatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        startkwalificatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStartkwalificatieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(startkwalificatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Startkwalificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStartkwalificatieWithPatch() throws Exception {
        // Initialize the database
        startkwalificatieRepository.saveAndFlush(startkwalificatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the startkwalificatie using partial update
        Startkwalificatie partialUpdatedStartkwalificatie = new Startkwalificatie();
        partialUpdatedStartkwalificatie.setId(startkwalificatie.getId());

        restStartkwalificatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStartkwalificatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStartkwalificatie))
            )
            .andExpect(status().isOk());

        // Validate the Startkwalificatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStartkwalificatieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedStartkwalificatie, startkwalificatie),
            getPersistedStartkwalificatie(startkwalificatie)
        );
    }

    @Test
    @Transactional
    void fullUpdateStartkwalificatieWithPatch() throws Exception {
        // Initialize the database
        startkwalificatieRepository.saveAndFlush(startkwalificatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the startkwalificatie using partial update
        Startkwalificatie partialUpdatedStartkwalificatie = new Startkwalificatie();
        partialUpdatedStartkwalificatie.setId(startkwalificatie.getId());

        partialUpdatedStartkwalificatie.datumbehaald(UPDATED_DATUMBEHAALD);

        restStartkwalificatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStartkwalificatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStartkwalificatie))
            )
            .andExpect(status().isOk());

        // Validate the Startkwalificatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStartkwalificatieUpdatableFieldsEquals(
            partialUpdatedStartkwalificatie,
            getPersistedStartkwalificatie(partialUpdatedStartkwalificatie)
        );
    }

    @Test
    @Transactional
    void patchNonExistingStartkwalificatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        startkwalificatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStartkwalificatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, startkwalificatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(startkwalificatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Startkwalificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStartkwalificatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        startkwalificatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStartkwalificatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(startkwalificatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Startkwalificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStartkwalificatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        startkwalificatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStartkwalificatieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(startkwalificatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Startkwalificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStartkwalificatie() throws Exception {
        // Initialize the database
        startkwalificatieRepository.saveAndFlush(startkwalificatie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the startkwalificatie
        restStartkwalificatieMockMvc
            .perform(delete(ENTITY_API_URL_ID, startkwalificatie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return startkwalificatieRepository.count();
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

    protected Startkwalificatie getPersistedStartkwalificatie(Startkwalificatie startkwalificatie) {
        return startkwalificatieRepository.findById(startkwalificatie.getId()).orElseThrow();
    }

    protected void assertPersistedStartkwalificatieToMatchAllProperties(Startkwalificatie expectedStartkwalificatie) {
        assertStartkwalificatieAllPropertiesEquals(expectedStartkwalificatie, getPersistedStartkwalificatie(expectedStartkwalificatie));
    }

    protected void assertPersistedStartkwalificatieToMatchUpdatableProperties(Startkwalificatie expectedStartkwalificatie) {
        assertStartkwalificatieAllUpdatablePropertiesEquals(
            expectedStartkwalificatie,
            getPersistedStartkwalificatie(expectedStartkwalificatie)
        );
    }
}
