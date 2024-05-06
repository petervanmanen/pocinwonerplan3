package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.GebiedengroepTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GebiedengroepTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gebiedengroep.class);
        Gebiedengroep gebiedengroep1 = getGebiedengroepSample1();
        Gebiedengroep gebiedengroep2 = new Gebiedengroep();
        assertThat(gebiedengroep1).isNotEqualTo(gebiedengroep2);

        gebiedengroep2.setId(gebiedengroep1.getId());
        assertThat(gebiedengroep1).isEqualTo(gebiedengroep2);

        gebiedengroep2 = getGebiedengroepSample2();
        assertThat(gebiedengroep1).isNotEqualTo(gebiedengroep2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Gebiedengroep gebiedengroep = new Gebiedengroep();
        assertThat(gebiedengroep.hashCode()).isZero();

        Gebiedengroep gebiedengroep1 = getGebiedengroepSample1();
        gebiedengroep.setId(gebiedengroep1.getId());
        assertThat(gebiedengroep).hasSameHashCodeAs(gebiedengroep1);
    }
}
