package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SportlocatieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Sportlocatie getSportlocatieSample1() {
        return new Sportlocatie().id(1L).naam("naam1");
    }

    public static Sportlocatie getSportlocatieSample2() {
        return new Sportlocatie().id(2L).naam("naam2");
    }

    public static Sportlocatie getSportlocatieRandomSampleGenerator() {
        return new Sportlocatie().id(longCount.incrementAndGet()).naam(UUID.randomUUID().toString());
    }
}
