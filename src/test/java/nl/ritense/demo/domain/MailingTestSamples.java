package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MailingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Mailing getMailingSample1() {
        return new Mailing().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Mailing getMailingSample2() {
        return new Mailing().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Mailing getMailingRandomSampleGenerator() {
        return new Mailing().id(longCount.incrementAndGet()).naam(UUID.randomUUID().toString()).omschrijving(UUID.randomUUID().toString());
    }
}
