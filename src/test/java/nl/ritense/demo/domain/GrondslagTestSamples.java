package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class GrondslagTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Grondslag getGrondslagSample1() {
        return new Grondslag().id(1L).code("code1").omschrijving("omschrijving1");
    }

    public static Grondslag getGrondslagSample2() {
        return new Grondslag().id(2L).code("code2").omschrijving("omschrijving2");
    }

    public static Grondslag getGrondslagRandomSampleGenerator() {
        return new Grondslag()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
