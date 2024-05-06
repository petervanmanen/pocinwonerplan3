package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class NormwaardeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Normwaarde getNormwaardeSample1() {
        return new Normwaarde()
            .id(1L)
            .kwalitatievewaarde("kwalitatievewaarde1")
            .kwantitatievewaardeeenheid("kwantitatievewaardeeenheid1")
            .kwantitatievewaardeomvang("kwantitatievewaardeomvang1");
    }

    public static Normwaarde getNormwaardeSample2() {
        return new Normwaarde()
            .id(2L)
            .kwalitatievewaarde("kwalitatievewaarde2")
            .kwantitatievewaardeeenheid("kwantitatievewaardeeenheid2")
            .kwantitatievewaardeomvang("kwantitatievewaardeomvang2");
    }

    public static Normwaarde getNormwaardeRandomSampleGenerator() {
        return new Normwaarde()
            .id(longCount.incrementAndGet())
            .kwalitatievewaarde(UUID.randomUUID().toString())
            .kwantitatievewaardeeenheid(UUID.randomUUID().toString())
            .kwantitatievewaardeomvang(UUID.randomUUID().toString());
    }
}
