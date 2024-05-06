package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class GunningTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Gunning getGunningSample1() {
        return new Gunning()
            .id(1L)
            .bericht("bericht1")
            .datumgunning("datumgunning1")
            .datumpublicatie("datumpublicatie1")
            .datumvoorlopigegunning("datumvoorlopigegunning1")
            .gegundeprijs("gegundeprijs1");
    }

    public static Gunning getGunningSample2() {
        return new Gunning()
            .id(2L)
            .bericht("bericht2")
            .datumgunning("datumgunning2")
            .datumpublicatie("datumpublicatie2")
            .datumvoorlopigegunning("datumvoorlopigegunning2")
            .gegundeprijs("gegundeprijs2");
    }

    public static Gunning getGunningRandomSampleGenerator() {
        return new Gunning()
            .id(longCount.incrementAndGet())
            .bericht(UUID.randomUUID().toString())
            .datumgunning(UUID.randomUUID().toString())
            .datumpublicatie(UUID.randomUUID().toString())
            .datumvoorlopigegunning(UUID.randomUUID().toString())
            .gegundeprijs(UUID.randomUUID().toString());
    }
}
