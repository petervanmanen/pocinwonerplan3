package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MaatschappelijkeactiviteitTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Maatschappelijkeactiviteit getMaatschappelijkeactiviteitSample1() {
        return new Maatschappelijkeactiviteit()
            .id(1L)
            .adresbinnenland("adresbinnenland1")
            .adrescorrespondentie("adrescorrespondentie1")
            .indicatieeconomischactief("indicatieeconomischactief1")
            .kvknummer("kvknummer1")
            .rechtsvorm("rechtsvorm1")
            .rsin("rsin1")
            .statutairenaam("statutairenaam1")
            .telefoonnummer("telefoonnummer1")
            .url("url1");
    }

    public static Maatschappelijkeactiviteit getMaatschappelijkeactiviteitSample2() {
        return new Maatschappelijkeactiviteit()
            .id(2L)
            .adresbinnenland("adresbinnenland2")
            .adrescorrespondentie("adrescorrespondentie2")
            .indicatieeconomischactief("indicatieeconomischactief2")
            .kvknummer("kvknummer2")
            .rechtsvorm("rechtsvorm2")
            .rsin("rsin2")
            .statutairenaam("statutairenaam2")
            .telefoonnummer("telefoonnummer2")
            .url("url2");
    }

    public static Maatschappelijkeactiviteit getMaatschappelijkeactiviteitRandomSampleGenerator() {
        return new Maatschappelijkeactiviteit()
            .id(longCount.incrementAndGet())
            .adresbinnenland(UUID.randomUUID().toString())
            .adrescorrespondentie(UUID.randomUUID().toString())
            .indicatieeconomischactief(UUID.randomUUID().toString())
            .kvknummer(UUID.randomUUID().toString())
            .rechtsvorm(UUID.randomUUID().toString())
            .rsin(UUID.randomUUID().toString())
            .statutairenaam(UUID.randomUUID().toString())
            .telefoonnummer(UUID.randomUUID().toString())
            .url(UUID.randomUUID().toString());
    }
}
