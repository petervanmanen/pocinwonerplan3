package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MuseumobjectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Museumobject getMuseumobjectSample1() {
        return new Museumobject().id(1L).afmeting("afmeting1").medium("medium1").verkrijging("verkrijging1");
    }

    public static Museumobject getMuseumobjectSample2() {
        return new Museumobject().id(2L).afmeting("afmeting2").medium("medium2").verkrijging("verkrijging2");
    }

    public static Museumobject getMuseumobjectRandomSampleGenerator() {
        return new Museumobject()
            .id(longCount.incrementAndGet())
            .afmeting(UUID.randomUUID().toString())
            .medium(UUID.randomUUID().toString())
            .verkrijging(UUID.randomUUID().toString());
    }
}
