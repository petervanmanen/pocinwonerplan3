package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ActiviteitTestSamples.*;
import static nl.ritense.demo.domain.KostenplaatsTestSamples.*;
import static nl.ritense.demo.domain.MuseumrelatieTestSamples.*;
import static nl.ritense.demo.domain.PlanTestSamples.*;
import static nl.ritense.demo.domain.ProgrammaTestSamples.*;
import static nl.ritense.demo.domain.ProgrammasoortTestSamples.*;
import static nl.ritense.demo.domain.RaadsstukTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProgrammaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Programma.class);
        Programma programma1 = getProgrammaSample1();
        Programma programma2 = new Programma();
        assertThat(programma1).isNotEqualTo(programma2);

        programma2.setId(programma1.getId());
        assertThat(programma1).isEqualTo(programma2);

        programma2 = getProgrammaSample2();
        assertThat(programma1).isNotEqualTo(programma2);
    }

    @Test
    void bestaatuitActiviteitTest() throws Exception {
        Programma programma = getProgrammaRandomSampleGenerator();
        Activiteit activiteitBack = getActiviteitRandomSampleGenerator();

        programma.addBestaatuitActiviteit(activiteitBack);
        assertThat(programma.getBestaatuitActiviteits()).containsOnly(activiteitBack);
        assertThat(activiteitBack.getBestaatuitProgramma()).isEqualTo(programma);

        programma.removeBestaatuitActiviteit(activiteitBack);
        assertThat(programma.getBestaatuitActiviteits()).doesNotContain(activiteitBack);
        assertThat(activiteitBack.getBestaatuitProgramma()).isNull();

        programma.bestaatuitActiviteits(new HashSet<>(Set.of(activiteitBack)));
        assertThat(programma.getBestaatuitActiviteits()).containsOnly(activiteitBack);
        assertThat(activiteitBack.getBestaatuitProgramma()).isEqualTo(programma);

        programma.setBestaatuitActiviteits(new HashSet<>());
        assertThat(programma.getBestaatuitActiviteits()).doesNotContain(activiteitBack);
        assertThat(activiteitBack.getBestaatuitProgramma()).isNull();
    }

    @Test
    void binnenprogrammaPlanTest() throws Exception {
        Programma programma = getProgrammaRandomSampleGenerator();
        Plan planBack = getPlanRandomSampleGenerator();

        programma.addBinnenprogrammaPlan(planBack);
        assertThat(programma.getBinnenprogrammaPlans()).containsOnly(planBack);
        assertThat(planBack.getBinnenprogrammaProgramma()).isEqualTo(programma);

        programma.removeBinnenprogrammaPlan(planBack);
        assertThat(programma.getBinnenprogrammaPlans()).doesNotContain(planBack);
        assertThat(planBack.getBinnenprogrammaProgramma()).isNull();

        programma.binnenprogrammaPlans(new HashSet<>(Set.of(planBack)));
        assertThat(programma.getBinnenprogrammaPlans()).containsOnly(planBack);
        assertThat(planBack.getBinnenprogrammaProgramma()).isEqualTo(programma);

        programma.setBinnenprogrammaPlans(new HashSet<>());
        assertThat(programma.getBinnenprogrammaPlans()).doesNotContain(planBack);
        assertThat(planBack.getBinnenprogrammaProgramma()).isNull();
    }

    @Test
    void heeftKostenplaatsTest() throws Exception {
        Programma programma = getProgrammaRandomSampleGenerator();
        Kostenplaats kostenplaatsBack = getKostenplaatsRandomSampleGenerator();

        programma.setHeeftKostenplaats(kostenplaatsBack);
        assertThat(programma.getHeeftKostenplaats()).isEqualTo(kostenplaatsBack);

        programma.heeftKostenplaats(null);
        assertThat(programma.getHeeftKostenplaats()).isNull();
    }

    @Test
    void voorProgrammasoortTest() throws Exception {
        Programma programma = getProgrammaRandomSampleGenerator();
        Programmasoort programmasoortBack = getProgrammasoortRandomSampleGenerator();

        programma.addVoorProgrammasoort(programmasoortBack);
        assertThat(programma.getVoorProgrammasoorts()).containsOnly(programmasoortBack);

        programma.removeVoorProgrammasoort(programmasoortBack);
        assertThat(programma.getVoorProgrammasoorts()).doesNotContain(programmasoortBack);

        programma.voorProgrammasoorts(new HashSet<>(Set.of(programmasoortBack)));
        assertThat(programma.getVoorProgrammasoorts()).containsOnly(programmasoortBack);

        programma.setVoorProgrammasoorts(new HashSet<>());
        assertThat(programma.getVoorProgrammasoorts()).doesNotContain(programmasoortBack);
    }

    @Test
    void voorMuseumrelatieTest() throws Exception {
        Programma programma = getProgrammaRandomSampleGenerator();
        Museumrelatie museumrelatieBack = getMuseumrelatieRandomSampleGenerator();

        programma.setVoorMuseumrelatie(museumrelatieBack);
        assertThat(programma.getVoorMuseumrelatie()).isEqualTo(museumrelatieBack);

        programma.voorMuseumrelatie(null);
        assertThat(programma.getVoorMuseumrelatie()).isNull();
    }

    @Test
    void hoortbijRaadsstukTest() throws Exception {
        Programma programma = getProgrammaRandomSampleGenerator();
        Raadsstuk raadsstukBack = getRaadsstukRandomSampleGenerator();

        programma.addHoortbijRaadsstuk(raadsstukBack);
        assertThat(programma.getHoortbijRaadsstuks()).containsOnly(raadsstukBack);
        assertThat(raadsstukBack.getHoortbijProgrammas()).containsOnly(programma);

        programma.removeHoortbijRaadsstuk(raadsstukBack);
        assertThat(programma.getHoortbijRaadsstuks()).doesNotContain(raadsstukBack);
        assertThat(raadsstukBack.getHoortbijProgrammas()).doesNotContain(programma);

        programma.hoortbijRaadsstuks(new HashSet<>(Set.of(raadsstukBack)));
        assertThat(programma.getHoortbijRaadsstuks()).containsOnly(raadsstukBack);
        assertThat(raadsstukBack.getHoortbijProgrammas()).containsOnly(programma);

        programma.setHoortbijRaadsstuks(new HashSet<>());
        assertThat(programma.getHoortbijRaadsstuks()).doesNotContain(raadsstukBack);
        assertThat(raadsstukBack.getHoortbijProgrammas()).doesNotContain(programma);
    }
}
