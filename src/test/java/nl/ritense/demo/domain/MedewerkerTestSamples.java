package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MedewerkerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Medewerker getMedewerkerSample1() {
        return new Medewerker()
            .id(1L)
            .achternaam("achternaam1")
            .datumuitdienst("datumuitdienst1")
            .emailadres("emailadres1")
            .extern("extern1")
            .functie("functie1")
            .geslachtsaanduiding("geslachtsaanduiding1")
            .medewerkeridentificatie("medewerkeridentificatie1")
            .medewerkertoelichting("medewerkertoelichting1")
            .roepnaam("roepnaam1")
            .telefoonnummer("telefoonnummer1")
            .voorletters("voorletters1")
            .voorvoegselachternaam("voorvoegselachternaam1");
    }

    public static Medewerker getMedewerkerSample2() {
        return new Medewerker()
            .id(2L)
            .achternaam("achternaam2")
            .datumuitdienst("datumuitdienst2")
            .emailadres("emailadres2")
            .extern("extern2")
            .functie("functie2")
            .geslachtsaanduiding("geslachtsaanduiding2")
            .medewerkeridentificatie("medewerkeridentificatie2")
            .medewerkertoelichting("medewerkertoelichting2")
            .roepnaam("roepnaam2")
            .telefoonnummer("telefoonnummer2")
            .voorletters("voorletters2")
            .voorvoegselachternaam("voorvoegselachternaam2");
    }

    public static Medewerker getMedewerkerRandomSampleGenerator() {
        return new Medewerker()
            .id(longCount.incrementAndGet())
            .achternaam(UUID.randomUUID().toString())
            .datumuitdienst(UUID.randomUUID().toString())
            .emailadres(UUID.randomUUID().toString())
            .extern(UUID.randomUUID().toString())
            .functie(UUID.randomUUID().toString())
            .geslachtsaanduiding(UUID.randomUUID().toString())
            .medewerkeridentificatie(UUID.randomUUID().toString())
            .medewerkertoelichting(UUID.randomUUID().toString())
            .roepnaam(UUID.randomUUID().toString())
            .telefoonnummer(UUID.randomUUID().toString())
            .voorletters(UUID.randomUUID().toString())
            .voorvoegselachternaam(UUID.randomUUID().toString());
    }
}
