package nl.ritense.demo.web.rest;

import static nl.ritense.demo.domain.SensorAsserts.*;
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
import nl.ritense.demo.domain.Sensor;
import nl.ritense.demo.repository.SensorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SensorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SensorResourceIT {

    private static final String DEFAULT_AANLEGHOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_AANLEGHOOGTE = "BBBBBBBBBB";

    private static final String DEFAULT_ELEKTRAKAST = "AAAAAAAAAA";
    private static final String UPDATED_ELEKTRAKAST = "BBBBBBBBBB";

    private static final String DEFAULT_FREQUENTIEOMVORMER = "AAAAAAAAAA";
    private static final String UPDATED_FREQUENTIEOMVORMER = "BBBBBBBBBB";

    private static final String DEFAULT_HOOGTE = "AAAAAAAAAA";
    private static final String UPDATED_HOOGTE = "BBBBBBBBBB";

    private static final String DEFAULT_JAARONDERHOUDUITGEVOERD = "AAAAAAAAAA";
    private static final String UPDATED_JAARONDERHOUDUITGEVOERD = "BBBBBBBBBB";

    private static final String DEFAULT_LEVERANCIER = "AAAAAAAAAA";
    private static final String UPDATED_LEVERANCIER = "BBBBBBBBBB";

    private static final String DEFAULT_MEETPUNT = "AAAAAAAAAA";
    private static final String UPDATED_MEETPUNT = "BBBBBBBBBB";

    private static final String DEFAULT_PLC = "AAAAAAAAAA";
    private static final String UPDATED_PLC = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sensors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSensorMockMvc;

    private Sensor sensor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sensor createEntity(EntityManager em) {
        Sensor sensor = new Sensor()
            .aanleghoogte(DEFAULT_AANLEGHOOGTE)
            .elektrakast(DEFAULT_ELEKTRAKAST)
            .frequentieomvormer(DEFAULT_FREQUENTIEOMVORMER)
            .hoogte(DEFAULT_HOOGTE)
            .jaaronderhouduitgevoerd(DEFAULT_JAARONDERHOUDUITGEVOERD)
            .leverancier(DEFAULT_LEVERANCIER)
            .meetpunt(DEFAULT_MEETPUNT)
            .plc(DEFAULT_PLC);
        return sensor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sensor createUpdatedEntity(EntityManager em) {
        Sensor sensor = new Sensor()
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .elektrakast(UPDATED_ELEKTRAKAST)
            .frequentieomvormer(UPDATED_FREQUENTIEOMVORMER)
            .hoogte(UPDATED_HOOGTE)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .leverancier(UPDATED_LEVERANCIER)
            .meetpunt(UPDATED_MEETPUNT)
            .plc(UPDATED_PLC);
        return sensor;
    }

    @BeforeEach
    public void initTest() {
        sensor = createEntity(em);
    }

    @Test
    @Transactional
    void createSensor() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Sensor
        var returnedSensor = om.readValue(
            restSensorMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sensor)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Sensor.class
        );

        // Validate the Sensor in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSensorUpdatableFieldsEquals(returnedSensor, getPersistedSensor(returnedSensor));
    }

    @Test
    @Transactional
    void createSensorWithExistingId() throws Exception {
        // Create the Sensor with an existing ID
        sensor.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSensorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sensor)))
            .andExpect(status().isBadRequest());

        // Validate the Sensor in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSensors() throws Exception {
        // Initialize the database
        sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList
        restSensorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sensor.getId().intValue())))
            .andExpect(jsonPath("$.[*].aanleghoogte").value(hasItem(DEFAULT_AANLEGHOOGTE)))
            .andExpect(jsonPath("$.[*].elektrakast").value(hasItem(DEFAULT_ELEKTRAKAST)))
            .andExpect(jsonPath("$.[*].frequentieomvormer").value(hasItem(DEFAULT_FREQUENTIEOMVORMER)))
            .andExpect(jsonPath("$.[*].hoogte").value(hasItem(DEFAULT_HOOGTE)))
            .andExpect(jsonPath("$.[*].jaaronderhouduitgevoerd").value(hasItem(DEFAULT_JAARONDERHOUDUITGEVOERD)))
            .andExpect(jsonPath("$.[*].leverancier").value(hasItem(DEFAULT_LEVERANCIER)))
            .andExpect(jsonPath("$.[*].meetpunt").value(hasItem(DEFAULT_MEETPUNT)))
            .andExpect(jsonPath("$.[*].plc").value(hasItem(DEFAULT_PLC)));
    }

    @Test
    @Transactional
    void getSensor() throws Exception {
        // Initialize the database
        sensorRepository.saveAndFlush(sensor);

        // Get the sensor
        restSensorMockMvc
            .perform(get(ENTITY_API_URL_ID, sensor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sensor.getId().intValue()))
            .andExpect(jsonPath("$.aanleghoogte").value(DEFAULT_AANLEGHOOGTE))
            .andExpect(jsonPath("$.elektrakast").value(DEFAULT_ELEKTRAKAST))
            .andExpect(jsonPath("$.frequentieomvormer").value(DEFAULT_FREQUENTIEOMVORMER))
            .andExpect(jsonPath("$.hoogte").value(DEFAULT_HOOGTE))
            .andExpect(jsonPath("$.jaaronderhouduitgevoerd").value(DEFAULT_JAARONDERHOUDUITGEVOERD))
            .andExpect(jsonPath("$.leverancier").value(DEFAULT_LEVERANCIER))
            .andExpect(jsonPath("$.meetpunt").value(DEFAULT_MEETPUNT))
            .andExpect(jsonPath("$.plc").value(DEFAULT_PLC));
    }

    @Test
    @Transactional
    void getNonExistingSensor() throws Exception {
        // Get the sensor
        restSensorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSensor() throws Exception {
        // Initialize the database
        sensorRepository.saveAndFlush(sensor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sensor
        Sensor updatedSensor = sensorRepository.findById(sensor.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSensor are not directly saved in db
        em.detach(updatedSensor);
        updatedSensor
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .elektrakast(UPDATED_ELEKTRAKAST)
            .frequentieomvormer(UPDATED_FREQUENTIEOMVORMER)
            .hoogte(UPDATED_HOOGTE)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .leverancier(UPDATED_LEVERANCIER)
            .meetpunt(UPDATED_MEETPUNT)
            .plc(UPDATED_PLC);

        restSensorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSensor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSensor))
            )
            .andExpect(status().isOk());

        // Validate the Sensor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSensorToMatchAllProperties(updatedSensor);
    }

    @Test
    @Transactional
    void putNonExistingSensor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sensor.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSensorMockMvc
            .perform(put(ENTITY_API_URL_ID, sensor.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sensor)))
            .andExpect(status().isBadRequest());

        // Validate the Sensor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSensor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sensor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSensorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sensor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sensor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSensor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sensor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSensorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sensor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sensor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSensorWithPatch() throws Exception {
        // Initialize the database
        sensorRepository.saveAndFlush(sensor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sensor using partial update
        Sensor partialUpdatedSensor = new Sensor();
        partialUpdatedSensor.setId(sensor.getId());

        partialUpdatedSensor
            .frequentieomvormer(UPDATED_FREQUENTIEOMVORMER)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .leverancier(UPDATED_LEVERANCIER)
            .plc(UPDATED_PLC);

        restSensorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSensor.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSensor))
            )
            .andExpect(status().isOk());

        // Validate the Sensor in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSensorUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSensor, sensor), getPersistedSensor(sensor));
    }

    @Test
    @Transactional
    void fullUpdateSensorWithPatch() throws Exception {
        // Initialize the database
        sensorRepository.saveAndFlush(sensor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sensor using partial update
        Sensor partialUpdatedSensor = new Sensor();
        partialUpdatedSensor.setId(sensor.getId());

        partialUpdatedSensor
            .aanleghoogte(UPDATED_AANLEGHOOGTE)
            .elektrakast(UPDATED_ELEKTRAKAST)
            .frequentieomvormer(UPDATED_FREQUENTIEOMVORMER)
            .hoogte(UPDATED_HOOGTE)
            .jaaronderhouduitgevoerd(UPDATED_JAARONDERHOUDUITGEVOERD)
            .leverancier(UPDATED_LEVERANCIER)
            .meetpunt(UPDATED_MEETPUNT)
            .plc(UPDATED_PLC);

        restSensorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSensor.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSensor))
            )
            .andExpect(status().isOk());

        // Validate the Sensor in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSensorUpdatableFieldsEquals(partialUpdatedSensor, getPersistedSensor(partialUpdatedSensor));
    }

    @Test
    @Transactional
    void patchNonExistingSensor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sensor.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSensorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sensor.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sensor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sensor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSensor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sensor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSensorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sensor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sensor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSensor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sensor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSensorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sensor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sensor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSensor() throws Exception {
        // Initialize the database
        sensorRepository.saveAndFlush(sensor);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sensor
        restSensorMockMvc
            .perform(delete(ENTITY_API_URL_ID, sensor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sensorRepository.count();
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

    protected Sensor getPersistedSensor(Sensor sensor) {
        return sensorRepository.findById(sensor.getId()).orElseThrow();
    }

    protected void assertPersistedSensorToMatchAllProperties(Sensor expectedSensor) {
        assertSensorAllPropertiesEquals(expectedSensor, getPersistedSensor(expectedSensor));
    }

    protected void assertPersistedSensorToMatchUpdatableProperties(Sensor expectedSensor) {
        assertSensorAllUpdatablePropertiesEquals(expectedSensor, getPersistedSensor(expectedSensor));
    }
}
