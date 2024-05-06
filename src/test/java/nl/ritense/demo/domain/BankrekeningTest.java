package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BankafschriftTestSamples.*;
import static nl.ritense.demo.domain.BankrekeningTestSamples.*;
import static nl.ritense.demo.domain.BetalingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankrekeningTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bankrekening.class);
        Bankrekening bankrekening1 = getBankrekeningSample1();
        Bankrekening bankrekening2 = new Bankrekening();
        assertThat(bankrekening1).isNotEqualTo(bankrekening2);

        bankrekening2.setId(bankrekening1.getId());
        assertThat(bankrekening1).isEqualTo(bankrekening2);

        bankrekening2 = getBankrekeningSample2();
        assertThat(bankrekening1).isNotEqualTo(bankrekening2);
    }

    @Test
    void heeftBankafschriftTest() throws Exception {
        Bankrekening bankrekening = getBankrekeningRandomSampleGenerator();
        Bankafschrift bankafschriftBack = getBankafschriftRandomSampleGenerator();

        bankrekening.addHeeftBankafschrift(bankafschriftBack);
        assertThat(bankrekening.getHeeftBankafschrifts()).containsOnly(bankafschriftBack);
        assertThat(bankafschriftBack.getHeeftBankrekening()).isEqualTo(bankrekening);

        bankrekening.removeHeeftBankafschrift(bankafschriftBack);
        assertThat(bankrekening.getHeeftBankafschrifts()).doesNotContain(bankafschriftBack);
        assertThat(bankafschriftBack.getHeeftBankrekening()).isNull();

        bankrekening.heeftBankafschrifts(new HashSet<>(Set.of(bankafschriftBack)));
        assertThat(bankrekening.getHeeftBankafschrifts()).containsOnly(bankafschriftBack);
        assertThat(bankafschriftBack.getHeeftBankrekening()).isEqualTo(bankrekening);

        bankrekening.setHeeftBankafschrifts(new HashSet<>());
        assertThat(bankrekening.getHeeftBankafschrifts()).doesNotContain(bankafschriftBack);
        assertThat(bankafschriftBack.getHeeftBankrekening()).isNull();
    }

    @Test
    void vanBetalingTest() throws Exception {
        Bankrekening bankrekening = getBankrekeningRandomSampleGenerator();
        Betaling betalingBack = getBetalingRandomSampleGenerator();

        bankrekening.addVanBetaling(betalingBack);
        assertThat(bankrekening.getVanBetalings()).containsOnly(betalingBack);
        assertThat(betalingBack.getVanBankrekening()).isEqualTo(bankrekening);

        bankrekening.removeVanBetaling(betalingBack);
        assertThat(bankrekening.getVanBetalings()).doesNotContain(betalingBack);
        assertThat(betalingBack.getVanBankrekening()).isNull();

        bankrekening.vanBetalings(new HashSet<>(Set.of(betalingBack)));
        assertThat(bankrekening.getVanBetalings()).containsOnly(betalingBack);
        assertThat(betalingBack.getVanBankrekening()).isEqualTo(bankrekening);

        bankrekening.setVanBetalings(new HashSet<>());
        assertThat(bankrekening.getVanBetalings()).doesNotContain(betalingBack);
        assertThat(betalingBack.getVanBankrekening()).isNull();
    }

    @Test
    void naarBetalingTest() throws Exception {
        Bankrekening bankrekening = getBankrekeningRandomSampleGenerator();
        Betaling betalingBack = getBetalingRandomSampleGenerator();

        bankrekening.addNaarBetaling(betalingBack);
        assertThat(bankrekening.getNaarBetalings()).containsOnly(betalingBack);
        assertThat(betalingBack.getNaarBankrekening()).isEqualTo(bankrekening);

        bankrekening.removeNaarBetaling(betalingBack);
        assertThat(bankrekening.getNaarBetalings()).doesNotContain(betalingBack);
        assertThat(betalingBack.getNaarBankrekening()).isNull();

        bankrekening.naarBetalings(new HashSet<>(Set.of(betalingBack)));
        assertThat(bankrekening.getNaarBetalings()).containsOnly(betalingBack);
        assertThat(betalingBack.getNaarBankrekening()).isEqualTo(bankrekening);

        bankrekening.setNaarBetalings(new HashSet<>());
        assertThat(bankrekening.getNaarBetalings()).doesNotContain(betalingBack);
        assertThat(betalingBack.getNaarBankrekening()).isNull();
    }
}
