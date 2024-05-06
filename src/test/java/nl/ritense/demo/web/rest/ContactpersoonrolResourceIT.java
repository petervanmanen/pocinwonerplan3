package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ContactpersoonrolAsserts.*;
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
import nl.ritense.demo.domain.Contactpersoonrol;
import nl.ritense.demo.repository.ContactpersoonrolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ContactpersoonrolResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContactpersoonrolResourceIT {

    private static final String DEFAULT_CONTACTPERSOONEMAILADRES = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTPERSOONEMAILADRES = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACTPERSOONFUNCTIE = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTPERSOONFUNCTIE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACTPERSOONNAAM = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTPERSOONNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACTPERSOONTELEFOONNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTPERSOONTELEFOONNUMMER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/contactpersoonrols";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ContactpersoonrolRepository contactpersoonrolRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactpersoonrolMockMvc;

    private Contactpersoonrol contactpersoonrol;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contactpersoonrol createEntity(EntityManager em) {
        Contactpersoonrol contactpersoonrol = new Contactpersoonrol()
            .contactpersoonemailadres(DEFAULT_CONTACTPERSOONEMAILADRES)
            .contactpersoonfunctie(DEFAULT_CONTACTPERSOONFUNCTIE)
            .contactpersoonnaam(DEFAULT_CONTACTPERSOONNAAM)
            .contactpersoontelefoonnummer(DEFAULT_CONTACTPERSOONTELEFOONNUMMER);
        return contactpersoonrol;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contactpersoonrol createUpdatedEntity(EntityManager em) {
        Contactpersoonrol contactpersoonrol = new Contactpersoonrol()
            .contactpersoonemailadres(UPDATED_CONTACTPERSOONEMAILADRES)
            .contactpersoonfunctie(UPDATED_CONTACTPERSOONFUNCTIE)
            .contactpersoonnaam(UPDATED_CONTACTPERSOONNAAM)
            .contactpersoontelefoonnummer(UPDATED_CONTACTPERSOONTELEFOONNUMMER);
        return contactpersoonrol;
    }

    @BeforeEach
    public void initTest() {
        contactpersoonrol = createEntity(em);
    }

    @Test
    @Transactional
    void createContactpersoonrol() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Contactpersoonrol
        var returnedContactpersoonrol = om.readValue(
            restContactpersoonrolMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contactpersoonrol)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Contactpersoonrol.class
        );

        // Validate the Contactpersoonrol in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertContactpersoonrolUpdatableFieldsEquals(returnedContactpersoonrol, getPersistedContactpersoonrol(returnedContactpersoonrol));
    }

    @Test
    @Transactional
    void createContactpersoonrolWithExistingId() throws Exception {
        // Create the Contactpersoonrol with an existing ID
        contactpersoonrol.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactpersoonrolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contactpersoonrol)))
            .andExpect(status().isBadRequest());

        // Validate the Contactpersoonrol in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllContactpersoonrols() throws Exception {
        // Initialize the database
        contactpersoonrolRepository.saveAndFlush(contactpersoonrol);

        // Get all the contactpersoonrolList
        restContactpersoonrolMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactpersoonrol.getId().intValue())))
            .andExpect(jsonPath("$.[*].contactpersoonemailadres").value(hasItem(DEFAULT_CONTACTPERSOONEMAILADRES)))
            .andExpect(jsonPath("$.[*].contactpersoonfunctie").value(hasItem(DEFAULT_CONTACTPERSOONFUNCTIE)))
            .andExpect(jsonPath("$.[*].contactpersoonnaam").value(hasItem(DEFAULT_CONTACTPERSOONNAAM)))
            .andExpect(jsonPath("$.[*].contactpersoontelefoonnummer").value(hasItem(DEFAULT_CONTACTPERSOONTELEFOONNUMMER)));
    }

    @Test
    @Transactional
    void getContactpersoonrol() throws Exception {
        // Initialize the database
        contactpersoonrolRepository.saveAndFlush(contactpersoonrol);

        // Get the contactpersoonrol
        restContactpersoonrolMockMvc
            .perform(get(ENTITY_API_URL_ID, contactpersoonrol.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contactpersoonrol.getId().intValue()))
            .andExpect(jsonPath("$.contactpersoonemailadres").value(DEFAULT_CONTACTPERSOONEMAILADRES))
            .andExpect(jsonPath("$.contactpersoonfunctie").value(DEFAULT_CONTACTPERSOONFUNCTIE))
            .andExpect(jsonPath("$.contactpersoonnaam").value(DEFAULT_CONTACTPERSOONNAAM))
            .andExpect(jsonPath("$.contactpersoontelefoonnummer").value(DEFAULT_CONTACTPERSOONTELEFOONNUMMER));
    }

    @Test
    @Transactional
    void getNonExistingContactpersoonrol() throws Exception {
        // Get the contactpersoonrol
        restContactpersoonrolMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingContactpersoonrol() throws Exception {
        // Initialize the database
        contactpersoonrolRepository.saveAndFlush(contactpersoonrol);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contactpersoonrol
        Contactpersoonrol updatedContactpersoonrol = contactpersoonrolRepository.findById(contactpersoonrol.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedContactpersoonrol are not directly saved in db
        em.detach(updatedContactpersoonrol);
        updatedContactpersoonrol
            .contactpersoonemailadres(UPDATED_CONTACTPERSOONEMAILADRES)
            .contactpersoonfunctie(UPDATED_CONTACTPERSOONFUNCTIE)
            .contactpersoonnaam(UPDATED_CONTACTPERSOONNAAM)
            .contactpersoontelefoonnummer(UPDATED_CONTACTPERSOONTELEFOONNUMMER);

        restContactpersoonrolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedContactpersoonrol.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedContactpersoonrol))
            )
            .andExpect(status().isOk());

        // Validate the Contactpersoonrol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedContactpersoonrolToMatchAllProperties(updatedContactpersoonrol);
    }

    @Test
    @Transactional
    void putNonExistingContactpersoonrol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contactpersoonrol.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactpersoonrolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contactpersoonrol.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contactpersoonrol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contactpersoonrol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContactpersoonrol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contactpersoonrol.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactpersoonrolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contactpersoonrol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contactpersoonrol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContactpersoonrol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contactpersoonrol.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactpersoonrolMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contactpersoonrol)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contactpersoonrol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContactpersoonrolWithPatch() throws Exception {
        // Initialize the database
        contactpersoonrolRepository.saveAndFlush(contactpersoonrol);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contactpersoonrol using partial update
        Contactpersoonrol partialUpdatedContactpersoonrol = new Contactpersoonrol();
        partialUpdatedContactpersoonrol.setId(contactpersoonrol.getId());

        partialUpdatedContactpersoonrol
            .contactpersoonemailadres(UPDATED_CONTACTPERSOONEMAILADRES)
            .contactpersoonfunctie(UPDATED_CONTACTPERSOONFUNCTIE)
            .contactpersoonnaam(UPDATED_CONTACTPERSOONNAAM)
            .contactpersoontelefoonnummer(UPDATED_CONTACTPERSOONTELEFOONNUMMER);

        restContactpersoonrolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContactpersoonrol.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContactpersoonrol))
            )
            .andExpect(status().isOk());

        // Validate the Contactpersoonrol in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContactpersoonrolUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedContactpersoonrol, contactpersoonrol),
            getPersistedContactpersoonrol(contactpersoonrol)
        );
    }

    @Test
    @Transactional
    void fullUpdateContactpersoonrolWithPatch() throws Exception {
        // Initialize the database
        contactpersoonrolRepository.saveAndFlush(contactpersoonrol);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contactpersoonrol using partial update
        Contactpersoonrol partialUpdatedContactpersoonrol = new Contactpersoonrol();
        partialUpdatedContactpersoonrol.setId(contactpersoonrol.getId());

        partialUpdatedContactpersoonrol
            .contactpersoonemailadres(UPDATED_CONTACTPERSOONEMAILADRES)
            .contactpersoonfunctie(UPDATED_CONTACTPERSOONFUNCTIE)
            .contactpersoonnaam(UPDATED_CONTACTPERSOONNAAM)
            .contactpersoontelefoonnummer(UPDATED_CONTACTPERSOONTELEFOONNUMMER);

        restContactpersoonrolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContactpersoonrol.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContactpersoonrol))
            )
            .andExpect(status().isOk());

        // Validate the Contactpersoonrol in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContactpersoonrolUpdatableFieldsEquals(
            partialUpdatedContactpersoonrol,
            getPersistedContactpersoonrol(partialUpdatedContactpersoonrol)
        );
    }

    @Test
    @Transactional
    void patchNonExistingContactpersoonrol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contactpersoonrol.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactpersoonrolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contactpersoonrol.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(contactpersoonrol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contactpersoonrol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContactpersoonrol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contactpersoonrol.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactpersoonrolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(contactpersoonrol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contactpersoonrol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContactpersoonrol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contactpersoonrol.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactpersoonrolMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(contactpersoonrol)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contactpersoonrol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContactpersoonrol() throws Exception {
        // Initialize the database
        contactpersoonrolRepository.saveAndFlush(contactpersoonrol);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the contactpersoonrol
        restContactpersoonrolMockMvc
            .perform(delete(ENTITY_API_URL_ID, contactpersoonrol.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return contactpersoonrolRepository.count();
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

    protected Contactpersoonrol getPersistedContactpersoonrol(Contactpersoonrol contactpersoonrol) {
        return contactpersoonrolRepository.findById(contactpersoonrol.getId()).orElseThrow();
    }

    protected void assertPersistedContactpersoonrolToMatchAllProperties(Contactpersoonrol expectedContactpersoonrol) {
        assertContactpersoonrolAllPropertiesEquals(expectedContactpersoonrol, getPersistedContactpersoonrol(expectedContactpersoonrol));
    }

    protected void assertPersistedContactpersoonrolToMatchUpdatableProperties(Contactpersoonrol expectedContactpersoonrol) {
        assertContactpersoonrolAllUpdatablePropertiesEquals(
            expectedContactpersoonrol,
            getPersistedContactpersoonrol(expectedContactpersoonrol)
        );
    }
}
