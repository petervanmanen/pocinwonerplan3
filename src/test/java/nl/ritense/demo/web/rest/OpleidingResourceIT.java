package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.OpleidingAsserts.*;
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
import nl.ritense.demo.domain.Opleiding;
import nl.ritense.demo.repository.OpleidingRepository;
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
 * Integration tests for the {@link OpleidingResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class OpleidingResourceIT {

    private static final String DEFAULT_INSTITUUT = "AAAAAAAAAA";
    private static final String UPDATED_INSTITUUT = "BBBBBBBBBB";

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_PRIJS = "AAAAAAAAAA";
    private static final String UPDATED_PRIJS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/opleidings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OpleidingRepository opleidingRepository;

    @Mock
    private OpleidingRepository opleidingRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOpleidingMockMvc;

    private Opleiding opleiding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Opleiding createEntity(EntityManager em) {
        Opleiding opleiding = new Opleiding()
            .instituut(DEFAULT_INSTITUUT)
            .naam(DEFAULT_NAAM)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .prijs(DEFAULT_PRIJS);
        return opleiding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Opleiding createUpdatedEntity(EntityManager em) {
        Opleiding opleiding = new Opleiding()
            .instituut(UPDATED_INSTITUUT)
            .naam(UPDATED_NAAM)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .prijs(UPDATED_PRIJS);
        return opleiding;
    }

    @BeforeEach
    public void initTest() {
        opleiding = createEntity(em);
    }

    @Test
    @Transactional
    void createOpleiding() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Opleiding
        var returnedOpleiding = om.readValue(
            restOpleidingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(opleiding)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Opleiding.class
        );

        // Validate the Opleiding in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOpleidingUpdatableFieldsEquals(returnedOpleiding, getPersistedOpleiding(returnedOpleiding));
    }

    @Test
    @Transactional
    void createOpleidingWithExistingId() throws Exception {
        // Create the Opleiding with an existing ID
        opleiding.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOpleidingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(opleiding)))
            .andExpect(status().isBadRequest());

        // Validate the Opleiding in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOpleidings() throws Exception {
        // Initialize the database
        opleidingRepository.saveAndFlush(opleiding);

        // Get all the opleidingList
        restOpleidingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(opleiding.getId().intValue())))
            .andExpect(jsonPath("$.[*].instituut").value(hasItem(DEFAULT_INSTITUUT)))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].prijs").value(hasItem(DEFAULT_PRIJS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllOpleidingsWithEagerRelationshipsIsEnabled() throws Exception {
        when(opleidingRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOpleidingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(opleidingRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllOpleidingsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(opleidingRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOpleidingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(opleidingRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getOpleiding() throws Exception {
        // Initialize the database
        opleidingRepository.saveAndFlush(opleiding);

        // Get the opleiding
        restOpleidingMockMvc
            .perform(get(ENTITY_API_URL_ID, opleiding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(opleiding.getId().intValue()))
            .andExpect(jsonPath("$.instituut").value(DEFAULT_INSTITUUT))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.prijs").value(DEFAULT_PRIJS));
    }

    @Test
    @Transactional
    void getNonExistingOpleiding() throws Exception {
        // Get the opleiding
        restOpleidingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOpleiding() throws Exception {
        // Initialize the database
        opleidingRepository.saveAndFlush(opleiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the opleiding
        Opleiding updatedOpleiding = opleidingRepository.findById(opleiding.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOpleiding are not directly saved in db
        em.detach(updatedOpleiding);
        updatedOpleiding.instituut(UPDATED_INSTITUUT).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING).prijs(UPDATED_PRIJS);

        restOpleidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOpleiding.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOpleiding))
            )
            .andExpect(status().isOk());

        // Validate the Opleiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOpleidingToMatchAllProperties(updatedOpleiding);
    }

    @Test
    @Transactional
    void putNonExistingOpleiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opleiding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpleidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, opleiding.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(opleiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opleiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOpleiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opleiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpleidingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(opleiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opleiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOpleiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opleiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpleidingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(opleiding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Opleiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOpleidingWithPatch() throws Exception {
        // Initialize the database
        opleidingRepository.saveAndFlush(opleiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the opleiding using partial update
        Opleiding partialUpdatedOpleiding = new Opleiding();
        partialUpdatedOpleiding.setId(opleiding.getId());

        partialUpdatedOpleiding.naam(UPDATED_NAAM).prijs(UPDATED_PRIJS);

        restOpleidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpleiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOpleiding))
            )
            .andExpect(status().isOk());

        // Validate the Opleiding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOpleidingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOpleiding, opleiding),
            getPersistedOpleiding(opleiding)
        );
    }

    @Test
    @Transactional
    void fullUpdateOpleidingWithPatch() throws Exception {
        // Initialize the database
        opleidingRepository.saveAndFlush(opleiding);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the opleiding using partial update
        Opleiding partialUpdatedOpleiding = new Opleiding();
        partialUpdatedOpleiding.setId(opleiding.getId());

        partialUpdatedOpleiding.instituut(UPDATED_INSTITUUT).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING).prijs(UPDATED_PRIJS);

        restOpleidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpleiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOpleiding))
            )
            .andExpect(status().isOk());

        // Validate the Opleiding in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOpleidingUpdatableFieldsEquals(partialUpdatedOpleiding, getPersistedOpleiding(partialUpdatedOpleiding));
    }

    @Test
    @Transactional
    void patchNonExistingOpleiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opleiding.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpleidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, opleiding.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(opleiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opleiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOpleiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opleiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpleidingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(opleiding))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opleiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOpleiding() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opleiding.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpleidingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(opleiding)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Opleiding in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOpleiding() throws Exception {
        // Initialize the database
        opleidingRepository.saveAndFlush(opleiding);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the opleiding
        restOpleidingMockMvc
            .perform(delete(ENTITY_API_URL_ID, opleiding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return opleidingRepository.count();
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

    protected Opleiding getPersistedOpleiding(Opleiding opleiding) {
        return opleidingRepository.findById(opleiding.getId()).orElseThrow();
    }

    protected void assertPersistedOpleidingToMatchAllProperties(Opleiding expectedOpleiding) {
        assertOpleidingAllPropertiesEquals(expectedOpleiding, getPersistedOpleiding(expectedOpleiding));
    }

    protected void assertPersistedOpleidingToMatchUpdatableProperties(Opleiding expectedOpleiding) {
        assertOpleidingAllUpdatablePropertiesEquals(expectedOpleiding, getPersistedOpleiding(expectedOpleiding));
    }
}
