package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StraatsectieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Straatsectie getStraatsectieSample1() {
        return new Straatsectie().id(1L).code("code1").omschrijving("omschrijving1").zonecode("zonecode1");
    }

    public static Straatsectie getStraatsectieSample2() {
        return new Straatsectie().id(2L).code("code2").omschrijving("omschrijving2").zonecode("zonecode2");
    }

    public static Straatsectie getStraatsectieRandomSampleGenerator() {
        return new Straatsectie()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .zonecode(UUID.randomUUID().toString());
    }
}
