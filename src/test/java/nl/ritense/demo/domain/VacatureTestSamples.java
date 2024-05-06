package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VacatureTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Vacature getVacatureSample1() {
        return new Vacature().id(1L).datumopengesteld("datumopengesteld1");
    }

    public static Vacature getVacatureSample2() {
        return new Vacature().id(2L).datumopengesteld("datumopengesteld2");
    }

    public static Vacature getVacatureRandomSampleGenerator() {
        return new Vacature().id(longCount.incrementAndGet()).datumopengesteld(UUID.randomUUID().toString());
    }
}
