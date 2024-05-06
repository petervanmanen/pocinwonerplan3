package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WerkgelegenheidTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Werkgelegenheid getWerkgelegenheidSample1() {
        return new Werkgelegenheid()
            .id(1L)
            .aantalfulltimemannen("aantalfulltimemannen1")
            .aantalfulltimevrouwen("aantalfulltimevrouwen1")
            .aantalparttimemannen("aantalparttimemannen1")
            .aantalparttimevrouwen("aantalparttimevrouwen1")
            .grootteklasse("grootteklasse1");
    }

    public static Werkgelegenheid getWerkgelegenheidSample2() {
        return new Werkgelegenheid()
            .id(2L)
            .aantalfulltimemannen("aantalfulltimemannen2")
            .aantalfulltimevrouwen("aantalfulltimevrouwen2")
            .aantalparttimemannen("aantalparttimemannen2")
            .aantalparttimevrouwen("aantalparttimevrouwen2")
            .grootteklasse("grootteklasse2");
    }

    public static Werkgelegenheid getWerkgelegenheidRandomSampleGenerator() {
        return new Werkgelegenheid()
            .id(longCount.incrementAndGet())
            .aantalfulltimemannen(UUID.randomUUID().toString())
            .aantalfulltimevrouwen(UUID.randomUUID().toString())
            .aantalparttimemannen(UUID.randomUUID().toString())
            .aantalparttimevrouwen(UUID.randomUUID().toString())
            .grootteklasse(UUID.randomUUID().toString());
    }
}
