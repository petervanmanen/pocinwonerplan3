package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BouwactiviteitTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Bouwactiviteit getBouwactiviteitSample1() {
        return new Bouwactiviteit()
            .id(1L)
            .bouwjaarklasse("bouwjaarklasse1")
            .bouwjaartot("bouwjaartot1")
            .bouwjaarvan("bouwjaarvan1")
            .indicatie("indicatie1")
            .omschrijving("omschrijving1");
    }

    public static Bouwactiviteit getBouwactiviteitSample2() {
        return new Bouwactiviteit()
            .id(2L)
            .bouwjaarklasse("bouwjaarklasse2")
            .bouwjaartot("bouwjaartot2")
            .bouwjaarvan("bouwjaarvan2")
            .indicatie("indicatie2")
            .omschrijving("omschrijving2");
    }

    public static Bouwactiviteit getBouwactiviteitRandomSampleGenerator() {
        return new Bouwactiviteit()
            .id(longCount.incrementAndGet())
            .bouwjaarklasse(UUID.randomUUID().toString())
            .bouwjaartot(UUID.randomUUID().toString())
            .bouwjaarvan(UUID.randomUUID().toString())
            .indicatie(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
