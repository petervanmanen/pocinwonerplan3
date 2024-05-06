package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ReserveringTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Reservering getReserveringSample1() {
        return new Reservering()
            .id(1L)
            .aantal("aantal1")
            .btw("btw1")
            .tijdtot("tijdtot1")
            .tijdvanaf("tijdvanaf1")
            .totaalprijs("totaalprijs1");
    }

    public static Reservering getReserveringSample2() {
        return new Reservering()
            .id(2L)
            .aantal("aantal2")
            .btw("btw2")
            .tijdtot("tijdtot2")
            .tijdvanaf("tijdvanaf2")
            .totaalprijs("totaalprijs2");
    }

    public static Reservering getReserveringRandomSampleGenerator() {
        return new Reservering()
            .id(longCount.incrementAndGet())
            .aantal(UUID.randomUUID().toString())
            .btw(UUID.randomUUID().toString())
            .tijdtot(UUID.randomUUID().toString())
            .tijdvanaf(UUID.randomUUID().toString())
            .totaalprijs(UUID.randomUUID().toString());
    }
}
