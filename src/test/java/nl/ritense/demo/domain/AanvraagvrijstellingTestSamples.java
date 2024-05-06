package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AanvraagvrijstellingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Aanvraagvrijstelling getAanvraagvrijstellingSample1() {
        return new Aanvraagvrijstelling().id(1L).buitenlandseschoollocatie("buitenlandseschoollocatie1");
    }

    public static Aanvraagvrijstelling getAanvraagvrijstellingSample2() {
        return new Aanvraagvrijstelling().id(2L).buitenlandseschoollocatie("buitenlandseschoollocatie2");
    }

    public static Aanvraagvrijstelling getAanvraagvrijstellingRandomSampleGenerator() {
        return new Aanvraagvrijstelling().id(longCount.incrementAndGet()).buitenlandseschoollocatie(UUID.randomUUID().toString());
    }
}
