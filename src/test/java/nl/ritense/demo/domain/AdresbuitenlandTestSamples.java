package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AdresbuitenlandTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Adresbuitenland getAdresbuitenlandSample1() {
        return new Adresbuitenland()
            .id(1L)
            .adresregelbuitenland1("adresregelbuitenland11")
            .adresregelbuitenland2("adresregelbuitenland21")
            .adresregelbuitenland3("adresregelbuitenland31")
            .gemeentevaninschrijving("gemeentevaninschrijving1")
            .landadresbuitenland("landadresbuitenland1")
            .landwaarvandaaningeschreven("landwaarvandaaningeschreven1")
            .omschrijvingvandeaangifteadreshouding("omschrijvingvandeaangifteadreshouding1");
    }

    public static Adresbuitenland getAdresbuitenlandSample2() {
        return new Adresbuitenland()
            .id(2L)
            .adresregelbuitenland1("adresregelbuitenland12")
            .adresregelbuitenland2("adresregelbuitenland22")
            .adresregelbuitenland3("adresregelbuitenland32")
            .gemeentevaninschrijving("gemeentevaninschrijving2")
            .landadresbuitenland("landadresbuitenland2")
            .landwaarvandaaningeschreven("landwaarvandaaningeschreven2")
            .omschrijvingvandeaangifteadreshouding("omschrijvingvandeaangifteadreshouding2");
    }

    public static Adresbuitenland getAdresbuitenlandRandomSampleGenerator() {
        return new Adresbuitenland()
            .id(longCount.incrementAndGet())
            .adresregelbuitenland1(UUID.randomUUID().toString())
            .adresregelbuitenland2(UUID.randomUUID().toString())
            .adresregelbuitenland3(UUID.randomUUID().toString())
            .gemeentevaninschrijving(UUID.randomUUID().toString())
            .landadresbuitenland(UUID.randomUUID().toString())
            .landwaarvandaaningeschreven(UUID.randomUUID().toString())
            .omschrijvingvandeaangifteadreshouding(UUID.randomUUID().toString());
    }
}
