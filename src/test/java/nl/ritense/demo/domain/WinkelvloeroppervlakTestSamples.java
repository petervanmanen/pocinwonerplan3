package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WinkelvloeroppervlakTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Winkelvloeroppervlak getWinkelvloeroppervlakSample1() {
        return new Winkelvloeroppervlak()
            .id(1L)
            .aantalkassa("aantalkassa1")
            .bronwvo("bronwvo1")
            .leegstand("leegstand1")
            .winkelvloeroppervlakte("winkelvloeroppervlakte1")
            .wvoklasse("wvoklasse1");
    }

    public static Winkelvloeroppervlak getWinkelvloeroppervlakSample2() {
        return new Winkelvloeroppervlak()
            .id(2L)
            .aantalkassa("aantalkassa2")
            .bronwvo("bronwvo2")
            .leegstand("leegstand2")
            .winkelvloeroppervlakte("winkelvloeroppervlakte2")
            .wvoklasse("wvoklasse2");
    }

    public static Winkelvloeroppervlak getWinkelvloeroppervlakRandomSampleGenerator() {
        return new Winkelvloeroppervlak()
            .id(longCount.incrementAndGet())
            .aantalkassa(UUID.randomUUID().toString())
            .bronwvo(UUID.randomUUID().toString())
            .leegstand(UUID.randomUUID().toString())
            .winkelvloeroppervlakte(UUID.randomUUID().toString())
            .wvoklasse(UUID.randomUUID().toString());
    }
}
