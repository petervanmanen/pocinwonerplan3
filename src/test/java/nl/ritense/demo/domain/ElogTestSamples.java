package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ElogTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Elog getElogSample1() {
        return new Elog().id(1L).korteomschrijving("korteomschrijving1").omschrijving("omschrijving1").tijd("tijd1");
    }

    public static Elog getElogSample2() {
        return new Elog().id(2L).korteomschrijving("korteomschrijving2").omschrijving("omschrijving2").tijd("tijd2");
    }

    public static Elog getElogRandomSampleGenerator() {
        return new Elog()
            .id(longCount.incrementAndGet())
            .korteomschrijving(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .tijd(UUID.randomUUID().toString());
    }
}
