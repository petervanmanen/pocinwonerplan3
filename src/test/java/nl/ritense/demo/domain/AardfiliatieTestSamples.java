package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AardfiliatieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Aardfiliatie getAardfiliatieSample1() {
        return new Aardfiliatie().id(1L).codeaardfiliatie("codeaardfiliatie1").naamaardfiliatie("naamaardfiliatie1");
    }

    public static Aardfiliatie getAardfiliatieSample2() {
        return new Aardfiliatie().id(2L).codeaardfiliatie("codeaardfiliatie2").naamaardfiliatie("naamaardfiliatie2");
    }

    public static Aardfiliatie getAardfiliatieRandomSampleGenerator() {
        return new Aardfiliatie()
            .id(longCount.incrementAndGet())
            .codeaardfiliatie(UUID.randomUUID().toString())
            .naamaardfiliatie(UUID.randomUUID().toString());
    }
}
