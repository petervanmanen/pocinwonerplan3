package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.StudentenwoningenAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static nl.ritense.demo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Studentenwoningen;
import nl.ritense.demo.repository.StudentenwoningenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StudentenwoningenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StudentenwoningenResourceIT {

    private static final BigDecimal DEFAULT_HUURPRIJS = new BigDecimal(1);
    private static final BigDecimal UPDATED_HUURPRIJS = new BigDecimal(2);

    private static final Boolean DEFAULT_ZELFSTANDIG = false;
    private static final Boolean UPDATED_ZELFSTANDIG = true;

    private static final String ENTITY_API_URL = "/api/studentenwoningens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StudentenwoningenRepository studentenwoningenRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStudentenwoningenMockMvc;

    private Studentenwoningen studentenwoningen;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Studentenwoningen createEntity(EntityManager em) {
        Studentenwoningen studentenwoningen = new Studentenwoningen().huurprijs(DEFAULT_HUURPRIJS).zelfstandig(DEFAULT_ZELFSTANDIG);
        return studentenwoningen;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Studentenwoningen createUpdatedEntity(EntityManager em) {
        Studentenwoningen studentenwoningen = new Studentenwoningen().huurprijs(UPDATED_HUURPRIJS).zelfstandig(UPDATED_ZELFSTANDIG);
        return studentenwoningen;
    }

    @BeforeEach
    public void initTest() {
        studentenwoningen = createEntity(em);
    }

    @Test
    @Transactional
    void createStudentenwoningen() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Studentenwoningen
        var returnedStudentenwoningen = om.readValue(
            restStudentenwoningenMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(studentenwoningen)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Studentenwoningen.class
        );

        // Validate the Studentenwoningen in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertStudentenwoningenUpdatableFieldsEquals(returnedStudentenwoningen, getPersistedStudentenwoningen(returnedStudentenwoningen));
    }

    @Test
    @Transactional
    void createStudentenwoningenWithExistingId() throws Exception {
        // Create the Studentenwoningen with an existing ID
        studentenwoningen.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentenwoningenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(studentenwoningen)))
            .andExpect(status().isBadRequest());

        // Validate the Studentenwoningen in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStudentenwoningens() throws Exception {
        // Initialize the database
        studentenwoningenRepository.saveAndFlush(studentenwoningen);

        // Get all the studentenwoningenList
        restStudentenwoningenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentenwoningen.getId().intValue())))
            .andExpect(jsonPath("$.[*].huurprijs").value(hasItem(sameNumber(DEFAULT_HUURPRIJS))))
            .andExpect(jsonPath("$.[*].zelfstandig").value(hasItem(DEFAULT_ZELFSTANDIG.booleanValue())));
    }

    @Test
    @Transactional
    void getStudentenwoningen() throws Exception {
        // Initialize the database
        studentenwoningenRepository.saveAndFlush(studentenwoningen);

        // Get the studentenwoningen
        restStudentenwoningenMockMvc
            .perform(get(ENTITY_API_URL_ID, studentenwoningen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(studentenwoningen.getId().intValue()))
            .andExpect(jsonPath("$.huurprijs").value(sameNumber(DEFAULT_HUURPRIJS)))
            .andExpect(jsonPath("$.zelfstandig").value(DEFAULT_ZELFSTANDIG.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingStudentenwoningen() throws Exception {
        // Get the studentenwoningen
        restStudentenwoningenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStudentenwoningen() throws Exception {
        // Initialize the database
        studentenwoningenRepository.saveAndFlush(studentenwoningen);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the studentenwoningen
        Studentenwoningen updatedStudentenwoningen = studentenwoningenRepository.findById(studentenwoningen.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStudentenwoningen are not directly saved in db
        em.detach(updatedStudentenwoningen);
        updatedStudentenwoningen.huurprijs(UPDATED_HUURPRIJS).zelfstandig(UPDATED_ZELFSTANDIG);

        restStudentenwoningenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStudentenwoningen.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedStudentenwoningen))
            )
            .andExpect(status().isOk());

        // Validate the Studentenwoningen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStudentenwoningenToMatchAllProperties(updatedStudentenwoningen);
    }

    @Test
    @Transactional
    void putNonExistingStudentenwoningen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        studentenwoningen.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentenwoningenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, studentenwoningen.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(studentenwoningen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Studentenwoningen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStudentenwoningen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        studentenwoningen.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentenwoningenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(studentenwoningen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Studentenwoningen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStudentenwoningen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        studentenwoningen.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentenwoningenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(studentenwoningen)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Studentenwoningen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStudentenwoningenWithPatch() throws Exception {
        // Initialize the database
        studentenwoningenRepository.saveAndFlush(studentenwoningen);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the studentenwoningen using partial update
        Studentenwoningen partialUpdatedStudentenwoningen = new Studentenwoningen();
        partialUpdatedStudentenwoningen.setId(studentenwoningen.getId());

        partialUpdatedStudentenwoningen.huurprijs(UPDATED_HUURPRIJS);

        restStudentenwoningenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStudentenwoningen.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStudentenwoningen))
            )
            .andExpect(status().isOk());

        // Validate the Studentenwoningen in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStudentenwoningenUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedStudentenwoningen, studentenwoningen),
            getPersistedStudentenwoningen(studentenwoningen)
        );
    }

    @Test
    @Transactional
    void fullUpdateStudentenwoningenWithPatch() throws Exception {
        // Initialize the database
        studentenwoningenRepository.saveAndFlush(studentenwoningen);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the studentenwoningen using partial update
        Studentenwoningen partialUpdatedStudentenwoningen = new Studentenwoningen();
        partialUpdatedStudentenwoningen.setId(studentenwoningen.getId());

        partialUpdatedStudentenwoningen.huurprijs(UPDATED_HUURPRIJS).zelfstandig(UPDATED_ZELFSTANDIG);

        restStudentenwoningenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStudentenwoningen.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStudentenwoningen))
            )
            .andExpect(status().isOk());

        // Validate the Studentenwoningen in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStudentenwoningenUpdatableFieldsEquals(
            partialUpdatedStudentenwoningen,
            getPersistedStudentenwoningen(partialUpdatedStudentenwoningen)
        );
    }

    @Test
    @Transactional
    void patchNonExistingStudentenwoningen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        studentenwoningen.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentenwoningenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, studentenwoningen.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(studentenwoningen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Studentenwoningen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStudentenwoningen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        studentenwoningen.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentenwoningenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(studentenwoningen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Studentenwoningen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStudentenwoningen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        studentenwoningen.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentenwoningenMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(studentenwoningen)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Studentenwoningen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStudentenwoningen() throws Exception {
        // Initialize the database
        studentenwoningenRepository.saveAndFlush(studentenwoningen);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the studentenwoningen
        restStudentenwoningenMockMvc
            .perform(delete(ENTITY_API_URL_ID, studentenwoningen.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return studentenwoningenRepository.count();
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

    protected Studentenwoningen getPersistedStudentenwoningen(Studentenwoningen studentenwoningen) {
        return studentenwoningenRepository.findById(studentenwoningen.getId()).orElseThrow();
    }

    protected void assertPersistedStudentenwoningenToMatchAllProperties(Studentenwoningen expectedStudentenwoningen) {
        assertStudentenwoningenAllPropertiesEquals(expectedStudentenwoningen, getPersistedStudentenwoningen(expectedStudentenwoningen));
    }

    protected void assertPersistedStudentenwoningenToMatchUpdatableProperties(Studentenwoningen expectedStudentenwoningen) {
        assertStudentenwoningenAllUpdatablePropertiesEquals(
            expectedStudentenwoningen,
            getPersistedStudentenwoningen(expectedStudentenwoningen)
        );
    }
}
