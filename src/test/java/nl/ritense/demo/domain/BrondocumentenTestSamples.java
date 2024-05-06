package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BrondocumentenTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Brondocumenten getBrondocumentenSample1() {
        return new Brondocumenten()
            .id(1L)
            .aktegemeente("aktegemeente1")
            .datumdocument("datumdocument1")
            .documentgemeente("documentgemeente1")
            .documentidentificatie("documentidentificatie1")
            .documentomschrijving("documentomschrijving1");
    }

    public static Brondocumenten getBrondocumentenSample2() {
        return new Brondocumenten()
            .id(2L)
            .aktegemeente("aktegemeente2")
            .datumdocument("datumdocument2")
            .documentgemeente("documentgemeente2")
            .documentidentificatie("documentidentificatie2")
            .documentomschrijving("documentomschrijving2");
    }

    public static Brondocumenten getBrondocumentenRandomSampleGenerator() {
        return new Brondocumenten()
            .id(longCount.incrementAndGet())
            .aktegemeente(UUID.randomUUID().toString())
            .datumdocument(UUID.randomUUID().toString())
            .documentgemeente(UUID.randomUUID().toString())
            .documentidentificatie(UUID.randomUUID().toString())
            .documentomschrijving(UUID.randomUUID().toString());
    }
}
