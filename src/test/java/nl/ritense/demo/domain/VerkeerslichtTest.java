package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VerkeerslichtTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerkeerslichtTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verkeerslicht.class);
        Verkeerslicht verkeerslicht1 = getVerkeerslichtSample1();
        Verkeerslicht verkeerslicht2 = new Verkeerslicht();
        assertThat(verkeerslicht1).isNotEqualTo(verkeerslicht2);

        verkeerslicht2.setId(verkeerslicht1.getId());
        assertThat(verkeerslicht1).isEqualTo(verkeerslicht2);

        verkeerslicht2 = getVerkeerslichtSample2();
        assertThat(verkeerslicht1).isNotEqualTo(verkeerslicht2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Verkeerslicht verkeerslicht = new Verkeerslicht();
        assertThat(verkeerslicht.hashCode()).isZero();

        Verkeerslicht verkeerslicht1 = getVerkeerslichtSample1();
        verkeerslicht.setId(verkeerslicht1.getId());
        assertThat(verkeerslicht).hasSameHashCodeAs(verkeerslicht1);
    }
}
