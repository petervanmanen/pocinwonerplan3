package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AcademischetitelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Academischetitel getAcademischetitelSample1() {
        return new Academischetitel()
            .id(1L)
            .codeacademischetitel("codeacademischetitel1")
            .datumbegingeldigheidtitel("datumbegingeldigheidtitel1")
            .datumeindegeldigheidtitel("datumeindegeldigheidtitel1")
            .omschrijvingacademischetitel("omschrijvingacademischetitel1")
            .positieacademischetiteltovnaam("positieacademischetiteltovnaam1");
    }

    public static Academischetitel getAcademischetitelSample2() {
        return new Academischetitel()
            .id(2L)
            .codeacademischetitel("codeacademischetitel2")
            .datumbegingeldigheidtitel("datumbegingeldigheidtitel2")
            .datumeindegeldigheidtitel("datumeindegeldigheidtitel2")
            .omschrijvingacademischetitel("omschrijvingacademischetitel2")
            .positieacademischetiteltovnaam("positieacademischetiteltovnaam2");
    }

    public static Academischetitel getAcademischetitelRandomSampleGenerator() {
        return new Academischetitel()
            .id(longCount.incrementAndGet())
            .codeacademischetitel(UUID.randomUUID().toString())
            .datumbegingeldigheidtitel(UUID.randomUUID().toString())
            .datumeindegeldigheidtitel(UUID.randomUUID().toString())
            .omschrijvingacademischetitel(UUID.randomUUID().toString())
            .positieacademischetiteltovnaam(UUID.randomUUID().toString());
    }
}
