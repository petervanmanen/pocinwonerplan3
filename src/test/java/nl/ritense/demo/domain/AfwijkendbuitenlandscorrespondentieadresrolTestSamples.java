package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AfwijkendbuitenlandscorrespondentieadresrolTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Afwijkendbuitenlandscorrespondentieadresrol getAfwijkendbuitenlandscorrespondentieadresrolSample1() {
        return new Afwijkendbuitenlandscorrespondentieadresrol()
            .id(1L)
            .adresbuitenland1("adresbuitenland11")
            .adresbuitenland2("adresbuitenland21")
            .adresbuitenland3("adresbuitenland31")
            .landpostadres("landpostadres1");
    }

    public static Afwijkendbuitenlandscorrespondentieadresrol getAfwijkendbuitenlandscorrespondentieadresrolSample2() {
        return new Afwijkendbuitenlandscorrespondentieadresrol()
            .id(2L)
            .adresbuitenland1("adresbuitenland12")
            .adresbuitenland2("adresbuitenland22")
            .adresbuitenland3("adresbuitenland32")
            .landpostadres("landpostadres2");
    }

    public static Afwijkendbuitenlandscorrespondentieadresrol getAfwijkendbuitenlandscorrespondentieadresrolRandomSampleGenerator() {
        return new Afwijkendbuitenlandscorrespondentieadresrol()
            .id(longCount.incrementAndGet())
            .adresbuitenland1(UUID.randomUUID().toString())
            .adresbuitenland2(UUID.randomUUID().toString())
            .adresbuitenland3(UUID.randomUUID().toString())
            .landpostadres(UUID.randomUUID().toString());
    }
}
