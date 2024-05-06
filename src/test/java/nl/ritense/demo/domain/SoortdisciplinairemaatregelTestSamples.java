package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SoortdisciplinairemaatregelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Soortdisciplinairemaatregel getSoortdisciplinairemaatregelSample1() {
        return new Soortdisciplinairemaatregel().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Soortdisciplinairemaatregel getSoortdisciplinairemaatregelSample2() {
        return new Soortdisciplinairemaatregel().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Soortdisciplinairemaatregel getSoortdisciplinairemaatregelRandomSampleGenerator() {
        return new Soortdisciplinairemaatregel()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
