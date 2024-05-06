package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ArtefactAsserts.*;
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
import nl.ritense.demo.domain.Artefact;
import nl.ritense.demo.repository.ArtefactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ArtefactResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArtefactResourceIT {

    private static final String DEFAULT_ARTEFECTNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_ARTEFECTNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_BESCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_BESCHRIJVING = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CONSERVEREN = false;
    private static final Boolean UPDATED_CONSERVEREN = true;

    private static final String DEFAULT_DATERING = "AAAAAAAAAA";
    private static final String UPDATED_DATERING = "BBBBBBBBBB";

    private static final String DEFAULT_DATERINGCOMPLEX = "AAAAAAAAAA";
    private static final String UPDATED_DATERINGCOMPLEX = "BBBBBBBBBB";

    private static final String DEFAULT_DETERMINATIENIVEAU = "AAAAAAAAAA";
    private static final String UPDATED_DETERMINATIENIVEAU = "BBBBBBBBBB";

    private static final String DEFAULT_DIANUMMER = "AAAAAAAAAA";
    private static final String UPDATED_DIANUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_DOOSNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_DOOSNUMMER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EXPOSABEL = false;
    private static final Boolean UPDATED_EXPOSABEL = true;

    private static final String DEFAULT_FOTONUMMER = "AAAAAAAAAA";
    private static final String UPDATED_FOTONUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_FUNCTIE = "AAAAAAAAAA";
    private static final String UPDATED_FUNCTIE = "BBBBBBBBBB";

    private static final String DEFAULT_HERKOMST = "AAAAAAAAAA";
    private static final String UPDATED_HERKOMST = "BBBBBBBBBB";

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_KEYDOOS = "AAAAAAAAAA";
    private static final String UPDATED_KEYDOOS = "BBBBBBBBBB";

    private static final String DEFAULT_KEYMAGAZIJNPLAATSING = "AAAAAAAAAA";
    private static final String UPDATED_KEYMAGAZIJNPLAATSING = "BBBBBBBBBB";

    private static final String DEFAULT_KEYPUT = "AAAAAAAAAA";
    private static final String UPDATED_KEYPUT = "BBBBBBBBBB";

    private static final String DEFAULT_KEYVONDST = "AAAAAAAAAA";
    private static final String UPDATED_KEYVONDST = "BBBBBBBBBB";

    private static final String DEFAULT_LITERATUUR = "AAAAAAAAAA";
    private static final String UPDATED_LITERATUUR = "BBBBBBBBBB";

    private static final String DEFAULT_MATEN = "AAAAAAAAAA";
    private static final String UPDATED_MATEN = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OPMERKINGEN = "AAAAAAAAAA";
    private static final String UPDATED_OPMERKINGEN = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINE = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINE = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECTCD = "AAAAAAAAAA";
    private static final String UPDATED_PROJECTCD = "BBBBBBBBBB";

    private static final String DEFAULT_PUTNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_PUTNUMMER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_RESTAURATIEWENSELIJK = false;
    private static final Boolean UPDATED_RESTAURATIEWENSELIJK = true;

    private static final String DEFAULT_TEKENINGNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_TEKENINGNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VONDSTNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_VONDSTNUMMER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/artefacts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ArtefactRepository artefactRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArtefactMockMvc;

    private Artefact artefact;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Artefact createEntity(EntityManager em) {
        Artefact artefact = new Artefact()
            .artefectnummer(DEFAULT_ARTEFECTNUMMER)
            .beschrijving(DEFAULT_BESCHRIJVING)
            .conserveren(DEFAULT_CONSERVEREN)
            .datering(DEFAULT_DATERING)
            .dateringcomplex(DEFAULT_DATERINGCOMPLEX)
            .determinatieniveau(DEFAULT_DETERMINATIENIVEAU)
            .dianummer(DEFAULT_DIANUMMER)
            .doosnummer(DEFAULT_DOOSNUMMER)
            .exposabel(DEFAULT_EXPOSABEL)
            .fotonummer(DEFAULT_FOTONUMMER)
            .functie(DEFAULT_FUNCTIE)
            .herkomst(DEFAULT_HERKOMST)
            .key(DEFAULT_KEY)
            .keydoos(DEFAULT_KEYDOOS)
            .keymagazijnplaatsing(DEFAULT_KEYMAGAZIJNPLAATSING)
            .keyput(DEFAULT_KEYPUT)
            .keyvondst(DEFAULT_KEYVONDST)
            .literatuur(DEFAULT_LITERATUUR)
            .maten(DEFAULT_MATEN)
            .naam(DEFAULT_NAAM)
            .opmerkingen(DEFAULT_OPMERKINGEN)
            .origine(DEFAULT_ORIGINE)
            .projectcd(DEFAULT_PROJECTCD)
            .putnummer(DEFAULT_PUTNUMMER)
            .restauratiewenselijk(DEFAULT_RESTAURATIEWENSELIJK)
            .tekeningnummer(DEFAULT_TEKENINGNUMMER)
            .type(DEFAULT_TYPE)
            .vondstnummer(DEFAULT_VONDSTNUMMER);
        return artefact;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Artefact createUpdatedEntity(EntityManager em) {
        Artefact artefact = new Artefact()
            .artefectnummer(UPDATED_ARTEFECTNUMMER)
            .beschrijving(UPDATED_BESCHRIJVING)
            .conserveren(UPDATED_CONSERVEREN)
            .datering(UPDATED_DATERING)
            .dateringcomplex(UPDATED_DATERINGCOMPLEX)
            .determinatieniveau(UPDATED_DETERMINATIENIVEAU)
            .dianummer(UPDATED_DIANUMMER)
            .doosnummer(UPDATED_DOOSNUMMER)
            .exposabel(UPDATED_EXPOSABEL)
            .fotonummer(UPDATED_FOTONUMMER)
            .functie(UPDATED_FUNCTIE)
            .herkomst(UPDATED_HERKOMST)
            .key(UPDATED_KEY)
            .keydoos(UPDATED_KEYDOOS)
            .keymagazijnplaatsing(UPDATED_KEYMAGAZIJNPLAATSING)
            .keyput(UPDATED_KEYPUT)
            .keyvondst(UPDATED_KEYVONDST)
            .literatuur(UPDATED_LITERATUUR)
            .maten(UPDATED_MATEN)
            .naam(UPDATED_NAAM)
            .opmerkingen(UPDATED_OPMERKINGEN)
            .origine(UPDATED_ORIGINE)
            .projectcd(UPDATED_PROJECTCD)
            .putnummer(UPDATED_PUTNUMMER)
            .restauratiewenselijk(UPDATED_RESTAURATIEWENSELIJK)
            .tekeningnummer(UPDATED_TEKENINGNUMMER)
            .type(UPDATED_TYPE)
            .vondstnummer(UPDATED_VONDSTNUMMER);
        return artefact;
    }

    @BeforeEach
    public void initTest() {
        artefact = createEntity(em);
    }

    @Test
    @Transactional
    void createArtefact() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Artefact
        var returnedArtefact = om.readValue(
            restArtefactMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(artefact)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Artefact.class
        );

        // Validate the Artefact in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertArtefactUpdatableFieldsEquals(returnedArtefact, getPersistedArtefact(returnedArtefact));
    }

    @Test
    @Transactional
    void createArtefactWithExistingId() throws Exception {
        // Create the Artefact with an existing ID
        artefact.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArtefactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(artefact)))
            .andExpect(status().isBadRequest());

        // Validate the Artefact in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllArtefacts() throws Exception {
        // Initialize the database
        artefactRepository.saveAndFlush(artefact);

        // Get all the artefactList
        restArtefactMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artefact.getId().intValue())))
            .andExpect(jsonPath("$.[*].artefectnummer").value(hasItem(DEFAULT_ARTEFECTNUMMER)))
            .andExpect(jsonPath("$.[*].beschrijving").value(hasItem(DEFAULT_BESCHRIJVING)))
            .andExpect(jsonPath("$.[*].conserveren").value(hasItem(DEFAULT_CONSERVEREN.booleanValue())))
            .andExpect(jsonPath("$.[*].datering").value(hasItem(DEFAULT_DATERING)))
            .andExpect(jsonPath("$.[*].dateringcomplex").value(hasItem(DEFAULT_DATERINGCOMPLEX)))
            .andExpect(jsonPath("$.[*].determinatieniveau").value(hasItem(DEFAULT_DETERMINATIENIVEAU)))
            .andExpect(jsonPath("$.[*].dianummer").value(hasItem(DEFAULT_DIANUMMER)))
            .andExpect(jsonPath("$.[*].doosnummer").value(hasItem(DEFAULT_DOOSNUMMER)))
            .andExpect(jsonPath("$.[*].exposabel").value(hasItem(DEFAULT_EXPOSABEL.booleanValue())))
            .andExpect(jsonPath("$.[*].fotonummer").value(hasItem(DEFAULT_FOTONUMMER)))
            .andExpect(jsonPath("$.[*].functie").value(hasItem(DEFAULT_FUNCTIE)))
            .andExpect(jsonPath("$.[*].herkomst").value(hasItem(DEFAULT_HERKOMST)))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].keydoos").value(hasItem(DEFAULT_KEYDOOS)))
            .andExpect(jsonPath("$.[*].keymagazijnplaatsing").value(hasItem(DEFAULT_KEYMAGAZIJNPLAATSING)))
            .andExpect(jsonPath("$.[*].keyput").value(hasItem(DEFAULT_KEYPUT)))
            .andExpect(jsonPath("$.[*].keyvondst").value(hasItem(DEFAULT_KEYVONDST)))
            .andExpect(jsonPath("$.[*].literatuur").value(hasItem(DEFAULT_LITERATUUR)))
            .andExpect(jsonPath("$.[*].maten").value(hasItem(DEFAULT_MATEN)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].opmerkingen").value(hasItem(DEFAULT_OPMERKINGEN)))
            .andExpect(jsonPath("$.[*].origine").value(hasItem(DEFAULT_ORIGINE)))
            .andExpect(jsonPath("$.[*].projectcd").value(hasItem(DEFAULT_PROJECTCD)))
            .andExpect(jsonPath("$.[*].putnummer").value(hasItem(DEFAULT_PUTNUMMER)))
            .andExpect(jsonPath("$.[*].restauratiewenselijk").value(hasItem(DEFAULT_RESTAURATIEWENSELIJK.booleanValue())))
            .andExpect(jsonPath("$.[*].tekeningnummer").value(hasItem(DEFAULT_TEKENINGNUMMER)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].vondstnummer").value(hasItem(DEFAULT_VONDSTNUMMER)));
    }

    @Test
    @Transactional
    void getArtefact() throws Exception {
        // Initialize the database
        artefactRepository.saveAndFlush(artefact);

        // Get the artefact
        restArtefactMockMvc
            .perform(get(ENTITY_API_URL_ID, artefact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(artefact.getId().intValue()))
            .andExpect(jsonPath("$.artefectnummer").value(DEFAULT_ARTEFECTNUMMER))
            .andExpect(jsonPath("$.beschrijving").value(DEFAULT_BESCHRIJVING))
            .andExpect(jsonPath("$.conserveren").value(DEFAULT_CONSERVEREN.booleanValue()))
            .andExpect(jsonPath("$.datering").value(DEFAULT_DATERING))
            .andExpect(jsonPath("$.dateringcomplex").value(DEFAULT_DATERINGCOMPLEX))
            .andExpect(jsonPath("$.determinatieniveau").value(DEFAULT_DETERMINATIENIVEAU))
            .andExpect(jsonPath("$.dianummer").value(DEFAULT_DIANUMMER))
            .andExpect(jsonPath("$.doosnummer").value(DEFAULT_DOOSNUMMER))
            .andExpect(jsonPath("$.exposabel").value(DEFAULT_EXPOSABEL.booleanValue()))
            .andExpect(jsonPath("$.fotonummer").value(DEFAULT_FOTONUMMER))
            .andExpect(jsonPath("$.functie").value(DEFAULT_FUNCTIE))
            .andExpect(jsonPath("$.herkomst").value(DEFAULT_HERKOMST))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.keydoos").value(DEFAULT_KEYDOOS))
            .andExpect(jsonPath("$.keymagazijnplaatsing").value(DEFAULT_KEYMAGAZIJNPLAATSING))
            .andExpect(jsonPath("$.keyput").value(DEFAULT_KEYPUT))
            .andExpect(jsonPath("$.keyvondst").value(DEFAULT_KEYVONDST))
            .andExpect(jsonPath("$.literatuur").value(DEFAULT_LITERATUUR))
            .andExpect(jsonPath("$.maten").value(DEFAULT_MATEN))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.opmerkingen").value(DEFAULT_OPMERKINGEN))
            .andExpect(jsonPath("$.origine").value(DEFAULT_ORIGINE))
            .andExpect(jsonPath("$.projectcd").value(DEFAULT_PROJECTCD))
            .andExpect(jsonPath("$.putnummer").value(DEFAULT_PUTNUMMER))
            .andExpect(jsonPath("$.restauratiewenselijk").value(DEFAULT_RESTAURATIEWENSELIJK.booleanValue()))
            .andExpect(jsonPath("$.tekeningnummer").value(DEFAULT_TEKENINGNUMMER))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.vondstnummer").value(DEFAULT_VONDSTNUMMER));
    }

    @Test
    @Transactional
    void getNonExistingArtefact() throws Exception {
        // Get the artefact
        restArtefactMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArtefact() throws Exception {
        // Initialize the database
        artefactRepository.saveAndFlush(artefact);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the artefact
        Artefact updatedArtefact = artefactRepository.findById(artefact.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedArtefact are not directly saved in db
        em.detach(updatedArtefact);
        updatedArtefact
            .artefectnummer(UPDATED_ARTEFECTNUMMER)
            .beschrijving(UPDATED_BESCHRIJVING)
            .conserveren(UPDATED_CONSERVEREN)
            .datering(UPDATED_DATERING)
            .dateringcomplex(UPDATED_DATERINGCOMPLEX)
            .determinatieniveau(UPDATED_DETERMINATIENIVEAU)
            .dianummer(UPDATED_DIANUMMER)
            .doosnummer(UPDATED_DOOSNUMMER)
            .exposabel(UPDATED_EXPOSABEL)
            .fotonummer(UPDATED_FOTONUMMER)
            .functie(UPDATED_FUNCTIE)
            .herkomst(UPDATED_HERKOMST)
            .key(UPDATED_KEY)
            .keydoos(UPDATED_KEYDOOS)
            .keymagazijnplaatsing(UPDATED_KEYMAGAZIJNPLAATSING)
            .keyput(UPDATED_KEYPUT)
            .keyvondst(UPDATED_KEYVONDST)
            .literatuur(UPDATED_LITERATUUR)
            .maten(UPDATED_MATEN)
            .naam(UPDATED_NAAM)
            .opmerkingen(UPDATED_OPMERKINGEN)
            .origine(UPDATED_ORIGINE)
            .projectcd(UPDATED_PROJECTCD)
            .putnummer(UPDATED_PUTNUMMER)
            .restauratiewenselijk(UPDATED_RESTAURATIEWENSELIJK)
            .tekeningnummer(UPDATED_TEKENINGNUMMER)
            .type(UPDATED_TYPE)
            .vondstnummer(UPDATED_VONDSTNUMMER);

        restArtefactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedArtefact.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedArtefact))
            )
            .andExpect(status().isOk());

        // Validate the Artefact in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedArtefactToMatchAllProperties(updatedArtefact);
    }

    @Test
    @Transactional
    void putNonExistingArtefact() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artefact.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtefactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artefact.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(artefact))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artefact in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArtefact() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artefact.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtefactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(artefact))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artefact in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArtefact() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artefact.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtefactMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(artefact)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Artefact in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArtefactWithPatch() throws Exception {
        // Initialize the database
        artefactRepository.saveAndFlush(artefact);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the artefact using partial update
        Artefact partialUpdatedArtefact = new Artefact();
        partialUpdatedArtefact.setId(artefact.getId());

        partialUpdatedArtefact
            .artefectnummer(UPDATED_ARTEFECTNUMMER)
            .beschrijving(UPDATED_BESCHRIJVING)
            .datering(UPDATED_DATERING)
            .dianummer(UPDATED_DIANUMMER)
            .doosnummer(UPDATED_DOOSNUMMER)
            .exposabel(UPDATED_EXPOSABEL)
            .fotonummer(UPDATED_FOTONUMMER)
            .functie(UPDATED_FUNCTIE)
            .herkomst(UPDATED_HERKOMST)
            .key(UPDATED_KEY)
            .keydoos(UPDATED_KEYDOOS)
            .maten(UPDATED_MATEN)
            .projectcd(UPDATED_PROJECTCD)
            .restauratiewenselijk(UPDATED_RESTAURATIEWENSELIJK)
            .vondstnummer(UPDATED_VONDSTNUMMER);

        restArtefactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtefact.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArtefact))
            )
            .andExpect(status().isOk());

        // Validate the Artefact in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArtefactUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedArtefact, artefact), getPersistedArtefact(artefact));
    }

    @Test
    @Transactional
    void fullUpdateArtefactWithPatch() throws Exception {
        // Initialize the database
        artefactRepository.saveAndFlush(artefact);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the artefact using partial update
        Artefact partialUpdatedArtefact = new Artefact();
        partialUpdatedArtefact.setId(artefact.getId());

        partialUpdatedArtefact
            .artefectnummer(UPDATED_ARTEFECTNUMMER)
            .beschrijving(UPDATED_BESCHRIJVING)
            .conserveren(UPDATED_CONSERVEREN)
            .datering(UPDATED_DATERING)
            .dateringcomplex(UPDATED_DATERINGCOMPLEX)
            .determinatieniveau(UPDATED_DETERMINATIENIVEAU)
            .dianummer(UPDATED_DIANUMMER)
            .doosnummer(UPDATED_DOOSNUMMER)
            .exposabel(UPDATED_EXPOSABEL)
            .fotonummer(UPDATED_FOTONUMMER)
            .functie(UPDATED_FUNCTIE)
            .herkomst(UPDATED_HERKOMST)
            .key(UPDATED_KEY)
            .keydoos(UPDATED_KEYDOOS)
            .keymagazijnplaatsing(UPDATED_KEYMAGAZIJNPLAATSING)
            .keyput(UPDATED_KEYPUT)
            .keyvondst(UPDATED_KEYVONDST)
            .literatuur(UPDATED_LITERATUUR)
            .maten(UPDATED_MATEN)
            .naam(UPDATED_NAAM)
            .opmerkingen(UPDATED_OPMERKINGEN)
            .origine(UPDATED_ORIGINE)
            .projectcd(UPDATED_PROJECTCD)
            .putnummer(UPDATED_PUTNUMMER)
            .restauratiewenselijk(UPDATED_RESTAURATIEWENSELIJK)
            .tekeningnummer(UPDATED_TEKENINGNUMMER)
            .type(UPDATED_TYPE)
            .vondstnummer(UPDATED_VONDSTNUMMER);

        restArtefactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtefact.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArtefact))
            )
            .andExpect(status().isOk());

        // Validate the Artefact in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArtefactUpdatableFieldsEquals(partialUpdatedArtefact, getPersistedArtefact(partialUpdatedArtefact));
    }

    @Test
    @Transactional
    void patchNonExistingArtefact() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artefact.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtefactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, artefact.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(artefact))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artefact in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArtefact() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artefact.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtefactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(artefact))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artefact in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArtefact() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artefact.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtefactMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(artefact)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Artefact in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArtefact() throws Exception {
        // Initialize the database
        artefactRepository.saveAndFlush(artefact);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the artefact
        restArtefactMockMvc
            .perform(delete(ENTITY_API_URL_ID, artefact.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return artefactRepository.count();
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

    protected Artefact getPersistedArtefact(Artefact artefact) {
        return artefactRepository.findById(artefact.getId()).orElseThrow();
    }

    protected void assertPersistedArtefactToMatchAllProperties(Artefact expectedArtefact) {
        assertArtefactAllPropertiesEquals(expectedArtefact, getPersistedArtefact(expectedArtefact));
    }

    protected void assertPersistedArtefactToMatchUpdatableProperties(Artefact expectedArtefact) {
        assertArtefactAllUpdatablePropertiesEquals(expectedArtefact, getPersistedArtefact(expectedArtefact));
    }
}
