package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.InstallatieAsserts.*;
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
import nl.ritense.demo.domain.Installatie;
import nl.ritense.demo.repository.InstallatieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link InstallatieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InstallatieResourceIT {

    private static final String DEFAULT_BREEDTE = "AAAAAAAAAA";
    private static final String UPDATED_BREEDTE = "BBBBBBBBBB";

    private static final String DEFAULT_EANCODE = "AAAAAAAAAA";
    private static final String UPDATED_EANCODE = "BBBBBBBBBB";

    private static final String DEFAULT_FABRIKANT = "AAAAAAAAAA";
    private static final String UPDATED_FABRIKANT = "BBBBBBBBBB";

    private static final String DEFAULT_HOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_HOOGTE = "BBBBBBBBBB";

    private static final String DEFAULT_INBELGEGEVENS = "AAAAAAAAAA";
    private static final String UPDATED_INBELGEGEVENS = "BBBBBBBBBB";

    private static final String DEFAULT_INSTALLATEUR = "AAAAAAAAAA";
    private static final String UPDATED_INSTALLATEUR = "BBBBBBBBBB";

    private static final String DEFAULT_JAARONDERHOUDUITGEVOERD = "AAAAAAAAAA";
    private static final String UPDATED_JAARONDERHOUDUITGEVOERD = "BBBBBBBBBB";

    private static final String DEFAULT_LENGTE = "AAAAAAAAAA";
    private static final String UPDATED_LENGTE = "BBBBBBBBBB";

    private static final String DEFAULT_LEVERANCIER = "AAAAAAAAAA";
    private static final String UPDATED_LEVERANCIER = "BBBBBBBBBB";

    private static final String DEFAULT_TYPECOMMUNICATIE = "AAAAAAAAAA";
    private static final String UPDATED_TYPECOMMUNICATIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/installaties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InstallatieRepository installatieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInstallatieMockMvc;

    private Installatie installatie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Installatie createEntity(EntityManager em) {
        Installatie installatie = new Installatie()
            .breedte(DEFAULT_BREEDTE)
            .eancode(DEFAULT_EANCODE)
            .fabrikant(DEFAULT_FABRIKANT)
            .hoogte(DEFAULT_HOOGTE)
            .inbelgegevens(DEFAULT_INBELGEGEVENS)
            .installateur(DEFAULT_INSTALLATEUR)
            .jaaronderhouduitgevoerd(DEFAULT_JAARONDERHOUDUITGEVOERD)
            .lengte(DEFAULT_LENGTE)
            .leverancier(DEFAULT_LEVERANCIER)
            .typecommunicatie(DEFAULT_TYPECOMMUNICATIE);
        return installatie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Installatie createUpdatedEntity(EntityManager em) {
        Installatie installatie = new Installatie()
            .breedte(UPDATED_BREEDTE)
            .eancode(UPDATED_EANCODE)
            .fabrikant(UPDATED_FABRIKANT)
            .hoogte(UPDATED_HOOGTE)
            .inbelgegevens(UPDATED_INBELGEGEVENS)
            .installateur(UPDATED_INSTALLATEUR)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .typecommunicatie(UPDATED_TYPECOMMUNICATIE);
        return installatie;
    }

    @BeforeEach
    public void initTest() {
        installatie = createEntity(em);
    }

    @Test
    @Transactional
    void createInstallatie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Installatie
        var returnedInstallatie = om.readValue(
            restInstallatieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(installatie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Installatie.class
        );

        // Validate the Installatie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertInstallatieUpdatableFieldsEquals(returnedInstallatie, getPersistedInstallatie(returnedInstallatie));
    }

    @Test
    @Transactional
    void createInstallatieWithExistingId() throws Exception {
        // Create the Installatie with an existing ID
        installatie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstallatieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(installatie)))
            .andExpect(status().isBadRequest());

        // Validate the Installatie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInstallaties() throws Exception {
        // Initialize the database
        installatieRepository.saveAndFlush(installatie);

        // Get all the installatieList
        restInstallatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(installatie.getId().intValue())))
            .andExpect(jsonPath("$.[*].breedte").value(hasItem(DEFAULT_BREEDTE)))
            .andExpect(jsonPath("$.[*].eancode").value(hasItem(DEFAULT_EANCODE)))
            .andExpect(jsonPath("$.[*].fabrikant").value(hasItem(DEFAULT_FABRIKANT)))
            .andExpect(jsonPath("$.[*].hoogte").value(hasItem(DEFAULT_HOOGTE)))
            .andExpect(jsonPath("$.[*].inbelgegevens").value(hasItem(DEFAULT_INBELGEGEVENS)))
            .andExpect(jsonPath("$.[*].installateur").value(hasItem(DEFAULT_INSTALLATEUR)))
            .andExpect(jsonPath("$.[*].jaaronderhouduitgevoerd").value(hasItem(DEFAULT_JAARONDERHOUDUITGEVOERD)))
            .andExpect(jsonPath("$.[*].lengte").value(hasItem(DEFAULT_LENGTE)))
            .andExpect(jsonPath("$.[*].leverancier").value(hasItem(DEFAULT_LEVERANCIER)))
            .andExpect(jsonPath("$.[*].typecommunicatie").value(hasItem(DEFAULT_TYPECOMMUNICATIE)));
    }

    @Test
    @Transactional
    void getInstallatie() throws Exception {
        // Initialize the database
        installatieRepository.saveAndFlush(installatie);

        // Get the installatie
        restInstallatieMockMvc
            .perform(get(ENTITY_API_URL_ID, installatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(installatie.getId().intValue()))
            .andExpect(jsonPath("$.breedte").value(DEFAULT_BREEDTE))
            .andExpect(jsonPath("$.eancode").value(DEFAULT_EANCODE))
            .andExpect(jsonPath("$.fabrikant").value(DEFAULT_FABRIKANT))
            .andExpect(jsonPath("$.hoogte").value(DEFAULT_HOOGTE))
            .andExpect(jsonPath("$.inbelgegevens").value(DEFAULT_INBELGEGEVENS))
            .andExpect(jsonPath("$.installateur").value(DEFAULT_INSTALLATEUR))
            .andExpect(jsonPath("$.jaaronderhouduitgevoerd").value(DEFAULT_JAARONDERHOUDUITGEVOERD))
            .andExpect(jsonPath("$.lengte").value(DEFAULT_LENGTE))
            .andExpect(jsonPath("$.leverancier").value(DEFAULT_LEVERANCIER))
            .andExpect(jsonPath("$.typecommunicatie").value(DEFAULT_TYPECOMMUNICATIE));
    }

    @Test
    @Transactional
    void getNonExistingInstallatie() throws Exception {
        // Get the installatie
        restInstallatieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInstallatie() throws Exception {
        // Initialize the database
        installatieRepository.saveAndFlush(installatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the installatie
        Installatie updatedInstallatie = installatieRepository.findById(installatie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInstallatie are not directly saved in db
        em.detach(updatedInstallatie);
        updatedInstallatie
            .breedte(UPDATED_BREEDTE)
            .eancode(UPDATED_EANCODE)
            .fabrikant(UPDATED_FABRIKANT)
            .hoogte(UPDATED_HOOGTE)
            .inbelgegevens(UPDATED_INBELGEGEVENS)
            .installateur(UPDATED_INSTALLATEUR)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .typecommunicatie(UPDATED_TYPECOMMUNICATIE);

        restInstallatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInstallatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedInstallatie))
            )
            .andExpect(status().isOk());

        // Validate the Installatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInstallatieToMatchAllProperties(updatedInstallatie);
    }

    @Test
    @Transactional
    void putNonExistingInstallatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        installatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstallatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, installatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(installatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Installatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInstallatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        installatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInstallatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(installatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Installatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInstallatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        installatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInstallatieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(installatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Installatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInstallatieWithPatch() throws Exception {
        // Initialize the database
        installatieRepository.saveAndFlush(installatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the installatie using partial update
        Installatie partialUpdatedInstallatie = new Installatie();
        partialUpdatedInstallatie.setId(installatie.getId());

        partialUpdatedInstallatie
            .eancode(UPDATED_EANCODE)
            .fabrikant(UPDATED_FABRIKANT)
            .inbelgegevens(UPDATED_INBELGEGEVENS)
            .installateur(UPDATED_INSTALLATEUR)
            .leverancier(UPDATED_LEVERANCIER);

        restInstallatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInstallatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInstallatie))
            )
            .andExpect(status().isOk());

        // Validate the Installatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInstallatieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInstallatie, installatie),
            getPersistedInstallatie(installatie)
        );
    }

    @Test
    @Transactional
    void fullUpdateInstallatieWithPatch() throws Exception {
        // Initialize the database
        installatieRepository.saveAndFlush(installatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the installatie using partial update
        Installatie partialUpdatedInstallatie = new Installatie();
        partialUpdatedInstallatie.setId(installatie.getId());

        partialUpdatedInstallatie
            .breedte(UPDATED_BREEDTE)
            .eancode(UPDATED_EANCODE)
            .fabrikant(UPDATED_FABRIKANT)
            .hoogte(UPDATED_HOOGTE)
            .inbelgegevens(UPDATED_INBELGEGEVENS)
            .installateur(UPDATED_INSTALLATEUR)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .lengte(UPDATED_LENGTE)
            .leverancier(UPDATED_LEVERANCIER)
            .typecommunicatie(UPDATED_TYPECOMMUNICATIE);

        restInstallatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInstallatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInstallatie))
            )
            .andExpect(status().isOk());

        // Validate the Installatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInstallatieUpdatableFieldsEquals(partialUpdatedInstallatie, getPersistedInstallatie(partialUpdatedInstallatie));
    }

    @Test
    @Transactional
    void patchNonExistingInstallatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        installatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstallatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, installatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(installatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Installatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInstallatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        installatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInstallatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(installatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Installatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInstallatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        installatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInstallatieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(installatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Installatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInstallatie() throws Exception {
        // Initialize the database
        installatieRepository.saveAndFlush(installatie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the installatie
        restInstallatieMockMvc
            .perform(delete(ENTITY_API_URL_ID, installatie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return installatieRepository.count();
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

    protected Installatie getPersistedInstallatie(Installatie installatie) {
        return installatieRepository.findById(installatie.getId()).orElseThrow();
    }

    protected void assertPersistedInstallatieToMatchAllProperties(Installatie expectedInstallatie) {
        assertInstallatieAllPropertiesEquals(expectedInstallatie, getPersistedInstallatie(expectedInstallatie));
    }

    protected void assertPersistedInstallatieToMatchUpdatableProperties(Installatie expectedInstallatie) {
        assertInstallatieAllUpdatablePropertiesEquals(expectedInstallatie, getPersistedInstallatie(expectedInstallatie));
    }
}
