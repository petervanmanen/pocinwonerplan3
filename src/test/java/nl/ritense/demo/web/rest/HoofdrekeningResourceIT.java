package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.HoofdrekeningAsserts.*;
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
import nl.ritense.demo.domain.Hoofdrekening;
import nl.ritense.demo.domain.Inkooporder;
import nl.ritense.demo.repository.HoofdrekeningRepository;
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
 * Integration tests for the {@link HoofdrekeningResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class HoofdrekeningResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_NUMMER = "AAAAAAAA";
    private static final String UPDATED_NUMMER = "BBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_PIAHOOFCATEGORIEOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_PIAHOOFCATEGORIEOMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_PIAHOOFDCATEGORIECODE = "AAAAAAAAAA";
    private static final String UPDATED_PIAHOOFDCATEGORIECODE = "BBBBBBBBBB";

    private static final String DEFAULT_SUBCODE = "AAAAAAAAAA";
    private static final String UPDATED_SUBCODE = "BBBBBBBBBB";

    private static final String DEFAULT_SUBCODEOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_SUBCODEOMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/hoofdrekenings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HoofdrekeningRepository hoofdrekeningRepository;

    @Mock
    private HoofdrekeningRepository hoofdrekeningRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHoofdrekeningMockMvc;

    private Hoofdrekening hoofdrekening;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hoofdrekening createEntity(EntityManager em) {
        Hoofdrekening hoofdrekening = new Hoofdrekening()
            .naam(DEFAULT_NAAM)
            .nummer(DEFAULT_NUMMER)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .piahoofcategorieomschrijving(DEFAULT_PIAHOOFCATEGORIEOMSCHRIJVING)
            .piahoofdcategoriecode(DEFAULT_PIAHOOFDCATEGORIECODE)
            .subcode(DEFAULT_SUBCODE)
            .subcodeomschrijving(DEFAULT_SUBCODEOMSCHRIJVING);
        // Add required entity
        Inkooporder inkooporder;
        if (TestUtil.findAll(em, Inkooporder.class).isEmpty()) {
            inkooporder = InkooporderResourceIT.createEntity(em);
            em.persist(inkooporder);
            em.flush();
        } else {
            inkooporder = TestUtil.findAll(em, Inkooporder.class).get(0);
        }
        hoofdrekening.getWordtgeschrevenopInkooporders().add(inkooporder);
        return hoofdrekening;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hoofdrekening createUpdatedEntity(EntityManager em) {
        Hoofdrekening hoofdrekening = new Hoofdrekening()
            .naam(UPDATED_NAAM)
            .nummer(UPDATED_NUMMER)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .piahoofcategorieomschrijving(UPDATED_PIAHOOFCATEGORIEOMSCHRIJVING)
            .piahoofdcategoriecode(UPDATED_PIAHOOFDCATEGORIECODE)
            .subcode(UPDATED_SUBCODE)
            .subcodeomschrijving(UPDATED_SUBCODEOMSCHRIJVING);
        // Add required entity
        Inkooporder inkooporder;
        if (TestUtil.findAll(em, Inkooporder.class).isEmpty()) {
            inkooporder = InkooporderResourceIT.createUpdatedEntity(em);
            em.persist(inkooporder);
            em.flush();
        } else {
            inkooporder = TestUtil.findAll(em, Inkooporder.class).get(0);
        }
        hoofdrekening.getWordtgeschrevenopInkooporders().add(inkooporder);
        return hoofdrekening;
    }

    @BeforeEach
    public void initTest() {
        hoofdrekening = createEntity(em);
    }

    @Test
    @Transactional
    void createHoofdrekening() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Hoofdrekening
        var returnedHoofdrekening = om.readValue(
            restHoofdrekeningMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hoofdrekening)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Hoofdrekening.class
        );

        // Validate the Hoofdrekening in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertHoofdrekeningUpdatableFieldsEquals(returnedHoofdrekening, getPersistedHoofdrekening(returnedHoofdrekening));
    }

    @Test
    @Transactional
    void createHoofdrekeningWithExistingId() throws Exception {
        // Create the Hoofdrekening with an existing ID
        hoofdrekening.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHoofdrekeningMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hoofdrekening)))
            .andExpect(status().isBadRequest());

        // Validate the Hoofdrekening in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHoofdrekenings() throws Exception {
        // Initialize the database
        hoofdrekeningRepository.saveAndFlush(hoofdrekening);

        // Get all the hoofdrekeningList
        restHoofdrekeningMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hoofdrekening.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].nummer").value(hasItem(DEFAULT_NUMMER)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].piahoofcategorieomschrijving").value(hasItem(DEFAULT_PIAHOOFCATEGORIEOMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].piahoofdcategoriecode").value(hasItem(DEFAULT_PIAHOOFDCATEGORIECODE)))
            .andExpect(jsonPath("$.[*].subcode").value(hasItem(DEFAULT_SUBCODE)))
            .andExpect(jsonPath("$.[*].subcodeomschrijving").value(hasItem(DEFAULT_SUBCODEOMSCHRIJVING)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllHoofdrekeningsWithEagerRelationshipsIsEnabled() throws Exception {
        when(hoofdrekeningRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restHoofdrekeningMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(hoofdrekeningRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllHoofdrekeningsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(hoofdrekeningRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restHoofdrekeningMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(hoofdrekeningRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getHoofdrekening() throws Exception {
        // Initialize the database
        hoofdrekeningRepository.saveAndFlush(hoofdrekening);

        // Get the hoofdrekening
        restHoofdrekeningMockMvc
            .perform(get(ENTITY_API_URL_ID, hoofdrekening.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hoofdrekening.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.nummer").value(DEFAULT_NUMMER))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.piahoofcategorieomschrijving").value(DEFAULT_PIAHOOFCATEGORIEOMSCHRIJVING))
            .andExpect(jsonPath("$.piahoofdcategoriecode").value(DEFAULT_PIAHOOFDCATEGORIECODE))
            .andExpect(jsonPath("$.subcode").value(DEFAULT_SUBCODE))
            .andExpect(jsonPath("$.subcodeomschrijving").value(DEFAULT_SUBCODEOMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingHoofdrekening() throws Exception {
        // Get the hoofdrekening
        restHoofdrekeningMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHoofdrekening() throws Exception {
        // Initialize the database
        hoofdrekeningRepository.saveAndFlush(hoofdrekening);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hoofdrekening
        Hoofdrekening updatedHoofdrekening = hoofdrekeningRepository.findById(hoofdrekening.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHoofdrekening are not directly saved in db
        em.detach(updatedHoofdrekening);
        updatedHoofdrekening
            .naam(UPDATED_NAAM)
            .nummer(UPDATED_NUMMER)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .piahoofcategorieomschrijving(UPDATED_PIAHOOFCATEGORIEOMSCHRIJVING)
            .piahoofdcategoriecode(UPDATED_PIAHOOFDCATEGORIECODE)
            .subcode(UPDATED_SUBCODE)
            .subcodeomschrijving(UPDATED_SUBCODEOMSCHRIJVING);

        restHoofdrekeningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHoofdrekening.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedHoofdrekening))
            )
            .andExpect(status().isOk());

        // Validate the Hoofdrekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHoofdrekeningToMatchAllProperties(updatedHoofdrekening);
    }

    @Test
    @Transactional
    void putNonExistingHoofdrekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hoofdrekening.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHoofdrekeningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hoofdrekening.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hoofdrekening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hoofdrekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHoofdrekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hoofdrekening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoofdrekeningMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hoofdrekening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hoofdrekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHoofdrekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hoofdrekening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoofdrekeningMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hoofdrekening)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hoofdrekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHoofdrekeningWithPatch() throws Exception {
        // Initialize the database
        hoofdrekeningRepository.saveAndFlush(hoofdrekening);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hoofdrekening using partial update
        Hoofdrekening partialUpdatedHoofdrekening = new Hoofdrekening();
        partialUpdatedHoofdrekening.setId(hoofdrekening.getId());

        partialUpdatedHoofdrekening
            .omschrijving(UPDATED_OMSCHRIJVING)
            .piahoofcategorieomschrijving(UPDATED_PIAHOOFCATEGORIEOMSCHRIJVING)
            .piahoofdcategoriecode(UPDATED_PIAHOOFDCATEGORIECODE)
            .subcode(UPDATED_SUBCODE);

        restHoofdrekeningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHoofdrekening.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHoofdrekening))
            )
            .andExpect(status().isOk());

        // Validate the Hoofdrekening in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHoofdrekeningUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedHoofdrekening, hoofdrekening),
            getPersistedHoofdrekening(hoofdrekening)
        );
    }

    @Test
    @Transactional
    void fullUpdateHoofdrekeningWithPatch() throws Exception {
        // Initialize the database
        hoofdrekeningRepository.saveAndFlush(hoofdrekening);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hoofdrekening using partial update
        Hoofdrekening partialUpdatedHoofdrekening = new Hoofdrekening();
        partialUpdatedHoofdrekening.setId(hoofdrekening.getId());

        partialUpdatedHoofdrekening
            .naam(UPDATED_NAAM)
            .nummer(UPDATED_NUMMER)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .piahoofcategorieomschrijving(UPDATED_PIAHOOFCATEGORIEOMSCHRIJVING)
            .piahoofdcategoriecode(UPDATED_PIAHOOFDCATEGORIECODE)
            .subcode(UPDATED_SUBCODE)
            .subcodeomschrijving(UPDATED_SUBCODEOMSCHRIJVING);

        restHoofdrekeningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHoofdrekening.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHoofdrekening))
            )
            .andExpect(status().isOk());

        // Validate the Hoofdrekening in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHoofdrekeningUpdatableFieldsEquals(partialUpdatedHoofdrekening, getPersistedHoofdrekening(partialUpdatedHoofdrekening));
    }

    @Test
    @Transactional
    void patchNonExistingHoofdrekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hoofdrekening.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHoofdrekeningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hoofdrekening.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hoofdrekening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hoofdrekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHoofdrekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hoofdrekening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoofdrekeningMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hoofdrekening))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hoofdrekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHoofdrekening() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hoofdrekening.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoofdrekeningMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(hoofdrekening)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hoofdrekening in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHoofdrekening() throws Exception {
        // Initialize the database
        hoofdrekeningRepository.saveAndFlush(hoofdrekening);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the hoofdrekening
        restHoofdrekeningMockMvc
            .perform(delete(ENTITY_API_URL_ID, hoofdrekening.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return hoofdrekeningRepository.count();
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

    protected Hoofdrekening getPersistedHoofdrekening(Hoofdrekening hoofdrekening) {
        return hoofdrekeningRepository.findById(hoofdrekening.getId()).orElseThrow();
    }

    protected void assertPersistedHoofdrekeningToMatchAllProperties(Hoofdrekening expectedHoofdrekening) {
        assertHoofdrekeningAllPropertiesEquals(expectedHoofdrekening, getPersistedHoofdrekening(expectedHoofdrekening));
    }

    protected void assertPersistedHoofdrekeningToMatchUpdatableProperties(Hoofdrekening expectedHoofdrekening) {
        assertHoofdrekeningAllUpdatablePropertiesEquals(expectedHoofdrekening, getPersistedHoofdrekening(expectedHoofdrekening));
    }
}
