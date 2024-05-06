package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.BinnenlocatieAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static nl.ritense.demo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Binnenlocatie;
import nl.ritense.demo.repository.BinnenlocatieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BinnenlocatieResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class BinnenlocatieResourceIT {

    private static final String DEFAULT_ADRES = "AAAAAAAAAA";
    private static final String UPDATED_ADRES = "BBBBBBBBBB";

    private static final String DEFAULT_BOUWJAAR = "AAAAAAAAAA";
    private static final String UPDATED_BOUWJAAR = "BBBBBBBBBB";

    private static final Boolean DEFAULT_GEMEENTELIJK = false;
    private static final Boolean UPDATED_GEMEENTELIJK = true;

    private static final BigDecimal DEFAULT_GESCHATTEKOSTENPERJAAR = new BigDecimal(1);
    private static final BigDecimal UPDATED_GESCHATTEKOSTENPERJAAR = new BigDecimal(2);

    private static final String DEFAULT_GYMZAAL = "AAAAAAAAAA";
    private static final String UPDATED_GYMZAAL = "BBBBBBBBBB";

    private static final String DEFAULT_KLOKURENONDERWIJS = "AAAAAAAAAA";
    private static final String UPDATED_KLOKURENONDERWIJS = "BBBBBBBBBB";

    private static final String DEFAULT_KLOKURENVERENIGINGEN = "AAAAAAAAAA";
    private static final String UPDATED_KLOKURENVERENIGINGEN = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATIE = "AAAAAAAAAA";
    private static final String UPDATED_LOCATIE = "BBBBBBBBBB";

    private static final String DEFAULT_ONDERHOUDSNIVEAU = "AAAAAAAAAA";
    private static final String UPDATED_ONDERHOUDSNIVEAU = "BBBBBBBBBB";

    private static final String DEFAULT_ONDERHOUDSSTATUS = "AAAAAAAAAA";
    private static final String UPDATED_ONDERHOUDSSTATUS = "BBBBBBBBBB";

    private static final String DEFAULT_SPORTHAL = "AAAAAAAAAA";
    private static final String UPDATED_SPORTHAL = "BBBBBBBBBB";

    private static final String DEFAULT_VLOEROPPERVLAKTE = "AAAAAAAAAA";
    private static final String UPDATED_VLOEROPPERVLAKTE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/binnenlocaties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BinnenlocatieRepository binnenlocatieRepository;

    @Mock
    private BinnenlocatieRepository binnenlocatieRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBinnenlocatieMockMvc;

    private Binnenlocatie binnenlocatie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Binnenlocatie createEntity(EntityManager em) {
        Binnenlocatie binnenlocatie = new Binnenlocatie()
            .adres(DEFAULT_ADRES)
            .bouwjaar(DEFAULT_BOUWJAAR)
            .gemeentelijk(DEFAULT_GEMEENTELIJK)
            .geschattekostenperjaar(DEFAULT_GESCHATTEKOSTENPERJAAR)
            .gymzaal(DEFAULT_GYMZAAL)
            .klokurenonderwijs(DEFAULT_KLOKURENONDERWIJS)
            .klokurenverenigingen(DEFAULT_KLOKURENVERENIGINGEN)
            .locatie(DEFAULT_LOCATIE)
            .onderhoudsniveau(DEFAULT_ONDERHOUDSNIVEAU)
            .onderhoudsstatus(DEFAULT_ONDERHOUDSSTATUS)
            .sporthal(DEFAULT_SPORTHAL)
            .vloeroppervlakte(DEFAULT_VLOEROPPERVLAKTE);
        return binnenlocatie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Binnenlocatie createUpdatedEntity(EntityManager em) {
        Binnenlocatie binnenlocatie = new Binnenlocatie()
            .adres(UPDATED_ADRES)
            .bouwjaar(UPDATED_BOUWJAAR)
            .gemeentelijk(UPDATED_GEMEENTELIJK)
            .geschattekostenperjaar(UPDATED_GESCHATTEKOSTENPERJAAR)
            .gymzaal(UPDATED_GYMZAAL)
            .klokurenonderwijs(UPDATED_KLOKURENONDERWIJS)
            .klokurenverenigingen(UPDATED_KLOKURENVERENIGINGEN)
            .locatie(UPDATED_LOCATIE)
            .onderhoudsniveau(UPDATED_ONDERHOUDSNIVEAU)
            .onderhoudsstatus(UPDATED_ONDERHOUDSSTATUS)
            .sporthal(UPDATED_SPORTHAL)
            .vloeroppervlakte(UPDATED_VLOEROPPERVLAKTE);
        return binnenlocatie;
    }

    @BeforeEach
    public void initTest() {
        binnenlocatie = createEntity(em);
    }

    @Test
    @Transactional
    void createBinnenlocatie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Binnenlocatie
        var returnedBinnenlocatie = om.readValue(
            restBinnenlocatieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(binnenlocatie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Binnenlocatie.class
        );

        // Validate the Binnenlocatie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBinnenlocatieUpdatableFieldsEquals(returnedBinnenlocatie, getPersistedBinnenlocatie(returnedBinnenlocatie));
    }

    @Test
    @Transactional
    void createBinnenlocatieWithExistingId() throws Exception {
        // Create the Binnenlocatie with an existing ID
        binnenlocatie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBinnenlocatieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(binnenlocatie)))
            .andExpect(status().isBadRequest());

        // Validate the Binnenlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBinnenlocaties() throws Exception {
        // Initialize the database
        binnenlocatieRepository.saveAndFlush(binnenlocatie);

        // Get all the binnenlocatieList
        restBinnenlocatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(binnenlocatie.getId().intValue())))
            .andExpect(jsonPath("$.[*].adres").value(hasItem(DEFAULT_ADRES)))
            .andExpect(jsonPath("$.[*].bouwjaar").value(hasItem(DEFAULT_BOUWJAAR)))
            .andExpect(jsonPath("$.[*].gemeentelijk").value(hasItem(DEFAULT_GEMEENTELIJK.booleanValue())))
            .andExpect(jsonPath("$.[*].geschattekostenperjaar").value(hasItem(sameNumber(DEFAULT_GESCHATTEKOSTENPERJAAR))))
            .andExpect(jsonPath("$.[*].gymzaal").value(hasItem(DEFAULT_GYMZAAL)))
            .andExpect(jsonPath("$.[*].klokurenonderwijs").value(hasItem(DEFAULT_KLOKURENONDERWIJS)))
            .andExpect(jsonPath("$.[*].klokurenverenigingen").value(hasItem(DEFAULT_KLOKURENVERENIGINGEN)))
            .andExpect(jsonPath("$.[*].locatie").value(hasItem(DEFAULT_LOCATIE)))
            .andExpect(jsonPath("$.[*].onderhoudsniveau").value(hasItem(DEFAULT_ONDERHOUDSNIVEAU)))
            .andExpect(jsonPath("$.[*].onderhoudsstatus").value(hasItem(DEFAULT_ONDERHOUDSSTATUS)))
            .andExpect(jsonPath("$.[*].sporthal").value(hasItem(DEFAULT_SPORTHAL)))
            .andExpect(jsonPath("$.[*].vloeroppervlakte").value(hasItem(DEFAULT_VLOEROPPERVLAKTE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBinnenlocatiesWithEagerRelationshipsIsEnabled() throws Exception {
        when(binnenlocatieRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBinnenlocatieMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(binnenlocatieRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBinnenlocatiesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(binnenlocatieRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBinnenlocatieMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(binnenlocatieRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getBinnenlocatie() throws Exception {
        // Initialize the database
        binnenlocatieRepository.saveAndFlush(binnenlocatie);

        // Get the binnenlocatie
        restBinnenlocatieMockMvc
            .perform(get(ENTITY_API_URL_ID, binnenlocatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(binnenlocatie.getId().intValue()))
            .andExpect(jsonPath("$.adres").value(DEFAULT_ADRES))
            .andExpect(jsonPath("$.bouwjaar").value(DEFAULT_BOUWJAAR))
            .andExpect(jsonPath("$.gemeentelijk").value(DEFAULT_GEMEENTELIJK.booleanValue()))
            .andExpect(jsonPath("$.geschattekostenperjaar").value(sameNumber(DEFAULT_GESCHATTEKOSTENPERJAAR)))
            .andExpect(jsonPath("$.gymzaal").value(DEFAULT_GYMZAAL))
            .andExpect(jsonPath("$.klokurenonderwijs").value(DEFAULT_KLOKURENONDERWIJS))
            .andExpect(jsonPath("$.klokurenverenigingen").value(DEFAULT_KLOKURENVERENIGINGEN))
            .andExpect(jsonPath("$.locatie").value(DEFAULT_LOCATIE))
            .andExpect(jsonPath("$.onderhoudsniveau").value(DEFAULT_ONDERHOUDSNIVEAU))
            .andExpect(jsonPath("$.onderhoudsstatus").value(DEFAULT_ONDERHOUDSSTATUS))
            .andExpect(jsonPath("$.sporthal").value(DEFAULT_SPORTHAL))
            .andExpect(jsonPath("$.vloeroppervlakte").value(DEFAULT_VLOEROPPERVLAKTE));
    }

    @Test
    @Transactional
    void getNonExistingBinnenlocatie() throws Exception {
        // Get the binnenlocatie
        restBinnenlocatieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBinnenlocatie() throws Exception {
        // Initialize the database
        binnenlocatieRepository.saveAndFlush(binnenlocatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the binnenlocatie
        Binnenlocatie updatedBinnenlocatie = binnenlocatieRepository.findById(binnenlocatie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBinnenlocatie are not directly saved in db
        em.detach(updatedBinnenlocatie);
        updatedBinnenlocatie
            .adres(UPDATED_ADRES)
            .bouwjaar(UPDATED_BOUWJAAR)
            .gemeentelijk(UPDATED_GEMEENTELIJK)
            .geschattekostenperjaar(UPDATED_GESCHATTEKOSTENPERJAAR)
            .gymzaal(UPDATED_GYMZAAL)
            .klokurenonderwijs(UPDATED_KLOKURENONDERWIJS)
            .klokurenverenigingen(UPDATED_KLOKURENVERENIGINGEN)
            .locatie(UPDATED_LOCATIE)
            .onderhoudsniveau(UPDATED_ONDERHOUDSNIVEAU)
            .onderhoudsstatus(UPDATED_ONDERHOUDSSTATUS)
            .sporthal(UPDATED_SPORTHAL)
            .vloeroppervlakte(UPDATED_VLOEROPPERVLAKTE);

        restBinnenlocatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBinnenlocatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBinnenlocatie))
            )
            .andExpect(status().isOk());

        // Validate the Binnenlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBinnenlocatieToMatchAllProperties(updatedBinnenlocatie);
    }

    @Test
    @Transactional
    void putNonExistingBinnenlocatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        binnenlocatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBinnenlocatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, binnenlocatie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(binnenlocatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Binnenlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBinnenlocatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        binnenlocatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBinnenlocatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(binnenlocatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Binnenlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBinnenlocatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        binnenlocatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBinnenlocatieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(binnenlocatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Binnenlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBinnenlocatieWithPatch() throws Exception {
        // Initialize the database
        binnenlocatieRepository.saveAndFlush(binnenlocatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the binnenlocatie using partial update
        Binnenlocatie partialUpdatedBinnenlocatie = new Binnenlocatie();
        partialUpdatedBinnenlocatie.setId(binnenlocatie.getId());

        partialUpdatedBinnenlocatie
            .adres(UPDATED_ADRES)
            .gemeentelijk(UPDATED_GEMEENTELIJK)
            .onderhoudsstatus(UPDATED_ONDERHOUDSSTATUS)
            .sporthal(UPDATED_SPORTHAL);

        restBinnenlocatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBinnenlocatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBinnenlocatie))
            )
            .andExpect(status().isOk());

        // Validate the Binnenlocatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBinnenlocatieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBinnenlocatie, binnenlocatie),
            getPersistedBinnenlocatie(binnenlocatie)
        );
    }

    @Test
    @Transactional
    void fullUpdateBinnenlocatieWithPatch() throws Exception {
        // Initialize the database
        binnenlocatieRepository.saveAndFlush(binnenlocatie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the binnenlocatie using partial update
        Binnenlocatie partialUpdatedBinnenlocatie = new Binnenlocatie();
        partialUpdatedBinnenlocatie.setId(binnenlocatie.getId());

        partialUpdatedBinnenlocatie
            .adres(UPDATED_ADRES)
            .bouwjaar(UPDATED_BOUWJAAR)
            .gemeentelijk(UPDATED_GEMEENTELIJK)
            .geschattekostenperjaar(UPDATED_GESCHATTEKOSTENPERJAAR)
            .gymzaal(UPDATED_GYMZAAL)
            .klokurenonderwijs(UPDATED_KLOKURENONDERWIJS)
            .klokurenverenigingen(UPDATED_KLOKURENVERENIGINGEN)
            .locatie(UPDATED_LOCATIE)
            .onderhoudsniveau(UPDATED_ONDERHOUDSNIVEAU)
            .onderhoudsstatus(UPDATED_ONDERHOUDSSTATUS)
            .sporthal(UPDATED_SPORTHAL)
            .vloeroppervlakte(UPDATED_VLOEROPPERVLAKTE);

        restBinnenlocatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBinnenlocatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBinnenlocatie))
            )
            .andExpect(status().isOk());

        // Validate the Binnenlocatie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBinnenlocatieUpdatableFieldsEquals(partialUpdatedBinnenlocatie, getPersistedBinnenlocatie(partialUpdatedBinnenlocatie));
    }

    @Test
    @Transactional
    void patchNonExistingBinnenlocatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        binnenlocatie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBinnenlocatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, binnenlocatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(binnenlocatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Binnenlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBinnenlocatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        binnenlocatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBinnenlocatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(binnenlocatie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Binnenlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBinnenlocatie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        binnenlocatie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBinnenlocatieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(binnenlocatie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Binnenlocatie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBinnenlocatie() throws Exception {
        // Initialize the database
        binnenlocatieRepository.saveAndFlush(binnenlocatie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the binnenlocatie
        restBinnenlocatieMockMvc
            .perform(delete(ENTITY_API_URL_ID, binnenlocatie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return binnenlocatieRepository.count();
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

    protected Binnenlocatie getPersistedBinnenlocatie(Binnenlocatie binnenlocatie) {
        return binnenlocatieRepository.findById(binnenlocatie.getId()).orElseThrow();
    }

    protected void assertPersistedBinnenlocatieToMatchAllProperties(Binnenlocatie expectedBinnenlocatie) {
        assertBinnenlocatieAllPropertiesEquals(expectedBinnenlocatie, getPersistedBinnenlocatie(expectedBinnenlocatie));
    }

    protected void assertPersistedBinnenlocatieToMatchUpdatableProperties(Binnenlocatie expectedBinnenlocatie) {
        assertBinnenlocatieAllUpdatablePropertiesEquals(expectedBinnenlocatie, getPersistedBinnenlocatie(expectedBinnenlocatie));
    }
}
