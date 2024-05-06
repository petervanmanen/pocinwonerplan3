package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.DienstTestSamples.*;
import static nl.ritense.demo.domain.OnderwerpTestSamples.*;
import static nl.ritense.demo.domain.ProductTestSamples.*;
import static nl.ritense.demo.domain.ZaaktypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DienstTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dienst.class);
        Dienst dienst1 = getDienstSample1();
        Dienst dienst2 = new Dienst();
        assertThat(dienst1).isNotEqualTo(dienst2);

        dienst2.setId(dienst1.getId());
        assertThat(dienst1).isEqualTo(dienst2);

        dienst2 = getDienstSample2();
        assertThat(dienst1).isNotEqualTo(dienst2);
    }

    @Test
    void startZaaktypeTest() throws Exception {
        Dienst dienst = getDienstRandomSampleGenerator();
        Zaaktype zaaktypeBack = getZaaktypeRandomSampleGenerator();

        dienst.setStartZaaktype(zaaktypeBack);
        assertThat(dienst.getStartZaaktype()).isEqualTo(zaaktypeBack);

        dienst.startZaaktype(null);
        assertThat(dienst.getStartZaaktype()).isNull();
    }

    @Test
    void heeftOnderwerpTest() throws Exception {
        Dienst dienst = getDienstRandomSampleGenerator();
        Onderwerp onderwerpBack = getOnderwerpRandomSampleGenerator();

        dienst.setHeeftOnderwerp(onderwerpBack);
        assertThat(dienst.getHeeftOnderwerp()).isEqualTo(onderwerpBack);

        dienst.heeftOnderwerp(null);
        assertThat(dienst.getHeeftOnderwerp()).isNull();
    }

    @Test
    void betreftProductTest() throws Exception {
        Dienst dienst = getDienstRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        dienst.setBetreftProduct(productBack);
        assertThat(dienst.getBetreftProduct()).isEqualTo(productBack);

        dienst.betreftProduct(null);
        assertThat(dienst.getBetreftProduct()).isNull();
    }
}
