package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OpleidingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Opleiding getOpleidingSample1() {
        return new Opleiding().id(1L).instituut("instituut1").naam("naam1").omschrijving("omschrijving1").prijs("prijs1");
    }

    public static Opleiding getOpleidingSample2() {
        return new Opleiding().id(2L).instituut("instituut2").naam("naam2").omschrijving("omschrijving2").prijs("prijs2");
    }

    public static Opleiding getOpleidingRandomSampleGenerator() {
        return new Opleiding()
            .id(longCount.incrementAndGet())
            .instituut(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .prijs(UUID.randomUUID().toString());
    }
}
