package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BeschikkingTestSamples.*;
import static nl.ritense.demo.domain.DeclaratieregelTestSamples.*;
import static nl.ritense.demo.domain.LeverancierTestSamples.*;
import static nl.ritense.demo.domain.LeveringTestSamples.*;
import static nl.ritense.demo.domain.ToewijzingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ToewijzingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Toewijzing.class);
        Toewijzing toewijzing1 = getToewijzingSample1();
        Toewijzing toewijzing2 = new Toewijzing();
        assertThat(toewijzing1).isNotEqualTo(toewijzing2);

        toewijzing2.setId(toewijzing1.getId());
        assertThat(toewijzing1).isEqualTo(toewijzing2);

        toewijzing2 = getToewijzingSample2();
        assertThat(toewijzing1).isNotEqualTo(toewijzing2);
    }

    @Test
    void isopbasisvanDeclaratieregelTest() throws Exception {
        Toewijzing toewijzing = getToewijzingRandomSampleGenerator();
        Declaratieregel declaratieregelBack = getDeclaratieregelRandomSampleGenerator();

        toewijzing.addIsopbasisvanDeclaratieregel(declaratieregelBack);
        assertThat(toewijzing.getIsopbasisvanDeclaratieregels()).containsOnly(declaratieregelBack);
        assertThat(declaratieregelBack.getIsopbasisvanToewijzing()).isEqualTo(toewijzing);

        toewijzing.removeIsopbasisvanDeclaratieregel(declaratieregelBack);
        assertThat(toewijzing.getIsopbasisvanDeclaratieregels()).doesNotContain(declaratieregelBack);
        assertThat(declaratieregelBack.getIsopbasisvanToewijzing()).isNull();

        toewijzing.isopbasisvanDeclaratieregels(new HashSet<>(Set.of(declaratieregelBack)));
        assertThat(toewijzing.getIsopbasisvanDeclaratieregels()).containsOnly(declaratieregelBack);
        assertThat(declaratieregelBack.getIsopbasisvanToewijzing()).isEqualTo(toewijzing);

        toewijzing.setIsopbasisvanDeclaratieregels(new HashSet<>());
        assertThat(toewijzing.getIsopbasisvanDeclaratieregels()).doesNotContain(declaratieregelBack);
        assertThat(declaratieregelBack.getIsopbasisvanToewijzing()).isNull();
    }

    @Test
    void levertvoorzieningLeverancierTest() throws Exception {
        Toewijzing toewijzing = getToewijzingRandomSampleGenerator();
        Leverancier leverancierBack = getLeverancierRandomSampleGenerator();

        toewijzing.setLevertvoorzieningLeverancier(leverancierBack);
        assertThat(toewijzing.getLevertvoorzieningLeverancier()).isEqualTo(leverancierBack);

        toewijzing.levertvoorzieningLeverancier(null);
        assertThat(toewijzing.getLevertvoorzieningLeverancier()).isNull();
    }

    @Test
    void toewijzingBeschikkingTest() throws Exception {
        Toewijzing toewijzing = getToewijzingRandomSampleGenerator();
        Beschikking beschikkingBack = getBeschikkingRandomSampleGenerator();

        toewijzing.setToewijzingBeschikking(beschikkingBack);
        assertThat(toewijzing.getToewijzingBeschikking()).isEqualTo(beschikkingBack);

        toewijzing.toewijzingBeschikking(null);
        assertThat(toewijzing.getToewijzingBeschikking()).isNull();
    }

    @Test
    void geleverdezorgLeveringTest() throws Exception {
        Toewijzing toewijzing = getToewijzingRandomSampleGenerator();
        Levering leveringBack = getLeveringRandomSampleGenerator();

        toewijzing.addGeleverdezorgLevering(leveringBack);
        assertThat(toewijzing.getGeleverdezorgLeverings()).containsOnly(leveringBack);
        assertThat(leveringBack.getGeleverdezorgToewijzing()).isEqualTo(toewijzing);

        toewijzing.removeGeleverdezorgLevering(leveringBack);
        assertThat(toewijzing.getGeleverdezorgLeverings()).doesNotContain(leveringBack);
        assertThat(leveringBack.getGeleverdezorgToewijzing()).isNull();

        toewijzing.geleverdezorgLeverings(new HashSet<>(Set.of(leveringBack)));
        assertThat(toewijzing.getGeleverdezorgLeverings()).containsOnly(leveringBack);
        assertThat(leveringBack.getGeleverdezorgToewijzing()).isEqualTo(toewijzing);

        toewijzing.setGeleverdezorgLeverings(new HashSet<>());
        assertThat(toewijzing.getGeleverdezorgLeverings()).doesNotContain(leveringBack);
        assertThat(leveringBack.getGeleverdezorgToewijzing()).isNull();
    }
}
