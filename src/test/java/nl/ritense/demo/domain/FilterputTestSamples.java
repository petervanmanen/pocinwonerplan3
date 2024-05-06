package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FilterputTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Filterput getFilterputSample1() {
        return new Filterput().id(1L).drain("drain1").risicogebied("risicogebied1");
    }

    public static Filterput getFilterputSample2() {
        return new Filterput().id(2L).drain("drain2").risicogebied("risicogebied2");
    }

    public static Filterput getFilterputRandomSampleGenerator() {
        return new Filterput()
            .id(longCount.incrementAndGet())
            .drain(UUID.randomUUID().toString())
            .risicogebied(UUID.randomUUID().toString());
    }
}
