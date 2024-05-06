package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ReisdocumentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Reisdocument getReisdocumentSample1() {
        return new Reisdocument()
            .id(1L)
            .aanduidinginhoudingvermissing("aanduidinginhoudingvermissing1")
            .autoriteitvanafgifte("autoriteitvanafgifte1")
            .datumeindegeldigheiddocument("datumeindegeldigheiddocument1")
            .datumingangdocument("datumingangdocument1")
            .datuminhoudingofvermissing("datuminhoudingofvermissing1")
            .datumuitgifte("datumuitgifte1")
            .reisdocumentnummer("reisdocumentnummer1")
            .soort("soort1");
    }

    public static Reisdocument getReisdocumentSample2() {
        return new Reisdocument()
            .id(2L)
            .aanduidinginhoudingvermissing("aanduidinginhoudingvermissing2")
            .autoriteitvanafgifte("autoriteitvanafgifte2")
            .datumeindegeldigheiddocument("datumeindegeldigheiddocument2")
            .datumingangdocument("datumingangdocument2")
            .datuminhoudingofvermissing("datuminhoudingofvermissing2")
            .datumuitgifte("datumuitgifte2")
            .reisdocumentnummer("reisdocumentnummer2")
            .soort("soort2");
    }

    public static Reisdocument getReisdocumentRandomSampleGenerator() {
        return new Reisdocument()
            .id(longCount.incrementAndGet())
            .aanduidinginhoudingvermissing(UUID.randomUUID().toString())
            .autoriteitvanafgifte(UUID.randomUUID().toString())
            .datumeindegeldigheiddocument(UUID.randomUUID().toString())
            .datumingangdocument(UUID.randomUUID().toString())
            .datuminhoudingofvermissing(UUID.randomUUID().toString())
            .datumuitgifte(UUID.randomUUID().toString())
            .reisdocumentnummer(UUID.randomUUID().toString())
            .soort(UUID.randomUUID().toString());
    }
}
