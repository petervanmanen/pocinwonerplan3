package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DeclaratiesoortTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Declaratiesoort getDeclaratiesoortSample1() {
        return new Declaratiesoort().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Declaratiesoort getDeclaratiesoortSample2() {
        return new Declaratiesoort().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Declaratiesoort getDeclaratiesoortRandomSampleGenerator() {
        return new Declaratiesoort()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
