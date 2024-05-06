package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SpecificatieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Specificatie getSpecificatieSample1() {
        return new Specificatie()
            .id(1L)
            .antwoord("antwoord1")
            .groepering("groepering1")
            .publiceerbaar("publiceerbaar1")
            .vraagclassificatie("vraagclassificatie1")
            .vraagid("vraagid1")
            .vraagreferentie("vraagreferentie1")
            .vraagtekst("vraagtekst1");
    }

    public static Specificatie getSpecificatieSample2() {
        return new Specificatie()
            .id(2L)
            .antwoord("antwoord2")
            .groepering("groepering2")
            .publiceerbaar("publiceerbaar2")
            .vraagclassificatie("vraagclassificatie2")
            .vraagid("vraagid2")
            .vraagreferentie("vraagreferentie2")
            .vraagtekst("vraagtekst2");
    }

    public static Specificatie getSpecificatieRandomSampleGenerator() {
        return new Specificatie()
            .id(longCount.incrementAndGet())
            .antwoord(UUID.randomUUID().toString())
            .groepering(UUID.randomUUID().toString())
            .publiceerbaar(UUID.randomUUID().toString())
            .vraagclassificatie(UUID.randomUUID().toString())
            .vraagid(UUID.randomUUID().toString())
            .vraagreferentie(UUID.randomUUID().toString())
            .vraagtekst(UUID.randomUUID().toString());
    }
}
