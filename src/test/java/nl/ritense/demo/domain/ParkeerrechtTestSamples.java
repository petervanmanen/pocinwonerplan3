package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ParkeerrechtTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Parkeerrecht getParkeerrechtSample1() {
        return new Parkeerrecht()
            .id(1L)
            .aanmaaktijd("aanmaaktijd1")
            .datumtijdeinde("datumtijdeinde1")
            .datumtijdstart("datumtijdstart1")
            .productnaam("productnaam1")
            .productomschrijving("productomschrijving1");
    }

    public static Parkeerrecht getParkeerrechtSample2() {
        return new Parkeerrecht()
            .id(2L)
            .aanmaaktijd("aanmaaktijd2")
            .datumtijdeinde("datumtijdeinde2")
            .datumtijdstart("datumtijdstart2")
            .productnaam("productnaam2")
            .productomschrijving("productomschrijving2");
    }

    public static Parkeerrecht getParkeerrechtRandomSampleGenerator() {
        return new Parkeerrecht()
            .id(longCount.incrementAndGet())
            .aanmaaktijd(UUID.randomUUID().toString())
            .datumtijdeinde(UUID.randomUUID().toString())
            .datumtijdstart(UUID.randomUUID().toString())
            .productnaam(UUID.randomUUID().toString())
            .productomschrijving(UUID.randomUUID().toString());
    }
}
