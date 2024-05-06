package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.JuridischeregelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JuridischeregelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Juridischeregel.class);
        Juridischeregel juridischeregel1 = getJuridischeregelSample1();
        Juridischeregel juridischeregel2 = new Juridischeregel();
        assertThat(juridischeregel1).isNotEqualTo(juridischeregel2);

        juridischeregel2.setId(juridischeregel1.getId());
        assertThat(juridischeregel1).isEqualTo(juridischeregel2);

        juridischeregel2 = getJuridischeregelSample2();
        assertThat(juridischeregel1).isNotEqualTo(juridischeregel2);
    }
}
