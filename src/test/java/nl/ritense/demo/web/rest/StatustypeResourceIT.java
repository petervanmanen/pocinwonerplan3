package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.StatustypeAsserts.*;
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
import nl.ritense.demo.domain.Statustype;
import nl.ritense.demo.repository.StatustypeRepository;
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
 * Integration tests for the {@link StatustypeResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class StatustypeResourceIT {

    private static final String DEFAULT_DATUMBEGINGELDIGHEIDSTATUSTYPE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMBEGINGELDIGHEIDSTATUSTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMEINDEGELDIGHEIDSTATUSTYPE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMEINDEGELDIGHEIDSTATUSTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DOORLOOPTIJDSTATUS = "AAAAAAAAAA";
    private static final String UPDATED_DOORLOOPTIJDSTATUS = "BBBBBBBBBB";

    private static final String DEFAULT_STATUSTYPEOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_STATUSTYPEOMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_STATUSTYPEOMSCHRIJVINGGENERIEK = "AAAAAAAAAA";
    private static final String UPDATED_STATUSTYPEOMSCHRIJVINGGENERIEK = "BBBBBBBBBB";

    private static final String DEFAULT_STATUSTYPEVOLGNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_STATUSTYPEVOLGNUMMER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/statustypes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StatustypeRepository statustypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStatustypeMockMvc;

    private Statustype statustype;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Statustype createEntity(EntityManager em) {
        Statustype statustype = new Statustype()
            .datumbegingeldigheidstatustype(DEFAULT_DATUMBEGINGELDIGHEIDSTATUSTYPE)
            .datumeindegeldigheidstatustype(DEFAULT_DATUMEINDEGELDIGHEIDSTATUSTYPE)
            .doorlooptijdstatus(DEFAULT_DOORLOOPTIJDSTATUS)
            .statustypeomschrijving(DEFAULT_STATUSTYPEOMSCHRIJVING)
            .statustypeomschrijvinggeneriek(DEFAULT_STATUSTYPEOMSCHRIJVINGGENERIEK)
            .statustypevolgnummer(DEFAULT_STATUSTYPEVOLGNUMMER);
        return statustype;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Statustype createUpdatedEntity(EntityManager em) {
        Statustype statustype = new Statustype()
            .datumbegingeldigheidstatustype(UPDATED_DATUMBEGINGELDIGHEIDSTATUSTYPE)
            .datumeindegeldigheidstatustype(UPDATED_DATUMEINDEGELDIGHEIDSTATUSTYPE)
            .doorlooptijdstatus(UPDATED_DOORLOOPTIJDSTATUS)
            .statustypeomschrijving(UPDATED_STATUSTYPEOMSCHRIJVING)
            .statustypeomschrijvinggeneriek(UPDATED_STATUSTYPEOMSCHRIJVINGGENERIEK)
            .statustypevolgnummer(UPDATED_STATUSTYPEVOLGNUMMER);
        return statustype;
    }

    @BeforeEach
    public void initTest() {
        statustype = createEntity(em);
    }

    @Test
    @Transactional
    void createStatustype() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Statustype
        var returnedStatustype = om.readValue(
            restStatustypeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(statustype)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Statustype.class
        );

        // Validate the Statustype in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertStatustypeUpdatableFieldsEquals(returnedStatustype, getPersistedStatustype(returnedStatustype));
    }

    @Test
    @Transactional
    void createStatustypeWithExistingId() throws Exception {
        // Create the Statustype with an existing ID
        statustype.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatustypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(statustype)))
            .andExpect(status().isBadRequest());

        // Validate the Statustype in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStatustypes() throws Exception {
        // Initialize the database
        statustypeRepository.saveAndFlush(statustype);

        // Get all the statustypeList
        restStatustypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statustype.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumbegingeldigheidstatustype").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEIDSTATUSTYPE)))
            .andExpect(jsonPath("$.[*].datumeindegeldigheidstatustype").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEIDSTATUSTYPE)))
            .andExpect(jsonPath("$.[*].doorlooptijdstatus").value(hasItem(DEFAULT_DOORLOOPTIJDSTATUS)))
            .andExpect(jsonPath("$.[*].statustypeomschrijving").value(hasItem(DEFAULT_STATUSTYPEOMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].statustypeomschrijvinggeneriek").value(hasItem(DEFAULT_STATUSTYPEOMSCHRIJVINGGENERIEK)))
            .andExpect(jsonPath("$.[*].statustypevolgnummer").value(hasItem(DEFAULT_STATUSTYPEVOLGNUMMER)));
    }

    @Test
    @Transactional
    void getStatustype() throws Exception {
        // Initialize the database
        statustypeRepository.saveAndFlush(statustype);

        // Get the statustype
        restStatustypeMockMvc
            .perform(get(ENTITY_API_URL_ID, statustype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(statustype.getId().intValue()))
            .andExpect(jsonPath("$.datumbegingeldigheidstatustype").value(DEFAULT_DATUMBEGINGELDIGHEIDSTATUSTYPE))
            .andExpect(jsonPath("$.datumeindegeldigheidstatustype").value(DEFAULT_DATUMEINDEGELDIGHEIDSTATUSTYPE))
            .andExpect(jsonPath("$.doorlooptijdstatus").value(DEFAULT_DOORLOOPTIJDSTATUS))
            .andExpect(jsonPath("$.statustypeomschrijving").value(DEFAULT_STATUSTYPEOMSCHRIJVING))
            .andExpect(jsonPath("$.statustypeomschrijvinggeneriek").value(DEFAULT_STATUSTYPEOMSCHRIJVINGGENERIEK))
            .andExpect(jsonPath("$.statustypevolgnummer").value(DEFAULT_STATUSTYPEVOLGNUMMER));
    }

    @Test
    @Transactional
    void getNonExistingStatustype() throws Exception {
        // Get the statustype
        restStatustypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStatustype() throws Exception {
        // Initialize the database
        statustypeRepository.saveAndFlush(statustype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the statustype
        Statustype updatedStatustype = statustypeRepository.findById(statustype.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStatustype are not directly saved in db
        em.detach(updatedStatustype);
        updatedStatustype
            .datumbegingeldigheidstatustype(UPDATED_DATUMBEGINGELDIGHEIDSTATUSTYPE)
            .datumeindegeldigheidstatustype(UPDATED_DATUMEINDEGELDIGHEIDSTATUSTYPE)
            .doorlooptijdstatus(UPDATED_DOORLOOPTIJDSTATUS)
            .statustypeomschrijving(UPDATED_STATUSTYPEOMSCHRIJVING)
            .statustypeomschrijvinggeneriek(UPDATED_STATUSTYPEOMSCHRIJVINGGENERIEK)
            .statustypevolgnummer(UPDATED_STATUSTYPEVOLGNUMMER);

        restStatustypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStatustype.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedStatustype))
            )
            .andExpect(status().isOk());

        // Validate the Statustype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStatustypeToMatchAllProperties(updatedStatustype);
    }

    @Test
    @Transactional
    void putNonExistingStatustype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statustype.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatustypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statustype.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(statustype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Statustype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStatustype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statustype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatustypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(statustype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Statustype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStatustype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statustype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatustypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(statustype)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Statustype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStatustypeWithPatch() throws Exception {
        // Initialize the database
        statustypeRepository.saveAndFlush(statustype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the statustype using partial update
        Statustype partialUpdatedStatustype = new Statustype();
        partialUpdatedStatustype.setId(statustype.getId());

        partialUpdatedStatustype
            .datumbegingeldigheidstatustype(UPDATED_DATUMBEGINGELDIGHEIDSTATUSTYPE)
            .datumeindegeldigheidstatustype(UPDATED_DATUMEINDEGELDIGHEIDSTATUSTYPE)
            .doorlooptijdstatus(UPDATED_DOORLOOPTIJDSTATUS)
            .statustypeomschrijving(UPDATED_STATUSTYPEOMSCHRIJVING)
            .statustypeomschrijvinggeneriek(UPDATED_STATUSTYPEOMSCHRIJVINGGENERIEK);

        restStatustypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatustype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStatustype))
            )
            .andExpect(status().isOk());

        // Validate the Statustype in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStatustypeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedStatustype, statustype),
            getPersistedStatustype(statustype)
        );
    }

    @Test
    @Transactional
    void fullUpdateStatustypeWithPatch() throws Exception {
        // Initialize the database
        statustypeRepository.saveAndFlush(statustype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the statustype using partial update
        Statustype partialUpdatedStatustype = new Statustype();
        partialUpdatedStatustype.setId(statustype.getId());

        partialUpdatedStatustype
            .datumbegingeldigheidstatustype(UPDATED_DATUMBEGINGELDIGHEIDSTATUSTYPE)
            .datumeindegeldigheidstatustype(UPDATED_DATUMEINDEGELDIGHEIDSTATUSTYPE)
            .doorlooptijdstatus(UPDATED_DOORLOOPTIJDSTATUS)
            .statustypeomschrijving(UPDATED_STATUSTYPEOMSCHRIJVING)
            .statustypeomschrijvinggeneriek(UPDATED_STATUSTYPEOMSCHRIJVINGGENERIEK)
            .statustypevolgnummer(UPDATED_STATUSTYPEVOLGNUMMER);

        restStatustypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatustype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStatustype))
            )
            .andExpect(status().isOk());

        // Validate the Statustype in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStatustypeUpdatableFieldsEquals(partialUpdatedStatustype, getPersistedStatustype(partialUpdatedStatustype));
    }

    @Test
    @Transactional
    void patchNonExistingStatustype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statustype.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatustypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, statustype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(statustype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Statustype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStatustype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statustype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatustypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(statustype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Statustype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStatustype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statustype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatustypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(statustype)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Statustype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStatustype() throws Exception {
        // Initialize the database
        statustypeRepository.saveAndFlush(statustype);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the statustype
        restStatustypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, statustype.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return statustypeRepository.count();
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

    protected Statustype getPersistedStatustype(Statustype statustype) {
        return statustypeRepository.findById(statustype.getId()).orElseThrow();
    }

    protected void assertPersistedStatustypeToMatchAllProperties(Statustype expectedStatustype) {
        assertStatustypeAllPropertiesEquals(expectedStatustype, getPersistedStatustype(expectedStatustype));
    }

    protected void assertPersistedStatustypeToMatchUpdatableProperties(Statustype expectedStatustype) {
        assertStatustypeAllUpdatablePropertiesEquals(expectedStatustype, getPersistedStatustype(expectedStatustype));
    }
}
