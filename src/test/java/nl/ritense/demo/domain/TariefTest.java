package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ContractTestSamples.*;
import static nl.ritense.demo.domain.LeverancierTestSamples.*;
import static nl.ritense.demo.domain.TariefTestSamples.*;
import static nl.ritense.demo.domain.VoorzieningTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TariefTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tarief.class);
        Tarief tarief1 = getTariefSample1();
        Tarief tarief2 = new Tarief();
        assertThat(tarief1).isNotEqualTo(tarief2);

        tarief2.setId(tarief1.getId());
        assertThat(tarief1).isEqualTo(tarief2);

        tarief2 = getTariefSample2();
        assertThat(tarief1).isNotEqualTo(tarief2);
    }

    @Test
    void heeftLeverancierTest() throws Exception {
        Tarief tarief = getTariefRandomSampleGenerator();
        Leverancier leverancierBack = getLeverancierRandomSampleGenerator();

        tarief.setHeeftLeverancier(leverancierBack);
        assertThat(tarief.getHeeftLeverancier()).isEqualTo(leverancierBack);

        tarief.heeftLeverancier(null);
        assertThat(tarief.getHeeftLeverancier()).isNull();
    }

    @Test
    void bevatContractTest() throws Exception {
        Tarief tarief = getTariefRandomSampleGenerator();
        Contract contractBack = getContractRandomSampleGenerator();

        tarief.setBevatContract(contractBack);
        assertThat(tarief.getBevatContract()).isEqualTo(contractBack);

        tarief.bevatContract(null);
        assertThat(tarief.getBevatContract()).isNull();
    }

    @Test
    void heeftVoorzieningTest() throws Exception {
        Tarief tarief = getTariefRandomSampleGenerator();
        Voorziening voorzieningBack = getVoorzieningRandomSampleGenerator();

        tarief.setHeeftVoorziening(voorzieningBack);
        assertThat(tarief.getHeeftVoorziening()).isEqualTo(voorzieningBack);

        tarief.heeftVoorziening(null);
        assertThat(tarief.getHeeftVoorziening()).isNull();
    }
}
