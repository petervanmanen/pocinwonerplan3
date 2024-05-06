package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SoortkunstwerkTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Soortkunstwerk getSoortkunstwerkSample1() {
        return new Soortkunstwerk().id(1L).indicatieplusbrpopulatie("indicatieplusbrpopulatie1").typekunstwerk("typekunstwerk1");
    }

    public static Soortkunstwerk getSoortkunstwerkSample2() {
        return new Soortkunstwerk().id(2L).indicatieplusbrpopulatie("indicatieplusbrpopulatie2").typekunstwerk("typekunstwerk2");
    }

    public static Soortkunstwerk getSoortkunstwerkRandomSampleGenerator() {
        return new Soortkunstwerk()
            .id(longCount.incrementAndGet())
            .indicatieplusbrpopulatie(UUID.randomUUID().toString())
            .typekunstwerk(UUID.randomUUID().toString());
    }
}
