package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.VegetatieobjectAsserts.*;
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
import nl.ritense.demo.domain.Vegetatieobject;
import nl.ritense.demo.repository.VegetatieobjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VegetatieobjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VegetatieobjectResourceIT {

    private static final Boolean DEFAULT_AFVOEREN = false;
    private static final Boolean UPDATED_AFVOEREN = true;

    private static final String DEFAULT_BEREIKBAARHEID = "AAAAAAAAAA";
    private static final String UPDATED_BEREIKBAARHEID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ECOLOGISCHBEHEER = false;
    private static final Boolean UPDATED_ECOLOGISCHBEHEER = true;

    private static final String DEFAULT_KWALITEITSNIVEAUACTUEEL = "AAAAAAAAAA";
    private static final String UPDATED_KWALITEITSNIVEAUACTUEEL = "BBBBBBBBBB";

    private static final String DEFAULT_KWALITEITSNIVEAUGEWENST = "AAAAAAAAAA";
    private static final String UPDATED_KWALITEITSNIVEAUGEWENST = "BBBBBBBBBB";

    private static final String DEFAULT_KWEKER = "AAAAAAAAAA";
    private static final String UPDATED_KWEKER = "BBBBBBBBBB";

    private static final String DEFAULT_LEVERANCIER = "AAAAAAAAAA";
    private static final String UPDATED_LEVERANCIER = "BBBBBBBBBB";

    private static final String DEFAULT_EOBJECTNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_EOBJECTNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_SOORTNAAM = "AAAAAAAAAA";
    private static final String UPDATED_SOORTNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_TYPESTANDPLAATS = "AAAAAAAAAA";
    private static final String UPDATED_TYPESTANDPLAATS = "BBBBBBBBBB";

    private static final String DEFAULT_TYPESTANDPLAATSPLUS = "AAAAAAAAAA";
    private static final String UPDATED_TYPESTANDPLAATSPLUS = "BBBBBBBBBB";

    private static final String DEFAULT_VEGETATIEOBJECTBEREIKBAARHEIDPLUS = "AAAAAAAAAA";
    private static final String UPDATED_VEGETATIEOBJECTBEREIKBAARHEIDPLUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vegetatieobjects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VegetatieobjectRepository vegetatieobjectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVegetatieobjectMockMvc;

    private Vegetatieobject vegetatieobject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vegetatieobject createEntity(EntityManager em) {
        Vegetatieobject vegetatieobject = new Vegetatieobject()
            .afvoeren(DEFAULT_AFVOEREN)
            .bereikbaarheid(DEFAULT_BEREIKBAARHEID)
            .ecologischbeheer(DEFAULT_ECOLOGISCHBEHEER)
            .kwaliteitsniveauactueel(DEFAULT_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(DEFAULT_KWALITEITSNIVEAUGEWENST)
            .kweker(DEFAULT_KWEKER)
            .leverancier(DEFAULT_LEVERANCIER)
            .eobjectnummer(DEFAULT_EOBJECTNUMMER)
            .soortnaam(DEFAULT_SOORTNAAM)
            .typestandplaats(DEFAULT_TYPESTANDPLAATS)
            .typestandplaatsplus(DEFAULT_TYPESTANDPLAATSPLUS)
            .vegetatieobjectbereikbaarheidplus(DEFAULT_VEGETATIEOBJECTBEREIKBAARHEIDPLUS);
        return vegetatieobject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vegetatieobject createUpdatedEntity(EntityManager em) {
        Vegetatieobject vegetatieobject = new Vegetatieobject()
            .afvoeren(UPDATED_AFVOEREN)
            .bereikbaarheid(UPDATED_BEREIKBAARHEID)
            .ecologischbeheer(UPDATED_ECOLOGISCHBEHEER)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .kweker(UPDATED_KWEKER)
            .leverancier(UPDATED_LEVERANCIER)
            .eobjectnummer(UPDATED_EOBJECTNUMMER)
            .soortnaam(UPDATED_SOORTNAAM)
            .typestandplaats(UPDATED_TYPESTANDPLAATS)
            .typestandplaatsplus(UPDATED_TYPESTANDPLAATSPLUS)
            .vegetatieobjectbereikbaarheidplus(UPDATED_VEGETATIEOBJECTBEREIKBAARHEIDPLUS);
        return vegetatieobject;
    }

    @BeforeEach
    public void initTest() {
        vegetatieobject = createEntity(em);
    }

    @Test
    @Transactional
    void createVegetatieobject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vegetatieobject
        var returnedVegetatieobject = om.readValue(
            restVegetatieobjectMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vegetatieobject)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Vegetatieobject.class
        );

        // Validate the Vegetatieobject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVegetatieobjectUpdatableFieldsEquals(returnedVegetatieobject, getPersistedVegetatieobject(returnedVegetatieobject));
    }

    @Test
    @Transactional
    void createVegetatieobjectWithExistingId() throws Exception {
        // Create the Vegetatieobject with an existing ID
        vegetatieobject.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVegetatieobjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vegetatieobject)))
            .andExpect(status().isBadRequest());

        // Validate the Vegetatieobject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVegetatieobjects() throws Exception {
        // Initialize the database
        vegetatieobjectRepository.saveAndFlush(vegetatieobject);

        // Get all the vegetatieobjectList
        restVegetatieobjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vegetatieobject.getId().intValue())))
            .andExpect(jsonPath("$.[*].afvoeren").value(hasItem(DEFAULT_AFVOEREN.booleanValue())))
            .andExpect(jsonPath("$.[*].bereikbaarheid").value(hasItem(DEFAULT_BEREIKBAARHEID)))
            .andExpect(jsonPath("$.[*].ecologischbeheer").value(hasItem(DEFAULT_ECOLOGISCHBEHEER.booleanValue())))
            .andExpect(jsonPath("$.[*].kwaliteitsniveauactueel").value(hasItem(DEFAULT_KWALITEITSNIVEAUACTUEEL)))
            .andExpect(jsonPath("$.[*].kwaliteitsniveaugewenst").value(hasItem(DEFAULT_KWALITEITSNIVEAUGEWENST)))
            .andExpect(jsonPath("$.[*].kweker").value(hasItem(DEFAULT_KWEKER)))
            .andExpect(jsonPath("$.[*].leverancier").value(hasItem(DEFAULT_LEVERANCIER)))
            .andExpect(jsonPath("$.[*].eobjectnummer").value(hasItem(DEFAULT_EOBJECTNUMMER)))
            .andExpect(jsonPath("$.[*].soortnaam").value(hasItem(DEFAULT_SOORTNAAM)))
            .andExpect(jsonPath("$.[*].typestandplaats").value(hasItem(DEFAULT_TYPESTANDPLAATS)))
            .andExpect(jsonPath("$.[*].typestandplaatsplus").value(hasItem(DEFAULT_TYPESTANDPLAATSPLUS)))
            .andExpect(jsonPath("$.[*].vegetatieobjectbereikbaarheidplus").value(hasItem(DEFAULT_VEGETATIEOBJECTBEREIKBAARHEIDPLUS)));
    }

    @Test
    @Transactional
    void getVegetatieobject() throws Exception {
        // Initialize the database
        vegetatieobjectRepository.saveAndFlush(vegetatieobject);

        // Get the vegetatieobject
        restVegetatieobjectMockMvc
            .perform(get(ENTITY_API_URL_ID, vegetatieobject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vegetatieobject.getId().intValue()))
            .andExpect(jsonPath("$.afvoeren").value(DEFAULT_AFVOEREN.booleanValue()))
            .andExpect(jsonPath("$.bereikbaarheid").value(DEFAULT_BEREIKBAARHEID))
            .andExpect(jsonPath("$.ecologischbeheer").value(DEFAULT_ECOLOGISCHBEHEER.booleanValue()))
            .andExpect(jsonPath("$.kwaliteitsniveauactueel").value(DEFAULT_KWALITEITSNIVEAUACTUEEL))
            .andExpect(jsonPath("$.kwaliteitsniveaugewenst").value(DEFAULT_KWALITEITSNIVEAUGEWENST))
            .andExpect(jsonPath("$.kweker").value(DEFAULT_KWEKER))
            .andExpect(jsonPath("$.leverancier").value(DEFAULT_LEVERANCIER))
            .andExpect(jsonPath("$.eobjectnummer").value(DEFAULT_EOBJECTNUMMER))
            .andExpect(jsonPath("$.soortnaam").value(DEFAULT_SOORTNAAM))
            .andExpect(jsonPath("$.typestandplaats").value(DEFAULT_TYPESTANDPLAATS))
            .andExpect(jsonPath("$.typestandplaatsplus").value(DEFAULT_TYPESTANDPLAATSPLUS))
            .andExpect(jsonPath("$.vegetatieobjectbereikbaarheidplus").value(DEFAULT_VEGETATIEOBJECTBEREIKBAARHEIDPLUS));
    }

    @Test
    @Transactional
    void getNonExistingVegetatieobject() throws Exception {
        // Get the vegetatieobject
        restVegetatieobjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVegetatieobject() throws Exception {
        // Initialize the database
        vegetatieobjectRepository.saveAndFlush(vegetatieobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vegetatieobject
        Vegetatieobject updatedVegetatieobject = vegetatieobjectRepository.findById(vegetatieobject.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVegetatieobject are not directly saved in db
        em.detach(updatedVegetatieobject);
        updatedVegetatieobject
            .afvoeren(UPDATED_AFVOEREN)
            .bereikbaarheid(UPDATED_BEREIKBAARHEID)
            .ecologischbeheer(UPDATED_ECOLOGISCHBEHEER)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .kweker(UPDATED_KWEKER)
            .leverancier(UPDATED_LEVERANCIER)
            .eobjectnummer(UPDATED_EOBJECTNUMMER)
            .soortnaam(UPDATED_SOORTNAAM)
            .typestandplaats(UPDATED_TYPESTANDPLAATS)
            .typestandplaatsplus(UPDATED_TYPESTANDPLAATSPLUS)
            .vegetatieobjectbereikbaarheidplus(UPDATED_VEGETATIEOBJECTBEREIKBAARHEIDPLUS);

        restVegetatieobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVegetatieobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVegetatieobject))
            )
            .andExpect(status().isOk());

        // Validate the Vegetatieobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVegetatieobjectToMatchAllProperties(updatedVegetatieobject);
    }

    @Test
    @Transactional
    void putNonExistingVegetatieobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vegetatieobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVegetatieobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vegetatieobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vegetatieobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vegetatieobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVegetatieobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vegetatieobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVegetatieobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vegetatieobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vegetatieobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVegetatieobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vegetatieobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVegetatieobjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vegetatieobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vegetatieobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVegetatieobjectWithPatch() throws Exception {
        // Initialize the database
        vegetatieobjectRepository.saveAndFlush(vegetatieobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vegetatieobject using partial update
        Vegetatieobject partialUpdatedVegetatieobject = new Vegetatieobject();
        partialUpdatedVegetatieobject.setId(vegetatieobject.getId());

        partialUpdatedVegetatieobject
            .afvoeren(UPDATED_AFVOEREN)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .soortnaam(UPDATED_SOORTNAAM)
            .typestandplaats(UPDATED_TYPESTANDPLAATS)
            .typestandplaatsplus(UPDATED_TYPESTANDPLAATSPLUS)
            .vegetatieobjectbereikbaarheidplus(UPDATED_VEGETATIEOBJECTBEREIKBAARHEIDPLUS);

        restVegetatieobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVegetatieobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVegetatieobject))
            )
            .andExpect(status().isOk());

        // Validate the Vegetatieobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVegetatieobjectUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVegetatieobject, vegetatieobject),
            getPersistedVegetatieobject(vegetatieobject)
        );
    }

    @Test
    @Transactional
    void fullUpdateVegetatieobjectWithPatch() throws Exception {
        // Initialize the database
        vegetatieobjectRepository.saveAndFlush(vegetatieobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vegetatieobject using partial update
        Vegetatieobject partialUpdatedVegetatieobject = new Vegetatieobject();
        partialUpdatedVegetatieobject.setId(vegetatieobject.getId());

        partialUpdatedVegetatieobject
            .afvoeren(UPDATED_AFVOEREN)
            .bereikbaarheid(UPDATED_BEREIKBAARHEID)
            .ecologischbeheer(UPDATED_ECOLOGISCHBEHEER)
            .kwaliteitsniveauactueel(UPDATED_KWALITEITSNIVEAUACTUEEL)
            .kwaliteitsniveaugewenst(UPDATED_KWALITEITSNIVEAUGEWENST)
            .kweker(UPDATED_KWEKER)
            .leverancier(UPDATED_LEVERANCIER)
            .eobjectnummer(UPDATED_EOBJECTNUMMER)
            .soortnaam(UPDATED_SOORTNAAM)
            .typestandplaats(UPDATED_TYPESTANDPLAATS)
            .typestandplaatsplus(UPDATED_TYPESTANDPLAATSPLUS)
            .vegetatieobjectbereikbaarheidplus(UPDATED_VEGETATIEOBJECTBEREIKBAARHEIDPLUS);

        restVegetatieobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVegetatieobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVegetatieobject))
            )
            .andExpect(status().isOk());

        // Validate the Vegetatieobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVegetatieobjectUpdatableFieldsEquals(
            partialUpdatedVegetatieobject,
            getPersistedVegetatieobject(partialUpdatedVegetatieobject)
        );
    }

    @Test
    @Transactional
    void patchNonExistingVegetatieobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vegetatieobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVegetatieobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vegetatieobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vegetatieobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vegetatieobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVegetatieobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vegetatieobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVegetatieobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vegetatieobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vegetatieobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVegetatieobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vegetatieobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVegetatieobjectMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vegetatieobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vegetatieobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVegetatieobject() throws Exception {
        // Initialize the database
        vegetatieobjectRepository.saveAndFlush(vegetatieobject);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vegetatieobject
        restVegetatieobjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, vegetatieobject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vegetatieobjectRepository.count();
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

    protected Vegetatieobject getPersistedVegetatieobject(Vegetatieobject vegetatieobject) {
        return vegetatieobjectRepository.findById(vegetatieobject.getId()).orElseThrow();
    }

    protected void assertPersistedVegetatieobjectToMatchAllProperties(Vegetatieobject expectedVegetatieobject) {
        assertVegetatieobjectAllPropertiesEquals(expectedVegetatieobject, getPersistedVegetatieobject(expectedVegetatieobject));
    }

    protected void assertPersistedVegetatieobjectToMatchUpdatableProperties(Vegetatieobject expectedVegetatieobject) {
        assertVegetatieobjectAllUpdatablePropertiesEquals(expectedVegetatieobject, getPersistedVegetatieobject(expectedVegetatieobject));
    }
}
