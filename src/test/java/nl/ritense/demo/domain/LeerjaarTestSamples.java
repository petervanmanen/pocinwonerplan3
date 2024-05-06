package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LeerjaarTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Leerjaar getLeerjaarSample1() {
        return new Leerjaar().id(1L).jaareinde("jaareinde1").jaarstart("jaarstart1");
    }

    public static Leerjaar getLeerjaarSample2() {
        return new Leerjaar().id(2L).jaareinde("jaareinde2").jaarstart("jaarstart2");
    }

    public static Leerjaar getLeerjaarRandomSampleGenerator() {
        return new Leerjaar()
            .id(longCount.incrementAndGet())
            .jaareinde(UUID.randomUUID().toString())
            .jaarstart(UUID.randomUUID().toString());
    }
}
