package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CollegelidTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Collegelid getCollegelidSample1() {
        return new Collegelid()
            .id(1L)
            .achternaam("achternaam1")
            .fractie("fractie1")
            .portefeuille("portefeuille1")
            .titel("titel1")
            .voornaam("voornaam1");
    }

    public static Collegelid getCollegelidSample2() {
        return new Collegelid()
            .id(2L)
            .achternaam("achternaam2")
            .fractie("fractie2")
            .portefeuille("portefeuille2")
            .titel("titel2")
            .voornaam("voornaam2");
    }

    public static Collegelid getCollegelidRandomSampleGenerator() {
        return new Collegelid()
            .id(longCount.incrementAndGet())
            .achternaam(UUID.randomUUID().toString())
            .fractie(UUID.randomUUID().toString())
            .portefeuille(UUID.randomUUID().toString())
            .titel(UUID.randomUUID().toString())
            .voornaam(UUID.randomUUID().toString());
    }
}
