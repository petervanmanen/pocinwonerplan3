package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.WozbelangAsserts.*;
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
import nl.ritense.demo.domain.Wozbelang;
import nl.ritense.demo.repository.WozbelangRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WozbelangResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WozbelangResourceIT {

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EIGENAARGEBRUIKER = "AAAAAAAAAA";
    private static final String UPDATED_EIGENAARGEBRUIKER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/wozbelangs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WozbelangRepository wozbelangRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWozbelangMockMvc;

    private Wozbelang wozbelang;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wozbelang createEntity(EntityManager em) {
        Wozbelang wozbelang = new Wozbelang()
            .datumbegingeldigheid(DEFAULT_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(DEFAULT_DATUMEINDEGELDIGHEID)
            .eigenaargebruiker(DEFAULT_EIGENAARGEBRUIKER);
        return wozbelang;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wozbelang createUpdatedEntity(EntityManager em) {
        Wozbelang wozbelang = new Wozbelang()
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .eigenaargebruiker(UPDATED_EIGENAARGEBRUIKER);
        return wozbelang;
    }

    @BeforeEach
    public void initTest() {
        wozbelang = createEntity(em);
    }

    @Test
    @Transactional
    void createWozbelang() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Wozbelang
        var returnedWozbelang = om.readValue(
            restWozbelangMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wozbelang)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Wozbelang.class
        );

        // Validate the Wozbelang in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertWozbelangUpdatableFieldsEquals(returnedWozbelang, getPersistedWozbelang(returnedWozbelang));
    }

    @Test
    @Transactional
    void createWozbelangWithExistingId() throws Exception {
        // Create the Wozbelang with an existing ID
        wozbelang.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWozbelangMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wozbelang)))
            .andExpect(status().isBadRequest());

        // Validate the Wozbelang in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWozbelangs() throws Exception {
        // Initialize the database
        wozbelangRepository.saveAndFlush(wozbelang);

        // Get all the wozbelangList
        restWozbelangMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wozbelang.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumbegingeldigheid").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheid").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].eigenaargebruiker").value(hasItem(DEFAULT_EIGENAARGEBRUIKER)));
    }

    @Test
    @Transactional
    void getWozbelang() throws Exception {
        // Initialize the database
        wozbelangRepository.saveAndFlush(wozbelang);

        // Get the wozbelang
        restWozbelangMockMvc
            .perform(get(ENTITY_API_URL_ID, wozbelang.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wozbelang.getId().intValue()))
            .andExpect(jsonPath("$.datumbegingeldigheid").value(DEFAULT_DATUMBEGINGELDIGHEID.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheid").value(DEFAULT_DATUMEINDEGELDIGHEID.toString()))
            .andExpect(jsonPath("$.eigenaargebruiker").value(DEFAULT_EIGENAARGEBRUIKER));
    }

    @Test
    @Transactional
    void getNonExistingWozbelang() throws Exception {
        // Get the wozbelang
        restWozbelangMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWozbelang() throws Exception {
        // Initialize the database
        wozbelangRepository.saveAndFlush(wozbelang);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the wozbelang
        Wozbelang updatedWozbelang = wozbelangRepository.findById(wozbelang.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWozbelang are not directly saved in db
        em.detach(updatedWozbelang);
        updatedWozbelang
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .eigenaargebruiker(UPDATED_EIGENAARGEBRUIKER);

        restWozbelangMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWozbelang.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedWozbelang))
            )
            .andExpect(status().isOk());

        // Validate the Wozbelang in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWozbelangToMatchAllProperties(updatedWozbelang);
    }

    @Test
    @Transactional
    void putNonExistingWozbelang() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wozbelang.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWozbelangMockMvc
            .perform(
                put(ENTITY_API_URL_ID, wozbelang.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wozbelang))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wozbelang in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWozbelang() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wozbelang.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWozbelangMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(wozbelang))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wozbelang in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWozbelang() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wozbelang.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWozbelangMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wozbelang)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Wozbelang in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWozbelangWithPatch() throws Exception {
        // Initialize the database
        wozbelangRepository.saveAndFlush(wozbelang);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the wozbelang using partial update
        Wozbelang partialUpdatedWozbelang = new Wozbelang();
        partialUpdatedWozbelang.setId(wozbelang.getId());

        partialUpdatedWozbelang.datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID);

        restWozbelangMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWozbelang.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWozbelang))
            )
            .andExpect(status().isOk());

        // Validate the Wozbelang in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWozbelangUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedWozbelang, wozbelang),
            getPersistedWozbelang(wozbelang)
        );
    }

    @Test
    @Transactional
    void fullUpdateWozbelangWithPatch() throws Exception {
        // Initialize the database
        wozbelangRepository.saveAndFlush(wozbelang);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the wozbelang using partial update
        Wozbelang partialUpdatedWozbelang = new Wozbelang();
        partialUpdatedWozbelang.setId(wozbelang.getId());

        partialUpdatedWozbelang
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .eigenaargebruiker(UPDATED_EIGENAARGEBRUIKER);

        restWozbelangMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWozbelang.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWozbelang))
            )
            .andExpect(status().isOk());

        // Validate the Wozbelang in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWozbelangUpdatableFieldsEquals(partialUpdatedWozbelang, getPersistedWozbelang(partialUpdatedWozbelang));
    }

    @Test
    @Transactional
    void patchNonExistingWozbelang() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wozbelang.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWozbelangMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, wozbelang.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(wozbelang))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wozbelang in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWozbelang() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wozbelang.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWozbelangMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(wozbelang))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wozbelang in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWozbelang() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wozbelang.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWozbelangMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(wozbelang)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Wozbelang in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWozbelang() throws Exception {
        // Initialize the database
        wozbelangRepository.saveAndFlush(wozbelang);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the wozbelang
        restWozbelangMockMvc
            .perform(delete(ENTITY_API_URL_ID, wozbelang.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return wozbelangRepository.count();
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

    protected Wozbelang getPersistedWozbelang(Wozbelang wozbelang) {
        return wozbelangRepository.findById(wozbelang.getId()).orElseThrow();
    }

    protected void assertPersistedWozbelangToMatchAllProperties(Wozbelang expectedWozbelang) {
        assertWozbelangAllPropertiesEquals(expectedWozbelang, getPersistedWozbelang(expectedWozbelang));
    }

    protected void assertPersistedWozbelangToMatchUpdatableProperties(Wozbelang expectedWozbelang) {
        assertWozbelangAllUpdatablePropertiesEquals(expectedWozbelang, getPersistedWozbelang(expectedWozbelang));
    }
}
