package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RedenverliesnationaliteitTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Redenverliesnationaliteit getRedenverliesnationaliteitSample1() {
        return new Redenverliesnationaliteit().id(1L).omschrijvingverlies("omschrijvingverlies1").redennummerverlies("redennummerverlies1");
    }

    public static Redenverliesnationaliteit getRedenverliesnationaliteitSample2() {
        return new Redenverliesnationaliteit().id(2L).omschrijvingverlies("omschrijvingverlies2").redennummerverlies("redennummerverlies2");
    }

    public static Redenverliesnationaliteit getRedenverliesnationaliteitRandomSampleGenerator() {
        return new Redenverliesnationaliteit()
            .id(longCount.incrementAndGet())
            .omschrijvingverlies(UUID.randomUUID().toString())
            .redennummerverlies(UUID.randomUUID().toString());
    }
}
