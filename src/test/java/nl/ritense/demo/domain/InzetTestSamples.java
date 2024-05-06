package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class InzetTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Inzet getInzetSample1() {
        return new Inzet().id(1L).percentage("percentage1").uren("uren1");
    }

    public static Inzet getInzetSample2() {
        return new Inzet().id(2L).percentage("percentage2").uren("uren2");
    }

    public static Inzet getInzetRandomSampleGenerator() {
        return new Inzet().id(longCount.incrementAndGet()).percentage(UUID.randomUUID().toString()).uren(UUID.randomUUID().toString());
    }
}
