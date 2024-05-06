package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SensorTestSamples.*;
import static nl.ritense.demo.domain.VerkeerstellingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerkeerstellingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verkeerstelling.class);
        Verkeerstelling verkeerstelling1 = getVerkeerstellingSample1();
        Verkeerstelling verkeerstelling2 = new Verkeerstelling();
        assertThat(verkeerstelling1).isNotEqualTo(verkeerstelling2);

        verkeerstelling2.setId(verkeerstelling1.getId());
        assertThat(verkeerstelling1).isEqualTo(verkeerstelling2);

        verkeerstelling2 = getVerkeerstellingSample2();
        assertThat(verkeerstelling1).isNotEqualTo(verkeerstelling2);
    }

    @Test
    void gegenereerddoorSensorTest() throws Exception {
        Verkeerstelling verkeerstelling = getVerkeerstellingRandomSampleGenerator();
        Sensor sensorBack = getSensorRandomSampleGenerator();

        verkeerstelling.setGegenereerddoorSensor(sensorBack);
        assertThat(verkeerstelling.getGegenereerddoorSensor()).isEqualTo(sensorBack);

        verkeerstelling.gegenereerddoorSensor(null);
        assertThat(verkeerstelling.getGegenereerddoorSensor()).isNull();
    }
}
