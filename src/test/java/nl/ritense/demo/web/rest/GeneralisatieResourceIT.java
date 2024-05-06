package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.GeneralisatieAsserts.*;
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
import nl.ritense.demo.domain.Generalisatie;
import nl.ritense.demo.repository.GeneralisatieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GeneralisatieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GeneralisatieResourceIT {

    private static final LocalDate DEFAULT_DATUMOPNAME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMOPNAME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DEFINITIE = "AAAAAAAAAA";
    private static final String UPDATED_DEFINITIE = "BBBBBBBBBB";

    private static final String DEFAULT_EAGUID = "AAAAAAAAAA";
    private static final String UPDATED_EAGUID = "BBBBBBBBBB";

    private static final String DEFAULT_HERKOMST = "AAAAAAAAAA";
    private static final String UPDATED_HERKOMST = "BBBBBBBBBB";

    private static final String DEFAULT_HERKOMSTDEFINITIE = "AAAAAAAAAA";
    private static final String UPDATED_HERKOMSTDEFINITIE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INDICATIEMATERIELEHISTORIE = false;
    private static final Boolean UPDATED_INDICATIEMATERIELEHISTORIE = true;

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_TOELICHTING = "AAAAAAAAAA";
    private static final String UPDATED_TOELICHTING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/generalisaties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GeneralisatieRepository generalisatieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGeneralisatieMockMvc;

    private Generalisatie generalisatie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Generalisatie createEntity(EntityManager em) {
        Generalisatie generalisatie = new Generalisatie()
            .datumopname(DEFAULT_DATUMOPNAME)
            .definitie(DEFAULT_DEFINITIE)
            .eaguid(DEFAULT_EAGUID)
            .herkomst(DEFAULT_HERKOMST)
            .herkomstdefinitie(DEFAULT_HERKOMSTDEFINITIE)
            .indicatiematerielehistorie(DEFAULT_INDICATIEMATERIELEHISTORIE)
            .naam(DEFAULT_NAAM)
            .toelichting(DEFAULT_TOELICHTING);
        return generalisatie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Generalisatie createUpdatedEntity(EntityManager em) {
        Generalisatie generalisatie = new Generalisatie()
            .datumopname(UPDATED_DATUMOPNAME)
            .definitie(UPDATED_DEFINITIE)
            .eaguid(UPDATED_EAGUID)
            .herkomst(UPDATED_HERKOMST)
            .herkomstdefinitie(UPDATED_HERKOMSTDEFINITIE)
            .indicatiematerielehistorie(UPDATED_INDICATIEMATERIELEHISTORIE)
            .naam(UPDATED_NAAM)
            .toelichting(UPDATED_TOELICHTING);
        return generalisatie;
    }

    @BeforeEach
    public void initTest() {
        generalisatie = createEntity(em);
    }

    @Test
    @Transactional
    void createGeneralisatie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Generalisatie
        var returnedGeneralisatie = om.readValue(
            restGeneralisatieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(generalisatie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Generalisatie.class
        );

        // Validate the Generalisatie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGeneralisatieUpdatableFieldsEquals(returnedGeneralisatie, getPersistedGeneralisatie(returnedGeneralisatie));
    }

    @Test
    @Transactional
    void createGeneralisatieWithExistingId() throws Exception {
        // Create the Generalisatie with an existing ID
        generalisatie.setId("existing_id");

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeneralisatieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(generalisatie)))
            .andExpect(status().isBadRequest());

        // Validate the Generalisatie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGeneralisaties() throws Exception {
        // Initialize the database
        generalisatieRepository.saveAndFlush(generalisatie);

        // Get all the generalisatieList
        restGeneralisatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(generalisatie.getId())))
            .andExpect(jsonPath("$.[*].datumopname").value(hasItem(DEFAULT_DATUMOPNAME.toString())))
            .andExpect(jsonPath("$.[*].definitie").value(hasItem(DEFAULT_DEFINITIE)))
            .andExpect(jsonPath("$.[*].eaguid").value(hasItem(DEFAULT_EAGUID)))
            .andExpect(jsonPath("$.[*].herkomst").value(hasItem(DEFAULT_HERKOMST)))
            .andExpect(jsonPath("$.[*].herkomstdefinitie").value(hasItem(DEFAULT_HERKOMSTDEFINITIE)))
            .andExpect(jsonPath("$.[*].indicatiematerielehistorie").value(hasItem(DEFAULT_INDICATIEMATERIELEHISTORIE.booleanValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].toelichting").value(hasItem(DEFAULT_TOELICHTING)));
    }

    @Test
    @Transactional
    void getGeneralisatie() throws Exception {
        // Initialize the database
        generalisatieRepository.saveAndFlush(generalisatie);

        // Get the generalisatie
        restGeneralisatieMockMvc
            .perform(get(ENTITY_API_URL_ID, generalisatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(generalisatie.getId()))
            .andExpect(jsonPath("$.datumopname").value(DEFAULT_DATUMOPNAME.toString()))
            .andExpect(jsonPath("$.definitie").value(DEFAULT_DEFINITIE))
            .andExpect(jsonPath("$.eaguid").value(DEFAULT_EAGUID))
            .andExpect(jsonPath("$.herkomst").value(DEFAULT_HERKOMST))
            .andExpect(jsonPath("$.herkomstdefinitie").value(DEFAULT_HERKOMSTDEFINITIE))
            .andExpect(jsonPath("$.indicatiematerielehistorie").value(DEFAULT_INDICATIEMATERIELEHISTORIE.booleanValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.toelichting").value(DEFAULT_TOELICHTING));
    }

    @Test
    @Transactional
    void getNonExistingGeneralisatie() throws Exception {
        // Get the generalisatie
        restGeneralisatieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGeneralisatie() throws Exception {
        // Initialize the database
        generalisatieRepository.saveAndFlush(generalisatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the generalisatie
        Generalisatie updatedGeneralisatie = generalisatieRepository.findById(generalisatie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGeneralisatie are not directly saved in db
        em.detach(updatedGeneralisatie);
        updatedGeneralisatie
            .datumopname(UPDATED_DATUMOPNAME)
            .definitie(UPDATED_DEFINITIE)
            .eaguid(UPDATED_EAGUID)
            .herkomst(UPDATED_HERKOMST)
            .herkomstdefinitie(UPDATED_HERKOMSTDEFINITIE)
            .indicatiematerielehistorie(UPDATED_INDICATIEMATERIELEHISTORIE)
            .naam(UPDATED_NAAM)
            .toelichting(UPDATED_TOELICHTING);

        restGeneralisatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGeneralisatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGeneralisatie))
            )
            .andExpect(status().isOk());

        // Validate the Generalisatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGeneralisatieToMatchAllProperties(updatedGeneralisatie);
    }

    @Test
    @Transactional
    void putNonExistingGeneralisatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        generalisatie.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeneralisatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, generalisatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(generalisatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Generalisatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGeneralisatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        generalisatie.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeneralisatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(generalisatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Generalisatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGeneralisatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        generalisatie.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeneralisatieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(generalisatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Generalisatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGeneralisatieWithPatch() throws Exception {
        // Initialize the database
        generalisatieRepository.saveAndFlush(generalisatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the generalisatie using partial update
        Generalisatie partialUpdatedGeneralisatie = new Generalisatie();
        partialUpdatedGeneralisatie.setId(generalisatie.getId());

        partialUpdatedGeneralisatie.definitie(UPDATED_DEFINITIE).naam(UPDATED_NAAM).toelichting(UPDATED_TOELICHTING);

        restGeneralisatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGeneralisatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGeneralisatie))
            )
            .andExpect(status().isOk());

        // Validate the Generalisatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGeneralisatieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGeneralisatie, generalisatie),
            getPersistedGeneralisatie(generalisatie)
        );
    }

    @Test
    @Transactional
    void fullUpdateGeneralisatieWithPatch() throws Exception {
        // Initialize the database
        generalisatieRepository.saveAndFlush(generalisatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the generalisatie using partial update
        Generalisatie partialUpdatedGeneralisatie = new Generalisatie();
        partialUpdatedGeneralisatie.setId(generalisatie.getId());

        partialUpdatedGeneralisatie
            .datumopname(UPDATED_DATUMOPNAME)
            .definitie(UPDATED_DEFINITIE)
            .eaguid(UPDATED_EAGUID)
            .herkomst(UPDATED_HERKOMST)
            .herkomstdefinitie(UPDATED_HERKOMSTDEFINITIE)
            .indicatiematerielehistorie(UPDATED_INDICATIEMATERIELEHISTORIE)
            .naam(UPDATED_NAAM)
            .toelichting(UPDATED_TOELICHTING);

        restGeneralisatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGeneralisatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGeneralisatie))
            )
            .andExpect(status().isOk());

        // Validate the Generalisatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGeneralisatieUpdatableFieldsEquals(partialUpdatedGeneralisatie, getPersistedGeneralisatie(partialUpdatedGeneralisatie));
    }

    @Test
    @Transactional
    void patchNonExistingGeneralisatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        generalisatie.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeneralisatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, generalisatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(generalisatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Generalisatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGeneralisatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        generalisatie.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeneralisatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(generalisatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Generalisatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGeneralisatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        generalisatie.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeneralisatieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(generalisatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Generalisatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGeneralisatie() throws Exception {
        // Initialize the database
        generalisatieRepository.saveAndFlush(generalisatie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the generalisatie
        restGeneralisatieMockMvc
            .perform(delete(ENTITY_API_URL_ID, generalisatie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return generalisatieRepository.count();
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

    protected Generalisatie getPersistedGeneralisatie(Generalisatie generalisatie) {
        return generalisatieRepository.findById(generalisatie.getId()).orElseThrow();
    }

    protected void assertPersistedGeneralisatieToMatchAllProperties(Generalisatie expectedGeneralisatie) {
        assertGeneralisatieAllPropertiesEquals(expectedGeneralisatie, getPersistedGeneralisatie(expectedGeneralisatie));
    }

    protected void assertPersistedGeneralisatieToMatchUpdatableProperties(Generalisatie expectedGeneralisatie) {
        assertGeneralisatieAllUpdatablePropertiesEquals(expectedGeneralisatie, getPersistedGeneralisatie(expectedGeneralisatie));
    }
}
