package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DocumenttypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Documenttype getDocumenttypeSample1() {
        return new Documenttype()
            .id(1L)
            .datumbegingeldigheiddocumenttype("datumbegingeldigheiddocumenttype1")
            .datumeindegeldigheiddocumenttype("datumeindegeldigheiddocumenttype1")
            .documentcategorie("documentcategorie1")
            .documenttypeomschrijving("documenttypeomschrijving1")
            .documenttypeomschrijvinggeneriek("documenttypeomschrijvinggeneriek1")
            .documenttypetrefwoord("documenttypetrefwoord1");
    }

    public static Documenttype getDocumenttypeSample2() {
        return new Documenttype()
            .id(2L)
            .datumbegingeldigheiddocumenttype("datumbegingeldigheiddocumenttype2")
            .datumeindegeldigheiddocumenttype("datumeindegeldigheiddocumenttype2")
            .documentcategorie("documentcategorie2")
            .documenttypeomschrijving("documenttypeomschrijving2")
            .documenttypeomschrijvinggeneriek("documenttypeomschrijvinggeneriek2")
            .documenttypetrefwoord("documenttypetrefwoord2");
    }

    public static Documenttype getDocumenttypeRandomSampleGenerator() {
        return new Documenttype()
            .id(longCount.incrementAndGet())
            .datumbegingeldigheiddocumenttype(UUID.randomUUID().toString())
            .datumeindegeldigheiddocumenttype(UUID.randomUUID().toString())
            .documentcategorie(UUID.randomUUID().toString())
            .documenttypeomschrijving(UUID.randomUUID().toString())
            .documenttypeomschrijvinggeneriek(UUID.randomUUID().toString())
            .documenttypetrefwoord(UUID.randomUUID().toString());
    }
}
