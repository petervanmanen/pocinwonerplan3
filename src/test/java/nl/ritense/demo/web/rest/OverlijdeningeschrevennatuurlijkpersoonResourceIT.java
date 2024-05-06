package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OverlijdeningeschrevennatuurlijkpersoonAsserts.*;
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
import nl.ritense.demo.domain.Overlijdeningeschrevennatuurlijkpersoon;
import nl.ritense.demo.repository.OverlijdeningeschrevennatuurlijkpersoonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OverlijdeningeschrevennatuurlijkpersoonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OverlijdeningeschrevennatuurlijkpersoonResourceIT {

    private static final String DEFAULT_BUITENLANDSEPLAATSOVERLIJDEN = "AAAAAAAAAA";
    private static final String UPDATED_BUITENLANDSEPLAATSOVERLIJDEN = "BBBBBBBBBB";

    private static final String DEFAULT_BUITENLANDSEREGIOOVERLIJDEN = "AAAAAAAAAA";
    private static final String UPDATED_BUITENLANDSEREGIOOVERLIJDEN = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMOVERLIJDEN = "AAAAAAAAAA";
    private static final String UPDATED_DATUMOVERLIJDEN = "BBBBBBBBBB";

    private static final String DEFAULT_GEMEENTEOVERLIJDEN = "AAAAAAAAAA";
    private static final String UPDATED_GEMEENTEOVERLIJDEN = "BBBBBBBBBB";

    private static final String DEFAULT_LANDOFGEBIEDOVERLIJDEN = "AAAAAAAAAA";
    private static final String UPDATED_LANDOFGEBIEDOVERLIJDEN = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVINGLOCATIEOVERLIJDEN = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVINGLOCATIEOVERLIJDEN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/overlijdeningeschrevennatuurlijkpersoons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OverlijdeningeschrevennatuurlijkpersoonRepository overlijdeningeschrevennatuurlijkpersoonRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOverlijdeningeschrevennatuurlijkpersoonMockMvc;

    private Overlijdeningeschrevennatuurlijkpersoon overlijdeningeschrevennatuurlijkpersoon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Overlijdeningeschrevennatuurlijkpersoon createEntity(EntityManager em) {
        Overlijdeningeschrevennatuurlijkpersoon overlijdeningeschrevennatuurlijkpersoon = new Overlijdeningeschrevennatuurlijkpersoon()
            .buitenlandseplaatsoverlijden(DEFAULT_BUITENLANDSEPLAATSOVERLIJDEN)
            .buitenlandseregiooverlijden(DEFAULT_BUITENLANDSEREGIOOVERLIJDEN)
            .datumoverlijden(DEFAULT_DATUMOVERLIJDEN)
            .gemeenteoverlijden(DEFAULT_GEMEENTEOVERLIJDEN)
            .landofgebiedoverlijden(DEFAULT_LANDOFGEBIEDOVERLIJDEN)
            .omschrijvinglocatieoverlijden(DEFAULT_OMSCHRIJVINGLOCATIEOVERLIJDEN);
        return overlijdeningeschrevennatuurlijkpersoon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Overlijdeningeschrevennatuurlijkpersoon createUpdatedEntity(EntityManager em) {
        Overlijdeningeschrevennatuurlijkpersoon overlijdeningeschrevennatuurlijkpersoon = new Overlijdeningeschrevennatuurlijkpersoon()
            .buitenlandseplaatsoverlijden(UPDATED_BUITENLANDSEPLAATSOVERLIJDEN)
            .buitenlandseregiooverlijden(UPDATED_BUITENLANDSEREGIOOVERLIJDEN)
            .datumoverlijden(UPDATED_DATUMOVERLIJDEN)
            .gemeenteoverlijden(UPDATED_GEMEENTEOVERLIJDEN)
            .landofgebiedoverlijden(UPDATED_LANDOFGEBIEDOVERLIJDEN)
            .omschrijvinglocatieoverlijden(UPDATED_OMSCHRIJVINGLOCATIEOVERLIJDEN);
        return overlijdeningeschrevennatuurlijkpersoon;
    }

    @BeforeEach
    public void initTest() {
        overlijdeningeschrevennatuurlijkpersoon = createEntity(em);
    }

    @Test
    @Transactional
    void createOverlijdeningeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Overlijdeningeschrevennatuurlijkpersoon
        var returnedOverlijdeningeschrevennatuurlijkpersoon = om.readValue(
            restOverlijdeningeschrevennatuurlijkpersoonMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(overlijdeningeschrevennatuurlijkpersoon))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Overlijdeningeschrevennatuurlijkpersoon.class
        );

        // Validate the Overlijdeningeschrevennatuurlijkpersoon in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOverlijdeningeschrevennatuurlijkpersoonUpdatableFieldsEquals(
            returnedOverlijdeningeschrevennatuurlijkpersoon,
            getPersistedOverlijdeningeschrevennatuurlijkpersoon(returnedOverlijdeningeschrevennatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void createOverlijdeningeschrevennatuurlijkpersoonWithExistingId() throws Exception {
        // Create the Overlijdeningeschrevennatuurlijkpersoon with an existing ID
        overlijdeningeschrevennatuurlijkpersoon.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOverlijdeningeschrevennatuurlijkpersoonMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(overlijdeningeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overlijdeningeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOverlijdeningeschrevennatuurlijkpersoons() throws Exception {
        // Initialize the database
        overlijdeningeschrevennatuurlijkpersoonRepository.saveAndFlush(overlijdeningeschrevennatuurlijkpersoon);

        // Get all the overlijdeningeschrevennatuurlijkpersoonList
        restOverlijdeningeschrevennatuurlijkpersoonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(overlijdeningeschrevennatuurlijkpersoon.getId().intValue())))
            .andExpect(jsonPath("$.[*].buitenlandseplaatsoverlijden").value(hasItem(DEFAULT_BUITENLANDSEPLAATSOVERLIJDEN)))
            .andExpect(jsonPath("$.[*].buitenlandseregiooverlijden").value(hasItem(DEFAULT_BUITENLANDSEREGIOOVERLIJDEN)))
            .andExpect(jsonPath("$.[*].datumoverlijden").value(hasItem(DEFAULT_DATUMOVERLIJDEN)))
            .andExpect(jsonPath("$.[*].gemeenteoverlijden").value(hasItem(DEFAULT_GEMEENTEOVERLIJDEN)))
            .andExpect(jsonPath("$.[*].landofgebiedoverlijden").value(hasItem(DEFAULT_LANDOFGEBIEDOVERLIJDEN)))
            .andExpect(jsonPath("$.[*].omschrijvinglocatieoverlijden").value(hasItem(DEFAULT_OMSCHRIJVINGLOCATIEOVERLIJDEN)));
    }

    @Test
    @Transactional
    void getOverlijdeningeschrevennatuurlijkpersoon() throws Exception {
        // Initialize the database
        overlijdeningeschrevennatuurlijkpersoonRepository.saveAndFlush(overlijdeningeschrevennatuurlijkpersoon);

        // Get the overlijdeningeschrevennatuurlijkpersoon
        restOverlijdeningeschrevennatuurlijkpersoonMockMvc
            .perform(get(ENTITY_API_URL_ID, overlijdeningeschrevennatuurlijkpersoon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(overlijdeningeschrevennatuurlijkpersoon.getId().intValue()))
            .andExpect(jsonPath("$.buitenlandseplaatsoverlijden").value(DEFAULT_BUITENLANDSEPLAATSOVERLIJDEN))
            .andExpect(jsonPath("$.buitenlandseregiooverlijden").value(DEFAULT_BUITENLANDSEREGIOOVERLIJDEN))
            .andExpect(jsonPath("$.datumoverlijden").value(DEFAULT_DATUMOVERLIJDEN))
            .andExpect(jsonPath("$.gemeenteoverlijden").value(DEFAULT_GEMEENTEOVERLIJDEN))
            .andExpect(jsonPath("$.landofgebiedoverlijden").value(DEFAULT_LANDOFGEBIEDOVERLIJDEN))
            .andExpect(jsonPath("$.omschrijvinglocatieoverlijden").value(DEFAULT_OMSCHRIJVINGLOCATIEOVERLIJDEN));
    }

    @Test
    @Transactional
    void getNonExistingOverlijdeningeschrevennatuurlijkpersoon() throws Exception {
        // Get the overlijdeningeschrevennatuurlijkpersoon
        restOverlijdeningeschrevennatuurlijkpersoonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOverlijdeningeschrevennatuurlijkpersoon() throws Exception {
        // Initialize the database
        overlijdeningeschrevennatuurlijkpersoonRepository.saveAndFlush(overlijdeningeschrevennatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the overlijdeningeschrevennatuurlijkpersoon
        Overlijdeningeschrevennatuurlijkpersoon updatedOverlijdeningeschrevennatuurlijkpersoon =
            overlijdeningeschrevennatuurlijkpersoonRepository.findById(overlijdeningeschrevennatuurlijkpersoon.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOverlijdeningeschrevennatuurlijkpersoon are not directly saved in db
        em.detach(updatedOverlijdeningeschrevennatuurlijkpersoon);
        updatedOverlijdeningeschrevennatuurlijkpersoon
            .buitenlandseplaatsoverlijden(UPDATED_BUITENLANDSEPLAATSOVERLIJDEN)
            .buitenlandseregiooverlijden(UPDATED_BUITENLANDSEREGIOOVERLIJDEN)
            .datumoverlijden(UPDATED_DATUMOVERLIJDEN)
            .gemeenteoverlijden(UPDATED_GEMEENTEOVERLIJDEN)
            .landofgebiedoverlijden(UPDATED_LANDOFGEBIEDOVERLIJDEN)
            .omschrijvinglocatieoverlijden(UPDATED_OMSCHRIJVINGLOCATIEOVERLIJDEN);

        restOverlijdeningeschrevennatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOverlijdeningeschrevennatuurlijkpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOverlijdeningeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Overlijdeningeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOverlijdeningeschrevennatuurlijkpersoonToMatchAllProperties(updatedOverlijdeningeschrevennatuurlijkpersoon);
    }

    @Test
    @Transactional
    void putNonExistingOverlijdeningeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overlijdeningeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOverlijdeningeschrevennatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, overlijdeningeschrevennatuurlijkpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(overlijdeningeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overlijdeningeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOverlijdeningeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overlijdeningeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverlijdeningeschrevennatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(overlijdeningeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overlijdeningeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOverlijdeningeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overlijdeningeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverlijdeningeschrevennatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(overlijdeningeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Overlijdeningeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOverlijdeningeschrevennatuurlijkpersoonWithPatch() throws Exception {
        // Initialize the database
        overlijdeningeschrevennatuurlijkpersoonRepository.saveAndFlush(overlijdeningeschrevennatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the overlijdeningeschrevennatuurlijkpersoon using partial update
        Overlijdeningeschrevennatuurlijkpersoon partialUpdatedOverlijdeningeschrevennatuurlijkpersoon =
            new Overlijdeningeschrevennatuurlijkpersoon();
        partialUpdatedOverlijdeningeschrevennatuurlijkpersoon.setId(overlijdeningeschrevennatuurlijkpersoon.getId());

        partialUpdatedOverlijdeningeschrevennatuurlijkpersoon
            .buitenlandseplaatsoverlijden(UPDATED_BUITENLANDSEPLAATSOVERLIJDEN)
            .datumoverlijden(UPDATED_DATUMOVERLIJDEN)
            .gemeenteoverlijden(UPDATED_GEMEENTEOVERLIJDEN)
            .omschrijvinglocatieoverlijden(UPDATED_OMSCHRIJVINGLOCATIEOVERLIJDEN);

        restOverlijdeningeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOverlijdeningeschrevennatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOverlijdeningeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Overlijdeningeschrevennatuurlijkpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOverlijdeningeschrevennatuurlijkpersoonUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOverlijdeningeschrevennatuurlijkpersoon, overlijdeningeschrevennatuurlijkpersoon),
            getPersistedOverlijdeningeschrevennatuurlijkpersoon(overlijdeningeschrevennatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void fullUpdateOverlijdeningeschrevennatuurlijkpersoonWithPatch() throws Exception {
        // Initialize the database
        overlijdeningeschrevennatuurlijkpersoonRepository.saveAndFlush(overlijdeningeschrevennatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the overlijdeningeschrevennatuurlijkpersoon using partial update
        Overlijdeningeschrevennatuurlijkpersoon partialUpdatedOverlijdeningeschrevennatuurlijkpersoon =
            new Overlijdeningeschrevennatuurlijkpersoon();
        partialUpdatedOverlijdeningeschrevennatuurlijkpersoon.setId(overlijdeningeschrevennatuurlijkpersoon.getId());

        partialUpdatedOverlijdeningeschrevennatuurlijkpersoon
            .buitenlandseplaatsoverlijden(UPDATED_BUITENLANDSEPLAATSOVERLIJDEN)
            .buitenlandseregiooverlijden(UPDATED_BUITENLANDSEREGIOOVERLIJDEN)
            .datumoverlijden(UPDATED_DATUMOVERLIJDEN)
            .gemeenteoverlijden(UPDATED_GEMEENTEOVERLIJDEN)
            .landofgebiedoverlijden(UPDATED_LANDOFGEBIEDOVERLIJDEN)
            .omschrijvinglocatieoverlijden(UPDATED_OMSCHRIJVINGLOCATIEOVERLIJDEN);

        restOverlijdeningeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOverlijdeningeschrevennatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOverlijdeningeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Overlijdeningeschrevennatuurlijkpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOverlijdeningeschrevennatuurlijkpersoonUpdatableFieldsEquals(
            partialUpdatedOverlijdeningeschrevennatuurlijkpersoon,
            getPersistedOverlijdeningeschrevennatuurlijkpersoon(partialUpdatedOverlijdeningeschrevennatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOverlijdeningeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overlijdeningeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOverlijdeningeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, overlijdeningeschrevennatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(overlijdeningeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overlijdeningeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOverlijdeningeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overlijdeningeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverlijdeningeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(overlijdeningeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overlijdeningeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOverlijdeningeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overlijdeningeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverlijdeningeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(overlijdeningeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Overlijdeningeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOverlijdeningeschrevennatuurlijkpersoon() throws Exception {
        // Initialize the database
        overlijdeningeschrevennatuurlijkpersoonRepository.saveAndFlush(overlijdeningeschrevennatuurlijkpersoon);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the overlijdeningeschrevennatuurlijkpersoon
        restOverlijdeningeschrevennatuurlijkpersoonMockMvc
            .perform(delete(ENTITY_API_URL_ID, overlijdeningeschrevennatuurlijkpersoon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return overlijdeningeschrevennatuurlijkpersoonRepository.count();
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

    protected Overlijdeningeschrevennatuurlijkpersoon getPersistedOverlijdeningeschrevennatuurlijkpersoon(
        Overlijdeningeschrevennatuurlijkpersoon overlijdeningeschrevennatuurlijkpersoon
    ) {
        return overlijdeningeschrevennatuurlijkpersoonRepository.findById(overlijdeningeschrevennatuurlijkpersoon.getId()).orElseThrow();
    }

    protected void assertPersistedOverlijdeningeschrevennatuurlijkpersoonToMatchAllProperties(
        Overlijdeningeschrevennatuurlijkpersoon expectedOverlijdeningeschrevennatuurlijkpersoon
    ) {
        assertOverlijdeningeschrevennatuurlijkpersoonAllPropertiesEquals(
            expectedOverlijdeningeschrevennatuurlijkpersoon,
            getPersistedOverlijdeningeschrevennatuurlijkpersoon(expectedOverlijdeningeschrevennatuurlijkpersoon)
        );
    }

    protected void assertPersistedOverlijdeningeschrevennatuurlijkpersoonToMatchUpdatableProperties(
        Overlijdeningeschrevennatuurlijkpersoon expectedOverlijdeningeschrevennatuurlijkpersoon
    ) {
        assertOverlijdeningeschrevennatuurlijkpersoonAllUpdatablePropertiesEquals(
            expectedOverlijdeningeschrevennatuurlijkpersoon,
            getPersistedOverlijdeningeschrevennatuurlijkpersoon(expectedOverlijdeningeschrevennatuurlijkpersoon)
        );
    }
}
