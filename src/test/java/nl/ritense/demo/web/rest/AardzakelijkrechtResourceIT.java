package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AardzakelijkrechtAsserts.*;
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
import nl.ritense.demo.domain.Aardzakelijkrecht;
import nl.ritense.demo.repository.AardzakelijkrechtRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AardzakelijkrechtResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AardzakelijkrechtResourceIT {

    private static final String DEFAULT_CODEAARDZAKELIJKRECHT = "AAAAAAAAAA";
    private static final String UPDATED_CODEAARDZAKELIJKRECHT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDAARDZAKELIJKRECHT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDAARDZAKELIJKRECHT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDAARDZAKELIJKRECHT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDAARDZAKELIJKRECHT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NAAMAARDZAKELIJKRECHT = "AAAAAAAAAA";
    private static final String UPDATED_NAAMAARDZAKELIJKRECHT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/aardzakelijkrechts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AardzakelijkrechtRepository aardzakelijkrechtRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAardzakelijkrechtMockMvc;

    private Aardzakelijkrecht aardzakelijkrecht;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aardzakelijkrecht createEntity(EntityManager em) {
        Aardzakelijkrecht aardzakelijkrecht = new Aardzakelijkrecht()
            .codeaardzakelijkrecht(DEFAULT_CODEAARDZAKELIJKRECHT)
            .datumbegingeldigheidaardzakelijkrecht(DEFAULT_DATUMBEGINGELDIGHEIDAARDZAKELIJKRECHT)
            .datumeindegeldigheidaardzakelijkrecht(DEFAULT_DATUMEINDEGELDIGHEIDAARDZAKELIJKRECHT)
            .naamaardzakelijkrecht(DEFAULT_NAAMAARDZAKELIJKRECHT);
        return aardzakelijkrecht;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aardzakelijkrecht createUpdatedEntity(EntityManager em) {
        Aardzakelijkrecht aardzakelijkrecht = new Aardzakelijkrecht()
            .codeaardzakelijkrecht(UPDATED_CODEAARDZAKELIJKRECHT)
            .datumbegingeldigheidaardzakelijkrecht(UPDATED_DATUMBEGINGELDIGHEIDAARDZAKELIJKRECHT)
            .datumeindegeldigheidaardzakelijkrecht(UPDATED_DATUMEINDEGELDIGHEIDAARDZAKELIJKRECHT)
            .naamaardzakelijkrecht(UPDATED_NAAMAARDZAKELIJKRECHT);
        return aardzakelijkrecht;
    }

    @BeforeEach
    public void initTest() {
        aardzakelijkrecht = createEntity(em);
    }

    @Test
    @Transactional
    void createAardzakelijkrecht() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Aardzakelijkrecht
        var returnedAardzakelijkrecht = om.readValue(
            restAardzakelijkrechtMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aardzakelijkrecht)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Aardzakelijkrecht.class
        );

        // Validate the Aardzakelijkrecht in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAardzakelijkrechtUpdatableFieldsEquals(returnedAardzakelijkrecht, getPersistedAardzakelijkrecht(returnedAardzakelijkrecht));
    }

    @Test
    @Transactional
    void createAardzakelijkrechtWithExistingId() throws Exception {
        // Create the Aardzakelijkrecht with an existing ID
        aardzakelijkrecht.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAardzakelijkrechtMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aardzakelijkrecht)))
            .andExpect(status().isBadRequest());

        // Validate the Aardzakelijkrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAardzakelijkrechts() throws Exception {
        // Initialize the database
        aardzakelijkrechtRepository.saveAndFlush(aardzakelijkrecht);

        // Get all the aardzakelijkrechtList
        restAardzakelijkrechtMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aardzakelijkrecht.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeaardzakelijkrecht").value(hasItem(DEFAULT_CODEAARDZAKELIJKRECHT)))
            .andExpect(
                jsonPath("$.[*].datumbegingeldigheidaardzakelijkrecht").value(
                    hasItem(DEFAULT_DATUMBEGINGELDIGHEIDAARDZAKELIJKRECHT.toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].datumeindegeldigheidaardzakelijkrecht").value(
                    hasItem(DEFAULT_DATUMEINDEGELDIGHEIDAARDZAKELIJKRECHT.toString())
                )
            )
            .andExpect(jsonPath("$.[*].naamaardzakelijkrecht").value(hasItem(DEFAULT_NAAMAARDZAKELIJKRECHT)));
    }

    @Test
    @Transactional
    void getAardzakelijkrecht() throws Exception {
        // Initialize the database
        aardzakelijkrechtRepository.saveAndFlush(aardzakelijkrecht);

        // Get the aardzakelijkrecht
        restAardzakelijkrechtMockMvc
            .perform(get(ENTITY_API_URL_ID, aardzakelijkrecht.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aardzakelijkrecht.getId().intValue()))
            .andExpect(jsonPath("$.codeaardzakelijkrecht").value(DEFAULT_CODEAARDZAKELIJKRECHT))
            .andExpect(jsonPath("$.datumbegingeldigheidaardzakelijkrecht").value(DEFAULT_DATUMBEGINGELDIGHEIDAARDZAKELIJKRECHT.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheidaardzakelijkrecht").value(DEFAULT_DATUMEINDEGELDIGHEIDAARDZAKELIJKRECHT.toString()))
            .andExpect(jsonPath("$.naamaardzakelijkrecht").value(DEFAULT_NAAMAARDZAKELIJKRECHT));
    }

    @Test
    @Transactional
    void getNonExistingAardzakelijkrecht() throws Exception {
        // Get the aardzakelijkrecht
        restAardzakelijkrechtMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAardzakelijkrecht() throws Exception {
        // Initialize the database
        aardzakelijkrechtRepository.saveAndFlush(aardzakelijkrecht);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aardzakelijkrecht
        Aardzakelijkrecht updatedAardzakelijkrecht = aardzakelijkrechtRepository.findById(aardzakelijkrecht.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAardzakelijkrecht are not directly saved in db
        em.detach(updatedAardzakelijkrecht);
        updatedAardzakelijkrecht
            .codeaardzakelijkrecht(UPDATED_CODEAARDZAKELIJKRECHT)
            .datumbegingeldigheidaardzakelijkrecht(UPDATED_DATUMBEGINGELDIGHEIDAARDZAKELIJKRECHT)
            .datumeindegeldigheidaardzakelijkrecht(UPDATED_DATUMEINDEGELDIGHEIDAARDZAKELIJKRECHT)
            .naamaardzakelijkrecht(UPDATED_NAAMAARDZAKELIJKRECHT);

        restAardzakelijkrechtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAardzakelijkrecht.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAardzakelijkrecht))
            )
            .andExpect(status().isOk());

        // Validate the Aardzakelijkrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAardzakelijkrechtToMatchAllProperties(updatedAardzakelijkrecht);
    }

    @Test
    @Transactional
    void putNonExistingAardzakelijkrecht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aardzakelijkrecht.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAardzakelijkrechtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aardzakelijkrecht.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aardzakelijkrecht))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aardzakelijkrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAardzakelijkrecht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aardzakelijkrecht.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAardzakelijkrechtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aardzakelijkrecht))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aardzakelijkrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAardzakelijkrecht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aardzakelijkrecht.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAardzakelijkrechtMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aardzakelijkrecht)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aardzakelijkrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAardzakelijkrechtWithPatch() throws Exception {
        // Initialize the database
        aardzakelijkrechtRepository.saveAndFlush(aardzakelijkrecht);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aardzakelijkrecht using partial update
        Aardzakelijkrecht partialUpdatedAardzakelijkrecht = new Aardzakelijkrecht();
        partialUpdatedAardzakelijkrecht.setId(aardzakelijkrecht.getId());

        partialUpdatedAardzakelijkrecht.datumbegingeldigheidaardzakelijkrecht(UPDATED_DATUMBEGINGELDIGHEIDAARDZAKELIJKRECHT);

        restAardzakelijkrechtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAardzakelijkrecht.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAardzakelijkrecht))
            )
            .andExpect(status().isOk());

        // Validate the Aardzakelijkrecht in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAardzakelijkrechtUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAardzakelijkrecht, aardzakelijkrecht),
            getPersistedAardzakelijkrecht(aardzakelijkrecht)
        );
    }

    @Test
    @Transactional
    void fullUpdateAardzakelijkrechtWithPatch() throws Exception {
        // Initialize the database
        aardzakelijkrechtRepository.saveAndFlush(aardzakelijkrecht);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aardzakelijkrecht using partial update
        Aardzakelijkrecht partialUpdatedAardzakelijkrecht = new Aardzakelijkrecht();
        partialUpdatedAardzakelijkrecht.setId(aardzakelijkrecht.getId());

        partialUpdatedAardzakelijkrecht
            .codeaardzakelijkrecht(UPDATED_CODEAARDZAKELIJKRECHT)
            .datumbegingeldigheidaardzakelijkrecht(UPDATED_DATUMBEGINGELDIGHEIDAARDZAKELIJKRECHT)
            .datumeindegeldigheidaardzakelijkrecht(UPDATED_DATUMEINDEGELDIGHEIDAARDZAKELIJKRECHT)
            .naamaardzakelijkrecht(UPDATED_NAAMAARDZAKELIJKRECHT);

        restAardzakelijkrechtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAardzakelijkrecht.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAardzakelijkrecht))
            )
            .andExpect(status().isOk());

        // Validate the Aardzakelijkrecht in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAardzakelijkrechtUpdatableFieldsEquals(
            partialUpdatedAardzakelijkrecht,
            getPersistedAardzakelijkrecht(partialUpdatedAardzakelijkrecht)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAardzakelijkrecht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aardzakelijkrecht.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAardzakelijkrechtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aardzakelijkrecht.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aardzakelijkrecht))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aardzakelijkrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAardzakelijkrecht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aardzakelijkrecht.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAardzakelijkrechtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aardzakelijkrecht))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aardzakelijkrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAardzakelijkrecht() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aardzakelijkrecht.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAardzakelijkrechtMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(aardzakelijkrecht)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aardzakelijkrecht in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAardzakelijkrecht() throws Exception {
        // Initialize the database
        aardzakelijkrechtRepository.saveAndFlush(aardzakelijkrecht);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the aardzakelijkrecht
        restAardzakelijkrechtMockMvc
            .perform(delete(ENTITY_API_URL_ID, aardzakelijkrecht.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return aardzakelijkrechtRepository.count();
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

    protected Aardzakelijkrecht getPersistedAardzakelijkrecht(Aardzakelijkrecht aardzakelijkrecht) {
        return aardzakelijkrechtRepository.findById(aardzakelijkrecht.getId()).orElseThrow();
    }

    protected void assertPersistedAardzakelijkrechtToMatchAllProperties(Aardzakelijkrecht expectedAardzakelijkrecht) {
        assertAardzakelijkrechtAllPropertiesEquals(expectedAardzakelijkrecht, getPersistedAardzakelijkrecht(expectedAardzakelijkrecht));
    }

    protected void assertPersistedAardzakelijkrechtToMatchUpdatableProperties(Aardzakelijkrecht expectedAardzakelijkrecht) {
        assertAardzakelijkrechtAllUpdatablePropertiesEquals(
            expectedAardzakelijkrecht,
            getPersistedAardzakelijkrecht(expectedAardzakelijkrecht)
        );
    }
}
