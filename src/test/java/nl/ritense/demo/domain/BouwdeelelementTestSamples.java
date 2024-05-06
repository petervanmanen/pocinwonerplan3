package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BouwdeelelementTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Bouwdeelelement getBouwdeelelementSample1() {
        return new Bouwdeelelement().id(1L).code("code1").omschrijving("omschrijving1");
    }

    public static Bouwdeelelement getBouwdeelelementSample2() {
        return new Bouwdeelelement().id(2L).code("code2").omschrijving("omschrijving2");
    }

    public static Bouwdeelelement getBouwdeelelementRandomSampleGenerator() {
        return new Bouwdeelelement()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
