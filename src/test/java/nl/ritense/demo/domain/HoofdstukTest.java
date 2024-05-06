package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BegrotingregelTestSamples.*;
import static nl.ritense.demo.domain.DoelstellingTestSamples.*;
import static nl.ritense.demo.domain.HoofdstukTestSamples.*;
import static nl.ritense.demo.domain.PeriodeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HoofdstukTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hoofdstuk.class);
        Hoofdstuk hoofdstuk1 = getHoofdstukSample1();
        Hoofdstuk hoofdstuk2 = new Hoofdstuk();
        assertThat(hoofdstuk1).isNotEqualTo(hoofdstuk2);

        hoofdstuk2.setId(hoofdstuk1.getId());
        assertThat(hoofdstuk1).isEqualTo(hoofdstuk2);

        hoofdstuk2 = getHoofdstukSample2();
        assertThat(hoofdstuk1).isNotEqualTo(hoofdstuk2);
    }

    @Test
    void heeftDoelstellingTest() throws Exception {
        Hoofdstuk hoofdstuk = getHoofdstukRandomSampleGenerator();
        Doelstelling doelstellingBack = getDoelstellingRandomSampleGenerator();

        hoofdstuk.addHeeftDoelstelling(doelstellingBack);
        assertThat(hoofdstuk.getHeeftDoelstellings()).containsOnly(doelstellingBack);
        assertThat(doelstellingBack.getHeeftHoofdstuk()).isEqualTo(hoofdstuk);

        hoofdstuk.removeHeeftDoelstelling(doelstellingBack);
        assertThat(hoofdstuk.getHeeftDoelstellings()).doesNotContain(doelstellingBack);
        assertThat(doelstellingBack.getHeeftHoofdstuk()).isNull();

        hoofdstuk.heeftDoelstellings(new HashSet<>(Set.of(doelstellingBack)));
        assertThat(hoofdstuk.getHeeftDoelstellings()).containsOnly(doelstellingBack);
        assertThat(doelstellingBack.getHeeftHoofdstuk()).isEqualTo(hoofdstuk);

        hoofdstuk.setHeeftDoelstellings(new HashSet<>());
        assertThat(hoofdstuk.getHeeftDoelstellings()).doesNotContain(doelstellingBack);
        assertThat(doelstellingBack.getHeeftHoofdstuk()).isNull();
    }

    @Test
    void binnenPeriodeTest() throws Exception {
        Hoofdstuk hoofdstuk = getHoofdstukRandomSampleGenerator();
        Periode periodeBack = getPeriodeRandomSampleGenerator();

        hoofdstuk.addBinnenPeriode(periodeBack);
        assertThat(hoofdstuk.getBinnenPeriodes()).containsOnly(periodeBack);

        hoofdstuk.removeBinnenPeriode(periodeBack);
        assertThat(hoofdstuk.getBinnenPeriodes()).doesNotContain(periodeBack);

        hoofdstuk.binnenPeriodes(new HashSet<>(Set.of(periodeBack)));
        assertThat(hoofdstuk.getBinnenPeriodes()).containsOnly(periodeBack);

        hoofdstuk.setBinnenPeriodes(new HashSet<>());
        assertThat(hoofdstuk.getBinnenPeriodes()).doesNotContain(periodeBack);
    }

    @Test
    void betreftBegrotingregelTest() throws Exception {
        Hoofdstuk hoofdstuk = getHoofdstukRandomSampleGenerator();
        Begrotingregel begrotingregelBack = getBegrotingregelRandomSampleGenerator();

        hoofdstuk.addBetreftBegrotingregel(begrotingregelBack);
        assertThat(hoofdstuk.getBetreftBegrotingregels()).containsOnly(begrotingregelBack);
        assertThat(begrotingregelBack.getBetreftHoofdstuk()).isEqualTo(hoofdstuk);

        hoofdstuk.removeBetreftBegrotingregel(begrotingregelBack);
        assertThat(hoofdstuk.getBetreftBegrotingregels()).doesNotContain(begrotingregelBack);
        assertThat(begrotingregelBack.getBetreftHoofdstuk()).isNull();

        hoofdstuk.betreftBegrotingregels(new HashSet<>(Set.of(begrotingregelBack)));
        assertThat(hoofdstuk.getBetreftBegrotingregels()).containsOnly(begrotingregelBack);
        assertThat(begrotingregelBack.getBetreftHoofdstuk()).isEqualTo(hoofdstuk);

        hoofdstuk.setBetreftBegrotingregels(new HashSet<>());
        assertThat(hoofdstuk.getBetreftBegrotingregels()).doesNotContain(begrotingregelBack);
        assertThat(begrotingregelBack.getBetreftHoofdstuk()).isNull();
    }
}
