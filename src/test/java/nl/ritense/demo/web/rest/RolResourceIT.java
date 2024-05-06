package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.RolAsserts.*;
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
import nl.ritense.demo.domain.Rol;
import nl.ritense.demo.repository.RolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RolResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RolResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rols";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRolMockMvc;

    private Rol rol;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rol createEntity(EntityManager em) {
        Rol rol = new Rol().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return rol;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rol createUpdatedEntity(EntityManager em) {
        Rol rol = new Rol().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return rol;
    }

    @BeforeEach
    public void initTest() {
        rol = createEntity(em);
    }

    @Test
    @Transactional
    void createRol() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Rol
        var returnedRol = om.readValue(
            restRolMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rol)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Rol.class
        );

        // Validate the Rol in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRolUpdatableFieldsEquals(returnedRol, getPersistedRol(returnedRol));
    }

    @Test
    @Transactional
    void createRolWithExistingId() throws Exception {
        // Create the Rol with an existing ID
        rol.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rol)))
            .andExpect(status().isBadRequest());

        // Validate the Rol in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRols() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get all the rolList
        restRolMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rol.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getRol() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        // Get the rol
        restRolMockMvc
            .perform(get(ENTITY_API_URL_ID, rol.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rol.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingRol() throws Exception {
        // Get the rol
        restRolMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRol() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rol
        Rol updatedRol = rolRepository.findById(rol.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRol are not directly saved in db
        em.detach(updatedRol);
        updatedRol.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restRolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRol.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(updatedRol))
            )
            .andExpect(status().isOk());

        // Validate the Rol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRolToMatchAllProperties(updatedRol);
    }

    @Test
    @Transactional
    void putNonExistingRol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rol.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRolMockMvc
            .perform(put(ENTITY_API_URL_ID, rol.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rol)))
            .andExpect(status().isBadRequest());

        // Validate the Rol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rol.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rol.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRolMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rol)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRolWithPatch() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rol using partial update
        Rol partialUpdatedRol = new Rol();
        partialUpdatedRol.setId(rol.getId());

        partialUpdatedRol.naam(UPDATED_NAAM);

        restRolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRol.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRol))
            )
            .andExpect(status().isOk());

        // Validate the Rol in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRolUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedRol, rol), getPersistedRol(rol));
    }

    @Test
    @Transactional
    void fullUpdateRolWithPatch() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rol using partial update
        Rol partialUpdatedRol = new Rol();
        partialUpdatedRol.setId(rol.getId());

        partialUpdatedRol.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restRolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRol.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRol))
            )
            .andExpect(status().isOk());

        // Validate the Rol in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRolUpdatableFieldsEquals(partialUpdatedRol, getPersistedRol(partialUpdatedRol));
    }

    @Test
    @Transactional
    void patchNonExistingRol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rol.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRolMockMvc
            .perform(patch(ENTITY_API_URL_ID, rol.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rol)))
            .andExpect(status().isBadRequest());

        // Validate the Rol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rol.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rol.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRolMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rol)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRol() throws Exception {
        // Initialize the database
        rolRepository.saveAndFlush(rol);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the rol
        restRolMockMvc.perform(delete(ENTITY_API_URL_ID, rol.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return rolRepository.count();
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

    protected Rol getPersistedRol(Rol rol) {
        return rolRepository.findById(rol.getId()).orElseThrow();
    }

    protected void assertPersistedRolToMatchAllProperties(Rol expectedRol) {
        assertRolAllPropertiesEquals(expectedRol, getPersistedRol(expectedRol));
    }

    protected void assertPersistedRolToMatchUpdatableProperties(Rol expectedRol) {
        assertRolAllUpdatablePropertiesEquals(expectedRol, getPersistedRol(expectedRol));
    }
}
