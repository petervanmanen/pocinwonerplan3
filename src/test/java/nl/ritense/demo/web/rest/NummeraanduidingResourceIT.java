package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.NummeraanduidingAsserts.*;
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
import nl.ritense.demo.domain.Nummeraanduiding;
import nl.ritense.demo.repository.NummeraanduidingRepository;
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
 * Integration tests for the {@link NummeraanduidingResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class NummeraanduidingResourceIT {

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMINGANG = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMINGANG = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_GECONSTATEERD = false;
    private static final Boolean UPDATED_GECONSTATEERD = true;

    private static final String DEFAULT_GEOMETRIE = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIE = "BBBBBBBBBB";

    private static final String DEFAULT_HUISLETTER = "AAAAAAAAAA";
    private static final String UPDATED_HUISLETTER = "BBBBBBBBBB";

    private static final String DEFAULT_HUISNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_HUISNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_HUISNUMMERTOEVOEGING = "AAAAAAAAAA";
    private static final String UPDATED_HUISNUMMERTOEVOEGING = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INONDERZOEK = false;
    private static final Boolean UPDATED_INONDERZOEK = true;

    private static final String DEFAULT_POSTCODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEADRESSEERBAAROBJECT = "AAAAAAAAAA";
    private static final String UPDATED_TYPEADRESSEERBAAROBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_VERSIE = "AAAAAAAAAA";
    private static final String UPDATED_VERSIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nummeraanduidings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NummeraanduidingRepository nummeraanduidingRepository;

    @Mock
    private NummeraanduidingRepository nummeraanduidingRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNummeraanduidingMockMvc;

    private Nummeraanduiding nummeraanduiding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nummeraanduiding createEntity(EntityManager em) {
        Nummeraanduiding nummeraanduiding = new Nummeraanduiding()
            .datumbegingeldigheid(DEFAULT_DATUMBEGINGELDIGHEID)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumeindegeldigheid(DEFAULT_DATUMEINDEGELDIGHEID)
            .datumingang(DEFAULT_DATUMINGANG)
            .geconstateerd(DEFAULT_GECONSTATEERD)
            .geometrie(DEFAULT_GEOMETRIE)
            .huisletter(DEFAULT_HUISLETTER)
            .huisnummer(DEFAULT_HUISNUMMER)
            .huisnummertoevoeging(DEFAULT_HUISNUMMERTOEVOEGING)
            .identificatie(DEFAULT_IDENTIFICATIE)
            .inonderzoek(DEFAULT_INONDERZOEK)
            .postcode(DEFAULT_POSTCODE)
            .status(DEFAULT_STATUS)
            .typeadresseerbaarobject(DEFAULT_TYPEADRESSEERBAAROBJECT)
            .versie(DEFAULT_VERSIE);
        return nummeraanduiding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nummeraanduiding createUpdatedEntity(EntityManager em) {
        Nummeraanduiding nummeraanduiding = new Nummeraanduiding()
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumingang(UPDATED_DATUMINGANG)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .geometrie(UPDATED_GEOMETRIE)
            .huisletter(UPDATED_HUISLETTER)
            .huisnummer(UPDATED_HUISNUMMER)
            .huisnummertoevoeging(UPDATED_HUISNUMMERTOEVOEGING)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .postcode(UPDATED_POSTCODE)
            .status(UPDATED_STATUS)
            .typeadresseerbaarobject(UPDATED_TYPEADRESSEERBAAROBJECT)
            .versie(UPDATED_VERSIE);
        return nummeraanduiding;
    }

    @BeforeEach
    public void initTest() {
        nummeraanduiding = createEntity(em);
    }

    @Test
    @Transactional
    void createNummeraanduiding() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Nummeraanduiding
        var returnedNummeraanduiding = om.readValue(
            restNummeraanduidingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nummeraanduiding)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Nummeraanduiding.class
        );

        // Validate the Nummeraanduiding in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertNummeraanduidingUpdatableFieldsEquals(returnedNummeraanduiding, getPersistedNummeraanduiding(returnedNummeraanduiding));
    }

    @Test
    @Transactional
    void createNummeraanduidingWithExistingId() throws Exception {
        // Create the Nummeraanduiding with an existing ID
        nummeraanduiding.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNummeraanduidingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nummeraanduiding)))
            .andExpect(status().isBadRequest());

        // Validate the Nummeraanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNummeraanduidings() throws Exception {
        // Initialize the database
        nummeraanduidingRepository.saveAndFlush(nummeraanduiding);

        // Get all the nummeraanduidingList
        restNummeraanduidingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nummeraanduiding.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumbegingeldigheid").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheid").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].datumingang").value(hasItem(DEFAULT_DATUMINGANG.toString())))
            .andExpect(jsonPath("$.[*].geconstateerd").value(hasItem(DEFAULT_GECONSTATEERD.booleanValue())))
            .andExpect(jsonPath("$.[*].geometrie").value(hasItem(DEFAULT_GEOMETRIE)))
            .andExpect(jsonPath("$.[*].huisletter").value(hasItem(DEFAULT_HUISLETTER)))
            .andExpect(jsonPath("$.[*].huisnummer").value(hasItem(DEFAULT_HUISNUMMER)))
            .andExpect(jsonPath("$.[*].huisnummertoevoeging").value(hasItem(DEFAULT_HUISNUMMERTOEVOEGING)))
            .andExpect(jsonPath("$.[*].identificatie").value(hasItem(DEFAULT_IDENTIFICATIE)))
            .andExpect(jsonPath("$.[*].inonderzoek").value(hasItem(DEFAULT_INONDERZOEK.booleanValue())))
            .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].typeadresseerbaarobject").value(hasItem(DEFAULT_TYPEADRESSEERBAAROBJECT)))
            .andExpect(jsonPath("$.[*].versie").value(hasItem(DEFAULT_VERSIE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNummeraanduidingsWithEagerRelationshipsIsEnabled() throws Exception {
        when(nummeraanduidingRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNummeraanduidingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(nummeraanduidingRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNummeraanduidingsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(nummeraanduidingRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNummeraanduidingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(nummeraanduidingRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getNummeraanduiding() throws Exception {
        // Initialize the database
        nummeraanduidingRepository.saveAndFlush(nummeraanduiding);

        // Get the nummeraanduiding
        restNummeraanduidingMockMvc
            .perform(get(ENTITY_API_URL_ID, nummeraanduiding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nummeraanduiding.getId().intValue()))
            .andExpect(jsonPath("$.datumbegingeldigheid").value(DEFAULT_DATUMBEGINGELDIGHEID.toString()))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheid").value(DEFAULT_DATUMEINDEGELDIGHEID.toString()))
            .andExpect(jsonPath("$.datumingang").value(DEFAULT_DATUMINGANG.toString()))
            .andExpect(jsonPath("$.geconstateerd").value(DEFAULT_GECONSTATEERD.booleanValue()))
            .andExpect(jsonPath("$.geometrie").value(DEFAULT_GEOMETRIE))
            .andExpect(jsonPath("$.huisletter").value(DEFAULT_HUISLETTER))
            .andExpect(jsonPath("$.huisnummer").value(DEFAULT_HUISNUMMER))
            .andExpect(jsonPath("$.huisnummertoevoeging").value(DEFAULT_HUISNUMMERTOEVOEGING))
            .andExpect(jsonPath("$.identificatie").value(DEFAULT_IDENTIFICATIE))
            .andExpect(jsonPath("$.inonderzoek").value(DEFAULT_INONDERZOEK.booleanValue()))
            .andExpect(jsonPath("$.postcode").value(DEFAULT_POSTCODE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.typeadresseerbaarobject").value(DEFAULT_TYPEADRESSEERBAAROBJECT))
            .andExpect(jsonPath("$.versie").value(DEFAULT_VERSIE));
    }

    @Test
    @Transactional
    void getNonExistingNummeraanduiding() throws Exception {
        // Get the nummeraanduiding
        restNummeraanduidingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNummeraanduiding() throws Exception {
        // Initialize the database
        nummeraanduidingRepository.saveAndFlush(nummeraanduiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the nummeraanduiding
        Nummeraanduiding updatedNummeraanduiding = nummeraanduidingRepository.findById(nummeraanduiding.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNummeraanduiding are not directly saved in db
        em.detach(updatedNummeraanduiding);
        updatedNummeraanduiding
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumingang(UPDATED_DATUMINGANG)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .geometrie(UPDATED_GEOMETRIE)
            .huisletter(UPDATED_HUISLETTER)
            .huisnummer(UPDATED_HUISNUMMER)
            .huisnummertoevoeging(UPDATED_HUISNUMMERTOEVOEGING)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .postcode(UPDATED_POSTCODE)
            .status(UPDATED_STATUS)
            .typeadresseerbaarobject(UPDATED_TYPEADRESSEERBAAROBJECT)
            .versie(UPDATED_VERSIE);

        restNummeraanduidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNummeraanduiding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedNummeraanduiding))
            )
            .andExpect(status().isOk());

        // Validate the Nummeraanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNummeraanduidingToMatchAllProperties(updatedNummeraanduiding);
    }

    @Test
    @Transactional
    void putNonExistingNummeraanduiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nummeraanduiding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNummeraanduidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nummeraanduiding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(nummeraanduiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nummeraanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNummeraanduiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nummeraanduiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNummeraanduidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(nummeraanduiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nummeraanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNummeraanduiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nummeraanduiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNummeraanduidingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nummeraanduiding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nummeraanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNummeraanduidingWithPatch() throws Exception {
        // Initialize the database
        nummeraanduidingRepository.saveAndFlush(nummeraanduiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the nummeraanduiding using partial update
        Nummeraanduiding partialUpdatedNummeraanduiding = new Nummeraanduiding();
        partialUpdatedNummeraanduiding.setId(nummeraanduiding.getId());

        partialUpdatedNummeraanduiding
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumingang(UPDATED_DATUMINGANG)
            .huisletter(UPDATED_HUISLETTER)
            .huisnummertoevoeging(UPDATED_HUISNUMMERTOEVOEGING)
            .identificatie(UPDATED_IDENTIFICATIE)
            .status(UPDATED_STATUS);

        restNummeraanduidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNummeraanduiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNummeraanduiding))
            )
            .andExpect(status().isOk());

        // Validate the Nummeraanduiding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNummeraanduidingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNummeraanduiding, nummeraanduiding),
            getPersistedNummeraanduiding(nummeraanduiding)
        );
    }

    @Test
    @Transactional
    void fullUpdateNummeraanduidingWithPatch() throws Exception {
        // Initialize the database
        nummeraanduidingRepository.saveAndFlush(nummeraanduiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the nummeraanduiding using partial update
        Nummeraanduiding partialUpdatedNummeraanduiding = new Nummeraanduiding();
        partialUpdatedNummeraanduiding.setId(nummeraanduiding.getId());

        partialUpdatedNummeraanduiding
            .datumbegingeldigheid(UPDATED_DATUMBEGINGELDIGHEID)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumingang(UPDATED_DATUMINGANG)
            .geconstateerd(UPDATED_GECONSTATEERD)
            .geometrie(UPDATED_GEOMETRIE)
            .huisletter(UPDATED_HUISLETTER)
            .huisnummer(UPDATED_HUISNUMMER)
            .huisnummertoevoeging(UPDATED_HUISNUMMERTOEVOEGING)
            .identificatie(UPDATED_IDENTIFICATIE)
            .inonderzoek(UPDATED_INONDERZOEK)
            .postcode(UPDATED_POSTCODE)
            .status(UPDATED_STATUS)
            .typeadresseerbaarobject(UPDATED_TYPEADRESSEERBAAROBJECT)
            .versie(UPDATED_VERSIE);

        restNummeraanduidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNummeraanduiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNummeraanduiding))
            )
            .andExpect(status().isOk());

        // Validate the Nummeraanduiding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNummeraanduidingUpdatableFieldsEquals(
            partialUpdatedNummeraanduiding,
            getPersistedNummeraanduiding(partialUpdatedNummeraanduiding)
        );
    }

    @Test
    @Transactional
    void patchNonExistingNummeraanduiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nummeraanduiding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNummeraanduidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nummeraanduiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(nummeraanduiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nummeraanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNummeraanduiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nummeraanduiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNummeraanduidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(nummeraanduiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nummeraanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNummeraanduiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nummeraanduiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNummeraanduidingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(nummeraanduiding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nummeraanduiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNummeraanduiding() throws Exception {
        // Initialize the database
        nummeraanduidingRepository.saveAndFlush(nummeraanduiding);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the nummeraanduiding
        restNummeraanduidingMockMvc
            .perform(delete(ENTITY_API_URL_ID, nummeraanduiding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return nummeraanduidingRepository.count();
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

    protected Nummeraanduiding getPersistedNummeraanduiding(Nummeraanduiding nummeraanduiding) {
        return nummeraanduidingRepository.findById(nummeraanduiding.getId()).orElseThrow();
    }

    protected void assertPersistedNummeraanduidingToMatchAllProperties(Nummeraanduiding expectedNummeraanduiding) {
        assertNummeraanduidingAllPropertiesEquals(expectedNummeraanduiding, getPersistedNummeraanduiding(expectedNummeraanduiding));
    }

    protected void assertPersistedNummeraanduidingToMatchUpdatableProperties(Nummeraanduiding expectedNummeraanduiding) {
        assertNummeraanduidingAllUpdatablePropertiesEquals(
            expectedNummeraanduiding,
            getPersistedNummeraanduiding(expectedNummeraanduiding)
        );
    }
}
