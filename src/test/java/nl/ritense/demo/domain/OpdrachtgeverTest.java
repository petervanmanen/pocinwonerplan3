package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.FunctieTestSamples.*;
import static nl.ritense.demo.domain.OpdrachtgeverTestSamples.*;
import static nl.ritense.demo.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OpdrachtgeverTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Opdrachtgever.class);
        Opdrachtgever opdrachtgever1 = getOpdrachtgeverSample1();
        Opdrachtgever opdrachtgever2 = new Opdrachtgever();
        assertThat(opdrachtgever1).isNotEqualTo(opdrachtgever2);

        opdrachtgever2.setId(opdrachtgever1.getId());
        assertThat(opdrachtgever1).isEqualTo(opdrachtgever2);

        opdrachtgever2 = getOpdrachtgeverSample2();
        assertThat(opdrachtgever1).isNotEqualTo(opdrachtgever2);
    }

    @Test
    void isopdrachtgeverProductTest() throws Exception {
        Opdrachtgever opdrachtgever = getOpdrachtgeverRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        opdrachtgever.addIsopdrachtgeverProduct(productBack);
        assertThat(opdrachtgever.getIsopdrachtgeverProducts()).containsOnly(productBack);
        assertThat(productBack.getIsopdrachtgeverOpdrachtgever()).isEqualTo(opdrachtgever);

        opdrachtgever.removeIsopdrachtgeverProduct(productBack);
        assertThat(opdrachtgever.getIsopdrachtgeverProducts()).doesNotContain(productBack);
        assertThat(productBack.getIsopdrachtgeverOpdrachtgever()).isNull();

        opdrachtgever.isopdrachtgeverProducts(new HashSet<>(Set.of(productBack)));
        assertThat(opdrachtgever.getIsopdrachtgeverProducts()).containsOnly(productBack);
        assertThat(productBack.getIsopdrachtgeverOpdrachtgever()).isEqualTo(opdrachtgever);

        opdrachtgever.setIsopdrachtgeverProducts(new HashSet<>());
        assertThat(opdrachtgever.getIsopdrachtgeverProducts()).doesNotContain(productBack);
        assertThat(productBack.getIsopdrachtgeverOpdrachtgever()).isNull();
    }

    @Test
    void uitgevoerddoorFunctieTest() throws Exception {
        Opdrachtgever opdrachtgever = getOpdrachtgeverRandomSampleGenerator();
        Functie functieBack = getFunctieRandomSampleGenerator();

        opdrachtgever.setUitgevoerddoorFunctie(functieBack);
        assertThat(opdrachtgever.getUitgevoerddoorFunctie()).isEqualTo(functieBack);

        opdrachtgever.uitgevoerddoorFunctie(null);
        assertThat(opdrachtgever.getUitgevoerddoorFunctie()).isNull();
    }
}
