package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ContractTestSamples.*;
import static nl.ritense.demo.domain.ContractTestSamples.*;
import static nl.ritense.demo.domain.InkooporderTestSamples.*;
import static nl.ritense.demo.domain.LeverancierTestSamples.*;
import static nl.ritense.demo.domain.TariefTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContractTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contract.class);
        Contract contract1 = getContractSample1();
        Contract contract2 = new Contract();
        assertThat(contract1).isNotEqualTo(contract2);

        contract2.setId(contract1.getId());
        assertThat(contract1).isEqualTo(contract2);

        contract2 = getContractSample2();
        assertThat(contract1).isNotEqualTo(contract2);
    }

    @Test
    void bevatTariefTest() throws Exception {
        Contract contract = getContractRandomSampleGenerator();
        Tarief tariefBack = getTariefRandomSampleGenerator();

        contract.addBevatTarief(tariefBack);
        assertThat(contract.getBevatTariefs()).containsOnly(tariefBack);
        assertThat(tariefBack.getBevatContract()).isEqualTo(contract);

        contract.removeBevatTarief(tariefBack);
        assertThat(contract.getBevatTariefs()).doesNotContain(tariefBack);
        assertThat(tariefBack.getBevatContract()).isNull();

        contract.bevatTariefs(new HashSet<>(Set.of(tariefBack)));
        assertThat(contract.getBevatTariefs()).containsOnly(tariefBack);
        assertThat(tariefBack.getBevatContract()).isEqualTo(contract);

        contract.setBevatTariefs(new HashSet<>());
        assertThat(contract.getBevatTariefs()).doesNotContain(tariefBack);
        assertThat(tariefBack.getBevatContract()).isNull();
    }

    @Test
    void bovenliggendContractTest() throws Exception {
        Contract contract = getContractRandomSampleGenerator();
        Contract contractBack = getContractRandomSampleGenerator();

        contract.setBovenliggendContract(contractBack);
        assertThat(contract.getBovenliggendContract()).isEqualTo(contractBack);

        contract.bovenliggendContract(null);
        assertThat(contract.getBovenliggendContract()).isNull();
    }

    @Test
    void betreftInkooporderTest() throws Exception {
        Contract contract = getContractRandomSampleGenerator();
        Inkooporder inkooporderBack = getInkooporderRandomSampleGenerator();

        contract.setBetreftInkooporder(inkooporderBack);
        assertThat(contract.getBetreftInkooporder()).isEqualTo(inkooporderBack);
        assertThat(inkooporderBack.getBetreftContract()).isEqualTo(contract);

        contract.betreftInkooporder(null);
        assertThat(contract.getBetreftInkooporder()).isNull();
        assertThat(inkooporderBack.getBetreftContract()).isNull();
    }

    @Test
    void heeftLeverancierTest() throws Exception {
        Contract contract = getContractRandomSampleGenerator();
        Leverancier leverancierBack = getLeverancierRandomSampleGenerator();

        contract.setHeeftLeverancier(leverancierBack);
        assertThat(contract.getHeeftLeverancier()).isEqualTo(leverancierBack);

        contract.heeftLeverancier(null);
        assertThat(contract.getHeeftLeverancier()).isNull();
    }

    @Test
    void contractantLeverancierTest() throws Exception {
        Contract contract = getContractRandomSampleGenerator();
        Leverancier leverancierBack = getLeverancierRandomSampleGenerator();

        contract.setContractantLeverancier(leverancierBack);
        assertThat(contract.getContractantLeverancier()).isEqualTo(leverancierBack);

        contract.contractantLeverancier(null);
        assertThat(contract.getContractantLeverancier()).isNull();
    }

    @Test
    void bovenliggendContract2Test() throws Exception {
        Contract contract = getContractRandomSampleGenerator();
        Contract contractBack = getContractRandomSampleGenerator();

        contract.addBovenliggendContract2(contractBack);
        assertThat(contract.getBovenliggendContract2s()).containsOnly(contractBack);
        assertThat(contractBack.getBovenliggendContract()).isEqualTo(contract);

        contract.removeBovenliggendContract2(contractBack);
        assertThat(contract.getBovenliggendContract2s()).doesNotContain(contractBack);
        assertThat(contractBack.getBovenliggendContract()).isNull();

        contract.bovenliggendContract2s(new HashSet<>(Set.of(contractBack)));
        assertThat(contract.getBovenliggendContract2s()).containsOnly(contractBack);
        assertThat(contractBack.getBovenliggendContract()).isEqualTo(contract);

        contract.setBovenliggendContract2s(new HashSet<>());
        assertThat(contract.getBovenliggendContract2s()).doesNotContain(contractBack);
        assertThat(contractBack.getBovenliggendContract()).isNull();
    }
}
