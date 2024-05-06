package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BakAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Bak;
import nl.ritense.demo.repository.BakRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BakResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BakResourceIT {

    private static final String DEFAULT_BREEDTE = "AAAAAAAAAA";
    private static final String UPDATED_BREEDTE = "BBBBBBBBBB";

    private static final String DEFAULT_DIAMETER = "AAAAAAAAAA";
    private static final String UPDATED_DIAMETER = "BBBBBBBBBB";

    private static final String DEFAULT_GEWICHTLEEG = "AAAAAAAAAA";
    private static final String UPDATED_GEWICHTLEEG = "BBBBBBBBBB";

    private static final String DEFAULT_GEWICHTVOL = "AAAAAAAAAA";
    private static final String UPDATED_GEWICHTVOL = "BBBBBBBBBB";

    private static final String DEFAULT_HOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_HOOGTE = "BBBBBBBBBB";

    private static final String DEFAULT_INHOUD = "AAAAAAAAAA";
    private static final String UPDATED_INHOUD = "BBBBBBBBBB";

    private static final String DEFAULT_JAARONDERHOUDUITGEVOERD = "AAAAAAAAAA";
    private static final String UPDATED_JAARONDERHOUDUITGEVOERD = "BBBBBBBBBB";

    private static final String DEFAULT_KWALITEITSNIVEAUACTUEEL = "AAAAAAAAAA";
    private static final String UPDATED_KWALITEITSNIVEAUACTUEEL = "BBBBBBBBBB";

    private static final String DEFAULT_KWALITEITSNIVEAUGEWENST = "AAAAAAAAAA";
    private static final String UPDATED_KWALITEITSNIVEAUGEWENST = "BBBBBBBBBB";

    private static final String DEFAULT_LENGTE = "AAAAAAAAAA";
    private static final String UPDATED_LENGTE = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIAAL = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAAL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VERPLAATSBAAR = false;
    private static final Boolean UPDATED_VERPLAATSBAAR = true;

    private static final String DEFAULT_VORM = "AAAAAAAAAA";
    private static final String UPDATED_VORM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/baks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BakRepository bakRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBakMockMvc;

    private Bak bak;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bak createEntity(EntityManager em) {
        Bak bak = new Bak()
            .breedte(DEFAULT_BREEDTE)
            .diameter(DEFAULT_DIAMETER)
            .gewichtleeg(DEFAULT_GEWICHTLEEG)
            .gewichtvol(DEFAULT_GEWICHTVOL)
            .hoogte(DEFAULT_HOOGTE)
            .inhoud(DEFAULT_INHOUD)
            .jaaronderhouduitgevoerd(DEFAULT_JAARONDERHOUDUITGEVOERD)
            .kwaliteitsniveauactueel(DEFAULT_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(DEFAULT_KWALITEITSNIVEAUGEWENST)
            .lengte(DEFAULT_LENGTE)
            .materiaal(DEFAULT_MATERIAAL)
            .verplaatsbaar(DEFAULT_VERPLAATSBAAR)
            .vorm(DEFAULT_VORM);
        return bak;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bak createUpdatedEntity(EntityManager em) {
        Bak bak = new Bak()
            .breedte(UPDATED_BREEDTE)
            .diameter(UPDATED_DIAMETER)
            .gewichtleeg(UPDATED_GEWICHTLEEG)
            .gewichtvol(UPDATED_GEWICHTVOL)
            .hoogte(UPDATED_HOOGTE)
            .inhoud(UPDATED_INHOUD)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .lengte(UPDATED_LENGTE)
            .materiaal(UPDATED_MATERIAAL)
            .verplaatsbaar(UPDATED_VERPLAATSBAAR)
            .vorm(UPDATED_VORM);
        return bak;
    }

    @BeforeEach
    public void initTest() {
        bak = createEntity(em);
    }

    @Test
    @Transactional
    void createBak() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Bak
        var returnedBak = om.readValue(
            restBakMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bak)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Bak.class
        );

        // Validate the Bak in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBakUpdatableFieldsEquals(returnedBak, getPersistedBak(returnedBak));
    }

    @Test
    @Transactional
    void createBakWithExistingId() throws Exception {
        // Create the Bak with an existing ID
        bak.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBakMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bak)))
            .andExpect(status().isBadRequest());

        // Validate the Bak in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBaks() throws Exception {
        // Initialize the database
        bakRepository.saveAndFlush(bak);

        // Get all the bakList
        restBakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bak.getId().intValue())))
            .andExpect(jsonPath("$.[*].breedte").value(hasItem(DEFAULT_BREEDTE)))
            .andExpect(jsonPath("$.[*].diameter").value(hasItem(DEFAULT_DIAMETER)))
            .andExpect(jsonPath("$.[*].gewichtleeg").value(hasItem(DEFAULT_GEWICHTLEEG)))
            .andExpect(jsonPath("$.[*].gewichtvol").value(hasItem(DEFAULT_GEWICHTVOL)))
            .andExpect(jsonPath("$.[*].hoogte").value(hasItem(DEFAULT_HOOGTE)))
            .andExpect(jsonPath("$.[*].inhoud").value(hasItem(DEFAULT_INHOUD)))
            .andExpect(jsonPath("$.[*].jaaronderhouduitgevoerd").value(hasItem(DEFAULT_JAARONDERHOUDUITGEVOERD)))
            .andExpect(jsonPath("$.[*].kwaliteitsniveauactueel").value(hasItem(DEFAULT_KWALITEITSNIVEAUACTUEEL)))
            .andExpect(jsonPath("$.[*].kwaliteitsniveaugewenst").value(hasItem(DEFAULT_KWALITEITSNIVEAUGEWENST)))
            .andExpect(jsonPath("$.[*].lengte").value(hasItem(DEFAULT_LENGTE)))
            .andExpect(jsonPath("$.[*].materiaal").value(hasItem(DEFAULT_MATERIAAL)))
            .andExpect(jsonPath("$.[*].verplaatsbaar").value(hasItem(DEFAULT_VERPLAATSBAAR.booleanValue())))
            .andExpect(jsonPath("$.[*].vorm").value(hasItem(DEFAULT_VORM)));
    }

    @Test
    @Transactional
    void getBak() throws Exception {
        // Initialize the database
        bakRepository.saveAndFlush(bak);

        // Get the bak
        restBakMockMvc
            .perform(get(ENTITY_API_URL_ID, bak.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bak.getId().intValue()))
            .andExpect(jsonPath("$.breedte").value(DEFAULT_BREEDTE))
            .andExpect(jsonPath("$.diameter").value(DEFAULT_DIAMETER))
            .andExpect(jsonPath("$.gewichtleeg").value(DEFAULT_GEWICHTLEEG))
            .andExpect(jsonPath("$.gewichtvol").value(DEFAULT_GEWICHTVOL))
            .andExpect(jsonPath("$.hoogte").value(DEFAULT_HOOGTE))
            .andExpect(jsonPath("$.inhoud").value(DEFAULT_INHOUD))
            .andExpect(jsonPath("$.jaaronderhouduitgevoerd").value(DEFAULT_JAARONDERHOUDUITGEVOERD))
            .andExpect(jsonPath("$.kwaliteitsniveauactueel").value(DEFAULT_KWALITEITSNIVEAUACTUEEL))
            .andExpect(jsonPath("$.kwaliteitsniveaugewenst").value(DEFAULT_KWALITEITSNIVEAUGEWENST))
            .andExpect(jsonPath("$.lengte").value(DEFAULT_LENGTE))
            .andExpect(jsonPath("$.materiaal").value(DEFAULT_MATERIAAL))
            .andExpect(jsonPath("$.verplaatsbaar").value(DEFAULT_VERPLAATSBAAR.booleanValue()))
            .andExpect(jsonPath("$.vorm").value(DEFAULT_VORM));
    }

    @Test
    @Transactional
    void getNonExistingBak() throws Exception {
        // Get the bak
        restBakMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBak() throws Exception {
        // Initialize the database
        bakRepository.saveAndFlush(bak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bak
        Bak updatedBak = bakRepository.findById(bak.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBak are not directly saved in db
        em.detach(updatedBak);
        updatedBak
            .breedte(UPDATED_BREEDTE)
            .diameter(UPDATED_DIAMETER)
            .gewichtleeg(UPDATED_GEWICHTLEEG)
            .gewichtvol(UPDATED_GEWICHTVOL)
            .hoogte(UPDATED_HOOGTE)
            .inhoud(UPDATED_INHOUD)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .lengte(UPDATED_LENGTE)
            .materiaal(UPDATED_MATERIAAL)
            .verplaatsbaar(UPDATED_VERPLAATSBAAR)
            .vorm(UPDATED_VORM);

        restBakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBak.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(updatedBak))
            )
            .andExpect(status().isOk());

        // Validate the Bak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBakToMatchAllProperties(updatedBak);
    }

    @Test
    @Transactional
    void putNonExistingBak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBakMockMvc
            .perform(put(ENTITY_API_URL_ID, bak.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bak)))
            .andExpect(status().isBadRequest());

        // Validate the Bak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBakMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBakWithPatch() throws Exception {
        // Initialize the database
        bakRepository.saveAndFlush(bak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bak using partial update
        Bak partialUpdatedBak = new Bak();
        partialUpdatedBak.setId(bak.getId());

        partialUpdatedBak
            .diameter(UPDATED_DIAMETER)
            .gewichtleeg(UPDATED_GEWICHTLEEG)
            .inhoud(UPDATED_INHOUD)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST);

        restBakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBak))
            )
            .andExpect(status().isOk());

        // Validate the Bak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBakUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBak, bak), getPersistedBak(bak));
    }

    @Test
    @Transactional
    void fullUpdateBakWithPatch() throws Exception {
        // Initialize the database
        bakRepository.saveAndFlush(bak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bak using partial update
        Bak partialUpdatedBak = new Bak();
        partialUpdatedBak.setId(bak.getId());

        partialUpdatedBak
            .breedte(UPDATED_BREEDTE)
            .diameter(UPDATED_DIAMETER)
            .gewichtleeg(UPDATED_GEWICHTLEEG)
            .gewichtvol(UPDATED_GEWICHTVOL)
            .hoogte(UPDATED_HOOGTE)
            .inhoud(UPDATED_INHOUD)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .lengte(UPDATED_LENGTE)
            .materiaal(UPDATED_MATERIAAL)
            .verplaatsbaar(UPDATED_VERPLAATSBAAR)
            .vorm(UPDATED_VORM);

        restBakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBak))
            )
            .andExpect(status().isOk());

        // Validate the Bak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBakUpdatableFieldsEquals(partialUpdatedBak, getPersistedBak(partialUpdatedBak));
    }

    @Test
    @Transactional
    void patchNonExistingBak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBakMockMvc
            .perform(patch(ENTITY_API_URL_ID, bak.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bak)))
            .andExpect(status().isBadRequest());

        // Validate the Bak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBakMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bak)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBak() throws Exception {
        // Initialize the database
        bakRepository.saveAndFlush(bak);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bak
        restBakMockMvc.perform(delete(ENTITY_API_URL_ID, bak.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bakRepository.count();
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

    protected Bak getPersistedBak(Bak bak) {
        return bakRepository.findById(bak.getId()).orElseThrow();
    }

    protected void assertPersistedBakToMatchAllProperties(Bak expectedBak) {
        assertBakAllPropertiesEquals(expectedBak, getPersistedBak(expectedBak));
    }

    protected void assertPersistedBakToMatchUpdatableProperties(Bak expectedBak) {
        assertBakAllUpdatablePropertiesEquals(expectedBak, getPersistedBak(expectedBak));
    }
}
