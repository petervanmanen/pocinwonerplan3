package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BegrotingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Begroting getBegrotingSample1() {
        return new Begroting().id(1L).naam("naam1").nummer("nummer1").omschrijving("omschrijving1");
    }

    public static Begroting getBegrotingSample2() {
        return new Begroting().id(2L).naam("naam2").nummer("nummer2").omschrijving("omschrijving2");
    }

    public static Begroting getBegrotingRandomSampleGenerator() {
        return new Begroting()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .nummer(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
