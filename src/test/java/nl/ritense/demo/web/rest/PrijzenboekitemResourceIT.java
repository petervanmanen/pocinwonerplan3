package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.PrijzenboekitemAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static nl.ritense.demo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Prijzenboekitem;
import nl.ritense.demo.repository.PrijzenboekitemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PrijzenboekitemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PrijzenboekitemResourceIT {

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEID = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_PRIJS = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRIJS = new BigDecimal(2);

    private static final String DEFAULT_VERRICHTING = "AAAAAAAAAA";
    private static final String UPDATED_VERRICHTING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/prijzenboekitems";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PrijzenboekitemRepository prijzenboekitemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrijzenboekitemMockMvc;

    private Prijzenboekitem prijzenboekitem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prijzenboekitem createEntity(EntityManager em) {
        Prijzenboekitem prijzenboekitem = new Prijzenboekitem()
            .datumeindegeldigheid(DEFAULT_DATUMEINDEGELDIGHEID)
            .datumstart(DEFAULT_DATUMSTART)
            .prijs(DEFAULT_PRIJS)
            .verrichting(DEFAULT_VERRICHTING);
        return prijzenboekitem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prijzenboekitem createUpdatedEntity(EntityManager em) {
        Prijzenboekitem prijzenboekitem = new Prijzenboekitem()
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumstart(UPDATED_DATUMSTART)
            .prijs(UPDATED_PRIJS)
            .verrichting(UPDATED_VERRICHTING);
        return prijzenboekitem;
    }

    @BeforeEach
    public void initTest() {
        prijzenboekitem = createEntity(em);
    }

    @Test
    @Transactional
    void createPrijzenboekitem() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Prijzenboekitem
        var returnedPrijzenboekitem = om.readValue(
            restPrijzenboekitemMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prijzenboekitem)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Prijzenboekitem.class
        );

        // Validate the Prijzenboekitem in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPrijzenboekitemUpdatableFieldsEquals(returnedPrijzenboekitem, getPersistedPrijzenboekitem(returnedPrijzenboekitem));
    }

    @Test
    @Transactional
    void createPrijzenboekitemWithExistingId() throws Exception {
        // Create the Prijzenboekitem with an existing ID
        prijzenboekitem.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrijzenboekitemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prijzenboekitem)))
            .andExpect(status().isBadRequest());

        // Validate the Prijzenboekitem in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPrijzenboekitems() throws Exception {
        // Initialize the database
        prijzenboekitemRepository.saveAndFlush(prijzenboekitem);

        // Get all the prijzenboekitemList
        restPrijzenboekitemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prijzenboekitem.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheid").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEID.toString())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())))
            .andExpect(jsonPath("$.[*].prijs").value(hasItem(sameNumber(DEFAULT_PRIJS))))
            .andExpect(jsonPath("$.[*].verrichting").value(hasItem(DEFAULT_VERRICHTING)));
    }

    @Test
    @Transactional
    void getPrijzenboekitem() throws Exception {
        // Initialize the database
        prijzenboekitemRepository.saveAndFlush(prijzenboekitem);

        // Get the prijzenboekitem
        restPrijzenboekitemMockMvc
            .perform(get(ENTITY_API_URL_ID, prijzenboekitem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prijzenboekitem.getId().intValue()))
            .andExpect(jsonPath("$.datumeindegeldigheid").value(DEFAULT_DATUMEINDEGELDIGHEID.toString()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()))
            .andExpect(jsonPath("$.prijs").value(sameNumber(DEFAULT_PRIJS)))
            .andExpect(jsonPath("$.verrichting").value(DEFAULT_VERRICHTING));
    }

    @Test
    @Transactional
    void getNonExistingPrijzenboekitem() throws Exception {
        // Get the prijzenboekitem
        restPrijzenboekitemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPrijzenboekitem() throws Exception {
        // Initialize the database
        prijzenboekitemRepository.saveAndFlush(prijzenboekitem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prijzenboekitem
        Prijzenboekitem updatedPrijzenboekitem = prijzenboekitemRepository.findById(prijzenboekitem.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPrijzenboekitem are not directly saved in db
        em.detach(updatedPrijzenboekitem);
        updatedPrijzenboekitem
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumstart(UPDATED_DATUMSTART)
            .prijs(UPDATED_PRIJS)
            .verrichting(UPDATED_VERRICHTING);

        restPrijzenboekitemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPrijzenboekitem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPrijzenboekitem))
            )
            .andExpect(status().isOk());

        // Validate the Prijzenboekitem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPrijzenboekitemToMatchAllProperties(updatedPrijzenboekitem);
    }

    @Test
    @Transactional
    void putNonExistingPrijzenboekitem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prijzenboekitem.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrijzenboekitemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prijzenboekitem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(prijzenboekitem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prijzenboekitem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrijzenboekitem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prijzenboekitem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrijzenboekitemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(prijzenboekitem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prijzenboekitem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrijzenboekitem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prijzenboekitem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrijzenboekitemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prijzenboekitem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prijzenboekitem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePrijzenboekitemWithPatch() throws Exception {
        // Initialize the database
        prijzenboekitemRepository.saveAndFlush(prijzenboekitem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prijzenboekitem using partial update
        Prijzenboekitem partialUpdatedPrijzenboekitem = new Prijzenboekitem();
        partialUpdatedPrijzenboekitem.setId(prijzenboekitem.getId());

        partialUpdatedPrijzenboekitem.prijs(UPDATED_PRIJS);

        restPrijzenboekitemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrijzenboekitem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPrijzenboekitem))
            )
            .andExpect(status().isOk());

        // Validate the Prijzenboekitem in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPrijzenboekitemUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPrijzenboekitem, prijzenboekitem),
            getPersistedPrijzenboekitem(prijzenboekitem)
        );
    }

    @Test
    @Transactional
    void fullUpdatePrijzenboekitemWithPatch() throws Exception {
        // Initialize the database
        prijzenboekitemRepository.saveAndFlush(prijzenboekitem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prijzenboekitem using partial update
        Prijzenboekitem partialUpdatedPrijzenboekitem = new Prijzenboekitem();
        partialUpdatedPrijzenboekitem.setId(prijzenboekitem.getId());

        partialUpdatedPrijzenboekitem
            .datumeindegeldigheid(UPDATED_DATUMEINDEGELDIGHEID)
            .datumstart(UPDATED_DATUMSTART)
            .prijs(UPDATED_PRIJS)
            .verrichting(UPDATED_VERRICHTING);

        restPrijzenboekitemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrijzenboekitem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPrijzenboekitem))
            )
            .andExpect(status().isOk());

        // Validate the Prijzenboekitem in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPrijzenboekitemUpdatableFieldsEquals(
            partialUpdatedPrijzenboekitem,
            getPersistedPrijzenboekitem(partialUpdatedPrijzenboekitem)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPrijzenboekitem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prijzenboekitem.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrijzenboekitemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, prijzenboekitem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(prijzenboekitem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prijzenboekitem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrijzenboekitem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prijzenboekitem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrijzenboekitemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(prijzenboekitem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prijzenboekitem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrijzenboekitem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prijzenboekitem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrijzenboekitemMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(prijzenboekitem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prijzenboekitem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrijzenboekitem() throws Exception {
        // Initialize the database
        prijzenboekitemRepository.saveAndFlush(prijzenboekitem);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the prijzenboekitem
        restPrijzenboekitemMockMvc
            .perform(delete(ENTITY_API_URL_ID, prijzenboekitem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return prijzenboekitemRepository.count();
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

    protected Prijzenboekitem getPersistedPrijzenboekitem(Prijzenboekitem prijzenboekitem) {
        return prijzenboekitemRepository.findById(prijzenboekitem.getId()).orElseThrow();
    }

    protected void assertPersistedPrijzenboekitemToMatchAllProperties(Prijzenboekitem expectedPrijzenboekitem) {
        assertPrijzenboekitemAllPropertiesEquals(expectedPrijzenboekitem, getPersistedPrijzenboekitem(expectedPrijzenboekitem));
    }

    protected void assertPersistedPrijzenboekitemToMatchUpdatableProperties(Prijzenboekitem expectedPrijzenboekitem) {
        assertPrijzenboekitemAllUpdatablePropertiesEquals(expectedPrijzenboekitem, getPersistedPrijzenboekitem(expectedPrijzenboekitem));
    }
}
