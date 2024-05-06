package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UitkeringsrunTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Uitkeringsrun getUitkeringsrunSample1() {
        return new Uitkeringsrun().id(1L).frequentie("frequentie1").perioderun("perioderun1").soortrun("soortrun1");
    }

    public static Uitkeringsrun getUitkeringsrunSample2() {
        return new Uitkeringsrun().id(2L).frequentie("frequentie2").perioderun("perioderun2").soortrun("soortrun2");
    }

    public static Uitkeringsrun getUitkeringsrunRandomSampleGenerator() {
        return new Uitkeringsrun()
            .id(longCount.incrementAndGet())
            .frequentie(UUID.randomUUID().toString())
            .perioderun(UUID.randomUUID().toString())
            .soortrun(UUID.randomUUID().toString());
    }
}
