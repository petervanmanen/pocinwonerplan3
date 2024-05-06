package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BalieverkoopentreekaartTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Balieverkoopentreekaart getBalieverkoopentreekaartSample1() {
        return new Balieverkoopentreekaart()
            .id(1L)
            .datumeindegeldigheid("datumeindegeldigheid1")
            .datumstart("datumstart1")
            .gebruiktop("gebruiktop1")
            .rondleiding("rondleiding1");
    }

    public static Balieverkoopentreekaart getBalieverkoopentreekaartSample2() {
        return new Balieverkoopentreekaart()
            .id(2L)
            .datumeindegeldigheid("datumeindegeldigheid2")
            .datumstart("datumstart2")
            .gebruiktop("gebruiktop2")
            .rondleiding("rondleiding2");
    }

    public static Balieverkoopentreekaart getBalieverkoopentreekaartRandomSampleGenerator() {
        return new Balieverkoopentreekaart()
            .id(longCount.incrementAndGet())
            .datumeindegeldigheid(UUID.randomUUID().toString())
            .datumstart(UUID.randomUUID().toString())
            .gebruiktop(UUID.randomUUID().toString())
            .rondleiding(UUID.randomUUID().toString());
    }
}
