package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BruikleenTestSamples.*;
import static nl.ritense.demo.domain.LenerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LenerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lener.class);
        Lener lener1 = getLenerSample1();
        Lener lener2 = new Lener();
        assertThat(lener1).isNotEqualTo(lener2);

        lener2.setId(lener1.getId());
        assertThat(lener1).isEqualTo(lener2);

        lener2 = getLenerSample2();
        assertThat(lener1).isNotEqualTo(lener2);
    }

    @Test
    void isBruikleenTest() throws Exception {
        Lener lener = getLenerRandomSampleGenerator();
        Bruikleen bruikleenBack = getBruikleenRandomSampleGenerator();

        lener.addIsBruikleen(bruikleenBack);
        assertThat(lener.getIsBruikleens()).containsOnly(bruikleenBack);

        lener.removeIsBruikleen(bruikleenBack);
        assertThat(lener.getIsBruikleens()).doesNotContain(bruikleenBack);

        lener.isBruikleens(new HashSet<>(Set.of(bruikleenBack)));
        assertThat(lener.getIsBruikleens()).containsOnly(bruikleenBack);

        lener.setIsBruikleens(new HashSet<>());
        assertThat(lener.getIsBruikleens()).doesNotContain(bruikleenBack);
    }
}
