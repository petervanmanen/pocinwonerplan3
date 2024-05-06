package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KpclassaclasscAsserts.*;
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
import nl.ritense.demo.domain.Kpclassaclassc;
import nl.ritense.demo.repository.KpclassaclasscRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KpclassaclasscResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KpclassaclasscResourceIT {

    private static final String DEFAULT_MBRONSYSTEEM = "AAAAAAAAAA";
    private static final String UPDATED_MBRONSYSTEEM = "BBBBBBBBBB";

    private static final String DEFAULT_MDATUMTIJDGELADEN = "AAAAAAAAAA";
    private static final String UPDATED_MDATUMTIJDGELADEN = "BBBBBBBBBB";

    private static final Integer DEFAULT_CLASSCID = 1;
    private static final Integer UPDATED_CLASSCID = 2;

    private static final Integer DEFAULT_CLASSAID = 1;
    private static final Integer UPDATED_CLASSAID = 2;

    private static final String ENTITY_API_URL = "/api/kpclassaclasscs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KpclassaclasscRepository kpclassaclasscRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKpclassaclasscMockMvc;

    private Kpclassaclassc kpclassaclassc;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kpclassaclassc createEntity(EntityManager em) {
        Kpclassaclassc kpclassaclassc = new Kpclassaclassc()
            .mbronsysteem(DEFAULT_MBRONSYSTEEM)
            .mdatumtijdgeladen(DEFAULT_MDATUMTIJDGELADEN)
            .classcid(DEFAULT_CLASSCID)
            .classaid(DEFAULT_CLASSAID);
        return kpclassaclassc;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kpclassaclassc createUpdatedEntity(EntityManager em) {
        Kpclassaclassc kpclassaclassc = new Kpclassaclassc()
            .mbronsysteem(UPDATED_MBRONSYSTEEM)
            .mdatumtijdgeladen(UPDATED_MDATUMTIJDGELADEN)
            .classcid(UPDATED_CLASSCID)
            .classaid(UPDATED_CLASSAID);
        return kpclassaclassc;
    }

    @BeforeEach
    public void initTest() {
        kpclassaclassc = createEntity(em);
    }

    @Test
    @Transactional
    void createKpclassaclassc() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Kpclassaclassc
        var returnedKpclassaclassc = om.readValue(
            restKpclassaclasscMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kpclassaclassc)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Kpclassaclassc.class
        );

        // Validate the Kpclassaclassc in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKpclassaclasscUpdatableFieldsEquals(returnedKpclassaclassc, getPersistedKpclassaclassc(returnedKpclassaclassc));
    }

    @Test
    @Transactional
    void createKpclassaclasscWithExistingId() throws Exception {
        // Create the Kpclassaclassc with an existing ID
        kpclassaclassc.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKpclassaclasscMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kpclassaclassc)))
            .andExpect(status().isBadRequest());

        // Validate the Kpclassaclassc in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKpclassaclasscs() throws Exception {
        // Initialize the database
        kpclassaclasscRepository.saveAndFlush(kpclassaclassc);

        // Get all the kpclassaclasscList
        restKpclassaclasscMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kpclassaclassc.getId().intValue())))
            .andExpect(jsonPath("$.[*].mbronsysteem").value(hasItem(DEFAULT_MBRONSYSTEEM)))
            .andExpect(jsonPath("$.[*].mdatumtijdgeladen").value(hasItem(DEFAULT_MDATUMTIJDGELADEN)))
            .andExpect(jsonPath("$.[*].classcid").value(hasItem(DEFAULT_CLASSCID)))
            .andExpect(jsonPath("$.[*].classaid").value(hasItem(DEFAULT_CLASSAID)));
    }

    @Test
    @Transactional
    void getKpclassaclassc() throws Exception {
        // Initialize the database
        kpclassaclasscRepository.saveAndFlush(kpclassaclassc);

        // Get the kpclassaclassc
        restKpclassaclasscMockMvc
            .perform(get(ENTITY_API_URL_ID, kpclassaclassc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kpclassaclassc.getId().intValue()))
            .andExpect(jsonPath("$.mbronsysteem").value(DEFAULT_MBRONSYSTEEM))
            .andExpect(jsonPath("$.mdatumtijdgeladen").value(DEFAULT_MDATUMTIJDGELADEN))
            .andExpect(jsonPath("$.classcid").value(DEFAULT_CLASSCID))
            .andExpect(jsonPath("$.classaid").value(DEFAULT_CLASSAID));
    }

    @Test
    @Transactional
    void getNonExistingKpclassaclassc() throws Exception {
        // Get the kpclassaclassc
        restKpclassaclasscMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKpclassaclassc() throws Exception {
        // Initialize the database
        kpclassaclasscRepository.saveAndFlush(kpclassaclassc);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kpclassaclassc
        Kpclassaclassc updatedKpclassaclassc = kpclassaclasscRepository.findById(kpclassaclassc.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedKpclassaclassc are not directly saved in db
        em.detach(updatedKpclassaclassc);
        updatedKpclassaclassc
            .mbronsysteem(UPDATED_MBRONSYSTEEM)
            .mdatumtijdgeladen(UPDATED_MDATUMTIJDGELADEN)
            .classcid(UPDATED_CLASSCID)
            .classaid(UPDATED_CLASSAID);

        restKpclassaclasscMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKpclassaclassc.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedKpclassaclassc))
            )
            .andExpect(status().isOk());

        // Validate the Kpclassaclassc in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedKpclassaclasscToMatchAllProperties(updatedKpclassaclassc);
    }

    @Test
    @Transactional
    void putNonExistingKpclassaclassc() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kpclassaclassc.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKpclassaclasscMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kpclassaclassc.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kpclassaclassc))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kpclassaclassc in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKpclassaclassc() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kpclassaclassc.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKpclassaclasscMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(kpclassaclassc))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kpclassaclassc in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKpclassaclassc() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kpclassaclassc.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKpclassaclasscMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(kpclassaclassc)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kpclassaclassc in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKpclassaclasscWithPatch() throws Exception {
        // Initialize the database
        kpclassaclasscRepository.saveAndFlush(kpclassaclassc);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kpclassaclassc using partial update
        Kpclassaclassc partialUpdatedKpclassaclassc = new Kpclassaclassc();
        partialUpdatedKpclassaclassc.setId(kpclassaclassc.getId());

        partialUpdatedKpclassaclassc
            .mbronsysteem(UPDATED_MBRONSYSTEEM)
            .mdatumtijdgeladen(UPDATED_MDATUMTIJDGELADEN)
            .classcid(UPDATED_CLASSCID)
            .classaid(UPDATED_CLASSAID);

        restKpclassaclasscMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKpclassaclassc.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKpclassaclassc))
            )
            .andExpect(status().isOk());

        // Validate the Kpclassaclassc in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKpclassaclasscUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedKpclassaclassc, kpclassaclassc),
            getPersistedKpclassaclassc(kpclassaclassc)
        );
    }

    @Test
    @Transactional
    void fullUpdateKpclassaclasscWithPatch() throws Exception {
        // Initialize the database
        kpclassaclasscRepository.saveAndFlush(kpclassaclassc);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the kpclassaclassc using partial update
        Kpclassaclassc partialUpdatedKpclassaclassc = new Kpclassaclassc();
        partialUpdatedKpclassaclassc.setId(kpclassaclassc.getId());

        partialUpdatedKpclassaclassc
            .mbronsysteem(UPDATED_MBRONSYSTEEM)
            .mdatumtijdgeladen(UPDATED_MDATUMTIJDGELADEN)
            .classcid(UPDATED_CLASSCID)
            .classaid(UPDATED_CLASSAID);

        restKpclassaclasscMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKpclassaclassc.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKpclassaclassc))
            )
            .andExpect(status().isOk());

        // Validate the Kpclassaclassc in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKpclassaclasscUpdatableFieldsEquals(partialUpdatedKpclassaclassc, getPersistedKpclassaclassc(partialUpdatedKpclassaclassc));
    }

    @Test
    @Transactional
    void patchNonExistingKpclassaclassc() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kpclassaclassc.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKpclassaclasscMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kpclassaclassc.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kpclassaclassc))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kpclassaclassc in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKpclassaclassc() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kpclassaclassc.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKpclassaclasscMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(kpclassaclassc))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kpclassaclassc in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKpclassaclassc() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        kpclassaclassc.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKpclassaclasscMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(kpclassaclassc)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kpclassaclassc in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKpclassaclassc() throws Exception {
        // Initialize the database
        kpclassaclasscRepository.saveAndFlush(kpclassaclassc);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the kpclassaclassc
        restKpclassaclasscMockMvc
            .perform(delete(ENTITY_API_URL_ID, kpclassaclassc.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return kpclassaclasscRepository.count();
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

    protected Kpclassaclassc getPersistedKpclassaclassc(Kpclassaclassc kpclassaclassc) {
        return kpclassaclasscRepository.findById(kpclassaclassc.getId()).orElseThrow();
    }

    protected void assertPersistedKpclassaclasscToMatchAllProperties(Kpclassaclassc expectedKpclassaclassc) {
        assertKpclassaclasscAllPropertiesEquals(expectedKpclassaclassc, getPersistedKpclassaclassc(expectedKpclassaclassc));
    }

    protected void assertPersistedKpclassaclasscToMatchUpdatableProperties(Kpclassaclassc expectedKpclassaclassc) {
        assertKpclassaclasscAllUpdatablePropertiesEquals(expectedKpclassaclassc, getPersistedKpclassaclassc(expectedKpclassaclassc));
    }
}
