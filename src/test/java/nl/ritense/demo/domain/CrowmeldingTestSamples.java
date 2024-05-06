package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CrowmeldingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Crowmelding getCrowmeldingSample1() {
        return new Crowmelding().id(1L).kwaliteitsniveau("kwaliteitsniveau1");
    }

    public static Crowmelding getCrowmeldingSample2() {
        return new Crowmelding().id(2L).kwaliteitsniveau("kwaliteitsniveau2");
    }

    public static Crowmelding getCrowmeldingRandomSampleGenerator() {
        return new Crowmelding().id(longCount.incrementAndGet()).kwaliteitsniveau(UUID.randomUUID().toString());
    }
}
