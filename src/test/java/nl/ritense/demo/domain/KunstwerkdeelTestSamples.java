package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class KunstwerkdeelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Kunstwerkdeel getKunstwerkdeelSample1() {
        return new Kunstwerkdeel()
            .id(1L)
            .geometriekunstwerkdeel("geometriekunstwerkdeel1")
            .identificatiekunstwerkdeel("identificatiekunstwerkdeel1")
            .lod0geometriekunstwerkdeel("lod0geometriekunstwerkdeel1")
            .lod1geometriekunstwerkdeel("lod1geometriekunstwerkdeel1")
            .lod2geometriekunstwerkdeel("lod2geometriekunstwerkdeel1")
            .lod3geometriekunstwerkdeel("lod3geometriekunstwerkdeel1")
            .relatievehoogteliggingkunstwerkdeel("relatievehoogteliggingkunstwerkdeel1")
            .statuskunstwerkdeel("statuskunstwerkdeel1");
    }

    public static Kunstwerkdeel getKunstwerkdeelSample2() {
        return new Kunstwerkdeel()
            .id(2L)
            .geometriekunstwerkdeel("geometriekunstwerkdeel2")
            .identificatiekunstwerkdeel("identificatiekunstwerkdeel2")
            .lod0geometriekunstwerkdeel("lod0geometriekunstwerkdeel2")
            .lod1geometriekunstwerkdeel("lod1geometriekunstwerkdeel2")
            .lod2geometriekunstwerkdeel("lod2geometriekunstwerkdeel2")
            .lod3geometriekunstwerkdeel("lod3geometriekunstwerkdeel2")
            .relatievehoogteliggingkunstwerkdeel("relatievehoogteliggingkunstwerkdeel2")
            .statuskunstwerkdeel("statuskunstwerkdeel2");
    }

    public static Kunstwerkdeel getKunstwerkdeelRandomSampleGenerator() {
        return new Kunstwerkdeel()
            .id(longCount.incrementAndGet())
            .geometriekunstwerkdeel(UUID.randomUUID().toString())
            .identificatiekunstwerkdeel(UUID.randomUUID().toString())
            .lod0geometriekunstwerkdeel(UUID.randomUUID().toString())
            .lod1geometriekunstwerkdeel(UUID.randomUUID().toString())
            .lod2geometriekunstwerkdeel(UUID.randomUUID().toString())
            .lod3geometriekunstwerkdeel(UUID.randomUUID().toString())
            .relatievehoogteliggingkunstwerkdeel(UUID.randomUUID().toString())
            .statuskunstwerkdeel(UUID.randomUUID().toString());
    }
}
