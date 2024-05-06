package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VerblijfsobjectAsserts.*;
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
import nl.ritense.demo.domain.Verblijfsobject;
import nl.ritense.demo.repository.VerblijfsobjectRepository;
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
 * Integration tests for the {@link VerblijfsobjectResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class VerblijfsobjectResourceIT {

    private static final String DEFAULT_AANTALKAMERS = "AAAAAAAAAA";
    private static final String UPDATED_AANTALKAMERS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMINGANG = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMINGANG = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DOCUMENTDATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOCUMENTDATUM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DOCUMENTNR = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTNR = "BBBBBBBBBB";

    private static final String DEFAULT_GEBRUIKSDOEL = "AAAAAAAAAA";
    private static final String UPDATED_GEBRUIKSDOEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_GECONSTATEERD = false;
    private static final Boolean UPDATED_GECONSTATEERD = true;

    private static final String DEFAULT_GEOMETRIE = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIE = "BBBBBBBBBB";

    private static final String DEFAULT_HOOGSTEBOUWLAAGVERBLIJFSOBJECT = "AAAAAAAAAA";
    private static final String UPDATED_HOOGSTEBOUWLAAGVERBLIJFSOBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INONDERZOEK = false;
    private static final Boolean UPDATED_INONDERZOEK = true;

    private static final String DEFAULT_LAAGSTEBOUWLAAGVERBLIJFSOBJECT = "AAAAAAAAAA";
    private static final String UPDATED_LAAGSTEBOUWLAAGVERBLIJFSOBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_ONTSLUITINGVERDIEPING = "AAAAAAAAAA";
    private static final String UPDATED_ONTSLUITINGVERDIEPING = "BBBBBBBBBB";

    private static final String DEFAULT_SOORTWOONOBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SOORTWOONOBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_TOEGANGBOUWLAAGVERBLIJFSOBJECT = "AAAAAAAAAA";
    private static final String UPDATED_TOEGANGBOUWLAAGVERBLIJFSOBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_VERSIE = "AAAAAAAAAA";
    private static final String UPDATED_VERSIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/verblijfsobjects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VerblijfsobjectRepository verblijfsobjectRepository;

    @Mock
    private VerblijfsobjectRepository verblijfsobjectRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVerblijfsobjectMockMvc;

    private Verblijfsobject verblijfsobject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verblijfsobject createEntity(EntityManager em) {
        Verblijfsobject verblijfsobject = new Verblijfsobject()
            .aantalkamers(DEFAULT_AANTALKAMERS)
            .datumbegingeldigheid(DEFAULT_DATUMBEGINGELDIGHEID)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumeindegeldigheid(DEFAULT_DATUMEINDEGELDIGHEID)
            .datumingang(DEFAULT_DATUMINGANG)
            .documentdatum(DEFAULT_DOCUMENTDATUM)
            .documentnr(DEFAULT_DOCUMENTNR)
            .gebruiksdoel(DEFAULT_GEBRUIKSDOEL)
            .geconstateerd(DEFAULT_GECONSTATEERD)
            .geometrie(DEFAULT_GEOMETRIE)
            .hoogstebouwlaagverblijfsobject(DEFAULT_HOOGSTEBOUWLAAGVERBLIJFSOBJECT)
            .identificatie(DEFAULT_IDENTIFICATIE)
            .inonderzoek(DEFAULT_INONDERZOEK)
            .laagstebouwlaagverblijfsobject(DEFAULT_LAAGSTEBOUWLAAGVERBLIJFSOBJECT)
            .ontsluitingverdieping(DEFAULT_ONTSLUITINGVERDIEPING)
            .soortwoonobject(DEFAULT_SOORTWOONOBJECT)
            .status(DEFAULT_STATUS)
            .toegangbouwlaagverblijfsobject(DEFAULT_TOEGANGBOUWLAAGVERBLIJFSOBJECT)
            .versie(DEFAULT_VERSIE);
        return verblijfsobject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Verblijfsobject createUpdatedEntity(EntityManager em) {
        Verblijfsobject verblijfsobject = new Verblijfsobject()
            .aantalkamers(UPDATED_AANTALKAMERS)
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumingang(UPDATED_DATUMINGANG)
            .documentdatum(UPDATED_DOCUMENTDATUM)
            .documentnr(UPDATED_DOCUMENTNR)
            .gebruiksdoel(UPDATED_GEBRUIKSDOEL)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .geometrie(UPDATED_GEOMETRIE)
            .hoogstebouwlaagverblijfsobject(UPDATED_HOOGSTEBOUWLAAGVERBLIJFSOBJECT)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .laagstebouwlaagverblijfsobject(UPDATED_LAAGSTEBOUWLAAGVERBLIJFSOBJECT)
            .ontsluitingverdieping(UPDATED_ONTSLUITINGVERDIEPING)
            .soortwoonobject(UPDATED_SOORTWOONOBJECT)
            .status(UPDATED_STATUS)
            .toegangbouwlaagverblijfsobject(UPDATED_TOEGANGBOUWLAAGVERBLIJFSOBJECT)
            .versie(UPDATED_VERSIE);
        return verblijfsobject;
    }

    @BeforeEach
    public void initTest() {
        verblijfsobject = createEntity(em);
    }

    @Test
    @Transactional
    void createVerblijfsobject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Verblijfsobject
        var returnedVerblijfsobject = om.readValue(
            restVerblijfsobjectMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verblijfsobject)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Verblijfsobject.class
        );

        // Validate the Verblijfsobject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVerblijfsobjectUpdatableFieldsEquals(returnedVerblijfsobject, getPersistedVerblijfsobject(returnedVerblijfsobject));
    }

    @Test
    @Transactional
    void createVerblijfsobjectWithExistingId() throws Exception {
        // Create the Verblijfsobject with an existing ID
        verblijfsobject.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerblijfsobjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verblijfsobject)))
            .andExpect(status().isBadRequest());

        // Validate the Verblijfsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVerblijfsobjects() throws Exception {
        // Initialize the database
        verblijfsobjectRepository.saveAndFlush(verblijfsobject);

        // Get all the verblijfsobjectList
        restVerblijfsobjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verblijfsobject.getId().intValue())))
            .andExpect(jsonPath("$.[*].aantalkamers").value(hasItem(DEFAULT_AANTALKAMERS)))
            .andExpect(jsonPath("$.[*].datumbegingeldigheid").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheid").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].datumingang").value(hasItem(DEFAULT_DATUMINGANG.toString())))
            .andExpect(jsonPath("$.[*].documentdatum").value(hasItem(DEFAULT_DOCUMENTDATUM.toString())))
            .andExpect(jsonPath("$.[*].documentnr").value(hasItem(DEFAULT_DOCUMENTNR)))
            .andExpect(jsonPath("$.[*].gebruiksdoel").value(hasItem(DEFAULT_GEBRUIKSDOEL)))
            .andExpect(jsonPath("$.[*].geconstateerd").value(hasItem(DEFAULT_GECONSTATEERD.booleanValue())))
            .andExpect(jsonPath("$.[*].geometrie").value(hasItem(DEFAULT_GEOMETRIE)))
            .andExpect(jsonPath("$.[*].hoogstebouwlaagverblijfsobject").value(hasItem(DEFAULT_HOOGSTEBOUWLAAGVERBLIJFSOBJECT)))
            .andExpect(jsonPath("$.[*].identificatie").value(hasItem(DEFAULT_IDENTIFICATIE)))
            .andExpect(jsonPath("$.[*].inonderzoek").value(hasItem(DEFAULT_INONDERZOEK.booleanValue())))
            .andExpect(jsonPath("$.[*].laagstebouwlaagverblijfsobject").value(hasItem(DEFAULT_LAAGSTEBOUWLAAGVERBLIJFSOBJECT)))
            .andExpect(jsonPath("$.[*].ontsluitingverdieping").value(hasItem(DEFAULT_ONTSLUITINGVERDIEPING)))
            .andExpect(jsonPath("$.[*].soortwoonobject").value(hasItem(DEFAULT_SOORTWOONOBJECT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].toegangbouwlaagverblijfsobject").value(hasItem(DEFAULT_TOEGANGBOUWLAAGVERBLIJFSOBJECT)))
            .andExpect(jsonPath("$.[*].versie").value(hasItem(DEFAULT_VERSIE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVerblijfsobjectsWithEagerRelationshipsIsEnabled() throws Exception {
        when(verblijfsobjectRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVerblijfsobjectMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(verblijfsobjectRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVerblijfsobjectsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(verblijfsobjectRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVerblijfsobjectMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(verblijfsobjectRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getVerblijfsobject() throws Exception {
        // Initialize the database
        verblijfsobjectRepository.saveAndFlush(verblijfsobject);

        // Get the verblijfsobject
        restVerblijfsobjectMockMvc
            .perform(get(ENTITY_API_URL_ID, verblijfsobject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(verblijfsobject.getId().intValue()))
            .andExpect(jsonPath("$.aantalkamers").value(DEFAULT_AANTALKAMERS))
            .andExpect(jsonPath("$.datumbegingeldigheid").value(DEFAULT_DATUMBEGINGELDIGHEID.toString()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheid").value(DEFAULT_DATUMEINDEGELDIGHEID.toString()))
            .andExpect(jsonPath("$.datumingang").value(DEFAULT_DATUMINGANG.toString()))
            .andExpect(jsonPath("$.documentdatum").value(DEFAULT_DOCUMENTDATUM.toString()))
            .andExpect(jsonPath("$.documentnr").value(DEFAULT_DOCUMENTNR))
            .andExpect(jsonPath("$.gebruiksdoel").value(DEFAULT_GEBRUIKSDOEL))
            .andExpect(jsonPath("$.geconstateerd").value(DEFAULT_GECONSTATEERD.booleanValue()))
            .andExpect(jsonPath("$.geometrie").value(DEFAULT_GEOMETRIE))
            .andExpect(jsonPath("$.hoogstebouwlaagverblijfsobject").value(DEFAULT_HOOGSTEBOUWLAAGVERBLIJFSOBJECT))
            .andExpect(jsonPath("$.identificatie").value(DEFAULT_IDENTIFICATIE))
            .andExpect(jsonPath("$.inonderzoek").value(DEFAULT_INONDERZOEK.booleanValue()))
            .andExpect(jsonPath("$.laagstebouwlaagverblijfsobject").value(DEFAULT_LAAGSTEBOUWLAAGVERBLIJFSOBJECT))
            .andExpect(jsonPath("$.ontsluitingverdieping").value(DEFAULT_ONTSLUITINGVERDIEPING))
            .andExpect(jsonPath("$.soortwoonobject").value(DEFAULT_SOORTWOONOBJECT))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.toegangbouwlaagverblijfsobject").value(DEFAULT_TOEGANGBOUWLAAGVERBLIJFSOBJECT))
            .andExpect(jsonPath("$.versie").value(DEFAULT_VERSIE));
    }

    @Test
    @Transactional
    void getNonExistingVerblijfsobject() throws Exception {
        // Get the verblijfsobject
        restVerblijfsobjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVerblijfsobject() throws Exception {
        // Initialize the database
        verblijfsobjectRepository.saveAndFlush(verblijfsobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verblijfsobject
        Verblijfsobject updatedVerblijfsobject = verblijfsobjectRepository.findById(verblijfsobject.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVerblijfsobject are not directly saved in db
        em.detach(updatedVerblijfsobject);
        updatedVerblijfsobject
            .aantalkamers(UPDATED_AANTALKAMERS)
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumingang(UPDATED_DATUMINGANG)
            .documentdatum(UPDATED_DOCUMENTDATUM)
            .documentnr(UPDATED_DOCUMENTNR)
            .gebruiksdoel(UPDATED_GEBRUIKSDOEL)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .geometrie(UPDATED_GEOMETRIE)
            .hoogstebouwlaagverblijfsobject(UPDATED_HOOGSTEBOUWLAAGVERBLIJFSOBJECT)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .laagstebouwlaagverblijfsobject(UPDATED_LAAGSTEBOUWLAAGVERBLIJFSOBJECT)
            .ontsluitingverdieping(UPDATED_ONTSLUITINGVERDIEPING)
            .soortwoonobject(UPDATED_SOORTWOONOBJECT)
            .status(UPDATED_STATUS)
            .toegangbouwlaagverblijfsobject(UPDATED_TOEGANGBOUWLAAGVERBLIJFSOBJECT)
            .versie(UPDATED_VERSIE);

        restVerblijfsobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVerblijfsobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVerblijfsobject))
            )
            .andExpect(status().isOk());

        // Validate the Verblijfsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVerblijfsobjectToMatchAllProperties(updatedVerblijfsobject);
    }

    @Test
    @Transactional
    void putNonExistingVerblijfsobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfsobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerblijfsobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, verblijfsobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verblijfsobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVerblijfsobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfsobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerblijfsobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(verblijfsobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVerblijfsobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfsobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerblijfsobjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(verblijfsobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verblijfsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVerblijfsobjectWithPatch() throws Exception {
        // Initialize the database
        verblijfsobjectRepository.saveAndFlush(verblijfsobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verblijfsobject using partial update
        Verblijfsobject partialUpdatedVerblijfsobject = new Verblijfsobject();
        partialUpdatedVerblijfsobject.setId(verblijfsobject.getId());

        partialUpdatedVerblijfsobject
            .datumingang(UPDATED_DATUMINGANG)
            .documentdatum(UPDATED_DOCUMENTDATUM)
            .documentnr(UPDATED_DOCUMENTNR)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .geometrie(UPDATED_GEOMETRIE)
            .hoogstebouwlaagverblijfsobject(UPDATED_HOOGSTEBOUWLAAGVERBLIJFSOBJECT)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .laagstebouwlaagverblijfsobject(UPDATED_LAAGSTEBOUWLAAGVERBLIJFSOBJECT)
            .versie(UPDATED_VERSIE);

        restVerblijfsobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerblijfsobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerblijfsobject))
            )
            .andExpect(status().isOk());

        // Validate the Verblijfsobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerblijfsobjectUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVerblijfsobject, verblijfsobject),
            getPersistedVerblijfsobject(verblijfsobject)
        );
    }

    @Test
    @Transactional
    void fullUpdateVerblijfsobjectWithPatch() throws Exception {
        // Initialize the database
        verblijfsobjectRepository.saveAndFlush(verblijfsobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the verblijfsobject using partial update
        Verblijfsobject partialUpdatedVerblijfsobject = new Verblijfsobject();
        partialUpdatedVerblijfsobject.setId(verblijfsobject.getId());

        partialUpdatedVerblijfsobject
            .aantalkamers(UPDATED_AANTALKAMERS)
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumingang(UPDATED_DATUMINGANG)
            .documentdatum(UPDATED_DOCUMENTDATUM)
            .documentnr(UPDATED_DOCUMENTNR)
            .gebruiksdoel(UPDATED_GEBRUIKSDOEL)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .geometrie(UPDATED_GEOMETRIE)
            .hoogstebouwlaagverblijfsobject(UPDATED_HOOGSTEBOUWLAAGVERBLIJFSOBJECT)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .laagstebouwlaagverblijfsobject(UPDATED_LAAGSTEBOUWLAAGVERBLIJFSOBJECT)
            .ontsluitingverdieping(UPDATED_ONTSLUITINGVERDIEPING)
            .soortwoonobject(UPDATED_SOORTWOONOBJECT)
            .status(UPDATED_STATUS)
            .toegangbouwlaagverblijfsobject(UPDATED_TOEGANGBOUWLAAGVERBLIJFSOBJECT)
            .versie(UPDATED_VERSIE);

        restVerblijfsobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVerblijfsobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVerblijfsobject))
            )
            .andExpect(status().isOk());

        // Validate the Verblijfsobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVerblijfsobjectUpdatableFieldsEquals(
            partialUpdatedVerblijfsobject,
            getPersistedVerblijfsobject(partialUpdatedVerblijfsobject)
        );
    }

    @Test
    @Transactional
    void patchNonExistingVerblijfsobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfsobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVerblijfsobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, verblijfsobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verblijfsobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVerblijfsobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfsobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerblijfsobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(verblijfsobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Verblijfsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVerblijfsobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        verblijfsobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVerblijfsobjectMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(verblijfsobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Verblijfsobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVerblijfsobject() throws Exception {
        // Initialize the database
        verblijfsobjectRepository.saveAndFlush(verblijfsobject);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the verblijfsobject
        restVerblijfsobjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, verblijfsobject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return verblijfsobjectRepository.count();
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

    protected Verblijfsobject getPersistedVerblijfsobject(Verblijfsobject verblijfsobject) {
        return verblijfsobjectRepository.findById(verblijfsobject.getId()).orElseThrow();
    }

    protected void assertPersistedVerblijfsobjectToMatchAllProperties(Verblijfsobject expectedVerblijfsobject) {
        assertVerblijfsobjectAllPropertiesEquals(expectedVerblijfsobject, getPersistedVerblijfsobject(expectedVerblijfsobject));
    }

    protected void assertPersistedVerblijfsobjectToMatchUpdatableProperties(Verblijfsobject expectedVerblijfsobject) {
        assertVerblijfsobjectAllUpdatablePropertiesEquals(expectedVerblijfsobject, getPersistedVerblijfsobject(expectedVerblijfsobject));
    }
}
