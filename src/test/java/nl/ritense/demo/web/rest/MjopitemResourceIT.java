package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.MjopitemAsserts.*;
import static nl.ritense.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static nl.ritense.demo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import nl.ritense.demo.IntegrationTest;
import nl.ritense.demo.domain.Mjopitem;
import nl.ritense.demo.repository.MjopitemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MjopitemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MjopitemResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMEINDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMEINDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMOPZEGGINGAANBIEDER = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMOPZEGGINGAANBIEDER = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMOPZEGGINGONTVANGER = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMOPZEGGINGONTVANGER = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMSTART = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMSTART = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_KOSTEN = new BigDecimal(1);
    private static final BigDecimal UPDATED_KOSTEN = new BigDecimal(2);

    private static final String DEFAULT_OMSCHRIJVING = "AAAAAAAAAA";
    private static final String UPDATED_OMSCHRIJVING = "BBBBBBBBBB";

    private static final String DEFAULT_OPZEGTERMIJNAANBIEDER = "AAAAAAAAAA";
    private static final String UPDATED_OPZEGTERMIJNAANBIEDER = "BBBBBBBBBB";

    private static final String DEFAULT_OPZEGTERMIJNONTVANGER = "AAAAAAAAAA";
    private static final String UPDATED_OPZEGTERMIJNONTVANGER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/mjopitems";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MjopitemRepository mjopitemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMjopitemMockMvc;

    private Mjopitem mjopitem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mjopitem createEntity(EntityManager em) {
        Mjopitem mjopitem = new Mjopitem()
            .code(DEFAULT_CODE)
            .datumeinde(DEFAULT_DATUMEINDE)
            .datumopzeggingaanbieder(DEFAULT_DATUMOPZEGGINGAANBIEDER)
            .datumopzeggingontvanger(DEFAULT_DATUMOPZEGGINGONTVANGER)
            .datumstart(DEFAULT_DATUMSTART)
            .kosten(DEFAULT_KOSTEN)
            .omschrijving(DEFAULT_OMSCHRIJVING)
            .opzegtermijnaanbieder(DEFAULT_OPZEGTERMIJNAANBIEDER)
            .opzegtermijnontvanger(DEFAULT_OPZEGTERMIJNONTVANGER);
        return mjopitem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mjopitem createUpdatedEntity(EntityManager em) {
        Mjopitem mjopitem = new Mjopitem()
            .code(UPDATED_CODE)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumopzeggingaanbieder(UPDATED_DATUMOPZEGGINGAANBIEDER)
            .datumopzeggingontvanger(UPDATED_DATUMOPZEGGINGONTVANGER)
            .datumstart(UPDATED_DATUMSTART)
            .kosten(UPDATED_KOSTEN)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .opzegtermijnaanbieder(UPDATED_OPZEGTERMIJNAANBIEDER)
            .opzegtermijnontvanger(UPDATED_OPZEGTERMIJNONTVANGER);
        return mjopitem;
    }

    @BeforeEach
    public void initTest() {
        mjopitem = createEntity(em);
    }

    @Test
    @Transactional
    void createMjopitem() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Mjopitem
        var returnedMjopitem = om.readValue(
            restMjopitemMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mjopitem)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Mjopitem.class
        );

        // Validate the Mjopitem in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMjopitemUpdatableFieldsEquals(returnedMjopitem, getPersistedMjopitem(returnedMjopitem));
    }

    @Test
    @Transactional
    void createMjopitemWithExistingId() throws Exception {
        // Create the Mjopitem with an existing ID
        mjopitem.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMjopitemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mjopitem)))
            .andExpect(status().isBadRequest());

        // Validate the Mjopitem in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMjopitems() throws Exception {
        // Initialize the database
        mjopitemRepository.saveAndFlush(mjopitem);

        // Get all the mjopitemList
        restMjopitemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mjopitem.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].datumeinde").value(hasItem(DEFAULT_DATUMEINDE.toString())))
            .andExpect(jsonPath("$.[*].datumopzeggingaanbieder").value(hasItem(DEFAULT_DATUMOPZEGGINGAANBIEDER.toString())))
            .andExpect(jsonPath("$.[*].datumopzeggingontvanger").value(hasItem(DEFAULT_DATUMOPZEGGINGONTVANGER.toString())))
            .andExpect(jsonPath("$.[*].datumstart").value(hasItem(DEFAULT_DATUMSTART.toString())))
            .andExpect(jsonPath("$.[*].kosten").value(hasItem(sameNumber(DEFAULT_KOSTEN))))
            .andExpect(jsonPath("$.[*].omschrijving").value(hasItem(DEFAULT_OMSCHRIJVING)))
            .andExpect(jsonPath("$.[*].opzegtermijnaanbieder").value(hasItem(DEFAULT_OPZEGTERMIJNAANBIEDER)))
            .andExpect(jsonPath("$.[*].opzegtermijnontvanger").value(hasItem(DEFAULT_OPZEGTERMIJNONTVANGER)));
    }

    @Test
    @Transactional
    void getMjopitem() throws Exception {
        // Initialize the database
        mjopitemRepository.saveAndFlush(mjopitem);

        // Get the mjopitem
        restMjopitemMockMvc
            .perform(get(ENTITY_API_URL_ID, mjopitem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mjopitem.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.datumeinde").value(DEFAULT_DATUMEINDE.toString()))
            .andExpect(jsonPath("$.datumopzeggingaanbieder").value(DEFAULT_DATUMOPZEGGINGAANBIEDER.toString()))
            .andExpect(jsonPath("$.datumopzeggingontvanger").value(DEFAULT_DATUMOPZEGGINGONTVANGER.toString()))
            .andExpect(jsonPath("$.datumstart").value(DEFAULT_DATUMSTART.toString()))
            .andExpect(jsonPath("$.kosten").value(sameNumber(DEFAULT_KOSTEN)))
            .andExpect(jsonPath("$.omschrijving").value(DEFAULT_OMSCHRIJVING))
            .andExpect(jsonPath("$.opzegtermijnaanbieder").value(DEFAULT_OPZEGTERMIJNAANBIEDER))
            .andExpect(jsonPath("$.opzegtermijnontvanger").value(DEFAULT_OPZEGTERMIJNONTVANGER));
    }

    @Test
    @Transactional
    void getNonExistingMjopitem() throws Exception {
        // Get the mjopitem
        restMjopitemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMjopitem() throws Exception {
        // Initialize the database
        mjopitemRepository.saveAndFlush(mjopitem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mjopitem
        Mjopitem updatedMjopitem = mjopitemRepository.findById(mjopitem.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMjopitem are not directly saved in db
        em.detach(updatedMjopitem);
        updatedMjopitem
            .code(UPDATED_CODE)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumopzeggingaanbieder(UPDATED_DATUMOPZEGGINGAANBIEDER)
            .datumopzeggingontvanger(UPDATED_DATUMOPZEGGINGONTVANGER)
            .datumstart(UPDATED_DATUMSTART)
            .kosten(UPDATED_KOSTEN)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .opzegtermijnaanbieder(UPDATED_OPZEGTERMIJNAANBIEDER)
            .opzegtermijnontvanger(UPDATED_OPZEGTERMIJNONTVANGER);

        restMjopitemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMjopitem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMjopitem))
            )
            .andExpect(status().isOk());

        // Validate the Mjopitem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMjopitemToMatchAllProperties(updatedMjopitem);
    }

    @Test
    @Transactional
    void putNonExistingMjopitem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mjopitem.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMjopitemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mjopitem.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mjopitem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mjopitem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMjopitem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mjopitem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMjopitemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(mjopitem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mjopitem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMjopitem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mjopitem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMjopitemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mjopitem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mjopitem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMjopitemWithPatch() throws Exception {
        // Initialize the database
        mjopitemRepository.saveAndFlush(mjopitem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mjopitem using partial update
        Mjopitem partialUpdatedMjopitem = new Mjopitem();
        partialUpdatedMjopitem.setId(mjopitem.getId());

        partialUpdatedMjopitem
            .code(UPDATED_CODE)
            .datumopzeggingontvanger(UPDATED_DATUMOPZEGGINGONTVANGER)
            .kosten(UPDATED_KOSTEN)
            .omschrijving(UPDATED_OMSCHRIJVING);

        restMjopitemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMjopitem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMjopitem))
            )
            .andExpect(status().isOk());

        // Validate the Mjopitem in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMjopitemUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedMjopitem, mjopitem), getPersistedMjopitem(mjopitem));
    }

    @Test
    @Transactional
    void fullUpdateMjopitemWithPatch() throws Exception {
        // Initialize the database
        mjopitemRepository.saveAndFlush(mjopitem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mjopitem using partial update
        Mjopitem partialUpdatedMjopitem = new Mjopitem();
        partialUpdatedMjopitem.setId(mjopitem.getId());

        partialUpdatedMjopitem
            .code(UPDATED_CODE)
            .datumeinde(UPDATED_DATUMEINDE)
            .datumopzeggingaanbieder(UPDATED_DATUMOPZEGGINGAANBIEDER)
            .datumopzeggingontvanger(UPDATED_DATUMOPZEGGINGONTVANGER)
            .datumstart(UPDATED_DATUMSTART)
            .kosten(UPDATED_KOSTEN)
            .omschrijving(UPDATED_OMSCHRIJVING)
            .opzegtermijnaanbieder(UPDATED_OPZEGTERMIJNAANBIEDER)
            .opzegtermijnontvanger(UPDATED_OPZEGTERMIJNONTVANGER);

        restMjopitemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMjopitem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMjopitem))
            )
            .andExpect(status().isOk());

        // Validate the Mjopitem in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMjopitemUpdatableFieldsEquals(partialUpdatedMjopitem, getPersistedMjopitem(partialUpdatedMjopitem));
    }

    @Test
    @Transactional
    void patchNonExistingMjopitem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mjopitem.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMjopitemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mjopitem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(mjopitem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mjopitem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMjopitem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mjopitem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMjopitemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(mjopitem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mjopitem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMjopitem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mjopitem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMjopitemMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(mjopitem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mjopitem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMjopitem() throws Exception {
        // Initialize the database
        mjopitemRepository.saveAndFlush(mjopitem);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the mjopitem
        restMjopitemMockMvc
            .perform(delete(ENTITY_API_URL_ID, mjopitem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return mjopitemRepository.count();
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

    protected Mjopitem getPersistedMjopitem(Mjopitem mjopitem) {
        return mjopitemRepository.findById(mjopitem.getId()).orElseThrow();
    }

    protected void assertPersistedMjopitemToMatchAllProperties(Mjopitem expectedMjopitem) {
        assertMjopitemAllPropertiesEquals(expectedMjopitem, getPersistedMjopitem(expectedMjopitem));
    }

    protected void assertPersistedMjopitemToMatchUpdatableProperties(Mjopitem expectedMjopitem) {
        assertMjopitemAllUpdatablePropertiesEquals(expectedMjopitem, getPersistedMjopitem(expectedMjopitem));
    }
}
