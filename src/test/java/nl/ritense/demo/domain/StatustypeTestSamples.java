package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StatustypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Statustype getStatustypeSample1() {
        return new Statustype()
            .id(1L)
            .datumbegingeldigheidstatustype("datumbegingeldigheidstatustype1")
            .datumeindegeldigheidstatustype("datumeindegeldigheidstatustype1")
            .doorlooptijdstatus("doorlooptijdstatus1")
            .statustypeomschrijving("statustypeomschrijving1")
            .statustypeomschrijvinggeneriek("statustypeomschrijvinggeneriek1")
            .statustypevolgnummer("statustypevolgnummer1");
    }

    public static Statustype getStatustypeSample2() {
        return new Statustype()
            .id(2L)
            .datumbegingeldigheidstatustype("datumbegingeldigheidstatustype2")
            .datumeindegeldigheidstatustype("datumeindegeldigheidstatustype2")
            .doorlooptijdstatus("doorlooptijdstatus2")
            .statustypeomschrijving("statustypeomschrijving2")
            .statustypeomschrijvinggeneriek("statustypeomschrijvinggeneriek2")
            .statustypevolgnummer("statustypevolgnummer2");
    }

    public static Statustype getStatustypeRandomSampleGenerator() {
        return new Statustype()
            .id(longCount.incrementAndGet())
            .datumbegingeldigheidstatustype(UUID.randomUUID().toString())
            .datumeindegeldigheidstatustype(UUID.randomUUID().toString())
            .doorlooptijdstatus(UUID.randomUUID().toString())
            .statustypeomschrijving(UUID.randomUUID().toString())
            .statustypeomschrijvinggeneriek(UUID.randomUUID().toString())
            .statustypevolgnummer(UUID.randomUUID().toString());
    }
}
