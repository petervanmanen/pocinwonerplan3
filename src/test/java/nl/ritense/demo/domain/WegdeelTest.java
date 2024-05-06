package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.StremmingTestSamples.*;
import static nl.ritense.demo.domain.WegdeelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WegdeelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wegdeel.class);
        Wegdeel wegdeel1 = getWegdeelSample1();
        Wegdeel wegdeel2 = new Wegdeel();
        assertThat(wegdeel1).isNotEqualTo(wegdeel2);

        wegdeel2.setId(wegdeel1.getId());
        assertThat(wegdeel1).isEqualTo(wegdeel2);

        wegdeel2 = getWegdeelSample2();
        assertThat(wegdeel1).isNotEqualTo(wegdeel2);
    }

    @Test
    void betreftStremmingTest() throws Exception {
        Wegdeel wegdeel = getWegdeelRandomSampleGenerator();
        Stremming stremmingBack = getStremmingRandomSampleGenerator();

        wegdeel.addBetreftStremming(stremmingBack);
        assertThat(wegdeel.getBetreftStremmings()).containsOnly(stremmingBack);
        assertThat(stremmingBack.getBetreftWegdeels()).containsOnly(wegdeel);

        wegdeel.removeBetreftStremming(stremmingBack);
        assertThat(wegdeel.getBetreftStremmings()).doesNotContain(stremmingBack);
        assertThat(stremmingBack.getBetreftWegdeels()).doesNotContain(wegdeel);

        wegdeel.betreftStremmings(new HashSet<>(Set.of(stremmingBack)));
        assertThat(wegdeel.getBetreftStremmings()).containsOnly(stremmingBack);
        assertThat(stremmingBack.getBetreftWegdeels()).containsOnly(wegdeel);

        wegdeel.setBetreftStremmings(new HashSet<>());
        assertThat(wegdeel.getBetreftStremmings()).doesNotContain(stremmingBack);
        assertThat(stremmingBack.getBetreftWegdeels()).doesNotContain(wegdeel);
    }
}
