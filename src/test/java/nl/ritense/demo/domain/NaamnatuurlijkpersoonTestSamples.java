package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class NaamnatuurlijkpersoonTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Naamnatuurlijkpersoon getNaamnatuurlijkpersoonSample1() {
        return new Naamnatuurlijkpersoon()
            .id(1L)
            .adellijketitelofpredikaat("adellijketitelofpredikaat1")
            .geslachtsnaam("geslachtsnaam1")
            .voornamen("voornamen1")
            .voorvoegselgeslachtsnaam("voorvoegselgeslachtsnaam1");
    }

    public static Naamnatuurlijkpersoon getNaamnatuurlijkpersoonSample2() {
        return new Naamnatuurlijkpersoon()
            .id(2L)
            .adellijketitelofpredikaat("adellijketitelofpredikaat2")
            .geslachtsnaam("geslachtsnaam2")
            .voornamen("voornamen2")
            .voorvoegselgeslachtsnaam("voorvoegselgeslachtsnaam2");
    }

    public static Naamnatuurlijkpersoon getNaamnatuurlijkpersoonRandomSampleGenerator() {
        return new Naamnatuurlijkpersoon()
            .id(longCount.incrementAndGet())
            .adellijketitelofpredikaat(UUID.randomUUID().toString())
            .geslachtsnaam(UUID.randomUUID().toString())
            .voornamen(UUID.randomUUID().toString())
            .voorvoegselgeslachtsnaam(UUID.randomUUID().toString());
    }
}
