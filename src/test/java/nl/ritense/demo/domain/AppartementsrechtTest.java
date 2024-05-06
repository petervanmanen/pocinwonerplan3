package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AppartementsrechtTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppartementsrechtTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Appartementsrecht.class);
        Appartementsrecht appartementsrecht1 = getAppartementsrechtSample1();
        Appartementsrecht appartementsrecht2 = new Appartementsrecht();
        assertThat(appartementsrecht1).isNotEqualTo(appartementsrecht2);

        appartementsrecht2.setId(appartementsrecht1.getId());
        assertThat(appartementsrecht1).isEqualTo(appartementsrecht2);

        appartementsrecht2 = getAppartementsrechtSample2();
        assertThat(appartementsrecht1).isNotEqualTo(appartementsrecht2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Appartementsrecht appartementsrecht = new Appartementsrecht();
        assertThat(appartementsrecht.hashCode()).isZero();

        Appartementsrecht appartementsrecht1 = getAppartementsrechtSample1();
        appartementsrecht.setId(appartementsrecht1.getId());
        assertThat(appartementsrecht).hasSameHashCodeAs(appartementsrecht1);
    }
}
