package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ContactTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Contact getContactSample1() {
        return new Contact().id(1L).contactsoort("contactsoort1").tekst("tekst1");
    }

    public static Contact getContactSample2() {
        return new Contact().id(2L).contactsoort("contactsoort2").tekst("tekst2");
    }

    public static Contact getContactRandomSampleGenerator() {
        return new Contact().id(longCount.incrementAndGet()).contactsoort(UUID.randomUUID().toString()).tekst(UUID.randomUUID().toString());
    }
}
