package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RekeningnummerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Rekeningnummer getRekeningnummerSample1() {
        return new Rekeningnummer().id(1L).bic("bic1").iban("iban1");
    }

    public static Rekeningnummer getRekeningnummerSample2() {
        return new Rekeningnummer().id(2L).bic("bic2").iban("iban2");
    }

    public static Rekeningnummer getRekeningnummerRandomSampleGenerator() {
        return new Rekeningnummer().id(longCount.incrementAndGet()).bic(UUID.randomUUID().toString()).iban(UUID.randomUUID().toString());
    }
}
