package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BijzonderheidTestSamples.*;
import static nl.ritense.demo.domain.BijzonderheidsoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BijzonderheidsoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bijzonderheidsoort.class);
        Bijzonderheidsoort bijzonderheidsoort1 = getBijzonderheidsoortSample1();
        Bijzonderheidsoort bijzonderheidsoort2 = new Bijzonderheidsoort();
        assertThat(bijzonderheidsoort1).isNotEqualTo(bijzonderheidsoort2);

        bijzonderheidsoort2.setId(bijzonderheidsoort1.getId());
        assertThat(bijzonderheidsoort1).isEqualTo(bijzonderheidsoort2);

        bijzonderheidsoort2 = getBijzonderheidsoortSample2();
        assertThat(bijzonderheidsoort1).isNotEqualTo(bijzonderheidsoort2);
    }

    @Test
    void isvansoortBijzonderheidTest() throws Exception {
        Bijzonderheidsoort bijzonderheidsoort = getBijzonderheidsoortRandomSampleGenerator();
        Bijzonderheid bijzonderheidBack = getBijzonderheidRandomSampleGenerator();

        bijzonderheidsoort.addIsvansoortBijzonderheid(bijzonderheidBack);
        assertThat(bijzonderheidsoort.getIsvansoortBijzonderheids()).containsOnly(bijzonderheidBack);
        assertThat(bijzonderheidBack.getIsvansoortBijzonderheidsoort()).isEqualTo(bijzonderheidsoort);

        bijzonderheidsoort.removeIsvansoortBijzonderheid(bijzonderheidBack);
        assertThat(bijzonderheidsoort.getIsvansoortBijzonderheids()).doesNotContain(bijzonderheidBack);
        assertThat(bijzonderheidBack.getIsvansoortBijzonderheidsoort()).isNull();

        bijzonderheidsoort.isvansoortBijzonderheids(new HashSet<>(Set.of(bijzonderheidBack)));
        assertThat(bijzonderheidsoort.getIsvansoortBijzonderheids()).containsOnly(bijzonderheidBack);
        assertThat(bijzonderheidBack.getIsvansoortBijzonderheidsoort()).isEqualTo(bijzonderheidsoort);

        bijzonderheidsoort.setIsvansoortBijzonderheids(new HashSet<>());
        assertThat(bijzonderheidsoort.getIsvansoortBijzonderheids()).doesNotContain(bijzonderheidBack);
        assertThat(bijzonderheidBack.getIsvansoortBijzonderheidsoort()).isNull();
    }
}
