package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ValutasoortAsserts.*;
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
import nl.ritense.demo.domain.Valutasoort;
import nl.ritense.demo.repository.ValutasoortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ValutasoortResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ValutasoortResourceIT {

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDVALUTASOORT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDVALUTASOORT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDVALUTASOORT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDVALUTASOORT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NAAMVALUTA = "AAAAAAAAAA";
    private static final String UPDATED_NAAMVALUTA = "BBBBBBBBBB";

    private static final String DEFAULT_VALUTACODE = "AAAAAAAAAA";
    private static final String UPDATED_VALUTACODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/valutasoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ValutasoortRepository valutasoortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restValutasoortMockMvc;

    private Valutasoort valutasoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Valutasoort createEntity(EntityManager em) {
        Valutasoort valutasoort = new Valutasoort()
            .datumbegingeldigheidvalutasoort(DEFAULT_DATUMBEGINGELDIGHEIDVALUTASOORT)
            .datumeindegeldigheidvalutasoort(DEFAULT_DATUMEINDEGELDIGHEIDVALUTASOORT)
            .naamvaluta(DEFAULT_NAAMVALUTA)
            .valutacode(DEFAULT_VALUTACODE);
        return valutasoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Valutasoort createUpdatedEntity(EntityManager em) {
        Valutasoort valutasoort = new Valutasoort()
            .datumbegingeldigheidvalutasoort(UPDATED_DATUMBEGINGELDIGHEIDVALUTASOORT)
            .datumeindegeldigheidvalutasoort(UPDATED_DATUMEINDEGELDIGHEIDVALUTASOORT)
            .naamvaluta(UPDATED_NAAMVALUTA)
            .valutacode(UPDATED_VALUTACODE);
        return valutasoort;
    }

    @BeforeEach
    public void initTest() {
        valutasoort = createEntity(em);
    }

    @Test
    @Transactional
    void createValutasoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Valutasoort
        var returnedValutasoort = om.readValue(
            restValutasoortMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(valutasoort)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Valutasoort.class
        );

        // Validate the Valutasoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertValutasoortUpdatableFieldsEquals(returnedValutasoort, getPersistedValutasoort(returnedValutasoort));
    }

    @Test
    @Transactional
    void createValutasoortWithExistingId() throws Exception {
        // Create the Valutasoort with an existing ID
        valutasoort.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restValutasoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(valutasoort)))
            .andExpect(status().isBadRequest());

        // Validate the Valutasoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllValutasoorts() throws Exception {
        // Initialize the database
        valutasoortRepository.saveAndFlush(valutasoort);

        // Get all the valutasoortList
        restValutasoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(valutasoort.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumbegingeldigheidvalutasoort").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEIDVALUTASOORT.toString())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheidvalutasoort").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEIDVALUTASOORT.toString())))
            .andExpect(jsonPath("$.[*].naamvaluta").value(hasItem(DEFAULT_NAAMVALUTA)))
            .andExpect(jsonPath("$.[*].valutacode").value(hasItem(DEFAULT_VALUTACODE)));
    }

    @Test
    @Transactional
    void getValutasoort() throws Exception {
        // Initialize the database
        valutasoortRepository.saveAndFlush(valutasoort);

        // Get the valutasoort
        restValutasoortMockMvc
            .perform(get(ENTITY_API_URL_ID, valutasoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(valutasoort.getId().intValue()))
            .andExpect(jsonPath("$.datumbegingeldigheidvalutasoort").value(DEFAULT_DATUMBEGINGELDIGHEIDVALUTASOORT.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheidvalutasoort").value(DEFAULT_DATUMEINDEGELDIGHEIDVALUTASOORT.toString()))
            .andExpect(jsonPath("$.naamvaluta").value(DEFAULT_NAAMVALUTA))
            .andExpect(jsonPath("$.valutacode").value(DEFAULT_VALUTACODE));
    }

    @Test
    @Transactional
    void getNonExistingValutasoort() throws Exception {
        // Get the valutasoort
        restValutasoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingValutasoort() throws Exception {
        // Initialize the database
        valutasoortRepository.saveAndFlush(valutasoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the valutasoort
        Valutasoort updatedValutasoort = valutasoortRepository.findById(valutasoort.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedValutasoort are not directly saved in db
        em.detach(updatedValutasoort);
        updatedValutasoort
            .datumbegingeldigheidvalutasoort(UPDATED_DATUMBEGINGELDIGHEIDVALUTASOORT)
            .datumeindegeldigheidvalutasoort(UPDATED_DATUMEINDEGELDIGHEIDVALUTASOORT)
            .naamvaluta(UPDATED_NAAMVALUTA)
            .valutacode(UPDATED_VALUTACODE);

        restValutasoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedValutasoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedValutasoort))
            )
            .andExpect(status().isOk());

        // Validate the Valutasoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedValutasoortToMatchAllProperties(updatedValutasoort);
    }

    @Test
    @Transactional
    void putNonExistingValutasoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        valutasoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restValutasoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, valutasoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(valutasoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Valutasoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchValutasoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        valutasoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restValutasoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(valutasoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Valutasoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamValutasoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        valutasoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restValutasoortMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(valutasoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Valutasoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateValutasoortWithPatch() throws Exception {
        // Initialize the database
        valutasoortRepository.saveAndFlush(valutasoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the valutasoort using partial update
        Valutasoort partialUpdatedValutasoort = new Valutasoort();
        partialUpdatedValutasoort.setId(valutasoort.getId());

        partialUpdatedValutasoort.naamvaluta(UPDATED_NAAMVALUTA);

        restValutasoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedValutasoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedValutasoort))
            )
            .andExpect(status().isOk());

        // Validate the Valutasoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertValutasoortUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedValutasoort, valutasoort),
            getPersistedValutasoort(valutasoort)
        );
    }

    @Test
    @Transactional
    void fullUpdateValutasoortWithPatch() throws Exception {
        // Initialize the database
        valutasoortRepository.saveAndFlush(valutasoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the valutasoort using partial update
        Valutasoort partialUpdatedValutasoort = new Valutasoort();
        partialUpdatedValutasoort.setId(valutasoort.getId());

        partialUpdatedValutasoort
            .datumbegingeldigheidvalutasoort(UPDATED_DATUMBEGINGELDIGHEIDVALUTASOORT)
            .datumeindegeldigheidvalutasoort(UPDATED_DATUMEINDEGELDIGHEIDVALUTASOORT)
            .naamvaluta(UPDATED_NAAMVALUTA)
            .valutacode(UPDATED_VALUTACODE);

        restValutasoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedValutasoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedValutasoort))
            )
            .andExpect(status().isOk());

        // Validate the Valutasoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertValutasoortUpdatableFieldsEquals(partialUpdatedValutasoort, getPersistedValutasoort(partialUpdatedValutasoort));
    }

    @Test
    @Transactional
    void patchNonExistingValutasoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        valutasoort.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restValutasoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, valutasoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(valutasoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Valutasoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchValutasoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        valutasoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restValutasoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(valutasoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Valutasoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamValutasoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        valutasoort.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restValutasoortMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(valutasoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Valutasoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteValutasoort() throws Exception {
        // Initialize the database
        valutasoortRepository.saveAndFlush(valutasoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the valutasoort
        restValutasoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, valutasoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return valutasoortRepository.count();
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

    protected Valutasoort getPersistedValutasoort(Valutasoort valutasoort) {
        return valutasoortRepository.findById(valutasoort.getId()).orElseThrow();
    }

    protected void assertPersistedValutasoortToMatchAllProperties(Valutasoort expectedValutasoort) {
        assertValutasoortAllPropertiesEquals(expectedValutasoort, getPersistedValutasoort(expectedValutasoort));
    }

    protected void assertPersistedValutasoortToMatchUpdatableProperties(Valutasoort expectedValutasoort) {
        assertValutasoortAllUpdatablePropertiesEquals(expectedValutasoort, getPersistedValutasoort(expectedValutasoort));
    }
}
