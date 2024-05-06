package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AanvraagTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Aanvraag getAanvraagSample1() {
        return new Aanvraag().id(1L).datumtijd("datumtijd1");
    }

    public static Aanvraag getAanvraagSample2() {
        return new Aanvraag().id(2L).datumtijd("datumtijd2");
    }

    public static Aanvraag getAanvraagRandomSampleGenerator() {
        return new Aanvraag().id(longCount.incrementAndGet()).datumtijd(UUID.randomUUID().toString());
    }
}
