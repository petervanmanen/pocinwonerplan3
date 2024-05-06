package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OphaalmomentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Ophaalmoment getOphaalmomentSample1() {
        return new Ophaalmoment().id(1L).gewichtstoename("gewichtstoename1").tijdstip("tijdstip1");
    }

    public static Ophaalmoment getOphaalmomentSample2() {
        return new Ophaalmoment().id(2L).gewichtstoename("gewichtstoename2").tijdstip("tijdstip2");
    }

    public static Ophaalmoment getOphaalmomentRandomSampleGenerator() {
        return new Ophaalmoment()
            .id(longCount.incrementAndGet())
            .gewichtstoename(UUID.randomUUID().toString())
            .tijdstip(UUID.randomUUID().toString());
    }
}
