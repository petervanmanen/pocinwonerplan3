package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class HotelbezoekTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Hotelbezoek getHotelbezoekSample1() {
        return new Hotelbezoek().id(1L);
    }

    public static Hotelbezoek getHotelbezoekSample2() {
        return new Hotelbezoek().id(2L);
    }

    public static Hotelbezoek getHotelbezoekRandomSampleGenerator() {
        return new Hotelbezoek().id(longCount.incrementAndGet());
    }
}
