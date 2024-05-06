package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AutoriteitafgiftenederlandsreisdocumentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Autoriteitafgiftenederlandsreisdocument getAutoriteitafgiftenederlandsreisdocumentSample1() {
        return new Autoriteitafgiftenederlandsreisdocument().id(1L).code("code1").omschrijving("omschrijving1");
    }

    public static Autoriteitafgiftenederlandsreisdocument getAutoriteitafgiftenederlandsreisdocumentSample2() {
        return new Autoriteitafgiftenederlandsreisdocument().id(2L).code("code2").omschrijving("omschrijving2");
    }

    public static Autoriteitafgiftenederlandsreisdocument getAutoriteitafgiftenederlandsreisdocumentRandomSampleGenerator() {
        return new Autoriteitafgiftenederlandsreisdocument()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}
