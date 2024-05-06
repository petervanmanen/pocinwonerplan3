package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.MedewerkerAsserts.*;
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
import nl.ritense.demo.domain.Medewerker;
import nl.ritense.demo.repository.MedewerkerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MedewerkerResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class MedewerkerResourceIT {

    private static final String DEFAULT_ACHTERNAAM = "AAAAAAAAAA";
    private static final String UPDATED_ACHTERNAAM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMINDIENST = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMINDIENST = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DATUMUITDIENST = "AAAAAAAAAA";
    private static final String UPDATED_DATUMUITDIENST = "BBBBBBBBBB";

    private static final String DEFAULT_EMAILADRES = "AAAAAAAAAA";
    private static final String UPDATED_EMAILADRES = "BBBBBBBBBB";

    private static final String DEFAULT_EXTERN = "AAAAAAAAAA";
    private static final String UPDATED_EXTERN = "BBBBBBBBBB";

    private static final String DEFAULT_FUNCTIE = "AAAAAAAAAA";
    private static final String UPDATED_FUNCTIE = "BBBBBBBBBB";

    private static final String DEFAULT_GESLACHTSAANDUIDING = "AAAAAAAAAA";
    private static final String UPDATED_GESLACHTSAANDUIDING = "BBBBBBBBBB";

    private static final String DEFAULT_MEDEWERKERIDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_MEDEWERKERIDENTIFICATIE = "BBBBBBBBBB";

    private static final String DEFAULT_MEDEWERKERTOELICHTING = "AAAAAAAAAA";
    private static final String UPDATED_MEDEWERKERTOELICHTING = "BBBBBBBBBB";

    private static final String DEFAULT_ROEPNAAM = "AAAAAAAAAA";
    private static final String UPDATED_ROEPNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFOONNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_TELEFOONNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_VOORLETTERS = "AAAAAAAAAA";
    private static final String UPDATED_VOORLETTERS = "BBBBBBBBBB";

    private static final String DEFAULT_VOORVOEGSELACHTERNAAM = "AAAAAAAAAA";
    private static final String UPDATED_VOORVOEGSELACHTERNAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/medewerkers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MedewerkerRepository medewerkerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMedewerkerMockMvc;

    private Medewerker medewerker;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medewerker createEntity(EntityManager em) {
        Medewerker medewerker = new Medewerker()
            .achternaam(DEFAULT_ACHTERNAAM)
            .datumindienst(DEFAULT_DATUMINDIENST)
            .datumuitdienst(DEFAULT_DATUMUITDIENST)
            .emailadres(DEFAULT_EMAILADRES)
            .extern(DEFAULT_EXTERN)
            .functie(DEFAULT_FUNCTIE)
            .geslachtsaanduiding(DEFAULT_GESLACHTSAANDUIDING)
            .medewerkeridentificatie(DEFAULT_MEDEWERKERIDENTIFICATIE)
            .medewerkertoelichting(DEFAULT_MEDEWERKERTOELICHTING)
            .roepnaam(DEFAULT_ROEPNAAM)
            .telefoonnummer(DEFAULT_TELEFOONNUMMER)
            .voorletters(DEFAULT_VOORLETTERS)
            .voorvoegselachternaam(DEFAULT_VOORVOEGSELACHTERNAAM);
        return medewerker;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medewerker createUpdatedEntity(EntityManager em) {
        Medewerker medewerker = new Medewerker()
            .achternaam(UPDATED_ACHTERNAAM)
            .datumindienst(UPDATED_DATUMINDIENST)
            .datumuitdienst(UPDATED_DATUMUITDIENST)
            .emailadres(UPDATED_EMAILADRES)
            .extern(UPDATED_EXTERN)
            .functie(UPDATED_FUNCTIE)
            .geslachtsaanduiding(UPDATED_GESLACHTSAANDUIDING)
            .medewerkeridentificatie(UPDATED_MEDEWERKERIDENTIFICATIE)
            .medewerkertoelichting(UPDATED_MEDEWERKERTOELICHTING)
            .roepnaam(UPDATED_ROEPNAAM)
            .telefoonnummer(UPDATED_TELEFOONNUMMER)
            .voorletters(UPDATED_VOORLETTERS)
            .voorvoegselachternaam(UPDATED_VOORVOEGSELACHTERNAAM);
        return medewerker;
    }

    @BeforeEach
    public void initTest() {
        medewerker = createEntity(em);
    }

    @Test
    @Transactional
    void createMedewerker() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Medewerker
        var returnedMedewerker = om.readValue(
            restMedewerkerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(medewerker)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Medewerker.class
        );

        // Validate the Medewerker in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMedewerkerUpdatableFieldsEquals(returnedMedewerker, getPersistedMedewerker(returnedMedewerker));
    }

    @Test
    @Transactional
    void createMedewerkerWithExistingId() throws Exception {
        // Create the Medewerker with an existing ID
        medewerker.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedewerkerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(medewerker)))
            .andExpect(status().isBadRequest());

        // Validate the Medewerker in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMedewerkers() throws Exception {
        // Initialize the database
        medewerkerRepository.saveAndFlush(medewerker);

        // Get all the medewerkerList
        restMedewerkerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medewerker.getId().intValue())))
            .andExpect(jsonPath("$.[*].achternaam").value(hasItem(DEFAULT_ACHTERNAAM)))
            .andExpect(jsonPath("$.[*].datumindienst").value(hasItem(DEFAULT_DATUMINDIENST.toString())))
            .andExpect(jsonPath("$.[*].datumuitdienst").value(hasItem(DEFAULT_DATUMUITDIENST)))
            .andExpect(jsonPath("$.[*].emailadres").value(hasItem(DEFAULT_EMAILADRES)))
            .andExpect(jsonPath("$.[*].extern").value(hasItem(DEFAULT_EXTERN)))
            .andExpect(jsonPath("$.[*].functie").value(hasItem(DEFAULT_FUNCTIE)))
            .andExpect(jsonPath("$.[*].geslachtsaanduiding").value(hasItem(DEFAULT_GESLACHTSAANDUIDING)))
            .andExpect(jsonPath("$.[*].medewerkeridentificatie").value(hasItem(DEFAULT_MEDEWERKERIDENTIFICATIE)))
            .andExpect(jsonPath("$.[*].medewerkertoelichting").value(hasItem(DEFAULT_MEDEWERKERTOELICHTING)))
            .andExpect(jsonPath("$.[*].roepnaam").value(hasItem(DEFAULT_ROEPNAAM)))
            .andExpect(jsonPath("$.[*].telefoonnummer").value(hasItem(DEFAULT_TELEFOONNUMMER)))
            .andExpect(jsonPath("$.[*].voorletters").value(hasItem(DEFAULT_VOORLETTERS)))
            .andExpect(jsonPath("$.[*].voorvoegselachternaam").value(hasItem(DEFAULT_VOORVOEGSELACHTERNAAM)));
    }

    @Test
    @Transactional
    void getMedewerker() throws Exception {
        // Initialize the database
        medewerkerRepository.saveAndFlush(medewerker);

        // Get the medewerker
        restMedewerkerMockMvc
            .perform(get(ENTITY_API_URL_ID, medewerker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(medewerker.getId().intValue()))
            .andExpect(jsonPath("$.achternaam").value(DEFAULT_ACHTERNAAM))
            .andExpect(jsonPath("$.datumindienst").value(DEFAULT_DATUMINDIENST.toString()))
            .andExpect(jsonPath("$.datumuitdienst").value(DEFAULT_DATUMUITDIENST))
            .andExpect(jsonPath("$.emailadres").value(DEFAULT_EMAILADRES))
            .andExpect(jsonPath("$.extern").value(DEFAULT_EXTERN))
            .andExpect(jsonPath("$.functie").value(DEFAULT_FUNCTIE))
            .andExpect(jsonPath("$.geslachtsaanduiding").value(DEFAULT_GESLACHTSAANDUIDING))
            .andExpect(jsonPath("$.medewerkeridentificatie").value(DEFAULT_MEDEWERKERIDENTIFICATIE))
            .andExpect(jsonPath("$.medewerkertoelichting").value(DEFAULT_MEDEWERKERTOELICHTING))
            .andExpect(jsonPath("$.roepnaam").value(DEFAULT_ROEPNAAM))
            .andExpect(jsonPath("$.telefoonnummer").value(DEFAULT_TELEFOONNUMMER))
            .andExpect(jsonPath("$.voorletters").value(DEFAULT_VOORLETTERS))
            .andExpect(jsonPath("$.voorvoegselachternaam").value(DEFAULT_VOORVOEGSELACHTERNAAM));
    }

    @Test
    @Transactional
    void getNonExistingMedewerker() throws Exception {
        // Get the medewerker
        restMedewerkerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMedewerker() throws Exception {
        // Initialize the database
        medewerkerRepository.saveAndFlush(medewerker);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the medewerker
        Medewerker updatedMedewerker = medewerkerRepository.findById(medewerker.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMedewerker are not directly saved in db
        em.detach(updatedMedewerker);
        updatedMedewerker
            .achternaam(UPDATED_ACHTERNAAM)
            .datumindienst(UPDATED_DATUMINDIENST)
            .datumuitdienst(UPDATED_DATUMUITDIENST)
            .emailadres(UPDATED_EMAILADRES)
            .extern(UPDATED_EXTERN)
            .functie(UPDATED_FUNCTIE)
            .geslachtsaanduiding(UPDATED_GESLACHTSAANDUIDING)
            .medewerkeridentificatie(UPDATED_MEDEWERKERIDENTIFICATIE)
            .medewerkertoelichting(UPDATED_MEDEWERKERTOELICHTING)
            .roepnaam(UPDATED_ROEPNAAM)
            .telefoonnummer(UPDATED_TELEFOONNUMMER)
            .voorletters(UPDATED_VOORLETTERS)
            .voorvoegselachternaam(UPDATED_VOORVOEGSELACHTERNAAM);

        restMedewerkerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMedewerker.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMedewerker))
            )
            .andExpect(status().isOk());

        // Validate the Medewerker in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMedewerkerToMatchAllProperties(updatedMedewerker);
    }

    @Test
    @Transactional
    void putNonExistingMedewerker() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        medewerker.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedewerkerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, medewerker.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(medewerker))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medewerker in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMedewerker() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        medewerker.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedewerkerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(medewerker))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medewerker in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMedewerker() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        medewerker.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedewerkerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(medewerker)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Medewerker in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMedewerkerWithPatch() throws Exception {
        // Initialize the database
        medewerkerRepository.saveAndFlush(medewerker);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the medewerker using partial update
        Medewerker partialUpdatedMedewerker = new Medewerker();
        partialUpdatedMedewerker.setId(medewerker.getId());

        partialUpdatedMedewerker
            .datumuitdienst(UPDATED_DATUMUITDIENST)
            .emailadres(UPDATED_EMAILADRES)
            .extern(UPDATED_EXTERN)
            .medewerkertoelichting(UPDATED_MEDEWERKERTOELICHTING)
            .roepnaam(UPDATED_ROEPNAAM)
            .telefoonnummer(UPDATED_TELEFOONNUMMER)
            .voorletters(UPDATED_VOORLETTERS);

        restMedewerkerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedewerker.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMedewerker))
            )
            .andExpect(status().isOk());

        // Validate the Medewerker in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMedewerkerUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMedewerker, medewerker),
            getPersistedMedewerker(medewerker)
        );
    }

    @Test
    @Transactional
    void fullUpdateMedewerkerWithPatch() throws Exception {
        // Initialize the database
        medewerkerRepository.saveAndFlush(medewerker);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the medewerker using partial update
        Medewerker partialUpdatedMedewerker = new Medewerker();
        partialUpdatedMedewerker.setId(medewerker.getId());

        partialUpdatedMedewerker
            .achternaam(UPDATED_ACHTERNAAM)
            .datumindienst(UPDATED_DATUMINDIENST)
            .datumuitdienst(UPDATED_DATUMUITDIENST)
            .emailadres(UPDATED_EMAILADRES)
            .extern(UPDATED_EXTERN)
            .functie(UPDATED_FUNCTIE)
            .geslachtsaanduiding(UPDATED_GESLACHTSAANDUIDING)
            .medewerkeridentificatie(UPDATED_MEDEWERKERIDENTIFICATIE)
            .medewerkertoelichting(UPDATED_MEDEWERKERTOELICHTING)
            .roepnaam(UPDATED_ROEPNAAM)
            .telefoonnummer(UPDATED_TELEFOONNUMMER)
            .voorletters(UPDATED_VOORLETTERS)
            .voorvoegselachternaam(UPDATED_VOORVOEGSELACHTERNAAM);

        restMedewerkerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedewerker.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMedewerker))
            )
            .andExpect(status().isOk());

        // Validate the Medewerker in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMedewerkerUpdatableFieldsEquals(partialUpdatedMedewerker, getPersistedMedewerker(partialUpdatedMedewerker));
    }

    @Test
    @Transactional
    void patchNonExistingMedewerker() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        medewerker.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedewerkerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, medewerker.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(medewerker))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medewerker in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMedewerker() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        medewerker.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedewerkerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(medewerker))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medewerker in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMedewerker() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        medewerker.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedewerkerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(medewerker)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Medewerker in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMedewerker() throws Exception {
        // Initialize the database
        medewerkerRepository.saveAndFlush(medewerker);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the medewerker
        restMedewerkerMockMvc
            .perform(delete(ENTITY_API_URL_ID, medewerker.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return medewerkerRepository.count();
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

    protected Medewerker getPersistedMedewerker(Medewerker medewerker) {
        return medewerkerRepository.findById(medewerker.getId()).orElseThrow();
    }

    protected void assertPersistedMedewerkerToMatchAllProperties(Medewerker expectedMedewerker) {
        assertMedewerkerAllPropertiesEquals(expectedMedewerker, getPersistedMedewerker(expectedMedewerker));
    }

    protected void assertPersistedMedewerkerToMatchUpdatableProperties(Medewerker expectedMedewerker) {
        assertMedewerkerAllUpdatablePropertiesEquals(expectedMedewerker, getPersistedMedewerker(expectedMedewerker));
    }
}
