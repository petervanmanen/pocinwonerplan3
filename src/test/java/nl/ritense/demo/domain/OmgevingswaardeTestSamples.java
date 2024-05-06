package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OmgevingswaardeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Omgevingswaarde getOmgevingswaardeSample1() {
        return new Omgevingswaarde().id(1L).naam("naam1").omgevingswaardegroep("omgevingswaardegroep1");
    }

    public static Omgevingswaarde getOmgevingswaardeSample2() {
        return new Omgevingswaarde().id(2L).naam("naam2").omgevingswaardegroep("omgevingswaardegroep2");
    }

    public static Omgevingswaarde getOmgevingswaardeRandomSampleGenerator() {
        return new Omgevingswaarde()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .omgevingswaardegroep(UUID.randomUUID().toString());
    }
}
