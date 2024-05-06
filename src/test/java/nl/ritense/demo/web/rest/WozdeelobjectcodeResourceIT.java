package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.WozdeelobjectcodeAsserts.*;
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
import nl.ritense.demo.domain.Wozdeelobjectcode;
import nl.ritense.demo.repository.WozdeelobjectcodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WozdeelobjectcodeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WozdeelobjectcodeResourceIT {

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDDEELOJECTCODE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDDEELOJECTCODE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDDEELOBJECTCODE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDDEELOBJECTCODE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DEELOBJECTCODE = "AAAAAAAAAA";
    private static final String UPDATED_DEELOBJECTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAAMDEELOBJECTCODE = "AAAAAAAAAA";
    private static final String UPDATED_NAAMDEELOBJECTCODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/wozdeelobjectcodes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WozdeelobjectcodeRepository wozdeelobjectcodeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWozdeelobjectcodeMockMvc;

    private Wozdeelobjectcode wozdeelobjectcode;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wozdeelobjectcode createEntity(EntityManager em) {
        Wozdeelobjectcode wozdeelobjectcode = new Wozdeelobjectcode()
            .datumbegingeldigheiddeelojectcode(DEFAULT_DATUMBEGINGELDIGHEIDDEELOJECTCODE)
            .datumeindegeldigheiddeelobjectcode(DEFAULT_DATUMEINDEGELDIGHEIDDEELOBJECTCODE)
            .deelobjectcode(DEFAULT_DEELOBJECTCODE)
            .naamdeelobjectcode(DEFAULT_NAAMDEELOBJECTCODE);
        return wozdeelobjectcode;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wozdeelobjectcode createUpdatedEntity(EntityManager em) {
        Wozdeelobjectcode wozdeelobjectcode = new Wozdeelobjectcode()
            .datumbegingeldigheiddeelojectcode(UPDATED_DATUMBEGINGELDIGHEIDDEELOJECTCODE)
            .datumeindegeldigheiddeelobjectcode(UPDATED_DATUMEINDEGELDIGHEIDDEELOBJECTCODE)
            .deelobjectcode(UPDATED_DEELOBJECTCODE)
            .naamdeelobjectcode(UPDATED_NAAMDEELOBJECTCODE);
        return wozdeelobjectcode;
    }

    @BeforeEach
    public void initTest() {
        wozdeelobjectcode = createEntity(em);
    }

    @Test
    @Transactional
    void createWozdeelobjectcode() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Wozdeelobjectcode
        var returnedWozdeelobjectcode = om.readValue(
            restWozdeelobjectcodeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wozdeelobjectcode)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Wozdeelobjectcode.class
        );

        // Validate the Wozdeelobjectcode in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertWozdeelobjectcodeUpdatableFieldsEquals(returnedWozdeelobjectcode, getPersistedWozdeelobjectcode(returnedWozdeelobjectcode));
    }

    @Test
    @Transactional
    void createWozdeelobjectcodeWithExistingId() throws Exception {
        // Create the Wozdeelobjectcode with an existing ID
        wozdeelobjectcode.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWozdeelobjectcodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wozdeelobjectcode)))
            .andExpect(status().isBadRequest());

        // Validate the Wozdeelobjectcode in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWozdeelobjectcodes() throws Exception {
        // Initialize the database
        wozdeelobjectcodeRepository.saveAndFlush(wozdeelobjectcode);

        // Get all the wozdeelobjectcodeList
        restWozdeelobjectcodeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wozdeelobjectcode.getId().intValue())))
            .andExpect(
                jsonPath("$.[*].datumbegingeldigheiddeelojectcode").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEIDDEELOJECTCODE.toString()))
            )
            .andExpect(
                jsonPath("$.[*].datumeindegeldigheiddeelobjectcode").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEIDDEELOBJECTCODE.toString()))
            )
            .andExpect(jsonPath("$.[*].deelobjectcode").value(hasItem(DEFAULT_DEELOBJECTCODE)))
            .andExpect(jsonPath("$.[*].naamdeelobjectcode").value(hasItem(DEFAULT_NAAMDEELOBJECTCODE)));
    }

    @Test
    @Transactional
    void getWozdeelobjectcode() throws Exception {
        // Initialize the database
        wozdeelobjectcodeRepository.saveAndFlush(wozdeelobjectcode);

        // Get the wozdeelobjectcode
        restWozdeelobjectcodeMockMvc
            .perform(get(ENTITY_API_URL_ID, wozdeelobjectcode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wozdeelobjectcode.getId().intValue()))
            .andExpect(jsonPath("$.datumbegingeldigheiddeelojectcode").value(DEFAULT_DATUMBEGINGELDIGHEIDDEELOJECTCODE.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheiddeelobjectcode").value(DEFAULT_DATUMEINDEGELDIGHEIDDEELOBJECTCODE.toString()))
            .andExpect(jsonPath("$.deelobjectcode").value(DEFAULT_DEELOBJECTCODE))
            .andExpect(jsonPath("$.naamdeelobjectcode").value(DEFAULT_NAAMDEELOBJECTCODE));
    }

    @Test
    @Transactional
    void getNonExistingWozdeelobjectcode() throws Exception {
        // Get the wozdeelobjectcode
        restWozdeelobjectcodeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWozdeelobjectcode() throws Exception {
        // Initialize the database
        wozdeelobjectcodeRepository.saveAndFlush(wozdeelobjectcode);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the wozdeelobjectcode
        Wozdeelobjectcode updatedWozdeelobjectcode = wozdeelobjectcodeRepository.findById(wozdeelobjectcode.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWozdeelobjectcode are not directly saved in db
        em.detach(updatedWozdeelobjectcode);
        updatedWozdeelobjectcode
            .datumbegingeldigheiddeelojectcode(UPDATED_DATUMBEGINGELDIGHEIDDEELOJECTCODE)
            .datumeindegeldigheiddeelobjectcode(UPDATED_DATUMEINDEGELDIGHEIDDEELOBJECTCODE)
            .deelobjectcode(UPDATED_DEELOBJECTCODE)
            .naamdeelobjectcode(UPDATED_NAAMDEELOBJECTCODE);

        restWozdeelobjectcodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWozdeelobjectcode.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedWozdeelobjectcode))
            )
            .andExpect(status().isOk());

        // Validate the Wozdeelobjectcode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWozdeelobjectcodeToMatchAllProperties(updatedWozdeelobjectcode);
    }

    @Test
    @Transactional
    void putNonExistingWozdeelobjectcode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wozdeelobjectcode.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWozdeelobjectcodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, wozdeelobjectcode.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(wozdeelobjectcode))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wozdeelobjectcode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWozdeelobjectcode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wozdeelobjectcode.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWozdeelobjectcodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(wozdeelobjectcode))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wozdeelobjectcode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWozdeelobjectcode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wozdeelobjectcode.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWozdeelobjectcodeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wozdeelobjectcode)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Wozdeelobjectcode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWozdeelobjectcodeWithPatch() throws Exception {
        // Initialize the database
        wozdeelobjectcodeRepository.saveAndFlush(wozdeelobjectcode);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the wozdeelobjectcode using partial update
        Wozdeelobjectcode partialUpdatedWozdeelobjectcode = new Wozdeelobjectcode();
        partialUpdatedWozdeelobjectcode.setId(wozdeelobjectcode.getId());

        partialUpdatedWozdeelobjectcode
            .datumbegingeldigheiddeelojectcode(UPDATED_DATUMBEGINGELDIGHEIDDEELOJECTCODE)
            .datumeindegeldigheiddeelobjectcode(UPDATED_DATUMEINDEGELDIGHEIDDEELOBJECTCODE)
            .naamdeelobjectcode(UPDATED_NAAMDEELOBJECTCODE);

        restWozdeelobjectcodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWozdeelobjectcode.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWozdeelobjectcode))
            )
            .andExpect(status().isOk());

        // Validate the Wozdeelobjectcode in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWozdeelobjectcodeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedWozdeelobjectcode, wozdeelobjectcode),
            getPersistedWozdeelobjectcode(wozdeelobjectcode)
        );
    }

    @Test
    @Transactional
    void fullUpdateWozdeelobjectcodeWithPatch() throws Exception {
        // Initialize the database
        wozdeelobjectcodeRepository.saveAndFlush(wozdeelobjectcode);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the wozdeelobjectcode using partial update
        Wozdeelobjectcode partialUpdatedWozdeelobjectcode = new Wozdeelobjectcode();
        partialUpdatedWozdeelobjectcode.setId(wozdeelobjectcode.getId());

        partialUpdatedWozdeelobjectcode
            .datumbegingeldigheiddeelojectcode(UPDATED_DATUMBEGINGELDIGHEIDDEELOJECTCODE)
            .datumeindegeldigheiddeelobjectcode(UPDATED_DATUMEINDEGELDIGHEIDDEELOBJECTCODE)
            .deelobjectcode(UPDATED_DEELOBJECTCODE)
            .naamdeelobjectcode(UPDATED_NAAMDEELOBJECTCODE);

        restWozdeelobjectcodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWozdeelobjectcode.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWozdeelobjectcode))
            )
            .andExpect(status().isOk());

        // Validate the Wozdeelobjectcode in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWozdeelobjectcodeUpdatableFieldsEquals(
            partialUpdatedWozdeelobjectcode,
            getPersistedWozdeelobjectcode(partialUpdatedWozdeelobjectcode)
        );
    }

    @Test
    @Transactional
    void patchNonExistingWozdeelobjectcode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wozdeelobjectcode.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWozdeelobjectcodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, wozdeelobjectcode.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(wozdeelobjectcode))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wozdeelobjectcode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWozdeelobjectcode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wozdeelobjectcode.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWozdeelobjectcodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(wozdeelobjectcode))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wozdeelobjectcode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWozdeelobjectcode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wozdeelobjectcode.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWozdeelobjectcodeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(wozdeelobjectcode)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Wozdeelobjectcode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWozdeelobjectcode() throws Exception {
        // Initialize the database
        wozdeelobjectcodeRepository.saveAndFlush(wozdeelobjectcode);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the wozdeelobjectcode
        restWozdeelobjectcodeMockMvc
            .perform(delete(ENTITY_API_URL_ID, wozdeelobjectcode.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return wozdeelobjectcodeRepository.count();
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

    protected Wozdeelobjectcode getPersistedWozdeelobjectcode(Wozdeelobjectcode wozdeelobjectcode) {
        return wozdeelobjectcodeRepository.findById(wozdeelobjectcode.getId()).orElseThrow();
    }

    protected void assertPersistedWozdeelobjectcodeToMatchAllProperties(Wozdeelobjectcode expectedWozdeelobjectcode) {
        assertWozdeelobjectcodeAllPropertiesEquals(expectedWozdeelobjectcode, getPersistedWozdeelobjectcode(expectedWozdeelobjectcode));
    }

    protected void assertPersistedWozdeelobjectcodeToMatchUpdatableProperties(Wozdeelobjectcode expectedWozdeelobjectcode) {
        assertWozdeelobjectcodeAllUpdatablePropertiesEquals(
            expectedWozdeelobjectcode,
            getPersistedWozdeelobjectcode(expectedWozdeelobjectcode)
        );
    }
}
