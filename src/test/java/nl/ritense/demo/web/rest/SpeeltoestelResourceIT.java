package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SpeeltoestelAsserts.*;
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
import nl.ritense.demo.domain.Speeltoestel;
import nl.ritense.demo.repository.SpeeltoestelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SpeeltoestelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SpeeltoestelResourceIT {

    private static final String DEFAULT_CATALOGUSPRIJS = "AAAAAAAAAA";
    private static final String UPDATED_CATALOGUSPRIJS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CERTIFICAAT = false;
    private static final Boolean UPDATED_CERTIFICAAT = true;

    private static final String DEFAULT_CERTIFICAATNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_CERTIFICAATNUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_CERTIFICERINGSINSTANTIE = "AAAAAAAAAA";
    private static final String UPDATED_CERTIFICERINGSINSTANTIE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTROLEFREQUENTIE = "AAAAAAAAAA";
    private static final String UPDATED_CONTROLEFREQUENTIE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMCERTIFICAAT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMCERTIFICAAT = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_GEMAKKELIJKTOEGANKELIJK = false;
    private static final Boolean UPDATED_GEMAKKELIJKTOEGANKELIJK = true;

    private static final String DEFAULT_INSPECTIEVOLGORDE = "AAAAAAAAAA";
    private static final String UPDATED_INSPECTIEVOLGORDE = "BBBBBBBBBB";

    private static final String DEFAULT_INSTALLATIEKOSTEN = "AAAAAAAAAA";
    private static final String UPDATED_INSTALLATIEKOSTEN = "BBBBBBBBBB";

    private static final String DEFAULT_SPEELTERREIN = "AAAAAAAAAA";
    private static final String UPDATED_SPEELTERREIN = "BBBBBBBBBB";

    private static final String DEFAULT_SPEELTOESTELTOESTELONDERDEEL = "AAAAAAAAAA";
    private static final String UPDATED_SPEELTOESTELTOESTELONDERDEEL = "BBBBBBBBBB";

    private static final String DEFAULT_TECHNISCHELEVENSDUUR = "AAAAAAAAAA";
    private static final String UPDATED_TECHNISCHELEVENSDUUR = "BBBBBBBBBB";

    private static final String DEFAULT_TOESTELCODE = "AAAAAAAAAA";
    private static final String UPDATED_TOESTELCODE = "BBBBBBBBBB";

    private static final String DEFAULT_TOESTELGROEP = "AAAAAAAAAA";
    private static final String UPDATED_TOESTELGROEP = "BBBBBBBBBB";

    private static final String DEFAULT_TOESTELNAAM = "AAAAAAAAAA";
    private static final String UPDATED_TOESTELNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPENUMMER = "AAAAAAAAAA";
    private static final String UPDATED_TYPENUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEPLUS = "AAAAAAAAAA";
    private static final String UPDATED_TYPEPLUS = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEPLUS_2 = "AAAAAAAAAA";
    private static final String UPDATED_TYPEPLUS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_VALRUIMTEHOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_VALRUIMTEHOOGTE = "BBBBBBBBBB";

    private static final String DEFAULT_VALRUIMTEOMVANG = "AAAAAAAAAA";
    private static final String UPDATED_VALRUIMTEOMVANG = "BBBBBBBBBB";

    private static final String DEFAULT_VRIJEVALHOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_VRIJEVALHOOGTE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/speeltoestels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SpeeltoestelRepository speeltoestelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSpeeltoestelMockMvc;

    private Speeltoestel speeltoestel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Speeltoestel createEntity(EntityManager em) {
        Speeltoestel speeltoestel = new Speeltoestel()
            .catalogusprijs(DEFAULT_CATALOGUSPRIJS)
            .certificaat(DEFAULT_CERTIFICAAT)
            .certificaatnummer(DEFAULT_CERTIFICAATNUMMER)
            .certificeringsinstantie(DEFAULT_CERTIFICERINGSINSTANTIE)
            .controlefrequentie(DEFAULT_CONTROLEFREQUENTIE)
            .datumcertificaat(DEFAULT_DATUMCERTIFICAAT)
            .gemakkelijktoegankelijk(DEFAULT_GEMAKKELIJKTOEGANKELIJK)
            .inspectievolgorde(DEFAULT_INSPECTIEVOLGORDE)
            .installatiekosten(DEFAULT_INSTALLATIEKOSTEN)
            .speelterrein(DEFAULT_SPEELTERREIN)
            .speeltoesteltoestelonderdeel(DEFAULT_SPEELTOESTELTOESTELONDERDEEL)
            .technischelevensduur(DEFAULT_TECHNISCHELEVENSDUUR)
            .toestelcode(DEFAULT_TOESTELCODE)
            .toestelgroep(DEFAULT_TOESTELGROEP)
            .toestelnaam(DEFAULT_TOESTELNAAM)
            .type(DEFAULT_TYPE)
            .typenummer(DEFAULT_TYPENUMMER)
            .typeplus(DEFAULT_TYPEPLUS)
            .typeplus2(DEFAULT_TYPEPLUS_2)
            .valruimtehoogte(DEFAULT_VALRUIMTEHOOGTE)
            .valruimteomvang(DEFAULT_VALRUIMTEOMVANG)
            .vrijevalhoogte(DEFAULT_VRIJEVALHOOGTE);
        return speeltoestel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Speeltoestel createUpdatedEntity(EntityManager em) {
        Speeltoestel speeltoestel = new Speeltoestel()
            .catalogusprijs(UPDATED_CATALOGUSPRIJS)
            .certificaat(UPDATED_CERTIFICAAT)
            .certificaatnummer(UPDATED_CERTIFICAATNUMMER)
            .certificeringsinstantie(UPDATED_CERTIFICERINGSINSTANTIE)
            .controlefrequentie(UPDATED_CONTROLEFREQUENTIE)
            .datumcertificaat(UPDATED_DATUMCERTIFICAAT)
            .gemakkelijktoegankelijk(UPDATED_GEMAKKELIJKTOEGANKELIJK)
            .inspectievolgorde(UPDATED_INSPECTIEVOLGORDE)
            .installatiekosten(UPDATED_INSTALLATIEKOSTEN)
            .speelterrein(UPDATED_SPEELTERREIN)
            .speeltoesteltoestelonderdeel(UPDATED_SPEELTOESTELTOESTELONDERDEEL)
            .technischelevensduur(UPDATED_TECHNISCHELEVENSDUUR)
            .toestelcode(UPDATED_TOESTELCODE)
            .toestelgroep(UPDATED_TOESTELGROEP)
            .toestelnaam(UPDATED_TOESTELNAAM)
            .type(UPDATED_TYPE)
            .typenummer(UPDATED_TYPENUMMER)
            .typeplus(UPDATED_TYPEPLUS)
            .typeplus2(UPDATED_TYPEPLUS_2)
            .valruimtehoogte(UPDATED_VALRUIMTEHOOGTE)
            .valruimteomvang(UPDATED_VALRUIMTEOMVANG)
            .vrijevalhoogte(UPDATED_VRIJEVALHOOGTE);
        return speeltoestel;
    }

    @BeforeEach
    public void initTest() {
        speeltoestel = createEntity(em);
    }

    @Test
    @Transactional
    void createSpeeltoestel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Speeltoestel
        var returnedSpeeltoestel = om.readValue(
            restSpeeltoestelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(speeltoestel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Speeltoestel.class
        );

        // Validate the Speeltoestel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSpeeltoestelUpdatableFieldsEquals(returnedSpeeltoestel, getPersistedSpeeltoestel(returnedSpeeltoestel));
    }

    @Test
    @Transactional
    void createSpeeltoestelWithExistingId() throws Exception {
        // Create the Speeltoestel with an existing ID
        speeltoestel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpeeltoestelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(speeltoestel)))
            .andExpect(status().isBadRequest());

        // Validate the Speeltoestel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSpeeltoestels() throws Exception {
        // Initialize the database
        speeltoestelRepository.saveAndFlush(speeltoestel);

        // Get all the speeltoestelList
        restSpeeltoestelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(speeltoestel.getId().intValue())))
            .andExpect(jsonPath("$.[*].catalogusprijs").value(hasItem(DEFAULT_CATALOGUSPRIJS)))
            .andExpect(jsonPath("$.[*].certificaat").value(hasItem(DEFAULT_CERTIFICAAT.booleanValue())))
            .andExpect(jsonPath("$.[*].certificaatnummer").value(hasItem(DEFAULT_CERTIFICAATNUMMER)))
            .andExpect(jsonPath("$.[*].certificeringsinstantie").value(hasItem(DEFAULT_CERTIFICERINGSINSTANTIE)))
            .andExpect(jsonPath("$.[*].controlefrequentie").value(hasItem(DEFAULT_CONTROLEFREQUENTIE)))
            .andExpect(jsonPath("$.[*].datumcertificaat").value(hasItem(DEFAULT_DATUMCERTIFICAAT.toString())))
            .andExpect(jsonPath("$.[*].gemakkelijktoegankelijk").value(hasItem(DEFAULT_GEMAKKELIJKTOEGANKELIJK.booleanValue())))
            .andExpect(jsonPath("$.[*].inspectievolgorde").value(hasItem(DEFAULT_INSPECTIEVOLGORDE)))
            .andExpect(jsonPath("$.[*].installatiekosten").value(hasItem(DEFAULT_INSTALLATIEKOSTEN)))
            .andExpect(jsonPath("$.[*].speelterrein").value(hasItem(DEFAULT_SPEELTERREIN)))
            .andExpect(jsonPath("$.[*].speeltoesteltoestelonderdeel").value(hasItem(DEFAULT_SPEELTOESTELTOESTELONDERDEEL)))
            .andExpect(jsonPath("$.[*].technischelevensduur").value(hasItem(DEFAULT_TECHNISCHELEVENSDUUR)))
            .andExpect(jsonPath("$.[*].toestelcode").value(hasItem(DEFAULT_TOESTELCODE)))
            .andExpect(jsonPath("$.[*].toestelgroep").value(hasItem(DEFAULT_TOESTELGROEP)))
            .andExpect(jsonPath("$.[*].toestelnaam").value(hasItem(DEFAULT_TOESTELNAAM)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].typenummer").value(hasItem(DEFAULT_TYPENUMMER)))
            .andExpect(jsonPath("$.[*].typeplus").value(hasItem(DEFAULT_TYPEPLUS)))
            .andExpect(jsonPath("$.[*].typeplus2").value(hasItem(DEFAULT_TYPEPLUS_2)))
            .andExpect(jsonPath("$.[*].valruimtehoogte").value(hasItem(DEFAULT_VALRUIMTEHOOGTE)))
            .andExpect(jsonPath("$.[*].valruimteomvang").value(hasItem(DEFAULT_VALRUIMTEOMVANG)))
            .andExpect(jsonPath("$.[*].vrijevalhoogte").value(hasItem(DEFAULT_VRIJEVALHOOGTE)));
    }

    @Test
    @Transactional
    void getSpeeltoestel() throws Exception {
        // Initialize the database
        speeltoestelRepository.saveAndFlush(speeltoestel);

        // Get the speeltoestel
        restSpeeltoestelMockMvc
            .perform(get(ENTITY_API_URL_ID, speeltoestel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(speeltoestel.getId().intValue()))
            .andExpect(jsonPath("$.catalogusprijs").value(DEFAULT_CATALOGUSPRIJS))
            .andExpect(jsonPath("$.certificaat").value(DEFAULT_CERTIFICAAT.booleanValue()))
            .andExpect(jsonPath("$.certificaatnummer").value(DEFAULT_CERTIFICAATNUMMER))
            .andExpect(jsonPath("$.certificeringsinstantie").value(DEFAULT_CERTIFICERINGSINSTANTIE))
            .andExpect(jsonPath("$.controlefrequentie").value(DEFAULT_CONTROLEFREQUENTIE))
            .andExpect(jsonPath("$.datumcertificaat").value(DEFAULT_DATUMCERTIFICAAT.toString()))
            .andExpect(jsonPath("$.gemakkelijktoegankelijk").value(DEFAULT_GEMAKKELIJKTOEGANKELIJK.booleanValue()))
            .andExpect(jsonPath("$.inspectievolgorde").value(DEFAULT_INSPECTIEVOLGORDE))
            .andExpect(jsonPath("$.installatiekosten").value(DEFAULT_INSTALLATIEKOSTEN))
            .andExpect(jsonPath("$.speelterrein").value(DEFAULT_SPEELTERREIN))
            .andExpect(jsonPath("$.speeltoesteltoestelonderdeel").value(DEFAULT_SPEELTOESTELTOESTELONDERDEEL))
            .andExpect(jsonPath("$.technischelevensduur").value(DEFAULT_TECHNISCHELEVENSDUUR))
            .andExpect(jsonPath("$.toestelcode").value(DEFAULT_TOESTELCODE))
            .andExpect(jsonPath("$.toestelgroep").value(DEFAULT_TOESTELGROEP))
            .andExpect(jsonPath("$.toestelnaam").value(DEFAULT_TOESTELNAAM))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.typenummer").value(DEFAULT_TYPENUMMER))
            .andExpect(jsonPath("$.typeplus").value(DEFAULT_TYPEPLUS))
            .andExpect(jsonPath("$.typeplus2").value(DEFAULT_TYPEPLUS_2))
            .andExpect(jsonPath("$.valruimtehoogte").value(DEFAULT_VALRUIMTEHOOGTE))
            .andExpect(jsonPath("$.valruimteomvang").value(DEFAULT_VALRUIMTEOMVANG))
            .andExpect(jsonPath("$.vrijevalhoogte").value(DEFAULT_VRIJEVALHOOGTE));
    }

    @Test
    @Transactional
    void getNonExistingSpeeltoestel() throws Exception {
        // Get the speeltoestel
        restSpeeltoestelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSpeeltoestel() throws Exception {
        // Initialize the database
        speeltoestelRepository.saveAndFlush(speeltoestel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the speeltoestel
        Speeltoestel updatedSpeeltoestel = speeltoestelRepository.findById(speeltoestel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSpeeltoestel are not directly saved in db
        em.detach(updatedSpeeltoestel);
        updatedSpeeltoestel
            .catalogusprijs(UPDATED_CATALOGUSPRIJS)
            .certificaat(UPDATED_CERTIFICAAT)
            .certificaatnummer(UPDATED_CERTIFICAATNUMMER)
            .certificeringsinstantie(UPDATED_CERTIFICERINGSINSTANTIE)
            .controlefrequentie(UPDATED_CONTROLEFREQUENTIE)
            .datumcertificaat(UPDATED_DATUMCERTIFICAAT)
            .gemakkelijktoegankelijk(UPDATED_GEMAKKELIJKTOEGANKELIJK)
            .inspectievolgorde(UPDATED_INSPECTIEVOLGORDE)
            .installatiekosten(UPDATED_INSTALLATIEKOSTEN)
            .speelterrein(UPDATED_SPEELTERREIN)
            .speeltoesteltoestelonderdeel(UPDATED_SPEELTOESTELTOESTELONDERDEEL)
            .technischelevensduur(UPDATED_TECHNISCHELEVENSDUUR)
            .toestelcode(UPDATED_TOESTELCODE)
            .toestelgroep(UPDATED_TOESTELGROEP)
            .toestelnaam(UPDATED_TOESTELNAAM)
            .type(UPDATED_TYPE)
            .typenummer(UPDATED_TYPENUMMER)
            .typeplus(UPDATED_TYPEPLUS)
            .typeplus2(UPDATED_TYPEPLUS_2)
            .valruimtehoogte(UPDATED_VALRUIMTEHOOGTE)
            .valruimteomvang(UPDATED_VALRUIMTEOMVANG)
            .vrijevalhoogte(UPDATED_VRIJEVALHOOGTE);

        restSpeeltoestelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSpeeltoestel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSpeeltoestel))
            )
            .andExpect(status().isOk());

        // Validate the Speeltoestel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSpeeltoestelToMatchAllProperties(updatedSpeeltoestel);
    }

    @Test
    @Transactional
    void putNonExistingSpeeltoestel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        speeltoestel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpeeltoestelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, speeltoestel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(speeltoestel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Speeltoestel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSpeeltoestel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        speeltoestel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpeeltoestelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(speeltoestel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Speeltoestel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSpeeltoestel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        speeltoestel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpeeltoestelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(speeltoestel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Speeltoestel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSpeeltoestelWithPatch() throws Exception {
        // Initialize the database
        speeltoestelRepository.saveAndFlush(speeltoestel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the speeltoestel using partial update
        Speeltoestel partialUpdatedSpeeltoestel = new Speeltoestel();
        partialUpdatedSpeeltoestel.setId(speeltoestel.getId());

        partialUpdatedSpeeltoestel
            .certificaatnummer(UPDATED_CERTIFICAATNUMMER)
            .certificeringsinstantie(UPDATED_CERTIFICERINGSINSTANTIE)
            .controlefrequentie(UPDATED_CONTROLEFREQUENTIE)
            .gemakkelijktoegankelijk(UPDATED_GEMAKKELIJKTOEGANKELIJK)
            .technischelevensduur(UPDATED_TECHNISCHELEVENSDUUR)
            .toestelgroep(UPDATED_TOESTELGROEP)
            .toestelnaam(UPDATED_TOESTELNAAM)
            .type(UPDATED_TYPE)
            .typeplus(UPDATED_TYPEPLUS)
            .vrijevalhoogte(UPDATED_VRIJEVALHOOGTE);

        restSpeeltoestelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpeeltoestel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSpeeltoestel))
            )
            .andExpect(status().isOk());

        // Validate the Speeltoestel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSpeeltoestelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSpeeltoestel, speeltoestel),
            getPersistedSpeeltoestel(speeltoestel)
        );
    }

    @Test
    @Transactional
    void fullUpdateSpeeltoestelWithPatch() throws Exception {
        // Initialize the database
        speeltoestelRepository.saveAndFlush(speeltoestel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the speeltoestel using partial update
        Speeltoestel partialUpdatedSpeeltoestel = new Speeltoestel();
        partialUpdatedSpeeltoestel.setId(speeltoestel.getId());

        partialUpdatedSpeeltoestel
            .catalogusprijs(UPDATED_CATALOGUSPRIJS)
            .certificaat(UPDATED_CERTIFICAAT)
            .certificaatnummer(UPDATED_CERTIFICAATNUMMER)
            .certificeringsinstantie(UPDATED_CERTIFICERINGSINSTANTIE)
            .controlefrequentie(UPDATED_CONTROLEFREQUENTIE)
            .datumcertificaat(UPDATED_DATUMCERTIFICAAT)
            .gemakkelijktoegankelijk(UPDATED_GEMAKKELIJKTOEGANKELIJK)
            .inspectievolgorde(UPDATED_INSPECTIEVOLGORDE)
            .installatiekosten(UPDATED_INSTALLATIEKOSTEN)
            .speelterrein(UPDATED_SPEELTERREIN)
            .speeltoesteltoestelonderdeel(UPDATED_SPEELTOESTELTOESTELONDERDEEL)
            .technischelevensduur(UPDATED_TECHNISCHELEVENSDUUR)
            .toestelcode(UPDATED_TOESTELCODE)
            .toestelgroep(UPDATED_TOESTELGROEP)
            .toestelnaam(UPDATED_TOESTELNAAM)
            .type(UPDATED_TYPE)
            .typenummer(UPDATED_TYPENUMMER)
            .typeplus(UPDATED_TYPEPLUS)
            .typeplus2(UPDATED_TYPEPLUS_2)
            .valruimtehoogte(UPDATED_VALRUIMTEHOOGTE)
            .valruimteomvang(UPDATED_VALRUIMTEOMVANG)
            .vrijevalhoogte(UPDATED_VRIJEVALHOOGTE);

        restSpeeltoestelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpeeltoestel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSpeeltoestel))
            )
            .andExpect(status().isOk());

        // Validate the Speeltoestel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSpeeltoestelUpdatableFieldsEquals(partialUpdatedSpeeltoestel, getPersistedSpeeltoestel(partialUpdatedSpeeltoestel));
    }

    @Test
    @Transactional
    void patchNonExistingSpeeltoestel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        speeltoestel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpeeltoestelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, speeltoestel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(speeltoestel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Speeltoestel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSpeeltoestel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        speeltoestel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpeeltoestelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(speeltoestel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Speeltoestel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSpeeltoestel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        speeltoestel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpeeltoestelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(speeltoestel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Speeltoestel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSpeeltoestel() throws Exception {
        // Initialize the database
        speeltoestelRepository.saveAndFlush(speeltoestel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the speeltoestel
        restSpeeltoestelMockMvc
            .perform(delete(ENTITY_API_URL_ID, speeltoestel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return speeltoestelRepository.count();
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

    protected Speeltoestel getPersistedSpeeltoestel(Speeltoestel speeltoestel) {
        return speeltoestelRepository.findById(speeltoestel.getId()).orElseThrow();
    }

    protected void assertPersistedSpeeltoestelToMatchAllProperties(Speeltoestel expectedSpeeltoestel) {
        assertSpeeltoestelAllPropertiesEquals(expectedSpeeltoestel, getPersistedSpeeltoestel(expectedSpeeltoestel));
    }

    protected void assertPersistedSpeeltoestelToMatchUpdatableProperties(Speeltoestel expectedSpeeltoestel) {
        assertSpeeltoestelAllUpdatablePropertiesEquals(expectedSpeeltoestel, getPersistedSpeeltoestel(expectedSpeeltoestel));
    }
}
