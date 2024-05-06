package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RegelingsoortTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Regelingsoort getRegelingsoortSample1() {
        return new Regelingsoort().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Regelingsoort getRegelingsoortSample2() {
        return new Regelingsoort().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Regelingsoort getRegelingsoortRandomSampleGenerator() {
        return new Regelingsoort()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
