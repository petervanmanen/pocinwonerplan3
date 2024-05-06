package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BalieverkoopTestSamples.*;
import static nl.ritense.demo.domain.PrijsTestSamples.*;
import static nl.ritense.demo.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BalieverkoopTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Balieverkoop.class);
        Balieverkoop balieverkoop1 = getBalieverkoopSample1();
        Balieverkoop balieverkoop2 = new Balieverkoop();
        assertThat(balieverkoop1).isNotEqualTo(balieverkoop2);

        balieverkoop2.setId(balieverkoop1.getId());
        assertThat(balieverkoop1).isEqualTo(balieverkoop2);

        balieverkoop2 = getBalieverkoopSample2();
        assertThat(balieverkoop1).isNotEqualTo(balieverkoop2);
    }

    @Test
    void tegenprijsPrijsTest() throws Exception {
        Balieverkoop balieverkoop = getBalieverkoopRandomSampleGenerator();
        Prijs prijsBack = getPrijsRandomSampleGenerator();

        balieverkoop.setTegenprijsPrijs(prijsBack);
        assertThat(balieverkoop.getTegenprijsPrijs()).isEqualTo(prijsBack);

        balieverkoop.tegenprijsPrijs(null);
        assertThat(balieverkoop.getTegenprijsPrijs()).isNull();
    }

    @Test
    void betreftProductTest() throws Exception {
        Balieverkoop balieverkoop = getBalieverkoopRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        balieverkoop.setBetreftProduct(productBack);
        assertThat(balieverkoop.getBetreftProduct()).isEqualTo(productBack);

        balieverkoop.betreftProduct(null);
        assertThat(balieverkoop.getBetreftProduct()).isNull();
    }
}
