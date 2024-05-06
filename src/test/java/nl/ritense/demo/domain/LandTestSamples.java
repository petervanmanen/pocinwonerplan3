package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LandTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Land getLandSample1() {
        return new Land()
            .id(1L)
            .datumeindeland("datumeindeland1")
            .datumingangland("datumingangland1")
            .landcode("landcode1")
            .landcodeisodrieletterig("landcodeisodrieletterig1")
            .landcodeisotweeletterig("landcodeisotweeletterig1")
            .landnaam("landnaam1");
    }

    public static Land getLandSample2() {
        return new Land()
            .id(2L)
            .datumeindeland("datumeindeland2")
            .datumingangland("datumingangland2")
            .landcode("landcode2")
            .landcodeisodrieletterig("landcodeisodrieletterig2")
            .landcodeisotweeletterig("landcodeisotweeletterig2")
            .landnaam("landnaam2");
    }

    public static Land getLandRandomSampleGenerator() {
        return new Land()
            .id(longCount.incrementAndGet())
            .datumeindeland(UUID.randomUUID().toString())
            .datumingangland(UUID.randomUUID().toString())
            .landcode(UUID.randomUUID().toString())
            .landcodeisodrieletterig(UUID.randomUUID().toString())
            .landcodeisotweeletterig(UUID.randomUUID().toString())
            .landnaam(UUID.randomUUID().toString());
    }
}
