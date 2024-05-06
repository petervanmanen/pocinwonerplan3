package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ActiviteitTestSamples.*;
import static nl.ritense.demo.domain.ReserveringTestSamples.*;
import static nl.ritense.demo.domain.VoorzieningTestSamples.*;
import static nl.ritense.demo.domain.ZaalTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReserveringTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reservering.class);
        Reservering reservering1 = getReserveringSample1();
        Reservering reservering2 = new Reservering();
        assertThat(reservering1).isNotEqualTo(reservering2);

        reservering2.setId(reservering1.getId());
        assertThat(reservering1).isEqualTo(reservering2);

        reservering2 = getReserveringSample2();
        assertThat(reservering1).isNotEqualTo(reservering2);
    }

    @Test
    void betreftVoorzieningTest() throws Exception {
        Reservering reservering = getReserveringRandomSampleGenerator();
        Voorziening voorzieningBack = getVoorzieningRandomSampleGenerator();

        reservering.setBetreftVoorziening(voorzieningBack);
        assertThat(reservering.getBetreftVoorziening()).isEqualTo(voorzieningBack);

        reservering.betreftVoorziening(null);
        assertThat(reservering.getBetreftVoorziening()).isNull();
    }

    @Test
    void betreftZaalTest() throws Exception {
        Reservering reservering = getReserveringRandomSampleGenerator();
        Zaal zaalBack = getZaalRandomSampleGenerator();

        reservering.setBetreftZaal(zaalBack);
        assertThat(reservering.getBetreftZaal()).isEqualTo(zaalBack);

        reservering.betreftZaal(null);
        assertThat(reservering.getBetreftZaal()).isNull();
    }

    @Test
    void heeftActiviteitTest() throws Exception {
        Reservering reservering = getReserveringRandomSampleGenerator();
        Activiteit activiteitBack = getActiviteitRandomSampleGenerator();

        reservering.setHeeftActiviteit(activiteitBack);
        assertThat(reservering.getHeeftActiviteit()).isEqualTo(activiteitBack);

        reservering.heeftActiviteit(null);
        assertThat(reservering.getHeeftActiviteit()).isNull();
    }
}
