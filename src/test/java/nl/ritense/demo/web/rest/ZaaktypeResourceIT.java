package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ZaaktypeAsserts.*;
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
import nl.ritense.demo.domain.Zaaktype;
import nl.ritense.demo.repository.ZaaktypeRepository;
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
 * Integration tests for the {@link ZaaktypeResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@AutoConfigureMockMvc
@WithMockUser
class ZaaktypeResourceIT {

    private static final String DEFAULT_ARCHIEFCODE = "AAAAAAAAAA";
    private static final String UPDATED_ARCHIEFCODE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMBEGINGELDIGHEIDZAAKTYPE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMBEGINGELDIGHEIDZAAKTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMEINDEGELDIGHEIDZAAKTYPE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMEINDEGELDIGHEIDZAAKTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DOORLOOPTIJDBEHANDELING = "AAAAAAAAAA";
    private static final String UPDATED_DOORLOOPTIJDBEHANDELING = "BBBBBBBBBB";

    private static final String DEFAULT_INDICATIEPUBLICATIE = "AAAAAAAAAA";
    private static final String UPDATED_INDICATIEPUBLICATIE = "BBBBBBBBBB";

    private static final String DEFAULT_PUBLICATIETEKST = "AAAAAAAAAA";
    private static final String UPDATED_PUBLICATIETEKST = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICENORMBEHANDELING = "AAAAAAAAAA";
    private static final String UPDATED_SERVICENORMBEHANDELING = "BBBBBBBBBB";

    private static final String DEFAULT_TREFWOORD = "AAAAAAAAAA";
    private static final String UPDATED_TREFWOORD = "BBBBBBBBBB";

    private static final String DEFAULT_VERTROUWELIJKAANDUIDING = "AAAAAAAAAA";
    private static final String UPDATED_VERTROUWELIJKAANDUIDING = "BBBBBBBBBB";

    private static final String DEFAULT_ZAAKCATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_ZAAKCATEGORIE = "BBBBBBBBBB";

    private static final String DEFAULT_ZAAKTYPEOMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_ZAAKTYPEOMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_ZAAKTYPEOMSCHRIJVINGGENERIEK = "AAAAAAAAAA";
    private static final String UPDATED_ZAAKTYPEOMSCHRIJVINGGENERIEK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/zaaktypes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ZaaktypeRepository zaaktypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restZaaktypeMockMvc;

    private Zaaktype zaaktype;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Zaaktype createEntity(EntityManager em) {
        Zaaktype zaaktype = new Zaaktype()
            .archiefcode(DEFAULT_ARCHIEFCODE)
            .datumbegingeldigheidzaaktype(DEFAULT_DATUMBEGINGELDIGHEIDZAAKTYPE)
            .datumeindegeldigheidzaaktype(DEFAULT_DATUMEINDEGELDIGHEIDZAAKTYPE)
            .doorlooptijdbehandeling(DEFAULT_DOORLOOPTIJDBEHANDELING)
            .indicatiepublicatie(DEFAULT_INDICATIEPUBLICATIE)
            .publicatietekst(DEFAULT_PUBLICATIETEKST)
            .servicenormbehandeling(DEFAULT_SERVICENORMBEHANDELING)
            .trefwoord(DEFAULT_TREFWOORD)
            .vertrouwelijkaanduiding(DEFAULT_VERTROUWELIJKAANDUIDING)
            .zaakcategorie(DEFAULT_ZAAKCATEGORIE)
            .zaaktypeomschrijving(DEFAULT_ZAAKTYPEOMSCHRIJVING)
            .zaaktypeomschrijvinggeneriek(DEFAULT_ZAAKTYPEOMSCHRIJVINGGENERIEK);
        return zaaktype;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Zaaktype createUpdatedEntity(EntityManager em) {
        Zaaktype zaaktype = new Zaaktype()
            .archiefcode(UPDATED_ARCHIEFCODE)
            .datumbegingeldigheidzaaktype(UPDATED_DATUMBEGINGELDIGHEIDZAAKTYPE)
            .datumeindegeldigheidzaaktype(UPDATED_DATUMEINDEGELDIGHEIDZAAKTYPE)
            .doorlooptijdbehandeling(UPDATED_DOORLOOPTIJDBEHANDELING)
            .indicatiepublicatie(UPDATED_INDICATIEPUBLICATIE)
            .publicatietekst(UPDATED_PUBLICATIETEKST)
            .servicenormbehandeling(UPDATED_SERVICENORMBEHANDELING)
            .trefwoord(UPDATED_TREFWOORD)
            .vertrouwelijkaanduiding(UPDATED_VERTROUWELIJKAANDUIDING)
            .zaakcategorie(UPDATED_ZAAKCATEGORIE)
            .zaaktypeomschrijving(UPDATED_ZAAKTYPEOMSCHRIJVING)
            .zaaktypeomschrijvinggeneriek(UPDATED_ZAAKTYPEOMSCHRIJVINGGENERIEK);
        return zaaktype;
    }

    @BeforeEach
    public void initTest() {
        zaaktype = createEntity(em);
    }

    @Test
    @Transactional
    void createZaaktype() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Zaaktype
        var returnedZaaktype = om.readValue(
            restZaaktypeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zaaktype)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Zaaktype.class
        );

        // Validate the Zaaktype in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertZaaktypeUpdatableFieldsEquals(returnedZaaktype, getPersistedZaaktype(returnedZaaktype));
    }

    @Test
    @Transactional
    void createZaaktypeWithExistingId() throws Exception {
        // Create the Zaaktype with an existing ID
        zaaktype.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restZaaktypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zaaktype)))
            .andExpect(status().isBadRequest());

        // Validate the Zaaktype in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllZaaktypes() throws Exception {
        // Initialize the database
        zaaktypeRepository.saveAndFlush(zaaktype);

        // Get all the zaaktypeList
        restZaaktypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zaaktype.getId().intValue())))
            .andExpect(jsonPath("$.[*].archiefcode").value(hasItem(DEFAULT_ARCHIEFCODE)))
            .andExpect(jsonPath("$.[*].datumbegingeldigheidzaaktype").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEIDZAAKTYPE)))
            .andExpect(jsonPath("$.[*].datumeindegeldigheidzaaktype").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEIDZAAKTYPE)))
            .andExpect(jsonPath("$.[*].doorlooptijdbehandeling").value(hasItem(DEFAULT_DOORLOOPTIJDBEHANDELING)))
            .andExpect(jsonPath("$.[*].indicatiepublicatie").value(hasItem(DEFAULT_INDICATIEPUBLICATIE)))
            .andExpect(jsonPath("$.[*].publicatietekst").value(hasItem(DEFAULT_PUBLICATIETEKST)))
            .andExpect(jsonPath("$.[*].servicenormbehandeling").value(hasItem(DEFAULT_SERVICENORMBEHANDELING)))
            .andExpect(jsonPath("$.[*].trefwoord").value(hasItem(DEFAULT_TREFWOORD)))
            .andExpect(jsonPath("$.[*].vertrouwelijkaanduiding").value(hasItem(DEFAULT_VERTROUWELIJKAANDUIDING)))
            .andExpect(jsonPath("$.[*].zaakcategorie").value(hasItem(DEFAULT_ZAAKCATEGORIE)))
            .andExpect(jsonPath("$.[*].zaaktypeomschrijving").value(hasItem(DEFAULT_ZAAKTYPEOMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].zaaktypeomschrijvinggeneriek").value(hasItem(DEFAULT_ZAAKTYPEOMSCHRIJVINGGENERIEK)));
    }

    @Test
    @Transactional
    void getZaaktype() throws Exception {
        // Initialize the database
        zaaktypeRepository.saveAndFlush(zaaktype);

        // Get the zaaktype
        restZaaktypeMockMvc
            .perform(get(ENTITY_API_URL_ID, zaaktype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(zaaktype.getId().intValue()))
            .andExpect(jsonPath("$.archiefcode").value(DEFAULT_ARCHIEFCODE))
            .andExpect(jsonPath("$.datumbegingeldigheidzaaktype").value(DEFAULT_DATUMBEGINGELDIGHEIDZAAKTYPE))
            .andExpect(jsonPath("$.datumeindegeldigheidzaaktype").value(DEFAULT_DATUMEINDEGELDIGHEIDZAAKTYPE))
            .andExpect(jsonPath("$.doorlooptijdbehandeling").value(DEFAULT_DOORLOOPTIJDBEHANDELING))
            .andExpect(jsonPath("$.indicatiepublicatie").value(DEFAULT_INDICATIEPUBLICATIE))
            .andExpect(jsonPath("$.publicatietekst").value(DEFAULT_PUBLICATIETEKST))
            .andExpect(jsonPath("$.servicenormbehandeling").value(DEFAULT_SERVICENORMBEHANDELING))
            .andExpect(jsonPath("$.trefwoord").value(DEFAULT_TREFWOORD))
            .andExpect(jsonPath("$.vertrouwelijkaanduiding").value(DEFAULT_VERTROUWELIJKAANDUIDING))
            .andExpect(jsonPath("$.zaakcategorie").value(DEFAULT_ZAAKCATEGORIE))
            .andExpect(jsonPath("$.zaaktypeomschrijving").value(DEFAULT_ZAAKTYPEOMSCHRIJVING))
            .andExpect(jsonPath("$.zaaktypeomschrijvinggeneriek").value(DEFAULT_ZAAKTYPEOMSCHRIJVINGGENERIEK));
    }

    @Test
    @Transactional
    void getNonExistingZaaktype() throws Exception {
        // Get the zaaktype
        restZaaktypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingZaaktype() throws Exception {
        // Initialize the database
        zaaktypeRepository.saveAndFlush(zaaktype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the zaaktype
        Zaaktype updatedZaaktype = zaaktypeRepository.findById(zaaktype.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedZaaktype are not directly saved in db
        em.detach(updatedZaaktype);
        updatedZaaktype
            .archiefcode(UPDATED_ARCHIEFCODE)
            .datumbegingeldigheidzaaktype(UPDATED_DATUMBEGINGELDIGHEIDZAAKTYPE)
            .datumeindegeldigheidzaaktype(UPDATED_DATUMEINDEGELDIGHEIDZAAKTYPE)
            .doorlooptijdbehandeling(UPDATED_DOORLOOPTIJDBEHANDELING)
            .indicatiepublicatie(UPDATED_INDICATIEPUBLICATIE)
            .publicatietekst(UPDATED_PUBLICATIETEKST)
            .servicenormbehandeling(UPDATED_SERVICENORMBEHANDELING)
            .trefwoord(UPDATED_TREFWOORD)
            .vertrouwelijkaanduiding(UPDATED_VERTROUWELIJKAANDUIDING)
            .zaakcategorie(UPDATED_ZAAKCATEGORIE)
            .zaaktypeomschrijving(UPDATED_ZAAKTYPEOMSCHRIJVING)
            .zaaktypeomschrijvinggeneriek(UPDATED_ZAAKTYPEOMSCHRIJVINGGENERIEK);

        restZaaktypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedZaaktype.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedZaaktype))
            )
            .andExpect(status().isOk());

        // Validate the Zaaktype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedZaaktypeToMatchAllProperties(updatedZaaktype);
    }

    @Test
    @Transactional
    void putNonExistingZaaktype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zaaktype.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZaaktypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, zaaktype.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zaaktype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zaaktype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchZaaktype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zaaktype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZaaktypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(zaaktype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zaaktype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamZaaktype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zaaktype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZaaktypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zaaktype)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Zaaktype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateZaaktypeWithPatch() throws Exception {
        // Initialize the database
        zaaktypeRepository.saveAndFlush(zaaktype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the zaaktype using partial update
        Zaaktype partialUpdatedZaaktype = new Zaaktype();
        partialUpdatedZaaktype.setId(zaaktype.getId());

        partialUpdatedZaaktype
            .archiefcode(UPDATED_ARCHIEFCODE)
            .datumbegingeldigheidzaaktype(UPDATED_DATUMBEGINGELDIGHEIDZAAKTYPE)
            .indicatiepublicatie(UPDATED_INDICATIEPUBLICATIE)
            .publicatietekst(UPDATED_PUBLICATIETEKST)
            .zaakcategorie(UPDATED_ZAAKCATEGORIE)
            .zaaktypeomschrijving(UPDATED_ZAAKTYPEOMSCHRIJVING)
            .zaaktypeomschrijvinggeneriek(UPDATED_ZAAKTYPEOMSCHRIJVINGGENERIEK);

        restZaaktypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedZaaktype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedZaaktype))
            )
            .andExpect(status().isOk());

        // Validate the Zaaktype in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertZaaktypeUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedZaaktype, zaaktype), getPersistedZaaktype(zaaktype));
    }

    @Test
    @Transactional
    void fullUpdateZaaktypeWithPatch() throws Exception {
        // Initialize the database
        zaaktypeRepository.saveAndFlush(zaaktype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the zaaktype using partial update
        Zaaktype partialUpdatedZaaktype = new Zaaktype();
        partialUpdatedZaaktype.setId(zaaktype.getId());

        partialUpdatedZaaktype
            .archiefcode(UPDATED_ARCHIEFCODE)
            .datumbegingeldigheidzaaktype(UPDATED_DATUMBEGINGELDIGHEIDZAAKTYPE)
            .datumeindegeldigheidzaaktype(UPDATED_DATUMEINDEGELDIGHEIDZAAKTYPE)
            .doorlooptijdbehandeling(UPDATED_DOORLOOPTIJDBEHANDELING)
            .indicatiepublicatie(UPDATED_INDICATIEPUBLICATIE)
            .publicatietekst(UPDATED_PUBLICATIETEKST)
            .servicenormbehandeling(UPDATED_SERVICENORMBEHANDELING)
            .trefwoord(UPDATED_TREFWOORD)
            .vertrouwelijkaanduiding(UPDATED_VERTROUWELIJKAANDUIDING)
            .zaakcategorie(UPDATED_ZAAKCATEGORIE)
            .zaaktypeomschrijving(UPDATED_ZAAKTYPEOMSCHRIJVING)
            .zaaktypeomschrijvinggeneriek(UPDATED_ZAAKTYPEOMSCHRIJVINGGENERIEK);

        restZaaktypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedZaaktype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedZaaktype))
            )
            .andExpect(status().isOk());

        // Validate the Zaaktype in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertZaaktypeUpdatableFieldsEquals(partialUpdatedZaaktype, getPersistedZaaktype(partialUpdatedZaaktype));
    }

    @Test
    @Transactional
    void patchNonExistingZaaktype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zaaktype.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZaaktypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, zaaktype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(zaaktype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zaaktype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchZaaktype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zaaktype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZaaktypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(zaaktype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zaaktype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamZaaktype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zaaktype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZaaktypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(zaaktype)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Zaaktype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteZaaktype() throws Exception {
        // Initialize the database
        zaaktypeRepository.saveAndFlush(zaaktype);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the zaaktype
        restZaaktypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, zaaktype.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return zaaktypeRepository.count();
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

    protected Zaaktype getPersistedZaaktype(Zaaktype zaaktype) {
        return zaaktypeRepository.findById(zaaktype.getId()).orElseThrow();
    }

    protected void assertPersistedZaaktypeToMatchAllProperties(Zaaktype expectedZaaktype) {
        assertZaaktypeAllPropertiesEquals(expectedZaaktype, getPersistedZaaktype(expectedZaaktype));
    }

    protected void assertPersistedZaaktypeToMatchUpdatableProperties(Zaaktype expectedZaaktype) {
        assertZaaktypeAllUpdatablePropertiesEquals(expectedZaaktype, getPersistedZaaktype(expectedZaaktype));
    }
}
