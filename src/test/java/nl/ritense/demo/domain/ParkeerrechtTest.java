package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BelproviderTestSamples.*;
import static nl.ritense.demo.domain.ParkeerrechtTestSamples.*;
import static nl.ritense.demo.domain.ParkeervergunningTestSamples.*;
import static nl.ritense.demo.domain.VoertuigTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ParkeerrechtTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Parkeerrecht.class);
        Parkeerrecht parkeerrecht1 = getParkeerrechtSample1();
        Parkeerrecht parkeerrecht2 = new Parkeerrecht();
        assertThat(parkeerrecht1).isNotEqualTo(parkeerrecht2);

        parkeerrecht2.setId(parkeerrecht1.getId());
        assertThat(parkeerrecht1).isEqualTo(parkeerrecht2);

        parkeerrecht2 = getParkeerrechtSample2();
        assertThat(parkeerrecht1).isNotEqualTo(parkeerrecht2);
    }

    @Test
    void leverancierBelproviderTest() throws Exception {
        Parkeerrecht parkeerrecht = getParkeerrechtRandomSampleGenerator();
        Belprovider belproviderBack = getBelproviderRandomSampleGenerator();

        parkeerrecht.setLeverancierBelprovider(belproviderBack);
        assertThat(parkeerrecht.getLeverancierBelprovider()).isEqualTo(belproviderBack);

        parkeerrecht.leverancierBelprovider(null);
        assertThat(parkeerrecht.getLeverancierBelprovider()).isNull();
    }

    @Test
    void betreftVoertuigTest() throws Exception {
        Parkeerrecht parkeerrecht = getParkeerrechtRandomSampleGenerator();
        Voertuig voertuigBack = getVoertuigRandomSampleGenerator();

        parkeerrecht.setBetreftVoertuig(voertuigBack);
        assertThat(parkeerrecht.getBetreftVoertuig()).isEqualTo(voertuigBack);

        parkeerrecht.betreftVoertuig(null);
        assertThat(parkeerrecht.getBetreftVoertuig()).isNull();
    }

    @Test
    void resulteertParkeervergunningTest() throws Exception {
        Parkeerrecht parkeerrecht = getParkeerrechtRandomSampleGenerator();
        Parkeervergunning parkeervergunningBack = getParkeervergunningRandomSampleGenerator();

        parkeerrecht.setResulteertParkeervergunning(parkeervergunningBack);
        assertThat(parkeerrecht.getResulteertParkeervergunning()).isEqualTo(parkeervergunningBack);
        assertThat(parkeervergunningBack.getResulteertParkeerrecht()).isEqualTo(parkeerrecht);

        parkeerrecht.resulteertParkeervergunning(null);
        assertThat(parkeerrecht.getResulteertParkeervergunning()).isNull();
        assertThat(parkeervergunningBack.getResulteertParkeerrecht()).isNull();
    }
}
