package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StrijdigheidofnietigheidTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Strijdigheidofnietigheid getStrijdigheidofnietigheidSample1() {
        return new Strijdigheidofnietigheid().id(1L).aanduidingstrijdigheidnietigheid("aanduidingstrijdigheidnietigheid1");
    }

    public static Strijdigheidofnietigheid getStrijdigheidofnietigheidSample2() {
        return new Strijdigheidofnietigheid().id(2L).aanduidingstrijdigheidnietigheid("aanduidingstrijdigheidnietigheid2");
    }

    public static Strijdigheidofnietigheid getStrijdigheidofnietigheidRandomSampleGenerator() {
        return new Strijdigheidofnietigheid()
            .id(longCount.incrementAndGet())
            .aanduidingstrijdigheidnietigheid(UUID.randomUUID().toString());
    }
}
