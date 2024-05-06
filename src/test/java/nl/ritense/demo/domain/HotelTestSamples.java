package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class HotelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Hotel getHotelSample1() {
        return new Hotel().id(1L).aantalkamers("aantalkamers1");
    }

    public static Hotel getHotelSample2() {
        return new Hotel().id(2L).aantalkamers("aantalkamers2");
    }

    public static Hotel getHotelRandomSampleGenerator() {
        return new Hotel().id(longCount.incrementAndGet()).aantalkamers(UUID.randomUUID().toString());
    }
}
