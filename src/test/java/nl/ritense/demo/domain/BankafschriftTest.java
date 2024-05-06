package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BankafschriftTestSamples.*;
import static nl.ritense.demo.domain.BankafschriftregelTestSamples.*;
import static nl.ritense.demo.domain.BankrekeningTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankafschriftTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bankafschrift.class);
        Bankafschrift bankafschrift1 = getBankafschriftSample1();
        Bankafschrift bankafschrift2 = new Bankafschrift();
        assertThat(bankafschrift1).isNotEqualTo(bankafschrift2);

        bankafschrift2.setId(bankafschrift1.getId());
        assertThat(bankafschrift1).isEqualTo(bankafschrift2);

        bankafschrift2 = getBankafschriftSample2();
        assertThat(bankafschrift1).isNotEqualTo(bankafschrift2);
    }

    @Test
    void heeftBankafschriftregelTest() throws Exception {
        Bankafschrift bankafschrift = getBankafschriftRandomSampleGenerator();
        Bankafschriftregel bankafschriftregelBack = getBankafschriftregelRandomSampleGenerator();

        bankafschrift.addHeeftBankafschriftregel(bankafschriftregelBack);
        assertThat(bankafschrift.getHeeftBankafschriftregels()).containsOnly(bankafschriftregelBack);
        assertThat(bankafschriftregelBack.getHeeftBankafschrift()).isEqualTo(bankafschrift);

        bankafschrift.removeHeeftBankafschriftregel(bankafschriftregelBack);
        assertThat(bankafschrift.getHeeftBankafschriftregels()).doesNotContain(bankafschriftregelBack);
        assertThat(bankafschriftregelBack.getHeeftBankafschrift()).isNull();

        bankafschrift.heeftBankafschriftregels(new HashSet<>(Set.of(bankafschriftregelBack)));
        assertThat(bankafschrift.getHeeftBankafschriftregels()).containsOnly(bankafschriftregelBack);
        assertThat(bankafschriftregelBack.getHeeftBankafschrift()).isEqualTo(bankafschrift);

        bankafschrift.setHeeftBankafschriftregels(new HashSet<>());
        assertThat(bankafschrift.getHeeftBankafschriftregels()).doesNotContain(bankafschriftregelBack);
        assertThat(bankafschriftregelBack.getHeeftBankafschrift()).isNull();
    }

    @Test
    void heeftBankrekeningTest() throws Exception {
        Bankafschrift bankafschrift = getBankafschriftRandomSampleGenerator();
        Bankrekening bankrekeningBack = getBankrekeningRandomSampleGenerator();

        bankafschrift.setHeeftBankrekening(bankrekeningBack);
        assertThat(bankafschrift.getHeeftBankrekening()).isEqualTo(bankrekeningBack);

        bankafschrift.heeftBankrekening(null);
        assertThat(bankafschrift.getHeeftBankrekening()).isNull();
    }
}
