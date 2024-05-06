package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ContainertypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Containertype getContainertypeSample1() {
        return new Containertype().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Containertype getContainertypeSample2() {
        return new Containertype().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Containertype getContainertypeRandomSampleGenerator() {
        return new Containertype()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
