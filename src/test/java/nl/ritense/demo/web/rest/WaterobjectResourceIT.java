package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.WaterobjectAsserts.*;
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
import nl.ritense.demo.domain.Waterobject;
import nl.ritense.demo.repository.WaterobjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WaterobjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WaterobjectResourceIT {

    private static final String DEFAULT_BREEDTE = "AAAAAAAAAA";
    private static final String UPDATED_BREEDTE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FOLIE = false;
    private static final Boolean UPDATED_FOLIE = true;

    private static final String DEFAULT_HOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_HOOGTE = "BBBBBBBBBB";

    private static final String DEFAULT_INFILTRERENDOPPERVLAK = "AAAAAAAAAA";
    private static final String UPDATED_INFILTRERENDOPPERVLAK = "BBBBBBBBBB";

    private static final String DEFAULT_INFILTRERENDVERMOGEN = "AAAAAAAAAA";
    private static final String UPDATED_INFILTRERENDVERMOGEN = "BBBBBBBBBB";

    private static final String DEFAULT_LENGTE = "AAAAAAAAAA";
    private static final String UPDATED_LENGTE = "BBBBBBBBBB";

    private static final String DEFAULT_LOZINGSPUNT = "AAAAAAAAAA";
    private static final String UPDATED_LOZINGSPUNT = "BBBBBBBBBB";

    private static final String DEFAULT_OPPERVLAKTE = "AAAAAAAAAA";
    private static final String UPDATED_OPPERVLAKTE = "BBBBBBBBBB";

    private static final String DEFAULT_POROSITEIT = "AAAAAAAAAA";
    private static final String UPDATED_POROSITEIT = "BBBBBBBBBB";

    private static final String DEFAULT_STREEFDIEPTE = "AAAAAAAAAA";
    private static final String UPDATED_STREEFDIEPTE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEPLUS = "AAAAAAAAAA";
    private static final String UPDATED_TYPEPLUS = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEPLUS_2 = "AAAAAAAAAA";
    private static final String UPDATED_TYPEPLUS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEVAARWATER = "AAAAAAAAAA";
    private static final String UPDATED_TYPEVAARWATER = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEWATERPLANT = "AAAAAAAAAA";
    private static final String UPDATED_TYPEWATERPLANT = "BBBBBBBBBB";

    private static final String DEFAULT_UITSTROOMNIVEAU = "AAAAAAAAAA";
    private static final String UPDATED_UITSTROOMNIVEAU = "BBBBBBBBBB";

    private static final String DEFAULT_VAARWEGTRAJECT = "AAAAAAAAAA";
    private static final String UPDATED_VAARWEGTRAJECT = "BBBBBBBBBB";

    private static final String DEFAULT_VORM = "AAAAAAAAAA";
    private static final String UPDATED_VORM = "BBBBBBBBBB";

    private static final String DEFAULT_WATERNAAM = "AAAAAAAAAA";
    private static final String UPDATED_WATERNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_WATERPEIL = "AAAAAAAAAA";
    private static final String UPDATED_WATERPEIL = "BBBBBBBBBB";

    private static final String DEFAULT_WATERPEILWINTER = "AAAAAAAAAA";
    private static final String UPDATED_WATERPEILWINTER = "BBBBBBBBBB";

    private static final String DEFAULT_WATERPEILZOMER = "AAAAAAAAAA";
    private static final String UPDATED_WATERPEILZOMER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_WATERPLANTEN = false;
    private static final Boolean UPDATED_WATERPLANTEN = true;

    private static final String ENTITY_API_URL = "/api/waterobjects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WaterobjectRepository waterobjectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWaterobjectMockMvc;

    private Waterobject waterobject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Waterobject createEntity(EntityManager em) {
        Waterobject waterobject = new Waterobject()
            .breedte(DEFAULT_BREEDTE)
            .folie(DEFAULT_FOLIE)
            .hoogte(DEFAULT_HOOGTE)
            .infiltrerendoppervlak(DEFAULT_INFILTRERENDOPPERVLAK)
            .infiltrerendvermogen(DEFAULT_INFILTRERENDVERMOGEN)
            .lengte(DEFAULT_LENGTE)
            .lozingspunt(DEFAULT_LOZINGSPUNT)
            .oppervlakte(DEFAULT_OPPERVLAKTE)
            .porositeit(DEFAULT_POROSITEIT)
            .streefdiepte(DEFAULT_STREEFDIEPTE)
            .type(DEFAULT_TYPE)
            .typeplus(DEFAULT_TYPEPLUS)
            .typeplus2(DEFAULT_TYPEPLUS_2)
            .typevaarwater(DEFAULT_TYPEVAARWATER)
            .typewaterplant(DEFAULT_TYPEWATERPLANT)
            .uitstroomniveau(DEFAULT_UITSTROOMNIVEAU)
            .vaarwegtraject(DEFAULT_VAARWEGTRAJECT)
            .vorm(DEFAULT_VORM)
            .waternaam(DEFAULT_WATERNAAM)
            .waterpeil(DEFAULT_WATERPEIL)
            .waterpeilwinter(DEFAULT_WATERPEILWINTER)
            .waterpeilzomer(DEFAULT_WATERPEILZOMER)
            .waterplanten(DEFAULT_WATERPLANTEN);
        return waterobject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Waterobject createUpdatedEntity(EntityManager em) {
        Waterobject waterobject = new Waterobject()
            .breedte(UPDATED_BREEDTE)
            .folie(UPDATED_FOLIE)
            .hoogte(UPDATED_HOOGTE)
            .infiltrerendoppervlak(UPDATED_INFILTRERENDOPPERVLAK)
            .infiltrerendvermogen(UPDATED_INFILTRERENDVERMOGEN)
            .lengte(UPDATED_LENGTE)
            .lozingspunt(UPDATED_LOZINGSPUNT)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .porositeit(UPDATED_POROSITEIT)
            .streefdiepte(UPDATED_STREEFDIEPTE)
            .type(UPDATED_TYPE)
            .typeplus(UPDATED_TYPEPLUS)
            .typeplus2(UPDATED_TYPEPLUS_2)
            .typevaarwater(UPDATED_TYPEVAARWATER)
            .typewaterplant(UPDATED_TYPEWATERPLANT)
            .uitstroomniveau(UPDATED_UITSTROOMNIVEAU)
            .vaarwegtraject(UPDATED_VAARWEGTRAJECT)
            .vorm(UPDATED_VORM)
            .waternaam(UPDATED_WATERNAAM)
            .waterpeil(UPDATED_WATERPEIL)
            .waterpeilwinter(UPDATED_WATERPEILWINTER)
            .waterpeilzomer(UPDATED_WATERPEILZOMER)
            .waterplanten(UPDATED_WATERPLANTEN);
        return waterobject;
    }

    @BeforeEach
    public void initTest() {
        waterobject = createEntity(em);
    }

    @Test
    @Transactional
    void createWaterobject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Waterobject
        var returnedWaterobject = om.readValue(
            restWaterobjectMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(waterobject)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Waterobject.class
        );

        // Validate the Waterobject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertWaterobjectUpdatableFieldsEquals(returnedWaterobject, getPersistedWaterobject(returnedWaterobject));
    }

    @Test
    @Transactional
    void createWaterobjectWithExistingId() throws Exception {
        // Create the Waterobject with an existing ID
        waterobject.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWaterobjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(waterobject)))
            .andExpect(status().isBadRequest());

        // Validate the Waterobject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWaterobjects() throws Exception {
        // Initialize the database
        waterobjectRepository.saveAndFlush(waterobject);

        // Get all the waterobjectList
        restWaterobjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(waterobject.getId().intValue())))
            .andExpect(jsonPath("$.[*].breedte").value(hasItem(DEFAULT_BREEDTE)))
            .andExpect(jsonPath("$.[*].folie").value(hasItem(DEFAULT_FOLIE.booleanValue())))
            .andExpect(jsonPath("$.[*].hoogte").value(hasItem(DEFAULT_HOOGTE)))
            .andExpect(jsonPath("$.[*].infiltrerendoppervlak").value(hasItem(DEFAULT_INFILTRERENDOPPERVLAK)))
            .andExpect(jsonPath("$.[*].infiltrerendvermogen").value(hasItem(DEFAULT_INFILTRERENDVERMOGEN)))
            .andExpect(jsonPath("$.[*].lengte").value(hasItem(DEFAULT_LENGTE)))
            .andExpect(jsonPath("$.[*].lozingspunt").value(hasItem(DEFAULT_LOZINGSPUNT)))
            .andExpect(jsonPath("$.[*].oppervlakte").value(hasItem(DEFAULT_OPPERVLAKTE)))
            .andExpect(jsonPath("$.[*].porositeit").value(hasItem(DEFAULT_POROSITEIT)))
            .andExpect(jsonPath("$.[*].streefdiepte").value(hasItem(DEFAULT_STREEFDIEPTE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].typeplus").value(hasItem(DEFAULT_TYPEPLUS)))
            .andExpect(jsonPath("$.[*].typeplus2").value(hasItem(DEFAULT_TYPEPLUS_2)))
            .andExpect(jsonPath("$.[*].typevaarwater").value(hasItem(DEFAULT_TYPEVAARWATER)))
            .andExpect(jsonPath("$.[*].typewaterplant").value(hasItem(DEFAULT_TYPEWATERPLANT)))
            .andExpect(jsonPath("$.[*].uitstroomniveau").value(hasItem(DEFAULT_UITSTROOMNIVEAU)))
            .andExpect(jsonPath("$.[*].vaarwegtraject").value(hasItem(DEFAULT_VAARWEGTRAJECT)))
            .andExpect(jsonPath("$.[*].vorm").value(hasItem(DEFAULT_VORM)))
            .andExpect(jsonPath("$.[*].waternaam").value(hasItem(DEFAULT_WATERNAAM)))
            .andExpect(jsonPath("$.[*].waterpeil").value(hasItem(DEFAULT_WATERPEIL)))
            .andExpect(jsonPath("$.[*].waterpeilwinter").value(hasItem(DEFAULT_WATERPEILWINTER)))
            .andExpect(jsonPath("$.[*].waterpeilzomer").value(hasItem(DEFAULT_WATERPEILZOMER)))
            .andExpect(jsonPath("$.[*].waterplanten").value(hasItem(DEFAULT_WATERPLANTEN.booleanValue())));
    }

    @Test
    @Transactional
    void getWaterobject() throws Exception {
        // Initialize the database
        waterobjectRepository.saveAndFlush(waterobject);

        // Get the waterobject
        restWaterobjectMockMvc
            .perform(get(ENTITY_API_URL_ID, waterobject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(waterobject.getId().intValue()))
            .andExpect(jsonPath("$.breedte").value(DEFAULT_BREEDTE))
            .andExpect(jsonPath("$.folie").value(DEFAULT_FOLIE.booleanValue()))
            .andExpect(jsonPath("$.hoogte").value(DEFAULT_HOOGTE))
            .andExpect(jsonPath("$.infiltrerendoppervlak").value(DEFAULT_INFILTRERENDOPPERVLAK))
            .andExpect(jsonPath("$.infiltrerendvermogen").value(DEFAULT_INFILTRERENDVERMOGEN))
            .andExpect(jsonPath("$.lengte").value(DEFAULT_LENGTE))
            .andExpect(jsonPath("$.lozingspunt").value(DEFAULT_LOZINGSPUNT))
            .andExpect(jsonPath("$.oppervlakte").value(DEFAULT_OPPERVLAKTE))
            .andExpect(jsonPath("$.porositeit").value(DEFAULT_POROSITEIT))
            .andExpect(jsonPath("$.streefdiepte").value(DEFAULT_STREEFDIEPTE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.typeplus").value(DEFAULT_TYPEPLUS))
            .andExpect(jsonPath("$.typeplus2").value(DEFAULT_TYPEPLUS_2))
            .andExpect(jsonPath("$.typevaarwater").value(DEFAULT_TYPEVAARWATER))
            .andExpect(jsonPath("$.typewaterplant").value(DEFAULT_TYPEWATERPLANT))
            .andExpect(jsonPath("$.uitstroomniveau").value(DEFAULT_UITSTROOMNIVEAU))
            .andExpect(jsonPath("$.vaarwegtraject").value(DEFAULT_VAARWEGTRAJECT))
            .andExpect(jsonPath("$.vorm").value(DEFAULT_VORM))
            .andExpect(jsonPath("$.waternaam").value(DEFAULT_WATERNAAM))
            .andExpect(jsonPath("$.waterpeil").value(DEFAULT_WATERPEIL))
            .andExpect(jsonPath("$.waterpeilwinter").value(DEFAULT_WATERPEILWINTER))
            .andExpect(jsonPath("$.waterpeilzomer").value(DEFAULT_WATERPEILZOMER))
            .andExpect(jsonPath("$.waterplanten").value(DEFAULT_WATERPLANTEN.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingWaterobject() throws Exception {
        // Get the waterobject
        restWaterobjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWaterobject() throws Exception {
        // Initialize the database
        waterobjectRepository.saveAndFlush(waterobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the waterobject
        Waterobject updatedWaterobject = waterobjectRepository.findById(waterobject.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWaterobject are not directly saved in db
        em.detach(updatedWaterobject);
        updatedWaterobject
            .breedte(UPDATED_BREEDTE)
            .folie(UPDATED_FOLIE)
            .hoogte(UPDATED_HOOGTE)
            .infiltrerendoppervlak(UPDATED_INFILTRERENDOPPERVLAK)
            .infiltrerendvermogen(UPDATED_INFILTRERENDVERMOGEN)
            .lengte(UPDATED_LENGTE)
            .lozingspunt(UPDATED_LOZINGSPUNT)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .porositeit(UPDATED_POROSITEIT)
            .streefdiepte(UPDATED_STREEFDIEPTE)
            .type(UPDATED_TYPE)
            .typeplus(UPDATED_TYPEPLUS)
            .typeplus2(UPDATED_TYPEPLUS_2)
            .typevaarwater(UPDATED_TYPEVAARWATER)
            .typewaterplant(UPDATED_TYPEWATERPLANT)
            .uitstroomniveau(UPDATED_UITSTROOMNIVEAU)
            .vaarwegtraject(UPDATED_VAARWEGTRAJECT)
            .vorm(UPDATED_VORM)
            .waternaam(UPDATED_WATERNAAM)
            .waterpeil(UPDATED_WATERPEIL)
            .waterpeilwinter(UPDATED_WATERPEILWINTER)
            .waterpeilzomer(UPDATED_WATERPEILZOMER)
            .waterplanten(UPDATED_WATERPLANTEN);

        restWaterobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWaterobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedWaterobject))
            )
            .andExpect(status().isOk());

        // Validate the Waterobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWaterobjectToMatchAllProperties(updatedWaterobject);
    }

    @Test
    @Transactional
    void putNonExistingWaterobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        waterobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWaterobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, waterobject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(waterobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Waterobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWaterobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        waterobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWaterobjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(waterobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Waterobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWaterobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        waterobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWaterobjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(waterobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Waterobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWaterobjectWithPatch() throws Exception {
        // Initialize the database
        waterobjectRepository.saveAndFlush(waterobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the waterobject using partial update
        Waterobject partialUpdatedWaterobject = new Waterobject();
        partialUpdatedWaterobject.setId(waterobject.getId());

        partialUpdatedWaterobject
            .breedte(UPDATED_BREEDTE)
            .hoogte(UPDATED_HOOGTE)
            .infiltrerendoppervlak(UPDATED_INFILTRERENDOPPERVLAK)
            .infiltrerendvermogen(UPDATED_INFILTRERENDVERMOGEN)
            .lozingspunt(UPDATED_LOZINGSPUNT)
            .streefdiepte(UPDATED_STREEFDIEPTE)
            .typeplus2(UPDATED_TYPEPLUS_2)
            .vorm(UPDATED_VORM)
            .waterpeil(UPDATED_WATERPEIL)
            .waterpeilzomer(UPDATED_WATERPEILZOMER)
            .waterplanten(UPDATED_WATERPLANTEN);

        restWaterobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWaterobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWaterobject))
            )
            .andExpect(status().isOk());

        // Validate the Waterobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWaterobjectUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedWaterobject, waterobject),
            getPersistedWaterobject(waterobject)
        );
    }

    @Test
    @Transactional
    void fullUpdateWaterobjectWithPatch() throws Exception {
        // Initialize the database
        waterobjectRepository.saveAndFlush(waterobject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the waterobject using partial update
        Waterobject partialUpdatedWaterobject = new Waterobject();
        partialUpdatedWaterobject.setId(waterobject.getId());

        partialUpdatedWaterobject
            .breedte(UPDATED_BREEDTE)
            .folie(UPDATED_FOLIE)
            .hoogte(UPDATED_HOOGTE)
            .infiltrerendoppervlak(UPDATED_INFILTRERENDOPPERVLAK)
            .infiltrerendvermogen(UPDATED_INFILTRERENDVERMOGEN)
            .lengte(UPDATED_LENGTE)
            .lozingspunt(UPDATED_LOZINGSPUNT)
            .oppervlakte(UPDATED_OPPERVLAKTE)
            .porositeit(UPDATED_POROSITEIT)
            .streefdiepte(UPDATED_STREEFDIEPTE)
            .type(UPDATED_TYPE)
            .typeplus(UPDATED_TYPEPLUS)
            .typeplus2(UPDATED_TYPEPLUS_2)
            .typevaarwater(UPDATED_TYPEVAARWATER)
            .typewaterplant(UPDATED_TYPEWATERPLANT)
            .uitstroomniveau(UPDATED_UITSTROOMNIVEAU)
            .vaarwegtraject(UPDATED_VAARWEGTRAJECT)
            .vorm(UPDATED_VORM)
            .waternaam(UPDATED_WATERNAAM)
            .waterpeil(UPDATED_WATERPEIL)
            .waterpeilwinter(UPDATED_WATERPEILWINTER)
            .waterpeilzomer(UPDATED_WATERPEILZOMER)
            .waterplanten(UPDATED_WATERPLANTEN);

        restWaterobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWaterobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWaterobject))
            )
            .andExpect(status().isOk());

        // Validate the Waterobject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWaterobjectUpdatableFieldsEquals(partialUpdatedWaterobject, getPersistedWaterobject(partialUpdatedWaterobject));
    }

    @Test
    @Transactional
    void patchNonExistingWaterobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        waterobject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWaterobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, waterobject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(waterobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Waterobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWaterobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        waterobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWaterobjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(waterobject))
            )
            .andExpect(status().isBadRequest());

        // Validate the Waterobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWaterobject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        waterobject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWaterobjectMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(waterobject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Waterobject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWaterobject() throws Exception {
        // Initialize the database
        waterobjectRepository.saveAndFlush(waterobject);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the waterobject
        restWaterobjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, waterobject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return waterobjectRepository.count();
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

    protected Waterobject getPersistedWaterobject(Waterobject waterobject) {
        return waterobjectRepository.findById(waterobject.getId()).orElseThrow();
    }

    protected void assertPersistedWaterobjectToMatchAllProperties(Waterobject expectedWaterobject) {
        assertWaterobjectAllPropertiesEquals(expectedWaterobject, getPersistedWaterobject(expectedWaterobject));
    }

    protected void assertPersistedWaterobjectToMatchUpdatableProperties(Waterobject expectedWaterobject) {
        assertWaterobjectAllUpdatablePropertiesEquals(expectedWaterobject, getPersistedWaterobject(expectedWaterobject));
    }
}
