package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VegetatieobjectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Vegetatieobject getVegetatieobjectSample1() {
        return new Vegetatieobject()
            .id(1L)
            .bereikbaarheid("bereikbaarheid1")
            .kwaliteitsniveauactueel("kwaliteitsniveauactueel1")
            .kwaliteitsniveaugewenst("kwaliteitsniveaugewenst1")
            .kweker("kweker1")
            .leverancier("leverancier1")
            .eobjectnummer("eobjectnummer1")
            .soortnaam("soortnaam1")
            .typestandplaats("typestandplaats1")
            .typestandplaatsplus("typestandplaatsplus1")
            .vegetatieobjectbereikbaarheidplus("vegetatieobjectbereikbaarheidplus1");
    }

    public static Vegetatieobject getVegetatieobjectSample2() {
        return new Vegetatieobject()
            .id(2L)
            .bereikbaarheid("bereikbaarheid2")
            .kwaliteitsniveauactueel("kwaliteitsniveauactueel2")
            .kwaliteitsniveaugewenst("kwaliteitsniveaugewenst2")
            .kweker("kweker2")
            .leverancier("leverancier2")
            .eobjectnummer("eobjectnummer2")
            .soortnaam("soortnaam2")
            .typestandplaats("typestandplaats2")
            .typestandplaatsplus("typestandplaatsplus2")
            .vegetatieobjectbereikbaarheidplus("vegetatieobjectbereikbaarheidplus2");
    }

    public static Vegetatieobject getVegetatieobjectRandomSampleGenerator() {
        return new Vegetatieobject()
            .id(longCount.incrementAndGet())
            .bereikbaarheid(UUID.randomUUID().toString())
            .kwaliteitsniveauactueel(UUID.randomUUID().toString())
            .kwaliteitsniveaugewenst(UUID.randomUUID().toString())
            .kweker(UUID.randomUUID().toString())
            .leverancier(UUID.randomUUID().toString())
            .eobjectnummer(UUID.randomUUID().toString())
            .soortnaam(UUID.randomUUID().toString())
            .typestandplaats(UUID.randomUUID().toString())
            .typestandplaatsplus(UUID.randomUUID().toString())
            .vegetatieobjectbereikbaarheidplus(UUID.randomUUID().toString());
    }
}
