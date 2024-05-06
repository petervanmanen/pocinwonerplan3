package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BeslissingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Beslissing getBeslissingSample1() {
        return new Beslissing().id(1L).opmerkingen("opmerkingen1").reden("reden1");
    }

    public static Beslissing getBeslissingSample2() {
        return new Beslissing().id(2L).opmerkingen("opmerkingen2").reden("reden2");
    }

    public static Beslissing getBeslissingRandomSampleGenerator() {
        return new Beslissing()
            .id(longCount.incrementAndGet())
            .opmerkingen(UUID.randomUUID().toString())
            .reden(UUID.randomUUID().toString());
    }
}
