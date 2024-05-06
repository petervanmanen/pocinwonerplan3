package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class IngezeteneTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Ingezetene getIngezeteneSample1() {
        return new Ingezetene()
            .id(1L)
            .datumverkrijgingverblijfstitel("datumverkrijgingverblijfstitel1")
            .datumverliesverblijfstitel("datumverliesverblijfstitel1")
            .indicatieblokkering("indicatieblokkering1")
            .indicatiecurateleregister("indicatiecurateleregister1")
            .indicatiegezagminderjarige("indicatiegezagminderjarige1");
    }

    public static Ingezetene getIngezeteneSample2() {
        return new Ingezetene()
            .id(2L)
            .datumverkrijgingverblijfstitel("datumverkrijgingverblijfstitel2")
            .datumverliesverblijfstitel("datumverliesverblijfstitel2")
            .indicatieblokkering("indicatieblokkering2")
            .indicatiecurateleregister("indicatiecurateleregister2")
            .indicatiegezagminderjarige("indicatiegezagminderjarige2");
    }

    public static Ingezetene getIngezeteneRandomSampleGenerator() {
        return new Ingezetene()
            .id(longCount.incrementAndGet())
            .datumverkrijgingverblijfstitel(UUID.randomUUID().toString())
            .datumverliesverblijfstitel(UUID.randomUUID().toString())
            .indicatieblokkering(UUID.randomUUID().toString())
            .indicatiecurateleregister(UUID.randomUUID().toString())
            .indicatiegezagminderjarige(UUID.randomUUID().toString());
    }
}
