package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.Class1Asserts.*;
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
import nl.ritense.demo.domain.Class1;
import nl.ritense.demo.repository.Class1Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link Class1Resource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class Class1ResourceIT {

    private static final String DEFAULT_WAARDE = "AAAAAAAAAA";
    private static final String UPDATED_WAARDE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/class-1-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private Class1Repository class1Repository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClass1MockMvc;

    private Class1 class1;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Class1 createEntity(EntityManager em) {
        Class1 class1 = new Class1().waarde(DEFAULT_WAARDE);
        return class1;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Class1 createUpdatedEntity(EntityManager em) {
        Class1 class1 = new Class1().waarde(UPDATED_WAARDE);
        return class1;
    }

    @BeforeEach
    public void initTest() {
        class1 = createEntity(em);
    }

    @Test
    @Transactional
    void createClass1() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Class1
        var returnedClass1 = om.readValue(
            restClass1MockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(class1)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Class1.class
        );

        // Validate the Class1 in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertClass1UpdatableFieldsEquals(returnedClass1, getPersistedClass1(returnedClass1));
    }

    @Test
    @Transactional
    void createClass1WithExistingId() throws Exception {
        // Create the Class1 with an existing ID
        class1.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClass1MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(class1)))
            .andExpect(status().isBadRequest());

        // Validate the Class1 in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClass1s() throws Exception {
        // Initialize the database
        class1Repository.saveAndFlush(class1);

        // Get all the class1List
        restClass1MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(class1.getId().intValue())))
            .andExpect(jsonPath("$.[*].waarde").value(hasItem(DEFAULT_WAARDE)));
    }

    @Test
    @Transactional
    void getClass1() throws Exception {
        // Initialize the database
        class1Repository.saveAndFlush(class1);

        // Get the class1
        restClass1MockMvc
            .perform(get(ENTITY_API_URL_ID, class1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(class1.getId().intValue()))
            .andExpect(jsonPath("$.waarde").value(DEFAULT_WAARDE));
    }

    @Test
    @Transactional
    void getNonExistingClass1() throws Exception {
        // Get the class1
        restClass1MockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClass1() throws Exception {
        // Initialize the database
        class1Repository.saveAndFlush(class1);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the class1
        Class1 updatedClass1 = class1Repository.findById(class1.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedClass1 are not directly saved in db
        em.detach(updatedClass1);
        updatedClass1.waarde(UPDATED_WAARDE);

        restClass1MockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClass1.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedClass1))
            )
            .andExpect(status().isOk());

        // Validate the Class1 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedClass1ToMatchAllProperties(updatedClass1);
    }

    @Test
    @Transactional
    void putNonExistingClass1() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        class1.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClass1MockMvc
            .perform(put(ENTITY_API_URL_ID, class1.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(class1)))
            .andExpect(status().isBadRequest());

        // Validate the Class1 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClass1() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        class1.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClass1MockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(class1))
            )
            .andExpect(status().isBadRequest());

        // Validate the Class1 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClass1() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        class1.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClass1MockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(class1)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Class1 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClass1WithPatch() throws Exception {
        // Initialize the database
        class1Repository.saveAndFlush(class1);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the class1 using partial update
        Class1 partialUpdatedClass1 = new Class1();
        partialUpdatedClass1.setId(class1.getId());

        restClass1MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClass1.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedClass1))
            )
            .andExpect(status().isOk());

        // Validate the Class1 in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertClass1UpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedClass1, class1), getPersistedClass1(class1));
    }

    @Test
    @Transactional
    void fullUpdateClass1WithPatch() throws Exception {
        // Initialize the database
        class1Repository.saveAndFlush(class1);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the class1 using partial update
        Class1 partialUpdatedClass1 = new Class1();
        partialUpdatedClass1.setId(class1.getId());

        partialUpdatedClass1.waarde(UPDATED_WAARDE);

        restClass1MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClass1.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedClass1))
            )
            .andExpect(status().isOk());

        // Validate the Class1 in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertClass1UpdatableFieldsEquals(partialUpdatedClass1, getPersistedClass1(partialUpdatedClass1));
    }

    @Test
    @Transactional
    void patchNonExistingClass1() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        class1.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClass1MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, class1.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(class1))
            )
            .andExpect(status().isBadRequest());

        // Validate the Class1 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClass1() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        class1.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClass1MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(class1))
            )
            .andExpect(status().isBadRequest());

        // Validate the Class1 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClass1() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        class1.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClass1MockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(class1)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Class1 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClass1() throws Exception {
        // Initialize the database
        class1Repository.saveAndFlush(class1);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the class1
        restClass1MockMvc
            .perform(delete(ENTITY_API_URL_ID, class1.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return class1Repository.count();
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

    protected Class1 getPersistedClass1(Class1 class1) {
        return class1Repository.findById(class1.getId()).orElseThrow();
    }

    protected void assertPersistedClass1ToMatchAllProperties(Class1 expectedClass1) {
        assertClass1AllPropertiesEquals(expectedClass1, getPersistedClass1(expectedClass1));
    }

    protected void assertPersistedClass1ToMatchUpdatableProperties(Class1 expectedClass1) {
        assertClass1AllUpdatablePropertiesEquals(expectedClass1, getPersistedClass1(expectedClass1));
    }
}
