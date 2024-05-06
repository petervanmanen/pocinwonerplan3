package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.DebiteurTestSamples.*;
import static nl.ritense.demo.domain.FactuurTestSamples.*;
import static nl.ritense.demo.domain.FactuurregelTestSamples.*;
import static nl.ritense.demo.domain.InkooporderTestSamples.*;
import static nl.ritense.demo.domain.KostenplaatsTestSamples.*;
import static nl.ritense.demo.domain.LeverancierTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FactuurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Factuur.class);
        Factuur factuur1 = getFactuurSample1();
        Factuur factuur2 = new Factuur();
        assertThat(factuur1).isNotEqualTo(factuur2);

        factuur2.setId(factuur1.getId());
        assertThat(factuur1).isEqualTo(factuur2);

        factuur2 = getFactuurSample2();
        assertThat(factuur1).isNotEqualTo(factuur2);
    }

    @Test
    void heeftFactuurregelTest() throws Exception {
        Factuur factuur = getFactuurRandomSampleGenerator();
        Factuurregel factuurregelBack = getFactuurregelRandomSampleGenerator();

        factuur.addHeeftFactuurregel(factuurregelBack);
        assertThat(factuur.getHeeftFactuurregels()).containsOnly(factuurregelBack);
        assertThat(factuurregelBack.getHeeftFactuur()).isEqualTo(factuur);

        factuur.removeHeeftFactuurregel(factuurregelBack);
        assertThat(factuur.getHeeftFactuurregels()).doesNotContain(factuurregelBack);
        assertThat(factuurregelBack.getHeeftFactuur()).isNull();

        factuur.heeftFactuurregels(new HashSet<>(Set.of(factuurregelBack)));
        assertThat(factuur.getHeeftFactuurregels()).containsOnly(factuurregelBack);
        assertThat(factuurregelBack.getHeeftFactuur()).isEqualTo(factuur);

        factuur.setHeeftFactuurregels(new HashSet<>());
        assertThat(factuur.getHeeftFactuurregels()).doesNotContain(factuurregelBack);
        assertThat(factuurregelBack.getHeeftFactuur()).isNull();
    }

    @Test
    void schrijftopKostenplaatsTest() throws Exception {
        Factuur factuur = getFactuurRandomSampleGenerator();
        Kostenplaats kostenplaatsBack = getKostenplaatsRandomSampleGenerator();

        factuur.setSchrijftopKostenplaats(kostenplaatsBack);
        assertThat(factuur.getSchrijftopKostenplaats()).isEqualTo(kostenplaatsBack);

        factuur.schrijftopKostenplaats(null);
        assertThat(factuur.getSchrijftopKostenplaats()).isNull();
    }

    @Test
    void gedektviaInkooporderTest() throws Exception {
        Factuur factuur = getFactuurRandomSampleGenerator();
        Inkooporder inkooporderBack = getInkooporderRandomSampleGenerator();

        factuur.setGedektviaInkooporder(inkooporderBack);
        assertThat(factuur.getGedektviaInkooporder()).isEqualTo(inkooporderBack);

        factuur.gedektviaInkooporder(null);
        assertThat(factuur.getGedektviaInkooporder()).isNull();
    }

    @Test
    void crediteurLeverancierTest() throws Exception {
        Factuur factuur = getFactuurRandomSampleGenerator();
        Leverancier leverancierBack = getLeverancierRandomSampleGenerator();

        factuur.setCrediteurLeverancier(leverancierBack);
        assertThat(factuur.getCrediteurLeverancier()).isEqualTo(leverancierBack);

        factuur.crediteurLeverancier(null);
        assertThat(factuur.getCrediteurLeverancier()).isNull();
    }

    @Test
    void heeftDebiteurTest() throws Exception {
        Factuur factuur = getFactuurRandomSampleGenerator();
        Debiteur debiteurBack = getDebiteurRandomSampleGenerator();

        factuur.setHeeftDebiteur(debiteurBack);
        assertThat(factuur.getHeeftDebiteur()).isEqualTo(debiteurBack);

        factuur.heeftDebiteur(null);
        assertThat(factuur.getHeeftDebiteur()).isNull();
    }
}
