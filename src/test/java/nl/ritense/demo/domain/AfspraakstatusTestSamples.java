package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AfspraakstatusTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Afspraakstatus getAfspraakstatusSample1() {
        return new Afspraakstatus().id(1L).status("status1");
    }

    public static Afspraakstatus getAfspraakstatusSample2() {
        return new Afspraakstatus().id(2L).status("status2");
    }

    public static Afspraakstatus getAfspraakstatusRandomSampleGenerator() {
        return new Afspraakstatus().id(longCount.incrementAndGet()).status(UUID.randomUUID().toString());
    }
}
