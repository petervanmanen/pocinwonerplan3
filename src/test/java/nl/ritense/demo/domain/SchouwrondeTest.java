package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AreaalTestSamples.*;
import static nl.ritense.demo.domain.MedewerkerTestSamples.*;
import static nl.ritense.demo.domain.MeldingTestSamples.*;
import static nl.ritense.demo.domain.SchouwrondeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SchouwrondeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Schouwronde.class);
        Schouwronde schouwronde1 = getSchouwrondeSample1();
        Schouwronde schouwronde2 = new Schouwronde();
        assertThat(schouwronde1).isNotEqualTo(schouwronde2);

        schouwronde2.setId(schouwronde1.getId());
        assertThat(schouwronde1).isEqualTo(schouwronde2);

        schouwronde2 = getSchouwrondeSample2();
        assertThat(schouwronde1).isNotEqualTo(schouwronde2);
    }

    @Test
    void heeftMeldingTest() throws Exception {
        Schouwronde schouwronde = getSchouwrondeRandomSampleGenerator();
        Melding meldingBack = getMeldingRandomSampleGenerator();

        schouwronde.addHeeftMelding(meldingBack);
        assertThat(schouwronde.getHeeftMeldings()).containsOnly(meldingBack);
        assertThat(meldingBack.getHeeftSchouwronde()).isEqualTo(schouwronde);

        schouwronde.removeHeeftMelding(meldingBack);
        assertThat(schouwronde.getHeeftMeldings()).doesNotContain(meldingBack);
        assertThat(meldingBack.getHeeftSchouwronde()).isNull();

        schouwronde.heeftMeldings(new HashSet<>(Set.of(meldingBack)));
        assertThat(schouwronde.getHeeftMeldings()).containsOnly(meldingBack);
        assertThat(meldingBack.getHeeftSchouwronde()).isEqualTo(schouwronde);

        schouwronde.setHeeftMeldings(new HashSet<>());
        assertThat(schouwronde.getHeeftMeldings()).doesNotContain(meldingBack);
        assertThat(meldingBack.getHeeftSchouwronde()).isNull();
    }

    @Test
    void voertuitMedewerkerTest() throws Exception {
        Schouwronde schouwronde = getSchouwrondeRandomSampleGenerator();
        Medewerker medewerkerBack = getMedewerkerRandomSampleGenerator();

        schouwronde.setVoertuitMedewerker(medewerkerBack);
        assertThat(schouwronde.getVoertuitMedewerker()).isEqualTo(medewerkerBack);

        schouwronde.voertuitMedewerker(null);
        assertThat(schouwronde.getVoertuitMedewerker()).isNull();
    }

    @Test
    void binnenAreaalTest() throws Exception {
        Schouwronde schouwronde = getSchouwrondeRandomSampleGenerator();
        Areaal areaalBack = getAreaalRandomSampleGenerator();

        schouwronde.addBinnenAreaal(areaalBack);
        assertThat(schouwronde.getBinnenAreaals()).containsOnly(areaalBack);
        assertThat(areaalBack.getBinnenSchouwrondes()).containsOnly(schouwronde);

        schouwronde.removeBinnenAreaal(areaalBack);
        assertThat(schouwronde.getBinnenAreaals()).doesNotContain(areaalBack);
        assertThat(areaalBack.getBinnenSchouwrondes()).doesNotContain(schouwronde);

        schouwronde.binnenAreaals(new HashSet<>(Set.of(areaalBack)));
        assertThat(schouwronde.getBinnenAreaals()).containsOnly(areaalBack);
        assertThat(areaalBack.getBinnenSchouwrondes()).containsOnly(schouwronde);

        schouwronde.setBinnenAreaals(new HashSet<>());
        assertThat(schouwronde.getBinnenAreaals()).doesNotContain(areaalBack);
        assertThat(areaalBack.getBinnenSchouwrondes()).doesNotContain(schouwronde);
    }
}
