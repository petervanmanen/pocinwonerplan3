package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VerzuimTestSamples.*;
import static nl.ritense.demo.domain.VerzuimsoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerzuimsoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verzuimsoort.class);
        Verzuimsoort verzuimsoort1 = getVerzuimsoortSample1();
        Verzuimsoort verzuimsoort2 = new Verzuimsoort();
        assertThat(verzuimsoort1).isNotEqualTo(verzuimsoort2);

        verzuimsoort2.setId(verzuimsoort1.getId());
        assertThat(verzuimsoort1).isEqualTo(verzuimsoort2);

        verzuimsoort2 = getVerzuimsoortSample2();
        assertThat(verzuimsoort1).isNotEqualTo(verzuimsoort2);
    }

    @Test
    void soortverzuimVerzuimTest() throws Exception {
        Verzuimsoort verzuimsoort = getVerzuimsoortRandomSampleGenerator();
        Verzuim verzuimBack = getVerzuimRandomSampleGenerator();

        verzuimsoort.addSoortverzuimVerzuim(verzuimBack);
        assertThat(verzuimsoort.getSoortverzuimVerzuims()).containsOnly(verzuimBack);
        assertThat(verzuimBack.getSoortverzuimVerzuimsoort()).isEqualTo(verzuimsoort);

        verzuimsoort.removeSoortverzuimVerzuim(verzuimBack);
        assertThat(verzuimsoort.getSoortverzuimVerzuims()).doesNotContain(verzuimBack);
        assertThat(verzuimBack.getSoortverzuimVerzuimsoort()).isNull();

        verzuimsoort.soortverzuimVerzuims(new HashSet<>(Set.of(verzuimBack)));
        assertThat(verzuimsoort.getSoortverzuimVerzuims()).containsOnly(verzuimBack);
        assertThat(verzuimBack.getSoortverzuimVerzuimsoort()).isEqualTo(verzuimsoort);

        verzuimsoort.setSoortverzuimVerzuims(new HashSet<>());
        assertThat(verzuimsoort.getSoortverzuimVerzuims()).doesNotContain(verzuimBack);
        assertThat(verzuimBack.getSoortverzuimVerzuimsoort()).isNull();
    }
}
