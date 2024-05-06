package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BankafschriftTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Bankafschrift getBankafschriftSample1() {
        return new Bankafschrift().id(1L).datum("datum1").nummer("nummer1");
    }

    public static Bankafschrift getBankafschriftSample2() {
        return new Bankafschrift().id(2L).datum("datum2").nummer("nummer2");
    }

    public static Bankafschrift getBankafschriftRandomSampleGenerator() {
        return new Bankafschrift().id(longCount.incrementAndGet()).datum(UUID.randomUUID().toString()).nummer(UUID.randomUUID().toString());
    }
}
