package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.WerknemerAsserts.*;
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
import nl.ritense.demo.domain.Rol;
import nl.ritense.demo.domain.Sollicitatiegesprek;
import nl.ritense.demo.domain.Werknemer;
import nl.ritense.demo.repository.WerknemerRepository;
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
 * Integration tests for the {@link WerknemerResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class WerknemerResourceIT {

    private static final LocalDate DEFAULT_GEBOORTEDATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GEBOORTEDATUM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_VOORNAAM = "AAAAAAAAAA";
    private static final String UPDATED_VOORNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_WOONPLAATS = "AAAAAAAAAA";
    private static final String UPDATED_WOONPLAATS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/werknemers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WerknemerRepository werknemerRepository;

    @Mock
    private WerknemerRepository werknemerRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWerknemerMockMvc;

    private Werknemer werknemer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Werknemer createEntity(EntityManager em) {
        Werknemer werknemer = new Werknemer()
            .geboortedatum(DEFAULT_GEBOORTEDATUM)
            .naam(DEFAULT_NAAM)
            .voornaam(DEFAULT_VOORNAAM)
            .woonplaats(DEFAULT_WOONPLAATS);
        // Add required entity
        Rol rol;
        if (TestUtil.findAll(em, Rol.class).isEmpty()) {
            rol = RolResourceIT.createEntity(em);
            em.persist(rol);
            em.flush();
        } else {
            rol = TestUtil.findAll(em, Rol.class).get(0);
        }
        werknemer.getHeeftRols().add(rol);
        // Add required entity
        Sollicitatiegesprek sollicitatiegesprek;
        if (TestUtil.findAll(em, Sollicitatiegesprek.class).isEmpty()) {
            sollicitatiegesprek = SollicitatiegesprekResourceIT.createEntity(em);
            em.persist(sollicitatiegesprek);
            em.flush();
        } else {
            sollicitatiegesprek = TestUtil.findAll(em, Sollicitatiegesprek.class).get(0);
        }
        werknemer.getDoetsollicitatiegesprekSollicitatiegespreks().add(sollicitatiegesprek);
        return werknemer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Werknemer createUpdatedEntity(EntityManager em) {
        Werknemer werknemer = new Werknemer()
            .geboortedatum(UPDATED_GEBOORTEDATUM)
            .naam(UPDATED_NAAM)
            .voornaam(UPDATED_VOORNAAM)
            .woonplaats(UPDATED_WOONPLAATS);
        // Add required entity
        Rol rol;
        if (TestUtil.findAll(em, Rol.class).isEmpty()) {
            rol = RolResourceIT.createUpdatedEntity(em);
            em.persist(rol);
            em.flush();
        } else {
            rol = TestUtil.findAll(em, Rol.class).get(0);
        }
        werknemer.getHeeftRols().add(rol);
        // Add required entity
        Sollicitatiegesprek sollicitatiegesprek;
        if (TestUtil.findAll(em, Sollicitatiegesprek.class).isEmpty()) {
            sollicitatiegesprek = SollicitatiegesprekResourceIT.createUpdatedEntity(em);
            em.persist(sollicitatiegesprek);
            em.flush();
        } else {
            sollicitatiegesprek = TestUtil.findAll(em, Sollicitatiegesprek.class).get(0);
        }
        werknemer.getDoetsollicitatiegesprekSollicitatiegespreks().add(sollicitatiegesprek);
        return werknemer;
    }

    @BeforeEach
    public void initTest() {
        werknemer = createEntity(em);
    }

    @Test
    @Transactional
    void createWerknemer() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Werknemer
        var returnedWerknemer = om.readValue(
            restWerknemerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(werknemer)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Werknemer.class
        );

        // Validate the Werknemer in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertWerknemerUpdatableFieldsEquals(returnedWerknemer, getPersistedWerknemer(returnedWerknemer));
    }

    @Test
    @Transactional
    void createWerknemerWithExistingId() throws Exception {
        // Create the Werknemer with an existing ID
        werknemer.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWerknemerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(werknemer)))
            .andExpect(status().isBadRequest());

        // Validate the Werknemer in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWerknemers() throws Exception {
        // Initialize the database
        werknemerRepository.saveAndFlush(werknemer);

        // Get all the werknemerList
        restWerknemerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(werknemer.getId().intValue())))
            .andExpect(jsonPath("$.[*].geboortedatum").value(hasItem(DEFAULT_GEBOORTEDATUM.toString())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].voornaam").value(hasItem(DEFAULT_VOORNAAM)))
            .andExpect(jsonPath("$.[*].woonplaats").value(hasItem(DEFAULT_WOONPLAATS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllWerknemersWithEagerRelationshipsIsEnabled() throws Exception {
        when(werknemerRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restWerknemerMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(werknemerRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllWerknemersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(werknemerRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restWerknemerMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(werknemerRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getWerknemer() throws Exception {
        // Initialize the database
        werknemerRepository.saveAndFlush(werknemer);

        // Get the werknemer
        restWerknemerMockMvc
            .perform(get(ENTITY_API_URL_ID, werknemer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(werknemer.getId().intValue()))
            .andExpect(jsonPath("$.geboortedatum").value(DEFAULT_GEBOORTEDATUM.toString()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.voornaam").value(DEFAULT_VOORNAAM))
            .andExpect(jsonPath("$.woonplaats").value(DEFAULT_WOONPLAATS));
    }

    @Test
    @Transactional
    void getNonExistingWerknemer() throws Exception {
        // Get the werknemer
        restWerknemerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWerknemer() throws Exception {
        // Initialize the database
        werknemerRepository.saveAndFlush(werknemer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the werknemer
        Werknemer updatedWerknemer = werknemerRepository.findById(werknemer.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWerknemer are not directly saved in db
        em.detach(updatedWerknemer);
        updatedWerknemer.geboortedatum(UPDATED_GEBOORTEDATUM).naam(UPDATED_NAAM).voornaam(UPDATED_VOORNAAM).woonplaats(UPDATED_WOONPLAATS);

        restWerknemerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWerknemer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedWerknemer))
            )
            .andExpect(status().isOk());

        // Validate the Werknemer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWerknemerToMatchAllProperties(updatedWerknemer);
    }

    @Test
    @Transactional
    void putNonExistingWerknemer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        werknemer.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWerknemerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, werknemer.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(werknemer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Werknemer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWerknemer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        werknemer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWerknemerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(werknemer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Werknemer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWerknemer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        werknemer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWerknemerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(werknemer)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Werknemer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWerknemerWithPatch() throws Exception {
        // Initialize the database
        werknemerRepository.saveAndFlush(werknemer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the werknemer using partial update
        Werknemer partialUpdatedWerknemer = new Werknemer();
        partialUpdatedWerknemer.setId(werknemer.getId());

        partialUpdatedWerknemer.naam(UPDATED_NAAM).voornaam(UPDATED_VOORNAAM).woonplaats(UPDATED_WOONPLAATS);

        restWerknemerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWerknemer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWerknemer))
            )
            .andExpect(status().isOk());

        // Validate the Werknemer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWerknemerUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedWerknemer, werknemer),
            getPersistedWerknemer(werknemer)
        );
    }

    @Test
    @Transactional
    void fullUpdateWerknemerWithPatch() throws Exception {
        // Initialize the database
        werknemerRepository.saveAndFlush(werknemer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the werknemer using partial update
        Werknemer partialUpdatedWerknemer = new Werknemer();
        partialUpdatedWerknemer.setId(werknemer.getId());

        partialUpdatedWerknemer
            .geboortedatum(UPDATED_GEBOORTEDATUM)
            .naam(UPDATED_NAAM)
            .voornaam(UPDATED_VOORNAAM)
            .woonplaats(UPDATED_WOONPLAATS);

        restWerknemerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWerknemer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWerknemer))
            )
            .andExpect(status().isOk());

        // Validate the Werknemer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWerknemerUpdatableFieldsEquals(partialUpdatedWerknemer, getPersistedWerknemer(partialUpdatedWerknemer));
    }

    @Test
    @Transactional
    void patchNonExistingWerknemer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        werknemer.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWerknemerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, werknemer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(werknemer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Werknemer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWerknemer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        werknemer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWerknemerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(werknemer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Werknemer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWerknemer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        werknemer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWerknemerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(werknemer)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Werknemer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWerknemer() throws Exception {
        // Initialize the database
        werknemerRepository.saveAndFlush(werknemer);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the werknemer
        restWerknemerMockMvc
            .perform(delete(ENTITY_API_URL_ID, werknemer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return werknemerRepository.count();
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

    protected Werknemer getPersistedWerknemer(Werknemer werknemer) {
        return werknemerRepository.findById(werknemer.getId()).orElseThrow();
    }

    protected void assertPersistedWerknemerToMatchAllProperties(Werknemer expectedWerknemer) {
        assertWerknemerAllPropertiesEquals(expectedWerknemer, getPersistedWerknemer(expectedWerknemer));
    }

    protected void assertPersistedWerknemerToMatchUpdatableProperties(Werknemer expectedWerknemer) {
        assertWerknemerAllUpdatablePropertiesEquals(expectedWerknemer, getPersistedWerknemer(expectedWerknemer));
    }
}
