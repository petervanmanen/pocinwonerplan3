package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VoorzieningTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Voorziening getVoorzieningSample1() {
        return new Voorziening().id(1L).aantalbeschikbaar("aantalbeschikbaar1").naam("naam1").omschrijving("omschrijving1");
    }

    public static Voorziening getVoorzieningSample2() {
        return new Voorziening().id(2L).aantalbeschikbaar("aantalbeschikbaar2").naam("naam2").omschrijving("omschrijving2");
    }

    public static Voorziening getVoorzieningRandomSampleGenerator() {
        return new Voorziening()
            .id(longCount.incrementAndGet())
            .aantalbeschikbaar(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
