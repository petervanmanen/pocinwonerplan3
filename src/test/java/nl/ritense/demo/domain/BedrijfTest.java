package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BedrijfTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BedrijfTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bedrijf.class);
        Bedrijf bedrijf1 = getBedrijfSample1();
        Bedrijf bedrijf2 = new Bedrijf();
        assertThat(bedrijf1).isNotEqualTo(bedrijf2);

        bedrijf2.setId(bedrijf1.getId());
        assertThat(bedrijf1).isEqualTo(bedrijf2);

        bedrijf2 = getBedrijfSample2();
        assertThat(bedrijf1).isNotEqualTo(bedrijf2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Bedrijf bedrijf = new Bedrijf();
        assertThat(bedrijf.hashCode()).isZero();

        Bedrijf bedrijf1 = getBedrijfSample1();
        bedrijf.setId(bedrijf1.getId());
        assertThat(bedrijf).hasSameHashCodeAs(bedrijf1);
    }
}
