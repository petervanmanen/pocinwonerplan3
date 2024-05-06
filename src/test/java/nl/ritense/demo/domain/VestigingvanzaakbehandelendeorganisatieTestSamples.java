package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class VestigingvanzaakbehandelendeorganisatieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Vestigingvanzaakbehandelendeorganisatie getVestigingvanzaakbehandelendeorganisatieSample1() {
        return new Vestigingvanzaakbehandelendeorganisatie().id(1L);
    }

    public static Vestigingvanzaakbehandelendeorganisatie getVestigingvanzaakbehandelendeorganisatieSample2() {
        return new Vestigingvanzaakbehandelendeorganisatie().id(2L);
    }

    public static Vestigingvanzaakbehandelendeorganisatie getVestigingvanzaakbehandelendeorganisatieRandomSampleGenerator() {
        return new Vestigingvanzaakbehandelendeorganisatie().id(longCount.incrementAndGet());
    }
}
