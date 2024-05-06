package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.HotelbezoekAsserts.*;
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
import nl.ritense.demo.domain.Hotelbezoek;
import nl.ritense.demo.repository.HotelbezoekRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link HotelbezoekResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HotelbezoekResourceIT {

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/hotelbezoeks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HotelbezoekRepository hotelbezoekRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHotelbezoekMockMvc;

    private Hotelbezoek hotelbezoek;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hotelbezoek createEntity(EntityManager em) {
        Hotelbezoek hotelbezoek = new Hotelbezoek().datumeinde(DEFAULT_DATUMEINDE).datumstart(DEFAULT_DATUMSTART);
        return hotelbezoek;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hotelbezoek createUpdatedEntity(EntityManager em) {
        Hotelbezoek hotelbezoek = new Hotelbezoek().datumeinde(UPDATED_DATUMEINDE).datumstart(UPDATED_DATUMSTART);
        return hotelbezoek;
    }

    @BeforeEach
    public void initTest() {
        hotelbezoek = createEntity(em);
    }

    @Test
    @Transactional
    void createHotelbezoek() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Hotelbezoek
        var returnedHotelbezoek = om.readValue(
            restHotelbezoekMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hotelbezoek)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Hotelbezoek.class
        );

        // Validate the Hotelbezoek in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertHotelbezoekUpdatableFieldsEquals(returnedHotelbezoek, getPersistedHotelbezoek(returnedHotelbezoek));
    }

    @Test
    @Transactional
    void createHotelbezoekWithExistingId() throws Exception {
        // Create the Hotelbezoek with an existing ID
        hotelbezoek.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHotelbezoekMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hotelbezoek)))
            .andExpect(status().isBadRequest());

        // Validate the Hotelbezoek in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHotelbezoeks() throws Exception {
        // Initialize the database
        hotelbezoekRepository.saveAndFlush(hotelbezoek);

        // Get all the hotelbezoekList
        restHotelbezoekMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hotelbezoek.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())));
    }

    @Test
    @Transactional
    void getHotelbezoek() throws Exception {
        // Initialize the database
        hotelbezoekRepository.saveAndFlush(hotelbezoek);

        // Get the hotelbezoek
        restHotelbezoekMockMvc
            .perform(get(ENTITY_API_URL_ID, hotelbezoek.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hotelbezoek.getId().intValue()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()));
    }

    @Test
    @Transactional
    void getNonExistingHotelbezoek() throws Exception {
        // Get the hotelbezoek
        restHotelbezoekMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHotelbezoek() throws Exception {
        // Initialize the database
        hotelbezoekRepository.saveAndFlush(hotelbezoek);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hotelbezoek
        Hotelbezoek updatedHotelbezoek = hotelbezoekRepository.findById(hotelbezoek.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHotelbezoek are not directly saved in db
        em.detach(updatedHotelbezoek);
        updatedHotelbezoek.datumeinde(UPDATED_DATUMEINDE).datumstart(UPDATED_DATUMSTART);

        restHotelbezoekMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHotelbezoek.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedHotelbezoek))
            )
            .andExpect(status().isOk());

        // Validate the Hotelbezoek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHotelbezoekToMatchAllProperties(updatedHotelbezoek);
    }

    @Test
    @Transactional
    void putNonExistingHotelbezoek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hotelbezoek.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHotelbezoekMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hotelbezoek.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hotelbezoek))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hotelbezoek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHotelbezoek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hotelbezoek.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelbezoekMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hotelbezoek))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hotelbezoek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHotelbezoek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hotelbezoek.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelbezoekMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hotelbezoek)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hotelbezoek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHotelbezoekWithPatch() throws Exception {
        // Initialize the database
        hotelbezoekRepository.saveAndFlush(hotelbezoek);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hotelbezoek using partial update
        Hotelbezoek partialUpdatedHotelbezoek = new Hotelbezoek();
        partialUpdatedHotelbezoek.setId(hotelbezoek.getId());

        restHotelbezoekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHotelbezoek.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHotelbezoek))
            )
            .andExpect(status().isOk());

        // Validate the Hotelbezoek in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHotelbezoekUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedHotelbezoek, hotelbezoek),
            getPersistedHotelbezoek(hotelbezoek)
        );
    }

    @Test
    @Transactional
    void fullUpdateHotelbezoekWithPatch() throws Exception {
        // Initialize the database
        hotelbezoekRepository.saveAndFlush(hotelbezoek);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hotelbezoek using partial update
        Hotelbezoek partialUpdatedHotelbezoek = new Hotelbezoek();
        partialUpdatedHotelbezoek.setId(hotelbezoek.getId());

        partialUpdatedHotelbezoek.datumeinde(UPDATED_DATUMEINDE).datumstart(UPDATED_DATUMSTART);

        restHotelbezoekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHotelbezoek.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHotelbezoek))
            )
            .andExpect(status().isOk());

        // Validate the Hotelbezoek in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHotelbezoekUpdatableFieldsEquals(partialUpdatedHotelbezoek, getPersistedHotelbezoek(partialUpdatedHotelbezoek));
    }

    @Test
    @Transactional
    void patchNonExistingHotelbezoek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hotelbezoek.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHotelbezoekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hotelbezoek.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hotelbezoek))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hotelbezoek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHotelbezoek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hotelbezoek.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelbezoekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hotelbezoek))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hotelbezoek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHotelbezoek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hotelbezoek.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelbezoekMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(hotelbezoek)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hotelbezoek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHotelbezoek() throws Exception {
        // Initialize the database
        hotelbezoekRepository.saveAndFlush(hotelbezoek);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the hotelbezoek
        restHotelbezoekMockMvc
            .perform(delete(ENTITY_API_URL_ID, hotelbezoek.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return hotelbezoekRepository.count();
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

    protected Hotelbezoek getPersistedHotelbezoek(Hotelbezoek hotelbezoek) {
        return hotelbezoekRepository.findById(hotelbezoek.getId()).orElseThrow();
    }

    protected void assertPersistedHotelbezoekToMatchAllProperties(Hotelbezoek expectedHotelbezoek) {
        assertHotelbezoekAllPropertiesEquals(expectedHotelbezoek, getPersistedHotelbezoek(expectedHotelbezoek));
    }

    protected void assertPersistedHotelbezoekToMatchUpdatableProperties(Hotelbezoek expectedHotelbezoek) {
        assertHotelbezoekAllUpdatablePropertiesEquals(expectedHotelbezoek, getPersistedHotelbezoek(expectedHotelbezoek));
    }
}
