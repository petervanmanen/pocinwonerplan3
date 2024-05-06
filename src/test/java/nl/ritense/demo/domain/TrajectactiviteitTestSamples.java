package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TrajectactiviteitTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Trajectactiviteit getTrajectactiviteitSample1() {
        return new Trajectactiviteit()
            .id(1L)
            .contract("contract1")
            .crediteur("crediteur1")
            .kinderopvang("kinderopvang1")
            .status("status1")
            .succesvol("succesvol1");
    }

    public static Trajectactiviteit getTrajectactiviteitSample2() {
        return new Trajectactiviteit()
            .id(2L)
            .contract("contract2")
            .crediteur("crediteur2")
            .kinderopvang("kinderopvang2")
            .status("status2")
            .succesvol("succesvol2");
    }

    public static Trajectactiviteit getTrajectactiviteitRandomSampleGenerator() {
        return new Trajectactiviteit()
            .id(longCount.incrementAndGet())
            .contract(UUID.randomUUID().toString())
            .crediteur(UUID.randomUUID().toString())
            .kinderopvang(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString())
            .succesvol(UUID.randomUUID().toString());
    }
}
