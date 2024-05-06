package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class NotitieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Notitie getNotitieSample1() {
        return new Notitie().id(1L).inhoud("inhoud1");
    }

    public static Notitie getNotitieSample2() {
        return new Notitie().id(2L).inhoud("inhoud2");
    }

    public static Notitie getNotitieRandomSampleGenerator() {
        return new Notitie().id(longCount.incrementAndGet()).inhoud(UUID.randomUUID().toString());
    }
}
