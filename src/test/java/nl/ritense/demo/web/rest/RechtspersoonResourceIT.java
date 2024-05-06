package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.RechtspersoonAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Kadastralemutatie;
import nl.ritense.demo.domain.Rechtspersoon;
import nl.ritense.demo.repository.RechtspersoonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RechtspersoonResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RechtspersoonResourceIT {

    private static final String DEFAULT_ADRESBINNENLAND = "AAAAAAAAAA";
    private static final String UPDATED_ADRESBINNENLAND = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESBUITENLAND = "AAAAAAAAAA";
    private static final String UPDATED_ADRESBUITENLAND = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESCORRESPONDENTIE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESCORRESPONDENTIE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAILADRES = "AAAAAAAAAA";
    private static final String UPDATED_EMAILADRES = "BBBBBBBBBB";

    private static final String DEFAULT_FAXNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_FAXNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIE = "BBBBBBBBBB";

    private static final String DEFAULT_KVKNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_KVKNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_RECHTSVORM = "AAAAAAAAAA";
    private static final String UPDATED_RECHTSVORM = "BBBBBBBBBB";

    private static final String DEFAULT_REKENINGNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_REKENINGNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFOONNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_TELEFOONNUMMER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rechtspersoons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RechtspersoonRepository rechtspersoonRepository;

    @Mock
    private RechtspersoonRepository rechtspersoonRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRechtspersoonMockMvc;

    private Rechtspersoon rechtspersoon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rechtspersoon createEntity(EntityManager em) {
        Rechtspersoon rechtspersoon = new Rechtspersoon()
            .adresbinnenland(DEFAULT_ADRESBINNENLAND)
            .adresbuitenland(DEFAULT_ADRESBUITENLAND)
            .adrescorrespondentie(DEFAULT_ADRESCORRESPONDENTIE)
            .emailadres(DEFAULT_EMAILADRES)
            .faxnummer(DEFAULT_FAXNUMMER)
            .identificatie(DEFAULT_IDENTIFICATIE)
            .kvknummer(DEFAULT_KVKNUMMER)
            .naam(DEFAULT_NAAM)
            .rechtsvorm(DEFAULT_RECHTSVORM)
            .rekeningnummer(DEFAULT_REKENINGNUMMER)
            .telefoonnummer(DEFAULT_TELEFOONNUMMER);
        // Add required entity
        Kadastralemutatie kadastralemutatie;
        if (TestUtil.findAll(em, Kadastralemutatie.class).isEmpty()) {
            kadastralemutatie = KadastralemutatieResourceIT.createEntity(em);
            em.persist(kadastralemutatie);
            em.flush();
        } else {
            kadastralemutatie = TestUtil.findAll(em, Kadastralemutatie.class).get(0);
        }
        rechtspersoon.getBetrokkenenKadastralemutaties().add(kadastralemutatie);
        return rechtspersoon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rechtspersoon createUpdatedEntity(EntityManager em) {
        Rechtspersoon rechtspersoon = new Rechtspersoon()
            .adresbinnenland(UPDATED_ADRESBINNENLAND)
            .adresbuitenland(UPDATED_ADRESBUITENLAND)
            .adrescorrespondentie(UPDATED_ADRESCORRESPONDENTIE)
            .emailadres(UPDATED_EMAILADRES)
            .faxnummer(UPDATED_FAXNUMMER)
            .identificatie(UPDATED_IDENTIFICATIE)
            .kvknummer(UPDATED_KVKNUMMER)
            .naam(UPDATED_NAAM)
            .rechtsvorm(UPDATED_RECHTSVORM)
            .rekeningnummer(UPDATED_REKENINGNUMMER)
            .telefoonnummer(UPDATED_TELEFOONNUMMER);
        // Add required entity
        Kadastralemutatie kadastralemutatie;
        if (TestUtil.findAll(em, Kadastralemutatie.class).isEmpty()) {
            kadastralemutatie = KadastralemutatieResourceIT.createUpdatedEntity(em);
            em.persist(kadastralemutatie);
            em.flush();
        } else {
            kadastralemutatie = TestUtil.findAll(em, Kadastralemutatie.class).get(0);
        }
        rechtspersoon.getBetrokkenenKadastralemutaties().add(kadastralemutatie);
        return rechtspersoon;
    }

    @BeforeEach
    public void initTest() {
        rechtspersoon = createEntity(em);
    }

    @Test
    @Transactional
    void createRechtspersoon() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Rechtspersoon
        var returnedRechtspersoon = om.readValue(
            restRechtspersoonMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rechtspersoon)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Rechtspersoon.class
        );

        // Validate the Rechtspersoon in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRechtspersoonUpdatableFieldsEquals(returnedRechtspersoon, getPersistedRechtspersoon(returnedRechtspersoon));
    }

    @Test
    @Transactional
    void createRechtspersoonWithExistingId() throws Exception {
        // Create the Rechtspersoon with an existing ID
        rechtspersoon.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRechtspersoonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rechtspersoon)))
            .andExpect(status().isBadRequest());

        // Validate the Rechtspersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRechtspersoons() throws Exception {
        // Initialize the database
        rechtspersoonRepository.saveAndFlush(rechtspersoon);

        // Get all the rechtspersoonList
        restRechtspersoonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rechtspersoon.getId().intValue())))
            .andExpect(jsonPath("$.[*].adresbinnenland").value(hasItem(DEFAULT_ADRESBINNENLAND)))
            .andExpect(jsonPath("$.[*].adresbuitenland").value(hasItem(DEFAULT_ADRESBUITENLAND)))
            .andExpect(jsonPath("$.[*].adrescorrespondentie").value(hasItem(DEFAULT_ADRESCORRESPONDENTIE)))
            .andExpect(jsonPath("$.[*].emailadres").value(hasItem(DEFAULT_EMAILADRES)))
            .andExpect(jsonPath("$.[*].faxnummer").value(hasItem(DEFAULT_FAXNUMMER)))
            .andExpect(jsonPath("$.[*].identificatie").value(hasItem(DEFAULT_IDENTIFICATIE)))
            .andExpect(jsonPath("$.[*].kvknummer").value(hasItem(DEFAULT_KVKNUMMER)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].rechtsvorm").value(hasItem(DEFAULT_RECHTSVORM)))
            .andExpect(jsonPath("$.[*].rekeningnummer").value(hasItem(DEFAULT_REKENINGNUMMER)))
            .andExpect(jsonPath("$.[*].telefoonnummer").value(hasItem(DEFAULT_TELEFOONNUMMER)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRechtspersoonsWithEagerRelationshipsIsEnabled() throws Exception {
        when(rechtspersoonRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRechtspersoonMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(rechtspersoonRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRechtspersoonsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(rechtspersoonRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRechtspersoonMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(rechtspersoonRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getRechtspersoon() throws Exception {
        // Initialize the database
        rechtspersoonRepository.saveAndFlush(rechtspersoon);

        // Get the rechtspersoon
        restRechtspersoonMockMvc
            .perform(get(ENTITY_API_URL_ID, rechtspersoon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rechtspersoon.getId().intValue()))
            .andExpect(jsonPath("$.adresbinnenland").value(DEFAULT_ADRESBINNENLAND))
            .andExpect(jsonPath("$.adresbuitenland").value(DEFAULT_ADRESBUITENLAND))
            .andExpect(jsonPath("$.adrescorrespondentie").value(DEFAULT_ADRESCORRESPONDENTIE))
            .andExpect(jsonPath("$.emailadres").value(DEFAULT_EMAILADRES))
            .andExpect(jsonPath("$.faxnummer").value(DEFAULT_FAXNUMMER))
            .andExpect(jsonPath("$.identificatie").value(DEFAULT_IDENTIFICATIE))
            .andExpect(jsonPath("$.kvknummer").value(DEFAULT_KVKNUMMER))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.rechtsvorm").value(DEFAULT_RECHTSVORM))
            .andExpect(jsonPath("$.rekeningnummer").value(DEFAULT_REKENINGNUMMER))
            .andExpect(jsonPath("$.telefoonnummer").value(DEFAULT_TELEFOONNUMMER));
    }

    @Test
    @Transactional
    void getNonExistingRechtspersoon() throws Exception {
        // Get the rechtspersoon
        restRechtspersoonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRechtspersoon() throws Exception {
        // Initialize the database
        rechtspersoonRepository.saveAndFlush(rechtspersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rechtspersoon
        Rechtspersoon updatedRechtspersoon = rechtspersoonRepository.findById(rechtspersoon.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRechtspersoon are not directly saved in db
        em.detach(updatedRechtspersoon);
        updatedRechtspersoon
            .adresbinnenland(UPDATED_ADRESBINNENLAND)
            .adresbuitenland(UPDATED_ADRESBUITENLAND)
            .adrescorrespondentie(UPDATED_ADRESCORRESPONDENTIE)
            .emailadres(UPDATED_EMAILADRES)
            .faxnummer(UPDATED_FAXNUMMER)
            .identificatie(UPDATED_IDENTIFICATIE)
            .kvknummer(UPDATED_KVKNUMMER)
            .naam(UPDATED_NAAM)
            .rechtsvorm(UPDATED_RECHTSVORM)
            .rekeningnummer(UPDATED_REKENINGNUMMER)
            .telefoonnummer(UPDATED_TELEFOONNUMMER);

        restRechtspersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRechtspersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRechtspersoon))
            )
            .andExpect(status().isOk());

        // Validate the Rechtspersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRechtspersoonToMatchAllProperties(updatedRechtspersoon);
    }

    @Test
    @Transactional
    void putNonExistingRechtspersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rechtspersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRechtspersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rechtspersoon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rechtspersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rechtspersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRechtspersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rechtspersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRechtspersoonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rechtspersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rechtspersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRechtspersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rechtspersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRechtspersoonMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rechtspersoon)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rechtspersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRechtspersoonWithPatch() throws Exception {
        // Initialize the database
        rechtspersoonRepository.saveAndFlush(rechtspersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rechtspersoon using partial update
        Rechtspersoon partialUpdatedRechtspersoon = new Rechtspersoon();
        partialUpdatedRechtspersoon.setId(rechtspersoon.getId());

        partialUpdatedRechtspersoon
            .adresbinnenland(UPDATED_ADRESBINNENLAND)
            .adresbuitenland(UPDATED_ADRESBUITENLAND)
            .emailadres(UPDATED_EMAILADRES)
            .rechtsvorm(UPDATED_RECHTSVORM)
            .rekeningnummer(UPDATED_REKENINGNUMMER);

        restRechtspersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRechtspersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRechtspersoon))
            )
            .andExpect(status().isOk());

        // Validate the Rechtspersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRechtspersoonUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRechtspersoon, rechtspersoon),
            getPersistedRechtspersoon(rechtspersoon)
        );
    }

    @Test
    @Transactional
    void fullUpdateRechtspersoonWithPatch() throws Exception {
        // Initialize the database
        rechtspersoonRepository.saveAndFlush(rechtspersoon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rechtspersoon using partial update
        Rechtspersoon partialUpdatedRechtspersoon = new Rechtspersoon();
        partialUpdatedRechtspersoon.setId(rechtspersoon.getId());

        partialUpdatedRechtspersoon
            .adresbinnenland(UPDATED_ADRESBINNENLAND)
            .adresbuitenland(UPDATED_ADRESBUITENLAND)
            .adrescorrespondentie(UPDATED_ADRESCORRESPONDENTIE)
            .emailadres(UPDATED_EMAILADRES)
            .faxnummer(UPDATED_FAXNUMMER)
            .identificatie(UPDATED_IDENTIFICATIE)
            .kvknummer(UPDATED_KVKNUMMER)
            .naam(UPDATED_NAAM)
            .rechtsvorm(UPDATED_RECHTSVORM)
            .rekeningnummer(UPDATED_REKENINGNUMMER)
            .telefoonnummer(UPDATED_TELEFOONNUMMER);

        restRechtspersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRechtspersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRechtspersoon))
            )
            .andExpect(status().isOk());

        // Validate the Rechtspersoon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRechtspersoonUpdatableFieldsEquals(partialUpdatedRechtspersoon, getPersistedRechtspersoon(partialUpdatedRechtspersoon));
    }

    @Test
    @Transactional
    void patchNonExistingRechtspersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rechtspersoon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRechtspersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rechtspersoon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rechtspersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rechtspersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRechtspersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rechtspersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRechtspersoonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rechtspersoon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rechtspersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRechtspersoon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rechtspersoon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRechtspersoonMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rechtspersoon)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rechtspersoon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRechtspersoon() throws Exception {
        // Initialize the database
        rechtspersoonRepository.saveAndFlush(rechtspersoon);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the rechtspersoon
        restRechtspersoonMockMvc
            .perform(delete(ENTITY_API_URL_ID, rechtspersoon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return rechtspersoonRepository.count();
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

    protected Rechtspersoon getPersistedRechtspersoon(Rechtspersoon rechtspersoon) {
        return rechtspersoonRepository.findById(rechtspersoon.getId()).orElseThrow();
    }

    protected void assertPersistedRechtspersoonToMatchAllProperties(Rechtspersoon expectedRechtspersoon) {
        assertRechtspersoonAllPropertiesEquals(expectedRechtspersoon, getPersistedRechtspersoon(expectedRechtspersoon));
    }

    protected void assertPersistedRechtspersoonToMatchUpdatableProperties(Rechtspersoon expectedRechtspersoon) {
        assertRechtspersoonAllUpdatablePropertiesEquals(expectedRechtspersoon, getPersistedRechtspersoon(expectedRechtspersoon));
    }
}
