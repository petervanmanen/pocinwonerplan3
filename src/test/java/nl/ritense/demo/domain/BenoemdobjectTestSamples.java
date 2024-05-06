package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BenoemdobjectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Benoemdobject getBenoemdobjectSample1() {
        return new Benoemdobject().id(1L).geometriepunt("geometriepunt1").geometrievlak("geometrievlak1").identificatie("identificatie1");
    }

    public static Benoemdobject getBenoemdobjectSample2() {
        return new Benoemdobject().id(2L).geometriepunt("geometriepunt2").geometrievlak("geometrievlak2").identificatie("identificatie2");
    }

    public static Benoemdobject getBenoemdobjectRandomSampleGenerator() {
        return new Benoemdobject()
            .id(longCount.incrementAndGet())
            .geometriepunt(UUID.randomUUID().toString())
            .geometrievlak(UUID.randomUUID().toString())
            .identificatie(UUID.randomUUID().toString());
    }
}
