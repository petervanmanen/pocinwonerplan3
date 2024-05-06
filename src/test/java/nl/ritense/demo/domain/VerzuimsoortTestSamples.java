package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VerzuimsoortTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Verzuimsoort getVerzuimsoortSample1() {
        return new Verzuimsoort().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Verzuimsoort getVerzuimsoortSample2() {
        return new Verzuimsoort().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Verzuimsoort getVerzuimsoortRandomSampleGenerator() {
        return new Verzuimsoort()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
