package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AanvraaginkooporderTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Aanvraaginkooporder getAanvraaginkooporderSample1() {
        return new Aanvraaginkooporder()
            .id(1L)
            .betalingovermeerjaren("betalingovermeerjaren1")
            .correspondentienummer("correspondentienummer1")
            .inhuuranders("inhuuranders1")
            .leveringofdienst("leveringofdienst1")
            .omschrijving("omschrijving1")
            .onderwerp("onderwerp1")
            .reactie("reactie1")
            .status("status1")
            .wijzevaninhuur("wijzevaninhuur1");
    }

    public static Aanvraaginkooporder getAanvraaginkooporderSample2() {
        return new Aanvraaginkooporder()
            .id(2L)
            .betalingovermeerjaren("betalingovermeerjaren2")
            .correspondentienummer("correspondentienummer2")
            .inhuuranders("inhuuranders2")
            .leveringofdienst("leveringofdienst2")
            .omschrijving("omschrijving2")
            .onderwerp("onderwerp2")
            .reactie("reactie2")
            .status("status2")
            .wijzevaninhuur("wijzevaninhuur2");
    }

    public static Aanvraaginkooporder getAanvraaginkooporderRandomSampleGenerator() {
        return new Aanvraaginkooporder()
            .id(longCount.incrementAndGet())
            .betalingovermeerjaren(UUID.randomUUID().toString())
            .correspondentienummer(UUID.randomUUID().toString())
            .inhuuranders(UUID.randomUUID().toString())
            .leveringofdienst(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .onderwerp(UUID.randomUUID().toString())
            .reactie(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString())
            .wijzevaninhuur(UUID.randomUUID().toString());
    }
}
