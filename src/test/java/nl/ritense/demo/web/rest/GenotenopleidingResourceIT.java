package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.GenotenopleidingAsserts.*;
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
import nl.ritense.demo.domain.Genotenopleiding;
import nl.ritense.demo.repository.GenotenopleidingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GenotenopleidingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GenotenopleidingResourceIT {

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMTOEWIJZING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMTOEWIJZING = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_PRIJS = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRIJS = new BigDecimal(2);

    private static final String DEFAULT_VERREKENEN = "AAAAAAAAAA";
    private static final String UPDATED_VERREKENEN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/genotenopleidings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GenotenopleidingRepository genotenopleidingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGenotenopleidingMockMvc;

    private Genotenopleiding genotenopleiding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Genotenopleiding createEntity(EntityManager em) {
        Genotenopleiding genotenopleiding = new Genotenopleiding()
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumstart(DEFAULT_DATUMSTART)
            .datumtoewijzing(DEFAULT_DATUMTOEWIJZING)
            .prijs(DEFAULT_PRIJS)
            .verrekenen(DEFAULT_VERREKENEN);
        return genotenopleiding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Genotenopleiding createUpdatedEntity(EntityManager em) {
        Genotenopleiding genotenopleiding = new Genotenopleiding()
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .datumtoewijzing(UPDATED_DATUMTOEWIJZING)
            .prijs(UPDATED_PRIJS)
            .verrekenen(UPDATED_VERREKENEN);
        return genotenopleiding;
    }

    @BeforeEach
    public void initTest() {
        genotenopleiding = createEntity(em);
    }

    @Test
    @Transactional
    void createGenotenopleiding() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Genotenopleiding
        var returnedGenotenopleiding = om.readValue(
            restGenotenopleidingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(genotenopleiding)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Genotenopleiding.class
        );

        // Validate the Genotenopleiding in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGenotenopleidingUpdatableFieldsEquals(returnedGenotenopleiding, getPersistedGenotenopleiding(returnedGenotenopleiding));
    }

    @Test
    @Transactional
    void createGenotenopleidingWithExistingId() throws Exception {
        // Create the Genotenopleiding with an existing ID
        genotenopleiding.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGenotenopleidingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(genotenopleiding)))
            .andExpect(status().isBadRequest());

        // Validate the Genotenopleiding in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGenotenopleidings() throws Exception {
        // Initialize the database
        genotenopleidingRepository.saveAndFlush(genotenopleiding);

        // Get all the genotenopleidingList
        restGenotenopleidingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(genotenopleiding.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())))
            .andExpect(jsonPath("$.[*].datumtoewijzing").value(hasItem(DEFAULT_DATUMTOEWIJZING.toString())))
            .andExpect(jsonPath("$.[*].prijs").value(hasItem(sameNumber(DEFAULT_PRIJS))))
            .andExpect(jsonPath("$.[*].verrekenen").value(hasItem(DEFAULT_VERREKENEN)));
    }

    @Test
    @Transactional
    void getGenotenopleiding() throws Exception {
        // Initialize the database
        genotenopleidingRepository.saveAndFlush(genotenopleiding);

        // Get the genotenopleiding
        restGenotenopleidingMockMvc
            .perform(get(ENTITY_API_URL_ID, genotenopleiding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(genotenopleiding.getId().intValue()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()))
            .andExpect(jsonPath("$.datumtoewijzing").value(DEFAULT_DATUMTOEWIJZING.toString()))
            .andExpect(jsonPath("$.prijs").value(sameNumber(DEFAULT_PRIJS)))
            .andExpect(jsonPath("$.verrekenen").value(DEFAULT_VERREKENEN));
    }

    @Test
    @Transactional
    void getNonExistingGenotenopleiding() throws Exception {
        // Get the genotenopleiding
        restGenotenopleidingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGenotenopleiding() throws Exception {
        // Initialize the database
        genotenopleidingRepository.saveAndFlush(genotenopleiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the genotenopleiding
        Genotenopleiding updatedGenotenopleiding = genotenopleidingRepository.findById(genotenopleiding.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGenotenopleiding are not directly saved in db
        em.detach(updatedGenotenopleiding);
        updatedGenotenopleiding
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .datumtoewijzing(UPDATED_DATUMTOEWIJZING)
            .prijs(UPDATED_PRIJS)
            .verrekenen(UPDATED_VERREKENEN);

        restGenotenopleidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGenotenopleiding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGenotenopleiding))
            )
            .andExpect(status().isOk());

        // Validate the Genotenopleiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGenotenopleidingToMatchAllProperties(updatedGenotenopleiding);
    }

    @Test
    @Transactional
    void putNonExistingGenotenopleiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        genotenopleiding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGenotenopleidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, genotenopleiding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(genotenopleiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Genotenopleiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGenotenopleiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        genotenopleiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGenotenopleidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(genotenopleiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Genotenopleiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGenotenopleiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        genotenopleiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGenotenopleidingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(genotenopleiding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Genotenopleiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGenotenopleidingWithPatch() throws Exception {
        // Initialize the database
        genotenopleidingRepository.saveAndFlush(genotenopleiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the genotenopleiding using partial update
        Genotenopleiding partialUpdatedGenotenopleiding = new Genotenopleiding();
        partialUpdatedGenotenopleiding.setId(genotenopleiding.getId());

        partialUpdatedGenotenopleiding.datumstart(UPDATED_DATUMSTART).verrekenen(UPDATED_VERREKENEN);

        restGenotenopleidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGenotenopleiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGenotenopleiding))
            )
            .andExpect(status().isOk());

        // Validate the Genotenopleiding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGenotenopleidingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGenotenopleiding, genotenopleiding),
            getPersistedGenotenopleiding(genotenopleiding)
        );
    }

    @Test
    @Transactional
    void fullUpdateGenotenopleidingWithPatch() throws Exception {
        // Initialize the database
        genotenopleidingRepository.saveAndFlush(genotenopleiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the genotenopleiding using partial update
        Genotenopleiding partialUpdatedGenotenopleiding = new Genotenopleiding();
        partialUpdatedGenotenopleiding.setId(genotenopleiding.getId());

        partialUpdatedGenotenopleiding
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART)
            .datumtoewijzing(UPDATED_DATUMTOEWIJZING)
            .prijs(UPDATED_PRIJS)
            .verrekenen(UPDATED_VERREKENEN);

        restGenotenopleidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGenotenopleiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGenotenopleiding))
            )
            .andExpect(status().isOk());

        // Validate the Genotenopleiding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGenotenopleidingUpdatableFieldsEquals(
            partialUpdatedGenotenopleiding,
            getPersistedGenotenopleiding(partialUpdatedGenotenopleiding)
        );
    }

    @Test
    @Transactional
    void patchNonExistingGenotenopleiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        genotenopleiding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGenotenopleidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, genotenopleiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(genotenopleiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Genotenopleiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGenotenopleiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        genotenopleiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGenotenopleidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(genotenopleiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Genotenopleiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGenotenopleiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        genotenopleiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGenotenopleidingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(genotenopleiding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Genotenopleiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGenotenopleiding() throws Exception {
        // Initialize the database
        genotenopleidingRepository.saveAndFlush(genotenopleiding);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the genotenopleiding
        restGenotenopleidingMockMvc
            .perform(delete(ENTITY_API_URL_ID, genotenopleiding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return genotenopleidingRepository.count();
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

    protected Genotenopleiding getPersistedGenotenopleiding(Genotenopleiding genotenopleiding) {
        return genotenopleidingRepository.findById(genotenopleiding.getId()).orElseThrow();
    }

    protected void assertPersistedGenotenopleidingToMatchAllProperties(Genotenopleiding expectedGenotenopleiding) {
        assertGenotenopleidingAllPropertiesEquals(expectedGenotenopleiding, getPersistedGenotenopleiding(expectedGenotenopleiding));
    }

    protected void assertPersistedGenotenopleidingToMatchUpdatableProperties(Genotenopleiding expectedGenotenopleiding) {
        assertGenotenopleidingAllUpdatablePropertiesEquals(
            expectedGenotenopleiding,
            getPersistedGenotenopleiding(expectedGenotenopleiding)
        );
    }
}
