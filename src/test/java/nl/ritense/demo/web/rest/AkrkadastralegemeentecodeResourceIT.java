package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AkrkadastralegemeentecodeAsserts.*;
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
import nl.ritense.demo.domain.Akrkadastralegemeentecode;
import nl.ritense.demo.repository.AkrkadastralegemeentecodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AkrkadastralegemeentecodeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AkrkadastralegemeentecodeResourceIT {

    private static final String DEFAULT_AKRCODE = "AAAAAAAAAA";
    private static final String UPDATED_AKRCODE = "BBBBBBBBBB";

    private static final String DEFAULT_CODEAKRKADADASTRALEGEMEENTECODE = "AAAAAAAAAA";
    private static final String UPDATED_CODEAKRKADADASTRALEGEMEENTECODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMBEGINGELDIGHEIDAKRCODE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBEGINGELDIGHEIDAKRCODE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMEINDEGELDIGHEIDAKRCODE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDEGELDIGHEIDAKRCODE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/akrkadastralegemeentecodes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AkrkadastralegemeentecodeRepository akrkadastralegemeentecodeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAkrkadastralegemeentecodeMockMvc;

    private Akrkadastralegemeentecode akrkadastralegemeentecode;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Akrkadastralegemeentecode createEntity(EntityManager em) {
        Akrkadastralegemeentecode akrkadastralegemeentecode = new Akrkadastralegemeentecode()
            .akrcode(DEFAULT_AKRCODE)
            .codeakrkadadastralegemeentecode(DEFAULT_CODEAKRKADADASTRALEGEMEENTECODE)
            .datumbegingeldigheidakrcode(DEFAULT_DATUMBEGINGELDIGHEIDAKRCODE)
            .datumeindegeldigheidakrcode(DEFAULT_DATUMEINDEGELDIGHEIDAKRCODE);
        return akrkadastralegemeentecode;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Akrkadastralegemeentecode createUpdatedEntity(EntityManager em) {
        Akrkadastralegemeentecode akrkadastralegemeentecode = new Akrkadastralegemeentecode()
            .akrcode(UPDATED_AKRCODE)
            .codeakrkadadastralegemeentecode(UPDATED_CODEAKRKADADASTRALEGEMEENTECODE)
            .datumbegingeldigheidakrcode(UPDATED_DATUMBEGINGELDIGHEIDAKRCODE)
            .datumeindegeldigheidakrcode(UPDATED_DATUMEINDEGELDIGHEIDAKRCODE);
        return akrkadastralegemeentecode;
    }

    @BeforeEach
    public void initTest() {
        akrkadastralegemeentecode = createEntity(em);
    }

    @Test
    @Transactional
    void createAkrkadastralegemeentecode() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Akrkadastralegemeentecode
        var returnedAkrkadastralegemeentecode = om.readValue(
            restAkrkadastralegemeentecodeMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(akrkadastralegemeentecode))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Akrkadastralegemeentecode.class
        );

        // Validate the Akrkadastralegemeentecode in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAkrkadastralegemeentecodeUpdatableFieldsEquals(
            returnedAkrkadastralegemeentecode,
            getPersistedAkrkadastralegemeentecode(returnedAkrkadastralegemeentecode)
        );
    }

    @Test
    @Transactional
    void createAkrkadastralegemeentecodeWithExistingId() throws Exception {
        // Create the Akrkadastralegemeentecode with an existing ID
        akrkadastralegemeentecode.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAkrkadastralegemeentecodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(akrkadastralegemeentecode)))
            .andExpect(status().isBadRequest());

        // Validate the Akrkadastralegemeentecode in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAkrkadastralegemeentecodes() throws Exception {
        // Initialize the database
        akrkadastralegemeentecodeRepository.saveAndFlush(akrkadastralegemeentecode);

        // Get all the akrkadastralegemeentecodeList
        restAkrkadastralegemeentecodeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(akrkadastralegemeentecode.getId().intValue())))
            .andExpect(jsonPath("$.[*].akrcode").value(hasItem(DEFAULT_AKRCODE)))
            .andExpect(jsonPath("$.[*].codeakrkadadastralegemeentecode").value(hasItem(DEFAULT_CODEAKRKADADASTRALEGEMEENTECODE)))
            .andExpect(jsonPath("$.[*].datumbegingeldigheidakrcode").value(hasItem(DEFAULT_DATUMBEGINGELDIGHEIDAKRCODE.toString())))
            .andExpect(jsonPath("$.[*].datumeindegeldigheidakrcode").value(hasItem(DEFAULT_DATUMEINDEGELDIGHEIDAKRCODE.toString())));
    }

    @Test
    @Transactional
    void getAkrkadastralegemeentecode() throws Exception {
        // Initialize the database
        akrkadastralegemeentecodeRepository.saveAndFlush(akrkadastralegemeentecode);

        // Get the akrkadastralegemeentecode
        restAkrkadastralegemeentecodeMockMvc
            .perform(get(ENTITY_API_URL_ID, akrkadastralegemeentecode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(akrkadastralegemeentecode.getId().intValue()))
            .andExpect(jsonPath("$.akrcode").value(DEFAULT_AKRCODE))
            .andExpect(jsonPath("$.codeakrkadadastralegemeentecode").value(DEFAULT_CODEAKRKADADASTRALEGEMEENTECODE))
            .andExpect(jsonPath("$.datumbegingeldigheidakrcode").value(DEFAULT_DATUMBEGINGELDIGHEIDAKRCODE.toString()))
            .andExpect(jsonPath("$.datumeindegeldigheidakrcode").value(DEFAULT_DATUMEINDEGELDIGHEIDAKRCODE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAkrkadastralegemeentecode() throws Exception {
        // Get the akrkadastralegemeentecode
        restAkrkadastralegemeentecodeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAkrkadastralegemeentecode() throws Exception {
        // Initialize the database
        akrkadastralegemeentecodeRepository.saveAndFlush(akrkadastralegemeentecode);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the akrkadastralegemeentecode
        Akrkadastralegemeentecode updatedAkrkadastralegemeentecode = akrkadastralegemeentecodeRepository
            .findById(akrkadastralegemeentecode.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAkrkadastralegemeentecode are not directly saved in db
        em.detach(updatedAkrkadastralegemeentecode);
        updatedAkrkadastralegemeentecode
            .akrcode(UPDATED_AKRCODE)
            .codeakrkadadastralegemeentecode(UPDATED_CODEAKRKADADASTRALEGEMEENTECODE)
            .datumbegingeldigheidakrcode(UPDATED_DATUMBEGINGELDIGHEIDAKRCODE)
            .datumeindegeldigheidakrcode(UPDATED_DATUMEINDEGELDIGHEIDAKRCODE);

        restAkrkadastralegemeentecodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAkrkadastralegemeentecode.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAkrkadastralegemeentecode))
            )
            .andExpect(status().isOk());

        // Validate the Akrkadastralegemeentecode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAkrkadastralegemeentecodeToMatchAllProperties(updatedAkrkadastralegemeentecode);
    }

    @Test
    @Transactional
    void putNonExistingAkrkadastralegemeentecode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        akrkadastralegemeentecode.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAkrkadastralegemeentecodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, akrkadastralegemeentecode.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(akrkadastralegemeentecode))
            )
            .andExpect(status().isBadRequest());

        // Validate the Akrkadastralegemeentecode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAkrkadastralegemeentecode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        akrkadastralegemeentecode.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAkrkadastralegemeentecodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(akrkadastralegemeentecode))
            )
            .andExpect(status().isBadRequest());

        // Validate the Akrkadastralegemeentecode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAkrkadastralegemeentecode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        akrkadastralegemeentecode.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAkrkadastralegemeentecodeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(akrkadastralegemeentecode)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Akrkadastralegemeentecode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAkrkadastralegemeentecodeWithPatch() throws Exception {
        // Initialize the database
        akrkadastralegemeentecodeRepository.saveAndFlush(akrkadastralegemeentecode);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the akrkadastralegemeentecode using partial update
        Akrkadastralegemeentecode partialUpdatedAkrkadastralegemeentecode = new Akrkadastralegemeentecode();
        partialUpdatedAkrkadastralegemeentecode.setId(akrkadastralegemeentecode.getId());

        partialUpdatedAkrkadastralegemeentecode
            .akrcode(UPDATED_AKRCODE)
            .codeakrkadadastralegemeentecode(UPDATED_CODEAKRKADADASTRALEGEMEENTECODE)
            .datumbegingeldigheidakrcode(UPDATED_DATUMBEGINGELDIGHEIDAKRCODE);

        restAkrkadastralegemeentecodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAkrkadastralegemeentecode.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAkrkadastralegemeentecode))
            )
            .andExpect(status().isOk());

        // Validate the Akrkadastralegemeentecode in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAkrkadastralegemeentecodeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAkrkadastralegemeentecode, akrkadastralegemeentecode),
            getPersistedAkrkadastralegemeentecode(akrkadastralegemeentecode)
        );
    }

    @Test
    @Transactional
    void fullUpdateAkrkadastralegemeentecodeWithPatch() throws Exception {
        // Initialize the database
        akrkadastralegemeentecodeRepository.saveAndFlush(akrkadastralegemeentecode);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the akrkadastralegemeentecode using partial update
        Akrkadastralegemeentecode partialUpdatedAkrkadastralegemeentecode = new Akrkadastralegemeentecode();
        partialUpdatedAkrkadastralegemeentecode.setId(akrkadastralegemeentecode.getId());

        partialUpdatedAkrkadastralegemeentecode
            .akrcode(UPDATED_AKRCODE)
            .codeakrkadadastralegemeentecode(UPDATED_CODEAKRKADADASTRALEGEMEENTECODE)
            .datumbegingeldigheidakrcode(UPDATED_DATUMBEGINGELDIGHEIDAKRCODE)
            .datumeindegeldigheidakrcode(UPDATED_DATUMEINDEGELDIGHEIDAKRCODE);

        restAkrkadastralegemeentecodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAkrkadastralegemeentecode.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAkrkadastralegemeentecode))
            )
            .andExpect(status().isOk());

        // Validate the Akrkadastralegemeentecode in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAkrkadastralegemeentecodeUpdatableFieldsEquals(
            partialUpdatedAkrkadastralegemeentecode,
            getPersistedAkrkadastralegemeentecode(partialUpdatedAkrkadastralegemeentecode)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAkrkadastralegemeentecode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        akrkadastralegemeentecode.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAkrkadastralegemeentecodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, akrkadastralegemeentecode.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(akrkadastralegemeentecode))
            )
            .andExpect(status().isBadRequest());

        // Validate the Akrkadastralegemeentecode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAkrkadastralegemeentecode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        akrkadastralegemeentecode.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAkrkadastralegemeentecodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(akrkadastralegemeentecode))
            )
            .andExpect(status().isBadRequest());

        // Validate the Akrkadastralegemeentecode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAkrkadastralegemeentecode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        akrkadastralegemeentecode.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAkrkadastralegemeentecodeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(akrkadastralegemeentecode))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Akrkadastralegemeentecode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAkrkadastralegemeentecode() throws Exception {
        // Initialize the database
        akrkadastralegemeentecodeRepository.saveAndFlush(akrkadastralegemeentecode);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the akrkadastralegemeentecode
        restAkrkadastralegemeentecodeMockMvc
            .perform(delete(ENTITY_API_URL_ID, akrkadastralegemeentecode.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return akrkadastralegemeentecodeRepository.count();
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

    protected Akrkadastralegemeentecode getPersistedAkrkadastralegemeentecode(Akrkadastralegemeentecode akrkadastralegemeentecode) {
        return akrkadastralegemeentecodeRepository.findById(akrkadastralegemeentecode.getId()).orElseThrow();
    }

    protected void assertPersistedAkrkadastralegemeentecodeToMatchAllProperties(
        Akrkadastralegemeentecode expectedAkrkadastralegemeentecode
    ) {
        assertAkrkadastralegemeentecodeAllPropertiesEquals(
            expectedAkrkadastralegemeentecode,
            getPersistedAkrkadastralegemeentecode(expectedAkrkadastralegemeentecode)
        );
    }

    protected void assertPersistedAkrkadastralegemeentecodeToMatchUpdatableProperties(
        Akrkadastralegemeentecode expectedAkrkadastralegemeentecode
    ) {
        assertAkrkadastralegemeentecodeAllUpdatablePropertiesEquals(
            expectedAkrkadastralegemeentecode,
            getPersistedAkrkadastralegemeentecode(expectedAkrkadastralegemeentecode)
        );
    }
}
