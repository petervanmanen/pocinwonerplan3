package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StrooidagTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Strooidag getStrooidagSample1() {
        return new Strooidag()
            .id(1L)
            .maximumtemperatuur("maximumtemperatuur1")
            .minimumtemperatuur("minimumtemperatuur1")
            .tijdmaximumtemperatuur("tijdmaximumtemperatuur1")
            .tijdminimumtemperatuur("tijdminimumtemperatuur1");
    }

    public static Strooidag getStrooidagSample2() {
        return new Strooidag()
            .id(2L)
            .maximumtemperatuur("maximumtemperatuur2")
            .minimumtemperatuur("minimumtemperatuur2")
            .tijdmaximumtemperatuur("tijdmaximumtemperatuur2")
            .tijdminimumtemperatuur("tijdminimumtemperatuur2");
    }

    public static Strooidag getStrooidagRandomSampleGenerator() {
        return new Strooidag()
            .id(longCount.incrementAndGet())
            .maximumtemperatuur(UUID.randomUUID().toString())
            .minimumtemperatuur(UUID.randomUUID().toString())
            .tijdmaximumtemperatuur(UUID.randomUUID().toString())
            .tijdminimumtemperatuur(UUID.randomUUID().toString());
    }
}
