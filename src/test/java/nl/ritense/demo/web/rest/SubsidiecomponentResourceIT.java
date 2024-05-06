package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SubsidiecomponentAsserts.*;
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
import nl.ritense.demo.domain.Subsidiecomponent;
import nl.ritense.demo.repository.SubsidiecomponentRepository;
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
 * Integration tests for the {@link SubsidiecomponentResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class SubsidiecomponentResourceIT {

    private static final BigDecimal DEFAULT_GERESERVEERDBEDRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_GERESERVEERDBEDRAG = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOEGEKENDBEDRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOEGEKENDBEDRAG = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/subsidiecomponents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SubsidiecomponentRepository subsidiecomponentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubsidiecomponentMockMvc;

    private Subsidiecomponent subsidiecomponent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subsidiecomponent createEntity(EntityManager em) {
        Subsidiecomponent subsidiecomponent = new Subsidiecomponent()
            .gereserveerdbedrag(DEFAULT_GERESERVEERDBEDRAG)
            .toegekendbedrag(DEFAULT_TOEGEKENDBEDRAG);
        return subsidiecomponent;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subsidiecomponent createUpdatedEntity(EntityManager em) {
        Subsidiecomponent subsidiecomponent = new Subsidiecomponent()
            .gereserveerdbedrag(UPDATED_GERESERVEERDBEDRAG)
            .toegekendbedrag(UPDATED_TOEGEKENDBEDRAG);
        return subsidiecomponent;
    }

    @BeforeEach
    public void initTest() {
        subsidiecomponent = createEntity(em);
    }

    @Test
    @Transactional
    void createSubsidiecomponent() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Subsidiecomponent
        var returnedSubsidiecomponent = om.readValue(
            restSubsidiecomponentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subsidiecomponent)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Subsidiecomponent.class
        );

        // Validate the Subsidiecomponent in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSubsidiecomponentUpdatableFieldsEquals(returnedSubsidiecomponent, getPersistedSubsidiecomponent(returnedSubsidiecomponent));
    }

    @Test
    @Transactional
    void createSubsidiecomponentWithExistingId() throws Exception {
        // Create the Subsidiecomponent with an existing ID
        subsidiecomponent.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubsidiecomponentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subsidiecomponent)))
            .andExpect(status().isBadRequest());

        // Validate the Subsidiecomponent in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSubsidiecomponents() throws Exception {
        // Initialize the database
        subsidiecomponentRepository.saveAndFlush(subsidiecomponent);

        // Get all the subsidiecomponentList
        restSubsidiecomponentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subsidiecomponent.getId().intValue())))
            .andExpect(jsonPath("$.[*].gereserveerdbedrag").value(hasItem(sameNumber(DEFAULT_GERESERVEERDBEDRAG))))
            .andExpect(jsonPath("$.[*].toegekendbedrag").value(hasItem(sameNumber(DEFAULT_TOEGEKENDBEDRAG))));
    }

    @Test
    @Transactional
    void getSubsidiecomponent() throws Exception {
        // Initialize the database
        subsidiecomponentRepository.saveAndFlush(subsidiecomponent);

        // Get the subsidiecomponent
        restSubsidiecomponentMockMvc
            .perform(get(ENTITY_API_URL_ID, subsidiecomponent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subsidiecomponent.getId().intValue()))
            .andExpect(jsonPath("$.gereserveerdbedrag").value(sameNumber(DEFAULT_GERESERVEERDBEDRAG)))
            .andExpect(jsonPath("$.toegekendbedrag").value(sameNumber(DEFAULT_TOEGEKENDBEDRAG)));
    }

    @Test
    @Transactional
    void getNonExistingSubsidiecomponent() throws Exception {
        // Get the subsidiecomponent
        restSubsidiecomponentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSubsidiecomponent() throws Exception {
        // Initialize the database
        subsidiecomponentRepository.saveAndFlush(subsidiecomponent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subsidiecomponent
        Subsidiecomponent updatedSubsidiecomponent = subsidiecomponentRepository.findById(subsidiecomponent.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSubsidiecomponent are not directly saved in db
        em.detach(updatedSubsidiecomponent);
        updatedSubsidiecomponent.gereserveerdbedrag(UPDATED_GERESERVEERDBEDRAG).toegekendbedrag(UPDATED_TOEGEKENDBEDRAG);

        restSubsidiecomponentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSubsidiecomponent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSubsidiecomponent))
            )
            .andExpect(status().isOk());

        // Validate the Subsidiecomponent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSubsidiecomponentToMatchAllProperties(updatedSubsidiecomponent);
    }

    @Test
    @Transactional
    void putNonExistingSubsidiecomponent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subsidiecomponent.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubsidiecomponentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subsidiecomponent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(subsidiecomponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subsidiecomponent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubsidiecomponent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subsidiecomponent.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubsidiecomponentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(subsidiecomponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subsidiecomponent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubsidiecomponent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subsidiecomponent.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubsidiecomponentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subsidiecomponent)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Subsidiecomponent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubsidiecomponentWithPatch() throws Exception {
        // Initialize the database
        subsidiecomponentRepository.saveAndFlush(subsidiecomponent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subsidiecomponent using partial update
        Subsidiecomponent partialUpdatedSubsidiecomponent = new Subsidiecomponent();
        partialUpdatedSubsidiecomponent.setId(subsidiecomponent.getId());

        partialUpdatedSubsidiecomponent.toegekendbedrag(UPDATED_TOEGEKENDBEDRAG);

        restSubsidiecomponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubsidiecomponent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSubsidiecomponent))
            )
            .andExpect(status().isOk());

        // Validate the Subsidiecomponent in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSubsidiecomponentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSubsidiecomponent, subsidiecomponent),
            getPersistedSubsidiecomponent(subsidiecomponent)
        );
    }

    @Test
    @Transactional
    void fullUpdateSubsidiecomponentWithPatch() throws Exception {
        // Initialize the database
        subsidiecomponentRepository.saveAndFlush(subsidiecomponent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subsidiecomponent using partial update
        Subsidiecomponent partialUpdatedSubsidiecomponent = new Subsidiecomponent();
        partialUpdatedSubsidiecomponent.setId(subsidiecomponent.getId());

        partialUpdatedSubsidiecomponent.gereserveerdbedrag(UPDATED_GERESERVEERDBEDRAG).toegekendbedrag(UPDATED_TOEGEKENDBEDRAG);

        restSubsidiecomponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubsidiecomponent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSubsidiecomponent))
            )
            .andExpect(status().isOk());

        // Validate the Subsidiecomponent in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSubsidiecomponentUpdatableFieldsEquals(
            partialUpdatedSubsidiecomponent,
            getPersistedSubsidiecomponent(partialUpdatedSubsidiecomponent)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSubsidiecomponent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subsidiecomponent.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubsidiecomponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subsidiecomponent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(subsidiecomponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subsidiecomponent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubsidiecomponent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subsidiecomponent.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubsidiecomponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(subsidiecomponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subsidiecomponent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubsidiecomponent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subsidiecomponent.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubsidiecomponentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(subsidiecomponent)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Subsidiecomponent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubsidiecomponent() throws Exception {
        // Initialize the database
        subsidiecomponentRepository.saveAndFlush(subsidiecomponent);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the subsidiecomponent
        restSubsidiecomponentMockMvc
            .perform(delete(ENTITY_API_URL_ID, subsidiecomponent.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return subsidiecomponentRepository.count();
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

    protected Subsidiecomponent getPersistedSubsidiecomponent(Subsidiecomponent subsidiecomponent) {
        return subsidiecomponentRepository.findById(subsidiecomponent.getId()).orElseThrow();
    }

    protected void assertPersistedSubsidiecomponentToMatchAllProperties(Subsidiecomponent expectedSubsidiecomponent) {
        assertSubsidiecomponentAllPropertiesEquals(expectedSubsidiecomponent, getPersistedSubsidiecomponent(expectedSubsidiecomponent));
    }

    protected void assertPersistedSubsidiecomponentToMatchUpdatableProperties(Subsidiecomponent expectedSubsidiecomponent) {
        assertSubsidiecomponentAllUpdatablePropertiesEquals(
            expectedSubsidiecomponent,
            getPersistedSubsidiecomponent(expectedSubsidiecomponent)
        );
    }
}
