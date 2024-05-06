package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ParkeerrechtTestSamples.*;
import static nl.ritense.demo.domain.ParkeervergunningTestSamples.*;
import static nl.ritense.demo.domain.ProductgroepTestSamples.*;
import static nl.ritense.demo.domain.ProductsoortTestSamples.*;
import static nl.ritense.demo.domain.RechtspersoonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ParkeervergunningTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Parkeervergunning.class);
        Parkeervergunning parkeervergunning1 = getParkeervergunningSample1();
        Parkeervergunning parkeervergunning2 = new Parkeervergunning();
        assertThat(parkeervergunning1).isNotEqualTo(parkeervergunning2);

        parkeervergunning2.setId(parkeervergunning1.getId());
        assertThat(parkeervergunning1).isEqualTo(parkeervergunning2);

        parkeervergunning2 = getParkeervergunningSample2();
        assertThat(parkeervergunning1).isNotEqualTo(parkeervergunning2);
    }

    @Test
    void resulteertParkeerrechtTest() throws Exception {
        Parkeervergunning parkeervergunning = getParkeervergunningRandomSampleGenerator();
        Parkeerrecht parkeerrechtBack = getParkeerrechtRandomSampleGenerator();

        parkeervergunning.setResulteertParkeerrecht(parkeerrechtBack);
        assertThat(parkeervergunning.getResulteertParkeerrecht()).isEqualTo(parkeerrechtBack);

        parkeervergunning.resulteertParkeerrecht(null);
        assertThat(parkeervergunning.getResulteertParkeerrecht()).isNull();
    }

    @Test
    void houderRechtspersoonTest() throws Exception {
        Parkeervergunning parkeervergunning = getParkeervergunningRandomSampleGenerator();
        Rechtspersoon rechtspersoonBack = getRechtspersoonRandomSampleGenerator();

        parkeervergunning.setHouderRechtspersoon(rechtspersoonBack);
        assertThat(parkeervergunning.getHouderRechtspersoon()).isEqualTo(rechtspersoonBack);

        parkeervergunning.houderRechtspersoon(null);
        assertThat(parkeervergunning.getHouderRechtspersoon()).isNull();
    }

    @Test
    void soortProductgroepTest() throws Exception {
        Parkeervergunning parkeervergunning = getParkeervergunningRandomSampleGenerator();
        Productgroep productgroepBack = getProductgroepRandomSampleGenerator();

        parkeervergunning.setSoortProductgroep(productgroepBack);
        assertThat(parkeervergunning.getSoortProductgroep()).isEqualTo(productgroepBack);

        parkeervergunning.soortProductgroep(null);
        assertThat(parkeervergunning.getSoortProductgroep()).isNull();
    }

    @Test
    void soortProductsoortTest() throws Exception {
        Parkeervergunning parkeervergunning = getParkeervergunningRandomSampleGenerator();
        Productsoort productsoortBack = getProductsoortRandomSampleGenerator();

        parkeervergunning.setSoortProductsoort(productsoortBack);
        assertThat(parkeervergunning.getSoortProductsoort()).isEqualTo(productsoortBack);

        parkeervergunning.soortProductsoort(null);
        assertThat(parkeervergunning.getSoortProductsoort()).isNull();
    }
}
