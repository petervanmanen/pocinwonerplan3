package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VerblijfsrechtingeschrevennatuurlijkpersoonTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Verblijfsrechtingeschrevennatuurlijkpersoon getVerblijfsrechtingeschrevennatuurlijkpersoonSample1() {
        return new Verblijfsrechtingeschrevennatuurlijkpersoon()
            .id(1L)
            .aanduidingverblijfsrecht("aanduidingverblijfsrecht1")
            .datumaanvangverblijfsrecht("datumaanvangverblijfsrecht1")
            .datummededelingverblijfsrecht("datummededelingverblijfsrecht1")
            .datumvoorzieneindeverblijfsrecht("datumvoorzieneindeverblijfsrecht1");
    }

    public static Verblijfsrechtingeschrevennatuurlijkpersoon getVerblijfsrechtingeschrevennatuurlijkpersoonSample2() {
        return new Verblijfsrechtingeschrevennatuurlijkpersoon()
            .id(2L)
            .aanduidingverblijfsrecht("aanduidingverblijfsrecht2")
            .datumaanvangverblijfsrecht("datumaanvangverblijfsrecht2")
            .datummededelingverblijfsrecht("datummededelingverblijfsrecht2")
            .datumvoorzieneindeverblijfsrecht("datumvoorzieneindeverblijfsrecht2");
    }

    public static Verblijfsrechtingeschrevennatuurlijkpersoon getVerblijfsrechtingeschrevennatuurlijkpersoonRandomSampleGenerator() {
        return new Verblijfsrechtingeschrevennatuurlijkpersoon()
            .id(longCount.incrementAndGet())
            .aanduidingverblijfsrecht(UUID.randomUUID().toString())
            .datumaanvangverblijfsrecht(UUID.randomUUID().toString())
            .datummededelingverblijfsrecht(UUID.randomUUID().toString())
            .datumvoorzieneindeverblijfsrecht(UUID.randomUUID().toString());
    }
}
