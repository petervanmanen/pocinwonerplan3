package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.EcomponentAsserts.*;
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
import nl.ritense.demo.domain.Ecomponent;
import nl.ritense.demo.repository.EcomponentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EcomponentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EcomponentResourceIT {

    private static final String DEFAULT_BEDRAG = "AAAAAAAAAA";
    private static final String UPDATED_BEDRAG = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMBEGINBETREKKINGOP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINBETREKKINGOP = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEBETREKKINGOP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEBETREKKINGOP = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DEBETCREDIT = "AAAAAAAAAA";
    private static final String UPDATED_DEBETCREDIT = "BBBBBBBBBB";

    private static final String DEFAULT_GROEP = "AAAAAAAAAA";
    private static final String UPDATED_GROEP = "BBBBBBBBBB";

    private static final String DEFAULT_GROEPCODE = "AAAAAAAAAA";
    private static final String UPDATED_GROEPCODE = "BBBBBBBBBB";

    private static final String DEFAULT_GROOTBOEKCODE = "AAAAAAAAAA";
    private static final String UPDATED_GROOTBOEKCODE = "BBBBBBBBBB";

    private static final String DEFAULT_GROOTBOEKOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_GROOTBOEKOMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_KOSTENPLAATS = "AAAAAAAAAA";
    private static final String UPDATED_KOSTENPLAATS = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_REKENINGNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_REKENINGNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_TOELICHTING = "AAAAAAAAAA";
    private static final String UPDATED_TOELICHTING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ecomponents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EcomponentRepository ecomponentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEcomponentMockMvc;

    private Ecomponent ecomponent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ecomponent createEntity(EntityManager em) {
        Ecomponent ecomponent = new Ecomponent()
            .bedrag(DEFAULT_BEDRAG)
            .datumbeginbetrekkingop(DEFAULT_DATUMBEGINBETREKKINGOP)
            .datumeindebetrekkingop(DEFAULT_DATUMEINDEBETREKKINGOP)
            .debetcredit(DEFAULT_DEBETCREDIT)
            .groep(DEFAULT_GROEP)
            .groepcode(DEFAULT_GROEPCODE)
            .grootboekcode(DEFAULT_GROOTBOEKCODE)
            .grootboekomschrijving(DEFAULT_GROOTBOEKOMSCHRIJVING)
            .kostenplaats(DEFAULT_KOSTENPLAATS)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .rekeningnummer(DEFAULT_REKENINGNUMMER)
            .toelichting(DEFAULT_TOELICHTING);
        return ecomponent;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ecomponent createUpdatedEntity(EntityManager em) {
        Ecomponent ecomponent = new Ecomponent()
            .bedrag(UPDATED_BEDRAG)
            .datumbeginbetrekkingop(UPDATED_DATUMBEGINBETREKKINGOP)
            .datumeindebetrekkingop(UPDATED_DATUMEINDEBETREKKINGOP)
            .debetcredit(UPDATED_DEBETCREDIT)
            .groep(UPDATED_GROEP)
            .groepcode(UPDATED_GROEPCODE)
            .grootboekcode(UPDATED_GROOTBOEKCODE)
            .grootboekomschrijving(UPDATED_GROOTBOEKOMSCHRIJVING)
            .kostenplaats(UPDATED_KOSTENPLAATS)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .rekeningnummer(UPDATED_REKENINGNUMMER)
            .toelichting(UPDATED_TOELICHTING);
        return ecomponent;
    }

    @BeforeEach
    public void initTest() {
        ecomponent = createEntity(em);
    }

    @Test
    @Transactional
    void createEcomponent() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Ecomponent
        var returnedEcomponent = om.readValue(
            restEcomponentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ecomponent)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Ecomponent.class
        );

        // Validate the Ecomponent in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEcomponentUpdatableFieldsEquals(returnedEcomponent, getPersistedEcomponent(returnedEcomponent));
    }

    @Test
    @Transactional
    void createEcomponentWithExistingId() throws Exception {
        // Create the Ecomponent with an existing ID
        ecomponent.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEcomponentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ecomponent)))
            .andExpect(status().isBadRequest());

        // Validate the Ecomponent in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEcomponents() throws Exception {
        // Initialize the database
        ecomponentRepository.saveAndFlush(ecomponent);

        // Get all the ecomponentList
        restEcomponentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ecomponent.getId().intValue())))
            .andExpect(jsonPath("$.[*].bedrag").value(hasItem(DEFAULT_BEDRAG)))
            .andExpect(jsonPath("$.[*].datumbeginbetrekkingop").value(hasItem(DEFAULT_DATUMBEGINBETREKKINGOP.toString())))
            .andExpect(jsonPath("$.[*].datumeindebetrekkingop").value(hasItem(DEFAULT_DATUMEINDEBETREKKINGOP.toString())))
            .andExpect(jsonPath("$.[*].debetcredit").value(hasItem(DEFAULT_DEBETCREDIT)))
            .andExpect(jsonPath("$.[*].groep").value(hasItem(DEFAULT_GROEP)))
            .andExpect(jsonPath("$.[*].groepcode").value(hasItem(DEFAULT_GROEPCODE)))
            .andExpect(jsonPath("$.[*].grootboekcode").value(hasItem(DEFAULT_GROOTBOEKCODE)))
            .andExpect(jsonPath("$.[*].grootboekomschrijving").value(hasItem(DEFAULT_GROOTBOEKOMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].kostenplaats").value(hasItem(DEFAULT_KOSTENPLAATS)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].rekeningnummer").value(hasItem(DEFAULT_REKENINGNUMMER)))
            .andExpect(jsonPath("$.[*].toelichting").value(hasItem(DEFAULT_TOELICHTING)));
    }

    @Test
    @Transactional
    void getEcomponent() throws Exception {
        // Initialize the database
        ecomponentRepository.saveAndFlush(ecomponent);

        // Get the ecomponent
        restEcomponentMockMvc
            .perform(get(ENTITY_API_URL_ID, ecomponent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ecomponent.getId().intValue()))
            .andExpect(jsonPath("$.bedrag").value(DEFAULT_BEDRAG))
            .andExpect(jsonPath("$.datumbeginbetrekkingop").value(DEFAULT_DATUMBEGINBETREKKINGOP.toString()))
            .andExpect(jsonPath("$.datumeindebetrekkingop").value(DEFAULT_DATUMEINDEBETREKKINGOP.toString()))
            .andExpect(jsonPath("$.debetcredit").value(DEFAULT_DEBETCREDIT))
            .andExpect(jsonPath("$.groep").value(DEFAULT_GROEP))
            .andExpect(jsonPath("$.groepcode").value(DEFAULT_GROEPCODE))
            .andExpect(jsonPath("$.grootboekcode").value(DEFAULT_GROOTBOEKCODE))
            .andExpect(jsonPath("$.grootboekomschrijving").value(DEFAULT_GROOTBOEKOMSCHRIJVING))
            .andExpect(jsonPath("$.kostenplaats").value(DEFAULT_KOSTENPLAATS))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.rekeningnummer").value(DEFAULT_REKENINGNUMMER))
            .andExpect(jsonPath("$.toelichting").value(DEFAULT_TOELICHTING));
    }

    @Test
    @Transactional
    void getNonExistingEcomponent() throws Exception {
        // Get the ecomponent
        restEcomponentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEcomponent() throws Exception {
        // Initialize the database
        ecomponentRepository.saveAndFlush(ecomponent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ecomponent
        Ecomponent updatedEcomponent = ecomponentRepository.findById(ecomponent.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEcomponent are not directly saved in db
        em.detach(updatedEcomponent);
        updatedEcomponent
            .bedrag(UPDATED_BEDRAG)
            .datumbeginbetrekkingop(UPDATED_DATUMBEGINBETREKKINGOP)
            .datumeindebetrekkingop(UPDATED_DATUMEINDEBETREKKINGOP)
            .debetcredit(UPDATED_DEBETCREDIT)
            .groep(UPDATED_GROEP)
            .groepcode(UPDATED_GROEPCODE)
            .grootboekcode(UPDATED_GROOTBOEKCODE)
            .grootboekomschrijving(UPDATED_GROOTBOEKOMSCHRIJVING)
            .kostenplaats(UPDATED_KOSTENPLAATS)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .rekeningnummer(UPDATED_REKENINGNUMMER)
            .toelichting(UPDATED_TOELICHTING);

        restEcomponentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEcomponent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEcomponent))
            )
            .andExpect(status().isOk());

        // Validate the Ecomponent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEcomponentToMatchAllProperties(updatedEcomponent);
    }

    @Test
    @Transactional
    void putNonExistingEcomponent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ecomponent.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEcomponentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ecomponent.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ecomponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ecomponent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEcomponent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ecomponent.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcomponentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ecomponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ecomponent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEcomponent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ecomponent.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcomponentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ecomponent)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ecomponent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEcomponentWithPatch() throws Exception {
        // Initialize the database
        ecomponentRepository.saveAndFlush(ecomponent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ecomponent using partial update
        Ecomponent partialUpdatedEcomponent = new Ecomponent();
        partialUpdatedEcomponent.setId(ecomponent.getId());

        partialUpdatedEcomponent
            .datumeindebetrekkingop(UPDATED_DATUMEINDEBETREKKINGOP)
            .debetcredit(UPDATED_DEBETCREDIT)
            .groep(UPDATED_GROEP)
            .groepcode(UPDATED_GROEPCODE)
            .grootboekcode(UPDATED_GROOTBOEKCODE)
            .grootboekomschrijving(UPDATED_GROOTBOEKOMSCHRIJVING)
            .kostenplaats(UPDATED_KOSTENPLAATS)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restEcomponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEcomponent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEcomponent))
            )
            .andExpect(status().isOk());

        // Validate the Ecomponent in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEcomponentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEcomponent, ecomponent),
            getPersistedEcomponent(ecomponent)
        );
    }

    @Test
    @Transactional
    void fullUpdateEcomponentWithPatch() throws Exception {
        // Initialize the database
        ecomponentRepository.saveAndFlush(ecomponent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ecomponent using partial update
        Ecomponent partialUpdatedEcomponent = new Ecomponent();
        partialUpdatedEcomponent.setId(ecomponent.getId());

        partialUpdatedEcomponent
            .bedrag(UPDATED_BEDRAG)
            .datumbeginbetrekkingop(UPDATED_DATUMBEGINBETREKKINGOP)
            .datumeindebetrekkingop(UPDATED_DATUMEINDEBETREKKINGOP)
            .debetcredit(UPDATED_DEBETCREDIT)
            .groep(UPDATED_GROEP)
            .groepcode(UPDATED_GROEPCODE)
            .grootboekcode(UPDATED_GROOTBOEKCODE)
            .grootboekomschrijving(UPDATED_GROOTBOEKOMSCHRIJVING)
            .kostenplaats(UPDATED_KOSTENPLAATS)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .rekeningnummer(UPDATED_REKENINGNUMMER)
            .toelichting(UPDATED_TOELICHTING);

        restEcomponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEcomponent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEcomponent))
            )
            .andExpect(status().isOk());

        // Validate the Ecomponent in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEcomponentUpdatableFieldsEquals(partialUpdatedEcomponent, getPersistedEcomponent(partialUpdatedEcomponent));
    }

    @Test
    @Transactional
    void patchNonExistingEcomponent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ecomponent.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEcomponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ecomponent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ecomponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ecomponent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEcomponent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ecomponent.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcomponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ecomponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ecomponent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEcomponent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ecomponent.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcomponentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ecomponent)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ecomponent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEcomponent() throws Exception {
        // Initialize the database
        ecomponentRepository.saveAndFlush(ecomponent);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ecomponent
        restEcomponentMockMvc
            .perform(delete(ENTITY_API_URL_ID, ecomponent.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ecomponentRepository.count();
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

    protected Ecomponent getPersistedEcomponent(Ecomponent ecomponent) {
        return ecomponentRepository.findById(ecomponent.getId()).orElseThrow();
    }

    protected void assertPersistedEcomponentToMatchAllProperties(Ecomponent expectedEcomponent) {
        assertEcomponentAllPropertiesEquals(expectedEcomponent, getPersistedEcomponent(expectedEcomponent));
    }

    protected void assertPersistedEcomponentToMatchUpdatableProperties(Ecomponent expectedEcomponent) {
        assertEcomponentAllUpdatablePropertiesEquals(expectedEcomponent, getPersistedEcomponent(expectedEcomponent));
    }
}
