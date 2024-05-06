package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.DebiteurTestSamples.*;
import static nl.ritense.demo.domain.FactuurTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DebiteurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Debiteur.class);
        Debiteur debiteur1 = getDebiteurSample1();
        Debiteur debiteur2 = new Debiteur();
        assertThat(debiteur1).isNotEqualTo(debiteur2);

        debiteur2.setId(debiteur1.getId());
        assertThat(debiteur1).isEqualTo(debiteur2);

        debiteur2 = getDebiteurSample2();
        assertThat(debiteur1).isNotEqualTo(debiteur2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Debiteur debiteur = new Debiteur();
        assertThat(debiteur.hashCode()).isZero();

        Debiteur debiteur1 = getDebiteurSample1();
        debiteur.setId(debiteur1.getId());
        assertThat(debiteur).hasSameHashCodeAs(debiteur1);
    }

    @Test
    void heeftFactuurTest() throws Exception {
        Debiteur debiteur = getDebiteurRandomSampleGenerator();
        Factuur factuurBack = getFactuurRandomSampleGenerator();

        debiteur.addHeeftFactuur(factuurBack);
        assertThat(debiteur.getHeeftFactuurs()).containsOnly(factuurBack);
        assertThat(factuurBack.getHeeftDebiteur()).isEqualTo(debiteur);

        debiteur.removeHeeftFactuur(factuurBack);
        assertThat(debiteur.getHeeftFactuurs()).doesNotContain(factuurBack);
        assertThat(factuurBack.getHeeftDebiteur()).isNull();

        debiteur.heeftFactuurs(new HashSet<>(Set.of(factuurBack)));
        assertThat(debiteur.getHeeftFactuurs()).containsOnly(factuurBack);
        assertThat(factuurBack.getHeeftDebiteur()).isEqualTo(debiteur);

        debiteur.setHeeftFactuurs(new HashSet<>());
        assertThat(debiteur.getHeeftFactuurs()).doesNotContain(factuurBack);
        assertThat(factuurBack.getHeeftDebiteur()).isNull();
    }
}
