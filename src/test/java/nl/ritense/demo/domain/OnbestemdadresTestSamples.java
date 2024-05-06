package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OnbestemdadresTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Onbestemdadres getOnbestemdadresSample1() {
        return new Onbestemdadres()
            .id(1L)
            .huisletter("huisletter1")
            .huisnummer("huisnummer1")
            .huisnummertoevoeging("huisnummertoevoeging1")
            .postcode("postcode1")
            .straatnaam("straatnaam1");
    }

    public static Onbestemdadres getOnbestemdadresSample2() {
        return new Onbestemdadres()
            .id(2L)
            .huisletter("huisletter2")
            .huisnummer("huisnummer2")
            .huisnummertoevoeging("huisnummertoevoeging2")
            .postcode("postcode2")
            .straatnaam("straatnaam2");
    }

    public static Onbestemdadres getOnbestemdadresRandomSampleGenerator() {
        return new Onbestemdadres()
            .id(longCount.incrementAndGet())
            .huisletter(UUID.randomUUID().toString())
            .huisnummer(UUID.randomUUID().toString())
            .huisnummertoevoeging(UUID.randomUUID().toString())
            .postcode(UUID.randomUUID().toString())
            .straatnaam(UUID.randomUUID().toString());
    }
}
