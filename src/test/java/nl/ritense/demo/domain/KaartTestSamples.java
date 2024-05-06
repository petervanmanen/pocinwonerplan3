package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class KaartTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Kaart getKaartSample1() {
        return new Kaart().id(1L).kaart("kaart1").naam("naam1").omschrijving("omschrijving1");
    }

    public static Kaart getKaartSample2() {
        return new Kaart().id(2L).kaart("kaart2").naam("naam2").omschrijving("omschrijving2");
    }

    public static Kaart getKaartRandomSampleGenerator() {
        return new Kaart()
            .id(longCount.incrementAndGet())
            .kaart(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
