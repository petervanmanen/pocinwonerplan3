package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SamengesteldenaamnatuurlijkpersoonTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Samengesteldenaamnatuurlijkpersoon getSamengesteldenaamnatuurlijkpersoonSample1() {
        return new Samengesteldenaamnatuurlijkpersoon()
            .id(1L)
            .adellijketitel("adellijketitel1")
            .geslachtsnaamstam("geslachtsnaamstam1")
            .namenreeks("namenreeks1")
            .predicaat("predicaat1")
            .scheidingsteken("scheidingsteken1")
            .voornamen("voornamen1")
            .voorvoegsel("voorvoegsel1");
    }

    public static Samengesteldenaamnatuurlijkpersoon getSamengesteldenaamnatuurlijkpersoonSample2() {
        return new Samengesteldenaamnatuurlijkpersoon()
            .id(2L)
            .adellijketitel("adellijketitel2")
            .geslachtsnaamstam("geslachtsnaamstam2")
            .namenreeks("namenreeks2")
            .predicaat("predicaat2")
            .scheidingsteken("scheidingsteken2")
            .voornamen("voornamen2")
            .voorvoegsel("voorvoegsel2");
    }

    public static Samengesteldenaamnatuurlijkpersoon getSamengesteldenaamnatuurlijkpersoonRandomSampleGenerator() {
        return new Samengesteldenaamnatuurlijkpersoon()
            .id(longCount.incrementAndGet())
            .adellijketitel(UUID.randomUUID().toString())
            .geslachtsnaamstam(UUID.randomUUID().toString())
            .namenreeks(UUID.randomUUID().toString())
            .predicaat(UUID.randomUUID().toString())
            .scheidingsteken(UUID.randomUUID().toString())
            .voornamen(UUID.randomUUID().toString())
            .voorvoegsel(UUID.randomUUID().toString());
    }
}
