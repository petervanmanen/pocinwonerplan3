package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VerblijfbuitenlandsubjectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Verblijfbuitenlandsubject getVerblijfbuitenlandsubjectSample1() {
        return new Verblijfbuitenlandsubject()
            .id(1L)
            .adresbuitenland1("adresbuitenland11")
            .adresbuitenland2("adresbuitenland21")
            .adresbuitenland3("adresbuitenland31")
            .landverblijfadres("landverblijfadres1");
    }

    public static Verblijfbuitenlandsubject getVerblijfbuitenlandsubjectSample2() {
        return new Verblijfbuitenlandsubject()
            .id(2L)
            .adresbuitenland1("adresbuitenland12")
            .adresbuitenland2("adresbuitenland22")
            .adresbuitenland3("adresbuitenland32")
            .landverblijfadres("landverblijfadres2");
    }

    public static Verblijfbuitenlandsubject getVerblijfbuitenlandsubjectRandomSampleGenerator() {
        return new Verblijfbuitenlandsubject()
            .id(longCount.incrementAndGet())
            .adresbuitenland1(UUID.randomUUID().toString())
            .adresbuitenland2(UUID.randomUUID().toString())
            .adresbuitenland3(UUID.randomUUID().toString())
            .landverblijfadres(UUID.randomUUID().toString());
    }
}
