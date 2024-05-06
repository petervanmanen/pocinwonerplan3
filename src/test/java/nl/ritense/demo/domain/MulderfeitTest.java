package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.MulderfeitTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MulderfeitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mulderfeit.class);
        Mulderfeit mulderfeit1 = getMulderfeitSample1();
        Mulderfeit mulderfeit2 = new Mulderfeit();
        assertThat(mulderfeit1).isNotEqualTo(mulderfeit2);

        mulderfeit2.setId(mulderfeit1.getId());
        assertThat(mulderfeit1).isEqualTo(mulderfeit2);

        mulderfeit2 = getMulderfeitSample2();
        assertThat(mulderfeit1).isNotEqualTo(mulderfeit2);
    }
}
