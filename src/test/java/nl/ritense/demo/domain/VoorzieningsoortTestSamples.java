package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VoorzieningsoortTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Voorzieningsoort getVoorzieningsoortSample1() {
        return new Voorzieningsoort()
            .id(1L)
            .code("code1")
            .naam("naam1")
            .omschrijving("omschrijving1")
            .productcategorie("productcategorie1")
            .productcategoriecode("productcategoriecode1")
            .productcode("productcode1")
            .wet("wet1");
    }

    public static Voorzieningsoort getVoorzieningsoortSample2() {
        return new Voorzieningsoort()
            .id(2L)
            .code("code2")
            .naam("naam2")
            .omschrijving("omschrijving2")
            .productcategorie("productcategorie2")
            .productcategoriecode("productcategoriecode2")
            .productcode("productcode2")
            .wet("wet2");
    }

    public static Voorzieningsoort getVoorzieningsoortRandomSampleGenerator() {
        return new Voorzieningsoort()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .productcategorie(UUID.randomUUID().toString())
            .productcategoriecode(UUID.randomUUID().toString())
            .productcode(UUID.randomUUID().toString())
            .wet(UUID.randomUUID().toString());
    }
}
