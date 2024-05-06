package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class NationaliteitingeschrevennatuurlijkpersoonTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Nationaliteitingeschrevennatuurlijkpersoon getNationaliteitingeschrevennatuurlijkpersoonSample1() {
        return new Nationaliteitingeschrevennatuurlijkpersoon()
            .id(1L)
            .buitenlandspersoonsnummer("buitenlandspersoonsnummer1")
            .nationaliteit("nationaliteit1")
            .redenverkrijging("redenverkrijging1")
            .redenverlies("redenverlies1");
    }

    public static Nationaliteitingeschrevennatuurlijkpersoon getNationaliteitingeschrevennatuurlijkpersoonSample2() {
        return new Nationaliteitingeschrevennatuurlijkpersoon()
            .id(2L)
            .buitenlandspersoonsnummer("buitenlandspersoonsnummer2")
            .nationaliteit("nationaliteit2")
            .redenverkrijging("redenverkrijging2")
            .redenverlies("redenverlies2");
    }

    public static Nationaliteitingeschrevennatuurlijkpersoon getNationaliteitingeschrevennatuurlijkpersoonRandomSampleGenerator() {
        return new Nationaliteitingeschrevennatuurlijkpersoon()
            .id(longCount.incrementAndGet())
            .buitenlandspersoonsnummer(UUID.randomUUID().toString())
            .nationaliteit(UUID.randomUUID().toString())
            .redenverkrijging(UUID.randomUUID().toString())
            .redenverlies(UUID.randomUUID().toString());
    }
}
