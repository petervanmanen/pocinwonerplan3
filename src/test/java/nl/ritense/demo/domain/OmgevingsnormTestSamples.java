package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OmgevingsnormTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Omgevingsnorm getOmgevingsnormSample1() {
        return new Omgevingsnorm().id(1L).naam("naam1").omgevingsnormgroep("omgevingsnormgroep1");
    }

    public static Omgevingsnorm getOmgevingsnormSample2() {
        return new Omgevingsnorm().id(2L).naam("naam2").omgevingsnormgroep("omgevingsnormgroep2");
    }

    public static Omgevingsnorm getOmgevingsnormRandomSampleGenerator() {
        return new Omgevingsnorm()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .omgevingsnormgroep(UUID.randomUUID().toString());
    }
}
