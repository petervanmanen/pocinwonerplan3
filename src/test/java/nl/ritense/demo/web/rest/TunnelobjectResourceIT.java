package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.TunnelobjectAsserts.*;
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
import nl.ritense.demo.domain.Tunnelobject;
import nl.ritense.demo.repository.TunnelobjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TunnelobjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TunnelobjectResourceIT {

    private static final String DEFAULT_AANLEGHOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_AANLEGHOOGTE = "BBBBBBBBBB";

    private static final String DEFAULT_AANTALTUNNELBUIZEN = "AAAAAAAAAA";
    private static final String UPDATED_AANTALTUNNELBUIZEN = "BBBBBBBBBB";

    private static final String DEFAULT_BREEDTE = "AAAAAAAAAA";
    private static final String UPDATED_BREEDTE = "BBBBBBBBBB";

    private static final String DEFAULT_DOORRIJBREEDTE = "AAAAAAAAAA";
    private static final String UPDATED_DOORRIJBREEDTE = "BBBBBBBBBB";

    private static final String DEFAULT_DOORRIJHOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_DOORRIJHOOGTE = "BBBBBBBBBB";

    private static final String DEFAULT_HOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_HOOGTE = "BBBBBBBBBB";

    private static final String DEFAULT_JAARCONSERVEREN = "AAAAAAAAAA";
    private static final String UPDATED_JAARCONSERVEREN = "BBBBBBBBBB";

    private static final String DEFAULT_JAARONDERHOUDUITGEVOERD = "AAAAAAAAAA";
    private static final String UPDATED_JAARONDERHOUDUITGEVOERD = "BBBBBBBBBB";

    private static final String DEFAULT_LENGTE = "AAAAAAAAAA";
    private static final String UPDATED_LENGTE = "BBBBBBBBBB";

    private static final String DEFAULT_LEVERANCIER = "AAAAAAAAAA";
    private static final String UPDATED_LEVERANCIER = "BBBBBBBBBB";

    private static final String DEFAULT_EOBJECTNAAM = "AAAAAAAAAA";
    private static final String UPDATED_EOBJECTNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_EOBJECTNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_EOBJECTNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_OPPERVLAKTE = "AAAAAAAAAA";
    private static final String UPDATED_OPPERVLAKTE = "BBBBBBBBBB";

    private static final String DEFAULT_TUNNELOBJECTMATERIAAL = "AAAAAAAAAA";
    private static final String UPDATED_TUNNELOBJECTMATERIAAL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tunnelobjects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TunnelobjectRepository tunnelobjectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTunnelobjectMockMvc;

    private Tunnelobject tunnelobject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tunnelobject createEntity(EntityManager em) {
        Tunnelobject tunnelobject = new Tunnelobject()
            .aanleghoogte(DEFAULT_AANLEGHOOGTE)
            .aantaltunnelbuizen(DEFAULT_AANTALTUNNELBUIZEN)
            .breedte(DEFAULT_BREEDTE)
            .doorrijbreedte(DEFAULT_DOORRIJBREEDTE)
            .doorrijhoogte(DEFAULT_DOORRIJHOOGTE)
            .hoogte(DEFAULT_HOOGTE)
            .jaarconserveren(DEFAULT_JAARCONSERVEREN)
            .jaaronderhouduitgevoerd(DEFAULT_JAARONDERHOUDUITGEVOERD)
            .lengte(DEFAULT_LENGTE)
            .leverancier(DEFAULT_LEVERANCIER)
            .eobjectnaam(DEFAULT_EOBJECTNAAM)
            .eobjectnummer(DEFAULT_EOBJECTNUMMER)
            .oppervlakte(DEFAULT_OPPERVLAKTE)
            .tunnelobjectmateriaal(DEFAULT_TUNNELOBJECTMATERIAAL);
        return tunnelobject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tunnelobject createUpdatedEntity(EntityManager em) {
        Tunnelobject tunnelobject = new Tunnelobject()
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .aantaltunnelbuizen(UPDATED_AANTALTUNNELBUIZEN)
            .breedte(UPDATED_BREEDTE)
            .doorrijbreedte(UPDATED_DOORRIJBREEDTE)
            .doorrijhoogte(UPDATED_DOORRIJHOOGTE)
            .hoogte(UPDATED_HOOGTE)
            .jaarconserveren(UPDATED_JAARCONSERVEREN)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .eobjectnaam(UPDATED_EOBJECTNAAM)
            .eobjectnummer(UPDATED_EOBJECTNUMMER)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .tunnelobjectmateriaal(UPDATED_TUNNELOBJECTMATERIAAL);
        return tunnelobject;
    }

    @BeforeEach
    public void initTest() {
        tunnelobject = createEntity(em);
    }

    @Test
    @Transactional
    void createTunnelobject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Tunnelobject
        var returnedTunnelobject = om.readValue(
            restTunnelobjectMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tunnelobject)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Tunnelobject.class
        );

        // Validate the Tunnelobject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTunnelobjectUpdatableFieldsEquals(returnedTunnelobject, getPersistedTunnelobject(returnedTunnelobject));
    }

    @Test
    @Transactional
    void createTunnelobjectWithExistingId() throws Exception {
        // Create the Tunnelobject with an existing ID
        tunnelobject.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTunnelobjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tunnelobject)))
            .andExpect(status().isBadRequest());

        // Validate the Tunnelobject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTunnelobjects() throws Exception {
        // Initialize the database
        tunnelobjectRepository.saveAndFlush(tunnelobject);

        // Get all the tunnelobjectList
        restTunnelobjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tunnelobject.getId().intValue())))
            .andExpect(jsonPath("$.[*].aanleghoogte").value(hasItem(DEFAULT_AANLEGHOOGTE)))
            .andExpect(jsonPath("$.[*].aantaltunnelbuizen").value(hasItem(DEFAULT_AANTALTUNNELBUIZEN)))
            .andExpect(jsonPath("$.[*].breedte").value(hasItem(DEFAULT_BREEDTE)))
            .andExpect(jsonPath("$.[*].doorrijbreedte").value(hasItem(DEFAULT_DOORRIJBREEDTE)))
            .andExpect(jsonPath("$.[*].doorrijhoogte").value(hasItem(DEFAULT_DOORRIJHOOGTE)))
            .andExpect(jsonPath("$.[*].hoogte").value(hasItem(DEFAULT_HOOGTE)))
            .andExpect(jsonPath("$.[*].jaarconserveren").value(hasItem(DEFAULT_JAARCONSERVEREN)))
            .andExpect(jsonPath("$.[*].jaaronderhouduitgevoerd").value(hasItem(DEFAULT_JAARONDERHOUDUITGEVOERD)))
            .andExpect(jsonPath("$.[*].lengte").value(hasItem(DEFAULT_LENGTE)))
            .andExpect(jsonPath("$.[*].leverancier").value(hasItem(DEFAULT_LEVERANCIER)))
            .andExpect(jsonPath("$.[*].eobjectnaam").value(hasItem(DEFAULT_EOBJECTNAAM)))
            .andExpect(jsonPath("$.[*].eobjectnummer").value(hasItem(DEFAULT_EOBJECTNUMMER)))
            .andExpect(jsonPath("$.[*].oppervlakte").value(hasItem(DEFAULT_OPPERVLAKTE)))
            .andExpect(jsonPath("$.[*].tunnelobjectmateriaal").value(hasItem(DEFAULT_TUNNELOBJECTMATERIAAL)));
    }

    @Test
    @Transactional
    void getTunnelobject() throws Exception {
        // Initialize the database
        tunnelobjectRepository.saveAndFlush(tunnelobject);

        // Get the tunnelobject
        restTunnelobjectMockMvc
            .perform(get(ENTITY_API_URL_ID, tunnelobject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tunnelobject.getId().intValue()))
            .andExpect(jsonPath("$.aanleghoogte").value(DEFAULT_AANLEGHOOGTE))
            .andExpect(jsonPath("$.aantaltunnelbuizen").value(DEFAULT_AANTALTUNNELBUIZEN))
            .andExpect(jsonPath("$.breedte").value(DEFAULT_BREEDTE))
            .andExpect(jsonPath("$.doorrijbreedte").value(DEFAULT_DOORRIJBREEDTE))
            .andExpect(jsonPath("$.doorrijhoogte").value(DEFAULT_DOORRIJHOOGTE))
            .andExpect(jsonPath("$.hoogte").value(DEFAULT_HOOGTE))
            .andExpect(jsonPath("$.jaarconserveren").value(DEFAULT_JAARCONSERVEREN))
            .andExpect(jsonPath("$.jaaronderhouduitgevoerd").value(DEFAULT_JAARONDERHOUDUITGEVOERD))
            .andExpect(jsonPath("$.lengte").value(DEFAULT_LENGTE))
            .andExpect(jsonPath("$.leverancier").value(DEFAULT_LEVERANCIER))
            .andExpect(jsonPath("$.eobjectnaam").value(DEFAULT_EOBJECTNAAM))
            .andExpect(jsonPath("$.eobjectnummer").value(DEFAULT_EOBJECTNUMMER))
            .andExpect(jsonPath("$.oppervlakte").value(DEFAULT_OPPERVLAKTE))
            .andExpect(jsonPath("$.tunnelobjectmateriaal").value(DEFAULT_TUNNELOBJECTMATERIAAL));
    }

    @Test
    @Transactional
    void getNonExistingTunnelobject() throws Exception {
        // Get the tunnelobject
        restTunnelobjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTunnelobject() throws Exception {
        // Initialize the database
        tunnelobjectRepository.saveAndFlush(tunnelobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tunnelobject
        Tunnelobject updatedTunnelobject = tunnelobjectRepository.findById(tunnelobject.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTunnelobject are not directly saved in db
        em.detach(updatedTunnelobject);
        updatedTunnelobject
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .aantaltunnelbuizen(UPDATED_AANTALTUNNELBUIZEN)
            .breedte(UPDATED_BREEDTE)
            .doorrijbreedte(UPDATED_DOORRIJBREEDTE)
            .doorrijhoogte(UPDATED_DOORRIJHOOGTE)
            .hoogte(UPDATED_HOOGTE)
            .jaarconserveren(UPDATED_JAARCONSERVEREN)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .eobjectnaam(UPDATED_EOBJECTNAAM)
            .eobjectnummer(UPDATED_EOBJECTNUMMER)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .tunnelobjectmateriaal(UPDATED_TUNNELOBJECTMATERIAAL);

        restTunnelobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTunnelobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTunnelobject))
            )
            .andExpect(status().isOk());

        // Validate the Tunnelobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTunnelobjectToMatchAllProperties(updatedTunnelobject);
    }

    @Test
    @Transactional
    void putNonExistingTunnelobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tunnelobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTunnelobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tunnelobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tunnelobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tunnelobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTunnelobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tunnelobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTunnelobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tunnelobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tunnelobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTunnelobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tunnelobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTunnelobjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tunnelobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tunnelobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTunnelobjectWithPatch() throws Exception {
        // Initialize the database
        tunnelobjectRepository.saveAndFlush(tunnelobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tunnelobject using partial update
        Tunnelobject partialUpdatedTunnelobject = new Tunnelobject();
        partialUpdatedTunnelobject.setId(tunnelobject.getId());

        partialUpdatedTunnelobject
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .aantaltunnelbuizen(UPDATED_AANTALTUNNELBUIZEN)
            .breedte(UPDATED_BREEDTE)
            .doorrijbreedte(UPDATED_DOORRIJBREEDTE)
            .doorrijhoogte(UPDATED_DOORRIJHOOGTE)
            .hoogte(UPDATED_HOOGTE)
            .eobjectnaam(UPDATED_EOBJECTNAAM)
            .oppervlakte(UPDATED_OPPERVLAKTE);

        restTunnelobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTunnelobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTunnelobject))
            )
            .andExpect(status().isOk());

        // Validate the Tunnelobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTunnelobjectUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTunnelobject, tunnelobject),
            getPersistedTunnelobject(tunnelobject)
        );
    }

    @Test
    @Transactional
    void fullUpdateTunnelobjectWithPatch() throws Exception {
        // Initialize the database
        tunnelobjectRepository.saveAndFlush(tunnelobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tunnelobject using partial update
        Tunnelobject partialUpdatedTunnelobject = new Tunnelobject();
        partialUpdatedTunnelobject.setId(tunnelobject.getId());

        partialUpdatedTunnelobject
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .aantaltunnelbuizen(UPDATED_AANTALTUNNELBUIZEN)
            .breedte(UPDATED_BREEDTE)
            .doorrijbreedte(UPDATED_DOORRIJBREEDTE)
            .doorrijhoogte(UPDATED_DOORRIJHOOGTE)
            .hoogte(UPDATED_HOOGTE)
            .jaarconserveren(UPDATED_JAARCONSERVEREN)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .eobjectnaam(UPDATED_EOBJECTNAAM)
            .eobjectnummer(UPDATED_EOBJECTNUMMER)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .tunnelobjectmateriaal(UPDATED_TUNNELOBJECTMATERIAAL);

        restTunnelobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTunnelobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTunnelobject))
            )
            .andExpect(status().isOk());

        // Validate the Tunnelobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTunnelobjectUpdatableFieldsEquals(partialUpdatedTunnelobject, getPersistedTunnelobject(partialUpdatedTunnelobject));
    }

    @Test
    @Transactional
    void patchNonExistingTunnelobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tunnelobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTunnelobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tunnelobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tunnelobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tunnelobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTunnelobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tunnelobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTunnelobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tunnelobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tunnelobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTunnelobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tunnelobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTunnelobjectMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tunnelobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tunnelobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTunnelobject() throws Exception {
        // Initialize the database
        tunnelobjectRepository.saveAndFlush(tunnelobject);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tunnelobject
        restTunnelobjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, tunnelobject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tunnelobjectRepository.count();
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

    protected Tunnelobject getPersistedTunnelobject(Tunnelobject tunnelobject) {
        return tunnelobjectRepository.findById(tunnelobject.getId()).orElseThrow();
    }

    protected void assertPersistedTunnelobjectToMatchAllProperties(Tunnelobject expectedTunnelobject) {
        assertTunnelobjectAllPropertiesEquals(expectedTunnelobject, getPersistedTunnelobject(expectedTunnelobject));
    }

    protected void assertPersistedTunnelobjectToMatchUpdatableProperties(Tunnelobject expectedTunnelobject) {
        assertTunnelobjectAllUpdatablePropertiesEquals(expectedTunnelobject, getPersistedTunnelobject(expectedTunnelobject));
    }
}
