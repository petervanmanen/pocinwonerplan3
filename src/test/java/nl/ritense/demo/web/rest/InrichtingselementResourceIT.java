package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.InrichtingselementAsserts.*;
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
import nl.ritense.demo.domain.Inrichtingselement;
import nl.ritense.demo.repository.InrichtingselementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link InrichtingselementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InrichtingselementResourceIT {

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDINRICHTINGSELEMENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDINRICHTINGSELEMENT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDINRICHTINGSELEMENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDINRICHTINGSELEMENT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GEOMETRIEINRICHTINGSELEMENT = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIEINRICHTINGSELEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIEINRICHTINGSELEMENT = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIEINRICHTINGSELEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_LOD_0_GEOMETRIEINRICHTINGSELEMENT = "AAAAAAAAAA";
    private static final String UPDATED_LOD_0_GEOMETRIEINRICHTINGSELEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_PLUSTYPEINRICHTINGSELEMENT = "AAAAAAAAAA";
    private static final String UPDATED_PLUSTYPEINRICHTINGSELEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_RELATIEVEHOOGTELIGGINGINRICHTINGSELEMENT = "AAAAAAAAAA";
    private static final String UPDATED_RELATIEVEHOOGTELIGGINGINRICHTINGSELEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_STATUSINRICHTINGSELEMENT = "AAAAAAAAAA";
    private static final String UPDATED_STATUSINRICHTINGSELEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEINRICHTINGSELEMENT = "AAAAAAAAAA";
    private static final String UPDATED_TYPEINRICHTINGSELEMENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/inrichtingselements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InrichtingselementRepository inrichtingselementRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInrichtingselementMockMvc;

    private Inrichtingselement inrichtingselement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inrichtingselement createEntity(EntityManager em) {
        Inrichtingselement inrichtingselement = new Inrichtingselement()
            .datumbegingeldigheidinrichtingselement(DEFAULT_DATUMBEGINGELDIGHEIDINRICHTINGSELEMENT)
            .datumeindegeldigheidinrichtingselement(DEFAULT_DATUMEINDEGELDIGHEIDINRICHTINGSELEMENT)
            .geometrieinrichtingselement(DEFAULT_GEOMETRIEINRICHTINGSELEMENT)
            .identificatieinrichtingselement(DEFAULT_IDENTIFICATIEINRICHTINGSELEMENT)
            .lod0geometrieinrichtingselement(DEFAULT_LOD_0_GEOMETRIEINRICHTINGSELEMENT)
            .plustypeinrichtingselement(DEFAULT_PLUSTYPEINRICHTINGSELEMENT)
            .relatievehoogteligginginrichtingselement(DEFAULT_RELATIEVEHOOGTELIGGINGINRICHTINGSELEMENT)
            .statusinrichtingselement(DEFAULT_STATUSINRICHTINGSELEMENT)
            .typeinrichtingselement(DEFAULT_TYPEINRICHTINGSELEMENT);
        return inrichtingselement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inrichtingselement createUpdatedEntity(EntityManager em) {
        Inrichtingselement inrichtingselement = new Inrichtingselement()
            .datumbegingeldigheidinrichtingselement(UPDATED_DATUMBEGINGELDIGHEIDINRICHTINGSELEMENT)
            .datumeindegeldigheidinrichtingselement(UPDATED_DATUMEINDEGELDIGHEIDINRICHTINGSELEMENT)
            .geometrieinrichtingselement(UPDATED_GEOMETRIEINRICHTINGSELEMENT)
            .identificatieinrichtingselement(UPDATED_IDENTIFICATIEINRICHTINGSELEMENT)
            .lod0geometrieinrichtingselement(UPDATED_LOD_0_GEOMETRIEINRICHTINGSELEMENT)
            .plustypeinrichtingselement(UPDATED_PLUSTYPEINRICHTINGSELEMENT)
            .relatievehoogteligginginrichtingselement(UPDATED_RELATIEVEHOOGTELIGGINGINRICHTINGSELEMENT)
            .statusinrichtingselement(UPDATED_STATUSINRICHTINGSELEMENT)
            .typeinrichtingselement(UPDATED_TYPEINRICHTINGSELEMENT);
        return inrichtingselement;
    }

    @BeforeEach
    public void initTest() {
        inrichtingselement = createEntity(em);
    }

    @Test
    @Transactional
    void createInrichtingselement() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Inrichtingselement
        var returnedInrichtingselement = om.readValue(
            restInrichtingselementMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inrichtingselement)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Inrichtingselement.class
        );

        // Validate the Inrichtingselement in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertInrichtingselementUpdatableFieldsEquals(
            returnedInrichtingselement,
            getPersistedInrichtingselement(returnedInrichtingselement)
        );
    }

    @Test
    @Transactional
    void createInrichtingselementWithExistingId() throws Exception {
        // Create the Inrichtingselement with an existing ID
        inrichtingselement.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInrichtingselementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inrichtingselement)))
            .andExpect(status().isBadRequest());

        // Validate the Inrichtingselement in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInrichtingselements() throws Exception {
        // Initialize the database
        inrichtingselementRepository.saveAndFlush(inrichtingselement);

        // Get all the inrichtingselementList
        restInrichtingselementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inrichtingselement.getId().intValue())))
            .andExpect(
                jsonPath("$.[*].datumbegingeldigheidinrichtingselement").value(
                    hasItem(DEFAULT_DATUMBEGINGELDIGHEIDINRICHTINGSELEMENT.toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].datumeindegeldigheidinrichtingselement").value(
                    hasItem(DEFAULT_DATUMEINDEGELDIGHEIDINRICHTINGSELEMENT.toString())
                )
            )
            .andExpect(jsonPath("$.[*].geometrieinrichtingselement").value(hasItem(DEFAULT_GEOMETRIEINRICHTINGSELEMENT)))
            .andExpect(jsonPath("$.[*].identificatieinrichtingselement").value(hasItem(DEFAULT_IDENTIFICATIEINRICHTINGSELEMENT)))
            .andExpect(jsonPath("$.[*].lod0geometrieinrichtingselement").value(hasItem(DEFAULT_LOD_0_GEOMETRIEINRICHTINGSELEMENT)))
            .andExpect(jsonPath("$.[*].plustypeinrichtingselement").value(hasItem(DEFAULT_PLUSTYPEINRICHTINGSELEMENT)))
            .andExpect(
                jsonPath("$.[*].relatievehoogteligginginrichtingselement").value(hasItem(DEFAULT_RELATIEVEHOOGTELIGGINGINRICHTINGSELEMENT))
            )
            .andExpect(jsonPath("$.[*].statusinrichtingselement").value(hasItem(DEFAULT_STATUSINRICHTINGSELEMENT)))
            .andExpect(jsonPath("$.[*].typeinrichtingselement").value(hasItem(DEFAULT_TYPEINRICHTINGSELEMENT)));
    }

    @Test
    @Transactional
    void getInrichtingselement() throws Exception {
        // Initialize the database
        inrichtingselementRepository.saveAndFlush(inrichtingselement);

        // Get the inrichtingselement
        restInrichtingselementMockMvc
            .perform(get(ENTITY_API_URL_ID, inrichtingselement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inrichtingselement.getId().intValue()))
            .andExpect(
                jsonPath("$.datumbegingeldigheidinrichtingselement").value(DEFAULT_DATUMBEGINGELDIGHEIDINRICHTINGSELEMENT.toString())
            )
            .andExpect(
                jsonPath("$.datumeindegeldigheidinrichtingselement").value(DEFAULT_DATUMEINDEGELDIGHEIDINRICHTINGSELEMENT.toString())
            )
            .andExpect(jsonPath("$.geometrieinrichtingselement").value(DEFAULT_GEOMETRIEINRICHTINGSELEMENT))
            .andExpect(jsonPath("$.identificatieinrichtingselement").value(DEFAULT_IDENTIFICATIEINRICHTINGSELEMENT))
            .andExpect(jsonPath("$.lod0geometrieinrichtingselement").value(DEFAULT_LOD_0_GEOMETRIEINRICHTINGSELEMENT))
            .andExpect(jsonPath("$.plustypeinrichtingselement").value(DEFAULT_PLUSTYPEINRICHTINGSELEMENT))
            .andExpect(jsonPath("$.relatievehoogteligginginrichtingselement").value(DEFAULT_RELATIEVEHOOGTELIGGINGINRICHTINGSELEMENT))
            .andExpect(jsonPath("$.statusinrichtingselement").value(DEFAULT_STATUSINRICHTINGSELEMENT))
            .andExpect(jsonPath("$.typeinrichtingselement").value(DEFAULT_TYPEINRICHTINGSELEMENT));
    }

    @Test
    @Transactional
    void getNonExistingInrichtingselement() throws Exception {
        // Get the inrichtingselement
        restInrichtingselementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInrichtingselement() throws Exception {
        // Initialize the database
        inrichtingselementRepository.saveAndFlush(inrichtingselement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inrichtingselement
        Inrichtingselement updatedInrichtingselement = inrichtingselementRepository.findById(inrichtingselement.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInrichtingselement are not directly saved in db
        em.detach(updatedInrichtingselement);
        updatedInrichtingselement
            .datumbegingeldigheidinrichtingselement(UPDATED_DATUMBEGINGELDIGHEIDINRICHTINGSELEMENT)
            .datumeindegeldigheidinrichtingselement(UPDATED_DATUMEINDEGELDIGHEIDINRICHTINGSELEMENT)
            .geometrieinrichtingselement(UPDATED_GEOMETRIEINRICHTINGSELEMENT)
            .identificatieinrichtingselement(UPDATED_IDENTIFICATIEINRICHTINGSELEMENT)
            .lod0geometrieinrichtingselement(UPDATED_LOD_0_GEOMETRIEINRICHTINGSELEMENT)
            .plustypeinrichtingselement(UPDATED_PLUSTYPEINRICHTINGSELEMENT)
            .relatievehoogteligginginrichtingselement(UPDATED_RELATIEVEHOOGTELIGGINGINRICHTINGSELEMENT)
            .statusinrichtingselement(UPDATED_STATUSINRICHTINGSELEMENT)
            .typeinrichtingselement(UPDATED_TYPEINRICHTINGSELEMENT);

        restInrichtingselementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInrichtingselement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedInrichtingselement))
            )
            .andExpect(status().isOk());

        // Validate the Inrichtingselement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInrichtingselementToMatchAllProperties(updatedInrichtingselement);
    }

    @Test
    @Transactional
    void putNonExistingInrichtingselement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inrichtingselement.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInrichtingselementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inrichtingselement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inrichtingselement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inrichtingselement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInrichtingselement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inrichtingselement.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInrichtingselementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inrichtingselement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inrichtingselement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInrichtingselement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inrichtingselement.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInrichtingselementMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inrichtingselement)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inrichtingselement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInrichtingselementWithPatch() throws Exception {
        // Initialize the database
        inrichtingselementRepository.saveAndFlush(inrichtingselement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inrichtingselement using partial update
        Inrichtingselement partialUpdatedInrichtingselement = new Inrichtingselement();
        partialUpdatedInrichtingselement.setId(inrichtingselement.getId());

        partialUpdatedInrichtingselement.datumeindegeldigheidinrichtingselement(UPDATED_DATUMEINDEGELDIGHEIDINRICHTINGSELEMENT);

        restInrichtingselementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInrichtingselement.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInrichtingselement))
            )
            .andExpect(status().isOk());

        // Validate the Inrichtingselement in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInrichtingselementUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInrichtingselement, inrichtingselement),
            getPersistedInrichtingselement(inrichtingselement)
        );
    }

    @Test
    @Transactional
    void fullUpdateInrichtingselementWithPatch() throws Exception {
        // Initialize the database
        inrichtingselementRepository.saveAndFlush(inrichtingselement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inrichtingselement using partial update
        Inrichtingselement partialUpdatedInrichtingselement = new Inrichtingselement();
        partialUpdatedInrichtingselement.setId(inrichtingselement.getId());

        partialUpdatedInrichtingselement
            .datumbegingeldigheidinrichtingselement(UPDATED_DATUMBEGINGELDIGHEIDINRICHTINGSELEMENT)
            .datumeindegeldigheidinrichtingselement(UPDATED_DATUMEINDEGELDIGHEIDINRICHTINGSELEMENT)
            .geometrieinrichtingselement(UPDATED_GEOMETRIEINRICHTINGSELEMENT)
            .identificatieinrichtingselement(UPDATED_IDENTIFICATIEINRICHTINGSELEMENT)
            .lod0geometrieinrichtingselement(UPDATED_LOD_0_GEOMETRIEINRICHTINGSELEMENT)
            .plustypeinrichtingselement(UPDATED_PLUSTYPEINRICHTINGSELEMENT)
            .relatievehoogteligginginrichtingselement(UPDATED_RELATIEVEHOOGTELIGGINGINRICHTINGSELEMENT)
            .statusinrichtingselement(UPDATED_STATUSINRICHTINGSELEMENT)
            .typeinrichtingselement(UPDATED_TYPEINRICHTINGSELEMENT);

        restInrichtingselementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInrichtingselement.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInrichtingselement))
            )
            .andExpect(status().isOk());

        // Validate the Inrichtingselement in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInrichtingselementUpdatableFieldsEquals(
            partialUpdatedInrichtingselement,
            getPersistedInrichtingselement(partialUpdatedInrichtingselement)
        );
    }

    @Test
    @Transactional
    void patchNonExistingInrichtingselement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inrichtingselement.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInrichtingselementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inrichtingselement.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inrichtingselement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inrichtingselement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInrichtingselement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inrichtingselement.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInrichtingselementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inrichtingselement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inrichtingselement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInrichtingselement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inrichtingselement.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInrichtingselementMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(inrichtingselement)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inrichtingselement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInrichtingselement() throws Exception {
        // Initialize the database
        inrichtingselementRepository.saveAndFlush(inrichtingselement);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the inrichtingselement
        restInrichtingselementMockMvc
            .perform(delete(ENTITY_API_URL_ID, inrichtingselement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return inrichtingselementRepository.count();
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

    protected Inrichtingselement getPersistedInrichtingselement(Inrichtingselement inrichtingselement) {
        return inrichtingselementRepository.findById(inrichtingselement.getId()).orElseThrow();
    }

    protected void assertPersistedInrichtingselementToMatchAllProperties(Inrichtingselement expectedInrichtingselement) {
        assertInrichtingselementAllPropertiesEquals(expectedInrichtingselement, getPersistedInrichtingselement(expectedInrichtingselement));
    }

    protected void assertPersistedInrichtingselementToMatchUpdatableProperties(Inrichtingselement expectedInrichtingselement) {
        assertInrichtingselementAllUpdatablePropertiesEquals(
            expectedInrichtingselement,
            getPersistedInrichtingselement(expectedInrichtingselement)
        );
    }
}
