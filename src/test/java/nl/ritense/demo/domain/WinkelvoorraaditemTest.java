package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ProductTestSamples.*;
import static nl.ritense.demo.domain.WinkelvoorraaditemTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WinkelvoorraaditemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Winkelvoorraaditem.class);
        Winkelvoorraaditem winkelvoorraaditem1 = getWinkelvoorraaditemSample1();
        Winkelvoorraaditem winkelvoorraaditem2 = new Winkelvoorraaditem();
        assertThat(winkelvoorraaditem1).isNotEqualTo(winkelvoorraaditem2);

        winkelvoorraaditem2.setId(winkelvoorraaditem1.getId());
        assertThat(winkelvoorraaditem1).isEqualTo(winkelvoorraaditem2);

        winkelvoorraaditem2 = getWinkelvoorraaditemSample2();
        assertThat(winkelvoorraaditem1).isNotEqualTo(winkelvoorraaditem2);
    }

    @Test
    void betreftProductTest() throws Exception {
        Winkelvoorraaditem winkelvoorraaditem = getWinkelvoorraaditemRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        winkelvoorraaditem.setBetreftProduct(productBack);
        assertThat(winkelvoorraaditem.getBetreftProduct()).isEqualTo(productBack);

        winkelvoorraaditem.betreftProduct(null);
        assertThat(winkelvoorraaditem.getBetreftProduct()).isNull();
    }
}
