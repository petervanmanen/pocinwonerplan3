package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ActiviteitAsserts.*;
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
import nl.ritense.demo.domain.Activiteit;
import nl.ritense.demo.repository.ActiviteitRepository;
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
 * Integration tests for the {@link ActiviteitResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ActiviteitResourceIT {

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/activiteits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ActiviteitRepository activiteitRepository;

    @Mock
    private ActiviteitRepository activiteitRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActiviteitMockMvc;

    private Activiteit activiteit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Activiteit createEntity(EntityManager em) {
        Activiteit activiteit = new Activiteit().omschrijving(DEFAULT_OMSCHRIJVING);
        return activiteit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Activiteit createUpdatedEntity(EntityManager em) {
        Activiteit activiteit = new Activiteit().omschrijving(UPDATED_OMSCHRIJVING);
        return activiteit;
    }

    @BeforeEach
    public void initTest() {
        activiteit = createEntity(em);
    }

    @Test
    @Transactional
    void createActiviteit() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Activiteit
        var returnedActiviteit = om.readValue(
            restActiviteitMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(activiteit)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Activiteit.class
        );

        // Validate the Activiteit in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertActiviteitUpdatableFieldsEquals(returnedActiviteit, getPersistedActiviteit(returnedActiviteit));
    }

    @Test
    @Transactional
    void createActiviteitWithExistingId() throws Exception {
        // Create the Activiteit with an existing ID
        activiteit.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restActiviteitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(activiteit)))
            .andExpect(status().isBadRequest());

        // Validate the Activiteit in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllActiviteits() throws Exception {
        // Initialize the database
        activiteitRepository.saveAndFlush(activiteit);

        // Get all the activiteitList
        restActiviteitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activiteit.getId().intValue())))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllActiviteitsWithEagerRelationshipsIsEnabled() throws Exception {
        when(activiteitRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restActiviteitMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(activiteitRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllActiviteitsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(activiteitRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restActiviteitMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(activiteitRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getActiviteit() throws Exception {
        // Initialize the database
        activiteitRepository.saveAndFlush(activiteit);

        // Get the activiteit
        restActiviteitMockMvc
            .perform(get(ENTITY_API_URL_ID, activiteit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(activiteit.getId().intValue()))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingActiviteit() throws Exception {
        // Get the activiteit
        restActiviteitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingActiviteit() throws Exception {
        // Initialize the database
        activiteitRepository.saveAndFlush(activiteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the activiteit
        Activiteit updatedActiviteit = activiteitRepository.findById(activiteit.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedActiviteit are not directly saved in db
        em.detach(updatedActiviteit);
        updatedActiviteit.omschrijving(UPDATED_OMSCHRIJVING);

        restActiviteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedActiviteit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedActiviteit))
            )
            .andExpect(status().isOk());

        // Validate the Activiteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedActiviteitToMatchAllProperties(updatedActiviteit);
    }

    @Test
    @Transactional
    void putNonExistingActiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activiteit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActiviteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, activiteit.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(activiteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activiteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchActiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activiteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActiviteitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(activiteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activiteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamActiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activiteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActiviteitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(activiteit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Activiteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateActiviteitWithPatch() throws Exception {
        // Initialize the database
        activiteitRepository.saveAndFlush(activiteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the activiteit using partial update
        Activiteit partialUpdatedActiviteit = new Activiteit();
        partialUpdatedActiviteit.setId(activiteit.getId());

        partialUpdatedActiviteit.omschrijving(UPDATED_OMSCHRIJVING);

        restActiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActiviteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedActiviteit))
            )
            .andExpect(status().isOk());

        // Validate the Activiteit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertActiviteitUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedActiviteit, activiteit),
            getPersistedActiviteit(activiteit)
        );
    }

    @Test
    @Transactional
    void fullUpdateActiviteitWithPatch() throws Exception {
        // Initialize the database
        activiteitRepository.saveAndFlush(activiteit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the activiteit using partial update
        Activiteit partialUpdatedActiviteit = new Activiteit();
        partialUpdatedActiviteit.setId(activiteit.getId());

        partialUpdatedActiviteit.omschrijving(UPDATED_OMSCHRIJVING);

        restActiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActiviteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedActiviteit))
            )
            .andExpect(status().isOk());

        // Validate the Activiteit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertActiviteitUpdatableFieldsEquals(partialUpdatedActiviteit, getPersistedActiviteit(partialUpdatedActiviteit));
    }

    @Test
    @Transactional
    void patchNonExistingActiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activiteit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, activiteit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(activiteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activiteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchActiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activiteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActiviteitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(activiteit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activiteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamActiviteit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activiteit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActiviteitMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(activiteit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Activiteit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteActiviteit() throws Exception {
        // Initialize the database
        activiteitRepository.saveAndFlush(activiteit);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the activiteit
        restActiviteitMockMvc
            .perform(delete(ENTITY_API_URL_ID, activiteit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return activiteitRepository.count();
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

    protected Activiteit getPersistedActiviteit(Activiteit activiteit) {
        return activiteitRepository.findById(activiteit.getId()).orElseThrow();
    }

    protected void assertPersistedActiviteitToMatchAllProperties(Activiteit expectedActiviteit) {
        assertActiviteitAllPropertiesEquals(expectedActiviteit, getPersistedActiviteit(expectedActiviteit));
    }

    protected void assertPersistedActiviteitToMatchUpdatableProperties(Activiteit expectedActiviteit) {
        assertActiviteitAllUpdatablePropertiesEquals(expectedActiviteit, getPersistedActiviteit(expectedActiviteit));
    }
}
