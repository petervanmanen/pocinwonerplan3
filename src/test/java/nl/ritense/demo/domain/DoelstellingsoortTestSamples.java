package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DoelstellingsoortTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Doelstellingsoort getDoelstellingsoortSample1() {
        return new Doelstellingsoort().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Doelstellingsoort getDoelstellingsoortSample2() {
        return new Doelstellingsoort().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Doelstellingsoort getDoelstellingsoortRandomSampleGenerator() {
        return new Doelstellingsoort()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
