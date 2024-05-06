package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.StartformulieraanbestedenAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static nl.ritense.demo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Startformulieraanbesteden;
import nl.ritense.demo.repository.StartformulieraanbestedenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StartformulieraanbestedenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StartformulieraanbestedenResourceIT {

    private static final String DEFAULT_BEOOGDELOOPTIJD = "AAAAAAAAAA";
    private static final String UPDATED_BEOOGDELOOPTIJD = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_BEOOGDETOTALEOPDRACHTWAARDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BEOOGDETOTALEOPDRACHTWAARDE = new BigDecimal(2);

    private static final Boolean DEFAULT_INDICATIEAANVULLENDEOPDRACHTLEVERANCIER = false;
    private static final Boolean UPDATED_INDICATIEAANVULLENDEOPDRACHTLEVERANCIER = true;

    private static final String DEFAULT_INDICATIEBEOOGDEAANBESTEDINGONDERHANDS = "AAAAAAAAAA";
    private static final String UPDATED_INDICATIEBEOOGDEAANBESTEDINGONDERHANDS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INDICATIEBEOOGDEPROCKOMTOVEREEN = false;
    private static final Boolean UPDATED_INDICATIEBEOOGDEPROCKOMTOVEREEN = true;

    private static final String DEFAULT_INDICATIEEENMALIGELOS = "AAAAAAAAAA";
    private static final String UPDATED_INDICATIEEENMALIGELOS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INDICATIEMEERJARIGERAAMOVEREENKOMST = false;
    private static final Boolean UPDATED_INDICATIEMEERJARIGERAAMOVEREENKOMST = true;

    private static final String DEFAULT_INDICATIEMEERJARIGREPETEREND = "AAAAAAAAAA";
    private static final String UPDATED_INDICATIEMEERJARIGREPETEREND = "BBBBBBBBBB";

    private static final String DEFAULT_INDICATOROVERKOEPELENDPROJECT = "AAAAAAAAAA";
    private static final String UPDATED_INDICATOROVERKOEPELENDPROJECT = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_OPDRACHTCATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_OPDRACHTCATEGORIE = "BBBBBBBBBB";

    private static final String DEFAULT_OPDRACHTSOORT = "AAAAAAAAAA";
    private static final String UPDATED_OPDRACHTSOORT = "BBBBBBBBBB";

    private static final String DEFAULT_TOELICHTINGAANVULLENDEOPDRACHT = "AAAAAAAAAA";
    private static final String UPDATED_TOELICHTINGAANVULLENDEOPDRACHT = "BBBBBBBBBB";

    private static final String DEFAULT_TOELICHTINGEENMALIGOFREPETEREND = "AAAAAAAAAA";
    private static final String UPDATED_TOELICHTINGEENMALIGOFREPETEREND = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/startformulieraanbestedens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StartformulieraanbestedenRepository startformulieraanbestedenRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStartformulieraanbestedenMockMvc;

    private Startformulieraanbesteden startformulieraanbesteden;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Startformulieraanbesteden createEntity(EntityManager em) {
        Startformulieraanbesteden startformulieraanbesteden = new Startformulieraanbesteden()
            .beoogdelooptijd(DEFAULT_BEOOGDELOOPTIJD)
            .beoogdetotaleopdrachtwaarde(DEFAULT_BEOOGDETOTALEOPDRACHTWAARDE)
            .indicatieaanvullendeopdrachtleverancier(DEFAULT_INDICATIEAANVULLENDEOPDRACHTLEVERANCIER)
            .indicatiebeoogdeaanbestedingonderhands(DEFAULT_INDICATIEBEOOGDEAANBESTEDINGONDERHANDS)
            .indicatiebeoogdeprockomtovereen(DEFAULT_INDICATIEBEOOGDEPROCKOMTOVEREEN)
            .indicatieeenmaligelos(DEFAULT_INDICATIEEENMALIGELOS)
            .indicatiemeerjarigeraamovereenkomst(DEFAULT_INDICATIEMEERJARIGERAAMOVEREENKOMST)
            .indicatiemeerjarigrepeterend(DEFAULT_INDICATIEMEERJARIGREPETEREND)
            .indicatoroverkoepelendproject(DEFAULT_INDICATOROVERKOEPELENDPROJECT)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .opdrachtcategorie(DEFAULT_OPDRACHTCATEGORIE)
            .opdrachtsoort(DEFAULT_OPDRACHTSOORT)
            .toelichtingaanvullendeopdracht(DEFAULT_TOELICHTINGAANVULLENDEOPDRACHT)
            .toelichtingeenmaligofrepeterend(DEFAULT_TOELICHTINGEENMALIGOFREPETEREND);
        return startformulieraanbesteden;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Startformulieraanbesteden createUpdatedEntity(EntityManager em) {
        Startformulieraanbesteden startformulieraanbesteden = new Startformulieraanbesteden()
            .beoogdelooptijd(UPDATED_BEOOGDELOOPTIJD)
            .beoogdetotaleopdrachtwaarde(UPDATED_BEOOGDETOTALEOPDRACHTWAARDE)
            .indicatieaanvullendeopdrachtleverancier(UPDATED_INDICATIEAANVULLENDEOPDRACHTLEVERANCIER)
            .indicatiebeoogdeaanbestedingonderhands(UPDATED_INDICATIEBEOOGDEAANBESTEDINGONDERHANDS)
            .indicatiebeoogdeprockomtovereen(UPDATED_INDICATIEBEOOGDEPROCKOMTOVEREEN)
            .indicatieeenmaligelos(UPDATED_INDICATIEEENMALIGELOS)
            .indicatiemeerjarigeraamovereenkomst(UPDATED_INDICATIEMEERJARIGERAAMOVEREENKOMST)
            .indicatiemeerjarigrepeterend(UPDATED_INDICATIEMEERJARIGREPETEREND)
            .indicatoroverkoepelendproject(UPDATED_INDICATOROVERKOEPELENDPROJECT)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .opdrachtcategorie(UPDATED_OPDRACHTCATEGORIE)
            .opdrachtsoort(UPDATED_OPDRACHTSOORT)
            .toelichtingaanvullendeopdracht(UPDATED_TOELICHTINGAANVULLENDEOPDRACHT)
            .toelichtingeenmaligofrepeterend(UPDATED_TOELICHTINGEENMALIGOFREPETEREND);
        return startformulieraanbesteden;
    }

    @BeforeEach
    public void initTest() {
        startformulieraanbesteden = createEntity(em);
    }

    @Test
    @Transactional
    void createStartformulieraanbesteden() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Startformulieraanbesteden
        var returnedStartformulieraanbesteden = om.readValue(
            restStartformulieraanbestedenMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(startformulieraanbesteden))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Startformulieraanbesteden.class
        );

        // Validate the Startformulieraanbesteden in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertStartformulieraanbestedenUpdatableFieldsEquals(
            returnedStartformulieraanbesteden,
            getPersistedStartformulieraanbesteden(returnedStartformulieraanbesteden)
        );
    }

    @Test
    @Transactional
    void createStartformulieraanbestedenWithExistingId() throws Exception {
        // Create the Startformulieraanbesteden with an existing ID
        startformulieraanbesteden.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStartformulieraanbestedenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(startformulieraanbesteden)))
            .andExpect(status().isBadRequest());

        // Validate the Startformulieraanbesteden in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStartformulieraanbestedens() throws Exception {
        // Initialize the database
        startformulieraanbestedenRepository.saveAndFlush(startformulieraanbesteden);

        // Get all the startformulieraanbestedenList
        restStartformulieraanbestedenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(startformulieraanbesteden.getId().intValue())))
            .andExpect(jsonPath("$.[*].beoogdelooptijd").value(hasItem(DEFAULT_BEOOGDELOOPTIJD)))
            .andExpect(jsonPath("$.[*].beoogdetotaleopdrachtwaarde").value(hasItem(sameNumber(DEFAULT_BEOOGDETOTALEOPDRACHTWAARDE))))
            .andExpect(
                jsonPath("$.[*].indicatieaanvullendeopdrachtleverancier").value(
                    hasItem(DEFAULT_INDICATIEAANVULLENDEOPDRACHTLEVERANCIER.booleanValue())
                )
            )
            .andExpect(
                jsonPath("$.[*].indicatiebeoogdeaanbestedingonderhands").value(hasItem(DEFAULT_INDICATIEBEOOGDEAANBESTEDINGONDERHANDS))
            )
            .andExpect(
                jsonPath("$.[*].indicatiebeoogdeprockomtovereen").value(hasItem(DEFAULT_INDICATIEBEOOGDEPROCKOMTOVEREEN.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].indicatieeenmaligelos").value(hasItem(DEFAULT_INDICATIEEENMALIGELOS)))
            .andExpect(
                jsonPath("$.[*].indicatiemeerjarigeraamovereenkomst").value(
                    hasItem(DEFAULT_INDICATIEMEERJARIGERAAMOVEREENKOMST.booleanValue())
                )
            )
            .andExpect(jsonPath("$.[*].indicatiemeerjarigrepeterend").value(hasItem(DEFAULT_INDICATIEMEERJARIGREPETEREND)))
            .andExpect(jsonPath("$.[*].indicatoroverkoepelendproject").value(hasItem(DEFAULT_INDICATOROVERKOEPELENDPROJECT)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].opdrachtcategorie").value(hasItem(DEFAULT_OPDRACHTCATEGORIE)))
            .andExpect(jsonPath("$.[*].opdrachtsoort").value(hasItem(DEFAULT_OPDRACHTSOORT)))
            .andExpect(jsonPath("$.[*].toelichtingaanvullendeopdracht").value(hasItem(DEFAULT_TOELICHTINGAANVULLENDEOPDRACHT)))
            .andExpect(jsonPath("$.[*].toelichtingeenmaligofrepeterend").value(hasItem(DEFAULT_TOELICHTINGEENMALIGOFREPETEREND)));
    }

    @Test
    @Transactional
    void getStartformulieraanbesteden() throws Exception {
        // Initialize the database
        startformulieraanbestedenRepository.saveAndFlush(startformulieraanbesteden);

        // Get the startformulieraanbesteden
        restStartformulieraanbestedenMockMvc
            .perform(get(ENTITY_API_URL_ID, startformulieraanbesteden.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(startformulieraanbesteden.getId().intValue()))
            .andExpect(jsonPath("$.beoogdelooptijd").value(DEFAULT_BEOOGDELOOPTIJD))
            .andExpect(jsonPath("$.beoogdetotaleopdrachtwaarde").value(sameNumber(DEFAULT_BEOOGDETOTALEOPDRACHTWAARDE)))
            .andExpect(
                jsonPath("$.indicatieaanvullendeopdrachtleverancier").value(DEFAULT_INDICATIEAANVULLENDEOPDRACHTLEVERANCIER.booleanValue())
            )
            .andExpect(jsonPath("$.indicatiebeoogdeaanbestedingonderhands").value(DEFAULT_INDICATIEBEOOGDEAANBESTEDINGONDERHANDS))
            .andExpect(jsonPath("$.indicatiebeoogdeprockomtovereen").value(DEFAULT_INDICATIEBEOOGDEPROCKOMTOVEREEN.booleanValue()))
            .andExpect(jsonPath("$.indicatieeenmaligelos").value(DEFAULT_INDICATIEEENMALIGELOS))
            .andExpect(jsonPath("$.indicatiemeerjarigeraamovereenkomst").value(DEFAULT_INDICATIEMEERJARIGERAAMOVEREENKOMST.booleanValue()))
            .andExpect(jsonPath("$.indicatiemeerjarigrepeterend").value(DEFAULT_INDICATIEMEERJARIGREPETEREND))
            .andExpect(jsonPath("$.indicatoroverkoepelendproject").value(DEFAULT_INDICATOROVERKOEPELENDPROJECT))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.opdrachtcategorie").value(DEFAULT_OPDRACHTCATEGORIE))
            .andExpect(jsonPath("$.opdrachtsoort").value(DEFAULT_OPDRACHTSOORT))
            .andExpect(jsonPath("$.toelichtingaanvullendeopdracht").value(DEFAULT_TOELICHTINGAANVULLENDEOPDRACHT))
            .andExpect(jsonPath("$.toelichtingeenmaligofrepeterend").value(DEFAULT_TOELICHTINGEENMALIGOFREPETEREND));
    }

    @Test
    @Transactional
    void getNonExistingStartformulieraanbesteden() throws Exception {
        // Get the startformulieraanbesteden
        restStartformulieraanbestedenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStartformulieraanbesteden() throws Exception {
        // Initialize the database
        startformulieraanbestedenRepository.saveAndFlush(startformulieraanbesteden);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the startformulieraanbesteden
        Startformulieraanbesteden updatedStartformulieraanbesteden = startformulieraanbestedenRepository
            .findById(startformulieraanbesteden.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedStartformulieraanbesteden are not directly saved in db
        em.detach(updatedStartformulieraanbesteden);
        updatedStartformulieraanbesteden
            .beoogdelooptijd(UPDATED_BEOOGDELOOPTIJD)
            .beoogdetotaleopdrachtwaarde(UPDATED_BEOOGDETOTALEOPDRACHTWAARDE)
            .indicatieaanvullendeopdrachtleverancier(UPDATED_INDICATIEAANVULLENDEOPDRACHTLEVERANCIER)
            .indicatiebeoogdeaanbestedingonderhands(UPDATED_INDICATIEBEOOGDEAANBESTEDINGONDERHANDS)
            .indicatiebeoogdeprockomtovereen(UPDATED_INDICATIEBEOOGDEPROCKOMTOVEREEN)
            .indicatieeenmaligelos(UPDATED_INDICATIEEENMALIGELOS)
            .indicatiemeerjarigeraamovereenkomst(UPDATED_INDICATIEMEERJARIGERAAMOVEREENKOMST)
            .indicatiemeerjarigrepeterend(UPDATED_INDICATIEMEERJARIGREPETEREND)
            .indicatoroverkoepelendproject(UPDATED_INDICATOROVERKOEPELENDPROJECT)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .opdrachtcategorie(UPDATED_OPDRACHTCATEGORIE)
            .opdrachtsoort(UPDATED_OPDRACHTSOORT)
            .toelichtingaanvullendeopdracht(UPDATED_TOELICHTINGAANVULLENDEOPDRACHT)
            .toelichtingeenmaligofrepeterend(UPDATED_TOELICHTINGEENMALIGOFREPETEREND);

        restStartformulieraanbestedenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStartformulieraanbesteden.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedStartformulieraanbesteden))
            )
            .andExpect(status().isOk());

        // Validate the Startformulieraanbesteden in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStartformulieraanbestedenToMatchAllProperties(updatedStartformulieraanbesteden);
    }

    @Test
    @Transactional
    void putNonExistingStartformulieraanbesteden() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        startformulieraanbesteden.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStartformulieraanbestedenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, startformulieraanbesteden.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(startformulieraanbesteden))
            )
            .andExpect(status().isBadRequest());

        // Validate the Startformulieraanbesteden in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStartformulieraanbesteden() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        startformulieraanbesteden.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStartformulieraanbestedenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(startformulieraanbesteden))
            )
            .andExpect(status().isBadRequest());

        // Validate the Startformulieraanbesteden in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStartformulieraanbesteden() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        startformulieraanbesteden.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStartformulieraanbestedenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(startformulieraanbesteden)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Startformulieraanbesteden in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStartformulieraanbestedenWithPatch() throws Exception {
        // Initialize the database
        startformulieraanbestedenRepository.saveAndFlush(startformulieraanbesteden);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the startformulieraanbesteden using partial update
        Startformulieraanbesteden partialUpdatedStartformulieraanbesteden = new Startformulieraanbesteden();
        partialUpdatedStartformulieraanbesteden.setId(startformulieraanbesteden.getId());

        partialUpdatedStartformulieraanbesteden
            .beoogdelooptijd(UPDATED_BEOOGDELOOPTIJD)
            .indicatieeenmaligelos(UPDATED_INDICATIEEENMALIGELOS)
            .indicatiemeerjarigrepeterend(UPDATED_INDICATIEMEERJARIGREPETEREND)
            .indicatoroverkoepelendproject(UPDATED_INDICATOROVERKOEPELENDPROJECT)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .opdrachtcategorie(UPDATED_OPDRACHTCATEGORIE)
            .toelichtingaanvullendeopdracht(UPDATED_TOELICHTINGAANVULLENDEOPDRACHT);

        restStartformulieraanbestedenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStartformulieraanbesteden.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStartformulieraanbesteden))
            )
            .andExpect(status().isOk());

        // Validate the Startformulieraanbesteden in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStartformulieraanbestedenUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedStartformulieraanbesteden, startformulieraanbesteden),
            getPersistedStartformulieraanbesteden(startformulieraanbesteden)
        );
    }

    @Test
    @Transactional
    void fullUpdateStartformulieraanbestedenWithPatch() throws Exception {
        // Initialize the database
        startformulieraanbestedenRepository.saveAndFlush(startformulieraanbesteden);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the startformulieraanbesteden using partial update
        Startformulieraanbesteden partialUpdatedStartformulieraanbesteden = new Startformulieraanbesteden();
        partialUpdatedStartformulieraanbesteden.setId(startformulieraanbesteden.getId());

        partialUpdatedStartformulieraanbesteden
            .beoogdelooptijd(UPDATED_BEOOGDELOOPTIJD)
            .beoogdetotaleopdrachtwaarde(UPDATED_BEOOGDETOTALEOPDRACHTWAARDE)
            .indicatieaanvullendeopdrachtleverancier(UPDATED_INDICATIEAANVULLENDEOPDRACHTLEVERANCIER)
            .indicatiebeoogdeaanbestedingonderhands(UPDATED_INDICATIEBEOOGDEAANBESTEDINGONDERHANDS)
            .indicatiebeoogdeprockomtovereen(UPDATED_INDICATIEBEOOGDEPROCKOMTOVEREEN)
            .indicatieeenmaligelos(UPDATED_INDICATIEEENMALIGELOS)
            .indicatiemeerjarigeraamovereenkomst(UPDATED_INDICATIEMEERJARIGERAAMOVEREENKOMST)
            .indicatiemeerjarigrepeterend(UPDATED_INDICATIEMEERJARIGREPETEREND)
            .indicatoroverkoepelendproject(UPDATED_INDICATOROVERKOEPELENDPROJECT)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .opdrachtcategorie(UPDATED_OPDRACHTCATEGORIE)
            .opdrachtsoort(UPDATED_OPDRACHTSOORT)
            .toelichtingaanvullendeopdracht(UPDATED_TOELICHTINGAANVULLENDEOPDRACHT)
            .toelichtingeenmaligofrepeterend(UPDATED_TOELICHTINGEENMALIGOFREPETEREND);

        restStartformulieraanbestedenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStartformulieraanbesteden.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStartformulieraanbesteden))
            )
            .andExpect(status().isOk());

        // Validate the Startformulieraanbesteden in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStartformulieraanbestedenUpdatableFieldsEquals(
            partialUpdatedStartformulieraanbesteden,
            getPersistedStartformulieraanbesteden(partialUpdatedStartformulieraanbesteden)
        );
    }

    @Test
    @Transactional
    void patchNonExistingStartformulieraanbesteden() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        startformulieraanbesteden.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStartformulieraanbestedenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, startformulieraanbesteden.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(startformulieraanbesteden))
            )
            .andExpect(status().isBadRequest());

        // Validate the Startformulieraanbesteden in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStartformulieraanbesteden() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        startformulieraanbesteden.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStartformulieraanbestedenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(startformulieraanbesteden))
            )
            .andExpect(status().isBadRequest());

        // Validate the Startformulieraanbesteden in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStartformulieraanbesteden() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        startformulieraanbesteden.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStartformulieraanbestedenMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(startformulieraanbesteden))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Startformulieraanbesteden in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStartformulieraanbesteden() throws Exception {
        // Initialize the database
        startformulieraanbestedenRepository.saveAndFlush(startformulieraanbesteden);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the startformulieraanbesteden
        restStartformulieraanbestedenMockMvc
            .perform(delete(ENTITY_API_URL_ID, startformulieraanbesteden.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return startformulieraanbestedenRepository.count();
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

    protected Startformulieraanbesteden getPersistedStartformulieraanbesteden(Startformulieraanbesteden startformulieraanbesteden) {
        return startformulieraanbestedenRepository.findById(startformulieraanbesteden.getId()).orElseThrow();
    }

    protected void assertPersistedStartformulieraanbestedenToMatchAllProperties(
        Startformulieraanbesteden expectedStartformulieraanbesteden
    ) {
        assertStartformulieraanbestedenAllPropertiesEquals(
            expectedStartformulieraanbesteden,
            getPersistedStartformulieraanbesteden(expectedStartformulieraanbesteden)
        );
    }

    protected void assertPersistedStartformulieraanbestedenToMatchUpdatableProperties(
        Startformulieraanbesteden expectedStartformulieraanbesteden
    ) {
        assertStartformulieraanbestedenAllUpdatablePropertiesEquals(
            expectedStartformulieraanbesteden,
            getPersistedStartformulieraanbesteden(expectedStartformulieraanbesteden)
        );
    }
}
