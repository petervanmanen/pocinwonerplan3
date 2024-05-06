package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.WeginrichtingsobjectAsserts.*;
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
import nl.ritense.demo.domain.Weginrichtingsobject;
import nl.ritense.demo.repository.WeginrichtingsobjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WeginrichtingsobjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WeginrichtingsobjectResourceIT {

    private static final String DEFAULT_AANLEGHOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_AANLEGHOOGTE = "BBBBBBBBBB";

    private static final String DEFAULT_BREEDTE = "AAAAAAAAAA";
    private static final String UPDATED_BREEDTE = "BBBBBBBBBB";

    private static final String DEFAULT_HOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_HOOGTE = "BBBBBBBBBB";

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

    private static final String DEFAULT_WEGINRICHTINGSOBJECTWEGFUNCTIE = "AAAAAAAAAA";
    private static final String UPDATED_WEGINRICHTINGSOBJECTWEGFUNCTIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/weginrichtingsobjects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WeginrichtingsobjectRepository weginrichtingsobjectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWeginrichtingsobjectMockMvc;

    private Weginrichtingsobject weginrichtingsobject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Weginrichtingsobject createEntity(EntityManager em) {
        Weginrichtingsobject weginrichtingsobject = new Weginrichtingsobject()
            .aanleghoogte(DEFAULT_AANLEGHOOGTE)
            .breedte(DEFAULT_BREEDTE)
            .hoogte(DEFAULT_HOOGTE)
            .jaarconserveren(DEFAULT_JAARCONSERVEREN)
            .jaaronderhouduitgevoerd(DEFAULT_JAARONDERHOUDUITGEVOERD)
            .kwaliteitsniveauactueel(DEFAULT_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(DEFAULT_KWALITEITSNIVEAUGEWENST)
            .lengte(DEFAULT_LENGTE)
            .leverancier(DEFAULT_LEVERANCIER)
            .materiaal(DEFAULT_MATERIAAL)
            .oppervlakte(DEFAULT_OPPERVLAKTE)
            .weginrichtingsobjectwegfunctie(DEFAULT_WEGINRICHTINGSOBJECTWEGFUNCTIE);
        return weginrichtingsobject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Weginrichtingsobject createUpdatedEntity(EntityManager em) {
        Weginrichtingsobject weginrichtingsobject = new Weginrichtingsobject()
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .breedte(UPDATED_BREEDTE)
            .hoogte(UPDATED_HOOGTE)
            .jaarconserveren(UPDATED_JAARCONSERVEREN)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .materiaal(UPDATED_MATERIAAL)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .weginrichtingsobjectwegfunctie(UPDATED_WEGINRICHTINGSOBJECTWEGFUNCTIE);
        return weginrichtingsobject;
    }

    @BeforeEach
    public void initTest() {
        weginrichtingsobject = createEntity(em);
    }

    @Test
    @Transactional
    void createWeginrichtingsobject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Weginrichtingsobject
        var returnedWeginrichtingsobject = om.readValue(
            restWeginrichtingsobjectMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(weginrichtingsobject)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Weginrichtingsobject.class
        );

        // Validate the Weginrichtingsobject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertWeginrichtingsobjectUpdatableFieldsEquals(
            returnedWeginrichtingsobject,
            getPersistedWeginrichtingsobject(returnedWeginrichtingsobject)
        );
    }

    @Test
    @Transactional
    void createWeginrichtingsobjectWithExistingId() throws Exception {
        // Create the Weginrichtingsobject with an existing ID
        weginrichtingsobject.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWeginrichtingsobjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(weginrichtingsobject)))
            .andExpect(status().isBadRequest());

        // Validate the Weginrichtingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWeginrichtingsobjects() throws Exception {
        // Initialize the database
        weginrichtingsobjectRepository.saveAndFlush(weginrichtingsobject);

        // Get all the weginrichtingsobjectList
        restWeginrichtingsobjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(weginrichtingsobject.getId().intValue())))
            .andExpect(jsonPath("$.[*].aanleghoogte").value(hasItem(DEFAULT_AANLEGHOOGTE)))
            .andExpect(jsonPath("$.[*].breedte").value(hasItem(DEFAULT_BREEDTE)))
            .andExpect(jsonPath("$.[*].hoogte").value(hasItem(DEFAULT_HOOGTE)))
            .andExpect(jsonPath("$.[*].jaarconserveren").value(hasItem(DEFAULT_JAARCONSERVEREN)))
            .andExpect(jsonPath("$.[*].jaaronderhouduitgevoerd").value(hasItem(DEFAULT_JAARONDERHOUDUITGEVOERD)))
            .andExpect(jsonPath("$.[*].kwaliteitsniveauactueel").value(hasItem(DEFAULT_KWALITEITSNIVEAUACTUEEL)))
            .andExpect(jsonPath("$.[*].kwaliteitsniveaugewenst").value(hasItem(DEFAULT_KWALITEITSNIVEAUGEWENST)))
            .andExpect(jsonPath("$.[*].lengte").value(hasItem(DEFAULT_LENGTE)))
            .andExpect(jsonPath("$.[*].leverancier").value(hasItem(DEFAULT_LEVERANCIER)))
            .andExpect(jsonPath("$.[*].materiaal").value(hasItem(DEFAULT_MATERIAAL)))
            .andExpect(jsonPath("$.[*].oppervlakte").value(hasItem(DEFAULT_OPPERVLAKTE)))
            .andExpect(jsonPath("$.[*].weginrichtingsobjectwegfunctie").value(hasItem(DEFAULT_WEGINRICHTINGSOBJECTWEGFUNCTIE)));
    }

    @Test
    @Transactional
    void getWeginrichtingsobject() throws Exception {
        // Initialize the database
        weginrichtingsobjectRepository.saveAndFlush(weginrichtingsobject);

        // Get the weginrichtingsobject
        restWeginrichtingsobjectMockMvc
            .perform(get(ENTITY_API_URL_ID, weginrichtingsobject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(weginrichtingsobject.getId().intValue()))
            .andExpect(jsonPath("$.aanleghoogte").value(DEFAULT_AANLEGHOOGTE))
            .andExpect(jsonPath("$.breedte").value(DEFAULT_BREEDTE))
            .andExpect(jsonPath("$.hoogte").value(DEFAULT_HOOGTE))
            .andExpect(jsonPath("$.jaarconserveren").value(DEFAULT_JAARCONSERVEREN))
            .andExpect(jsonPath("$.jaaronderhouduitgevoerd").value(DEFAULT_JAARONDERHOUDUITGEVOERD))
            .andExpect(jsonPath("$.kwaliteitsniveauactueel").value(DEFAULT_KWALITEITSNIVEAUACTUEEL))
            .andExpect(jsonPath("$.kwaliteitsniveaugewenst").value(DEFAULT_KWALITEITSNIVEAUGEWENST))
            .andExpect(jsonPath("$.lengte").value(DEFAULT_LENGTE))
            .andExpect(jsonPath("$.leverancier").value(DEFAULT_LEVERANCIER))
            .andExpect(jsonPath("$.materiaal").value(DEFAULT_MATERIAAL))
            .andExpect(jsonPath("$.oppervlakte").value(DEFAULT_OPPERVLAKTE))
            .andExpect(jsonPath("$.weginrichtingsobjectwegfunctie").value(DEFAULT_WEGINRICHTINGSOBJECTWEGFUNCTIE));
    }

    @Test
    @Transactional
    void getNonExistingWeginrichtingsobject() throws Exception {
        // Get the weginrichtingsobject
        restWeginrichtingsobjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWeginrichtingsobject() throws Exception {
        // Initialize the database
        weginrichtingsobjectRepository.saveAndFlush(weginrichtingsobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the weginrichtingsobject
        Weginrichtingsobject updatedWeginrichtingsobject = weginrichtingsobjectRepository
            .findById(weginrichtingsobject.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedWeginrichtingsobject are not directly saved in db
        em.detach(updatedWeginrichtingsobject);
        updatedWeginrichtingsobject
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .breedte(UPDATED_BREEDTE)
            .hoogte(UPDATED_HOOGTE)
            .jaarconserveren(UPDATED_JAARCONSERVEREN)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .materiaal(UPDATED_MATERIAAL)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .weginrichtingsobjectwegfunctie(UPDATED_WEGINRICHTINGSOBJECTWEGFUNCTIE);

        restWeginrichtingsobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWeginrichtingsobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedWeginrichtingsobject))
            )
            .andExpect(status().isOk());

        // Validate the Weginrichtingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWeginrichtingsobjectToMatchAllProperties(updatedWeginrichtingsobject);
    }

    @Test
    @Transactional
    void putNonExistingWeginrichtingsobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        weginrichtingsobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWeginrichtingsobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, weginrichtingsobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(weginrichtingsobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Weginrichtingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWeginrichtingsobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        weginrichtingsobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWeginrichtingsobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(weginrichtingsobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Weginrichtingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWeginrichtingsobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        weginrichtingsobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWeginrichtingsobjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(weginrichtingsobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Weginrichtingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWeginrichtingsobjectWithPatch() throws Exception {
        // Initialize the database
        weginrichtingsobjectRepository.saveAndFlush(weginrichtingsobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the weginrichtingsobject using partial update
        Weginrichtingsobject partialUpdatedWeginrichtingsobject = new Weginrichtingsobject();
        partialUpdatedWeginrichtingsobject.setId(weginrichtingsobject.getId());

        partialUpdatedWeginrichtingsobject
            .breedte(UPDATED_BREEDTE)
            .jaarconserveren(UPDATED_JAARCONSERVEREN)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .leverancier(UPDATED_LEVERANCIER)
            .materiaal(UPDATED_MATERIAAL)
            .weginrichtingsobjectwegfunctie(UPDATED_WEGINRICHTINGSOBJECTWEGFUNCTIE);

        restWeginrichtingsobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWeginrichtingsobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWeginrichtingsobject))
            )
            .andExpect(status().isOk());

        // Validate the Weginrichtingsobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWeginrichtingsobjectUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedWeginrichtingsobject, weginrichtingsobject),
            getPersistedWeginrichtingsobject(weginrichtingsobject)
        );
    }

    @Test
    @Transactional
    void fullUpdateWeginrichtingsobjectWithPatch() throws Exception {
        // Initialize the database
        weginrichtingsobjectRepository.saveAndFlush(weginrichtingsobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the weginrichtingsobject using partial update
        Weginrichtingsobject partialUpdatedWeginrichtingsobject = new Weginrichtingsobject();
        partialUpdatedWeginrichtingsobject.setId(weginrichtingsobject.getId());

        partialUpdatedWeginrichtingsobject
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .breedte(UPDATED_BREEDTE)
            .hoogte(UPDATED_HOOGTE)
            .jaarconserveren(UPDATED_JAARCONSERVEREN)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .materiaal(UPDATED_MATERIAAL)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .weginrichtingsobjectwegfunctie(UPDATED_WEGINRICHTINGSOBJECTWEGFUNCTIE);

        restWeginrichtingsobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWeginrichtingsobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWeginrichtingsobject))
            )
            .andExpect(status().isOk());

        // Validate the Weginrichtingsobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWeginrichtingsobjectUpdatableFieldsEquals(
            partialUpdatedWeginrichtingsobject,
            getPersistedWeginrichtingsobject(partialUpdatedWeginrichtingsobject)
        );
    }

    @Test
    @Transactional
    void patchNonExistingWeginrichtingsobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        weginrichtingsobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWeginrichtingsobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, weginrichtingsobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(weginrichtingsobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Weginrichtingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWeginrichtingsobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        weginrichtingsobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWeginrichtingsobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(weginrichtingsobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Weginrichtingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWeginrichtingsobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        weginrichtingsobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWeginrichtingsobjectMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(weginrichtingsobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Weginrichtingsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWeginrichtingsobject() throws Exception {
        // Initialize the database
        weginrichtingsobjectRepository.saveAndFlush(weginrichtingsobject);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the weginrichtingsobject
        restWeginrichtingsobjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, weginrichtingsobject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return weginrichtingsobjectRepository.count();
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

    protected Weginrichtingsobject getPersistedWeginrichtingsobject(Weginrichtingsobject weginrichtingsobject) {
        return weginrichtingsobjectRepository.findById(weginrichtingsobject.getId()).orElseThrow();
    }

    protected void assertPersistedWeginrichtingsobjectToMatchAllProperties(Weginrichtingsobject expectedWeginrichtingsobject) {
        assertWeginrichtingsobjectAllPropertiesEquals(
            expectedWeginrichtingsobject,
            getPersistedWeginrichtingsobject(expectedWeginrichtingsobject)
        );
    }

    protected void assertPersistedWeginrichtingsobjectToMatchUpdatableProperties(Weginrichtingsobject expectedWeginrichtingsobject) {
        assertWeginrichtingsobjectAllUpdatablePropertiesEquals(
            expectedWeginrichtingsobject,
            getPersistedWeginrichtingsobject(expectedWeginrichtingsobject)
        );
    }
}
