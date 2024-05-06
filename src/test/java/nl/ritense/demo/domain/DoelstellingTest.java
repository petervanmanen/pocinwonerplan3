package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BegrotingregelTestSamples.*;
import static nl.ritense.demo.domain.DoelstellingTestSamples.*;
import static nl.ritense.demo.domain.DoelstellingsoortTestSamples.*;
import static nl.ritense.demo.domain.HoofdstukTestSamples.*;
import static nl.ritense.demo.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DoelstellingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Doelstelling.class);
        Doelstelling doelstelling1 = getDoelstellingSample1();
        Doelstelling doelstelling2 = new Doelstelling();
        assertThat(doelstelling1).isNotEqualTo(doelstelling2);

        doelstelling2.setId(doelstelling1.getId());
        assertThat(doelstelling1).isEqualTo(doelstelling2);

        doelstelling2 = getDoelstellingSample2();
        assertThat(doelstelling1).isNotEqualTo(doelstelling2);
    }

    @Test
    void heeftProductTest() throws Exception {
        Doelstelling doelstelling = getDoelstellingRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        doelstelling.addHeeftProduct(productBack);
        assertThat(doelstelling.getHeeftProducts()).containsOnly(productBack);
        assertThat(productBack.getHeeftDoelstelling()).isEqualTo(doelstelling);

        doelstelling.removeHeeftProduct(productBack);
        assertThat(doelstelling.getHeeftProducts()).doesNotContain(productBack);
        assertThat(productBack.getHeeftDoelstelling()).isNull();

        doelstelling.heeftProducts(new HashSet<>(Set.of(productBack)));
        assertThat(doelstelling.getHeeftProducts()).containsOnly(productBack);
        assertThat(productBack.getHeeftDoelstelling()).isEqualTo(doelstelling);

        doelstelling.setHeeftProducts(new HashSet<>());
        assertThat(doelstelling.getHeeftProducts()).doesNotContain(productBack);
        assertThat(productBack.getHeeftDoelstelling()).isNull();
    }

    @Test
    void isvansoortDoelstellingsoortTest() throws Exception {
        Doelstelling doelstelling = getDoelstellingRandomSampleGenerator();
        Doelstellingsoort doelstellingsoortBack = getDoelstellingsoortRandomSampleGenerator();

        doelstelling.setIsvansoortDoelstellingsoort(doelstellingsoortBack);
        assertThat(doelstelling.getIsvansoortDoelstellingsoort()).isEqualTo(doelstellingsoortBack);

        doelstelling.isvansoortDoelstellingsoort(null);
        assertThat(doelstelling.getIsvansoortDoelstellingsoort()).isNull();
    }

    @Test
    void heeftHoofdstukTest() throws Exception {
        Doelstelling doelstelling = getDoelstellingRandomSampleGenerator();
        Hoofdstuk hoofdstukBack = getHoofdstukRandomSampleGenerator();

        doelstelling.setHeeftHoofdstuk(hoofdstukBack);
        assertThat(doelstelling.getHeeftHoofdstuk()).isEqualTo(hoofdstukBack);

        doelstelling.heeftHoofdstuk(null);
        assertThat(doelstelling.getHeeftHoofdstuk()).isNull();
    }

    @Test
    void betreftBegrotingregelTest() throws Exception {
        Doelstelling doelstelling = getDoelstellingRandomSampleGenerator();
        Begrotingregel begrotingregelBack = getBegrotingregelRandomSampleGenerator();

        doelstelling.addBetreftBegrotingregel(begrotingregelBack);
        assertThat(doelstelling.getBetreftBegrotingregels()).containsOnly(begrotingregelBack);
        assertThat(begrotingregelBack.getBetreftDoelstelling()).isEqualTo(doelstelling);

        doelstelling.removeBetreftBegrotingregel(begrotingregelBack);
        assertThat(doelstelling.getBetreftBegrotingregels()).doesNotContain(begrotingregelBack);
        assertThat(begrotingregelBack.getBetreftDoelstelling()).isNull();

        doelstelling.betreftBegrotingregels(new HashSet<>(Set.of(begrotingregelBack)));
        assertThat(doelstelling.getBetreftBegrotingregels()).containsOnly(begrotingregelBack);
        assertThat(begrotingregelBack.getBetreftDoelstelling()).isEqualTo(doelstelling);

        doelstelling.setBetreftBegrotingregels(new HashSet<>());
        assertThat(doelstelling.getBetreftBegrotingregels()).doesNotContain(begrotingregelBack);
        assertThat(begrotingregelBack.getBetreftDoelstelling()).isNull();
    }
}
