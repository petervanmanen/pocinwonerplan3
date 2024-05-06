package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OrganisatorischeeenheidAsserts.*;
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
import nl.ritense.demo.domain.Organisatorischeeenheid;
import nl.ritense.demo.repository.OrganisatorischeeenheidRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OrganisatorischeeenheidResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrganisatorischeeenheidResourceIT {

    private static final String DEFAULT_DATUMONTSTAAN = "AAAAAAAAAA";
    private static final String UPDATED_DATUMONTSTAAN = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMOPHEFFING = "AAAAAAAAAA";
    private static final String UPDATED_DATUMOPHEFFING = "BBBBBBBBBB";

    private static final String DEFAULT_EMAILADRES = "AAAAAAAAAA";
    private static final String UPDATED_EMAILADRES = "BBBBBBBBBB";

    private static final String DEFAULT_FAXNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_FAXNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_FORMATIE = "AAAAAAAAAA";
    private static final String UPDATED_FORMATIE = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_NAAMVERKORT = "AAAAAAAAAA";
    private static final String UPDATED_NAAMVERKORT = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANISATIEIDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_ORGANISATIEIDENTIFICATIE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFOONNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_TELEFOONNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_TOELICHTING = "AAAAAAAAAA";
    private static final String UPDATED_TOELICHTING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/organisatorischeeenheids";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OrganisatorischeeenheidRepository organisatorischeeenheidRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrganisatorischeeenheidMockMvc;

    private Organisatorischeeenheid organisatorischeeenheid;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organisatorischeeenheid createEntity(EntityManager em) {
        Organisatorischeeenheid organisatorischeeenheid = new Organisatorischeeenheid()
            .datumontstaan(DEFAULT_DATUMONTSTAAN)
            .datumopheffing(DEFAULT_DATUMOPHEFFING)
            .emailadres(DEFAULT_EMAILADRES)
            .faxnummer(DEFAULT_FAXNUMMER)
            .formatie(DEFAULT_FORMATIE)
            .naam(DEFAULT_NAAM)
            .naamverkort(DEFAULT_NAAMVERKORT)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .organisatieidentificatie(DEFAULT_ORGANISATIEIDENTIFICATIE)
            .telefoonnummer(DEFAULT_TELEFOONNUMMER)
            .toelichting(DEFAULT_TOELICHTING);
        return organisatorischeeenheid;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organisatorischeeenheid createUpdatedEntity(EntityManager em) {
        Organisatorischeeenheid organisatorischeeenheid = new Organisatorischeeenheid()
            .datumontstaan(UPDATED_DATUMONTSTAAN)
            .datumopheffing(UPDATED_DATUMOPHEFFING)
            .emailadres(UPDATED_EMAILADRES)
            .faxnummer(UPDATED_FAXNUMMER)
            .formatie(UPDATED_FORMATIE)
            .naam(UPDATED_NAAM)
            .naamverkort(UPDATED_NAAMVERKORT)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .organisatieidentificatie(UPDATED_ORGANISATIEIDENTIFICATIE)
            .telefoonnummer(UPDATED_TELEFOONNUMMER)
            .toelichting(UPDATED_TOELICHTING);
        return organisatorischeeenheid;
    }

    @BeforeEach
    public void initTest() {
        organisatorischeeenheid = createEntity(em);
    }

    @Test
    @Transactional
    void createOrganisatorischeeenheid() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Organisatorischeeenheid
        var returnedOrganisatorischeeenheid = om.readValue(
            restOrganisatorischeeenheidMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(organisatorischeeenheid))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Organisatorischeeenheid.class
        );

        // Validate the Organisatorischeeenheid in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOrganisatorischeeenheidUpdatableFieldsEquals(
            returnedOrganisatorischeeenheid,
            getPersistedOrganisatorischeeenheid(returnedOrganisatorischeeenheid)
        );
    }

    @Test
    @Transactional
    void createOrganisatorischeeenheidWithExistingId() throws Exception {
        // Create the Organisatorischeeenheid with an existing ID
        organisatorischeeenheid.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganisatorischeeenheidMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(organisatorischeeenheid)))
            .andExpect(status().isBadRequest());

        // Validate the Organisatorischeeenheid in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrganisatorischeeenheids() throws Exception {
        // Initialize the database
        organisatorischeeenheidRepository.saveAndFlush(organisatorischeeenheid);

        // Get all the organisatorischeeenheidList
        restOrganisatorischeeenheidMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organisatorischeeenheid.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumontstaan").value(hasItem(DEFAULT_DATUMONTSTAAN)))
            .andExpect(jsonPath("$.[*].datumopheffing").value(hasItem(DEFAULT_DATUMOPHEFFING)))
            .andExpect(jsonPath("$.[*].emailadres").value(hasItem(DEFAULT_EMAILADRES)))
            .andExpect(jsonPath("$.[*].faxnummer").value(hasItem(DEFAULT_FAXNUMMER)))
            .andExpect(jsonPath("$.[*].formatie").value(hasItem(DEFAULT_FORMATIE)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].naamverkort").value(hasItem(DEFAULT_NAAMVERKORT)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].organisatieidentificatie").value(hasItem(DEFAULT_ORGANISATIEIDENTIFICATIE)))
            .andExpect(jsonPath("$.[*].telefoonnummer").value(hasItem(DEFAULT_TELEFOONNUMMER)))
            .andExpect(jsonPath("$.[*].toelichting").value(hasItem(DEFAULT_TOELICHTING)));
    }

    @Test
    @Transactional
    void getOrganisatorischeeenheid() throws Exception {
        // Initialize the database
        organisatorischeeenheidRepository.saveAndFlush(organisatorischeeenheid);

        // Get the organisatorischeeenheid
        restOrganisatorischeeenheidMockMvc
            .perform(get(ENTITY_API_URL_ID, organisatorischeeenheid.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organisatorischeeenheid.getId().intValue()))
            .andExpect(jsonPath("$.datumontstaan").value(DEFAULT_DATUMONTSTAAN))
            .andExpect(jsonPath("$.datumopheffing").value(DEFAULT_DATUMOPHEFFING))
            .andExpect(jsonPath("$.emailadres").value(DEFAULT_EMAILADRES))
            .andExpect(jsonPath("$.faxnummer").value(DEFAULT_FAXNUMMER))
            .andExpect(jsonPath("$.formatie").value(DEFAULT_FORMATIE))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.naamverkort").value(DEFAULT_NAAMVERKORT))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.organisatieidentificatie").value(DEFAULT_ORGANISATIEIDENTIFICATIE))
            .andExpect(jsonPath("$.telefoonnummer").value(DEFAULT_TELEFOONNUMMER))
            .andExpect(jsonPath("$.toelichting").value(DEFAULT_TOELICHTING));
    }

    @Test
    @Transactional
    void getNonExistingOrganisatorischeeenheid() throws Exception {
        // Get the organisatorischeeenheid
        restOrganisatorischeeenheidMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrganisatorischeeenheid() throws Exception {
        // Initialize the database
        organisatorischeeenheidRepository.saveAndFlush(organisatorischeeenheid);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the organisatorischeeenheid
        Organisatorischeeenheid updatedOrganisatorischeeenheid = organisatorischeeenheidRepository
            .findById(organisatorischeeenheid.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedOrganisatorischeeenheid are not directly saved in db
        em.detach(updatedOrganisatorischeeenheid);
        updatedOrganisatorischeeenheid
            .datumontstaan(UPDATED_DATUMONTSTAAN)
            .datumopheffing(UPDATED_DATUMOPHEFFING)
            .emailadres(UPDATED_EMAILADRES)
            .faxnummer(UPDATED_FAXNUMMER)
            .formatie(UPDATED_FORMATIE)
            .naam(UPDATED_NAAM)
            .naamverkort(UPDATED_NAAMVERKORT)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .organisatieidentificatie(UPDATED_ORGANISATIEIDENTIFICATIE)
            .telefoonnummer(UPDATED_TELEFOONNUMMER)
            .toelichting(UPDATED_TOELICHTING);

        restOrganisatorischeeenheidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOrganisatorischeeenheid.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOrganisatorischeeenheid))
            )
            .andExpect(status().isOk());

        // Validate the Organisatorischeeenheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOrganisatorischeeenheidToMatchAllProperties(updatedOrganisatorischeeenheid);
    }

    @Test
    @Transactional
    void putNonExistingOrganisatorischeeenheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organisatorischeeenheid.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganisatorischeeenheidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organisatorischeeenheid.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organisatorischeeenheid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organisatorischeeenheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrganisatorischeeenheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organisatorischeeenheid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganisatorischeeenheidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organisatorischeeenheid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organisatorischeeenheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrganisatorischeeenheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organisatorischeeenheid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganisatorischeeenheidMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(organisatorischeeenheid)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Organisatorischeeenheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrganisatorischeeenheidWithPatch() throws Exception {
        // Initialize the database
        organisatorischeeenheidRepository.saveAndFlush(organisatorischeeenheid);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the organisatorischeeenheid using partial update
        Organisatorischeeenheid partialUpdatedOrganisatorischeeenheid = new Organisatorischeeenheid();
        partialUpdatedOrganisatorischeeenheid.setId(organisatorischeeenheid.getId());

        partialUpdatedOrganisatorischeeenheid
            .datumontstaan(UPDATED_DATUMONTSTAAN)
            .faxnummer(UPDATED_FAXNUMMER)
            .formatie(UPDATED_FORMATIE)
            .naamverkort(UPDATED_NAAMVERKORT)
            .organisatieidentificatie(UPDATED_ORGANISATIEIDENTIFICATIE)
            .telefoonnummer(UPDATED_TELEFOONNUMMER)
            .toelichting(UPDATED_TOELICHTING);

        restOrganisatorischeeenheidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganisatorischeeenheid.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrganisatorischeeenheid))
            )
            .andExpect(status().isOk());

        // Validate the Organisatorischeeenheid in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrganisatorischeeenheidUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOrganisatorischeeenheid, organisatorischeeenheid),
            getPersistedOrganisatorischeeenheid(organisatorischeeenheid)
        );
    }

    @Test
    @Transactional
    void fullUpdateOrganisatorischeeenheidWithPatch() throws Exception {
        // Initialize the database
        organisatorischeeenheidRepository.saveAndFlush(organisatorischeeenheid);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the organisatorischeeenheid using partial update
        Organisatorischeeenheid partialUpdatedOrganisatorischeeenheid = new Organisatorischeeenheid();
        partialUpdatedOrganisatorischeeenheid.setId(organisatorischeeenheid.getId());

        partialUpdatedOrganisatorischeeenheid
            .datumontstaan(UPDATED_DATUMONTSTAAN)
            .datumopheffing(UPDATED_DATUMOPHEFFING)
            .emailadres(UPDATED_EMAILADRES)
            .faxnummer(UPDATED_FAXNUMMER)
            .formatie(UPDATED_FORMATIE)
            .naam(UPDATED_NAAM)
            .naamverkort(UPDATED_NAAMVERKORT)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .organisatieidentificatie(UPDATED_ORGANISATIEIDENTIFICATIE)
            .telefoonnummer(UPDATED_TELEFOONNUMMER)
            .toelichting(UPDATED_TOELICHTING);

        restOrganisatorischeeenheidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganisatorischeeenheid.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrganisatorischeeenheid))
            )
            .andExpect(status().isOk());

        // Validate the Organisatorischeeenheid in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrganisatorischeeenheidUpdatableFieldsEquals(
            partialUpdatedOrganisatorischeeenheid,
            getPersistedOrganisatorischeeenheid(partialUpdatedOrganisatorischeeenheid)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOrganisatorischeeenheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organisatorischeeenheid.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganisatorischeeenheidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, organisatorischeeenheid.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(organisatorischeeenheid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organisatorischeeenheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrganisatorischeeenheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organisatorischeeenheid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganisatorischeeenheidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(organisatorischeeenheid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organisatorischeeenheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrganisatorischeeenheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organisatorischeeenheid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganisatorischeeenheidMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(organisatorischeeenheid))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Organisatorischeeenheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrganisatorischeeenheid() throws Exception {
        // Initialize the database
        organisatorischeeenheidRepository.saveAndFlush(organisatorischeeenheid);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the organisatorischeeenheid
        restOrganisatorischeeenheidMockMvc
            .perform(delete(ENTITY_API_URL_ID, organisatorischeeenheid.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return organisatorischeeenheidRepository.count();
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

    protected Organisatorischeeenheid getPersistedOrganisatorischeeenheid(Organisatorischeeenheid organisatorischeeenheid) {
        return organisatorischeeenheidRepository.findById(organisatorischeeenheid.getId()).orElseThrow();
    }

    protected void assertPersistedOrganisatorischeeenheidToMatchAllProperties(Organisatorischeeenheid expectedOrganisatorischeeenheid) {
        assertOrganisatorischeeenheidAllPropertiesEquals(
            expectedOrganisatorischeeenheid,
            getPersistedOrganisatorischeeenheid(expectedOrganisatorischeeenheid)
        );
    }

    protected void assertPersistedOrganisatorischeeenheidToMatchUpdatableProperties(
        Organisatorischeeenheid expectedOrganisatorischeeenheid
    ) {
        assertOrganisatorischeeenheidAllUpdatablePropertiesEquals(
            expectedOrganisatorischeeenheid,
            getPersistedOrganisatorischeeenheid(expectedOrganisatorischeeenheid)
        );
    }
}
