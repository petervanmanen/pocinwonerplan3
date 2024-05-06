package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.HistorischpersoonAsserts.*;
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
import nl.ritense.demo.domain.Historischpersoon;
import nl.ritense.demo.repository.HistorischpersoonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link HistorischpersoonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HistorischpersoonResourceIT {

    private static final String DEFAULT_BEROEP = "AAAAAAAAAA";
    private static final String UPDATED_BEROEP = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMGEBOORTE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMGEBOORTE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMOVERLIJDEN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMOVERLIJDEN = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_PUBLIEKTOEGANKELIJK = "AAAAAAAAAA";
    private static final String UPDATED_PUBLIEKTOEGANKELIJK = "BBBBBBBBBB";

    private static final String DEFAULT_WOONDEOP = "AAAAAAAAAA";
    private static final String UPDATED_WOONDEOP = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/historischpersoons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HistorischpersoonRepository historischpersoonRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHistorischpersoonMockMvc;

    private Historischpersoon historischpersoon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Historischpersoon createEntity(EntityManager em) {
        Historischpersoon historischpersoon = new Historischpersoon()
            .beroep(DEFAULT_BEROEP)
            .datumgeboorte(DEFAULT_DATUMGEBOORTE)
            .datumoverlijden(DEFAULT_DATUMOVERLIJDEN)
            .naam(DEFAULT_NAAM)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .publiektoegankelijk(DEFAULT_PUBLIEKTOEGANKELIJK)
            .woondeop(DEFAULT_WOONDEOP);
        return historischpersoon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Historischpersoon createUpdatedEntity(EntityManager em) {
        Historischpersoon historischpersoon = new Historischpersoon()
            .beroep(UPDATED_BEROEP)
            .datumgeboorte(UPDATED_DATUMGEBOORTE)
            .datumoverlijden(UPDATED_DATUMOVERLIJDEN)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .publiektoegankelijk(UPDATED_PUBLIEKTOEGANKELIJK)
            .woondeop(UPDATED_WOONDEOP);
        return historischpersoon;
    }

    @BeforeEach
    public void initTest() {
        historischpersoon = createEntity(em);
    }

    @Test
    @Transactional
    void createHistorischpersoon() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Historischpersoon
        var returnedHistorischpersoon = om.readValue(
            restHistorischpersoonMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(historischpersoon)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Historischpersoon.class
        );

        // Validate the Historischpersoon in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertHistorischpersoonUpdatableFieldsEquals(returnedHistorischpersoon, getPersistedHistorischpersoon(returnedHistorischpersoon));
    }

    @Test
    @Transactional
    void createHistorischpersoonWithExistingId() throws Exception {
        // Create the Historischpersoon with an existing ID
        historischpersoon.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistorischpersoonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(historischpersoon)))
            .andExpect(status().isBadRequest());

        // Validate the Historischpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHistorischpersoons() throws Exception {
        // Initialize the database
        historischpersoonRepository.saveAndFlush(historischpersoon);

        // Get all the historischpersoonList
        restHistorischpersoonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historischpersoon.getId().intValue())))
            .andExpect(jsonPath("$.[*].beroep").value(hasItem(DEFAULT_BEROEP)))
            .andExpect(jsonPath("$.[*].datumgeboorte").value(hasItem(DEFAULT_DATUMGEBOORTE.toString())))
            .andExpect(jsonPath("$.[*].datumoverlijden").value(hasItem(DEFAULT_DATUMOVERLIJDEN.toString())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].publiektoegankelijk").value(hasItem(DEFAULT_PUBLIEKTOEGANKELIJK)))
            .andExpect(jsonPath("$.[*].woondeop").value(hasItem(DEFAULT_WOONDEOP)));
    }

    @Test
    @Transactional
    void getHistorischpersoon() throws Exception {
        // Initialize the database
        historischpersoonRepository.saveAndFlush(historischpersoon);

        // Get the historischpersoon
        restHistorischpersoonMockMvc
            .perform(get(ENTITY_API_URL_ID, historischpersoon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(historischpersoon.getId().intValue()))
            .andExpect(jsonPath("$.beroep").value(DEFAULT_BEROEP))
            .andExpect(jsonPath("$.datumgeboorte").value(DEFAULT_DATUMGEBOORTE.toString()))
            .andExpect(jsonPath("$.datumoverlijden").value(DEFAULT_DATUMOVERLIJDEN.toString()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.publiektoegankelijk").value(DEFAULT_PUBLIEKTOEGANKELIJK))
            .andExpect(jsonPath("$.woondeop").value(DEFAULT_WOONDEOP));
    }

    @Test
    @Transactional
    void getNonExistingHistorischpersoon() throws Exception {
        // Get the historischpersoon
        restHistorischpersoonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHistorischpersoon() throws Exception {
        // Initialize the database
        historischpersoonRepository.saveAndFlush(historischpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the historischpersoon
        Historischpersoon updatedHistorischpersoon = historischpersoonRepository.findById(historischpersoon.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHistorischpersoon are not directly saved in db
        em.detach(updatedHistorischpersoon);
        updatedHistorischpersoon
            .beroep(UPDATED_BEROEP)
            .datumgeboorte(UPDATED_DATUMGEBOORTE)
            .datumoverlijden(UPDATED_DATUMOVERLIJDEN)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .publiektoegankelijk(UPDATED_PUBLIEKTOEGANKELIJK)
            .woondeop(UPDATED_WOONDEOP);

        restHistorischpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHistorischpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedHistorischpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Historischpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHistorischpersoonToMatchAllProperties(updatedHistorischpersoon);
    }

    @Test
    @Transactional
    void putNonExistingHistorischpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        historischpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistorischpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, historischpersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(historischpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Historischpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHistorischpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        historischpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistorischpersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(historischpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Historischpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHistorischpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        historischpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistorischpersoonMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(historischpersoon)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Historischpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHistorischpersoonWithPatch() throws Exception {
        // Initialize the database
        historischpersoonRepository.saveAndFlush(historischpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the historischpersoon using partial update
        Historischpersoon partialUpdatedHistorischpersoon = new Historischpersoon();
        partialUpdatedHistorischpersoon.setId(historischpersoon.getId());

        partialUpdatedHistorischpersoon
            .beroep(UPDATED_BEROEP)
            .datumgeboorte(UPDATED_DATUMGEBOORTE)
            .naam(UPDATED_NAAM)
            .woondeop(UPDATED_WOONDEOP);

        restHistorischpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHistorischpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHistorischpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Historischpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHistorischpersoonUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedHistorischpersoon, historischpersoon),
            getPersistedHistorischpersoon(historischpersoon)
        );
    }

    @Test
    @Transactional
    void fullUpdateHistorischpersoonWithPatch() throws Exception {
        // Initialize the database
        historischpersoonRepository.saveAndFlush(historischpersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the historischpersoon using partial update
        Historischpersoon partialUpdatedHistorischpersoon = new Historischpersoon();
        partialUpdatedHistorischpersoon.setId(historischpersoon.getId());

        partialUpdatedHistorischpersoon
            .beroep(UPDATED_BEROEP)
            .datumgeboorte(UPDATED_DATUMGEBOORTE)
            .datumoverlijden(UPDATED_DATUMOVERLIJDEN)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .publiektoegankelijk(UPDATED_PUBLIEKTOEGANKELIJK)
            .woondeop(UPDATED_WOONDEOP);

        restHistorischpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHistorischpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHistorischpersoon))
            )
            .andExpect(status().isOk());

        // Validate the Historischpersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHistorischpersoonUpdatableFieldsEquals(
            partialUpdatedHistorischpersoon,
            getPersistedHistorischpersoon(partialUpdatedHistorischpersoon)
        );
    }

    @Test
    @Transactional
    void patchNonExistingHistorischpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        historischpersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistorischpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, historischpersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(historischpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Historischpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHistorischpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        historischpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistorischpersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(historischpersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Historischpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHistorischpersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        historischpersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistorischpersoonMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(historischpersoon)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Historischpersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHistorischpersoon() throws Exception {
        // Initialize the database
        historischpersoonRepository.saveAndFlush(historischpersoon);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the historischpersoon
        restHistorischpersoonMockMvc
            .perform(delete(ENTITY_API_URL_ID, historischpersoon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return historischpersoonRepository.count();
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

    protected Historischpersoon getPersistedHistorischpersoon(Historischpersoon historischpersoon) {
        return historischpersoonRepository.findById(historischpersoon.getId()).orElseThrow();
    }

    protected void assertPersistedHistorischpersoonToMatchAllProperties(Historischpersoon expectedHistorischpersoon) {
        assertHistorischpersoonAllPropertiesEquals(expectedHistorischpersoon, getPersistedHistorischpersoon(expectedHistorischpersoon));
    }

    protected void assertPersistedHistorischpersoonToMatchUpdatableProperties(Historischpersoon expectedHistorischpersoon) {
        assertHistorischpersoonAllUpdatablePropertiesEquals(
            expectedHistorischpersoon,
            getPersistedHistorischpersoon(expectedHistorischpersoon)
        );
    }
}
