package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VorderingregelAsserts.*;
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
import nl.ritense.demo.domain.Vorderingregel;
import nl.ritense.demo.repository.VorderingregelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VorderingregelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VorderingregelResourceIT {

    private static final String DEFAULT_AANGEMAAKTDOOR = "AAAAAAAAAA";
    private static final String UPDATED_AANGEMAAKTDOOR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_AANMAAKDATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_AANMAAKDATUM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_BEDRAGEXCLBTW = "AAAAAAAAAA";
    private static final String UPDATED_BEDRAGEXCLBTW = "BBBBBBBBBB";

    private static final String DEFAULT_BEDRAGINCLBTW = "AAAAAAAAAA";
    private static final String UPDATED_BEDRAGINCLBTW = "BBBBBBBBBB";

    private static final String DEFAULT_BTWCATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_BTWCATEGORIE = "BBBBBBBBBB";

    private static final String DEFAULT_GEMUTEERDDOOR = "AAAAAAAAAA";
    private static final String UPDATED_GEMUTEERDDOOR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MUTATIEDATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MUTATIEDATUM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_PERIODIEK = "AAAAAAAAAA";
    private static final String UPDATED_PERIODIEK = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vorderingregels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VorderingregelRepository vorderingregelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVorderingregelMockMvc;

    private Vorderingregel vorderingregel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vorderingregel createEntity(EntityManager em) {
        Vorderingregel vorderingregel = new Vorderingregel()
            .aangemaaktdoor(DEFAULT_AANGEMAAKTDOOR)
            .aanmaakdatum(DEFAULT_AANMAAKDATUM)
            .bedragexclbtw(DEFAULT_BEDRAGEXCLBTW)
            .bedraginclbtw(DEFAULT_BEDRAGINCLBTW)
            .btwcategorie(DEFAULT_BTWCATEGORIE)
            .gemuteerddoor(DEFAULT_GEMUTEERDDOOR)
            .mutatiedatum(DEFAULT_MUTATIEDATUM)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .periodiek(DEFAULT_PERIODIEK)
            .type(DEFAULT_TYPE);
        return vorderingregel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vorderingregel createUpdatedEntity(EntityManager em) {
        Vorderingregel vorderingregel = new Vorderingregel()
            .aangemaaktdoor(UPDATED_AANGEMAAKTDOOR)
            .aanmaakdatum(UPDATED_AANMAAKDATUM)
            .bedragexclbtw(UPDATED_BEDRAGEXCLBTW)
            .bedraginclbtw(UPDATED_BEDRAGINCLBTW)
            .btwcategorie(UPDATED_BTWCATEGORIE)
            .gemuteerddoor(UPDATED_GEMUTEERDDOOR)
            .mutatiedatum(UPDATED_MUTATIEDATUM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .periodiek(UPDATED_PERIODIEK)
            .type(UPDATED_TYPE);
        return vorderingregel;
    }

    @BeforeEach
    public void initTest() {
        vorderingregel = createEntity(em);
    }

    @Test
    @Transactional
    void createVorderingregel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vorderingregel
        var returnedVorderingregel = om.readValue(
            restVorderingregelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vorderingregel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Vorderingregel.class
        );

        // Validate the Vorderingregel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVorderingregelUpdatableFieldsEquals(returnedVorderingregel, getPersistedVorderingregel(returnedVorderingregel));
    }

    @Test
    @Transactional
    void createVorderingregelWithExistingId() throws Exception {
        // Create the Vorderingregel with an existing ID
        vorderingregel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVorderingregelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vorderingregel)))
            .andExpect(status().isBadRequest());

        // Validate the Vorderingregel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVorderingregels() throws Exception {
        // Initialize the database
        vorderingregelRepository.saveAndFlush(vorderingregel);

        // Get all the vorderingregelList
        restVorderingregelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vorderingregel.getId().intValue())))
            .andExpect(jsonPath("$.[*].aangemaaktdoor").value(hasItem(DEFAULT_AANGEMAAKTDOOR)))
            .andExpect(jsonPath("$.[*].aanmaakdatum").value(hasItem(DEFAULT_AANMAAKDATUM.toString())))
            .andExpect(jsonPath("$.[*].bedragexclbtw").value(hasItem(DEFAULT_BEDRAGEXCLBTW)))
            .andExpect(jsonPath("$.[*].bedraginclbtw").value(hasItem(DEFAULT_BEDRAGINCLBTW)))
            .andExpect(jsonPath("$.[*].btwcategorie").value(hasItem(DEFAULT_BTWCATEGORIE)))
            .andExpect(jsonPath("$.[*].gemuteerddoor").value(hasItem(DEFAULT_GEMUTEERDDOOR)))
            .andExpect(jsonPath("$.[*].mutatiedatum").value(hasItem(DEFAULT_MUTATIEDATUM.toString())))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].periodiek").value(hasItem(DEFAULT_PERIODIEK)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getVorderingregel() throws Exception {
        // Initialize the database
        vorderingregelRepository.saveAndFlush(vorderingregel);

        // Get the vorderingregel
        restVorderingregelMockMvc
            .perform(get(ENTITY_API_URL_ID, vorderingregel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vorderingregel.getId().intValue()))
            .andExpect(jsonPath("$.aangemaaktdoor").value(DEFAULT_AANGEMAAKTDOOR))
            .andExpect(jsonPath("$.aanmaakdatum").value(DEFAULT_AANMAAKDATUM.toString()))
            .andExpect(jsonPath("$.bedragexclbtw").value(DEFAULT_BEDRAGEXCLBTW))
            .andExpect(jsonPath("$.bedraginclbtw").value(DEFAULT_BEDRAGINCLBTW))
            .andExpect(jsonPath("$.btwcategorie").value(DEFAULT_BTWCATEGORIE))
            .andExpect(jsonPath("$.gemuteerddoor").value(DEFAULT_GEMUTEERDDOOR))
            .andExpect(jsonPath("$.mutatiedatum").value(DEFAULT_MUTATIEDATUM.toString()))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.periodiek").value(DEFAULT_PERIODIEK))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingVorderingregel() throws Exception {
        // Get the vorderingregel
        restVorderingregelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVorderingregel() throws Exception {
        // Initialize the database
        vorderingregelRepository.saveAndFlush(vorderingregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vorderingregel
        Vorderingregel updatedVorderingregel = vorderingregelRepository.findById(vorderingregel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVorderingregel are not directly saved in db
        em.detach(updatedVorderingregel);
        updatedVorderingregel
            .aangemaaktdoor(UPDATED_AANGEMAAKTDOOR)
            .aanmaakdatum(UPDATED_AANMAAKDATUM)
            .bedragexclbtw(UPDATED_BEDRAGEXCLBTW)
            .bedraginclbtw(UPDATED_BEDRAGINCLBTW)
            .btwcategorie(UPDATED_BTWCATEGORIE)
            .gemuteerddoor(UPDATED_GEMUTEERDDOOR)
            .mutatiedatum(UPDATED_MUTATIEDATUM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .periodiek(UPDATED_PERIODIEK)
            .type(UPDATED_TYPE);

        restVorderingregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVorderingregel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVorderingregel))
            )
            .andExpect(status().isOk());

        // Validate the Vorderingregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVorderingregelToMatchAllProperties(updatedVorderingregel);
    }

    @Test
    @Transactional
    void putNonExistingVorderingregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vorderingregel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVorderingregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vorderingregel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vorderingregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vorderingregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVorderingregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vorderingregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVorderingregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vorderingregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vorderingregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVorderingregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vorderingregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVorderingregelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vorderingregel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vorderingregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVorderingregelWithPatch() throws Exception {
        // Initialize the database
        vorderingregelRepository.saveAndFlush(vorderingregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vorderingregel using partial update
        Vorderingregel partialUpdatedVorderingregel = new Vorderingregel();
        partialUpdatedVorderingregel.setId(vorderingregel.getId());

        partialUpdatedVorderingregel
            .bedraginclbtw(UPDATED_BEDRAGINCLBTW)
            .btwcategorie(UPDATED_BTWCATEGORIE)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restVorderingregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVorderingregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVorderingregel))
            )
            .andExpect(status().isOk());

        // Validate the Vorderingregel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVorderingregelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVorderingregel, vorderingregel),
            getPersistedVorderingregel(vorderingregel)
        );
    }

    @Test
    @Transactional
    void fullUpdateVorderingregelWithPatch() throws Exception {
        // Initialize the database
        vorderingregelRepository.saveAndFlush(vorderingregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vorderingregel using partial update
        Vorderingregel partialUpdatedVorderingregel = new Vorderingregel();
        partialUpdatedVorderingregel.setId(vorderingregel.getId());

        partialUpdatedVorderingregel
            .aangemaaktdoor(UPDATED_AANGEMAAKTDOOR)
            .aanmaakdatum(UPDATED_AANMAAKDATUM)
            .bedragexclbtw(UPDATED_BEDRAGEXCLBTW)
            .bedraginclbtw(UPDATED_BEDRAGINCLBTW)
            .btwcategorie(UPDATED_BTWCATEGORIE)
            .gemuteerddoor(UPDATED_GEMUTEERDDOOR)
            .mutatiedatum(UPDATED_MUTATIEDATUM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .periodiek(UPDATED_PERIODIEK)
            .type(UPDATED_TYPE);

        restVorderingregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVorderingregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVorderingregel))
            )
            .andExpect(status().isOk());

        // Validate the Vorderingregel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVorderingregelUpdatableFieldsEquals(partialUpdatedVorderingregel, getPersistedVorderingregel(partialUpdatedVorderingregel));
    }

    @Test
    @Transactional
    void patchNonExistingVorderingregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vorderingregel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVorderingregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vorderingregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vorderingregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vorderingregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVorderingregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vorderingregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVorderingregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vorderingregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vorderingregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVorderingregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vorderingregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVorderingregelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vorderingregel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vorderingregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVorderingregel() throws Exception {
        // Initialize the database
        vorderingregelRepository.saveAndFlush(vorderingregel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vorderingregel
        restVorderingregelMockMvc
            .perform(delete(ENTITY_API_URL_ID, vorderingregel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vorderingregelRepository.count();
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

    protected Vorderingregel getPersistedVorderingregel(Vorderingregel vorderingregel) {
        return vorderingregelRepository.findById(vorderingregel.getId()).orElseThrow();
    }

    protected void assertPersistedVorderingregelToMatchAllProperties(Vorderingregel expectedVorderingregel) {
        assertVorderingregelAllPropertiesEquals(expectedVorderingregel, getPersistedVorderingregel(expectedVorderingregel));
    }

    protected void assertPersistedVorderingregelToMatchUpdatableProperties(Vorderingregel expectedVorderingregel) {
        assertVorderingregelAllUpdatablePropertiesEquals(expectedVorderingregel, getPersistedVorderingregel(expectedVorderingregel));
    }
}
