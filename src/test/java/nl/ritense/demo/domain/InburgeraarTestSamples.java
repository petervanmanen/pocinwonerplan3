package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class InburgeraarTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Inburgeraar getInburgeraarSample1() {
        return new Inburgeraar().id(1L).doelgroep("doelgroep1").gedetailleerdedoelgroep("gedetailleerdedoelgroep1");
    }

    public static Inburgeraar getInburgeraarSample2() {
        return new Inburgeraar().id(2L).doelgroep("doelgroep2").gedetailleerdedoelgroep("gedetailleerdedoelgroep2");
    }

    public static Inburgeraar getInburgeraarRandomSampleGenerator() {
        return new Inburgeraar()
            .id(longCount.incrementAndGet())
            .doelgroep(UUID.randomUUID().toString())
            .gedetailleerdedoelgroep(UUID.randomUUID().toString());
    }
}
