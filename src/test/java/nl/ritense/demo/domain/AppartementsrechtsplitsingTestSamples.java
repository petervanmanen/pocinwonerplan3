package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AppartementsrechtsplitsingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Appartementsrechtsplitsing getAppartementsrechtsplitsingSample1() {
        return new Appartementsrechtsplitsing()
            .id(1L)
            .ddentificatieappartementsrechtsplitsing("ddentificatieappartementsrechtsplitsing1")
            .typesplitsing("typesplitsing1");
    }

    public static Appartementsrechtsplitsing getAppartementsrechtsplitsingSample2() {
        return new Appartementsrechtsplitsing()
            .id(2L)
            .ddentificatieappartementsrechtsplitsing("ddentificatieappartementsrechtsplitsing2")
            .typesplitsing("typesplitsing2");
    }

    public static Appartementsrechtsplitsing getAppartementsrechtsplitsingRandomSampleGenerator() {
        return new Appartementsrechtsplitsing()
            .id(longCount.incrementAndGet())
            .ddentificatieappartementsrechtsplitsing(UUID.randomUUID().toString())
            .typesplitsing(UUID.randomUUID().toString());
    }
}
