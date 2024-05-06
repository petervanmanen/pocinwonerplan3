package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.TunneldeelAsserts.*;
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
import nl.ritense.demo.domain.Tunneldeel;
import nl.ritense.demo.repository.TunneldeelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TunneldeelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TunneldeelResourceIT {

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDTUNNELDEEL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDTUNNELDEEL = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDTUNNELDEEL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDTUNNELDEEL = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GEOMETRIETUNNELDEEL = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIETUNNELDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIETUNNELDEEL = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIETUNNELDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_LOD_0_GEOMETRIETUNNELDEEL = "AAAAAAAAAA";
    private static final String UPDATED_LOD_0_GEOMETRIETUNNELDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_RELATIEVEHOOGTELIGGINGTUNNELDEEL = "AAAAAAAAAA";
    private static final String UPDATED_RELATIEVEHOOGTELIGGINGTUNNELDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_STATUSTUNNELDEEL = "AAAAAAAAAA";
    private static final String UPDATED_STATUSTUNNELDEEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tunneldeels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TunneldeelRepository tunneldeelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTunneldeelMockMvc;

    private Tunneldeel tunneldeel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tunneldeel createEntity(EntityManager em) {
        Tunneldeel tunneldeel = new Tunneldeel()
            .datumbegingeldigheidtunneldeel(DEFAULT_DATUMBEGINGELDIGHEIDTUNNELDEEL)
            .datumeindegeldigheidtunneldeel(DEFAULT_DATUMEINDEGELDIGHEIDTUNNELDEEL)
            .geometrietunneldeel(DEFAULT_GEOMETRIETUNNELDEEL)
            .identificatietunneldeel(DEFAULT_IDENTIFICATIETUNNELDEEL)
            .lod0geometrietunneldeel(DEFAULT_LOD_0_GEOMETRIETUNNELDEEL)
            .relatievehoogteliggingtunneldeel(DEFAULT_RELATIEVEHOOGTELIGGINGTUNNELDEEL)
            .statustunneldeel(DEFAULT_STATUSTUNNELDEEL);
        return tunneldeel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tunneldeel createUpdatedEntity(EntityManager em) {
        Tunneldeel tunneldeel = new Tunneldeel()
            .datumbegingeldigheidtunneldeel(UPDATED_DATUMBEGINGELDIGHEIDTUNNELDEEL)
            .datumeindegeldigheidtunneldeel(UPDATED_DATUMEINDEGELDIGHEIDTUNNELDEEL)
            .geometrietunneldeel(UPDATED_GEOMETRIETUNNELDEEL)
            .identificatietunneldeel(UPDATED_IDENTIFICATIETUNNELDEEL)
            .lod0geometrietunneldeel(UPDATED_LOD_0_GEOMETRIETUNNELDEEL)
            .relatievehoogteliggingtunneldeel(UPDATED_RELATIEVEHOOGTELIGGINGTUNNELDEEL)
            .statustunneldeel(UPDATED_STATUSTUNNELDEEL);
        return tunneldeel;
    }

    @BeforeEach
    public void initTest() {
        tunneldeel = createEntity(em);
    }

    @Test
    @Transactional
    void createTunneldeel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Tunneldeel
        var returnedTunneldeel = om.readValue(
            restTunneldeelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tunneldeel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Tunneldeel.class
        );

        // Validate the Tunneldeel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTunneldeelUpdatableFieldsEquals(returnedTunneldeel, getPersistedTunneldeel(returnedTunneldeel));
    }

    @Test
    @Transactional
    void createTunneldeelWithExistingId() throws Exception {
        // Create the Tunneldeel with an existing ID
        tunneldeel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTunneldeelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tunneldeel)))
            .andExpect(status().isBadRequest());

        // Validate the Tunneldeel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTunneldeels() throws Exception {
        // Initialize the database
        tunneldeelRepository.saveAndFlush(tunneldeel);

        // Get all the tunneldeelList
        restTunneldeelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tunneldeel.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumbegingeldigheidtunneldeel").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEIDTUNNELDEEL.toString())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheidtunneldeel").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEIDTUNNELDEEL.toString())))
            .andExpect(jsonPath("$.[*].geometrietunneldeel").value(hasItem(DEFAULT_GEOMETRIETUNNELDEEL)))
            .andExpect(jsonPath("$.[*].identificatietunneldeel").value(hasItem(DEFAULT_IDENTIFICATIETUNNELDEEL)))
            .andExpect(jsonPath("$.[*].lod0geometrietunneldeel").value(hasItem(DEFAULT_LOD_0_GEOMETRIETUNNELDEEL)))
            .andExpect(jsonPath("$.[*].relatievehoogteliggingtunneldeel").value(hasItem(DEFAULT_RELATIEVEHOOGTELIGGINGTUNNELDEEL)))
            .andExpect(jsonPath("$.[*].statustunneldeel").value(hasItem(DEFAULT_STATUSTUNNELDEEL)));
    }

    @Test
    @Transactional
    void getTunneldeel() throws Exception {
        // Initialize the database
        tunneldeelRepository.saveAndFlush(tunneldeel);

        // Get the tunneldeel
        restTunneldeelMockMvc
            .perform(get(ENTITY_API_URL_ID, tunneldeel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tunneldeel.getId().intValue()))
            .andExpect(jsonPath("$.datumbegingeldigheidtunneldeel").value(DEFAULT_DATUMBEGINGELDIGHEIDTUNNELDEEL.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheidtunneldeel").value(DEFAULT_DATUMEINDEGELDIGHEIDTUNNELDEEL.toString()))
            .andExpect(jsonPath("$.geometrietunneldeel").value(DEFAULT_GEOMETRIETUNNELDEEL))
            .andExpect(jsonPath("$.identificatietunneldeel").value(DEFAULT_IDENTIFICATIETUNNELDEEL))
            .andExpect(jsonPath("$.lod0geometrietunneldeel").value(DEFAULT_LOD_0_GEOMETRIETUNNELDEEL))
            .andExpect(jsonPath("$.relatievehoogteliggingtunneldeel").value(DEFAULT_RELATIEVEHOOGTELIGGINGTUNNELDEEL))
            .andExpect(jsonPath("$.statustunneldeel").value(DEFAULT_STATUSTUNNELDEEL));
    }

    @Test
    @Transactional
    void getNonExistingTunneldeel() throws Exception {
        // Get the tunneldeel
        restTunneldeelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTunneldeel() throws Exception {
        // Initialize the database
        tunneldeelRepository.saveAndFlush(tunneldeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tunneldeel
        Tunneldeel updatedTunneldeel = tunneldeelRepository.findById(tunneldeel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTunneldeel are not directly saved in db
        em.detach(updatedTunneldeel);
        updatedTunneldeel
            .datumbegingeldigheidtunneldeel(UPDATED_DATUMBEGINGELDIGHEIDTUNNELDEEL)
            .datumeindegeldigheidtunneldeel(UPDATED_DATUMEINDEGELDIGHEIDTUNNELDEEL)
            .geometrietunneldeel(UPDATED_GEOMETRIETUNNELDEEL)
            .identificatietunneldeel(UPDATED_IDENTIFICATIETUNNELDEEL)
            .lod0geometrietunneldeel(UPDATED_LOD_0_GEOMETRIETUNNELDEEL)
            .relatievehoogteliggingtunneldeel(UPDATED_RELATIEVEHOOGTELIGGINGTUNNELDEEL)
            .statustunneldeel(UPDATED_STATUSTUNNELDEEL);

        restTunneldeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTunneldeel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTunneldeel))
            )
            .andExpect(status().isOk());

        // Validate the Tunneldeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTunneldeelToMatchAllProperties(updatedTunneldeel);
    }

    @Test
    @Transactional
    void putNonExistingTunneldeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tunneldeel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTunneldeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tunneldeel.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tunneldeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tunneldeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTunneldeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tunneldeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTunneldeelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tunneldeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tunneldeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTunneldeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tunneldeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTunneldeelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tunneldeel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tunneldeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTunneldeelWithPatch() throws Exception {
        // Initialize the database
        tunneldeelRepository.saveAndFlush(tunneldeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tunneldeel using partial update
        Tunneldeel partialUpdatedTunneldeel = new Tunneldeel();
        partialUpdatedTunneldeel.setId(tunneldeel.getId());

        partialUpdatedTunneldeel
            .datumbegingeldigheidtunneldeel(UPDATED_DATUMBEGINGELDIGHEIDTUNNELDEEL)
            .datumeindegeldigheidtunneldeel(UPDATED_DATUMEINDEGELDIGHEIDTUNNELDEEL)
            .geometrietunneldeel(UPDATED_GEOMETRIETUNNELDEEL)
            .lod0geometrietunneldeel(UPDATED_LOD_0_GEOMETRIETUNNELDEEL);

        restTunneldeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTunneldeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTunneldeel))
            )
            .andExpect(status().isOk());

        // Validate the Tunneldeel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTunneldeelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTunneldeel, tunneldeel),
            getPersistedTunneldeel(tunneldeel)
        );
    }

    @Test
    @Transactional
    void fullUpdateTunneldeelWithPatch() throws Exception {
        // Initialize the database
        tunneldeelRepository.saveAndFlush(tunneldeel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tunneldeel using partial update
        Tunneldeel partialUpdatedTunneldeel = new Tunneldeel();
        partialUpdatedTunneldeel.setId(tunneldeel.getId());

        partialUpdatedTunneldeel
            .datumbegingeldigheidtunneldeel(UPDATED_DATUMBEGINGELDIGHEIDTUNNELDEEL)
            .datumeindegeldigheidtunneldeel(UPDATED_DATUMEINDEGELDIGHEIDTUNNELDEEL)
            .geometrietunneldeel(UPDATED_GEOMETRIETUNNELDEEL)
            .identificatietunneldeel(UPDATED_IDENTIFICATIETUNNELDEEL)
            .lod0geometrietunneldeel(UPDATED_LOD_0_GEOMETRIETUNNELDEEL)
            .relatievehoogteliggingtunneldeel(UPDATED_RELATIEVEHOOGTELIGGINGTUNNELDEEL)
            .statustunneldeel(UPDATED_STATUSTUNNELDEEL);

        restTunneldeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTunneldeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTunneldeel))
            )
            .andExpect(status().isOk());

        // Validate the Tunneldeel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTunneldeelUpdatableFieldsEquals(partialUpdatedTunneldeel, getPersistedTunneldeel(partialUpdatedTunneldeel));
    }

    @Test
    @Transactional
    void patchNonExistingTunneldeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tunneldeel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTunneldeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tunneldeel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tunneldeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tunneldeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTunneldeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tunneldeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTunneldeelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tunneldeel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tunneldeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTunneldeel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tunneldeel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTunneldeelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tunneldeel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tunneldeel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTunneldeel() throws Exception {
        // Initialize the database
        tunneldeelRepository.saveAndFlush(tunneldeel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tunneldeel
        restTunneldeelMockMvc
            .perform(delete(ENTITY_API_URL_ID, tunneldeel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tunneldeelRepository.count();
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

    protected Tunneldeel getPersistedTunneldeel(Tunneldeel tunneldeel) {
        return tunneldeelRepository.findById(tunneldeel.getId()).orElseThrow();
    }

    protected void assertPersistedTunneldeelToMatchAllProperties(Tunneldeel expectedTunneldeel) {
        assertTunneldeelAllPropertiesEquals(expectedTunneldeel, getPersistedTunneldeel(expectedTunneldeel));
    }

    protected void assertPersistedTunneldeelToMatchUpdatableProperties(Tunneldeel expectedTunneldeel) {
        assertTunneldeelAllUpdatablePropertiesEquals(expectedTunneldeel, getPersistedTunneldeel(expectedTunneldeel));
    }
}
