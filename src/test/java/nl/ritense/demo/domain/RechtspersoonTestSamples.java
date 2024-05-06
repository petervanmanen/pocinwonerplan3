package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RechtspersoonTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Rechtspersoon getRechtspersoonSample1() {
        return new Rechtspersoon()
            .id(1L)
            .adresbinnenland("adresbinnenland1")
            .adresbuitenland("adresbuitenland1")
            .adrescorrespondentie("adrescorrespondentie1")
            .emailadres("emailadres1")
            .faxnummer("faxnummer1")
            .identificatie("identificatie1")
            .kvknummer("kvknummer1")
            .naam("naam1")
            .rechtsvorm("rechtsvorm1")
            .rekeningnummer("rekeningnummer1")
            .telefoonnummer("telefoonnummer1");
    }

    public static Rechtspersoon getRechtspersoonSample2() {
        return new Rechtspersoon()
            .id(2L)
            .adresbinnenland("adresbinnenland2")
            .adresbuitenland("adresbuitenland2")
            .adrescorrespondentie("adrescorrespondentie2")
            .emailadres("emailadres2")
            .faxnummer("faxnummer2")
            .identificatie("identificatie2")
            .kvknummer("kvknummer2")
            .naam("naam2")
            .rechtsvorm("rechtsvorm2")
            .rekeningnummer("rekeningnummer2")
            .telefoonnummer("telefoonnummer2");
    }

    public static Rechtspersoon getRechtspersoonRandomSampleGenerator() {
        return new Rechtspersoon()
            .id(longCount.incrementAndGet())
            .adresbinnenland(UUID.randomUUID().toString())
            .adresbuitenland(UUID.randomUUID().toString())
            .adrescorrespondentie(UUID.randomUUID().toString())
            .emailadres(UUID.randomUUID().toString())
            .faxnummer(UUID.randomUUID().toString())
            .identificatie(UUID.randomUUID().toString())
            .kvknummer(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .rechtsvorm(UUID.randomUUID().toString())
            .rekeningnummer(UUID.randomUUID().toString())
            .telefoonnummer(UUID.randomUUID().toString());
    }
}
