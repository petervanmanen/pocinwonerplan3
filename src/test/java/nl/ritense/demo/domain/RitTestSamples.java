package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RitTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Rit getRitSample1() {
        return new Rit().id(1L).eindtijd("eindtijd1").ritcode("ritcode1").starttijd("starttijd1");
    }

    public static Rit getRitSample2() {
        return new Rit().id(2L).eindtijd("eindtijd2").ritcode("ritcode2").starttijd("starttijd2");
    }

    public static Rit getRitRandomSampleGenerator() {
        return new Rit()
            .id(longCount.incrementAndGet())
            .eindtijd(UUID.randomUUID().toString())
            .ritcode(UUID.randomUUID().toString())
            .starttijd(UUID.randomUUID().toString());
    }
}
