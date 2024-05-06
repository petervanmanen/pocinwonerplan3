package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FormelehistorieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Formelehistorie getFormelehistorieSample1() {
        return new Formelehistorie().id(1L).tijdstipregistratiegegevens("tijdstipregistratiegegevens1");
    }

    public static Formelehistorie getFormelehistorieSample2() {
        return new Formelehistorie().id(2L).tijdstipregistratiegegevens("tijdstipregistratiegegevens2");
    }

    public static Formelehistorie getFormelehistorieRandomSampleGenerator() {
        return new Formelehistorie().id(longCount.incrementAndGet()).tijdstipregistratiegegevens(UUID.randomUUID().toString());
    }
}
