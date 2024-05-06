package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ParkeerrechtAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static nl.ritense.demo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Parkeerrecht;
import nl.ritense.demo.repository.ParkeerrechtRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ParkeerrechtResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParkeerrechtResourceIT {

    private static final String DEFAULT_AANMAAKTIJD = "AAAAAAAAAA";
    private static final String UPDATED_AANMAAKTIJD = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_BEDRAGAANKOOP = new BigDecimal(1);
    private static final BigDecimal UPDATED_BEDRAGAANKOOP = new BigDecimal(2);

    private static final BigDecimal DEFAULT_BEDRAGBTW = new BigDecimal(1);
    private static final BigDecimal UPDATED_BEDRAGBTW = new BigDecimal(2);

    private static final String DEFAULT_DATUMTIJDEINDE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMTIJDEINDE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMTIJDSTART = "AAAAAAAAAA";
    private static final String UPDATED_DATUMTIJDSTART = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCTNAAM = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCTNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCTOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCTOMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/parkeerrechts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ParkeerrechtRepository parkeerrechtRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParkeerrechtMockMvc;

    private Parkeerrecht parkeerrecht;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parkeerrecht createEntity(EntityManager em) {
        Parkeerrecht parkeerrecht = new Parkeerrecht()
            .aanmaaktijd(DEFAULT_AANMAAKTIJD)
            .bedragaankoop(DEFAULT_BEDRAGAANKOOP)
            .bedragbtw(DEFAULT_BEDRAGBTW)
            .datumtijdeinde(DEFAULT_DATUMTIJDEINDE)
            .datumtijdstart(DEFAULT_DATUMTIJDSTART)
            .productnaam(DEFAULT_PRODUCTNAAM)
            .productomschrijving(DEFAULT_PRODUCTOMSCHRIJVING);
        return parkeerrecht;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parkeerrecht createUpdatedEntity(EntityManager em) {
        Parkeerrecht parkeerrecht = new Parkeerrecht()
            .aanmaaktijd(UPDATED_AANMAAKTIJD)
            .bedragaankoop(UPDATED_BEDRAGAANKOOP)
            .bedragbtw(UPDATED_BEDRAGBTW)
            .datumtijdeinde(UPDATED_DATUMTIJDEINDE)
            .datumtijdstart(UPDATED_DATUMTIJDSTART)
            .productnaam(UPDATED_PRODUCTNAAM)
            .productomschrijving(UPDATED_PRODUCTOMSCHRIJVING);
        return parkeerrecht;
    }

    @BeforeEach
    public void initTest() {
        parkeerrecht = createEntity(em);
    }

    @Test
    @Transactional
    void createParkeerrecht() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Parkeerrecht
        var returnedParkeerrecht = om.readValue(
            restParkeerrechtMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parkeerrecht)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Parkeerrecht.class
        );

        // Validate the Parkeerrecht in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertParkeerrechtUpdatableFieldsEquals(returnedParkeerrecht, getPersistedParkeerrecht(returnedParkeerrecht));
    }

    @Test
    @Transactional
    void createParkeerrechtWithExistingId() throws Exception {
        // Create the Parkeerrecht with an existing ID
        parkeerrecht.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParkeerrechtMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parkeerrecht)))
            .andExpect(status().isBadRequest());

        // Validate the Parkeerrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllParkeerrechts() throws Exception {
        // Initialize the database
        parkeerrechtRepository.saveAndFlush(parkeerrecht);

        // Get all the parkeerrechtList
        restParkeerrechtMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parkeerrecht.getId().intValue())))
            .andExpect(jsonPath("$.[*].aanmaaktijd").value(hasItem(DEFAULT_AANMAAKTIJD)))
            .andExpect(jsonPath("$.[*].bedragaankoop").value(hasItem(sameNumber(DEFAULT_BEDRAGAANKOOP))))
            .andExpect(jsonPath("$.[*].bedragbtw").value(hasItem(sameNumber(DEFAULT_BEDRAGBTW))))
            .andExpect(jsonPath("$.[*].datumtijdeinde").value(hasItem(DEFAULT_DATUMTIJDEINDE)))
            .andExpect(jsonPath("$.[*].datumtijdstart").value(hasItem(DEFAULT_DATUMTIJDSTART)))
            .andExpect(jsonPath("$.[*].productnaam").value(hasItem(DEFAULT_PRODUCTNAAM)))
            .andExpect(jsonPath("$.[*].productomschrijving").value(hasItem(DEFAULT_PRODUCTOMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getParkeerrecht() throws Exception {
        // Initialize the database
        parkeerrechtRepository.saveAndFlush(parkeerrecht);

        // Get the parkeerrecht
        restParkeerrechtMockMvc
            .perform(get(ENTITY_API_URL_ID, parkeerrecht.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(parkeerrecht.getId().intValue()))
            .andExpect(jsonPath("$.aanmaaktijd").value(DEFAULT_AANMAAKTIJD))
            .andExpect(jsonPath("$.bedragaankoop").value(sameNumber(DEFAULT_BEDRAGAANKOOP)))
            .andExpect(jsonPath("$.bedragbtw").value(sameNumber(DEFAULT_BEDRAGBTW)))
            .andExpect(jsonPath("$.datumtijdeinde").value(DEFAULT_DATUMTIJDEINDE))
            .andExpect(jsonPath("$.datumtijdstart").value(DEFAULT_DATUMTIJDSTART))
            .andExpect(jsonPath("$.productnaam").value(DEFAULT_PRODUCTNAAM))
            .andExpect(jsonPath("$.productomschrijving").value(DEFAULT_PRODUCTOMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingParkeerrecht() throws Exception {
        // Get the parkeerrecht
        restParkeerrechtMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingParkeerrecht() throws Exception {
        // Initialize the database
        parkeerrechtRepository.saveAndFlush(parkeerrecht);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the parkeerrecht
        Parkeerrecht updatedParkeerrecht = parkeerrechtRepository.findById(parkeerrecht.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedParkeerrecht are not directly saved in db
        em.detach(updatedParkeerrecht);
        updatedParkeerrecht
            .aanmaaktijd(UPDATED_AANMAAKTIJD)
            .bedragaankoop(UPDATED_BEDRAGAANKOOP)
            .bedragbtw(UPDATED_BEDRAGBTW)
            .datumtijdeinde(UPDATED_DATUMTIJDEINDE)
            .datumtijdstart(UPDATED_DATUMTIJDSTART)
            .productnaam(UPDATED_PRODUCTNAAM)
            .productomschrijving(UPDATED_PRODUCTOMSCHRIJVING);

        restParkeerrechtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedParkeerrecht.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedParkeerrecht))
            )
            .andExpect(status().isOk());

        // Validate the Parkeerrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedParkeerrechtToMatchAllProperties(updatedParkeerrecht);
    }

    @Test
    @Transactional
    void putNonExistingParkeerrecht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parkeerrecht.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParkeerrechtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parkeerrecht.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(parkeerrecht))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parkeerrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchParkeerrecht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parkeerrecht.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkeerrechtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(parkeerrecht))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parkeerrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamParkeerrecht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parkeerrecht.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkeerrechtMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parkeerrecht)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Parkeerrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateParkeerrechtWithPatch() throws Exception {
        // Initialize the database
        parkeerrechtRepository.saveAndFlush(parkeerrecht);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the parkeerrecht using partial update
        Parkeerrecht partialUpdatedParkeerrecht = new Parkeerrecht();
        partialUpdatedParkeerrecht.setId(parkeerrecht.getId());

        partialUpdatedParkeerrecht.datumtijdstart(UPDATED_DATUMTIJDSTART).productomschrijving(UPDATED_PRODUCTOMSCHRIJVING);

        restParkeerrechtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParkeerrecht.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedParkeerrecht))
            )
            .andExpect(status().isOk());

        // Validate the Parkeerrecht in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertParkeerrechtUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedParkeerrecht, parkeerrecht),
            getPersistedParkeerrecht(parkeerrecht)
        );
    }

    @Test
    @Transactional
    void fullUpdateParkeerrechtWithPatch() throws Exception {
        // Initialize the database
        parkeerrechtRepository.saveAndFlush(parkeerrecht);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the parkeerrecht using partial update
        Parkeerrecht partialUpdatedParkeerrecht = new Parkeerrecht();
        partialUpdatedParkeerrecht.setId(parkeerrecht.getId());

        partialUpdatedParkeerrecht
            .aanmaaktijd(UPDATED_AANMAAKTIJD)
            .bedragaankoop(UPDATED_BEDRAGAANKOOP)
            .bedragbtw(UPDATED_BEDRAGBTW)
            .datumtijdeinde(UPDATED_DATUMTIJDEINDE)
            .datumtijdstart(UPDATED_DATUMTIJDSTART)
            .productnaam(UPDATED_PRODUCTNAAM)
            .productomschrijving(UPDATED_PRODUCTOMSCHRIJVING);

        restParkeerrechtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParkeerrecht.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedParkeerrecht))
            )
            .andExpect(status().isOk());

        // Validate the Parkeerrecht in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertParkeerrechtUpdatableFieldsEquals(partialUpdatedParkeerrecht, getPersistedParkeerrecht(partialUpdatedParkeerrecht));
    }

    @Test
    @Transactional
    void patchNonExistingParkeerrecht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parkeerrecht.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParkeerrechtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, parkeerrecht.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(parkeerrecht))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parkeerrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchParkeerrecht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parkeerrecht.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkeerrechtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(parkeerrecht))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parkeerrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamParkeerrecht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parkeerrecht.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkeerrechtMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(parkeerrecht)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Parkeerrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteParkeerrecht() throws Exception {
        // Initialize the database
        parkeerrechtRepository.saveAndFlush(parkeerrecht);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the parkeerrecht
        restParkeerrechtMockMvc
            .perform(delete(ENTITY_API_URL_ID, parkeerrecht.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return parkeerrechtRepository.count();
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

    protected Parkeerrecht getPersistedParkeerrecht(Parkeerrecht parkeerrecht) {
        return parkeerrechtRepository.findById(parkeerrecht.getId()).orElseThrow();
    }

    protected void assertPersistedParkeerrechtToMatchAllProperties(Parkeerrecht expectedParkeerrecht) {
        assertParkeerrechtAllPropertiesEquals(expectedParkeerrecht, getPersistedParkeerrecht(expectedParkeerrecht));
    }

    protected void assertPersistedParkeerrechtToMatchUpdatableProperties(Parkeerrecht expectedParkeerrecht) {
        assertParkeerrechtAllUpdatablePropertiesEquals(expectedParkeerrecht, getPersistedParkeerrecht(expectedParkeerrecht));
    }
}
