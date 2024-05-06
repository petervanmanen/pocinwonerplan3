package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class InkooporderTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Inkooporder getInkooporderSample1() {
        return new Inkooporder()
            .id(1L)
            .artikelcode("artikelcode1")
            .betreft("betreft1")
            .datumeinde("datumeinde1")
            .datumingediend("datumingediend1")
            .datumstart("datumstart1")
            .goederencode("goederencode1")
            .omschrijving("omschrijving1")
            .ordernummer("ordernummer1")
            .wijzevanaanbesteden("wijzevanaanbesteden1");
    }

    public static Inkooporder getInkooporderSample2() {
        return new Inkooporder()
            .id(2L)
            .artikelcode("artikelcode2")
            .betreft("betreft2")
            .datumeinde("datumeinde2")
            .datumingediend("datumingediend2")
            .datumstart("datumstart2")
            .goederencode("goederencode2")
            .omschrijving("omschrijving2")
            .ordernummer("ordernummer2")
            .wijzevanaanbesteden("wijzevanaanbesteden2");
    }

    public static Inkooporder getInkooporderRandomSampleGenerator() {
        return new Inkooporder()
            .id(longCount.incrementAndGet())
            .artikelcode(UUID.randomUUID().toString())
            .betreft(UUID.randomUUID().toString())
            .datumeinde(UUID.randomUUID().toString())
            .datumingediend(UUID.randomUUID().toString())
            .datumstart(UUID.randomUUID().toString())
            .goederencode(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .ordernummer(UUID.randomUUID().toString())
            .wijzevanaanbesteden(UUID.randomUUID().toString());
    }
}
