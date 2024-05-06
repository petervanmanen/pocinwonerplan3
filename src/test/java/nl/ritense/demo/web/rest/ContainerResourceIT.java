package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ContainerAsserts.*;
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
import nl.ritense.demo.domain.Container;
import nl.ritense.demo.repository.ContainerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ContainerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContainerResourceIT {

    private static final String DEFAULT_CONTAINERCODE = "AAAAAAAAAA";
    private static final String UPDATED_CONTAINERCODE = "BBBBBBBBBB";

    private static final String DEFAULT_SENSORID = "AAAAAAAAAA";
    private static final String UPDATED_SENSORID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/containers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ContainerRepository containerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContainerMockMvc;

    private Container container;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Container createEntity(EntityManager em) {
        Container container = new Container().containercode(DEFAULT_CONTAINERCODE).sensorid(DEFAULT_SENSORID);
        return container;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Container createUpdatedEntity(EntityManager em) {
        Container container = new Container().containercode(UPDATED_CONTAINERCODE).sensorid(UPDATED_SENSORID);
        return container;
    }

    @BeforeEach
    public void initTest() {
        container = createEntity(em);
    }

    @Test
    @Transactional
    void createContainer() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Container
        var returnedContainer = om.readValue(
            restContainerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(container)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Container.class
        );

        // Validate the Container in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertContainerUpdatableFieldsEquals(returnedContainer, getPersistedContainer(returnedContainer));
    }

    @Test
    @Transactional
    void createContainerWithExistingId() throws Exception {
        // Create the Container with an existing ID
        container.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContainerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(container)))
            .andExpect(status().isBadRequest());

        // Validate the Container in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllContainers() throws Exception {
        // Initialize the database
        containerRepository.saveAndFlush(container);

        // Get all the containerList
        restContainerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(container.getId().intValue())))
            .andExpect(jsonPath("$.[*].containercode").value(hasItem(DEFAULT_CONTAINERCODE)))
            .andExpect(jsonPath("$.[*].sensorid").value(hasItem(DEFAULT_SENSORID)));
    }

    @Test
    @Transactional
    void getContainer() throws Exception {
        // Initialize the database
        containerRepository.saveAndFlush(container);

        // Get the container
        restContainerMockMvc
            .perform(get(ENTITY_API_URL_ID, container.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(container.getId().intValue()))
            .andExpect(jsonPath("$.containercode").value(DEFAULT_CONTAINERCODE))
            .andExpect(jsonPath("$.sensorid").value(DEFAULT_SENSORID));
    }

    @Test
    @Transactional
    void getNonExistingContainer() throws Exception {
        // Get the container
        restContainerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingContainer() throws Exception {
        // Initialize the database
        containerRepository.saveAndFlush(container);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the container
        Container updatedContainer = containerRepository.findById(container.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedContainer are not directly saved in db
        em.detach(updatedContainer);
        updatedContainer.containercode(UPDATED_CONTAINERCODE).sensorid(UPDATED_SENSORID);

        restContainerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedContainer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedContainer))
            )
            .andExpect(status().isOk());

        // Validate the Container in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedContainerToMatchAllProperties(updatedContainer);
    }

    @Test
    @Transactional
    void putNonExistingContainer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        container.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContainerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, container.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(container))
            )
            .andExpect(status().isBadRequest());

        // Validate the Container in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContainer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        container.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(container))
            )
            .andExpect(status().isBadRequest());

        // Validate the Container in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContainer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        container.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(container)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Container in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContainerWithPatch() throws Exception {
        // Initialize the database
        containerRepository.saveAndFlush(container);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the container using partial update
        Container partialUpdatedContainer = new Container();
        partialUpdatedContainer.setId(container.getId());

        partialUpdatedContainer.containercode(UPDATED_CONTAINERCODE).sensorid(UPDATED_SENSORID);

        restContainerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContainer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContainer))
            )
            .andExpect(status().isOk());

        // Validate the Container in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContainerUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedContainer, container),
            getPersistedContainer(container)
        );
    }

    @Test
    @Transactional
    void fullUpdateContainerWithPatch() throws Exception {
        // Initialize the database
        containerRepository.saveAndFlush(container);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the container using partial update
        Container partialUpdatedContainer = new Container();
        partialUpdatedContainer.setId(container.getId());

        partialUpdatedContainer.containercode(UPDATED_CONTAINERCODE).sensorid(UPDATED_SENSORID);

        restContainerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContainer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContainer))
            )
            .andExpect(status().isOk());

        // Validate the Container in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContainerUpdatableFieldsEquals(partialUpdatedContainer, getPersistedContainer(partialUpdatedContainer));
    }

    @Test
    @Transactional
    void patchNonExistingContainer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        container.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContainerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, container.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(container))
            )
            .andExpect(status().isBadRequest());

        // Validate the Container in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContainer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        container.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(container))
            )
            .andExpect(status().isBadRequest());

        // Validate the Container in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContainer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        container.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(container)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Container in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContainer() throws Exception {
        // Initialize the database
        containerRepository.saveAndFlush(container);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the container
        restContainerMockMvc
            .perform(delete(ENTITY_API_URL_ID, container.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return containerRepository.count();
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

    protected Container getPersistedContainer(Container container) {
        return containerRepository.findById(container.getId()).orElseThrow();
    }

    protected void assertPersistedContainerToMatchAllProperties(Container expectedContainer) {
        assertContainerAllPropertiesEquals(expectedContainer, getPersistedContainer(expectedContainer));
    }

    protected void assertPersistedContainerToMatchUpdatableProperties(Container expectedContainer) {
        assertContainerAllUpdatablePropertiesEquals(expectedContainer, getPersistedContainer(expectedContainer));
    }
}
