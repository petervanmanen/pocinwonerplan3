package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ValutaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Valuta getValutaSample1() {
        return new Valuta()
            .id(1L)
            .datumbegingeldigheid("datumbegingeldigheid1")
            .datumeindegeldigheid("datumeindegeldigheid1")
            .naam("naam1")
            .valutacode("valutacode1");
    }

    public static Valuta getValutaSample2() {
        return new Valuta()
            .id(2L)
            .datumbegingeldigheid("datumbegingeldigheid2")
            .datumeindegeldigheid("datumeindegeldigheid2")
            .naam("naam2")
            .valutacode("valutacode2");
    }

    public static Valuta getValutaRandomSampleGenerator() {
        return new Valuta()
            .id(longCount.incrementAndGet())
            .datumbegingeldigheid(UUID.randomUUID().toString())
            .datumeindegeldigheid(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .valutacode(UUID.randomUUID().toString());
    }
}
