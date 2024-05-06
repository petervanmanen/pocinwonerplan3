package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class NormprofielTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Normprofiel getNormprofielSample1() {
        return new Normprofiel().id(1L).code("code1").omschrijving("omschrijving1").schaal("schaal1");
    }

    public static Normprofiel getNormprofielSample2() {
        return new Normprofiel().id(2L).code("code2").omschrijving("omschrijving2").schaal("schaal2");
    }

    public static Normprofiel getNormprofielRandomSampleGenerator() {
        return new Normprofiel()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .schaal(UUID.randomUUID().toString());
    }
}
