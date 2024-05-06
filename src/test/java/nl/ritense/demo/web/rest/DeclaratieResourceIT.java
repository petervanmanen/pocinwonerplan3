package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.DeclaratieAsserts.*;
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
import nl.ritense.demo.domain.Declaratie;
import nl.ritense.demo.repository.DeclaratieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DeclaratieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DeclaratieResourceIT {

    private static final String DEFAULT_DATUMDECLARATIE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMDECLARATIE = "BBBBBBBBBB";

    private static final String DEFAULT_DECLARATIEBEDRAG = "AAAAAAAAAA";
    private static final String UPDATED_DECLARATIEBEDRAG = "BBBBBBBBBB";

    private static final String DEFAULT_DECLARATIESTATUS = "AAAAAAAAAA";
    private static final String UPDATED_DECLARATIESTATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/declaraties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DeclaratieRepository declaratieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeclaratieMockMvc;

    private Declaratie declaratie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Declaratie createEntity(EntityManager em) {
        Declaratie declaratie = new Declaratie()
            .datumdeclaratie(DEFAULT_DATUMDECLARATIE)
            .declaratiebedrag(DEFAULT_DECLARATIEBEDRAG)
            .declaratiestatus(DEFAULT_DECLARATIESTATUS);
        return declaratie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Declaratie createUpdatedEntity(EntityManager em) {
        Declaratie declaratie = new Declaratie()
            .datumdeclaratie(UPDATED_DATUMDECLARATIE)
            .declaratiebedrag(UPDATED_DECLARATIEBEDRAG)
            .declaratiestatus(UPDATED_DECLARATIESTATUS);
        return declaratie;
    }

    @BeforeEach
    public void initTest() {
        declaratie = createEntity(em);
    }

    @Test
    @Transactional
    void createDeclaratie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Declaratie
        var returnedDeclaratie = om.readValue(
            restDeclaratieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(declaratie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Declaratie.class
        );

        // Validate the Declaratie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDeclaratieUpdatableFieldsEquals(returnedDeclaratie, getPersistedDeclaratie(returnedDeclaratie));
    }

    @Test
    @Transactional
    void createDeclaratieWithExistingId() throws Exception {
        // Create the Declaratie with an existing ID
        declaratie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeclaratieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(declaratie)))
            .andExpect(status().isBadRequest());

        // Validate the Declaratie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDeclaraties() throws Exception {
        // Initialize the database
        declaratieRepository.saveAndFlush(declaratie);

        // Get all the declaratieList
        restDeclaratieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(declaratie.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumdeclaratie").value(hasItem(DEFAULT_DATUMDECLARATIE)))
            .andExpect(jsonPath("$.[*].declaratiebedrag").value(hasItem(DEFAULT_DECLARATIEBEDRAG)))
            .andExpect(jsonPath("$.[*].declaratiestatus").value(hasItem(DEFAULT_DECLARATIESTATUS)));
    }

    @Test
    @Transactional
    void getDeclaratie() throws Exception {
        // Initialize the database
        declaratieRepository.saveAndFlush(declaratie);

        // Get the declaratie
        restDeclaratieMockMvc
            .perform(get(ENTITY_API_URL_ID, declaratie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(declaratie.getId().intValue()))
            .andExpect(jsonPath("$.datumdeclaratie").value(DEFAULT_DATUMDECLARATIE))
            .andExpect(jsonPath("$.declaratiebedrag").value(DEFAULT_DECLARATIEBEDRAG))
            .andExpect(jsonPath("$.declaratiestatus").value(DEFAULT_DECLARATIESTATUS));
    }

    @Test
    @Transactional
    void getNonExistingDeclaratie() throws Exception {
        // Get the declaratie
        restDeclaratieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDeclaratie() throws Exception {
        // Initialize the database
        declaratieRepository.saveAndFlush(declaratie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the declaratie
        Declaratie updatedDeclaratie = declaratieRepository.findById(declaratie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDeclaratie are not directly saved in db
        em.detach(updatedDeclaratie);
        updatedDeclaratie
            .datumdeclaratie(UPDATED_DATUMDECLARATIE)
            .declaratiebedrag(UPDATED_DECLARATIEBEDRAG)
            .declaratiestatus(UPDATED_DECLARATIESTATUS);

        restDeclaratieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDeclaratie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDeclaratie))
            )
            .andExpect(status().isOk());

        // Validate the Declaratie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDeclaratieToMatchAllProperties(updatedDeclaratie);
    }

    @Test
    @Transactional
    void putNonExistingDeclaratie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        declaratie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeclaratieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, declaratie.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(declaratie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Declaratie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDeclaratie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        declaratie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeclaratieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(declaratie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Declaratie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDeclaratie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        declaratie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeclaratieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(declaratie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Declaratie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDeclaratieWithPatch() throws Exception {
        // Initialize the database
        declaratieRepository.saveAndFlush(declaratie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the declaratie using partial update
        Declaratie partialUpdatedDeclaratie = new Declaratie();
        partialUpdatedDeclaratie.setId(declaratie.getId());

        partialUpdatedDeclaratie.datumdeclaratie(UPDATED_DATUMDECLARATIE);

        restDeclaratieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeclaratie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDeclaratie))
            )
            .andExpect(status().isOk());

        // Validate the Declaratie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDeclaratieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDeclaratie, declaratie),
            getPersistedDeclaratie(declaratie)
        );
    }

    @Test
    @Transactional
    void fullUpdateDeclaratieWithPatch() throws Exception {
        // Initialize the database
        declaratieRepository.saveAndFlush(declaratie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the declaratie using partial update
        Declaratie partialUpdatedDeclaratie = new Declaratie();
        partialUpdatedDeclaratie.setId(declaratie.getId());

        partialUpdatedDeclaratie
            .datumdeclaratie(UPDATED_DATUMDECLARATIE)
            .declaratiebedrag(UPDATED_DECLARATIEBEDRAG)
            .declaratiestatus(UPDATED_DECLARATIESTATUS);

        restDeclaratieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeclaratie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDeclaratie))
            )
            .andExpect(status().isOk());

        // Validate the Declaratie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDeclaratieUpdatableFieldsEquals(partialUpdatedDeclaratie, getPersistedDeclaratie(partialUpdatedDeclaratie));
    }

    @Test
    @Transactional
    void patchNonExistingDeclaratie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        declaratie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeclaratieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, declaratie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(declaratie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Declaratie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDeclaratie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        declaratie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeclaratieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(declaratie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Declaratie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDeclaratie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        declaratie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeclaratieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(declaratie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Declaratie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDeclaratie() throws Exception {
        // Initialize the database
        declaratieRepository.saveAndFlush(declaratie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the declaratie
        restDeclaratieMockMvc
            .perform(delete(ENTITY_API_URL_ID, declaratie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return declaratieRepository.count();
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

    protected Declaratie getPersistedDeclaratie(Declaratie declaratie) {
        return declaratieRepository.findById(declaratie.getId()).orElseThrow();
    }

    protected void assertPersistedDeclaratieToMatchAllProperties(Declaratie expectedDeclaratie) {
        assertDeclaratieAllPropertiesEquals(expectedDeclaratie, getPersistedDeclaratie(expectedDeclaratie));
    }

    protected void assertPersistedDeclaratieToMatchUpdatableProperties(Declaratie expectedDeclaratie) {
        assertDeclaratieAllUpdatablePropertiesEquals(expectedDeclaratie, getPersistedDeclaratie(expectedDeclaratie));
    }
}
