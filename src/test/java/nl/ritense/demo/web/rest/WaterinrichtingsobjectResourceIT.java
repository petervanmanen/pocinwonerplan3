package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.WaterinrichtingsobjectAsserts.*;
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
import nl.ritense.demo.domain.Waterinrichtingsobject;
import nl.ritense.demo.repository.WaterinrichtingsobjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WaterinrichtingsobjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WaterinrichtingsobjectResourceIT {

    private static final String DEFAULT_AANLEGHOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_AANLEGHOOGTE = "BBBBBBBBBB";

    private static final String DEFAULT_BREEDTE = "AAAAAAAAAA";
    private static final String UPDATED_BREEDTE = "BBBBBBBBBB";

    private static final String DEFAULT_JAARCONSERVEREN = "AAAAAAAAAA";
    private static final String UPDATED_JAARCONSERVEREN = "BBBBBBBBBB";

    private static final String DEFAULT_JAARONDERHOUDUITGEVOERD = "AAAAAAAAAA";
    private static final String UPDATED_JAARONDERHOUDUITGEVOERD = "BBBBBBBBBB";

    private static final String DEFAULT_KWALITEITSNIVEAUACTUEEL = "AAAAAAAAAA";
    private static final String UPDATED_KWALITEITSNIVEAUACTUEEL = "BBBBBBBBBB";

    private static final String DEFAULT_KWALITEITSNIVEAUGEWENST = "AAAAAAAAAA";
    private static final String UPDATED_KWALITEITSNIVEAUGEWENST = "BBBBBBBBBB";

    private static final String DEFAULT_LENGTE = "AAAAAAAAAA";
    private static final String UPDATED_LENGTE = "BBBBBBBBBB";

    private static final String DEFAULT_LEVERANCIER = "AAAAAAAAAA";
    private static final String UPDATED_LEVERANCIER = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIAAL = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAAL = "BBBBBBBBBB";

    private static final String DEFAULT_OPPERVLAKTE = "AAAAAAAAAA";
    private static final String UPDATED_OPPERVLAKTE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/waterinrichtingsobjects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WaterinrichtingsobjectRepository waterinrichtingsobjectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWaterinrichtingsobjectMockMvc;

    private Waterinrichtingsobject waterinrichtingsobject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Waterinrichtingsobject createEntity(EntityManager em) {
        Waterinrichtingsobject waterinrichtingsobject = new Waterinrichtingsobject()
            .aanleghoogte(DEFAULT_AANLEGHOOGTE)
            .breedte(DEFAULT_BREEDTE)
            .jaarconserveren(DEFAULT_JAARCONSERVEREN)
            .jaaronderhouduitgevoerd(DEFAULT_JAARONDERHOUDUITGEVOERD)
            .kwaliteitsniveauactueel(DEFAULT_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(DEFAULT_KWALITEITSNIVEAUGEWENST)
            .lengte(DEFAULT_LENGTE)
            .leverancier(DEFAULT_LEVERANCIER)
            .materiaal(DEFAULT_MATERIAAL)
            .oppervlakte(DEFAULT_OPPERVLAKTE);
        return waterinrichtingsobject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Waterinrichtingsobject createUpdatedEntity(EntityManager em) {
        Waterinrichtingsobject waterinrichtingsobject = new Waterinrichtingsobject()
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .breedte(UPDATED_BREEDTE)
            .jaarconserveren(UPDATED_JAARCONSERVEREN)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .materiaal(UPDATED_MATERIAAL)
            .oppervlakte(UPDATED_OPPERVLAKTE);
        return waterinrichtingsobject;
    }

    @BeforeEach
    public void initTest() {
        waterinrichtingsobject = createEntity(em);
    }

    @Test
    @Transactional
    void createWaterinrichtingsobject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Waterinrichtingsobject
        var returnedWaterinrichtingsobject = om.readValue(
            restWaterinrichtingsobjectMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(waterinrichtingsobject)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Waterinrichtingsobject.class
        );

        // Validate the Waterinrichtingsobject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertWaterinrichtingsobjectUpdatableFieldsEquals(
            returnedWaterinrichtingsobject,
            getPersistedWaterinrichtingsobject(returnedWaterinrichtingsobject)
        );
    }

    @Test
    @Transactional
    void createWaterinrichtingsobjectWithExistingId() throws Exception {
        // Create the Waterinrichtingsobject with an existing ID
        waterinrichtingsobject.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWaterinrichtingsobjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(waterinrichtingsobject)))
            .andExpect(status().isBadRequest());

        // Validate the Waterinrichtingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWaterinrichtingsobjects() throws Exception {
        // Initialize the database
        waterinrichtingsobjectRepository.saveAndFlush(waterinrichtingsobject);

        // Get all the waterinrichtingsobjectList
        restWaterinrichtingsobjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(waterinrichtingsobject.getId().intValue())))
            .andExpect(jsonPath("$.[*].aanleghoogte").value(hasItem(DEFAULT_AANLEGHOOGTE)))
            .andExpect(jsonPath("$.[*].breedte").value(hasItem(DEFAULT_BREEDTE)))
            .andExpect(jsonPath("$.[*].jaarconserveren").value(hasItem(DEFAULT_JAARCONSERVEREN)))
            .andExpect(jsonPath("$.[*].jaaronderhouduitgevoerd").value(hasItem(DEFAULT_JAARONDERHOUDUITGEVOERD)))
            .andExpect(jsonPath("$.[*].kwaliteitsniveauactueel").value(hasItem(DEFAULT_KWALITEITSNIVEAUACTUEEL)))
            .andExpect(jsonPath("$.[*].kwaliteitsniveaugewenst").value(hasItem(DEFAULT_KWALITEITSNIVEAUGEWENST)))
            .andExpect(jsonPath("$.[*].lengte").value(hasItem(DEFAULT_LENGTE)))
            .andExpect(jsonPath("$.[*].leverancier").value(hasItem(DEFAULT_LEVERANCIER)))
            .andExpect(jsonPath("$.[*].materiaal").value(hasItem(DEFAULT_MATERIAAL)))
            .andExpect(jsonPath("$.[*].oppervlakte").value(hasItem(DEFAULT_OPPERVLAKTE)));
    }

    @Test
    @Transactional
    void getWaterinrichtingsobject() throws Exception {
        // Initialize the database
        waterinrichtingsobjectRepository.saveAndFlush(waterinrichtingsobject);

        // Get the waterinrichtingsobject
        restWaterinrichtingsobjectMockMvc
            .perform(get(ENTITY_API_URL_ID, waterinrichtingsobject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(waterinrichtingsobject.getId().intValue()))
            .andExpect(jsonPath("$.aanleghoogte").value(DEFAULT_AANLEGHOOGTE))
            .andExpect(jsonPath("$.breedte").value(DEFAULT_BREEDTE))
            .andExpect(jsonPath("$.jaarconserveren").value(DEFAULT_JAARCONSERVEREN))
            .andExpect(jsonPath("$.jaaronderhouduitgevoerd").value(DEFAULT_JAARONDERHOUDUITGEVOERD))
            .andExpect(jsonPath("$.kwaliteitsniveauactueel").value(DEFAULT_KWALITEITSNIVEAUACTUEEL))
            .andExpect(jsonPath("$.kwaliteitsniveaugewenst").value(DEFAULT_KWALITEITSNIVEAUGEWENST))
            .andExpect(jsonPath("$.lengte").value(DEFAULT_LENGTE))
            .andExpect(jsonPath("$.leverancier").value(DEFAULT_LEVERANCIER))
            .andExpect(jsonPath("$.materiaal").value(DEFAULT_MATERIAAL))
            .andExpect(jsonPath("$.oppervlakte").value(DEFAULT_OPPERVLAKTE));
    }

    @Test
    @Transactional
    void getNonExistingWaterinrichtingsobject() throws Exception {
        // Get the waterinrichtingsobject
        restWaterinrichtingsobjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWaterinrichtingsobject() throws Exception {
        // Initialize the database
        waterinrichtingsobjectRepository.saveAndFlush(waterinrichtingsobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the waterinrichtingsobject
        Waterinrichtingsobject updatedWaterinrichtingsobject = waterinrichtingsobjectRepository
            .findById(waterinrichtingsobject.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedWaterinrichtingsobject are not directly saved in db
        em.detach(updatedWaterinrichtingsobject);
        updatedWaterinrichtingsobject
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .breedte(UPDATED_BREEDTE)
            .jaarconserveren(UPDATED_JAARCONSERVEREN)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .materiaal(UPDATED_MATERIAAL)
            .oppervlakte(UPDATED_OPPERVLAKTE);

        restWaterinrichtingsobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWaterinrichtingsobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedWaterinrichtingsobject))
            )
            .andExpect(status().isOk());

        // Validate the Waterinrichtingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWaterinrichtingsobjectToMatchAllProperties(updatedWaterinrichtingsobject);
    }

    @Test
    @Transactional
    void putNonExistingWaterinrichtingsobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        waterinrichtingsobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWaterinrichtingsobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, waterinrichtingsobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(waterinrichtingsobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Waterinrichtingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWaterinrichtingsobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        waterinrichtingsobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWaterinrichtingsobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(waterinrichtingsobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Waterinrichtingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWaterinrichtingsobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        waterinrichtingsobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWaterinrichtingsobjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(waterinrichtingsobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Waterinrichtingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWaterinrichtingsobjectWithPatch() throws Exception {
        // Initialize the database
        waterinrichtingsobjectRepository.saveAndFlush(waterinrichtingsobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the waterinrichtingsobject using partial update
        Waterinrichtingsobject partialUpdatedWaterinrichtingsobject = new Waterinrichtingsobject();
        partialUpdatedWaterinrichtingsobject.setId(waterinrichtingsobject.getId());

        partialUpdatedWaterinrichtingsobject
            .jaarconserveren(UPDATED_JAARCONSERVEREN)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .leverancier(UPDATED_LEVERANCIER)
            .materiaal(UPDATED_MATERIAAL);

        restWaterinrichtingsobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWaterinrichtingsobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWaterinrichtingsobject))
            )
            .andExpect(status().isOk());

        // Validate the Waterinrichtingsobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWaterinrichtingsobjectUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedWaterinrichtingsobject, waterinrichtingsobject),
            getPersistedWaterinrichtingsobject(waterinrichtingsobject)
        );
    }

    @Test
    @Transactional
    void fullUpdateWaterinrichtingsobjectWithPatch() throws Exception {
        // Initialize the database
        waterinrichtingsobjectRepository.saveAndFlush(waterinrichtingsobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the waterinrichtingsobject using partial update
        Waterinrichtingsobject partialUpdatedWaterinrichtingsobject = new Waterinrichtingsobject();
        partialUpdatedWaterinrichtingsobject.setId(waterinrichtingsobject.getId());

        partialUpdatedWaterinrichtingsobject
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .breedte(UPDATED_BREEDTE)
            .jaarconserveren(UPDATED_JAARCONSERVEREN)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .materiaal(UPDATED_MATERIAAL)
            .oppervlakte(UPDATED_OPPERVLAKTE);

        restWaterinrichtingsobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWaterinrichtingsobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWaterinrichtingsobject))
            )
            .andExpect(status().isOk());

        // Validate the Waterinrichtingsobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWaterinrichtingsobjectUpdatableFieldsEquals(
            partialUpdatedWaterinrichtingsobject,
            getPersistedWaterinrichtingsobject(partialUpdatedWaterinrichtingsobject)
        );
    }

    @Test
    @Transactional
    void patchNonExistingWaterinrichtingsobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        waterinrichtingsobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWaterinrichtingsobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, waterinrichtingsobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(waterinrichtingsobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Waterinrichtingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWaterinrichtingsobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        waterinrichtingsobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWaterinrichtingsobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(waterinrichtingsobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Waterinrichtingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWaterinrichtingsobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        waterinrichtingsobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWaterinrichtingsobjectMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(waterinrichtingsobject))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Waterinrichtingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWaterinrichtingsobject() throws Exception {
        // Initialize the database
        waterinrichtingsobjectRepository.saveAndFlush(waterinrichtingsobject);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the waterinrichtingsobject
        restWaterinrichtingsobjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, waterinrichtingsobject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return waterinrichtingsobjectRepository.count();
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

    protected Waterinrichtingsobject getPersistedWaterinrichtingsobject(Waterinrichtingsobject waterinrichtingsobject) {
        return waterinrichtingsobjectRepository.findById(waterinrichtingsobject.getId()).orElseThrow();
    }

    protected void assertPersistedWaterinrichtingsobjectToMatchAllProperties(Waterinrichtingsobject expectedWaterinrichtingsobject) {
        assertWaterinrichtingsobjectAllPropertiesEquals(
            expectedWaterinrichtingsobject,
            getPersistedWaterinrichtingsobject(expectedWaterinrichtingsobject)
        );
    }

    protected void assertPersistedWaterinrichtingsobjectToMatchUpdatableProperties(Waterinrichtingsobject expectedWaterinrichtingsobject) {
        assertWaterinrichtingsobjectAllUpdatablePropertiesEquals(
            expectedWaterinrichtingsobject,
            getPersistedWaterinrichtingsobject(expectedWaterinrichtingsobject)
        );
    }
}
