package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.HotelTestSamples.*;
import static nl.ritense.demo.domain.HotelbezoekTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HotelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hotel.class);
        Hotel hotel1 = getHotelSample1();
        Hotel hotel2 = new Hotel();
        assertThat(hotel1).isNotEqualTo(hotel2);

        hotel2.setId(hotel1.getId());
        assertThat(hotel1).isEqualTo(hotel2);

        hotel2 = getHotelSample2();
        assertThat(hotel1).isNotEqualTo(hotel2);
    }

    @Test
    void heeftHotelbezoekTest() throws Exception {
        Hotel hotel = getHotelRandomSampleGenerator();
        Hotelbezoek hotelbezoekBack = getHotelbezoekRandomSampleGenerator();

        hotel.addHeeftHotelbezoek(hotelbezoekBack);
        assertThat(hotel.getHeeftHotelbezoeks()).containsOnly(hotelbezoekBack);
        assertThat(hotelbezoekBack.getHeeftHotel()).isEqualTo(hotel);

        hotel.removeHeeftHotelbezoek(hotelbezoekBack);
        assertThat(hotel.getHeeftHotelbezoeks()).doesNotContain(hotelbezoekBack);
        assertThat(hotelbezoekBack.getHeeftHotel()).isNull();

        hotel.heeftHotelbezoeks(new HashSet<>(Set.of(hotelbezoekBack)));
        assertThat(hotel.getHeeftHotelbezoeks()).containsOnly(hotelbezoekBack);
        assertThat(hotelbezoekBack.getHeeftHotel()).isEqualTo(hotel);

        hotel.setHeeftHotelbezoeks(new HashSet<>());
        assertThat(hotel.getHeeftHotelbezoeks()).doesNotContain(hotelbezoekBack);
        assertThat(hotelbezoekBack.getHeeftHotel()).isNull();
    }
}
