package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CaseaanmeldingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Caseaanmelding getCaseaanmeldingSample1() {
        return new Caseaanmelding().id(1L).datum("datum1");
    }

    public static Caseaanmelding getCaseaanmeldingSample2() {
        return new Caseaanmelding().id(2L).datum("datum2");
    }

    public static Caseaanmelding getCaseaanmeldingRandomSampleGenerator() {
        return new Caseaanmelding().id(longCount.incrementAndGet()).datum(UUID.randomUUID().toString());
    }
}
