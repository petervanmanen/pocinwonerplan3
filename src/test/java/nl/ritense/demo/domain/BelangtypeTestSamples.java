package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BelangtypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Belangtype getBelangtypeSample1() {
        return new Belangtype().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Belangtype getBelangtypeSample2() {
        return new Belangtype().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Belangtype getBelangtypeRandomSampleGenerator() {
        return new Belangtype()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
