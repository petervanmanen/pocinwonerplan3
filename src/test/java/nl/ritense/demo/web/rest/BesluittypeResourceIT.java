package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BesluittypeAsserts.*;
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
import nl.ritense.demo.domain.Besluittype;
import nl.ritense.demo.repository.BesluittypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BesluittypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BesluittypeResourceIT {

    private static final String DEFAULT_BESLUITCATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_BESLUITCATEGORIE = "BBBBBBBBBB";

    private static final String DEFAULT_BESLUITTYPEOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_BESLUITTYPEOMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_BESLUITTYPEOMSCHRIJVINGGENERIEK = "AAAAAAAAAA";
    private static final String UPDATED_BESLUITTYPEOMSCHRIJVINGGENERIEK = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMBEGINGELDIGHEIDBESLUITTYPE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMBEGINGELDIGHEIDBESLUITTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMEINDEGELDIGHEIDBESLUITTYPE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMEINDEGELDIGHEIDBESLUITTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_INDICATIEPUBLICATIE = "AAAAAAAAAA";
    private static final String UPDATED_INDICATIEPUBLICATIE = "BBBBBBBBBB";

    private static final String DEFAULT_PUBLICATIETEKST = "AAAAAAAAAA";
    private static final String UPDATED_PUBLICATIETEKST = "BBBBBBBBBB";

    private static final String DEFAULT_PUBLICATIETERMIJN = "AAAAAAAAAA";
    private static final String UPDATED_PUBLICATIETERMIJN = "BBBBBBBBBB";

    private static final String DEFAULT_REACTIETERMIJN = "AAAAAAAAAA";
    private static final String UPDATED_REACTIETERMIJN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/besluittypes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BesluittypeRepository besluittypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBesluittypeMockMvc;

    private Besluittype besluittype;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Besluittype createEntity(EntityManager em) {
        Besluittype besluittype = new Besluittype()
            .besluitcategorie(DEFAULT_BESLUITCATEGORIE)
            .besluittypeomschrijving(DEFAULT_BESLUITTYPEOMSCHRIJVING)
            .besluittypeomschrijvinggeneriek(DEFAULT_BESLUITTYPEOMSCHRIJVINGGENERIEK)
            .datumbegingeldigheidbesluittype(DEFAULT_DATUMBEGINGELDIGHEIDBESLUITTYPE)
            .datumeindegeldigheidbesluittype(DEFAULT_DATUMEINDEGELDIGHEIDBESLUITTYPE)
            .indicatiepublicatie(DEFAULT_INDICATIEPUBLICATIE)
            .publicatietekst(DEFAULT_PUBLICATIETEKST)
            .publicatietermijn(DEFAULT_PUBLICATIETERMIJN)
            .reactietermijn(DEFAULT_REACTIETERMIJN);
        return besluittype;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Besluittype createUpdatedEntity(EntityManager em) {
        Besluittype besluittype = new Besluittype()
            .besluitcategorie(UPDATED_BESLUITCATEGORIE)
            .besluittypeomschrijving(UPDATED_BESLUITTYPEOMSCHRIJVING)
            .besluittypeomschrijvinggeneriek(UPDATED_BESLUITTYPEOMSCHRIJVINGGENERIEK)
            .datumbegingeldigheidbesluittype(UPDATED_DATUMBEGINGELDIGHEIDBESLUITTYPE)
            .datumeindegeldigheidbesluittype(UPDATED_DATUMEINDEGELDIGHEIDBESLUITTYPE)
            .indicatiepublicatie(UPDATED_INDICATIEPUBLICATIE)
            .publicatietekst(UPDATED_PUBLICATIETEKST)
            .publicatietermijn(UPDATED_PUBLICATIETERMIJN)
            .reactietermijn(UPDATED_REACTIETERMIJN);
        return besluittype;
    }

    @BeforeEach
    public void initTest() {
        besluittype = createEntity(em);
    }

    @Test
    @Transactional
    void createBesluittype() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Besluittype
        var returnedBesluittype = om.readValue(
            restBesluittypeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(besluittype)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Besluittype.class
        );

        // Validate the Besluittype in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBesluittypeUpdatableFieldsEquals(returnedBesluittype, getPersistedBesluittype(returnedBesluittype));
    }

    @Test
    @Transactional
    void createBesluittypeWithExistingId() throws Exception {
        // Create the Besluittype with an existing ID
        besluittype.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBesluittypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(besluittype)))
            .andExpect(status().isBadRequest());

        // Validate the Besluittype in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBesluittypes() throws Exception {
        // Initialize the database
        besluittypeRepository.saveAndFlush(besluittype);

        // Get all the besluittypeList
        restBesluittypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(besluittype.getId().intValue())))
            .andExpect(jsonPath("$.[*].besluitcategorie").value(hasItem(DEFAULT_BESLUITCATEGORIE)))
            .andExpect(jsonPath("$.[*].besluittypeomschrijving").value(hasItem(DEFAULT_BESLUITTYPEOMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].besluittypeomschrijvinggeneriek").value(hasItem(DEFAULT_BESLUITTYPEOMSCHRIJVINGGENERIEK)))
            .andExpect(jsonPath("$.[*].datumbegingeldigheidbesluittype").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEIDBESLUITTYPE)))
            .andExpect(jsonPath("$.[*].datumeindegeldigheidbesluittype").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEIDBESLUITTYPE)))
            .andExpect(jsonPath("$.[*].indicatiepublicatie").value(hasItem(DEFAULT_INDICATIEPUBLICATIE)))
            .andExpect(jsonPath("$.[*].publicatietekst").value(hasItem(DEFAULT_PUBLICATIETEKST)))
            .andExpect(jsonPath("$.[*].publicatietermijn").value(hasItem(DEFAULT_PUBLICATIETERMIJN)))
            .andExpect(jsonPath("$.[*].reactietermijn").value(hasItem(DEFAULT_REACTIETERMIJN)));
    }

    @Test
    @Transactional
    void getBesluittype() throws Exception {
        // Initialize the database
        besluittypeRepository.saveAndFlush(besluittype);

        // Get the besluittype
        restBesluittypeMockMvc
            .perform(get(ENTITY_API_URL_ID, besluittype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(besluittype.getId().intValue()))
            .andExpect(jsonPath("$.besluitcategorie").value(DEFAULT_BESLUITCATEGORIE))
            .andExpect(jsonPath("$.besluittypeomschrijving").value(DEFAULT_BESLUITTYPEOMSCHRIJVING))
            .andExpect(jsonPath("$.besluittypeomschrijvinggeneriek").value(DEFAULT_BESLUITTYPEOMSCHRIJVINGGENERIEK))
            .andExpect(jsonPath("$.datumbegingeldigheidbesluittype").value(DEFAULT_DATUMBEGINGELDIGHEIDBESLUITTYPE))
            .andExpect(jsonPath("$.datumeindegeldigheidbesluittype").value(DEFAULT_DATUMEINDEGELDIGHEIDBESLUITTYPE))
            .andExpect(jsonPath("$.indicatiepublicatie").value(DEFAULT_INDICATIEPUBLICATIE))
            .andExpect(jsonPath("$.publicatietekst").value(DEFAULT_PUBLICATIETEKST))
            .andExpect(jsonPath("$.publicatietermijn").value(DEFAULT_PUBLICATIETERMIJN))
            .andExpect(jsonPath("$.reactietermijn").value(DEFAULT_REACTIETERMIJN));
    }

    @Test
    @Transactional
    void getNonExistingBesluittype() throws Exception {
        // Get the besluittype
        restBesluittypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBesluittype() throws Exception {
        // Initialize the database
        besluittypeRepository.saveAndFlush(besluittype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the besluittype
        Besluittype updatedBesluittype = besluittypeRepository.findById(besluittype.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBesluittype are not directly saved in db
        em.detach(updatedBesluittype);
        updatedBesluittype
            .besluitcategorie(UPDATED_BESLUITCATEGORIE)
            .besluittypeomschrijving(UPDATED_BESLUITTYPEOMSCHRIJVING)
            .besluittypeomschrijvinggeneriek(UPDATED_BESLUITTYPEOMSCHRIJVINGGENERIEK)
            .datumbegingeldigheidbesluittype(UPDATED_DATUMBEGINGELDIGHEIDBESLUITTYPE)
            .datumeindegeldigheidbesluittype(UPDATED_DATUMEINDEGELDIGHEIDBESLUITTYPE)
            .indicatiepublicatie(UPDATED_INDICATIEPUBLICATIE)
            .publicatietekst(UPDATED_PUBLICATIETEKST)
            .publicatietermijn(UPDATED_PUBLICATIETERMIJN)
            .reactietermijn(UPDATED_REACTIETERMIJN);

        restBesluittypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBesluittype.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBesluittype))
            )
            .andExpect(status().isOk());

        // Validate the Besluittype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBesluittypeToMatchAllProperties(updatedBesluittype);
    }

    @Test
    @Transactional
    void putNonExistingBesluittype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        besluittype.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBesluittypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, besluittype.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(besluittype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Besluittype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBesluittype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        besluittype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBesluittypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(besluittype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Besluittype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBesluittype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        besluittype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBesluittypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(besluittype)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Besluittype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBesluittypeWithPatch() throws Exception {
        // Initialize the database
        besluittypeRepository.saveAndFlush(besluittype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the besluittype using partial update
        Besluittype partialUpdatedBesluittype = new Besluittype();
        partialUpdatedBesluittype.setId(besluittype.getId());

        partialUpdatedBesluittype
            .besluitcategorie(UPDATED_BESLUITCATEGORIE)
            .datumbegingeldigheidbesluittype(UPDATED_DATUMBEGINGELDIGHEIDBESLUITTYPE)
            .datumeindegeldigheidbesluittype(UPDATED_DATUMEINDEGELDIGHEIDBESLUITTYPE)
            .publicatietekst(UPDATED_PUBLICATIETEKST);

        restBesluittypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBesluittype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBesluittype))
            )
            .andExpect(status().isOk());

        // Validate the Besluittype in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBesluittypeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBesluittype, besluittype),
            getPersistedBesluittype(besluittype)
        );
    }

    @Test
    @Transactional
    void fullUpdateBesluittypeWithPatch() throws Exception {
        // Initialize the database
        besluittypeRepository.saveAndFlush(besluittype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the besluittype using partial update
        Besluittype partialUpdatedBesluittype = new Besluittype();
        partialUpdatedBesluittype.setId(besluittype.getId());

        partialUpdatedBesluittype
            .besluitcategorie(UPDATED_BESLUITCATEGORIE)
            .besluittypeomschrijving(UPDATED_BESLUITTYPEOMSCHRIJVING)
            .besluittypeomschrijvinggeneriek(UPDATED_BESLUITTYPEOMSCHRIJVINGGENERIEK)
            .datumbegingeldigheidbesluittype(UPDATED_DATUMBEGINGELDIGHEIDBESLUITTYPE)
            .datumeindegeldigheidbesluittype(UPDATED_DATUMEINDEGELDIGHEIDBESLUITTYPE)
            .indicatiepublicatie(UPDATED_INDICATIEPUBLICATIE)
            .publicatietekst(UPDATED_PUBLICATIETEKST)
            .publicatietermijn(UPDATED_PUBLICATIETERMIJN)
            .reactietermijn(UPDATED_REACTIETERMIJN);

        restBesluittypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBesluittype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBesluittype))
            )
            .andExpect(status().isOk());

        // Validate the Besluittype in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBesluittypeUpdatableFieldsEquals(partialUpdatedBesluittype, getPersistedBesluittype(partialUpdatedBesluittype));
    }

    @Test
    @Transactional
    void patchNonExistingBesluittype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        besluittype.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBesluittypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, besluittype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(besluittype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Besluittype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBesluittype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        besluittype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBesluittypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(besluittype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Besluittype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBesluittype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        besluittype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBesluittypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(besluittype)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Besluittype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBesluittype() throws Exception {
        // Initialize the database
        besluittypeRepository.saveAndFlush(besluittype);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the besluittype
        restBesluittypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, besluittype.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return besluittypeRepository.count();
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

    protected Besluittype getPersistedBesluittype(Besluittype besluittype) {
        return besluittypeRepository.findById(besluittype.getId()).orElseThrow();
    }

    protected void assertPersistedBesluittypeToMatchAllProperties(Besluittype expectedBesluittype) {
        assertBesluittypeAllPropertiesEquals(expectedBesluittype, getPersistedBesluittype(expectedBesluittype));
    }

    protected void assertPersistedBesluittypeToMatchUpdatableProperties(Besluittype expectedBesluittype) {
        assertBesluittypeAllUpdatablePropertiesEquals(expectedBesluittype, getPersistedBesluittype(expectedBesluittype));
    }
}
