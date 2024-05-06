package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MoormeldingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Moormelding getMoormeldingSample1() {
        return new Moormelding()
            .id(1L)
            .adresaanduiding("adresaanduiding1")
            .datumaanmelding("datumaanmelding1")
            .datumgoedkeuring("datumgoedkeuring1")
            .eindtijd("eindtijd1")
            .omschrijvingherstelwerkzaamheden("omschrijvingherstelwerkzaamheden1")
            .starttijd("starttijd1")
            .wegbeheerder("wegbeheerder1");
    }

    public static Moormelding getMoormeldingSample2() {
        return new Moormelding()
            .id(2L)
            .adresaanduiding("adresaanduiding2")
            .datumaanmelding("datumaanmelding2")
            .datumgoedkeuring("datumgoedkeuring2")
            .eindtijd("eindtijd2")
            .omschrijvingherstelwerkzaamheden("omschrijvingherstelwerkzaamheden2")
            .starttijd("starttijd2")
            .wegbeheerder("wegbeheerder2");
    }

    public static Moormelding getMoormeldingRandomSampleGenerator() {
        return new Moormelding()
            .id(longCount.incrementAndGet())
            .adresaanduiding(UUID.randomUUID().toString())
            .datumaanmelding(UUID.randomUUID().toString())
            .datumgoedkeuring(UUID.randomUUID().toString())
            .eindtijd(UUID.randomUUID().toString())
            .omschrijvingherstelwerkzaamheden(UUID.randomUUID().toString())
            .starttijd(UUID.randomUUID().toString())
            .wegbeheerder(UUID.randomUUID().toString());
    }
}
