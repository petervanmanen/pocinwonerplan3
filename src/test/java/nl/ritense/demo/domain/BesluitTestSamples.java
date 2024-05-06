package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BesluitTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Besluit getBesluitSample1() {
        return new Besluit()
            .id(1L)
            .besluit("besluit1")
            .besluitidentificatie("besluitidentificatie1")
            .besluittoelichting("besluittoelichting1")
            .datumbesluit("datumbesluit1")
            .datumpublicatie("datumpublicatie1")
            .datumstart("datumstart1")
            .datumuiterlijkereactie("datumuiterlijkereactie1")
            .datumverval("datumverval1")
            .datumverzending("datumverzending1")
            .redenverval("redenverval1");
    }

    public static Besluit getBesluitSample2() {
        return new Besluit()
            .id(2L)
            .besluit("besluit2")
            .besluitidentificatie("besluitidentificatie2")
            .besluittoelichting("besluittoelichting2")
            .datumbesluit("datumbesluit2")
            .datumpublicatie("datumpublicatie2")
            .datumstart("datumstart2")
            .datumuiterlijkereactie("datumuiterlijkereactie2")
            .datumverval("datumverval2")
            .datumverzending("datumverzending2")
            .redenverval("redenverval2");
    }

    public static Besluit getBesluitRandomSampleGenerator() {
        return new Besluit()
            .id(longCount.incrementAndGet())
            .besluit(UUID.randomUUID().toString())
            .besluitidentificatie(UUID.randomUUID().toString())
            .besluittoelichting(UUID.randomUUID().toString())
            .datumbesluit(UUID.randomUUID().toString())
            .datumpublicatie(UUID.randomUUID().toString())
            .datumstart(UUID.randomUUID().toString())
            .datumuiterlijkereactie(UUID.randomUUID().toString())
            .datumverval(UUID.randomUUID().toString())
            .datumverzending(UUID.randomUUID().toString())
            .redenverval(UUID.randomUUID().toString());
    }
}
