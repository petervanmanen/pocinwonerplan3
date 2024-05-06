package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.WozdeelobjectAsserts.*;
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
import nl.ritense.demo.domain.Wozdeelobject;
import nl.ritense.demo.repository.WozdeelobjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WozdeelobjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WozdeelobjectResourceIT {

    private static final String DEFAULT_CODEWOZDEELOBJECT = "AAAAAAAAAA";
    private static final String UPDATED_CODEWOZDEELOBJECT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDDEELOBJECT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDDEELOBJECT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDDEELOBJECT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDDEELOBJECT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_STATUSWOZDEELOBJECT = "AAAAAAAAAA";
    private static final String UPDATED_STATUSWOZDEELOBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_WOZDEELOBJECTNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_WOZDEELOBJECTNUMMER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/wozdeelobjects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WozdeelobjectRepository wozdeelobjectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWozdeelobjectMockMvc;

    private Wozdeelobject wozdeelobject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wozdeelobject createEntity(EntityManager em) {
        Wozdeelobject wozdeelobject = new Wozdeelobject()
            .codewozdeelobject(DEFAULT_CODEWOZDEELOBJECT)
            .datumbegingeldigheiddeelobject(DEFAULT_DATUMBEGINGELDIGHEIDDEELOBJECT)
            .datumeindegeldigheiddeelobject(DEFAULT_DATUMEINDEGELDIGHEIDDEELOBJECT)
            .statuswozdeelobject(DEFAULT_STATUSWOZDEELOBJECT)
            .wozdeelobjectnummer(DEFAULT_WOZDEELOBJECTNUMMER);
        return wozdeelobject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wozdeelobject createUpdatedEntity(EntityManager em) {
        Wozdeelobject wozdeelobject = new Wozdeelobject()
            .codewozdeelobject(UPDATED_CODEWOZDEELOBJECT)
            .datumbegingeldigheiddeelobject(UPDATED_DATUMBEGINGELDIGHEIDDEELOBJECT)
            .datumeindegeldigheiddeelobject(UPDATED_DATUMEINDEGELDIGHEIDDEELOBJECT)
            .statuswozdeelobject(UPDATED_STATUSWOZDEELOBJECT)
            .wozdeelobjectnummer(UPDATED_WOZDEELOBJECTNUMMER);
        return wozdeelobject;
    }

    @BeforeEach
    public void initTest() {
        wozdeelobject = createEntity(em);
    }

    @Test
    @Transactional
    void createWozdeelobject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Wozdeelobject
        var returnedWozdeelobject = om.readValue(
            restWozdeelobjectMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wozdeelobject)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Wozdeelobject.class
        );

        // Validate the Wozdeelobject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertWozdeelobjectUpdatableFieldsEquals(returnedWozdeelobject, getPersistedWozdeelobject(returnedWozdeelobject));
    }

    @Test
    @Transactional
    void createWozdeelobjectWithExistingId() throws Exception {
        // Create the Wozdeelobject with an existing ID
        wozdeelobject.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWozdeelobjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wozdeelobject)))
            .andExpect(status().isBadRequest());

        // Validate the Wozdeelobject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWozdeelobjects() throws Exception {
        // Initialize the database
        wozdeelobjectRepository.saveAndFlush(wozdeelobject);

        // Get all the wozdeelobjectList
        restWozdeelobjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wozdeelobject.getId().intValue())))
            .andExpect(jsonPath("$.[*].codewozdeelobject").value(hasItem(DEFAULT_CODEWOZDEELOBJECT)))
            .andExpect(jsonPath("$.[*].datumbegingeldigheiddeelobject").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEIDDEELOBJECT.toString())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheiddeelobject").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEIDDEELOBJECT.toString())))
            .andExpect(jsonPath("$.[*].statuswozdeelobject").value(hasItem(DEFAULT_STATUSWOZDEELOBJECT)))
            .andExpect(jsonPath("$.[*].wozdeelobjectnummer").value(hasItem(DEFAULT_WOZDEELOBJECTNUMMER)));
    }

    @Test
    @Transactional
    void getWozdeelobject() throws Exception {
        // Initialize the database
        wozdeelobjectRepository.saveAndFlush(wozdeelobject);

        // Get the wozdeelobject
        restWozdeelobjectMockMvc
            .perform(get(ENTITY_API_URL_ID, wozdeelobject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wozdeelobject.getId().intValue()))
            .andExpect(jsonPath("$.codewozdeelobject").value(DEFAULT_CODEWOZDEELOBJECT))
            .andExpect(jsonPath("$.datumbegingeldigheiddeelobject").value(DEFAULT_DATUMBEGINGELDIGHEIDDEELOBJECT.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheiddeelobject").value(DEFAULT_DATUMEINDEGELDIGHEIDDEELOBJECT.toString()))
            .andExpect(jsonPath("$.statuswozdeelobject").value(DEFAULT_STATUSWOZDEELOBJECT))
            .andExpect(jsonPath("$.wozdeelobjectnummer").value(DEFAULT_WOZDEELOBJECTNUMMER));
    }

    @Test
    @Transactional
    void getNonExistingWozdeelobject() throws Exception {
        // Get the wozdeelobject
        restWozdeelobjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWozdeelobject() throws Exception {
        // Initialize the database
        wozdeelobjectRepository.saveAndFlush(wozdeelobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the wozdeelobject
        Wozdeelobject updatedWozdeelobject = wozdeelobjectRepository.findById(wozdeelobject.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWozdeelobject are not directly saved in db
        em.detach(updatedWozdeelobject);
        updatedWozdeelobject
            .codewozdeelobject(UPDATED_CODEWOZDEELOBJECT)
            .datumbegingeldigheiddeelobject(UPDATED_DATUMBEGINGELDIGHEIDDEELOBJECT)
            .datumeindegeldigheiddeelobject(UPDATED_DATUMEINDEGELDIGHEIDDEELOBJECT)
            .statuswozdeelobject(UPDATED_STATUSWOZDEELOBJECT)
            .wozdeelobjectnummer(UPDATED_WOZDEELOBJECTNUMMER);

        restWozdeelobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWozdeelobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedWozdeelobject))
            )
            .andExpect(status().isOk());

        // Validate the Wozdeelobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWozdeelobjectToMatchAllProperties(updatedWozdeelobject);
    }

    @Test
    @Transactional
    void putNonExistingWozdeelobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wozdeelobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWozdeelobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, wozdeelobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(wozdeelobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wozdeelobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWozdeelobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wozdeelobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWozdeelobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(wozdeelobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wozdeelobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWozdeelobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wozdeelobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWozdeelobjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wozdeelobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Wozdeelobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWozdeelobjectWithPatch() throws Exception {
        // Initialize the database
        wozdeelobjectRepository.saveAndFlush(wozdeelobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the wozdeelobject using partial update
        Wozdeelobject partialUpdatedWozdeelobject = new Wozdeelobject();
        partialUpdatedWozdeelobject.setId(wozdeelobject.getId());

        partialUpdatedWozdeelobject
            .codewozdeelobject(UPDATED_CODEWOZDEELOBJECT)
            .datumeindegeldigheiddeelobject(UPDATED_DATUMEINDEGELDIGHEIDDEELOBJECT)
            .statuswozdeelobject(UPDATED_STATUSWOZDEELOBJECT);

        restWozdeelobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWozdeelobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWozdeelobject))
            )
            .andExpect(status().isOk());

        // Validate the Wozdeelobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWozdeelobjectUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedWozdeelobject, wozdeelobject),
            getPersistedWozdeelobject(wozdeelobject)
        );
    }

    @Test
    @Transactional
    void fullUpdateWozdeelobjectWithPatch() throws Exception {
        // Initialize the database
        wozdeelobjectRepository.saveAndFlush(wozdeelobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the wozdeelobject using partial update
        Wozdeelobject partialUpdatedWozdeelobject = new Wozdeelobject();
        partialUpdatedWozdeelobject.setId(wozdeelobject.getId());

        partialUpdatedWozdeelobject
            .codewozdeelobject(UPDATED_CODEWOZDEELOBJECT)
            .datumbegingeldigheiddeelobject(UPDATED_DATUMBEGINGELDIGHEIDDEELOBJECT)
            .datumeindegeldigheiddeelobject(UPDATED_DATUMEINDEGELDIGHEIDDEELOBJECT)
            .statuswozdeelobject(UPDATED_STATUSWOZDEELOBJECT)
            .wozdeelobjectnummer(UPDATED_WOZDEELOBJECTNUMMER);

        restWozdeelobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWozdeelobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWozdeelobject))
            )
            .andExpect(status().isOk());

        // Validate the Wozdeelobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWozdeelobjectUpdatableFieldsEquals(partialUpdatedWozdeelobject, getPersistedWozdeelobject(partialUpdatedWozdeelobject));
    }

    @Test
    @Transactional
    void patchNonExistingWozdeelobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wozdeelobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWozdeelobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, wozdeelobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(wozdeelobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wozdeelobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWozdeelobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wozdeelobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWozdeelobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(wozdeelobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wozdeelobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWozdeelobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wozdeelobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWozdeelobjectMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(wozdeelobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Wozdeelobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWozdeelobject() throws Exception {
        // Initialize the database
        wozdeelobjectRepository.saveAndFlush(wozdeelobject);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the wozdeelobject
        restWozdeelobjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, wozdeelobject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return wozdeelobjectRepository.count();
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

    protected Wozdeelobject getPersistedWozdeelobject(Wozdeelobject wozdeelobject) {
        return wozdeelobjectRepository.findById(wozdeelobject.getId()).orElseThrow();
    }

    protected void assertPersistedWozdeelobjectToMatchAllProperties(Wozdeelobject expectedWozdeelobject) {
        assertWozdeelobjectAllPropertiesEquals(expectedWozdeelobject, getPersistedWozdeelobject(expectedWozdeelobject));
    }

    protected void assertPersistedWozdeelobjectToMatchUpdatableProperties(Wozdeelobject expectedWozdeelobject) {
        assertWozdeelobjectAllUpdatablePropertiesEquals(expectedWozdeelobject, getPersistedWozdeelobject(expectedWozdeelobject));
    }
}
