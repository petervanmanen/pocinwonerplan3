package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.GeweldsincidentTestSamples.*;
import static nl.ritense.demo.domain.WerknemerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GeweldsincidentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Geweldsincident.class);
        Geweldsincident geweldsincident1 = getGeweldsincidentSample1();
        Geweldsincident geweldsincident2 = new Geweldsincident();
        assertThat(geweldsincident1).isNotEqualTo(geweldsincident2);

        geweldsincident2.setId(geweldsincident1.getId());
        assertThat(geweldsincident1).isEqualTo(geweldsincident2);

        geweldsincident2 = getGeweldsincidentSample2();
        assertThat(geweldsincident1).isNotEqualTo(geweldsincident2);
    }

    @Test
    void heeftondergaanWerknemerTest() throws Exception {
        Geweldsincident geweldsincident = getGeweldsincidentRandomSampleGenerator();
        Werknemer werknemerBack = getWerknemerRandomSampleGenerator();

        geweldsincident.addHeeftondergaanWerknemer(werknemerBack);
        assertThat(geweldsincident.getHeeftondergaanWerknemers()).containsOnly(werknemerBack);
        assertThat(werknemerBack.getHeeftondergaanGeweldsincident()).isEqualTo(geweldsincident);

        geweldsincident.removeHeeftondergaanWerknemer(werknemerBack);
        assertThat(geweldsincident.getHeeftondergaanWerknemers()).doesNotContain(werknemerBack);
        assertThat(werknemerBack.getHeeftondergaanGeweldsincident()).isNull();

        geweldsincident.heeftondergaanWerknemers(new HashSet<>(Set.of(werknemerBack)));
        assertThat(geweldsincident.getHeeftondergaanWerknemers()).containsOnly(werknemerBack);
        assertThat(werknemerBack.getHeeftondergaanGeweldsincident()).isEqualTo(geweldsincident);

        geweldsincident.setHeeftondergaanWerknemers(new HashSet<>());
        assertThat(geweldsincident.getHeeftondergaanWerknemers()).doesNotContain(werknemerBack);
        assertThat(werknemerBack.getHeeftondergaanGeweldsincident()).isNull();
    }
}
