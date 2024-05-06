package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BouwdeelTestSamples.*;
import static nl.ritense.demo.domain.BouwdeelelementTestSamples.*;
import static nl.ritense.demo.domain.VastgoedobjectTestSamples.*;
import static nl.ritense.demo.domain.WerkbonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BouwdeelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bouwdeel.class);
        Bouwdeel bouwdeel1 = getBouwdeelSample1();
        Bouwdeel bouwdeel2 = new Bouwdeel();
        assertThat(bouwdeel1).isNotEqualTo(bouwdeel2);

        bouwdeel2.setId(bouwdeel1.getId());
        assertThat(bouwdeel1).isEqualTo(bouwdeel2);

        bouwdeel2 = getBouwdeelSample2();
        assertThat(bouwdeel1).isNotEqualTo(bouwdeel2);
    }

    @Test
    void bestaatuitBouwdeelelementTest() throws Exception {
        Bouwdeel bouwdeel = getBouwdeelRandomSampleGenerator();
        Bouwdeelelement bouwdeelelementBack = getBouwdeelelementRandomSampleGenerator();

        bouwdeel.addBestaatuitBouwdeelelement(bouwdeelelementBack);
        assertThat(bouwdeel.getBestaatuitBouwdeelelements()).containsOnly(bouwdeelelementBack);
        assertThat(bouwdeelelementBack.getBestaatuitBouwdeel()).isEqualTo(bouwdeel);

        bouwdeel.removeBestaatuitBouwdeelelement(bouwdeelelementBack);
        assertThat(bouwdeel.getBestaatuitBouwdeelelements()).doesNotContain(bouwdeelelementBack);
        assertThat(bouwdeelelementBack.getBestaatuitBouwdeel()).isNull();

        bouwdeel.bestaatuitBouwdeelelements(new HashSet<>(Set.of(bouwdeelelementBack)));
        assertThat(bouwdeel.getBestaatuitBouwdeelelements()).containsOnly(bouwdeelelementBack);
        assertThat(bouwdeelelementBack.getBestaatuitBouwdeel()).isEqualTo(bouwdeel);

        bouwdeel.setBestaatuitBouwdeelelements(new HashSet<>());
        assertThat(bouwdeel.getBestaatuitBouwdeelelements()).doesNotContain(bouwdeelelementBack);
        assertThat(bouwdeelelementBack.getBestaatuitBouwdeel()).isNull();
    }

    @Test
    void bestaatuitVastgoedobjectTest() throws Exception {
        Bouwdeel bouwdeel = getBouwdeelRandomSampleGenerator();
        Vastgoedobject vastgoedobjectBack = getVastgoedobjectRandomSampleGenerator();

        bouwdeel.setBestaatuitVastgoedobject(vastgoedobjectBack);
        assertThat(bouwdeel.getBestaatuitVastgoedobject()).isEqualTo(vastgoedobjectBack);

        bouwdeel.bestaatuitVastgoedobject(null);
        assertThat(bouwdeel.getBestaatuitVastgoedobject()).isNull();
    }

    @Test
    void betreftWerkbonTest() throws Exception {
        Bouwdeel bouwdeel = getBouwdeelRandomSampleGenerator();
        Werkbon werkbonBack = getWerkbonRandomSampleGenerator();

        bouwdeel.addBetreftWerkbon(werkbonBack);
        assertThat(bouwdeel.getBetreftWerkbons()).containsOnly(werkbonBack);
        assertThat(werkbonBack.getBetreftBouwdeels()).containsOnly(bouwdeel);

        bouwdeel.removeBetreftWerkbon(werkbonBack);
        assertThat(bouwdeel.getBetreftWerkbons()).doesNotContain(werkbonBack);
        assertThat(werkbonBack.getBetreftBouwdeels()).doesNotContain(bouwdeel);

        bouwdeel.betreftWerkbons(new HashSet<>(Set.of(werkbonBack)));
        assertThat(bouwdeel.getBetreftWerkbons()).containsOnly(werkbonBack);
        assertThat(werkbonBack.getBetreftBouwdeels()).containsOnly(bouwdeel);

        bouwdeel.setBetreftWerkbons(new HashSet<>());
        assertThat(bouwdeel.getBetreftWerkbons()).doesNotContain(werkbonBack);
        assertThat(werkbonBack.getBetreftBouwdeels()).doesNotContain(bouwdeel);
    }
}
