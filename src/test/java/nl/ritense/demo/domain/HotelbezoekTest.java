package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.HotelTestSamples.*;
import static nl.ritense.demo.domain.HotelbezoekTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HotelbezoekTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hotelbezoek.class);
        Hotelbezoek hotelbezoek1 = getHotelbezoekSample1();
        Hotelbezoek hotelbezoek2 = new Hotelbezoek();
        assertThat(hotelbezoek1).isNotEqualTo(hotelbezoek2);

        hotelbezoek2.setId(hotelbezoek1.getId());
        assertThat(hotelbezoek1).isEqualTo(hotelbezoek2);

        hotelbezoek2 = getHotelbezoekSample2();
        assertThat(hotelbezoek1).isNotEqualTo(hotelbezoek2);
    }

    @Test
    void heeftHotelTest() throws Exception {
        Hotelbezoek hotelbezoek = getHotelbezoekRandomSampleGenerator();
        Hotel hotelBack = getHotelRandomSampleGenerator();

        hotelbezoek.setHeeftHotel(hotelBack);
        assertThat(hotelbezoek.getHeeftHotel()).isEqualTo(hotelBack);

        hotelbezoek.heeftHotel(null);
        assertThat(hotelbezoek.getHeeftHotel()).isNull();
    }
}
