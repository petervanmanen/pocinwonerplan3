package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ContainertypeAsserts.*;
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
import nl.ritense.demo.domain.Containertype;
import nl.ritense.demo.domain.Vuilniswagen;
import nl.ritense.demo.repository.ContainertypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ContainertypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContainertypeResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/containertypes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ContainertypeRepository containertypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContainertypeMockMvc;

    private Containertype containertype;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Containertype createEntity(EntityManager em) {
        Containertype containertype = new Containertype().naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        // Add required entity
        Vuilniswagen vuilniswagen;
        if (TestUtil.findAll(em, Vuilniswagen.class).isEmpty()) {
            vuilniswagen = VuilniswagenResourceIT.createEntity(em);
            em.persist(vuilniswagen);
            em.flush();
        } else {
            vuilniswagen = TestUtil.findAll(em, Vuilniswagen.class).get(0);
        }
        containertype.getGeschiktvoorVuilniswagens().add(vuilniswagen);
        return containertype;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Containertype createUpdatedEntity(EntityManager em) {
        Containertype containertype = new Containertype().naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        // Add required entity
        Vuilniswagen vuilniswagen;
        if (TestUtil.findAll(em, Vuilniswagen.class).isEmpty()) {
            vuilniswagen = VuilniswagenResourceIT.createUpdatedEntity(em);
            em.persist(vuilniswagen);
            em.flush();
        } else {
            vuilniswagen = TestUtil.findAll(em, Vuilniswagen.class).get(0);
        }
        containertype.getGeschiktvoorVuilniswagens().add(vuilniswagen);
        return containertype;
    }

    @BeforeEach
    public void initTest() {
        containertype = createEntity(em);
    }

    @Test
    @Transactional
    void createContainertype() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Containertype
        var returnedContainertype = om.readValue(
            restContainertypeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containertype)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Containertype.class
        );

        // Validate the Containertype in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertContainertypeUpdatableFieldsEquals(returnedContainertype, getPersistedContainertype(returnedContainertype));
    }

    @Test
    @Transactional
    void createContainertypeWithExistingId() throws Exception {
        // Create the Containertype with an existing ID
        containertype.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContainertypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containertype)))
            .andExpect(status().isBadRequest());

        // Validate the Containertype in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllContainertypes() throws Exception {
        // Initialize the database
        containertypeRepository.saveAndFlush(containertype);

        // Get all the containertypeList
        restContainertypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(containertype.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getContainertype() throws Exception {
        // Initialize the database
        containertypeRepository.saveAndFlush(containertype);

        // Get the containertype
        restContainertypeMockMvc
            .perform(get(ENTITY_API_URL_ID, containertype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(containertype.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingContainertype() throws Exception {
        // Get the containertype
        restContainertypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingContainertype() throws Exception {
        // Initialize the database
        containertypeRepository.saveAndFlush(containertype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the containertype
        Containertype updatedContainertype = containertypeRepository.findById(containertype.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedContainertype are not directly saved in db
        em.detach(updatedContainertype);
        updatedContainertype.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restContainertypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedContainertype.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedContainertype))
            )
            .andExpect(status().isOk());

        // Validate the Containertype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedContainertypeToMatchAllProperties(updatedContainertype);
    }

    @Test
    @Transactional
    void putNonExistingContainertype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        containertype.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContainertypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, containertype.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(containertype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Containertype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContainertype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        containertype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainertypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(containertype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Containertype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContainertype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        containertype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainertypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containertype)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Containertype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContainertypeWithPatch() throws Exception {
        // Initialize the database
        containertypeRepository.saveAndFlush(containertype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the containertype using partial update
        Containertype partialUpdatedContainertype = new Containertype();
        partialUpdatedContainertype.setId(containertype.getId());

        restContainertypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContainertype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContainertype))
            )
            .andExpect(status().isOk());

        // Validate the Containertype in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContainertypeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedContainertype, containertype),
            getPersistedContainertype(containertype)
        );
    }

    @Test
    @Transactional
    void fullUpdateContainertypeWithPatch() throws Exception {
        // Initialize the database
        containertypeRepository.saveAndFlush(containertype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the containertype using partial update
        Containertype partialUpdatedContainertype = new Containertype();
        partialUpdatedContainertype.setId(containertype.getId());

        partialUpdatedContainertype.naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restContainertypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContainertype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContainertype))
            )
            .andExpect(status().isOk());

        // Validate the Containertype in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContainertypeUpdatableFieldsEquals(partialUpdatedContainertype, getPersistedContainertype(partialUpdatedContainertype));
    }

    @Test
    @Transactional
    void patchNonExistingContainertype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        containertype.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContainertypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, containertype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(containertype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Containertype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContainertype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        containertype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainertypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(containertype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Containertype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContainertype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        containertype.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainertypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(containertype)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Containertype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContainertype() throws Exception {
        // Initialize the database
        containertypeRepository.saveAndFlush(containertype);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the containertype
        restContainertypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, containertype.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return containertypeRepository.count();
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

    protected Containertype getPersistedContainertype(Containertype containertype) {
        return containertypeRepository.findById(containertype.getId()).orElseThrow();
    }

    protected void assertPersistedContainertypeToMatchAllProperties(Containertype expectedContainertype) {
        assertContainertypeAllPropertiesEquals(expectedContainertype, getPersistedContainertype(expectedContainertype));
    }

    protected void assertPersistedContainertypeToMatchUpdatableProperties(Containertype expectedContainertype) {
        assertContainertypeAllUpdatablePropertiesEquals(expectedContainertype, getPersistedContainertype(expectedContainertype));
    }
}
