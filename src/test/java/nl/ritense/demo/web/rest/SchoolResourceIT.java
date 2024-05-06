package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SchoolAsserts.*;
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
import nl.ritense.demo.domain.Onderwijsloopbaan;
import nl.ritense.demo.domain.School;
import nl.ritense.demo.repository.SchoolRepository;
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
 * Integration tests for the {@link SchoolResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SchoolResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/schools";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SchoolRepository schoolRepository;

    @Mock
    private SchoolRepository schoolRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSchoolMockMvc;

    private School school;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static School createEntity(EntityManager em) {
        School school = new School().naam(DEFAULT_NAAM);
        // Add required entity
        Onderwijsloopbaan onderwijsloopbaan;
        if (TestUtil.findAll(em, Onderwijsloopbaan.class).isEmpty()) {
            onderwijsloopbaan = OnderwijsloopbaanResourceIT.createEntity(em);
            em.persist(onderwijsloopbaan);
            em.flush();
        } else {
            onderwijsloopbaan = TestUtil.findAll(em, Onderwijsloopbaan.class).get(0);
        }
        school.getKentOnderwijsloopbaans().add(onderwijsloopbaan);
        return school;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static School createUpdatedEntity(EntityManager em) {
        School school = new School().naam(UPDATED_NAAM);
        // Add required entity
        Onderwijsloopbaan onderwijsloopbaan;
        if (TestUtil.findAll(em, Onderwijsloopbaan.class).isEmpty()) {
            onderwijsloopbaan = OnderwijsloopbaanResourceIT.createUpdatedEntity(em);
            em.persist(onderwijsloopbaan);
            em.flush();
        } else {
            onderwijsloopbaan = TestUtil.findAll(em, Onderwijsloopbaan.class).get(0);
        }
        school.getKentOnderwijsloopbaans().add(onderwijsloopbaan);
        return school;
    }

    @BeforeEach
    public void initTest() {
        school = createEntity(em);
    }

    @Test
    @Transactional
    void createSchool() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the School
        var returnedSchool = om.readValue(
            restSchoolMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(school)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            School.class
        );

        // Validate the School in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSchoolUpdatableFieldsEquals(returnedSchool, getPersistedSchool(returnedSchool));
    }

    @Test
    @Transactional
    void createSchoolWithExistingId() throws Exception {
        // Create the School with an existing ID
        school.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchoolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(school)))
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSchools() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList
        restSchoolMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(school.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSchoolsWithEagerRelationshipsIsEnabled() throws Exception {
        when(schoolRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSchoolMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(schoolRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSchoolsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(schoolRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSchoolMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(schoolRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get the school
        restSchoolMockMvc
            .perform(get(ENTITY_API_URL_ID, school.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(school.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM));
    }

    @Test
    @Transactional
    void getNonExistingSchool() throws Exception {
        // Get the school
        restSchoolMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the school
        School updatedSchool = schoolRepository.findById(school.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSchool are not directly saved in db
        em.detach(updatedSchool);
        updatedSchool.naam(UPDATED_NAAM);

        restSchoolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSchool.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSchool))
            )
            .andExpect(status().isOk());

        // Validate the School in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSchoolToMatchAllProperties(updatedSchool);
    }

    @Test
    @Transactional
    void putNonExistingSchool() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        school.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchoolMockMvc
            .perform(put(ENTITY_API_URL_ID, school.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(school)))
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSchool() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        school.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchoolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(school))
            )
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSchool() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        school.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchoolMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(school)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the School in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSchoolWithPatch() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the school using partial update
        School partialUpdatedSchool = new School();
        partialUpdatedSchool.setId(school.getId());

        partialUpdatedSchool.naam(UPDATED_NAAM);

        restSchoolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSchool.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSchool))
            )
            .andExpect(status().isOk());

        // Validate the School in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSchoolUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSchool, school), getPersistedSchool(school));
    }

    @Test
    @Transactional
    void fullUpdateSchoolWithPatch() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the school using partial update
        School partialUpdatedSchool = new School();
        partialUpdatedSchool.setId(school.getId());

        partialUpdatedSchool.naam(UPDATED_NAAM);

        restSchoolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSchool.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSchool))
            )
            .andExpect(status().isOk());

        // Validate the School in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSchoolUpdatableFieldsEquals(partialUpdatedSchool, getPersistedSchool(partialUpdatedSchool));
    }

    @Test
    @Transactional
    void patchNonExistingSchool() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        school.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchoolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, school.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(school))
            )
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSchool() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        school.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchoolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(school))
            )
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSchool() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        school.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchoolMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(school)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the School in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the school
        restSchoolMockMvc
            .perform(delete(ENTITY_API_URL_ID, school.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return schoolRepository.count();
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

    protected School getPersistedSchool(School school) {
        return schoolRepository.findById(school.getId()).orElseThrow();
    }

    protected void assertPersistedSchoolToMatchAllProperties(School expectedSchool) {
        assertSchoolAllPropertiesEquals(expectedSchool, getPersistedSchool(expectedSchool));
    }

    protected void assertPersistedSchoolToMatchUpdatableProperties(School expectedSchool) {
        assertSchoolAllUpdatablePropertiesEquals(expectedSchool, getPersistedSchool(expectedSchool));
    }
}
