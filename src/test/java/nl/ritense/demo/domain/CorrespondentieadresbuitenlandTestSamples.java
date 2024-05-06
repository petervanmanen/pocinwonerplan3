package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CorrespondentieadresbuitenlandTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Correspondentieadresbuitenland getCorrespondentieadresbuitenlandSample1() {
        return new Correspondentieadresbuitenland()
            .id(1L)
            .adresbuitenland1("adresbuitenland11")
            .adresbuitenland2("adresbuitenland21")
            .adresbuitenland3("adresbuitenland31")
            .adresbuitenland4("adresbuitenland41")
            .adresbuitenland5("adresbuitenland51")
            .adresbuitenland6("adresbuitenland61")
            .landcorrespondentieadres("landcorrespondentieadres1");
    }

    public static Correspondentieadresbuitenland getCorrespondentieadresbuitenlandSample2() {
        return new Correspondentieadresbuitenland()
            .id(2L)
            .adresbuitenland1("adresbuitenland12")
            .adresbuitenland2("adresbuitenland22")
            .adresbuitenland3("adresbuitenland32")
            .adresbuitenland4("adresbuitenland42")
            .adresbuitenland5("adresbuitenland52")
            .adresbuitenland6("adresbuitenland62")
            .landcorrespondentieadres("landcorrespondentieadres2");
    }

    public static Correspondentieadresbuitenland getCorrespondentieadresbuitenlandRandomSampleGenerator() {
        return new Correspondentieadresbuitenland()
            .id(longCount.incrementAndGet())
            .adresbuitenland1(UUID.randomUUID().toString())
            .adresbuitenland2(UUID.randomUUID().toString())
            .adresbuitenland3(UUID.randomUUID().toString())
            .adresbuitenland4(UUID.randomUUID().toString())
            .adresbuitenland5(UUID.randomUUID().toString())
            .adresbuitenland6(UUID.randomUUID().toString())
            .landcorrespondentieadres(UUID.randomUUID().toString());
    }
}
