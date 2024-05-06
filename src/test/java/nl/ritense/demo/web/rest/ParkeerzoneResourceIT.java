package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ParkeerzoneAsserts.*;
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
import nl.ritense.demo.domain.Parkeerzone;
import nl.ritense.demo.repository.ParkeerzoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ParkeerzoneResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParkeerzoneResourceIT {

    private static final String DEFAULT_AANTALPARKEERVLAKKEN = "AAAAAAAAAA";
    private static final String UPDATED_AANTALPARKEERVLAKKEN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ALLEENDAGTARIEF = false;
    private static final Boolean UPDATED_ALLEENDAGTARIEF = true;

    private static final BigDecimal DEFAULT_DAGTARIEF = new BigDecimal(1);
    private static final BigDecimal UPDATED_DAGTARIEF = new BigDecimal(2);

    private static final String DEFAULT_EINDEDAG = "AAAAAAAAAA";
    private static final String UPDATED_EINDEDAG = "BBBBBBBBBB";

    private static final String DEFAULT_EINDTIJD = "AAAAAAAAAA";
    private static final String UPDATED_EINDTIJD = "BBBBBBBBBB";

    private static final String DEFAULT_GEBRUIK = "AAAAAAAAAA";
    private static final String UPDATED_GEBRUIK = "BBBBBBBBBB";

    private static final String DEFAULT_GEOMETRIE = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIE = "BBBBBBBBBB";

    private static final String DEFAULT_IPMCODE = "AAAAAAAAAA";
    private static final String UPDATED_IPMCODE = "BBBBBBBBBB";

    private static final String DEFAULT_IPMNAAM = "AAAAAAAAAA";
    private static final String UPDATED_IPMNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PARKEERGARAGE = false;
    private static final Boolean UPDATED_PARKEERGARAGE = true;

    private static final String DEFAULT_SECTORCODE = "AAAAAAAAAA";
    private static final String UPDATED_SECTORCODE = "BBBBBBBBBB";

    private static final String DEFAULT_SOORTCODE = "AAAAAAAAAA";
    private static final String UPDATED_SOORTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_STARTDAG = "AAAAAAAAAA";
    private static final String UPDATED_STARTDAG = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_STARTTARIEF = new BigDecimal(1);
    private static final BigDecimal UPDATED_STARTTARIEF = new BigDecimal(2);

    private static final String DEFAULT_STARTTIJD = "AAAAAAAAAA";
    private static final String UPDATED_STARTTIJD = "BBBBBBBBBB";

    private static final String DEFAULT_TYPECODE = "AAAAAAAAAA";
    private static final String UPDATED_TYPECODE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPENAAM = "AAAAAAAAAA";
    private static final String UPDATED_TYPENAAM = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_UURTARIEF = new BigDecimal(1);
    private static final BigDecimal UPDATED_UURTARIEF = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/parkeerzones";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ParkeerzoneRepository parkeerzoneRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParkeerzoneMockMvc;

    private Parkeerzone parkeerzone;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parkeerzone createEntity(EntityManager em) {
        Parkeerzone parkeerzone = new Parkeerzone()
            .aantalparkeervlakken(DEFAULT_AANTALPARKEERVLAKKEN)
            .alleendagtarief(DEFAULT_ALLEENDAGTARIEF)
            .dagtarief(DEFAULT_DAGTARIEF)
            .eindedag(DEFAULT_EINDEDAG)
            .eindtijd(DEFAULT_EINDTIJD)
            .gebruik(DEFAULT_GEBRUIK)
            .geometrie(DEFAULT_GEOMETRIE)
            .ipmcode(DEFAULT_IPMCODE)
            .ipmnaam(DEFAULT_IPMNAAM)
            .naam(DEFAULT_NAAM)
            .parkeergarage(DEFAULT_PARKEERGARAGE)
            .sectorcode(DEFAULT_SECTORCODE)
            .soortcode(DEFAULT_SOORTCODE)
            .startdag(DEFAULT_STARTDAG)
            .starttarief(DEFAULT_STARTTARIEF)
            .starttijd(DEFAULT_STARTTIJD)
            .typecode(DEFAULT_TYPECODE)
            .typenaam(DEFAULT_TYPENAAM)
            .uurtarief(DEFAULT_UURTARIEF);
        return parkeerzone;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parkeerzone createUpdatedEntity(EntityManager em) {
        Parkeerzone parkeerzone = new Parkeerzone()
            .aantalparkeervlakken(UPDATED_AANTALPARKEERVLAKKEN)
            .alleendagtarief(UPDATED_ALLEENDAGTARIEF)
            .dagtarief(UPDATED_DAGTARIEF)
            .eindedag(UPDATED_EINDEDAG)
            .eindtijd(UPDATED_EINDTIJD)
            .gebruik(UPDATED_GEBRUIK)
            .geometrie(UPDATED_GEOMETRIE)
            .ipmcode(UPDATED_IPMCODE)
            .ipmnaam(UPDATED_IPMNAAM)
            .naam(UPDATED_NAAM)
            .parkeergarage(UPDATED_PARKEERGARAGE)
            .sectorcode(UPDATED_SECTORCODE)
            .soortcode(UPDATED_SOORTCODE)
            .startdag(UPDATED_STARTDAG)
            .starttarief(UPDATED_STARTTARIEF)
            .starttijd(UPDATED_STARTTIJD)
            .typecode(UPDATED_TYPECODE)
            .typenaam(UPDATED_TYPENAAM)
            .uurtarief(UPDATED_UURTARIEF);
        return parkeerzone;
    }

    @BeforeEach
    public void initTest() {
        parkeerzone = createEntity(em);
    }

    @Test
    @Transactional
    void createParkeerzone() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Parkeerzone
        var returnedParkeerzone = om.readValue(
            restParkeerzoneMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parkeerzone)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Parkeerzone.class
        );

        // Validate the Parkeerzone in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertParkeerzoneUpdatableFieldsEquals(returnedParkeerzone, getPersistedParkeerzone(returnedParkeerzone));
    }

    @Test
    @Transactional
    void createParkeerzoneWithExistingId() throws Exception {
        // Create the Parkeerzone with an existing ID
        parkeerzone.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParkeerzoneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parkeerzone)))
            .andExpect(status().isBadRequest());

        // Validate the Parkeerzone in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllParkeerzones() throws Exception {
        // Initialize the database
        parkeerzoneRepository.saveAndFlush(parkeerzone);

        // Get all the parkeerzoneList
        restParkeerzoneMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parkeerzone.getId().intValue())))
            .andExpect(jsonPath("$.[*].aantalparkeervlakken").value(hasItem(DEFAULT_AANTALPARKEERVLAKKEN)))
            .andExpect(jsonPath("$.[*].alleendagtarief").value(hasItem(DEFAULT_ALLEENDAGTARIEF.booleanValue())))
            .andExpect(jsonPath("$.[*].dagtarief").value(hasItem(sameNumber(DEFAULT_DAGTARIEF))))
            .andExpect(jsonPath("$.[*].eindedag").value(hasItem(DEFAULT_EINDEDAG)))
            .andExpect(jsonPath("$.[*].eindtijd").value(hasItem(DEFAULT_EINDTIJD)))
            .andExpect(jsonPath("$.[*].gebruik").value(hasItem(DEFAULT_GEBRUIK)))
            .andExpect(jsonPath("$.[*].geometrie").value(hasItem(DEFAULT_GEOMETRIE)))
            .andExpect(jsonPath("$.[*].ipmcode").value(hasItem(DEFAULT_IPMCODE)))
            .andExpect(jsonPath("$.[*].ipmnaam").value(hasItem(DEFAULT_IPMNAAM)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].parkeergarage").value(hasItem(DEFAULT_PARKEERGARAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].sectorcode").value(hasItem(DEFAULT_SECTORCODE)))
            .andExpect(jsonPath("$.[*].soortcode").value(hasItem(DEFAULT_SOORTCODE)))
            .andExpect(jsonPath("$.[*].startdag").value(hasItem(DEFAULT_STARTDAG)))
            .andExpect(jsonPath("$.[*].starttarief").value(hasItem(sameNumber(DEFAULT_STARTTARIEF))))
            .andExpect(jsonPath("$.[*].starttijd").value(hasItem(DEFAULT_STARTTIJD)))
            .andExpect(jsonPath("$.[*].typecode").value(hasItem(DEFAULT_TYPECODE)))
            .andExpect(jsonPath("$.[*].typenaam").value(hasItem(DEFAULT_TYPENAAM)))
            .andExpect(jsonPath("$.[*].uurtarief").value(hasItem(sameNumber(DEFAULT_UURTARIEF))));
    }

    @Test
    @Transactional
    void getParkeerzone() throws Exception {
        // Initialize the database
        parkeerzoneRepository.saveAndFlush(parkeerzone);

        // Get the parkeerzone
        restParkeerzoneMockMvc
            .perform(get(ENTITY_API_URL_ID, parkeerzone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(parkeerzone.getId().intValue()))
            .andExpect(jsonPath("$.aantalparkeervlakken").value(DEFAULT_AANTALPARKEERVLAKKEN))
            .andExpect(jsonPath("$.alleendagtarief").value(DEFAULT_ALLEENDAGTARIEF.booleanValue()))
            .andExpect(jsonPath("$.dagtarief").value(sameNumber(DEFAULT_DAGTARIEF)))
            .andExpect(jsonPath("$.eindedag").value(DEFAULT_EINDEDAG))
            .andExpect(jsonPath("$.eindtijd").value(DEFAULT_EINDTIJD))
            .andExpect(jsonPath("$.gebruik").value(DEFAULT_GEBRUIK))
            .andExpect(jsonPath("$.geometrie").value(DEFAULT_GEOMETRIE))
            .andExpect(jsonPath("$.ipmcode").value(DEFAULT_IPMCODE))
            .andExpect(jsonPath("$.ipmnaam").value(DEFAULT_IPMNAAM))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.parkeergarage").value(DEFAULT_PARKEERGARAGE.booleanValue()))
            .andExpect(jsonPath("$.sectorcode").value(DEFAULT_SECTORCODE))
            .andExpect(jsonPath("$.soortcode").value(DEFAULT_SOORTCODE))
            .andExpect(jsonPath("$.startdag").value(DEFAULT_STARTDAG))
            .andExpect(jsonPath("$.starttarief").value(sameNumber(DEFAULT_STARTTARIEF)))
            .andExpect(jsonPath("$.starttijd").value(DEFAULT_STARTTIJD))
            .andExpect(jsonPath("$.typecode").value(DEFAULT_TYPECODE))
            .andExpect(jsonPath("$.typenaam").value(DEFAULT_TYPENAAM))
            .andExpect(jsonPath("$.uurtarief").value(sameNumber(DEFAULT_UURTARIEF)));
    }

    @Test
    @Transactional
    void getNonExistingParkeerzone() throws Exception {
        // Get the parkeerzone
        restParkeerzoneMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingParkeerzone() throws Exception {
        // Initialize the database
        parkeerzoneRepository.saveAndFlush(parkeerzone);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the parkeerzone
        Parkeerzone updatedParkeerzone = parkeerzoneRepository.findById(parkeerzone.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedParkeerzone are not directly saved in db
        em.detach(updatedParkeerzone);
        updatedParkeerzone
            .aantalparkeervlakken(UPDATED_AANTALPARKEERVLAKKEN)
            .alleendagtarief(UPDATED_ALLEENDAGTARIEF)
            .dagtarief(UPDATED_DAGTARIEF)
            .eindedag(UPDATED_EINDEDAG)
            .eindtijd(UPDATED_EINDTIJD)
            .gebruik(UPDATED_GEBRUIK)
            .geometrie(UPDATED_GEOMETRIE)
            .ipmcode(UPDATED_IPMCODE)
            .ipmnaam(UPDATED_IPMNAAM)
            .naam(UPDATED_NAAM)
            .parkeergarage(UPDATED_PARKEERGARAGE)
            .sectorcode(UPDATED_SECTORCODE)
            .soortcode(UPDATED_SOORTCODE)
            .startdag(UPDATED_STARTDAG)
            .starttarief(UPDATED_STARTTARIEF)
            .starttijd(UPDATED_STARTTIJD)
            .typecode(UPDATED_TYPECODE)
            .typenaam(UPDATED_TYPENAAM)
            .uurtarief(UPDATED_UURTARIEF);

        restParkeerzoneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedParkeerzone.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedParkeerzone))
            )
            .andExpect(status().isOk());

        // Validate the Parkeerzone in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedParkeerzoneToMatchAllProperties(updatedParkeerzone);
    }

    @Test
    @Transactional
    void putNonExistingParkeerzone() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parkeerzone.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParkeerzoneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parkeerzone.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(parkeerzone))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parkeerzone in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchParkeerzone() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parkeerzone.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkeerzoneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(parkeerzone))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parkeerzone in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamParkeerzone() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parkeerzone.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkeerzoneMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(parkeerzone)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Parkeerzone in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateParkeerzoneWithPatch() throws Exception {
        // Initialize the database
        parkeerzoneRepository.saveAndFlush(parkeerzone);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the parkeerzone using partial update
        Parkeerzone partialUpdatedParkeerzone = new Parkeerzone();
        partialUpdatedParkeerzone.setId(parkeerzone.getId());

        partialUpdatedParkeerzone
            .aantalparkeervlakken(UPDATED_AANTALPARKEERVLAKKEN)
            .dagtarief(UPDATED_DAGTARIEF)
            .eindtijd(UPDATED_EINDTIJD)
            .ipmcode(UPDATED_IPMCODE)
            .naam(UPDATED_NAAM)
            .parkeergarage(UPDATED_PARKEERGARAGE)
            .sectorcode(UPDATED_SECTORCODE)
            .soortcode(UPDATED_SOORTCODE)
            .starttijd(UPDATED_STARTTIJD)
            .uurtarief(UPDATED_UURTARIEF);

        restParkeerzoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParkeerzone.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedParkeerzone))
            )
            .andExpect(status().isOk());

        // Validate the Parkeerzone in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertParkeerzoneUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedParkeerzone, parkeerzone),
            getPersistedParkeerzone(parkeerzone)
        );
    }

    @Test
    @Transactional
    void fullUpdateParkeerzoneWithPatch() throws Exception {
        // Initialize the database
        parkeerzoneRepository.saveAndFlush(parkeerzone);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the parkeerzone using partial update
        Parkeerzone partialUpdatedParkeerzone = new Parkeerzone();
        partialUpdatedParkeerzone.setId(parkeerzone.getId());

        partialUpdatedParkeerzone
            .aantalparkeervlakken(UPDATED_AANTALPARKEERVLAKKEN)
            .alleendagtarief(UPDATED_ALLEENDAGTARIEF)
            .dagtarief(UPDATED_DAGTARIEF)
            .eindedag(UPDATED_EINDEDAG)
            .eindtijd(UPDATED_EINDTIJD)
            .gebruik(UPDATED_GEBRUIK)
            .geometrie(UPDATED_GEOMETRIE)
            .ipmcode(UPDATED_IPMCODE)
            .ipmnaam(UPDATED_IPMNAAM)
            .naam(UPDATED_NAAM)
            .parkeergarage(UPDATED_PARKEERGARAGE)
            .sectorcode(UPDATED_SECTORCODE)
            .soortcode(UPDATED_SOORTCODE)
            .startdag(UPDATED_STARTDAG)
            .starttarief(UPDATED_STARTTARIEF)
            .starttijd(UPDATED_STARTTIJD)
            .typecode(UPDATED_TYPECODE)
            .typenaam(UPDATED_TYPENAAM)
            .uurtarief(UPDATED_UURTARIEF);

        restParkeerzoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParkeerzone.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedParkeerzone))
            )
            .andExpect(status().isOk());

        // Validate the Parkeerzone in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertParkeerzoneUpdatableFieldsEquals(partialUpdatedParkeerzone, getPersistedParkeerzone(partialUpdatedParkeerzone));
    }

    @Test
    @Transactional
    void patchNonExistingParkeerzone() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parkeerzone.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParkeerzoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, parkeerzone.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(parkeerzone))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parkeerzone in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchParkeerzone() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parkeerzone.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkeerzoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(parkeerzone))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parkeerzone in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamParkeerzone() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        parkeerzone.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkeerzoneMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(parkeerzone)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Parkeerzone in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteParkeerzone() throws Exception {
        // Initialize the database
        parkeerzoneRepository.saveAndFlush(parkeerzone);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the parkeerzone
        restParkeerzoneMockMvc
            .perform(delete(ENTITY_API_URL_ID, parkeerzone.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return parkeerzoneRepository.count();
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

    protected Parkeerzone getPersistedParkeerzone(Parkeerzone parkeerzone) {
        return parkeerzoneRepository.findById(parkeerzone.getId()).orElseThrow();
    }

    protected void assertPersistedParkeerzoneToMatchAllProperties(Parkeerzone expectedParkeerzone) {
        assertParkeerzoneAllPropertiesEquals(expectedParkeerzone, getPersistedParkeerzone(expectedParkeerzone));
    }

    protected void assertPersistedParkeerzoneToMatchUpdatableProperties(Parkeerzone expectedParkeerzone) {
        assertParkeerzoneAllUpdatablePropertiesEquals(expectedParkeerzone, getPersistedParkeerzone(expectedParkeerzone));
    }
}
