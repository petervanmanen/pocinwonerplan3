package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.WinkelvoorraaditemAsserts.*;
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
import nl.ritense.demo.domain.Winkelvoorraaditem;
import nl.ritense.demo.repository.WinkelvoorraaditemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WinkelvoorraaditemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WinkelvoorraaditemResourceIT {

    private static final String DEFAULT_AANTAL = "AAAAAAAAAA";
    private static final String UPDATED_AANTAL = "BBBBBBBBBB";

    private static final String DEFAULT_AANTALINBESTELLING = "AAAAAAAAAA";
    private static final String UPDATED_AANTALINBESTELLING = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUMLEVERINGBESTELLING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMLEVERINGBESTELLING = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LOCATIE = "AAAAAAAAAA";
    private static final String UPDATED_LOCATIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/winkelvoorraaditems";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WinkelvoorraaditemRepository winkelvoorraaditemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWinkelvoorraaditemMockMvc;

    private Winkelvoorraaditem winkelvoorraaditem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Winkelvoorraaditem createEntity(EntityManager em) {
        Winkelvoorraaditem winkelvoorraaditem = new Winkelvoorraaditem()
            .aantal(DEFAULT_AANTAL)
            .aantalinbestelling(DEFAULT_AANTALINBESTELLING)
            .datumleveringbestelling(DEFAULT_DATUMLEVERINGBESTELLING)
            .locatie(DEFAULT_LOCATIE);
        return winkelvoorraaditem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Winkelvoorraaditem createUpdatedEntity(EntityManager em) {
        Winkelvoorraaditem winkelvoorraaditem = new Winkelvoorraaditem()
            .aantal(UPDATED_AANTAL)
            .aantalinbestelling(UPDATED_AANTALINBESTELLING)
            .datumleveringbestelling(UPDATED_DATUMLEVERINGBESTELLING)
            .locatie(UPDATED_LOCATIE);
        return winkelvoorraaditem;
    }

    @BeforeEach
    public void initTest() {
        winkelvoorraaditem = createEntity(em);
    }

    @Test
    @Transactional
    void createWinkelvoorraaditem() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Winkelvoorraaditem
        var returnedWinkelvoorraaditem = om.readValue(
            restWinkelvoorraaditemMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(winkelvoorraaditem)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Winkelvoorraaditem.class
        );

        // Validate the Winkelvoorraaditem in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertWinkelvoorraaditemUpdatableFieldsEquals(
            returnedWinkelvoorraaditem,
            getPersistedWinkelvoorraaditem(returnedWinkelvoorraaditem)
        );
    }

    @Test
    @Transactional
    void createWinkelvoorraaditemWithExistingId() throws Exception {
        // Create the Winkelvoorraaditem with an existing ID
        winkelvoorraaditem.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWinkelvoorraaditemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(winkelvoorraaditem)))
            .andExpect(status().isBadRequest());

        // Validate the Winkelvoorraaditem in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWinkelvoorraaditems() throws Exception {
        // Initialize the database
        winkelvoorraaditemRepository.saveAndFlush(winkelvoorraaditem);

        // Get all the winkelvoorraaditemList
        restWinkelvoorraaditemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(winkelvoorraaditem.getId().intValue())))
            .andExpect(jsonPath("$.[*].aantal").value(hasItem(DEFAULT_AANTAL)))
            .andExpect(jsonPath("$.[*].aantalinbestelling").value(hasItem(DEFAULT_AANTALINBESTELLING)))
            .andExpect(jsonPath("$.[*].datumleveringbestelling").value(hasItem(DEFAULT_DATUMLEVERINGBESTELLING.toString())))
            .andExpect(jsonPath("$.[*].locatie").value(hasItem(DEFAULT_LOCATIE)));
    }

    @Test
    @Transactional
    void getWinkelvoorraaditem() throws Exception {
        // Initialize the database
        winkelvoorraaditemRepository.saveAndFlush(winkelvoorraaditem);

        // Get the winkelvoorraaditem
        restWinkelvoorraaditemMockMvc
            .perform(get(ENTITY_API_URL_ID, winkelvoorraaditem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(winkelvoorraaditem.getId().intValue()))
            .andExpect(jsonPath("$.aantal").value(DEFAULT_AANTAL))
            .andExpect(jsonPath("$.aantalinbestelling").value(DEFAULT_AANTALINBESTELLING))
            .andExpect(jsonPath("$.datumleveringbestelling").value(DEFAULT_DATUMLEVERINGBESTELLING.toString()))
            .andExpect(jsonPath("$.locatie").value(DEFAULT_LOCATIE));
    }

    @Test
    @Transactional
    void getNonExistingWinkelvoorraaditem() throws Exception {
        // Get the winkelvoorraaditem
        restWinkelvoorraaditemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWinkelvoorraaditem() throws Exception {
        // Initialize the database
        winkelvoorraaditemRepository.saveAndFlush(winkelvoorraaditem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the winkelvoorraaditem
        Winkelvoorraaditem updatedWinkelvoorraaditem = winkelvoorraaditemRepository.findById(winkelvoorraaditem.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWinkelvoorraaditem are not directly saved in db
        em.detach(updatedWinkelvoorraaditem);
        updatedWinkelvoorraaditem
            .aantal(UPDATED_AANTAL)
            .aantalinbestelling(UPDATED_AANTALINBESTELLING)
            .datumleveringbestelling(UPDATED_DATUMLEVERINGBESTELLING)
            .locatie(UPDATED_LOCATIE);

        restWinkelvoorraaditemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWinkelvoorraaditem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedWinkelvoorraaditem))
            )
            .andExpect(status().isOk());

        // Validate the Winkelvoorraaditem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWinkelvoorraaditemToMatchAllProperties(updatedWinkelvoorraaditem);
    }

    @Test
    @Transactional
    void putNonExistingWinkelvoorraaditem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        winkelvoorraaditem.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWinkelvoorraaditemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, winkelvoorraaditem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(winkelvoorraaditem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Winkelvoorraaditem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWinkelvoorraaditem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        winkelvoorraaditem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWinkelvoorraaditemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(winkelvoorraaditem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Winkelvoorraaditem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWinkelvoorraaditem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        winkelvoorraaditem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWinkelvoorraaditemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(winkelvoorraaditem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Winkelvoorraaditem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWinkelvoorraaditemWithPatch() throws Exception {
        // Initialize the database
        winkelvoorraaditemRepository.saveAndFlush(winkelvoorraaditem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the winkelvoorraaditem using partial update
        Winkelvoorraaditem partialUpdatedWinkelvoorraaditem = new Winkelvoorraaditem();
        partialUpdatedWinkelvoorraaditem.setId(winkelvoorraaditem.getId());

        partialUpdatedWinkelvoorraaditem.locatie(UPDATED_LOCATIE);

        restWinkelvoorraaditemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWinkelvoorraaditem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWinkelvoorraaditem))
            )
            .andExpect(status().isOk());

        // Validate the Winkelvoorraaditem in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWinkelvoorraaditemUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedWinkelvoorraaditem, winkelvoorraaditem),
            getPersistedWinkelvoorraaditem(winkelvoorraaditem)
        );
    }

    @Test
    @Transactional
    void fullUpdateWinkelvoorraaditemWithPatch() throws Exception {
        // Initialize the database
        winkelvoorraaditemRepository.saveAndFlush(winkelvoorraaditem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the winkelvoorraaditem using partial update
        Winkelvoorraaditem partialUpdatedWinkelvoorraaditem = new Winkelvoorraaditem();
        partialUpdatedWinkelvoorraaditem.setId(winkelvoorraaditem.getId());

        partialUpdatedWinkelvoorraaditem
            .aantal(UPDATED_AANTAL)
            .aantalinbestelling(UPDATED_AANTALINBESTELLING)
            .datumleveringbestelling(UPDATED_DATUMLEVERINGBESTELLING)
            .locatie(UPDATED_LOCATIE);

        restWinkelvoorraaditemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWinkelvoorraaditem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWinkelvoorraaditem))
            )
            .andExpect(status().isOk());

        // Validate the Winkelvoorraaditem in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWinkelvoorraaditemUpdatableFieldsEquals(
            partialUpdatedWinkelvoorraaditem,
            getPersistedWinkelvoorraaditem(partialUpdatedWinkelvoorraaditem)
        );
    }

    @Test
    @Transactional
    void patchNonExistingWinkelvoorraaditem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        winkelvoorraaditem.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWinkelvoorraaditemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, winkelvoorraaditem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(winkelvoorraaditem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Winkelvoorraaditem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWinkelvoorraaditem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        winkelvoorraaditem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWinkelvoorraaditemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(winkelvoorraaditem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Winkelvoorraaditem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWinkelvoorraaditem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        winkelvoorraaditem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWinkelvoorraaditemMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(winkelvoorraaditem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Winkelvoorraaditem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWinkelvoorraaditem() throws Exception {
        // Initialize the database
        winkelvoorraaditemRepository.saveAndFlush(winkelvoorraaditem);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the winkelvoorraaditem
        restWinkelvoorraaditemMockMvc
            .perform(delete(ENTITY_API_URL_ID, winkelvoorraaditem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return winkelvoorraaditemRepository.count();
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

    protected Winkelvoorraaditem getPersistedWinkelvoorraaditem(Winkelvoorraaditem winkelvoorraaditem) {
        return winkelvoorraaditemRepository.findById(winkelvoorraaditem.getId()).orElseThrow();
    }

    protected void assertPersistedWinkelvoorraaditemToMatchAllProperties(Winkelvoorraaditem expectedWinkelvoorraaditem) {
        assertWinkelvoorraaditemAllPropertiesEquals(expectedWinkelvoorraaditem, getPersistedWinkelvoorraaditem(expectedWinkelvoorraaditem));
    }

    protected void assertPersistedWinkelvoorraaditemToMatchUpdatableProperties(Winkelvoorraaditem expectedWinkelvoorraaditem) {
        assertWinkelvoorraaditemAllUpdatablePropertiesEquals(
            expectedWinkelvoorraaditem,
            getPersistedWinkelvoorraaditem(expectedWinkelvoorraaditem)
        );
    }
}
