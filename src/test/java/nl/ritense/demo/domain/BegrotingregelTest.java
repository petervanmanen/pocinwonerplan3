package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BegrotingTestSamples.*;
import static nl.ritense.demo.domain.BegrotingregelTestSamples.*;
import static nl.ritense.demo.domain.DoelstellingTestSamples.*;
import static nl.ritense.demo.domain.HoofdrekeningTestSamples.*;
import static nl.ritense.demo.domain.HoofdstukTestSamples.*;
import static nl.ritense.demo.domain.KostenplaatsTestSamples.*;
import static nl.ritense.demo.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BegrotingregelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Begrotingregel.class);
        Begrotingregel begrotingregel1 = getBegrotingregelSample1();
        Begrotingregel begrotingregel2 = new Begrotingregel();
        assertThat(begrotingregel1).isNotEqualTo(begrotingregel2);

        begrotingregel2.setId(begrotingregel1.getId());
        assertThat(begrotingregel1).isEqualTo(begrotingregel2);

        begrotingregel2 = getBegrotingregelSample2();
        assertThat(begrotingregel1).isNotEqualTo(begrotingregel2);
    }

    @Test
    void betreftDoelstellingTest() throws Exception {
        Begrotingregel begrotingregel = getBegrotingregelRandomSampleGenerator();
        Doelstelling doelstellingBack = getDoelstellingRandomSampleGenerator();

        begrotingregel.setBetreftDoelstelling(doelstellingBack);
        assertThat(begrotingregel.getBetreftDoelstelling()).isEqualTo(doelstellingBack);

        begrotingregel.betreftDoelstelling(null);
        assertThat(begrotingregel.getBetreftDoelstelling()).isNull();
    }

    @Test
    void betreftProductTest() throws Exception {
        Begrotingregel begrotingregel = getBegrotingregelRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        begrotingregel.setBetreftProduct(productBack);
        assertThat(begrotingregel.getBetreftProduct()).isEqualTo(productBack);

        begrotingregel.betreftProduct(null);
        assertThat(begrotingregel.getBetreftProduct()).isNull();
    }

    @Test
    void betreftKostenplaatsTest() throws Exception {
        Begrotingregel begrotingregel = getBegrotingregelRandomSampleGenerator();
        Kostenplaats kostenplaatsBack = getKostenplaatsRandomSampleGenerator();

        begrotingregel.setBetreftKostenplaats(kostenplaatsBack);
        assertThat(begrotingregel.getBetreftKostenplaats()).isEqualTo(kostenplaatsBack);

        begrotingregel.betreftKostenplaats(null);
        assertThat(begrotingregel.getBetreftKostenplaats()).isNull();
    }

    @Test
    void betreftHoofdrekeningTest() throws Exception {
        Begrotingregel begrotingregel = getBegrotingregelRandomSampleGenerator();
        Hoofdrekening hoofdrekeningBack = getHoofdrekeningRandomSampleGenerator();

        begrotingregel.setBetreftHoofdrekening(hoofdrekeningBack);
        assertThat(begrotingregel.getBetreftHoofdrekening()).isEqualTo(hoofdrekeningBack);

        begrotingregel.betreftHoofdrekening(null);
        assertThat(begrotingregel.getBetreftHoofdrekening()).isNull();
    }

    @Test
    void betreftHoofdstukTest() throws Exception {
        Begrotingregel begrotingregel = getBegrotingregelRandomSampleGenerator();
        Hoofdstuk hoofdstukBack = getHoofdstukRandomSampleGenerator();

        begrotingregel.setBetreftHoofdstuk(hoofdstukBack);
        assertThat(begrotingregel.getBetreftHoofdstuk()).isEqualTo(hoofdstukBack);

        begrotingregel.betreftHoofdstuk(null);
        assertThat(begrotingregel.getBetreftHoofdstuk()).isNull();
    }

    @Test
    void heeftBegrotingTest() throws Exception {
        Begrotingregel begrotingregel = getBegrotingregelRandomSampleGenerator();
        Begroting begrotingBack = getBegrotingRandomSampleGenerator();

        begrotingregel.setHeeftBegroting(begrotingBack);
        assertThat(begrotingregel.getHeeftBegroting()).isEqualTo(begrotingBack);

        begrotingregel.heeftBegroting(null);
        assertThat(begrotingregel.getHeeftBegroting()).isNull();
    }
}
