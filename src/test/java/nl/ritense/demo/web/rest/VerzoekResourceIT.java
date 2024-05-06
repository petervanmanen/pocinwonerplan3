package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VerzoekAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Projectactiviteit;
import nl.ritense.demo.domain.Projectlocatie;
import nl.ritense.demo.domain.Verzoek;
import nl.ritense.demo.repository.VerzoekRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
 * Integration tests for the {@link VerzoekResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class VerzoekResourceIT {

    private static final Boolean DEFAULT_AKKOORDVERKLARING = false;
    private static final Boolean UPDATED_AKKOORDVERKLARING = true;

    private static final Boolean DEFAULT_AMBTSHALVE = false;
    private static final Boolean UPDATED_AMBTSHALVE = true;

    private static final LocalDate DEFAULT_DATUMINDIENING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMINDIENING = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DOEL = "AAAAAAAAAA";
    private static final String UPDATED_DOEL = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENTIEAANVRAGER = "AAAAAAAAAA";
    private static final String UPDATED_REFERENTIEAANVRAGER = "BBBBBBBBBB";

    private static final String DEFAULT_TOELICHTINGLATERAANTELEVERENINFORMATIE = "AAAAAAAAAA";
    private static final String UPDATED_TOELICHTINGLATERAANTELEVERENINFORMATIE = "BBBBBBBBBB";

    private static final String DEFAULT_TOELICHTINGNIETAANTELEVERENINFORMATIE = "AAAAAAAAAA";
    private static final String UPDATED_TOELICHTINGNIETAANTELEVERENINFORMATIE = "BBBBBBBBBB";

    private static final String DEFAULT_TOELICHTINGVERZOEK = "AAAAAAAAAA";
    private static final String UPDATED_TOELICHTINGVERZOEK = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VERZOEKNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_VERZOEKNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_VOLGNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_VOLGNUMMER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/verzoeks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VerzoekRepository verzoekRepository;

    @Mock
    private VerzoekRepository verzoekRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVerzoekMockMvc;

    private Verzoek verzoek;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verzoek createEntity(EntityManager em) {
        Verzoek verzoek = new Verzoek()
            .akkoordverklaring(DEFAULT_AKKOORDVERKLARING)
            .ambtshalve(DEFAULT_AMBTSHALVE)
            .datumindiening(DEFAULT_DATUMINDIENING)
            .doel(DEFAULT_DOEL)
            .naam(DEFAULT_NAAM)
            .referentieaanvrager(DEFAULT_REFERENTIEAANVRAGER)
            .toelichtinglateraantelevereninformatie(DEFAULT_TOELICHTINGLATERAANTELEVERENINFORMATIE)
            .toelichtingnietaantelevereninformatie(DEFAULT_TOELICHTINGNIETAANTELEVERENINFORMATIE)
            .toelichtingverzoek(DEFAULT_TOELICHTINGVERZOEK)
            .type(DEFAULT_TYPE)
            .verzoeknummer(DEFAULT_VERZOEKNUMMER)
            .volgnummer(DEFAULT_VOLGNUMMER);
        // Add required entity
        Projectactiviteit projectactiviteit;
        if (TestUtil.findAll(em, Projectactiviteit.class).isEmpty()) {
            projectactiviteit = ProjectactiviteitResourceIT.createEntity(em);
            em.persist(projectactiviteit);
            em.flush();
        } else {
            projectactiviteit = TestUtil.findAll(em, Projectactiviteit.class).get(0);
        }
        verzoek.getBetreftProjectactiviteits().add(projectactiviteit);
        // Add required entity
        Projectlocatie projectlocatie;
        if (TestUtil.findAll(em, Projectlocatie.class).isEmpty()) {
            projectlocatie = ProjectlocatieResourceIT.createEntity(em);
            em.persist(projectlocatie);
            em.flush();
        } else {
            projectlocatie = TestUtil.findAll(em, Projectlocatie.class).get(0);
        }
        verzoek.getBetreftProjectlocaties().add(projectlocatie);
        return verzoek;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verzoek createUpdatedEntity(EntityManager em) {
        Verzoek verzoek = new Verzoek()
            .akkoordverklaring(UPDATED_AKKOORDVERKLARING)
            .ambtshalve(UPDATED_AMBTSHALVE)
            .datumindiening(UPDATED_DATUMINDIENING)
            .doel(UPDATED_DOEL)
            .naam(UPDATED_NAAM)
            .referentieaanvrager(UPDATED_REFERENTIEAANVRAGER)
            .toelichtinglateraantelevereninformatie(UPDATED_TOELICHTINGLATERAANTELEVERENINFORMATIE)
            .toelichtingnietaantelevereninformatie(UPDATED_TOELICHTINGNIETAANTELEVERENINFORMATIE)
            .toelichtingverzoek(UPDATED_TOELICHTINGVERZOEK)
            .type(UPDATED_TYPE)
            .verzoeknummer(UPDATED_VERZOEKNUMMER)
            .volgnummer(UPDATED_VOLGNUMMER);
        // Add required entity
        Projectactiviteit projectactiviteit;
        if (TestUtil.findAll(em, Projectactiviteit.class).isEmpty()) {
            projectactiviteit = ProjectactiviteitResourceIT.createUpdatedEntity(em);
            em.persist(projectactiviteit);
            em.flush();
        } else {
            projectactiviteit = TestUtil.findAll(em, Projectactiviteit.class).get(0);
        }
        verzoek.getBetreftProjectactiviteits().add(projectactiviteit);
        // Add required entity
        Projectlocatie projectlocatie;
        if (TestUtil.findAll(em, Projectlocatie.class).isEmpty()) {
            projectlocatie = ProjectlocatieResourceIT.createUpdatedEntity(em);
            em.persist(projectlocatie);
            em.flush();
        } else {
            projectlocatie = TestUtil.findAll(em, Projectlocatie.class).get(0);
        }
        verzoek.getBetreftProjectlocaties().add(projectlocatie);
        return verzoek;
    }

    @BeforeEach
    public void initTest() {
        verzoek = createEntity(em);
    }

    @Test
    @Transactional
    void createVerzoek() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Verzoek
        var returnedVerzoek = om.readValue(
            restVerzoekMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verzoek)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Verzoek.class
        );

        // Validate the Verzoek in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVerzoekUpdatableFieldsEquals(returnedVerzoek, getPersistedVerzoek(returnedVerzoek));
    }

    @Test
    @Transactional
    void createVerzoekWithExistingId() throws Exception {
        // Create the Verzoek with an existing ID
        verzoek.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerzoekMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verzoek)))
            .andExpect(status().isBadRequest());

        // Validate the Verzoek in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVerzoeks() throws Exception {
        // Initialize the database
        verzoekRepository.saveAndFlush(verzoek);

        // Get all the verzoekList
        restVerzoekMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verzoek.getId().intValue())))
            .andExpect(jsonPath("$.[*].akkoordverklaring").value(hasItem(DEFAULT_AKKOORDVERKLARING.booleanValue())))
            .andExpect(jsonPath("$.[*].ambtshalve").value(hasItem(DEFAULT_AMBTSHALVE.booleanValue())))
            .andExpect(jsonPath("$.[*].datumindiening").value(hasItem(DEFAULT_DATUMINDIENING.toString())))
            .andExpect(jsonPath("$.[*].doel").value(hasItem(DEFAULT_DOEL)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].referentieaanvrager").value(hasItem(DEFAULT_REFERENTIEAANVRAGER)))
            .andExpect(
                jsonPath("$.[*].toelichtinglateraantelevereninformatie").value(hasItem(DEFAULT_TOELICHTINGLATERAANTELEVERENINFORMATIE))
            )
            .andExpect(
                jsonPath("$.[*].toelichtingnietaantelevereninformatie").value(hasItem(DEFAULT_TOELICHTINGNIETAANTELEVERENINFORMATIE))
            )
            .andExpect(jsonPath("$.[*].toelichtingverzoek").value(hasItem(DEFAULT_TOELICHTINGVERZOEK)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].verzoeknummer").value(hasItem(DEFAULT_VERZOEKNUMMER)))
            .andExpect(jsonPath("$.[*].volgnummer").value(hasItem(DEFAULT_VOLGNUMMER)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVerzoeksWithEagerRelationshipsIsEnabled() throws Exception {
        when(verzoekRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVerzoekMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(verzoekRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVerzoeksWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(verzoekRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVerzoekMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(verzoekRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getVerzoek() throws Exception {
        // Initialize the database
        verzoekRepository.saveAndFlush(verzoek);

        // Get the verzoek
        restVerzoekMockMvc
            .perform(get(ENTITY_API_URL_ID, verzoek.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(verzoek.getId().intValue()))
            .andExpect(jsonPath("$.akkoordverklaring").value(DEFAULT_AKKOORDVERKLARING.booleanValue()))
            .andExpect(jsonPath("$.ambtshalve").value(DEFAULT_AMBTSHALVE.booleanValue()))
            .andExpect(jsonPath("$.datumindiening").value(DEFAULT_DATUMINDIENING.toString()))
            .andExpect(jsonPath("$.doel").value(DEFAULT_DOEL))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.referentieaanvrager").value(DEFAULT_REFERENTIEAANVRAGER))
            .andExpect(jsonPath("$.toelichtinglateraantelevereninformatie").value(DEFAULT_TOELICHTINGLATERAANTELEVERENINFORMATIE))
            .andExpect(jsonPath("$.toelichtingnietaantelevereninformatie").value(DEFAULT_TOELICHTINGNIETAANTELEVERENINFORMATIE))
            .andExpect(jsonPath("$.toelichtingverzoek").value(DEFAULT_TOELICHTINGVERZOEK))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.verzoeknummer").value(DEFAULT_VERZOEKNUMMER))
            .andExpect(jsonPath("$.volgnummer").value(DEFAULT_VOLGNUMMER));
    }

    @Test
    @Transactional
    void getNonExistingVerzoek() throws Exception {
        // Get the verzoek
        restVerzoekMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVerzoek() throws Exception {
        // Initialize the database
        verzoekRepository.saveAndFlush(verzoek);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verzoek
        Verzoek updatedVerzoek = verzoekRepository.findById(verzoek.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVerzoek are not directly saved in db
        em.detach(updatedVerzoek);
        updatedVerzoek
            .akkoordverklaring(UPDATED_AKKOORDVERKLARING)
            .ambtshalve(UPDATED_AMBTSHALVE)
            .datumindiening(UPDATED_DATUMINDIENING)
            .doel(UPDATED_DOEL)
            .naam(UPDATED_NAAM)
            .referentieaanvrager(UPDATED_REFERENTIEAANVRAGER)
            .toelichtinglateraantelevereninformatie(UPDATED_TOELICHTINGLATERAANTELEVERENINFORMATIE)
            .toelichtingnietaantelevereninformatie(UPDATED_TOELICHTINGNIETAANTELEVERENINFORMATIE)
            .toelichtingverzoek(UPDATED_TOELICHTINGVERZOEK)
            .type(UPDATED_TYPE)
            .verzoeknummer(UPDATED_VERZOEKNUMMER)
            .volgnummer(UPDATED_VOLGNUMMER);

        restVerzoekMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVerzoek.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVerzoek))
            )
            .andExpect(status().isOk());

        // Validate the Verzoek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVerzoekToMatchAllProperties(updatedVerzoek);
    }

    @Test
    @Transactional
    void putNonExistingVerzoek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verzoek.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerzoekMockMvc
            .perform(put(ENTITY_API_URL_ID, verzoek.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verzoek)))
            .andExpect(status().isBadRequest());

        // Validate the Verzoek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVerzoek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verzoek.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerzoekMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verzoek))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verzoek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVerzoek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verzoek.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerzoekMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verzoek)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verzoek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVerzoekWithPatch() throws Exception {
        // Initialize the database
        verzoekRepository.saveAndFlush(verzoek);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verzoek using partial update
        Verzoek partialUpdatedVerzoek = new Verzoek();
        partialUpdatedVerzoek.setId(verzoek.getId());

        partialUpdatedVerzoek
            .doel(UPDATED_DOEL)
            .referentieaanvrager(UPDATED_REFERENTIEAANVRAGER)
            .toelichtingnietaantelevereninformatie(UPDATED_TOELICHTINGNIETAANTELEVERENINFORMATIE)
            .toelichtingverzoek(UPDATED_TOELICHTINGVERZOEK)
            .volgnummer(UPDATED_VOLGNUMMER);

        restVerzoekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerzoek.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerzoek))
            )
            .andExpect(status().isOk());

        // Validate the Verzoek in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerzoekUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedVerzoek, verzoek), getPersistedVerzoek(verzoek));
    }

    @Test
    @Transactional
    void fullUpdateVerzoekWithPatch() throws Exception {
        // Initialize the database
        verzoekRepository.saveAndFlush(verzoek);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verzoek using partial update
        Verzoek partialUpdatedVerzoek = new Verzoek();
        partialUpdatedVerzoek.setId(verzoek.getId());

        partialUpdatedVerzoek
            .akkoordverklaring(UPDATED_AKKOORDVERKLARING)
            .ambtshalve(UPDATED_AMBTSHALVE)
            .datumindiening(UPDATED_DATUMINDIENING)
            .doel(UPDATED_DOEL)
            .naam(UPDATED_NAAM)
            .referentieaanvrager(UPDATED_REFERENTIEAANVRAGER)
            .toelichtinglateraantelevereninformatie(UPDATED_TOELICHTINGLATERAANTELEVERENINFORMATIE)
            .toelichtingnietaantelevereninformatie(UPDATED_TOELICHTINGNIETAANTELEVERENINFORMATIE)
            .toelichtingverzoek(UPDATED_TOELICHTINGVERZOEK)
            .type(UPDATED_TYPE)
            .verzoeknummer(UPDATED_VERZOEKNUMMER)
            .volgnummer(UPDATED_VOLGNUMMER);

        restVerzoekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerzoek.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerzoek))
            )
            .andExpect(status().isOk());

        // Validate the Verzoek in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerzoekUpdatableFieldsEquals(partialUpdatedVerzoek, getPersistedVerzoek(partialUpdatedVerzoek));
    }

    @Test
    @Transactional
    void patchNonExistingVerzoek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verzoek.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerzoekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, verzoek.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(verzoek))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verzoek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVerzoek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verzoek.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerzoekMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verzoek))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verzoek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVerzoek() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verzoek.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerzoekMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(verzoek)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verzoek in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVerzoek() throws Exception {
        // Initialize the database
        verzoekRepository.saveAndFlush(verzoek);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the verzoek
        restVerzoekMockMvc
            .perform(delete(ENTITY_API_URL_ID, verzoek.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return verzoekRepository.count();
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

    protected Verzoek getPersistedVerzoek(Verzoek verzoek) {
        return verzoekRepository.findById(verzoek.getId()).orElseThrow();
    }

    protected void assertPersistedVerzoekToMatchAllProperties(Verzoek expectedVerzoek) {
        assertVerzoekAllPropertiesEquals(expectedVerzoek, getPersistedVerzoek(expectedVerzoek));
    }

    protected void assertPersistedVerzoekToMatchUpdatableProperties(Verzoek expectedVerzoek) {
        assertVerzoekAllUpdatablePropertiesEquals(expectedVerzoek, getPersistedVerzoek(expectedVerzoek));
    }
}
