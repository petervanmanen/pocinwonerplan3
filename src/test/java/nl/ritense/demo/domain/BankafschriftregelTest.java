package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BankafschriftTestSamples.*;
import static nl.ritense.demo.domain.BankafschriftregelTestSamples.*;
import static nl.ritense.demo.domain.BetalingTestSamples.*;
import static nl.ritense.demo.domain.MutatieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankafschriftregelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bankafschriftregel.class);
        Bankafschriftregel bankafschriftregel1 = getBankafschriftregelSample1();
        Bankafschriftregel bankafschriftregel2 = new Bankafschriftregel();
        assertThat(bankafschriftregel1).isNotEqualTo(bankafschriftregel2);

        bankafschriftregel2.setId(bankafschriftregel1.getId());
        assertThat(bankafschriftregel1).isEqualTo(bankafschriftregel2);

        bankafschriftregel2 = getBankafschriftregelSample2();
        assertThat(bankafschriftregel1).isNotEqualTo(bankafschriftregel2);
    }

    @Test
    void leidttotMutatieTest() throws Exception {
        Bankafschriftregel bankafschriftregel = getBankafschriftregelRandomSampleGenerator();
        Mutatie mutatieBack = getMutatieRandomSampleGenerator();

        bankafschriftregel.setLeidttotMutatie(mutatieBack);
        assertThat(bankafschriftregel.getLeidttotMutatie()).isEqualTo(mutatieBack);

        bankafschriftregel.leidttotMutatie(null);
        assertThat(bankafschriftregel.getLeidttotMutatie()).isNull();
    }

    @Test
    void heeftBankafschriftTest() throws Exception {
        Bankafschriftregel bankafschriftregel = getBankafschriftregelRandomSampleGenerator();
        Bankafschrift bankafschriftBack = getBankafschriftRandomSampleGenerator();

        bankafschriftregel.setHeeftBankafschrift(bankafschriftBack);
        assertThat(bankafschriftregel.getHeeftBankafschrift()).isEqualTo(bankafschriftBack);

        bankafschriftregel.heeftBankafschrift(null);
        assertThat(bankafschriftregel.getHeeftBankafschrift()).isNull();
    }

    @Test
    void komtvooropBetalingTest() throws Exception {
        Bankafschriftregel bankafschriftregel = getBankafschriftregelRandomSampleGenerator();
        Betaling betalingBack = getBetalingRandomSampleGenerator();

        bankafschriftregel.addKomtvooropBetaling(betalingBack);
        assertThat(bankafschriftregel.getKomtvooropBetalings()).containsOnly(betalingBack);
        assertThat(betalingBack.getKomtvooropBankafschriftregel()).isEqualTo(bankafschriftregel);

        bankafschriftregel.removeKomtvooropBetaling(betalingBack);
        assertThat(bankafschriftregel.getKomtvooropBetalings()).doesNotContain(betalingBack);
        assertThat(betalingBack.getKomtvooropBankafschriftregel()).isNull();

        bankafschriftregel.komtvooropBetalings(new HashSet<>(Set.of(betalingBack)));
        assertThat(bankafschriftregel.getKomtvooropBetalings()).containsOnly(betalingBack);
        assertThat(betalingBack.getKomtvooropBankafschriftregel()).isEqualTo(bankafschriftregel);

        bankafschriftregel.setKomtvooropBetalings(new HashSet<>());
        assertThat(bankafschriftregel.getKomtvooropBetalings()).doesNotContain(betalingBack);
        assertThat(betalingBack.getKomtvooropBankafschriftregel()).isNull();
    }
}
