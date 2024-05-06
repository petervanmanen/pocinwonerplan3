package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RioolputTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Rioolput getRioolputSample1() {
        return new Rioolput()
            .id(1L)
            .aantalbedrijven("aantalbedrijven1")
            .aantalrecreatie("aantalrecreatie1")
            .aantalwoningen("aantalwoningen1")
            .afvoerendoppervlak("afvoerendoppervlak1")
            .bergendoppervlak("bergendoppervlak1")
            .rioolputconstructieonderdeel("rioolputconstructieonderdeel1")
            .rioolputrioolleiding("rioolputrioolleiding1")
            .risicogebied("risicogebied1")
            .toegangbreedte("toegangbreedte1")
            .toeganglengte("toeganglengte1")
            .type("type1")
            .typeplus("typeplus1");
    }

    public static Rioolput getRioolputSample2() {
        return new Rioolput()
            .id(2L)
            .aantalbedrijven("aantalbedrijven2")
            .aantalrecreatie("aantalrecreatie2")
            .aantalwoningen("aantalwoningen2")
            .afvoerendoppervlak("afvoerendoppervlak2")
            .bergendoppervlak("bergendoppervlak2")
            .rioolputconstructieonderdeel("rioolputconstructieonderdeel2")
            .rioolputrioolleiding("rioolputrioolleiding2")
            .risicogebied("risicogebied2")
            .toegangbreedte("toegangbreedte2")
            .toeganglengte("toeganglengte2")
            .type("type2")
            .typeplus("typeplus2");
    }

    public static Rioolput getRioolputRandomSampleGenerator() {
        return new Rioolput()
            .id(longCount.incrementAndGet())
            .aantalbedrijven(UUID.randomUUID().toString())
            .aantalrecreatie(UUID.randomUUID().toString())
            .aantalwoningen(UUID.randomUUID().toString())
            .afvoerendoppervlak(UUID.randomUUID().toString())
            .bergendoppervlak(UUID.randomUUID().toString())
            .rioolputconstructieonderdeel(UUID.randomUUID().toString())
            .rioolputrioolleiding(UUID.randomUUID().toString())
            .risicogebied(UUID.randomUUID().toString())
            .toegangbreedte(UUID.randomUUID().toString())
            .toeganglengte(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString())
            .typeplus(UUID.randomUUID().toString());
    }
}
