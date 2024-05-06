package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class NietnatuurlijkpersoonTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Nietnatuurlijkpersoon getNietnatuurlijkpersoonSample1() {
        return new Nietnatuurlijkpersoon()
            .id(1L)
            .faxnummer("faxnummer1")
            .kvknummer("kvknummer1")
            .nnpid("nnpid1")
            .rechtsvorm("rechtsvorm1")
            .rsinnummer("rsinnummer1")
            .statutairenaam("statutairenaam1")
            .statutairezetel("statutairezetel1")
            .websiteurl("websiteurl1");
    }

    public static Nietnatuurlijkpersoon getNietnatuurlijkpersoonSample2() {
        return new Nietnatuurlijkpersoon()
            .id(2L)
            .faxnummer("faxnummer2")
            .kvknummer("kvknummer2")
            .nnpid("nnpid2")
            .rechtsvorm("rechtsvorm2")
            .rsinnummer("rsinnummer2")
            .statutairenaam("statutairenaam2")
            .statutairezetel("statutairezetel2")
            .websiteurl("websiteurl2");
    }

    public static Nietnatuurlijkpersoon getNietnatuurlijkpersoonRandomSampleGenerator() {
        return new Nietnatuurlijkpersoon()
            .id(longCount.incrementAndGet())
            .faxnummer(UUID.randomUUID().toString())
            .kvknummer(UUID.randomUUID().toString())
            .nnpid(UUID.randomUUID().toString())
            .rechtsvorm(UUID.randomUUID().toString())
            .rsinnummer(UUID.randomUUID().toString())
            .statutairenaam(UUID.randomUUID().toString())
            .statutairezetel(UUID.randomUUID().toString())
            .websiteurl(UUID.randomUUID().toString());
    }
}
