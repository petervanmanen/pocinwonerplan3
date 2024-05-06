package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ClientbegeleiderAsserts.*;
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
import nl.ritense.demo.domain.Clientbegeleider;
import nl.ritense.demo.repository.ClientbegeleiderRepository;
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
 * Integration tests for the {@link ClientbegeleiderResource} REST controller.
 */
@IntegrationTest
@Disabled("Cyclic required relationships detected")
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ClientbegeleiderResourceIT {

    private static final String DEFAULT_BEGELEIDERSCODE = "AAAAAAAAAA";
    private static final String UPDATED_BEGELEIDERSCODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/clientbegeleiders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ClientbegeleiderRepository clientbegeleiderRepository;

    @Mock
    private ClientbegeleiderRepository clientbegeleiderRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientbegeleiderMockMvc;

    private Clientbegeleider clientbegeleider;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clientbegeleider createEntity(EntityManager em) {
        Clientbegeleider clientbegeleider = new Clientbegeleider().begeleiderscode(DEFAULT_BEGELEIDERSCODE);
        return clientbegeleider;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clientbegeleider createUpdatedEntity(EntityManager em) {
        Clientbegeleider clientbegeleider = new Clientbegeleider().begeleiderscode(UPDATED_BEGELEIDERSCODE);
        return clientbegeleider;
    }

    @BeforeEach
    public void initTest() {
        clientbegeleider = createEntity(em);
    }

    @Test
    @Transactional
    void createClientbegeleider() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Clientbegeleider
        var returnedClientbegeleider = om.readValue(
            restClientbegeleiderMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(clientbegeleider)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Clientbegeleider.class
        );

        // Validate the Clientbegeleider in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertClientbegeleiderUpdatableFieldsEquals(returnedClientbegeleider, getPersistedClientbegeleider(returnedClientbegeleider));
    }

    @Test
    @Transactional
    void createClientbegeleiderWithExistingId() throws Exception {
        // Create the Clientbegeleider with an existing ID
        clientbegeleider.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientbegeleiderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(clientbegeleider)))
            .andExpect(status().isBadRequest());

        // Validate the Clientbegeleider in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClientbegeleiders() throws Exception {
        // Initialize the database
        clientbegeleiderRepository.saveAndFlush(clientbegeleider);

        // Get all the clientbegeleiderList
        restClientbegeleiderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientbegeleider.getId().intValue())))
            .andExpect(jsonPath("$.[*].begeleiderscode").value(hasItem(DEFAULT_BEGELEIDERSCODE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllClientbegeleidersWithEagerRelationshipsIsEnabled() throws Exception {
        when(clientbegeleiderRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restClientbegeleiderMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(clientbegeleiderRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllClientbegeleidersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(clientbegeleiderRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restClientbegeleiderMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(clientbegeleiderRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getClientbegeleider() throws Exception {
        // Initialize the database
        clientbegeleiderRepository.saveAndFlush(clientbegeleider);

        // Get the clientbegeleider
        restClientbegeleiderMockMvc
            .perform(get(ENTITY_API_URL_ID, clientbegeleider.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clientbegeleider.getId().intValue()))
            .andExpect(jsonPath("$.begeleiderscode").value(DEFAULT_BEGELEIDERSCODE));
    }

    @Test
    @Transactional
    void getNonExistingClientbegeleider() throws Exception {
        // Get the clientbegeleider
        restClientbegeleiderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClientbegeleider() throws Exception {
        // Initialize the database
        clientbegeleiderRepository.saveAndFlush(clientbegeleider);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the clientbegeleider
        Clientbegeleider updatedClientbegeleider = clientbegeleiderRepository.findById(clientbegeleider.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedClientbegeleider are not directly saved in db
        em.detach(updatedClientbegeleider);
        updatedClientbegeleider.begeleiderscode(UPDATED_BEGELEIDERSCODE);

        restClientbegeleiderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClientbegeleider.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedClientbegeleider))
            )
            .andExpect(status().isOk());

        // Validate the Clientbegeleider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedClientbegeleiderToMatchAllProperties(updatedClientbegeleider);
    }

    @Test
    @Transactional
    void putNonExistingClientbegeleider() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        clientbegeleider.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientbegeleiderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clientbegeleider.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(clientbegeleider))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clientbegeleider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClientbegeleider() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        clientbegeleider.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientbegeleiderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(clientbegeleider))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clientbegeleider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClientbegeleider() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        clientbegeleider.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientbegeleiderMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(clientbegeleider)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Clientbegeleider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClientbegeleiderWithPatch() throws Exception {
        // Initialize the database
        clientbegeleiderRepository.saveAndFlush(clientbegeleider);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the clientbegeleider using partial update
        Clientbegeleider partialUpdatedClientbegeleider = new Clientbegeleider();
        partialUpdatedClientbegeleider.setId(clientbegeleider.getId());

        restClientbegeleiderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClientbegeleider.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedClientbegeleider))
            )
            .andExpect(status().isOk());

        // Validate the Clientbegeleider in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertClientbegeleiderUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedClientbegeleider, clientbegeleider),
            getPersistedClientbegeleider(clientbegeleider)
        );
    }

    @Test
    @Transactional
    void fullUpdateClientbegeleiderWithPatch() throws Exception {
        // Initialize the database
        clientbegeleiderRepository.saveAndFlush(clientbegeleider);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the clientbegeleider using partial update
        Clientbegeleider partialUpdatedClientbegeleider = new Clientbegeleider();
        partialUpdatedClientbegeleider.setId(clientbegeleider.getId());

        partialUpdatedClientbegeleider.begeleiderscode(UPDATED_BEGELEIDERSCODE);

        restClientbegeleiderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClientbegeleider.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedClientbegeleider))
            )
            .andExpect(status().isOk());

        // Validate the Clientbegeleider in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertClientbegeleiderUpdatableFieldsEquals(
            partialUpdatedClientbegeleider,
            getPersistedClientbegeleider(partialUpdatedClientbegeleider)
        );
    }

    @Test
    @Transactional
    void patchNonExistingClientbegeleider() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        clientbegeleider.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientbegeleiderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clientbegeleider.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(clientbegeleider))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clientbegeleider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClientbegeleider() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        clientbegeleider.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientbegeleiderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(clientbegeleider))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clientbegeleider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClientbegeleider() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        clientbegeleider.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientbegeleiderMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(clientbegeleider)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Clientbegeleider in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClientbegeleider() throws Exception {
        // Initialize the database
        clientbegeleiderRepository.saveAndFlush(clientbegeleider);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the clientbegeleider
        restClientbegeleiderMockMvc
            .perform(delete(ENTITY_API_URL_ID, clientbegeleider.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return clientbegeleiderRepository.count();
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

    protected Clientbegeleider getPersistedClientbegeleider(Clientbegeleider clientbegeleider) {
        return clientbegeleiderRepository.findById(clientbegeleider.getId()).orElseThrow();
    }

    protected void assertPersistedClientbegeleiderToMatchAllProperties(Clientbegeleider expectedClientbegeleider) {
        assertClientbegeleiderAllPropertiesEquals(expectedClientbegeleider, getPersistedClientbegeleider(expectedClientbegeleider));
    }

    protected void assertPersistedClientbegeleiderToMatchUpdatableProperties(Clientbegeleider expectedClientbegeleider) {
        assertClientbegeleiderAllUpdatablePropertiesEquals(
            expectedClientbegeleider,
            getPersistedClientbegeleider(expectedClientbegeleider)
        );
    }
}
