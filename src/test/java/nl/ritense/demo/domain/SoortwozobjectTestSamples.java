package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SoortwozobjectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Soortwozobject getSoortwozobjectSample1() {
        return new Soortwozobject()
            .id(1L)
            .naamsoortobjectcode("naamsoortobjectcode1")
            .opmerkingensoortobjectcode("opmerkingensoortobjectcode1")
            .soortobjectcode("soortobjectcode1");
    }

    public static Soortwozobject getSoortwozobjectSample2() {
        return new Soortwozobject()
            .id(2L)
            .naamsoortobjectcode("naamsoortobjectcode2")
            .opmerkingensoortobjectcode("opmerkingensoortobjectcode2")
            .soortobjectcode("soortobjectcode2");
    }

    public static Soortwozobject getSoortwozobjectRandomSampleGenerator() {
        return new Soortwozobject()
            .id(longCount.incrementAndGet())
            .naamsoortobjectcode(UUID.randomUUID().toString())
            .opmerkingensoortobjectcode(UUID.randomUUID().toString())
            .soortobjectcode(UUID.randomUUID().toString());
    }
}
