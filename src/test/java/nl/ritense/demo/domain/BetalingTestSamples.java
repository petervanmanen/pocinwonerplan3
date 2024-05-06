package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BetalingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Betaling getBetalingSample1() {
        return new Betaling().id(1L).datumtijd("datumtijd1").omschrijving("omschrijving1").valuta("valuta1");
    }

    public static Betaling getBetalingSample2() {
        return new Betaling().id(2L).datumtijd("datumtijd2").omschrijving("omschrijving2").valuta("valuta2");
    }

    public static Betaling getBetalingRandomSampleGenerator() {
        return new Betaling()
            .id(longCount.incrementAndGet())
            .datumtijd(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .valuta(UUID.randomUUID().toString());
    }
}
