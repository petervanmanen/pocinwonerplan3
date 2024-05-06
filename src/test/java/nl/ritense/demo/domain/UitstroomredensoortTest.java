package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.UitstroomredenTestSamples.*;
import static nl.ritense.demo.domain.UitstroomredensoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UitstroomredensoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Uitstroomredensoort.class);
        Uitstroomredensoort uitstroomredensoort1 = getUitstroomredensoortSample1();
        Uitstroomredensoort uitstroomredensoort2 = new Uitstroomredensoort();
        assertThat(uitstroomredensoort1).isNotEqualTo(uitstroomredensoort2);

        uitstroomredensoort2.setId(uitstroomredensoort1.getId());
        assertThat(uitstroomredensoort1).isEqualTo(uitstroomredensoort2);

        uitstroomredensoort2 = getUitstroomredensoortSample2();
        assertThat(uitstroomredensoort1).isNotEqualTo(uitstroomredensoort2);
    }

    @Test
    void soortuitstroomredenUitstroomredenTest() throws Exception {
        Uitstroomredensoort uitstroomredensoort = getUitstroomredensoortRandomSampleGenerator();
        Uitstroomreden uitstroomredenBack = getUitstroomredenRandomSampleGenerator();

        uitstroomredensoort.addSoortuitstroomredenUitstroomreden(uitstroomredenBack);
        assertThat(uitstroomredensoort.getSoortuitstroomredenUitstroomredens()).containsOnly(uitstroomredenBack);
        assertThat(uitstroomredenBack.getSoortuitstroomredenUitstroomredensoort()).isEqualTo(uitstroomredensoort);

        uitstroomredensoort.removeSoortuitstroomredenUitstroomreden(uitstroomredenBack);
        assertThat(uitstroomredensoort.getSoortuitstroomredenUitstroomredens()).doesNotContain(uitstroomredenBack);
        assertThat(uitstroomredenBack.getSoortuitstroomredenUitstroomredensoort()).isNull();

        uitstroomredensoort.soortuitstroomredenUitstroomredens(new HashSet<>(Set.of(uitstroomredenBack)));
        assertThat(uitstroomredensoort.getSoortuitstroomredenUitstroomredens()).containsOnly(uitstroomredenBack);
        assertThat(uitstroomredenBack.getSoortuitstroomredenUitstroomredensoort()).isEqualTo(uitstroomredensoort);

        uitstroomredensoort.setSoortuitstroomredenUitstroomredens(new HashSet<>());
        assertThat(uitstroomredensoort.getSoortuitstroomredenUitstroomredens()).doesNotContain(uitstroomredenBack);
        assertThat(uitstroomredenBack.getSoortuitstroomredenUitstroomredensoort()).isNull();
    }
}
