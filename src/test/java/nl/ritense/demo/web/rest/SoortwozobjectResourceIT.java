package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SoortwozobjectAsserts.*;
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
import nl.ritense.demo.domain.Soortwozobject;
import nl.ritense.demo.repository.SoortwozobjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SoortwozobjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SoortwozobjectResourceIT {

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDSOORTOBJECTCODE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDSOORTOBJECTCODE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDSOORTOBJECTCODE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDSOORTOBJECTCODE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NAAMSOORTOBJECTCODE = "AAAAAAAAAA";
    private static final String UPDATED_NAAMSOORTOBJECTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_OPMERKINGENSOORTOBJECTCODE = "AAAAAAAAAA";
    private static final String UPDATED_OPMERKINGENSOORTOBJECTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_SOORTOBJECTCODE = "AAAAAAAAAA";
    private static final String UPDATED_SOORTOBJECTCODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/soortwozobjects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SoortwozobjectRepository soortwozobjectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSoortwozobjectMockMvc;

    private Soortwozobject soortwozobject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Soortwozobject createEntity(EntityManager em) {
        Soortwozobject soortwozobject = new Soortwozobject()
            .datumbegingeldigheidsoortobjectcode(DEFAULT_DATUMBEGINGELDIGHEIDSOORTOBJECTCODE)
            .datumeindegeldigheidsoortobjectcode(DEFAULT_DATUMEINDEGELDIGHEIDSOORTOBJECTCODE)
            .naamsoortobjectcode(DEFAULT_NAAMSOORTOBJECTCODE)
            .opmerkingensoortobjectcode(DEFAULT_OPMERKINGENSOORTOBJECTCODE)
            .soortobjectcode(DEFAULT_SOORTOBJECTCODE);
        return soortwozobject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Soortwozobject createUpdatedEntity(EntityManager em) {
        Soortwozobject soortwozobject = new Soortwozobject()
            .datumbegingeldigheidsoortobjectcode(UPDATED_DATUMBEGINGELDIGHEIDSOORTOBJECTCODE)
            .datumeindegeldigheidsoortobjectcode(UPDATED_DATUMEINDEGELDIGHEIDSOORTOBJECTCODE)
            .naamsoortobjectcode(UPDATED_NAAMSOORTOBJECTCODE)
            .opmerkingensoortobjectcode(UPDATED_OPMERKINGENSOORTOBJECTCODE)
            .soortobjectcode(UPDATED_SOORTOBJECTCODE);
        return soortwozobject;
    }

    @BeforeEach
    public void initTest() {
        soortwozobject = createEntity(em);
    }

    @Test
    @Transactional
    void createSoortwozobject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Soortwozobject
        var returnedSoortwozobject = om.readValue(
            restSoortwozobjectMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(soortwozobject)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Soortwozobject.class
        );

        // Validate the Soortwozobject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSoortwozobjectUpdatableFieldsEquals(returnedSoortwozobject, getPersistedSoortwozobject(returnedSoortwozobject));
    }

    @Test
    @Transactional
    void createSoortwozobjectWithExistingId() throws Exception {
        // Create the Soortwozobject with an existing ID
        soortwozobject.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSoortwozobjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(soortwozobject)))
            .andExpect(status().isBadRequest());

        // Validate the Soortwozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSoortwozobjects() throws Exception {
        // Initialize the database
        soortwozobjectRepository.saveAndFlush(soortwozobject);

        // Get all the soortwozobjectList
        restSoortwozobjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(soortwozobject.getId().intValue())))
            .andExpect(
                jsonPath("$.[*].datumbegingeldigheidsoortobjectcode").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEIDSOORTOBJECTCODE.toString()))
            )
            .andExpect(
                jsonPath("$.[*].datumeindegeldigheidsoortobjectcode").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEIDSOORTOBJECTCODE.toString()))
            )
            .andExpect(jsonPath("$.[*].naamsoortobjectcode").value(hasItem(DEFAULT_NAAMSOORTOBJECTCODE)))
            .andExpect(jsonPath("$.[*].opmerkingensoortobjectcode").value(hasItem(DEFAULT_OPMERKINGENSOORTOBJECTCODE)))
            .andExpect(jsonPath("$.[*].soortobjectcode").value(hasItem(DEFAULT_SOORTOBJECTCODE)));
    }

    @Test
    @Transactional
    void getSoortwozobject() throws Exception {
        // Initialize the database
        soortwozobjectRepository.saveAndFlush(soortwozobject);

        // Get the soortwozobject
        restSoortwozobjectMockMvc
            .perform(get(ENTITY_API_URL_ID, soortwozobject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(soortwozobject.getId().intValue()))
            .andExpect(jsonPath("$.datumbegingeldigheidsoortobjectcode").value(DEFAULT_DATUMBEGINGELDIGHEIDSOORTOBJECTCODE.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheidsoortobjectcode").value(DEFAULT_DATUMEINDEGELDIGHEIDSOORTOBJECTCODE.toString()))
            .andExpect(jsonPath("$.naamsoortobjectcode").value(DEFAULT_NAAMSOORTOBJECTCODE))
            .andExpect(jsonPath("$.opmerkingensoortobjectcode").value(DEFAULT_OPMERKINGENSOORTOBJECTCODE))
            .andExpect(jsonPath("$.soortobjectcode").value(DEFAULT_SOORTOBJECTCODE));
    }

    @Test
    @Transactional
    void getNonExistingSoortwozobject() throws Exception {
        // Get the soortwozobject
        restSoortwozobjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSoortwozobject() throws Exception {
        // Initialize the database
        soortwozobjectRepository.saveAndFlush(soortwozobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the soortwozobject
        Soortwozobject updatedSoortwozobject = soortwozobjectRepository.findById(soortwozobject.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSoortwozobject are not directly saved in db
        em.detach(updatedSoortwozobject);
        updatedSoortwozobject
            .datumbegingeldigheidsoortobjectcode(UPDATED_DATUMBEGINGELDIGHEIDSOORTOBJECTCODE)
            .datumeindegeldigheidsoortobjectcode(UPDATED_DATUMEINDEGELDIGHEIDSOORTOBJECTCODE)
            .naamsoortobjectcode(UPDATED_NAAMSOORTOBJECTCODE)
            .opmerkingensoortobjectcode(UPDATED_OPMERKINGENSOORTOBJECTCODE)
            .soortobjectcode(UPDATED_SOORTOBJECTCODE);

        restSoortwozobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSoortwozobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSoortwozobject))
            )
            .andExpect(status().isOk());

        // Validate the Soortwozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSoortwozobjectToMatchAllProperties(updatedSoortwozobject);
    }

    @Test
    @Transactional
    void putNonExistingSoortwozobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortwozobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoortwozobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, soortwozobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(soortwozobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortwozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSoortwozobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortwozobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortwozobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(soortwozobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortwozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSoortwozobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortwozobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortwozobjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(soortwozobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Soortwozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSoortwozobjectWithPatch() throws Exception {
        // Initialize the database
        soortwozobjectRepository.saveAndFlush(soortwozobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the soortwozobject using partial update
        Soortwozobject partialUpdatedSoortwozobject = new Soortwozobject();
        partialUpdatedSoortwozobject.setId(soortwozobject.getId());

        partialUpdatedSoortwozobject
            .datumbegingeldigheidsoortobjectcode(UPDATED_DATUMBEGINGELDIGHEIDSOORTOBJECTCODE)
            .naamsoortobjectcode(UPDATED_NAAMSOORTOBJECTCODE)
            .opmerkingensoortobjectcode(UPDATED_OPMERKINGENSOORTOBJECTCODE);

        restSoortwozobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoortwozobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSoortwozobject))
            )
            .andExpect(status().isOk());

        // Validate the Soortwozobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSoortwozobjectUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSoortwozobject, soortwozobject),
            getPersistedSoortwozobject(soortwozobject)
        );
    }

    @Test
    @Transactional
    void fullUpdateSoortwozobjectWithPatch() throws Exception {
        // Initialize the database
        soortwozobjectRepository.saveAndFlush(soortwozobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the soortwozobject using partial update
        Soortwozobject partialUpdatedSoortwozobject = new Soortwozobject();
        partialUpdatedSoortwozobject.setId(soortwozobject.getId());

        partialUpdatedSoortwozobject
            .datumbegingeldigheidsoortobjectcode(UPDATED_DATUMBEGINGELDIGHEIDSOORTOBJECTCODE)
            .datumeindegeldigheidsoortobjectcode(UPDATED_DATUMEINDEGELDIGHEIDSOORTOBJECTCODE)
            .naamsoortobjectcode(UPDATED_NAAMSOORTOBJECTCODE)
            .opmerkingensoortobjectcode(UPDATED_OPMERKINGENSOORTOBJECTCODE)
            .soortobjectcode(UPDATED_SOORTOBJECTCODE);

        restSoortwozobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoortwozobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSoortwozobject))
            )
            .andExpect(status().isOk());

        // Validate the Soortwozobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSoortwozobjectUpdatableFieldsEquals(partialUpdatedSoortwozobject, getPersistedSoortwozobject(partialUpdatedSoortwozobject));
    }

    @Test
    @Transactional
    void patchNonExistingSoortwozobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortwozobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoortwozobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, soortwozobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(soortwozobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortwozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSoortwozobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortwozobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortwozobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(soortwozobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soortwozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSoortwozobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        soortwozobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoortwozobjectMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(soortwozobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Soortwozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSoortwozobject() throws Exception {
        // Initialize the database
        soortwozobjectRepository.saveAndFlush(soortwozobject);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the soortwozobject
        restSoortwozobjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, soortwozobject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return soortwozobjectRepository.count();
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

    protected Soortwozobject getPersistedSoortwozobject(Soortwozobject soortwozobject) {
        return soortwozobjectRepository.findById(soortwozobject.getId()).orElseThrow();
    }

    protected void assertPersistedSoortwozobjectToMatchAllProperties(Soortwozobject expectedSoortwozobject) {
        assertSoortwozobjectAllPropertiesEquals(expectedSoortwozobject, getPersistedSoortwozobject(expectedSoortwozobject));
    }

    protected void assertPersistedSoortwozobjectToMatchUpdatableProperties(Soortwozobject expectedSoortwozobject) {
        assertSoortwozobjectAllUpdatablePropertiesEquals(expectedSoortwozobject, getPersistedSoortwozobject(expectedSoortwozobject));
    }
}
