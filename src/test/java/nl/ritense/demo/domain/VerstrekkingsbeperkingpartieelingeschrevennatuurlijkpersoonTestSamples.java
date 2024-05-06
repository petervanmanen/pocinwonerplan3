package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon getVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonSample1() {
        return new Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon()
            .id(1L)
            .gemeenteverordening("gemeenteverordening1")
            .omschrijvingderde("omschrijvingderde1")
            .partij("partij1");
    }

    public static Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon getVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonSample2() {
        return new Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon()
            .id(2L)
            .gemeenteverordening("gemeenteverordening2")
            .omschrijvingderde("omschrijvingderde2")
            .partij("partij2");
    }

    public static Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon getVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRandomSampleGenerator() {
        return new Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon()
            .id(longCount.incrementAndGet())
            .gemeenteverordening(UUID.randomUUID().toString())
            .omschrijvingderde(UUID.randomUUID().toString())
            .partij(UUID.randomUUID().toString());
    }
}
