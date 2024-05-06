package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class HoofdrekeningTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Hoofdrekening getHoofdrekeningSample1() {
        return new Hoofdrekening()
            .id(1L)
            .naam("naam1")
            .nummer("nummer1")
            .omschrijving("omschrijving1")
            .piahoofcategorieomschrijving("piahoofcategorieomschrijving1")
            .piahoofdcategoriecode("piahoofdcategoriecode1")
            .subcode("subcode1")
            .subcodeomschrijving("subcodeomschrijving1");
    }

    public static Hoofdrekening getHoofdrekeningSample2() {
        return new Hoofdrekening()
            .id(2L)
            .naam("naam2")
            .nummer("nummer2")
            .omschrijving("omschrijving2")
            .piahoofcategorieomschrijving("piahoofcategorieomschrijving2")
            .piahoofdcategoriecode("piahoofdcategoriecode2")
            .subcode("subcode2")
            .subcodeomschrijving("subcodeomschrijving2");
    }

    public static Hoofdrekening getHoofdrekeningRandomSampleGenerator() {
        return new Hoofdrekening()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .nummer(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .piahoofcategorieomschrijving(UUID.randomUUID().toString())
            .piahoofdcategoriecode(UUID.randomUUID().toString())
            .subcode(UUID.randomUUID().toString())
            .subcodeomschrijving(UUID.randomUUID().toString());
    }
}
