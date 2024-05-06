package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.FunctieTestSamples.*;
import static nl.ritense.demo.domain.OpdrachtnemerTestSamples.*;
import static nl.ritense.demo.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OpdrachtnemerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Opdrachtnemer.class);
        Opdrachtnemer opdrachtnemer1 = getOpdrachtnemerSample1();
        Opdrachtnemer opdrachtnemer2 = new Opdrachtnemer();
        assertThat(opdrachtnemer1).isNotEqualTo(opdrachtnemer2);

        opdrachtnemer2.setId(opdrachtnemer1.getId());
        assertThat(opdrachtnemer1).isEqualTo(opdrachtnemer2);

        opdrachtnemer2 = getOpdrachtnemerSample2();
        assertThat(opdrachtnemer1).isNotEqualTo(opdrachtnemer2);
    }

    @Test
    void isopdrachtnemerProductTest() throws Exception {
        Opdrachtnemer opdrachtnemer = getOpdrachtnemerRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        opdrachtnemer.addIsopdrachtnemerProduct(productBack);
        assertThat(opdrachtnemer.getIsopdrachtnemerProducts()).containsOnly(productBack);
        assertThat(productBack.getIsopdrachtnemerOpdrachtnemer()).isEqualTo(opdrachtnemer);

        opdrachtnemer.removeIsopdrachtnemerProduct(productBack);
        assertThat(opdrachtnemer.getIsopdrachtnemerProducts()).doesNotContain(productBack);
        assertThat(productBack.getIsopdrachtnemerOpdrachtnemer()).isNull();

        opdrachtnemer.isopdrachtnemerProducts(new HashSet<>(Set.of(productBack)));
        assertThat(opdrachtnemer.getIsopdrachtnemerProducts()).containsOnly(productBack);
        assertThat(productBack.getIsopdrachtnemerOpdrachtnemer()).isEqualTo(opdrachtnemer);

        opdrachtnemer.setIsopdrachtnemerProducts(new HashSet<>());
        assertThat(opdrachtnemer.getIsopdrachtnemerProducts()).doesNotContain(productBack);
        assertThat(productBack.getIsopdrachtnemerOpdrachtnemer()).isNull();
    }

    @Test
    void uitgevoerddoorFunctieTest() throws Exception {
        Opdrachtnemer opdrachtnemer = getOpdrachtnemerRandomSampleGenerator();
        Functie functieBack = getFunctieRandomSampleGenerator();

        opdrachtnemer.setUitgevoerddoorFunctie(functieBack);
        assertThat(opdrachtnemer.getUitgevoerddoorFunctie()).isEqualTo(functieBack);

        opdrachtnemer.uitgevoerddoorFunctie(null);
        assertThat(opdrachtnemer.getUitgevoerddoorFunctie()).isNull();
    }
}
