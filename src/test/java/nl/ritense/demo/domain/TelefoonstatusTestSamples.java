package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TelefoonstatusTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Telefoonstatus getTelefoonstatusSample1() {
        return new Telefoonstatus().id(1L).contactconnectionstate("contactconnectionstate1").status("status1");
    }

    public static Telefoonstatus getTelefoonstatusSample2() {
        return new Telefoonstatus().id(2L).contactconnectionstate("contactconnectionstate2").status("status2");
    }

    public static Telefoonstatus getTelefoonstatusRandomSampleGenerator() {
        return new Telefoonstatus()
            .id(longCount.incrementAndGet())
            .contactconnectionstate(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString());
    }
}
