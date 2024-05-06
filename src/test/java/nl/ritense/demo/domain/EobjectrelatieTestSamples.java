package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EobjectrelatieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Eobjectrelatie getEobjectrelatieSample1() {
        return new Eobjectrelatie().id(1L).rol("rol1");
    }

    public static Eobjectrelatie getEobjectrelatieSample2() {
        return new Eobjectrelatie().id(2L).rol("rol2");
    }

    public static Eobjectrelatie getEobjectrelatieRandomSampleGenerator() {
        return new Eobjectrelatie().id(longCount.incrementAndGet()).rol(UUID.randomUUID().toString());
    }
}
