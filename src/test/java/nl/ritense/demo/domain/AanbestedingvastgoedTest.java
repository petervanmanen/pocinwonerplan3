package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AanbestedingvastgoedTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AanbestedingvastgoedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aanbestedingvastgoed.class);
        Aanbestedingvastgoed aanbestedingvastgoed1 = getAanbestedingvastgoedSample1();
        Aanbestedingvastgoed aanbestedingvastgoed2 = new Aanbestedingvastgoed();
        assertThat(aanbestedingvastgoed1).isNotEqualTo(aanbestedingvastgoed2);

        aanbestedingvastgoed2.setId(aanbestedingvastgoed1.getId());
        assertThat(aanbestedingvastgoed1).isEqualTo(aanbestedingvastgoed2);

        aanbestedingvastgoed2 = getAanbestedingvastgoedSample2();
        assertThat(aanbestedingvastgoed1).isNotEqualTo(aanbestedingvastgoed2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Aanbestedingvastgoed aanbestedingvastgoed = new Aanbestedingvastgoed();
        assertThat(aanbestedingvastgoed.hashCode()).isZero();

        Aanbestedingvastgoed aanbestedingvastgoed1 = getAanbestedingvastgoedSample1();
        aanbestedingvastgoed.setId(aanbestedingvastgoed1.getId());
        assertThat(aanbestedingvastgoed).hasSameHashCodeAs(aanbestedingvastgoed1);
    }
}
