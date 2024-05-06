package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UitvoeringsregelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Uitvoeringsregel getUitvoeringsregelSample1() {
        return new Uitvoeringsregel().id(1L).naam("naam1").omschrijving("omschrijving1").regel("regel1");
    }

    public static Uitvoeringsregel getUitvoeringsregelSample2() {
        return new Uitvoeringsregel().id(2L).naam("naam2").omschrijving("omschrijving2").regel("regel2");
    }

    public static Uitvoeringsregel getUitvoeringsregelRandomSampleGenerator() {
        return new Uitvoeringsregel()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .regel(UUID.randomUUID().toString());
    }
}
