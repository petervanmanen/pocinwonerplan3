package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AardzakelijkrechtTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Aardzakelijkrecht getAardzakelijkrechtSample1() {
        return new Aardzakelijkrecht()
            .id(1L)
            .codeaardzakelijkrecht("codeaardzakelijkrecht1")
            .naamaardzakelijkrecht("naamaardzakelijkrecht1");
    }

    public static Aardzakelijkrecht getAardzakelijkrechtSample2() {
        return new Aardzakelijkrecht()
            .id(2L)
            .codeaardzakelijkrecht("codeaardzakelijkrecht2")
            .naamaardzakelijkrecht("naamaardzakelijkrecht2");
    }

    public static Aardzakelijkrecht getAardzakelijkrechtRandomSampleGenerator() {
        return new Aardzakelijkrecht()
            .id(longCount.incrementAndGet())
            .codeaardzakelijkrecht(UUID.randomUUID().toString())
            .naamaardzakelijkrecht(UUID.randomUUID().toString());
    }
}
