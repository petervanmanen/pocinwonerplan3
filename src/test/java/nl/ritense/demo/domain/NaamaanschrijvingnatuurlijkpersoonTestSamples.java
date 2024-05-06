package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class NaamaanschrijvingnatuurlijkpersoonTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Naamaanschrijvingnatuurlijkpersoon getNaamaanschrijvingnatuurlijkpersoonSample1() {
        return new Naamaanschrijvingnatuurlijkpersoon()
            .id(1L)
            .aanhefaanschrijving("aanhefaanschrijving1")
            .geslachtsnaamaanschrijving("geslachtsnaamaanschrijving1")
            .voorlettersaanschrijving("voorlettersaanschrijving1")
            .voornamenaanschrijving("voornamenaanschrijving1");
    }

    public static Naamaanschrijvingnatuurlijkpersoon getNaamaanschrijvingnatuurlijkpersoonSample2() {
        return new Naamaanschrijvingnatuurlijkpersoon()
            .id(2L)
            .aanhefaanschrijving("aanhefaanschrijving2")
            .geslachtsnaamaanschrijving("geslachtsnaamaanschrijving2")
            .voorlettersaanschrijving("voorlettersaanschrijving2")
            .voornamenaanschrijving("voornamenaanschrijving2");
    }

    public static Naamaanschrijvingnatuurlijkpersoon getNaamaanschrijvingnatuurlijkpersoonRandomSampleGenerator() {
        return new Naamaanschrijvingnatuurlijkpersoon()
            .id(longCount.incrementAndGet())
            .aanhefaanschrijving(UUID.randomUUID().toString())
            .geslachtsnaamaanschrijving(UUID.randomUUID().toString())
            .voorlettersaanschrijving(UUID.randomUUID().toString())
            .voornamenaanschrijving(UUID.randomUUID().toString());
    }
}
