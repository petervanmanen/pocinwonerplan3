package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.RioolputAsserts.*;
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
import nl.ritense.demo.domain.Rioolput;
import nl.ritense.demo.repository.RioolputRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RioolputResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RioolputResourceIT {

    private static final String DEFAULT_AANTALBEDRIJVEN = "AAAAAAAAAA";
    private static final String UPDATED_AANTALBEDRIJVEN = "BBBBBBBBBB";

    private static final String DEFAULT_AANTALRECREATIE = "AAAAAAAAAA";
    private static final String UPDATED_AANTALRECREATIE = "BBBBBBBBBB";

    private static final String DEFAULT_AANTALWONINGEN = "AAAAAAAAAA";
    private static final String UPDATED_AANTALWONINGEN = "BBBBBBBBBB";

    private static final String DEFAULT_AFVOERENDOPPERVLAK = "AAAAAAAAAA";
    private static final String UPDATED_AFVOERENDOPPERVLAK = "BBBBBBBBBB";

    private static final String DEFAULT_BERGENDOPPERVLAK = "AAAAAAAAAA";
    private static final String UPDATED_BERGENDOPPERVLAK = "BBBBBBBBBB";

    private static final String DEFAULT_RIOOLPUTCONSTRUCTIEONDERDEEL = "AAAAAAAAAA";
    private static final String UPDATED_RIOOLPUTCONSTRUCTIEONDERDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_RIOOLPUTRIOOLLEIDING = "AAAAAAAAAA";
    private static final String UPDATED_RIOOLPUTRIOOLLEIDING = "BBBBBBBBBB";

    private static final String DEFAULT_RISICOGEBIED = "AAAAAAAAAA";
    private static final String UPDATED_RISICOGEBIED = "BBBBBBBBBB";

    private static final String DEFAULT_TOEGANGBREEDTE = "AAAAAAAAAA";
    private static final String UPDATED_TOEGANGBREEDTE = "BBBBBBBBBB";

    private static final String DEFAULT_TOEGANGLENGTE = "AAAAAAAAAA";
    private static final String UPDATED_TOEGANGLENGTE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEPLUS = "AAAAAAAAAA";
    private static final String UPDATED_TYPEPLUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rioolputs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RioolputRepository rioolputRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRioolputMockMvc;

    private Rioolput rioolput;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rioolput createEntity(EntityManager em) {
        Rioolput rioolput = new Rioolput()
            .aantalbedrijven(DEFAULT_AANTALBEDRIJVEN)
            .aantalrecreatie(DEFAULT_AANTALRECREATIE)
            .aantalwoningen(DEFAULT_AANTALWONINGEN)
            .afvoerendoppervlak(DEFAULT_AFVOERENDOPPERVLAK)
            .bergendoppervlak(DEFAULT_BERGENDOPPERVLAK)
            .rioolputconstructieonderdeel(DEFAULT_RIOOLPUTCONSTRUCTIEONDERDEEL)
            .rioolputrioolleiding(DEFAULT_RIOOLPUTRIOOLLEIDING)
            .risicogebied(DEFAULT_RISICOGEBIED)
            .toegangbreedte(DEFAULT_TOEGANGBREEDTE)
            .toeganglengte(DEFAULT_TOEGANGLENGTE)
            .type(DEFAULT_TYPE)
            .typeplus(DEFAULT_TYPEPLUS);
        return rioolput;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rioolput createUpdatedEntity(EntityManager em) {
        Rioolput rioolput = new Rioolput()
            .aantalbedrijven(UPDATED_AANTALBEDRIJVEN)
            .aantalrecreatie(UPDATED_AANTALRECREATIE)
            .aantalwoningen(UPDATED_AANTALWONINGEN)
            .afvoerendoppervlak(UPDATED_AFVOERENDOPPERVLAK)
            .bergendoppervlak(UPDATED_BERGENDOPPERVLAK)
            .rioolputconstructieonderdeel(UPDATED_RIOOLPUTCONSTRUCTIEONDERDEEL)
            .rioolputrioolleiding(UPDATED_RIOOLPUTRIOOLLEIDING)
            .risicogebied(UPDATED_RISICOGEBIED)
            .toegangbreedte(UPDATED_TOEGANGBREEDTE)
            .toeganglengte(UPDATED_TOEGANGLENGTE)
            .type(UPDATED_TYPE)
            .typeplus(UPDATED_TYPEPLUS);
        return rioolput;
    }

    @BeforeEach
    public void initTest() {
        rioolput = createEntity(em);
    }

    @Test
    @Transactional
    void createRioolput() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Rioolput
        var returnedRioolput = om.readValue(
            restRioolputMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rioolput)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Rioolput.class
        );

        // Validate the Rioolput in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRioolputUpdatableFieldsEquals(returnedRioolput, getPersistedRioolput(returnedRioolput));
    }

    @Test
    @Transactional
    void createRioolputWithExistingId() throws Exception {
        // Create the Rioolput with an existing ID
        rioolput.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRioolputMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rioolput)))
            .andExpect(status().isBadRequest());

        // Validate the Rioolput in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRioolputs() throws Exception {
        // Initialize the database
        rioolputRepository.saveAndFlush(rioolput);

        // Get all the rioolputList
        restRioolputMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rioolput.getId().intValue())))
            .andExpect(jsonPath("$.[*].aantalbedrijven").value(hasItem(DEFAULT_AANTALBEDRIJVEN)))
            .andExpect(jsonPath("$.[*].aantalrecreatie").value(hasItem(DEFAULT_AANTALRECREATIE)))
            .andExpect(jsonPath("$.[*].aantalwoningen").value(hasItem(DEFAULT_AANTALWONINGEN)))
            .andExpect(jsonPath("$.[*].afvoerendoppervlak").value(hasItem(DEFAULT_AFVOERENDOPPERVLAK)))
            .andExpect(jsonPath("$.[*].bergendoppervlak").value(hasItem(DEFAULT_BERGENDOPPERVLAK)))
            .andExpect(jsonPath("$.[*].rioolputconstructieonderdeel").value(hasItem(DEFAULT_RIOOLPUTCONSTRUCTIEONDERDEEL)))
            .andExpect(jsonPath("$.[*].rioolputrioolleiding").value(hasItem(DEFAULT_RIOOLPUTRIOOLLEIDING)))
            .andExpect(jsonPath("$.[*].risicogebied").value(hasItem(DEFAULT_RISICOGEBIED)))
            .andExpect(jsonPath("$.[*].toegangbreedte").value(hasItem(DEFAULT_TOEGANGBREEDTE)))
            .andExpect(jsonPath("$.[*].toeganglengte").value(hasItem(DEFAULT_TOEGANGLENGTE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].typeplus").value(hasItem(DEFAULT_TYPEPLUS)));
    }

    @Test
    @Transactional
    void getRioolput() throws Exception {
        // Initialize the database
        rioolputRepository.saveAndFlush(rioolput);

        // Get the rioolput
        restRioolputMockMvc
            .perform(get(ENTITY_API_URL_ID, rioolput.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rioolput.getId().intValue()))
            .andExpect(jsonPath("$.aantalbedrijven").value(DEFAULT_AANTALBEDRIJVEN))
            .andExpect(jsonPath("$.aantalrecreatie").value(DEFAULT_AANTALRECREATIE))
            .andExpect(jsonPath("$.aantalwoningen").value(DEFAULT_AANTALWONINGEN))
            .andExpect(jsonPath("$.afvoerendoppervlak").value(DEFAULT_AFVOERENDOPPERVLAK))
            .andExpect(jsonPath("$.bergendoppervlak").value(DEFAULT_BERGENDOPPERVLAK))
            .andExpect(jsonPath("$.rioolputconstructieonderdeel").value(DEFAULT_RIOOLPUTCONSTRUCTIEONDERDEEL))
            .andExpect(jsonPath("$.rioolputrioolleiding").value(DEFAULT_RIOOLPUTRIOOLLEIDING))
            .andExpect(jsonPath("$.risicogebied").value(DEFAULT_RISICOGEBIED))
            .andExpect(jsonPath("$.toegangbreedte").value(DEFAULT_TOEGANGBREEDTE))
            .andExpect(jsonPath("$.toeganglengte").value(DEFAULT_TOEGANGLENGTE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.typeplus").value(DEFAULT_TYPEPLUS));
    }

    @Test
    @Transactional
    void getNonExistingRioolput() throws Exception {
        // Get the rioolput
        restRioolputMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRioolput() throws Exception {
        // Initialize the database
        rioolputRepository.saveAndFlush(rioolput);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rioolput
        Rioolput updatedRioolput = rioolputRepository.findById(rioolput.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRioolput are not directly saved in db
        em.detach(updatedRioolput);
        updatedRioolput
            .aantalbedrijven(UPDATED_AANTALBEDRIJVEN)
            .aantalrecreatie(UPDATED_AANTALRECREATIE)
            .aantalwoningen(UPDATED_AANTALWONINGEN)
            .afvoerendoppervlak(UPDATED_AFVOERENDOPPERVLAK)
            .bergendoppervlak(UPDATED_BERGENDOPPERVLAK)
            .rioolputconstructieonderdeel(UPDATED_RIOOLPUTCONSTRUCTIEONDERDEEL)
            .rioolputrioolleiding(UPDATED_RIOOLPUTRIOOLLEIDING)
            .risicogebied(UPDATED_RISICOGEBIED)
            .toegangbreedte(UPDATED_TOEGANGBREEDTE)
            .toeganglengte(UPDATED_TOEGANGLENGTE)
            .type(UPDATED_TYPE)
            .typeplus(UPDATED_TYPEPLUS);

        restRioolputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRioolput.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRioolput))
            )
            .andExpect(status().isOk());

        // Validate the Rioolput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRioolputToMatchAllProperties(updatedRioolput);
    }

    @Test
    @Transactional
    void putNonExistingRioolput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rioolput.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRioolputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rioolput.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rioolput))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rioolput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRioolput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rioolput.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRioolputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rioolput))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rioolput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRioolput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rioolput.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRioolputMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rioolput)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rioolput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRioolputWithPatch() throws Exception {
        // Initialize the database
        rioolputRepository.saveAndFlush(rioolput);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rioolput using partial update
        Rioolput partialUpdatedRioolput = new Rioolput();
        partialUpdatedRioolput.setId(rioolput.getId());

        partialUpdatedRioolput
            .aantalbedrijven(UPDATED_AANTALBEDRIJVEN)
            .aantalrecreatie(UPDATED_AANTALRECREATIE)
            .rioolputconstructieonderdeel(UPDATED_RIOOLPUTCONSTRUCTIEONDERDEEL)
            .rioolputrioolleiding(UPDATED_RIOOLPUTRIOOLLEIDING)
            .toegangbreedte(UPDATED_TOEGANGBREEDTE)
            .typeplus(UPDATED_TYPEPLUS);

        restRioolputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRioolput.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRioolput))
            )
            .andExpect(status().isOk());

        // Validate the Rioolput in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRioolputUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedRioolput, rioolput), getPersistedRioolput(rioolput));
    }

    @Test
    @Transactional
    void fullUpdateRioolputWithPatch() throws Exception {
        // Initialize the database
        rioolputRepository.saveAndFlush(rioolput);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rioolput using partial update
        Rioolput partialUpdatedRioolput = new Rioolput();
        partialUpdatedRioolput.setId(rioolput.getId());

        partialUpdatedRioolput
            .aantalbedrijven(UPDATED_AANTALBEDRIJVEN)
            .aantalrecreatie(UPDATED_AANTALRECREATIE)
            .aantalwoningen(UPDATED_AANTALWONINGEN)
            .afvoerendoppervlak(UPDATED_AFVOERENDOPPERVLAK)
            .bergendoppervlak(UPDATED_BERGENDOPPERVLAK)
            .rioolputconstructieonderdeel(UPDATED_RIOOLPUTCONSTRUCTIEONDERDEEL)
            .rioolputrioolleiding(UPDATED_RIOOLPUTRIOOLLEIDING)
            .risicogebied(UPDATED_RISICOGEBIED)
            .toegangbreedte(UPDATED_TOEGANGBREEDTE)
            .toeganglengte(UPDATED_TOEGANGLENGTE)
            .type(UPDATED_TYPE)
            .typeplus(UPDATED_TYPEPLUS);

        restRioolputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRioolput.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRioolput))
            )
            .andExpect(status().isOk());

        // Validate the Rioolput in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRioolputUpdatableFieldsEquals(partialUpdatedRioolput, getPersistedRioolput(partialUpdatedRioolput));
    }

    @Test
    @Transactional
    void patchNonExistingRioolput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rioolput.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRioolputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rioolput.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rioolput))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rioolput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRioolput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rioolput.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRioolputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rioolput))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rioolput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRioolput() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rioolput.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRioolputMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rioolput)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rioolput in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRioolput() throws Exception {
        // Initialize the database
        rioolputRepository.saveAndFlush(rioolput);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the rioolput
        restRioolputMockMvc
            .perform(delete(ENTITY_API_URL_ID, rioolput.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return rioolputRepository.count();
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

    protected Rioolput getPersistedRioolput(Rioolput rioolput) {
        return rioolputRepository.findById(rioolput.getId()).orElseThrow();
    }

    protected void assertPersistedRioolputToMatchAllProperties(Rioolput expectedRioolput) {
        assertRioolputAllPropertiesEquals(expectedRioolput, getPersistedRioolput(expectedRioolput));
    }

    protected void assertPersistedRioolputToMatchUpdatableProperties(Rioolput expectedRioolput) {
        assertRioolputAllUpdatablePropertiesEquals(expectedRioolput, getPersistedRioolput(expectedRioolput));
    }
}
