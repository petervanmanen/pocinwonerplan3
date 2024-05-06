package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VideoopnameTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Videoopname getVideoopnameSample1() {
        return new Videoopname()
            .id(1L)
            .bestandsgrootte("bestandsgrootte1")
            .datumtijd("datumtijd1")
            .lengte("lengte1")
            .videoformaat("videoformaat1");
    }

    public static Videoopname getVideoopnameSample2() {
        return new Videoopname()
            .id(2L)
            .bestandsgrootte("bestandsgrootte2")
            .datumtijd("datumtijd2")
            .lengte("lengte2")
            .videoformaat("videoformaat2");
    }

    public static Videoopname getVideoopnameRandomSampleGenerator() {
        return new Videoopname()
            .id(longCount.incrementAndGet())
            .bestandsgrootte(UUID.randomUUID().toString())
            .datumtijd(UUID.randomUUID().toString())
            .lengte(UUID.randomUUID().toString())
            .videoformaat(UUID.randomUUID().toString());
    }
}
