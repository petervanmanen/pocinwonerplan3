package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BalieverkoopTestSamples.*;
import static nl.ritense.demo.domain.BegrotingregelTestSamples.*;
import static nl.ritense.demo.domain.DienstTestSamples.*;
import static nl.ritense.demo.domain.DoelstellingTestSamples.*;
import static nl.ritense.demo.domain.KostenplaatsTestSamples.*;
import static nl.ritense.demo.domain.LeverancierTestSamples.*;
import static nl.ritense.demo.domain.OmzetgroepTestSamples.*;
import static nl.ritense.demo.domain.OpdrachtgeverTestSamples.*;
import static nl.ritense.demo.domain.OpdrachtnemerTestSamples.*;
import static nl.ritense.demo.domain.PrijsTestSamples.*;
import static nl.ritense.demo.domain.ProductTestSamples.*;
import static nl.ritense.demo.domain.ProductgroepTestSamples.*;
import static nl.ritense.demo.domain.WinkelvoorraaditemTestSamples.*;
import static nl.ritense.demo.domain.ZaaktypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product.class);
        Product product1 = getProductSample1();
        Product product2 = new Product();
        assertThat(product1).isNotEqualTo(product2);

        product2.setId(product1.getId());
        assertThat(product1).isEqualTo(product2);

        product2 = getProductSample2();
        assertThat(product1).isNotEqualTo(product2);
    }

    @Test
    void heeftprijsPrijsTest() throws Exception {
        Product product = getProductRandomSampleGenerator();
        Prijs prijsBack = getPrijsRandomSampleGenerator();

        product.addHeeftprijsPrijs(prijsBack);
        assertThat(product.getHeeftprijsPrijs()).containsOnly(prijsBack);
        assertThat(prijsBack.getHeeftprijsProduct()).isEqualTo(product);

        product.removeHeeftprijsPrijs(prijsBack);
        assertThat(product.getHeeftprijsPrijs()).doesNotContain(prijsBack);
        assertThat(prijsBack.getHeeftprijsProduct()).isNull();

        product.heeftprijsPrijs(new HashSet<>(Set.of(prijsBack)));
        assertThat(product.getHeeftprijsPrijs()).containsOnly(prijsBack);
        assertThat(prijsBack.getHeeftprijsProduct()).isEqualTo(product);

        product.setHeeftprijsPrijs(new HashSet<>());
        assertThat(product.getHeeftprijsPrijs()).doesNotContain(prijsBack);
        assertThat(prijsBack.getHeeftprijsProduct()).isNull();
    }

    @Test
    void betreftZaaktypeTest() throws Exception {
        Product product = getProductRandomSampleGenerator();
        Zaaktype zaaktypeBack = getZaaktypeRandomSampleGenerator();

        product.addBetreftZaaktype(zaaktypeBack);
        assertThat(product.getBetreftZaaktypes()).containsOnly(zaaktypeBack);
        assertThat(zaaktypeBack.getBetreftProduct()).isEqualTo(product);

        product.removeBetreftZaaktype(zaaktypeBack);
        assertThat(product.getBetreftZaaktypes()).doesNotContain(zaaktypeBack);
        assertThat(zaaktypeBack.getBetreftProduct()).isNull();

        product.betreftZaaktypes(new HashSet<>(Set.of(zaaktypeBack)));
        assertThat(product.getBetreftZaaktypes()).containsOnly(zaaktypeBack);
        assertThat(zaaktypeBack.getBetreftProduct()).isEqualTo(product);

        product.setBetreftZaaktypes(new HashSet<>());
        assertThat(product.getBetreftZaaktypes()).doesNotContain(zaaktypeBack);
        assertThat(zaaktypeBack.getBetreftProduct()).isNull();
    }

    @Test
    void leverancierLeverancierTest() throws Exception {
        Product product = getProductRandomSampleGenerator();
        Leverancier leverancierBack = getLeverancierRandomSampleGenerator();

        product.setLeverancierLeverancier(leverancierBack);
        assertThat(product.getLeverancierLeverancier()).isEqualTo(leverancierBack);

        product.leverancierLeverancier(null);
        assertThat(product.getLeverancierLeverancier()).isNull();
    }

    @Test
    void heeftKostenplaatsTest() throws Exception {
        Product product = getProductRandomSampleGenerator();
        Kostenplaats kostenplaatsBack = getKostenplaatsRandomSampleGenerator();

        product.setHeeftKostenplaats(kostenplaatsBack);
        assertThat(product.getHeeftKostenplaats()).isEqualTo(kostenplaatsBack);

        product.heeftKostenplaats(null);
        assertThat(product.getHeeftKostenplaats()).isNull();
    }

    @Test
    void valtbinnenOmzetgroepTest() throws Exception {
        Product product = getProductRandomSampleGenerator();
        Omzetgroep omzetgroepBack = getOmzetgroepRandomSampleGenerator();

        product.addValtbinnenOmzetgroep(omzetgroepBack);
        assertThat(product.getValtbinnenOmzetgroeps()).containsOnly(omzetgroepBack);

        product.removeValtbinnenOmzetgroep(omzetgroepBack);
        assertThat(product.getValtbinnenOmzetgroeps()).doesNotContain(omzetgroepBack);

        product.valtbinnenOmzetgroeps(new HashSet<>(Set.of(omzetgroepBack)));
        assertThat(product.getValtbinnenOmzetgroeps()).containsOnly(omzetgroepBack);

        product.setValtbinnenOmzetgroeps(new HashSet<>());
        assertThat(product.getValtbinnenOmzetgroeps()).doesNotContain(omzetgroepBack);
    }

    @Test
    void valtbinnenProductgroepTest() throws Exception {
        Product product = getProductRandomSampleGenerator();
        Productgroep productgroepBack = getProductgroepRandomSampleGenerator();

        product.addValtbinnenProductgroep(productgroepBack);
        assertThat(product.getValtbinnenProductgroeps()).containsOnly(productgroepBack);

        product.removeValtbinnenProductgroep(productgroepBack);
        assertThat(product.getValtbinnenProductgroeps()).doesNotContain(productgroepBack);

        product.valtbinnenProductgroeps(new HashSet<>(Set.of(productgroepBack)));
        assertThat(product.getValtbinnenProductgroeps()).containsOnly(productgroepBack);

        product.setValtbinnenProductgroeps(new HashSet<>());
        assertThat(product.getValtbinnenProductgroeps()).doesNotContain(productgroepBack);
    }

    @Test
    void betreftWinkelvoorraaditemTest() throws Exception {
        Product product = getProductRandomSampleGenerator();
        Winkelvoorraaditem winkelvoorraaditemBack = getWinkelvoorraaditemRandomSampleGenerator();

        product.setBetreftWinkelvoorraaditem(winkelvoorraaditemBack);
        assertThat(product.getBetreftWinkelvoorraaditem()).isEqualTo(winkelvoorraaditemBack);
        assertThat(winkelvoorraaditemBack.getBetreftProduct()).isEqualTo(product);

        product.betreftWinkelvoorraaditem(null);
        assertThat(product.getBetreftWinkelvoorraaditem()).isNull();
        assertThat(winkelvoorraaditemBack.getBetreftProduct()).isNull();
    }

    @Test
    void heeftDoelstellingTest() throws Exception {
        Product product = getProductRandomSampleGenerator();
        Doelstelling doelstellingBack = getDoelstellingRandomSampleGenerator();

        product.setHeeftDoelstelling(doelstellingBack);
        assertThat(product.getHeeftDoelstelling()).isEqualTo(doelstellingBack);

        product.heeftDoelstelling(null);
        assertThat(product.getHeeftDoelstelling()).isNull();
    }

    @Test
    void isopdrachtgeverOpdrachtgeverTest() throws Exception {
        Product product = getProductRandomSampleGenerator();
        Opdrachtgever opdrachtgeverBack = getOpdrachtgeverRandomSampleGenerator();

        product.setIsopdrachtgeverOpdrachtgever(opdrachtgeverBack);
        assertThat(product.getIsopdrachtgeverOpdrachtgever()).isEqualTo(opdrachtgeverBack);

        product.isopdrachtgeverOpdrachtgever(null);
        assertThat(product.getIsopdrachtgeverOpdrachtgever()).isNull();
    }

    @Test
    void isopdrachtnemerOpdrachtnemerTest() throws Exception {
        Product product = getProductRandomSampleGenerator();
        Opdrachtnemer opdrachtnemerBack = getOpdrachtnemerRandomSampleGenerator();

        product.setIsopdrachtnemerOpdrachtnemer(opdrachtnemerBack);
        assertThat(product.getIsopdrachtnemerOpdrachtnemer()).isEqualTo(opdrachtnemerBack);

        product.isopdrachtnemerOpdrachtnemer(null);
        assertThat(product.getIsopdrachtnemerOpdrachtnemer()).isNull();
    }

    @Test
    void betreftBalieverkoopTest() throws Exception {
        Product product = getProductRandomSampleGenerator();
        Balieverkoop balieverkoopBack = getBalieverkoopRandomSampleGenerator();

        product.addBetreftBalieverkoop(balieverkoopBack);
        assertThat(product.getBetreftBalieverkoops()).containsOnly(balieverkoopBack);
        assertThat(balieverkoopBack.getBetreftProduct()).isEqualTo(product);

        product.removeBetreftBalieverkoop(balieverkoopBack);
        assertThat(product.getBetreftBalieverkoops()).doesNotContain(balieverkoopBack);
        assertThat(balieverkoopBack.getBetreftProduct()).isNull();

        product.betreftBalieverkoops(new HashSet<>(Set.of(balieverkoopBack)));
        assertThat(product.getBetreftBalieverkoops()).containsOnly(balieverkoopBack);
        assertThat(balieverkoopBack.getBetreftProduct()).isEqualTo(product);

        product.setBetreftBalieverkoops(new HashSet<>());
        assertThat(product.getBetreftBalieverkoops()).doesNotContain(balieverkoopBack);
        assertThat(balieverkoopBack.getBetreftProduct()).isNull();
    }

    @Test
    void betreftDienstTest() throws Exception {
        Product product = getProductRandomSampleGenerator();
        Dienst dienstBack = getDienstRandomSampleGenerator();

        product.addBetreftDienst(dienstBack);
        assertThat(product.getBetreftDiensts()).containsOnly(dienstBack);
        assertThat(dienstBack.getBetreftProduct()).isEqualTo(product);

        product.removeBetreftDienst(dienstBack);
        assertThat(product.getBetreftDiensts()).doesNotContain(dienstBack);
        assertThat(dienstBack.getBetreftProduct()).isNull();

        product.betreftDiensts(new HashSet<>(Set.of(dienstBack)));
        assertThat(product.getBetreftDiensts()).containsOnly(dienstBack);
        assertThat(dienstBack.getBetreftProduct()).isEqualTo(product);

        product.setBetreftDiensts(new HashSet<>());
        assertThat(product.getBetreftDiensts()).doesNotContain(dienstBack);
        assertThat(dienstBack.getBetreftProduct()).isNull();
    }

    @Test
    void betreftBegrotingregelTest() throws Exception {
        Product product = getProductRandomSampleGenerator();
        Begrotingregel begrotingregelBack = getBegrotingregelRandomSampleGenerator();

        product.addBetreftBegrotingregel(begrotingregelBack);
        assertThat(product.getBetreftBegrotingregels()).containsOnly(begrotingregelBack);
        assertThat(begrotingregelBack.getBetreftProduct()).isEqualTo(product);

        product.removeBetreftBegrotingregel(begrotingregelBack);
        assertThat(product.getBetreftBegrotingregels()).doesNotContain(begrotingregelBack);
        assertThat(begrotingregelBack.getBetreftProduct()).isNull();

        product.betreftBegrotingregels(new HashSet<>(Set.of(begrotingregelBack)));
        assertThat(product.getBetreftBegrotingregels()).containsOnly(begrotingregelBack);
        assertThat(begrotingregelBack.getBetreftProduct()).isEqualTo(product);

        product.setBetreftBegrotingregels(new HashSet<>());
        assertThat(product.getBetreftBegrotingregels()).doesNotContain(begrotingregelBack);
        assertThat(begrotingregelBack.getBetreftProduct()).isNull();
    }
}
