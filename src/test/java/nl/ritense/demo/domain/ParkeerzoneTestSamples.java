package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ParkeerzoneTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Parkeerzone getParkeerzoneSample1() {
        return new Parkeerzone()
            .id(1L)
            .aantalparkeervlakken("aantalparkeervlakken1")
            .eindedag("eindedag1")
            .eindtijd("eindtijd1")
            .gebruik("gebruik1")
            .geometrie("geometrie1")
            .ipmcode("ipmcode1")
            .ipmnaam("ipmnaam1")
            .naam("naam1")
            .sectorcode("sectorcode1")
            .soortcode("soortcode1")
            .startdag("startdag1")
            .starttijd("starttijd1")
            .typecode("typecode1")
            .typenaam("typenaam1");
    }

    public static Parkeerzone getParkeerzoneSample2() {
        return new Parkeerzone()
            .id(2L)
            .aantalparkeervlakken("aantalparkeervlakken2")
            .eindedag("eindedag2")
            .eindtijd("eindtijd2")
            .gebruik("gebruik2")
            .geometrie("geometrie2")
            .ipmcode("ipmcode2")
            .ipmnaam("ipmnaam2")
            .naam("naam2")
            .sectorcode("sectorcode2")
            .soortcode("soortcode2")
            .startdag("startdag2")
            .starttijd("starttijd2")
            .typecode("typecode2")
            .typenaam("typenaam2");
    }

    public static Parkeerzone getParkeerzoneRandomSampleGenerator() {
        return new Parkeerzone()
            .id(longCount.incrementAndGet())
            .aantalparkeervlakken(UUID.randomUUID().toString())
            .eindedag(UUID.randomUUID().toString())
            .eindtijd(UUID.randomUUID().toString())
            .gebruik(UUID.randomUUID().toString())
            .geometrie(UUID.randomUUID().toString())
            .ipmcode(UUID.randomUUID().toString())
            .ipmnaam(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .sectorcode(UUID.randomUUID().toString())
            .soortcode(UUID.randomUUID().toString())
            .startdag(UUID.randomUUID().toString())
            .starttijd(UUID.randomUUID().toString())
            .typecode(UUID.randomUUID().toString())
            .typenaam(UUID.randomUUID().toString());
    }
}
