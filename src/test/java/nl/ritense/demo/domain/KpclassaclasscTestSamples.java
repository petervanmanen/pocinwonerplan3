package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class KpclassaclasscTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Kpclassaclassc getKpclassaclasscSample1() {
        return new Kpclassaclassc().id(1L).mbronsysteem("mbronsysteem1").mdatumtijdgeladen("mdatumtijdgeladen1").classcid(1).classaid(1);
    }

    public static Kpclassaclassc getKpclassaclasscSample2() {
        return new Kpclassaclassc().id(2L).mbronsysteem("mbronsysteem2").mdatumtijdgeladen("mdatumtijdgeladen2").classcid(2).classaid(2);
    }

    public static Kpclassaclassc getKpclassaclasscRandomSampleGenerator() {
        return new Kpclassaclassc()
            .id(longCount.incrementAndGet())
            .mbronsysteem(UUID.randomUUID().toString())
            .mdatumtijdgeladen(UUID.randomUUID().toString())
            .classcid(intCount.incrementAndGet())
            .classaid(intCount.incrementAndGet());
    }
}
