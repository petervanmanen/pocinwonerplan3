package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.MaatregelenTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MaatregelenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Maatregelen.class);
        Maatregelen maatregelen1 = getMaatregelenSample1();
        Maatregelen maatregelen2 = new Maatregelen();
        assertThat(maatregelen1).isNotEqualTo(maatregelen2);

        maatregelen2.setId(maatregelen1.getId());
        assertThat(maatregelen1).isEqualTo(maatregelen2);

        maatregelen2 = getMaatregelenSample2();
        assertThat(maatregelen1).isNotEqualTo(maatregelen2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Maatregelen maatregelen = new Maatregelen();
        assertThat(maatregelen.hashCode()).isZero();

        Maatregelen maatregelen1 = getMaatregelenSample1();
        maatregelen.setId(maatregelen1.getId());
        assertThat(maatregelen).hasSameHashCodeAs(maatregelen1);
    }
}
