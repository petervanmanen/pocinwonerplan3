package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RapportagemomentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Rapportagemoment getRapportagemomentSample1() {
        return new Rapportagemoment().id(1L).naam("naam1").omschrijving("omschrijving1").termijn("termijn1");
    }

    public static Rapportagemoment getRapportagemomentSample2() {
        return new Rapportagemoment().id(2L).naam("naam2").omschrijving("omschrijving2").termijn("termijn2");
    }

    public static Rapportagemoment getRapportagemomentRandomSampleGenerator() {
        return new Rapportagemoment()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .termijn(UUID.randomUUID().toString());
    }
}
