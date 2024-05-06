package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BuurtAsserts.*;
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
import nl.ritense.demo.domain.Areaal;
import nl.ritense.demo.domain.Buurt;
import nl.ritense.demo.repository.BuurtRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BuurtResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BuurtResourceIT {

    private static final String DEFAULT_BUURTCODE = "AAAAAAAAAA";
    private static final String UPDATED_BUURTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_BUURTGEOMETRIE = "AAAAAAAAAA";
    private static final String UPDATED_BUURTGEOMETRIE = "BBBBBBBBBB";

    private static final String DEFAULT_BUURTNAAM = "AAAAAAAAAA";
    private static final String UPDATED_BUURTNAAM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDBUURT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDBUURT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDBUURT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDBUURT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMINGANG = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMINGANG = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_GECONSTATEERD = false;
    private static final Boolean UPDATED_GECONSTATEERD = true;

    private static final String DEFAULT_IDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INONDERZOEK = false;
    private static final Boolean UPDATED_INONDERZOEK = true;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_VERSIE = "AAAAAAAAAA";
    private static final String UPDATED_VERSIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/buurts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BuurtRepository buurtRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBuurtMockMvc;

    private Buurt buurt;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Buurt createEntity(EntityManager em) {
        Buurt buurt = new Buurt()
            .buurtcode(DEFAULT_BUURTCODE)
            .buurtgeometrie(DEFAULT_BUURTGEOMETRIE)
            .buurtnaam(DEFAULT_BUURTNAAM)
            .datumbegingeldigheidbuurt(DEFAULT_DATUMBEGINGELDIGHEIDBUURT)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumeindegeldigheidbuurt(DEFAULT_DATUMEINDEGELDIGHEIDBUURT)
            .datumingang(DEFAULT_DATUMINGANG)
            .geconstateerd(DEFAULT_GECONSTATEERD)
            .identificatie(DEFAULT_IDENTIFICATIE)
            .inonderzoek(DEFAULT_INONDERZOEK)
            .status(DEFAULT_STATUS)
            .versie(DEFAULT_VERSIE);
        // Add required entity
        Areaal areaal;
        if (TestUtil.findAll(em, Areaal.class).isEmpty()) {
            areaal = AreaalResourceIT.createEntity(em);
            em.persist(areaal);
            em.flush();
        } else {
            areaal = TestUtil.findAll(em, Areaal.class).get(0);
        }
        buurt.getLigtinAreaals().add(areaal);
        return buurt;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Buurt createUpdatedEntity(EntityManager em) {
        Buurt buurt = new Buurt()
            .buurtcode(UPDATED_BUURTCODE)
            .buurtgeometrie(UPDATED_BUURTGEOMETRIE)
            .buurtnaam(UPDATED_BUURTNAAM)
            .datumbegingeldigheidbuurt(UPDATED_DATUMBEGINGELDIGHEIDBUURT)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegeldigheidbuurt(UPDATED_DATUMEINDEGELDIGHEIDBUURT)
            .datumingang(UPDATED_DATUMINGANG)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .status(UPDATED_STATUS)
            .versie(UPDATED_VERSIE);
        // Add required entity
        Areaal areaal;
        if (TestUtil.findAll(em, Areaal.class).isEmpty()) {
            areaal = AreaalResourceIT.createUpdatedEntity(em);
            em.persist(areaal);
            em.flush();
        } else {
            areaal = TestUtil.findAll(em, Areaal.class).get(0);
        }
        buurt.getLigtinAreaals().add(areaal);
        return buurt;
    }

    @BeforeEach
    public void initTest() {
        buurt = createEntity(em);
    }

    @Test
    @Transactional
    void createBuurt() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Buurt
        var returnedBuurt = om.readValue(
            restBuurtMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buurt)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Buurt.class
        );

        // Validate the Buurt in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBuurtUpdatableFieldsEquals(returnedBuurt, getPersistedBuurt(returnedBuurt));
    }

    @Test
    @Transactional
    void createBuurtWithExistingId() throws Exception {
        // Create the Buurt with an existing ID
        buurt.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBuurtMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buurt)))
            .andExpect(status().isBadRequest());

        // Validate the Buurt in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBuurts() throws Exception {
        // Initialize the database
        buurtRepository.saveAndFlush(buurt);

        // Get all the buurtList
        restBuurtMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buurt.getId().intValue())))
            .andExpect(jsonPath("$.[*].buurtcode").value(hasItem(DEFAULT_BUURTCODE)))
            .andExpect(jsonPath("$.[*].buurtgeometrie").value(hasItem(DEFAULT_BUURTGEOMETRIE)))
            .andExpect(jsonPath("$.[*].buurtnaam").value(hasItem(DEFAULT_BUURTNAAM)))
            .andExpect(jsonPath("$.[*].datumbegingeldigheidbuurt").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEIDBUURT.toString())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheidbuurt").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEIDBUURT.toString())))
            .andExpect(jsonPath("$.[*].datumingang").value(hasItem(DEFAULT_DATUMINGANG.toString())))
            .andExpect(jsonPath("$.[*].geconstateerd").value(hasItem(DEFAULT_GECONSTATEERD.booleanValue())))
            .andExpect(jsonPath("$.[*].identificatie").value(hasItem(DEFAULT_IDENTIFICATIE)))
            .andExpect(jsonPath("$.[*].inonderzoek").value(hasItem(DEFAULT_INONDERZOEK.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].versie").value(hasItem(DEFAULT_VERSIE)));
    }

    @Test
    @Transactional
    void getBuurt() throws Exception {
        // Initialize the database
        buurtRepository.saveAndFlush(buurt);

        // Get the buurt
        restBuurtMockMvc
            .perform(get(ENTITY_API_URL_ID, buurt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(buurt.getId().intValue()))
            .andExpect(jsonPath("$.buurtcode").value(DEFAULT_BUURTCODE))
            .andExpect(jsonPath("$.buurtgeometrie").value(DEFAULT_BUURTGEOMETRIE))
            .andExpect(jsonPath("$.buurtnaam").value(DEFAULT_BUURTNAAM))
            .andExpect(jsonPath("$.datumbegingeldigheidbuurt").value(DEFAULT_DATUMBEGINGELDIGHEIDBUURT.toString()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheidbuurt").value(DEFAULT_DATUMEINDEGELDIGHEIDBUURT.toString()))
            .andExpect(jsonPath("$.datumingang").value(DEFAULT_DATUMINGANG.toString()))
            .andExpect(jsonPath("$.geconstateerd").value(DEFAULT_GECONSTATEERD.booleanValue()))
            .andExpect(jsonPath("$.identificatie").value(DEFAULT_IDENTIFICATIE))
            .andExpect(jsonPath("$.inonderzoek").value(DEFAULT_INONDERZOEK.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.versie").value(DEFAULT_VERSIE));
    }

    @Test
    @Transactional
    void getNonExistingBuurt() throws Exception {
        // Get the buurt
        restBuurtMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBuurt() throws Exception {
        // Initialize the database
        buurtRepository.saveAndFlush(buurt);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the buurt
        Buurt updatedBuurt = buurtRepository.findById(buurt.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBuurt are not directly saved in db
        em.detach(updatedBuurt);
        updatedBuurt
            .buurtcode(UPDATED_BUURTCODE)
            .buurtgeometrie(UPDATED_BUURTGEOMETRIE)
            .buurtnaam(UPDATED_BUURTNAAM)
            .datumbegingeldigheidbuurt(UPDATED_DATUMBEGINGELDIGHEIDBUURT)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegeldigheidbuurt(UPDATED_DATUMEINDEGELDIGHEIDBUURT)
            .datumingang(UPDATED_DATUMINGANG)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .status(UPDATED_STATUS)
            .versie(UPDATED_VERSIE);

        restBuurtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBuurt.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBuurt))
            )
            .andExpect(status().isOk());

        // Validate the Buurt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBuurtToMatchAllProperties(updatedBuurt);
    }

    @Test
    @Transactional
    void putNonExistingBuurt() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        buurt.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuurtMockMvc
            .perform(put(ENTITY_API_URL_ID, buurt.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buurt)))
            .andExpect(status().isBadRequest());

        // Validate the Buurt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBuurt() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        buurt.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuurtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(buurt))
            )
            .andExpect(status().isBadRequest());

        // Validate the Buurt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBuurt() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        buurt.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuurtMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buurt)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Buurt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBuurtWithPatch() throws Exception {
        // Initialize the database
        buurtRepository.saveAndFlush(buurt);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the buurt using partial update
        Buurt partialUpdatedBuurt = new Buurt();
        partialUpdatedBuurt.setId(buurt.getId());

        partialUpdatedBuurt
            .buurtnaam(UPDATED_BUURTNAAM)
            .datumeindegeldigheidbuurt(UPDATED_DATUMEINDEGELDIGHEIDBUURT)
            .status(UPDATED_STATUS);

        restBuurtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBuurt.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBuurt))
            )
            .andExpect(status().isOk());

        // Validate the Buurt in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBuurtUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBuurt, buurt), getPersistedBuurt(buurt));
    }

    @Test
    @Transactional
    void fullUpdateBuurtWithPatch() throws Exception {
        // Initialize the database
        buurtRepository.saveAndFlush(buurt);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the buurt using partial update
        Buurt partialUpdatedBuurt = new Buurt();
        partialUpdatedBuurt.setId(buurt.getId());

        partialUpdatedBuurt
            .buurtcode(UPDATED_BUURTCODE)
            .buurtgeometrie(UPDATED_BUURTGEOMETRIE)
            .buurtnaam(UPDATED_BUURTNAAM)
            .datumbegingeldigheidbuurt(UPDATED_DATUMBEGINGELDIGHEIDBUURT)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegeldigheidbuurt(UPDATED_DATUMEINDEGELDIGHEIDBUURT)
            .datumingang(UPDATED_DATUMINGANG)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .status(UPDATED_STATUS)
            .versie(UPDATED_VERSIE);

        restBuurtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBuurt.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBuurt))
            )
            .andExpect(status().isOk());

        // Validate the Buurt in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBuurtUpdatableFieldsEquals(partialUpdatedBuurt, getPersistedBuurt(partialUpdatedBuurt));
    }

    @Test
    @Transactional
    void patchNonExistingBuurt() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        buurt.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuurtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, buurt.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(buurt))
            )
            .andExpect(status().isBadRequest());

        // Validate the Buurt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBuurt() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        buurt.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuurtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(buurt))
            )
            .andExpect(status().isBadRequest());

        // Validate the Buurt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBuurt() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        buurt.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuurtMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(buurt)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Buurt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBuurt() throws Exception {
        // Initialize the database
        buurtRepository.saveAndFlush(buurt);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the buurt
        restBuurtMockMvc
            .perform(delete(ENTITY_API_URL_ID, buurt.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return buurtRepository.count();
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

    protected Buurt getPersistedBuurt(Buurt buurt) {
        return buurtRepository.findById(buurt.getId()).orElseThrow();
    }

    protected void assertPersistedBuurtToMatchAllProperties(Buurt expectedBuurt) {
        assertBuurtAllPropertiesEquals(expectedBuurt, getPersistedBuurt(expectedBuurt));
    }

    protected void assertPersistedBuurtToMatchUpdatableProperties(Buurt expectedBuurt) {
        assertBuurtAllUpdatablePropertiesEquals(expectedBuurt, getPersistedBuurt(expectedBuurt));
    }
}
