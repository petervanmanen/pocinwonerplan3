package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.DeclaratieregelAsserts.*;
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
import nl.ritense.demo.domain.Declaratieregel;
import nl.ritense.demo.repository.DeclaratieregelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DeclaratieregelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DeclaratieregelResourceIT {

    private static final BigDecimal DEFAULT_BEDRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_BEDRAG = new BigDecimal(2);

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/declaratieregels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DeclaratieregelRepository declaratieregelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeclaratieregelMockMvc;

    private Declaratieregel declaratieregel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Declaratieregel createEntity(EntityManager em) {
        Declaratieregel declaratieregel = new Declaratieregel()
            .bedrag(DEFAULT_BEDRAG)
            .code(DEFAULT_CODE)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumstart(DEFAULT_DATUMSTART);
        return declaratieregel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Declaratieregel createUpdatedEntity(EntityManager em) {
        Declaratieregel declaratieregel = new Declaratieregel()
            .bedrag(UPDATED_BEDRAG)
            .code(UPDATED_CODE)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART);
        return declaratieregel;
    }

    @BeforeEach
    public void initTest() {
        declaratieregel = createEntity(em);
    }

    @Test
    @Transactional
    void createDeclaratieregel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Declaratieregel
        var returnedDeclaratieregel = om.readValue(
            restDeclaratieregelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(declaratieregel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Declaratieregel.class
        );

        // Validate the Declaratieregel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDeclaratieregelUpdatableFieldsEquals(returnedDeclaratieregel, getPersistedDeclaratieregel(returnedDeclaratieregel));
    }

    @Test
    @Transactional
    void createDeclaratieregelWithExistingId() throws Exception {
        // Create the Declaratieregel with an existing ID
        declaratieregel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeclaratieregelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(declaratieregel)))
            .andExpect(status().isBadRequest());

        // Validate the Declaratieregel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDeclaratieregels() throws Exception {
        // Initialize the database
        declaratieregelRepository.saveAndFlush(declaratieregel);

        // Get all the declaratieregelList
        restDeclaratieregelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(declaratieregel.getId().intValue())))
            .andExpect(jsonPath("$.[*].bedrag").value(hasItem(sameNumber(DEFAULT_BEDRAG))))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())));
    }

    @Test
    @Transactional
    void getDeclaratieregel() throws Exception {
        // Initialize the database
        declaratieregelRepository.saveAndFlush(declaratieregel);

        // Get the declaratieregel
        restDeclaratieregelMockMvc
            .perform(get(ENTITY_API_URL_ID, declaratieregel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(declaratieregel.getId().intValue()))
            .andExpect(jsonPath("$.bedrag").value(sameNumber(DEFAULT_BEDRAG)))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDeclaratieregel() throws Exception {
        // Get the declaratieregel
        restDeclaratieregelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDeclaratieregel() throws Exception {
        // Initialize the database
        declaratieregelRepository.saveAndFlush(declaratieregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the declaratieregel
        Declaratieregel updatedDeclaratieregel = declaratieregelRepository.findById(declaratieregel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDeclaratieregel are not directly saved in db
        em.detach(updatedDeclaratieregel);
        updatedDeclaratieregel.bedrag(UPDATED_BEDRAG).code(UPDATED_CODE).datumeinde(UPDATED_DATUMEINDE).datumstart(UPDATED_DATUMSTART);

        restDeclaratieregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDeclaratieregel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDeclaratieregel))
            )
            .andExpect(status().isOk());

        // Validate the Declaratieregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDeclaratieregelToMatchAllProperties(updatedDeclaratieregel);
    }

    @Test
    @Transactional
    void putNonExistingDeclaratieregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        declaratieregel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeclaratieregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, declaratieregel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(declaratieregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Declaratieregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDeclaratieregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        declaratieregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeclaratieregelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(declaratieregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Declaratieregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDeclaratieregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        declaratieregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeclaratieregelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(declaratieregel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Declaratieregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDeclaratieregelWithPatch() throws Exception {
        // Initialize the database
        declaratieregelRepository.saveAndFlush(declaratieregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the declaratieregel using partial update
        Declaratieregel partialUpdatedDeclaratieregel = new Declaratieregel();
        partialUpdatedDeclaratieregel.setId(declaratieregel.getId());

        partialUpdatedDeclaratieregel.code(UPDATED_CODE).datumstart(UPDATED_DATUMSTART);

        restDeclaratieregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeclaratieregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDeclaratieregel))
            )
            .andExpect(status().isOk());

        // Validate the Declaratieregel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDeclaratieregelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDeclaratieregel, declaratieregel),
            getPersistedDeclaratieregel(declaratieregel)
        );
    }

    @Test
    @Transactional
    void fullUpdateDeclaratieregelWithPatch() throws Exception {
        // Initialize the database
        declaratieregelRepository.saveAndFlush(declaratieregel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the declaratieregel using partial update
        Declaratieregel partialUpdatedDeclaratieregel = new Declaratieregel();
        partialUpdatedDeclaratieregel.setId(declaratieregel.getId());

        partialUpdatedDeclaratieregel
            .bedrag(UPDATED_BEDRAG)
            .code(UPDATED_CODE)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumstart(UPDATED_DATUMSTART);

        restDeclaratieregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeclaratieregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDeclaratieregel))
            )
            .andExpect(status().isOk());

        // Validate the Declaratieregel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDeclaratieregelUpdatableFieldsEquals(
            partialUpdatedDeclaratieregel,
            getPersistedDeclaratieregel(partialUpdatedDeclaratieregel)
        );
    }

    @Test
    @Transactional
    void patchNonExistingDeclaratieregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        declaratieregel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeclaratieregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, declaratieregel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(declaratieregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Declaratieregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDeclaratieregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        declaratieregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeclaratieregelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(declaratieregel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Declaratieregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDeclaratieregel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        declaratieregel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeclaratieregelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(declaratieregel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Declaratieregel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDeclaratieregel() throws Exception {
        // Initialize the database
        declaratieregelRepository.saveAndFlush(declaratieregel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the declaratieregel
        restDeclaratieregelMockMvc
            .perform(delete(ENTITY_API_URL_ID, declaratieregel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return declaratieregelRepository.count();
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

    protected Declaratieregel getPersistedDeclaratieregel(Declaratieregel declaratieregel) {
        return declaratieregelRepository.findById(declaratieregel.getId()).orElseThrow();
    }

    protected void assertPersistedDeclaratieregelToMatchAllProperties(Declaratieregel expectedDeclaratieregel) {
        assertDeclaratieregelAllPropertiesEquals(expectedDeclaratieregel, getPersistedDeclaratieregel(expectedDeclaratieregel));
    }

    protected void assertPersistedDeclaratieregelToMatchUpdatableProperties(Declaratieregel expectedDeclaratieregel) {
        assertDeclaratieregelAllUpdatablePropertiesEquals(expectedDeclaratieregel, getPersistedDeclaratieregel(expectedDeclaratieregel));
    }
}
