package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SpecificatieAsserts.*;
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
import nl.ritense.demo.domain.Projectactiviteit;
import nl.ritense.demo.domain.Specificatie;
import nl.ritense.demo.repository.SpecificatieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SpecificatieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SpecificatieResourceIT {

    private static final String DEFAULT_ANTWOORD = "AAAAAAAAAA";
    private static final String UPDATED_ANTWOORD = "BBBBBBBBBB";

    private static final String DEFAULT_GROEPERING = "AAAAAAAAAA";
    private static final String UPDATED_GROEPERING = "BBBBBBBBBB";

    private static final String DEFAULT_PUBLICEERBAAR = "AAAAAAAAAA";
    private static final String UPDATED_PUBLICEERBAAR = "BBBBBBBBBB";

    private static final String DEFAULT_VRAAGCLASSIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_VRAAGCLASSIFICATIE = "BBBBBBBBBB";

    private static final String DEFAULT_VRAAGID = "AAAAAAAAAA";
    private static final String UPDATED_VRAAGID = "BBBBBBBBBB";

    private static final String DEFAULT_VRAAGREFERENTIE = "AAAAAAAAAA";
    private static final String UPDATED_VRAAGREFERENTIE = "BBBBBBBBBB";

    private static final String DEFAULT_VRAAGTEKST = "AAAAAAAAAA";
    private static final String UPDATED_VRAAGTEKST = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/specificaties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SpecificatieRepository specificatieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSpecificatieMockMvc;

    private Specificatie specificatie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Specificatie createEntity(EntityManager em) {
        Specificatie specificatie = new Specificatie()
            .antwoord(DEFAULT_ANTWOORD)
            .groepering(DEFAULT_GROEPERING)
            .publiceerbaar(DEFAULT_PUBLICEERBAAR)
            .vraagclassificatie(DEFAULT_VRAAGCLASSIFICATIE)
            .vraagid(DEFAULT_VRAAGID)
            .vraagreferentie(DEFAULT_VRAAGREFERENTIE)
            .vraagtekst(DEFAULT_VRAAGTEKST);
        // Add required entity
        Projectactiviteit projectactiviteit;
        if (TestUtil.findAll(em, Projectactiviteit.class).isEmpty()) {
            projectactiviteit = ProjectactiviteitResourceIT.createEntity(em);
            em.persist(projectactiviteit);
            em.flush();
        } else {
            projectactiviteit = TestUtil.findAll(em, Projectactiviteit.class).get(0);
        }
        specificatie.setGedefinieerddoorProjectactiviteit(projectactiviteit);
        return specificatie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Specificatie createUpdatedEntity(EntityManager em) {
        Specificatie specificatie = new Specificatie()
            .antwoord(UPDATED_ANTWOORD)
            .groepering(UPDATED_GROEPERING)
            .publiceerbaar(UPDATED_PUBLICEERBAAR)
            .vraagclassificatie(UPDATED_VRAAGCLASSIFICATIE)
            .vraagid(UPDATED_VRAAGID)
            .vraagreferentie(UPDATED_VRAAGREFERENTIE)
            .vraagtekst(UPDATED_VRAAGTEKST);
        // Add required entity
        Projectactiviteit projectactiviteit;
        if (TestUtil.findAll(em, Projectactiviteit.class).isEmpty()) {
            projectactiviteit = ProjectactiviteitResourceIT.createUpdatedEntity(em);
            em.persist(projectactiviteit);
            em.flush();
        } else {
            projectactiviteit = TestUtil.findAll(em, Projectactiviteit.class).get(0);
        }
        specificatie.setGedefinieerddoorProjectactiviteit(projectactiviteit);
        return specificatie;
    }

    @BeforeEach
    public void initTest() {
        specificatie = createEntity(em);
    }

    @Test
    @Transactional
    void createSpecificatie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Specificatie
        var returnedSpecificatie = om.readValue(
            restSpecificatieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(specificatie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Specificatie.class
        );

        // Validate the Specificatie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSpecificatieUpdatableFieldsEquals(returnedSpecificatie, getPersistedSpecificatie(returnedSpecificatie));
    }

    @Test
    @Transactional
    void createSpecificatieWithExistingId() throws Exception {
        // Create the Specificatie with an existing ID
        specificatie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpecificatieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(specificatie)))
            .andExpect(status().isBadRequest());

        // Validate the Specificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSpecificaties() throws Exception {
        // Initialize the database
        specificatieRepository.saveAndFlush(specificatie);

        // Get all the specificatieList
        restSpecificatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(specificatie.getId().intValue())))
            .andExpect(jsonPath("$.[*].antwoord").value(hasItem(DEFAULT_ANTWOORD)))
            .andExpect(jsonPath("$.[*].groepering").value(hasItem(DEFAULT_GROEPERING)))
            .andExpect(jsonPath("$.[*].publiceerbaar").value(hasItem(DEFAULT_PUBLICEERBAAR)))
            .andExpect(jsonPath("$.[*].vraagclassificatie").value(hasItem(DEFAULT_VRAAGCLASSIFICATIE)))
            .andExpect(jsonPath("$.[*].vraagid").value(hasItem(DEFAULT_VRAAGID)))
            .andExpect(jsonPath("$.[*].vraagreferentie").value(hasItem(DEFAULT_VRAAGREFERENTIE)))
            .andExpect(jsonPath("$.[*].vraagtekst").value(hasItem(DEFAULT_VRAAGTEKST)));
    }

    @Test
    @Transactional
    void getSpecificatie() throws Exception {
        // Initialize the database
        specificatieRepository.saveAndFlush(specificatie);

        // Get the specificatie
        restSpecificatieMockMvc
            .perform(get(ENTITY_API_URL_ID, specificatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(specificatie.getId().intValue()))
            .andExpect(jsonPath("$.antwoord").value(DEFAULT_ANTWOORD))
            .andExpect(jsonPath("$.groepering").value(DEFAULT_GROEPERING))
            .andExpect(jsonPath("$.publiceerbaar").value(DEFAULT_PUBLICEERBAAR))
            .andExpect(jsonPath("$.vraagclassificatie").value(DEFAULT_VRAAGCLASSIFICATIE))
            .andExpect(jsonPath("$.vraagid").value(DEFAULT_VRAAGID))
            .andExpect(jsonPath("$.vraagreferentie").value(DEFAULT_VRAAGREFERENTIE))
            .andExpect(jsonPath("$.vraagtekst").value(DEFAULT_VRAAGTEKST));
    }

    @Test
    @Transactional
    void getNonExistingSpecificatie() throws Exception {
        // Get the specificatie
        restSpecificatieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSpecificatie() throws Exception {
        // Initialize the database
        specificatieRepository.saveAndFlush(specificatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the specificatie
        Specificatie updatedSpecificatie = specificatieRepository.findById(specificatie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSpecificatie are not directly saved in db
        em.detach(updatedSpecificatie);
        updatedSpecificatie
            .antwoord(UPDATED_ANTWOORD)
            .groepering(UPDATED_GROEPERING)
            .publiceerbaar(UPDATED_PUBLICEERBAAR)
            .vraagclassificatie(UPDATED_VRAAGCLASSIFICATIE)
            .vraagid(UPDATED_VRAAGID)
            .vraagreferentie(UPDATED_VRAAGREFERENTIE)
            .vraagtekst(UPDATED_VRAAGTEKST);

        restSpecificatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSpecificatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSpecificatie))
            )
            .andExpect(status().isOk());

        // Validate the Specificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSpecificatieToMatchAllProperties(updatedSpecificatie);
    }

    @Test
    @Transactional
    void putNonExistingSpecificatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        specificatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecificatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, specificatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(specificatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Specificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSpecificatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        specificatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecificatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(specificatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Specificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSpecificatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        specificatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecificatieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(specificatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Specificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSpecificatieWithPatch() throws Exception {
        // Initialize the database
        specificatieRepository.saveAndFlush(specificatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the specificatie using partial update
        Specificatie partialUpdatedSpecificatie = new Specificatie();
        partialUpdatedSpecificatie.setId(specificatie.getId());

        partialUpdatedSpecificatie.groepering(UPDATED_GROEPERING).vraagid(UPDATED_VRAAGID).vraagtekst(UPDATED_VRAAGTEKST);

        restSpecificatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpecificatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSpecificatie))
            )
            .andExpect(status().isOk());

        // Validate the Specificatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSpecificatieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSpecificatie, specificatie),
            getPersistedSpecificatie(specificatie)
        );
    }

    @Test
    @Transactional
    void fullUpdateSpecificatieWithPatch() throws Exception {
        // Initialize the database
        specificatieRepository.saveAndFlush(specificatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the specificatie using partial update
        Specificatie partialUpdatedSpecificatie = new Specificatie();
        partialUpdatedSpecificatie.setId(specificatie.getId());

        partialUpdatedSpecificatie
            .antwoord(UPDATED_ANTWOORD)
            .groepering(UPDATED_GROEPERING)
            .publiceerbaar(UPDATED_PUBLICEERBAAR)
            .vraagclassificatie(UPDATED_VRAAGCLASSIFICATIE)
            .vraagid(UPDATED_VRAAGID)
            .vraagreferentie(UPDATED_VRAAGREFERENTIE)
            .vraagtekst(UPDATED_VRAAGTEKST);

        restSpecificatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpecificatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSpecificatie))
            )
            .andExpect(status().isOk());

        // Validate the Specificatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSpecificatieUpdatableFieldsEquals(partialUpdatedSpecificatie, getPersistedSpecificatie(partialUpdatedSpecificatie));
    }

    @Test
    @Transactional
    void patchNonExistingSpecificatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        specificatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecificatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, specificatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(specificatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Specificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSpecificatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        specificatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecificatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(specificatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Specificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSpecificatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        specificatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecificatieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(specificatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Specificatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSpecificatie() throws Exception {
        // Initialize the database
        specificatieRepository.saveAndFlush(specificatie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the specificatie
        restSpecificatieMockMvc
            .perform(delete(ENTITY_API_URL_ID, specificatie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return specificatieRepository.count();
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

    protected Specificatie getPersistedSpecificatie(Specificatie specificatie) {
        return specificatieRepository.findById(specificatie.getId()).orElseThrow();
    }

    protected void assertPersistedSpecificatieToMatchAllProperties(Specificatie expectedSpecificatie) {
        assertSpecificatieAllPropertiesEquals(expectedSpecificatie, getPersistedSpecificatie(expectedSpecificatie));
    }

    protected void assertPersistedSpecificatieToMatchUpdatableProperties(Specificatie expectedSpecificatie) {
        assertSpecificatieAllUpdatablePropertiesEquals(expectedSpecificatie, getPersistedSpecificatie(expectedSpecificatie));
    }
}
