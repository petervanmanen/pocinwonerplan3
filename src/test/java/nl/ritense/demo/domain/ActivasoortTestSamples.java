package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ActivasoortTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Activasoort getActivasoortSample1() {
        return new Activasoort().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Activasoort getActivasoortSample2() {
        return new Activasoort().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Activasoort getActivasoortRandomSampleGenerator() {
        return new Activasoort()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
