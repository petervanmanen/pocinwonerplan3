package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VerblijfadresingeschrevennatuurlijkpersoonTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Verblijfadresingeschrevennatuurlijkpersoon getVerblijfadresingeschrevennatuurlijkpersoonSample1() {
        return new Verblijfadresingeschrevennatuurlijkpersoon()
            .id(1L)
            .adresherkomst("adresherkomst1")
            .beschrijvinglocatie("beschrijvinglocatie1");
    }

    public static Verblijfadresingeschrevennatuurlijkpersoon getVerblijfadresingeschrevennatuurlijkpersoonSample2() {
        return new Verblijfadresingeschrevennatuurlijkpersoon()
            .id(2L)
            .adresherkomst("adresherkomst2")
            .beschrijvinglocatie("beschrijvinglocatie2");
    }

    public static Verblijfadresingeschrevennatuurlijkpersoon getVerblijfadresingeschrevennatuurlijkpersoonRandomSampleGenerator() {
        return new Verblijfadresingeschrevennatuurlijkpersoon()
            .id(longCount.incrementAndGet())
            .adresherkomst(UUID.randomUUID().toString())
            .beschrijvinglocatie(UUID.randomUUID().toString());
    }
}
