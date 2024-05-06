package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TeamTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Team getTeamSample1() {
        return new Team().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Team getTeamSample2() {
        return new Team().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Team getTeamRandomSampleGenerator() {
        return new Team().id(longCount.incrementAndGet()).naam(UUID.randomUUID().toString()).omschrijving(UUID.randomUUID().toString());
    }
}
