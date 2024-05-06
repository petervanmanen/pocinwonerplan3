package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ContractTestSamples.*;
import static nl.ritense.demo.domain.FactuurTestSamples.*;
import static nl.ritense.demo.domain.HoofdrekeningTestSamples.*;
import static nl.ritense.demo.domain.InkooporderTestSamples.*;
import static nl.ritense.demo.domain.InkooporderTestSamples.*;
import static nl.ritense.demo.domain.InkooppakketTestSamples.*;
import static nl.ritense.demo.domain.KostenplaatsTestSamples.*;
import static nl.ritense.demo.domain.LeverancierTestSamples.*;
import static nl.ritense.demo.domain.WerkbonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InkooporderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inkooporder.class);
        Inkooporder inkooporder1 = getInkooporderSample1();
        Inkooporder inkooporder2 = new Inkooporder();
        assertThat(inkooporder1).isNotEqualTo(inkooporder2);

        inkooporder2.setId(inkooporder1.getId());
        assertThat(inkooporder1).isEqualTo(inkooporder2);

        inkooporder2 = getInkooporderSample2();
        assertThat(inkooporder1).isNotEqualTo(inkooporder2);
    }

    @Test
    void betreftContractTest() throws Exception {
        Inkooporder inkooporder = getInkooporderRandomSampleGenerator();
        Contract contractBack = getContractRandomSampleGenerator();

        inkooporder.setBetreftContract(contractBack);
        assertThat(inkooporder.getBetreftContract()).isEqualTo(contractBack);

        inkooporder.betreftContract(null);
        assertThat(inkooporder.getBetreftContract()).isNull();
    }

    @Test
    void oorspronkelijkInkooporderTest() throws Exception {
        Inkooporder inkooporder = getInkooporderRandomSampleGenerator();
        Inkooporder inkooporderBack = getInkooporderRandomSampleGenerator();

        inkooporder.setOorspronkelijkInkooporder(inkooporderBack);
        assertThat(inkooporder.getOorspronkelijkInkooporder()).isEqualTo(inkooporderBack);

        inkooporder.oorspronkelijkInkooporder(null);
        assertThat(inkooporder.getOorspronkelijkInkooporder()).isNull();
    }

    @Test
    void hoortbijWerkbonTest() throws Exception {
        Inkooporder inkooporder = getInkooporderRandomSampleGenerator();
        Werkbon werkbonBack = getWerkbonRandomSampleGenerator();

        inkooporder.addHoortbijWerkbon(werkbonBack);
        assertThat(inkooporder.getHoortbijWerkbons()).containsOnly(werkbonBack);
        assertThat(werkbonBack.getHoortbijInkooporder()).isEqualTo(inkooporder);

        inkooporder.removeHoortbijWerkbon(werkbonBack);
        assertThat(inkooporder.getHoortbijWerkbons()).doesNotContain(werkbonBack);
        assertThat(werkbonBack.getHoortbijInkooporder()).isNull();

        inkooporder.hoortbijWerkbons(new HashSet<>(Set.of(werkbonBack)));
        assertThat(inkooporder.getHoortbijWerkbons()).containsOnly(werkbonBack);
        assertThat(werkbonBack.getHoortbijInkooporder()).isEqualTo(inkooporder);

        inkooporder.setHoortbijWerkbons(new HashSet<>());
        assertThat(inkooporder.getHoortbijWerkbons()).doesNotContain(werkbonBack);
        assertThat(werkbonBack.getHoortbijInkooporder()).isNull();
    }

    @Test
    void gerelateerdInkooporderTest() throws Exception {
        Inkooporder inkooporder = getInkooporderRandomSampleGenerator();
        Inkooporder inkooporderBack = getInkooporderRandomSampleGenerator();

        inkooporder.addGerelateerdInkooporder(inkooporderBack);
        assertThat(inkooporder.getGerelateerdInkooporders()).containsOnly(inkooporderBack);
        assertThat(inkooporderBack.getGerelateerdInkooporder2()).isEqualTo(inkooporder);

        inkooporder.removeGerelateerdInkooporder(inkooporderBack);
        assertThat(inkooporder.getGerelateerdInkooporders()).doesNotContain(inkooporderBack);
        assertThat(inkooporderBack.getGerelateerdInkooporder2()).isNull();

        inkooporder.gerelateerdInkooporders(new HashSet<>(Set.of(inkooporderBack)));
        assertThat(inkooporder.getGerelateerdInkooporders()).containsOnly(inkooporderBack);
        assertThat(inkooporderBack.getGerelateerdInkooporder2()).isEqualTo(inkooporder);

        inkooporder.setGerelateerdInkooporders(new HashSet<>());
        assertThat(inkooporder.getGerelateerdInkooporders()).doesNotContain(inkooporderBack);
        assertThat(inkooporderBack.getGerelateerdInkooporder2()).isNull();
    }

    @Test
    void heeftInkooppakketTest() throws Exception {
        Inkooporder inkooporder = getInkooporderRandomSampleGenerator();
        Inkooppakket inkooppakketBack = getInkooppakketRandomSampleGenerator();

        inkooporder.setHeeftInkooppakket(inkooppakketBack);
        assertThat(inkooporder.getHeeftInkooppakket()).isEqualTo(inkooppakketBack);

        inkooporder.heeftInkooppakket(null);
        assertThat(inkooporder.getHeeftInkooppakket()).isNull();
    }

    @Test
    void verplichtingaanLeverancierTest() throws Exception {
        Inkooporder inkooporder = getInkooporderRandomSampleGenerator();
        Leverancier leverancierBack = getLeverancierRandomSampleGenerator();

        inkooporder.setVerplichtingaanLeverancier(leverancierBack);
        assertThat(inkooporder.getVerplichtingaanLeverancier()).isEqualTo(leverancierBack);

        inkooporder.verplichtingaanLeverancier(null);
        assertThat(inkooporder.getVerplichtingaanLeverancier()).isNull();
    }

    @Test
    void wordtgeschrevenopHoofdrekeningTest() throws Exception {
        Inkooporder inkooporder = getInkooporderRandomSampleGenerator();
        Hoofdrekening hoofdrekeningBack = getHoofdrekeningRandomSampleGenerator();

        inkooporder.addWordtgeschrevenopHoofdrekening(hoofdrekeningBack);
        assertThat(inkooporder.getWordtgeschrevenopHoofdrekenings()).containsOnly(hoofdrekeningBack);

        inkooporder.removeWordtgeschrevenopHoofdrekening(hoofdrekeningBack);
        assertThat(inkooporder.getWordtgeschrevenopHoofdrekenings()).doesNotContain(hoofdrekeningBack);

        inkooporder.wordtgeschrevenopHoofdrekenings(new HashSet<>(Set.of(hoofdrekeningBack)));
        assertThat(inkooporder.getWordtgeschrevenopHoofdrekenings()).containsOnly(hoofdrekeningBack);

        inkooporder.setWordtgeschrevenopHoofdrekenings(new HashSet<>());
        assertThat(inkooporder.getWordtgeschrevenopHoofdrekenings()).doesNotContain(hoofdrekeningBack);
    }

    @Test
    void oorspronkelijkInkooporder2Test() throws Exception {
        Inkooporder inkooporder = getInkooporderRandomSampleGenerator();
        Inkooporder inkooporderBack = getInkooporderRandomSampleGenerator();

        inkooporder.setOorspronkelijkInkooporder2(inkooporderBack);
        assertThat(inkooporder.getOorspronkelijkInkooporder2()).isEqualTo(inkooporderBack);
        assertThat(inkooporderBack.getOorspronkelijkInkooporder()).isEqualTo(inkooporder);

        inkooporder.oorspronkelijkInkooporder2(null);
        assertThat(inkooporder.getOorspronkelijkInkooporder2()).isNull();
        assertThat(inkooporderBack.getOorspronkelijkInkooporder()).isNull();
    }

    @Test
    void gerelateerdInkooporder2Test() throws Exception {
        Inkooporder inkooporder = getInkooporderRandomSampleGenerator();
        Inkooporder inkooporderBack = getInkooporderRandomSampleGenerator();

        inkooporder.setGerelateerdInkooporder2(inkooporderBack);
        assertThat(inkooporder.getGerelateerdInkooporder2()).isEqualTo(inkooporderBack);

        inkooporder.gerelateerdInkooporder2(null);
        assertThat(inkooporder.getGerelateerdInkooporder2()).isNull();
    }

    @Test
    void gedektviaFactuurTest() throws Exception {
        Inkooporder inkooporder = getInkooporderRandomSampleGenerator();
        Factuur factuurBack = getFactuurRandomSampleGenerator();

        inkooporder.addGedektviaFactuur(factuurBack);
        assertThat(inkooporder.getGedektviaFactuurs()).containsOnly(factuurBack);
        assertThat(factuurBack.getGedektviaInkooporder()).isEqualTo(inkooporder);

        inkooporder.removeGedektviaFactuur(factuurBack);
        assertThat(inkooporder.getGedektviaFactuurs()).doesNotContain(factuurBack);
        assertThat(factuurBack.getGedektviaInkooporder()).isNull();

        inkooporder.gedektviaFactuurs(new HashSet<>(Set.of(factuurBack)));
        assertThat(inkooporder.getGedektviaFactuurs()).containsOnly(factuurBack);
        assertThat(factuurBack.getGedektviaInkooporder()).isEqualTo(inkooporder);

        inkooporder.setGedektviaFactuurs(new HashSet<>());
        assertThat(inkooporder.getGedektviaFactuurs()).doesNotContain(factuurBack);
        assertThat(factuurBack.getGedektviaInkooporder()).isNull();
    }

    @Test
    void heeftKostenplaatsTest() throws Exception {
        Inkooporder inkooporder = getInkooporderRandomSampleGenerator();
        Kostenplaats kostenplaatsBack = getKostenplaatsRandomSampleGenerator();

        inkooporder.addHeeftKostenplaats(kostenplaatsBack);
        assertThat(inkooporder.getHeeftKostenplaats()).containsOnly(kostenplaatsBack);
        assertThat(kostenplaatsBack.getHeeftInkooporders()).containsOnly(inkooporder);

        inkooporder.removeHeeftKostenplaats(kostenplaatsBack);
        assertThat(inkooporder.getHeeftKostenplaats()).doesNotContain(kostenplaatsBack);
        assertThat(kostenplaatsBack.getHeeftInkooporders()).doesNotContain(inkooporder);

        inkooporder.heeftKostenplaats(new HashSet<>(Set.of(kostenplaatsBack)));
        assertThat(inkooporder.getHeeftKostenplaats()).containsOnly(kostenplaatsBack);
        assertThat(kostenplaatsBack.getHeeftInkooporders()).containsOnly(inkooporder);

        inkooporder.setHeeftKostenplaats(new HashSet<>());
        assertThat(inkooporder.getHeeftKostenplaats()).doesNotContain(kostenplaatsBack);
        assertThat(kostenplaatsBack.getHeeftInkooporders()).doesNotContain(inkooporder);
    }
}
