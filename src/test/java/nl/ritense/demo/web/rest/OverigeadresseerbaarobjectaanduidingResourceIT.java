package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OverigeadresseerbaarobjectaanduidingAsserts.*;
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
import nl.ritense.demo.domain.Overigeadresseerbaarobjectaanduiding;
import nl.ritense.demo.repository.OverigeadresseerbaarobjectaanduidingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OverigeadresseerbaarobjectaanduidingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OverigeadresseerbaarobjectaanduidingResourceIT {

    private static final String DEFAULT_IDENTIFICATIECODEOVERIGADRESSEERBAAROBJECTAANDUIDING = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIECODEOVERIGADRESSEERBAAROBJECTAANDUIDING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/overigeadresseerbaarobjectaanduidings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OverigeadresseerbaarobjectaanduidingRepository overigeadresseerbaarobjectaanduidingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOverigeadresseerbaarobjectaanduidingMockMvc;

    private Overigeadresseerbaarobjectaanduiding overigeadresseerbaarobjectaanduiding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Overigeadresseerbaarobjectaanduiding createEntity(EntityManager em) {
        Overigeadresseerbaarobjectaanduiding overigeadresseerbaarobjectaanduiding = new Overigeadresseerbaarobjectaanduiding()
            .identificatiecodeoverigadresseerbaarobjectaanduiding(DEFAULT_IDENTIFICATIECODEOVERIGADRESSEERBAAROBJECTAANDUIDING);
        return overigeadresseerbaarobjectaanduiding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Overigeadresseerbaarobjectaanduiding createUpdatedEntity(EntityManager em) {
        Overigeadresseerbaarobjectaanduiding overigeadresseerbaarobjectaanduiding = new Overigeadresseerbaarobjectaanduiding()
            .identificatiecodeoverigadresseerbaarobjectaanduiding(UPDATED_IDENTIFICATIECODEOVERIGADRESSEERBAAROBJECTAANDUIDING);
        return overigeadresseerbaarobjectaanduiding;
    }

    @BeforeEach
    public void initTest() {
        overigeadresseerbaarobjectaanduiding = createEntity(em);
    }

    @Test
    @Transactional
    void createOverigeadresseerbaarobjectaanduiding() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Overigeadresseerbaarobjectaanduiding
        var returnedOverigeadresseerbaarobjectaanduiding = om.readValue(
            restOverigeadresseerbaarobjectaanduidingMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(overigeadresseerbaarobjectaanduiding))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Overigeadresseerbaarobjectaanduiding.class
        );

        // Validate the Overigeadresseerbaarobjectaanduiding in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOverigeadresseerbaarobjectaanduidingUpdatableFieldsEquals(
            returnedOverigeadresseerbaarobjectaanduiding,
            getPersistedOverigeadresseerbaarobjectaanduiding(returnedOverigeadresseerbaarobjectaanduiding)
        );
    }

    @Test
    @Transactional
    void createOverigeadresseerbaarobjectaanduidingWithExistingId() throws Exception {
        // Create the Overigeadresseerbaarobjectaanduiding with an existing ID
        overigeadresseerbaarobjectaanduiding.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOverigeadresseerbaarobjectaanduidingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(overigeadresseerbaarobjectaanduiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overigeadresseerbaarobjectaanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOverigeadresseerbaarobjectaanduidings() throws Exception {
        // Initialize the database
        overigeadresseerbaarobjectaanduidingRepository.saveAndFlush(overigeadresseerbaarobjectaanduiding);

        // Get all the overigeadresseerbaarobjectaanduidingList
        restOverigeadresseerbaarobjectaanduidingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(overigeadresseerbaarobjectaanduiding.getId().intValue())))
            .andExpect(
                jsonPath("$.[*].identificatiecodeoverigadresseerbaarobjectaanduiding").value(
                    hasItem(DEFAULT_IDENTIFICATIECODEOVERIGADRESSEERBAAROBJECTAANDUIDING)
                )
            );
    }

    @Test
    @Transactional
    void getOverigeadresseerbaarobjectaanduiding() throws Exception {
        // Initialize the database
        overigeadresseerbaarobjectaanduidingRepository.saveAndFlush(overigeadresseerbaarobjectaanduiding);

        // Get the overigeadresseerbaarobjectaanduiding
        restOverigeadresseerbaarobjectaanduidingMockMvc
            .perform(get(ENTITY_API_URL_ID, overigeadresseerbaarobjectaanduiding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(overigeadresseerbaarobjectaanduiding.getId().intValue()))
            .andExpect(
                jsonPath("$.identificatiecodeoverigadresseerbaarobjectaanduiding").value(
                    DEFAULT_IDENTIFICATIECODEOVERIGADRESSEERBAAROBJECTAANDUIDING
                )
            );
    }

    @Test
    @Transactional
    void getNonExistingOverigeadresseerbaarobjectaanduiding() throws Exception {
        // Get the overigeadresseerbaarobjectaanduiding
        restOverigeadresseerbaarobjectaanduidingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOverigeadresseerbaarobjectaanduiding() throws Exception {
        // Initialize the database
        overigeadresseerbaarobjectaanduidingRepository.saveAndFlush(overigeadresseerbaarobjectaanduiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the overigeadresseerbaarobjectaanduiding
        Overigeadresseerbaarobjectaanduiding updatedOverigeadresseerbaarobjectaanduiding = overigeadresseerbaarobjectaanduidingRepository
            .findById(overigeadresseerbaarobjectaanduiding.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedOverigeadresseerbaarobjectaanduiding are not directly saved in db
        em.detach(updatedOverigeadresseerbaarobjectaanduiding);
        updatedOverigeadresseerbaarobjectaanduiding.identificatiecodeoverigadresseerbaarobjectaanduiding(
            UPDATED_IDENTIFICATIECODEOVERIGADRESSEERBAAROBJECTAANDUIDING
        );

        restOverigeadresseerbaarobjectaanduidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOverigeadresseerbaarobjectaanduiding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOverigeadresseerbaarobjectaanduiding))
            )
            .andExpect(status().isOk());

        // Validate the Overigeadresseerbaarobjectaanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOverigeadresseerbaarobjectaanduidingToMatchAllProperties(updatedOverigeadresseerbaarobjectaanduiding);
    }

    @Test
    @Transactional
    void putNonExistingOverigeadresseerbaarobjectaanduiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overigeadresseerbaarobjectaanduiding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOverigeadresseerbaarobjectaanduidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, overigeadresseerbaarobjectaanduiding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(overigeadresseerbaarobjectaanduiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overigeadresseerbaarobjectaanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOverigeadresseerbaarobjectaanduiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overigeadresseerbaarobjectaanduiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverigeadresseerbaarobjectaanduidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(overigeadresseerbaarobjectaanduiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overigeadresseerbaarobjectaanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOverigeadresseerbaarobjectaanduiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overigeadresseerbaarobjectaanduiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverigeadresseerbaarobjectaanduidingMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(overigeadresseerbaarobjectaanduiding))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Overigeadresseerbaarobjectaanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOverigeadresseerbaarobjectaanduidingWithPatch() throws Exception {
        // Initialize the database
        overigeadresseerbaarobjectaanduidingRepository.saveAndFlush(overigeadresseerbaarobjectaanduiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the overigeadresseerbaarobjectaanduiding using partial update
        Overigeadresseerbaarobjectaanduiding partialUpdatedOverigeadresseerbaarobjectaanduiding =
            new Overigeadresseerbaarobjectaanduiding();
        partialUpdatedOverigeadresseerbaarobjectaanduiding.setId(overigeadresseerbaarobjectaanduiding.getId());

        partialUpdatedOverigeadresseerbaarobjectaanduiding.identificatiecodeoverigadresseerbaarobjectaanduiding(
            UPDATED_IDENTIFICATIECODEOVERIGADRESSEERBAAROBJECTAANDUIDING
        );

        restOverigeadresseerbaarobjectaanduidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOverigeadresseerbaarobjectaanduiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOverigeadresseerbaarobjectaanduiding))
            )
            .andExpect(status().isOk());

        // Validate the Overigeadresseerbaarobjectaanduiding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOverigeadresseerbaarobjectaanduidingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOverigeadresseerbaarobjectaanduiding, overigeadresseerbaarobjectaanduiding),
            getPersistedOverigeadresseerbaarobjectaanduiding(overigeadresseerbaarobjectaanduiding)
        );
    }

    @Test
    @Transactional
    void fullUpdateOverigeadresseerbaarobjectaanduidingWithPatch() throws Exception {
        // Initialize the database
        overigeadresseerbaarobjectaanduidingRepository.saveAndFlush(overigeadresseerbaarobjectaanduiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the overigeadresseerbaarobjectaanduiding using partial update
        Overigeadresseerbaarobjectaanduiding partialUpdatedOverigeadresseerbaarobjectaanduiding =
            new Overigeadresseerbaarobjectaanduiding();
        partialUpdatedOverigeadresseerbaarobjectaanduiding.setId(overigeadresseerbaarobjectaanduiding.getId());

        partialUpdatedOverigeadresseerbaarobjectaanduiding.identificatiecodeoverigadresseerbaarobjectaanduiding(
            UPDATED_IDENTIFICATIECODEOVERIGADRESSEERBAAROBJECTAANDUIDING
        );

        restOverigeadresseerbaarobjectaanduidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOverigeadresseerbaarobjectaanduiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOverigeadresseerbaarobjectaanduiding))
            )
            .andExpect(status().isOk());

        // Validate the Overigeadresseerbaarobjectaanduiding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOverigeadresseerbaarobjectaanduidingUpdatableFieldsEquals(
            partialUpdatedOverigeadresseerbaarobjectaanduiding,
            getPersistedOverigeadresseerbaarobjectaanduiding(partialUpdatedOverigeadresseerbaarobjectaanduiding)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOverigeadresseerbaarobjectaanduiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overigeadresseerbaarobjectaanduiding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOverigeadresseerbaarobjectaanduidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, overigeadresseerbaarobjectaanduiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(overigeadresseerbaarobjectaanduiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overigeadresseerbaarobjectaanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOverigeadresseerbaarobjectaanduiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overigeadresseerbaarobjectaanduiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverigeadresseerbaarobjectaanduidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(overigeadresseerbaarobjectaanduiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Overigeadresseerbaarobjectaanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOverigeadresseerbaarobjectaanduiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        overigeadresseerbaarobjectaanduiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverigeadresseerbaarobjectaanduidingMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(overigeadresseerbaarobjectaanduiding))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Overigeadresseerbaarobjectaanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOverigeadresseerbaarobjectaanduiding() throws Exception {
        // Initialize the database
        overigeadresseerbaarobjectaanduidingRepository.saveAndFlush(overigeadresseerbaarobjectaanduiding);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the overigeadresseerbaarobjectaanduiding
        restOverigeadresseerbaarobjectaanduidingMockMvc
            .perform(delete(ENTITY_API_URL_ID, overigeadresseerbaarobjectaanduiding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return overigeadresseerbaarobjectaanduidingRepository.count();
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

    protected Overigeadresseerbaarobjectaanduiding getPersistedOverigeadresseerbaarobjectaanduiding(
        Overigeadresseerbaarobjectaanduiding overigeadresseerbaarobjectaanduiding
    ) {
        return overigeadresseerbaarobjectaanduidingRepository.findById(overigeadresseerbaarobjectaanduiding.getId()).orElseThrow();
    }

    protected void assertPersistedOverigeadresseerbaarobjectaanduidingToMatchAllProperties(
        Overigeadresseerbaarobjectaanduiding expectedOverigeadresseerbaarobjectaanduiding
    ) {
        assertOverigeadresseerbaarobjectaanduidingAllPropertiesEquals(
            expectedOverigeadresseerbaarobjectaanduiding,
            getPersistedOverigeadresseerbaarobjectaanduiding(expectedOverigeadresseerbaarobjectaanduiding)
        );
    }

    protected void assertPersistedOverigeadresseerbaarobjectaanduidingToMatchUpdatableProperties(
        Overigeadresseerbaarobjectaanduiding expectedOverigeadresseerbaarobjectaanduiding
    ) {
        assertOverigeadresseerbaarobjectaanduidingAllUpdatablePropertiesEquals(
            expectedOverigeadresseerbaarobjectaanduiding,
            getPersistedOverigeadresseerbaarobjectaanduiding(expectedOverigeadresseerbaarobjectaanduiding)
        );
    }
}
