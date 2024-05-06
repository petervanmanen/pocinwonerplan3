package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.AanvraaginkooporderAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static nl.ritense.demo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Aanvraaginkooporder;
import nl.ritense.demo.repository.AanvraaginkooporderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AanvraaginkooporderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AanvraaginkooporderResourceIT {

    private static final String DEFAULT_BETALINGOVERMEERJAREN = "AAAAAAAAAA";
    private static final String UPDATED_BETALINGOVERMEERJAREN = "BBBBBBBBBB";

    private static final String DEFAULT_CORRESPONDENTIENUMMER = "AAAAAAAAAA";
    private static final String UPDATED_CORRESPONDENTIENUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_INHUURANDERS = "AAAAAAAAAA";
    private static final String UPDATED_INHUURANDERS = "BBBBBBBBBB";

    private static final String DEFAULT_LEVERINGOFDIENST = "AAAAAAAAAA";
    private static final String UPDATED_LEVERINGOFDIENST = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_NETTOTOTAALBEDRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_NETTOTOTAALBEDRAG = new BigDecimal(2);

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_ONDERWERP = "AAAAAAAAAA";
    private static final String UPDATED_ONDERWERP = "BBBBBBBBBB";

    private static final String DEFAULT_REACTIE = "AAAAAAAAAA";
    private static final String UPDATED_REACTIE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_WIJZEVANINHUUR = "AAAAAAAAAA";
    private static final String UPDATED_WIJZEVANINHUUR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/aanvraaginkooporders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AanvraaginkooporderRepository aanvraaginkooporderRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAanvraaginkooporderMockMvc;

    private Aanvraaginkooporder aanvraaginkooporder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aanvraaginkooporder createEntity(EntityManager em) {
        Aanvraaginkooporder aanvraaginkooporder = new Aanvraaginkooporder()
            .betalingovermeerjaren(DEFAULT_BETALINGOVERMEERJAREN)
            .correspondentienummer(DEFAULT_CORRESPONDENTIENUMMER)
            .inhuuranders(DEFAULT_INHUURANDERS)
            .leveringofdienst(DEFAULT_LEVERINGOFDIENST)
            .nettototaalbedrag(DEFAULT_NETTOTOTAALBEDRAG)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .onderwerp(DEFAULT_ONDERWERP)
            .reactie(DEFAULT_REACTIE)
            .status(DEFAULT_STATUS)
            .wijzevaninhuur(DEFAULT_WIJZEVANINHUUR);
        return aanvraaginkooporder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aanvraaginkooporder createUpdatedEntity(EntityManager em) {
        Aanvraaginkooporder aanvraaginkooporder = new Aanvraaginkooporder()
            .betalingovermeerjaren(UPDATED_BETALINGOVERMEERJAREN)
            .correspondentienummer(UPDATED_CORRESPONDENTIENUMMER)
            .inhuuranders(UPDATED_INHUURANDERS)
            .leveringofdienst(UPDATED_LEVERINGOFDIENST)
            .nettototaalbedrag(UPDATED_NETTOTOTAALBEDRAG)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .onderwerp(UPDATED_ONDERWERP)
            .reactie(UPDATED_REACTIE)
            .status(UPDATED_STATUS)
            .wijzevaninhuur(UPDATED_WIJZEVANINHUUR);
        return aanvraaginkooporder;
    }

    @BeforeEach
    public void initTest() {
        aanvraaginkooporder = createEntity(em);
    }

    @Test
    @Transactional
    void createAanvraaginkooporder() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Aanvraaginkooporder
        var returnedAanvraaginkooporder = om.readValue(
            restAanvraaginkooporderMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanvraaginkooporder)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Aanvraaginkooporder.class
        );

        // Validate the Aanvraaginkooporder in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAanvraaginkooporderUpdatableFieldsEquals(
            returnedAanvraaginkooporder,
            getPersistedAanvraaginkooporder(returnedAanvraaginkooporder)
        );
    }

    @Test
    @Transactional
    void createAanvraaginkooporderWithExistingId() throws Exception {
        // Create the Aanvraaginkooporder with an existing ID
        aanvraaginkooporder.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAanvraaginkooporderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanvraaginkooporder)))
            .andExpect(status().isBadRequest());

        // Validate the Aanvraaginkooporder in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAanvraaginkooporders() throws Exception {
        // Initialize the database
        aanvraaginkooporderRepository.saveAndFlush(aanvraaginkooporder);

        // Get all the aanvraaginkooporderList
        restAanvraaginkooporderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aanvraaginkooporder.getId().intValue())))
            .andExpect(jsonPath("$.[*].betalingovermeerjaren").value(hasItem(DEFAULT_BETALINGOVERMEERJAREN)))
            .andExpect(jsonPath("$.[*].correspondentienummer").value(hasItem(DEFAULT_CORRESPONDENTIENUMMER)))
            .andExpect(jsonPath("$.[*].inhuuranders").value(hasItem(DEFAULT_INHUURANDERS)))
            .andExpect(jsonPath("$.[*].leveringofdienst").value(hasItem(DEFAULT_LEVERINGOFDIENST)))
            .andExpect(jsonPath("$.[*].nettototaalbedrag").value(hasItem(sameNumber(DEFAULT_NETTOTOTAALBEDRAG))))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].onderwerp").value(hasItem(DEFAULT_ONDERWERP)))
            .andExpect(jsonPath("$.[*].reactie").value(hasItem(DEFAULT_REACTIE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].wijzevaninhuur").value(hasItem(DEFAULT_WIJZEVANINHUUR)));
    }

    @Test
    @Transactional
    void getAanvraaginkooporder() throws Exception {
        // Initialize the database
        aanvraaginkooporderRepository.saveAndFlush(aanvraaginkooporder);

        // Get the aanvraaginkooporder
        restAanvraaginkooporderMockMvc
            .perform(get(ENTITY_API_URL_ID, aanvraaginkooporder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aanvraaginkooporder.getId().intValue()))
            .andExpect(jsonPath("$.betalingovermeerjaren").value(DEFAULT_BETALINGOVERMEERJAREN))
            .andExpect(jsonPath("$.correspondentienummer").value(DEFAULT_CORRESPONDENTIENUMMER))
            .andExpect(jsonPath("$.inhuuranders").value(DEFAULT_INHUURANDERS))
            .andExpect(jsonPath("$.leveringofdienst").value(DEFAULT_LEVERINGOFDIENST))
            .andExpect(jsonPath("$.nettototaalbedrag").value(sameNumber(DEFAULT_NETTOTOTAALBEDRAG)))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.onderwerp").value(DEFAULT_ONDERWERP))
            .andExpect(jsonPath("$.reactie").value(DEFAULT_REACTIE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.wijzevaninhuur").value(DEFAULT_WIJZEVANINHUUR));
    }

    @Test
    @Transactional
    void getNonExistingAanvraaginkooporder() throws Exception {
        // Get the aanvraaginkooporder
        restAanvraaginkooporderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAanvraaginkooporder() throws Exception {
        // Initialize the database
        aanvraaginkooporderRepository.saveAndFlush(aanvraaginkooporder);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aanvraaginkooporder
        Aanvraaginkooporder updatedAanvraaginkooporder = aanvraaginkooporderRepository.findById(aanvraaginkooporder.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAanvraaginkooporder are not directly saved in db
        em.detach(updatedAanvraaginkooporder);
        updatedAanvraaginkooporder
            .betalingovermeerjaren(UPDATED_BETALINGOVERMEERJAREN)
            .correspondentienummer(UPDATED_CORRESPONDENTIENUMMER)
            .inhuuranders(UPDATED_INHUURANDERS)
            .leveringofdienst(UPDATED_LEVERINGOFDIENST)
            .nettototaalbedrag(UPDATED_NETTOTOTAALBEDRAG)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .onderwerp(UPDATED_ONDERWERP)
            .reactie(UPDATED_REACTIE)
            .status(UPDATED_STATUS)
            .wijzevaninhuur(UPDATED_WIJZEVANINHUUR);

        restAanvraaginkooporderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAanvraaginkooporder.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAanvraaginkooporder))
            )
            .andExpect(status().isOk());

        // Validate the Aanvraaginkooporder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAanvraaginkooporderToMatchAllProperties(updatedAanvraaginkooporder);
    }

    @Test
    @Transactional
    void putNonExistingAanvraaginkooporder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanvraaginkooporder.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAanvraaginkooporderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aanvraaginkooporder.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aanvraaginkooporder))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanvraaginkooporder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAanvraaginkooporder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanvraaginkooporder.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanvraaginkooporderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aanvraaginkooporder))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanvraaginkooporder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAanvraaginkooporder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanvraaginkooporder.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanvraaginkooporderMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aanvraaginkooporder)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aanvraaginkooporder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAanvraaginkooporderWithPatch() throws Exception {
        // Initialize the database
        aanvraaginkooporderRepository.saveAndFlush(aanvraaginkooporder);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aanvraaginkooporder using partial update
        Aanvraaginkooporder partialUpdatedAanvraaginkooporder = new Aanvraaginkooporder();
        partialUpdatedAanvraaginkooporder.setId(aanvraaginkooporder.getId());

        partialUpdatedAanvraaginkooporder
            .correspondentienummer(UPDATED_CORRESPONDENTIENUMMER)
            .leveringofdienst(UPDATED_LEVERINGOFDIENST)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .onderwerp(UPDATED_ONDERWERP)
            .reactie(UPDATED_REACTIE);

        restAanvraaginkooporderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAanvraaginkooporder.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAanvraaginkooporder))
            )
            .andExpect(status().isOk());

        // Validate the Aanvraaginkooporder in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAanvraaginkooporderUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAanvraaginkooporder, aanvraaginkooporder),
            getPersistedAanvraaginkooporder(aanvraaginkooporder)
        );
    }

    @Test
    @Transactional
    void fullUpdateAanvraaginkooporderWithPatch() throws Exception {
        // Initialize the database
        aanvraaginkooporderRepository.saveAndFlush(aanvraaginkooporder);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aanvraaginkooporder using partial update
        Aanvraaginkooporder partialUpdatedAanvraaginkooporder = new Aanvraaginkooporder();
        partialUpdatedAanvraaginkooporder.setId(aanvraaginkooporder.getId());

        partialUpdatedAanvraaginkooporder
            .betalingovermeerjaren(UPDATED_BETALINGOVERMEERJAREN)
            .correspondentienummer(UPDATED_CORRESPONDENTIENUMMER)
            .inhuuranders(UPDATED_INHUURANDERS)
            .leveringofdienst(UPDATED_LEVERINGOFDIENST)
            .nettototaalbedrag(UPDATED_NETTOTOTAALBEDRAG)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .onderwerp(UPDATED_ONDERWERP)
            .reactie(UPDATED_REACTIE)
            .status(UPDATED_STATUS)
            .wijzevaninhuur(UPDATED_WIJZEVANINHUUR);

        restAanvraaginkooporderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAanvraaginkooporder.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAanvraaginkooporder))
            )
            .andExpect(status().isOk());

        // Validate the Aanvraaginkooporder in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAanvraaginkooporderUpdatableFieldsEquals(
            partialUpdatedAanvraaginkooporder,
            getPersistedAanvraaginkooporder(partialUpdatedAanvraaginkooporder)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAanvraaginkooporder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanvraaginkooporder.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAanvraaginkooporderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aanvraaginkooporder.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aanvraaginkooporder))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanvraaginkooporder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAanvraaginkooporder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanvraaginkooporder.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanvraaginkooporderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aanvraaginkooporder))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aanvraaginkooporder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAanvraaginkooporder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aanvraaginkooporder.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAanvraaginkooporderMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(aanvraaginkooporder)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aanvraaginkooporder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAanvraaginkooporder() throws Exception {
        // Initialize the database
        aanvraaginkooporderRepository.saveAndFlush(aanvraaginkooporder);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the aanvraaginkooporder
        restAanvraaginkooporderMockMvc
            .perform(delete(ENTITY_API_URL_ID, aanvraaginkooporder.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return aanvraaginkooporderRepository.count();
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

    protected Aanvraaginkooporder getPersistedAanvraaginkooporder(Aanvraaginkooporder aanvraaginkooporder) {
        return aanvraaginkooporderRepository.findById(aanvraaginkooporder.getId()).orElseThrow();
    }

    protected void assertPersistedAanvraaginkooporderToMatchAllProperties(Aanvraaginkooporder expectedAanvraaginkooporder) {
        assertAanvraaginkooporderAllPropertiesEquals(
            expectedAanvraaginkooporder,
            getPersistedAanvraaginkooporder(expectedAanvraaginkooporder)
        );
    }

    protected void assertPersistedAanvraaginkooporderToMatchUpdatableProperties(Aanvraaginkooporder expectedAanvraaginkooporder) {
        assertAanvraaginkooporderAllUpdatablePropertiesEquals(
            expectedAanvraaginkooporder,
            getPersistedAanvraaginkooporder(expectedAanvraaginkooporder)
        );
    }
}
