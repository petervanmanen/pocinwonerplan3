package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RedenbeeindigingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Redenbeeindiging getRedenbeeindigingSample1() {
        return new Redenbeeindiging().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Redenbeeindiging getRedenbeeindigingSample2() {
        return new Redenbeeindiging().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Redenbeeindiging getRedenbeeindigingRandomSampleGenerator() {
        return new Redenbeeindiging()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
