package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProjectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Project getProjectSample1() {
        return new Project()
            .id(1L)
            .coordinaten("coordinaten1")
            .jaartot("jaartot1")
            .jaarvan("jaarvan1")
            .locatie("locatie1")
            .naam("naam1")
            .naamcode("naamcode1")
            .projectcd("projectcd1")
            .toponiem("toponiem1")
            .trefwoorden("trefwoorden1");
    }

    public static Project getProjectSample2() {
        return new Project()
            .id(2L)
            .coordinaten("coordinaten2")
            .jaartot("jaartot2")
            .jaarvan("jaarvan2")
            .locatie("locatie2")
            .naam("naam2")
            .naamcode("naamcode2")
            .projectcd("projectcd2")
            .toponiem("toponiem2")
            .trefwoorden("trefwoorden2");
    }

    public static Project getProjectRandomSampleGenerator() {
        return new Project()
            .id(longCount.incrementAndGet())
            .coordinaten(UUID.randomUUID().toString())
            .jaartot(UUID.randomUUID().toString())
            .jaarvan(UUID.randomUUID().toString())
            .locatie(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .naamcode(UUID.randomUUID().toString())
            .projectcd(UUID.randomUUID().toString())
            .toponiem(UUID.randomUUID().toString())
            .trefwoorden(UUID.randomUUID().toString());
    }
}
