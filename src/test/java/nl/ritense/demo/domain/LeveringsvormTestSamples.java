package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LeveringsvormTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Leveringsvorm getLeveringsvormSample1() {
        return new Leveringsvorm().id(1L).leveringsvormcode("leveringsvormcode1").naam("naam1").wet("wet1");
    }

    public static Leveringsvorm getLeveringsvormSample2() {
        return new Leveringsvorm().id(2L).leveringsvormcode("leveringsvormcode2").naam("naam2").wet("wet2");
    }

    public static Leveringsvorm getLeveringsvormRandomSampleGenerator() {
        return new Leveringsvorm()
            .id(longCount.incrementAndGet())
            .leveringsvormcode(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .wet(UUID.randomUUID().toString());
    }
}
