package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.FactuurTestSamples.*;
import static nl.ritense.demo.domain.FactuurregelTestSamples.*;
import static nl.ritense.demo.domain.MutatieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FactuurregelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Factuurregel.class);
        Factuurregel factuurregel1 = getFactuurregelSample1();
        Factuurregel factuurregel2 = new Factuurregel();
        assertThat(factuurregel1).isNotEqualTo(factuurregel2);

        factuurregel2.setId(factuurregel1.getId());
        assertThat(factuurregel1).isEqualTo(factuurregel2);

        factuurregel2 = getFactuurregelSample2();
        assertThat(factuurregel1).isNotEqualTo(factuurregel2);
    }

    @Test
    void leidttotMutatieTest() throws Exception {
        Factuurregel factuurregel = getFactuurregelRandomSampleGenerator();
        Mutatie mutatieBack = getMutatieRandomSampleGenerator();

        factuurregel.setLeidttotMutatie(mutatieBack);
        assertThat(factuurregel.getLeidttotMutatie()).isEqualTo(mutatieBack);

        factuurregel.leidttotMutatie(null);
        assertThat(factuurregel.getLeidttotMutatie()).isNull();
    }

    @Test
    void heeftFactuurTest() throws Exception {
        Factuurregel factuurregel = getFactuurregelRandomSampleGenerator();
        Factuur factuurBack = getFactuurRandomSampleGenerator();

        factuurregel.setHeeftFactuur(factuurBack);
        assertThat(factuurregel.getHeeftFactuur()).isEqualTo(factuurBack);

        factuurregel.heeftFactuur(null);
        assertThat(factuurregel.getHeeftFactuur()).isNull();
    }
}
