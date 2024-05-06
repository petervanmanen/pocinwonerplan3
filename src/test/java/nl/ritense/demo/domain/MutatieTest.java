package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BankafschriftregelTestSamples.*;
import static nl.ritense.demo.domain.BatchregelTestSamples.*;
import static nl.ritense.demo.domain.FactuurregelTestSamples.*;
import static nl.ritense.demo.domain.HoofdrekeningTestSamples.*;
import static nl.ritense.demo.domain.KostenplaatsTestSamples.*;
import static nl.ritense.demo.domain.MutatieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MutatieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mutatie.class);
        Mutatie mutatie1 = getMutatieSample1();
        Mutatie mutatie2 = new Mutatie();
        assertThat(mutatie1).isNotEqualTo(mutatie2);

        mutatie2.setId(mutatie1.getId());
        assertThat(mutatie1).isEqualTo(mutatie2);

        mutatie2 = getMutatieSample2();
        assertThat(mutatie1).isNotEqualTo(mutatie2);
    }

    @Test
    void vanHoofdrekeningTest() throws Exception {
        Mutatie mutatie = getMutatieRandomSampleGenerator();
        Hoofdrekening hoofdrekeningBack = getHoofdrekeningRandomSampleGenerator();

        mutatie.setVanHoofdrekening(hoofdrekeningBack);
        assertThat(mutatie.getVanHoofdrekening()).isEqualTo(hoofdrekeningBack);

        mutatie.vanHoofdrekening(null);
        assertThat(mutatie.getVanHoofdrekening()).isNull();
    }

    @Test
    void naarHoofdrekeningTest() throws Exception {
        Mutatie mutatie = getMutatieRandomSampleGenerator();
        Hoofdrekening hoofdrekeningBack = getHoofdrekeningRandomSampleGenerator();

        mutatie.setNaarHoofdrekening(hoofdrekeningBack);
        assertThat(mutatie.getNaarHoofdrekening()).isEqualTo(hoofdrekeningBack);

        mutatie.naarHoofdrekening(null);
        assertThat(mutatie.getNaarHoofdrekening()).isNull();
    }

    @Test
    void heeftbetrekkingopKostenplaatsTest() throws Exception {
        Mutatie mutatie = getMutatieRandomSampleGenerator();
        Kostenplaats kostenplaatsBack = getKostenplaatsRandomSampleGenerator();

        mutatie.setHeeftbetrekkingopKostenplaats(kostenplaatsBack);
        assertThat(mutatie.getHeeftbetrekkingopKostenplaats()).isEqualTo(kostenplaatsBack);

        mutatie.heeftbetrekkingopKostenplaats(null);
        assertThat(mutatie.getHeeftbetrekkingopKostenplaats()).isNull();
    }

    @Test
    void leidttotBankafschriftregelTest() throws Exception {
        Mutatie mutatie = getMutatieRandomSampleGenerator();
        Bankafschriftregel bankafschriftregelBack = getBankafschriftregelRandomSampleGenerator();

        mutatie.setLeidttotBankafschriftregel(bankafschriftregelBack);
        assertThat(mutatie.getLeidttotBankafschriftregel()).isEqualTo(bankafschriftregelBack);
        assertThat(bankafschriftregelBack.getLeidttotMutatie()).isEqualTo(mutatie);

        mutatie.leidttotBankafschriftregel(null);
        assertThat(mutatie.getLeidttotBankafschriftregel()).isNull();
        assertThat(bankafschriftregelBack.getLeidttotMutatie()).isNull();
    }

    @Test
    void leidttotBatchregelTest() throws Exception {
        Mutatie mutatie = getMutatieRandomSampleGenerator();
        Batchregel batchregelBack = getBatchregelRandomSampleGenerator();

        mutatie.setLeidttotBatchregel(batchregelBack);
        assertThat(mutatie.getLeidttotBatchregel()).isEqualTo(batchregelBack);
        assertThat(batchregelBack.getLeidttotMutatie()).isEqualTo(mutatie);

        mutatie.leidttotBatchregel(null);
        assertThat(mutatie.getLeidttotBatchregel()).isNull();
        assertThat(batchregelBack.getLeidttotMutatie()).isNull();
    }

    @Test
    void leidttotFactuurregelTest() throws Exception {
        Mutatie mutatie = getMutatieRandomSampleGenerator();
        Factuurregel factuurregelBack = getFactuurregelRandomSampleGenerator();

        mutatie.setLeidttotFactuurregel(factuurregelBack);
        assertThat(mutatie.getLeidttotFactuurregel()).isEqualTo(factuurregelBack);
        assertThat(factuurregelBack.getLeidttotMutatie()).isEqualTo(mutatie);

        mutatie.leidttotFactuurregel(null);
        assertThat(mutatie.getLeidttotFactuurregel()).isNull();
        assertThat(factuurregelBack.getLeidttotMutatie()).isNull();
    }
}
