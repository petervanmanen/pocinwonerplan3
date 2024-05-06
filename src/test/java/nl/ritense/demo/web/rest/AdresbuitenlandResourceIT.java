package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AdresbuitenlandAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Adresbuitenland;
import nl.ritense.demo.repository.AdresbuitenlandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AdresbuitenlandResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdresbuitenlandResourceIT {

    private static final String DEFAULT_ADRESREGELBUITENLAND_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADRESREGELBUITENLAND_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESREGELBUITENLAND_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADRESREGELBUITENLAND_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESREGELBUITENLAND_3 = "AAAAAAAAAA";
    private static final String UPDATED_ADRESREGELBUITENLAND_3 = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMAANVANGADRESBUITENLAND = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMAANVANGADRESBUITENLAND = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMINSCHRIJVINGGEMEENTE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMINSCHRIJVINGGEMEENTE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMVESTIGINGNEDERLAND = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMVESTIGINGNEDERLAND = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GEMEENTEVANINSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_GEMEENTEVANINSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_LANDADRESBUITENLAND = "AAAAAAAAAA";
    private static final String UPDATED_LANDADRESBUITENLAND = "BBBBBBBBBB";

    private static final String DEFAULT_LANDWAARVANDAANINGESCHREVEN = "AAAAAAAAAA";
    private static final String UPDATED_LANDWAARVANDAANINGESCHREVEN = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVINGVANDEAANGIFTEADRESHOUDING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVINGVANDEAANGIFTEADRESHOUDING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/adresbuitenlands";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AdresbuitenlandRepository adresbuitenlandRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdresbuitenlandMockMvc;

    private Adresbuitenland adresbuitenland;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adresbuitenland createEntity(EntityManager em) {
        Adresbuitenland adresbuitenland = new Adresbuitenland()
            .adresregelbuitenland1(DEFAULT_ADRESREGELBUITENLAND_1)
            .adresregelbuitenland2(DEFAULT_ADRESREGELBUITENLAND_2)
            .adresregelbuitenland3(DEFAULT_ADRESREGELBUITENLAND_3)
            .datumaanvangadresbuitenland(DEFAULT_DATUMAANVANGADRESBUITENLAND)
            .datuminschrijvinggemeente(DEFAULT_DATUMINSCHRIJVINGGEMEENTE)
            .datumvestigingnederland(DEFAULT_DATUMVESTIGINGNEDERLAND)
            .gemeentevaninschrijving(DEFAULT_GEMEENTEVANINSCHRIJVING)
            .landadresbuitenland(DEFAULT_LANDADRESBUITENLAND)
            .landwaarvandaaningeschreven(DEFAULT_LANDWAARVANDAANINGESCHREVEN)
            .omschrijvingvandeaangifteadreshouding(DEFAULT_OMSCHRIJVINGVANDEAANGIFTEADRESHOUDING);
        return adresbuitenland;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adresbuitenland createUpdatedEntity(EntityManager em) {
        Adresbuitenland adresbuitenland = new Adresbuitenland()
            .adresregelbuitenland1(UPDATED_ADRESREGELBUITENLAND_1)
            .adresregelbuitenland2(UPDATED_ADRESREGELBUITENLAND_2)
            .adresregelbuitenland3(UPDATED_ADRESREGELBUITENLAND_3)
            .datumaanvangadresbuitenland(UPDATED_DATUMAANVANGADRESBUITENLAND)
            .datuminschrijvinggemeente(UPDATED_DATUMINSCHRIJVINGGEMEENTE)
            .datumvestigingnederland(UPDATED_DATUMVESTIGINGNEDERLAND)
            .gemeentevaninschrijving(UPDATED_GEMEENTEVANINSCHRIJVING)
            .landadresbuitenland(UPDATED_LANDADRESBUITENLAND)
            .landwaarvandaaningeschreven(UPDATED_LANDWAARVANDAANINGESCHREVEN)
            .omschrijvingvandeaangifteadreshouding(UPDATED_OMSCHRIJVINGVANDEAANGIFTEADRESHOUDING);
        return adresbuitenland;
    }

    @BeforeEach
    public void initTest() {
        adresbuitenland = createEntity(em);
    }

    @Test
    @Transactional
    void createAdresbuitenland() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Adresbuitenland
        var returnedAdresbuitenland = om.readValue(
            restAdresbuitenlandMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adresbuitenland)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Adresbuitenland.class
        );

        // Validate the Adresbuitenland in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAdresbuitenlandUpdatableFieldsEquals(returnedAdresbuitenland, getPersistedAdresbuitenland(returnedAdresbuitenland));
    }

    @Test
    @Transactional
    void createAdresbuitenlandWithExistingId() throws Exception {
        // Create the Adresbuitenland with an existing ID
        adresbuitenland.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdresbuitenlandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adresbuitenland)))
            .andExpect(status().isBadRequest());

        // Validate the Adresbuitenland in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAdresbuitenlands() throws Exception {
        // Initialize the database
        adresbuitenlandRepository.saveAndFlush(adresbuitenland);

        // Get all the adresbuitenlandList
        restAdresbuitenlandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adresbuitenland.getId().intValue())))
            .andExpect(jsonPath("$.[*].adresregelbuitenland1").value(hasItem(DEFAULT_ADRESREGELBUITENLAND_1)))
            .andExpect(jsonPath("$.[*].adresregelbuitenland2").value(hasItem(DEFAULT_ADRESREGELBUITENLAND_2)))
            .andExpect(jsonPath("$.[*].adresregelbuitenland3").value(hasItem(DEFAULT_ADRESREGELBUITENLAND_3)))
            .andExpect(jsonPath("$.[*].datumaanvangadresbuitenland").value(hasItem(DEFAULT_DATUMAANVANGADRESBUITENLAND.toString())))
            .andExpect(jsonPath("$.[*].datuminschrijvinggemeente").value(hasItem(DEFAULT_DATUMINSCHRIJVINGGEMEENTE.toString())))
            .andExpect(jsonPath("$.[*].datumvestigingnederland").value(hasItem(DEFAULT_DATUMVESTIGINGNEDERLAND.toString())))
            .andExpect(jsonPath("$.[*].gemeentevaninschrijving").value(hasItem(DEFAULT_GEMEENTEVANINSCHRIJVING)))
            .andExpect(jsonPath("$.[*].landadresbuitenland").value(hasItem(DEFAULT_LANDADRESBUITENLAND)))
            .andExpect(jsonPath("$.[*].landwaarvandaaningeschreven").value(hasItem(DEFAULT_LANDWAARVANDAANINGESCHREVEN)))
            .andExpect(
                jsonPath("$.[*].omschrijvingvandeaangifteadreshouding").value(hasItem(DEFAULT_OMSCHRIJVINGVANDEAANGIFTEADRESHOUDING))
            );
    }

    @Test
    @Transactional
    void getAdresbuitenland() throws Exception {
        // Initialize the database
        adresbuitenlandRepository.saveAndFlush(adresbuitenland);

        // Get the adresbuitenland
        restAdresbuitenlandMockMvc
            .perform(get(ENTITY_API_URL_ID, adresbuitenland.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adresbuitenland.getId().intValue()))
            .andExpect(jsonPath("$.adresregelbuitenland1").value(DEFAULT_ADRESREGELBUITENLAND_1))
            .andExpect(jsonPath("$.adresregelbuitenland2").value(DEFAULT_ADRESREGELBUITENLAND_2))
            .andExpect(jsonPath("$.adresregelbuitenland3").value(DEFAULT_ADRESREGELBUITENLAND_3))
            .andExpect(jsonPath("$.datumaanvangadresbuitenland").value(DEFAULT_DATUMAANVANGADRESBUITENLAND.toString()))
            .andExpect(jsonPath("$.datuminschrijvinggemeente").value(DEFAULT_DATUMINSCHRIJVINGGEMEENTE.toString()))
            .andExpect(jsonPath("$.datumvestigingnederland").value(DEFAULT_DATUMVESTIGINGNEDERLAND.toString()))
            .andExpect(jsonPath("$.gemeentevaninschrijving").value(DEFAULT_GEMEENTEVANINSCHRIJVING))
            .andExpect(jsonPath("$.landadresbuitenland").value(DEFAULT_LANDADRESBUITENLAND))
            .andExpect(jsonPath("$.landwaarvandaaningeschreven").value(DEFAULT_LANDWAARVANDAANINGESCHREVEN))
            .andExpect(jsonPath("$.omschrijvingvandeaangifteadreshouding").value(DEFAULT_OMSCHRIJVINGVANDEAANGIFTEADRESHOUDING));
    }

    @Test
    @Transactional
    void getNonExistingAdresbuitenland() throws Exception {
        // Get the adresbuitenland
        restAdresbuitenlandMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAdresbuitenland() throws Exception {
        // Initialize the database
        adresbuitenlandRepository.saveAndFlush(adresbuitenland);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the adresbuitenland
        Adresbuitenland updatedAdresbuitenland = adresbuitenlandRepository.findById(adresbuitenland.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAdresbuitenland are not directly saved in db
        em.detach(updatedAdresbuitenland);
        updatedAdresbuitenland
            .adresregelbuitenland1(UPDATED_ADRESREGELBUITENLAND_1)
            .adresregelbuitenland2(UPDATED_ADRESREGELBUITENLAND_2)
            .adresregelbuitenland3(UPDATED_ADRESREGELBUITENLAND_3)
            .datumaanvangadresbuitenland(UPDATED_DATUMAANVANGADRESBUITENLAND)
            .datuminschrijvinggemeente(UPDATED_DATUMINSCHRIJVINGGEMEENTE)
            .datumvestigingnederland(UPDATED_DATUMVESTIGINGNEDERLAND)
            .gemeentevaninschrijving(UPDATED_GEMEENTEVANINSCHRIJVING)
            .landadresbuitenland(UPDATED_LANDADRESBUITENLAND)
            .landwaarvandaaningeschreven(UPDATED_LANDWAARVANDAANINGESCHREVEN)
            .omschrijvingvandeaangifteadreshouding(UPDATED_OMSCHRIJVINGVANDEAANGIFTEADRESHOUDING);

        restAdresbuitenlandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAdresbuitenland.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAdresbuitenland))
            )
            .andExpect(status().isOk());

        // Validate the Adresbuitenland in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAdresbuitenlandToMatchAllProperties(updatedAdresbuitenland);
    }

    @Test
    @Transactional
    void putNonExistingAdresbuitenland() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adresbuitenland.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdresbuitenlandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adresbuitenland.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(adresbuitenland))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adresbuitenland in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdresbuitenland() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adresbuitenland.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresbuitenlandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(adresbuitenland))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adresbuitenland in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdresbuitenland() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adresbuitenland.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresbuitenlandMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adresbuitenland)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Adresbuitenland in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdresbuitenlandWithPatch() throws Exception {
        // Initialize the database
        adresbuitenlandRepository.saveAndFlush(adresbuitenland);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the adresbuitenland using partial update
        Adresbuitenland partialUpdatedAdresbuitenland = new Adresbuitenland();
        partialUpdatedAdresbuitenland.setId(adresbuitenland.getId());

        partialUpdatedAdresbuitenland
            .adresregelbuitenland1(UPDATED_ADRESREGELBUITENLAND_1)
            .adresregelbuitenland3(UPDATED_ADRESREGELBUITENLAND_3)
            .datumvestigingnederland(UPDATED_DATUMVESTIGINGNEDERLAND)
            .gemeentevaninschrijving(UPDATED_GEMEENTEVANINSCHRIJVING)
            .landwaarvandaaningeschreven(UPDATED_LANDWAARVANDAANINGESCHREVEN)
            .omschrijvingvandeaangifteadreshouding(UPDATED_OMSCHRIJVINGVANDEAANGIFTEADRESHOUDING);

        restAdresbuitenlandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdresbuitenland.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAdresbuitenland))
            )
            .andExpect(status().isOk());

        // Validate the Adresbuitenland in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAdresbuitenlandUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAdresbuitenland, adresbuitenland),
            getPersistedAdresbuitenland(adresbuitenland)
        );
    }

    @Test
    @Transactional
    void fullUpdateAdresbuitenlandWithPatch() throws Exception {
        // Initialize the database
        adresbuitenlandRepository.saveAndFlush(adresbuitenland);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the adresbuitenland using partial update
        Adresbuitenland partialUpdatedAdresbuitenland = new Adresbuitenland();
        partialUpdatedAdresbuitenland.setId(adresbuitenland.getId());

        partialUpdatedAdresbuitenland
            .adresregelbuitenland1(UPDATED_ADRESREGELBUITENLAND_1)
            .adresregelbuitenland2(UPDATED_ADRESREGELBUITENLAND_2)
            .adresregelbuitenland3(UPDATED_ADRESREGELBUITENLAND_3)
            .datumaanvangadresbuitenland(UPDATED_DATUMAANVANGADRESBUITENLAND)
            .datuminschrijvinggemeente(UPDATED_DATUMINSCHRIJVINGGEMEENTE)
            .datumvestigingnederland(UPDATED_DATUMVESTIGINGNEDERLAND)
            .gemeentevaninschrijving(UPDATED_GEMEENTEVANINSCHRIJVING)
            .landadresbuitenland(UPDATED_LANDADRESBUITENLAND)
            .landwaarvandaaningeschreven(UPDATED_LANDWAARVANDAANINGESCHREVEN)
            .omschrijvingvandeaangifteadreshouding(UPDATED_OMSCHRIJVINGVANDEAANGIFTEADRESHOUDING);

        restAdresbuitenlandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdresbuitenland.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAdresbuitenland))
            )
            .andExpect(status().isOk());

        // Validate the Adresbuitenland in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAdresbuitenlandUpdatableFieldsEquals(
            partialUpdatedAdresbuitenland,
            getPersistedAdresbuitenland(partialUpdatedAdresbuitenland)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAdresbuitenland() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adresbuitenland.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdresbuitenlandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adresbuitenland.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(adresbuitenland))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adresbuitenland in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdresbuitenland() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adresbuitenland.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresbuitenlandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(adresbuitenland))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adresbuitenland in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdresbuitenland() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adresbuitenland.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresbuitenlandMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(adresbuitenland)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Adresbuitenland in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdresbuitenland() throws Exception {
        // Initialize the database
        adresbuitenlandRepository.saveAndFlush(adresbuitenland);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the adresbuitenland
        restAdresbuitenlandMockMvc
            .perform(delete(ENTITY_API_URL_ID, adresbuitenland.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return adresbuitenlandRepository.count();
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

    protected Adresbuitenland getPersistedAdresbuitenland(Adresbuitenland adresbuitenland) {
        return adresbuitenlandRepository.findById(adresbuitenland.getId()).orElseThrow();
    }

    protected void assertPersistedAdresbuitenlandToMatchAllProperties(Adresbuitenland expectedAdresbuitenland) {
        assertAdresbuitenlandAllPropertiesEquals(expectedAdresbuitenland, getPersistedAdresbuitenland(expectedAdresbuitenland));
    }

    protected void assertPersistedAdresbuitenlandToMatchUpdatableProperties(Adresbuitenland expectedAdresbuitenland) {
        assertAdresbuitenlandAllUpdatablePropertiesEquals(expectedAdresbuitenland, getPersistedAdresbuitenland(expectedAdresbuitenland));
    }
}
