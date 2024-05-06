package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ContainerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Container getContainerSample1() {
        return new Container().id(1L).containercode("containercode1").sensorid("sensorid1");
    }

    public static Container getContainerSample2() {
        return new Container().id(2L).containercode("containercode2").sensorid("sensorid2");
    }

    public static Container getContainerRandomSampleGenerator() {
        return new Container()
            .id(longCount.incrementAndGet())
            .containercode(UUID.randomUUID().toString())
            .sensorid(UUID.randomUUID().toString());
    }
}
