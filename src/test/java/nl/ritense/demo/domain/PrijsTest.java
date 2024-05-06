package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BalieverkoopTestSamples.*;
import static nl.ritense.demo.domain.PrijsTestSamples.*;
import static nl.ritense.demo.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrijsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prijs.class);
        Prijs prijs1 = getPrijsSample1();
        Prijs prijs2 = new Prijs();
        assertThat(prijs1).isNotEqualTo(prijs2);

        prijs2.setId(prijs1.getId());
        assertThat(prijs1).isEqualTo(prijs2);

        prijs2 = getPrijsSample2();
        assertThat(prijs1).isNotEqualTo(prijs2);
    }

    @Test
    void heeftprijsProductTest() throws Exception {
        Prijs prijs = getPrijsRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        prijs.setHeeftprijsProduct(productBack);
        assertThat(prijs.getHeeftprijsProduct()).isEqualTo(productBack);

        prijs.heeftprijsProduct(null);
        assertThat(prijs.getHeeftprijsProduct()).isNull();
    }

    @Test
    void tegenprijsBalieverkoopTest() throws Exception {
        Prijs prijs = getPrijsRandomSampleGenerator();
        Balieverkoop balieverkoopBack = getBalieverkoopRandomSampleGenerator();

        prijs.addTegenprijsBalieverkoop(balieverkoopBack);
        assertThat(prijs.getTegenprijsBalieverkoops()).containsOnly(balieverkoopBack);
        assertThat(balieverkoopBack.getTegenprijsPrijs()).isEqualTo(prijs);

        prijs.removeTegenprijsBalieverkoop(balieverkoopBack);
        assertThat(prijs.getTegenprijsBalieverkoops()).doesNotContain(balieverkoopBack);
        assertThat(balieverkoopBack.getTegenprijsPrijs()).isNull();

        prijs.tegenprijsBalieverkoops(new HashSet<>(Set.of(balieverkoopBack)));
        assertThat(prijs.getTegenprijsBalieverkoops()).containsOnly(balieverkoopBack);
        assertThat(balieverkoopBack.getTegenprijsPrijs()).isEqualTo(prijs);

        prijs.setTegenprijsBalieverkoops(new HashSet<>());
        assertThat(prijs.getTegenprijsBalieverkoops()).doesNotContain(balieverkoopBack);
        assertThat(balieverkoopBack.getTegenprijsPrijs()).isNull();
    }
}
