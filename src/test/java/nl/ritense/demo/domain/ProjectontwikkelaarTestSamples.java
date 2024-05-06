package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProjectontwikkelaarTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Projectontwikkelaar getProjectontwikkelaarSample1() {
        return new Projectontwikkelaar().id(1L).adres("adres1").naam("naam1");
    }

    public static Projectontwikkelaar getProjectontwikkelaarSample2() {
        return new Projectontwikkelaar().id(2L).adres("adres2").naam("naam2");
    }

    public static Projectontwikkelaar getProjectontwikkelaarRandomSampleGenerator() {
        return new Projectontwikkelaar()
            .id(longCount.incrementAndGet())
            .adres(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString());
    }
}
