package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BeschermdestatusTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Beschermdestatus getBeschermdestatusSample1() {
        return new Beschermdestatus()
            .id(1L)
            .bronnen("bronnen1")
            .complex("complex1")
            .gemeentelijkmonumentcode("gemeentelijkmonumentcode1")
            .gezichtscode("gezichtscode1")
            .naam("naam1")
            .omschrijving("omschrijving1")
            .opmerkingen("opmerkingen1")
            .rijksmonumentcode("rijksmonumentcode1")
            .type("type1");
    }

    public static Beschermdestatus getBeschermdestatusSample2() {
        return new Beschermdestatus()
            .id(2L)
            .bronnen("bronnen2")
            .complex("complex2")
            .gemeentelijkmonumentcode("gemeentelijkmonumentcode2")
            .gezichtscode("gezichtscode2")
            .naam("naam2")
            .omschrijving("omschrijving2")
            .opmerkingen("opmerkingen2")
            .rijksmonumentcode("rijksmonumentcode2")
            .type("type2");
    }

    public static Beschermdestatus getBeschermdestatusRandomSampleGenerator() {
        return new Beschermdestatus()
            .id(longCount.incrementAndGet())
            .bronnen(UUID.randomUUID().toString())
            .complex(UUID.randomUUID().toString())
            .gemeentelijkmonumentcode(UUID.randomUUID().toString())
            .gezichtscode(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .opmerkingen(UUID.randomUUID().toString())
            .rijksmonumentcode(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString());
    }
}
