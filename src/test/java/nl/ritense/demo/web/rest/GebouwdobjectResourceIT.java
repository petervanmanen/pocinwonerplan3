package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.GebouwdobjectAsserts.*;
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
import nl.ritense.demo.domain.Gebouwdobject;
import nl.ritense.demo.repository.GebouwdobjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GebouwdobjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GebouwdobjectResourceIT {

    private static final String DEFAULT_BOUWKUNDIGEBESTEMMINGACTUEEL = "AAAAAAAAAA";
    private static final String UPDATED_BOUWKUNDIGEBESTEMMINGACTUEEL = "BBBBBBBBBB";

    private static final String DEFAULT_BRUTOINHOUD = "AAAAAAAAAA";
    private static final String UPDATED_BRUTOINHOUD = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIE = "BBBBBBBBBB";

    private static final String DEFAULT_INWINNINGOPPERVLAKTE = "AAAAAAAAAA";
    private static final String UPDATED_INWINNINGOPPERVLAKTE = "BBBBBBBBBB";

    private static final String DEFAULT_OPPERVLAKTEOBJECT = "AAAAAAAAAA";
    private static final String UPDATED_OPPERVLAKTEOBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_STATUSVOORTGANGBOUW = "AAAAAAAAAA";
    private static final String UPDATED_STATUSVOORTGANGBOUW = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/gebouwdobjects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GebouwdobjectRepository gebouwdobjectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGebouwdobjectMockMvc;

    private Gebouwdobject gebouwdobject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gebouwdobject createEntity(EntityManager em) {
        Gebouwdobject gebouwdobject = new Gebouwdobject()
            .bouwkundigebestemmingactueel(DEFAULT_BOUWKUNDIGEBESTEMMINGACTUEEL)
            .brutoinhoud(DEFAULT_BRUTOINHOUD)
            .identificatie(DEFAULT_IDENTIFICATIE)
            .inwinningoppervlakte(DEFAULT_INWINNINGOPPERVLAKTE)
            .oppervlakteobject(DEFAULT_OPPERVLAKTEOBJECT)
            .statusvoortgangbouw(DEFAULT_STATUSVOORTGANGBOUW);
        return gebouwdobject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gebouwdobject createUpdatedEntity(EntityManager em) {
        Gebouwdobject gebouwdobject = new Gebouwdobject()
            .bouwkundigebestemmingactueel(UPDATED_BOUWKUNDIGEBESTEMMINGACTUEEL)
            .brutoinhoud(UPDATED_BRUTOINHOUD)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inwinningoppervlakte(UPDATED_INWINNINGOPPERVLAKTE)
            .oppervlakteobject(UPDATED_OPPERVLAKTEOBJECT)
            .statusvoortgangbouw(UPDATED_STATUSVOORTGANGBOUW);
        return gebouwdobject;
    }

    @BeforeEach
    public void initTest() {
        gebouwdobject = createEntity(em);
    }

    @Test
    @Transactional
    void createGebouwdobject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Gebouwdobject
        var returnedGebouwdobject = om.readValue(
            restGebouwdobjectMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gebouwdobject)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Gebouwdobject.class
        );

        // Validate the Gebouwdobject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGebouwdobjectUpdatableFieldsEquals(returnedGebouwdobject, getPersistedGebouwdobject(returnedGebouwdobject));
    }

    @Test
    @Transactional
    void createGebouwdobjectWithExistingId() throws Exception {
        // Create the Gebouwdobject with an existing ID
        gebouwdobject.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGebouwdobjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gebouwdobject)))
            .andExpect(status().isBadRequest());

        // Validate the Gebouwdobject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGebouwdobjects() throws Exception {
        // Initialize the database
        gebouwdobjectRepository.saveAndFlush(gebouwdobject);

        // Get all the gebouwdobjectList
        restGebouwdobjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gebouwdobject.getId().intValue())))
            .andExpect(jsonPath("$.[*].bouwkundigebestemmingactueel").value(hasItem(DEFAULT_BOUWKUNDIGEBESTEMMINGACTUEEL)))
            .andExpect(jsonPath("$.[*].brutoinhoud").value(hasItem(DEFAULT_BRUTOINHOUD)))
            .andExpect(jsonPath("$.[*].identificatie").value(hasItem(DEFAULT_IDENTIFICATIE)))
            .andExpect(jsonPath("$.[*].inwinningoppervlakte").value(hasItem(DEFAULT_INWINNINGOPPERVLAKTE)))
            .andExpect(jsonPath("$.[*].oppervlakteobject").value(hasItem(DEFAULT_OPPERVLAKTEOBJECT)))
            .andExpect(jsonPath("$.[*].statusvoortgangbouw").value(hasItem(DEFAULT_STATUSVOORTGANGBOUW)));
    }

    @Test
    @Transactional
    void getGebouwdobject() throws Exception {
        // Initialize the database
        gebouwdobjectRepository.saveAndFlush(gebouwdobject);

        // Get the gebouwdobject
        restGebouwdobjectMockMvc
            .perform(get(ENTITY_API_URL_ID, gebouwdobject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gebouwdobject.getId().intValue()))
            .andExpect(jsonPath("$.bouwkundigebestemmingactueel").value(DEFAULT_BOUWKUNDIGEBESTEMMINGACTUEEL))
            .andExpect(jsonPath("$.brutoinhoud").value(DEFAULT_BRUTOINHOUD))
            .andExpect(jsonPath("$.identificatie").value(DEFAULT_IDENTIFICATIE))
            .andExpect(jsonPath("$.inwinningoppervlakte").value(DEFAULT_INWINNINGOPPERVLAKTE))
            .andExpect(jsonPath("$.oppervlakteobject").value(DEFAULT_OPPERVLAKTEOBJECT))
            .andExpect(jsonPath("$.statusvoortgangbouw").value(DEFAULT_STATUSVOORTGANGBOUW));
    }

    @Test
    @Transactional
    void getNonExistingGebouwdobject() throws Exception {
        // Get the gebouwdobject
        restGebouwdobjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGebouwdobject() throws Exception {
        // Initialize the database
        gebouwdobjectRepository.saveAndFlush(gebouwdobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gebouwdobject
        Gebouwdobject updatedGebouwdobject = gebouwdobjectRepository.findById(gebouwdobject.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGebouwdobject are not directly saved in db
        em.detach(updatedGebouwdobject);
        updatedGebouwdobject
            .bouwkundigebestemmingactueel(UPDATED_BOUWKUNDIGEBESTEMMINGACTUEEL)
            .brutoinhoud(UPDATED_BRUTOINHOUD)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inwinningoppervlakte(UPDATED_INWINNINGOPPERVLAKTE)
            .oppervlakteobject(UPDATED_OPPERVLAKTEOBJECT)
            .statusvoortgangbouw(UPDATED_STATUSVOORTGANGBOUW);

        restGebouwdobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGebouwdobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGebouwdobject))
            )
            .andExpect(status().isOk());

        // Validate the Gebouwdobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGebouwdobjectToMatchAllProperties(updatedGebouwdobject);
    }

    @Test
    @Transactional
    void putNonExistingGebouwdobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebouwdobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGebouwdobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gebouwdobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gebouwdobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gebouwdobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGebouwdobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebouwdobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGebouwdobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gebouwdobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gebouwdobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGebouwdobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebouwdobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGebouwdobjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gebouwdobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gebouwdobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGebouwdobjectWithPatch() throws Exception {
        // Initialize the database
        gebouwdobjectRepository.saveAndFlush(gebouwdobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gebouwdobject using partial update
        Gebouwdobject partialUpdatedGebouwdobject = new Gebouwdobject();
        partialUpdatedGebouwdobject.setId(gebouwdobject.getId());

        partialUpdatedGebouwdobject.bouwkundigebestemmingactueel(UPDATED_BOUWKUNDIGEBESTEMMINGACTUEEL).identificatie(UPDATED_IDENTIFICATIE);

        restGebouwdobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGebouwdobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGebouwdobject))
            )
            .andExpect(status().isOk());

        // Validate the Gebouwdobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGebouwdobjectUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGebouwdobject, gebouwdobject),
            getPersistedGebouwdobject(gebouwdobject)
        );
    }

    @Test
    @Transactional
    void fullUpdateGebouwdobjectWithPatch() throws Exception {
        // Initialize the database
        gebouwdobjectRepository.saveAndFlush(gebouwdobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gebouwdobject using partial update
        Gebouwdobject partialUpdatedGebouwdobject = new Gebouwdobject();
        partialUpdatedGebouwdobject.setId(gebouwdobject.getId());

        partialUpdatedGebouwdobject
            .bouwkundigebestemmingactueel(UPDATED_BOUWKUNDIGEBESTEMMINGACTUEEL)
            .brutoinhoud(UPDATED_BRUTOINHOUD)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inwinningoppervlakte(UPDATED_INWINNINGOPPERVLAKTE)
            .oppervlakteobject(UPDATED_OPPERVLAKTEOBJECT)
            .statusvoortgangbouw(UPDATED_STATUSVOORTGANGBOUW);

        restGebouwdobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGebouwdobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGebouwdobject))
            )
            .andExpect(status().isOk());

        // Validate the Gebouwdobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGebouwdobjectUpdatableFieldsEquals(partialUpdatedGebouwdobject, getPersistedGebouwdobject(partialUpdatedGebouwdobject));
    }

    @Test
    @Transactional
    void patchNonExistingGebouwdobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebouwdobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGebouwdobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gebouwdobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gebouwdobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gebouwdobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGebouwdobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebouwdobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGebouwdobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gebouwdobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gebouwdobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGebouwdobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gebouwdobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGebouwdobjectMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(gebouwdobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gebouwdobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGebouwdobject() throws Exception {
        // Initialize the database
        gebouwdobjectRepository.saveAndFlush(gebouwdobject);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the gebouwdobject
        restGebouwdobjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, gebouwdobject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return gebouwdobjectRepository.count();
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

    protected Gebouwdobject getPersistedGebouwdobject(Gebouwdobject gebouwdobject) {
        return gebouwdobjectRepository.findById(gebouwdobject.getId()).orElseThrow();
    }

    protected void assertPersistedGebouwdobjectToMatchAllProperties(Gebouwdobject expectedGebouwdobject) {
        assertGebouwdobjectAllPropertiesEquals(expectedGebouwdobject, getPersistedGebouwdobject(expectedGebouwdobject));
    }

    protected void assertPersistedGebouwdobjectToMatchUpdatableProperties(Gebouwdobject expectedGebouwdobject) {
        assertGebouwdobjectAllUpdatablePropertiesEquals(expectedGebouwdobject, getPersistedGebouwdobject(expectedGebouwdobject));
    }
}
