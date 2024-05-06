package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SubrekeningTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Subrekening getSubrekeningSample1() {
        return new Subrekening().id(1L).naam("naam1").nummer("nummer1").omschrijving("omschrijving1");
    }

    public static Subrekening getSubrekeningSample2() {
        return new Subrekening().id(2L).naam("naam2").nummer("nummer2").omschrijving("omschrijving2");
    }

    public static Subrekening getSubrekeningRandomSampleGenerator() {
        return new Subrekening()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .nummer(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
