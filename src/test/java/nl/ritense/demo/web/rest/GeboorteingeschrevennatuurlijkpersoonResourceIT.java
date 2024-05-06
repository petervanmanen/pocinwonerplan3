package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.GeboorteingeschrevennatuurlijkpersoonAsserts.*;
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
import nl.ritense.demo.domain.Geboorteingeschrevennatuurlijkpersoon;
import nl.ritense.demo.repository.GeboorteingeschrevennatuurlijkpersoonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GeboorteingeschrevennatuurlijkpersoonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GeboorteingeschrevennatuurlijkpersoonResourceIT {

    private static final String DEFAULT_BUITENLANDSEPLAATSGEBOORTE = "AAAAAAAAAA";
    private static final String UPDATED_BUITENLANDSEPLAATSGEBOORTE = "BBBBBBBBBB";

    private static final String DEFAULT_BUITENLANDSEREGIOGEBOORTE = "AAAAAAAAAA";
    private static final String UPDATED_BUITENLANDSEREGIOGEBOORTE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMGEBOORTE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMGEBOORTE = "BBBBBBBBBB";

    private static final String DEFAULT_GEMEENTEGEBOORTE = "AAAAAAAAAA";
    private static final String UPDATED_GEMEENTEGEBOORTE = "BBBBBBBBBB";

    private static final String DEFAULT_LANDOFGEBIEDGEBOORTE = "AAAAAAAAAA";
    private static final String UPDATED_LANDOFGEBIEDGEBOORTE = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVINGLOCATIEGEBOORTE = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVINGLOCATIEGEBOORTE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/geboorteingeschrevennatuurlijkpersoons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GeboorteingeschrevennatuurlijkpersoonRepository geboorteingeschrevennatuurlijkpersoonRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGeboorteingeschrevennatuurlijkpersoonMockMvc;

    private Geboorteingeschrevennatuurlijkpersoon geboorteingeschrevennatuurlijkpersoon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Geboorteingeschrevennatuurlijkpersoon createEntity(EntityManager em) {
        Geboorteingeschrevennatuurlijkpersoon geboorteingeschrevennatuurlijkpersoon = new Geboorteingeschrevennatuurlijkpersoon()
            .buitenlandseplaatsgeboorte(DEFAULT_BUITENLANDSEPLAATSGEBOORTE)
            .buitenlandseregiogeboorte(DEFAULT_BUITENLANDSEREGIOGEBOORTE)
            .datumgeboorte(DEFAULT_DATUMGEBOORTE)
            .gemeentegeboorte(DEFAULT_GEMEENTEGEBOORTE)
            .landofgebiedgeboorte(DEFAULT_LANDOFGEBIEDGEBOORTE)
            .omschrijvinglocatiegeboorte(DEFAULT_OMSCHRIJVINGLOCATIEGEBOORTE);
        return geboorteingeschrevennatuurlijkpersoon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Geboorteingeschrevennatuurlijkpersoon createUpdatedEntity(EntityManager em) {
        Geboorteingeschrevennatuurlijkpersoon geboorteingeschrevennatuurlijkpersoon = new Geboorteingeschrevennatuurlijkpersoon()
            .buitenlandseplaatsgeboorte(UPDATED_BUITENLANDSEPLAATSGEBOORTE)
            .buitenlandseregiogeboorte(UPDATED_BUITENLANDSEREGIOGEBOORTE)
            .datumgeboorte(UPDATED_DATUMGEBOORTE)
            .gemeentegeboorte(UPDATED_GEMEENTEGEBOORTE)
            .landofgebiedgeboorte(UPDATED_LANDOFGEBIEDGEBOORTE)
            .omschrijvinglocatiegeboorte(UPDATED_OMSCHRIJVINGLOCATIEGEBOORTE);
        return geboorteingeschrevennatuurlijkpersoon;
    }

    @BeforeEach
    public void initTest() {
        geboorteingeschrevennatuurlijkpersoon = createEntity(em);
    }

    @Test
    @Transactional
    void createGeboorteingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Geboorteingeschrevennatuurlijkpersoon
        var returnedGeboorteingeschrevennatuurlijkpersoon = om.readValue(
            restGeboorteingeschrevennatuurlijkpersoonMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(geboorteingeschrevennatuurlijkpersoon))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Geboorteingeschrevennatuurlijkpersoon.class
        );

        // Validate the Geboorteingeschrevennatuurlijkpersoon in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGeboorteingeschrevennatuurlijkpersoonUpdatableFieldsEquals(
            returnedGeboorteingeschrevennatuurlijkpersoon,
            getPersistedGeboorteingeschrevennatuurlijkpersoon(returnedGeboorteingeschrevennatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void createGeboorteingeschrevennatuurlijkpersoonWithExistingId() throws Exception {
        // Create the Geboorteingeschrevennatuurlijkpersoon with an existing ID
        geboorteingeschrevennatuurlijkpersoon.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeboorteingeschrevennatuurlijkpersoonMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(geboorteingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Geboorteingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGeboorteingeschrevennatuurlijkpersoons() throws Exception {
        // Initialize the database
        geboorteingeschrevennatuurlijkpersoonRepository.saveAndFlush(geboorteingeschrevennatuurlijkpersoon);

        // Get all the geboorteingeschrevennatuurlijkpersoonList
        restGeboorteingeschrevennatuurlijkpersoonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geboorteingeschrevennatuurlijkpersoon.getId().intValue())))
            .andExpect(jsonPath("$.[*].buitenlandseplaatsgeboorte").value(hasItem(DEFAULT_BUITENLANDSEPLAATSGEBOORTE)))
            .andExpect(jsonPath("$.[*].buitenlandseregiogeboorte").value(hasItem(DEFAULT_BUITENLANDSEREGIOGEBOORTE)))
            .andExpect(jsonPath("$.[*].datumgeboorte").value(hasItem(DEFAULT_DATUMGEBOORTE)))
            .andExpect(jsonPath("$.[*].gemeentegeboorte").value(hasItem(DEFAULT_GEMEENTEGEBOORTE)))
            .andExpect(jsonPath("$.[*].landofgebiedgeboorte").value(hasItem(DEFAULT_LANDOFGEBIEDGEBOORTE)))
            .andExpect(jsonPath("$.[*].omschrijvinglocatiegeboorte").value(hasItem(DEFAULT_OMSCHRIJVINGLOCATIEGEBOORTE)));
    }

    @Test
    @Transactional
    void getGeboorteingeschrevennatuurlijkpersoon() throws Exception {
        // Initialize the database
        geboorteingeschrevennatuurlijkpersoonRepository.saveAndFlush(geboorteingeschrevennatuurlijkpersoon);

        // Get the geboorteingeschrevennatuurlijkpersoon
        restGeboorteingeschrevennatuurlijkpersoonMockMvc
            .perform(get(ENTITY_API_URL_ID, geboorteingeschrevennatuurlijkpersoon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(geboorteingeschrevennatuurlijkpersoon.getId().intValue()))
            .andExpect(jsonPath("$.buitenlandseplaatsgeboorte").value(DEFAULT_BUITENLANDSEPLAATSGEBOORTE))
            .andExpect(jsonPath("$.buitenlandseregiogeboorte").value(DEFAULT_BUITENLANDSEREGIOGEBOORTE))
            .andExpect(jsonPath("$.datumgeboorte").value(DEFAULT_DATUMGEBOORTE))
            .andExpect(jsonPath("$.gemeentegeboorte").value(DEFAULT_GEMEENTEGEBOORTE))
            .andExpect(jsonPath("$.landofgebiedgeboorte").value(DEFAULT_LANDOFGEBIEDGEBOORTE))
            .andExpect(jsonPath("$.omschrijvinglocatiegeboorte").value(DEFAULT_OMSCHRIJVINGLOCATIEGEBOORTE));
    }

    @Test
    @Transactional
    void getNonExistingGeboorteingeschrevennatuurlijkpersoon() throws Exception {
        // Get the geboorteingeschrevennatuurlijkpersoon
        restGeboorteingeschrevennatuurlijkpersoonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGeboorteingeschrevennatuurlijkpersoon() throws Exception {
        // Initialize the database
        geboorteingeschrevennatuurlijkpersoonRepository.saveAndFlush(geboorteingeschrevennatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the geboorteingeschrevennatuurlijkpersoon
        Geboorteingeschrevennatuurlijkpersoon updatedGeboorteingeschrevennatuurlijkpersoon = geboorteingeschrevennatuurlijkpersoonRepository
            .findById(geboorteingeschrevennatuurlijkpersoon.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedGeboorteingeschrevennatuurlijkpersoon are not directly saved in db
        em.detach(updatedGeboorteingeschrevennatuurlijkpersoon);
        updatedGeboorteingeschrevennatuurlijkpersoon
            .buitenlandseplaatsgeboorte(UPDATED_BUITENLANDSEPLAATSGEBOORTE)
            .buitenlandseregiogeboorte(UPDATED_BUITENLANDSEREGIOGEBOORTE)
            .datumgeboorte(UPDATED_DATUMGEBOORTE)
            .gemeentegeboorte(UPDATED_GEMEENTEGEBOORTE)
            .landofgebiedgeboorte(UPDATED_LANDOFGEBIEDGEBOORTE)
            .omschrijvinglocatiegeboorte(UPDATED_OMSCHRIJVINGLOCATIEGEBOORTE);

        restGeboorteingeschrevennatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGeboorteingeschrevennatuurlijkpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGeboorteingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Geboorteingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGeboorteingeschrevennatuurlijkpersoonToMatchAllProperties(updatedGeboorteingeschrevennatuurlijkpersoon);
    }

    @Test
    @Transactional
    void putNonExistingGeboorteingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        geboorteingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeboorteingeschrevennatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, geboorteingeschrevennatuurlijkpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(geboorteingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Geboorteingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGeboorteingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        geboorteingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeboorteingeschrevennatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(geboorteingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Geboorteingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGeboorteingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        geboorteingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeboorteingeschrevennatuurlijkpersoonMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(geboorteingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Geboorteingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGeboorteingeschrevennatuurlijkpersoonWithPatch() throws Exception {
        // Initialize the database
        geboorteingeschrevennatuurlijkpersoonRepository.saveAndFlush(geboorteingeschrevennatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the geboorteingeschrevennatuurlijkpersoon using partial update
        Geboorteingeschrevennatuurlijkpersoon partialUpdatedGeboorteingeschrevennatuurlijkpersoon =
            new Geboorteingeschrevennatuurlijkpersoon();
        partialUpdatedGeboorteingeschrevennatuurlijkpersoon.setId(geboorteingeschrevennatuurlijkpersoon.getId());

        partialUpdatedGeboorteingeschrevennatuurlijkpersoon
            .buitenlandseregiogeboorte(UPDATED_BUITENLANDSEREGIOGEBOORTE)
            .gemeentegeboorte(UPDATED_GEMEENTEGEBOORTE);

        restGeboorteingeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGeboorteingeschrevennatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGeboorteingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Geboorteingeschrevennatuurlijkpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGeboorteingeschrevennatuurlijkpersoonUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGeboorteingeschrevennatuurlijkpersoon, geboorteingeschrevennatuurlijkpersoon),
            getPersistedGeboorteingeschrevennatuurlijkpersoon(geboorteingeschrevennatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void fullUpdateGeboorteingeschrevennatuurlijkpersoonWithPatch() throws Exception {
        // Initialize the database
        geboorteingeschrevennatuurlijkpersoonRepository.saveAndFlush(geboorteingeschrevennatuurlijkpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the geboorteingeschrevennatuurlijkpersoon using partial update
        Geboorteingeschrevennatuurlijkpersoon partialUpdatedGeboorteingeschrevennatuurlijkpersoon =
            new Geboorteingeschrevennatuurlijkpersoon();
        partialUpdatedGeboorteingeschrevennatuurlijkpersoon.setId(geboorteingeschrevennatuurlijkpersoon.getId());

        partialUpdatedGeboorteingeschrevennatuurlijkpersoon
            .buitenlandseplaatsgeboorte(UPDATED_BUITENLANDSEPLAATSGEBOORTE)
            .buitenlandseregiogeboorte(UPDATED_BUITENLANDSEREGIOGEBOORTE)
            .datumgeboorte(UPDATED_DATUMGEBOORTE)
            .gemeentegeboorte(UPDATED_GEMEENTEGEBOORTE)
            .landofgebiedgeboorte(UPDATED_LANDOFGEBIEDGEBOORTE)
            .omschrijvinglocatiegeboorte(UPDATED_OMSCHRIJVINGLOCATIEGEBOORTE);

        restGeboorteingeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGeboorteingeschrevennatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGeboorteingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Geboorteingeschrevennatuurlijkpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGeboorteingeschrevennatuurlijkpersoonUpdatableFieldsEquals(
            partialUpdatedGeboorteingeschrevennatuurlijkpersoon,
            getPersistedGeboorteingeschrevennatuurlijkpersoon(partialUpdatedGeboorteingeschrevennatuurlijkpersoon)
        );
    }

    @Test
    @Transactional
    void patchNonExistingGeboorteingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        geboorteingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeboorteingeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, geboorteingeschrevennatuurlijkpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(geboorteingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Geboorteingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGeboorteingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        geboorteingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeboorteingeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(geboorteingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Geboorteingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGeboorteingeschrevennatuurlijkpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        geboorteingeschrevennatuurlijkpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeboorteingeschrevennatuurlijkpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(geboorteingeschrevennatuurlijkpersoon))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Geboorteingeschrevennatuurlijkpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGeboorteingeschrevennatuurlijkpersoon() throws Exception {
        // Initialize the database
        geboorteingeschrevennatuurlijkpersoonRepository.saveAndFlush(geboorteingeschrevennatuurlijkpersoon);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the geboorteingeschrevennatuurlijkpersoon
        restGeboorteingeschrevennatuurlijkpersoonMockMvc
            .perform(delete(ENTITY_API_URL_ID, geboorteingeschrevennatuurlijkpersoon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return geboorteingeschrevennatuurlijkpersoonRepository.count();
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

    protected Geboorteingeschrevennatuurlijkpersoon getPersistedGeboorteingeschrevennatuurlijkpersoon(
        Geboorteingeschrevennatuurlijkpersoon geboorteingeschrevennatuurlijkpersoon
    ) {
        return geboorteingeschrevennatuurlijkpersoonRepository.findById(geboorteingeschrevennatuurlijkpersoon.getId()).orElseThrow();
    }

    protected void assertPersistedGeboorteingeschrevennatuurlijkpersoonToMatchAllProperties(
        Geboorteingeschrevennatuurlijkpersoon expectedGeboorteingeschrevennatuurlijkpersoon
    ) {
        assertGeboorteingeschrevennatuurlijkpersoonAllPropertiesEquals(
            expectedGeboorteingeschrevennatuurlijkpersoon,
            getPersistedGeboorteingeschrevennatuurlijkpersoon(expectedGeboorteingeschrevennatuurlijkpersoon)
        );
    }

    protected void assertPersistedGeboorteingeschrevennatuurlijkpersoonToMatchUpdatableProperties(
        Geboorteingeschrevennatuurlijkpersoon expectedGeboorteingeschrevennatuurlijkpersoon
    ) {
        assertGeboorteingeschrevennatuurlijkpersoonAllUpdatablePropertiesEquals(
            expectedGeboorteingeschrevennatuurlijkpersoon,
            getPersistedGeboorteingeschrevennatuurlijkpersoon(expectedGeboorteingeschrevennatuurlijkpersoon)
        );
    }
}
