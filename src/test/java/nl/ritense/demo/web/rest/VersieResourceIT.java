package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VersieAsserts.*;
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
import nl.ritense.demo.domain.Versie;
import nl.ritense.demo.repository.VersieRepository;
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
 * Integration tests for the {@link VersieResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class VersieResourceIT {

    private static final String DEFAULT_AANTAL = "AAAAAAAAAA";
    private static final String UPDATED_AANTAL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMEINDESUPPORT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDESUPPORT = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_KOSTEN = new BigDecimal(1);
    private static final BigDecimal UPDATED_KOSTEN = new BigDecimal(2);

    private static final String DEFAULT_LICENTIE = "AAAAAAAAAA";
    private static final String UPDATED_LICENTIE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_VERSIENUMMER = "AAAAAAAA";
    private static final String UPDATED_VERSIENUMMER = "BBBBBBBB";

    private static final String ENTITY_API_URL = "/api/versies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VersieRepository versieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVersieMockMvc;

    private Versie versie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Versie createEntity(EntityManager em) {
        Versie versie = new Versie()
            .aantal(DEFAULT_AANTAL)
            .datumeindesupport(DEFAULT_DATUMEINDESUPPORT)
            .kosten(DEFAULT_KOSTEN)
            .licentie(DEFAULT_LICENTIE)
            .status(DEFAULT_STATUS)
            .versienummer(DEFAULT_VERSIENUMMER);
        return versie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Versie createUpdatedEntity(EntityManager em) {
        Versie versie = new Versie()
            .aantal(UPDATED_AANTAL)
            .datumeindesupport(UPDATED_DATUMEINDESUPPORT)
            .kosten(UPDATED_KOSTEN)
            .licentie(UPDATED_LICENTIE)
            .status(UPDATED_STATUS)
            .versienummer(UPDATED_VERSIENUMMER);
        return versie;
    }

    @BeforeEach
    public void initTest() {
        versie = createEntity(em);
    }

    @Test
    @Transactional
    void createVersie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Versie
        var returnedVersie = om.readValue(
            restVersieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(versie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Versie.class
        );

        // Validate the Versie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVersieUpdatableFieldsEquals(returnedVersie, getPersistedVersie(returnedVersie));
    }

    @Test
    @Transactional
    void createVersieWithExistingId() throws Exception {
        // Create the Versie with an existing ID
        versie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVersieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(versie)))
            .andExpect(status().isBadRequest());

        // Validate the Versie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVersies() throws Exception {
        // Initialize the database
        versieRepository.saveAndFlush(versie);

        // Get all the versieList
        restVersieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(versie.getId().intValue())))
            .andExpect(jsonPath("$.[*].aantal").value(hasItem(DEFAULT_AANTAL)))
            .andExpect(jsonPath("$.[*].datumeindesupport").value(hasItem(DEFAULT_DATUMEINDESUPPORT.toString())))
            .andExpect(jsonPath("$.[*].kosten").value(hasItem(sameNumber(DEFAULT_KOSTEN))))
            .andExpect(jsonPath("$.[*].licentie").value(hasItem(DEFAULT_LICENTIE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].versienummer").value(hasItem(DEFAULT_VERSIENUMMER)));
    }

    @Test
    @Transactional
    void getVersie() throws Exception {
        // Initialize the database
        versieRepository.saveAndFlush(versie);

        // Get the versie
        restVersieMockMvc
            .perform(get(ENTITY_API_URL_ID, versie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(versie.getId().intValue()))
            .andExpect(jsonPath("$.aantal").value(DEFAULT_AANTAL))
            .andExpect(jsonPath("$.datumeindesupport").value(DEFAULT_DATUMEINDESUPPORT.toString()))
            .andExpect(jsonPath("$.kosten").value(sameNumber(DEFAULT_KOSTEN)))
            .andExpect(jsonPath("$.licentie").value(DEFAULT_LICENTIE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.versienummer").value(DEFAULT_VERSIENUMMER));
    }

    @Test
    @Transactional
    void getNonExistingVersie() throws Exception {
        // Get the versie
        restVersieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVersie() throws Exception {
        // Initialize the database
        versieRepository.saveAndFlush(versie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the versie
        Versie updatedVersie = versieRepository.findById(versie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVersie are not directly saved in db
        em.detach(updatedVersie);
        updatedVersie
            .aantal(UPDATED_AANTAL)
            .datumeindesupport(UPDATED_DATUMEINDESUPPORT)
            .kosten(UPDATED_KOSTEN)
            .licentie(UPDATED_LICENTIE)
            .status(UPDATED_STATUS)
            .versienummer(UPDATED_VERSIENUMMER);

        restVersieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVersie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVersie))
            )
            .andExpect(status().isOk());

        // Validate the Versie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVersieToMatchAllProperties(updatedVersie);
    }

    @Test
    @Transactional
    void putNonExistingVersie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        versie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVersieMockMvc
            .perform(put(ENTITY_API_URL_ID, versie.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(versie)))
            .andExpect(status().isBadRequest());

        // Validate the Versie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVersie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        versie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVersieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(versie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Versie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVersie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        versie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVersieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(versie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Versie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVersieWithPatch() throws Exception {
        // Initialize the database
        versieRepository.saveAndFlush(versie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the versie using partial update
        Versie partialUpdatedVersie = new Versie();
        partialUpdatedVersie.setId(versie.getId());

        partialUpdatedVersie.kosten(UPDATED_KOSTEN).versienummer(UPDATED_VERSIENUMMER);

        restVersieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVersie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVersie))
            )
            .andExpect(status().isOk());

        // Validate the Versie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVersieUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedVersie, versie), getPersistedVersie(versie));
    }

    @Test
    @Transactional
    void fullUpdateVersieWithPatch() throws Exception {
        // Initialize the database
        versieRepository.saveAndFlush(versie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the versie using partial update
        Versie partialUpdatedVersie = new Versie();
        partialUpdatedVersie.setId(versie.getId());

        partialUpdatedVersie
            .aantal(UPDATED_AANTAL)
            .datumeindesupport(UPDATED_DATUMEINDESUPPORT)
            .kosten(UPDATED_KOSTEN)
            .licentie(UPDATED_LICENTIE)
            .status(UPDATED_STATUS)
            .versienummer(UPDATED_VERSIENUMMER);

        restVersieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVersie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVersie))
            )
            .andExpect(status().isOk());

        // Validate the Versie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVersieUpdatableFieldsEquals(partialUpdatedVersie, getPersistedVersie(partialUpdatedVersie));
    }

    @Test
    @Transactional
    void patchNonExistingVersie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        versie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVersieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, versie.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(versie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Versie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVersie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        versie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVersieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(versie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Versie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVersie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        versie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVersieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(versie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Versie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVersie() throws Exception {
        // Initialize the database
        versieRepository.saveAndFlush(versie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the versie
        restVersieMockMvc
            .perform(delete(ENTITY_API_URL_ID, versie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return versieRepository.count();
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

    protected Versie getPersistedVersie(Versie versie) {
        return versieRepository.findById(versie.getId()).orElseThrow();
    }

    protected void assertPersistedVersieToMatchAllProperties(Versie expectedVersie) {
        assertVersieAllPropertiesEquals(expectedVersie, getPersistedVersie(expectedVersie));
    }

    protected void assertPersistedVersieToMatchUpdatableProperties(Versie expectedVersie) {
        assertVersieAllUpdatablePropertiesEquals(expectedVersie, getPersistedVersie(expectedVersie));
    }
}
