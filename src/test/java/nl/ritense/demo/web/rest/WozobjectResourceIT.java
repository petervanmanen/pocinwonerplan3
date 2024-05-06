package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.WozobjectAsserts.*;
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
import nl.ritense.demo.domain.Wozobject;
import nl.ritense.demo.repository.WozobjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WozobjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WozobjectResourceIT {

    private static final String DEFAULT_EMPTY = "AAAAAAAAAA";
    private static final String UPDATED_EMPTY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDWOZOBJECT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDWOZOBJECT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDWOZOBJECT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDWOZOBJECT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMWAARDEPEILING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMWAARDEPEILING = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GEBRUIKSCODE = "AAAAAAAAAA";
    private static final String UPDATED_GEBRUIKSCODE = "BBBBBBBBBB";

    private static final String DEFAULT_GEOMETRIEWOZOBJECT = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIEWOZOBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_GRONDOPPERVLAKTE = "AAAAAAAAAA";
    private static final String UPDATED_GRONDOPPERVLAKTE = "BBBBBBBBBB";

    private static final String DEFAULT_SOORTOBJECTCODE = "AAAAAAAAAA";
    private static final String UPDATED_SOORTOBJECTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUSWOZOBJECT = "AAAAAAAAAA";
    private static final String UPDATED_STATUSWOZOBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_VASTGESTELDEWAARDE = "AAAAAAAAAA";
    private static final String UPDATED_VASTGESTELDEWAARDE = "BBBBBBBBBB";

    private static final String DEFAULT_WOZOBJECTNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_WOZOBJECTNUMMER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/wozobjects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WozobjectRepository wozobjectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWozobjectMockMvc;

    private Wozobject wozobject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wozobject createEntity(EntityManager em) {
        Wozobject wozobject = new Wozobject()
            .empty(DEFAULT_EMPTY)
            .datumbegingeldigheidwozobject(DEFAULT_DATUMBEGINGELDIGHEIDWOZOBJECT)
            .datumeindegeldigheidwozobject(DEFAULT_DATUMEINDEGELDIGHEIDWOZOBJECT)
            .datumwaardepeiling(DEFAULT_DATUMWAARDEPEILING)
            .gebruikscode(DEFAULT_GEBRUIKSCODE)
            .geometriewozobject(DEFAULT_GEOMETRIEWOZOBJECT)
            .grondoppervlakte(DEFAULT_GRONDOPPERVLAKTE)
            .soortobjectcode(DEFAULT_SOORTOBJECTCODE)
            .statuswozobject(DEFAULT_STATUSWOZOBJECT)
            .vastgesteldewaarde(DEFAULT_VASTGESTELDEWAARDE)
            .wozobjectnummer(DEFAULT_WOZOBJECTNUMMER);
        return wozobject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wozobject createUpdatedEntity(EntityManager em) {
        Wozobject wozobject = new Wozobject()
            .empty(UPDATED_EMPTY)
            .datumbegingeldigheidwozobject(UPDATED_DATUMBEGINGELDIGHEIDWOZOBJECT)
            .datumeindegeldigheidwozobject(UPDATED_DATUMEINDEGELDIGHEIDWOZOBJECT)
            .datumwaardepeiling(UPDATED_DATUMWAARDEPEILING)
            .gebruikscode(UPDATED_GEBRUIKSCODE)
            .geometriewozobject(UPDATED_GEOMETRIEWOZOBJECT)
            .grondoppervlakte(UPDATED_GRONDOPPERVLAKTE)
            .soortobjectcode(UPDATED_SOORTOBJECTCODE)
            .statuswozobject(UPDATED_STATUSWOZOBJECT)
            .vastgesteldewaarde(UPDATED_VASTGESTELDEWAARDE)
            .wozobjectnummer(UPDATED_WOZOBJECTNUMMER);
        return wozobject;
    }

    @BeforeEach
    public void initTest() {
        wozobject = createEntity(em);
    }

    @Test
    @Transactional
    void createWozobject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Wozobject
        var returnedWozobject = om.readValue(
            restWozobjectMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wozobject)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Wozobject.class
        );

        // Validate the Wozobject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertWozobjectUpdatableFieldsEquals(returnedWozobject, getPersistedWozobject(returnedWozobject));
    }

    @Test
    @Transactional
    void createWozobjectWithExistingId() throws Exception {
        // Create the Wozobject with an existing ID
        wozobject.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWozobjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wozobject)))
            .andExpect(status().isBadRequest());

        // Validate the Wozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWozobjects() throws Exception {
        // Initialize the database
        wozobjectRepository.saveAndFlush(wozobject);

        // Get all the wozobjectList
        restWozobjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wozobject.getId().intValue())))
            .andExpect(jsonPath("$.[*].empty").value(hasItem(DEFAULT_EMPTY)))
            .andExpect(jsonPath("$.[*].datumbegingeldigheidwozobject").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEIDWOZOBJECT.toString())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheidwozobject").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEIDWOZOBJECT.toString())))
            .andExpect(jsonPath("$.[*].datumwaardepeiling").value(hasItem(DEFAULT_DATUMWAARDEPEILING.toString())))
            .andExpect(jsonPath("$.[*].gebruikscode").value(hasItem(DEFAULT_GEBRUIKSCODE)))
            .andExpect(jsonPath("$.[*].geometriewozobject").value(hasItem(DEFAULT_GEOMETRIEWOZOBJECT)))
            .andExpect(jsonPath("$.[*].grondoppervlakte").value(hasItem(DEFAULT_GRONDOPPERVLAKTE)))
            .andExpect(jsonPath("$.[*].soortobjectcode").value(hasItem(DEFAULT_SOORTOBJECTCODE)))
            .andExpect(jsonPath("$.[*].statuswozobject").value(hasItem(DEFAULT_STATUSWOZOBJECT)))
            .andExpect(jsonPath("$.[*].vastgesteldewaarde").value(hasItem(DEFAULT_VASTGESTELDEWAARDE)))
            .andExpect(jsonPath("$.[*].wozobjectnummer").value(hasItem(DEFAULT_WOZOBJECTNUMMER)));
    }

    @Test
    @Transactional
    void getWozobject() throws Exception {
        // Initialize the database
        wozobjectRepository.saveAndFlush(wozobject);

        // Get the wozobject
        restWozobjectMockMvc
            .perform(get(ENTITY_API_URL_ID, wozobject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wozobject.getId().intValue()))
            .andExpect(jsonPath("$.empty").value(DEFAULT_EMPTY))
            .andExpect(jsonPath("$.datumbegingeldigheidwozobject").value(DEFAULT_DATUMBEGINGELDIGHEIDWOZOBJECT.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheidwozobject").value(DEFAULT_DATUMEINDEGELDIGHEIDWOZOBJECT.toString()))
            .andExpect(jsonPath("$.datumwaardepeiling").value(DEFAULT_DATUMWAARDEPEILING.toString()))
            .andExpect(jsonPath("$.gebruikscode").value(DEFAULT_GEBRUIKSCODE))
            .andExpect(jsonPath("$.geometriewozobject").value(DEFAULT_GEOMETRIEWOZOBJECT))
            .andExpect(jsonPath("$.grondoppervlakte").value(DEFAULT_GRONDOPPERVLAKTE))
            .andExpect(jsonPath("$.soortobjectcode").value(DEFAULT_SOORTOBJECTCODE))
            .andExpect(jsonPath("$.statuswozobject").value(DEFAULT_STATUSWOZOBJECT))
            .andExpect(jsonPath("$.vastgesteldewaarde").value(DEFAULT_VASTGESTELDEWAARDE))
            .andExpect(jsonPath("$.wozobjectnummer").value(DEFAULT_WOZOBJECTNUMMER));
    }

    @Test
    @Transactional
    void getNonExistingWozobject() throws Exception {
        // Get the wozobject
        restWozobjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWozobject() throws Exception {
        // Initialize the database
        wozobjectRepository.saveAndFlush(wozobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the wozobject
        Wozobject updatedWozobject = wozobjectRepository.findById(wozobject.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWozobject are not directly saved in db
        em.detach(updatedWozobject);
        updatedWozobject
            .empty(UPDATED_EMPTY)
            .datumbegingeldigheidwozobject(UPDATED_DATUMBEGINGELDIGHEIDWOZOBJECT)
            .datumeindegeldigheidwozobject(UPDATED_DATUMEINDEGELDIGHEIDWOZOBJECT)
            .datumwaardepeiling(UPDATED_DATUMWAARDEPEILING)
            .gebruikscode(UPDATED_GEBRUIKSCODE)
            .geometriewozobject(UPDATED_GEOMETRIEWOZOBJECT)
            .grondoppervlakte(UPDATED_GRONDOPPERVLAKTE)
            .soortobjectcode(UPDATED_SOORTOBJECTCODE)
            .statuswozobject(UPDATED_STATUSWOZOBJECT)
            .vastgesteldewaarde(UPDATED_VASTGESTELDEWAARDE)
            .wozobjectnummer(UPDATED_WOZOBJECTNUMMER);

        restWozobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWozobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedWozobject))
            )
            .andExpect(status().isOk());

        // Validate the Wozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWozobjectToMatchAllProperties(updatedWozobject);
    }

    @Test
    @Transactional
    void putNonExistingWozobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wozobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWozobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, wozobject.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wozobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWozobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wozobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWozobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(wozobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWozobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wozobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWozobjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wozobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Wozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWozobjectWithPatch() throws Exception {
        // Initialize the database
        wozobjectRepository.saveAndFlush(wozobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the wozobject using partial update
        Wozobject partialUpdatedWozobject = new Wozobject();
        partialUpdatedWozobject.setId(wozobject.getId());

        partialUpdatedWozobject
            .datumeindegeldigheidwozobject(UPDATED_DATUMEINDEGELDIGHEIDWOZOBJECT)
            .gebruikscode(UPDATED_GEBRUIKSCODE)
            .grondoppervlakte(UPDATED_GRONDOPPERVLAKTE)
            .soortobjectcode(UPDATED_SOORTOBJECTCODE)
            .statuswozobject(UPDATED_STATUSWOZOBJECT);

        restWozobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWozobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWozobject))
            )
            .andExpect(status().isOk());

        // Validate the Wozobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWozobjectUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedWozobject, wozobject),
            getPersistedWozobject(wozobject)
        );
    }

    @Test
    @Transactional
    void fullUpdateWozobjectWithPatch() throws Exception {
        // Initialize the database
        wozobjectRepository.saveAndFlush(wozobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the wozobject using partial update
        Wozobject partialUpdatedWozobject = new Wozobject();
        partialUpdatedWozobject.setId(wozobject.getId());

        partialUpdatedWozobject
            .empty(UPDATED_EMPTY)
            .datumbegingeldigheidwozobject(UPDATED_DATUMBEGINGELDIGHEIDWOZOBJECT)
            .datumeindegeldigheidwozobject(UPDATED_DATUMEINDEGELDIGHEIDWOZOBJECT)
            .datumwaardepeiling(UPDATED_DATUMWAARDEPEILING)
            .gebruikscode(UPDATED_GEBRUIKSCODE)
            .geometriewozobject(UPDATED_GEOMETRIEWOZOBJECT)
            .grondoppervlakte(UPDATED_GRONDOPPERVLAKTE)
            .soortobjectcode(UPDATED_SOORTOBJECTCODE)
            .statuswozobject(UPDATED_STATUSWOZOBJECT)
            .vastgesteldewaarde(UPDATED_VASTGESTELDEWAARDE)
            .wozobjectnummer(UPDATED_WOZOBJECTNUMMER);

        restWozobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWozobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWozobject))
            )
            .andExpect(status().isOk());

        // Validate the Wozobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWozobjectUpdatableFieldsEquals(partialUpdatedWozobject, getPersistedWozobject(partialUpdatedWozobject));
    }

    @Test
    @Transactional
    void patchNonExistingWozobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wozobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWozobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, wozobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(wozobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWozobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wozobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWozobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(wozobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWozobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        wozobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWozobjectMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(wozobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Wozobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWozobject() throws Exception {
        // Initialize the database
        wozobjectRepository.saveAndFlush(wozobject);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the wozobject
        restWozobjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, wozobject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return wozobjectRepository.count();
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

    protected Wozobject getPersistedWozobject(Wozobject wozobject) {
        return wozobjectRepository.findById(wozobject.getId()).orElseThrow();
    }

    protected void assertPersistedWozobjectToMatchAllProperties(Wozobject expectedWozobject) {
        assertWozobjectAllPropertiesEquals(expectedWozobject, getPersistedWozobject(expectedWozobject));
    }

    protected void assertPersistedWozobjectToMatchUpdatableProperties(Wozobject expectedWozobject) {
        assertWozobjectAllUpdatablePropertiesEquals(expectedWozobject, getPersistedWozobject(expectedWozobject));
    }
}
