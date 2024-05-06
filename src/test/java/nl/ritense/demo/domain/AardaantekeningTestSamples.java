package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AardaantekeningTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Aardaantekening getAardaantekeningSample1() {
        return new Aardaantekening().id(1L).codeaardaantekening("codeaardaantekening1").naamaardaantekening("naamaardaantekening1");
    }

    public static Aardaantekening getAardaantekeningSample2() {
        return new Aardaantekening().id(2L).codeaardaantekening("codeaardaantekening2").naamaardaantekening("naamaardaantekening2");
    }

    public static Aardaantekening getAardaantekeningRandomSampleGenerator() {
        return new Aardaantekening()
            .id(longCount.incrementAndGet())
            .codeaardaantekening(UUID.randomUUID().toString())
            .naamaardaantekening(UUID.randomUUID().toString());
    }
}
