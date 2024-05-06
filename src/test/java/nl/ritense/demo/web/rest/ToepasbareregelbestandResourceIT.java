package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ToepasbareregelbestandAsserts.*;
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
import nl.ritense.demo.domain.Toepasbareregelbestand;
import nl.ritense.demo.repository.ToepasbareregelbestandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ToepasbareregelbestandResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ToepasbareregelbestandResourceIT {

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/toepasbareregelbestands";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ToepasbareregelbestandRepository toepasbareregelbestandRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restToepasbareregelbestandMockMvc;

    private Toepasbareregelbestand toepasbareregelbestand;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Toepasbareregelbestand createEntity(EntityManager em) {
        Toepasbareregelbestand toepasbareregelbestand = new Toepasbareregelbestand()
            .datumeindegeldigheid(DEFAULT_DATUMEINDEGELDIGHEID)
            .datumstart(DEFAULT_DATUMSTART);
        return toepasbareregelbestand;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Toepasbareregelbestand createUpdatedEntity(EntityManager em) {
        Toepasbareregelbestand toepasbareregelbestand = new Toepasbareregelbestand()
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumstart(UPDATED_DATUMSTART);
        return toepasbareregelbestand;
    }

    @BeforeEach
    public void initTest() {
        toepasbareregelbestand = createEntity(em);
    }

    @Test
    @Transactional
    void createToepasbareregelbestand() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Toepasbareregelbestand
        var returnedToepasbareregelbestand = om.readValue(
            restToepasbareregelbestandMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(toepasbareregelbestand)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Toepasbareregelbestand.class
        );

        // Validate the Toepasbareregelbestand in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertToepasbareregelbestandUpdatableFieldsEquals(
            returnedToepasbareregelbestand,
            getPersistedToepasbareregelbestand(returnedToepasbareregelbestand)
        );
    }

    @Test
    @Transactional
    void createToepasbareregelbestandWithExistingId() throws Exception {
        // Create the Toepasbareregelbestand with an existing ID
        toepasbareregelbestand.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restToepasbareregelbestandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(toepasbareregelbestand)))
            .andExpect(status().isBadRequest());

        // Validate the Toepasbareregelbestand in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllToepasbareregelbestands() throws Exception {
        // Initialize the database
        toepasbareregelbestandRepository.saveAndFlush(toepasbareregelbestand);

        // Get all the toepasbareregelbestandList
        restToepasbareregelbestandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(toepasbareregelbestand.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheid").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())));
    }

    @Test
    @Transactional
    void getToepasbareregelbestand() throws Exception {
        // Initialize the database
        toepasbareregelbestandRepository.saveAndFlush(toepasbareregelbestand);

        // Get the toepasbareregelbestand
        restToepasbareregelbestandMockMvc
            .perform(get(ENTITY_API_URL_ID, toepasbareregelbestand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(toepasbareregelbestand.getId().intValue()))
            .andExpect(jsonPath("$.datumeindegeldigheid").value(DEFAULT_DATUMEINDEGELDIGHEID.toString()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()));
    }

    @Test
    @Transactional
    void getNonExistingToepasbareregelbestand() throws Exception {
        // Get the toepasbareregelbestand
        restToepasbareregelbestandMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingToepasbareregelbestand() throws Exception {
        // Initialize the database
        toepasbareregelbestandRepository.saveAndFlush(toepasbareregelbestand);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the toepasbareregelbestand
        Toepasbareregelbestand updatedToepasbareregelbestand = toepasbareregelbestandRepository
            .findById(toepasbareregelbestand.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedToepasbareregelbestand are not directly saved in db
        em.detach(updatedToepasbareregelbestand);
        updatedToepasbareregelbestand.datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID).datumstart(UPDATED_DATUMSTART);

        restToepasbareregelbestandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedToepasbareregelbestand.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedToepasbareregelbestand))
            )
            .andExpect(status().isOk());

        // Validate the Toepasbareregelbestand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedToepasbareregelbestandToMatchAllProperties(updatedToepasbareregelbestand);
    }

    @Test
    @Transactional
    void putNonExistingToepasbareregelbestand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        toepasbareregelbestand.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restToepasbareregelbestandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, toepasbareregelbestand.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(toepasbareregelbestand))
            )
            .andExpect(status().isBadRequest());

        // Validate the Toepasbareregelbestand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchToepasbareregelbestand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        toepasbareregelbestand.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restToepasbareregelbestandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(toepasbareregelbestand))
            )
            .andExpect(status().isBadRequest());

        // Validate the Toepasbareregelbestand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamToepasbareregelbestand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        toepasbareregelbestand.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restToepasbareregelbestandMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(toepasbareregelbestand)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Toepasbareregelbestand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateToepasbareregelbestandWithPatch() throws Exception {
        // Initialize the database
        toepasbareregelbestandRepository.saveAndFlush(toepasbareregelbestand);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the toepasbareregelbestand using partial update
        Toepasbareregelbestand partialUpdatedToepasbareregelbestand = new Toepasbareregelbestand();
        partialUpdatedToepasbareregelbestand.setId(toepasbareregelbestand.getId());

        partialUpdatedToepasbareregelbestand.datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID).datumstart(UPDATED_DATUMSTART);

        restToepasbareregelbestandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedToepasbareregelbestand.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedToepasbareregelbestand))
            )
            .andExpect(status().isOk());

        // Validate the Toepasbareregelbestand in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertToepasbareregelbestandUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedToepasbareregelbestand, toepasbareregelbestand),
            getPersistedToepasbareregelbestand(toepasbareregelbestand)
        );
    }

    @Test
    @Transactional
    void fullUpdateToepasbareregelbestandWithPatch() throws Exception {
        // Initialize the database
        toepasbareregelbestandRepository.saveAndFlush(toepasbareregelbestand);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the toepasbareregelbestand using partial update
        Toepasbareregelbestand partialUpdatedToepasbareregelbestand = new Toepasbareregelbestand();
        partialUpdatedToepasbareregelbestand.setId(toepasbareregelbestand.getId());

        partialUpdatedToepasbareregelbestand.datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID).datumstart(UPDATED_DATUMSTART);

        restToepasbareregelbestandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedToepasbareregelbestand.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedToepasbareregelbestand))
            )
            .andExpect(status().isOk());

        // Validate the Toepasbareregelbestand in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertToepasbareregelbestandUpdatableFieldsEquals(
            partialUpdatedToepasbareregelbestand,
            getPersistedToepasbareregelbestand(partialUpdatedToepasbareregelbestand)
        );
    }

    @Test
    @Transactional
    void patchNonExistingToepasbareregelbestand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        toepasbareregelbestand.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restToepasbareregelbestandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, toepasbareregelbestand.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(toepasbareregelbestand))
            )
            .andExpect(status().isBadRequest());

        // Validate the Toepasbareregelbestand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchToepasbareregelbestand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        toepasbareregelbestand.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restToepasbareregelbestandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(toepasbareregelbestand))
            )
            .andExpect(status().isBadRequest());

        // Validate the Toepasbareregelbestand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamToepasbareregelbestand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        toepasbareregelbestand.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restToepasbareregelbestandMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(toepasbareregelbestand))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Toepasbareregelbestand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteToepasbareregelbestand() throws Exception {
        // Initialize the database
        toepasbareregelbestandRepository.saveAndFlush(toepasbareregelbestand);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the toepasbareregelbestand
        restToepasbareregelbestandMockMvc
            .perform(delete(ENTITY_API_URL_ID, toepasbareregelbestand.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return toepasbareregelbestandRepository.count();
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

    protected Toepasbareregelbestand getPersistedToepasbareregelbestand(Toepasbareregelbestand toepasbareregelbestand) {
        return toepasbareregelbestandRepository.findById(toepasbareregelbestand.getId()).orElseThrow();
    }

    protected void assertPersistedToepasbareregelbestandToMatchAllProperties(Toepasbareregelbestand expectedToepasbareregelbestand) {
        assertToepasbareregelbestandAllPropertiesEquals(
            expectedToepasbareregelbestand,
            getPersistedToepasbareregelbestand(expectedToepasbareregelbestand)
        );
    }

    protected void assertPersistedToepasbareregelbestandToMatchUpdatableProperties(Toepasbareregelbestand expectedToepasbareregelbestand) {
        assertToepasbareregelbestandAllUpdatablePropertiesEquals(
            expectedToepasbareregelbestand,
            getPersistedToepasbareregelbestand(expectedToepasbareregelbestand)
        );
    }
}
