package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProgrammasoortTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Programmasoort getProgrammasoortSample1() {
        return new Programmasoort().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Programmasoort getProgrammasoortSample2() {
        return new Programmasoort().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Programmasoort getProgrammasoortRandomSampleGenerator() {
        return new Programmasoort()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
