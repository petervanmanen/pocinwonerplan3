package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BankafschriftregelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Bankafschriftregel getBankafschriftregelSample1() {
        return new Bankafschriftregel().id(1L).datum("datum1").rekeningvan("rekeningvan1");
    }

    public static Bankafschriftregel getBankafschriftregelSample2() {
        return new Bankafschriftregel().id(2L).datum("datum2").rekeningvan("rekeningvan2");
    }

    public static Bankafschriftregel getBankafschriftregelRandomSampleGenerator() {
        return new Bankafschriftregel()
            .id(longCount.incrementAndGet())
            .datum(UUID.randomUUID().toString())
            .rekeningvan(UUID.randomUUID().toString());
    }
}
