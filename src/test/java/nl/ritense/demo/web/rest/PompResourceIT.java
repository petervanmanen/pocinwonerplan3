package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.PompAsserts.*;
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
import nl.ritense.demo.domain.Pomp;
import nl.ritense.demo.repository.PompRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PompResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PompResourceIT {

    private static final String DEFAULT_AANSLAGNIVEAU = "AAAAAAAAAA";
    private static final String UPDATED_AANSLAGNIVEAU = "BBBBBBBBBB";

    private static final String DEFAULT_BEGINSTANDDRAAIURENTELLER = "AAAAAAAAAA";
    private static final String UPDATED_BEGINSTANDDRAAIURENTELLER = "BBBBBBBBBB";

    private static final String DEFAULT_BESTURINGSKAST = "AAAAAAAAAA";
    private static final String UPDATED_BESTURINGSKAST = "BBBBBBBBBB";

    private static final String DEFAULT_LAATSTESTANDDRAAIURENTELLER = "AAAAAAAAAA";
    private static final String UPDATED_LAATSTESTANDDRAAIURENTELLER = "BBBBBBBBBB";

    private static final String DEFAULT_LAATSTESTANDKWHTELLER = "AAAAAAAAAA";
    private static final String UPDATED_LAATSTESTANDKWHTELLER = "BBBBBBBBBB";

    private static final String DEFAULT_LEVENSDUUR = "AAAAAAAAAA";
    private static final String UPDATED_LEVENSDUUR = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_MOTORVERMOGEN = "AAAAAAAAAA";
    private static final String UPDATED_MOTORVERMOGEN = "BBBBBBBBBB";

    private static final String DEFAULT_ONDERDEELMETPOMP = "AAAAAAAAAA";
    private static final String UPDATED_ONDERDEELMETPOMP = "BBBBBBBBBB";

    private static final String DEFAULT_ONTWERPCAPACITEIT = "AAAAAAAAAA";
    private static final String UPDATED_ONTWERPCAPACITEIT = "BBBBBBBBBB";

    private static final String DEFAULT_POMPCAPACITEIT = "AAAAAAAAAA";
    private static final String UPDATED_POMPCAPACITEIT = "BBBBBBBBBB";

    private static final String DEFAULT_SERIENUMMER = "AAAAAAAAAA";
    private static final String UPDATED_SERIENUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEONDERDEELMETPOMP = "AAAAAAAAAA";
    private static final String UPDATED_TYPEONDERDEELMETPOMP = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEPLUS = "AAAAAAAAAA";
    private static final String UPDATED_TYPEPLUS = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEWAAIER = "AAAAAAAAAA";
    private static final String UPDATED_TYPEWAAIER = "BBBBBBBBBB";

    private static final String DEFAULT_UITSLAGPEIL = "AAAAAAAAAA";
    private static final String UPDATED_UITSLAGPEIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pomps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PompRepository pompRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPompMockMvc;

    private Pomp pomp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pomp createEntity(EntityManager em) {
        Pomp pomp = new Pomp()
            .aanslagniveau(DEFAULT_AANSLAGNIVEAU)
            .beginstanddraaiurenteller(DEFAULT_BEGINSTANDDRAAIURENTELLER)
            .besturingskast(DEFAULT_BESTURINGSKAST)
            .laatstestanddraaiurenteller(DEFAULT_LAATSTESTANDDRAAIURENTELLER)
            .laatstestandkwhteller(DEFAULT_LAATSTESTANDKWHTELLER)
            .levensduur(DEFAULT_LEVENSDUUR)
            .model(DEFAULT_MODEL)
            .motorvermogen(DEFAULT_MOTORVERMOGEN)
            .onderdeelmetpomp(DEFAULT_ONDERDEELMETPOMP)
            .ontwerpcapaciteit(DEFAULT_ONTWERPCAPACITEIT)
            .pompcapaciteit(DEFAULT_POMPCAPACITEIT)
            .serienummer(DEFAULT_SERIENUMMER)
            .type(DEFAULT_TYPE)
            .typeonderdeelmetpomp(DEFAULT_TYPEONDERDEELMETPOMP)
            .typeplus(DEFAULT_TYPEPLUS)
            .typewaaier(DEFAULT_TYPEWAAIER)
            .uitslagpeil(DEFAULT_UITSLAGPEIL);
        return pomp;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pomp createUpdatedEntity(EntityManager em) {
        Pomp pomp = new Pomp()
            .aanslagniveau(UPDATED_AANSLAGNIVEAU)
            .beginstanddraaiurenteller(UPDATED_BEGINSTANDDRAAIURENTELLER)
            .besturingskast(UPDATED_BESTURINGSKAST)
            .laatstestanddraaiurenteller(UPDATED_LAATSTESTANDDRAAIURENTELLER)
            .laatstestandkwhteller(UPDATED_LAATSTESTANDKWHTELLER)
            .levensduur(UPDATED_LEVENSDUUR)
            .model(UPDATED_MODEL)
            .motorvermogen(UPDATED_MOTORVERMOGEN)
            .onderdeelmetpomp(UPDATED_ONDERDEELMETPOMP)
            .ontwerpcapaciteit(UPDATED_ONTWERPCAPACITEIT)
            .pompcapaciteit(UPDATED_POMPCAPACITEIT)
            .serienummer(UPDATED_SERIENUMMER)
            .type(UPDATED_TYPE)
            .typeonderdeelmetpomp(UPDATED_TYPEONDERDEELMETPOMP)
            .typeplus(UPDATED_TYPEPLUS)
            .typewaaier(UPDATED_TYPEWAAIER)
            .uitslagpeil(UPDATED_UITSLAGPEIL);
        return pomp;
    }

    @BeforeEach
    public void initTest() {
        pomp = createEntity(em);
    }

    @Test
    @Transactional
    void createPomp() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Pomp
        var returnedPomp = om.readValue(
            restPompMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pomp)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Pomp.class
        );

        // Validate the Pomp in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPompUpdatableFieldsEquals(returnedPomp, getPersistedPomp(returnedPomp));
    }

    @Test
    @Transactional
    void createPompWithExistingId() throws Exception {
        // Create the Pomp with an existing ID
        pomp.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPompMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pomp)))
            .andExpect(status().isBadRequest());

        // Validate the Pomp in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPomps() throws Exception {
        // Initialize the database
        pompRepository.saveAndFlush(pomp);

        // Get all the pompList
        restPompMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pomp.getId().intValue())))
            .andExpect(jsonPath("$.[*].aanslagniveau").value(hasItem(DEFAULT_AANSLAGNIVEAU)))
            .andExpect(jsonPath("$.[*].beginstanddraaiurenteller").value(hasItem(DEFAULT_BEGINSTANDDRAAIURENTELLER)))
            .andExpect(jsonPath("$.[*].besturingskast").value(hasItem(DEFAULT_BESTURINGSKAST)))
            .andExpect(jsonPath("$.[*].laatstestanddraaiurenteller").value(hasItem(DEFAULT_LAATSTESTANDDRAAIURENTELLER)))
            .andExpect(jsonPath("$.[*].laatstestandkwhteller").value(hasItem(DEFAULT_LAATSTESTANDKWHTELLER)))
            .andExpect(jsonPath("$.[*].levensduur").value(hasItem(DEFAULT_LEVENSDUUR)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].motorvermogen").value(hasItem(DEFAULT_MOTORVERMOGEN)))
            .andExpect(jsonPath("$.[*].onderdeelmetpomp").value(hasItem(DEFAULT_ONDERDEELMETPOMP)))
            .andExpect(jsonPath("$.[*].ontwerpcapaciteit").value(hasItem(DEFAULT_ONTWERPCAPACITEIT)))
            .andExpect(jsonPath("$.[*].pompcapaciteit").value(hasItem(DEFAULT_POMPCAPACITEIT)))
            .andExpect(jsonPath("$.[*].serienummer").value(hasItem(DEFAULT_SERIENUMMER)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].typeonderdeelmetpomp").value(hasItem(DEFAULT_TYPEONDERDEELMETPOMP)))
            .andExpect(jsonPath("$.[*].typeplus").value(hasItem(DEFAULT_TYPEPLUS)))
            .andExpect(jsonPath("$.[*].typewaaier").value(hasItem(DEFAULT_TYPEWAAIER)))
            .andExpect(jsonPath("$.[*].uitslagpeil").value(hasItem(DEFAULT_UITSLAGPEIL)));
    }

    @Test
    @Transactional
    void getPomp() throws Exception {
        // Initialize the database
        pompRepository.saveAndFlush(pomp);

        // Get the pomp
        restPompMockMvc
            .perform(get(ENTITY_API_URL_ID, pomp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pomp.getId().intValue()))
            .andExpect(jsonPath("$.aanslagniveau").value(DEFAULT_AANSLAGNIVEAU))
            .andExpect(jsonPath("$.beginstanddraaiurenteller").value(DEFAULT_BEGINSTANDDRAAIURENTELLER))
            .andExpect(jsonPath("$.besturingskast").value(DEFAULT_BESTURINGSKAST))
            .andExpect(jsonPath("$.laatstestanddraaiurenteller").value(DEFAULT_LAATSTESTANDDRAAIURENTELLER))
            .andExpect(jsonPath("$.laatstestandkwhteller").value(DEFAULT_LAATSTESTANDKWHTELLER))
            .andExpect(jsonPath("$.levensduur").value(DEFAULT_LEVENSDUUR))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.motorvermogen").value(DEFAULT_MOTORVERMOGEN))
            .andExpect(jsonPath("$.onderdeelmetpomp").value(DEFAULT_ONDERDEELMETPOMP))
            .andExpect(jsonPath("$.ontwerpcapaciteit").value(DEFAULT_ONTWERPCAPACITEIT))
            .andExpect(jsonPath("$.pompcapaciteit").value(DEFAULT_POMPCAPACITEIT))
            .andExpect(jsonPath("$.serienummer").value(DEFAULT_SERIENUMMER))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.typeonderdeelmetpomp").value(DEFAULT_TYPEONDERDEELMETPOMP))
            .andExpect(jsonPath("$.typeplus").value(DEFAULT_TYPEPLUS))
            .andExpect(jsonPath("$.typewaaier").value(DEFAULT_TYPEWAAIER))
            .andExpect(jsonPath("$.uitslagpeil").value(DEFAULT_UITSLAGPEIL));
    }

    @Test
    @Transactional
    void getNonExistingPomp() throws Exception {
        // Get the pomp
        restPompMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPomp() throws Exception {
        // Initialize the database
        pompRepository.saveAndFlush(pomp);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pomp
        Pomp updatedPomp = pompRepository.findById(pomp.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPomp are not directly saved in db
        em.detach(updatedPomp);
        updatedPomp
            .aanslagniveau(UPDATED_AANSLAGNIVEAU)
            .beginstanddraaiurenteller(UPDATED_BEGINSTANDDRAAIURENTELLER)
            .besturingskast(UPDATED_BESTURINGSKAST)
            .laatstestanddraaiurenteller(UPDATED_LAATSTESTANDDRAAIURENTELLER)
            .laatstestandkwhteller(UPDATED_LAATSTESTANDKWHTELLER)
            .levensduur(UPDATED_LEVENSDUUR)
            .model(UPDATED_MODEL)
            .motorvermogen(UPDATED_MOTORVERMOGEN)
            .onderdeelmetpomp(UPDATED_ONDERDEELMETPOMP)
            .ontwerpcapaciteit(UPDATED_ONTWERPCAPACITEIT)
            .pompcapaciteit(UPDATED_POMPCAPACITEIT)
            .serienummer(UPDATED_SERIENUMMER)
            .type(UPDATED_TYPE)
            .typeonderdeelmetpomp(UPDATED_TYPEONDERDEELMETPOMP)
            .typeplus(UPDATED_TYPEPLUS)
            .typewaaier(UPDATED_TYPEWAAIER)
            .uitslagpeil(UPDATED_UITSLAGPEIL);

        restPompMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPomp.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPomp))
            )
            .andExpect(status().isOk());

        // Validate the Pomp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPompToMatchAllProperties(updatedPomp);
    }

    @Test
    @Transactional
    void putNonExistingPomp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pomp.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPompMockMvc
            .perform(put(ENTITY_API_URL_ID, pomp.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pomp)))
            .andExpect(status().isBadRequest());

        // Validate the Pomp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPomp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pomp.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPompMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pomp))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pomp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPomp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pomp.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPompMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pomp)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pomp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePompWithPatch() throws Exception {
        // Initialize the database
        pompRepository.saveAndFlush(pomp);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pomp using partial update
        Pomp partialUpdatedPomp = new Pomp();
        partialUpdatedPomp.setId(pomp.getId());

        partialUpdatedPomp
            .besturingskast(UPDATED_BESTURINGSKAST)
            .laatstestanddraaiurenteller(UPDATED_LAATSTESTANDDRAAIURENTELLER)
            .laatstestandkwhteller(UPDATED_LAATSTESTANDKWHTELLER)
            .levensduur(UPDATED_LEVENSDUUR)
            .model(UPDATED_MODEL)
            .onderdeelmetpomp(UPDATED_ONDERDEELMETPOMP)
            .serienummer(UPDATED_SERIENUMMER)
            .typeonderdeelmetpomp(UPDATED_TYPEONDERDEELMETPOMP)
            .typeplus(UPDATED_TYPEPLUS)
            .uitslagpeil(UPDATED_UITSLAGPEIL);

        restPompMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPomp.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPomp))
            )
            .andExpect(status().isOk());

        // Validate the Pomp in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPompUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPomp, pomp), getPersistedPomp(pomp));
    }

    @Test
    @Transactional
    void fullUpdatePompWithPatch() throws Exception {
        // Initialize the database
        pompRepository.saveAndFlush(pomp);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pomp using partial update
        Pomp partialUpdatedPomp = new Pomp();
        partialUpdatedPomp.setId(pomp.getId());

        partialUpdatedPomp
            .aanslagniveau(UPDATED_AANSLAGNIVEAU)
            .beginstanddraaiurenteller(UPDATED_BEGINSTANDDRAAIURENTELLER)
            .besturingskast(UPDATED_BESTURINGSKAST)
            .laatstestanddraaiurenteller(UPDATED_LAATSTESTANDDRAAIURENTELLER)
            .laatstestandkwhteller(UPDATED_LAATSTESTANDKWHTELLER)
            .levensduur(UPDATED_LEVENSDUUR)
            .model(UPDATED_MODEL)
            .motorvermogen(UPDATED_MOTORVERMOGEN)
            .onderdeelmetpomp(UPDATED_ONDERDEELMETPOMP)
            .ontwerpcapaciteit(UPDATED_ONTWERPCAPACITEIT)
            .pompcapaciteit(UPDATED_POMPCAPACITEIT)
            .serienummer(UPDATED_SERIENUMMER)
            .type(UPDATED_TYPE)
            .typeonderdeelmetpomp(UPDATED_TYPEONDERDEELMETPOMP)
            .typeplus(UPDATED_TYPEPLUS)
            .typewaaier(UPDATED_TYPEWAAIER)
            .uitslagpeil(UPDATED_UITSLAGPEIL);

        restPompMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPomp.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPomp))
            )
            .andExpect(status().isOk());

        // Validate the Pomp in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPompUpdatableFieldsEquals(partialUpdatedPomp, getPersistedPomp(partialUpdatedPomp));
    }

    @Test
    @Transactional
    void patchNonExistingPomp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pomp.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPompMockMvc
            .perform(patch(ENTITY_API_URL_ID, pomp.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(pomp)))
            .andExpect(status().isBadRequest());

        // Validate the Pomp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPomp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pomp.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPompMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pomp))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pomp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPomp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pomp.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPompMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(pomp)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pomp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePomp() throws Exception {
        // Initialize the database
        pompRepository.saveAndFlush(pomp);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the pomp
        restPompMockMvc
            .perform(delete(ENTITY_API_URL_ID, pomp.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return pompRepository.count();
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

    protected Pomp getPersistedPomp(Pomp pomp) {
        return pompRepository.findById(pomp.getId()).orElseThrow();
    }

    protected void assertPersistedPompToMatchAllProperties(Pomp expectedPomp) {
        assertPompAllPropertiesEquals(expectedPomp, getPersistedPomp(expectedPomp));
    }

    protected void assertPersistedPompToMatchUpdatableProperties(Pomp expectedPomp) {
        assertPompAllUpdatablePropertiesEquals(expectedPomp, getPersistedPomp(expectedPomp));
    }
}
