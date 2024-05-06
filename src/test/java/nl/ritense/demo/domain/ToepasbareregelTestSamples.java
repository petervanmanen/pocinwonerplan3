package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ToepasbareregelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Toepasbareregel getToepasbareregelSample1() {
        return new Toepasbareregel()
            .id(1L)
            .domein("domein1")
            .naam("naam1")
            .omschrijving("omschrijving1")
            .soortaansluitpunt("soortaansluitpunt1")
            .toestemming("toestemming1");
    }

    public static Toepasbareregel getToepasbareregelSample2() {
        return new Toepasbareregel()
            .id(2L)
            .domein("domein2")
            .naam("naam2")
            .omschrijving("omschrijving2")
            .soortaansluitpunt("soortaansluitpunt2")
            .toestemming("toestemming2");
    }

    public static Toepasbareregel getToepasbareregelRandomSampleGenerator() {
        return new Toepasbareregel()
            .id(longCount.incrementAndGet())
            .domein(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .soortaansluitpunt(UUID.randomUUID().toString())
            .toestemming(UUID.randomUUID().toString());
    }
}
