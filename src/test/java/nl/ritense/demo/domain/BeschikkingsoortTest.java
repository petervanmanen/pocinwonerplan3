package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BeschikkingsoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BeschikkingsoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Beschikkingsoort.class);
        Beschikkingsoort beschikkingsoort1 = getBeschikkingsoortSample1();
        Beschikkingsoort beschikkingsoort2 = new Beschikkingsoort();
        assertThat(beschikkingsoort1).isNotEqualTo(beschikkingsoort2);

        beschikkingsoort2.setId(beschikkingsoort1.getId());
        assertThat(beschikkingsoort1).isEqualTo(beschikkingsoort2);

        beschikkingsoort2 = getBeschikkingsoortSample2();
        assertThat(beschikkingsoort1).isNotEqualTo(beschikkingsoort2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Beschikkingsoort beschikkingsoort = new Beschikkingsoort();
        assertThat(beschikkingsoort.hashCode()).isZero();

        Beschikkingsoort beschikkingsoort1 = getBeschikkingsoortSample1();
        beschikkingsoort.setId(beschikkingsoort1.getId());
        assertThat(beschikkingsoort).hasSameHashCodeAs(beschikkingsoort1);
    }
}
