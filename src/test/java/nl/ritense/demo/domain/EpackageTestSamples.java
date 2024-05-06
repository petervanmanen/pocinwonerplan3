package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EpackageTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Epackage getEpackageSample1() {
        return new Epackage().id(1L).naam("naam1").proces("proces1").project("project1").status("status1").toelichting("toelichting1");
    }

    public static Epackage getEpackageSample2() {
        return new Epackage().id(2L).naam("naam2").proces("proces2").project("project2").status("status2").toelichting("toelichting2");
    }

    public static Epackage getEpackageRandomSampleGenerator() {
        return new Epackage()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .proces(UUID.randomUUID().toString())
            .project(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString())
            .toelichting(UUID.randomUUID().toString());
    }
}
