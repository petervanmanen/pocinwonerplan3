package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OndersteunendwaterdeelAsserts.*;
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
import nl.ritense.demo.domain.Ondersteunendwaterdeel;
import nl.ritense.demo.repository.OndersteunendwaterdeelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OndersteunendwaterdeelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OndersteunendwaterdeelResourceIT {

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDONDERSTEUNENDWATERDEEL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDONDERSTEUNENDWATERDEEL = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDONDERSTEUNENDWATERDEEL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDONDERSTEUNENDWATERDEEL = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GEOMETRIEONDERSTEUNENDWATERDEEL = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIEONDERSTEUNENDWATERDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIEONDERSTEUNENDWATERDEEL = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIEONDERSTEUNENDWATERDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_PLUSTYPEONDERSTEUNENDWATERDEEL = "AAAAAAAAAA";
    private static final String UPDATED_PLUSTYPEONDERSTEUNENDWATERDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_RELATIEVEHOOGTELIGGINGONDERSTEUNENDWATERDEEL = "AAAAAAAAAA";
    private static final String UPDATED_RELATIEVEHOOGTELIGGINGONDERSTEUNENDWATERDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_STATUSONDERSTEUNENDWATERDEEL = "AAAAAAAAAA";
    private static final String UPDATED_STATUSONDERSTEUNENDWATERDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEONDERSTEUNENDWATERDEEL = "AAAAAAAAAA";
    private static final String UPDATED_TYPEONDERSTEUNENDWATERDEEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ondersteunendwaterdeels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OndersteunendwaterdeelRepository ondersteunendwaterdeelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOndersteunendwaterdeelMockMvc;

    private Ondersteunendwaterdeel ondersteunendwaterdeel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ondersteunendwaterdeel createEntity(EntityManager em) {
        Ondersteunendwaterdeel ondersteunendwaterdeel = new Ondersteunendwaterdeel()
            .datumbegingeldigheidondersteunendwaterdeel(DEFAULT_DATUMBEGINGELDIGHEIDONDERSTEUNENDWATERDEEL)
            .datumeindegeldigheidondersteunendwaterdeel(DEFAULT_DATUMEINDEGELDIGHEIDONDERSTEUNENDWATERDEEL)
            .geometrieondersteunendwaterdeel(DEFAULT_GEOMETRIEONDERSTEUNENDWATERDEEL)
            .identificatieondersteunendwaterdeel(DEFAULT_IDENTIFICATIEONDERSTEUNENDWATERDEEL)
            .plustypeondersteunendwaterdeel(DEFAULT_PLUSTYPEONDERSTEUNENDWATERDEEL)
            .relatievehoogteliggingondersteunendwaterdeel(DEFAULT_RELATIEVEHOOGTELIGGINGONDERSTEUNENDWATERDEEL)
            .statusondersteunendwaterdeel(DEFAULT_STATUSONDERSTEUNENDWATERDEEL)
            .typeondersteunendwaterdeel(DEFAULT_TYPEONDERSTEUNENDWATERDEEL);
        return ondersteunendwaterdeel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ondersteunendwaterdeel createUpdatedEntity(EntityManager em) {
        Ondersteunendwaterdeel ondersteunendwaterdeel = new Ondersteunendwaterdeel()
            .datumbegingeldigheidondersteunendwaterdeel(UPDATED_DATUMBEGINGELDIGHEIDONDERSTEUNENDWATERDEEL)
            .datumeindegeldigheidondersteunendwaterdeel(UPDATED_DATUMEINDEGELDIGHEIDONDERSTEUNENDWATERDEEL)
            .geometrieondersteunendwaterdeel(UPDATED_GEOMETRIEONDERSTEUNENDWATERDEEL)
            .identificatieondersteunendwaterdeel(UPDATED_IDENTIFICATIEONDERSTEUNENDWATERDEEL)
            .plustypeondersteunendwaterdeel(UPDATED_PLUSTYPEONDERSTEUNENDWATERDEEL)
            .relatievehoogteliggingondersteunendwaterdeel(UPDATED_RELATIEVEHOOGTELIGGINGONDERSTEUNENDWATERDEEL)
            .statusondersteunendwaterdeel(UPDATED_STATUSONDERSTEUNENDWATERDEEL)
            .typeondersteunendwaterdeel(UPDATED_TYPEONDERSTEUNENDWATERDEEL);
        return ondersteunendwaterdeel;
    }

    @BeforeEach
    public void initTest() {
        ondersteunendwaterdeel = createEntity(em);
    }

    @Test
    @Transactional
    void createOndersteunendwaterdeel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Ondersteunendwaterdeel
        var returnedOndersteunendwaterdeel = om.readValue(
            restOndersteunendwaterdeelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ondersteunendwaterdeel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Ondersteunendwaterdeel.class
        );

        // Validate the Ondersteunendwaterdeel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOndersteunendwaterdeelUpdatableFieldsEquals(
            returnedOndersteunendwaterdeel,
            getPersistedOndersteunendwaterdeel(returnedOndersteunendwaterdeel)
        );
    }

    @Test
    @Transactional
    void createOndersteunendwaterdeelWithExistingId() throws Exception {
        // Create the Ondersteunendwaterdeel with an existing ID
        ondersteunendwaterdeel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOndersteunendwaterdeelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ondersteunendwaterdeel)))
            .andExpect(status().isBadRequest());

        // Validate the Ondersteunendwaterdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOndersteunendwaterdeels() throws Exception {
        // Initialize the database
        ondersteunendwaterdeelRepository.saveAndFlush(ondersteunendwaterdeel);

        // Get all the ondersteunendwaterdeelList
        restOndersteunendwaterdeelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ondersteunendwaterdeel.getId().intValue())))
            .andExpect(
                jsonPath("$.[*].datumbegingeldigheidondersteunendwaterdeel").value(
                    hasItem(DEFAULT_DATUMBEGINGELDIGHEIDONDERSTEUNENDWATERDEEL.toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].datumeindegeldigheidondersteunendwaterdeel").value(
                    hasItem(DEFAULT_DATUMEINDEGELDIGHEIDONDERSTEUNENDWATERDEEL.toString())
                )
            )
            .andExpect(jsonPath("$.[*].geometrieondersteunendwaterdeel").value(hasItem(DEFAULT_GEOMETRIEONDERSTEUNENDWATERDEEL)))
            .andExpect(jsonPath("$.[*].identificatieondersteunendwaterdeel").value(hasItem(DEFAULT_IDENTIFICATIEONDERSTEUNENDWATERDEEL)))
            .andExpect(jsonPath("$.[*].plustypeondersteunendwaterdeel").value(hasItem(DEFAULT_PLUSTYPEONDERSTEUNENDWATERDEEL)))
            .andExpect(
                jsonPath("$.[*].relatievehoogteliggingondersteunendwaterdeel").value(
                    hasItem(DEFAULT_RELATIEVEHOOGTELIGGINGONDERSTEUNENDWATERDEEL)
                )
            )
            .andExpect(jsonPath("$.[*].statusondersteunendwaterdeel").value(hasItem(DEFAULT_STATUSONDERSTEUNENDWATERDEEL)))
            .andExpect(jsonPath("$.[*].typeondersteunendwaterdeel").value(hasItem(DEFAULT_TYPEONDERSTEUNENDWATERDEEL)));
    }

    @Test
    @Transactional
    void getOndersteunendwaterdeel() throws Exception {
        // Initialize the database
        ondersteunendwaterdeelRepository.saveAndFlush(ondersteunendwaterdeel);

        // Get the ondersteunendwaterdeel
        restOndersteunendwaterdeelMockMvc
            .perform(get(ENTITY_API_URL_ID, ondersteunendwaterdeel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ondersteunendwaterdeel.getId().intValue()))
            .andExpect(
                jsonPath("$.datumbegingeldigheidondersteunendwaterdeel").value(
                    DEFAULT_DATUMBEGINGELDIGHEIDONDERSTEUNENDWATERDEEL.toString()
                )
            )
            .andExpect(
                jsonPath("$.datumeindegeldigheidondersteunendwaterdeel").value(
                    DEFAULT_DATUMEINDEGELDIGHEIDONDERSTEUNENDWATERDEEL.toString()
                )
            )
            .andExpect(jsonPath("$.geometrieondersteunendwaterdeel").value(DEFAULT_GEOMETRIEONDERSTEUNENDWATERDEEL))
            .andExpect(jsonPath("$.identificatieondersteunendwaterdeel").value(DEFAULT_IDENTIFICATIEONDERSTEUNENDWATERDEEL))
            .andExpect(jsonPath("$.plustypeondersteunendwaterdeel").value(DEFAULT_PLUSTYPEONDERSTEUNENDWATERDEEL))
            .andExpect(
                jsonPath("$.relatievehoogteliggingondersteunendwaterdeel").value(DEFAULT_RELATIEVEHOOGTELIGGINGONDERSTEUNENDWATERDEEL)
            )
            .andExpect(jsonPath("$.statusondersteunendwaterdeel").value(DEFAULT_STATUSONDERSTEUNENDWATERDEEL))
            .andExpect(jsonPath("$.typeondersteunendwaterdeel").value(DEFAULT_TYPEONDERSTEUNENDWATERDEEL));
    }

    @Test
    @Transactional
    void getNonExistingOndersteunendwaterdeel() throws Exception {
        // Get the ondersteunendwaterdeel
        restOndersteunendwaterdeelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOndersteunendwaterdeel() throws Exception {
        // Initialize the database
        ondersteunendwaterdeelRepository.saveAndFlush(ondersteunendwaterdeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ondersteunendwaterdeel
        Ondersteunendwaterdeel updatedOndersteunendwaterdeel = ondersteunendwaterdeelRepository
            .findById(ondersteunendwaterdeel.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedOndersteunendwaterdeel are not directly saved in db
        em.detach(updatedOndersteunendwaterdeel);
        updatedOndersteunendwaterdeel
            .datumbegingeldigheidondersteunendwaterdeel(UPDATED_DATUMBEGINGELDIGHEIDONDERSTEUNENDWATERDEEL)
            .datumeindegeldigheidondersteunendwaterdeel(UPDATED_DATUMEINDEGELDIGHEIDONDERSTEUNENDWATERDEEL)
            .geometrieondersteunendwaterdeel(UPDATED_GEOMETRIEONDERSTEUNENDWATERDEEL)
            .identificatieondersteunendwaterdeel(UPDATED_IDENTIFICATIEONDERSTEUNENDWATERDEEL)
            .plustypeondersteunendwaterdeel(UPDATED_PLUSTYPEONDERSTEUNENDWATERDEEL)
            .relatievehoogteliggingondersteunendwaterdeel(UPDATED_RELATIEVEHOOGTELIGGINGONDERSTEUNENDWATERDEEL)
            .statusondersteunendwaterdeel(UPDATED_STATUSONDERSTEUNENDWATERDEEL)
            .typeondersteunendwaterdeel(UPDATED_TYPEONDERSTEUNENDWATERDEEL);

        restOndersteunendwaterdeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOndersteunendwaterdeel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOndersteunendwaterdeel))
            )
            .andExpect(status().isOk());

        // Validate the Ondersteunendwaterdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOndersteunendwaterdeelToMatchAllProperties(updatedOndersteunendwaterdeel);
    }

    @Test
    @Transactional
    void putNonExistingOndersteunendwaterdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ondersteunendwaterdeel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOndersteunendwaterdeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ondersteunendwaterdeel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ondersteunendwaterdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ondersteunendwaterdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOndersteunendwaterdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ondersteunendwaterdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOndersteunendwaterdeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ondersteunendwaterdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ondersteunendwaterdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOndersteunendwaterdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ondersteunendwaterdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOndersteunendwaterdeelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ondersteunendwaterdeel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ondersteunendwaterdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOndersteunendwaterdeelWithPatch() throws Exception {
        // Initialize the database
        ondersteunendwaterdeelRepository.saveAndFlush(ondersteunendwaterdeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ondersteunendwaterdeel using partial update
        Ondersteunendwaterdeel partialUpdatedOndersteunendwaterdeel = new Ondersteunendwaterdeel();
        partialUpdatedOndersteunendwaterdeel.setId(ondersteunendwaterdeel.getId());

        partialUpdatedOndersteunendwaterdeel
            .datumeindegeldigheidondersteunendwaterdeel(UPDATED_DATUMEINDEGELDIGHEIDONDERSTEUNENDWATERDEEL)
            .plustypeondersteunendwaterdeel(UPDATED_PLUSTYPEONDERSTEUNENDWATERDEEL)
            .relatievehoogteliggingondersteunendwaterdeel(UPDATED_RELATIEVEHOOGTELIGGINGONDERSTEUNENDWATERDEEL)
            .statusondersteunendwaterdeel(UPDATED_STATUSONDERSTEUNENDWATERDEEL)
            .typeondersteunendwaterdeel(UPDATED_TYPEONDERSTEUNENDWATERDEEL);

        restOndersteunendwaterdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOndersteunendwaterdeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOndersteunendwaterdeel))
            )
            .andExpect(status().isOk());

        // Validate the Ondersteunendwaterdeel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOndersteunendwaterdeelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOndersteunendwaterdeel, ondersteunendwaterdeel),
            getPersistedOndersteunendwaterdeel(ondersteunendwaterdeel)
        );
    }

    @Test
    @Transactional
    void fullUpdateOndersteunendwaterdeelWithPatch() throws Exception {
        // Initialize the database
        ondersteunendwaterdeelRepository.saveAndFlush(ondersteunendwaterdeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ondersteunendwaterdeel using partial update
        Ondersteunendwaterdeel partialUpdatedOndersteunendwaterdeel = new Ondersteunendwaterdeel();
        partialUpdatedOndersteunendwaterdeel.setId(ondersteunendwaterdeel.getId());

        partialUpdatedOndersteunendwaterdeel
            .datumbegingeldigheidondersteunendwaterdeel(UPDATED_DATUMBEGINGELDIGHEIDONDERSTEUNENDWATERDEEL)
            .datumeindegeldigheidondersteunendwaterdeel(UPDATED_DATUMEINDEGELDIGHEIDONDERSTEUNENDWATERDEEL)
            .geometrieondersteunendwaterdeel(UPDATED_GEOMETRIEONDERSTEUNENDWATERDEEL)
            .identificatieondersteunendwaterdeel(UPDATED_IDENTIFICATIEONDERSTEUNENDWATERDEEL)
            .plustypeondersteunendwaterdeel(UPDATED_PLUSTYPEONDERSTEUNENDWATERDEEL)
            .relatievehoogteliggingondersteunendwaterdeel(UPDATED_RELATIEVEHOOGTELIGGINGONDERSTEUNENDWATERDEEL)
            .statusondersteunendwaterdeel(UPDATED_STATUSONDERSTEUNENDWATERDEEL)
            .typeondersteunendwaterdeel(UPDATED_TYPEONDERSTEUNENDWATERDEEL);

        restOndersteunendwaterdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOndersteunendwaterdeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOndersteunendwaterdeel))
            )
            .andExpect(status().isOk());

        // Validate the Ondersteunendwaterdeel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOndersteunendwaterdeelUpdatableFieldsEquals(
            partialUpdatedOndersteunendwaterdeel,
            getPersistedOndersteunendwaterdeel(partialUpdatedOndersteunendwaterdeel)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOndersteunendwaterdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ondersteunendwaterdeel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOndersteunendwaterdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ondersteunendwaterdeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ondersteunendwaterdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ondersteunendwaterdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOndersteunendwaterdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ondersteunendwaterdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOndersteunendwaterdeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ondersteunendwaterdeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ondersteunendwaterdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOndersteunendwaterdeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ondersteunendwaterdeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOndersteunendwaterdeelMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ondersteunendwaterdeel))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ondersteunendwaterdeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOndersteunendwaterdeel() throws Exception {
        // Initialize the database
        ondersteunendwaterdeelRepository.saveAndFlush(ondersteunendwaterdeel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ondersteunendwaterdeel
        restOndersteunendwaterdeelMockMvc
            .perform(delete(ENTITY_API_URL_ID, ondersteunendwaterdeel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ondersteunendwaterdeelRepository.count();
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

    protected Ondersteunendwaterdeel getPersistedOndersteunendwaterdeel(Ondersteunendwaterdeel ondersteunendwaterdeel) {
        return ondersteunendwaterdeelRepository.findById(ondersteunendwaterdeel.getId()).orElseThrow();
    }

    protected void assertPersistedOndersteunendwaterdeelToMatchAllProperties(Ondersteunendwaterdeel expectedOndersteunendwaterdeel) {
        assertOndersteunendwaterdeelAllPropertiesEquals(
            expectedOndersteunendwaterdeel,
            getPersistedOndersteunendwaterdeel(expectedOndersteunendwaterdeel)
        );
    }

    protected void assertPersistedOndersteunendwaterdeelToMatchUpdatableProperties(Ondersteunendwaterdeel expectedOndersteunendwaterdeel) {
        assertOndersteunendwaterdeelAllUpdatablePropertiesEquals(
            expectedOndersteunendwaterdeel,
            getPersistedOndersteunendwaterdeel(expectedOndersteunendwaterdeel)
        );
    }
}
