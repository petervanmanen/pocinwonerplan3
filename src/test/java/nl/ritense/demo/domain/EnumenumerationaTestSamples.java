package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class EnumenumerationaTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Enumenumerationa getEnumenumerationaSample1() {
        return new Enumenumerationa().optie1("optie11").optie2("optie21").id(1);
    }

    public static Enumenumerationa getEnumenumerationaSample2() {
        return new Enumenumerationa().optie1("optie12").optie2("optie22").id(2);
    }

    public static Enumenumerationa getEnumenumerationaRandomSampleGenerator() {
        return new Enumenumerationa()
            .optie1(UUID.randomUUID().toString())
            .optie2(UUID.randomUUID().toString())
            .id(intCount.incrementAndGet());
    }
}
