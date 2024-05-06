package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MigratieingeschrevennatuurlijkpersoonTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Migratieingeschrevennatuurlijkpersoon getMigratieingeschrevennatuurlijkpersoonSample1() {
        return new Migratieingeschrevennatuurlijkpersoon()
            .id(1L)
            .aangevermigratie("aangevermigratie1")
            .redenwijzigingmigratie("redenwijzigingmigratie1")
            .soortmigratie("soortmigratie1");
    }

    public static Migratieingeschrevennatuurlijkpersoon getMigratieingeschrevennatuurlijkpersoonSample2() {
        return new Migratieingeschrevennatuurlijkpersoon()
            .id(2L)
            .aangevermigratie("aangevermigratie2")
            .redenwijzigingmigratie("redenwijzigingmigratie2")
            .soortmigratie("soortmigratie2");
    }

    public static Migratieingeschrevennatuurlijkpersoon getMigratieingeschrevennatuurlijkpersoonRandomSampleGenerator() {
        return new Migratieingeschrevennatuurlijkpersoon()
            .id(longCount.incrementAndGet())
            .aangevermigratie(UUID.randomUUID().toString())
            .redenwijzigingmigratie(UUID.randomUUID().toString())
            .soortmigratie(UUID.randomUUID().toString());
    }
}
