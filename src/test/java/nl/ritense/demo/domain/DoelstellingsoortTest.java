package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.DoelstellingTestSamples.*;
import static nl.ritense.demo.domain.DoelstellingsoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DoelstellingsoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Doelstellingsoort.class);
        Doelstellingsoort doelstellingsoort1 = getDoelstellingsoortSample1();
        Doelstellingsoort doelstellingsoort2 = new Doelstellingsoort();
        assertThat(doelstellingsoort1).isNotEqualTo(doelstellingsoort2);

        doelstellingsoort2.setId(doelstellingsoort1.getId());
        assertThat(doelstellingsoort1).isEqualTo(doelstellingsoort2);

        doelstellingsoort2 = getDoelstellingsoortSample2();
        assertThat(doelstellingsoort1).isNotEqualTo(doelstellingsoort2);
    }

    @Test
    void isvansoortDoelstellingTest() throws Exception {
        Doelstellingsoort doelstellingsoort = getDoelstellingsoortRandomSampleGenerator();
        Doelstelling doelstellingBack = getDoelstellingRandomSampleGenerator();

        doelstellingsoort.addIsvansoortDoelstelling(doelstellingBack);
        assertThat(doelstellingsoort.getIsvansoortDoelstellings()).containsOnly(doelstellingBack);
        assertThat(doelstellingBack.getIsvansoortDoelstellingsoort()).isEqualTo(doelstellingsoort);

        doelstellingsoort.removeIsvansoortDoelstelling(doelstellingBack);
        assertThat(doelstellingsoort.getIsvansoortDoelstellings()).doesNotContain(doelstellingBack);
        assertThat(doelstellingBack.getIsvansoortDoelstellingsoort()).isNull();

        doelstellingsoort.isvansoortDoelstellings(new HashSet<>(Set.of(doelstellingBack)));
        assertThat(doelstellingsoort.getIsvansoortDoelstellings()).containsOnly(doelstellingBack);
        assertThat(doelstellingBack.getIsvansoortDoelstellingsoort()).isEqualTo(doelstellingsoort);

        doelstellingsoort.setIsvansoortDoelstellings(new HashSet<>());
        assertThat(doelstellingsoort.getIsvansoortDoelstellings()).doesNotContain(doelstellingBack);
        assertThat(doelstellingBack.getIsvansoortDoelstellingsoort()).isNull();
    }
}
