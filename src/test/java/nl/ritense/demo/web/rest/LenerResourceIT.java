package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.LenerAsserts.*;
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
import nl.ritense.demo.domain.Bruikleen;
import nl.ritense.demo.domain.Lener;
import nl.ritense.demo.repository.LenerRepository;
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
 * Integration tests for the {@link LenerResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class LenerResourceIT {

    private static final String DEFAULT_OPMERKINGEN = "AAAAAAAAAA";
    private static final String UPDATED_OPMERKINGEN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/leners";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LenerRepository lenerRepository;

    @Mock
    private LenerRepository lenerRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLenerMockMvc;

    private Lener lener;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lener createEntity(EntityManager em) {
        Lener lener = new Lener().opmerkingen(DEFAULT_OPMERKINGEN);
        // Add required entity
        Bruikleen bruikleen;
        if (TestUtil.findAll(em, Bruikleen.class).isEmpty()) {
            bruikleen = BruikleenResourceIT.createEntity(em);
            em.persist(bruikleen);
            em.flush();
        } else {
            bruikleen = TestUtil.findAll(em, Bruikleen.class).get(0);
        }
        lener.getIsBruikleens().add(bruikleen);
        return lener;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lener createUpdatedEntity(EntityManager em) {
        Lener lener = new Lener().opmerkingen(UPDATED_OPMERKINGEN);
        // Add required entity
        Bruikleen bruikleen;
        if (TestUtil.findAll(em, Bruikleen.class).isEmpty()) {
            bruikleen = BruikleenResourceIT.createUpdatedEntity(em);
            em.persist(bruikleen);
            em.flush();
        } else {
            bruikleen = TestUtil.findAll(em, Bruikleen.class).get(0);
        }
        lener.getIsBruikleens().add(bruikleen);
        return lener;
    }

    @BeforeEach
    public void initTest() {
        lener = createEntity(em);
    }

    @Test
    @Transactional
    void createLener() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Lener
        var returnedLener = om.readValue(
            restLenerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lener)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Lener.class
        );

        // Validate the Lener in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLenerUpdatableFieldsEquals(returnedLener, getPersistedLener(returnedLener));
    }

    @Test
    @Transactional
    void createLenerWithExistingId() throws Exception {
        // Create the Lener with an existing ID
        lener.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLenerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lener)))
            .andExpect(status().isBadRequest());

        // Validate the Lener in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLeners() throws Exception {
        // Initialize the database
        lenerRepository.saveAndFlush(lener);

        // Get all the lenerList
        restLenerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lener.getId().intValue())))
            .andExpect(jsonPath("$.[*].opmerkingen").value(hasItem(DEFAULT_OPMERKINGEN)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLenersWithEagerRelationshipsIsEnabled() throws Exception {
        when(lenerRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLenerMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(lenerRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLenersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(lenerRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLenerMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(lenerRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getLener() throws Exception {
        // Initialize the database
        lenerRepository.saveAndFlush(lener);

        // Get the lener
        restLenerMockMvc
            .perform(get(ENTITY_API_URL_ID, lener.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lener.getId().intValue()))
            .andExpect(jsonPath("$.opmerkingen").value(DEFAULT_OPMERKINGEN));
    }

    @Test
    @Transactional
    void getNonExistingLener() throws Exception {
        // Get the lener
        restLenerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLener() throws Exception {
        // Initialize the database
        lenerRepository.saveAndFlush(lener);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lener
        Lener updatedLener = lenerRepository.findById(lener.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLener are not directly saved in db
        em.detach(updatedLener);
        updatedLener.opmerkingen(UPDATED_OPMERKINGEN);

        restLenerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLener.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLener))
            )
            .andExpect(status().isOk());

        // Validate the Lener in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLenerToMatchAllProperties(updatedLener);
    }

    @Test
    @Transactional
    void putNonExistingLener() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lener.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLenerMockMvc
            .perform(put(ENTITY_API_URL_ID, lener.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lener)))
            .andExpect(status().isBadRequest());

        // Validate the Lener in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLener() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lener.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLenerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(lener))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lener in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLener() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lener.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLenerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(lener)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Lener in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLenerWithPatch() throws Exception {
        // Initialize the database
        lenerRepository.saveAndFlush(lener);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lener using partial update
        Lener partialUpdatedLener = new Lener();
        partialUpdatedLener.setId(lener.getId());

        partialUpdatedLener.opmerkingen(UPDATED_OPMERKINGEN);

        restLenerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLener.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLener))
            )
            .andExpect(status().isOk());

        // Validate the Lener in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLenerUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedLener, lener), getPersistedLener(lener));
    }

    @Test
    @Transactional
    void fullUpdateLenerWithPatch() throws Exception {
        // Initialize the database
        lenerRepository.saveAndFlush(lener);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the lener using partial update
        Lener partialUpdatedLener = new Lener();
        partialUpdatedLener.setId(lener.getId());

        partialUpdatedLener.opmerkingen(UPDATED_OPMERKINGEN);

        restLenerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLener.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLener))
            )
            .andExpect(status().isOk());

        // Validate the Lener in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLenerUpdatableFieldsEquals(partialUpdatedLener, getPersistedLener(partialUpdatedLener));
    }

    @Test
    @Transactional
    void patchNonExistingLener() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lener.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLenerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lener.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(lener))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lener in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLener() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lener.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLenerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(lener))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lener in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLener() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        lener.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLenerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(lener)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Lener in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLener() throws Exception {
        // Initialize the database
        lenerRepository.saveAndFlush(lener);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the lener
        restLenerMockMvc
            .perform(delete(ENTITY_API_URL_ID, lener.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return lenerRepository.count();
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

    protected Lener getPersistedLener(Lener lener) {
        return lenerRepository.findById(lener.getId()).orElseThrow();
    }

    protected void assertPersistedLenerToMatchAllProperties(Lener expectedLener) {
        assertLenerAllPropertiesEquals(expectedLener, getPersistedLener(expectedLener));
    }

    protected void assertPersistedLenerToMatchUpdatableProperties(Lener expectedLener) {
        assertLenerAllUpdatablePropertiesEquals(expectedLener, getPersistedLener(expectedLener));
    }
}
