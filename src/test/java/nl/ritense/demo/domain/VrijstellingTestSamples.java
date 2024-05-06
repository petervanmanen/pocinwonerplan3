package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VrijstellingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Vrijstelling getVrijstellingSample1() {
        return new Vrijstelling().id(1L).buitenlandseschoollocatie("buitenlandseschoollocatie1").verzuimsoort("verzuimsoort1");
    }

    public static Vrijstelling getVrijstellingSample2() {
        return new Vrijstelling().id(2L).buitenlandseschoollocatie("buitenlandseschoollocatie2").verzuimsoort("verzuimsoort2");
    }

    public static Vrijstelling getVrijstellingRandomSampleGenerator() {
        return new Vrijstelling()
            .id(longCount.incrementAndGet())
            .buitenlandseschoollocatie(UUID.randomUUID().toString())
            .verzuimsoort(UUID.randomUUID().toString());
    }
}
