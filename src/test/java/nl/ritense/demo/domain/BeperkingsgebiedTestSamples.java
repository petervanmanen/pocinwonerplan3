package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BeperkingsgebiedTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Beperkingsgebied getBeperkingsgebiedSample1() {
        return new Beperkingsgebied().id(1L).groep("groep1").naam("naam1");
    }

    public static Beperkingsgebied getBeperkingsgebiedSample2() {
        return new Beperkingsgebied().id(2L).groep("groep2").naam("naam2");
    }

    public static Beperkingsgebied getBeperkingsgebiedRandomSampleGenerator() {
        return new Beperkingsgebied()
            .id(longCount.incrementAndGet())
            .groep(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString());
    }
}
