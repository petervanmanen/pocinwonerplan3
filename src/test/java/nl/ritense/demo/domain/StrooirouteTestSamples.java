package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StrooirouteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Strooiroute getStrooirouteSample1() {
        return new Strooiroute().id(1L).eroute("eroute1");
    }

    public static Strooiroute getStrooirouteSample2() {
        return new Strooiroute().id(2L).eroute("eroute2");
    }

    public static Strooiroute getStrooirouteRandomSampleGenerator() {
        return new Strooiroute().id(longCount.incrementAndGet()).eroute(UUID.randomUUID().toString());
    }
}
