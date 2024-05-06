package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SplitsingstekeningreferentieAsserts.*;
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
import nl.ritense.demo.domain.Splitsingstekeningreferentie;
import nl.ritense.demo.repository.SplitsingstekeningreferentieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SplitsingstekeningreferentieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SplitsingstekeningreferentieResourceIT {

    private static final String DEFAULT_BRONORGANISATIE = "AAAAAAAAAA";
    private static final String UPDATED_BRONORGANISATIE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMCREATIE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMCREATIE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_IDENTIFICATIETEKENING = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIETEKENING = "BBBBBBBBBB";

    private static final String DEFAULT_TITEL = "AAAAAAAAAA";
    private static final String UPDATED_TITEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/splitsingstekeningreferenties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SplitsingstekeningreferentieRepository splitsingstekeningreferentieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSplitsingstekeningreferentieMockMvc;

    private Splitsingstekeningreferentie splitsingstekeningreferentie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Splitsingstekeningreferentie createEntity(EntityManager em) {
        Splitsingstekeningreferentie splitsingstekeningreferentie = new Splitsingstekeningreferentie()
            .bronorganisatie(DEFAULT_BRONORGANISATIE)
            .datumcreatie(DEFAULT_DATUMCREATIE)
            .identificatietekening(DEFAULT_IDENTIFICATIETEKENING)
            .titel(DEFAULT_TITEL);
        return splitsingstekeningreferentie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Splitsingstekeningreferentie createUpdatedEntity(EntityManager em) {
        Splitsingstekeningreferentie splitsingstekeningreferentie = new Splitsingstekeningreferentie()
            .bronorganisatie(UPDATED_BRONORGANISATIE)
            .datumcreatie(UPDATED_DATUMCREATIE)
            .identificatietekening(UPDATED_IDENTIFICATIETEKENING)
            .titel(UPDATED_TITEL);
        return splitsingstekeningreferentie;
    }

    @BeforeEach
    public void initTest() {
        splitsingstekeningreferentie = createEntity(em);
    }

    @Test
    @Transactional
    void createSplitsingstekeningreferentie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Splitsingstekeningreferentie
        var returnedSplitsingstekeningreferentie = om.readValue(
            restSplitsingstekeningreferentieMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(splitsingstekeningreferentie))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Splitsingstekeningreferentie.class
        );

        // Validate the Splitsingstekeningreferentie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSplitsingstekeningreferentieUpdatableFieldsEquals(
            returnedSplitsingstekeningreferentie,
            getPersistedSplitsingstekeningreferentie(returnedSplitsingstekeningreferentie)
        );
    }

    @Test
    @Transactional
    void createSplitsingstekeningreferentieWithExistingId() throws Exception {
        // Create the Splitsingstekeningreferentie with an existing ID
        splitsingstekeningreferentie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSplitsingstekeningreferentieMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(splitsingstekeningreferentie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Splitsingstekeningreferentie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSplitsingstekeningreferenties() throws Exception {
        // Initialize the database
        splitsingstekeningreferentieRepository.saveAndFlush(splitsingstekeningreferentie);

        // Get all the splitsingstekeningreferentieList
        restSplitsingstekeningreferentieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(splitsingstekeningreferentie.getId().intValue())))
            .andExpect(jsonPath("$.[*].bronorganisatie").value(hasItem(DEFAULT_BRONORGANISATIE)))
            .andExpect(jsonPath("$.[*].datumcreatie").value(hasItem(DEFAULT_DATUMCREATIE.toString())))
            .andExpect(jsonPath("$.[*].identificatietekening").value(hasItem(DEFAULT_IDENTIFICATIETEKENING)))
            .andExpect(jsonPath("$.[*].titel").value(hasItem(DEFAULT_TITEL)));
    }

    @Test
    @Transactional
    void getSplitsingstekeningreferentie() throws Exception {
        // Initialize the database
        splitsingstekeningreferentieRepository.saveAndFlush(splitsingstekeningreferentie);

        // Get the splitsingstekeningreferentie
        restSplitsingstekeningreferentieMockMvc
            .perform(get(ENTITY_API_URL_ID, splitsingstekeningreferentie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(splitsingstekeningreferentie.getId().intValue()))
            .andExpect(jsonPath("$.bronorganisatie").value(DEFAULT_BRONORGANISATIE))
            .andExpect(jsonPath("$.datumcreatie").value(DEFAULT_DATUMCREATIE.toString()))
            .andExpect(jsonPath("$.identificatietekening").value(DEFAULT_IDENTIFICATIETEKENING))
            .andExpect(jsonPath("$.titel").value(DEFAULT_TITEL));
    }

    @Test
    @Transactional
    void getNonExistingSplitsingstekeningreferentie() throws Exception {
        // Get the splitsingstekeningreferentie
        restSplitsingstekeningreferentieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSplitsingstekeningreferentie() throws Exception {
        // Initialize the database
        splitsingstekeningreferentieRepository.saveAndFlush(splitsingstekeningreferentie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the splitsingstekeningreferentie
        Splitsingstekeningreferentie updatedSplitsingstekeningreferentie = splitsingstekeningreferentieRepository
            .findById(splitsingstekeningreferentie.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedSplitsingstekeningreferentie are not directly saved in db
        em.detach(updatedSplitsingstekeningreferentie);
        updatedSplitsingstekeningreferentie
            .bronorganisatie(UPDATED_BRONORGANISATIE)
            .datumcreatie(UPDATED_DATUMCREATIE)
            .identificatietekening(UPDATED_IDENTIFICATIETEKENING)
            .titel(UPDATED_TITEL);

        restSplitsingstekeningreferentieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSplitsingstekeningreferentie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSplitsingstekeningreferentie))
            )
            .andExpect(status().isOk());

        // Validate the Splitsingstekeningreferentie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSplitsingstekeningreferentieToMatchAllProperties(updatedSplitsingstekeningreferentie);
    }

    @Test
    @Transactional
    void putNonExistingSplitsingstekeningreferentie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        splitsingstekeningreferentie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSplitsingstekeningreferentieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, splitsingstekeningreferentie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(splitsingstekeningreferentie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Splitsingstekeningreferentie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSplitsingstekeningreferentie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        splitsingstekeningreferentie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSplitsingstekeningreferentieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(splitsingstekeningreferentie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Splitsingstekeningreferentie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSplitsingstekeningreferentie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        splitsingstekeningreferentie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSplitsingstekeningreferentieMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(splitsingstekeningreferentie))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Splitsingstekeningreferentie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSplitsingstekeningreferentieWithPatch() throws Exception {
        // Initialize the database
        splitsingstekeningreferentieRepository.saveAndFlush(splitsingstekeningreferentie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the splitsingstekeningreferentie using partial update
        Splitsingstekeningreferentie partialUpdatedSplitsingstekeningreferentie = new Splitsingstekeningreferentie();
        partialUpdatedSplitsingstekeningreferentie.setId(splitsingstekeningreferentie.getId());

        partialUpdatedSplitsingstekeningreferentie
            .bronorganisatie(UPDATED_BRONORGANISATIE)
            .identificatietekening(UPDATED_IDENTIFICATIETEKENING)
            .titel(UPDATED_TITEL);

        restSplitsingstekeningreferentieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSplitsingstekeningreferentie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSplitsingstekeningreferentie))
            )
            .andExpect(status().isOk());

        // Validate the Splitsingstekeningreferentie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSplitsingstekeningreferentieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSplitsingstekeningreferentie, splitsingstekeningreferentie),
            getPersistedSplitsingstekeningreferentie(splitsingstekeningreferentie)
        );
    }

    @Test
    @Transactional
    void fullUpdateSplitsingstekeningreferentieWithPatch() throws Exception {
        // Initialize the database
        splitsingstekeningreferentieRepository.saveAndFlush(splitsingstekeningreferentie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the splitsingstekeningreferentie using partial update
        Splitsingstekeningreferentie partialUpdatedSplitsingstekeningreferentie = new Splitsingstekeningreferentie();
        partialUpdatedSplitsingstekeningreferentie.setId(splitsingstekeningreferentie.getId());

        partialUpdatedSplitsingstekeningreferentie
            .bronorganisatie(UPDATED_BRONORGANISATIE)
            .datumcreatie(UPDATED_DATUMCREATIE)
            .identificatietekening(UPDATED_IDENTIFICATIETEKENING)
            .titel(UPDATED_TITEL);

        restSplitsingstekeningreferentieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSplitsingstekeningreferentie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSplitsingstekeningreferentie))
            )
            .andExpect(status().isOk());

        // Validate the Splitsingstekeningreferentie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSplitsingstekeningreferentieUpdatableFieldsEquals(
            partialUpdatedSplitsingstekeningreferentie,
            getPersistedSplitsingstekeningreferentie(partialUpdatedSplitsingstekeningreferentie)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSplitsingstekeningreferentie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        splitsingstekeningreferentie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSplitsingstekeningreferentieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, splitsingstekeningreferentie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(splitsingstekeningreferentie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Splitsingstekeningreferentie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSplitsingstekeningreferentie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        splitsingstekeningreferentie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSplitsingstekeningreferentieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(splitsingstekeningreferentie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Splitsingstekeningreferentie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSplitsingstekeningreferentie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        splitsingstekeningreferentie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSplitsingstekeningreferentieMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(splitsingstekeningreferentie))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Splitsingstekeningreferentie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSplitsingstekeningreferentie() throws Exception {
        // Initialize the database
        splitsingstekeningreferentieRepository.saveAndFlush(splitsingstekeningreferentie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the splitsingstekeningreferentie
        restSplitsingstekeningreferentieMockMvc
            .perform(delete(ENTITY_API_URL_ID, splitsingstekeningreferentie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return splitsingstekeningreferentieRepository.count();
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

    protected Splitsingstekeningreferentie getPersistedSplitsingstekeningreferentie(
        Splitsingstekeningreferentie splitsingstekeningreferentie
    ) {
        return splitsingstekeningreferentieRepository.findById(splitsingstekeningreferentie.getId()).orElseThrow();
    }

    protected void assertPersistedSplitsingstekeningreferentieToMatchAllProperties(
        Splitsingstekeningreferentie expectedSplitsingstekeningreferentie
    ) {
        assertSplitsingstekeningreferentieAllPropertiesEquals(
            expectedSplitsingstekeningreferentie,
            getPersistedSplitsingstekeningreferentie(expectedSplitsingstekeningreferentie)
        );
    }

    protected void assertPersistedSplitsingstekeningreferentieToMatchUpdatableProperties(
        Splitsingstekeningreferentie expectedSplitsingstekeningreferentie
    ) {
        assertSplitsingstekeningreferentieAllUpdatablePropertiesEquals(
            expectedSplitsingstekeningreferentie,
            getPersistedSplitsingstekeningreferentie(expectedSplitsingstekeningreferentie)
        );
    }
}
