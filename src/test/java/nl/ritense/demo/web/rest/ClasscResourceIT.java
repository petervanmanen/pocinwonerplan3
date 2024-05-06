package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ClasscAsserts.*;
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
import nl.ritense.demo.domain.Classc;
import nl.ritense.demo.repository.ClasscRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ClasscResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClasscResourceIT {

    private static final BigDecimal DEFAULT_BEDRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_BEDRAG = new BigDecimal(2);

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/classcs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ClasscRepository classcRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClasscMockMvc;

    private Classc classc;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classc createEntity(EntityManager em) {
        Classc classc = new Classc().bedrag(DEFAULT_BEDRAG).naam(DEFAULT_NAAM);
        return classc;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classc createUpdatedEntity(EntityManager em) {
        Classc classc = new Classc().bedrag(UPDATED_BEDRAG).naam(UPDATED_NAAM);
        return classc;
    }

    @BeforeEach
    public void initTest() {
        classc = createEntity(em);
    }

    @Test
    @Transactional
    void createClassc() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Classc
        var returnedClassc = om.readValue(
            restClasscMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classc)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Classc.class
        );

        // Validate the Classc in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertClasscUpdatableFieldsEquals(returnedClassc, getPersistedClassc(returnedClassc));
    }

    @Test
    @Transactional
    void createClasscWithExistingId() throws Exception {
        // Create the Classc with an existing ID
        classc.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClasscMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classc)))
            .andExpect(status().isBadRequest());

        // Validate the Classc in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClasscs() throws Exception {
        // Initialize the database
        classcRepository.saveAndFlush(classc);

        // Get all the classcList
        restClasscMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classc.getId().intValue())))
            .andExpect(jsonPath("$.[*].bedrag").value(hasItem(sameNumber(DEFAULT_BEDRAG))))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)));
    }

    @Test
    @Transactional
    void getClassc() throws Exception {
        // Initialize the database
        classcRepository.saveAndFlush(classc);

        // Get the classc
        restClasscMockMvc
            .perform(get(ENTITY_API_URL_ID, classc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classc.getId().intValue()))
            .andExpect(jsonPath("$.bedrag").value(sameNumber(DEFAULT_BEDRAG)))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM));
    }

    @Test
    @Transactional
    void getNonExistingClassc() throws Exception {
        // Get the classc
        restClasscMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClassc() throws Exception {
        // Initialize the database
        classcRepository.saveAndFlush(classc);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the classc
        Classc updatedClassc = classcRepository.findById(classc.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedClassc are not directly saved in db
        em.detach(updatedClassc);
        updatedClassc.bedrag(UPDATED_BEDRAG).naam(UPDATED_NAAM);

        restClasscMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClassc.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedClassc))
            )
            .andExpect(status().isOk());

        // Validate the Classc in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedClasscToMatchAllProperties(updatedClassc);
    }

    @Test
    @Transactional
    void putNonExistingClassc() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classc.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClasscMockMvc
            .perform(put(ENTITY_API_URL_ID, classc.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classc)))
            .andExpect(status().isBadRequest());

        // Validate the Classc in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClassc() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classc.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClasscMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(classc))
            )
            .andExpect(status().isBadRequest());

        // Validate the Classc in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClassc() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classc.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClasscMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(classc)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Classc in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClasscWithPatch() throws Exception {
        // Initialize the database
        classcRepository.saveAndFlush(classc);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the classc using partial update
        Classc partialUpdatedClassc = new Classc();
        partialUpdatedClassc.setId(classc.getId());

        partialUpdatedClassc.bedrag(UPDATED_BEDRAG).naam(UPDATED_NAAM);

        restClasscMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClassc.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedClassc))
            )
            .andExpect(status().isOk());

        // Validate the Classc in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertClasscUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedClassc, classc), getPersistedClassc(classc));
    }

    @Test
    @Transactional
    void fullUpdateClasscWithPatch() throws Exception {
        // Initialize the database
        classcRepository.saveAndFlush(classc);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the classc using partial update
        Classc partialUpdatedClassc = new Classc();
        partialUpdatedClassc.setId(classc.getId());

        partialUpdatedClassc.bedrag(UPDATED_BEDRAG).naam(UPDATED_NAAM);

        restClasscMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClassc.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedClassc))
            )
            .andExpect(status().isOk());

        // Validate the Classc in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertClasscUpdatableFieldsEquals(partialUpdatedClassc, getPersistedClassc(partialUpdatedClassc));
    }

    @Test
    @Transactional
    void patchNonExistingClassc() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classc.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClasscMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, classc.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(classc))
            )
            .andExpect(status().isBadRequest());

        // Validate the Classc in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClassc() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classc.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClasscMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(classc))
            )
            .andExpect(status().isBadRequest());

        // Validate the Classc in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClassc() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classc.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClasscMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(classc)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Classc in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClassc() throws Exception {
        // Initialize the database
        classcRepository.saveAndFlush(classc);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the classc
        restClasscMockMvc
            .perform(delete(ENTITY_API_URL_ID, classc.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return classcRepository.count();
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

    protected Classc getPersistedClassc(Classc classc) {
        return classcRepository.findById(classc.getId()).orElseThrow();
    }

    protected void assertPersistedClasscToMatchAllProperties(Classc expectedClassc) {
        assertClasscAllPropertiesEquals(expectedClassc, getPersistedClassc(expectedClassc));
    }

    protected void assertPersistedClasscToMatchUpdatableProperties(Classc expectedClassc) {
        assertClasscAllUpdatablePropertiesEquals(expectedClassc, getPersistedClassc(expectedClassc));
    }
}
