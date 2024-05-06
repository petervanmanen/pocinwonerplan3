package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VerblijfbuitenlandTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Verblijfbuitenland getVerblijfbuitenlandSample1() {
        return new Verblijfbuitenland()
            .id(1L)
            .adresregelbuitenland1("adresregelbuitenland11")
            .adresregelbuitenland2("adresregelbuitenland21")
            .adresregelbuitenland3("adresregelbuitenland31")
            .adresregelbuitenland4("adresregelbuitenland41")
            .adresregelbuitenland5("adresregelbuitenland51")
            .adresregelbuitenland6("adresregelbuitenland61")
            .landofgebiedverblijfadres("landofgebiedverblijfadres1");
    }

    public static Verblijfbuitenland getVerblijfbuitenlandSample2() {
        return new Verblijfbuitenland()
            .id(2L)
            .adresregelbuitenland1("adresregelbuitenland12")
            .adresregelbuitenland2("adresregelbuitenland22")
            .adresregelbuitenland3("adresregelbuitenland32")
            .adresregelbuitenland4("adresregelbuitenland42")
            .adresregelbuitenland5("adresregelbuitenland52")
            .adresregelbuitenland6("adresregelbuitenland62")
            .landofgebiedverblijfadres("landofgebiedverblijfadres2");
    }

    public static Verblijfbuitenland getVerblijfbuitenlandRandomSampleGenerator() {
        return new Verblijfbuitenland()
            .id(longCount.incrementAndGet())
            .adresregelbuitenland1(UUID.randomUUID().toString())
            .adresregelbuitenland2(UUID.randomUUID().toString())
            .adresregelbuitenland3(UUID.randomUUID().toString())
            .adresregelbuitenland4(UUID.randomUUID().toString())
            .adresregelbuitenland5(UUID.randomUUID().toString())
            .adresregelbuitenland6(UUID.randomUUID().toString())
            .landofgebiedverblijfadres(UUID.randomUUID().toString());
    }
}
