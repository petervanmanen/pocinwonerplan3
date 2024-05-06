package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BoaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BoaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Boa.class);
        Boa boa1 = getBoaSample1();
        Boa boa2 = new Boa();
        assertThat(boa1).isNotEqualTo(boa2);

        boa2.setId(boa1.getId());
        assertThat(boa1).isEqualTo(boa2);

        boa2 = getBoaSample2();
        assertThat(boa1).isNotEqualTo(boa2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Boa boa = new Boa();
        assertThat(boa.hashCode()).isZero();

        Boa boa1 = getBoaSample1();
        boa.setId(boa1.getId());
        assertThat(boa).hasSameHashCodeAs(boa1);
    }
}
