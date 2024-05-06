package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ValutaAsserts.*;
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
import nl.ritense.demo.domain.Valuta;
import nl.ritense.demo.repository.ValutaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ValutaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ValutaResourceIT {

    private static final String DEFAULT_DATUMBEGINGELDIGHEID = "AAAAAAAAAA";
    private static final String UPDATED_DATUMBEGINGELDIGHEID = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMEINDEGELDIGHEID = "AAAAAAAAAA";
    private static final String UPDATED_DATUMEINDEGELDIGHEID = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_VALUTACODE = "AAAAAAAAAA";
    private static final String UPDATED_VALUTACODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/valutas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ValutaRepository valutaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restValutaMockMvc;

    private Valuta valuta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Valuta createEntity(EntityManager em) {
        Valuta valuta = new Valuta()
            .datumbegingeldigheid(DEFAULT_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(DEFAULT_DATUMEINDEGELDIGHEID)
            .naam(DEFAULT_NAAM)
            .valutacode(DEFAULT_VALUTACODE);
        return valuta;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Valuta createUpdatedEntity(EntityManager em) {
        Valuta valuta = new Valuta()
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .naam(UPDATED_NAAM)
            .valutacode(UPDATED_VALUTACODE);
        return valuta;
    }

    @BeforeEach
    public void initTest() {
        valuta = createEntity(em);
    }

    @Test
    @Transactional
    void createValuta() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Valuta
        var returnedValuta = om.readValue(
            restValutaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(valuta)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Valuta.class
        );

        // Validate the Valuta in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertValutaUpdatableFieldsEquals(returnedValuta, getPersistedValuta(returnedValuta));
    }

    @Test
    @Transactional
    void createValutaWithExistingId() throws Exception {
        // Create the Valuta with an existing ID
        valuta.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restValutaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(valuta)))
            .andExpect(status().isBadRequest());

        // Validate the Valuta in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllValutas() throws Exception {
        // Initialize the database
        valutaRepository.saveAndFlush(valuta);

        // Get all the valutaList
        restValutaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(valuta.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumbegingeldigheid").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEID)))
            .andExpect(jsonPath("$.[*].datumeindegeldigheid").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEID)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].valutacode").value(hasItem(DEFAULT_VALUTACODE)));
    }

    @Test
    @Transactional
    void getValuta() throws Exception {
        // Initialize the database
        valutaRepository.saveAndFlush(valuta);

        // Get the valuta
        restValutaMockMvc
            .perform(get(ENTITY_API_URL_ID, valuta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(valuta.getId().intValue()))
            .andExpect(jsonPath("$.datumbegingeldigheid").value(DEFAULT_DATUMBEGINGELDIGHEID))
            .andExpect(jsonPath("$.datumeindegeldigheid").value(DEFAULT_DATUMEINDEGELDIGHEID))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.valutacode").value(DEFAULT_VALUTACODE));
    }

    @Test
    @Transactional
    void getNonExistingValuta() throws Exception {
        // Get the valuta
        restValutaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingValuta() throws Exception {
        // Initialize the database
        valutaRepository.saveAndFlush(valuta);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the valuta
        Valuta updatedValuta = valutaRepository.findById(valuta.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedValuta are not directly saved in db
        em.detach(updatedValuta);
        updatedValuta
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .naam(UPDATED_NAAM)
            .valutacode(UPDATED_VALUTACODE);

        restValutaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedValuta.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedValuta))
            )
            .andExpect(status().isOk());

        // Validate the Valuta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedValutaToMatchAllProperties(updatedValuta);
    }

    @Test
    @Transactional
    void putNonExistingValuta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        valuta.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restValutaMockMvc
            .perform(put(ENTITY_API_URL_ID, valuta.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(valuta)))
            .andExpect(status().isBadRequest());

        // Validate the Valuta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchValuta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        valuta.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restValutaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(valuta))
            )
            .andExpect(status().isBadRequest());

        // Validate the Valuta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamValuta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        valuta.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restValutaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(valuta)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Valuta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateValutaWithPatch() throws Exception {
        // Initialize the database
        valutaRepository.saveAndFlush(valuta);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the valuta using partial update
        Valuta partialUpdatedValuta = new Valuta();
        partialUpdatedValuta.setId(valuta.getId());

        partialUpdatedValuta.datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID).naam(UPDATED_NAAM).valutacode(UPDATED_VALUTACODE);

        restValutaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedValuta.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedValuta))
            )
            .andExpect(status().isOk());

        // Validate the Valuta in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertValutaUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedValuta, valuta), getPersistedValuta(valuta));
    }

    @Test
    @Transactional
    void fullUpdateValutaWithPatch() throws Exception {
        // Initialize the database
        valutaRepository.saveAndFlush(valuta);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the valuta using partial update
        Valuta partialUpdatedValuta = new Valuta();
        partialUpdatedValuta.setId(valuta.getId());

        partialUpdatedValuta
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .naam(UPDATED_NAAM)
            .valutacode(UPDATED_VALUTACODE);

        restValutaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedValuta.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedValuta))
            )
            .andExpect(status().isOk());

        // Validate the Valuta in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertValutaUpdatableFieldsEquals(partialUpdatedValuta, getPersistedValuta(partialUpdatedValuta));
    }

    @Test
    @Transactional
    void patchNonExistingValuta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        valuta.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restValutaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, valuta.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(valuta))
            )
            .andExpect(status().isBadRequest());

        // Validate the Valuta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchValuta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        valuta.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restValutaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(valuta))
            )
            .andExpect(status().isBadRequest());

        // Validate the Valuta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamValuta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        valuta.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restValutaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(valuta)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Valuta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteValuta() throws Exception {
        // Initialize the database
        valutaRepository.saveAndFlush(valuta);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the valuta
        restValutaMockMvc
            .perform(delete(ENTITY_API_URL_ID, valuta.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return valutaRepository.count();
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

    protected Valuta getPersistedValuta(Valuta valuta) {
        return valutaRepository.findById(valuta.getId()).orElseThrow();
    }

    protected void assertPersistedValutaToMatchAllProperties(Valuta expectedValuta) {
        assertValutaAllPropertiesEquals(expectedValuta, getPersistedValuta(expectedValuta));
    }

    protected void assertPersistedValutaToMatchUpdatableProperties(Valuta expectedValuta) {
        assertValutaAllUpdatablePropertiesEquals(expectedValuta, getPersistedValuta(expectedValuta));
    }
}
