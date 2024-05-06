package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class GebiedsaanwijzingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Gebiedsaanwijzing getGebiedsaanwijzingSample1() {
        return new Gebiedsaanwijzing().id(1L).groep("groep1").naam("naam1").nen3610id("nen3610id1");
    }

    public static Gebiedsaanwijzing getGebiedsaanwijzingSample2() {
        return new Gebiedsaanwijzing().id(2L).groep("groep2").naam("naam2").nen3610id("nen3610id2");
    }

    public static Gebiedsaanwijzing getGebiedsaanwijzingRandomSampleGenerator() {
        return new Gebiedsaanwijzing()
            .id(longCount.incrementAndGet())
            .groep(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .nen3610id(UUID.randomUUID().toString());
    }
}
