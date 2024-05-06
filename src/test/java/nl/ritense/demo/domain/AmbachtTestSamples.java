package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AmbachtTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Ambacht getAmbachtSample1() {
        return new Ambacht().id(1L).ambachtsoort("ambachtsoort1").jaarambachttot("jaarambachttot1").jaarambachtvanaf("jaarambachtvanaf1");
    }

    public static Ambacht getAmbachtSample2() {
        return new Ambacht().id(2L).ambachtsoort("ambachtsoort2").jaarambachttot("jaarambachttot2").jaarambachtvanaf("jaarambachtvanaf2");
    }

    public static Ambacht getAmbachtRandomSampleGenerator() {
        return new Ambacht()
            .id(longCount.incrementAndGet())
            .ambachtsoort(UUID.randomUUID().toString())
            .jaarambachttot(UUID.randomUUID().toString())
            .jaarambachtvanaf(UUID.randomUUID().toString());
    }
}
