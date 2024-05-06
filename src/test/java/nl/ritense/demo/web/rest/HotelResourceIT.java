package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.HotelAsserts.*;
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
import nl.ritense.demo.domain.Hotel;
import nl.ritense.demo.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link HotelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HotelResourceIT {

    private static final String DEFAULT_AANTALKAMERS = "AAAAAAAAAA";
    private static final String UPDATED_AANTALKAMERS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/hotels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHotelMockMvc;

    private Hotel hotel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hotel createEntity(EntityManager em) {
        Hotel hotel = new Hotel().aantalkamers(DEFAULT_AANTALKAMERS);
        return hotel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hotel createUpdatedEntity(EntityManager em) {
        Hotel hotel = new Hotel().aantalkamers(UPDATED_AANTALKAMERS);
        return hotel;
    }

    @BeforeEach
    public void initTest() {
        hotel = createEntity(em);
    }

    @Test
    @Transactional
    void createHotel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Hotel
        var returnedHotel = om.readValue(
            restHotelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hotel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Hotel.class
        );

        // Validate the Hotel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertHotelUpdatableFieldsEquals(returnedHotel, getPersistedHotel(returnedHotel));
    }

    @Test
    @Transactional
    void createHotelWithExistingId() throws Exception {
        // Create the Hotel with an existing ID
        hotel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHotelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hotel)))
            .andExpect(status().isBadRequest());

        // Validate the Hotel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHotels() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList
        restHotelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hotel.getId().intValue())))
            .andExpect(jsonPath("$.[*].aantalkamers").value(hasItem(DEFAULT_AANTALKAMERS)));
    }

    @Test
    @Transactional
    void getHotel() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get the hotel
        restHotelMockMvc
            .perform(get(ENTITY_API_URL_ID, hotel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hotel.getId().intValue()))
            .andExpect(jsonPath("$.aantalkamers").value(DEFAULT_AANTALKAMERS));
    }

    @Test
    @Transactional
    void getNonExistingHotel() throws Exception {
        // Get the hotel
        restHotelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHotel() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hotel
        Hotel updatedHotel = hotelRepository.findById(hotel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHotel are not directly saved in db
        em.detach(updatedHotel);
        updatedHotel.aantalkamers(UPDATED_AANTALKAMERS);

        restHotelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHotel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedHotel))
            )
            .andExpect(status().isOk());

        // Validate the Hotel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHotelToMatchAllProperties(updatedHotel);
    }

    @Test
    @Transactional
    void putNonExistingHotel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hotel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHotelMockMvc
            .perform(put(ENTITY_API_URL_ID, hotel.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hotel)))
            .andExpect(status().isBadRequest());

        // Validate the Hotel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHotel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hotel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hotel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hotel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHotel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hotel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hotel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hotel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHotelWithPatch() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hotel using partial update
        Hotel partialUpdatedHotel = new Hotel();
        partialUpdatedHotel.setId(hotel.getId());

        restHotelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHotel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHotel))
            )
            .andExpect(status().isOk());

        // Validate the Hotel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHotelUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedHotel, hotel), getPersistedHotel(hotel));
    }

    @Test
    @Transactional
    void fullUpdateHotelWithPatch() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hotel using partial update
        Hotel partialUpdatedHotel = new Hotel();
        partialUpdatedHotel.setId(hotel.getId());

        partialUpdatedHotel.aantalkamers(UPDATED_AANTALKAMERS);

        restHotelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHotel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHotel))
            )
            .andExpect(status().isOk());

        // Validate the Hotel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHotelUpdatableFieldsEquals(partialUpdatedHotel, getPersistedHotel(partialUpdatedHotel));
    }

    @Test
    @Transactional
    void patchNonExistingHotel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hotel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHotelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hotel.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(hotel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hotel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHotel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hotel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hotel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hotel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHotel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hotel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(hotel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hotel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHotel() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the hotel
        restHotelMockMvc
            .perform(delete(ENTITY_API_URL_ID, hotel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return hotelRepository.count();
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

    protected Hotel getPersistedHotel(Hotel hotel) {
        return hotelRepository.findById(hotel.getId()).orElseThrow();
    }

    protected void assertPersistedHotelToMatchAllProperties(Hotel expectedHotel) {
        assertHotelAllPropertiesEquals(expectedHotel, getPersistedHotel(expectedHotel));
    }

    protected void assertPersistedHotelToMatchUpdatableProperties(Hotel expectedHotel) {
        assertHotelAllUpdatablePropertiesEquals(expectedHotel, getPersistedHotel(expectedHotel));
    }
}
