package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BijzonderheidTestSamples.*;
import static nl.ritense.demo.domain.BijzonderheidsoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BijzonderheidTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bijzonderheid.class);
        Bijzonderheid bijzonderheid1 = getBijzonderheidSample1();
        Bijzonderheid bijzonderheid2 = new Bijzonderheid();
        assertThat(bijzonderheid1).isNotEqualTo(bijzonderheid2);

        bijzonderheid2.setId(bijzonderheid1.getId());
        assertThat(bijzonderheid1).isEqualTo(bijzonderheid2);

        bijzonderheid2 = getBijzonderheidSample2();
        assertThat(bijzonderheid1).isNotEqualTo(bijzonderheid2);
    }

    @Test
    void isvansoortBijzonderheidsoortTest() throws Exception {
        Bijzonderheid bijzonderheid = getBijzonderheidRandomSampleGenerator();
        Bijzonderheidsoort bijzonderheidsoortBack = getBijzonderheidsoortRandomSampleGenerator();

        bijzonderheid.setIsvansoortBijzonderheidsoort(bijzonderheidsoortBack);
        assertThat(bijzonderheid.getIsvansoortBijzonderheidsoort()).isEqualTo(bijzonderheidsoortBack);

        bijzonderheid.isvansoortBijzonderheidsoort(null);
        assertThat(bijzonderheid.getIsvansoortBijzonderheidsoort()).isNull();
    }
}
