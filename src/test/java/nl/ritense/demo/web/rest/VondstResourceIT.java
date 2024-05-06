package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VondstAsserts.*;
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
import nl.ritense.demo.domain.Vondst;
import nl.ritense.demo.repository.VondstRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VondstResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VondstResourceIT {

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_KEYVULLING = "AAAAAAAAAA";
    private static final String UPDATED_KEYVULLING = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_OMSTANDIGHEDEN = "AAAAAAAAAA";
    private static final String UPDATED_OMSTANDIGHEDEN = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECTCD = "AAAAAAAAAA";
    private static final String UPDATED_PROJECTCD = "BBBBBBBBBB";

    private static final String DEFAULT_PUTNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_PUTNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_SPOORNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_SPOORNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_VLAKNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_VLAKNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_VONDSTNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_VONDSTNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_VULLINGNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_VULLINGNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_XCOORDINAAT = "AAAAAAAAAA";
    private static final String UPDATED_XCOORDINAAT = "BBBBBBBBBB";

    private static final String DEFAULT_YCOORDINAAT = "AAAAAAAAAA";
    private static final String UPDATED_YCOORDINAAT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vondsts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VondstRepository vondstRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVondstMockMvc;

    private Vondst vondst;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vondst createEntity(EntityManager em) {
        Vondst vondst = new Vondst()
            .datum(DEFAULT_DATUM)
            .key(DEFAULT_KEY)
            .keyvulling(DEFAULT_KEYVULLING)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .omstandigheden(DEFAULT_OMSTANDIGHEDEN)
            .projectcd(DEFAULT_PROJECTCD)
            .putnummer(DEFAULT_PUTNUMMER)
            .spoornummer(DEFAULT_SPOORNUMMER)
            .vlaknummer(DEFAULT_VLAKNUMMER)
            .vondstnummer(DEFAULT_VONDSTNUMMER)
            .vullingnummer(DEFAULT_VULLINGNUMMER)
            .xcoordinaat(DEFAULT_XCOORDINAAT)
            .ycoordinaat(DEFAULT_YCOORDINAAT);
        return vondst;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vondst createUpdatedEntity(EntityManager em) {
        Vondst vondst = new Vondst()
            .datum(UPDATED_DATUM)
            .key(UPDATED_KEY)
            .keyvulling(UPDATED_KEYVULLING)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .omstandigheden(UPDATED_OMSTANDIGHEDEN)
            .projectcd(UPDATED_PROJECTCD)
            .putnummer(UPDATED_PUTNUMMER)
            .spoornummer(UPDATED_SPOORNUMMER)
            .vlaknummer(UPDATED_VLAKNUMMER)
            .vondstnummer(UPDATED_VONDSTNUMMER)
            .vullingnummer(UPDATED_VULLINGNUMMER)
            .xcoordinaat(UPDATED_XCOORDINAAT)
            .ycoordinaat(UPDATED_YCOORDINAAT);
        return vondst;
    }

    @BeforeEach
    public void initTest() {
        vondst = createEntity(em);
    }

    @Test
    @Transactional
    void createVondst() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vondst
        var returnedVondst = om.readValue(
            restVondstMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vondst)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Vondst.class
        );

        // Validate the Vondst in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVondstUpdatableFieldsEquals(returnedVondst, getPersistedVondst(returnedVondst));
    }

    @Test
    @Transactional
    void createVondstWithExistingId() throws Exception {
        // Create the Vondst with an existing ID
        vondst.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVondstMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vondst)))
            .andExpect(status().isBadRequest());

        // Validate the Vondst in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVondsts() throws Exception {
        // Initialize the database
        vondstRepository.saveAndFlush(vondst);

        // Get all the vondstList
        restVondstMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vondst.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].keyvulling").value(hasItem(DEFAULT_KEYVULLING)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].omstandigheden").value(hasItem(DEFAULT_OMSTANDIGHEDEN)))
            .andExpect(jsonPath("$.[*].projectcd").value(hasItem(DEFAULT_PROJECTCD)))
            .andExpect(jsonPath("$.[*].putnummer").value(hasItem(DEFAULT_PUTNUMMER)))
            .andExpect(jsonPath("$.[*].spoornummer").value(hasItem(DEFAULT_SPOORNUMMER)))
            .andExpect(jsonPath("$.[*].vlaknummer").value(hasItem(DEFAULT_VLAKNUMMER)))
            .andExpect(jsonPath("$.[*].vondstnummer").value(hasItem(DEFAULT_VONDSTNUMMER)))
            .andExpect(jsonPath("$.[*].vullingnummer").value(hasItem(DEFAULT_VULLINGNUMMER)))
            .andExpect(jsonPath("$.[*].xcoordinaat").value(hasItem(DEFAULT_XCOORDINAAT)))
            .andExpect(jsonPath("$.[*].ycoordinaat").value(hasItem(DEFAULT_YCOORDINAAT)));
    }

    @Test
    @Transactional
    void getVondst() throws Exception {
        // Initialize the database
        vondstRepository.saveAndFlush(vondst);

        // Get the vondst
        restVondstMockMvc
            .perform(get(ENTITY_API_URL_ID, vondst.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vondst.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.keyvulling").value(DEFAULT_KEYVULLING))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.omstandigheden").value(DEFAULT_OMSTANDIGHEDEN))
            .andExpect(jsonPath("$.projectcd").value(DEFAULT_PROJECTCD))
            .andExpect(jsonPath("$.putnummer").value(DEFAULT_PUTNUMMER))
            .andExpect(jsonPath("$.spoornummer").value(DEFAULT_SPOORNUMMER))
            .andExpect(jsonPath("$.vlaknummer").value(DEFAULT_VLAKNUMMER))
            .andExpect(jsonPath("$.vondstnummer").value(DEFAULT_VONDSTNUMMER))
            .andExpect(jsonPath("$.vullingnummer").value(DEFAULT_VULLINGNUMMER))
            .andExpect(jsonPath("$.xcoordinaat").value(DEFAULT_XCOORDINAAT))
            .andExpect(jsonPath("$.ycoordinaat").value(DEFAULT_YCOORDINAAT));
    }

    @Test
    @Transactional
    void getNonExistingVondst() throws Exception {
        // Get the vondst
        restVondstMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVondst() throws Exception {
        // Initialize the database
        vondstRepository.saveAndFlush(vondst);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vondst
        Vondst updatedVondst = vondstRepository.findById(vondst.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVondst are not directly saved in db
        em.detach(updatedVondst);
        updatedVondst
            .datum(UPDATED_DATUM)
            .key(UPDATED_KEY)
            .keyvulling(UPDATED_KEYVULLING)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .omstandigheden(UPDATED_OMSTANDIGHEDEN)
            .projectcd(UPDATED_PROJECTCD)
            .putnummer(UPDATED_PUTNUMMER)
            .spoornummer(UPDATED_SPOORNUMMER)
            .vlaknummer(UPDATED_VLAKNUMMER)
            .vondstnummer(UPDATED_VONDSTNUMMER)
            .vullingnummer(UPDATED_VULLINGNUMMER)
            .xcoordinaat(UPDATED_XCOORDINAAT)
            .ycoordinaat(UPDATED_YCOORDINAAT);

        restVondstMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVondst.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVondst))
            )
            .andExpect(status().isOk());

        // Validate the Vondst in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVondstToMatchAllProperties(updatedVondst);
    }

    @Test
    @Transactional
    void putNonExistingVondst() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vondst.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVondstMockMvc
            .perform(put(ENTITY_API_URL_ID, vondst.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vondst)))
            .andExpect(status().isBadRequest());

        // Validate the Vondst in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVondst() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vondst.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVondstMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vondst))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vondst in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVondst() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vondst.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVondstMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vondst)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vondst in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVondstWithPatch() throws Exception {
        // Initialize the database
        vondstRepository.saveAndFlush(vondst);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vondst using partial update
        Vondst partialUpdatedVondst = new Vondst();
        partialUpdatedVondst.setId(vondst.getId());

        partialUpdatedVondst
            .datum(UPDATED_DATUM)
            .key(UPDATED_KEY)
            .projectcd(UPDATED_PROJECTCD)
            .putnummer(UPDATED_PUTNUMMER)
            .spoornummer(UPDATED_SPOORNUMMER)
            .vlaknummer(UPDATED_VLAKNUMMER)
            .vondstnummer(UPDATED_VONDSTNUMMER)
            .ycoordinaat(UPDATED_YCOORDINAAT);

        restVondstMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVondst.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVondst))
            )
            .andExpect(status().isOk());

        // Validate the Vondst in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVondstUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedVondst, vondst), getPersistedVondst(vondst));
    }

    @Test
    @Transactional
    void fullUpdateVondstWithPatch() throws Exception {
        // Initialize the database
        vondstRepository.saveAndFlush(vondst);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vondst using partial update
        Vondst partialUpdatedVondst = new Vondst();
        partialUpdatedVondst.setId(vondst.getId());

        partialUpdatedVondst
            .datum(UPDATED_DATUM)
            .key(UPDATED_KEY)
            .keyvulling(UPDATED_KEYVULLING)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .omstandigheden(UPDATED_OMSTANDIGHEDEN)
            .projectcd(UPDATED_PROJECTCD)
            .putnummer(UPDATED_PUTNUMMER)
            .spoornummer(UPDATED_SPOORNUMMER)
            .vlaknummer(UPDATED_VLAKNUMMER)
            .vondstnummer(UPDATED_VONDSTNUMMER)
            .vullingnummer(UPDATED_VULLINGNUMMER)
            .xcoordinaat(UPDATED_XCOORDINAAT)
            .ycoordinaat(UPDATED_YCOORDINAAT);

        restVondstMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVondst.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVondst))
            )
            .andExpect(status().isOk());

        // Validate the Vondst in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVondstUpdatableFieldsEquals(partialUpdatedVondst, getPersistedVondst(partialUpdatedVondst));
    }

    @Test
    @Transactional
    void patchNonExistingVondst() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vondst.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVondstMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vondst.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vondst))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vondst in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVondst() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vondst.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVondstMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vondst))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vondst in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVondst() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vondst.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVondstMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vondst)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vondst in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVondst() throws Exception {
        // Initialize the database
        vondstRepository.saveAndFlush(vondst);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vondst
        restVondstMockMvc
            .perform(delete(ENTITY_API_URL_ID, vondst.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vondstRepository.count();
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

    protected Vondst getPersistedVondst(Vondst vondst) {
        return vondstRepository.findById(vondst.getId()).orElseThrow();
    }

    protected void assertPersistedVondstToMatchAllProperties(Vondst expectedVondst) {
        assertVondstAllPropertiesEquals(expectedVondst, getPersistedVondst(expectedVondst));
    }

    protected void assertPersistedVondstToMatchUpdatableProperties(Vondst expectedVondst) {
        assertVondstAllUpdatablePropertiesEquals(expectedVondst, getPersistedVondst(expectedVondst));
    }
}
