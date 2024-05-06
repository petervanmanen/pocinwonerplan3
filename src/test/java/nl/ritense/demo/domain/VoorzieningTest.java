package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.LeveringTestSamples.*;
import static nl.ritense.demo.domain.ReserveringTestSamples.*;
import static nl.ritense.demo.domain.TariefTestSamples.*;
import static nl.ritense.demo.domain.VoorzieningTestSamples.*;
import static nl.ritense.demo.domain.VoorzieningsoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VoorzieningTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Voorziening.class);
        Voorziening voorziening1 = getVoorzieningSample1();
        Voorziening voorziening2 = new Voorziening();
        assertThat(voorziening1).isNotEqualTo(voorziening2);

        voorziening2.setId(voorziening1.getId());
        assertThat(voorziening1).isEqualTo(voorziening2);

        voorziening2 = getVoorzieningSample2();
        assertThat(voorziening1).isNotEqualTo(voorziening2);
    }

    @Test
    void heeftTariefTest() throws Exception {
        Voorziening voorziening = getVoorzieningRandomSampleGenerator();
        Tarief tariefBack = getTariefRandomSampleGenerator();

        voorziening.addHeeftTarief(tariefBack);
        assertThat(voorziening.getHeeftTariefs()).containsOnly(tariefBack);
        assertThat(tariefBack.getHeeftVoorziening()).isEqualTo(voorziening);

        voorziening.removeHeeftTarief(tariefBack);
        assertThat(voorziening.getHeeftTariefs()).doesNotContain(tariefBack);
        assertThat(tariefBack.getHeeftVoorziening()).isNull();

        voorziening.heeftTariefs(new HashSet<>(Set.of(tariefBack)));
        assertThat(voorziening.getHeeftTariefs()).containsOnly(tariefBack);
        assertThat(tariefBack.getHeeftVoorziening()).isEqualTo(voorziening);

        voorziening.setHeeftTariefs(new HashSet<>());
        assertThat(voorziening.getHeeftTariefs()).doesNotContain(tariefBack);
        assertThat(tariefBack.getHeeftVoorziening()).isNull();
    }

    @Test
    void valtbinnenVoorzieningsoortTest() throws Exception {
        Voorziening voorziening = getVoorzieningRandomSampleGenerator();
        Voorzieningsoort voorzieningsoortBack = getVoorzieningsoortRandomSampleGenerator();

        voorziening.setValtbinnenVoorzieningsoort(voorzieningsoortBack);
        assertThat(voorziening.getValtbinnenVoorzieningsoort()).isEqualTo(voorzieningsoortBack);

        voorziening.valtbinnenVoorzieningsoort(null);
        assertThat(voorziening.getValtbinnenVoorzieningsoort()).isNull();
    }

    @Test
    void betreftReserveringTest() throws Exception {
        Voorziening voorziening = getVoorzieningRandomSampleGenerator();
        Reservering reserveringBack = getReserveringRandomSampleGenerator();

        voorziening.addBetreftReservering(reserveringBack);
        assertThat(voorziening.getBetreftReserverings()).containsOnly(reserveringBack);
        assertThat(reserveringBack.getBetreftVoorziening()).isEqualTo(voorziening);

        voorziening.removeBetreftReservering(reserveringBack);
        assertThat(voorziening.getBetreftReserverings()).doesNotContain(reserveringBack);
        assertThat(reserveringBack.getBetreftVoorziening()).isNull();

        voorziening.betreftReserverings(new HashSet<>(Set.of(reserveringBack)));
        assertThat(voorziening.getBetreftReserverings()).containsOnly(reserveringBack);
        assertThat(reserveringBack.getBetreftVoorziening()).isEqualTo(voorziening);

        voorziening.setBetreftReserverings(new HashSet<>());
        assertThat(voorziening.getBetreftReserverings()).doesNotContain(reserveringBack);
        assertThat(reserveringBack.getBetreftVoorziening()).isNull();
    }

    @Test
    void voorzieningLeveringTest() throws Exception {
        Voorziening voorziening = getVoorzieningRandomSampleGenerator();
        Levering leveringBack = getLeveringRandomSampleGenerator();

        voorziening.addVoorzieningLevering(leveringBack);
        assertThat(voorziening.getVoorzieningLeverings()).containsOnly(leveringBack);
        assertThat(leveringBack.getVoorzieningVoorziening()).isEqualTo(voorziening);

        voorziening.removeVoorzieningLevering(leveringBack);
        assertThat(voorziening.getVoorzieningLeverings()).doesNotContain(leveringBack);
        assertThat(leveringBack.getVoorzieningVoorziening()).isNull();

        voorziening.voorzieningLeverings(new HashSet<>(Set.of(leveringBack)));
        assertThat(voorziening.getVoorzieningLeverings()).containsOnly(leveringBack);
        assertThat(leveringBack.getVoorzieningVoorziening()).isEqualTo(voorziening);

        voorziening.setVoorzieningLeverings(new HashSet<>());
        assertThat(voorziening.getVoorzieningLeverings()).doesNotContain(leveringBack);
        assertThat(leveringBack.getVoorzieningVoorziening()).isNull();
    }
}
