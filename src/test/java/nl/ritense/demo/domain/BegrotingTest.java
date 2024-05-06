package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BegrotingTestSamples.*;
import static nl.ritense.demo.domain.BegrotingregelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BegrotingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Begroting.class);
        Begroting begroting1 = getBegrotingSample1();
        Begroting begroting2 = new Begroting();
        assertThat(begroting1).isNotEqualTo(begroting2);

        begroting2.setId(begroting1.getId());
        assertThat(begroting1).isEqualTo(begroting2);

        begroting2 = getBegrotingSample2();
        assertThat(begroting1).isNotEqualTo(begroting2);
    }

    @Test
    void heeftBegrotingregelTest() throws Exception {
        Begroting begroting = getBegrotingRandomSampleGenerator();
        Begrotingregel begrotingregelBack = getBegrotingregelRandomSampleGenerator();

        begroting.addHeeftBegrotingregel(begrotingregelBack);
        assertThat(begroting.getHeeftBegrotingregels()).containsOnly(begrotingregelBack);
        assertThat(begrotingregelBack.getHeeftBegroting()).isEqualTo(begroting);

        begroting.removeHeeftBegrotingregel(begrotingregelBack);
        assertThat(begroting.getHeeftBegrotingregels()).doesNotContain(begrotingregelBack);
        assertThat(begrotingregelBack.getHeeftBegroting()).isNull();

        begroting.heeftBegrotingregels(new HashSet<>(Set.of(begrotingregelBack)));
        assertThat(begroting.getHeeftBegrotingregels()).containsOnly(begrotingregelBack);
        assertThat(begrotingregelBack.getHeeftBegroting()).isEqualTo(begroting);

        begroting.setHeeftBegrotingregels(new HashSet<>());
        assertThat(begroting.getHeeftBegrotingregels()).doesNotContain(begrotingregelBack);
        assertThat(begrotingregelBack.getHeeftBegroting()).isNull();
    }
}
