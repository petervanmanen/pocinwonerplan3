package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DisciplinairemaatregelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Disciplinairemaatregel getDisciplinairemaatregelSample1() {
        return new Disciplinairemaatregel().id(1L).omschrijving("omschrijving1").reden("reden1");
    }

    public static Disciplinairemaatregel getDisciplinairemaatregelSample2() {
        return new Disciplinairemaatregel().id(2L).omschrijving("omschrijving2").reden("reden2");
    }

    public static Disciplinairemaatregel getDisciplinairemaatregelRandomSampleGenerator() {
        return new Disciplinairemaatregel()
            .id(longCount.incrementAndGet())
            .omschrijving(UUID.randomUUID().toString())
            .reden(UUID.randomUUID().toString());
    }
}
