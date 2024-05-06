package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VthmeldingAsserts.*;
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
import nl.ritense.demo.domain.Vthmelding;
import nl.ritense.demo.repository.VthmeldingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VthmeldingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VthmeldingResourceIT {

    private static final String DEFAULT_ACTIVITEIT = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITEIT = "BBBBBBBBBB";

    private static final String DEFAULT_BEOORDELING = "AAAAAAAAAA";
    private static final String UPDATED_BEOORDELING = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMSEPONERING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSEPONERING = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DATUMTIJDTOT = "AAAAAAAAAA";
    private static final String UPDATED_DATUMTIJDTOT = "BBBBBBBBBB";

    private static final String DEFAULT_GESEPONEERD = "AAAAAAAAAA";
    private static final String UPDATED_GESEPONEERD = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATIE = "AAAAAAAAAA";
    private static final String UPDATED_LOCATIE = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANISATIEONDERDEEL = "AAAAAAAAAA";
    private static final String UPDATED_ORGANISATIEONDERDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_OVERTREDINGSCODE = "AAAAAAAAAA";
    private static final String UPDATED_OVERTREDINGSCODE = "BBBBBBBBBB";

    private static final String DEFAULT_OVERTREDINGSGROEP = "AAAAAAAAAA";
    private static final String UPDATED_OVERTREDINGSGROEP = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENTIENUMMER = "AAAAAAAAAA";
    private static final String UPDATED_REFERENTIENUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_RESULTAAT = "AAAAAAAAAA";
    private static final String UPDATED_RESULTAAT = "BBBBBBBBBB";

    private static final String DEFAULT_SOORTVTHMELDING = "AAAAAAAAAA";
    private static final String UPDATED_SOORTVTHMELDING = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_STRAATNAAM = "AAAAAAAAAA";
    private static final String UPDATED_STRAATNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_TAAKTYPE = "AAAAAAAAAA";
    private static final String UPDATED_TAAKTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ZAAKNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_ZAAKNUMMER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vthmeldings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VthmeldingRepository vthmeldingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVthmeldingMockMvc;

    private Vthmelding vthmelding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vthmelding createEntity(EntityManager em) {
        Vthmelding vthmelding = new Vthmelding()
            .activiteit(DEFAULT_ACTIVITEIT)
            .beoordeling(DEFAULT_BEOORDELING)
            .datumseponering(DEFAULT_DATUMSEPONERING)
            .datumtijdtot(DEFAULT_DATUMTIJDTOT)
            .geseponeerd(DEFAULT_GESEPONEERD)
            .locatie(DEFAULT_LOCATIE)
            .organisatieonderdeel(DEFAULT_ORGANISATIEONDERDEEL)
            .overtredingscode(DEFAULT_OVERTREDINGSCODE)
            .overtredingsgroep(DEFAULT_OVERTREDINGSGROEP)
            .referentienummer(DEFAULT_REFERENTIENUMMER)
            .resultaat(DEFAULT_RESULTAAT)
            .soortvthmelding(DEFAULT_SOORTVTHMELDING)
            .status(DEFAULT_STATUS)
            .straatnaam(DEFAULT_STRAATNAAM)
            .taaktype(DEFAULT_TAAKTYPE)
            .zaaknummer(DEFAULT_ZAAKNUMMER);
        return vthmelding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vthmelding createUpdatedEntity(EntityManager em) {
        Vthmelding vthmelding = new Vthmelding()
            .activiteit(UPDATED_ACTIVITEIT)
            .beoordeling(UPDATED_BEOORDELING)
            .datumseponering(UPDATED_DATUMSEPONERING)
            .datumtijdtot(UPDATED_DATUMTIJDTOT)
            .geseponeerd(UPDATED_GESEPONEERD)
            .locatie(UPDATED_LOCATIE)
            .organisatieonderdeel(UPDATED_ORGANISATIEONDERDEEL)
            .overtredingscode(UPDATED_OVERTREDINGSCODE)
            .overtredingsgroep(UPDATED_OVERTREDINGSGROEP)
            .referentienummer(UPDATED_REFERENTIENUMMER)
            .resultaat(UPDATED_RESULTAAT)
            .soortvthmelding(UPDATED_SOORTVTHMELDING)
            .status(UPDATED_STATUS)
            .straatnaam(UPDATED_STRAATNAAM)
            .taaktype(UPDATED_TAAKTYPE)
            .zaaknummer(UPDATED_ZAAKNUMMER);
        return vthmelding;
    }

    @BeforeEach
    public void initTest() {
        vthmelding = createEntity(em);
    }

    @Test
    @Transactional
    void createVthmelding() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vthmelding
        var returnedVthmelding = om.readValue(
            restVthmeldingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vthmelding)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Vthmelding.class
        );

        // Validate the Vthmelding in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVthmeldingUpdatableFieldsEquals(returnedVthmelding, getPersistedVthmelding(returnedVthmelding));
    }

    @Test
    @Transactional
    void createVthmeldingWithExistingId() throws Exception {
        // Create the Vthmelding with an existing ID
        vthmelding.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVthmeldingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vthmelding)))
            .andExpect(status().isBadRequest());

        // Validate the Vthmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVthmeldings() throws Exception {
        // Initialize the database
        vthmeldingRepository.saveAndFlush(vthmelding);

        // Get all the vthmeldingList
        restVthmeldingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vthmelding.getId().intValue())))
            .andExpect(jsonPath("$.[*].activiteit").value(hasItem(DEFAULT_ACTIVITEIT)))
            .andExpect(jsonPath("$.[*].beoordeling").value(hasItem(DEFAULT_BEOORDELING)))
            .andExpect(jsonPath("$.[*].datumseponering").value(hasItem(DEFAULT_DATUMSEPONERING.toString())))
            .andExpect(jsonPath("$.[*].datumtijdtot").value(hasItem(DEFAULT_DATUMTIJDTOT)))
            .andExpect(jsonPath("$.[*].geseponeerd").value(hasItem(DEFAULT_GESEPONEERD)))
            .andExpect(jsonPath("$.[*].locatie").value(hasItem(DEFAULT_LOCATIE)))
            .andExpect(jsonPath("$.[*].organisatieonderdeel").value(hasItem(DEFAULT_ORGANISATIEONDERDEEL)))
            .andExpect(jsonPath("$.[*].overtredingscode").value(hasItem(DEFAULT_OVERTREDINGSCODE)))
            .andExpect(jsonPath("$.[*].overtredingsgroep").value(hasItem(DEFAULT_OVERTREDINGSGROEP)))
            .andExpect(jsonPath("$.[*].referentienummer").value(hasItem(DEFAULT_REFERENTIENUMMER)))
            .andExpect(jsonPath("$.[*].resultaat").value(hasItem(DEFAULT_RESULTAAT)))
            .andExpect(jsonPath("$.[*].soortvthmelding").value(hasItem(DEFAULT_SOORTVTHMELDING)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].straatnaam").value(hasItem(DEFAULT_STRAATNAAM)))
            .andExpect(jsonPath("$.[*].taaktype").value(hasItem(DEFAULT_TAAKTYPE)))
            .andExpect(jsonPath("$.[*].zaaknummer").value(hasItem(DEFAULT_ZAAKNUMMER)));
    }

    @Test
    @Transactional
    void getVthmelding() throws Exception {
        // Initialize the database
        vthmeldingRepository.saveAndFlush(vthmelding);

        // Get the vthmelding
        restVthmeldingMockMvc
            .perform(get(ENTITY_API_URL_ID, vthmelding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vthmelding.getId().intValue()))
            .andExpect(jsonPath("$.activiteit").value(DEFAULT_ACTIVITEIT))
            .andExpect(jsonPath("$.beoordeling").value(DEFAULT_BEOORDELING))
            .andExpect(jsonPath("$.datumseponering").value(DEFAULT_DATUMSEPONERING.toString()))
            .andExpect(jsonPath("$.datumtijdtot").value(DEFAULT_DATUMTIJDTOT))
            .andExpect(jsonPath("$.geseponeerd").value(DEFAULT_GESEPONEERD))
            .andExpect(jsonPath("$.locatie").value(DEFAULT_LOCATIE))
            .andExpect(jsonPath("$.organisatieonderdeel").value(DEFAULT_ORGANISATIEONDERDEEL))
            .andExpect(jsonPath("$.overtredingscode").value(DEFAULT_OVERTREDINGSCODE))
            .andExpect(jsonPath("$.overtredingsgroep").value(DEFAULT_OVERTREDINGSGROEP))
            .andExpect(jsonPath("$.referentienummer").value(DEFAULT_REFERENTIENUMMER))
            .andExpect(jsonPath("$.resultaat").value(DEFAULT_RESULTAAT))
            .andExpect(jsonPath("$.soortvthmelding").value(DEFAULT_SOORTVTHMELDING))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.straatnaam").value(DEFAULT_STRAATNAAM))
            .andExpect(jsonPath("$.taaktype").value(DEFAULT_TAAKTYPE))
            .andExpect(jsonPath("$.zaaknummer").value(DEFAULT_ZAAKNUMMER));
    }

    @Test
    @Transactional
    void getNonExistingVthmelding() throws Exception {
        // Get the vthmelding
        restVthmeldingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVthmelding() throws Exception {
        // Initialize the database
        vthmeldingRepository.saveAndFlush(vthmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vthmelding
        Vthmelding updatedVthmelding = vthmeldingRepository.findById(vthmelding.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVthmelding are not directly saved in db
        em.detach(updatedVthmelding);
        updatedVthmelding
            .activiteit(UPDATED_ACTIVITEIT)
            .beoordeling(UPDATED_BEOORDELING)
            .datumseponering(UPDATED_DATUMSEPONERING)
            .datumtijdtot(UPDATED_DATUMTIJDTOT)
            .geseponeerd(UPDATED_GESEPONEERD)
            .locatie(UPDATED_LOCATIE)
            .organisatieonderdeel(UPDATED_ORGANISATIEONDERDEEL)
            .overtredingscode(UPDATED_OVERTREDINGSCODE)
            .overtredingsgroep(UPDATED_OVERTREDINGSGROEP)
            .referentienummer(UPDATED_REFERENTIENUMMER)
            .resultaat(UPDATED_RESULTAAT)
            .soortvthmelding(UPDATED_SOORTVTHMELDING)
            .status(UPDATED_STATUS)
            .straatnaam(UPDATED_STRAATNAAM)
            .taaktype(UPDATED_TAAKTYPE)
            .zaaknummer(UPDATED_ZAAKNUMMER);

        restVthmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVthmelding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVthmelding))
            )
            .andExpect(status().isOk());

        // Validate the Vthmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVthmeldingToMatchAllProperties(updatedVthmelding);
    }

    @Test
    @Transactional
    void putNonExistingVthmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vthmelding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVthmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vthmelding.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vthmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vthmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVthmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vthmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVthmeldingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vthmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vthmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVthmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vthmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVthmeldingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vthmelding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vthmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVthmeldingWithPatch() throws Exception {
        // Initialize the database
        vthmeldingRepository.saveAndFlush(vthmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vthmelding using partial update
        Vthmelding partialUpdatedVthmelding = new Vthmelding();
        partialUpdatedVthmelding.setId(vthmelding.getId());

        partialUpdatedVthmelding
            .beoordeling(UPDATED_BEOORDELING)
            .datumtijdtot(UPDATED_DATUMTIJDTOT)
            .geseponeerd(UPDATED_GESEPONEERD)
            .organisatieonderdeel(UPDATED_ORGANISATIEONDERDEEL)
            .resultaat(UPDATED_RESULTAAT)
            .soortvthmelding(UPDATED_SOORTVTHMELDING)
            .status(UPDATED_STATUS)
            .straatnaam(UPDATED_STRAATNAAM)
            .zaaknummer(UPDATED_ZAAKNUMMER);

        restVthmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVthmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVthmelding))
            )
            .andExpect(status().isOk());

        // Validate the Vthmelding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVthmeldingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVthmelding, vthmelding),
            getPersistedVthmelding(vthmelding)
        );
    }

    @Test
    @Transactional
    void fullUpdateVthmeldingWithPatch() throws Exception {
        // Initialize the database
        vthmeldingRepository.saveAndFlush(vthmelding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vthmelding using partial update
        Vthmelding partialUpdatedVthmelding = new Vthmelding();
        partialUpdatedVthmelding.setId(vthmelding.getId());

        partialUpdatedVthmelding
            .activiteit(UPDATED_ACTIVITEIT)
            .beoordeling(UPDATED_BEOORDELING)
            .datumseponering(UPDATED_DATUMSEPONERING)
            .datumtijdtot(UPDATED_DATUMTIJDTOT)
            .geseponeerd(UPDATED_GESEPONEERD)
            .locatie(UPDATED_LOCATIE)
            .organisatieonderdeel(UPDATED_ORGANISATIEONDERDEEL)
            .overtredingscode(UPDATED_OVERTREDINGSCODE)
            .overtredingsgroep(UPDATED_OVERTREDINGSGROEP)
            .referentienummer(UPDATED_REFERENTIENUMMER)
            .resultaat(UPDATED_RESULTAAT)
            .soortvthmelding(UPDATED_SOORTVTHMELDING)
            .status(UPDATED_STATUS)
            .straatnaam(UPDATED_STRAATNAAM)
            .taaktype(UPDATED_TAAKTYPE)
            .zaaknummer(UPDATED_ZAAKNUMMER);

        restVthmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVthmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVthmelding))
            )
            .andExpect(status().isOk());

        // Validate the Vthmelding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVthmeldingUpdatableFieldsEquals(partialUpdatedVthmelding, getPersistedVthmelding(partialUpdatedVthmelding));
    }

    @Test
    @Transactional
    void patchNonExistingVthmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vthmelding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVthmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vthmelding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vthmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vthmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVthmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vthmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVthmeldingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vthmelding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vthmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVthmelding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vthmelding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVthmeldingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vthmelding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vthmelding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVthmelding() throws Exception {
        // Initialize the database
        vthmeldingRepository.saveAndFlush(vthmelding);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vthmelding
        restVthmeldingMockMvc
            .perform(delete(ENTITY_API_URL_ID, vthmelding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vthmeldingRepository.count();
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

    protected Vthmelding getPersistedVthmelding(Vthmelding vthmelding) {
        return vthmeldingRepository.findById(vthmelding.getId()).orElseThrow();
    }

    protected void assertPersistedVthmeldingToMatchAllProperties(Vthmelding expectedVthmelding) {
        assertVthmeldingAllPropertiesEquals(expectedVthmelding, getPersistedVthmelding(expectedVthmelding));
    }

    protected void assertPersistedVthmeldingToMatchUpdatableProperties(Vthmelding expectedVthmelding) {
        assertVthmeldingAllUpdatablePropertiesEquals(expectedVthmelding, getPersistedVthmelding(expectedVthmelding));
    }
}
