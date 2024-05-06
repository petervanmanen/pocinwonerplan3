package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.WerkgelegenheidAsserts.*;
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
import nl.ritense.demo.domain.Werkgelegenheid;
import nl.ritense.demo.repository.WerkgelegenheidRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WerkgelegenheidResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WerkgelegenheidResourceIT {

    private static final String DEFAULT_AANTALFULLTIMEMANNEN = "AAAAAAAAAA";
    private static final String UPDATED_AANTALFULLTIMEMANNEN = "BBBBBBBBBB";

    private static final String DEFAULT_AANTALFULLTIMEVROUWEN = "AAAAAAAAAA";
    private static final String UPDATED_AANTALFULLTIMEVROUWEN = "BBBBBBBBBB";

    private static final String DEFAULT_AANTALPARTTIMEMANNEN = "AAAAAAAAAA";
    private static final String UPDATED_AANTALPARTTIMEMANNEN = "BBBBBBBBBB";

    private static final String DEFAULT_AANTALPARTTIMEVROUWEN = "AAAAAAAAAA";
    private static final String UPDATED_AANTALPARTTIMEVROUWEN = "BBBBBBBBBB";

    private static final String DEFAULT_GROOTTEKLASSE = "AAAAAAAAAA";
    private static final String UPDATED_GROOTTEKLASSE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/werkgelegenheids";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WerkgelegenheidRepository werkgelegenheidRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWerkgelegenheidMockMvc;

    private Werkgelegenheid werkgelegenheid;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Werkgelegenheid createEntity(EntityManager em) {
        Werkgelegenheid werkgelegenheid = new Werkgelegenheid()
            .aantalfulltimemannen(DEFAULT_AANTALFULLTIMEMANNEN)
            .aantalfulltimevrouwen(DEFAULT_AANTALFULLTIMEVROUWEN)
            .aantalparttimemannen(DEFAULT_AANTALPARTTIMEMANNEN)
            .aantalparttimevrouwen(DEFAULT_AANTALPARTTIMEVROUWEN)
            .grootteklasse(DEFAULT_GROOTTEKLASSE);
        return werkgelegenheid;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Werkgelegenheid createUpdatedEntity(EntityManager em) {
        Werkgelegenheid werkgelegenheid = new Werkgelegenheid()
            .aantalfulltimemannen(UPDATED_AANTALFULLTIMEMANNEN)
            .aantalfulltimevrouwen(UPDATED_AANTALFULLTIMEVROUWEN)
            .aantalparttimemannen(UPDATED_AANTALPARTTIMEMANNEN)
            .aantalparttimevrouwen(UPDATED_AANTALPARTTIMEVROUWEN)
            .grootteklasse(UPDATED_GROOTTEKLASSE);
        return werkgelegenheid;
    }

    @BeforeEach
    public void initTest() {
        werkgelegenheid = createEntity(em);
    }

    @Test
    @Transactional
    void createWerkgelegenheid() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Werkgelegenheid
        var returnedWerkgelegenheid = om.readValue(
            restWerkgelegenheidMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(werkgelegenheid)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Werkgelegenheid.class
        );

        // Validate the Werkgelegenheid in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertWerkgelegenheidUpdatableFieldsEquals(returnedWerkgelegenheid, getPersistedWerkgelegenheid(returnedWerkgelegenheid));
    }

    @Test
    @Transactional
    void createWerkgelegenheidWithExistingId() throws Exception {
        // Create the Werkgelegenheid with an existing ID
        werkgelegenheid.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWerkgelegenheidMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(werkgelegenheid)))
            .andExpect(status().isBadRequest());

        // Validate the Werkgelegenheid in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWerkgelegenheids() throws Exception {
        // Initialize the database
        werkgelegenheidRepository.saveAndFlush(werkgelegenheid);

        // Get all the werkgelegenheidList
        restWerkgelegenheidMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(werkgelegenheid.getId().intValue())))
            .andExpect(jsonPath("$.[*].aantalfulltimemannen").value(hasItem(DEFAULT_AANTALFULLTIMEMANNEN)))
            .andExpect(jsonPath("$.[*].aantalfulltimevrouwen").value(hasItem(DEFAULT_AANTALFULLTIMEVROUWEN)))
            .andExpect(jsonPath("$.[*].aantalparttimemannen").value(hasItem(DEFAULT_AANTALPARTTIMEMANNEN)))
            .andExpect(jsonPath("$.[*].aantalparttimevrouwen").value(hasItem(DEFAULT_AANTALPARTTIMEVROUWEN)))
            .andExpect(jsonPath("$.[*].grootteklasse").value(hasItem(DEFAULT_GROOTTEKLASSE)));
    }

    @Test
    @Transactional
    void getWerkgelegenheid() throws Exception {
        // Initialize the database
        werkgelegenheidRepository.saveAndFlush(werkgelegenheid);

        // Get the werkgelegenheid
        restWerkgelegenheidMockMvc
            .perform(get(ENTITY_API_URL_ID, werkgelegenheid.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(werkgelegenheid.getId().intValue()))
            .andExpect(jsonPath("$.aantalfulltimemannen").value(DEFAULT_AANTALFULLTIMEMANNEN))
            .andExpect(jsonPath("$.aantalfulltimevrouwen").value(DEFAULT_AANTALFULLTIMEVROUWEN))
            .andExpect(jsonPath("$.aantalparttimemannen").value(DEFAULT_AANTALPARTTIMEMANNEN))
            .andExpect(jsonPath("$.aantalparttimevrouwen").value(DEFAULT_AANTALPARTTIMEVROUWEN))
            .andExpect(jsonPath("$.grootteklasse").value(DEFAULT_GROOTTEKLASSE));
    }

    @Test
    @Transactional
    void getNonExistingWerkgelegenheid() throws Exception {
        // Get the werkgelegenheid
        restWerkgelegenheidMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWerkgelegenheid() throws Exception {
        // Initialize the database
        werkgelegenheidRepository.saveAndFlush(werkgelegenheid);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the werkgelegenheid
        Werkgelegenheid updatedWerkgelegenheid = werkgelegenheidRepository.findById(werkgelegenheid.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWerkgelegenheid are not directly saved in db
        em.detach(updatedWerkgelegenheid);
        updatedWerkgelegenheid
            .aantalfulltimemannen(UPDATED_AANTALFULLTIMEMANNEN)
            .aantalfulltimevrouwen(UPDATED_AANTALFULLTIMEVROUWEN)
            .aantalparttimemannen(UPDATED_AANTALPARTTIMEMANNEN)
            .aantalparttimevrouwen(UPDATED_AANTALPARTTIMEVROUWEN)
            .grootteklasse(UPDATED_GROOTTEKLASSE);

        restWerkgelegenheidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWerkgelegenheid.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedWerkgelegenheid))
            )
            .andExpect(status().isOk());

        // Validate the Werkgelegenheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWerkgelegenheidToMatchAllProperties(updatedWerkgelegenheid);
    }

    @Test
    @Transactional
    void putNonExistingWerkgelegenheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        werkgelegenheid.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWerkgelegenheidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, werkgelegenheid.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(werkgelegenheid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Werkgelegenheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWerkgelegenheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        werkgelegenheid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWerkgelegenheidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(werkgelegenheid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Werkgelegenheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWerkgelegenheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        werkgelegenheid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWerkgelegenheidMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(werkgelegenheid)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Werkgelegenheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWerkgelegenheidWithPatch() throws Exception {
        // Initialize the database
        werkgelegenheidRepository.saveAndFlush(werkgelegenheid);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the werkgelegenheid using partial update
        Werkgelegenheid partialUpdatedWerkgelegenheid = new Werkgelegenheid();
        partialUpdatedWerkgelegenheid.setId(werkgelegenheid.getId());

        partialUpdatedWerkgelegenheid
            .aantalfulltimevrouwen(UPDATED_AANTALFULLTIMEVROUWEN)
            .aantalparttimevrouwen(UPDATED_AANTALPARTTIMEVROUWEN);

        restWerkgelegenheidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWerkgelegenheid.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWerkgelegenheid))
            )
            .andExpect(status().isOk());

        // Validate the Werkgelegenheid in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWerkgelegenheidUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedWerkgelegenheid, werkgelegenheid),
            getPersistedWerkgelegenheid(werkgelegenheid)
        );
    }

    @Test
    @Transactional
    void fullUpdateWerkgelegenheidWithPatch() throws Exception {
        // Initialize the database
        werkgelegenheidRepository.saveAndFlush(werkgelegenheid);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the werkgelegenheid using partial update
        Werkgelegenheid partialUpdatedWerkgelegenheid = new Werkgelegenheid();
        partialUpdatedWerkgelegenheid.setId(werkgelegenheid.getId());

        partialUpdatedWerkgelegenheid
            .aantalfulltimemannen(UPDATED_AANTALFULLTIMEMANNEN)
            .aantalfulltimevrouwen(UPDATED_AANTALFULLTIMEVROUWEN)
            .aantalparttimemannen(UPDATED_AANTALPARTTIMEMANNEN)
            .aantalparttimevrouwen(UPDATED_AANTALPARTTIMEVROUWEN)
            .grootteklasse(UPDATED_GROOTTEKLASSE);

        restWerkgelegenheidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWerkgelegenheid.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWerkgelegenheid))
            )
            .andExpect(status().isOk());

        // Validate the Werkgelegenheid in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWerkgelegenheidUpdatableFieldsEquals(
            partialUpdatedWerkgelegenheid,
            getPersistedWerkgelegenheid(partialUpdatedWerkgelegenheid)
        );
    }

    @Test
    @Transactional
    void patchNonExistingWerkgelegenheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        werkgelegenheid.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWerkgelegenheidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, werkgelegenheid.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(werkgelegenheid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Werkgelegenheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWerkgelegenheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        werkgelegenheid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWerkgelegenheidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(werkgelegenheid))
            )
            .andExpect(status().isBadRequest());

        // Validate the Werkgelegenheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWerkgelegenheid() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        werkgelegenheid.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWerkgelegenheidMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(werkgelegenheid)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Werkgelegenheid in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWerkgelegenheid() throws Exception {
        // Initialize the database
        werkgelegenheidRepository.saveAndFlush(werkgelegenheid);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the werkgelegenheid
        restWerkgelegenheidMockMvc
            .perform(delete(ENTITY_API_URL_ID, werkgelegenheid.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return werkgelegenheidRepository.count();
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

    protected Werkgelegenheid getPersistedWerkgelegenheid(Werkgelegenheid werkgelegenheid) {
        return werkgelegenheidRepository.findById(werkgelegenheid.getId()).orElseThrow();
    }

    protected void assertPersistedWerkgelegenheidToMatchAllProperties(Werkgelegenheid expectedWerkgelegenheid) {
        assertWerkgelegenheidAllPropertiesEquals(expectedWerkgelegenheid, getPersistedWerkgelegenheid(expectedWerkgelegenheid));
    }

    protected void assertPersistedWerkgelegenheidToMatchUpdatableProperties(Werkgelegenheid expectedWerkgelegenheid) {
        assertWerkgelegenheidAllUpdatablePropertiesEquals(expectedWerkgelegenheid, getPersistedWerkgelegenheid(expectedWerkgelegenheid));
    }
}
