package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BredeintakeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BredeintakeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bredeintake.class);
        Bredeintake bredeintake1 = getBredeintakeSample1();
        Bredeintake bredeintake2 = new Bredeintake();
        assertThat(bredeintake1).isNotEqualTo(bredeintake2);

        bredeintake2.setId(bredeintake1.getId());
        assertThat(bredeintake1).isEqualTo(bredeintake2);

        bredeintake2 = getBredeintakeSample2();
        assertThat(bredeintake1).isNotEqualTo(bredeintake2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Bredeintake bredeintake = new Bredeintake();
        assertThat(bredeintake.hashCode()).isZero();

        Bredeintake bredeintake1 = getBredeintakeSample1();
        bredeintake.setId(bredeintake1.getId());
        assertThat(bredeintake).hasSameHashCodeAs(bredeintake1);
    }
}
