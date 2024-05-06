package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.ServerAsserts.*;
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
import nl.ritense.demo.domain.Server;
import nl.ritense.demo.repository.ServerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ServerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServerResourceIT {

    private static final Boolean DEFAULT_ACTIEF = false;
    private static final Boolean UPDATED_ACTIEF = true;

    private static final String DEFAULT_IPADRES = "AAAAAAAAAA";
    private static final String UPDATED_IPADRES = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATIE = "AAAAAAAAAA";
    private static final String UPDATED_LOCATIE = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANISATIE = "AAAAAAAAAA";
    private static final String UPDATED_ORGANISATIE = "BBBBBBBBBB";

    private static final String DEFAULT_SERIENUMMER = "AAAAAAAAAA";
    private static final String UPDATED_SERIENUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_SERVERID = "AAAAAAAAAA";
    private static final String UPDATED_SERVERID = "BBBBBBBBBB";

    private static final String DEFAULT_SERVERTYPE = "AAAAAAAAAA";
    private static final String UPDATED_SERVERTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VLAN = "AAAAAAAAAA";
    private static final String UPDATED_VLAN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/servers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ServerRepository serverRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServerMockMvc;

    private Server server;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Server createEntity(EntityManager em) {
        Server server = new Server()
            .actief(DEFAULT_ACTIEF)
            .ipadres(DEFAULT_IPADRES)
            .locatie(DEFAULT_LOCATIE)
            .organisatie(DEFAULT_ORGANISATIE)
            .serienummer(DEFAULT_SERIENUMMER)
            .serverid(DEFAULT_SERVERID)
            .servertype(DEFAULT_SERVERTYPE)
            .vlan(DEFAULT_VLAN);
        return server;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Server createUpdatedEntity(EntityManager em) {
        Server server = new Server()
            .actief(UPDATED_ACTIEF)
            .ipadres(UPDATED_IPADRES)
            .locatie(UPDATED_LOCATIE)
            .organisatie(UPDATED_ORGANISATIE)
            .serienummer(UPDATED_SERIENUMMER)
            .serverid(UPDATED_SERVERID)
            .servertype(UPDATED_SERVERTYPE)
            .vlan(UPDATED_VLAN);
        return server;
    }

    @BeforeEach
    public void initTest() {
        server = createEntity(em);
    }

    @Test
    @Transactional
    void createServer() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Server
        var returnedServer = om.readValue(
            restServerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(server)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Server.class
        );

        // Validate the Server in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertServerUpdatableFieldsEquals(returnedServer, getPersistedServer(returnedServer));
    }

    @Test
    @Transactional
    void createServerWithExistingId() throws Exception {
        // Create the Server with an existing ID
        server.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(server)))
            .andExpect(status().isBadRequest());

        // Validate the Server in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllServers() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get all the serverList
        restServerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(server.getId().intValue())))
            .andExpect(jsonPath("$.[*].actief").value(hasItem(DEFAULT_ACTIEF.booleanValue())))
            .andExpect(jsonPath("$.[*].ipadres").value(hasItem(DEFAULT_IPADRES)))
            .andExpect(jsonPath("$.[*].locatie").value(hasItem(DEFAULT_LOCATIE)))
            .andExpect(jsonPath("$.[*].organisatie").value(hasItem(DEFAULT_ORGANISATIE)))
            .andExpect(jsonPath("$.[*].serienummer").value(hasItem(DEFAULT_SERIENUMMER)))
            .andExpect(jsonPath("$.[*].serverid").value(hasItem(DEFAULT_SERVERID)))
            .andExpect(jsonPath("$.[*].servertype").value(hasItem(DEFAULT_SERVERTYPE)))
            .andExpect(jsonPath("$.[*].vlan").value(hasItem(DEFAULT_VLAN)));
    }

    @Test
    @Transactional
    void getServer() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        // Get the server
        restServerMockMvc
            .perform(get(ENTITY_API_URL_ID, server.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(server.getId().intValue()))
            .andExpect(jsonPath("$.actief").value(DEFAULT_ACTIEF.booleanValue()))
            .andExpect(jsonPath("$.ipadres").value(DEFAULT_IPADRES))
            .andExpect(jsonPath("$.locatie").value(DEFAULT_LOCATIE))
            .andExpect(jsonPath("$.organisatie").value(DEFAULT_ORGANISATIE))
            .andExpect(jsonPath("$.serienummer").value(DEFAULT_SERIENUMMER))
            .andExpect(jsonPath("$.serverid").value(DEFAULT_SERVERID))
            .andExpect(jsonPath("$.servertype").value(DEFAULT_SERVERTYPE))
            .andExpect(jsonPath("$.vlan").value(DEFAULT_VLAN));
    }

    @Test
    @Transactional
    void getNonExistingServer() throws Exception {
        // Get the server
        restServerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingServer() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the server
        Server updatedServer = serverRepository.findById(server.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedServer are not directly saved in db
        em.detach(updatedServer);
        updatedServer
            .actief(UPDATED_ACTIEF)
            .ipadres(UPDATED_IPADRES)
            .locatie(UPDATED_LOCATIE)
            .organisatie(UPDATED_ORGANISATIE)
            .serienummer(UPDATED_SERIENUMMER)
            .serverid(UPDATED_SERVERID)
            .servertype(UPDATED_SERVERTYPE)
            .vlan(UPDATED_VLAN);

        restServerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedServer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedServer))
            )
            .andExpect(status().isOk());

        // Validate the Server in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedServerToMatchAllProperties(updatedServer);
    }

    @Test
    @Transactional
    void putNonExistingServer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        server.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServerMockMvc
            .perform(put(ENTITY_API_URL_ID, server.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(server)))
            .andExpect(status().isBadRequest());

        // Validate the Server in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        server.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(server))
            )
            .andExpect(status().isBadRequest());

        // Validate the Server in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        server.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(server)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Server in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServerWithPatch() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the server using partial update
        Server partialUpdatedServer = new Server();
        partialUpdatedServer.setId(server.getId());

        partialUpdatedServer
            .organisatie(UPDATED_ORGANISATIE)
            .serienummer(UPDATED_SERIENUMMER)
            .servertype(UPDATED_SERVERTYPE)
            .vlan(UPDATED_VLAN);

        restServerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServer))
            )
            .andExpect(status().isOk());

        // Validate the Server in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServerUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedServer, server), getPersistedServer(server));
    }

    @Test
    @Transactional
    void fullUpdateServerWithPatch() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the server using partial update
        Server partialUpdatedServer = new Server();
        partialUpdatedServer.setId(server.getId());

        partialUpdatedServer
            .actief(UPDATED_ACTIEF)
            .ipadres(UPDATED_IPADRES)
            .locatie(UPDATED_LOCATIE)
            .organisatie(UPDATED_ORGANISATIE)
            .serienummer(UPDATED_SERIENUMMER)
            .serverid(UPDATED_SERVERID)
            .servertype(UPDATED_SERVERTYPE)
            .vlan(UPDATED_VLAN);

        restServerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServer))
            )
            .andExpect(status().isOk());

        // Validate the Server in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServerUpdatableFieldsEquals(partialUpdatedServer, getPersistedServer(partialUpdatedServer));
    }

    @Test
    @Transactional
    void patchNonExistingServer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        server.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, server.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(server))
            )
            .andExpect(status().isBadRequest());

        // Validate the Server in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        server.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(server))
            )
            .andExpect(status().isBadRequest());

        // Validate the Server in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        server.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(server)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Server in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServer() throws Exception {
        // Initialize the database
        serverRepository.saveAndFlush(server);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the server
        restServerMockMvc
            .perform(delete(ENTITY_API_URL_ID, server.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return serverRepository.count();
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

    protected Server getPersistedServer(Server server) {
        return serverRepository.findById(server.getId()).orElseThrow();
    }

    protected void assertPersistedServerToMatchAllProperties(Server expectedServer) {
        assertServerAllPropertiesEquals(expectedServer, getPersistedServer(expectedServer));
    }

    protected void assertPersistedServerToMatchUpdatableProperties(Server expectedServer) {
        assertServerAllUpdatablePropertiesEquals(expectedServer, getPersistedServer(expectedServer));
    }
}
