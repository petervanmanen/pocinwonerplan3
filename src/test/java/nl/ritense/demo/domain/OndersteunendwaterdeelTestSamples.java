package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OndersteunendwaterdeelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Ondersteunendwaterdeel getOndersteunendwaterdeelSample1() {
        return new Ondersteunendwaterdeel()
            .id(1L)
            .geometrieondersteunendwaterdeel("geometrieondersteunendwaterdeel1")
            .identificatieondersteunendwaterdeel("identificatieondersteunendwaterdeel1")
            .plustypeondersteunendwaterdeel("plustypeondersteunendwaterdeel1")
            .relatievehoogteliggingondersteunendwaterdeel("relatievehoogteliggingondersteunendwaterdeel1")
            .statusondersteunendwaterdeel("statusondersteunendwaterdeel1")
            .typeondersteunendwaterdeel("typeondersteunendwaterdeel1");
    }

    public static Ondersteunendwaterdeel getOndersteunendwaterdeelSample2() {
        return new Ondersteunendwaterdeel()
            .id(2L)
            .geometrieondersteunendwaterdeel("geometrieondersteunendwaterdeel2")
            .identificatieondersteunendwaterdeel("identificatieondersteunendwaterdeel2")
            .plustypeondersteunendwaterdeel("plustypeondersteunendwaterdeel2")
            .relatievehoogteliggingondersteunendwaterdeel("relatievehoogteliggingondersteunendwaterdeel2")
            .statusondersteunendwaterdeel("statusondersteunendwaterdeel2")
            .typeondersteunendwaterdeel("typeondersteunendwaterdeel2");
    }

    public static Ondersteunendwaterdeel getOndersteunendwaterdeelRandomSampleGenerator() {
        return new Ondersteunendwaterdeel()
            .id(longCount.incrementAndGet())
            .geometrieondersteunendwaterdeel(UUID.randomUUID().toString())
            .identificatieondersteunendwaterdeel(UUID.randomUUID().toString())
            .plustypeondersteunendwaterdeel(UUID.randomUUID().toString())
            .relatievehoogteliggingondersteunendwaterdeel(UUID.randomUUID().toString())
            .statusondersteunendwaterdeel(UUID.randomUUID().toString())
            .typeondersteunendwaterdeel(UUID.randomUUID().toString());
    }
}
