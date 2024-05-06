package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ContactpersoonrolTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Contactpersoonrol getContactpersoonrolSample1() {
        return new Contactpersoonrol()
            .id(1L)
            .contactpersoonemailadres("contactpersoonemailadres1")
            .contactpersoonfunctie("contactpersoonfunctie1")
            .contactpersoonnaam("contactpersoonnaam1")
            .contactpersoontelefoonnummer("contactpersoontelefoonnummer1");
    }

    public static Contactpersoonrol getContactpersoonrolSample2() {
        return new Contactpersoonrol()
            .id(2L)
            .contactpersoonemailadres("contactpersoonemailadres2")
            .contactpersoonfunctie("contactpersoonfunctie2")
            .contactpersoonnaam("contactpersoonnaam2")
            .contactpersoontelefoonnummer("contactpersoontelefoonnummer2");
    }

    public static Contactpersoonrol getContactpersoonrolRandomSampleGenerator() {
        return new Contactpersoonrol()
            .id(longCount.incrementAndGet())
            .contactpersoonemailadres(UUID.randomUUID().toString())
            .contactpersoonfunctie(UUID.randomUUID().toString())
            .contactpersoonnaam(UUID.randomUUID().toString())
            .contactpersoontelefoonnummer(UUID.randomUUID().toString());
    }
}
