package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BriefadresTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Briefadres getBriefadresSample1() {
        return new Briefadres().id(1L).adresfunctie("adresfunctie1").omschrijvingaangifte("omschrijvingaangifte1");
    }

    public static Briefadres getBriefadresSample2() {
        return new Briefadres().id(2L).adresfunctie("adresfunctie2").omschrijvingaangifte("omschrijvingaangifte2");
    }

    public static Briefadres getBriefadresRandomSampleGenerator() {
        return new Briefadres()
            .id(longCount.incrementAndGet())
            .adresfunctie(UUID.randomUUID().toString())
            .omschrijvingaangifte(UUID.randomUUID().toString());
    }
}
