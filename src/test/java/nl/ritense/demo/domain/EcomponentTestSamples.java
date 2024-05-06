package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EcomponentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Ecomponent getEcomponentSample1() {
        return new Ecomponent()
            .id(1L)
            .bedrag("bedrag1")
            .debetcredit("debetcredit1")
            .groep("groep1")
            .groepcode("groepcode1")
            .grootboekcode("grootboekcode1")
            .grootboekomschrijving("grootboekomschrijving1")
            .kostenplaats("kostenplaats1")
            .omschrijving("omschrijving1")
            .rekeningnummer("rekeningnummer1")
            .toelichting("toelichting1");
    }

    public static Ecomponent getEcomponentSample2() {
        return new Ecomponent()
            .id(2L)
            .bedrag("bedrag2")
            .debetcredit("debetcredit2")
            .groep("groep2")
            .groepcode("groepcode2")
            .grootboekcode("grootboekcode2")
            .grootboekomschrijving("grootboekomschrijving2")
            .kostenplaats("kostenplaats2")
            .omschrijving("omschrijving2")
            .rekeningnummer("rekeningnummer2")
            .toelichting("toelichting2");
    }

    public static Ecomponent getEcomponentRandomSampleGenerator() {
        return new Ecomponent()
            .id(longCount.incrementAndGet())
            .bedrag(UUID.randomUUID().toString())
            .debetcredit(UUID.randomUUID().toString())
            .groep(UUID.randomUUID().toString())
            .groepcode(UUID.randomUUID().toString())
            .grootboekcode(UUID.randomUUID().toString())
            .grootboekomschrijving(UUID.randomUUID().toString())
            .kostenplaats(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .rekeningnummer(UUID.randomUUID().toString())
            .toelichting(UUID.randomUUID().toString());
    }
}
