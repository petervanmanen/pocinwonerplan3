package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SpoorAsserts.*;
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
import nl.ritense.demo.domain.Spoor;
import nl.ritense.demo.repository.SpoorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SpoorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SpoorResourceIT {

    private static final String DEFAULT_AARD = "AAAAAAAAAA";
    private static final String UPDATED_AARD = "BBBBBBBBBB";

    private static final String DEFAULT_BESCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_BESCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_DATERING = "AAAAAAAAAA";
    private static final String UPDATED_DATERING = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_HOOGTEBOVEN = "AAAAAAAAAA";
    private static final String UPDATED_HOOGTEBOVEN = "BBBBBBBBBB";

    private static final String DEFAULT_HOOGTEONDER = "AAAAAAAAAA";
    private static final String UPDATED_HOOGTEONDER = "BBBBBBBBBB";

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_KEYVLAK = "AAAAAAAAAA";
    private static final String UPDATED_KEYVLAK = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECTCD = "AAAAAAAAAA";
    private static final String UPDATED_PROJECTCD = "BBBBBBBBBB";

    private static final String DEFAULT_PUTNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_PUTNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_SPOORNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_SPOORNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_VLAKNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_VLAKNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_VORM = "AAAAAAAAAA";
    private static final String UPDATED_VORM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/spoors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SpoorRepository spoorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSpoorMockMvc;

    private Spoor spoor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Spoor createEntity(EntityManager em) {
        Spoor spoor = new Spoor()
            .aard(DEFAULT_AARD)
            .beschrijving(DEFAULT_BESCHRIJVING)
            .datering(DEFAULT_DATERING)
            .datum(DEFAULT_DATUM)
            .hoogteboven(DEFAULT_HOOGTEBOVEN)
            .hoogteonder(DEFAULT_HOOGTEONDER)
            .key(DEFAULT_KEY)
            .keyvlak(DEFAULT_KEYVLAK)
            .projectcd(DEFAULT_PROJECTCD)
            .putnummer(DEFAULT_PUTNUMMER)
            .spoornummer(DEFAULT_SPOORNUMMER)
            .vlaknummer(DEFAULT_VLAKNUMMER)
            .vorm(DEFAULT_VORM);
        return spoor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Spoor createUpdatedEntity(EntityManager em) {
        Spoor spoor = new Spoor()
            .aard(UPDATED_AARD)
            .beschrijving(UPDATED_BESCHRIJVING)
            .datering(UPDATED_DATERING)
            .datum(UPDATED_DATUM)
            .hoogteboven(UPDATED_HOOGTEBOVEN)
            .hoogteonder(UPDATED_HOOGTEONDER)
            .key(UPDATED_KEY)
            .keyvlak(UPDATED_KEYVLAK)
            .projectcd(UPDATED_PROJECTCD)
            .putnummer(UPDATED_PUTNUMMER)
            .spoornummer(UPDATED_SPOORNUMMER)
            .vlaknummer(UPDATED_VLAKNUMMER)
            .vorm(UPDATED_VORM);
        return spoor;
    }

    @BeforeEach
    public void initTest() {
        spoor = createEntity(em);
    }

    @Test
    @Transactional
    void createSpoor() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Spoor
        var returnedSpoor = om.readValue(
            restSpoorMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(spoor)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Spoor.class
        );

        // Validate the Spoor in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSpoorUpdatableFieldsEquals(returnedSpoor, getPersistedSpoor(returnedSpoor));
    }

    @Test
    @Transactional
    void createSpoorWithExistingId() throws Exception {
        // Create the Spoor with an existing ID
        spoor.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpoorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(spoor)))
            .andExpect(status().isBadRequest());

        // Validate the Spoor in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSpoors() throws Exception {
        // Initialize the database
        spoorRepository.saveAndFlush(spoor);

        // Get all the spoorList
        restSpoorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(spoor.getId().intValue())))
            .andExpect(jsonPath("$.[*].aard").value(hasItem(DEFAULT_AARD)))
            .andExpect(jsonPath("$.[*].beschrijving").value(hasItem(DEFAULT_BESCHRIJVING)))
            .andExpect(jsonPath("$.[*].datering").value(hasItem(DEFAULT_DATERING)))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].hoogteboven").value(hasItem(DEFAULT_HOOGTEBOVEN)))
            .andExpect(jsonPath("$.[*].hoogteonder").value(hasItem(DEFAULT_HOOGTEONDER)))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].keyvlak").value(hasItem(DEFAULT_KEYVLAK)))
            .andExpect(jsonPath("$.[*].projectcd").value(hasItem(DEFAULT_PROJECTCD)))
            .andExpect(jsonPath("$.[*].putnummer").value(hasItem(DEFAULT_PUTNUMMER)))
            .andExpect(jsonPath("$.[*].spoornummer").value(hasItem(DEFAULT_SPOORNUMMER)))
            .andExpect(jsonPath("$.[*].vlaknummer").value(hasItem(DEFAULT_VLAKNUMMER)))
            .andExpect(jsonPath("$.[*].vorm").value(hasItem(DEFAULT_VORM)));
    }

    @Test
    @Transactional
    void getSpoor() throws Exception {
        // Initialize the database
        spoorRepository.saveAndFlush(spoor);

        // Get the spoor
        restSpoorMockMvc
            .perform(get(ENTITY_API_URL_ID, spoor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(spoor.getId().intValue()))
            .andExpect(jsonPath("$.aard").value(DEFAULT_AARD))
            .andExpect(jsonPath("$.beschrijving").value(DEFAULT_BESCHRIJVING))
            .andExpect(jsonPath("$.datering").value(DEFAULT_DATERING))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.hoogteboven").value(DEFAULT_HOOGTEBOVEN))
            .andExpect(jsonPath("$.hoogteonder").value(DEFAULT_HOOGTEONDER))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.keyvlak").value(DEFAULT_KEYVLAK))
            .andExpect(jsonPath("$.projectcd").value(DEFAULT_PROJECTCD))
            .andExpect(jsonPath("$.putnummer").value(DEFAULT_PUTNUMMER))
            .andExpect(jsonPath("$.spoornummer").value(DEFAULT_SPOORNUMMER))
            .andExpect(jsonPath("$.vlaknummer").value(DEFAULT_VLAKNUMMER))
            .andExpect(jsonPath("$.vorm").value(DEFAULT_VORM));
    }

    @Test
    @Transactional
    void getNonExistingSpoor() throws Exception {
        // Get the spoor
        restSpoorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSpoor() throws Exception {
        // Initialize the database
        spoorRepository.saveAndFlush(spoor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the spoor
        Spoor updatedSpoor = spoorRepository.findById(spoor.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSpoor are not directly saved in db
        em.detach(updatedSpoor);
        updatedSpoor
            .aard(UPDATED_AARD)
            .beschrijving(UPDATED_BESCHRIJVING)
            .datering(UPDATED_DATERING)
            .datum(UPDATED_DATUM)
            .hoogteboven(UPDATED_HOOGTEBOVEN)
            .hoogteonder(UPDATED_HOOGTEONDER)
            .key(UPDATED_KEY)
            .keyvlak(UPDATED_KEYVLAK)
            .projectcd(UPDATED_PROJECTCD)
            .putnummer(UPDATED_PUTNUMMER)
            .spoornummer(UPDATED_SPOORNUMMER)
            .vlaknummer(UPDATED_VLAKNUMMER)
            .vorm(UPDATED_VORM);

        restSpoorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSpoor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSpoor))
            )
            .andExpect(status().isOk());

        // Validate the Spoor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSpoorToMatchAllProperties(updatedSpoor);
    }

    @Test
    @Transactional
    void putNonExistingSpoor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        spoor.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpoorMockMvc
            .perform(put(ENTITY_API_URL_ID, spoor.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(spoor)))
            .andExpect(status().isBadRequest());

        // Validate the Spoor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSpoor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        spoor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpoorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(spoor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Spoor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSpoor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        spoor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpoorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(spoor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Spoor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSpoorWithPatch() throws Exception {
        // Initialize the database
        spoorRepository.saveAndFlush(spoor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the spoor using partial update
        Spoor partialUpdatedSpoor = new Spoor();
        partialUpdatedSpoor.setId(spoor.getId());

        partialUpdatedSpoor.beschrijving(UPDATED_BESCHRIJVING).key(UPDATED_KEY).putnummer(UPDATED_PUTNUMMER).vorm(UPDATED_VORM);

        restSpoorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpoor.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSpoor))
            )
            .andExpect(status().isOk());

        // Validate the Spoor in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSpoorUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSpoor, spoor), getPersistedSpoor(spoor));
    }

    @Test
    @Transactional
    void fullUpdateSpoorWithPatch() throws Exception {
        // Initialize the database
        spoorRepository.saveAndFlush(spoor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the spoor using partial update
        Spoor partialUpdatedSpoor = new Spoor();
        partialUpdatedSpoor.setId(spoor.getId());

        partialUpdatedSpoor
            .aard(UPDATED_AARD)
            .beschrijving(UPDATED_BESCHRIJVING)
            .datering(UPDATED_DATERING)
            .datum(UPDATED_DATUM)
            .hoogteboven(UPDATED_HOOGTEBOVEN)
            .hoogteonder(UPDATED_HOOGTEONDER)
            .key(UPDATED_KEY)
            .keyvlak(UPDATED_KEYVLAK)
            .projectcd(UPDATED_PROJECTCD)
            .putnummer(UPDATED_PUTNUMMER)
            .spoornummer(UPDATED_SPOORNUMMER)
            .vlaknummer(UPDATED_VLAKNUMMER)
            .vorm(UPDATED_VORM);

        restSpoorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpoor.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSpoor))
            )
            .andExpect(status().isOk());

        // Validate the Spoor in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSpoorUpdatableFieldsEquals(partialUpdatedSpoor, getPersistedSpoor(partialUpdatedSpoor));
    }

    @Test
    @Transactional
    void patchNonExistingSpoor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        spoor.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpoorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, spoor.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(spoor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Spoor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSpoor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        spoor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpoorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(spoor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Spoor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSpoor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        spoor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpoorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(spoor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Spoor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSpoor() throws Exception {
        // Initialize the database
        spoorRepository.saveAndFlush(spoor);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the spoor
        restSpoorMockMvc
            .perform(delete(ENTITY_API_URL_ID, spoor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return spoorRepository.count();
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

    protected Spoor getPersistedSpoor(Spoor spoor) {
        return spoorRepository.findById(spoor.getId()).orElseThrow();
    }

    protected void assertPersistedSpoorToMatchAllProperties(Spoor expectedSpoor) {
        assertSpoorAllPropertiesEquals(expectedSpoor, getPersistedSpoor(expectedSpoor));
    }

    protected void assertPersistedSpoorToMatchUpdatableProperties(Spoor expectedSpoor) {
        assertSpoorAllUpdatablePropertiesEquals(expectedSpoor, getPersistedSpoor(expectedSpoor));
    }
}
