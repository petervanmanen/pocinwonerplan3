package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.InkooporderAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static nl.ritense.demo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Inkooporder;
import nl.ritense.demo.repository.InkooporderRepository;
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
 * Integration tests for the {@link InkooporderResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class InkooporderResourceIT {

    private static final String DEFAULT_ARTIKELCODE = "AAAAAAAAAA";
    private static final String UPDATED_ARTIKELCODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_BETALINGMEERDEREJAREN = false;
    private static final Boolean UPDATED_BETALINGMEERDEREJAREN = true;

    private static final String DEFAULT_BETREFT = "AAAAAAAAAA";
    private static final String UPDATED_BETREFT = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMEINDE = "AAAAAAAAAA";
    private static final String UPDATED_DATUMEINDE = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMINGEDIEND = "AAAAAAAAAA";
    private static final String UPDATED_DATUMINGEDIEND = "BBBBBBBBBB";

    private static final String DEFAULT_DATUMSTART = "AAAAAAAAAA";
    private static final String UPDATED_DATUMSTART = "BBBBBBBBBB";

    private static final String DEFAULT_GOEDERENCODE = "AAAAAAAAAA";
    private static final String UPDATED_GOEDERENCODE = "BBBBBBBBBB";

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_ORDERNUMMER = "AAAAAAAA";
    private static final String UPDATED_ORDERNUMMER = "BBBBBBBB";

    private static final BigDecimal DEFAULT_SALDO = new BigDecimal(1);
    private static final BigDecimal UPDATED_SALDO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAALNETTOBEDRAG = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAALNETTOBEDRAG = new BigDecimal(2);

    private static final String DEFAULT_WIJZEVANAANBESTEDEN = "AAAAAAAAAA";
    private static final String UPDATED_WIJZEVANAANBESTEDEN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/inkooporders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InkooporderRepository inkooporderRepository;

    @Mock
    private InkooporderRepository inkooporderRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInkooporderMockMvc;

    private Inkooporder inkooporder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inkooporder createEntity(EntityManager em) {
        Inkooporder inkooporder = new Inkooporder()
            .artikelcode(DEFAULT_ARTIKELCODE)
            .betalingmeerderejaren(DEFAULT_BETALINGMEERDEREJAREN)
            .betreft(DEFAULT_BETREFT)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumingediend(DEFAULT_DATUMINGEDIEND)
            .datumstart(DEFAULT_DATUMSTART)
            .goederencode(DEFAULT_GOEDERENCODE)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .ordernummer(DEFAULT_ORDERNUMMER)
            .saldo(DEFAULT_SALDO)
            .totaalnettobedrag(DEFAULT_TOTAALNETTOBEDRAG)
            .wijzevanaanbesteden(DEFAULT_WIJZEVANAANBESTEDEN);
        return inkooporder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inkooporder createUpdatedEntity(EntityManager em) {
        Inkooporder inkooporder = new Inkooporder()
            .artikelcode(UPDATED_ARTIKELCODE)
            .betalingmeerderejaren(UPDATED_BETALINGMEERDEREJAREN)
            .betreft(UPDATED_BETREFT)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumingediend(UPDATED_DATUMINGEDIEND)
            .datumstart(UPDATED_DATUMSTART)
            .goederencode(UPDATED_GOEDERENCODE)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .ordernummer(UPDATED_ORDERNUMMER)
            .saldo(UPDATED_SALDO)
            .totaalnettobedrag(UPDATED_TOTAALNETTOBEDRAG)
            .wijzevanaanbesteden(UPDATED_WIJZEVANAANBESTEDEN);
        return inkooporder;
    }

    @BeforeEach
    public void initTest() {
        inkooporder = createEntity(em);
    }

    @Test
    @Transactional
    void createInkooporder() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Inkooporder
        var returnedInkooporder = om.readValue(
            restInkooporderMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inkooporder)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Inkooporder.class
        );

        // Validate the Inkooporder in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertInkooporderUpdatableFieldsEquals(returnedInkooporder, getPersistedInkooporder(returnedInkooporder));
    }

    @Test
    @Transactional
    void createInkooporderWithExistingId() throws Exception {
        // Create the Inkooporder with an existing ID
        inkooporder.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInkooporderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inkooporder)))
            .andExpect(status().isBadRequest());

        // Validate the Inkooporder in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInkooporders() throws Exception {
        // Initialize the database
        inkooporderRepository.saveAndFlush(inkooporder);

        // Get all the inkooporderList
        restInkooporderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inkooporder.getId().intValue())))
            .andExpect(jsonPath("$.[*].artikelcode").value(hasItem(DEFAULT_ARTIKELCODE)))
            .andExpect(jsonPath("$.[*].betalingmeerderejaren").value(hasItem(DEFAULT_BETALINGMEERDEREJAREN.booleanValue())))
            .andExpect(jsonPath("$.[*].betreft").value(hasItem(DEFAULT_BETREFT)))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE)))
            .andExpect(jsonPath("$.[*].datumingediend").value(hasItem(DEFAULT_DATUMINGEDIEND)))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART)))
            .andExpect(jsonPath("$.[*].goederencode").value(hasItem(DEFAULT_GOEDERENCODE)))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].ordernummer").value(hasItem(DEFAULT_ORDERNUMMER)))
            .andExpect(jsonPath("$.[*].saldo").value(hasItem(sameNumber(DEFAULT_SALDO))))
            .andExpect(jsonPath("$.[*].totaalnettobedrag").value(hasItem(sameNumber(DEFAULT_TOTAALNETTOBEDRAG))))
            .andExpect(jsonPath("$.[*].wijzevanaanbesteden").value(hasItem(DEFAULT_WIJZEVANAANBESTEDEN)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInkoopordersWithEagerRelationshipsIsEnabled() throws Exception {
        when(inkooporderRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInkooporderMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(inkooporderRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInkoopordersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(inkooporderRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInkooporderMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(inkooporderRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getInkooporder() throws Exception {
        // Initialize the database
        inkooporderRepository.saveAndFlush(inkooporder);

        // Get the inkooporder
        restInkooporderMockMvc
            .perform(get(ENTITY_API_URL_ID, inkooporder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inkooporder.getId().intValue()))
            .andExpect(jsonPath("$.artikelcode").value(DEFAULT_ARTIKELCODE))
            .andExpect(jsonPath("$.betalingmeerderejaren").value(DEFAULT_BETALINGMEERDEREJAREN.booleanValue()))
            .andExpect(jsonPath("$.betreft").value(DEFAULT_BETREFT))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE))
            .andExpect(jsonPath("$.datumingediend").value(DEFAULT_DATUMINGEDIEND))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART))
            .andExpect(jsonPath("$.goederencode").value(DEFAULT_GOEDERENCODE))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.ordernummer").value(DEFAULT_ORDERNUMMER))
            .andExpect(jsonPath("$.saldo").value(sameNumber(DEFAULT_SALDO)))
            .andExpect(jsonPath("$.totaalnettobedrag").value(sameNumber(DEFAULT_TOTAALNETTOBEDRAG)))
            .andExpect(jsonPath("$.wijzevanaanbesteden").value(DEFAULT_WIJZEVANAANBESTEDEN));
    }

    @Test
    @Transactional
    void getNonExistingInkooporder() throws Exception {
        // Get the inkooporder
        restInkooporderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInkooporder() throws Exception {
        // Initialize the database
        inkooporderRepository.saveAndFlush(inkooporder);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inkooporder
        Inkooporder updatedInkooporder = inkooporderRepository.findById(inkooporder.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInkooporder are not directly saved in db
        em.detach(updatedInkooporder);
        updatedInkooporder
            .artikelcode(UPDATED_ARTIKELCODE)
            .betalingmeerderejaren(UPDATED_BETALINGMEERDEREJAREN)
            .betreft(UPDATED_BETREFT)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumingediend(UPDATED_DATUMINGEDIEND)
            .datumstart(UPDATED_DATUMSTART)
            .goederencode(UPDATED_GOEDERENCODE)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .ordernummer(UPDATED_ORDERNUMMER)
            .saldo(UPDATED_SALDO)
            .totaalnettobedrag(UPDATED_TOTAALNETTOBEDRAG)
            .wijzevanaanbesteden(UPDATED_WIJZEVANAANBESTEDEN);

        restInkooporderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInkooporder.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedInkooporder))
            )
            .andExpect(status().isOk());

        // Validate the Inkooporder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInkooporderToMatchAllProperties(updatedInkooporder);
    }

    @Test
    @Transactional
    void putNonExistingInkooporder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inkooporder.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInkooporderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inkooporder.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inkooporder))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inkooporder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInkooporder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inkooporder.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInkooporderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inkooporder))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inkooporder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInkooporder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inkooporder.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInkooporderMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inkooporder)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inkooporder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInkooporderWithPatch() throws Exception {
        // Initialize the database
        inkooporderRepository.saveAndFlush(inkooporder);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inkooporder using partial update
        Inkooporder partialUpdatedInkooporder = new Inkooporder();
        partialUpdatedInkooporder.setId(inkooporder.getId());

        partialUpdatedInkooporder
            .goederencode(UPDATED_GOEDERENCODE)
            .ordernummer(UPDATED_ORDERNUMMER)
            .saldo(UPDATED_SALDO)
            .totaalnettobedrag(UPDATED_TOTAALNETTOBEDRAG)
            .wijzevanaanbesteden(UPDATED_WIJZEVANAANBESTEDEN);

        restInkooporderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInkooporder.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInkooporder))
            )
            .andExpect(status().isOk());

        // Validate the Inkooporder in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInkooporderUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInkooporder, inkooporder),
            getPersistedInkooporder(inkooporder)
        );
    }

    @Test
    @Transactional
    void fullUpdateInkooporderWithPatch() throws Exception {
        // Initialize the database
        inkooporderRepository.saveAndFlush(inkooporder);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inkooporder using partial update
        Inkooporder partialUpdatedInkooporder = new Inkooporder();
        partialUpdatedInkooporder.setId(inkooporder.getId());

        partialUpdatedInkooporder
            .artikelcode(UPDATED_ARTIKELCODE)
            .betalingmeerderejaren(UPDATED_BETALINGMEERDEREJAREN)
            .betreft(UPDATED_BETREFT)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumingediend(UPDATED_DATUMINGEDIEND)
            .datumstart(UPDATED_DATUMSTART)
            .goederencode(UPDATED_GOEDERENCODE)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .ordernummer(UPDATED_ORDERNUMMER)
            .saldo(UPDATED_SALDO)
            .totaalnettobedrag(UPDATED_TOTAALNETTOBEDRAG)
            .wijzevanaanbesteden(UPDATED_WIJZEVANAANBESTEDEN);

        restInkooporderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInkooporder.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInkooporder))
            )
            .andExpect(status().isOk());

        // Validate the Inkooporder in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInkooporderUpdatableFieldsEquals(partialUpdatedInkooporder, getPersistedInkooporder(partialUpdatedInkooporder));
    }

    @Test
    @Transactional
    void patchNonExistingInkooporder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inkooporder.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInkooporderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inkooporder.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inkooporder))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inkooporder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInkooporder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inkooporder.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInkooporderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inkooporder))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inkooporder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInkooporder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inkooporder.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInkooporderMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(inkooporder)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inkooporder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInkooporder() throws Exception {
        // Initialize the database
        inkooporderRepository.saveAndFlush(inkooporder);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the inkooporder
        restInkooporderMockMvc
            .perform(delete(ENTITY_API_URL_ID, inkooporder.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return inkooporderRepository.count();
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

    protected Inkooporder getPersistedInkooporder(Inkooporder inkooporder) {
        return inkooporderRepository.findById(inkooporder.getId()).orElseThrow();
    }

    protected void assertPersistedInkooporderToMatchAllProperties(Inkooporder expectedInkooporder) {
        assertInkooporderAllPropertiesEquals(expectedInkooporder, getPersistedInkooporder(expectedInkooporder));
    }

    protected void assertPersistedInkooporderToMatchUpdatableProperties(Inkooporder expectedInkooporder) {
        assertInkooporderAllUpdatablePropertiesEquals(expectedInkooporder, getPersistedInkooporder(expectedInkooporder));
    }
}
