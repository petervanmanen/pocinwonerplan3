package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PrijzenboekitemTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Prijzenboekitem getPrijzenboekitemSample1() {
        return new Prijzenboekitem().id(1L).verrichting("verrichting1");
    }

    public static Prijzenboekitem getPrijzenboekitemSample2() {
        return new Prijzenboekitem().id(2L).verrichting("verrichting2");
    }

    public static Prijzenboekitem getPrijzenboekitemRandomSampleGenerator() {
        return new Prijzenboekitem().id(longCount.incrementAndGet()).verrichting(UUID.randomUUID().toString());
    }
}
