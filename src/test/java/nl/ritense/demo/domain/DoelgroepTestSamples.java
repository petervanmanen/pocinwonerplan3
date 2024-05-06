package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DoelgroepTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Doelgroep getDoelgroepSample1() {
        return new Doelgroep().id(1L).branch("branch1").naam("naam1").omschrijving("omschrijving1").segment("segment1");
    }

    public static Doelgroep getDoelgroepSample2() {
        return new Doelgroep().id(2L).branch("branch2").naam("naam2").omschrijving("omschrijving2").segment("segment2");
    }

    public static Doelgroep getDoelgroepRandomSampleGenerator() {
        return new Doelgroep()
            .id(longCount.incrementAndGet())
            .branch(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .segment(UUID.randomUUID().toString());
    }
}
