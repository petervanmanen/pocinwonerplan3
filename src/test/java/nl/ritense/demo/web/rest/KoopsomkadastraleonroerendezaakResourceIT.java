package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.KoopsomkadastraleonroerendezaakAsserts.*;
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
import nl.ritense.demo.domain.Koopsomkadastraleonroerendezaak;
import nl.ritense.demo.repository.KoopsomkadastraleonroerendezaakRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KoopsomkadastraleonroerendezaakResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KoopsomkadastraleonroerendezaakResourceIT {

    private static final String DEFAULT_DATUMTRANSACTIE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMTRANSACTIE = "BBBBBBBBBB";

    private static final String DEFAULT_KOOPSOM = "AAAAAAAAAA";
    private static final String UPDATED_KOOPSOM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/koopsomkadastraleonroerendezaaks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private KoopsomkadastraleonroerendezaakRepository koopsomkadastraleonroerendezaakRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKoopsomkadastraleonroerendezaakMockMvc;

    private Koopsomkadastraleonroerendezaak koopsomkadastraleonroerendezaak;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Koopsomkadastraleonroerendezaak createEntity(EntityManager em) {
        Koopsomkadastraleonroerendezaak koopsomkadastraleonroerendezaak = new Koopsomkadastraleonroerendezaak()
            .datumtransactie(DEFAULT_DATUMTRANSACTIE)
            .koopsom(DEFAULT_KOOPSOM);
        return koopsomkadastraleonroerendezaak;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Koopsomkadastraleonroerendezaak createUpdatedEntity(EntityManager em) {
        Koopsomkadastraleonroerendezaak koopsomkadastraleonroerendezaak = new Koopsomkadastraleonroerendezaak()
            .datumtransactie(UPDATED_DATUMTRANSACTIE)
            .koopsom(UPDATED_KOOPSOM);
        return koopsomkadastraleonroerendezaak;
    }

    @BeforeEach
    public void initTest() {
        koopsomkadastraleonroerendezaak = createEntity(em);
    }

    @Test
    @Transactional
    void createKoopsomkadastraleonroerendezaak() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Koopsomkadastraleonroerendezaak
        var returnedKoopsomkadastraleonroerendezaak = om.readValue(
            restKoopsomkadastraleonroerendezaakMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(koopsomkadastraleonroerendezaak))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Koopsomkadastraleonroerendezaak.class
        );

        // Validate the Koopsomkadastraleonroerendezaak in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertKoopsomkadastraleonroerendezaakUpdatableFieldsEquals(
            returnedKoopsomkadastraleonroerendezaak,
            getPersistedKoopsomkadastraleonroerendezaak(returnedKoopsomkadastraleonroerendezaak)
        );
    }

    @Test
    @Transactional
    void createKoopsomkadastraleonroerendezaakWithExistingId() throws Exception {
        // Create the Koopsomkadastraleonroerendezaak with an existing ID
        koopsomkadastraleonroerendezaak.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKoopsomkadastraleonroerendezaakMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(koopsomkadastraleonroerendezaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Koopsomkadastraleonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKoopsomkadastraleonroerendezaaks() throws Exception {
        // Initialize the database
        koopsomkadastraleonroerendezaakRepository.saveAndFlush(koopsomkadastraleonroerendezaak);

        // Get all the koopsomkadastraleonroerendezaakList
        restKoopsomkadastraleonroerendezaakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(koopsomkadastraleonroerendezaak.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumtransactie").value(hasItem(DEFAULT_DATUMTRANSACTIE)))
            .andExpect(jsonPath("$.[*].koopsom").value(hasItem(DEFAULT_KOOPSOM)));
    }

    @Test
    @Transactional
    void getKoopsomkadastraleonroerendezaak() throws Exception {
        // Initialize the database
        koopsomkadastraleonroerendezaakRepository.saveAndFlush(koopsomkadastraleonroerendezaak);

        // Get the koopsomkadastraleonroerendezaak
        restKoopsomkadastraleonroerendezaakMockMvc
            .perform(get(ENTITY_API_URL_ID, koopsomkadastraleonroerendezaak.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(koopsomkadastraleonroerendezaak.getId().intValue()))
            .andExpect(jsonPath("$.datumtransactie").value(DEFAULT_DATUMTRANSACTIE))
            .andExpect(jsonPath("$.koopsom").value(DEFAULT_KOOPSOM));
    }

    @Test
    @Transactional
    void getNonExistingKoopsomkadastraleonroerendezaak() throws Exception {
        // Get the koopsomkadastraleonroerendezaak
        restKoopsomkadastraleonroerendezaakMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKoopsomkadastraleonroerendezaak() throws Exception {
        // Initialize the database
        koopsomkadastraleonroerendezaakRepository.saveAndFlush(koopsomkadastraleonroerendezaak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the koopsomkadastraleonroerendezaak
        Koopsomkadastraleonroerendezaak updatedKoopsomkadastraleonroerendezaak = koopsomkadastraleonroerendezaakRepository
            .findById(koopsomkadastraleonroerendezaak.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedKoopsomkadastraleonroerendezaak are not directly saved in db
        em.detach(updatedKoopsomkadastraleonroerendezaak);
        updatedKoopsomkadastraleonroerendezaak.datumtransactie(UPDATED_DATUMTRANSACTIE).koopsom(UPDATED_KOOPSOM);

        restKoopsomkadastraleonroerendezaakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKoopsomkadastraleonroerendezaak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedKoopsomkadastraleonroerendezaak))
            )
            .andExpect(status().isOk());

        // Validate the Koopsomkadastraleonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedKoopsomkadastraleonroerendezaakToMatchAllProperties(updatedKoopsomkadastraleonroerendezaak);
    }

    @Test
    @Transactional
    void putNonExistingKoopsomkadastraleonroerendezaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        koopsomkadastraleonroerendezaak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKoopsomkadastraleonroerendezaakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, koopsomkadastraleonroerendezaak.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(koopsomkadastraleonroerendezaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Koopsomkadastraleonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKoopsomkadastraleonroerendezaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        koopsomkadastraleonroerendezaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKoopsomkadastraleonroerendezaakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(koopsomkadastraleonroerendezaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Koopsomkadastraleonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKoopsomkadastraleonroerendezaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        koopsomkadastraleonroerendezaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKoopsomkadastraleonroerendezaakMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(koopsomkadastraleonroerendezaak))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Koopsomkadastraleonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKoopsomkadastraleonroerendezaakWithPatch() throws Exception {
        // Initialize the database
        koopsomkadastraleonroerendezaakRepository.saveAndFlush(koopsomkadastraleonroerendezaak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the koopsomkadastraleonroerendezaak using partial update
        Koopsomkadastraleonroerendezaak partialUpdatedKoopsomkadastraleonroerendezaak = new Koopsomkadastraleonroerendezaak();
        partialUpdatedKoopsomkadastraleonroerendezaak.setId(koopsomkadastraleonroerendezaak.getId());

        restKoopsomkadastraleonroerendezaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKoopsomkadastraleonroerendezaak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKoopsomkadastraleonroerendezaak))
            )
            .andExpect(status().isOk());

        // Validate the Koopsomkadastraleonroerendezaak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKoopsomkadastraleonroerendezaakUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedKoopsomkadastraleonroerendezaak, koopsomkadastraleonroerendezaak),
            getPersistedKoopsomkadastraleonroerendezaak(koopsomkadastraleonroerendezaak)
        );
    }

    @Test
    @Transactional
    void fullUpdateKoopsomkadastraleonroerendezaakWithPatch() throws Exception {
        // Initialize the database
        koopsomkadastraleonroerendezaakRepository.saveAndFlush(koopsomkadastraleonroerendezaak);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the koopsomkadastraleonroerendezaak using partial update
        Koopsomkadastraleonroerendezaak partialUpdatedKoopsomkadastraleonroerendezaak = new Koopsomkadastraleonroerendezaak();
        partialUpdatedKoopsomkadastraleonroerendezaak.setId(koopsomkadastraleonroerendezaak.getId());

        partialUpdatedKoopsomkadastraleonroerendezaak.datumtransactie(UPDATED_DATUMTRANSACTIE).koopsom(UPDATED_KOOPSOM);

        restKoopsomkadastraleonroerendezaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKoopsomkadastraleonroerendezaak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedKoopsomkadastraleonroerendezaak))
            )
            .andExpect(status().isOk());

        // Validate the Koopsomkadastraleonroerendezaak in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertKoopsomkadastraleonroerendezaakUpdatableFieldsEquals(
            partialUpdatedKoopsomkadastraleonroerendezaak,
            getPersistedKoopsomkadastraleonroerendezaak(partialUpdatedKoopsomkadastraleonroerendezaak)
        );
    }

    @Test
    @Transactional
    void patchNonExistingKoopsomkadastraleonroerendezaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        koopsomkadastraleonroerendezaak.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKoopsomkadastraleonroerendezaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, koopsomkadastraleonroerendezaak.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(koopsomkadastraleonroerendezaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Koopsomkadastraleonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKoopsomkadastraleonroerendezaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        koopsomkadastraleonroerendezaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKoopsomkadastraleonroerendezaakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(koopsomkadastraleonroerendezaak))
            )
            .andExpect(status().isBadRequest());

        // Validate the Koopsomkadastraleonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKoopsomkadastraleonroerendezaak() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        koopsomkadastraleonroerendezaak.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKoopsomkadastraleonroerendezaakMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(koopsomkadastraleonroerendezaak))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Koopsomkadastraleonroerendezaak in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKoopsomkadastraleonroerendezaak() throws Exception {
        // Initialize the database
        koopsomkadastraleonroerendezaakRepository.saveAndFlush(koopsomkadastraleonroerendezaak);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the koopsomkadastraleonroerendezaak
        restKoopsomkadastraleonroerendezaakMockMvc
            .perform(delete(ENTITY_API_URL_ID, koopsomkadastraleonroerendezaak.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return koopsomkadastraleonroerendezaakRepository.count();
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

    protected Koopsomkadastraleonroerendezaak getPersistedKoopsomkadastraleonroerendezaak(
        Koopsomkadastraleonroerendezaak koopsomkadastraleonroerendezaak
    ) {
        return koopsomkadastraleonroerendezaakRepository.findById(koopsomkadastraleonroerendezaak.getId()).orElseThrow();
    }

    protected void assertPersistedKoopsomkadastraleonroerendezaakToMatchAllProperties(
        Koopsomkadastraleonroerendezaak expectedKoopsomkadastraleonroerendezaak
    ) {
        assertKoopsomkadastraleonroerendezaakAllPropertiesEquals(
            expectedKoopsomkadastraleonroerendezaak,
            getPersistedKoopsomkadastraleonroerendezaak(expectedKoopsomkadastraleonroerendezaak)
        );
    }

    protected void assertPersistedKoopsomkadastraleonroerendezaakToMatchUpdatableProperties(
        Koopsomkadastraleonroerendezaak expectedKoopsomkadastraleonroerendezaak
    ) {
        assertKoopsomkadastraleonroerendezaakAllUpdatablePropertiesEquals(
            expectedKoopsomkadastraleonroerendezaak,
            getPersistedKoopsomkadastraleonroerendezaak(expectedKoopsomkadastraleonroerendezaak)
        );
    }
}
