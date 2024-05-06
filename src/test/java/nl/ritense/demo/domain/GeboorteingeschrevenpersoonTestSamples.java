package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class GeboorteingeschrevenpersoonTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Geboorteingeschrevenpersoon getGeboorteingeschrevenpersoonSample1() {
        return new Geboorteingeschrevenpersoon()
            .id(1L)
            .datumgeboorte("datumgeboorte1")
            .geboorteland("geboorteland1")
            .geboorteplaats("geboorteplaats1");
    }

    public static Geboorteingeschrevenpersoon getGeboorteingeschrevenpersoonSample2() {
        return new Geboorteingeschrevenpersoon()
            .id(2L)
            .datumgeboorte("datumgeboorte2")
            .geboorteland("geboorteland2")
            .geboorteplaats("geboorteplaats2");
    }

    public static Geboorteingeschrevenpersoon getGeboorteingeschrevenpersoonRandomSampleGenerator() {
        return new Geboorteingeschrevenpersoon()
            .id(longCount.incrementAndGet())
            .datumgeboorte(UUID.randomUUID().toString())
            .geboorteland(UUID.randomUUID().toString())
            .geboorteplaats(UUID.randomUUID().toString());
    }
}
