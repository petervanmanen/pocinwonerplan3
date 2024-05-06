package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BankafschriftregelTestSamples.*;
import static nl.ritense.demo.domain.BankrekeningTestSamples.*;
import static nl.ritense.demo.domain.BetalingTestSamples.*;
import static nl.ritense.demo.domain.ZaakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BetalingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Betaling.class);
        Betaling betaling1 = getBetalingSample1();
        Betaling betaling2 = new Betaling();
        assertThat(betaling1).isNotEqualTo(betaling2);

        betaling2.setId(betaling1.getId());
        assertThat(betaling1).isEqualTo(betaling2);

        betaling2 = getBetalingSample2();
        assertThat(betaling1).isNotEqualTo(betaling2);
    }

    @Test
    void komtvooropBankafschriftregelTest() throws Exception {
        Betaling betaling = getBetalingRandomSampleGenerator();
        Bankafschriftregel bankafschriftregelBack = getBankafschriftregelRandomSampleGenerator();

        betaling.setKomtvooropBankafschriftregel(bankafschriftregelBack);
        assertThat(betaling.getKomtvooropBankafschriftregel()).isEqualTo(bankafschriftregelBack);

        betaling.komtvooropBankafschriftregel(null);
        assertThat(betaling.getKomtvooropBankafschriftregel()).isNull();
    }

    @Test
    void vanBankrekeningTest() throws Exception {
        Betaling betaling = getBetalingRandomSampleGenerator();
        Bankrekening bankrekeningBack = getBankrekeningRandomSampleGenerator();

        betaling.setVanBankrekening(bankrekeningBack);
        assertThat(betaling.getVanBankrekening()).isEqualTo(bankrekeningBack);

        betaling.vanBankrekening(null);
        assertThat(betaling.getVanBankrekening()).isNull();
    }

    @Test
    void naarBankrekeningTest() throws Exception {
        Betaling betaling = getBetalingRandomSampleGenerator();
        Bankrekening bankrekeningBack = getBankrekeningRandomSampleGenerator();

        betaling.setNaarBankrekening(bankrekeningBack);
        assertThat(betaling.getNaarBankrekening()).isEqualTo(bankrekeningBack);

        betaling.naarBankrekening(null);
        assertThat(betaling.getNaarBankrekening()).isNull();
    }

    @Test
    void heeftbetalingZaakTest() throws Exception {
        Betaling betaling = getBetalingRandomSampleGenerator();
        Zaak zaakBack = getZaakRandomSampleGenerator();

        betaling.setHeeftbetalingZaak(zaakBack);
        assertThat(betaling.getHeeftbetalingZaak()).isEqualTo(zaakBack);

        betaling.heeftbetalingZaak(null);
        assertThat(betaling.getHeeftbetalingZaak()).isNull();
    }
}
