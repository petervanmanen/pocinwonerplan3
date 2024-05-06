package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TrajectactiviteitsoortTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Trajectactiviteitsoort getTrajectactiviteitsoortSample1() {
        return new Trajectactiviteitsoort()
            .id(1L)
            .aanleverensrg("aanleverensrg1")
            .omschrijving("omschrijving1")
            .typetrajectsrg("typetrajectsrg1");
    }

    public static Trajectactiviteitsoort getTrajectactiviteitsoortSample2() {
        return new Trajectactiviteitsoort()
            .id(2L)
            .aanleverensrg("aanleverensrg2")
            .omschrijving("omschrijving2")
            .typetrajectsrg("typetrajectsrg2");
    }

    public static Trajectactiviteitsoort getTrajectactiviteitsoortRandomSampleGenerator() {
        return new Trajectactiviteitsoort()
            .id(longCount.incrementAndGet())
            .aanleverensrg(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .typetrajectsrg(UUID.randomUUID().toString());
    }
}
