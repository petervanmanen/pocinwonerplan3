package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AomstatusTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Aomstatus getAomstatusSample1() {
        return new Aomstatus().id(1L).status("status1").statuscode("statuscode1").statusvolgorde("statusvolgorde1");
    }

    public static Aomstatus getAomstatusSample2() {
        return new Aomstatus().id(2L).status("status2").statuscode("statuscode2").statusvolgorde("statusvolgorde2");
    }

    public static Aomstatus getAomstatusRandomSampleGenerator() {
        return new Aomstatus()
            .id(longCount.incrementAndGet())
            .status(UUID.randomUUID().toString())
            .statuscode(UUID.randomUUID().toString())
            .statusvolgorde(UUID.randomUUID().toString());
    }
}
