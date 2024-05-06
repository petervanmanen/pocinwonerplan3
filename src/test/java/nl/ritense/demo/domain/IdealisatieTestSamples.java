package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class IdealisatieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Idealisatie getIdealisatieSample1() {
        return new Idealisatie().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Idealisatie getIdealisatieSample2() {
        return new Idealisatie().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Idealisatie getIdealisatieRandomSampleGenerator() {
        return new Idealisatie()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
