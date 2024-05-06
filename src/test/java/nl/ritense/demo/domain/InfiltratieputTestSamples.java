package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class InfiltratieputTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Infiltratieput getInfiltratieputSample1() {
        return new Infiltratieput().id(1L).porositeit("porositeit1").risicogebied("risicogebied1");
    }

    public static Infiltratieput getInfiltratieputSample2() {
        return new Infiltratieput().id(2L).porositeit("porositeit2").risicogebied("risicogebied2");
    }

    public static Infiltratieput getInfiltratieputRandomSampleGenerator() {
        return new Infiltratieput()
            .id(longCount.incrementAndGet())
            .porositeit(UUID.randomUUID().toString())
            .risicogebied(UUID.randomUUID().toString());
    }
}
