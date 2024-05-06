package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SensorTestSamples.*;
import static nl.ritense.demo.domain.VerkeerstellingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SensorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sensor.class);
        Sensor sensor1 = getSensorSample1();
        Sensor sensor2 = new Sensor();
        assertThat(sensor1).isNotEqualTo(sensor2);

        sensor2.setId(sensor1.getId());
        assertThat(sensor1).isEqualTo(sensor2);

        sensor2 = getSensorSample2();
        assertThat(sensor1).isNotEqualTo(sensor2);
    }

    @Test
    void gegenereerddoorVerkeerstellingTest() throws Exception {
        Sensor sensor = getSensorRandomSampleGenerator();
        Verkeerstelling verkeerstellingBack = getVerkeerstellingRandomSampleGenerator();

        sensor.addGegenereerddoorVerkeerstelling(verkeerstellingBack);
        assertThat(sensor.getGegenereerddoorVerkeerstellings()).containsOnly(verkeerstellingBack);
        assertThat(verkeerstellingBack.getGegenereerddoorSensor()).isEqualTo(sensor);

        sensor.removeGegenereerddoorVerkeerstelling(verkeerstellingBack);
        assertThat(sensor.getGegenereerddoorVerkeerstellings()).doesNotContain(verkeerstellingBack);
        assertThat(verkeerstellingBack.getGegenereerddoorSensor()).isNull();

        sensor.gegenereerddoorVerkeerstellings(new HashSet<>(Set.of(verkeerstellingBack)));
        assertThat(sensor.getGegenereerddoorVerkeerstellings()).containsOnly(verkeerstellingBack);
        assertThat(verkeerstellingBack.getGegenereerddoorSensor()).isEqualTo(sensor);

        sensor.setGegenereerddoorVerkeerstellings(new HashSet<>());
        assertThat(sensor.getGegenereerddoorVerkeerstellings()).doesNotContain(verkeerstellingBack);
        assertThat(verkeerstellingBack.getGegenereerddoorSensor()).isNull();
    }
}
