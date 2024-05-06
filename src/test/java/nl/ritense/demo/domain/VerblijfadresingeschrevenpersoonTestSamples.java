package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VerblijfadresingeschrevenpersoonTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Verblijfadresingeschrevenpersoon getVerblijfadresingeschrevenpersoonSample1() {
        return new Verblijfadresingeschrevenpersoon().id(1L).adresherkomst("adresherkomst1").beschrijvinglocatie("beschrijvinglocatie1");
    }

    public static Verblijfadresingeschrevenpersoon getVerblijfadresingeschrevenpersoonSample2() {
        return new Verblijfadresingeschrevenpersoon().id(2L).adresherkomst("adresherkomst2").beschrijvinglocatie("beschrijvinglocatie2");
    }

    public static Verblijfadresingeschrevenpersoon getVerblijfadresingeschrevenpersoonRandomSampleGenerator() {
        return new Verblijfadresingeschrevenpersoon()
            .id(longCount.incrementAndGet())
            .adresherkomst(UUID.randomUUID().toString())
            .beschrijvinglocatie(UUID.randomUUID().toString());
    }
}
