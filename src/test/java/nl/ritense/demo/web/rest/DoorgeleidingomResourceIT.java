package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.DoorgeleidingomAsserts.*;
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
import nl.ritense.demo.domain.Doorgeleidingom;
import nl.ritense.demo.repository.DoorgeleidingomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DoorgeleidingomResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DoorgeleidingomResourceIT {

    private static final String DEFAULT_AFDOENING = "AAAAAAAAAA";
    private static final String UPDATED_AFDOENING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/doorgeleidingoms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DoorgeleidingomRepository doorgeleidingomRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDoorgeleidingomMockMvc;

    private Doorgeleidingom doorgeleidingom;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Doorgeleidingom createEntity(EntityManager em) {
        Doorgeleidingom doorgeleidingom = new Doorgeleidingom().afdoening(DEFAULT_AFDOENING);
        return doorgeleidingom;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Doorgeleidingom createUpdatedEntity(EntityManager em) {
        Doorgeleidingom doorgeleidingom = new Doorgeleidingom().afdoening(UPDATED_AFDOENING);
        return doorgeleidingom;
    }

    @BeforeEach
    public void initTest() {
        doorgeleidingom = createEntity(em);
    }

    @Test
    @Transactional
    void createDoorgeleidingom() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Doorgeleidingom
        var returnedDoorgeleidingom = om.readValue(
            restDoorgeleidingomMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(doorgeleidingom)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Doorgeleidingom.class
        );

        // Validate the Doorgeleidingom in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDoorgeleidingomUpdatableFieldsEquals(returnedDoorgeleidingom, getPersistedDoorgeleidingom(returnedDoorgeleidingom));
    }

    @Test
    @Transactional
    void createDoorgeleidingomWithExistingId() throws Exception {
        // Create the Doorgeleidingom with an existing ID
        doorgeleidingom.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoorgeleidingomMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(doorgeleidingom)))
            .andExpect(status().isBadRequest());

        // Validate the Doorgeleidingom in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDoorgeleidingoms() throws Exception {
        // Initialize the database
        doorgeleidingomRepository.saveAndFlush(doorgeleidingom);

        // Get all the doorgeleidingomList
        restDoorgeleidingomMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doorgeleidingom.getId().intValue())))
            .andExpect(jsonPath("$.[*].afdoening").value(hasItem(DEFAULT_AFDOENING)));
    }

    @Test
    @Transactional
    void getDoorgeleidingom() throws Exception {
        // Initialize the database
        doorgeleidingomRepository.saveAndFlush(doorgeleidingom);

        // Get the doorgeleidingom
        restDoorgeleidingomMockMvc
            .perform(get(ENTITY_API_URL_ID, doorgeleidingom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(doorgeleidingom.getId().intValue()))
            .andExpect(jsonPath("$.afdoening").value(DEFAULT_AFDOENING));
    }

    @Test
    @Transactional
    void getNonExistingDoorgeleidingom() throws Exception {
        // Get the doorgeleidingom
        restDoorgeleidingomMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDoorgeleidingom() throws Exception {
        // Initialize the database
        doorgeleidingomRepository.saveAndFlush(doorgeleidingom);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the doorgeleidingom
        Doorgeleidingom updatedDoorgeleidingom = doorgeleidingomRepository.findById(doorgeleidingom.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDoorgeleidingom are not directly saved in db
        em.detach(updatedDoorgeleidingom);
        updatedDoorgeleidingom.afdoening(UPDATED_AFDOENING);

        restDoorgeleidingomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDoorgeleidingom.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDoorgeleidingom))
            )
            .andExpect(status().isOk());

        // Validate the Doorgeleidingom in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDoorgeleidingomToMatchAllProperties(updatedDoorgeleidingom);
    }

    @Test
    @Transactional
    void putNonExistingDoorgeleidingom() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doorgeleidingom.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoorgeleidingomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, doorgeleidingom.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(doorgeleidingom))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doorgeleidingom in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDoorgeleidingom() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doorgeleidingom.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoorgeleidingomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(doorgeleidingom))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doorgeleidingom in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDoorgeleidingom() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doorgeleidingom.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoorgeleidingomMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(doorgeleidingom)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Doorgeleidingom in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDoorgeleidingomWithPatch() throws Exception {
        // Initialize the database
        doorgeleidingomRepository.saveAndFlush(doorgeleidingom);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the doorgeleidingom using partial update
        Doorgeleidingom partialUpdatedDoorgeleidingom = new Doorgeleidingom();
        partialUpdatedDoorgeleidingom.setId(doorgeleidingom.getId());

        partialUpdatedDoorgeleidingom.afdoening(UPDATED_AFDOENING);

        restDoorgeleidingomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoorgeleidingom.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDoorgeleidingom))
            )
            .andExpect(status().isOk());

        // Validate the Doorgeleidingom in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDoorgeleidingomUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDoorgeleidingom, doorgeleidingom),
            getPersistedDoorgeleidingom(doorgeleidingom)
        );
    }

    @Test
    @Transactional
    void fullUpdateDoorgeleidingomWithPatch() throws Exception {
        // Initialize the database
        doorgeleidingomRepository.saveAndFlush(doorgeleidingom);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the doorgeleidingom using partial update
        Doorgeleidingom partialUpdatedDoorgeleidingom = new Doorgeleidingom();
        partialUpdatedDoorgeleidingom.setId(doorgeleidingom.getId());

        partialUpdatedDoorgeleidingom.afdoening(UPDATED_AFDOENING);

        restDoorgeleidingomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoorgeleidingom.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDoorgeleidingom))
            )
            .andExpect(status().isOk());

        // Validate the Doorgeleidingom in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDoorgeleidingomUpdatableFieldsEquals(
            partialUpdatedDoorgeleidingom,
            getPersistedDoorgeleidingom(partialUpdatedDoorgeleidingom)
        );
    }

    @Test
    @Transactional
    void patchNonExistingDoorgeleidingom() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doorgeleidingom.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoorgeleidingomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, doorgeleidingom.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(doorgeleidingom))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doorgeleidingom in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDoorgeleidingom() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doorgeleidingom.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoorgeleidingomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(doorgeleidingom))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doorgeleidingom in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDoorgeleidingom() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doorgeleidingom.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoorgeleidingomMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(doorgeleidingom)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Doorgeleidingom in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDoorgeleidingom() throws Exception {
        // Initialize the database
        doorgeleidingomRepository.saveAndFlush(doorgeleidingom);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the doorgeleidingom
        restDoorgeleidingomMockMvc
            .perform(delete(ENTITY_API_URL_ID, doorgeleidingom.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return doorgeleidingomRepository.count();
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

    protected Doorgeleidingom getPersistedDoorgeleidingom(Doorgeleidingom doorgeleidingom) {
        return doorgeleidingomRepository.findById(doorgeleidingom.getId()).orElseThrow();
    }

    protected void assertPersistedDoorgeleidingomToMatchAllProperties(Doorgeleidingom expectedDoorgeleidingom) {
        assertDoorgeleidingomAllPropertiesEquals(expectedDoorgeleidingom, getPersistedDoorgeleidingom(expectedDoorgeleidingom));
    }

    protected void assertPersistedDoorgeleidingomToMatchUpdatableProperties(Doorgeleidingom expectedDoorgeleidingom) {
        assertDoorgeleidingomAllUpdatablePropertiesEquals(expectedDoorgeleidingom, getPersistedDoorgeleidingom(expectedDoorgeleidingom));
    }
}
