package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DienstverbandTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Dienstverband getDienstverbandSample1() {
        return new Dienstverband().id(1L).periodiek("periodiek1").schaal("schaal1").urenperweek("urenperweek1");
    }

    public static Dienstverband getDienstverbandSample2() {
        return new Dienstverband().id(2L).periodiek("periodiek2").schaal("schaal2").urenperweek("urenperweek2");
    }

    public static Dienstverband getDienstverbandRandomSampleGenerator() {
        return new Dienstverband()
            .id(longCount.incrementAndGet())
            .periodiek(UUID.randomUUID().toString())
            .schaal(UUID.randomUUID().toString())
            .urenperweek(UUID.randomUUID().toString());
    }
}
