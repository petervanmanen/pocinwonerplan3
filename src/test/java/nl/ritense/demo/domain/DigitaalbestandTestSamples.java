package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DigitaalbestandTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Digitaalbestand getDigitaalbestandSample1() {
        return new Digitaalbestand().id(1L).blob("blob1").mimetype("mimetype1").naam("naam1").omschrijving("omschrijving1");
    }

    public static Digitaalbestand getDigitaalbestandSample2() {
        return new Digitaalbestand().id(2L).blob("blob2").mimetype("mimetype2").naam("naam2").omschrijving("omschrijving2");
    }

    public static Digitaalbestand getDigitaalbestandRandomSampleGenerator() {
        return new Digitaalbestand()
            .id(longCount.incrementAndGet())
            .blob(UUID.randomUUID().toString())
            .mimetype(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
