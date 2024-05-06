package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OnderwijssoortTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Onderwijssoort getOnderwijssoortSample1() {
        return new Onderwijssoort().id(1L).omschrijving("omschrijving1").onderwijstype("onderwijstype1");
    }

    public static Onderwijssoort getOnderwijssoortSample2() {
        return new Onderwijssoort().id(2L).omschrijving("omschrijving2").onderwijstype("onderwijstype2");
    }

    public static Onderwijssoort getOnderwijssoortRandomSampleGenerator() {
        return new Onderwijssoort()
            .id(longCount.incrementAndGet())
            .omschrijving(UUID.randomUUID().toString())
            .onderwijstype(UUID.randomUUID().toString());
    }
}
