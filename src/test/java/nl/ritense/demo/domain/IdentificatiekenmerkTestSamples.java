package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class IdentificatiekenmerkTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Identificatiekenmerk getIdentificatiekenmerkSample1() {
        return new Identificatiekenmerk().id(1L).kenmerk("kenmerk1");
    }

    public static Identificatiekenmerk getIdentificatiekenmerkSample2() {
        return new Identificatiekenmerk().id(2L).kenmerk("kenmerk2");
    }

    public static Identificatiekenmerk getIdentificatiekenmerkRandomSampleGenerator() {
        return new Identificatiekenmerk().id(longCount.incrementAndGet()).kenmerk(UUID.randomUUID().toString());
    }
}
