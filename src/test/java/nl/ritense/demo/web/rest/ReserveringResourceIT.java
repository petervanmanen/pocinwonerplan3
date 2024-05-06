package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ReserveringAsserts.*;
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
import nl.ritense.demo.domain.Reservering;
import nl.ritense.demo.repository.ReserveringRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ReserveringResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReserveringResourceIT {

    private static final String DEFAULT_AANTAL = "AAAAAAAAAA";
    private static final String UPDATED_AANTAL = "BBBBBBBBBB";

    private static final String DEFAULT_BTW = "AAAAAAAA";
    private static final String UPDATED_BTW = "BBBBBBBB";

    private static final String DEFAULT_TIJDTOT = "AAAAAAAAAA";
    private static final String UPDATED_TIJDTOT = "BBBBBBBBBB";

    private static final String DEFAULT_TIJDVANAF = "AAAAAAAAAA";
    private static final String UPDATED_TIJDVANAF = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAALPRIJS = "AAAAAAAAAA";
    private static final String UPDATED_TOTAALPRIJS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/reserverings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ReserveringRepository reserveringRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReserveringMockMvc;

    private Reservering reservering;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reservering createEntity(EntityManager em) {
        Reservering reservering = new Reservering()
            .aantal(DEFAULT_AANTAL)
            .btw(DEFAULT_BTW)
            .tijdtot(DEFAULT_TIJDTOT)
            .tijdvanaf(DEFAULT_TIJDVANAF)
            .totaalprijs(DEFAULT_TOTAALPRIJS);
        return reservering;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reservering createUpdatedEntity(EntityManager em) {
        Reservering reservering = new Reservering()
            .aantal(UPDATED_AANTAL)
            .btw(UPDATED_BTW)
            .tijdtot(UPDATED_TIJDTOT)
            .tijdvanaf(UPDATED_TIJDVANAF)
            .totaalprijs(UPDATED_TOTAALPRIJS);
        return reservering;
    }

    @BeforeEach
    public void initTest() {
        reservering = createEntity(em);
    }

    @Test
    @Transactional
    void createReservering() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Reservering
        var returnedReservering = om.readValue(
            restReserveringMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reservering)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Reservering.class
        );

        // Validate the Reservering in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertReserveringUpdatableFieldsEquals(returnedReservering, getPersistedReservering(returnedReservering));
    }

    @Test
    @Transactional
    void createReserveringWithExistingId() throws Exception {
        // Create the Reservering with an existing ID
        reservering.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReserveringMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reservering)))
            .andExpect(status().isBadRequest());

        // Validate the Reservering in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllReserverings() throws Exception {
        // Initialize the database
        reserveringRepository.saveAndFlush(reservering);

        // Get all the reserveringList
        restReserveringMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reservering.getId().intValue())))
            .andExpect(jsonPath("$.[*].aantal").value(hasItem(DEFAULT_AANTAL)))
            .andExpect(jsonPath("$.[*].btw").value(hasItem(DEFAULT_BTW)))
            .andExpect(jsonPath("$.[*].tijdtot").value(hasItem(DEFAULT_TIJDTOT)))
            .andExpect(jsonPath("$.[*].tijdvanaf").value(hasItem(DEFAULT_TIJDVANAF)))
            .andExpect(jsonPath("$.[*].totaalprijs").value(hasItem(DEFAULT_TOTAALPRIJS)));
    }

    @Test
    @Transactional
    void getReservering() throws Exception {
        // Initialize the database
        reserveringRepository.saveAndFlush(reservering);

        // Get the reservering
        restReserveringMockMvc
            .perform(get(ENTITY_API_URL_ID, reservering.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reservering.getId().intValue()))
            .andExpect(jsonPath("$.aantal").value(DEFAULT_AANTAL))
            .andExpect(jsonPath("$.btw").value(DEFAULT_BTW))
            .andExpect(jsonPath("$.tijdtot").value(DEFAULT_TIJDTOT))
            .andExpect(jsonPath("$.tijdvanaf").value(DEFAULT_TIJDVANAF))
            .andExpect(jsonPath("$.totaalprijs").value(DEFAULT_TOTAALPRIJS));
    }

    @Test
    @Transactional
    void getNonExistingReservering() throws Exception {
        // Get the reservering
        restReserveringMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingReservering() throws Exception {
        // Initialize the database
        reserveringRepository.saveAndFlush(reservering);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the reservering
        Reservering updatedReservering = reserveringRepository.findById(reservering.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedReservering are not directly saved in db
        em.detach(updatedReservering);
        updatedReservering
            .aantal(UPDATED_AANTAL)
            .btw(UPDATED_BTW)
            .tijdtot(UPDATED_TIJDTOT)
            .tijdvanaf(UPDATED_TIJDVANAF)
            .totaalprijs(UPDATED_TOTAALPRIJS);

        restReserveringMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedReservering.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedReservering))
            )
            .andExpect(status().isOk());

        // Validate the Reservering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedReserveringToMatchAllProperties(updatedReservering);
    }

    @Test
    @Transactional
    void putNonExistingReservering() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reservering.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReserveringMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reservering.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(reservering))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reservering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReservering() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reservering.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReserveringMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(reservering))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reservering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReservering() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reservering.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReserveringMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reservering)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reservering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReserveringWithPatch() throws Exception {
        // Initialize the database
        reserveringRepository.saveAndFlush(reservering);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the reservering using partial update
        Reservering partialUpdatedReservering = new Reservering();
        partialUpdatedReservering.setId(reservering.getId());

        partialUpdatedReservering.aantal(UPDATED_AANTAL).btw(UPDATED_BTW).tijdtot(UPDATED_TIJDTOT);

        restReserveringMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReservering.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedReservering))
            )
            .andExpect(status().isOk());

        // Validate the Reservering in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertReserveringUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedReservering, reservering),
            getPersistedReservering(reservering)
        );
    }

    @Test
    @Transactional
    void fullUpdateReserveringWithPatch() throws Exception {
        // Initialize the database
        reserveringRepository.saveAndFlush(reservering);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the reservering using partial update
        Reservering partialUpdatedReservering = new Reservering();
        partialUpdatedReservering.setId(reservering.getId());

        partialUpdatedReservering
            .aantal(UPDATED_AANTAL)
            .btw(UPDATED_BTW)
            .tijdtot(UPDATED_TIJDTOT)
            .tijdvanaf(UPDATED_TIJDVANAF)
            .totaalprijs(UPDATED_TOTAALPRIJS);

        restReserveringMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReservering.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedReservering))
            )
            .andExpect(status().isOk());

        // Validate the Reservering in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertReserveringUpdatableFieldsEquals(partialUpdatedReservering, getPersistedReservering(partialUpdatedReservering));
    }

    @Test
    @Transactional
    void patchNonExistingReservering() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reservering.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReserveringMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reservering.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(reservering))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reservering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReservering() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reservering.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReserveringMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(reservering))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reservering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReservering() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reservering.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReserveringMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(reservering)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reservering in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReservering() throws Exception {
        // Initialize the database
        reserveringRepository.saveAndFlush(reservering);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the reservering
        restReserveringMockMvc
            .perform(delete(ENTITY_API_URL_ID, reservering.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return reserveringRepository.count();
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

    protected Reservering getPersistedReservering(Reservering reservering) {
        return reserveringRepository.findById(reservering.getId()).orElseThrow();
    }

    protected void assertPersistedReserveringToMatchAllProperties(Reservering expectedReservering) {
        assertReserveringAllPropertiesEquals(expectedReservering, getPersistedReservering(expectedReservering));
    }

    protected void assertPersistedReserveringToMatchUpdatableProperties(Reservering expectedReservering) {
        assertReserveringAllUpdatablePropertiesEquals(expectedReservering, getPersistedReservering(expectedReservering));
    }
}
