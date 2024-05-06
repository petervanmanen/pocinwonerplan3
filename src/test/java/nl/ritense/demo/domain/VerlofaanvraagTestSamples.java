package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VerlofaanvraagTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Verlofaanvraag getVerlofaanvraagSample1() {
        return new Verlofaanvraag().id(1L).soortverlof("soortverlof1");
    }

    public static Verlofaanvraag getVerlofaanvraagSample2() {
        return new Verlofaanvraag().id(2L).soortverlof("soortverlof2");
    }

    public static Verlofaanvraag getVerlofaanvraagRandomSampleGenerator() {
        return new Verlofaanvraag().id(longCount.incrementAndGet()).soortverlof(UUID.randomUUID().toString());
    }
}
