package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UitlaatconstructieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Uitlaatconstructie getUitlaatconstructieSample1() {
        return new Uitlaatconstructie().id(1L).type("type1").waterobject("waterobject1");
    }

    public static Uitlaatconstructie getUitlaatconstructieSample2() {
        return new Uitlaatconstructie().id(2L).type("type2").waterobject("waterobject2");
    }

    public static Uitlaatconstructie getUitlaatconstructieRandomSampleGenerator() {
        return new Uitlaatconstructie()
            .id(longCount.incrementAndGet())
            .type(UUID.randomUUID().toString())
            .waterobject(UUID.randomUUID().toString());
    }
}
