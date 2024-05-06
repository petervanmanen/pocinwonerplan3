package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DeclaratieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Declaratie getDeclaratieSample1() {
        return new Declaratie()
            .id(1L)
            .datumdeclaratie("datumdeclaratie1")
            .declaratiebedrag("declaratiebedrag1")
            .declaratiestatus("declaratiestatus1");
    }

    public static Declaratie getDeclaratieSample2() {
        return new Declaratie()
            .id(2L)
            .datumdeclaratie("datumdeclaratie2")
            .declaratiebedrag("declaratiebedrag2")
            .declaratiestatus("declaratiestatus2");
    }

    public static Declaratie getDeclaratieRandomSampleGenerator() {
        return new Declaratie()
            .id(longCount.incrementAndGet())
            .datumdeclaratie(UUID.randomUUID().toString())
            .declaratiebedrag(UUID.randomUUID().toString())
            .declaratiestatus(UUID.randomUUID().toString());
    }
}
