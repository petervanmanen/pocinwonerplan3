package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.PuntengroepTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PuntengroepTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Puntengroep.class);
        Puntengroep puntengroep1 = getPuntengroepSample1();
        Puntengroep puntengroep2 = new Puntengroep();
        assertThat(puntengroep1).isNotEqualTo(puntengroep2);

        puntengroep2.setId(puntengroep1.getId());
        assertThat(puntengroep1).isEqualTo(puntengroep2);

        puntengroep2 = getPuntengroepSample2();
        assertThat(puntengroep1).isNotEqualTo(puntengroep2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Puntengroep puntengroep = new Puntengroep();
        assertThat(puntengroep.hashCode()).isZero();

        Puntengroep puntengroep1 = getPuntengroepSample1();
        puntengroep.setId(puntengroep1.getId());
        assertThat(puntengroep).hasSameHashCodeAs(puntengroep1);
    }
}
