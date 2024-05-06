package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ActivaTestSamples.*;
import static nl.ritense.demo.domain.BegrotingregelTestSamples.*;
import static nl.ritense.demo.domain.HoofdrekeningTestSamples.*;
import static nl.ritense.demo.domain.HoofdrekeningTestSamples.*;
import static nl.ritense.demo.domain.InkooporderTestSamples.*;
import static nl.ritense.demo.domain.KostenplaatsTestSamples.*;
import static nl.ritense.demo.domain.MutatieTestSamples.*;
import static nl.ritense.demo.domain.SubrekeningTestSamples.*;
import static nl.ritense.demo.domain.WerkorderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HoofdrekeningTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hoofdrekening.class);
        Hoofdrekening hoofdrekening1 = getHoofdrekeningSample1();
        Hoofdrekening hoofdrekening2 = new Hoofdrekening();
        assertThat(hoofdrekening1).isNotEqualTo(hoofdrekening2);

        hoofdrekening2.setId(hoofdrekening1.getId());
        assertThat(hoofdrekening1).isEqualTo(hoofdrekening2);

        hoofdrekening2 = getHoofdrekeningSample2();
        assertThat(hoofdrekening1).isNotEqualTo(hoofdrekening2);
    }

    @Test
    void valtbinnenHoofdrekeningTest() throws Exception {
        Hoofdrekening hoofdrekening = getHoofdrekeningRandomSampleGenerator();
        Hoofdrekening hoofdrekeningBack = getHoofdrekeningRandomSampleGenerator();

        hoofdrekening.addValtbinnenHoofdrekening(hoofdrekeningBack);
        assertThat(hoofdrekening.getValtbinnenHoofdrekenings()).containsOnly(hoofdrekeningBack);
        assertThat(hoofdrekeningBack.getValtbinnenHoofdrekening2()).isEqualTo(hoofdrekening);

        hoofdrekening.removeValtbinnenHoofdrekening(hoofdrekeningBack);
        assertThat(hoofdrekening.getValtbinnenHoofdrekenings()).doesNotContain(hoofdrekeningBack);
        assertThat(hoofdrekeningBack.getValtbinnenHoofdrekening2()).isNull();

        hoofdrekening.valtbinnenHoofdrekenings(new HashSet<>(Set.of(hoofdrekeningBack)));
        assertThat(hoofdrekening.getValtbinnenHoofdrekenings()).containsOnly(hoofdrekeningBack);
        assertThat(hoofdrekeningBack.getValtbinnenHoofdrekening2()).isEqualTo(hoofdrekening);

        hoofdrekening.setValtbinnenHoofdrekenings(new HashSet<>());
        assertThat(hoofdrekening.getValtbinnenHoofdrekenings()).doesNotContain(hoofdrekeningBack);
        assertThat(hoofdrekeningBack.getValtbinnenHoofdrekening2()).isNull();
    }

    @Test
    void heeftSubrekeningTest() throws Exception {
        Hoofdrekening hoofdrekening = getHoofdrekeningRandomSampleGenerator();
        Subrekening subrekeningBack = getSubrekeningRandomSampleGenerator();

        hoofdrekening.addHeeftSubrekening(subrekeningBack);
        assertThat(hoofdrekening.getHeeftSubrekenings()).containsOnly(subrekeningBack);
        assertThat(subrekeningBack.getHeeftHoofdrekening()).isEqualTo(hoofdrekening);

        hoofdrekening.removeHeeftSubrekening(subrekeningBack);
        assertThat(hoofdrekening.getHeeftSubrekenings()).doesNotContain(subrekeningBack);
        assertThat(subrekeningBack.getHeeftHoofdrekening()).isNull();

        hoofdrekening.heeftSubrekenings(new HashSet<>(Set.of(subrekeningBack)));
        assertThat(hoofdrekening.getHeeftSubrekenings()).containsOnly(subrekeningBack);
        assertThat(subrekeningBack.getHeeftHoofdrekening()).isEqualTo(hoofdrekening);

        hoofdrekening.setHeeftSubrekenings(new HashSet<>());
        assertThat(hoofdrekening.getHeeftSubrekenings()).doesNotContain(subrekeningBack);
        assertThat(subrekeningBack.getHeeftHoofdrekening()).isNull();
    }

    @Test
    void heeftWerkorderTest() throws Exception {
        Hoofdrekening hoofdrekening = getHoofdrekeningRandomSampleGenerator();
        Werkorder werkorderBack = getWerkorderRandomSampleGenerator();

        hoofdrekening.addHeeftWerkorder(werkorderBack);
        assertThat(hoofdrekening.getHeeftWerkorders()).containsOnly(werkorderBack);
        assertThat(werkorderBack.getHeeftHoofdrekening()).isEqualTo(hoofdrekening);

        hoofdrekening.removeHeeftWerkorder(werkorderBack);
        assertThat(hoofdrekening.getHeeftWerkorders()).doesNotContain(werkorderBack);
        assertThat(werkorderBack.getHeeftHoofdrekening()).isNull();

        hoofdrekening.heeftWerkorders(new HashSet<>(Set.of(werkorderBack)));
        assertThat(hoofdrekening.getHeeftWerkorders()).containsOnly(werkorderBack);
        assertThat(werkorderBack.getHeeftHoofdrekening()).isEqualTo(hoofdrekening);

        hoofdrekening.setHeeftWerkorders(new HashSet<>());
        assertThat(hoofdrekening.getHeeftWerkorders()).doesNotContain(werkorderBack);
        assertThat(werkorderBack.getHeeftHoofdrekening()).isNull();
    }

    @Test
    void heeftActivaTest() throws Exception {
        Hoofdrekening hoofdrekening = getHoofdrekeningRandomSampleGenerator();
        Activa activaBack = getActivaRandomSampleGenerator();

        hoofdrekening.addHeeftActiva(activaBack);
        assertThat(hoofdrekening.getHeeftActivas()).containsOnly(activaBack);

        hoofdrekening.removeHeeftActiva(activaBack);
        assertThat(hoofdrekening.getHeeftActivas()).doesNotContain(activaBack);

        hoofdrekening.heeftActivas(new HashSet<>(Set.of(activaBack)));
        assertThat(hoofdrekening.getHeeftActivas()).containsOnly(activaBack);

        hoofdrekening.setHeeftActivas(new HashSet<>());
        assertThat(hoofdrekening.getHeeftActivas()).doesNotContain(activaBack);
    }

    @Test
    void heeftKostenplaatsTest() throws Exception {
        Hoofdrekening hoofdrekening = getHoofdrekeningRandomSampleGenerator();
        Kostenplaats kostenplaatsBack = getKostenplaatsRandomSampleGenerator();

        hoofdrekening.addHeeftKostenplaats(kostenplaatsBack);
        assertThat(hoofdrekening.getHeeftKostenplaats()).containsOnly(kostenplaatsBack);

        hoofdrekening.removeHeeftKostenplaats(kostenplaatsBack);
        assertThat(hoofdrekening.getHeeftKostenplaats()).doesNotContain(kostenplaatsBack);

        hoofdrekening.heeftKostenplaats(new HashSet<>(Set.of(kostenplaatsBack)));
        assertThat(hoofdrekening.getHeeftKostenplaats()).containsOnly(kostenplaatsBack);

        hoofdrekening.setHeeftKostenplaats(new HashSet<>());
        assertThat(hoofdrekening.getHeeftKostenplaats()).doesNotContain(kostenplaatsBack);
    }

    @Test
    void valtbinnenHoofdrekening2Test() throws Exception {
        Hoofdrekening hoofdrekening = getHoofdrekeningRandomSampleGenerator();
        Hoofdrekening hoofdrekeningBack = getHoofdrekeningRandomSampleGenerator();

        hoofdrekening.setValtbinnenHoofdrekening2(hoofdrekeningBack);
        assertThat(hoofdrekening.getValtbinnenHoofdrekening2()).isEqualTo(hoofdrekeningBack);

        hoofdrekening.valtbinnenHoofdrekening2(null);
        assertThat(hoofdrekening.getValtbinnenHoofdrekening2()).isNull();
    }

    @Test
    void betreftBegrotingregelTest() throws Exception {
        Hoofdrekening hoofdrekening = getHoofdrekeningRandomSampleGenerator();
        Begrotingregel begrotingregelBack = getBegrotingregelRandomSampleGenerator();

        hoofdrekening.addBetreftBegrotingregel(begrotingregelBack);
        assertThat(hoofdrekening.getBetreftBegrotingregels()).containsOnly(begrotingregelBack);
        assertThat(begrotingregelBack.getBetreftHoofdrekening()).isEqualTo(hoofdrekening);

        hoofdrekening.removeBetreftBegrotingregel(begrotingregelBack);
        assertThat(hoofdrekening.getBetreftBegrotingregels()).doesNotContain(begrotingregelBack);
        assertThat(begrotingregelBack.getBetreftHoofdrekening()).isNull();

        hoofdrekening.betreftBegrotingregels(new HashSet<>(Set.of(begrotingregelBack)));
        assertThat(hoofdrekening.getBetreftBegrotingregels()).containsOnly(begrotingregelBack);
        assertThat(begrotingregelBack.getBetreftHoofdrekening()).isEqualTo(hoofdrekening);

        hoofdrekening.setBetreftBegrotingregels(new HashSet<>());
        assertThat(hoofdrekening.getBetreftBegrotingregels()).doesNotContain(begrotingregelBack);
        assertThat(begrotingregelBack.getBetreftHoofdrekening()).isNull();
    }

    @Test
    void vanMutatieTest() throws Exception {
        Hoofdrekening hoofdrekening = getHoofdrekeningRandomSampleGenerator();
        Mutatie mutatieBack = getMutatieRandomSampleGenerator();

        hoofdrekening.addVanMutatie(mutatieBack);
        assertThat(hoofdrekening.getVanMutaties()).containsOnly(mutatieBack);
        assertThat(mutatieBack.getVanHoofdrekening()).isEqualTo(hoofdrekening);

        hoofdrekening.removeVanMutatie(mutatieBack);
        assertThat(hoofdrekening.getVanMutaties()).doesNotContain(mutatieBack);
        assertThat(mutatieBack.getVanHoofdrekening()).isNull();

        hoofdrekening.vanMutaties(new HashSet<>(Set.of(mutatieBack)));
        assertThat(hoofdrekening.getVanMutaties()).containsOnly(mutatieBack);
        assertThat(mutatieBack.getVanHoofdrekening()).isEqualTo(hoofdrekening);

        hoofdrekening.setVanMutaties(new HashSet<>());
        assertThat(hoofdrekening.getVanMutaties()).doesNotContain(mutatieBack);
        assertThat(mutatieBack.getVanHoofdrekening()).isNull();
    }

    @Test
    void naarMutatieTest() throws Exception {
        Hoofdrekening hoofdrekening = getHoofdrekeningRandomSampleGenerator();
        Mutatie mutatieBack = getMutatieRandomSampleGenerator();

        hoofdrekening.addNaarMutatie(mutatieBack);
        assertThat(hoofdrekening.getNaarMutaties()).containsOnly(mutatieBack);
        assertThat(mutatieBack.getNaarHoofdrekening()).isEqualTo(hoofdrekening);

        hoofdrekening.removeNaarMutatie(mutatieBack);
        assertThat(hoofdrekening.getNaarMutaties()).doesNotContain(mutatieBack);
        assertThat(mutatieBack.getNaarHoofdrekening()).isNull();

        hoofdrekening.naarMutaties(new HashSet<>(Set.of(mutatieBack)));
        assertThat(hoofdrekening.getNaarMutaties()).containsOnly(mutatieBack);
        assertThat(mutatieBack.getNaarHoofdrekening()).isEqualTo(hoofdrekening);

        hoofdrekening.setNaarMutaties(new HashSet<>());
        assertThat(hoofdrekening.getNaarMutaties()).doesNotContain(mutatieBack);
        assertThat(mutatieBack.getNaarHoofdrekening()).isNull();
    }

    @Test
    void wordtgeschrevenopInkooporderTest() throws Exception {
        Hoofdrekening hoofdrekening = getHoofdrekeningRandomSampleGenerator();
        Inkooporder inkooporderBack = getInkooporderRandomSampleGenerator();

        hoofdrekening.addWordtgeschrevenopInkooporder(inkooporderBack);
        assertThat(hoofdrekening.getWordtgeschrevenopInkooporders()).containsOnly(inkooporderBack);
        assertThat(inkooporderBack.getWordtgeschrevenopHoofdrekenings()).containsOnly(hoofdrekening);

        hoofdrekening.removeWordtgeschrevenopInkooporder(inkooporderBack);
        assertThat(hoofdrekening.getWordtgeschrevenopInkooporders()).doesNotContain(inkooporderBack);
        assertThat(inkooporderBack.getWordtgeschrevenopHoofdrekenings()).doesNotContain(hoofdrekening);

        hoofdrekening.wordtgeschrevenopInkooporders(new HashSet<>(Set.of(inkooporderBack)));
        assertThat(hoofdrekening.getWordtgeschrevenopInkooporders()).containsOnly(inkooporderBack);
        assertThat(inkooporderBack.getWordtgeschrevenopHoofdrekenings()).containsOnly(hoofdrekening);

        hoofdrekening.setWordtgeschrevenopInkooporders(new HashSet<>());
        assertThat(hoofdrekening.getWordtgeschrevenopInkooporders()).doesNotContain(inkooporderBack);
        assertThat(inkooporderBack.getWordtgeschrevenopHoofdrekenings()).doesNotContain(hoofdrekening);
    }
}
