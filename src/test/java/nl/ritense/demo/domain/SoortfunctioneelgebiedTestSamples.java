package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SoortfunctioneelgebiedTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Soortfunctioneelgebied getSoortfunctioneelgebiedSample1() {
        return new Soortfunctioneelgebied()
            .id(1L)
            .indicatieplusbrpopulatie("indicatieplusbrpopulatie1")
            .typefunctioneelgebied("typefunctioneelgebied1");
    }

    public static Soortfunctioneelgebied getSoortfunctioneelgebiedSample2() {
        return new Soortfunctioneelgebied()
            .id(2L)
            .indicatieplusbrpopulatie("indicatieplusbrpopulatie2")
            .typefunctioneelgebied("typefunctioneelgebied2");
    }

    public static Soortfunctioneelgebied getSoortfunctioneelgebiedRandomSampleGenerator() {
        return new Soortfunctioneelgebied()
            .id(longCount.incrementAndGet())
            .indicatieplusbrpopulatie(UUID.randomUUID().toString())
            .typefunctioneelgebied(UUID.randomUUID().toString());
    }
}
