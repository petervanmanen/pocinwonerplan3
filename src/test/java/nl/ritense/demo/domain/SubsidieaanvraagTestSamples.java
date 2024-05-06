package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SubsidieaanvraagTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Subsidieaanvraag getSubsidieaanvraagSample1() {
        return new Subsidieaanvraag().id(1L).kenmerk("kenmerk1");
    }

    public static Subsidieaanvraag getSubsidieaanvraagSample2() {
        return new Subsidieaanvraag().id(2L).kenmerk("kenmerk2");
    }

    public static Subsidieaanvraag getSubsidieaanvraagRandomSampleGenerator() {
        return new Subsidieaanvraag().id(longCount.incrementAndGet()).kenmerk(UUID.randomUUID().toString());
    }
}
