package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DocumentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Document getDocumentSample1() {
        return new Document()
            .id(1L)
            .cocumentbeschrijving("cocumentbeschrijving1")
            .datumcreatiedocument("datumcreatiedocument1")
            .datumontvangstdocument("datumontvangstdocument1")
            .datumverzendingdocument("datumverzendingdocument1")
            .documentauteur("documentauteur1")
            .documentidentificatie("documentidentificatie1")
            .documenttitel("documenttitel1")
            .vertrouwelijkaanduiding("vertrouwelijkaanduiding1");
    }

    public static Document getDocumentSample2() {
        return new Document()
            .id(2L)
            .cocumentbeschrijving("cocumentbeschrijving2")
            .datumcreatiedocument("datumcreatiedocument2")
            .datumontvangstdocument("datumontvangstdocument2")
            .datumverzendingdocument("datumverzendingdocument2")
            .documentauteur("documentauteur2")
            .documentidentificatie("documentidentificatie2")
            .documenttitel("documenttitel2")
            .vertrouwelijkaanduiding("vertrouwelijkaanduiding2");
    }

    public static Document getDocumentRandomSampleGenerator() {
        return new Document()
            .id(longCount.incrementAndGet())
            .cocumentbeschrijving(UUID.randomUUID().toString())
            .datumcreatiedocument(UUID.randomUUID().toString())
            .datumontvangstdocument(UUID.randomUUID().toString())
            .datumverzendingdocument(UUID.randomUUID().toString())
            .documentauteur(UUID.randomUUID().toString())
            .documentidentificatie(UUID.randomUUID().toString())
            .documenttitel(UUID.randomUUID().toString())
            .vertrouwelijkaanduiding(UUID.randomUUID().toString());
    }
}
