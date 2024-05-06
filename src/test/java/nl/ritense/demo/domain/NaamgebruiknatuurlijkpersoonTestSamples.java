package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class NaamgebruiknatuurlijkpersoonTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Naamgebruiknatuurlijkpersoon getNaamgebruiknatuurlijkpersoonSample1() {
        return new Naamgebruiknatuurlijkpersoon()
            .id(1L)
            .aanhefaanschrijving("aanhefaanschrijving1")
            .adellijketitelnaamgebruik("adellijketitelnaamgebruik1")
            .geslachtsnaamstamnaamgebruik("geslachtsnaamstamnaamgebruik1");
    }

    public static Naamgebruiknatuurlijkpersoon getNaamgebruiknatuurlijkpersoonSample2() {
        return new Naamgebruiknatuurlijkpersoon()
            .id(2L)
            .aanhefaanschrijving("aanhefaanschrijving2")
            .adellijketitelnaamgebruik("adellijketitelnaamgebruik2")
            .geslachtsnaamstamnaamgebruik("geslachtsnaamstamnaamgebruik2");
    }

    public static Naamgebruiknatuurlijkpersoon getNaamgebruiknatuurlijkpersoonRandomSampleGenerator() {
        return new Naamgebruiknatuurlijkpersoon()
            .id(longCount.incrementAndGet())
            .aanhefaanschrijving(UUID.randomUUID().toString())
            .adellijketitelnaamgebruik(UUID.randomUUID().toString())
            .geslachtsnaamstamnaamgebruik(UUID.randomUUID().toString());
    }
}
