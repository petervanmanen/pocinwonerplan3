package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StrooirouteuitvoeringTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Strooirouteuitvoering getStrooirouteuitvoeringSample1() {
        return new Strooirouteuitvoering()
            .id(1L)
            .geplandeinde("geplandeinde1")
            .geplandstart("geplandstart1")
            .eroute("eroute1")
            .werkelijkeinde("werkelijkeinde1")
            .werkelijkestart("werkelijkestart1");
    }

    public static Strooirouteuitvoering getStrooirouteuitvoeringSample2() {
        return new Strooirouteuitvoering()
            .id(2L)
            .geplandeinde("geplandeinde2")
            .geplandstart("geplandstart2")
            .eroute("eroute2")
            .werkelijkeinde("werkelijkeinde2")
            .werkelijkestart("werkelijkestart2");
    }

    public static Strooirouteuitvoering getStrooirouteuitvoeringRandomSampleGenerator() {
        return new Strooirouteuitvoering()
            .id(longCount.incrementAndGet())
            .geplandeinde(UUID.randomUUID().toString())
            .geplandstart(UUID.randomUUID().toString())
            .eroute(UUID.randomUUID().toString())
            .werkelijkeinde(UUID.randomUUID().toString())
            .werkelijkestart(UUID.randomUUID().toString());
    }
}
