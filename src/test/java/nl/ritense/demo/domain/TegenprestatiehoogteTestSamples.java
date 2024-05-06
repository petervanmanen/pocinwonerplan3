package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TegenprestatiehoogteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Tegenprestatiehoogte getTegenprestatiehoogteSample1() {
        return new Tegenprestatiehoogte().id(1L).code("code1").naam("naam1").omschrijving("omschrijving1");
    }

    public static Tegenprestatiehoogte getTegenprestatiehoogteSample2() {
        return new Tegenprestatiehoogte().id(2L).code("code2").naam("naam2").omschrijving("omschrijving2");
    }

    public static Tegenprestatiehoogte getTegenprestatiehoogteRandomSampleGenerator() {
        return new Tegenprestatiehoogte()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
