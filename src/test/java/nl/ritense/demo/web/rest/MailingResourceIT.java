package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.MailingAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Mailing;
import nl.ritense.demo.repository.MailingRepository;
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
 * Integration tests for the {@link MailingResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MailingResourceIT {

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/mailings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MailingRepository mailingRepository;

    @Mock
    private MailingRepository mailingRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMailingMockMvc;

    private Mailing mailing;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mailing createEntity(EntityManager em) {
        Mailing mailing = new Mailing().datum(DEFAULT_DATUM).naam(DEFAULT_NAAM).omschrijving(DEFAULT_OMSCHRIJVING);
        return mailing;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mailing createUpdatedEntity(EntityManager em) {
        Mailing mailing = new Mailing().datum(UPDATED_DATUM).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);
        return mailing;
    }

    @BeforeEach
    public void initTest() {
        mailing = createEntity(em);
    }

    @Test
    @Transactional
    void createMailing() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Mailing
        var returnedMailing = om.readValue(
            restMailingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mailing)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Mailing.class
        );

        // Validate the Mailing in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMailingUpdatableFieldsEquals(returnedMailing, getPersistedMailing(returnedMailing));
    }

    @Test
    @Transactional
    void createMailingWithExistingId() throws Exception {
        // Create the Mailing with an existing ID
        mailing.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMailingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mailing)))
            .andExpect(status().isBadRequest());

        // Validate the Mailing in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMailings() throws Exception {
        // Initialize the database
        mailingRepository.saveAndFlush(mailing);

        // Get all the mailingList
        restMailingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mailing.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMailingsWithEagerRelationshipsIsEnabled() throws Exception {
        when(mailingRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMailingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(mailingRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMailingsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(mailingRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMailingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(mailingRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getMailing() throws Exception {
        // Initialize the database
        mailingRepository.saveAndFlush(mailing);

        // Get the mailing
        restMailingMockMvc
            .perform(get(ENTITY_API_URL_ID, mailing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mailing.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingMailing() throws Exception {
        // Get the mailing
        restMailingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMailing() throws Exception {
        // Initialize the database
        mailingRepository.saveAndFlush(mailing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mailing
        Mailing updatedMailing = mailingRepository.findById(mailing.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMailing are not directly saved in db
        em.detach(updatedMailing);
        updatedMailing.datum(UPDATED_DATUM).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restMailingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMailing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMailing))
            )
            .andExpect(status().isOk());

        // Validate the Mailing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMailingToMatchAllProperties(updatedMailing);
    }

    @Test
    @Transactional
    void putNonExistingMailing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mailing.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMailingMockMvc
            .perform(put(ENTITY_API_URL_ID, mailing.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mailing)))
            .andExpect(status().isBadRequest());

        // Validate the Mailing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMailing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mailing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMailingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(mailing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mailing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMailing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mailing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMailingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mailing)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mailing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMailingWithPatch() throws Exception {
        // Initialize the database
        mailingRepository.saveAndFlush(mailing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mailing using partial update
        Mailing partialUpdatedMailing = new Mailing();
        partialUpdatedMailing.setId(mailing.getId());

        partialUpdatedMailing.datum(UPDATED_DATUM);

        restMailingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMailing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMailing))
            )
            .andExpect(status().isOk());

        // Validate the Mailing in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMailingUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedMailing, mailing), getPersistedMailing(mailing));
    }

    @Test
    @Transactional
    void fullUpdateMailingWithPatch() throws Exception {
        // Initialize the database
        mailingRepository.saveAndFlush(mailing);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mailing using partial update
        Mailing partialUpdatedMailing = new Mailing();
        partialUpdatedMailing.setId(mailing.getId());

        partialUpdatedMailing.datum(UPDATED_DATUM).naam(UPDATED_NAAM).omschrijving(UPDATED_OMSCHRIJVING);

        restMailingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMailing.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMailing))
            )
            .andExpect(status().isOk());

        // Validate the Mailing in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMailingUpdatableFieldsEquals(partialUpdatedMailing, getPersistedMailing(partialUpdatedMailing));
    }

    @Test
    @Transactional
    void patchNonExistingMailing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mailing.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMailingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mailing.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(mailing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mailing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMailing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mailing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMailingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(mailing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mailing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMailing() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mailing.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMailingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(mailing)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mailing in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMailing() throws Exception {
        // Initialize the database
        mailingRepository.saveAndFlush(mailing);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the mailing
        restMailingMockMvc
            .perform(delete(ENTITY_API_URL_ID, mailing.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return mailingRepository.count();
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

    protected Mailing getPersistedMailing(Mailing mailing) {
        return mailingRepository.findById(mailing.getId()).orElseThrow();
    }

    protected void assertPersistedMailingToMatchAllProperties(Mailing expectedMailing) {
        assertMailingAllPropertiesEquals(expectedMailing, getPersistedMailing(expectedMailing));
    }

    protected void assertPersistedMailingToMatchUpdatableProperties(Mailing expectedMailing) {
        assertMailingAllUpdatablePropertiesEquals(expectedMailing, getPersistedMailing(expectedMailing));
    }
}
