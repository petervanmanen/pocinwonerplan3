package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ValutasoortTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Valutasoort getValutasoortSample1() {
        return new Valutasoort().id(1L).naamvaluta("naamvaluta1").valutacode("valutacode1");
    }

    public static Valutasoort getValutasoortSample2() {
        return new Valutasoort().id(2L).naamvaluta("naamvaluta2").valutacode("valutacode2");
    }

    public static Valutasoort getValutasoortRandomSampleGenerator() {
        return new Valutasoort()
            .id(longCount.incrementAndGet())
            .naamvaluta(UUID.randomUUID().toString())
            .valutacode(UUID.randomUUID().toString());
    }
}
