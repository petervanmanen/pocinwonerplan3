package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AutoriteitafgiftenederlandsreisdocumentAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Autoriteitafgiftenederlandsreisdocument;
import nl.ritense.demo.repository.AutoriteitafgiftenederlandsreisdocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AutoriteitafgiftenederlandsreisdocumentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AutoriteitafgiftenederlandsreisdocumentResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDAUTORITEITVANAFGIFTE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDAUTORITEITVANAFGIFTE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDAUTORITEITVANAFGIFTE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDAUTORITEITVANAFGIFTE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/autoriteitafgiftenederlandsreisdocuments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AutoriteitafgiftenederlandsreisdocumentRepository autoriteitafgiftenederlandsreisdocumentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAutoriteitafgiftenederlandsreisdocumentMockMvc;

    private Autoriteitafgiftenederlandsreisdocument autoriteitafgiftenederlandsreisdocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autoriteitafgiftenederlandsreisdocument createEntity(EntityManager em) {
        Autoriteitafgiftenederlandsreisdocument autoriteitafgiftenederlandsreisdocument = new Autoriteitafgiftenederlandsreisdocument()
            .code(DEFAULT_CODE)
            .datumbegingeldigheidautoriteitvanafgifte(DEFAULT_DATUMBEGINGELDIGHEIDAUTORITEITVANAFGIFTE)
            .datumeindegeldigheidautoriteitvanafgifte(DEFAULT_DATUMEINDEGELDIGHEIDAUTORITEITVANAFGIFTE)
            .omschrijving(DEFAULT_OMSCHRIJVING);
        return autoriteitafgiftenederlandsreisdocument;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autoriteitafgiftenederlandsreisdocument createUpdatedEntity(EntityManager em) {
        Autoriteitafgiftenederlandsreisdocument autoriteitafgiftenederlandsreisdocument = new Autoriteitafgiftenederlandsreisdocument()
            .code(UPDATED_CODE)
            .datumbegingeldigheidautoriteitvanafgifte(UPDATED_DATUMBEGINGELDIGHEIDAUTORITEITVANAFGIFTE)
            .datumeindegeldigheidautoriteitvanafgifte(UPDATED_DATUMEINDEGELDIGHEIDAUTORITEITVANAFGIFTE)
            .omschrijving(UPDATED_OMSCHRIJVING);
        return autoriteitafgiftenederlandsreisdocument;
    }

    @BeforeEach
    public void initTest() {
        autoriteitafgiftenederlandsreisdocument = createEntity(em);
    }

    @Test
    @Transactional
    void createAutoriteitafgiftenederlandsreisdocument() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Autoriteitafgiftenederlandsreisdocument
        var returnedAutoriteitafgiftenederlandsreisdocument = om.readValue(
            restAutoriteitafgiftenederlandsreisdocumentMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(autoriteitafgiftenederlandsreisdocument))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Autoriteitafgiftenederlandsreisdocument.class
        );

        // Validate the Autoriteitafgiftenederlandsreisdocument in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAutoriteitafgiftenederlandsreisdocumentUpdatableFieldsEquals(
            returnedAutoriteitafgiftenederlandsreisdocument,
            getPersistedAutoriteitafgiftenederlandsreisdocument(returnedAutoriteitafgiftenederlandsreisdocument)
        );
    }

    @Test
    @Transactional
    void createAutoriteitafgiftenederlandsreisdocumentWithExistingId() throws Exception {
        // Create the Autoriteitafgiftenederlandsreisdocument with an existing ID
        autoriteitafgiftenederlandsreisdocument.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutoriteitafgiftenederlandsreisdocumentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autoriteitafgiftenederlandsreisdocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autoriteitafgiftenederlandsreisdocument in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAutoriteitafgiftenederlandsreisdocuments() throws Exception {
        // Initialize the database
        autoriteitafgiftenederlandsreisdocumentRepository.saveAndFlush(autoriteitafgiftenederlandsreisdocument);

        // Get all the autoriteitafgiftenederlandsreisdocumentList
        restAutoriteitafgiftenederlandsreisdocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autoriteitafgiftenederlandsreisdocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(
                jsonPath("$.[*].datumbegingeldigheidautoriteitvanafgifte").value(
                    hasItem(DEFAULT_DATUMBEGINGELDIGHEIDAUTORITEITVANAFGIFTE.toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].datumeindegeldigheidautoriteitvanafgifte").value(
                    hasItem(DEFAULT_DATUMEINDEGELDIGHEIDAUTORITEITVANAFGIFTE.toString())
                )
            )
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)));
    }

    @Test
    @Transactional
    void getAutoriteitafgiftenederlandsreisdocument() throws Exception {
        // Initialize the database
        autoriteitafgiftenederlandsreisdocumentRepository.saveAndFlush(autoriteitafgiftenederlandsreisdocument);

        // Get the autoriteitafgiftenederlandsreisdocument
        restAutoriteitafgiftenederlandsreisdocumentMockMvc
            .perform(get(ENTITY_API_URL_ID, autoriteitafgiftenederlandsreisdocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(autoriteitafgiftenederlandsreisdocument.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(
                jsonPath("$.datumbegingeldigheidautoriteitvanafgifte").value(DEFAULT_DATUMBEGINGELDIGHEIDAUTORITEITVANAFGIFTE.toString())
            )
            .andExpect(
                jsonPath("$.datumeindegeldigheidautoriteitvanafgifte").value(DEFAULT_DATUMEINDEGELDIGHEIDAUTORITEITVANAFGIFTE.toString())
            )
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING));
    }

    @Test
    @Transactional
    void getNonExistingAutoriteitafgiftenederlandsreisdocument() throws Exception {
        // Get the autoriteitafgiftenederlandsreisdocument
        restAutoriteitafgiftenederlandsreisdocumentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAutoriteitafgiftenederlandsreisdocument() throws Exception {
        // Initialize the database
        autoriteitafgiftenederlandsreisdocumentRepository.saveAndFlush(autoriteitafgiftenederlandsreisdocument);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autoriteitafgiftenederlandsreisdocument
        Autoriteitafgiftenederlandsreisdocument updatedAutoriteitafgiftenederlandsreisdocument =
            autoriteitafgiftenederlandsreisdocumentRepository.findById(autoriteitafgiftenederlandsreisdocument.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAutoriteitafgiftenederlandsreisdocument are not directly saved in db
        em.detach(updatedAutoriteitafgiftenederlandsreisdocument);
        updatedAutoriteitafgiftenederlandsreisdocument
            .code(UPDATED_CODE)
            .datumbegingeldigheidautoriteitvanafgifte(UPDATED_DATUMBEGINGELDIGHEIDAUTORITEITVANAFGIFTE)
            .datumeindegeldigheidautoriteitvanafgifte(UPDATED_DATUMEINDEGELDIGHEIDAUTORITEITVANAFGIFTE)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restAutoriteitafgiftenederlandsreisdocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAutoriteitafgiftenederlandsreisdocument.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAutoriteitafgiftenederlandsreisdocument))
            )
            .andExpect(status().isOk());

        // Validate the Autoriteitafgiftenederlandsreisdocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAutoriteitafgiftenederlandsreisdocumentToMatchAllProperties(updatedAutoriteitafgiftenederlandsreisdocument);
    }

    @Test
    @Transactional
    void putNonExistingAutoriteitafgiftenederlandsreisdocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autoriteitafgiftenederlandsreisdocument.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutoriteitafgiftenederlandsreisdocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, autoriteitafgiftenederlandsreisdocument.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autoriteitafgiftenederlandsreisdocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autoriteitafgiftenederlandsreisdocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAutoriteitafgiftenederlandsreisdocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autoriteitafgiftenederlandsreisdocument.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutoriteitafgiftenederlandsreisdocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autoriteitafgiftenederlandsreisdocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autoriteitafgiftenederlandsreisdocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAutoriteitafgiftenederlandsreisdocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autoriteitafgiftenederlandsreisdocument.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutoriteitafgiftenederlandsreisdocumentMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autoriteitafgiftenederlandsreisdocument))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Autoriteitafgiftenederlandsreisdocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAutoriteitafgiftenederlandsreisdocumentWithPatch() throws Exception {
        // Initialize the database
        autoriteitafgiftenederlandsreisdocumentRepository.saveAndFlush(autoriteitafgiftenederlandsreisdocument);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autoriteitafgiftenederlandsreisdocument using partial update
        Autoriteitafgiftenederlandsreisdocument partialUpdatedAutoriteitafgiftenederlandsreisdocument =
            new Autoriteitafgiftenederlandsreisdocument();
        partialUpdatedAutoriteitafgiftenederlandsreisdocument.setId(autoriteitafgiftenederlandsreisdocument.getId());

        partialUpdatedAutoriteitafgiftenederlandsreisdocument
            .datumbegingeldigheidautoriteitvanafgifte(UPDATED_DATUMBEGINGELDIGHEIDAUTORITEITVANAFGIFTE)
            .datumeindegeldigheidautoriteitvanafgifte(UPDATED_DATUMEINDEGELDIGHEIDAUTORITEITVANAFGIFTE);

        restAutoriteitafgiftenederlandsreisdocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutoriteitafgiftenederlandsreisdocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAutoriteitafgiftenederlandsreisdocument))
            )
            .andExpect(status().isOk());

        // Validate the Autoriteitafgiftenederlandsreisdocument in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAutoriteitafgiftenederlandsreisdocumentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAutoriteitafgiftenederlandsreisdocument, autoriteitafgiftenederlandsreisdocument),
            getPersistedAutoriteitafgiftenederlandsreisdocument(autoriteitafgiftenederlandsreisdocument)
        );
    }

    @Test
    @Transactional
    void fullUpdateAutoriteitafgiftenederlandsreisdocumentWithPatch() throws Exception {
        // Initialize the database
        autoriteitafgiftenederlandsreisdocumentRepository.saveAndFlush(autoriteitafgiftenederlandsreisdocument);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autoriteitafgiftenederlandsreisdocument using partial update
        Autoriteitafgiftenederlandsreisdocument partialUpdatedAutoriteitafgiftenederlandsreisdocument =
            new Autoriteitafgiftenederlandsreisdocument();
        partialUpdatedAutoriteitafgiftenederlandsreisdocument.setId(autoriteitafgiftenederlandsreisdocument.getId());

        partialUpdatedAutoriteitafgiftenederlandsreisdocument
            .code(UPDATED_CODE)
            .datumbegingeldigheidautoriteitvanafgifte(UPDATED_DATUMBEGINGELDIGHEIDAUTORITEITVANAFGIFTE)
            .datumeindegeldigheidautoriteitvanafgifte(UPDATED_DATUMEINDEGELDIGHEIDAUTORITEITVANAFGIFTE)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restAutoriteitafgiftenederlandsreisdocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutoriteitafgiftenederlandsreisdocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAutoriteitafgiftenederlandsreisdocument))
            )
            .andExpect(status().isOk());

        // Validate the Autoriteitafgiftenederlandsreisdocument in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAutoriteitafgiftenederlandsreisdocumentUpdatableFieldsEquals(
            partialUpdatedAutoriteitafgiftenederlandsreisdocument,
            getPersistedAutoriteitafgiftenederlandsreisdocument(partialUpdatedAutoriteitafgiftenederlandsreisdocument)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAutoriteitafgiftenederlandsreisdocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autoriteitafgiftenederlandsreisdocument.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutoriteitafgiftenederlandsreisdocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, autoriteitafgiftenederlandsreisdocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autoriteitafgiftenederlandsreisdocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autoriteitafgiftenederlandsreisdocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAutoriteitafgiftenederlandsreisdocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autoriteitafgiftenederlandsreisdocument.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutoriteitafgiftenederlandsreisdocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autoriteitafgiftenederlandsreisdocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autoriteitafgiftenederlandsreisdocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAutoriteitafgiftenederlandsreisdocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autoriteitafgiftenederlandsreisdocument.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutoriteitafgiftenederlandsreisdocumentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autoriteitafgiftenederlandsreisdocument))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Autoriteitafgiftenederlandsreisdocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAutoriteitafgiftenederlandsreisdocument() throws Exception {
        // Initialize the database
        autoriteitafgiftenederlandsreisdocumentRepository.saveAndFlush(autoriteitafgiftenederlandsreisdocument);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the autoriteitafgiftenederlandsreisdocument
        restAutoriteitafgiftenederlandsreisdocumentMockMvc
            .perform(delete(ENTITY_API_URL_ID, autoriteitafgiftenederlandsreisdocument.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return autoriteitafgiftenederlandsreisdocumentRepository.count();
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

    protected Autoriteitafgiftenederlandsreisdocument getPersistedAutoriteitafgiftenederlandsreisdocument(
        Autoriteitafgiftenederlandsreisdocument autoriteitafgiftenederlandsreisdocument
    ) {
        return autoriteitafgiftenederlandsreisdocumentRepository.findById(autoriteitafgiftenederlandsreisdocument.getId()).orElseThrow();
    }

    protected void assertPersistedAutoriteitafgiftenederlandsreisdocumentToMatchAllProperties(
        Autoriteitafgiftenederlandsreisdocument expectedAutoriteitafgiftenederlandsreisdocument
    ) {
        assertAutoriteitafgiftenederlandsreisdocumentAllPropertiesEquals(
            expectedAutoriteitafgiftenederlandsreisdocument,
            getPersistedAutoriteitafgiftenederlandsreisdocument(expectedAutoriteitafgiftenederlandsreisdocument)
        );
    }

    protected void assertPersistedAutoriteitafgiftenederlandsreisdocumentToMatchUpdatableProperties(
        Autoriteitafgiftenederlandsreisdocument expectedAutoriteitafgiftenederlandsreisdocument
    ) {
        assertAutoriteitafgiftenederlandsreisdocumentAllUpdatablePropertiesEquals(
            expectedAutoriteitafgiftenederlandsreisdocument,
            getPersistedAutoriteitafgiftenederlandsreisdocument(expectedAutoriteitafgiftenederlandsreisdocument)
        );
    }
}
