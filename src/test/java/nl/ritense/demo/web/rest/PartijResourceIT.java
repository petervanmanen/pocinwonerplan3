package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.PartijAsserts.*;
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
import nl.ritense.demo.domain.Partij;
import nl.ritense.demo.repository.PartijRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PartijResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PartijResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMAANVANGGELDIGHEIDPARTIJ = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMAANVANGGELDIGHEIDPARTIJ = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDPARTIJ = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDPARTIJ = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_SOORT = "AAAAAAAAAA";
    private static final String UPDATED_SOORT = "BBBBBBBBBB";

    private static final String DEFAULT_VERSTREKKINGSBEPERKINGMOGELIJK = "AAAAAAAAAA";
    private static final String UPDATED_VERSTREKKINGSBEPERKINGMOGELIJK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/partijs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PartijRepository partijRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartijMockMvc;

    private Partij partij;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Partij createEntity(EntityManager em) {
        Partij partij = new Partij()
            .code(DEFAULT_CODE)
            .datumaanvanggeldigheidpartij(DEFAULT_DATUMAANVANGGELDIGHEIDPARTIJ)
            .datumeindegeldigheidpartij(DEFAULT_DATUMEINDEGELDIGHEIDPARTIJ)
            .naam(DEFAULT_NAAM)
            .soort(DEFAULT_SOORT)
            .verstrekkingsbeperkingmogelijk(DEFAULT_VERSTREKKINGSBEPERKINGMOGELIJK);
        return partij;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Partij createUpdatedEntity(EntityManager em) {
        Partij partij = new Partij()
            .code(UPDATED_CODE)
            .datumaanvanggeldigheidpartij(UPDATED_DATUMAANVANGGELDIGHEIDPARTIJ)
            .datumeindegeldigheidpartij(UPDATED_DATUMEINDEGELDIGHEIDPARTIJ)
            .naam(UPDATED_NAAM)
            .soort(UPDATED_SOORT)
            .verstrekkingsbeperkingmogelijk(UPDATED_VERSTREKKINGSBEPERKINGMOGELIJK);
        return partij;
    }

    @BeforeEach
    public void initTest() {
        partij = createEntity(em);
    }

    @Test
    @Transactional
    void createPartij() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Partij
        var returnedPartij = om.readValue(
            restPartijMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(partij)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Partij.class
        );

        // Validate the Partij in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPartijUpdatableFieldsEquals(returnedPartij, getPersistedPartij(returnedPartij));
    }

    @Test
    @Transactional
    void createPartijWithExistingId() throws Exception {
        // Create the Partij with an existing ID
        partij.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartijMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(partij)))
            .andExpect(status().isBadRequest());

        // Validate the Partij in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPartijs() throws Exception {
        // Initialize the database
        partijRepository.saveAndFlush(partij);

        // Get all the partijList
        restPartijMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partij.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].datumaanvanggeldigheidpartij").value(hasItem(DEFAULT_DATUMAANVANGGELDIGHEIDPARTIJ.toString())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheidpartij").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEIDPARTIJ.toString())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].soort").value(hasItem(DEFAULT_SOORT)))
            .andExpect(jsonPath("$.[*].verstrekkingsbeperkingmogelijk").value(hasItem(DEFAULT_VERSTREKKINGSBEPERKINGMOGELIJK)));
    }

    @Test
    @Transactional
    void getPartij() throws Exception {
        // Initialize the database
        partijRepository.saveAndFlush(partij);

        // Get the partij
        restPartijMockMvc
            .perform(get(ENTITY_API_URL_ID, partij.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partij.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.datumaanvanggeldigheidpartij").value(DEFAULT_DATUMAANVANGGELDIGHEIDPARTIJ.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheidpartij").value(DEFAULT_DATUMEINDEGELDIGHEIDPARTIJ.toString()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.soort").value(DEFAULT_SOORT))
            .andExpect(jsonPath("$.verstrekkingsbeperkingmogelijk").value(DEFAULT_VERSTREKKINGSBEPERKINGMOGELIJK));
    }

    @Test
    @Transactional
    void getNonExistingPartij() throws Exception {
        // Get the partij
        restPartijMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPartij() throws Exception {
        // Initialize the database
        partijRepository.saveAndFlush(partij);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the partij
        Partij updatedPartij = partijRepository.findById(partij.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPartij are not directly saved in db
        em.detach(updatedPartij);
        updatedPartij
            .code(UPDATED_CODE)
            .datumaanvanggeldigheidpartij(UPDATED_DATUMAANVANGGELDIGHEIDPARTIJ)
            .datumeindegeldigheidpartij(UPDATED_DATUMEINDEGELDIGHEIDPARTIJ)
            .naam(UPDATED_NAAM)
            .soort(UPDATED_SOORT)
            .verstrekkingsbeperkingmogelijk(UPDATED_VERSTREKKINGSBEPERKINGMOGELIJK);

        restPartijMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPartij.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPartij))
            )
            .andExpect(status().isOk());

        // Validate the Partij in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPartijToMatchAllProperties(updatedPartij);
    }

    @Test
    @Transactional
    void putNonExistingPartij() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partij.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartijMockMvc
            .perform(put(ENTITY_API_URL_ID, partij.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(partij)))
            .andExpect(status().isBadRequest());

        // Validate the Partij in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPartij() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partij.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartijMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(partij))
            )
            .andExpect(status().isBadRequest());

        // Validate the Partij in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPartij() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partij.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartijMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(partij)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Partij in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePartijWithPatch() throws Exception {
        // Initialize the database
        partijRepository.saveAndFlush(partij);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the partij using partial update
        Partij partialUpdatedPartij = new Partij();
        partialUpdatedPartij.setId(partij.getId());

        partialUpdatedPartij
            .code(UPDATED_CODE)
            .datumaanvanggeldigheidpartij(UPDATED_DATUMAANVANGGELDIGHEIDPARTIJ)
            .datumeindegeldigheidpartij(UPDATED_DATUMEINDEGELDIGHEIDPARTIJ)
            .naam(UPDATED_NAAM);

        restPartijMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartij.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPartij))
            )
            .andExpect(status().isOk());

        // Validate the Partij in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPartijUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPartij, partij), getPersistedPartij(partij));
    }

    @Test
    @Transactional
    void fullUpdatePartijWithPatch() throws Exception {
        // Initialize the database
        partijRepository.saveAndFlush(partij);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the partij using partial update
        Partij partialUpdatedPartij = new Partij();
        partialUpdatedPartij.setId(partij.getId());

        partialUpdatedPartij
            .code(UPDATED_CODE)
            .datumaanvanggeldigheidpartij(UPDATED_DATUMAANVANGGELDIGHEIDPARTIJ)
            .datumeindegeldigheidpartij(UPDATED_DATUMEINDEGELDIGHEIDPARTIJ)
            .naam(UPDATED_NAAM)
            .soort(UPDATED_SOORT)
            .verstrekkingsbeperkingmogelijk(UPDATED_VERSTREKKINGSBEPERKINGMOGELIJK);

        restPartijMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartij.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPartij))
            )
            .andExpect(status().isOk());

        // Validate the Partij in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPartijUpdatableFieldsEquals(partialUpdatedPartij, getPersistedPartij(partialUpdatedPartij));
    }

    @Test
    @Transactional
    void patchNonExistingPartij() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partij.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartijMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partij.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(partij))
            )
            .andExpect(status().isBadRequest());

        // Validate the Partij in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPartij() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partij.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartijMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partij))
            )
            .andExpect(status().isBadRequest());

        // Validate the Partij in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPartij() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partij.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartijMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(partij)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Partij in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePartij() throws Exception {
        // Initialize the database
        partijRepository.saveAndFlush(partij);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the partij
        restPartijMockMvc
            .perform(delete(ENTITY_API_URL_ID, partij.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return partijRepository.count();
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

    protected Partij getPersistedPartij(Partij partij) {
        return partijRepository.findById(partij.getId()).orElseThrow();
    }

    protected void assertPersistedPartijToMatchAllProperties(Partij expectedPartij) {
        assertPartijAllPropertiesEquals(expectedPartij, getPersistedPartij(expectedPartij));
    }

    protected void assertPersistedPartijToMatchUpdatableProperties(Partij expectedPartij) {
        assertPartijAllUpdatablePropertiesEquals(expectedPartij, getPersistedPartij(expectedPartij));
    }
}
