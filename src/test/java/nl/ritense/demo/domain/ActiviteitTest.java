package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ActiviteitTestSamples.*;
import static nl.ritense.demo.domain.ActiviteitTestSamples.*;
import static nl.ritense.demo.domain.ActiviteitsoortTestSamples.*;
import static nl.ritense.demo.domain.LocatieTestSamples.*;
import static nl.ritense.demo.domain.ProgrammaTestSamples.*;
import static nl.ritense.demo.domain.ReserveringTestSamples.*;
import static nl.ritense.demo.domain.RondleidingTestSamples.*;
import static nl.ritense.demo.domain.VerzoekTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ActiviteitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Activiteit.class);
        Activiteit activiteit1 = getActiviteitSample1();
        Activiteit activiteit2 = new Activiteit();
        assertThat(activiteit1).isNotEqualTo(activiteit2);

        activiteit2.setId(activiteit1.getId());
        assertThat(activiteit1).isEqualTo(activiteit2);

        activiteit2 = getActiviteitSample2();
        assertThat(activiteit1).isNotEqualTo(activiteit2);
    }

    @Test
    void gerelateerdeactiviteitActiviteitTest() throws Exception {
        Activiteit activiteit = getActiviteitRandomSampleGenerator();
        Activiteit activiteitBack = getActiviteitRandomSampleGenerator();

        activiteit.setGerelateerdeactiviteitActiviteit(activiteitBack);
        assertThat(activiteit.getGerelateerdeactiviteitActiviteit()).isEqualTo(activiteitBack);

        activiteit.gerelateerdeactiviteitActiviteit(null);
        assertThat(activiteit.getGerelateerdeactiviteitActiviteit()).isNull();
    }

    @Test
    void bovenliggendeactiviteitActiviteitTest() throws Exception {
        Activiteit activiteit = getActiviteitRandomSampleGenerator();
        Activiteit activiteitBack = getActiviteitRandomSampleGenerator();

        activiteit.setBovenliggendeactiviteitActiviteit(activiteitBack);
        assertThat(activiteit.getBovenliggendeactiviteitActiviteit()).isEqualTo(activiteitBack);

        activiteit.bovenliggendeactiviteitActiviteit(null);
        assertThat(activiteit.getBovenliggendeactiviteitActiviteit()).isNull();
    }

    @Test
    void bestaatuitActiviteitTest() throws Exception {
        Activiteit activiteit = getActiviteitRandomSampleGenerator();
        Activiteit activiteitBack = getActiviteitRandomSampleGenerator();

        activiteit.addBestaatuitActiviteit(activiteitBack);
        assertThat(activiteit.getBestaatuitActiviteits()).containsOnly(activiteitBack);
        assertThat(activiteitBack.getBestaatuitActiviteit2()).isEqualTo(activiteit);

        activiteit.removeBestaatuitActiviteit(activiteitBack);
        assertThat(activiteit.getBestaatuitActiviteits()).doesNotContain(activiteitBack);
        assertThat(activiteitBack.getBestaatuitActiviteit2()).isNull();

        activiteit.bestaatuitActiviteits(new HashSet<>(Set.of(activiteitBack)));
        assertThat(activiteit.getBestaatuitActiviteits()).containsOnly(activiteitBack);
        assertThat(activiteitBack.getBestaatuitActiviteit2()).isEqualTo(activiteit);

        activiteit.setBestaatuitActiviteits(new HashSet<>());
        assertThat(activiteit.getBestaatuitActiviteits()).doesNotContain(activiteitBack);
        assertThat(activiteitBack.getBestaatuitActiviteit2()).isNull();
    }

    @Test
    void heeftReserveringTest() throws Exception {
        Activiteit activiteit = getActiviteitRandomSampleGenerator();
        Reservering reserveringBack = getReserveringRandomSampleGenerator();

        activiteit.addHeeftReservering(reserveringBack);
        assertThat(activiteit.getHeeftReserverings()).containsOnly(reserveringBack);
        assertThat(reserveringBack.getHeeftActiviteit()).isEqualTo(activiteit);

        activiteit.removeHeeftReservering(reserveringBack);
        assertThat(activiteit.getHeeftReserverings()).doesNotContain(reserveringBack);
        assertThat(reserveringBack.getHeeftActiviteit()).isNull();

        activiteit.heeftReserverings(new HashSet<>(Set.of(reserveringBack)));
        assertThat(activiteit.getHeeftReserverings()).containsOnly(reserveringBack);
        assertThat(reserveringBack.getHeeftActiviteit()).isEqualTo(activiteit);

        activiteit.setHeeftReserverings(new HashSet<>());
        assertThat(activiteit.getHeeftReserverings()).doesNotContain(reserveringBack);
        assertThat(reserveringBack.getHeeftActiviteit()).isNull();
    }

    @Test
    void heeftRondleidingTest() throws Exception {
        Activiteit activiteit = getActiviteitRandomSampleGenerator();
        Rondleiding rondleidingBack = getRondleidingRandomSampleGenerator();

        activiteit.setHeeftRondleiding(rondleidingBack);
        assertThat(activiteit.getHeeftRondleiding()).isEqualTo(rondleidingBack);

        activiteit.heeftRondleiding(null);
        assertThat(activiteit.getHeeftRondleiding()).isNull();
    }

    @Test
    void vansoortActiviteitsoortTest() throws Exception {
        Activiteit activiteit = getActiviteitRandomSampleGenerator();
        Activiteitsoort activiteitsoortBack = getActiviteitsoortRandomSampleGenerator();

        activiteit.setVansoortActiviteitsoort(activiteitsoortBack);
        assertThat(activiteit.getVansoortActiviteitsoort()).isEqualTo(activiteitsoortBack);

        activiteit.vansoortActiviteitsoort(null);
        assertThat(activiteit.getVansoortActiviteitsoort()).isNull();
    }

    @Test
    void isverbondenmetLocatieTest() throws Exception {
        Activiteit activiteit = getActiviteitRandomSampleGenerator();
        Locatie locatieBack = getLocatieRandomSampleGenerator();

        activiteit.addIsverbondenmetLocatie(locatieBack);
        assertThat(activiteit.getIsverbondenmetLocaties()).containsOnly(locatieBack);

        activiteit.removeIsverbondenmetLocatie(locatieBack);
        assertThat(activiteit.getIsverbondenmetLocaties()).doesNotContain(locatieBack);

        activiteit.isverbondenmetLocaties(new HashSet<>(Set.of(locatieBack)));
        assertThat(activiteit.getIsverbondenmetLocaties()).containsOnly(locatieBack);

        activiteit.setIsverbondenmetLocaties(new HashSet<>());
        assertThat(activiteit.getIsverbondenmetLocaties()).doesNotContain(locatieBack);
    }

    @Test
    void gerelateerdeactiviteitActiviteit2Test() throws Exception {
        Activiteit activiteit = getActiviteitRandomSampleGenerator();
        Activiteit activiteitBack = getActiviteitRandomSampleGenerator();

        activiteit.setGerelateerdeactiviteitActiviteit2(activiteitBack);
        assertThat(activiteit.getGerelateerdeactiviteitActiviteit2()).isEqualTo(activiteitBack);
        assertThat(activiteitBack.getGerelateerdeactiviteitActiviteit()).isEqualTo(activiteit);

        activiteit.gerelateerdeactiviteitActiviteit2(null);
        assertThat(activiteit.getGerelateerdeactiviteitActiviteit2()).isNull();
        assertThat(activiteitBack.getGerelateerdeactiviteitActiviteit()).isNull();
    }

    @Test
    void bovenliggendeactiviteitActiviteit2Test() throws Exception {
        Activiteit activiteit = getActiviteitRandomSampleGenerator();
        Activiteit activiteitBack = getActiviteitRandomSampleGenerator();

        activiteit.setBovenliggendeactiviteitActiviteit2(activiteitBack);
        assertThat(activiteit.getBovenliggendeactiviteitActiviteit2()).isEqualTo(activiteitBack);
        assertThat(activiteitBack.getBovenliggendeactiviteitActiviteit()).isEqualTo(activiteit);

        activiteit.bovenliggendeactiviteitActiviteit2(null);
        assertThat(activiteit.getBovenliggendeactiviteitActiviteit2()).isNull();
        assertThat(activiteitBack.getBovenliggendeactiviteitActiviteit()).isNull();
    }

    @Test
    void bestaatuitActiviteit2Test() throws Exception {
        Activiteit activiteit = getActiviteitRandomSampleGenerator();
        Activiteit activiteitBack = getActiviteitRandomSampleGenerator();

        activiteit.setBestaatuitActiviteit2(activiteitBack);
        assertThat(activiteit.getBestaatuitActiviteit2()).isEqualTo(activiteitBack);

        activiteit.bestaatuitActiviteit2(null);
        assertThat(activiteit.getBestaatuitActiviteit2()).isNull();
    }

    @Test
    void bestaatuitProgrammaTest() throws Exception {
        Activiteit activiteit = getActiviteitRandomSampleGenerator();
        Programma programmaBack = getProgrammaRandomSampleGenerator();

        activiteit.setBestaatuitProgramma(programmaBack);
        assertThat(activiteit.getBestaatuitProgramma()).isEqualTo(programmaBack);

        activiteit.bestaatuitProgramma(null);
        assertThat(activiteit.getBestaatuitProgramma()).isNull();
    }

    @Test
    void betreftVerzoekTest() throws Exception {
        Activiteit activiteit = getActiviteitRandomSampleGenerator();
        Verzoek verzoekBack = getVerzoekRandomSampleGenerator();

        activiteit.addBetreftVerzoek(verzoekBack);
        assertThat(activiteit.getBetreftVerzoeks()).containsOnly(verzoekBack);
        assertThat(verzoekBack.getBetreftActiviteits()).containsOnly(activiteit);

        activiteit.removeBetreftVerzoek(verzoekBack);
        assertThat(activiteit.getBetreftVerzoeks()).doesNotContain(verzoekBack);
        assertThat(verzoekBack.getBetreftActiviteits()).doesNotContain(activiteit);

        activiteit.betreftVerzoeks(new HashSet<>(Set.of(verzoekBack)));
        assertThat(activiteit.getBetreftVerzoeks()).containsOnly(verzoekBack);
        assertThat(verzoekBack.getBetreftActiviteits()).containsOnly(activiteit);

        activiteit.setBetreftVerzoeks(new HashSet<>());
        assertThat(activiteit.getBetreftVerzoeks()).doesNotContain(verzoekBack);
        assertThat(verzoekBack.getBetreftActiviteits()).doesNotContain(activiteit);
    }
}
