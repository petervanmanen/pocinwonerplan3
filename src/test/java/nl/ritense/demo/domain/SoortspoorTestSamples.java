package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SoortspoorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Soortspoor getSoortspoorSample1() {
        return new Soortspoor().id(1L).functiespoor("functiespoor1").indicatieplusbrpopulatie("indicatieplusbrpopulatie1");
    }

    public static Soortspoor getSoortspoorSample2() {
        return new Soortspoor().id(2L).functiespoor("functiespoor2").indicatieplusbrpopulatie("indicatieplusbrpopulatie2");
    }

    public static Soortspoor getSoortspoorRandomSampleGenerator() {
        return new Soortspoor()
            .id(longCount.incrementAndGet())
            .functiespoor(UUID.randomUUID().toString())
            .indicatieplusbrpopulatie(UUID.randomUUID().toString());
    }
}
