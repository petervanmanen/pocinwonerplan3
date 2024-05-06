package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VlakAsserts.*;
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
import nl.ritense.demo.domain.Vlak;
import nl.ritense.demo.repository.VlakRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VlakResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VlakResourceIT {

    private static final String DEFAULT_DIEPTETOT = "AAAAAAAAAA";
    private static final String UPDATED_DIEPTETOT = "BBBBBBBBBB";

    private static final String DEFAULT_DIEPTEVAN = "AAAAAAAAAA";
    private static final String UPDATED_DIEPTEVAN = "BBBBBBBBBB";

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_KEYPUT = "AAAAAAAAAA";
    private static final String UPDATED_KEYPUT = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECTCD = "AAAAAAAAAA";
    private static final String UPDATED_PROJECTCD = "BBBBBBBBBB";

    private static final String DEFAULT_PUTNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_PUTNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_VLAKNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_VLAKNUMMER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vlaks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VlakRepository vlakRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVlakMockMvc;

    private Vlak vlak;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vlak createEntity(EntityManager em) {
        Vlak vlak = new Vlak()
            .dieptetot(DEFAULT_DIEPTETOT)
            .dieptevan(DEFAULT_DIEPTEVAN)
            .key(DEFAULT_KEY)
            .keyput(DEFAULT_KEYPUT)
            .projectcd(DEFAULT_PROJECTCD)
            .putnummer(DEFAULT_PUTNUMMER)
            .vlaknummer(DEFAULT_VLAKNUMMER);
        return vlak;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vlak createUpdatedEntity(EntityManager em) {
        Vlak vlak = new Vlak()
            .dieptetot(UPDATED_DIEPTETOT)
            .dieptevan(UPDATED_DIEPTEVAN)
            .key(UPDATED_KEY)
            .keyput(UPDATED_KEYPUT)
            .projectcd(UPDATED_PROJECTCD)
            .putnummer(UPDATED_PUTNUMMER)
            .vlaknummer(UPDATED_VLAKNUMMER);
        return vlak;
    }

    @BeforeEach
    public void initTest() {
        vlak = createEntity(em);
    }

    @Test
    @Transactional
    void createVlak() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vlak
        var returnedVlak = om.readValue(
            restVlakMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vlak)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Vlak.class
        );

        // Validate the Vlak in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVlakUpdatableFieldsEquals(returnedVlak, getPersistedVlak(returnedVlak));
    }

    @Test
    @Transactional
    void createVlakWithExistingId() throws Exception {
        // Create the Vlak with an existing ID
        vlak.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVlakMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vlak)))
            .andExpect(status().isBadRequest());

        // Validate the Vlak in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVlaks() throws Exception {
        // Initialize the database
        vlakRepository.saveAndFlush(vlak);

        // Get all the vlakList
        restVlakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vlak.getId().intValue())))
            .andExpect(jsonPath("$.[*].dieptetot").value(hasItem(DEFAULT_DIEPTETOT)))
            .andExpect(jsonPath("$.[*].dieptevan").value(hasItem(DEFAULT_DIEPTEVAN)))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].keyput").value(hasItem(DEFAULT_KEYPUT)))
            .andExpect(jsonPath("$.[*].projectcd").value(hasItem(DEFAULT_PROJECTCD)))
            .andExpect(jsonPath("$.[*].putnummer").value(hasItem(DEFAULT_PUTNUMMER)))
            .andExpect(jsonPath("$.[*].vlaknummer").value(hasItem(DEFAULT_VLAKNUMMER)));
    }

    @Test
    @Transactional
    void getVlak() throws Exception {
        // Initialize the database
        vlakRepository.saveAndFlush(vlak);

        // Get the vlak
        restVlakMockMvc
            .perform(get(ENTITY_API_URL_ID, vlak.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vlak.getId().intValue()))
            .andExpect(jsonPath("$.dieptetot").value(DEFAULT_DIEPTETOT))
            .andExpect(jsonPath("$.dieptevan").value(DEFAULT_DIEPTEVAN))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.keyput").value(DEFAULT_KEYPUT))
            .andExpect(jsonPath("$.projectcd").value(DEFAULT_PROJECTCD))
            .andExpect(jsonPath("$.putnummer").value(DEFAULT_PUTNUMMER))
            .andExpect(jsonPath("$.vlaknummer").value(DEFAULT_VLAKNUMMER));
    }

    @Test
    @Transactional
    void getNonExistingVlak() throws Exception {
        // Get the vlak
        restVlakMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVlak() throws Exception {
        // Initialize the database
        vlakRepository.saveAndFlush(vlak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vlak
        Vlak updatedVlak = vlakRepository.findById(vlak.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVlak are not directly saved in db
        em.detach(updatedVlak);
        updatedVlak
            .dieptetot(UPDATED_DIEPTETOT)
            .dieptevan(UPDATED_DIEPTEVAN)
            .key(UPDATED_KEY)
            .keyput(UPDATED_KEYPUT)
            .projectcd(UPDATED_PROJECTCD)
            .putnummer(UPDATED_PUTNUMMER)
            .vlaknummer(UPDATED_VLAKNUMMER);

        restVlakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVlak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVlak))
            )
            .andExpect(status().isOk());

        // Validate the Vlak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVlakToMatchAllProperties(updatedVlak);
    }

    @Test
    @Transactional
    void putNonExistingVlak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vlak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVlakMockMvc
            .perform(put(ENTITY_API_URL_ID, vlak.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vlak)))
            .andExpect(status().isBadRequest());

        // Validate the Vlak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVlak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vlak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVlakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vlak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vlak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVlak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vlak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVlakMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vlak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vlak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVlakWithPatch() throws Exception {
        // Initialize the database
        vlakRepository.saveAndFlush(vlak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vlak using partial update
        Vlak partialUpdatedVlak = new Vlak();
        partialUpdatedVlak.setId(vlak.getId());

        partialUpdatedVlak.projectcd(UPDATED_PROJECTCD).putnummer(UPDATED_PUTNUMMER);

        restVlakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVlak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVlak))
            )
            .andExpect(status().isOk());

        // Validate the Vlak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVlakUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedVlak, vlak), getPersistedVlak(vlak));
    }

    @Test
    @Transactional
    void fullUpdateVlakWithPatch() throws Exception {
        // Initialize the database
        vlakRepository.saveAndFlush(vlak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vlak using partial update
        Vlak partialUpdatedVlak = new Vlak();
        partialUpdatedVlak.setId(vlak.getId());

        partialUpdatedVlak
            .dieptetot(UPDATED_DIEPTETOT)
            .dieptevan(UPDATED_DIEPTEVAN)
            .key(UPDATED_KEY)
            .keyput(UPDATED_KEYPUT)
            .projectcd(UPDATED_PROJECTCD)
            .putnummer(UPDATED_PUTNUMMER)
            .vlaknummer(UPDATED_VLAKNUMMER);

        restVlakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVlak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVlak))
            )
            .andExpect(status().isOk());

        // Validate the Vlak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVlakUpdatableFieldsEquals(partialUpdatedVlak, getPersistedVlak(partialUpdatedVlak));
    }

    @Test
    @Transactional
    void patchNonExistingVlak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vlak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVlakMockMvc
            .perform(patch(ENTITY_API_URL_ID, vlak.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vlak)))
            .andExpect(status().isBadRequest());

        // Validate the Vlak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVlak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vlak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVlakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vlak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vlak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVlak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vlak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVlakMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vlak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vlak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVlak() throws Exception {
        // Initialize the database
        vlakRepository.saveAndFlush(vlak);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vlak
        restVlakMockMvc
            .perform(delete(ENTITY_API_URL_ID, vlak.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vlakRepository.count();
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

    protected Vlak getPersistedVlak(Vlak vlak) {
        return vlakRepository.findById(vlak.getId()).orElseThrow();
    }

    protected void assertPersistedVlakToMatchAllProperties(Vlak expectedVlak) {
        assertVlakAllPropertiesEquals(expectedVlak, getPersistedVlak(expectedVlak));
    }

    protected void assertPersistedVlakToMatchUpdatableProperties(Vlak expectedVlak) {
        assertVlakAllUpdatablePropertiesEquals(expectedVlak, getPersistedVlak(expectedVlak));
    }
}
