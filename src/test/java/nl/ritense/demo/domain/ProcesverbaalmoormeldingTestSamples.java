package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProcesverbaalmoormeldingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Procesverbaalmoormelding getProcesverbaalmoormeldingSample1() {
        return new Procesverbaalmoormelding().id(1L).datum("datum1").opmerkingen("opmerkingen1");
    }

    public static Procesverbaalmoormelding getProcesverbaalmoormeldingSample2() {
        return new Procesverbaalmoormelding().id(2L).datum("datum2").opmerkingen("opmerkingen2");
    }

    public static Procesverbaalmoormelding getProcesverbaalmoormeldingRandomSampleGenerator() {
        return new Procesverbaalmoormelding()
            .id(longCount.incrementAndGet())
            .datum(UUID.randomUUID().toString())
            .opmerkingen(UUID.randomUUID().toString());
    }
}
