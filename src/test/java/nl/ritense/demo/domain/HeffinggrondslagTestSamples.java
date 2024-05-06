package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class HeffinggrondslagTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Heffinggrondslag getHeffinggrondslagSample1() {
        return new Heffinggrondslag()
            .id(1L)
            .domein("domein1")
            .hoofdstuk("hoofdstuk1")
            .omschrijving("omschrijving1")
            .paragraaf("paragraaf1");
    }

    public static Heffinggrondslag getHeffinggrondslagSample2() {
        return new Heffinggrondslag()
            .id(2L)
            .domein("domein2")
            .hoofdstuk("hoofdstuk2")
            .omschrijving("omschrijving2")
            .paragraaf("paragraaf2");
    }

    public static Heffinggrondslag getHeffinggrondslagRandomSampleGenerator() {
        return new Heffinggrondslag()
            .id(longCount.incrementAndGet())
            .domein(UUID.randomUUID().toString())
            .hoofdstuk(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .paragraaf(UUID.randomUUID().toString());
    }
}
