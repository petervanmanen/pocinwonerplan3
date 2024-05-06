package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.RegeltekstAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Regeltekst;
import nl.ritense.demo.repository.RegeltekstRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
 * Integration tests for the {@link RegeltekstResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RegeltekstResourceIT {

    private static final String DEFAULT_IDENTIFICATIE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATIE = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_TEKST = "AAAAAAAAAA";
    private static final String UPDATED_TEKST = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/regelteksts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RegeltekstRepository regeltekstRepository;

    @Mock
    private RegeltekstRepository regeltekstRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRegeltekstMockMvc;

    private Regeltekst regeltekst;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Regeltekst createEntity(EntityManager em) {
        Regeltekst regeltekst = new Regeltekst()
            .identificatie(DEFAULT_IDENTIFICATIE)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .tekst(DEFAULT_TEKST);
        return regeltekst;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Regeltekst createUpdatedEntity(EntityManager em) {
        Regeltekst regeltekst = new Regeltekst()
            .identificatie(UPDATED_IDENTIFICATIE)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .tekst(UPDATED_TEKST);
        return regeltekst;
    }

    @BeforeEach
    public void initTest() {
        regeltekst = createEntity(em);
    }

    @Test
    @Transactional
    void createRegeltekst() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Regeltekst
        var returnedRegeltekst = om.readValue(
            restRegeltekstMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(regeltekst)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Regeltekst.class
        );

        // Validate the Regeltekst in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRegeltekstUpdatableFieldsEquals(returnedRegeltekst, getPersistedRegeltekst(returnedRegeltekst));
    }

    @Test
    @Transactional
    void createRegeltekstWithExistingId() throws Exception {
        // Create the Regeltekst with an existing ID
        regeltekst.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegeltekstMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(regeltekst)))
            .andExpect(status().isBadRequest());

        // Validate the Regeltekst in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRegelteksts() throws Exception {
        // Initialize the database
        regeltekstRepository.saveAndFlush(regeltekst);

        // Get all the regeltekstList
        restRegeltekstMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regeltekst.getId().intValue())))
            .andExpect(jsonPath("$.[*].identificatie").value(hasItem(DEFAULT_IDENTIFICATIE)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].tekst").value(hasItem(DEFAULT_TEKST)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRegeltekstsWithEagerRelationshipsIsEnabled() throws Exception {
        when(regeltekstRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRegeltekstMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(regeltekstRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRegeltekstsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(regeltekstRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRegeltekstMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(regeltekstRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getRegeltekst() throws Exception {
        // Initialize the database
        regeltekstRepository.saveAndFlush(regeltekst);

        // Get the regeltekst
        restRegeltekstMockMvc
            .perform(get(ENTITY_API_URL_ID, regeltekst.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(regeltekst.getId().intValue()))
            .andExpect(jsonPath("$.identificatie").value(DEFAULT_IDENTIFICATIE))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.tekst").value(DEFAULT_TEKST));
    }

    @Test
    @Transactional
    void getNonExistingRegeltekst() throws Exception {
        // Get the regeltekst
        restRegeltekstMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRegeltekst() throws Exception {
        // Initialize the database
        regeltekstRepository.saveAndFlush(regeltekst);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the regeltekst
        Regeltekst updatedRegeltekst = regeltekstRepository.findById(regeltekst.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRegeltekst are not directly saved in db
        em.detach(updatedRegeltekst);
        updatedRegeltekst.identificatie(UPDATED_IDENTIFICATIE).omschrijving(UPDATED_OMSCHRIJVING).tekst(UPDATED_TEKST);

        restRegeltekstMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRegeltekst.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRegeltekst))
            )
            .andExpect(status().isOk());

        // Validate the Regeltekst in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRegeltekstToMatchAllProperties(updatedRegeltekst);
    }

    @Test
    @Transactional
    void putNonExistingRegeltekst() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        regeltekst.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegeltekstMockMvc
            .perform(
                put(ENTITY_API_URL_ID, regeltekst.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(regeltekst))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regeltekst in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRegeltekst() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        regeltekst.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegeltekstMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(regeltekst))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regeltekst in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRegeltekst() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        regeltekst.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegeltekstMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(regeltekst)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Regeltekst in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRegeltekstWithPatch() throws Exception {
        // Initialize the database
        regeltekstRepository.saveAndFlush(regeltekst);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the regeltekst using partial update
        Regeltekst partialUpdatedRegeltekst = new Regeltekst();
        partialUpdatedRegeltekst.setId(regeltekst.getId());

        partialUpdatedRegeltekst.identificatie(UPDATED_IDENTIFICATIE).tekst(UPDATED_TEKST);

        restRegeltekstMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegeltekst.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRegeltekst))
            )
            .andExpect(status().isOk());

        // Validate the Regeltekst in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRegeltekstUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRegeltekst, regeltekst),
            getPersistedRegeltekst(regeltekst)
        );
    }

    @Test
    @Transactional
    void fullUpdateRegeltekstWithPatch() throws Exception {
        // Initialize the database
        regeltekstRepository.saveAndFlush(regeltekst);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the regeltekst using partial update
        Regeltekst partialUpdatedRegeltekst = new Regeltekst();
        partialUpdatedRegeltekst.setId(regeltekst.getId());

        partialUpdatedRegeltekst.identificatie(UPDATED_IDENTIFICATIE).omschrijving(UPDATED_OMSCHRIJVING).tekst(UPDATED_TEKST);

        restRegeltekstMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegeltekst.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRegeltekst))
            )
            .andExpect(status().isOk());

        // Validate the Regeltekst in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRegeltekstUpdatableFieldsEquals(partialUpdatedRegeltekst, getPersistedRegeltekst(partialUpdatedRegeltekst));
    }

    @Test
    @Transactional
    void patchNonExistingRegeltekst() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        regeltekst.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegeltekstMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, regeltekst.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(regeltekst))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regeltekst in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRegeltekst() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        regeltekst.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegeltekstMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(regeltekst))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regeltekst in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRegeltekst() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        regeltekst.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegeltekstMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(regeltekst)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Regeltekst in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRegeltekst() throws Exception {
        // Initialize the database
        regeltekstRepository.saveAndFlush(regeltekst);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the regeltekst
        restRegeltekstMockMvc
            .perform(delete(ENTITY_API_URL_ID, regeltekst.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return regeltekstRepository.count();
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

    protected Regeltekst getPersistedRegeltekst(Regeltekst regeltekst) {
        return regeltekstRepository.findById(regeltekst.getId()).orElseThrow();
    }

    protected void assertPersistedRegeltekstToMatchAllProperties(Regeltekst expectedRegeltekst) {
        assertRegeltekstAllPropertiesEquals(expectedRegeltekst, getPersistedRegeltekst(expectedRegeltekst));
    }

    protected void assertPersistedRegeltekstToMatchUpdatableProperties(Regeltekst expectedRegeltekst) {
        assertRegeltekstAllUpdatablePropertiesEquals(expectedRegeltekst, getPersistedRegeltekst(expectedRegeltekst));
    }
}
