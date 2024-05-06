package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SubsidiebeschikkingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Subsidiebeschikking getSubsidiebeschikkingSample1() {
        return new Subsidiebeschikking()
            .id(1L)
            .beschikkingsnummer("beschikkingsnummer1")
            .besluit("besluit1")
            .internkenmerk("internkenmerk1")
            .kenmerk("kenmerk1")
            .opmerkingen("opmerkingen1");
    }

    public static Subsidiebeschikking getSubsidiebeschikkingSample2() {
        return new Subsidiebeschikking()
            .id(2L)
            .beschikkingsnummer("beschikkingsnummer2")
            .besluit("besluit2")
            .internkenmerk("internkenmerk2")
            .kenmerk("kenmerk2")
            .opmerkingen("opmerkingen2");
    }

    public static Subsidiebeschikking getSubsidiebeschikkingRandomSampleGenerator() {
        return new Subsidiebeschikking()
            .id(longCount.incrementAndGet())
            .beschikkingsnummer(UUID.randomUUID().toString())
            .besluit(UUID.randomUUID().toString())
            .internkenmerk(UUID.randomUUID().toString())
            .kenmerk(UUID.randomUUID().toString())
            .opmerkingen(UUID.randomUUID().toString());
    }
}
