package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class HaltverwijzingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Haltverwijzing getHaltverwijzingSample1() {
        return new Haltverwijzing().id(1L).afdoening("afdoening1").memo("memo1");
    }

    public static Haltverwijzing getHaltverwijzingSample2() {
        return new Haltverwijzing().id(2L).afdoening("afdoening2").memo("memo2");
    }

    public static Haltverwijzing getHaltverwijzingRandomSampleGenerator() {
        return new Haltverwijzing()
            .id(longCount.incrementAndGet())
            .afdoening(UUID.randomUUID().toString())
            .memo(UUID.randomUUID().toString());
    }
}
