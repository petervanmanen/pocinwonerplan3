package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OverlijdeningeschrevenpersoonAsserts.*;
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
import nl.ritense.demo.domain.Overlijdeningeschrevenpersoon;
import nl.ritense.demo.repository.OverlijdeningeschrevenpersoonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OverlijdeningeschrevenpersoonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OverlijdeningeschrevenpersoonResourceIT {

    private static final String DEFAULT_DATUMOVERLIJDEN = "AAAAAAAAAA";
    private static final String UPDATED_DATUMOVERLIJDEN = "BBBBBBBBBB";

    private static final String DEFAULT_LANDOVERLIJDEN = "AAAAAAAAAA";
    private static final String UPDATED_LANDOVERLIJDEN = "BBBBBBBBBB";

    private static final String DEFAULT_OVERLIJDENSPLAATS = "AAAAAAAAAA";
    private static final String UPDATED_OVERLIJDENSPLAATS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/overlijdeningeschrevenpersoons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OverlijdeningeschrevenpersoonRepository overlijdeningeschrevenpersoonRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOverlijdeningeschrevenpersoonMockMvc;

    private Overlijdeningeschrevenpersoon overlijdeningeschrevenpersoon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Overlijdeningeschrevenpersoon createEntity(EntityManager em) {
        Overlijdeningeschrevenpersoon overlijdeningeschrevenpersoon = new Overlijdeningeschrevenpersoon()
            .datumoverlijden(DEFAULT_DATUMOVERLIJDEN)
            .landoverlijden(DEFAULT_LANDOVERLIJDEN)
            .overlijdensplaats(DEFAULT_OVERLIJDENSPLAATS);
        return overlijdeningeschrevenpersoon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Overlijdeningeschrevenpersoon createUpdatedEntity(EntityManager em) {
        Overlijdeningeschrevenpersoon overlijdeningeschrevenpersoon = new Overlijdeningeschrevenpersoon()
            .datumoverlijden(UPDATED_DATUMOVERLIJDEN)
            .landoverlijden(UPDATED_LANDOVERLIJDEN)
            .overlijdensplaats(UPDATED_OVERLIJDENSPLAATS);
        return overlijdeningeschrevenpersoon;
    }

    @BeforeEach
    public void initTest() {
        overlijdeningeschrevenpersoon = createEntity(em);
    }

    @Test
    @Transactional
    void createOverlijdeningeschrevenpersoon() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Overlijdeningeschrevenpersoon
        var returnedOverlijdeningeschrevenpersoon = om.readValue(
            restOverlijdeningeschrevenpersoonMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(overlijdeningeschrevenpersoon))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Overlijdeningeschrevenpersoon.class
        );

        // Validate the Overlijdeningeschrevenpersoon in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOverlijdeningeschrevenpersoonUpdatableFieldsEquals(
            returnedOverlijdeningeschrevenpersoon,
            getPersistedOverlijdeningeschrevenpersoon(returnedOverlijdeningeschrevenpersoon)
        );
    }

    @Test
    @Transactional
    void createOverlijdeningeschrevenpersoonWithExistingId() throws Exception {
        // Create the Overlijdeningeschrevenpersoon with an existing ID
        overlijdeningeschrevenpersoon.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOverlijdeningeschrevenpersoonMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(overlijdeningeschrevenpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overlijdeningeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOverlijdeningeschrevenpersoons() throws Exception {
        // Initialize the database
        overlijdeningeschrevenpersoonRepository.saveAndFlush(overlijdeningeschrevenpersoon);

        // Get all the overlijdeningeschrevenpersoonList
        restOverlijdeningeschrevenpersoonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(overlijdeningeschrevenpersoon.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumoverlijden").value(hasItem(DEFAULT_DATUMOVERLIJDEN)))
            .andExpect(jsonPath("$.[*].landoverlijden").value(hasItem(DEFAULT_LANDOVERLIJDEN)))
            .andExpect(jsonPath("$.[*].overlijdensplaats").value(hasItem(DEFAULT_OVERLIJDENSPLAATS)));
    }

    @Test
    @Transactional
    void getOverlijdeningeschrevenpersoon() throws Exception {
        // Initialize the database
        overlijdeningeschrevenpersoonRepository.saveAndFlush(overlijdeningeschrevenpersoon);

        // Get the overlijdeningeschrevenpersoon
        restOverlijdeningeschrevenpersoonMockMvc
            .perform(get(ENTITY_API_URL_ID, overlijdeningeschrevenpersoon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(overlijdeningeschrevenpersoon.getId().intValue()))
            .andExpect(jsonPath("$.datumoverlijden").value(DEFAULT_DATUMOVERLIJDEN))
            .andExpect(jsonPath("$.landoverlijden").value(DEFAULT_LANDOVERLIJDEN))
            .andExpect(jsonPath("$.overlijdensplaats").value(DEFAULT_OVERLIJDENSPLAATS));
    }

    @Test
    @Transactional
    void getNonExistingOverlijdeningeschrevenpersoon() throws Exception {
        // Get the overlijdeningeschrevenpersoon
        restOverlijdeningeschrevenpersoonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOverlijdeningeschrevenpersoon() throws Exception {
        // Initialize the database
        overlijdeningeschrevenpersoonRepository.saveAndFlush(overlijdeningeschrevenpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the overlijdeningeschrevenpersoon
        Overlijdeningeschrevenpersoon updatedOverlijdeningeschrevenpersoon = overlijdeningeschrevenpersoonRepository
            .findById(overlijdeningeschrevenpersoon.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedOverlijdeningeschrevenpersoon are not directly saved in db
        em.detach(updatedOverlijdeningeschrevenpersoon);
        updatedOverlijdeningeschrevenpersoon
            .datumoverlijden(UPDATED_DATUMOVERLIJDEN)
            .landoverlijden(UPDATED_LANDOVERLIJDEN)
            .overlijdensplaats(UPDATED_OVERLIJDENSPLAATS);

        restOverlijdeningeschrevenpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOverlijdeningeschrevenpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOverlijdeningeschrevenpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Overlijdeningeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOverlijdeningeschrevenpersoonToMatchAllProperties(updatedOverlijdeningeschrevenpersoon);
    }

    @Test
    @Transactional
    void putNonExistingOverlijdeningeschrevenpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overlijdeningeschrevenpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOverlijdeningeschrevenpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, overlijdeningeschrevenpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(overlijdeningeschrevenpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overlijdeningeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOverlijdeningeschrevenpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overlijdeningeschrevenpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverlijdeningeschrevenpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(overlijdeningeschrevenpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overlijdeningeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOverlijdeningeschrevenpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overlijdeningeschrevenpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverlijdeningeschrevenpersoonMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(overlijdeningeschrevenpersoon))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Overlijdeningeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOverlijdeningeschrevenpersoonWithPatch() throws Exception {
        // Initialize the database
        overlijdeningeschrevenpersoonRepository.saveAndFlush(overlijdeningeschrevenpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the overlijdeningeschrevenpersoon using partial update
        Overlijdeningeschrevenpersoon partialUpdatedOverlijdeningeschrevenpersoon = new Overlijdeningeschrevenpersoon();
        partialUpdatedOverlijdeningeschrevenpersoon.setId(overlijdeningeschrevenpersoon.getId());

        partialUpdatedOverlijdeningeschrevenpersoon.datumoverlijden(UPDATED_DATUMOVERLIJDEN);

        restOverlijdeningeschrevenpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOverlijdeningeschrevenpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOverlijdeningeschrevenpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Overlijdeningeschrevenpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOverlijdeningeschrevenpersoonUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOverlijdeningeschrevenpersoon, overlijdeningeschrevenpersoon),
            getPersistedOverlijdeningeschrevenpersoon(overlijdeningeschrevenpersoon)
        );
    }

    @Test
    @Transactional
    void fullUpdateOverlijdeningeschrevenpersoonWithPatch() throws Exception {
        // Initialize the database
        overlijdeningeschrevenpersoonRepository.saveAndFlush(overlijdeningeschrevenpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the overlijdeningeschrevenpersoon using partial update
        Overlijdeningeschrevenpersoon partialUpdatedOverlijdeningeschrevenpersoon = new Overlijdeningeschrevenpersoon();
        partialUpdatedOverlijdeningeschrevenpersoon.setId(overlijdeningeschrevenpersoon.getId());

        partialUpdatedOverlijdeningeschrevenpersoon
            .datumoverlijden(UPDATED_DATUMOVERLIJDEN)
            .landoverlijden(UPDATED_LANDOVERLIJDEN)
            .overlijdensplaats(UPDATED_OVERLIJDENSPLAATS);

        restOverlijdeningeschrevenpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOverlijdeningeschrevenpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOverlijdeningeschrevenpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Overlijdeningeschrevenpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOverlijdeningeschrevenpersoonUpdatableFieldsEquals(
            partialUpdatedOverlijdeningeschrevenpersoon,
            getPersistedOverlijdeningeschrevenpersoon(partialUpdatedOverlijdeningeschrevenpersoon)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOverlijdeningeschrevenpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overlijdeningeschrevenpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOverlijdeningeschrevenpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, overlijdeningeschrevenpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(overlijdeningeschrevenpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overlijdeningeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOverlijdeningeschrevenpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overlijdeningeschrevenpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverlijdeningeschrevenpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(overlijdeningeschrevenpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overlijdeningeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOverlijdeningeschrevenpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overlijdeningeschrevenpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverlijdeningeschrevenpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(overlijdeningeschrevenpersoon))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Overlijdeningeschrevenpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOverlijdeningeschrevenpersoon() throws Exception {
        // Initialize the database
        overlijdeningeschrevenpersoonRepository.saveAndFlush(overlijdeningeschrevenpersoon);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the overlijdeningeschrevenpersoon
        restOverlijdeningeschrevenpersoonMockMvc
            .perform(delete(ENTITY_API_URL_ID, overlijdeningeschrevenpersoon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return overlijdeningeschrevenpersoonRepository.count();
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

    protected Overlijdeningeschrevenpersoon getPersistedOverlijdeningeschrevenpersoon(
        Overlijdeningeschrevenpersoon overlijdeningeschrevenpersoon
    ) {
        return overlijdeningeschrevenpersoonRepository.findById(overlijdeningeschrevenpersoon.getId()).orElseThrow();
    }

    protected void assertPersistedOverlijdeningeschrevenpersoonToMatchAllProperties(
        Overlijdeningeschrevenpersoon expectedOverlijdeningeschrevenpersoon
    ) {
        assertOverlijdeningeschrevenpersoonAllPropertiesEquals(
            expectedOverlijdeningeschrevenpersoon,
            getPersistedOverlijdeningeschrevenpersoon(expectedOverlijdeningeschrevenpersoon)
        );
    }

    protected void assertPersistedOverlijdeningeschrevenpersoonToMatchUpdatableProperties(
        Overlijdeningeschrevenpersoon expectedOverlijdeningeschrevenpersoon
    ) {
        assertOverlijdeningeschrevenpersoonAllUpdatablePropertiesEquals(
            expectedOverlijdeningeschrevenpersoon,
            getPersistedOverlijdeningeschrevenpersoon(expectedOverlijdeningeschrevenpersoon)
        );
    }
}
