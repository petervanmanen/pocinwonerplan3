package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RaadslidTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Raadslid getRaadslidSample1() {
        return new Raadslid().id(1L).achternaam("achternaam1").fractie("fractie1").titel("titel1").voornaam("voornaam1");
    }

    public static Raadslid getRaadslidSample2() {
        return new Raadslid().id(2L).achternaam("achternaam2").fractie("fractie2").titel("titel2").voornaam("voornaam2");
    }

    public static Raadslid getRaadslidRandomSampleGenerator() {
        return new Raadslid()
            .id(longCount.incrementAndGet())
            .achternaam(UUID.randomUUID().toString())
            .fractie(UUID.randomUUID().toString())
            .titel(UUID.randomUUID().toString())
            .voornaam(UUID.randomUUID().toString());
    }
}
