package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AommeldingwmojeugdTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Aommeldingwmojeugd getAommeldingwmojeugdSample1() {
        return new Aommeldingwmojeugd()
            .id(1L)
            .aanmelder("aanmelder1")
            .aanmeldingdoor("aanmeldingdoor1")
            .aanmeldingdoorlandelijk("aanmeldingdoorlandelijk1")
            .aanmeldwijze("aanmeldwijze1")
            .deskundigheid("deskundigheid1")
            .isclientopdehoogte("isclientopdehoogte1")
            .onderzoekswijze("onderzoekswijze1")
            .redenafsluiting("redenafsluiting1")
            .vervolg("vervolg1")
            .verwezen("verwezen1");
    }

    public static Aommeldingwmojeugd getAommeldingwmojeugdSample2() {
        return new Aommeldingwmojeugd()
            .id(2L)
            .aanmelder("aanmelder2")
            .aanmeldingdoor("aanmeldingdoor2")
            .aanmeldingdoorlandelijk("aanmeldingdoorlandelijk2")
            .aanmeldwijze("aanmeldwijze2")
            .deskundigheid("deskundigheid2")
            .isclientopdehoogte("isclientopdehoogte2")
            .onderzoekswijze("onderzoekswijze2")
            .redenafsluiting("redenafsluiting2")
            .vervolg("vervolg2")
            .verwezen("verwezen2");
    }

    public static Aommeldingwmojeugd getAommeldingwmojeugdRandomSampleGenerator() {
        return new Aommeldingwmojeugd()
            .id(longCount.incrementAndGet())
            .aanmelder(UUID.randomUUID().toString())
            .aanmeldingdoor(UUID.randomUUID().toString())
            .aanmeldingdoorlandelijk(UUID.randomUUID().toString())
            .aanmeldwijze(UUID.randomUUID().toString())
            .deskundigheid(UUID.randomUUID().toString())
            .isclientopdehoogte(UUID.randomUUID().toString())
            .onderzoekswijze(UUID.randomUUID().toString())
            .redenafsluiting(UUID.randomUUID().toString())
            .vervolg(UUID.randomUUID().toString())
            .verwezen(UUID.randomUUID().toString());
    }
}
