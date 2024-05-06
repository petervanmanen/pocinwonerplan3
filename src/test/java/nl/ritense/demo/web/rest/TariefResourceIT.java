package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.TariefAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static nl.ritense.demo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Tarief;
import nl.ritense.demo.repository.TariefRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TariefResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class TariefResourceIT {

    private static final BigDecimal DEFAULT_BEDRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_BEDRAG = new BigDecimal(2);

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EENHEID = "AAAAAAAAAA";
    private static final String UPDATED_EENHEID = "BBBBBBBBBB";

    private static final String DEFAULT_WET = "AAAAAAAAAA";
    private static final String UPDATED_WET = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tariefs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TariefRepository tariefRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTariefMockMvc;

    private Tarief tarief;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tarief createEntity(EntityManager em) {
        Tarief tarief = new Tarief()
            .bedrag(DEFAULT_BEDRAG)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumstart(DEFAULT_DATUMSTART)
            .eenheid(DEFAULT_EENHEID)
            .wet(DEFAULT_WET);
        return tarief;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tarief createUpdatedEntity(EntityManager em) {
        Tarief tarief = new Tarief()
            .bedrag(UPDATED_BEDRAG)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .eenheid(UPDATED_EENHEID)
            .wet(UPDATED_WET);
        return tarief;
    }

    @BeforeEach
    public void initTest() {
        tarief = createEntity(em);
    }

    @Test
    @Transactional
    void createTarief() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Tarief
        var returnedTarief = om.readValue(
            restTariefMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tarief)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Tarief.class
        );

        // Validate the Tarief in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTariefUpdatableFieldsEquals(returnedTarief, getPersistedTarief(returnedTarief));
    }

    @Test
    @Transactional
    void createTariefWithExistingId() throws Exception {
        // Create the Tarief with an existing ID
        tarief.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTariefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tarief)))
            .andExpect(status().isBadRequest());

        // Validate the Tarief in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTariefs() throws Exception {
        // Initialize the database
        tariefRepository.saveAndFlush(tarief);

        // Get all the tariefList
        restTariefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarief.getId().intValue())))
            .andExpect(jsonPath("$.[*].bedrag").value(hasItem(sameNumber(DEFAULT_BEDRAG))))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())))
            .andExpect(jsonPath("$.[*].eenheid").value(hasItem(DEFAULT_EENHEID)))
            .andExpect(jsonPath("$.[*].wet").value(hasItem(DEFAULT_WET)));
    }

    @Test
    @Transactional
    void getTarief() throws Exception {
        // Initialize the database
        tariefRepository.saveAndFlush(tarief);

        // Get the tarief
        restTariefMockMvc
            .perform(get(ENTITY_API_URL_ID, tarief.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tarief.getId().intValue()))
            .andExpect(jsonPath("$.bedrag").value(sameNumber(DEFAULT_BEDRAG)))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()))
            .andExpect(jsonPath("$.eenheid").value(DEFAULT_EENHEID))
            .andExpect(jsonPath("$.wet").value(DEFAULT_WET));
    }

    @Test
    @Transactional
    void getNonExistingTarief() throws Exception {
        // Get the tarief
        restTariefMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTarief() throws Exception {
        // Initialize the database
        tariefRepository.saveAndFlush(tarief);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tarief
        Tarief updatedTarief = tariefRepository.findById(tarief.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTarief are not directly saved in db
        em.detach(updatedTarief);
        updatedTarief
            .bedrag(UPDATED_BEDRAG)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .eenheid(UPDATED_EENHEID)
            .wet(UPDATED_WET);

        restTariefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTarief.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTarief))
            )
            .andExpect(status().isOk());

        // Validate the Tarief in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTariefToMatchAllProperties(updatedTarief);
    }

    @Test
    @Transactional
    void putNonExistingTarief() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarief.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTariefMockMvc
            .perform(put(ENTITY_API_URL_ID, tarief.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tarief)))
            .andExpect(status().isBadRequest());

        // Validate the Tarief in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTarief() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarief.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTariefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tarief))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tarief in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTarief() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarief.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTariefMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tarief)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tarief in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTariefWithPatch() throws Exception {
        // Initialize the database
        tariefRepository.saveAndFlush(tarief);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tarief using partial update
        Tarief partialUpdatedTarief = new Tarief();
        partialUpdatedTarief.setId(tarief.getId());

        partialUpdatedTarief.bedrag(UPDATED_BEDRAG).datumeinde(UPDATED_DATUMEINDE).datumstart(UPDATED_DATUMSTART);

        restTariefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTarief.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTarief))
            )
            .andExpect(status().isOk());

        // Validate the Tarief in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTariefUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedTarief, tarief), getPersistedTarief(tarief));
    }

    @Test
    @Transactional
    void fullUpdateTariefWithPatch() throws Exception {
        // Initialize the database
        tariefRepository.saveAndFlush(tarief);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tarief using partial update
        Tarief partialUpdatedTarief = new Tarief();
        partialUpdatedTarief.setId(tarief.getId());

        partialUpdatedTarief
            .bedrag(UPDATED_BEDRAG)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .eenheid(UPDATED_EENHEID)
            .wet(UPDATED_WET);

        restTariefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTarief.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTarief))
            )
            .andExpect(status().isOk());

        // Validate the Tarief in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTariefUpdatableFieldsEquals(partialUpdatedTarief, getPersistedTarief(partialUpdatedTarief));
    }

    @Test
    @Transactional
    void patchNonExistingTarief() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarief.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTariefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tarief.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tarief))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tarief in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTarief() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarief.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTariefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tarief))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tarief in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTarief() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarief.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTariefMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tarief)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tarief in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTarief() throws Exception {
        // Initialize the database
        tariefRepository.saveAndFlush(tarief);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tarief
        restTariefMockMvc
            .perform(delete(ENTITY_API_URL_ID, tarief.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tariefRepository.count();
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

    protected Tarief getPersistedTarief(Tarief tarief) {
        return tariefRepository.findById(tarief.getId()).orElseThrow();
    }

    protected void assertPersistedTariefToMatchAllProperties(Tarief expectedTarief) {
        assertTariefAllPropertiesEquals(expectedTarief, getPersistedTarief(expectedTarief));
    }

    protected void assertPersistedTariefToMatchUpdatableProperties(Tarief expectedTarief) {
        assertTariefAllUpdatablePropertiesEquals(expectedTarief, getPersistedTarief(expectedTarief));
    }
}
