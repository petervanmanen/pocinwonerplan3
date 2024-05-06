package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BouwdeelTestSamples.*;
import static nl.ritense.demo.domain.BouwdeelelementTestSamples.*;
import static nl.ritense.demo.domain.WerkbonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BouwdeelelementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bouwdeelelement.class);
        Bouwdeelelement bouwdeelelement1 = getBouwdeelelementSample1();
        Bouwdeelelement bouwdeelelement2 = new Bouwdeelelement();
        assertThat(bouwdeelelement1).isNotEqualTo(bouwdeelelement2);

        bouwdeelelement2.setId(bouwdeelelement1.getId());
        assertThat(bouwdeelelement1).isEqualTo(bouwdeelelement2);

        bouwdeelelement2 = getBouwdeelelementSample2();
        assertThat(bouwdeelelement1).isNotEqualTo(bouwdeelelement2);
    }

    @Test
    void bestaatuitBouwdeelTest() throws Exception {
        Bouwdeelelement bouwdeelelement = getBouwdeelelementRandomSampleGenerator();
        Bouwdeel bouwdeelBack = getBouwdeelRandomSampleGenerator();

        bouwdeelelement.setBestaatuitBouwdeel(bouwdeelBack);
        assertThat(bouwdeelelement.getBestaatuitBouwdeel()).isEqualTo(bouwdeelBack);

        bouwdeelelement.bestaatuitBouwdeel(null);
        assertThat(bouwdeelelement.getBestaatuitBouwdeel()).isNull();
    }

    @Test
    void betreftWerkbonTest() throws Exception {
        Bouwdeelelement bouwdeelelement = getBouwdeelelementRandomSampleGenerator();
        Werkbon werkbonBack = getWerkbonRandomSampleGenerator();

        bouwdeelelement.addBetreftWerkbon(werkbonBack);
        assertThat(bouwdeelelement.getBetreftWerkbons()).containsOnly(werkbonBack);
        assertThat(werkbonBack.getBetreftBouwdeelelements()).containsOnly(bouwdeelelement);

        bouwdeelelement.removeBetreftWerkbon(werkbonBack);
        assertThat(bouwdeelelement.getBetreftWerkbons()).doesNotContain(werkbonBack);
        assertThat(werkbonBack.getBetreftBouwdeelelements()).doesNotContain(bouwdeelelement);

        bouwdeelelement.betreftWerkbons(new HashSet<>(Set.of(werkbonBack)));
        assertThat(bouwdeelelement.getBetreftWerkbons()).containsOnly(werkbonBack);
        assertThat(werkbonBack.getBetreftBouwdeelelements()).containsOnly(bouwdeelelement);

        bouwdeelelement.setBetreftWerkbons(new HashSet<>());
        assertThat(bouwdeelelement.getBetreftWerkbons()).doesNotContain(werkbonBack);
        assertThat(werkbonBack.getBetreftBouwdeelelements()).doesNotContain(bouwdeelelement);
    }
}
