package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OverigbenoemdterreinTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Overigbenoemdterrein getOverigbenoemdterreinSample1() {
        return new Overigbenoemdterrein()
            .id(1L)
            .gebruiksdoeloverigbenoemdterrein("gebruiksdoeloverigbenoemdterrein1")
            .overigbenoemdterreinidentificatie("overigbenoemdterreinidentificatie1");
    }

    public static Overigbenoemdterrein getOverigbenoemdterreinSample2() {
        return new Overigbenoemdterrein()
            .id(2L)
            .gebruiksdoeloverigbenoemdterrein("gebruiksdoeloverigbenoemdterrein2")
            .overigbenoemdterreinidentificatie("overigbenoemdterreinidentificatie2");
    }

    public static Overigbenoemdterrein getOverigbenoemdterreinRandomSampleGenerator() {
        return new Overigbenoemdterrein()
            .id(longCount.incrementAndGet())
            .gebruiksdoeloverigbenoemdterrein(UUID.randomUUID().toString())
            .overigbenoemdterreinidentificatie(UUID.randomUUID().toString());
    }
}
