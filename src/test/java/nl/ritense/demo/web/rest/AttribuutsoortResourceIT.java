package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AttribuutsoortAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Attribuutsoort;
import nl.ritense.demo.repository.AttribuutsoortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AttribuutsoortResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AttribuutsoortResourceIT {

    private static final Boolean DEFAULT_AUTHENTIEK = false;
    private static final Boolean UPDATED_AUTHENTIEK = true;

    private static final LocalDate DEFAULT_DATUMOPNAME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMOPNAME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DEFINITIE = "AAAAAAAAAA";
    private static final String UPDATED_DEFINITIE = "BBBBBBBBBB";

    private static final String DEFAULT_DOMEIN = "AAAAAAAAAA";
    private static final String UPDATED_DOMEIN = "BBBBBBBBBB";

    private static final String DEFAULT_EAGUID = "AAAAAAAAAA";
    private static final String UPDATED_EAGUID = "BBBBBBBBBB";

    private static final String DEFAULT_HERKOMST = "AAAAAAAAAA";
    private static final String UPDATED_HERKOMST = "BBBBBBBBBB";

    private static final String DEFAULT_HERKOMSTDEFINITIE = "AAAAAAAAAA";
    private static final String UPDATED_HERKOMSTDEFINITIE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IDENTIFICEREND = false;
    private static final Boolean UPDATED_IDENTIFICEREND = true;

    private static final Boolean DEFAULT_INDICATIEAFLEIDBAAR = false;
    private static final Boolean UPDATED_INDICATIEAFLEIDBAAR = true;

    private static final Boolean DEFAULT_INDICATIEMATERIELEHISTORIE = false;
    private static final Boolean UPDATED_INDICATIEMATERIELEHISTORIE = true;

    private static final String DEFAULT_KARDINALITEIT = "AAAAAAAAAA";
    private static final String UPDATED_KARDINALITEIT = "BBBBBBBBBB";

    private static final String DEFAULT_LENGTE = "AAAAAAAAAA";
    private static final String UPDATED_LENGTE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MOGELIJKGEENWAARDE = false;
    private static final Boolean UPDATED_MOGELIJKGEENWAARDE = true;

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_PATROON = "AAAAAAAAAA";
    private static final String UPDATED_PATROON = "BBBBBBBBBB";

    private static final String DEFAULT_PRECISIE = "AAAAAAAAAA";
    private static final String UPDATED_PRECISIE = "BBBBBBBBBB";

    private static final String DEFAULT_STEREOTYPE = "AAAAAAAAAA";
    private static final String UPDATED_STEREOTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TOELICHTING = "AAAAAAAAAA";
    private static final String UPDATED_TOELICHTING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/attribuutsoorts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AttribuutsoortRepository attribuutsoortRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttribuutsoortMockMvc;

    private Attribuutsoort attribuutsoort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attribuutsoort createEntity(EntityManager em) {
        Attribuutsoort attribuutsoort = new Attribuutsoort()
            .authentiek(DEFAULT_AUTHENTIEK)
            .datumopname(DEFAULT_DATUMOPNAME)
            .definitie(DEFAULT_DEFINITIE)
            .domein(DEFAULT_DOMEIN)
            .eaguid(DEFAULT_EAGUID)
            .herkomst(DEFAULT_HERKOMST)
            .herkomstdefinitie(DEFAULT_HERKOMSTDEFINITIE)
            .identificerend(DEFAULT_IDENTIFICEREND)
            .indicatieafleidbaar(DEFAULT_INDICATIEAFLEIDBAAR)
            .indicatiematerielehistorie(DEFAULT_INDICATIEMATERIELEHISTORIE)
            .kardinaliteit(DEFAULT_KARDINALITEIT)
            .lengte(DEFAULT_LENGTE)
            .mogelijkgeenwaarde(DEFAULT_MOGELIJKGEENWAARDE)
            .naam(DEFAULT_NAAM)
            .patroon(DEFAULT_PATROON)
            .precisie(DEFAULT_PRECISIE)
            .stereotype(DEFAULT_STEREOTYPE)
            .toelichting(DEFAULT_TOELICHTING);
        return attribuutsoort;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attribuutsoort createUpdatedEntity(EntityManager em) {
        Attribuutsoort attribuutsoort = new Attribuutsoort()
            .authentiek(UPDATED_AUTHENTIEK)
            .datumopname(UPDATED_DATUMOPNAME)
            .definitie(UPDATED_DEFINITIE)
            .domein(UPDATED_DOMEIN)
            .eaguid(UPDATED_EAGUID)
            .herkomst(UPDATED_HERKOMST)
            .herkomstdefinitie(UPDATED_HERKOMSTDEFINITIE)
            .identificerend(UPDATED_IDENTIFICEREND)
            .indicatieafleidbaar(UPDATED_INDICATIEAFLEIDBAAR)
            .indicatiematerielehistorie(UPDATED_INDICATIEMATERIELEHISTORIE)
            .kardinaliteit(UPDATED_KARDINALITEIT)
            .lengte(UPDATED_LENGTE)
            .mogelijkgeenwaarde(UPDATED_MOGELIJKGEENWAARDE)
            .naam(UPDATED_NAAM)
            .patroon(UPDATED_PATROON)
            .precisie(UPDATED_PRECISIE)
            .stereotype(UPDATED_STEREOTYPE)
            .toelichting(UPDATED_TOELICHTING);
        return attribuutsoort;
    }

    @BeforeEach
    public void initTest() {
        attribuutsoort = createEntity(em);
    }

    @Test
    @Transactional
    void createAttribuutsoort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Attribuutsoort
        var returnedAttribuutsoort = om.readValue(
            restAttribuutsoortMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(attribuutsoort)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Attribuutsoort.class
        );

        // Validate the Attribuutsoort in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAttribuutsoortUpdatableFieldsEquals(returnedAttribuutsoort, getPersistedAttribuutsoort(returnedAttribuutsoort));
    }

    @Test
    @Transactional
    void createAttribuutsoortWithExistingId() throws Exception {
        // Create the Attribuutsoort with an existing ID
        attribuutsoort.setId("existing_id");

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttribuutsoortMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(attribuutsoort)))
            .andExpect(status().isBadRequest());

        // Validate the Attribuutsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAttribuutsoorts() throws Exception {
        // Initialize the database
        attribuutsoortRepository.saveAndFlush(attribuutsoort);

        // Get all the attribuutsoortList
        restAttribuutsoortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attribuutsoort.getId())))
            .andExpect(jsonPath("$.[*].authentiek").value(hasItem(DEFAULT_AUTHENTIEK.booleanValue())))
            .andExpect(jsonPath("$.[*].datumopname").value(hasItem(DEFAULT_DATUMOPNAME.toString())))
            .andExpect(jsonPath("$.[*].definitie").value(hasItem(DEFAULT_DEFINITIE)))
            .andExpect(jsonPath("$.[*].domein").value(hasItem(DEFAULT_DOMEIN)))
            .andExpect(jsonPath("$.[*].eaguid").value(hasItem(DEFAULT_EAGUID)))
            .andExpect(jsonPath("$.[*].herkomst").value(hasItem(DEFAULT_HERKOMST)))
            .andExpect(jsonPath("$.[*].herkomstdefinitie").value(hasItem(DEFAULT_HERKOMSTDEFINITIE)))
            .andExpect(jsonPath("$.[*].identificerend").value(hasItem(DEFAULT_IDENTIFICEREND.booleanValue())))
            .andExpect(jsonPath("$.[*].indicatieafleidbaar").value(hasItem(DEFAULT_INDICATIEAFLEIDBAAR.booleanValue())))
            .andExpect(jsonPath("$.[*].indicatiematerielehistorie").value(hasItem(DEFAULT_INDICATIEMATERIELEHISTORIE.booleanValue())))
            .andExpect(jsonPath("$.[*].kardinaliteit").value(hasItem(DEFAULT_KARDINALITEIT)))
            .andExpect(jsonPath("$.[*].lengte").value(hasItem(DEFAULT_LENGTE)))
            .andExpect(jsonPath("$.[*].mogelijkgeenwaarde").value(hasItem(DEFAULT_MOGELIJKGEENWAARDE.booleanValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].patroon").value(hasItem(DEFAULT_PATROON)))
            .andExpect(jsonPath("$.[*].precisie").value(hasItem(DEFAULT_PRECISIE)))
            .andExpect(jsonPath("$.[*].stereotype").value(hasItem(DEFAULT_STEREOTYPE)))
            .andExpect(jsonPath("$.[*].toelichting").value(hasItem(DEFAULT_TOELICHTING)));
    }

    @Test
    @Transactional
    void getAttribuutsoort() throws Exception {
        // Initialize the database
        attribuutsoortRepository.saveAndFlush(attribuutsoort);

        // Get the attribuutsoort
        restAttribuutsoortMockMvc
            .perform(get(ENTITY_API_URL_ID, attribuutsoort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attribuutsoort.getId()))
            .andExpect(jsonPath("$.authentiek").value(DEFAULT_AUTHENTIEK.booleanValue()))
            .andExpect(jsonPath("$.datumopname").value(DEFAULT_DATUMOPNAME.toString()))
            .andExpect(jsonPath("$.definitie").value(DEFAULT_DEFINITIE))
            .andExpect(jsonPath("$.domein").value(DEFAULT_DOMEIN))
            .andExpect(jsonPath("$.eaguid").value(DEFAULT_EAGUID))
            .andExpect(jsonPath("$.herkomst").value(DEFAULT_HERKOMST))
            .andExpect(jsonPath("$.herkomstdefinitie").value(DEFAULT_HERKOMSTDEFINITIE))
            .andExpect(jsonPath("$.identificerend").value(DEFAULT_IDENTIFICEREND.booleanValue()))
            .andExpect(jsonPath("$.indicatieafleidbaar").value(DEFAULT_INDICATIEAFLEIDBAAR.booleanValue()))
            .andExpect(jsonPath("$.indicatiematerielehistorie").value(DEFAULT_INDICATIEMATERIELEHISTORIE.booleanValue()))
            .andExpect(jsonPath("$.kardinaliteit").value(DEFAULT_KARDINALITEIT))
            .andExpect(jsonPath("$.lengte").value(DEFAULT_LENGTE))
            .andExpect(jsonPath("$.mogelijkgeenwaarde").value(DEFAULT_MOGELIJKGEENWAARDE.booleanValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.patroon").value(DEFAULT_PATROON))
            .andExpect(jsonPath("$.precisie").value(DEFAULT_PRECISIE))
            .andExpect(jsonPath("$.stereotype").value(DEFAULT_STEREOTYPE))
            .andExpect(jsonPath("$.toelichting").value(DEFAULT_TOELICHTING));
    }

    @Test
    @Transactional
    void getNonExistingAttribuutsoort() throws Exception {
        // Get the attribuutsoort
        restAttribuutsoortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAttribuutsoort() throws Exception {
        // Initialize the database
        attribuutsoortRepository.saveAndFlush(attribuutsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the attribuutsoort
        Attribuutsoort updatedAttribuutsoort = attribuutsoortRepository.findById(attribuutsoort.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAttribuutsoort are not directly saved in db
        em.detach(updatedAttribuutsoort);
        updatedAttribuutsoort
            .authentiek(UPDATED_AUTHENTIEK)
            .datumopname(UPDATED_DATUMOPNAME)
            .definitie(UPDATED_DEFINITIE)
            .domein(UPDATED_DOMEIN)
            .eaguid(UPDATED_EAGUID)
            .herkomst(UPDATED_HERKOMST)
            .herkomstdefinitie(UPDATED_HERKOMSTDEFINITIE)
            .identificerend(UPDATED_IDENTIFICEREND)
            .indicatieafleidbaar(UPDATED_INDICATIEAFLEIDBAAR)
            .indicatiematerielehistorie(UPDATED_INDICATIEMATERIELEHISTORIE)
            .kardinaliteit(UPDATED_KARDINALITEIT)
            .lengte(UPDATED_LENGTE)
            .mogelijkgeenwaarde(UPDATED_MOGELIJKGEENWAARDE)
            .naam(UPDATED_NAAM)
            .patroon(UPDATED_PATROON)
            .precisie(UPDATED_PRECISIE)
            .stereotype(UPDATED_STEREOTYPE)
            .toelichting(UPDATED_TOELICHTING);

        restAttribuutsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAttribuutsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAttribuutsoort))
            )
            .andExpect(status().isOk());

        // Validate the Attribuutsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAttribuutsoortToMatchAllProperties(updatedAttribuutsoort);
    }

    @Test
    @Transactional
    void putNonExistingAttribuutsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        attribuutsoort.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttribuutsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attribuutsoort.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(attribuutsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attribuutsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAttribuutsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        attribuutsoort.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttribuutsoortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(attribuutsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attribuutsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAttribuutsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        attribuutsoort.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttribuutsoortMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(attribuutsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Attribuutsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAttribuutsoortWithPatch() throws Exception {
        // Initialize the database
        attribuutsoortRepository.saveAndFlush(attribuutsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the attribuutsoort using partial update
        Attribuutsoort partialUpdatedAttribuutsoort = new Attribuutsoort();
        partialUpdatedAttribuutsoort.setId(attribuutsoort.getId());

        partialUpdatedAttribuutsoort
            .authentiek(UPDATED_AUTHENTIEK)
            .definitie(UPDATED_DEFINITIE)
            .identificerend(UPDATED_IDENTIFICEREND)
            .kardinaliteit(UPDATED_KARDINALITEIT)
            .lengte(UPDATED_LENGTE)
            .mogelijkgeenwaarde(UPDATED_MOGELIJKGEENWAARDE)
            .patroon(UPDATED_PATROON)
            .precisie(UPDATED_PRECISIE)
            .stereotype(UPDATED_STEREOTYPE);

        restAttribuutsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttribuutsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAttribuutsoort))
            )
            .andExpect(status().isOk());

        // Validate the Attribuutsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAttribuutsoortUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAttribuutsoort, attribuutsoort),
            getPersistedAttribuutsoort(attribuutsoort)
        );
    }

    @Test
    @Transactional
    void fullUpdateAttribuutsoortWithPatch() throws Exception {
        // Initialize the database
        attribuutsoortRepository.saveAndFlush(attribuutsoort);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the attribuutsoort using partial update
        Attribuutsoort partialUpdatedAttribuutsoort = new Attribuutsoort();
        partialUpdatedAttribuutsoort.setId(attribuutsoort.getId());

        partialUpdatedAttribuutsoort
            .authentiek(UPDATED_AUTHENTIEK)
            .datumopname(UPDATED_DATUMOPNAME)
            .definitie(UPDATED_DEFINITIE)
            .domein(UPDATED_DOMEIN)
            .eaguid(UPDATED_EAGUID)
            .herkomst(UPDATED_HERKOMST)
            .herkomstdefinitie(UPDATED_HERKOMSTDEFINITIE)
            .identificerend(UPDATED_IDENTIFICEREND)
            .indicatieafleidbaar(UPDATED_INDICATIEAFLEIDBAAR)
            .indicatiematerielehistorie(UPDATED_INDICATIEMATERIELEHISTORIE)
            .kardinaliteit(UPDATED_KARDINALITEIT)
            .lengte(UPDATED_LENGTE)
            .mogelijkgeenwaarde(UPDATED_MOGELIJKGEENWAARDE)
            .naam(UPDATED_NAAM)
            .patroon(UPDATED_PATROON)
            .precisie(UPDATED_PRECISIE)
            .stereotype(UPDATED_STEREOTYPE)
            .toelichting(UPDATED_TOELICHTING);

        restAttribuutsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttribuutsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAttribuutsoort))
            )
            .andExpect(status().isOk());

        // Validate the Attribuutsoort in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAttribuutsoortUpdatableFieldsEquals(partialUpdatedAttribuutsoort, getPersistedAttribuutsoort(partialUpdatedAttribuutsoort));
    }

    @Test
    @Transactional
    void patchNonExistingAttribuutsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        attribuutsoort.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttribuutsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, attribuutsoort.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(attribuutsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attribuutsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAttribuutsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        attribuutsoort.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttribuutsoortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(attribuutsoort))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attribuutsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAttribuutsoort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        attribuutsoort.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttribuutsoortMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(attribuutsoort)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Attribuutsoort in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAttribuutsoort() throws Exception {
        // Initialize the database
        attribuutsoortRepository.saveAndFlush(attribuutsoort);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the attribuutsoort
        restAttribuutsoortMockMvc
            .perform(delete(ENTITY_API_URL_ID, attribuutsoort.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return attribuutsoortRepository.count();
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

    protected Attribuutsoort getPersistedAttribuutsoort(Attribuutsoort attribuutsoort) {
        return attribuutsoortRepository.findById(attribuutsoort.getId()).orElseThrow();
    }

    protected void assertPersistedAttribuutsoortToMatchAllProperties(Attribuutsoort expectedAttribuutsoort) {
        assertAttribuutsoortAllPropertiesEquals(expectedAttribuutsoort, getPersistedAttribuutsoort(expectedAttribuutsoort));
    }

    protected void assertPersistedAttribuutsoortToMatchUpdatableProperties(Attribuutsoort expectedAttribuutsoort) {
        assertAttribuutsoortAllUpdatablePropertiesEquals(expectedAttribuutsoort, getPersistedAttribuutsoort(expectedAttribuutsoort));
    }
}
