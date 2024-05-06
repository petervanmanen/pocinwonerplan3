package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AommeldingwmojeugdAsserts.*;
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
import nl.ritense.demo.domain.Aommeldingwmojeugd;
import nl.ritense.demo.repository.AommeldingwmojeugdRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AommeldingwmojeugdResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AommeldingwmojeugdResourceIT {

    private static final String DEFAULT_AANMELDER = "AAAAAAAAAA";
    private static final String UPDATED_AANMELDER = "BBBBBBBBBB";

    private static final String DEFAULT_AANMELDINGDOOR = "AAAAAAAAAA";
    private static final String UPDATED_AANMELDINGDOOR = "BBBBBBBBBB";

    private static final String DEFAULT_AANMELDINGDOORLANDELIJK = "AAAAAAAAAA";
    private static final String UPDATED_AANMELDINGDOORLANDELIJK = "BBBBBBBBBB";

    private static final String DEFAULT_AANMELDWIJZE = "AAAAAAAAAA";
    private static final String UPDATED_AANMELDWIJZE = "BBBBBBBBBB";

    private static final String DEFAULT_DESKUNDIGHEID = "AAAAAAAAAA";
    private static final String UPDATED_DESKUNDIGHEID = "BBBBBBBBBB";

    private static final String DEFAULT_ISCLIENTOPDEHOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_ISCLIENTOPDEHOOGTE = "BBBBBBBBBB";

    private static final String DEFAULT_ONDERZOEKSWIJZE = "AAAAAAAAAA";
    private static final String UPDATED_ONDERZOEKSWIJZE = "BBBBBBBBBB";

    private static final String DEFAULT_REDENAFSLUITING = "AAAAAAAAAA";
    private static final String UPDATED_REDENAFSLUITING = "BBBBBBBBBB";

    private static final String DEFAULT_VERVOLG = "AAAAAAAAAA";
    private static final String UPDATED_VERVOLG = "BBBBBBBBBB";

    private static final String DEFAULT_VERWEZEN = "AAAAAAAAAA";
    private static final String UPDATED_VERWEZEN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/aommeldingwmojeugds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AommeldingwmojeugdRepository aommeldingwmojeugdRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAommeldingwmojeugdMockMvc;

    private Aommeldingwmojeugd aommeldingwmojeugd;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aommeldingwmojeugd createEntity(EntityManager em) {
        Aommeldingwmojeugd aommeldingwmojeugd = new Aommeldingwmojeugd()
            .aanmelder(DEFAULT_AANMELDER)
            .aanmeldingdoor(DEFAULT_AANMELDINGDOOR)
            .aanmeldingdoorlandelijk(DEFAULT_AANMELDINGDOORLANDELIJK)
            .aanmeldwijze(DEFAULT_AANMELDWIJZE)
            .deskundigheid(DEFAULT_DESKUNDIGHEID)
            .isclientopdehoogte(DEFAULT_ISCLIENTOPDEHOOGTE)
            .onderzoekswijze(DEFAULT_ONDERZOEKSWIJZE)
            .redenafsluiting(DEFAULT_REDENAFSLUITING)
            .vervolg(DEFAULT_VERVOLG)
            .verwezen(DEFAULT_VERWEZEN);
        return aommeldingwmojeugd;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aommeldingwmojeugd createUpdatedEntity(EntityManager em) {
        Aommeldingwmojeugd aommeldingwmojeugd = new Aommeldingwmojeugd()
            .aanmelder(UPDATED_AANMELDER)
            .aanmeldingdoor(UPDATED_AANMELDINGDOOR)
            .aanmeldingdoorlandelijk(UPDATED_AANMELDINGDOORLANDELIJK)
            .aanmeldwijze(UPDATED_AANMELDWIJZE)
            .deskundigheid(UPDATED_DESKUNDIGHEID)
            .isclientopdehoogte(UPDATED_ISCLIENTOPDEHOOGTE)
            .onderzoekswijze(UPDATED_ONDERZOEKSWIJZE)
            .redenafsluiting(UPDATED_REDENAFSLUITING)
            .vervolg(UPDATED_VERVOLG)
            .verwezen(UPDATED_VERWEZEN);
        return aommeldingwmojeugd;
    }

    @BeforeEach
    public void initTest() {
        aommeldingwmojeugd = createEntity(em);
    }

    @Test
    @Transactional
    void createAommeldingwmojeugd() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Aommeldingwmojeugd
        var returnedAommeldingwmojeugd = om.readValue(
            restAommeldingwmojeugdMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aommeldingwmojeugd)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Aommeldingwmojeugd.class
        );

        // Validate the Aommeldingwmojeugd in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAommeldingwmojeugdUpdatableFieldsEquals(
            returnedAommeldingwmojeugd,
            getPersistedAommeldingwmojeugd(returnedAommeldingwmojeugd)
        );
    }

    @Test
    @Transactional
    void createAommeldingwmojeugdWithExistingId() throws Exception {
        // Create the Aommeldingwmojeugd with an existing ID
        aommeldingwmojeugd.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAommeldingwmojeugdMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aommeldingwmojeugd)))
            .andExpect(status().isBadRequest());

        // Validate the Aommeldingwmojeugd in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAommeldingwmojeugds() throws Exception {
        // Initialize the database
        aommeldingwmojeugdRepository.saveAndFlush(aommeldingwmojeugd);

        // Get all the aommeldingwmojeugdList
        restAommeldingwmojeugdMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aommeldingwmojeugd.getId().intValue())))
            .andExpect(jsonPath("$.[*].aanmelder").value(hasItem(DEFAULT_AANMELDER)))
            .andExpect(jsonPath("$.[*].aanmeldingdoor").value(hasItem(DEFAULT_AANMELDINGDOOR)))
            .andExpect(jsonPath("$.[*].aanmeldingdoorlandelijk").value(hasItem(DEFAULT_AANMELDINGDOORLANDELIJK)))
            .andExpect(jsonPath("$.[*].aanmeldwijze").value(hasItem(DEFAULT_AANMELDWIJZE)))
            .andExpect(jsonPath("$.[*].deskundigheid").value(hasItem(DEFAULT_DESKUNDIGHEID)))
            .andExpect(jsonPath("$.[*].isclientopdehoogte").value(hasItem(DEFAULT_ISCLIENTOPDEHOOGTE)))
            .andExpect(jsonPath("$.[*].onderzoekswijze").value(hasItem(DEFAULT_ONDERZOEKSWIJZE)))
            .andExpect(jsonPath("$.[*].redenafsluiting").value(hasItem(DEFAULT_REDENAFSLUITING)))
            .andExpect(jsonPath("$.[*].vervolg").value(hasItem(DEFAULT_VERVOLG)))
            .andExpect(jsonPath("$.[*].verwezen").value(hasItem(DEFAULT_VERWEZEN)));
    }

    @Test
    @Transactional
    void getAommeldingwmojeugd() throws Exception {
        // Initialize the database
        aommeldingwmojeugdRepository.saveAndFlush(aommeldingwmojeugd);

        // Get the aommeldingwmojeugd
        restAommeldingwmojeugdMockMvc
            .perform(get(ENTITY_API_URL_ID, aommeldingwmojeugd.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aommeldingwmojeugd.getId().intValue()))
            .andExpect(jsonPath("$.aanmelder").value(DEFAULT_AANMELDER))
            .andExpect(jsonPath("$.aanmeldingdoor").value(DEFAULT_AANMELDINGDOOR))
            .andExpect(jsonPath("$.aanmeldingdoorlandelijk").value(DEFAULT_AANMELDINGDOORLANDELIJK))
            .andExpect(jsonPath("$.aanmeldwijze").value(DEFAULT_AANMELDWIJZE))
            .andExpect(jsonPath("$.deskundigheid").value(DEFAULT_DESKUNDIGHEID))
            .andExpect(jsonPath("$.isclientopdehoogte").value(DEFAULT_ISCLIENTOPDEHOOGTE))
            .andExpect(jsonPath("$.onderzoekswijze").value(DEFAULT_ONDERZOEKSWIJZE))
            .andExpect(jsonPath("$.redenafsluiting").value(DEFAULT_REDENAFSLUITING))
            .andExpect(jsonPath("$.vervolg").value(DEFAULT_VERVOLG))
            .andExpect(jsonPath("$.verwezen").value(DEFAULT_VERWEZEN));
    }

    @Test
    @Transactional
    void getNonExistingAommeldingwmojeugd() throws Exception {
        // Get the aommeldingwmojeugd
        restAommeldingwmojeugdMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAommeldingwmojeugd() throws Exception {
        // Initialize the database
        aommeldingwmojeugdRepository.saveAndFlush(aommeldingwmojeugd);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aommeldingwmojeugd
        Aommeldingwmojeugd updatedAommeldingwmojeugd = aommeldingwmojeugdRepository.findById(aommeldingwmojeugd.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAommeldingwmojeugd are not directly saved in db
        em.detach(updatedAommeldingwmojeugd);
        updatedAommeldingwmojeugd
            .aanmelder(UPDATED_AANMELDER)
            .aanmeldingdoor(UPDATED_AANMELDINGDOOR)
            .aanmeldingdoorlandelijk(UPDATED_AANMELDINGDOORLANDELIJK)
            .aanmeldwijze(UPDATED_AANMELDWIJZE)
            .deskundigheid(UPDATED_DESKUNDIGHEID)
            .isclientopdehoogte(UPDATED_ISCLIENTOPDEHOOGTE)
            .onderzoekswijze(UPDATED_ONDERZOEKSWIJZE)
            .redenafsluiting(UPDATED_REDENAFSLUITING)
            .vervolg(UPDATED_VERVOLG)
            .verwezen(UPDATED_VERWEZEN);

        restAommeldingwmojeugdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAommeldingwmojeugd.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAommeldingwmojeugd))
            )
            .andExpect(status().isOk());

        // Validate the Aommeldingwmojeugd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAommeldingwmojeugdToMatchAllProperties(updatedAommeldingwmojeugd);
    }

    @Test
    @Transactional
    void putNonExistingAommeldingwmojeugd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aommeldingwmojeugd.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAommeldingwmojeugdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aommeldingwmojeugd.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aommeldingwmojeugd))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aommeldingwmojeugd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAommeldingwmojeugd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aommeldingwmojeugd.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAommeldingwmojeugdMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aommeldingwmojeugd))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aommeldingwmojeugd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAommeldingwmojeugd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aommeldingwmojeugd.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAommeldingwmojeugdMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aommeldingwmojeugd)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aommeldingwmojeugd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAommeldingwmojeugdWithPatch() throws Exception {
        // Initialize the database
        aommeldingwmojeugdRepository.saveAndFlush(aommeldingwmojeugd);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aommeldingwmojeugd using partial update
        Aommeldingwmojeugd partialUpdatedAommeldingwmojeugd = new Aommeldingwmojeugd();
        partialUpdatedAommeldingwmojeugd.setId(aommeldingwmojeugd.getId());

        partialUpdatedAommeldingwmojeugd
            .aanmelder(UPDATED_AANMELDER)
            .isclientopdehoogte(UPDATED_ISCLIENTOPDEHOOGTE)
            .onderzoekswijze(UPDATED_ONDERZOEKSWIJZE);

        restAommeldingwmojeugdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAommeldingwmojeugd.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAommeldingwmojeugd))
            )
            .andExpect(status().isOk());

        // Validate the Aommeldingwmojeugd in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAommeldingwmojeugdUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAommeldingwmojeugd, aommeldingwmojeugd),
            getPersistedAommeldingwmojeugd(aommeldingwmojeugd)
        );
    }

    @Test
    @Transactional
    void fullUpdateAommeldingwmojeugdWithPatch() throws Exception {
        // Initialize the database
        aommeldingwmojeugdRepository.saveAndFlush(aommeldingwmojeugd);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aommeldingwmojeugd using partial update
        Aommeldingwmojeugd partialUpdatedAommeldingwmojeugd = new Aommeldingwmojeugd();
        partialUpdatedAommeldingwmojeugd.setId(aommeldingwmojeugd.getId());

        partialUpdatedAommeldingwmojeugd
            .aanmelder(UPDATED_AANMELDER)
            .aanmeldingdoor(UPDATED_AANMELDINGDOOR)
            .aanmeldingdoorlandelijk(UPDATED_AANMELDINGDOORLANDELIJK)
            .aanmeldwijze(UPDATED_AANMELDWIJZE)
            .deskundigheid(UPDATED_DESKUNDIGHEID)
            .isclientopdehoogte(UPDATED_ISCLIENTOPDEHOOGTE)
            .onderzoekswijze(UPDATED_ONDERZOEKSWIJZE)
            .redenafsluiting(UPDATED_REDENAFSLUITING)
            .vervolg(UPDATED_VERVOLG)
            .verwezen(UPDATED_VERWEZEN);

        restAommeldingwmojeugdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAommeldingwmojeugd.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAommeldingwmojeugd))
            )
            .andExpect(status().isOk());

        // Validate the Aommeldingwmojeugd in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAommeldingwmojeugdUpdatableFieldsEquals(
            partialUpdatedAommeldingwmojeugd,
            getPersistedAommeldingwmojeugd(partialUpdatedAommeldingwmojeugd)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAommeldingwmojeugd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aommeldingwmojeugd.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAommeldingwmojeugdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aommeldingwmojeugd.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aommeldingwmojeugd))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aommeldingwmojeugd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAommeldingwmojeugd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aommeldingwmojeugd.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAommeldingwmojeugdMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aommeldingwmojeugd))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aommeldingwmojeugd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAommeldingwmojeugd() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aommeldingwmojeugd.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAommeldingwmojeugdMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(aommeldingwmojeugd)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aommeldingwmojeugd in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAommeldingwmojeugd() throws Exception {
        // Initialize the database
        aommeldingwmojeugdRepository.saveAndFlush(aommeldingwmojeugd);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the aommeldingwmojeugd
        restAommeldingwmojeugdMockMvc
            .perform(delete(ENTITY_API_URL_ID, aommeldingwmojeugd.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return aommeldingwmojeugdRepository.count();
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

    protected Aommeldingwmojeugd getPersistedAommeldingwmojeugd(Aommeldingwmojeugd aommeldingwmojeugd) {
        return aommeldingwmojeugdRepository.findById(aommeldingwmojeugd.getId()).orElseThrow();
    }

    protected void assertPersistedAommeldingwmojeugdToMatchAllProperties(Aommeldingwmojeugd expectedAommeldingwmojeugd) {
        assertAommeldingwmojeugdAllPropertiesEquals(expectedAommeldingwmojeugd, getPersistedAommeldingwmojeugd(expectedAommeldingwmojeugd));
    }

    protected void assertPersistedAommeldingwmojeugdToMatchUpdatableProperties(Aommeldingwmojeugd expectedAommeldingwmojeugd) {
        assertAommeldingwmojeugdAllUpdatablePropertiesEquals(
            expectedAommeldingwmojeugd,
            getPersistedAommeldingwmojeugd(expectedAommeldingwmojeugd)
        );
    }
}
