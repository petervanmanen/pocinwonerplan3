package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OnderhoudTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OnderhoudTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Onderhoud.class);
        Onderhoud onderhoud1 = getOnderhoudSample1();
        Onderhoud onderhoud2 = new Onderhoud();
        assertThat(onderhoud1).isNotEqualTo(onderhoud2);

        onderhoud2.setId(onderhoud1.getId());
        assertThat(onderhoud1).isEqualTo(onderhoud2);

        onderhoud2 = getOnderhoudSample2();
        assertThat(onderhoud1).isNotEqualTo(onderhoud2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Onderhoud onderhoud = new Onderhoud();
        assertThat(onderhoud.hashCode()).isZero();

        Onderhoud onderhoud1 = getOnderhoudSample1();
        onderhoud.setId(onderhoud1.getId());
        assertThat(onderhoud).hasSameHashCodeAs(onderhoud1);
    }
}
