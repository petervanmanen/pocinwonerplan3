package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VerzoekomtoewijzingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Verzoekomtoewijzing getVerzoekomtoewijzingSample1() {
        return new Verzoekomtoewijzing()
            .id(1L)
            .beschikkingsnummer("beschikkingsnummer1")
            .commentaar("commentaar1")
            .eenheid("eenheid1")
            .frequentie("frequentie1")
            .referentieaanbieder("referentieaanbieder1")
            .soortverwijzer("soortverwijzer1")
            .verwijzer("verwijzer1")
            .volume("volume1");
    }

    public static Verzoekomtoewijzing getVerzoekomtoewijzingSample2() {
        return new Verzoekomtoewijzing()
            .id(2L)
            .beschikkingsnummer("beschikkingsnummer2")
            .commentaar("commentaar2")
            .eenheid("eenheid2")
            .frequentie("frequentie2")
            .referentieaanbieder("referentieaanbieder2")
            .soortverwijzer("soortverwijzer2")
            .verwijzer("verwijzer2")
            .volume("volume2");
    }

    public static Verzoekomtoewijzing getVerzoekomtoewijzingRandomSampleGenerator() {
        return new Verzoekomtoewijzing()
            .id(longCount.incrementAndGet())
            .beschikkingsnummer(UUID.randomUUID().toString())
            .commentaar(UUID.randomUUID().toString())
            .eenheid(UUID.randomUUID().toString())
            .frequentie(UUID.randomUUID().toString())
            .referentieaanbieder(UUID.randomUUID().toString())
            .soortverwijzer(UUID.randomUUID().toString())
            .verwijzer(UUID.randomUUID().toString())
            .volume(UUID.randomUUID().toString());
    }
}
