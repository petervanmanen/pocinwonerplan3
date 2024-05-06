package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OmgevingswaarderegelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Omgevingswaarderegel getOmgevingswaarderegelSample1() {
        return new Omgevingswaarderegel().id(1L).groep("groep1").naam("naam1");
    }

    public static Omgevingswaarderegel getOmgevingswaarderegelSample2() {
        return new Omgevingswaarderegel().id(2L).groep("groep2").naam("naam2");
    }

    public static Omgevingswaarderegel getOmgevingswaarderegelRandomSampleGenerator() {
        return new Omgevingswaarderegel()
            .id(longCount.incrementAndGet())
            .groep(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString());
    }
}
